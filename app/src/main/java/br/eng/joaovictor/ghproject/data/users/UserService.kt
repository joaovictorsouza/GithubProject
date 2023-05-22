package br.eng.joaovictor.ghproject.data.users

import br.eng.joaovictor.ghproject.model.Repo
import br.eng.joaovictor.ghproject.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface UserService {
   @GET("users")
   suspend fun getUser(@Query("since") since: Int?  = null) : List<User>

   @GET("users/{login}")
   suspend fun getUser(@Path("login") login: String) : User

   @GET("users/{login}/repos")
   suspend fun getUserRepos(@Path("login") login: String) : List<Repo>

}