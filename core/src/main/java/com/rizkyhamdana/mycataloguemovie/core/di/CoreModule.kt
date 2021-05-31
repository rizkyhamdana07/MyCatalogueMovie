package com.rizkyhamdana.mycataloguemovie.core.di

import androidx.room.Room
import com.rizkyhamdana.mycataloguemovie.core.BuildConfig
import com.rizkyhamdana.mycataloguemovie.core.data.CatalogueRepository
import com.rizkyhamdana.mycataloguemovie.core.data.source.local.LocalDataSource
import com.rizkyhamdana.mycataloguemovie.core.data.source.local.room.CatalogueDatabase
import com.rizkyhamdana.mycataloguemovie.core.data.source.remote.RemoteDataSource
import com.rizkyhamdana.mycataloguemovie.core.data.source.remote.api.ApiService
import com.rizkyhamdana.mycataloguemovie.core.domain.repository.ICatalogueRepository
import com.rizkyhamdana.mycataloguemovie.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module{
    factory { get<CatalogueDatabase>().catalogueDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            CatalogueDatabase::class.java,
            "favorite_database"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }

}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<ICatalogueRepository> {
        CatalogueRepository(
            get(),
            get(),
            get()
        )
    }
}