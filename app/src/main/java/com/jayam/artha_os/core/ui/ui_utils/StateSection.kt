package com.jayam.artha_os.core.ui.ui_utils

import androidx.compose.runtime.Composable
import com.jayam.artha_os.core.ui.common_components.LoadingCard

@Composable
fun <T> StateSection(
    state: UiState<T>,
    onRetry: () -> Unit,
    successContent: @Composable (data: T, isRefreshing: Boolean) -> Unit
) {
    when (state) {
        is UiState.Loading -> LoadingCard()
        is UiState.Success -> successContent(state.data, state.isRefreshing)
        is UiState.Error -> {
//            val cached = state.cachedData
//            if (cached != null) {
//                Column {
//                    successContent(cached, false)
//                    Spacer(Modifier.height(8.dp))
//                    ErrorBanner(state.message, onRetry)
//                }
//            } else {
                 ErrorCard(state.message, onRetry)
//            }
        }
    }
}