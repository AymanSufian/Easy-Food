package com.example.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfood.Pojo.MealByCategory
import com.example.easyfood.Pojo.MealsByCategoryList
import com.example.easyfood.Pojo.mealList
import com.example.easyfood.Retrofit.retrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel() {

    val mealsLiveData = MutableLiveData<List<MealByCategory>>()

    fun getMealsByCategory(categoryName:String){

        retrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : Callback<MealsByCategoryList>{
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {

                response.body()?.let { mealsList ->

                    mealsLiveData.postValue(mealsList.meals)
                }

            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.e("CategoryMealsViewModel",t.message.toString())
            }


        })

    }

    fun observeMealsLiveData():LiveData<List<MealByCategory>>{

        return mealsLiveData

    }
}