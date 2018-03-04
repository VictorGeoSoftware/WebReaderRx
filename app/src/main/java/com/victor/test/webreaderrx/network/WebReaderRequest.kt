package com.victor.test.webreaderrx.network

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET

/**
 * Created by victorpalmacarrasco on 3/3/18.
 * ${APP_NAME}
 */
interface WebReaderRequest {

    @GET("/2018/01/22/life-as-an-android-engineer/")
    fun getWebPageContent(): Observable<ResponseBody>
}