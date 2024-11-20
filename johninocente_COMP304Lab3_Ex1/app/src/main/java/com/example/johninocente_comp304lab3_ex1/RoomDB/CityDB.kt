package com.example.johninocente_comp304lab3_ex1.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [City::class] , version = 1)
abstract class CityDB : RoomDatabase(){
    abstract fun getCityDao() : CityDAO

    // multi-threading
    companion object{
        @Volatile
        private var INSTANCE : CityDB? = null
        fun getInstance(context: Context): CityDB {
            synchronized(this)
            {
                var instance = INSTANCE
                if (instance == null)
                {
                    instance = Room.databaseBuilder(context,
                        CityDB::class.java,
                        "citiesDB").
                    build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}