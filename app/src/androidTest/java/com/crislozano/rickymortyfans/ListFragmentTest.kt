package com.crislozano.rickymortyfans

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.crislozano.rickymortyfans.presentation.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListFragmentTest {

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }

    @Test
    fun testTopBar_onResumed() {
        activityScenario.moveToState(Lifecycle.State.CREATED)


        activityScenario.moveToState(Lifecycle.State.RESUMED)

        activityScenario.onActivity {
            val title = it.supportActionBar?.title
            assert(title == "Characters list")
            assert(it.supportActionBar?.isShowing == true)
        }
    }


    @Test
    fun testTopBar_onConfigurationChanges() {
        activityScenario.moveToState(Lifecycle.State.RESUMED)


        activityScenario.recreate()

        activityScenario.onActivity {
            val title = it.supportActionBar?.title
            assert(title == "Characters list")
            assert(it.supportActionBar?.isShowing == true)
        }
    }


}