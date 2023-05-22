package br.eng.joaovictor.ghproject.ui.user

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.eng.joaovictor.ghproject.data.users.UsersRepository
import br.eng.joaovictor.ghproject.model.Repo
import br.eng.joaovictor.ghproject.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val usersRepository: UsersRepository) : ViewModel() {
    var searchText = mutableStateOf(TextFieldValue(""))
    var isSearching = mutableStateOf(Boolean)
    var errorMessage = mutableStateOf(String())
    var userDetails: MutableState<User?> = mutableStateOf(null)
    var userRepos: MutableStateFlow<List<Repo>> = MutableStateFlow(listOf())

    suspend fun getUserDetails(login : String) {
        viewModelScope.launch {
            usersRepository.getUser(login)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    errorMessage.value = e.message.toString()
                }
                .collect {
                    userDetails.value = it
                }
        }
    }

    suspend fun getUserRepos(login : String) {
        viewModelScope.launch {
            usersRepository.getUserRepos(login)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    errorMessage.value = e.message.toString()
                }
                .collect {
                    userRepos.emit(
                        userRepos.value.plus(it)
                    )
                }
        }
    }
}