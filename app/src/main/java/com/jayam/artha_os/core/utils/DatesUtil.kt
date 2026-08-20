package com.jayam.artha_os.core.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun todayKey(): String = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)