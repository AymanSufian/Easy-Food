package com.example.easyfood.Retrofit

import com.example.easyfood.Pojo.CategoryList
import com.example.easyfood.Pojo.Meal
import com.example.easyfood.Pojo.MealsByCategoryList
import com.example.easyfood.Pojo.mealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface mealApi {

    @GET("random.php")
    fun getRandomMeal() : Call<mealList>

    @GET("lookup.php")
    fun getMealDetails(@Query("i") id:String) : Call<mealList>

    @GET("filter.php")
    fun getPopularItem(@Query("c") categoryName : String ): Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategory() : Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c")categoryName: String) : Call<MealsByCategoryList>

    @GET("search.php")
    fun searchMeal(@Query("s")searchQuery: String):Call<mealList>

}