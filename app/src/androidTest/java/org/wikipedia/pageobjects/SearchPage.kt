package org.wikipedia.pageobjects

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers
import org.wikipedia.R

class SearchPage : BasePage() {
    private val searchBarMatcher = ViewMatchers.withHint("Search Wikipedia")
    private val searchResultListMatcher =
        Matchers.allOf(withId(R.id.search_results_list), isDisplayed())
    private fun searchResultListItemMatcher(itemText: String) = Matchers.allOf(searchResultListMatcher, hasDescendant(withText(itemText)))
    private val emptySearchResultsMessageMatcher = withId(R.id.results_text)
    private val addNewLanguageSearchButtonMatcher = withId(R.id.search_lang_button)
    private val addNewLanguageButtonMatcher = withText("Add language")
    private val languagesListMatcher = withId(R.id.languages_list_recycler)
    private fun searchLanguageButtonMatcher(languageAbbreviation: String) = withText(languageAbbreviation)
    private fun searchHistoryListItemMatcher(itemText: String) = Matchers.allOf(withText(itemText), withParent(withId(R.id.recent_searches_recycler)))

    fun typeIntoSearchBar(text: String) {
        onView(searchBarMatcher)
            .perform(ViewActions.clearText())
            .perform(ViewActions.typeText(text))
    }

    fun putIntoSearchBar(text: String) {
        onView(searchBarMatcher)
            .perform(ViewActions.replaceText(text))
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
        pressBack()
    }

    fun changeSearchLanguage(languageAbbreviation: String) {
        clickWithWait(searchLanguageButtonMatcher(languageAbbreviation))
    }

    fun clickSearchResultItemWithText(text: String) {
        clickWithWait(searchResultListItemMatcher(text))
    }

    fun getResultsListText(): String {
        waitFor(emptySearchResultsMessageMatcher)
        return getText(emptySearchResultsMessageMatcher)
    }

    fun searchHistoryContainsItems(vararg items: String): Boolean {
        for (item: String in items) {
            if(!isViewDisplayed(searchHistoryListItemMatcher(item))){
                return false
            }
        }
        return true
    }
}