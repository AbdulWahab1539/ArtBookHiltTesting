package com.abduldev.artbookapptestingguide.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.abduldev.artbookapptestingguide.launchFragmentInHiltContainer
import com.abduldev.artbookapptestingguide.view.fragments.ArtDetailFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject
import com.abduldev.artbookapptestingguide.R
import com.abduldev.artbookapptestingguide.db.enities.Art
import com.abduldev.artbookapptestingguide.getOrAwaitValue
import com.abduldev.artbookapptestingguide.repo.FakeArtRepoTest
import com.abduldev.artbookapptestingguide.view.fragments.ArtDetailFragmentDirections
import com.abduldev.artbookapptestingguide.viewmodels.ArtViewModel
import com.google.common.truth.Truth.assertThat

@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class ArtDetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()

    }


    @Test
    fun testNavigationFromArtDetailsFragmentApi() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.ivArtistProfile))
            .perform(click())

        Mockito.verify(navController).navigate(
            ArtDetailFragmentDirections
                .actionArtDetailFragmentToSearchFragment()
        )
    }

    @Test
    fun testOnBackPressed() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.pressBack()
        Mockito.verify(navController).popBackStack()
    }

    @Test
    fun testSave() {
        val testViewModel = ArtViewModel(FakeArtRepoTest())
        launchFragmentInHiltContainer<ArtDetailFragment>(
            factory = fragmentFactory
        ) {
            viewModel = testViewModel
        }

        Espresso.onView(withId(R.id.etName))
            .perform(replaceText("Mona Lisa"))
        Espresso.onView(withId(R.id.etArtist))
            .perform(replaceText("Mona Lisa"))
        Espresso.onView(withId(R.id.etYear))
            .perform(replaceText("1500"))
        Espresso.onView(withId(R.id.btnSave))
            .perform(click())

        assertThat(testViewModel.artList.getOrAwaitValue()).contains(
            Art("Mona Lisa", "Mona Lisa", 1500, "")
        )
    }



}