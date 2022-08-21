package com.example.gatitbogi

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.ArrayList

@Dao
interface RecruitDao {
    @Insert
    fun insert(recruit: Recruit)

    @Query("SELECT * FROM RecruitTable")
    fun getRecruit(): List<Recruit>

    @Query("UPDATE RecruitTable SET participate = :person WHERE title = :title")
    fun participate(person: Int, title: String)
}