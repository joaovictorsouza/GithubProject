package br.eng.joaovictor.ghproject.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import br.eng.joaovictor.ghproject.BuildConfig
import br.eng.joaovictor.ghproject.data.users.UserService
import dagger.Provides

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Singleton
    @Provides
    fun provideRetrofit(): UserService {
        val builder = OkHttpClient().newBuilder()
        builder.readTimeout(10, TimeUnit.SECONDS)
        builder.connectTimeout(5, TimeUnit.SECONDS)
        builder.addInterceptor{ chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader("Authorization", BuildConfig.GITHUB_API_KEY)
                .addHeader("X-GitHub-Api-Version", "2022-11-28")
                .addHeader("Accept", "application/vnd.github+json")
                .build()
            chain.proceed(request)
        }

        return Retrofit.Builder()
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.GITHUB_API_URL)
            .build()
            .create(UserService::class.java)
    }
}