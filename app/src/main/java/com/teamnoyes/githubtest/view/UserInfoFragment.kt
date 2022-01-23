package com.teamnoyes.githubtest.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamnoyes.githubtest.R
import com.teamnoyes.githubtest.adapter.UserInfoAdapter
import com.teamnoyes.githubtest.databinding.FragmentUserInfoBinding
import com.teamnoyes.githubtest.domain.usecase.GetUserEventUseCase
import com.teamnoyes.githubtest.domain.usecase.GetUserInfoUseCase
import com.teamnoyes.githubtest.domain.usecase.GetUserRepoUseCase
import com.teamnoyes.githubtest.environments.GithubTestApplication
import com.teamnoyes.githubtest.utils.ConstValues
import com.teamnoyes.githubtest.utils.NetworkLiveData
import com.teamnoyes.githubtest.viewmodel.UserInfoViewModel
import com.teamnoyes.githubtest.viewmodelfactory.UserInfoViewModelFactory

class UserInfoFragment : Fragment(R.layout.fragment_user_info) {
    private lateinit var binding: FragmentUserInfoBinding
    private val userInfoViewModel: UserInfoViewModel by viewModels {
        UserInfoViewModelFactory(
            GetUserInfoUseCase(
                (requireActivity().application as GithubTestApplication).githubRepository
            ),
            GetUserRepoUseCase(
                (requireActivity().application as GithubTestApplication).githubRepository
            ),
            GetUserEventUseCase(
                (requireActivity().application as GithubTestApplication).githubRepository
            )
        )
    }
    private var userId: String? = null

    private lateinit var adapter: UserInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            userId = it.getString("userId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentUserInfoBinding.bind(view)
        initRecyclerView()
        initViews()
        setOnUserDataObserver()
        setOnRefreshObserver()
        setOnNetworkingObserver()

        if (savedInstanceState == null) {
            initData()
        }
    }

    private fun initViews() = with(binding) {
        userInfoToolbar.setNavigationIcon(R.drawable.arrow_back_24)
        userInfoToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        userId?.let {
            userInfoToolbar.title = it
        }

        userInfoSwipeView.setOnRefreshListener {
            userInfoViewModel.resetCurrentPage()
            resetRecyclerView()
            userInfoViewModel.getResetUserData()
        }
    }

    private fun initRecyclerView() = with(binding) {
        adapter = UserInfoAdapter()

        userInfoRecyclerView.adapter = adapter

        userInfoRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (userInfoViewModel.networkState.value == ConstValues.NETWORK_LOADING) {
                    return
                }

                if (userInfoViewModel.emptyState.value == ConstValues.RESULT_EMPTY) {
                    return
                }

                val lastVisibleItem =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                val lastCount = adapter.itemCount - 1

                if (lastVisibleItem == lastCount && (lastVisibleItem != -1 || lastCount != -1)) {
                    // 다음 거 가져오기
                    if (NetworkLiveData.isNetworkAvailable()) {
                        userInfoViewModel.getNextEventData()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.pleas_check_your_network),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    private fun setOnUserDataObserver() {
        userInfoViewModel.userDataList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun setOnRefreshObserver() {
        userInfoViewModel.resetState.observe(viewLifecycleOwner, {
            if (it) {
                binding.userInfoSwipeView.isRefreshing = false
            }
        })
    }

    private fun setOnNetworkingObserver() {
        userInfoViewModel.networkState.observe(viewLifecycleOwner, {
            when (it) {
                ConstValues.NETWORK_LOADING -> {
                    setOnProgressbarVisibility(View.VISIBLE)
                }
                ConstValues.NETWORK_ERROR -> {
                    setOnProgressbarVisibility(View.GONE)
                    Toast.makeText(requireContext(), "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    setOnProgressbarVisibility(View.GONE)
                }
            }
        })
    }

    private fun initData() {
        userId?.let {
            userInfoViewModel.getUserData(
                it,
                getString(R.string.divide_user_info),
                getString(R.string.divider_user_repo),
                getString(R.string.divider_user_event)
            )
        }
    }

    private fun resetRecyclerView() = with(binding) {
        userInfoViewModel.clearData()
        adapter.submitList(null)
    }

    private fun setOnProgressbarVisibility(visibility: Int) = with(binding) {
        userInfoProgressbar.visibility = visibility
    }
}