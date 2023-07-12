package org.wikipedia.pageobjects

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher
import org.wikipedia.TestUtil
import java.lang.IllegalStateException

open class BasePage {
    fun pressBack() {
        Espresso.pressBack()
    }

    protected fun getText(matcher: Matcher<View>): String {
        var text = String()
        onView(matcher).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(TextView::class.java)
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
        onView(view).perform(ViewActions.click())
    }

    protected fun waitFor(viewToMatch: Matcher<View>) {
        val timeoutInMilliseconds = 2000L
        val pollInterval = 250L
        var counter = 0L
        do {
            try {
                onView(viewToMatch)
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                return
            } catch (_: NoMatchingViewException) {
                onView(ViewMatchers.isRoot()).perform(TestUtil.waitOnId(pollInterval))
                counter += 250
            }
        } while (counter < timeoutInMilliseconds)
        throw IllegalStateException("No view found in specified time: $timeoutInMilliseconds")
    }

    protected fun isViewDisplayed(matcher: Matcher<View>): Boolean {
        try {
            onView(matcher).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        } catch (e: NoMatchingViewException) {
            return false
        }
        return true
    }
}