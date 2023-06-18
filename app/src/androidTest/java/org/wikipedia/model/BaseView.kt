package org.wikipedia.model

import androidx.test.espresso.Espresso

open class BaseView {
    fun pressBackToSearchView(): SearchView {
        pressBack()
        return SearchView()
    }

    private fun pressBack() {
        Espresso.pressBack()
    }
}