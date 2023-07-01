package org.wikipedia.model

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Assert
import org.wikipedia.R
import org.wikipedia.TestUtil

class SearchView : BaseView() {
    private val searchBarMatcher = ViewMatchers.withHint("Search Wikipedia")
    private val searchResultListMatcher =
        Matchers.allOf(withId(R.id.search_results_list), isDisplayed())
    private fun searchResultListItemMatcher(itemText: String) = Matchers.allOf(searchResultListMatcher, hasDescendant(withText(itemText)))
    private val emptySearchResultsMessageMatcher = withId(R.id.results_text)
    private val addNewLanguageSearchButtonMatcher = withId(R.id.search_lang_button)
    private val addNewLanguageButtonMatcher = withText("Add language")
    private val languagesListMatcher = withId(R.id.languages_list_recycler)
    private fun searchHistoryListItemMatcher(itemText: String) = Matchers.allOf(withText(itemText), withParent(withId(R.id.recent_searches_recycler)))


    fun typeIntoSearchBar(text: String) {
        onView(searchBarMatcher)
            .perform(ViewActions.clearText())
            .perform(ViewActions.typeText(text))

        //todo use smart wait instead
        TestUtil.delay(2)
    }

    fun putIntoSearchBar(text: String) {
        onView(searchBarMatcher)
            .perform(ViewActions.replaceText(text))

        TestUtil.delay(2)
    }

    fun clearSearchBar() {
        typeIntoSearchBar("")
    }

    fun clickOnNthResult(itemIndex: Int) {
        onView(searchResultListMatcher).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                itemIndex,
                ViewActions.click()
            )
        )
    }

    fun addSearchLanguage(language: String) {
        onView(addNewLanguageSearchButtonMatcher).perform(ViewActions.click())
        onView(addNewLanguageButtonMatcher).perform(ViewActions.click())
        onView(languagesListMatcher).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText(language)), ViewActions.click()
            )
        )
        onView(isRoot()).perform(ViewActions.pressBack())
    }

    fun changeSearchLanguage(languageAbbreviation: String) {
        onView(withText(languageAbbreviation)).perform(ViewActions.click())
    }

    fun searchResultsShouldContainItems(vararg items: String) {
        TestUtil.delay(2)
        for (item: String in items) {
            onView(searchResultListItemMatcher(item)).check(
                matches(isDisplayed())
            )
        }
    }

    fun resultsListShouldBeEmpty() {
        Assert.assertEquals("No results", getText(onView(emptySearchResultsMessageMatcher)))
    }

    fun searchHistoryShouldContainItems(vararg items: String) {
        for (item: String in items) {
            onView(searchHistoryListItemMatcher(item)).check(
                matches(
                    isDisplayed()
                )
            )
        }
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