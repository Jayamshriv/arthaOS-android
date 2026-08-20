package com.jayam.artha_os.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayam.artha_os.feature.profile.domain.ProfileRepository
import com.jayam.artha_os.feature.sms.domain.SmsInfo
import com.jayam.artha_os.feature.sms.domain.SmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
     private val smsRepository: SmsRepository,
     private val profileRepository: ProfileRepository
) : ViewModel(){
    var allSms = MutableStateFlow<List<SmsInfo>>(emptyList())
        private set

    init{
        viewModelScope.launch {
            smsRepository.getAll().collect {
                allSms.value = it
            }
        }
    }
}