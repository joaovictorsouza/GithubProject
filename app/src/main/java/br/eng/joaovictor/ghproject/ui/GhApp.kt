package br.eng.joaovictor.ghproject.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.eng.joaovictor.ghproject.ui.theme.GithubProjectTheme

@Composable
fun GhApp(
    widthSizeClass: WindowWidthSizeClass,
){
    val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded

    GithubProjectTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Row(){
                if(isExpandedScreen){
                    NavigationRail {

                    }
                }
                BoxWithConstraints {
                    GhNavGraph()
                }
            }
        }
    }
}