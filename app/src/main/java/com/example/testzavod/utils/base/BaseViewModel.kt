package com.example.testzavod.utils.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testzavod.domain.either.Either
import com.example.testzavod.presentation.state.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

        protected fun <T> Flow<Either<String, List<T>>>.listCollectData(_state: MutableStateFlow<UIState<List<T>>>) {


            viewModelScope.launch(Dispatchers.IO) {
                _state.value = UIState.Loading()
                this@listCollectData.collect { response ->
                    when (response) {
                        is Either.Left -> _state.value = UIState.Error(response.value)
                        is Either.Right -> _state.value = UIState.Success(response.value)
                    }
                }
            }
        }

        protected fun <T> Flow<Either<String, T>>.collectData(_state: MutableStateFlow<UIState<T>>) {
            viewModelScope.launch(Dispatchers.IO) {
                this@collectData.collect { res ->
                    Log.d("BaseViewModel", "collectData: $res")
                    when (res) {
                        is Either.Left -> _state.value = UIState.Error(res.value)
                        is Either.Right -> _state.value = UIState.Success(res.value)
                    }
                }
            }
        }
}