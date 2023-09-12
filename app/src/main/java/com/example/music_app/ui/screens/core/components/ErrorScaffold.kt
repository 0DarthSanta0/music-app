package com.example.music_app.ui.screens.core.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.music_app.AppErrors
import com.example.music_app.R
import com.example.music_app.ui.screens.core.toUIStringRes
import com.example.music_app.ui.theme.AppTheme

/**
 * Composable function providing a scaffold structure for error-handling screens.
 *
 * This function is intended for use in Composable components and offers a common structure
 * for screens that can display error messages with the option to retry actions.
 *
 * @param modifier the [Modifier] to be applied to this scaffold
 * @param topBar top app bar of the screen, typically a [SmallTopAppBar]
 * @param bottomBar bottom bar of the screen, typically a [NavigationBar]
 * @param floatingActionButton Main action button of the screen, typically a [FloatingActionButton]
 * @param floatingActionButtonPosition position of the FAB on the screen. See [FabPosition].
 * @param containerColor the color used for the background of this scaffold. Use [Color.Transparent]
 * to have no color.
 * @param contentColor the preferred color for content inside this scaffold. Defaults to either the
 * matching content color for [containerColor], or to the current [LocalContentColor] if
 * [containerColor] is not a color from the theme.
 * @param contentWindowInsets window insets to be passed to [content] slot via [PaddingValues]
 * params. Scaffold will take the insets into account from the top/bottom only if the [topBar]/
 * [bottomBar] are not present, as the scaffold expect [topBar]/[bottomBar] to handle insets
 * instead
 * @param error Error object that can be displayed on the screen.
 * @param snackbarHostState Snackbar host state for controlling the display of Snackbar.
 * @param onClick Action to be executed when the "Retry" button in the Snackbar is clicked.
 * @param content content of the screen. The lambda receives a [PaddingValues] that should be
 * applied to the content root via [Modifier.padding] and [Modifier.consumeWindowInsets] to
 * properly offset top and bottom bars. If using [Modifier.verticalScroll], apply this modifier to
 * the child of the scroll, and not on the scroll itself.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    error: AppErrors?,
    snackbarHostState: SnackbarHostState,
    onClick: () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    LaunchedEffect(error) {
        if (error != null) {
            snackbarHostState.showSnackbar(
                message = "",
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Snackbar(
                    action = {
                        Button(
                            onClick = { onClick() },
                            modifier = Modifier.padding(AppTheme.dimens.spacing10)
                        ) {
                            Text(text = stringResource(id = R.string.repeat))
                        }
                    },
                    modifier = Modifier.padding(AppTheme.dimens.spacing20)
                ) {
                    Text(text = stringResource(id = error?.toUIStringRes() ?: R.string.error))
                }
            }
        },
    ) { paddingValues ->
        content(paddingValues)
    }
}
