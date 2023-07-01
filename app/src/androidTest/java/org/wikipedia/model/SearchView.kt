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
    private val searchBarHint = "Search Wikipedia"
    private val searchResultList = R.id.search_results_list
    private val emptySearchResultsMessage = R.id.results_text
    private val addNewLanguageSearchButton = R.id.search_lang_button
    private val addNewLanguageText = "Add language"
    private val languagesList = R.id.languages_list_recycler

    //do szukania jęz id	@id/search_input
    private val searchLanguagesHolder = R.id.horizontal_scroll_languages
    private val searchHistory = R.id.recent_searches_recycler

    fun typeIntoSearchBar(text: String) {
        onView(ViewMatchers.withHint(searchBarHint))
            .perform(ViewActions.clearText())
            .perform(ViewActions.typeText(text))

        //todo use smart wait instead
        TestUtil.delay(2)
    }

    fun putIntoSearchBar(text: String) {
        onView(ViewMatchers.withHint(searchBarHint))
            .perform(ViewActions.replaceText(text))

        TestUtil.delay(2)
    }

    fun clearSearchBar() {
        typeIntoSearchBar("")
    }

    fun clickOnNthResult(itemIndex: Int) {
        onView(
            Matchers.allOf(
                withId(searchResultList),
                isDisplayed()
            )
        ).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                itemIndex,
                ViewActions.click()
            )
        )
    }

    fun addSearchLanguage(language: String) {
        onView(withId(addNewLanguageSearchButton)).perform(ViewActions.click())
        onView(withText(addNewLanguageText)).perform(ViewActions.click())
        onView(withId(languagesList)).perform(
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
            onView(Matchers.allOf(withId(searchResultList), hasDescendant(withText(item)))).check(
                matches(isDisplayed())
            )
        }
    }

    fun resultsListShouldBeEmpty() {
        Assert.assertEquals("No results", getText(onView(withId(emptySearchResultsMessage))))
    }

    fun searchHistoryShouldContainItems(vararg items: String) {
        for (item: String in items) {
            onView(Matchers.allOf(withText(item), withParent(withId(searchHistory)))).check(
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