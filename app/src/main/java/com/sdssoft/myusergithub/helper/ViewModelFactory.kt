package com.sdssoft.myusergithub.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sdssoft.myusergithub.ui.insert.FavoriteAddDeleteViewModel
import com.sdssoft.myusergithub.ui.insert.FavoriteViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTACE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTACE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTACE = ViewModelFactory(application)
                }
            }
            return INSTACE as ViewModelFactory
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(mApplication) as T
        }else if(modelClass.isAssignableFrom(FavoriteAddDeleteViewModel::class.java)){
            return FavoriteAddDeleteViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}