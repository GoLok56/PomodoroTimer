package io.github.golok56.pomodorotimer.presenter.contract

/**
 * @author Satria Adi Putra
 */
interface MainView {
    fun setButtonText(newText: String)
    fun startWorking()
    fun stopTimer()
    fun showToast(text: String)
    fun setSessionText(num: Int)
    fun setMinuteText(num: Long)
    fun setSecondText(num: Long)
    fun changeState(state: Int)
    fun startResting()
}