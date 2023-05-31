package com.example.easyfood.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.easyfood.Pojo.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(mealTypeCover::class)
abstract class MealDataBase : RoomDatabase(){

    abstract fun mealDAO():meal_dao

    companion object{

        @Volatile
        var INSTANCE:MealDataBase? = null

        @Synchronized
        fun getInstance(context:Context):MealDataBase {

            if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDataBase::class.java,
                    "meal.db"
                ) . fallbackToDestructiveMigration()
                    .build()

            }


            return INSTANCE as MealDataBase

        }
    }

}