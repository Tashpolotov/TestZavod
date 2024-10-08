package com.example.testzavod.utils.base

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.App
import com.example.testzavod.R
import com.example.testzavod.presentation.state.UIState
import com.example.testzavod.utils.showToast
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseFragment(
    @LayoutRes layoutRes: Int,
) : Fragment(layoutRes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("YourFragment123", "onCreateView() вызван")
        initialize()
        setupRequests()
        setupSubscribers()
        setupListeners()
        initSubscribers()
    }

    open fun initialize() {}

    open fun setupRequests() {}

    open fun setupSubscribers() {}

    open fun setupListeners() {}
    open fun initSubscribers() {}

    /**
     * for @
     */

    /**
     * For what
     *
     * @param state FSf
     * @param onSuccess fs
     */

    private var loadView: View? = null
    private val loadingDelay = 300L
    private var loadRequest = 0L
    protected open val loadingAlpha = 1f
    var isLoading: Boolean = false

    open fun showLoading(delayed: Boolean = false, anim: Boolean = true) {
        isLoading = true
        val ldRqst = loadRequest + 1
        loadRequest = ldRqst
        loadView =
            loadView ?: layoutInflater.inflate(R.layout.layout_loading, view as ViewGroup, true)
                .findViewById<View>(R.id.blur_Layout).apply {
                    alpha = 0f
                }

        loadView?.postDelayed(if (delayed) loadingDelay else 0L) {
            if (ldRqst == loadRequest || !delayed) {
                loadView?.animate()?.setDuration(if(anim) animationDuration * 2 else 0L)?.withStartAction {
                    val transition = (view as ViewGroup).layoutTransition
                    (view as ViewGroup).layoutTransition = null
                    loadView?.visibility = View.VISIBLE
                    view?.post { (view as ViewGroup).layoutTransition = transition }
                }?.alpha(loadingAlpha)?.start()
            }
        }
    }

    internal val animationDuration by lazy {
        300.toLong()
    }

    open fun hideLoading(delayed: Boolean = true, anim: Boolean = true) {
        loadRequest += 1
        loadView?.postDelayed(if (delayed) loadingDelay else 0L) {
            loadView?.animate()?.setDuration(if(anim) animationDuration * 2 else 0L)?.withEndAction {
                loadView?.visibility = View.GONE
                isLoading = false
            }?.alpha(0f)?.start()
        }
    }


    protected fun <T> StateFlow<UIState<T>>.collectUIState(
        state: ((UIState<T>) -> Unit)? = null,
        success: ((data: T) -> Unit)? = null,
        loading: ((data: UIState.Loading<T>) -> Unit)? = null,
        error: ((error: String) -> Unit)? = null,
        idle: ((idle: UIState.Idle<T>) -> Unit)? = null,
        gatherIfSucceed: ((state: UIState<T>) -> Unit)? = null
    ) {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                this@collectUIState.collect {
                    state?.invoke(it)
                    when (it) {
                        is UIState.Idle -> {
                            idle?.invoke(it)
                        }
                        is UIState.Loading -> {
                            loading?.invoke(it)
                        }
                        is UIState.Error -> {
                            error?.invoke(it.error)
                        }
                        is UIState.Success -> {
                            success?.invoke(it.data)
                        }
                    }
                }
            }
        }
    }
    protected fun <T> UIState<T>.assembleViewVisibility(
        group: ConstraintLayout,
        loader: CircularProgressIndicator,
        navigationSucceed: Boolean = false
    ) {
        fun displayLoader(isDisplayed: Boolean) {
            group.isVisible = isDisplayed
            loader.isVisible = isDisplayed
        }
        when (this) {
            is UIState.Idle -> {

            }
            is UIState.Loading -> {
                displayLoader(true)
            }
            is UIState.Error -> {
                displayLoader(false)
            }
            is UIState.Success -> {
                if (navigationSucceed) {
                    displayLoader(true)
                } else {
                    displayLoader(false)
                }

            }
        }

    }
}