package com.abduldev.artbookapptestingguide.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abduldev.artbookapptestingguide.MainCoroutineRule
import com.abduldev.artbookapptestingguide.getOrAwaitValueTest
import com.abduldev.artbookapptestingguide.repo.FakeArtRepo
import com.abduldev.artbookapptestingguide.utils.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ArtViewModelTest {
    private lateinit var viewModel: ArtViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        //Test Doubles
        viewModel = ArtViewModel(com.abduldev.artbookapptestingguide.repo.FakeArtRepo())

    }

    @Test
    fun `insert art without year return error`() {
        viewModel.validateInputs(
            "mona lisa",
            "mona lisa",
            ""
        )
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without name return error`() {
        viewModel.validateInputs(
            "",
            "mona lisa",
            "2020"
        )
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artistName return error`() {
        viewModel.validateInputs(
            "mona lisa",
            "",
            "2020"
        )
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art with non int year return error`() {
        viewModel.validateInputs(
            "mona lisa",
            "mona lisa",
            "qrffeg"
        )
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}