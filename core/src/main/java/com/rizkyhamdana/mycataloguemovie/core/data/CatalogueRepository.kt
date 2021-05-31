package com.rizkyhamdana.mycataloguemovie.core.data

import com.rizkyhamdana.mycataloguemovie.core.data.source.local.LocalDataSource
import com.rizkyhamdana.mycataloguemovie.core.data.source.remote.RemoteDataSource
import com.rizkyhamdana.mycataloguemovie.core.data.source.remote.api.ApiResponse
import com.rizkyhamdana.mycataloguemovie.core.data.source.remote.response.DetailsMovieResponse
import com.rizkyhamdana.mycataloguemovie.core.data.source.remote.response.DetailsTvResponse
import com.rizkyhamdana.mycataloguemovie.core.data.source.remote.response.ResultsMovie
import com.rizkyhamdana.mycataloguemovie.core.data.source.remote.response.ResultsTv
import com.rizkyhamdana.mycataloguemovie.core.domain.model.Movies
import com.rizkyhamdana.mycataloguemovie.core.domain.model.TvShow
import com.rizkyhamdana.mycataloguemovie.core.domain.repository.ICatalogueRepository
import com.rizkyhamdana.mycataloguemovie.core.utils.AppExecutors
import com.rizkyhamdana.mycataloguemovie.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CatalogueRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): ICatalogueRepository {

    override fun getMoviePopular(): Flow<Resource<List<Movies>>> =
        object :
            NetworkBoundResource<List<Movies>, List<ResultsMovie>>() {
            override fun loadFromDB(): Flow<List<Movies>> {
                return localDataSource.getAllMovie().map {
                    DataMapper.mapListMovieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movies>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsMovie>>> =
                remoteDataSource.getPopularMovie()

            override suspend fun saveCallResult(data: List<ResultsMovie>) {
                val moviesList = DataMapper.mapListMovieResponseToEntities(data)
                localDataSource.insertMovie(moviesList)
            }

        }.asFlow()


    override fun getTvPopular(): Flow<Resource<List<TvShow>>> =
        object :
            NetworkBoundResource<List<TvShow>, List<ResultsTv>>() {
            override fun loadFromDB(): Flow<List<TvShow>> {
                return localDataSource.getAllTvShow().map {
                    DataMapper.mapListTvEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<TvShow>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsTv>>> =
                remoteDataSource.getPopularTv()

            override suspend fun saveCallResult(data: List<ResultsTv>) {
                val tvShowList = DataMapper.mapListTvResponseToEntities(data)
                localDataSource.insertTv(tvShowList)
            }

        }.asFlow()


    override fun getDetailsMovie(id: Int): Flow<Resource<Movies>> =
        object :
            NetworkBoundResource<Movies, DetailsMovieResponse>() {
            override fun loadFromDB(): Flow<Movies> {
                return localDataSource.getDetailsMovie(id).map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: Movies?): Boolean {
                return data != null && !data.isFavorite
            }

            override suspend fun createCall(): Flow<ApiResponse<DetailsMovieResponse>> =
                remoteDataSource.getDetailsMovie(id)

            override suspend fun saveCallResult(data: DetailsMovieResponse) {
                val movies = DataMapper.mapDetailsMovieToEntities(data)
                localDataSource.updateMovie(movies)
            }

        }.asFlow()


    override fun getDetailsTv(id: Int): Flow<Resource<TvShow>> =
        object :
            NetworkBoundResource<TvShow, DetailsTvResponse>() {
            override fun loadFromDB(): Flow<TvShow> {
                return localDataSource.getDetailsTv(id).map {
                    DataMapper.mapTvEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: TvShow?): Boolean {
                return data != null && !data.isFavorite
            }

            override suspend fun createCall(): Flow<ApiResponse<DetailsTvResponse>> =
                remoteDataSource.getDetailsTv(id)

            override suspend fun saveCallResult(data: DetailsTvResponse) {
                val tvShow = DataMapper.mapDetailsTvToEntities(data)
                localDataSource.updateTv(tvShow)
            }

        }.asFlow()

    override fun getMovieFavorite(): Flow<List<Movies>> {
        return localDataSource.getMovieFavorite().map {
            DataMapper.mapListMovieEntitiesToDomain(it)
        }
    }

    override fun getTvFavorite(): Flow<List<TvShow>> {
        return localDataSource.getTvShowFavorite().map {
            DataMapper.mapListTvEntitiesToDomain(it)
        }
    }

    override fun setMovieFavorite(movies: Movies, state: Boolean) {
        val movieEntity = DataMapper.mapMovieDomainToEntities(movies)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteMovie(movieEntity, state)
        }
    }

    override fun setTvFavorite(tvShow: TvShow, state: Boolean) {
        val tvShowEntity = DataMapper.mapTvDomainToEntities(tvShow)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteTvShow(tvShowEntity, state)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: CatalogueRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): CatalogueRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: CatalogueRepository(
                        remoteDataSource,
                        localDataSource,
                        appExecutors
                    )
            }
    }

}