package com.tngdev.sportevent.ui.matchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tngdev.sportevent.R
import com.tngdev.sportevent.databinding.ActivityMainBinding
import com.tngdev.sportevent.databinding.FragmentMatchListBinding
import com.tngdev.sportevent.model.MatchItem
import com.tngdev.sportevent.network.ApiResource
import com.tngdev.sportevent.ui.matchlist.adapter.MatchListAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
open class MatchListFragment : Fragment() {

    companion object {
        fun newInstance() = MatchListFragment()
    }

    protected val viewModel by lazy { ViewModelProvider(this)[MatchListViewModel::class.java] }

    private var _binding: FragmentMatchListBinding? = null
    private val binding get() = _binding!!

    private var adapter: MatchListAdapter? = null
    private var noInternetSnackbar: Snackbar? = null
    private var isRefreshingData = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMatchListBinding.inflate(inflater, container, false)
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

        binding.tvAllTeam.setOnClickListener {
            // navigate to team screen
            val action =
                MatchListFragmentDirections
                    .actionMatchListFragmentToTeamListFragment()
            findNavController().navigate(action)
        }
    }

    private fun observeData() {
        viewModel.matchListResponse.observe(viewLifecycleOwner) {
            showResultData(it)
            if ((it is ApiResource.Loading).not())
                isRefreshingData = false
        }
    }

    private fun navigateToDetail(matchItem: MatchItem) {
        val action =
            MatchListFragmentDirections.actionMatchListFragmentToMatchDetailFragment(matchItem)
        findNavController().navigate(action)
    }

    private fun showResultData(apiResource: ApiResource<List<MatchItem>>) {
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
        adapter = MatchListAdapter()
        binding.contentList.rvList.also {
            it.adapter = adapter
            it.setHasFixedSize(true)
        }
    }

    open fun loadListData() {
        viewModel.getMatchList()
    }

    private fun refreshData() {
        isRefreshingData = true
        loadListData()
    }

    private fun showData(data: List<MatchItem>) {
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