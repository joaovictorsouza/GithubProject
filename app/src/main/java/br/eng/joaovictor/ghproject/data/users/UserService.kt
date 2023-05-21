package br.eng.joaovictor.ghproject.data.users

import br.eng.joaovictor.ghproject.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import javax.inject.Singleton


@Singleton
interface UserService {
   @GET("users")
   suspend fun getUser() : List<User>
}