package com.victor.test.webreaderrx

import android.app.Application
import com.victor.test.webreaderrx.di.AppComponent
import com.victor.test.webreaderrx.di.AppModule
import com.victor.test.webreaderrx.di.DaggerAppComponent

/**
 * Created by victorpalmacarrasco on 3/3/18.
 * ${APP_NAME}
 */
class MainApplication:Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }


    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}