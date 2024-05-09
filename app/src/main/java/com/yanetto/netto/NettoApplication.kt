package com.yanetto.netto

import android.app.Application
import com.yanetto.netto.data.AppContainer
import com.yanetto.netto.data.AppDataContainer

class NettoApplication:Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}