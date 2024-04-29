package com.branovitski.a3205testapp.ui

import android.window.SplashScreen
import com.branovitski.a3205testapp.ui.downloaded.DownloadedRepositoriesFragment
import com.branovitski.a3205testapp.ui.search.SearchFragment
import com.branovitski.a3205testapp.ui.splash.SplashFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun splash() = FragmentScreen { SplashFragment() }

    fun search() = FragmentScreen { SearchFragment() }
    fun downloads() = FragmentScreen { DownloadedRepositoriesFragment() }


}