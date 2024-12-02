package com.crislozano.rickymortyfans.presentation.commons

import java.util.Locale

fun String.capitalizeWords(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}