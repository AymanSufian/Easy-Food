package com.example.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.Pojo.MealByCategory
import com.example.easyfood.databinding.PopularItemBinding

class MostPopularAdapter() : RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {


    private var meal_list = ArrayList<MealByCategory>()


    lateinit var onItemClick : ((MealByCategory) -> Unit)


    var onLongItemClick : ((MealByCategory) -> Unit) ?= null

    fun setMeal (meal_list : ArrayList<MealByCategory>){
        this.meal_list = meal_list
        notifyDataSetChanged()


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {

        return PopularMealViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(meal_list[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)


        holder.itemView.setOnClickListener {

            onItemClick.invoke(meal_list[position])
        }

        holder.itemView.setOnLongClickListener {

            onLongItemClick?.invoke(meal_list[position])
            true
        }

    }

    override fun getItemCount(): Int {

        return meal_list.size

    }


    class PopularMealViewHolder(val binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root)



}