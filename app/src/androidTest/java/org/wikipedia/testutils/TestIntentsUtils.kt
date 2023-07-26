package org.wikipedia.testutils

import android.content.Intent
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import junit.framework.AssertionFailedError
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.hamcrest.Matchers

fun hasIntentActionAndData(action: String, dataString: String): Boolean {
    return isIntentPresent(
        IntentMatchers.hasAction(action),
        IntentMatchers.hasDataString(CoreMatchers.containsString(dataString))
    )
}

fun hasIntentActionAndExtraIntent(
    action: String,
    extraIntentAction: String,
    extraIntentDataString: String
): Boolean {
    val extraIntent = Matchers.allOf(
        IntentMatchers.hasAction(extraIntentAction), IntentMatchers.hasDataString(
            CoreMatchers.containsString(extraIntentDataString)
        )
    )
    return isIntentPresent(
        IntentMatchers.hasAction(action),
        IntentMatchers.hasExtra(Intent.EXTRA_INTENT, extraIntent)
    )
}

private fun isIntentPresent(vararg intentMatchers: Matcher<Intent>): Boolean {
    try {
        Intents.intended(CoreMatchers.allOf(intentMatchers.asIterable()))
    } catch (a: AssertionFailedError) {
        return false
    }
    return true
}