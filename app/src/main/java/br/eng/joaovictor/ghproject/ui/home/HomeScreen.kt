@file:OptIn(ExperimentalMaterial3Api::class)

package br.eng.joaovictor.ghproject.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.eng.joaovictor.ghproject.R
import br.eng.joaovictor.ghproject.util.OnBottomReached
import br.eng.joaovictor.ghproject.util.getColumnsNumber
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    widthSizeClass: WindowWidthSizeClass,
    onSelectedItem: (user: String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    val user = viewModel.users.collectAsState()
    val gridState = rememberLazyGridState()
    val snackBarHostState = SnackbarHostState()

    if (!viewModel.errorMessage.value.isNullOrEmpty()) {
        scope.launch {
            snackBarHostState.showSnackbar(viewModel.errorMessage.value!!)
        }
    }

    LaunchedEffect(scope.coroutineContext) {
        viewModel.getUserData()
    }

    gridState.OnBottomReached(buffer = 6) {
        scope.launch {
            viewModel.fetchMoreItems()
        }
    }

    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(widthSizeClass.getColumnsNumber()),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
    ) {
        this.items(user.value.size) { index ->
            val userItem = user.value[index]
            UserCard(
                modifier = Modifier.testTag("user-card"),
                user = userItem, onClick = {
                    onSelectedItem(userItem.login)
                })
        }
    }
}

