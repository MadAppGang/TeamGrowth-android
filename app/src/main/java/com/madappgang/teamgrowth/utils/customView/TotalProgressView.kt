package com.madappgang.teamgrowth.utils.customView

import android.content.Context
import android.content.res.TypedArray
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.madappgang.teamgrowth.R
import kotlin.math.min


/*
 * Created by Eugene Prytula on 4/12/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

class TotalProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val typedArray: TypedArray by lazy {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.TotalProgressView
        )
    }

    private var backgroundCircleColor = 0
    private var progressCircleColor = 0
    private var circleBackgroundStokeWidth = 0F
    private var circleProgressStrokeWidth = 0F
    private var circleProgressBlurStrokeWidth = 0F

    companion object {
        private val circleDiapason: ClosedFloatingPointRange<Float> = 0F..360F
    }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.TotalProgressView, 0, 0).apply {
            try {
                backgroundCircleColor = typedArray.getColor(R.styleable.TotalProgressView_backgroundCircleColor, 0)
                progressCircleColor = typedArray.getColor(R.styleable.TotalProgressView_progressCircleColor, 0)
                circleBackgroundStokeWidth = typedArray.getDimension(R.styleable.TotalProgressView_circleBackgroundStokeWidth, 0F)
                circleProgressStrokeWidth = typedArray.getDimension(R.styleable.TotalProgressView_circleProgressStrokeWidth, 0F)
                circleProgressBlurStrokeWidth = typedArray.getDimension(R.styleable.TotalProgressView_circleProgressBlurStrokeWidth, 0F)
            } finally {
                recycle()
            }
        }
    }

    private val progressRectF = RectF()

    private val backgroundCirclePaint = Paint().apply {
        isAntiAlias = true
        color = backgroundCircleColor
        strokeWidth = circleBackgroundStokeWidth
        style = Paint.Style.STROKE
    }

    private val progressCirclePaint = Paint().apply {
        isAntiAlias = true
        color = progressCircleColor
        strokeWidth = circleProgressStrokeWidth
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val blurProgressCirclePaint = Paint().apply {
        isAntiAlias = true
        color = progressCircleColor
        strokeWidth = circleProgressStrokeWidth
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        maskFilter = BlurMaskFilter(circleProgressBlurStrokeWidth, BlurMaskFilter.Blur.OUTER)
    }

    private var progress = 0F
    set(value) {
        field = value
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            val centerX = width / 2F
            val centerY = height / 2F
            val radius = min(centerX, centerY) - this.paddingBottom

            progressRectF.apply {
                top = centerY - radius + paddingTop
                bottom = centerY + radius - paddingBottom
                left = centerX - radius + paddingLeft
                right = centerX + radius - paddingRight
            }

            it.drawArc(progressRectF, circleDiapason.start, circleDiapason.endInclusive, false, backgroundCirclePaint)

            val progressSweepAngle = circleDiapason.endInclusive * (progress / 100F)
            it.drawArc(progressRectF, circleDiapason.start, progressSweepAngle, false, progressCirclePaint)
            it.drawArc(progressRectF, circleDiapason.start, progressSweepAngle, false, blurProgressCirclePaint)
        }
    }
}