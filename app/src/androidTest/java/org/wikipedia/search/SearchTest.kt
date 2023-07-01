package org.wikipedia.search

import org.junit.Test
import org.wikipedia.BaseTest
import org.wikipedia.model.ArticleView
import org.wikipedia.model.BaseView
import org.wikipedia.model.MainView
import org.wikipedia.model.OnboardingView
import org.wikipedia.model.SearchView

class SearchTest : BaseTest() {
    //todo dopisać metodę na pobieranie tekstu z recyclerview
    //todo mądre waity
    //todo pomijanie onboardingu za pomocą intenta/shared preferences

    @Test
    fun searchAndDisplayArticle() {
        val searchQuery = "Barack Obama"

        OnboardingView().run {
            skipOnboarding()
        }
        MainView().run {
            clickSearchBar()
        }
        SearchView().run {
            typeIntoSearchBar(searchQuery)
            clickSearchResultItemWithText(searchQuery)
        }
        ArticleView().run {
            articleViewShouldBeDisplayed()
        }
    }

    @Test
    fun searchNotExistingArticle() {
        val notExistingArticle = "articlenotexisting"

        OnboardingView().run {
            skipOnboarding()
        }
        MainView().run {
            clickSearchBar()
        }
        SearchView().run {
            typeIntoSearchBar(notExistingArticle)
            resultsListShouldBeEmpty()
        }
    }

    @Test
    fun changeLanguageInSearch() {
        val language = "Русский"
        val languageAbbreviation = "RU"
        val searchQuery = "Обама, Барак"

        OnboardingView().run {
            skipOnboarding()
        }
        MainView().run {
            clickSearchBar()
        }
        SearchView().run {
            addSearchLanguage(language)
            putIntoSearchBar(searchQuery)
            changeSearchLanguage(languageAbbreviation)
            clickSearchResultItemWithText(searchQuery)
        }
        ArticleView().run {
            articleViewShouldBeDisplayed()
        }
    }

    @Test
    fun searchHistoryShouldBeVisible() {
        val firstSearchQuery = "Barack Obama"
        val secondSearchQuery = "Joe Biden"

        OnboardingView().run {
            skipOnboarding()
        }
        MainView().run {
            clickSearchBar()
        }
        SearchView().run {
            typeIntoSearchBar(firstSearchQuery)
            clickSearchResultItemWithText(firstSearchQuery)
        }
        BaseView().pressBack()
        SearchView().run {
            typeIntoSearchBar(secondSearchQuery)
            clickSearchResultItemWithText(secondSearchQuery)
        }
        BaseView().pressBack()
        SearchView().run {
            clearSearchBar()
            searchHistoryShouldContainItems(firstSearchQuery, secondSearchQuery)
        }
    }
}