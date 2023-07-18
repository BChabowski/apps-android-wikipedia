package org.wikipedia.pageobjects

import android.content.Intent.ACTION_SENDTO
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasDataString
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Matcher
import org.wikipedia.R

class AboutPage : BasePage() {
    private val aboutPageScrollView = withId(R.id.about_scrollview)
    private val versionNumberMatcher = withId(R.id.about_version_text)
    private val aboutContributorsSectionMatcher = withId(R.id.about_contributors)
    private val aboutTranslatorsSectionMatcher = withId(R.id.about_translators)
    private val aboutLibrariesSectionMatcher = withId(R.id.activity_about_libraries)
    private val aboutAppLicenseSectionMatcher = withId(R.id.about_app_license)
    private val sendAppFeedbackButtonMatcher = withId(R.id.send_feedback_text)

    fun clickSendAppFeedbackButton() {
        onView(sendAppFeedbackButtonMatcher).perform(click())
    }

    fun areAllSectionsDisplayed(): Boolean {
        return isSectionDisplayed(aboutContributorsSectionMatcher)
                && isSectionDisplayed(aboutTranslatorsSectionMatcher)
                && isSectionDisplayed(aboutLibrariesSectionMatcher)
                && isSectionDisplayed(aboutAppLicenseSectionMatcher)
                && isSectionDisplayed(versionNumberMatcher)
    }

    fun waitUntilAboutPageIsDisplayed() {
        waitFor(aboutPageScrollView)
    }

    private fun isSectionDisplayed(matcher: Matcher<View>): Boolean {
        onView(matcher).perform(scrollTo())
        return isViewDisplayed(matcher)
    }
}