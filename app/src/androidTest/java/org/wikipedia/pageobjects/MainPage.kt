package org.wikipedia.pageobjects

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers
import org.wikipedia.R

class MainPage: BasePage() {
    private val searchBarMatcher = Matchers.allOf(ViewMatchers.withId(R.id.search_container),
        ViewMatchers.isDisplayed())

    fun clickSearchBar() {
        onView(searchBarMatcher)
            .perform(ViewActions.click())
    }
}