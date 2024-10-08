package com.example.testzavod.presentation.fragments.registration

import androidx.lifecycle.viewModelScope
import com.example.testzavod.domain.model.register.RegisterAnswer
import com.example.testzavod.domain.model.register.RegisterSend
import com.example.testzavod.domain.usecase.RegisterUseCase
import com.example.testzavod.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.testzavod.utils.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val useCase: RegisterUseCase
): BaseViewModel(){

    private val _register = MutableStateFlow<UIState<RegisterAnswer>>(UIState.Idle())
    val register = _register.asStateFlow()

    fun sendRegister(register : RegisterSend){
        viewModelScope.launch {
            useCase.sendRegister(register).collectData(_register)
        }
    }
}