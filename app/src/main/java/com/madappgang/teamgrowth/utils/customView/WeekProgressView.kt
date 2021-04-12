package com.madappgang.teamgrowth.utils.customView

import android.graphics.Canvas
import android.graphics.Paint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import com.madappgang.teamgrowth.R


/*
 * Created by Eugene Prytula on 4/12/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */


class WeekProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val typedArray: TypedArray by lazy {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.WeekProgressView
        )
    }

    private var totalLineColor = 0
    private var weekLineColor = 0
    private var backgroundLineColor = 0
    private var weekStrokeWidth = 0F

    private var totalProgress = 0
    private var weekProgress = 0

    companion object {
        private const val HUNDRED_PERCENT = 100F
    }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.OtpCodeView, 0, 0).apply {
            try {
                totalLineColor = typedArray.getColor(R.styleable.WeekProgressView_totalProgressLineColor, 0)
                weekLineColor = typedArray.getColor(R.styleable.WeekProgressView_weekProgressLineColor, 0)
                backgroundLineColor = typedArray.getColor(R.styleable.WeekProgressView_backgroundLineColor, 0)
                weekStrokeWidth = typedArray.getDimension(R.styleable.WeekProgressView_weekStrokeWidth, 0F)
            } finally {
                recycle()
            }
        }
    }

    private val totalLinePaint = Paint().apply {
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
        color = totalLineColor
        strokeWidth = weekStrokeWidth
    }

    private val weekLinePaint = Paint().apply {
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
        color = weekLineColor
        strokeWidth = weekStrokeWidth
    }

    private val backgroundLinePaint = Paint().apply {
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
        strokeWidth = weekStrokeWidth
        color = backgroundLineColor
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            val startX = 0F + paddingLeft
            val startY = height / 2F
            val stopX = width.toFloat() - paddingRight
            val stopY = height / 2F

            it.drawLine(startX, startY, stopX, stopY, backgroundLinePaint)

            val maxWeekProgress = HUNDRED_PERCENT.coerceAtMost((totalProgress + weekProgress).toFloat())
            val maxTotalProgress = HUNDRED_PERCENT.coerceAtMost(totalProgress.toFloat())
            it.drawLine(
                startX,
                startY,
                (stopX / HUNDRED_PERCENT) * maxWeekProgress,
                stopY,
                weekLinePaint
            )
            it.drawLine(
                startX,
                startY,
                (stopX / HUNDRED_PERCENT) * maxTotalProgress,
                stopY,
                totalLinePaint
            )
        }
    }

    fun setProgresses(totalProgress : Int, weekProgress : Int) {
        this.totalProgress = totalProgress
        this.weekProgress = weekProgress
        invalidate()
    }
}