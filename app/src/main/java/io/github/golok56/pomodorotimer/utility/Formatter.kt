package io.github.golok56.pomodorotimer.utility

import java.text.DecimalFormat

/**
 * @author Satria Adi Putra
 */
class Formatter {

    companion object {
        fun format(numberToFormat: Long) = DecimalFormat("00").format(numberToFormat)
    }

}