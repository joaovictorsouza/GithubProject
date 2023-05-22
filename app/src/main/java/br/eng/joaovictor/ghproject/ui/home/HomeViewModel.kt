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
import java.io.IOException
import java.util.logging.Logger
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val usersRepository: UsersRepository) : ViewModel() {


    val users : MutableStateFlow<List<User>> = MutableStateFlow<List<User>>(listOf())
    var isLoading = mutableStateOf(false)
    var errorMessage : MutableState<String> = mutableStateOf("")


    suspend fun getUserData(since : Int? = null){
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = usersRepository.getUsers(since)
                response.collect {
                    users.emit(users.value.plus(it))
                }
            } catch (e: IOException) {
                errorMessage.value = "Erro de conexão com o servidor"
                // Log the specific error if needed
            } catch (e: Exception) {
                errorMessage.value = "Erro desconhecido ao listar usuários"
                // Log the specific error if needed
            }finally {
                isLoading.value = false
            }
        }
 }

    suspend fun fetchMoreItems(){
        if(users.value.isNotEmpty()) {
            val since = users.value.last().id
            getUserData(since)
        }
    }

}