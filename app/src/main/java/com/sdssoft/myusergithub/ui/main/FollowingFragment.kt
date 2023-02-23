package com.sdssoft.myusergithub.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sdssoft.myusergithub.databinding.FragmentFollowingBinding
import com.sdssoft.myusergithub.model.FollowingResponseItem
import com.sdssoft.myusergithub.ui.insert.FollowingViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [FollowingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(context)
        binding.rvFollowing.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvFollowing.addItemDecoration(itemDecoration)

        val person = activity?.intent?.getStringExtra(DetailActivity.EXTRA_USERNAME)!!

        followingViewModel.showFollower(person)
        followingViewModel.listUser.observe(viewLifecycleOwner, { listUser ->
            setRecyclerViewData(listUser)
        })

        followingViewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            showProgressBar(isLoading)
        })
        return binding.root
    }

    private fun setRecyclerViewData(followerResponseItem: List<FollowingResponseItem>) {
        val adapter = FollowingAdapter()
        adapter.setData(followerResponseItem)
        binding.rvFollowing.adapter = adapter
        adapter.onItemClickCallback(object : FollowingAdapter.OnItemClickCallback {
            override fun onItemClicked(followingResponseItem: FollowingResponseItem) {
                val intentDetailActivity = Intent(context, DetailActivity::class.java)
                intentDetailActivity.putExtra(
                    DetailActivity.EXTRA_USERNAME,
                    followingResponseItem.login
                )
                startActivity(intentDetailActivity)
            }

        })
    }

    private fun showProgressBar(state: Boolean) {
        if (!state) {
            binding.progressBar.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FollowingFragment.
         */
        // TODO: Rename and change types and number of parameters

    }
}