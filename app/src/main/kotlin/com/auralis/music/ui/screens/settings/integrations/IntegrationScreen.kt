/**
 * Auralis Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.auralis.music.ui.screens.settings.integrations

import com.auralis.music.ui.utils.appTopBarWindowInsets
import com.auralis.music.ui.utils.appTopBarWindowInsets
import androidx.compose.foundation.layout.Column
import com.auralis.music.ui.utils.appTopBarWindowInsets
import androidx.compose.foundation.layout.padding
import com.auralis.music.ui.utils.appTopBarWindowInsets
import androidx.compose.foundation.layout.windowInsetsPadding
import com.auralis.music.ui.utils.appTopBarWindowInsets
import androidx.compose.foundation.rememberScrollState
import com.auralis.music.ui.utils.appTopBarWindowInsets
import androidx.compose.foundation.verticalScroll
import com.auralis.music.ui.utils.appTopBarWindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import com.auralis.music.ui.utils.appTopBarWindowInsets
import androidx.compose.material3.Icon
import com.auralis.music.ui.utils.appTopBarWindowInsets
import androidx.compose.material3.Text
import com.auralis.music.ui.utils.appTopBarWindowInsets
import androidx.compose.material3.TopAppBar
import com.auralis.music.ui.utils.appTopBarWindowInsets
import androidx.compose.material3.TopAppBarScrollBehavior
import com.auralis.music.ui.utils.appTopBarWindowInsets
import androidx.compose.runtime.Composable
import com.auralis.music.ui.utils.appTopBarWindowInsets
import androidx.compose.ui.Modifier
import com.auralis.music.ui.utils.appTopBarWindowInsets
import androidx.compose.ui.res.painterResource
import com.auralis.music.ui.utils.appTopBarWindowInsets
import androidx.compose.ui.res.stringResource
import com.auralis.music.ui.utils.appTopBarWindowInsets
import androidx.compose.ui.unit.dp
import com.auralis.music.ui.utils.appTopBarWindowInsets
import androidx.navigation.NavController
import com.auralis.music.ui.utils.appTopBarWindowInsets
import com.auralis.music.LocalPlayerAwareWindowInsets
import com.auralis.music.ui.utils.appTopBarWindowInsets
import com.auralis.music.R
import com.auralis.music.ui.utils.appTopBarWindowInsets
import com.auralis.music.ui.component.IconButton
import com.auralis.music.ui.utils.appTopBarWindowInsets
import com.auralis.music.ui.component.IntegrationCard
import com.auralis.music.ui.utils.appTopBarWindowInsets
import com.auralis.music.ui.component.IntegrationCardItem
import com.auralis.music.ui.utils.appTopBarWindowInsets
import com.auralis.music.ui.utils.backToMain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntegrationScreen(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    Column(
        Modifier
            .windowInsetsPadding(LocalPlayerAwareWindowInsets.current)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
    ) {
        IntegrationCard(
            title = stringResource(R.string.general),
            items = listOf(
                IntegrationCardItem(
                    icon = painterResource(R.drawable.discord),
                    title = { Text(stringResource(R.string.discord_integration)) },
                    onClick = {
                        navController.navigate("settings/integrations/discord")
                    }
                ),
                IntegrationCardItem(
                    icon = painterResource(R.drawable.music_note),
                    title = { Text(stringResource(R.string.lastfm_integration)) },
                    onClick = {
                        navController.navigate("settings/integrations/lastfm")
                    }
                )
            )
        )
    }

    TopAppBar(
            windowInsets = appTopBarWindowInsets(),
        title = { Text(stringResource(R.string.integrations)) },
        navigationIcon = {
            IconButton(
                onClick = navController::navigateUp,
                onLongClick = navController::backToMain,
            ) {
                Icon(
                    painterResource(R.drawable.arrow_back),
                    contentDescription = null,
                )
            }
        }
    )
}
