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

    fun clickSearchBar() {
        onView(searchBarMatcher).perform(click())
    }
}