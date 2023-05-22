package br.eng.joaovictor.ghproject.data.users

import br.eng.joaovictor.ghproject.model.Repo
import br.eng.joaovictor.ghproject.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(private val service : UserService) {
     suspend fun getUsers(since : Int? = null) : Flow<List<User>> = flow {
         emit(service.getUser(since))
     }

    suspend fun getUser(login : String) : Flow<User> = flow {
        emit(service.getUser(login))
    }

    suspend fun getUserRepos(login : String) : Flow<List<Repo>> = flow {
        emit(service.getUserRepos(login))
    }
}