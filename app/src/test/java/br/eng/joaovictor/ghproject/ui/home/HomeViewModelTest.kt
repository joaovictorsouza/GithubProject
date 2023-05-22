package br.eng.joaovictor.ghproject.ui.home

import br.eng.joaovictor.ghproject.data.users.UsersRepository
import br.eng.joaovictor.ghproject.model.User
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException


@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var usersRepository: UsersRepository
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        usersRepository = mockk()
        viewModel = HomeViewModel(usersRepository)
        Dispatchers.setMain(testCoroutineDispatcher)

    }

    @After
    fun teardown() {
        clearAllMocks()
    }

    @Test
    fun `getUserData should fetch users data and update users flow`() = runBlocking {
        val testUsers = listOf(User(id = 1, name = "User 1", avatarUrl = "", bio = null, login = "user1"),
            User(id = 2, name = "User 2",  avatarUrl = "", bio = null, login = "user2"))
        val usersFlow = MutableStateFlow(testUsers)
        coEvery { usersRepository.getUsers() } returns flowOf(testUsers)

        assert(viewModel.users.value.isEmpty())
        viewModel.getUserData()

        assert(!viewModel.isLoading.value)
        assert(viewModel.users.value == testUsers)

        coVerify { usersRepository.getUsers() }
    }

    @Test
    fun `getUserData should set error message when fetching users data fails`() = runBlocking {
        val errorMessage = "Erro de conexão com o servidor"
        coEvery { usersRepository.getUsers() } throws IOException()

        viewModel.getUserData()


        assert(!viewModel.isLoading.value)
        assert(viewModel.errorMessage.value == errorMessage)

        coVerify { usersRepository.getUsers() }
    }

    @Test
    fun `fetchMoreItems should fetch more users data and update users flow`() = runBlocking {
        val existingUsers = listOf(User(id = 1, name = "User 1", avatarUrl = "", bio = null, login = "user1"),
            User(id = 2, name = "User 2",  avatarUrl = "", bio = null, login = "user2"))

        val newUsers = listOf(User(id = 3, name = "User 3", avatarUrl = "", bio = null, login = "user3"),
            User(id = 4, name = "User 4", avatarUrl = "", bio = null, login = "user4"))

        val combinedUsers = existingUsers + newUsers
        val usersFlow = MutableStateFlow(existingUsers)
        coEvery { usersRepository.getUsers(any()) } returns flowOf(newUsers)

        viewModel.users.value = existingUsers

        viewModel.fetchMoreItems()


        assert(!viewModel.isLoading.value)
        assert(viewModel.users.value == combinedUsers)

        coVerify { usersRepository.getUsers(existingUsers.last().id) }
    }
}
