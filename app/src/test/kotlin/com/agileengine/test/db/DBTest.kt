package com.agileengine.test.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.agileengine.test.utils.LiveDataTestUtil
import junit.framework.TestCase.assertTrue
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import com.agileengine.test.models.Repo
import org.hamcrest.CoreMatchers
import org.junit.*


@RunWith(RobolectricTestRunner::class)
class DBTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var repoDao: IRepoDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        repoDao = database.repoDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun emptyRepos() {
        val repos = LiveDataTestUtil.getValue(repoDao.getRepos())
        assertTrue(repos.isEmpty())
    }

    @Test
    fun notEmptyRepos() {
        val fakeRepos = generateFakeRepos()
        repoDao.insert(fakeRepos)
        val repos = LiveDataTestUtil.getValue(repoDao.getRepos())
        assertTrue(repos.isNotEmpty())
        Assert.assertThat(repos.size, CoreMatchers.`is`(2))
    }

    @Test
    fun repoContent() {
        val fakeRepos = generateFakeRepos()
        repoDao.insert(fakeRepos)
        val repos = LiveDataTestUtil.getValue(repoDao.getRepos())
        assertTrue(repos.isNotEmpty())
        Assert.assertThat(repos[0].name, CoreMatchers.`is`("name 1"))
        Assert.assertThat(repos[1].description, CoreMatchers.`is`("description 2"))
    }

    private fun generateFakeRepos() = listOf(
        Repo().apply {
            id = 1
            name = "name 1"
            description = "description 1"
        },
        Repo().apply {
            id = 2
            name = "name 2"
            description = "description 2"
        }
    )

}