package com.example.gatitbogi

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RecruitTable")
data class Recruit(
    //var id: Int,
    var restaurantImg: Int,
    var restaurant: String,
    @PrimaryKey(autoGenerate = false) var title: String,
    var date: String,
    var time: String,
    var person: String,
    var gender: Int,
    var other: Boolean,
    var participate: Int
)