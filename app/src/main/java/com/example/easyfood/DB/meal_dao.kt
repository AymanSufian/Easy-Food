package com.example.easyfood.DB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.easyfood.Pojo.Meal


@Dao
interface meal_dao {


    @Insert
    suspend fun upsert(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("select * from mealInformation")
    fun getAllMeals():LiveData<List<Meal>>


}