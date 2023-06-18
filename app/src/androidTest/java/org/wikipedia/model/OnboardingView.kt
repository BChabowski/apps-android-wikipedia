package org.wikipedia.model

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers
import org.wikipedia.R

class OnboardingView: BaseView() {
    private val skipButtonId = R.id.fragment_onboarding_skip_button

    fun skipOnboarding(): MainView {
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(skipButtonId),
                ViewMatchers.isDisplayed()
            )
        )
            .perform(ViewActions.click())
        return MainView()
    }
}