package com.abduldev.artbookapptestingguide.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.abduldev.artbookapptestingguide.db.dao.ArtDao
import com.abduldev.artbookapptestingguide.db.enities.Art
import com.abduldev.artbookapptestingguide.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var dao: ArtDao
//    private lateinit var database: ArtistDatabase

    @get:Rule
    var hilRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database: ArtistDatabase

    @Before
    fun setUp() {
//        database = Room.inMemoryDatabaseBuilder(
//            ApplicationProvider.getApplicationContext(),
//            ArtistDatabase::class.java
//        ).allowMainThreadQueries().build()
        hilRule.inject()
        dao = database.artDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertArtTesting() = runBlocking {
        val exampleArt = Art(
            "mona lisa",
            "da vinci",
            1700,
            "test.com",
            1
        )
        dao.insertAtr(exampleArt)

        val list = dao.getAllArts().getOrAwaitValue()
        assertThat(list).contains(exampleArt)
    }

    @Test
    fun deleteArtTesting() = runBlocking {
        val exampleArt = Art(
            "mona lisa",
            "da vinci",
            1700,
            "test.com",
            1
        )
        dao.insertAtr(exampleArt)
        dao.deleteArt(exampleArt)

        val list = dao.getAllArts().getOrAwaitValue()
        assertThat(list).doesNotContain(exampleArt)
    }


}