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
import com.sdssoft.myusergithub.model.FollowerResponseItem
import com.sdssoft.myusergithub.ui.insert.FollowerViewModel
import com.sdssoft.myusergithub.databinding.FragmentFollowerBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [FollowerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentFollowerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val followerViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowerViewModel::class.java)
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(context)
        binding.rvFollower.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvFollower.addItemDecoration(itemDecoration)


        val person = activity?.intent?.getStringExtra(DetailActivity.EXTRA_USERNAME)!!

        followerViewModel.showFollower(person)
        followerViewModel.listUser.observe(viewLifecycleOwner, { listUser ->
            setRecyclerViewData(listUser)
        })

        followerViewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            showProgressBar(isLoading)
        })
        return binding.root
    }


    private fun setRecyclerViewData(followerResponseItem: List<FollowerResponseItem>) {
        val adapter = FollowerAdapter()
        adapter.setData(followerResponseItem)
        binding.rvFollower.adapter = adapter
        adapter.onItemClickCallback(object : FollowerAdapter.OnItemClickCallback {
            override fun onItemClicked(followerResponseItem: FollowerResponseItem) {
                val intentDetailActivity = Intent(context, DetailActivity::class.java)
                intentDetailActivity.putExtra(
                    DetailActivity.EXTRA_USERNAME,
                    followerResponseItem.login
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
         * @return A new instance of fragment FollowerFragment.
         */
        // TODO: Rename and change types and number of parameters


    }
}