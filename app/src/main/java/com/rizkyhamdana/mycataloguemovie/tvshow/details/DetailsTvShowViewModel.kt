package com.rizkyhamdana.mycataloguemovie.tvshow.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rizkyhamdana.mycataloguemovie.core.domain.model.TvShow
import com.rizkyhamdana.mycataloguemovie.core.domain.usecase.CatalogueUseCase

class DetailsTvShowViewModel(private val catalogueUseCase: CatalogueUseCase): ViewModel() {

    fun detailsTvShow(id: Int) = catalogueUseCase.getDetailsTv(id).asLiveData()

    fun setFavoriteTv(tvShow: TvShow, state: Boolean) = catalogueUseCase.setTvFavorite(tvShow, state)


}