package br.eng.joaovictor.ghproject.util

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

fun WindowWidthSizeClass.getColumnsNumber() : Int {
    return when (this) {
        WindowWidthSizeClass.Compact -> 1
        WindowWidthSizeClass.Medium -> 2
        WindowWidthSizeClass.Expanded -> 3
        else -> 1
    }
}