package org.wikipedia

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import org.wikipedia.main.MainActivity

@RunWith(AndroidJUnit4::class)
open class BaseTest {
    @Rule
    @JvmField
    val mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)
}