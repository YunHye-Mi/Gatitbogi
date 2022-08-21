package com.example.gatitbogi

import androidx.room.*

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(search: Search)

    @Delete
    fun delete(search: Search)

    @Query("DELETE FROM SearchTable")
    fun allDelete()

    @Query("SELECT * FROM SearchTable")
    fun getSearch(): List<Search>
}