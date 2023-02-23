package com.mozio.omarkrostom.arch.extensions

import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.mozio.omarkrostom.arch.BaseActivity
import com.mozio.omarkrostom.arch.BaseBottomSheetDialogFragment
import com.mozio.omarkrostom.arch.BaseFragment

/**
 * Get a view model for the running activity
 */
inline fun <reified T : ViewModel> BaseActivity.getViewModel() =
    viewModels<T>().value

/**
 * Get a view model running across the container activity (Shared with other fragments ie. same instance)
 */
inline fun <reified T : ViewModel> BaseFragment.getSharedViewModel() =
    activityViewModels<T>().value

inline fun <reified T : ViewModel> BaseBottomSheetDialogFragment.getSharedViewModel() =
    activityViewModels<T>().value

/**
 * Get a view model only for running fragment
 */
inline fun <reified T : ViewModel> BaseFragment.getPrivateViewModel() =
    viewModels<T>().value

inline fun <reified T : ViewModel> BaseBottomSheetDialogFragment.getPrivateViewModel() =
    viewModels<T>().value