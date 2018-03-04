package com.victor.test.webreaderrx.presenter

/**
 * Created by victorpalmacarrasco on 3/3/18.
 * ${APP_NAME}
 */
interface WebReaderPresenter {
    fun setView(webReaderView: WebReaderView)
    fun trueCaller10thCharacterRequest()
    fun trueCallerEvery10thCharacterRequest()
    fun trueCallerWordCounterRequest()
}