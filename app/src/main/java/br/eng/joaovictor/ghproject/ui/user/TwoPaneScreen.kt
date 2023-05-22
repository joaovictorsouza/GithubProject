package br.eng.joaovictor.ghproject.ui.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun TwoPaneScreen(
    pane1: @Composable () -> Unit,
    pane2: @Composable () -> Unit,
    modifier: Modifier = Modifier
){
    Row(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth(0.3f)) {
            pane1()
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            pane2()
        }
    }
}