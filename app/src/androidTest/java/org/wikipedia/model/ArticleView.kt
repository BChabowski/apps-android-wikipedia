package org.wikipedia.model

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.wikipedia.R

class ArticleView {
    private val articleWebView = R.id.page_web_view

    fun articleViewShouldBeDisplayed(): ArticleView {
        onView(withId(articleWebView)).check(ViewAssertions.matches(isDisplayed()))
        return this
    }
}