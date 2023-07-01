package org.wikipedia.model

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
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
        //todo use smart wait instead
        TestUtil.delay(2)
    }

    fun clickSearchResultItemWithText(text: String) {
        onView(searchResultListItemMatcher(text)).perform(ViewActions.click())
    }

    fun resultsListShouldBeEmpty() {
        Assert.assertEquals("No results", getText(onView(emptySearchResultsMessageMatcher)))
    }

    fun searchHistoryShouldContainItems(vararg items: String) {
        //todo użyć metody pobierającej text wszystkich itemów recyclerview
        for (item: String in items) {
            onView(searchHistoryListItemMatcher(item)).check(
                matches(
                    isDisplayed()
                )
            )
        }
    }
}