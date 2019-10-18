package com.agileengine.test.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.agileengine.test.api.repos.IReposApi
import com.agileengine.test.db.IRepoDao
import com.agileengine.test.models.Repo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ReposProvider(private val api: IReposApi, private val dao: IRepoDao) {

    val resource: LiveData<Resource> = MediatorLiveData()

    init {
        (resource as MediatorLiveData).addSource(dao.getRepos()) { updateData(it) }
    }

    fun load() {
        updateStatus(Status.LOADING)
        Single.create<Unit> {
            try {
                val data = api.fetchRepos().execute().body()
                data?.let { dao.insert(it) }
                it.onSuccess(Unit)
            } catch (e: Exception) {
                it.onError(e)
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ updateStatus(Status.DEFAULT) },
                { updateStatus(Status.ERROR, "Error. Please Try Later") })

    }

    private fun updateStatus(status: Status, message: String? = null) {
        val data = resource.value?.data ?: listOf()
        (resource as MediatorLiveData).postValue(Resource(status, data, message))
    }

    private fun updateData(data: List<Repo>) {
        val current = resource.value
        val status = current?.status ?: Status.DEFAULT
        val msg = current?.message
        (resource as MediatorLiveData).postValue(Resource(status, data, msg))
    }
}