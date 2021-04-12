package com.madappgang.teamgrowth.presenters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.madappgang.BaseClickListener
import com.madappgang.BaseDiffUtil
import com.madappgang.teamgrowth.R
import com.madappgang.teamgrowth.databinding.ItemGoalBinding
import com.madappgang.teamgrowth.domain.Goal
import com.madappgang.teamgrowth.domain.UserGoal
import kotlin.math.roundToInt


/*
 * Created by Eugene Prytula on 4/9/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

class GoalsAdapter(
    private val baseClickListener: BaseClickListener<UserGoal>
) : ListAdapter<UserGoal, GoalViewHolder>(BaseDiffUtil<UserGoal>()) {
    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) =
        holder.bind(getItem(position), baseClickListener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder =
        GoalViewHolder.from(parent)
}

class GoalViewHolder(
    private val itemGoalBinding: ItemGoalBinding
) : RecyclerView.ViewHolder(itemGoalBinding.root) {
    companion object {
        fun from(parent: ViewGroup): GoalViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = ItemGoalBinding.inflate(layoutInflater, parent, false)
            return GoalViewHolder(view)
        }
    }

    fun bind(userGoal: UserGoal, baseClickListener: BaseClickListener<UserGoal>) {
        with(itemGoalBinding) {
            val context = itemGoalBinding.root.context
            val goal = userGoal.goal

            constraintRootGoalCard.setOnClickListener { baseClickListener.clickListener(userGoal) }
            textVieCategory.text = String.format(context.getString(R.string.category), goal.category)
            textViewGoalText.text = goal.description
            textViewLink.text = goal.link
            textViewProgressThisWeek.text = String.format(context.getString(R.string.progressThisWeek), goal.weeklyProgress.roundToInt())
            textViewProgressTotal.text = String.format(context.getString(R.string.progressInTotal), goal.progress.roundToInt())
            weekProgressBar.setProgresses(goal.progress.toInt(), goal.weeklyProgress.toInt())
        }
    }
}