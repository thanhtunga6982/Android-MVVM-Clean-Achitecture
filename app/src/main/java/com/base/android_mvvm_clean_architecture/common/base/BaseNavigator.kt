package com.base.android_mvvm_clean_architecture.common.base

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

interface BaseNavigator<T> {

    fun findNavController(): NavController?

    fun navigate(event: T)

    fun navigateUp()
}

abstract class BaseNavigatorImpl<T>(
    protected val fragment: Fragment
) : BaseNavigator<T> {

    private var navController: NavController? = null

    override fun findNavController(): NavController? {
        return navController ?: try {
            fragment.findNavController().apply {
                navController = this
            }
        } catch (e: IllegalStateException) {
//            Timber.e(e)
            null
        }
    }

    override fun navigateUp() {
        findNavController()?.navigateUp()
    }

    protected fun popBackTo(@IdRes destinationId: Int, inclusive: Boolean = false) {
        findNavController()?.popBackStack(destinationId, inclusive)
    }
}