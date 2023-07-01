package org.wikipedia.model

import androidx.test.espresso.Espresso

open class BaseView {
    fun pressBack() {
        Espresso.pressBack()
    }
}