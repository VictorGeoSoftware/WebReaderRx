package com.victor.test.webreaderrx

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.victor.test.webreaderrx.presenter.WebReaderPresenter
import com.victor.test.webreaderrx.presenter.WebReaderView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), WebReaderView {

    @Inject lateinit var webReaderPresenter: WebReaderPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MainApplication).component.inject(this)


        btnPlay.setOnClickListener {
            Log.i("Webreader", "HAGO CLICK")
            webReaderPresenter.performTrueCallerProcess()
        }
    }

    override fun onStart() {
        super.onStart()
        webReaderPresenter.setView(this)
    }
    override fun onStop() {
        super.onStop()
        webReaderPresenter.onDestroy()
    }


    // ----------------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------------------------------------ PRESENTER VIEW ------------------------------------------------------------
    override fun showProgressBar() {
        btnPlay.isEnabled = false
        progressBar.visibility = View.VISIBLE
        txtTitle.visibility = View.GONE
    }

    override fun hideProgressBar() {
        btnPlay.isEnabled = true
        progressBar.visibility = View.GONE
    }

    override fun onFirstTenthCharacterRead(tenthChar: String) {
        txtFirstMethod.text = String.format(getString(R.string.tenth_character), tenthChar)
    }

    override fun onNewTenthCharacterRead(tenthChar: String) {
        val newText = "${txtSecondMethod.text} $tenthChar"
        txtSecondMethod.text = newText
    }

    override fun onWordCounterUpdated(tenthChar: String) {
        txtThirdMethod.text = String.format(getString(R.string.total_words_counted), tenthChar)
    }
}
