package com.example.easyfood.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.request.transition.Transition.ViewAdapter
import com.example.easyfood.R
import com.example.easyfood.activities.MainActivity
import com.example.easyfood.adapter.FavoritesMealsAdapter
import com.example.easyfood.databinding.FragmentSearchBinding
import com.example.easyfood.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class searchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding

    private lateinit var viewModel: HomeViewModel

    private lateinit var searchRecyclerViewAdapter: FavoritesMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        binding.imgSearchArrow.setOnClickListener{ searchMeals() }

        observeSearchedMealsLiveData()

        var searchJob : Job? = null
        binding.edSearchBar.addTextChangedListener {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchMeals(searchMeals().toString())
            }
        }


    }

    private fun observeSearchedMealsLiveData() {
        viewModel.observeSearchMealLiveData().observe(viewLifecycleOwner, Observer { mealsList ->

            searchRecyclerViewAdapter.differ.submitList(mealsList)


        })
    }


    private fun prepareRecyclerView() {

        searchRecyclerViewAdapter = FavoritesMealsAdapter()
        binding.rvSearchMeals.apply {

            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = searchRecyclerViewAdapter
        }

    }


    private fun searchMeals() {

        val searchQuery = binding.edSearchBar.text.toString()
        if(searchQuery.isNotEmpty()){ viewModel.searchMeals(searchQuery) }

    }


}