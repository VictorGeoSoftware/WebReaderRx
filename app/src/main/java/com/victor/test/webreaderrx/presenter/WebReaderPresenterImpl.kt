package com.victor.test.webreaderrx.presenter

import android.content.Context
import android.util.Log
import com.victor.test.webreaderrx.MainApplication
import com.victor.test.webreaderrx.network.WebReaderRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by victorpalmacarrasco on 3/3/18.
 * ${APP_NAME}
 */
class WebReaderPresenterImpl(context: Context): WebReaderPresenter {
    private lateinit var webReaderView: WebReaderView
    @Inject lateinit var webReaderRequest: WebReaderRequest


    init {
        (context as MainApplication).component.inject(this)
    }

    override fun setView(webReaderView: WebReaderView) {
        this.webReaderView = webReaderView
    }

    override fun trueCaller10thCharacterRequest() {

        webReaderRequest.getWebPageContent()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    Log.i("WebReader","WebReaderPresenterImpl - doOnNext :: ${it.charStream()}")
                    Log.i("WebReader","WebReaderPresenterImpl - doOnNext :: ${it.string()}")
                }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Log.i("WebReader","WebReaderPresenterImpl - response :: ${it.charStream()}")
                            Log.i("WebReader","WebReaderPresenterImpl - response :: ${it.string()}")
                        },
                        {
                            Log.i("WebReader","WebReaderPresenterImpl - error :: ${it.localizedMessage}")
                            it.printStackTrace()
                        },
                        {
                            Log.i("WebReader", "WebReaderPresenterImpl - finish!")
                        }
                )
    }

    override fun trueCallerEvery10thCharacterRequest() {

    }

    override fun trueCallerWordCounterRequest() {

    }
}