package br.eng.joaovictor.ghproject.ui.user

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import br.eng.joaovictor.ghproject.data.users.UsersRepository
import br.eng.joaovictor.ghproject.model.Repo
import br.eng.joaovictor.ghproject.model.User
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class UserViewModelTest {
    private lateinit var viewModel: UserViewModel
    private lateinit var usersRepository: UsersRepository
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    @Before
    fun setup() {
        usersRepository = mockk(relaxed = true)
        viewModel = UserViewModel(usersRepository)
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getUserDetails should update userDetails when successful`() = runBlocking {
        // Given
        val login = "john"
        val user = User(
            id = 1,
            login = login,
            avatarUrl = "",
            bio = null,
            name = ""
        )
        coEvery { usersRepository.getUser(login) } returns flowOf(user)

        // When
        viewModel.getUserDetails(login)

        // Then
        assert(viewModel.userDetails.value == user)
    }

    @Test
    fun `getUserDetails should set errorMessage when IOException occurs`() = runBlocking {
        // Given
        val login = "john"
        coEvery { usersRepository.getUser(login) } throws IOException()

        // When
        viewModel.getUserDetails(login)

        // Then
        assert(viewModel.errorMessage.value == "Erro de conexão com o servidor")
    }

    @Test
    fun `getUserDetails should set errorMessage when unknown exception occurs`() = runBlocking {
        // Given
        val login = "john"
        coEvery { usersRepository.getUser(login) } throws Exception()

        // When
        viewModel.getUserDetails(login)

        // Then
        assert(viewModel.errorMessage.value == "Erro desconhecido ao buscar dados do usuário")
    }

    @Test
    fun `getUserRepos should append repos to userRepos when successful`() = runBlocking {
        // Given
        val login = "john"
        val repos = listOf(
            Repo(
                id = 1,
                name = "repo1",
                description = "",
                fullName = "",
                language = "",
                url = ""
                ),
            Repo(
                id = 2,
                name = "repo2",
                description = "",
                fullName = "",
                language = "",
                url = ""
            )
        )
        coEvery { usersRepository.getUserRepos(login) } returns flowOf(repos)

        // When
        viewModel.getUserRepos(login)

        // Then
        assert(viewModel.userRepos.value == repos)
    }

    @Test
    fun `getUserRepos should set errorMessage when IOException occurs`() = runBlockingTest {
        // Given
        val login = "john"
        coEvery { usersRepository.getUserRepos(login) } throws IOException()

        // When
        viewModel.getUserRepos(login)

        // Then
        assert(viewModel.errorMessage.value == "Erro de conexão com o servidor")
    }

    @Test
    fun `getUserRepos should set errorMessage when unknown exception occurs`() = runBlockingTest {
        // Given
        val login = "john"
        coEvery { usersRepository.getUserRepos(login) } throws Exception()

        // When
        viewModel.getUserRepos(login)

        // Then
        assert(viewModel.errorMessage.value == "Erro desconhecido ao listar repositórios")
    }
}
