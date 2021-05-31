package com.rizkyhamdana.mycataloguemovie.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.rizkyhamdana.mycataloguemovie.core.data.Resource
import com.rizkyhamdana.mycataloguemovie.core.domain.model.TvShow
import com.rizkyhamdana.mycataloguemovie.core.ui.TvShowAdapter
import com.rizkyhamdana.mycataloguemovie.databinding.FragmentTvShowBinding
import com.rizkyhamdana.mycataloguemovie.tvshow.details.DetailsTvShowActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class TvShowFragment : Fragment() {


    private val tvShowViewModel: TvShowViewModel by viewModel()
    private lateinit var tvShowAdapter: TvShowAdapter
    private var _binding: FragmentTvShowBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTvShowBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvShowAdapter = TvShowAdapter()

        tvShowViewModel.popularTv.observe(viewLifecycleOwner, { tvShow ->
            if (tvShow != null) {
                when (tvShow) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        tvShowAdapter.setTvShow(tvShow.data)
                    }
                    is Resource.Error -> {

                    }
                }
            }
        })

        val pageTransformer = pageTransformer()

        val vpTvShow : ViewPager2 = binding.vpTvShow
        vpTvShow.apply {
            adapter = tvShowAdapter
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(pageTransformer)
        }



        tvShowAdapter.setOnItemClickCallback(object : TvShowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TvShow) {
                val intent = Intent(activity, DetailsTvShowActivity::class.java)
                intent.putExtra(DetailsTvShowActivity.EXTRA_ID, data.id)
                startActivity(intent)
            }
        })


    }
    private fun pageTransformer(): CompositePageTransformer {
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val r: Float = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        return compositePageTransformer
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}