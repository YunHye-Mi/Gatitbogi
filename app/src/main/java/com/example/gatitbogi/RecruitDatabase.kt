package com.example.gatitbogi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Restaurant::class, Search::class, Recruit::class], version = 1)
abstract class RecruitDatabase: RoomDatabase() {
    abstract fun recruitDao(): RecruitDao
    abstract fun searchDao(): SearchDao
    abstract fun restaurantDao(): RestaurantDao

    companion object{
        private var instance: RecruitDatabase? = null

        @Synchronized
        fun getInstance(context: Context): RecruitDatabase? {
            if (instance == null){
                synchronized(RecruitDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecruitDatabase::class.java,
                        "Recruit-database"
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}