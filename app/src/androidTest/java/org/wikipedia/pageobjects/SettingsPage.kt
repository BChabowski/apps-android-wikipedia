package org.wikipedia.pageobjects

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.wikipedia.R

class SettingsPage : BasePage() {
    private val settingsListMatcher = allOf(
        withId(R.id.recycler_view),
        isDisplayed()
    )
    private val aboutButtonMatcher = matchSettingsRecyclerViewItemByText("About the Wikipedia app")
    private val wikipediaFaqLinkMatcher = matchSettingsRecyclerViewItemByText("Wikipedia App FAQ")
    private val privacyPolicyLinkMatcher = matchSettingsRecyclerViewItemByText("Privacy policy")
    private val termsOfUseLinkMatcher = matchSettingsRecyclerViewItemByText("Terms of use")

    fun clickAbout() {
        onView(settingsListMatcher).perform(RecyclerViewActions.scrollToLastPosition<ViewHolder>())
        onView(aboutButtonMatcher).perform(click())
    }

    fun clickWikipediaAppFaqLink() {
        onView(settingsListMatcher).perform(RecyclerViewActions.scrollToLastPosition<ViewHolder>())
        onView(wikipediaFaqLinkMatcher).perform(click())
    }

    fun clickPrivacyPolicyLink() {
        onView(settingsListMatcher).perform(RecyclerViewActions.scrollToLastPosition<ViewHolder>())
        onView(privacyPolicyLinkMatcher).perform(click())
    }

    fun clickTermsOfUseLink() {
        onView(settingsListMatcher).perform(RecyclerViewActions.scrollToLastPosition<ViewHolder>())
        onView(termsOfUseLinkMatcher).perform(click())
    }

    private fun matchSettingsRecyclerViewItemByText(text: String): Matcher<View> {
        return allOf(
            withText(text),
            withParent(withParent(withParent(settingsListMatcher)))
        )
    }
}