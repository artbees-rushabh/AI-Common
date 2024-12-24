package albums.pictures.ai.commons.compose.extensions

import android.app.Activity
import android.content.Context
import albums.pictures.ai.commons.R
import albums.pictures.ai.commons.extensions.baseConfig
import albums.pictures.ai.commons.extensions.redirectToRateUs
import albums.pictures.ai.commons.extensions.toast
import albums.pictures.ai.commons.helpers.BaseConfig

val Context.config: BaseConfig get() = BaseConfig.newInstance(applicationContext)

fun Activity.rateStarsRedirectAndThankYou(stars: Int) {
    if (stars == 5) {
        redirectToRateUs()
    }
    toast(R.string.thank_you)
    baseConfig.wasAppRated = true
}
