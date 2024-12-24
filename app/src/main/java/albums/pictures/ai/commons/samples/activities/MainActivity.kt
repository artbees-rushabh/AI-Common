package albums.pictures.ai.commons.samples.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import albums.pictures.ai.commons.activities.AboutActivity
import albums.pictures.ai.commons.activities.CustomizationActivity
import albums.pictures.ai.commons.activities.ManageBlockedNumbersActivity
import albums.pictures.ai.commons.compose.alert_dialog.AlertDialogState
import albums.pictures.ai.commons.compose.alert_dialog.rememberAlertDialogState
import albums.pictures.ai.commons.compose.extensions.*
import albums.pictures.ai.commons.compose.theme.AppThemeSurface
import albums.pictures.ai.commons.dialogs.ConfirmationDialog
import albums.pictures.ai.commons.dialogs.DonateAlertDialog
import albums.pictures.ai.commons.dialogs.RateStarsAlertDialog
import albums.pictures.ai.commons.extensions.hideKeyboard
import albums.pictures.ai.commons.extensions.launchMoreAppsFromUsIntent
import albums.pictures.ai.commons.extensions.launchViewIntent
import albums.pictures.ai.commons.helpers.*
import albums.pictures.ai.commons.models.FAQItem
import albums.pictures.ai.commons.BuildConfig
import albums.pictures.ai.commons.R
import albums.pictures.ai.commons.samples.screens.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdgeSimple()
        setContent {
            AppThemeSurface {
                val showMoreApps = onEventValue { !resources.getBoolean(albums.pictures.ai.commons.R.bool.hide_google_relations) }

                MainScreen(
                    openColorCustomization = ::startCustomizationActivity,
                    manageBlockedNumbers = {
                        startActivity(Intent(this@MainActivity, ManageBlockedNumbersActivity::class.java))
                    },
                    showComposeDialogs = {
                        startActivity(Intent(this@MainActivity, TestDialogActivity::class.java))
                    },
                    openTestButton = {
                        ConfirmationDialog(
                            this@MainActivity,
                            FAKE_VERSION_APP_LABEL,
                            positive = albums.pictures.ai.commons.R.string.ok,
                            negative = 0
                        ) {
                            launchViewIntent(DEVELOPER_PLAY_STORE_URL)
                        }
                    },
                    showMoreApps = showMoreApps,
                    openAbout = ::launchAbout,
                    moreAppsFromUs = ::launchMoreAppsFromUsIntent
                )
                AppLaunched()
            }
        }
    }

    @Composable
    private fun AppLaunched(
        donateAlertDialogState: AlertDialogState = getDonateAlertDialogState(),
        rateStarsAlertDialogState: AlertDialogState = getRateStarsAlertDialogState(),
    ) {
        LaunchedEffect(Unit) {
            appLaunchedCompose(
                appId = BuildConfig.APPLICATION_ID,
                showDonateDialog = donateAlertDialogState::show,
                showRateUsDialog = rateStarsAlertDialogState::show,
                showUpgradeDialog = {}
            )
        }
    }

    @Composable
    private fun getDonateAlertDialogState() =
        rememberAlertDialogState().apply {
            DialogMember {
                DonateAlertDialog(alertDialogState = this)
            }
        }

    @Composable
    private fun getRateStarsAlertDialogState() = rememberAlertDialogState().apply {
        DialogMember {
            RateStarsAlertDialog(alertDialogState = this, onRating = ::rateStarsRedirectAndThankYou)
        }
    }

    private fun startCustomizationActivity() {
        Intent(applicationContext, CustomizationActivity::class.java).apply {
            putExtra(APP_ICON_IDS, getAppIconIDs())
            putExtra(APP_LAUNCHER_NAME, getAppLauncherName())
            startActivity(this)
        }
    }

    private fun launchAbout() {
        val licenses = LICENSE_AUTOFITTEXTVIEW

        val faqItems = arrayListOf(
            FAQItem(albums.pictures.ai.commons.R.string.faq_1_title_commons, albums.pictures.ai.commons.R.string.faq_1_text_commons),
            FAQItem(albums.pictures.ai.commons.R.string.faq_1_title_commons, albums.pictures.ai.commons.R.string.faq_1_text_commons),
            FAQItem(albums.pictures.ai.commons.R.string.faq_4_title_commons, albums.pictures.ai.commons.R.string.faq_4_text_commons)
        )

        if (!resources.getBoolean(albums.pictures.ai.commons.R.bool.hide_google_relations)) {
            faqItems.add(FAQItem(albums.pictures.ai.commons.R.string.faq_2_title_commons, albums.pictures.ai.commons.R.string.faq_2_text_commons))
            faqItems.add(FAQItem(albums.pictures.ai.commons.R.string.faq_6_title_commons, albums.pictures.ai.commons.R.string.faq_6_text_commons))
        }

        startAboutActivity(R.string.smtco_app_name, licenses, BuildConfig.VERSION_NAME, faqItems, true)
    }

    private fun startAboutActivity(
        appNameId: Int, licenseMask: Long, versionName: String, faqItems: ArrayList<FAQItem>, showFAQBeforeMail: Boolean,
        getAppIconIDs: ArrayList<Int> = getAppIconIDs(),
        getAppLauncherName: String = getAppLauncherName()
    ) {
        hideKeyboard()
        Intent(applicationContext, AboutActivity::class.java).apply {
            putExtra(APP_ICON_IDS, getAppIconIDs)
            putExtra(APP_LAUNCHER_NAME, getAppLauncherName)
            putExtra(APP_NAME, getString(appNameId))
            putExtra(APP_LICENSES, licenseMask)
            putExtra(APP_VERSION_NAME, versionName)
            putExtra(APP_FAQ, faqItems)
            putExtra(SHOW_FAQ_BEFORE_MAIL, showFAQBeforeMail)
            startActivity(this)
        }
    }

    private fun getAppLauncherName() = getString(R.string.smtco_app_name)

    private fun getAppIconIDs(): ArrayList<Int> {
        val ids = ArrayList<Int>()
        ids.add(R.mipmap.commons_launcher)
        return ids
    }
}
