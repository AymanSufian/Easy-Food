package com.example.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyfood.DB.MealDataBase
import com.example.easyfood.Pojo.Meal
import com.example.easyfood.Pojo.mealList
import com.example.easyfood.Retrofit.retrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class mealViewModel(
    val mealDatabase:MealDataBase
) : ViewModel() {

    private var mealDetailLiveData = MutableLiveData<Meal>()

    fun getMealDetail(id:String) {

        retrofitInstance.api.getMealDetails(id).enqueue(object : Callback<mealList>{
            override fun onResponse(call: Call<mealList>, response: Response<mealList>) {

                if (response.body()!=null){

                    mealDetailLiveData.value = response.body()!!.meals[0]

                } else

                    return
            }


            override fun onFailure(call: Call<mealList>, t: Throwable) {

                Log.d("Meal Activity",t.message.toString())

            }


        })

    }


    fun observerMealDetailsLiveData() : LiveData<Meal> {

        return  mealDetailLiveData

    }

    fun insertMeal(meal : Meal){
        viewModelScope.launch {
            mealDatabase.mealDAO().upsert(meal)
        }

    }




    }

