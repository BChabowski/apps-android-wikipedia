package org.wikipedia.model

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers
import org.wikipedia.R

class OnboardingView: BaseView() {
    private val skipButtonMatcher = Matchers.allOf(
        ViewMatchers.withId(R.id.fragment_onboarding_skip_button), ViewMatchers.isDisplayed())

    fun skipOnboarding() {
        Espresso.onView(skipButtonMatcher)
            .perform(ViewActions.click())
    }
}