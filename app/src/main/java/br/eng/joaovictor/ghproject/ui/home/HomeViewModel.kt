package br.eng.joaovictor.ghproject.ui.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.eng.joaovictor.ghproject.data.users.UsersRepository
import br.eng.joaovictor.ghproject.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Logger
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val usersRepository: UsersRepository) : ViewModel() {


    val users : MutableStateFlow<List<User>> = MutableStateFlow<List<User>>(listOf())
    var isLoading = mutableStateOf(false)
    var errorMessage : MutableState<String?> = mutableStateOf(null)


    suspend fun getUserData(){
        viewModelScope.launch {
            usersRepository.getUsers()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    errorMessage.value = e.message
                }
                .collect {
                    users.emit(it)
                }
        }
 }

    fun fetchMoreItems(){
        viewModelScope.launch {
            if(users.value.isNotEmpty()) {
                val since = users.value.last().id
                usersRepository.getUsers(since)
                    .flowOn(Dispatchers.IO)
                    .catch { e ->
                        errorMessage.value = e.message
                    }
                    .collect {
                        users.emit(
                            users.value.plus(it)
                        )
                    }
            }
        }
    }

}