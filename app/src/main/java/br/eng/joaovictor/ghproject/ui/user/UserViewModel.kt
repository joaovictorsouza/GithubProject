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
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val usersRepository: UsersRepository) : ViewModel() {
    var errorMessage = mutableStateOf(String())
    var userDetails: MutableState<User?> = mutableStateOf(null)
    var userRepos: MutableStateFlow<List<Repo>> = MutableStateFlow(listOf())
    var isLoadingUser = mutableStateOf(false)
    var isLoadingRepo = mutableStateOf(false)

    suspend fun getUserDetails(login : String) {
        viewModelScope.launch {
            try {
                isLoadingUser.value = true
                usersRepository.getUser(login)
                    .flowOn(Dispatchers.IO)
                    .collect {
                        userDetails.value = it
                    }
            }catch (e : IOException){
                errorMessage.value = "Erro de conexão com o servidor"
            }catch (e : Exception){
                errorMessage.value = "Erro desconhecido ao buscar dados do usuário"
            }finally {
                isLoadingUser.value = false
            }
        }
    }

    suspend fun getUserRepos(login : String) {
        viewModelScope.launch {
            try {
                isLoadingRepo.value = true
                usersRepository.getUserRepos(login)
                .flowOn(Dispatchers.IO)
                .collect {
                    userRepos.emit(
                        userRepos.value.plus(it)
                    )
                }
            }catch (e : IOException){
                errorMessage.value = "Erro de conexão com o servidor"
            }catch (e : Exception){
                errorMessage.value = "Erro desconhecido ao listar repositórios"
            }finally {
            isLoadingRepo.value = false
        }
        }
    }
}