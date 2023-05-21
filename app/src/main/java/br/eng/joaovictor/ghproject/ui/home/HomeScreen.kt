package br.eng.joaovictor.ghproject.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import br.eng.joaovictor.ghproject.util.getColumnsNumber
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    widthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    val user = viewModel.users.collectAsState()

    LaunchedEffect(scope.coroutineContext) {
        viewModel.getUserData()
    }

    LazyVerticalGrid(columns = GridCells.Fixed(widthSizeClass.getColumnsNumber())) {
        this.items(user.value.size) { index ->
            UserCard(user = user.value[index], onClick = {})
        }
    }
}