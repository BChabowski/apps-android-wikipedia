package org.wikipedia.search

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import org.junit.Before
import org.junit.Test
import org.wikipedia.BaseTest
import org.wikipedia.main.MainActivity
import org.wikipedia.model.OnboardingView

class SearchTest : BaseTest() {
    var mIdlingResource: IdlingResource? = null

    @Before
    fun registerIdlingResources() {
        mActivityTestRule.scenario.onActivity {
            fun perform(activity: MainActivity) {
                mIdlingResource = activity.getIdlingResource()
                // To prove that the test fails, omit this call:
                IdlingRegistry.getInstance().register(mIdlingResource);
            }
        }
    }


    @Test
    fun searchAndDisplayArticle() {
        //ominąć jakoś skipOnboarding przekazywaniem np intenta?
        OnboardingView().skipOnboarding()
            .clickSearchBar()
            .typeIntoSearchBar("Barack Obama")
            .clickOnNthResult(0)
            .articleViewShouldBeDisplayed()
    }

    //search after picking search from bottom bar

    @Test
    fun searchNotExistingArticle() {
        OnboardingView().skipOnboarding()
            .clickSearchBar()
            .typeIntoSearchBar("articlenotexisting")
            .resultsListShouldBeEmpty()
    }
}