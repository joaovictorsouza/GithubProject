@file:OptIn(ExperimentalMaterial3Api::class)

package br.eng.joaovictor.ghproject.ui.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.eng.joaovictor.ghproject.R
import br.eng.joaovictor.ghproject.model.Repo
import br.eng.joaovictor.ghproject.model.User
import br.eng.joaovictor.ghproject.ui.home.HomeViewModel
import br.eng.joaovictor.ghproject.util.getColumnsNumber
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun UserScreen(
    widthSizeClass: WindowWidthSizeClass,
    userLogin: String,
    modifier: Modifier = Modifier
) {
    val viewModel: UserViewModel = hiltViewModel()
    val gridState = rememberLazyGridState()
    val repos = viewModel.userRepos.collectAsState()

    if (userLogin.isNotEmpty()) {
        LaunchedEffect(key1 = userLogin) {
            viewModel.getUserDetails(userLogin)
            viewModel.getUserRepos(userLogin)
        }
    }

    if (widthSizeClass == WindowWidthSizeClass.Expanded) {
        TwoPaneScreen(modifier = modifier,
            pane1 = {
                if (viewModel.userDetails.value != null) {
                    UserDetailsCard(user = viewModel.userDetails.value!!,
                        isLoading = viewModel.isLoadingUser.value)
                }
            },
            pane2 = {
                RepositoryGrid(
                    title = {
                        Text(
                            text = "${stringResource(id = R.string.repos_of)} ${viewModel.userDetails.value?.name ?: ""}",
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily.SansSerif
                        )
                    },
                    state = gridState,
                    repos = repos,
                    isLoading = viewModel.isLoadingRepo.value
                )
            })
    } else {
        RepositoryList(
            modifier = modifier,
            user = viewModel.userDetails.value,
            repos = repos,
            isLoading = viewModel.isLoadingUser.value || viewModel.isLoadingRepo.value
        )
    }

}

@Composable
fun RepositoryGrid(
    title: @Composable () -> Unit,
    state: LazyGridState,
    repos: State<List<Repo>>,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 12.dp),
    ) {
        title()
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            state = state,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(repos.value.size) { index ->
                val item = repos.value[index]
                RepoCard(item = item)
            }

            if(isLoading) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun RepositoryList(
    isLoading: Boolean,
    user: User?,
    repos: State<List<Repo>>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (user != null) {
            item {
                UserDetailsCard(user, isLoading = false)
            }

            if(isLoading) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator()
                    }
                }
            }

            if(!isLoading) {
                item {
                    Text(
                        text = "${stringResource(id = R.string.repos_of)} ${user.name}",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.SansSerif
                    )
                }
            }

            items(repos.value.size) { index ->
                val item = repos.value[index]
                RepoCard(item = item)
            }
        }
    }
}


@Composable
fun UserDetailsCard(user: User, isLoading: Boolean) {
    Card {
        if(isLoading){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator()
                }
        }

        BoxWithConstraints {
            val width = maxWidth.value.toInt()
            Column(modifier = Modifier.padding(all = 12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(80.dp, 80.dp),
                        model = user.avatarUrl, contentDescription = user.name
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = user.name,
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily.SansSerif
                        )

                        Text(
                            text = "@${user.login}",
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                }


                if (!user.bio.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Bio:",
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = user.bio)
                }
            }
        }
    }
}

