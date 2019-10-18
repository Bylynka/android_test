package com.agileengine.test.providers

import com.agileengine.test.models.Repo

data class Resource(val status: Status, val data: List<Repo>, val message: String? = null)