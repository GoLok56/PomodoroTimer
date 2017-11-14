package io.github.golok56.pomodorotimer

import android.os.CountDownTimer
import io.github.golok56.pomodorotimer.presenter.CountDownPresenter
import java.util.concurrent.TimeUnit

/**
 * @author Satria Adi Putra
 */
class CustomTimer(
        private var presenter: CountDownPresenter,
        minutes: Long
) : CountDownTimer(TimeUnit.MINUTES.toMillis(minutes), TimeUnit.SECONDS.toMillis(1)) {

    override fun onFinish() {
        presenter.finishTicking()
    }

    override fun onTick(p0: Long) {
        presenter.onTick(p0)
    }
}