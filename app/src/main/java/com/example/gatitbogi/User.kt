package com.example.gatitbogi

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    @PrimaryKey var id: Int,
    var name: String,
    var gender: Int
)
