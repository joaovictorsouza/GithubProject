package br.eng.joaovictor.ghproject.ui.home

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.eng.joaovictor.ghproject.data.users.UsersRepository
import br.eng.joaovictor.ghproject.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val usersRepository: UsersRepository) : ViewModel() {


    val users : MutableStateFlow<List<User>> = MutableStateFlow<List<User>>(listOf())
    var isLoading = mutableStateOf(false)


    suspend fun getUserData(){
        viewModelScope.launch {
            usersRepository.getUsers()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    // handle exception
                }
                .collect {
                    users.emit(it)
                }
        }
 }

}