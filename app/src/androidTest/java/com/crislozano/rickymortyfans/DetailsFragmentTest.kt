package com.crislozano.rickymortyfans

import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.crislozano.rickymortyfans.presentation.MainActivity
import com.crislozano.rickymortyfans.presentation.list.CharacterItemAdapter
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest {

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity {
            it.findNavController(R.id.fragment_content)
        }
        Thread.sleep(5000)
        onView(withId(R.id.list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CharacterItemAdapter.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Thread.sleep(1000)
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }

    @Test
    fun testTopBar_onResumed() {
        activityScenario.moveToState(Lifecycle.State.RESUMED)

        activityScenario.onActivity {
            val title = it.supportActionBar?.title
            assert(title == "Character details")
            assert(it.supportActionBar?.isShowing == true)
        }
        onView(withContentDescription("Navigate up")).check(matches(isDisplayed()))
    }


    @Test
    fun testTopBar_onConfigurationChanges() {
        activityScenario.moveToState(Lifecycle.State.RESUMED)

        activityScenario.recreate()

        activityScenario.onActivity {
            val title = it.supportActionBar?.title
            assert(title == "Character details")
            assert(it.supportActionBar?.isShowing == true)
        }
        onView(withContentDescription("Navigate up")).check(matches(isDisplayed()))
    }
}