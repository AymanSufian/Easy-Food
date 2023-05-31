package com.example.easyfood.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.easyfood.DB.MealDataBase

class MealViewModelFactory(
    private val mealDataBase: MealDataBase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return mealViewModel(mealDataBase) as T
    }

}