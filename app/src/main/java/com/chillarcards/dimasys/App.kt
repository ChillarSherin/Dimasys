package com.chillarcards.dimasys

import android.app.Application
import com.chillarcards.dimasys.di.module.appModule
import com.chillarcards.dimasys.di.module.repoModule
import com.chillarcards.dimasys.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * @Author: Sherin Jaison
 * @Date: 29-05-2024$
 * Chillar
 * for kotlin koin di
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(appModule, repoModule, viewModelModule))
        }

        // Generate Hash Key >>>>> GOOGLE SMS
        //  val appSignatureHashHelper = AppSignatureHashHelper(this)
        //  Log.e(TAG, "HashKey: " + appSignatureHashHelper.appSignatures[0])
        //  Last uploaded VrgIsm6rcVw
    }


}