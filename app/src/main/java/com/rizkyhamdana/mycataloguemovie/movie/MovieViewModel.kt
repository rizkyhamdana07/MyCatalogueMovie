package com.rizkyhamdana.mycataloguemovie.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rizkyhamdana.mycataloguemovie.core.domain.usecase.CatalogueUseCase


class MovieViewModel(catalogueUseCase: CatalogueUseCase) : ViewModel() {

    val popularMovie = catalogueUseCase.getMoviePopular().asLiveData()

}