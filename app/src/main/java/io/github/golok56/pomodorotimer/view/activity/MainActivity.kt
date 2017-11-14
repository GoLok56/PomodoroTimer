package io.github.golok56.pomodorotimer.view.activity

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.github.golok56.pomodorotimer.CustomTimer
import io.github.golok56.pomodorotimer.R
import io.github.golok56.pomodorotimer.presenter.MainActivityPresenter
import io.github.golok56.pomodorotimer.presenter.contract.MainView
import io.github.golok56.pomodorotimer.utility.Formatter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    companion object {
        val WORK_STATE = 500
        val REST_STATE = 501
        val STOP_STATE = 502
    }

    private var presenter = MainActivityPresenter(this)

    private val TAG = "WAKE_LOCK"

    private lateinit var wakeLock : PowerManager.WakeLock

    // Total work duration in minute
    private val WORK_DURATION = 2L

    // Total rest duration in minute
    private val REST_DURATION = 1L

    // Timer for work
    private val workTimer = CustomTimer(presenter, WORK_DURATION)

    // Timer for rest
    private val restTimer = CustomTimer(presenter, REST_DURATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.initView(WORK_DURATION)
        btnStartTimer.setOnClickListener { _ ->
            presenter.onStartClicked()
        }

        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = pm.newWakeLock(
                PowerManager.SCREEN_DIM_WAKE_LOCK or PowerManager.ON_AFTER_RELEASE,
                TAG
        )
    }

    override fun startResting() {
        workTimer.cancel()
        restTimer.start()
    }

    override fun startWorking() {
        restTimer.cancel()
        workTimer.start()
        if(!wakeLock.isHeld) {
            try {
                wakeLock.acquire()
            } catch(ex: Exception){
                ex.printStackTrace()
            }
        }
    }

    override fun stopTimer() {
        workTimer.cancel()
        restTimer.cancel()
        try {
            wakeLock.release()
        } catch(ex : Exception){
            ex.printStackTrace()
        }
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun setButtonText(newText: String) {
        btnStartTimer.text = newText
    }

    override fun setMinuteText(num: Long) {
        tvMinute.text = Formatter.format(num)
    }

    override fun setSecondText(num: Long) {
        tvSecond.text = Formatter.format(num)
    }

    override fun setSessionText(num: Int) {
        tvCurrentSession.text = resources.getString(R.string.current_session_label, num)
    }

    override fun changeState(state: Int) {
        when(state){
            WORK_STATE -> tvCurrentState.text = getString(R.string.currently_working_label)
            REST_STATE -> tvCurrentState.text = getString(R.string.currently_resting_label)
            STOP_STATE -> tvCurrentState.text = getString(R.string.stop_label)
        }
    }
}
