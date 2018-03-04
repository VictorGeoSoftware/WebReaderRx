package com.victor.test.webreaderrx.presenter

/**
 * Created by victorpalmacarrasco on 3/3/18.
 * ${APP_NAME}
 */
interface WebReaderView {
    fun showProgressBar()
    fun onFirstTenthCharacterRead(tenthChar:String)
    fun onNewTenthCharacterRead(tenthChar:String)
    fun onWordCounterUpdated(tenthChar:String)
    fun hideProgressBar()
}