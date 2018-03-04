package com.victor.test.webreaderrx.di

import android.content.Context
import com.victor.test.webreaderrx.presenter.WebReaderPresenter
import com.victor.test.webreaderrx.presenter.WebReaderPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by victorpalmacarrasco on 3/3/18.
 * ${APP_NAME}
 */

@Module
class PresenterModule {

    @Provides
    @Singleton
    fun provideWebReaderPresenter(context: Context): WebReaderPresenter = WebReaderPresenterImpl(context)
}