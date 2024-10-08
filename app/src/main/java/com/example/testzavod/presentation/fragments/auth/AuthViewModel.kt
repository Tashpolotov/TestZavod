package com.example.testzavod.presentation.fragments.auth

import androidx.lifecycle.viewModelScope
import com.example.testzavod.domain.model.auth.AnswerAuthPhone
import com.example.testzavod.domain.model.auth.AuthCode
import com.example.testzavod.domain.model.auth.AuthPhone
import com.example.testzavod.domain.model.register.RegisterAnswer
import com.example.testzavod.domain.usecase.AuthUseCase
import com.example.testzavod.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.testzavod.utils.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val useCase : AuthUseCase
): BaseViewModel(){

    private val _auth = MutableStateFlow<UIState<AnswerAuthPhone>>(UIState.Idle())
    val auth = _auth.asStateFlow()

    private val _code = MutableStateFlow<UIState<RegisterAnswer>>(UIState.Idle())
    val code = _code.asStateFlow()

    fun sendAuth(phone:AuthPhone){
        viewModelScope.launch {
            useCase.sendAuth(phone).collectData(_auth)
        }
    }

    fun sendCode(code: AuthCode){
        viewModelScope.launch {
            useCase.sendCode(code).collectData(_code)
        }
    }
}