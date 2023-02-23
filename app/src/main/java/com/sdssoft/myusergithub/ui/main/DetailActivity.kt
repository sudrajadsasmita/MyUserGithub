package com.sdssoft.myusergithub.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.sdssoft.myusergithub.R
import com.sdssoft.myusergithub.database.User
import com.sdssoft.myusergithub.databinding.ActivityDetailBinding
import com.sdssoft.myusergithub.helper.ViewModelFactory
import com.sdssoft.myusergithub.ui.insert.DetailViewModel
import com.sdssoft.myusergithub.ui.insert.FavoriteAddDeleteViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var favoriteAddDeleteViewModel: FavoriteAddDeleteViewModel
    private var stateFavorite = false
    private var idUser: Int = 0

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.scrollDetail.isSmoothScrollingEnabled = true
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(resources.getColor(android.R.color.white))
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val person = intent.getStringExtra(EXTRA_USERNAME)!!
        val detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        favoriteAddDeleteViewModel = obtainViewModel(this)
        favoriteAddDeleteViewModel.getUserByLogin(person).observe(this, { user ->
            if (user != null) {
                stateFavorite = true
                idUser = user.id
            }
            stateFavorite(stateFavorite)
        })

        detailViewModel.getDetailUser(person)
        detailViewModel.detailUser.observe(this, { detailUser ->

            binding.tvName.text =
                if (detailUser.name.isNullOrEmpty() || detailUser.name == "") "Belum Tersedia" else detailUser.name
            binding.tvCompany.text =
                if (detailUser.company.isNullOrEmpty() || detailUser.company == "") "Belum Tersedia" else detailUser.company
            binding.tvBlog.text =
                if (detailUser.blog.isNullOrEmpty() || detailUser.blog == "") "Belum Tersedia" else detailUser.blog
            binding.tvLocation.text =
                if (detailUser.location.isNullOrEmpty() || detailUser.location == "") "Belum Tersedia" else detailUser.location

            Glide.with(this)
                .load(detailUser.avatarUrl)
                .into(binding.imgProfile)

            binding.btnFavorite.setOnClickListener {
                val user = User()
                if (stateFavorite) {
                    user.id = idUser
                    user.login = person
                    user.avatar = detailUser.avatarUrl
                    val alertDialogBuilder = AlertDialog.Builder(this)
                    alertDialogBuilder.setTitle("Hapus")
                        .setMessage("Apakah anda yakin ingin menghapus $person dari favorite")
                        .setCancelable(true)
                        .setPositiveButton("Ya") { _, _ ->
                            favoriteAddDeleteViewModel.delete(user)
                            Snackbar.make(
                                binding.root,
                                "$person berhasi di hapus",
                                Snackbar.LENGTH_SHORT
                            ).show()
                            stateFavorite = false
                        }
                        .setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                    stateFavorite(stateFavorite)
                } else {
                    //untuk add
                    user.login = person
                    user.avatar = detailUser.avatarUrl
                    favoriteAddDeleteViewModel.insert(user)
                    Snackbar.make(
                        binding.root,
                        "$person berhasil di tambahkan di daftar favorite",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    stateFavorite = true
                    stateFavorite(stateFavorite)
                }


            }
        })

        //actionbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = person
    }

    private fun stateFavorite(state: Boolean) {
        if (state) {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_red)
        } else {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteAddDeleteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteAddDeleteViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val favMenu = menu?.findItem(R.id.favorite_menu)!!
        favMenu.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about_menu -> {
                val mIntent = Intent(this, SettingActivity::class.java)
                startActivity(mIntent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}