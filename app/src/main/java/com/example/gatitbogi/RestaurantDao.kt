package com.example.gatitbogi

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.ArrayList

@Dao
interface RestaurantDao {
    @Query("SELECT * FROM RestaurantTable")
    fun getRestaurant(): List<Restaurant>

    @Query("SELECT * FROM RestaurantTable ORDER BY distance")
    fun alignDistance(): List<Restaurant>

    @Query("SELECT * FROM RestaurantTable ORDER BY rate")
    fun alignPopular(): List<Restaurant>
}