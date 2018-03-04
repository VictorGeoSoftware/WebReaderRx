package com.victor.test.webreaderrx.presenter

import android.content.Context
import android.util.Log
import com.victor.test.webreaderrx.MainApplication
import com.victor.test.webreaderrx.data.Constants
import com.victor.test.webreaderrx.data.ObsError
import com.victor.test.webreaderrx.network.WebReaderRequest
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by victorpalmacarrasco on 3/3/18.
 * ${APP_NAME}
 */
class WebReaderPresenterImpl(context: Context): WebReaderPresenter {
    private var webReaderView: WebReaderView? = null
    private lateinit var disposable: Disposable
    @Inject lateinit var webReaderRequest: WebReaderRequest


    init {
        (context as MainApplication).component.inject(this)
    }

    override fun setView(webReaderView: WebReaderView) {
        this.webReaderView = webReaderView
    }

    override fun performTrueCallerProcess() {

        disposable = webReaderRequest.getWebPageContent()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    webReaderView?.showProgressBar()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {

                    val contentToProcess: String = it.string()
                    val firstProcess = getTenthCharacterObservable(contentToProcess)
                    val secondProcess = getEvery10thCharacterObservable(contentToProcess)
                    val thirdProcess = getWordCounterRequest(contentToProcess)

                    Observable.merge<Pair<Int, String>>(firstProcess, secondProcess, thirdProcess)
                }
                .subscribe(
                        {
                            Log.i("WebReader","WebReaderPresenterImpl - onNext :: ${it.first}  ${it.second}")

                            when (it.first) {
                                Constants.TENTH_CHARACTER_OBS -> webReaderView?.onFirstTenthCharacterRead(it.second)

                                Constants.EVERY_10TH_CHARACTER_OBS -> webReaderView?.onNewTenthCharacterRead(it.second)

                                Constants.WORD_COUNTER_OBS -> webReaderView?.onWordCounterUpdated(it.second)
                            }
                        },
                        {
                            Log.i("WebReader","WebReaderPresenterImpl - error :: ${it.localizedMessage}")
                            it.printStackTrace()
                            webReaderView?.hideProgressBar()
                        },
                        {
                            Log.i("WebReader", "WebReaderPresenterImpl - finish!")
                            webReaderView?.hideProgressBar()
                        }
                )
    }

    override fun onDestroy() {
        webReaderView = null
        if (!disposable.isDisposed) { disposable.dispose() }
    }



    // ---------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------------------------------------ METHODS ------------------------------------------------------------
    private fun getTenthCharacterObservable(content: String): Observable<Pair<Int, String>> {
        Log.i("WebReader","WebReaderPresenterImpl - getTenthCharacterObservable - content :: $content")
        return Observable.create {

            val contentArray = content.toCharArray()
            Log.i("WebReader","WebReaderPresenterImpl - getTenthCharacterObservable - contentArray :: ${contentArray.size}")

            if (content.length > 10) {
                it.onNext(Pair(Constants.TENTH_CHARACTER_OBS, contentArray[9].toString()))
            } else {
                it.onError(Throwable(ObsError.LENGTH_ERROR))
            }

            it.onComplete()
        }
    }

    private fun getEvery10thCharacterObservable(content: String): Observable<Pair<Int, String>> {
        Log.i("WebReader","WebReaderPresenterImpl - getEvery10thCharacterObservable - content :: $content")

        return Observable.create {
            emitter ->
            val contentArray = content.toCharArray()
            Log.i("WebReader","WebReaderPresenterImpl - getWordCounterRequest - contentArray :: ${contentArray.size}")
            val processedContent = StringBuffer()

            if (contentArray.isNotEmpty()) {
                (1..contentArray.size step 10).forEach {
                    processedContent.append(contentArray[it].toString())
                }
            }

            emitter.onNext(Pair(Constants.EVERY_10TH_CHARACTER_OBS, processedContent.toString()))
            emitter.onComplete()
        }
    }

    private fun getWordCounterRequest(content: String): Observable<Pair<Int, String>> {
        Log.i("WebReader","WebReaderPresenterImpl - getWordCounterRequest - content :: $content")

        return Observable.create {
            val contentArray = content.toCharArray()
            Log.i("WebReader","WebReaderPresenterImpl - getWordCounterRequest - contentArray :: ${contentArray.size}")
            var wordCounter = 0

            for (char in contentArray) {
                if (!char.toString().contentEquals(" ") && !char.toString().contentEquals("\n")) {
                    wordCounter++
                }
            }

            Log.i("WebReader","WebReaderPresenterImpl - getWordCounterRequest - wordCounter :: $wordCounter")
            it.onNext(Pair(Constants.WORD_COUNTER_OBS, wordCounter.toString()))
            it.onComplete()
        }
    }

}