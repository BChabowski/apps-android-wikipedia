package org.wikipedia.pageobjects

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import org.hamcrest.Matcher
import org.wikipedia.TestUtil
import org.wikipedia.testdata.DefaultTestTimeouts

open class BasePage {
    fun pressBack() {
        Espresso.pressBack()
    }

    protected fun getText(matcher: Matcher<View>): String {
        var text = String()
        onView(matcher).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "Text of the view"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                text = tv.text.toString()
            }
        })

        return text
    }

    protected fun clickWithWait(view: Matcher<View>) {
        waitFor(view)
        onView(view).perform(click())
    }

    protected fun waitFor(viewToMatch: Matcher<View>) {
        var counter = 0L
        do {
            try {
                onView(viewToMatch)
                    .check(matches(isDisplayed()))
                return
            } catch (_: NoMatchingViewException) {
                onView(isRoot()).perform(TestUtil.waitOnId(Long.MAX_VALUE))
                counter += DefaultTestTimeouts.DEFAULT_POLL_INTERVAL.value
            }
        } while (counter < DefaultTestTimeouts.DEFAULT_TIMEOUT_IN_MILLISECONDS.value)
        throw IllegalStateException("No view found in specified time: ${DefaultTestTimeouts.DEFAULT_TIMEOUT_IN_MILLISECONDS.value}")
    }

    protected fun isViewDisplayed(matcher: Matcher<View>): Boolean {
        try {
            onView(matcher).check(matches(isDisplayed()))
        } catch (e: NoMatchingViewException) {
            return false
        }
        return true
    }
}