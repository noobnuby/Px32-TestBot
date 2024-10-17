package com.noobnuby.plugin.util

import java.text.NumberFormat
import java.util.*

fun Int.formatWon(): String = NumberFormat.getNumberInstance(Locale.KOREA).format(this)