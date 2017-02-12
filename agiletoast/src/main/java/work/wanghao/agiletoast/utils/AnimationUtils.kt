package work.wanghao.agiletoast.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.annotation.StyleRes
import work.wanghao.agiletoast.R
import work.wanghao.agiletoast.widget.AgileToast

/**
 * @author doublemine
 *         Created  on 2017/02/08 15:43.
 *         Summary:
 */

object AnimationUtils {


  val ANIMATION_DURATION: Long = 300

  @StyleRes fun getWindowShowAnimation(animationType: AnimationType): Int {
    when (animationType) {
      AnimationType.DEFAULT -> return R.style.Animation_Normal
      AnimationType.DRAWER -> return R.style.Animation_Drawer_Bottom
      AnimationType.POLL -> return R.style.Animation_Drawer_Top
      AnimationType.SCALE -> return R.style.Animation_Scale
      AnimationType.SOFT -> return R.style.Animation_Soft
    }
  }

  fun getShowAnimation(agileToast: AgileToast, animationType: AnimationType): AnimatorSet {
    when (animationType) {
      AnimationType.POLL -> {
        val drawerSet = AnimatorSet()
        drawerSet.playTogether(
            ObjectAnimator.ofFloat(agileToast.mContentView, "translationY",
                -(agileToast.mContentView!!.measuredHeight.toFloat()), 0f),
            ObjectAnimator.ofFloat(agileToast.mContentView, "alpha", 0f, 1f)
        )
        drawerSet.duration = ANIMATION_DURATION
        return drawerSet
      }

      AnimationType.DRAWER -> {
        val drawerSet = AnimatorSet()
        drawerSet.playTogether(
            ObjectAnimator.ofFloat(agileToast.mContentView, "translationY",
                (agileToast.mContentView!!.measuredHeight.toFloat()), 0f),
            ObjectAnimator.ofFloat(agileToast.mContentView, "alpha", 0f, 1f)
        )
        drawerSet.duration = ANIMATION_DURATION
        return drawerSet
      }

      AnimationType.SCALE -> {
        val scaleSet = AnimatorSet()
        scaleSet.playTogether(
            ObjectAnimator.ofFloat(agileToast.mContentView, "scaleX", 0f, 1f),
            ObjectAnimator.ofFloat(agileToast.mContentView, "scaleY", 0f, 1f)
        )
        scaleSet.duration = ANIMATION_DURATION
        return scaleSet
      }
      else -> {
        val defaultSet = AnimatorSet()
        defaultSet.play(ObjectAnimator.ofFloat(agileToast.mContentView, "alpha", 0f, 1f))
        defaultSet.duration = ANIMATION_DURATION
        return defaultSet
      }
    }
  }

  fun getHideAnimation(agileToast: AgileToast, animationType: AnimationType): AnimatorSet {
    when (animationType) {
      AnimationType.POLL -> {
        val drawerSet = AnimatorSet()
        drawerSet.playTogether(
            ObjectAnimator.ofFloat(agileToast.mContentView, "translationY", 0f,
                -agileToast.mContentView!!.measuredHeight.toFloat()),
            ObjectAnimator.ofFloat(agileToast.mContentView, "alpha", 1f, 0f)
        )
        drawerSet.duration = ANIMATION_DURATION
        return drawerSet
      }

      AnimationType.DRAWER -> {
        val drawerSet = AnimatorSet()
        drawerSet.playTogether(
            ObjectAnimator.ofFloat(agileToast.mContentView, "translationY", 0f,
                agileToast.mContentView!!.measuredHeight.toFloat()),
            ObjectAnimator.ofFloat(agileToast.mContentView, "alpha", 1f, 0f)
        )
        drawerSet.duration = ANIMATION_DURATION
        return drawerSet
      }

      AnimationType.SCALE -> {
        val scaleSet = AnimatorSet()
        scaleSet.playTogether(
            ObjectAnimator.ofFloat(agileToast.mContentView, "scaleX", 1f, 0f),
            ObjectAnimator.ofFloat(agileToast.mContentView, "scaleY", 1f, 0f)
        )
        scaleSet.duration = ANIMATION_DURATION
        return scaleSet
      }

      else -> {
        val defaultSet = AnimatorSet()
        defaultSet.play(ObjectAnimator.ofFloat(agileToast.mContentView, "alpha", 1f, 0f))
        defaultSet.duration = ANIMATION_DURATION
        return defaultSet
      }
    }
  }

}
