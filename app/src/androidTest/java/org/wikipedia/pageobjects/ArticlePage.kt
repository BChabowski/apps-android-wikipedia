package org.wikipedia.pageobjects

import androidx.test.espresso.matcher.ViewMatchers.withId
import org.wikipedia.R

class ArticlePage : BasePage() {
    private val articleWebViewMatcher = withId(R.id.page_web_view)

    fun isArticleViewDisplayed(): Boolean {
        return isViewDisplayed(articleWebViewMatcher)
    }
}