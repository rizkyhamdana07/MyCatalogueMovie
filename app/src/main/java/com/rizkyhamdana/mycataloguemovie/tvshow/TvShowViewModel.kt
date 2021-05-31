package com.rizkyhamdana.mycataloguemovie.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rizkyhamdana.mycataloguemovie.core.domain.usecase.CatalogueUseCase

class TvShowViewModel(catalogueUseCase: CatalogueUseCase) : ViewModel() {

    val popularTv = catalogueUseCase.getTvPopular().asLiveData()

}