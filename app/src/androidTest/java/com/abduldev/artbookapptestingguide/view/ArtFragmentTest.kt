package com.abduldev.artbookapptestingguide.view

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUiSaveStateControl
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.abduldev.artbookapptestingguide.launchFragmentInHiltContainer
import com.abduldev.artbookapptestingguide.view.fragments.ArtsFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject
import com.abduldev.artbookapptestingguide.R
import com.abduldev.artbookapptestingguide.view.fragments.ArtsFragmentDirections

@MediumTest
@HiltAndroidTest
class ArtFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testNavigationFragmentArtToDetail() {

        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtsFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(
                requireView(),
                navController
            )
        }
        Espresso.onView(
            ViewMatchers.withId(R.id.floatingActionButton)
        ).perform(click())

        Mockito.verify(navController).navigate(
            ArtsFragmentDirections.actionArtsFragmentToArtDetailFragment()
        )
    }

}