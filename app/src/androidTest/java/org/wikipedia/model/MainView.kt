package org.wikipedia.model

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers
import org.wikipedia.R

class MainView {
    private val searchBarId = R.id.search_container

    fun clickSearchBar(): SearchView {
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(searchBarId),
                ViewMatchers.isDisplayed()
            )
        )
            .perform(ViewActions.click())
        return SearchView()
    }
}