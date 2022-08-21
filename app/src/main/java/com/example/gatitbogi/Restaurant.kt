package com.example.gatitbogi

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RestaurantTable")
data class Restaurant(
    var restaurantImg: Int,
    @PrimaryKey var name: String,
    var category: String,
    var rate: Float,
    var menu: String,
    var info: String,
    var distance: Int,
    var seat: Int,
    var recruit: Int
)
