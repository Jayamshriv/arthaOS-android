package com.jayam.artha_os.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayam.artha_os.feature.profile.domain.ProfileRepository
import com.jayam.artha_os.feature.profile.domain.UserProfile
import com.jayam.artha_os.feature.sms.domain.SmsInfo
import com.jayam.artha_os.feature.sms.domain.SmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val smsRepository: SmsRepository,
) : ViewModel() {
    init {
        viewModelScope.launch {
            smsRepository.getAll().collect {
                allSms.value = it
            }
        }
    }
    var allSms = MutableStateFlow<List<SmsInfo>>(emptyList())
        private set
    val state = repository.getProfile()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun saveProfile(profile: UserProfile) {
        viewModelScope.launch { repository.saveProfile(profile) }
    }

    fun toggleSmsAutoCapture(enabled: Boolean) {
        viewModelScope.launch { repository.setSmsAutoCapture(enabled) }
    }
}