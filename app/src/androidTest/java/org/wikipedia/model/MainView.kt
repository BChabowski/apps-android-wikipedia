package org.wikipedia.model

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers
import org.wikipedia.R

class MainView: BaseView() {
    private val searchBarMatcher = Matchers.allOf(ViewMatchers.withId(R.id.search_container),
        ViewMatchers.isDisplayed())

    fun clickSearchBar() {
        Espresso.onView(searchBarMatcher)
            .perform(ViewActions.click())
    }
}