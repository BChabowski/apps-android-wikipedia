package org.wikipedia.e2etests.search

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.wikipedia.e2etests.BaseTest
import org.wikipedia.pageobjects.ArticlePage
import org.wikipedia.pageobjects.MainPage
import org.wikipedia.pageobjects.SearchPage

class SearchTest : BaseTest() {
    private val mainPage = MainPage()
    private val searchPage = SearchPage()
    private val articlePage = ArticlePage()

    @Before
    fun startActivity() {
        launchMainActivityWithDefaultPreferences()
    }

    @Test
    fun searchAndDisplayArticle() {
        val searchQuery = "Barack Obama"

        mainPage.tapSearchBar()

        searchPage.run {
            typeIntoSearchBar(searchQuery)
            tapSearchResultItemWithText(searchQuery)
        }

        assertTrue("Article is not visible", articlePage.isArticleViewDisplayed())
    }

    @Test
    fun searchNotExistingArticle() {
        val notExistingArticle = "articlenotexisting"

        mainPage.tapSearchBar()

        val searchListText = searchPage.run {
            typeIntoSearchBar(notExistingArticle)
            getResultsListText()
        }
        assertEquals("No results", searchListText)
    }

    @Test
    fun changeLanguageInSearch() {
        val language = "Русский"
        val languageAbbreviation = "RU"
        val searchQuery = "Обама, Барак"

        mainPage.tapSearchBar()

        searchPage.run {
            addSearchLanguage(language)
            putIntoSearchBar(searchQuery)
            changeSearchLanguage(languageAbbreviation)
            tapSearchResultItemWithText(searchQuery)
        }

        assertTrue("Article is not visible", articlePage.isArticleViewDisplayed())
    }

    @Test
    fun searchHistoryShouldBeVisible() {
        val firstSearchQuery = "Barack Obama"
        val secondSearchQuery = "Joe Biden"

        mainPage.tapSearchBar()

        searchPage.run {
            typeIntoSearchBar(firstSearchQuery)
            tapSearchResultItemWithText(firstSearchQuery)
        }

        articlePage.pressBack()

        searchPage.run {
            typeIntoSearchBar(secondSearchQuery)
            tapSearchResultItemWithText(secondSearchQuery)
        }

        articlePage.pressBack()

        val historyContainsItems = searchPage.run {
            clearSearchBar()
            searchHistoryContainsItems(firstSearchQuery, secondSearchQuery)
        }
        assertTrue("Search history doesn't contain all required items", historyContainsItems)
    }
}