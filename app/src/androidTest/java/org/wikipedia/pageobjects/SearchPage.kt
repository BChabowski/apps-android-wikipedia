package org.wikipedia.pageobjects

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.allOf
import org.wikipedia.R

class SearchPage : BasePage() {
    private val searchBarMatcher = withHint("Search Wikipedia")
    private val searchResultListMatcher =
        allOf(withId(R.id.search_results_list), isDisplayed())
    private fun searchResultListItemMatcher(itemText: String) =
        allOf(searchResultListMatcher, hasDescendant(withText(itemText)))
    private val emptySearchResultsMessageMatcher = withId(R.id.results_text)
    private val addNewLanguageSearchButtonMatcher = withId(R.id.search_lang_button)
    private val addNewLanguageButtonMatcher = withText("Add language")
    private val languagesListMatcher = withId(R.id.languages_list_recycler)
    private fun searchLanguageButtonMatcher(languageAbbreviation: String) =
        withText(languageAbbreviation)
    private fun searchHistoryListItemMatcher(itemText: String) =
        allOf(withText(itemText), withParent(withId(R.id.recent_searches_recycler)))

    fun typeIntoSearchBar(text: String) {
        onView(searchBarMatcher)
            .perform(clearText())
            .perform(typeText(text))
    }

    fun putIntoSearchBar(text: String) {
        onView(searchBarMatcher)
            .perform(replaceText(text))
    }

    fun clearSearchBar() {
        typeIntoSearchBar("")
    }

    fun addSearchLanguage(language: String) {
        onView(addNewLanguageSearchButtonMatcher).perform(click())
        onView(addNewLanguageButtonMatcher).perform(click())
        onView(languagesListMatcher).perform(
            actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText(language)), click()
            )
        )
        pressBack()
    }

    fun changeSearchLanguage(languageAbbreviation: String) {
        tapWithWait(searchLanguageButtonMatcher(languageAbbreviation))
    }

    fun tapSearchResultItemWithText(text: String) {
        tapWithWait(searchResultListItemMatcher(text))
    }

    fun getResultsListText(): String {
        waitFor(emptySearchResultsMessageMatcher)
        return getText(emptySearchResultsMessageMatcher)
    }

    fun searchHistoryContainsItems(vararg items: String): Boolean {
        for (item: String in items) {
            if (!isViewDisplayed(searchHistoryListItemMatcher(item))) {
                return false
            }
        }
        return true
    }
}