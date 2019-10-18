package com.agileengine.test.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos")
open class Repo {

    @PrimaryKey
    open var id: Int? = null

    open  var name: String? = null

    open  var description: String? = null
}