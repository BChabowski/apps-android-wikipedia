package org.wikipedia.search

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.wikipedia.BaseTest
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

        mainPage.clickSearchBar()

        searchPage.run {
            typeIntoSearchBar(searchQuery)
            clickSearchResultItemWithText(searchQuery)
        }

        assertTrue("Article is not visible", articlePage.isArticleViewDisplayed())
    }

    @Test
    fun searchNotExistingArticle() {
        val notExistingArticle = "articlenotexisting"

        mainPage.clickSearchBar()

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

        mainPage.clickSearchBar()

        searchPage.run {
            addSearchLanguage(language)
            putIntoSearchBar(searchQuery)
            changeSearchLanguage(languageAbbreviation)
            clickSearchResultItemWithText(searchQuery)
        }

        assertTrue("Article is not visible", articlePage.isArticleViewDisplayed())
    }

    @Test
    fun searchHistoryShouldBeVisible() {
        val firstSearchQuery = "Barack Obama"
        val secondSearchQuery = "Joe Biden"

        mainPage.clickSearchBar()

        searchPage.run {
            typeIntoSearchBar(firstSearchQuery)
            clickSearchResultItemWithText(firstSearchQuery)
        }

        articlePage.pressBack()

        searchPage.run {
            typeIntoSearchBar(secondSearchQuery)
            clickSearchResultItemWithText(secondSearchQuery)
        }

        articlePage.pressBack()

        val historyContainsItems = searchPage.run {
            clearSearchBar()
            searchHistoryContainsItems(firstSearchQuery, secondSearchQuery)
        }
        assertTrue("Search history doesn't contain all required items", historyContainsItems)
    }
}