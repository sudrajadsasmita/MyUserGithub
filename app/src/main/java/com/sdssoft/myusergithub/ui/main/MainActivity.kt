package com.sdssoft.myusergithub.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdssoft.myusergithub.DataStore.SettingPreference
import com.sdssoft.myusergithub.R
import com.sdssoft.myusergithub.databinding.ActivityMainBinding
import com.sdssoft.myusergithub.helper.SettingViewModelFactory
import com.sdssoft.myusergithub.model.ItemsItem
import com.sdssoft.myusergithub.ui.insert.MainViewModel
import com.sdssoft.myusergithub.ui.insert.SettingViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Setting")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(resources.getColor(android.R.color.white))
        val preferences = SettingPreference.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(preferences)
        ).get(SettingViewModel::class.java)
        settingViewModel.getThemeSetting().observe(this, { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })
        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        showList(false)
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        mainViewModel.listUser.observe(this, { listUser ->
            if (listUser != null) {
                showList(true)
                setRecyclerViewData(listUser)
            } else {
                showList(false)
            }
        })
        mainViewModel.isLoading.observe(this, { isLoading ->
            showProgressBar(isLoading)
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mainViewModel.findUsername(newText!!)
                return true
            }
        })
    }

    private fun setRecyclerViewData(itemsItem: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.setData(itemsItem)
        binding.rvUser.adapter = adapter
        adapter.onItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(itemsItem: ItemsItem) {
                val intentDetailActivity = Intent(this@MainActivity, DetailActivity::class.java)
                intentDetailActivity.putExtra(DetailActivity.EXTRA_USERNAME, itemsItem.login)
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

    private fun showList(state: Boolean) {
        if (!state) {
            binding.rvUser.visibility = View.GONE
        } else {
            binding.rvUser.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about_menu -> {
                val mIntent = Intent(this, SettingActivity::class.java)
                startActivity(mIntent)
            }
            R.id.favorite_menu -> {
                val mIntent = Intent(this, FavoriteActivity::class.java)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}