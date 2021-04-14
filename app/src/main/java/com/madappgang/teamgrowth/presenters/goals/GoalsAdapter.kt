package com.madappgang.teamgrowth.presenters.goals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.madappgang.BaseClickListener
import com.madappgang.teamgrowth.R
import com.madappgang.teamgrowth.databinding.ItemGoalBinding
import com.madappgang.teamgrowth.domain.UserGoal
import com.madappgang.teamgrowth.utils.extensions.animateProgressBar
import kotlin.math.roundToInt


/*
 * Created by Eugene Prytula on 4/9/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

private val diffCallback = object : DiffUtil.ItemCallback<UserGoal>() {
    override fun areItemsTheSame(oldItem: UserGoal, newItem: UserGoal): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: UserGoal, newItem: UserGoal): Boolean = oldItem.id == newItem.id
}

class GoalsAdapter(
    private val baseClickListener: BaseClickListener<UserGoal>
) : ListAdapter<UserGoal, GoalViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) = holder.bind(getItem(position), baseClickListener)
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

            val currentProgress = userGoal.value
            val previousProgress = currentProgress - userGoal.weeklyValue
            textViewProgressThisWeek.text = String.format(
                context.getString(R.string.progressThisWeek),
                currentProgress.roundToInt()
            )
            textViewProgressTotal.text = String.format(
                context.getString(R.string.progressInTotal),
                previousProgress.roundToInt()
            )
            weekProgressBar.animateProgressBar(previousProgress, currentProgress)
        }
    }
}