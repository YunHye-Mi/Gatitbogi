package com.example.gatitbogi

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SearchTable")
data class Search(
    @PrimaryKey var terms: String
)
