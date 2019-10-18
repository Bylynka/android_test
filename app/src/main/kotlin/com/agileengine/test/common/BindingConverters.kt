package com.agileengine.test.common

import android.view.View
import androidx.databinding.BindingConversion

@BindingConversion
fun convertBooleanToVisibility(isVisible: Boolean) = if (isVisible) View.VISIBLE else View.GONE