// Copyright 2020, Google LLC, Christopher Banes and the Tivi project contributors
// SPDX-License-Identifier: Apache-2.0

package app.tivi.home.trending

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.tivi.common.compose.EntryGrid
import app.tivi.common.compose.LocalStrings
import app.tivi.screens.TrendingShowsScreen
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

@Inject
class TrendingShowsUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is TrendingShowsScreen -> {
            ui<TrendingShowsUiState> { state, modifier ->
                TrendingShows(state, modifier)
            }
        }

        else -> null
    }
}

@Composable
internal fun TrendingShows(
    state: TrendingShowsUiState,
    modifier: Modifier = Modifier,
) {
    // Need to extract the eventSink out to a local val, so that the Compose Compiler
    // treats it as stable. See: https://issuetracker.google.com/issues/256100927
    val eventSink = state.eventSink

    EntryGrid(
        lazyPagingItems = state.items,
        title = LocalStrings.current.discoverTrendingTitle,
        onOpenShowDetails = { eventSink(TrendingShowsUiEvent.OpenShowDetails(it)) },
        onNavigateUp = { eventSink(TrendingShowsUiEvent.NavigateUp) },
        modifier = modifier,
    )
}
