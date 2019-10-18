package com.agileengine.test.features.repos

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.agileengine.test.R
import com.agileengine.test.models.Repo
import com.agileengine.test.providers.ReposProvider
import com.agileengine.test.providers.Status
import javax.inject.Inject

class ReposViewModel @Inject constructor(val provider: ReposProvider, val context: Context) :
    ViewModel() {

    val resource = provider.resource

    val repos = Transformations.switchMap(resource) {
        MutableLiveData<List<Repo>>(it.data)
    }

    val hasItems = Transformations.switchMap(resource) {
        MutableLiveData<Boolean>(it.data.isNotEmpty())
    }

    val isBusy = Transformations.switchMap(resource) {
        MutableLiveData<Boolean>(it.status == Status.LOADING)
    }

    val message = Transformations.switchMap(resource) {
        val msg = when {
            it.status == Status.LOADING -> context.getString(R.string.loading)
            it.status == Status.ERROR -> it.message
            it.data.isEmpty() -> context.getString(R.string.no_items)
            else -> ""
        }
        MutableLiveData<String>(msg)
    }

    init {
        loadData()
    }

    fun loadData() = provider.load()

}