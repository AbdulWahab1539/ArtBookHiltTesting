package com.abduldev.artbookapptestingguide.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.abduldev.artbookapptestingguide.R
import com.abduldev.artbookapptestingguide.adapters.ImageAdapter
import com.abduldev.artbookapptestingguide.getOrAwaitValue
import com.abduldev.artbookapptestingguide.launchFragmentInHiltContainer
import com.abduldev.artbookapptestingguide.repo.FakeArtRepoTest
import com.abduldev.artbookapptestingguide.view.fragments.SearchFragment
import com.abduldev.artbookapptestingguide.viewmodels.ArtViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class SearchFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun selectImage() {
        val navController = Mockito.mock(NavController::class.java)
        val selectedImageUrl = "test.com"
        val testViewModel = ArtViewModel(FakeArtRepoTest())

        launchFragmentInHiltContainer<SearchFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
            artViewModel = testViewModel
            imageAdapter.images = listOf(selectedImageUrl)
        }
        Espresso.onView(withId(R.id.rvSearch)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageAdapter
            .ImageAdapterViewHolder>(
                0, click()
            )
        )

        Mockito.verify(navController).popBackStack()
        assertThat(
            testViewModel.selectedImageUrl.getOrAwaitValue()
        ).isEqualTo(selectedImageUrl)
    }

}