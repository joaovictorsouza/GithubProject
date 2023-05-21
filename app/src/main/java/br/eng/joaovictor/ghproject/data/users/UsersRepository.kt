package br.eng.joaovictor.ghproject.data.users

import br.eng.joaovictor.ghproject.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(private val service : UserService) {
     suspend fun getUsers() : Flow<List<User>> = flow {
         emit(service.getUser())
     }
}