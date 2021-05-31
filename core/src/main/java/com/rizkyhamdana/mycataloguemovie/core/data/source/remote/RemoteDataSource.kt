package com.rizkyhamdana.mycataloguemovie.core.data.source.remote

import android.util.Log
import com.rizkyhamdana.mycataloguemovie.core.data.source.remote.api.ApiResponse
import com.rizkyhamdana.mycataloguemovie.core.data.source.remote.api.ApiService
import com.rizkyhamdana.mycataloguemovie.core.data.source.remote.response.DetailsMovieResponse
import com.rizkyhamdana.mycataloguemovie.core.data.source.remote.response.DetailsTvResponse
import com.rizkyhamdana.mycataloguemovie.core.data.source.remote.response.ResultsMovie
import com.rizkyhamdana.mycataloguemovie.core.data.source.remote.response.ResultsTv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {


    fun getPopularMovie(): Flow<ApiResponse<List<ResultsMovie>>> {
        return flow {
            try {
                val response = apiService.getPopularMovie()
                val listMovie = response.results
                if(listMovie.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getPopularTv(): Flow<ApiResponse<List<ResultsTv>>> {
        return flow {
            try {
                val response = apiService.getPopularTvShow()
                val listtv = response.results
                if(listtv.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getDetailsMovie(movieId : Int): Flow<ApiResponse<DetailsMovieResponse>> {
        return flow {
            try {
                val movie = apiService.getMovieDetails(movieId)
                emit(ApiResponse.Success(movie))
            }catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getDetailsTv(tvId: Int): Flow<ApiResponse<DetailsTvResponse>> {
        return flow {
            try {
                val tvShow = apiService.getTvDetails(tvId)
                emit(ApiResponse.Success(tvShow))
            }catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(apiService)
            }
    }

}