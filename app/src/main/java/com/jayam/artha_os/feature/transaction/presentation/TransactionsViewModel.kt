package com.jayam.artha_os.feature.transaction.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayam.artha_os.core.database.local.helper.TransactionType
import com.jayam.artha_os.core.ui.ui_utils.UiState
import com.jayam.artha_os.feature.transaction.domain.repo.TransactionRepository
import com.jayam.artha_os.feature.transaction.presentation.screens.TransactionsUiState
import com.jayam.artha_os.feature.ui_models.TransactionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    private val filter = MutableStateFlow("All")
    private val query = MutableStateFlow("")
    private val reloadTrigger = MutableStateFlow(0)

    private val _state = MutableStateFlow(TransactionsUiState())
    val state = _state.asStateFlow()

    init {
        combine(filter, query, reloadTrigger) { f, q, _ -> f to q }
            .distinctUntilChanged()
            .flatMapLatest { (f, q) ->
                when {
                    q.isNotBlank() -> repository.search(q)
                    f == "All" -> repository.getAllTransactions()
                    else -> repository.getByCategory(f)
                }
            }
            .onEach { transactions ->
                _state.value = _state.value.copy(
                    transactions = UiState.Success(
                        transactions.map {
                            TransactionItem(
                                id = it.id.toString(),
                                name = it.merchantName ?: it.description,
                                amount = it.amount,
                                isCredit = it.type == TransactionType.CREDIT,
                                category = it.category ?: "Uncategorized"
                            )
                        }
                    ),
                    selectedFilter = filter.value,
                    searchQuery = query.value
                )
            }
            .catch { e -> _state.value = _state.value.copy(transactions = UiState.Error(e.message ?: "Failed to load transactions")) }
            .launchIn(viewModelScope)
    }

    fun onFilterSelected(f: String) { filter.value = f }
    fun onSearchQueryChange(q: String) { query.value = q }
    fun retry() { reloadTrigger.value++ }
}