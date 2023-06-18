package org.wikipedia.model

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Assert
import org.wikipedia.R
import org.wikipedia.TestUtil

class SearchView {
    private val searchBarHint = "Search Wikipedia"
    private val searchResultList = R.id.search_results_list
    private val emptySearchResultsMessage = R.id.results_text

    fun typeIntoSearchBar(text: String) : SearchView {
        onView(ViewMatchers.withHint(searchBarHint))
            .perform(ViewActions.typeText (text))

        //todo use IdlingResources instead
        TestUtil.delay(2)
        return this
    }

    fun clickOnNthResult(itemIndex: Int): ArticleView {
        onView(
            Matchers.allOf(
                ViewMatchers.withId(searchResultList),
                ViewMatchers.isDisplayed()
            )).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(itemIndex, ViewActions.click()))
        return ArticleView()
    }

    fun resultsListShouldBeEmpty() {
        Assert.assertEquals("No results", getText(onView(withId(emptySearchResultsMessage))))
//        onView(recyclerViewSizeMatcher(0)).check(ViewAssertions.matches(isDisplayed()))
    }

    private fun getText(matcher: ViewInteraction): String {
        var text = String()
        matcher.perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "Text of the view"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                text = tv.text.toString()
            }
        })

        return text
    }

    private fun recyclerViewSizeMatcher(matcherSize: Int): Matcher<View?>? {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("with list size: $matcherSize")
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                return matcherSize == recyclerView.adapter!!.itemCount
            }
        }
    }

}