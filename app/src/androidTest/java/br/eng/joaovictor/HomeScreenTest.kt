package br.eng.joaovictor

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import br.eng.joaovictor.ghproject.ui.GhApp
import br.eng.joaovictor.ghproject.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@HiltAndroidTest
@RunWith(JUnit4::class)
class HomeScreenTest{

    val mockWebServer by lazy { MockWebServer() }


    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

//    @Before
//    fun setUp() {
//        mockWebServer.start(8080)
//    }
//
//    @After
//    fun teardown() {
//        mockWebServer.shutdown()
//    }

    @Before
    fun init() {
        hiltRule.inject()
    }

//    fun retrieveDispatcher(): Dispatcher {
//        return object : Dispatcher() {
//            override fun dispatch(request: RecordedRequest): MockResponse = when (request.path) {
//                "/user" -> MockResponse().setResponseCode(401)
//                else -> MockResponse().setResponseCode(404)
//            }
//        }
//    }
    @Test
    @InternalCoroutinesApi
    fun test_home_screen_ok() {
//        mockWebServer.dispatcher = retrieveDispatcher()
        with(composeRule) {
            wait()
        }
    }

}