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
      AnimationType.ANIMATION_DEFAULT -> return R.style.Animation_Normal
      AnimationType.ANIMATION_DRAWER_BOTTOM -> return R.style.Animation_Drawer_Bottom
      AnimationType.ANIMATION_DRAWER_TOP -> return R.style.Animation_Drawer_Top
      AnimationType.ANIMATION_SCALE -> return R.style.Animation_Scale
    }
  }

  fun getShowAnimation(agileToast: AgileToast, animationType: AnimationType): AnimatorSet {
    when (animationType) {
      AnimationType.ANIMATION_DRAWER_TOP -> {
        val drawerSet = AnimatorSet()
        drawerSet.playTogether(
            ObjectAnimator.ofFloat(agileToast.getContentView(), "translationY",
                -(agileToast.getContentView().measuredHeight.toFloat()), 0f),
            ObjectAnimator.ofFloat(agileToast.getContentView(), "alpha", 0f, 1f)
        )
        drawerSet.duration = ANIMATION_DURATION
        return drawerSet
      }

      AnimationType.ANIMATION_DRAWER_BOTTOM -> {
        val drawerSet = AnimatorSet()
        drawerSet.playTogether(
            ObjectAnimator.ofFloat(agileToast.getContentView(), "translationY",
                (agileToast.getContentView().measuredHeight.toFloat()), 0f),
            ObjectAnimator.ofFloat(agileToast.getContentView(), "alpha", 0f, 1f)
        )
        drawerSet.duration = ANIMATION_DURATION
        return drawerSet
      }

      AnimationType.ANIMATION_SCALE -> {
        val scaleSet = AnimatorSet()
        scaleSet.playTogether(
            ObjectAnimator.ofFloat(agileToast.getContentView(), "scaleX", 0f, 1f),
            ObjectAnimator.ofFloat(agileToast.getContentView(), "scaleY", 0f, 1f)
        )
        scaleSet.duration = ANIMATION_DURATION
        return scaleSet
      }


      AnimationType.ANIMATION_DEFAULT -> {
        val defaultSet = AnimatorSet()
        defaultSet.play(ObjectAnimator.ofFloat(agileToast.getContentView(), "alpha", 0f, 1f))
        defaultSet.duration = ANIMATION_DURATION
        return defaultSet
      }
    }
  }

  fun getHideAnimation(agileToast: AgileToast, animationType: AnimationType): AnimatorSet {
    when (animationType) {
      AnimationType.ANIMATION_DRAWER_TOP -> {
        val drawerSet = AnimatorSet()
        drawerSet.playTogether(
            ObjectAnimator.ofFloat(agileToast.getContentView(), "translationY", 0f,
                -agileToast.getContentView().measuredHeight.toFloat()),
            ObjectAnimator.ofFloat(agileToast.getContentView(), "alpha", 1f, 0f)
        )
        drawerSet.duration = ANIMATION_DURATION
        return drawerSet
      }

      AnimationType.ANIMATION_DRAWER_BOTTOM -> {
        val drawerSet = AnimatorSet()
        drawerSet.playTogether(
            ObjectAnimator.ofFloat(agileToast.getContentView(), "translationY", 0f,
                agileToast.getContentView().measuredHeight.toFloat()),
            ObjectAnimator.ofFloat(agileToast.getContentView(), "alpha", 1f, 0f)
        )
        drawerSet.duration = ANIMATION_DURATION
        return drawerSet
      }

      AnimationType.ANIMATION_SCALE -> {
        val scaleSet = AnimatorSet()
        scaleSet.playTogether(
            ObjectAnimator.ofFloat(agileToast.getContentView(), "scaleX", 1f, 0f),
            ObjectAnimator.ofFloat(agileToast.getContentView(), "scaleY", 1f, 0f)
        )
        scaleSet.duration = ANIMATION_DURATION
        return scaleSet
      }

      AnimationType.ANIMATION_DEFAULT -> {
        val defaultSet = AnimatorSet()
        defaultSet.play(ObjectAnimator.ofFloat(agileToast.getContentView(), "alpha", 1f, 0f))
        defaultSet.duration = ANIMATION_DURATION
        return defaultSet
      }
    }
  }

}
