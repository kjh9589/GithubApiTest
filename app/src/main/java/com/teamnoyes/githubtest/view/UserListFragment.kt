package com.teamnoyes.githubtest.view

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamnoyes.githubtest.R
import com.teamnoyes.githubtest.adapter.UserListAdapter
import com.teamnoyes.githubtest.databinding.FragmentUserListBinding
import com.teamnoyes.githubtest.domain.usecase.GetUserListUseCase
import com.teamnoyes.githubtest.environments.GithubTestApplication
import com.teamnoyes.githubtest.utils.ConstValues
import com.teamnoyes.githubtest.utils.ConstValues.NETWORK_ERROR
import com.teamnoyes.githubtest.utils.ConstValues.NETWORK_LOADING
import com.teamnoyes.githubtest.utils.NetworkLiveData
import com.teamnoyes.githubtest.viewmodel.UserListViewModel
import com.teamnoyes.githubtest.viewmodelfactory.UserListViewModelFactory

class UserListFragment : Fragment(R.layout.fragment_user_list) {
    private lateinit var binding: FragmentUserListBinding
    private val userListViewModel: UserListViewModel by viewModels {
        UserListViewModelFactory(
            GetUserListUseCase(
                (requireActivity().application as GithubTestApplication).githubRepository
            )
        )
    }
    private lateinit var adapter: UserListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserListBinding.bind(view)
        initRecyclerView()
        setOnUserListObserver()
        setOnRefreshObserver()
        setOnNetworkingObserver()
        initViews()
    }

    private fun initViews() = with(binding) {
        keywordEditText.setOnKeyListener { view, keyCode, keyEvent ->
            if (NetworkLiveData.isNetworkAvailable()) {
                when (keyEvent.action) {
                    KeyEvent.ACTION_DOWN -> {
                        if (keyCode == KeyEvent.KEYCODE_ENTER) {
                            checkKeywordEditText()
                            hideKeyboard()
                            return@setOnKeyListener true
                        }
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.pleas_check_your_network),
                    Toast.LENGTH_SHORT
                ).show()
            }
            return@setOnKeyListener false
        }

        searchImageView.setOnClickListener {
            if (NetworkLiveData.isNetworkAvailable()) {
                checkKeywordEditText()
                hideKeyboard()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.pleas_check_your_network),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        userListSwipeView.setOnRefreshListener {
            userListViewModel.resetCurrentPage()
            resetRecyclerView()
            userListViewModel.getResetUserList()
        }
    }

    private fun initRecyclerView() = with(binding) {
        adapter = UserListAdapter { item ->
            if (NetworkLiveData.isNetworkAvailable()) {
                moveToUserInfo(item.userId)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.pleas_check_your_network),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        userListRecyclerView.adapter = adapter
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)

                setOnVisibleView(View.VISIBLE, View.GONE)
            }
        })
        userListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (userListViewModel.networkState.value == NETWORK_LOADING) {
                    return
                }

                if (userListViewModel.emptyState.value == ConstValues.RESULT_EMPTY) {
                    return
                }

                val lastVisibleItem =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                val lastCount = adapter.itemCount - 1

                if (lastVisibleItem == lastCount && (lastVisibleItem != -1 || lastCount != -1)) {
                    // 다음 거 가져오기
                    if (NetworkLiveData.isNetworkAvailable()) {
                        userListViewModel.getNextUserList()
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

    private fun setOnUserListObserver() {
        userListViewModel.userList.observe(viewLifecycleOwner, { modelUserList ->

            if (modelUserList.isNotEmpty()) {
                adapter.submitList(modelUserList)
            } else {
                setOnVisibleView(View.GONE, View.VISIBLE)
            }
        })
    }

    private fun setOnRefreshObserver() {
        userListViewModel.resetState.observe(viewLifecycleOwner, {
            if (it) {
                binding.userListSwipeView.isRefreshing = false
            }
        })
    }

    private fun setOnNetworkingObserver() {
        userListViewModel.networkState.observe(viewLifecycleOwner, {
            when (it) {
                NETWORK_LOADING -> {
                    setOnProgressbarVisibility(View.VISIBLE)
                }
                NETWORK_ERROR -> {
                    setOnProgressbarVisibility(View.GONE)
                    Toast.makeText(requireContext(), "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    setOnProgressbarVisibility(View.GONE)
                }
            }
        })
    }

    private fun checkKeywordEditText() = with(binding) {
        if (keywordEditText.text.isEmpty()) {
            Toast.makeText(
                requireContext(),
                getString(R.string.please_input_id),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            resetRecyclerView()
            userListViewModel.getUserList(keywordEditText.text.toString())
            keywordEditText.text.clear()
        }
    }

    private fun setOnVisibleView(recyclerviewVisibility: Int, noViewVisibility: Int) =
        with(binding) {
            userListRecyclerView.visibility = recyclerviewVisibility
            noUserListView.visibility = noViewVisibility
        }

    private fun setOnProgressbarVisibility(visibility: Int) = with(binding) {
        userListProgressbar.visibility = visibility
    }

    private fun resetRecyclerView() = with(binding) {
        userListViewModel.clearData()
        adapter.submitList(null)
    }

    private fun moveToUserInfo(userName: String) {
        findNavController().navigate(
            UserListFragmentDirections.actionUserListFragmentToUserInfoFragment(
                userName
            )
        )
    }

    private fun hideKeyboard() = with(binding) {
        val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(keywordEditText.windowToken, 0)
    }
}