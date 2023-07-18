package org.wikipedia

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.hamcrest.CoreMatchers.anyOf
import org.hamcrest.Matcher
import org.junit.runner.RunWith
import org.wikipedia.main.MainActivity

@RunWith(AndroidJUnit4::class)
open class BaseTest {
    protected fun getSharedPreferencesEditor(): SharedPreferences.Editor {
        val targetContext = getInstrumentation().targetContext
        return PreferenceManager.getDefaultSharedPreferences(targetContext).edit()
    }

    protected fun launchActivityWithPreferences(
        activityToLaunch: Class<out Activity>,
        preferencesEditor: SharedPreferences.Editor
    ) {
        preferencesEditor.commit()
        ActivityScenario.launch(activityToLaunch)
    }

    protected fun launchMainActivityWithDefaultPreferences() {
        val preferencesEditor = getSharedPreferencesEditor()
        preferencesEditor.clear()
        preferencesEditor.putBoolean("initialOnboardingEnabled", false)
        launchActivityWithPreferences(MainActivity::class.java, preferencesEditor)
    }

    protected fun mockIntentsWithActions(vararg intentActions: String) {
        var intentMatchers = mutableListOf<Matcher<Intent>>()
        intentActions.forEach { e ->
            intentMatchers.add(hasAction(e))
        }
        Intents.init()
        intending(anyOf(intentMatchers.asIterable()))
            .respondWith(ActivityResult(RESULT_OK, null))
    }
}