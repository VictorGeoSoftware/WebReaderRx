package com.victor.test.webreaderrx

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.victor.test.webreaderrx.presenter.WebReaderPresenter
import com.victor.test.webreaderrx.presenter.WebReaderView
import javax.inject.Inject

class MainActivity : AppCompatActivity(), WebReaderView {

    @Inject lateinit var webReaderPresenter: WebReaderPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MainApplication).component.inject(this)

        webReaderPresenter.setView(this)
        webReaderPresenter.trueCaller10thCharacterRequest()
    }



    // ----------------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------------------------------------ PRESENTER VIEW ------------------------------------------------------------
    override fun onFirstTenthCharacterRead(tenthChar: String) {

    }

    override fun onNewTenthCharacterRead(tenthChar: String) {

    }

    override fun onWordCounterUpdated(tenthChar: String) {

    }
}
