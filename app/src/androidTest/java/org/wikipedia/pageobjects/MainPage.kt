package org.wikipedia.pageobjects

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString
import org.wikipedia.R

class MainPage : BasePage() {
    private val searchBarMatcher = allOf(
        withId(R.id.search_container),
        isDisplayed()
    )
    private val bottomBarMoreMatcher = withId(R.id.nav_more_container)
    private val settingsButtonMatcher =
        withId(R.id.main_drawer_settings_container)
    private val donateButtonMatcher = withId(R.id.main_drawer_donate_container)

    fun clickSearchBar() {
        onView(searchBarMatcher).perform(click())
    }

    fun clickMoreBottomBarButton() {
        onView(bottomBarMoreMatcher).perform(click())
    }

    fun clickSettingsBottomBarButton() {
        onView(settingsButtonMatcher).perform(click())
    }

    fun clickDonateBottomBarButton() {
        onView(donateButtonMatcher).perform(click())
    }

    fun isIntentSentAfterDonateButtonClick(): Boolean {
        val extraIntent = allOf(
            IntentMatchers.hasAction(Intent.ACTION_VIEW), IntentMatchers.hasDataString(
                containsString("https://donate.wikimedia.org/")
            )
        )
        return isIntentPresent(
            IntentMatchers.hasAction(Intent.ACTION_CHOOSER),
            IntentMatchers.hasExtra(Intent.EXTRA_INTENT, extraIntent)
        )
    }
}