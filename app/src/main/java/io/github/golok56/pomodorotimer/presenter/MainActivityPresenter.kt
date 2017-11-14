package io.github.golok56.pomodorotimer.presenter

import io.github.golok56.pomodorotimer.presenter.contract.MainView
import io.github.golok56.pomodorotimer.view.activity.MainActivity

/**
 * @author Satria Adi Putra
 */
class MainActivityPresenter(private var view: MainView): CountDownPresenter {
    private val MAX_SESSION = 4
    private var isStarted = false
    private var isWorking = false
    private var currentSession = 1

    fun initView(workingTime: Long){
        view.setMinuteText(workingTime)
        view.setSecondText(0)
        view.setSessionText(currentSession)
    }

    override fun onTick(millisecondLeft: Long){
        var secondLeft = millisecondLeft / 1000
        val minuteLeft = secondLeft / 60

        secondLeft %= 60

        view.setMinuteText(minuteLeft)
        view.setSecondText(secondLeft)
    }

    fun onStartClicked(){
        if(!isStarted){
            start()
        } else {
            stop()
        }

        isStarted = !isStarted
        isWorking = !isWorking
    }

    override fun finishTicking() {
        view.setSecondText(0)

        if(currentSession < MAX_SESSION) {
            if (isWorking) {
                view.showToast("Take your time. Enjoy your 10 minutes break.")
                view.startResting()
                view.changeState(MainActivity.REST_STATE)
            } else {
                view.setSessionText(++currentSession)
                view.showToast("Let's start your work")
                view.startWorking()
                view.changeState(MainActivity.WORK_STATE)
            }
            isWorking = !isWorking
        } else {
            stop()
            view.showToast("Good work for today!")
        }
    }

    fun start(){
        view.startWorking()
        view.changeState(MainActivity.WORK_STATE)
        view.setButtonText("Stop")
    }

    fun stop(){
        view.stopTimer()
        view.changeState(MainActivity.STOP_STATE)
        view.setButtonText("Start")
        currentSession = 1
    }

}