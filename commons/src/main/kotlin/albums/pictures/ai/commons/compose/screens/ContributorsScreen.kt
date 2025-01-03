package albums.pictures.ai.commons.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import albums.pictures.ai.commons.R
import albums.pictures.ai.commons.compose.components.LinkifyTextComponent
import albums.pictures.ai.commons.compose.extensions.MyDevices
import albums.pictures.ai.commons.compose.lists.SimpleLazyListScaffold
import albums.pictures.ai.commons.compose.settings.SettingsGroupTitle
import albums.pictures.ai.commons.compose.settings.SettingsHorizontalDivider
import albums.pictures.ai.commons.compose.settings.SettingsListItem
import albums.pictures.ai.commons.compose.settings.SettingsTitleTextComponent
import albums.pictures.ai.commons.compose.theme.AppThemeSurface
import albums.pictures.ai.commons.compose.theme.SimpleTheme
import albums.pictures.ai.commons.extensions.fromHtml
import albums.pictures.ai.commons.models.LanguageContributor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

private val startingPadding = Modifier.padding(start = 58.dp)

@Composable
internal fun ContributorsScreen(
    goBack: () -> Unit,
    showContributorsLabel: Boolean,
    contributors: ImmutableList<LanguageContributor>
) {
    SimpleLazyListScaffold(
        title = { scrolledColor ->
            Text(
                text = stringResource(id = R.string.contributors),
                modifier = Modifier
                    .padding(start = 28.dp)
                    .fillMaxWidth(),
                color = scrolledColor,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        goBack = goBack
    ) {
        item {
            SettingsGroupTitle {
                SettingsTitleTextComponent(text = stringResource(id = R.string.development), modifier = startingPadding)
            }
        }
        item {
            SettingsListItem(
                text = stringResource(id = R.string.contributors_developers),
                icon = R.drawable.ic_code_vector,
                tint = SimpleTheme.colorScheme.onSurface,
                fontSize = 14.sp
            )
        }
        item {
            Spacer(modifier = Modifier.padding(vertical = SimpleTheme.dimens.padding.medium))
        }
        item {
            SettingsHorizontalDivider()
        }
        item {
            SettingsGroupTitle {
                SettingsTitleTextComponent(text = stringResource(id = R.string.translation), modifier = startingPadding)
            }
        }
        items(contributors, key = { it.contributorsId.plus(it.iconId).plus(it.labelId) }) {
            ContributorItem(
                languageContributor = it
            )
        }
        if (showContributorsLabel) {
            item {
                SettingsListItem(
                    icon = R.drawable.ic_heart_vector,
                    text = {
                        val source = stringResource(id = R.string.contributors_label)
                        LinkifyTextComponent {
                            source.fromHtml()
                        }
                    },
                    tint = SimpleTheme.colorScheme.onSurface
                )
            }
            item {
                Spacer(modifier = Modifier.padding(bottom = SimpleTheme.dimens.padding.medium))
            }
        }
       
    }
}

@Composable
private fun ContributorItem(
    modifier: Modifier = Modifier,
    languageContributor: LanguageContributor
) {
    ListItem(
        headlineContent = {
            Text(
                text = stringResource(id = languageContributor.labelId),
                modifier = Modifier
                    .fillMaxWidth()
                    .then(modifier)
            )
        },
        leadingContent = {
            val imageSize = Modifier
                .size(SimpleTheme.dimens.icon.medium)
                .padding(SimpleTheme.dimens.padding.medium)
            Image(
                modifier = imageSize,
                painter = painterResource(id = languageContributor.iconId),
                contentDescription = stringResource(id = languageContributor.contributorsId),
            )
        },
        modifier = Modifier
            .fillMaxWidth(),
        supportingContent = {
            Text(
                text = stringResource(id = languageContributor.contributorsId),
                modifier = Modifier
                    .fillMaxWidth(),
                color = SimpleTheme.colorScheme.onSurface
            )
        }
    )
}

@Composable
@MyDevices
private fun ContributorsScreenPreview() {
    AppThemeSurface {
        ContributorsScreen(
            goBack = {},
            contributors = listOf(
                LanguageContributor(R.drawable.ic_flag_arabic_vector, R.string.translation_arabic, R.string.translators_arabic),
                LanguageContributor(R.drawable.ic_flag_azerbaijani_vector, R.string.translation_azerbaijani, R.string.translators_azerbaijani),
                LanguageContributor(R.drawable.ic_flag_bengali_vector, R.string.translation_bengali, R.string.translators_bengali),
                LanguageContributor(R.drawable.ic_flag_catalan_vector, R.string.translation_catalan, R.string.translators_catalan),
            ).toImmutableList(),
            showContributorsLabel = true,
        )
    }
}
