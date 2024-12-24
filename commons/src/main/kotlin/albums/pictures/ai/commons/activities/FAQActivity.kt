package albums.pictures.ai.commons.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import albums.pictures.ai.commons.compose.extensions.enableEdgeToEdgeSimple
import albums.pictures.ai.commons.compose.screens.FAQScreen
import albums.pictures.ai.commons.compose.theme.AppThemeSurface
import albums.pictures.ai.commons.helpers.APP_FAQ
import albums.pictures.ai.commons.models.FAQItem
import kotlinx.collections.immutable.toImmutableList

class FAQActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdgeSimple()
        setContent {
            AppThemeSurface {
                val faqItems = remember { intent.getSerializableExtra(APP_FAQ) as ArrayList<FAQItem> }
                FAQScreen(
                    goBack = ::finish,
                    faqItems = faqItems.toImmutableList()
                )
            }
        }
    }
}
