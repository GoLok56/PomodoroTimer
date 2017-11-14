package io.github.golok56.pomodorotimer.presenter

/**
 * @author Satria Adi Putra
 */
interface CountDownPresenter{
    fun onTick(millisecondLeft: Long)
    fun finishTicking()
}
