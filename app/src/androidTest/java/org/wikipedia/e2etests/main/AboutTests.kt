package org.wikipedia.e2etests.main

import android.content.Intent.ACTION_CHOOSER
import android.content.Intent.ACTION_SENDTO
import android.content.Intent.ACTION_VIEW
import androidx.test.espresso.intent.Intents
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.wikipedia.e2etests.BaseTest
import org.wikipedia.pageobjects.AboutPage
import org.wikipedia.pageobjects.MainPage
import org.wikipedia.pageobjects.SettingsPage
import org.wikipedia.testdata.LinkPaths.Companion.DONATE_PATH
import org.wikipedia.testdata.LinkPaths.Companion.FAQ_PATH
import org.wikipedia.testdata.LinkPaths.Companion.PRIVACY_POLICY_PATH
import org.wikipedia.testdata.LinkPaths.Companion.SEND_FEEDBACK_PATH
import org.wikipedia.testdata.LinkPaths.Companion.TERMS_OF_USE_PATH
import org.wikipedia.testutils.hasIntentActionAndData
import org.wikipedia.testutils.hasIntentActionAndExtraIntent

class AboutTests : BaseTest() {
    private val mainPage = MainPage()
    private val settingsPage = SettingsPage()
    private val aboutPage = AboutPage()

    @Before
    fun startActivity() {
        launchMainActivityWithDefaultPreferences()
        Intents.init()
        mockOutsideIntents()
    }

    @After
    fun cleanUp() {
        Intents.release()
    }

    @Test
    fun allSectionsInAboutAreDisplayed() {
        mainPage.run {
            tapMoreBottomBarButton()
            tapSettingsBottomBarButton()
        }
        settingsPage.tapAbout()

        aboutPage.waitUntilAboutPageIsDisplayed()
        assertTrue("Not all sections are properly displayed", aboutPage.areAllSectionsDisplayed())
    }

    @Test
    fun allSectionsInAboutAreDisplayedAfterPhoneOrientationChange() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        mainPage.run {
            tapMoreBottomBarButton()
            tapSettingsBottomBarButton()
        }
        settingsPage.tapAbout()

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
            tapMoreBottomBarButton()
            tapSettingsBottomBarButton()
        }
        settingsPage.tapAbout()

        aboutPage.tapSendAppFeedbackButton()
        assertTrue(
            "Intent not send after Send feedback button click",
            hasIntentActionAndData(ACTION_SENDTO, SEND_FEEDBACK_PATH)
        )
    }

    @Test
    fun faqLinkInAboutSectionOfSettingsSendsCorrectIntent() {
        mainPage.run {
            tapMoreBottomBarButton()
            tapSettingsBottomBarButton()
        }
        settingsPage.tapWikipediaAppFaqLink()
        assertTrue(
            "Intent not send after FAQ link click",
            hasIntentActionAndData(ACTION_VIEW, FAQ_PATH)
        )
    }

    @Test
    fun privacyPolicyLinkInAboutSectionOfSettingsSendsCorrectIntent() {
        mainPage.run {
            tapMoreBottomBarButton()
            tapSettingsBottomBarButton()
        }
        settingsPage.tapPrivacyPolicyLink()
        assertTrue(
            "Intent not send after Privacy policy link click",
            hasIntentActionAndData(ACTION_VIEW, PRIVACY_POLICY_PATH)
        )
    }

    @Test
    fun termsOfUseLinkInAboutSectionOfSettingsSendsCorrectIntent() {
        mainPage.run {
            tapMoreBottomBarButton()
            tapSettingsBottomBarButton()
        }
        settingsPage.tapTermsOfUseLink()
        assertTrue(
            "Intent not send after Terms of use link click",
            hasIntentActionAndData(ACTION_VIEW, TERMS_OF_USE_PATH)
        )
    }

    @Test
    fun donateButtonSendsCorrectIntent() {
        mainPage.run {
            tapMoreBottomBarButton()
            tapDonateBottomBarButton()
        }
        assertTrue(
            "Intent not send after Donate button click",
            hasIntentActionAndExtraIntent(
                action = ACTION_CHOOSER,
                extraIntentAction = ACTION_VIEW,
                extraIntentDataString = DONATE_PATH
            )
        )
    }
}