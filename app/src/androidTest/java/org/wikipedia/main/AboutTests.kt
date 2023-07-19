package org.wikipedia.main

import android.content.Intent.ACTION_CHOOSER
import android.content.Intent.ACTION_SENDTO
import android.content.Intent.ACTION_VIEW
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.wikipedia.BaseTest
import org.wikipedia.pageobjects.AboutPage
import org.wikipedia.pageobjects.MainPage
import org.wikipedia.pageobjects.SettingsPage

class AboutTests : BaseTest() {
    private val mainPage = MainPage()
    private val settingsPage = SettingsPage()
    private val aboutPage = AboutPage()

    @Before
    fun startActivity() {
        launchMainActivityWithDefaultPreferences()
        mockIntentsWithActions(ACTION_CHOOSER, ACTION_SENDTO, ACTION_VIEW)
    }

    @Test
    fun allSectionsInAboutAreDisplayed() {
        mainPage.run {
            clickMoreBottomBarButton()
            clickSettingsBottomBarButton()
        }
        settingsPage.clickAbout()

        aboutPage.waitUntilAboutPageIsDisplayed()
        assertTrue("Not all sections are properly displayed", aboutPage.areAllSectionsDisplayed())
    }

    @Test
    fun allSectionsInAboutAreDisplayedAfterPhoneOrientationChange() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        mainPage.run {
            clickMoreBottomBarButton()
            clickSettingsBottomBarButton()
        }
        settingsPage.clickAbout()

        device.setOrientationRight()

        aboutPage.waitUntilAboutPageIsDisplayed()
        assertTrue("Not all sections are properly displayed", aboutPage.areAllSectionsDisplayed())

        device.setOrientationNatural()

        aboutPage.waitUntilAboutPageIsDisplayed()
        assertTrue("Not all sections are properly displayed", aboutPage.areAllSectionsDisplayed())
    }

    @Test
    fun sendFeedbackButtonSendsCorrectIntent() {
        mainPage.run {
            clickMoreBottomBarButton()
            clickSettingsBottomBarButton()
        }
        settingsPage.clickAbout()

        aboutPage.clickSendAppFeedbackButton()
        assertTrue(
            "Intent not send after Send feedback button click",
            aboutPage.hasIntentActionAndData(ACTION_SENDTO, "mailto:")
        )
    }

    @Test
    fun faqLinkInAboutSectionOfSettingsSendsCorrectIntent() {
        mainPage.run {
            clickMoreBottomBarButton()
            clickSettingsBottomBarButton()
        }
        settingsPage.clickWikipediaAppFaqLink()
        assertTrue(
            "Intent not send after FAQ link click",
            settingsPage.hasIntentActionAndData(ACTION_VIEW, "https://m.mediawiki.org/")
        )
    }

    @Test
    fun privacyPolicyLinkInAboutSectionOfSettingsSendsCorrectIntent() {
        mainPage.run {
            clickMoreBottomBarButton()
            clickSettingsBottomBarButton()
        }
        settingsPage.clickPrivacyPolicyLink()
        assertTrue(
            "Intent not send after Privacy policy link click",
            settingsPage.hasIntentActionAndData(ACTION_VIEW, "https://foundation.wikimedia.org/")
        )
    }

    @Test
    fun termsOfUseLinkInAboutSectionOfSettingsSendsCorrectIntent() {
        mainPage.run {
            clickMoreBottomBarButton()
            clickSettingsBottomBarButton()
        }
        settingsPage.clickTermsOfUseLink()
        assertTrue(
            "Intent not send after Terms of use link click",
            settingsPage.hasIntentActionAndData(ACTION_VIEW, "https://foundation.wikimedia.org/")
        )
    }

    @Test
    fun donateButtonSendsCorrectIntent() {
        mainPage.run {
            clickMoreBottomBarButton()
            clickDonateBottomBarButton()
        }
        assertTrue(
            "Intent not send after Donate button click",
            mainPage.hasIntentActionAndExtraIntent(
                action = ACTION_CHOOSER,
                extraIntentAction = ACTION_VIEW,
                extraIntentDataString = "https://donate.wikimedia.org/"
            )
        )
    }
}