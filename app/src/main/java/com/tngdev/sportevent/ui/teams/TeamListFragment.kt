package com.tngdev.sportevent.ui.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tngdev.sportevent.R
import com.tngdev.sportevent.databinding.FragmentTeamListBinding
import com.tngdev.sportevent.model.TeamItem
import com.tngdev.sportevent.network.ApiResource
import com.tngdev.sportevent.ui.teams.adapter.TeamListAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
open class TeamListFragment : Fragment() {

    companion object {
        fun newInstance() = TeamListFragment()
    }

    protected val viewModel by lazy { ViewModelProvider(this)[TeamListViewModel::class.java] }

    private var _binding: FragmentTeamListBinding? = null
    private val binding get() = _binding!!

    private var adapter: TeamListAdapter? = null
    private var noInternetSnackbar: Snackbar? = null
    private var isRefreshingData = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("onViewCreated")

        initAdapter()
        observeData()
        bindEvents()
        bindData()

        refreshData()
        isRefreshingData = false
    }

    private fun bindData() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            showError = viewModel.showError
            errorMessage = viewModel.errorMessage
        }
    }

    private fun bindEvents() {

        binding.contentList.srlList.setOnRefreshListener {
            refreshData()
        }

        binding.contentList.btRetry.setOnClickListener {
            viewModel.hideError()
            refreshData()
        }

        adapter?.onItemClickListener = {
            navigateToDetail(it)
        }

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeData() {
        viewModel.teamListResponse.observe(viewLifecycleOwner) {
            showResultData(it)
            if ((it is ApiResource.Loading).not())
                isRefreshingData = false
        }
    }

    private fun navigateToDetail(teamItem: TeamItem) {
//        val action =
//            MovieListFragmentDirections
//                .actionMovieListFragmentToMovieDetailFragment(movieItem)
//        findNavController().navigate(action)
    }

    private fun showResultData(apiResource: ApiResource<List<TeamItem>>) {
        hideLoading()
        hideNoInternet()
        viewModel.hideError()
        when (apiResource) {
            is ApiResource.Success -> {
                apiResource.data?.let {
                    showData(it)
                }
            }
            is ApiResource.Error -> {
                if (adapter?.itemCount == 0)
                    viewModel.setShowError(getString(R.string.unable_to_get_data))
                else
                    showSnackbarError(getString(R.string.unable_to_get_data))
            }
            is ApiResource.NoInternet -> {
                if (adapter?.itemCount == 0)
                    viewModel.setShowError(getString(R.string.no_internet))
                else
                    showSnackbarError(getString(R.string.no_internet))
            }
            is ApiResource.Loading -> {
                showLoading()
            }
        }
    }

    private fun initAdapter() {
        adapter = TeamListAdapter()
        binding.contentList.rvList.also {
            it.layoutManager = GridLayoutManager(requireContext(), 2)
            it.adapter = adapter
            it.setHasFixedSize(true)
        }
    }

    open fun loadListData() {
        viewModel.getAllTeam()
    }

    private fun refreshData() {
        isRefreshingData = true
        loadListData()
    }

    private fun showData(data: List<TeamItem>) {
        adapter?.submitList(data)
    }

    private fun showLoading() {
            binding.contentList.srlList.isRefreshing = true
    }

    private fun hideLoading() {
        binding.contentList.srlList.isRefreshing = false
    }

    private fun showSnackbarError(message: CharSequence) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showNoInternet() {
        if (noInternetSnackbar == null)
            noInternetSnackbar = Snackbar.make(binding.root, getText(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
        noInternetSnackbar?.show()
    }

    private fun hideNoInternet() {
        noInternetSnackbar?.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}