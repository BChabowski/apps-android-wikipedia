package org.wikipedia.pageobjects

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matchers.allOf
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

    fun tapSearchBar() {
        onView(searchBarMatcher).perform(click())
    }

    fun tapMoreBottomBarButton() {
        onView(bottomBarMoreMatcher).perform(click())
    }

    fun tapSettingsBottomBarButton() {
        onView(settingsButtonMatcher).perform(click())
    }

    fun tapDonateBottomBarButton() {
        onView(donateButtonMatcher).perform(click())
    }
}