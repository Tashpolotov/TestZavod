package com.example.testzavod.presentation.fragments.profile

import androidx.lifecycle.viewModelScope
import com.example.testzavod.domain.model.profile.AnswerChange
import com.example.testzavod.domain.model.profile.ChangeData
import com.example.testzavod.domain.model.profile.ProfileModel
import com.example.testzavod.domain.usecase.ProfileUseCase
import com.example.testzavod.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekstudio.core_utils.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: ProfileUseCase
):BaseViewModel() {

    private val _profile = MutableStateFlow<Resource<ProfileModel>>(Resource.Empty())
    val profile = _profile.asStateFlow()

    private val _change = MutableStateFlow<Resource<AnswerChange>>(Resource.Empty())
    val change = _change.asStateFlow()

    fun getProfile() {
        viewModelScope.launch {
          useCase.getProfile().collectData(_profile)
        }
    }

    fun sendChange(changeData: ChangeData){
        viewModelScope.launch {
            useCase.sendChange(changeData).collectData(_change)
        }
    }
}