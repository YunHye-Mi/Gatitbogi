package com.example.gatitbogi

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    @PrimaryKey var username: String,
    var email: String,
    var password: Int
)
