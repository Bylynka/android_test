package com.agileengine.test.api


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.agileengine.test.api.repos.IReposApi
import com.agileengine.test.models.Repo
import com.squareup.moshi.Moshi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.IsNull.notNullValue
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(RobolectricTestRunner::class)
class ApiServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var apiService: IReposApi

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        val moshi = Moshi.Builder().build()
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(IReposApi::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun fetchRepos() {
        enqueueResponse()
        val response = apiService.fetchRepos().execute().body()
        val request = mockWebServer.takeRequest()

        assertThat(request.path, `is`("/repos"))
        assertThat<List<Repo>>(response, notNullValue())
        assertThat(response?.size ?: 0, `is`(30))

        assertThat(response?.get(1)?.name, `is`("simplerrd"))
        assertThat(response?.get(2)?.description, `is`("Test Framework for Objective-C"))
    }

    private fun enqueueResponse() {
        val inputStream = javaClass.classLoader
            ?.getResourceAsStream("repos.json") ?: return
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }

}