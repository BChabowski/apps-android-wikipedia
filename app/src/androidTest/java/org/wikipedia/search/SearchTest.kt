package org.wikipedia.search

import org.junit.Test
import org.wikipedia.BaseTest
import org.wikipedia.model.ArticleView
import org.wikipedia.model.BaseView
import org.wikipedia.model.MainView
import org.wikipedia.model.OnboardingView
import org.wikipedia.model.SearchView

class SearchTest : BaseTest() {
    //todo getText() do BaseView - może poprawić samą funkcję
    //todo dopisać metodę na pobieranie tekstu z recyclerview
    //todo mądre waity
    //todo pomijanie onboardingu za pomocą intenta/shared preferences


    @Test
    fun searchAndDisplayArticle() {
        OnboardingView().run {
            skipOnboarding()
        }
        MainView().run {
            clickSearchBar()
        }
        SearchView().run {
            typeIntoSearchBar("Barack Obama")
            searchResultsShouldContainItems("Barack Obama")
            clickOnNthResult(0)
        }
        ArticleView().run {
            articleViewShouldBeDisplayed()
        }
    }

    @Test
    fun searchNotExistingArticle() {
        OnboardingView().run {
            skipOnboarding()
        }
        MainView().run {
            clickSearchBar()
        }
        SearchView().run {
            typeIntoSearchBar("articlenotexisting")
            resultsListShouldBeEmpty()
        }
    }

    @Test
    fun changeLanguageInSearch() {
        val language = "Русский"
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
            changeSearchLanguage("RU")
            searchResultsShouldContainItems(searchQuery)
            clickOnNthResult(0)
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
            clickOnNthResult(0)
        }
        BaseView().pressBack()
        SearchView().run {
            typeIntoSearchBar(secondSearchQuery)
            clickOnNthResult(0)
        }
        BaseView().pressBack()
        SearchView().run {
            clearSearchBar()
            searchHistoryShouldContainItems(firstSearchQuery, secondSearchQuery)
        }
    }
}