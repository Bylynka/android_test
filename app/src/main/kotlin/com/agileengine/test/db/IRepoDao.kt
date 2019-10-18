package com.agileengine.test.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agileengine.test.models.Repo

@Dao
interface IRepoDao {

    @Query("Select * from repos")
    fun getRepos():LiveData<List<Repo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repos: List<Repo>)
}