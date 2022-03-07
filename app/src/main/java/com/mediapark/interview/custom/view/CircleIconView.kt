package com.mediapark.interview.custom.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ImageViewCompat
import com.mediapark.interview.R
import com.mediapark.interview.databinding.LayoutViewCircleIconViewBinding
import com.mediapark.interview.util.UIUtil

class CircleIconView: ConstraintLayout, View.OnClickListener {
    var clickListener: OnClickListener? = null

    lateinit var binding: LayoutViewCircleIconViewBinding

    constructor(context: Context) : super(context) { initView() }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { initView(attrs) }
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) { initView(attrs) }

    private fun initView(attrs: AttributeSet? = null) {
        binding = LayoutViewCircleIconViewBinding.inflate(LayoutInflater.from(context), this, true)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.CircleIconView)
        try {
            val resource = context.resources

            val clickable = ta.getBoolean(R.styleable.CircleIconView_clickable, false)
            val background = ta.getResourceId(R.styleable.CircleIconView_background, -1)
            val backgroundTint = ta.getResourceId(R.styleable.CircleIconView_backgroundTint, -1)

            val icon = ta.getResourceId(R.styleable.CircleIconView_srcCompat, -1)
            val tint = ta.getResourceId(R.styleable.CircleIconView_tint, -1)
            val spacing = ta.getDimensionPixelSize(R.styleable.CircleIconView_spacing, 0)

            if (background != -1)
                binding.circleIconViewImgIconBackground.setImageResource(background)
            if (backgroundTint != -1)
                ImageViewCompat.setImageTintList(binding.circleIconViewImgIconBackground, UIUtil.colorToColorStateList(context, backgroundTint))

            if (icon != -1)
                binding.circleIconViewImgIcon.setImageResource(icon)
            if (tint != -1)
                ImageViewCompat.setImageTintList(binding.circleIconViewImgIcon, UIUtil.colorToColorStateList(context, tint))
            binding.circleIconViewImgIcon.setPadding(spacing, spacing, spacing, spacing)

            binding.viewClickable.visibility = UIUtil.visibility(clickable)
            binding.viewClickable.setOnClickListener(this)
        } finally {
            ta.recycle()
        }
    }

    fun updateBackgroundColor(color: Int) {
        ImageViewCompat.setImageTintList(binding.circleIconViewImgIconBackground, UIUtil.colorToColorStateList(context, color))
    }

    fun updateIconSpacing(
        paddingLeft: Int,
        paddingTop: Int = paddingLeft,
        paddingRight: Int = paddingLeft,
        paddingBottom: Int = paddingLeft) {
        binding.circleIconViewImgIcon.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }

    fun updateIcon(resId: Int) {
        binding.circleIconViewImgIcon.setImageResource(resId)
    }

    override fun onClick(p0: View?) {
        clickListener?.onClick(this)
    }
}