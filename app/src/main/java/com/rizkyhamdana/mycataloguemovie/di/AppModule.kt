package com.rizkyhamdana.mycataloguemovie.di

import com.rizkyhamdana.mycataloguemovie.core.domain.usecase.CatalogueInteractor
import com.rizkyhamdana.mycataloguemovie.core.domain.usecase.CatalogueUseCase
import com.rizkyhamdana.mycataloguemovie.movie.MovieViewModel
import com.rizkyhamdana.mycataloguemovie.movie.details.DetailsMovieViewModel
import com.rizkyhamdana.mycataloguemovie.tvshow.TvShowViewModel
import com.rizkyhamdana.mycataloguemovie.tvshow.details.DetailsTvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<CatalogueUseCase> { CatalogueInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
    viewModel { DetailsMovieViewModel(get()) }
    viewModel { DetailsTvShowViewModel(get()) }

}