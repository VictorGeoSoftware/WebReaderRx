package com.victor.test.webreaderrx.di

import android.app.Application
import com.victor.test.webreaderrx.MainActivity
import com.victor.test.webreaderrx.presenter.WebReaderPresenterImpl
import dagger.Component
import javax.inject.Singleton

/**
 * Created by victorpalmacarrasco on 3/3/18.
 * ${APP_NAME}
 */
@Singleton
@Component(modules = [(AppModule::class), (NetworkModule::class), (PresenterModule::class)])
interface AppComponent {
    fun inject(application: Application)
    fun inject(webReaderPresenter: WebReaderPresenterImpl)
    fun inject(mainActivity: MainActivity)
}