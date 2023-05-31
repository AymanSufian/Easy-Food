package com.example.easyfood.Fragments.bottomSheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.easyfood.Fragments.HomeFragment
import com.example.easyfood.R
import com.example.easyfood.activities.MainActivity
import com.example.easyfood.activities.MealActivity
import com.example.easyfood.databinding.FragmentMealBottomSheetBinding
import com.example.easyfood.viewModel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val mealID = "param1"


class mealBottomSheetFragment : BottomSheetDialogFragment() {

    private var mealID: String? = null

    private lateinit var binding: FragmentMealBottomSheetBinding

    private lateinit var viewModel : HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealID = it.getString(mealID)
        }

        viewModel = (activity as MainActivity).viewModel
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentMealBottomSheetBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealID?.let {

            viewModel.getMealById(it) }

        observerBottomSheetMeal()

        onBottomSheetDialogClick()
    }


    private var mealName:String?=null
    private var mealThumb:String?=null

    private fun observerBottomSheetMeal() {

    viewModel.observeBottomSheetMealLiveData().observe(viewLifecycleOwner,Observer{ meal ->

        Glide.with(this).load(meal.strMealThumb).into(binding.imgBottomSheet)
        binding.tvBottomSheetArea.text = meal.strArea
        binding.tvBottomSheetCategory.text = meal.strCategory
        binding.tvBottomSheetMealName.text = meal.strMeal


        mealName = meal.strMeal
        mealThumb = meal.strMealThumb

    })

    }


    private fun onBottomSheetDialogClick() {

        binding.bottomSheet.setOnClickListener{

            if (mealName != null && mealThumb != null){

                val intent = Intent(activity,MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID,mealID)
                    putExtra(HomeFragment.MEAL_NAME,mealName)
                    putExtra(HomeFragment.MEAL_THUMB,mealThumb)

                }

                startActivity(intent)

            }

        }

    }


    companion object {

        @JvmStatic fun newInstance(param1: String) =
                mealBottomSheetFragment().apply {
                    arguments = Bundle().apply {
                        putString(mealID, param1)
                    }
                }
    }
}