package com.example.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyfood.DB.MealDataBase
import com.example.easyfood.Pojo.*
import com.example.easyfood.Retrofit.retrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class HomeViewModel(

    private val mealDataBase: MealDataBase

) : ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()

    private var popularItemLiveData = MutableLiveData<List<MealByCategory>>()

    private var categoriesLiveData = MutableLiveData<List<Category>>()

    private var favoritesMealLiveData = mealDataBase.mealDAO().getAllMeals()

    private var bottomSheetMealLiveData = MutableLiveData<Meal>()

    private var searchMealLiveData = MutableLiveData<List<Meal>>()



    fun getRandomMeal() {

        retrofitInstance.api.getRandomMeal().enqueue(object : Callback<mealList> {

            override fun onResponse(call: Call<mealList>, response: Response<mealList>) {

                if (response.body() != null) {

                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal


                } else {

                    return
                }

            }

            override fun onFailure(call: Call<mealList>, t: Throwable) {

                Log.d("Home Fragment", t.message.toString())

            }
        })

    }

    fun delete (meal: Meal) {
        viewModelScope.launch {
            mealDataBase.mealDAO().delete(meal)
        }
    }


    fun insertMeal(meal : Meal){
        viewModelScope.launch {
            mealDataBase.mealDAO().upsert(meal)
        }

    }


    fun observeRandomMealLiveData(): LiveData<Meal> {

        return randomMealLiveData

    }



    fun getPopularItem(){

        retrofitInstance.api.getPopularItem("Seafood").enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {

                if (response.body() != null){

                    popularItemLiveData.value = response.body()!!.meals

                }

            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {

                Log.d("HomeFragment",t.message.toString())

            }
        })
    }



    fun observePopularItemLiveData(): LiveData<List<MealByCategory>> {

        return popularItemLiveData

    }







    fun getCategories (){

        retrofitInstance.api.getCategory().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {

                response.body()?.let { categoryList ->
                    categoriesLiveData.postValue(categoryList.categories)
                }

            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {

                Log.e("HomeViewModel",t.message.toString())

            }


        })

    }


    fun getMealById( id: String) {

        retrofitInstance.api.getMealDetails(id).enqueue(object : Callback<mealList>{
            override fun onResponse(call: Call<mealList>, response: Response<mealList>) {

                val meal = response.body()?.meals?.first()
                meal?.let { meal ->
                    bottomSheetMealLiveData.postValue(meal)
                }

            }

            override fun onFailure(call: Call<mealList>, t: Throwable) {
                Log.e("HomeViewModel", t.message.toString())
            }


        })


    }

    fun observeCategoriesLiveData() : LiveData<List<Category>> {

        return categoriesLiveData

    }


    fun observeFavoritesMealsLiveData():LiveData<List<Meal>>{

        return  favoritesMealLiveData

    }

    fun observeBottomSheetMealLiveData(): LiveData<Meal> = bottomSheetMealLiveData


    fun searchMeals(searchQuery: String) = retrofitInstance.api.searchMeal(searchQuery).enqueue(
        object : Callback<mealList> {
            override fun onResponse(call: Call<mealList>, response: Response<mealList>) {

                val mealsList = response.body()?.meals
                mealsList?.let {
                    searchMealLiveData.postValue(it)

                }
            }

            override fun onFailure(call: Call<mealList>, t: Throwable) {
                Log.e("HomeViewModel", t.message.toString())
            }


        })


    fun observeSearchMealLiveData() : LiveData<List<Meal>> = searchMealLiveData

}