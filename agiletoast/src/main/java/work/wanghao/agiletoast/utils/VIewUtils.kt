package work.wanghao.agiletoast.utils

import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue

/**
 * @author doublemine
 *         Created  on 2017/02/09 16:44.
 *         Summary:
 */
object ViewUtils {
  fun convert2Dip(pixels: Int): Int {
    return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels.toFloat(),
        Resources.getSystem().displayMetrics))
  }

  fun getCornerBackground(color: Int): GradientDrawable {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.cornerRadius = convert2Dip(24).toFloat()
    gradientDrawable.setColor(color)
    return gradientDrawable
  }

  fun getFillBackground(color: Int): GradientDrawable {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.setColor(color)
    return gradientDrawable
  }

  fun getNormalBackground(color: Int): GradientDrawable {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.cornerRadius = convert2Dip(4).toFloat()
    gradientDrawable.setColor(color)
    return gradientDrawable
  }
}