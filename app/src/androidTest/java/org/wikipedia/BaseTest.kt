package org.wikipedia

import android.app.Activity
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.runner.RunWith
import org.wikipedia.main.MainActivity

@RunWith(AndroidJUnit4::class)
open class BaseTest {
    protected fun getSharedPreferencesEditor(): SharedPreferences.Editor {
        val targetContext = getInstrumentation().targetContext
        val preferencesEditor = PreferenceManager.getDefaultSharedPreferences(targetContext).edit()
        preferencesEditor.clear()
        return preferencesEditor
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
        preferencesEditor.putBoolean("initialOnboardingEnabled", false)
        launchActivityWithPreferences(MainActivity::class.java, preferencesEditor)
    }
}