package com.example.easyfood.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easyfood.DB.MealDataBase
import com.example.easyfood.Fragments.HomeFragment
import com.example.easyfood.Pojo.Meal
import com.example.easyfood.R
import com.example.easyfood.databinding.ActivityMealBinding
import com.example.easyfood.viewModel.MealViewModelFactory
import com.example.easyfood.viewModel.mealViewModel

class MealActivity : AppCompatActivity() {

    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealThumb : String
    private lateinit var binding: ActivityMealBinding

    private lateinit var mealMVVM : mealViewModel


    private lateinit var youtubeLink : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMealInformationFromIntent()

        setInformationInViews()


        loadingCase()


        val mealDataBase = MealDataBase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDataBase)

        mealMVVM = ViewModelProvider(this,viewModelFactory)[mealViewModel::class.java]

        mealMVVM.getMealDetail(mealId)
        observerMealDetailsLiveData()


        onFavouriteClick()
        onYoutubeImageClick()

    }

    private fun onFavouriteClick() {

        binding.btnAddFavorites.setOnClickListener{

            mealTOSave?.let {

                mealMVVM.insertMeal(it)
                Toast.makeText(this, "Meal Added To Favourite", Toast.LENGTH_SHORT).show()

            }

        }

    }

    private fun onYoutubeImageClick() {

        binding.imgYoutube.setOnClickListener{

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)

        }

    }

    private var mealTOSave:Meal?=null
    private fun observerMealDetailsLiveData() {

        mealMVVM.observerMealDetailsLiveData().observe(this, object : Observer<Meal> {

            override fun onChanged(t: Meal?) {


            onResponseCase()
            val meal = t
            mealTOSave = meal


            binding.tvCategory.text = "Category : ${t!!.strCategory}"

            binding.tvArea.text = "Area : ${t!!.strArea}"

            binding.tvInstructionsSteps.text = t.strInstructions

            youtubeLink = t.strYoutube!!
        }
        })
    }


    private fun setInformationInViews() {

        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

    }

    private fun getMealInformationFromIntent() {
        val intent = intent

        mealId = intent.getStringExtra(HomeFragment.MEAL_ID) !!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME) !!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB) !!

    }

    private fun loadingCase(){

        binding.progressBar.visibility = View.VISIBLE

        binding.btnAddFavorites.visibility = View.INVISIBLE

        binding.tvInstructions.visibility = View.INVISIBLE

        binding.tvCategory.visibility = View.INVISIBLE

        binding.tvArea.visibility = View.INVISIBLE

        binding.imgYoutube.visibility = View.INVISIBLE
    }


    private fun onResponseCase(){

        binding.progressBar.visibility = View.INVISIBLE

        binding.btnAddFavorites.visibility = View.VISIBLE

        binding.tvInstructions.visibility = View.VISIBLE

        binding.tvCategory.visibility = View.VISIBLE

        binding.tvArea.visibility = View.VISIBLE

        binding.imgYoutube.visibility = View.VISIBLE

    }

}