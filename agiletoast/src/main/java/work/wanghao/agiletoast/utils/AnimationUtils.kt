package work.wanghao.agiletoast.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import work.wanghao.agiletoast.widget.AgileToast

/**
 * @author doublemine
 *         Created  on 2017/02/08 15:43.
 *         Summary:
 */

object AnimationUtils {

  val ANIMATION_DURATION: Long = 300

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

      AnimationType.ANIMATION_PULL -> {
        val pullSet = AnimatorSet()
        pullSet.playTogether(
            ObjectAnimator.ofFloat(agileToast.getContentView(), "translationY",
                agileToast.getContentView().measuredHeight.toFloat(), 0f),
            ObjectAnimator.ofFloat(agileToast.getContentView(), "alpha", 0f, 1f)
        )
        pullSet.duration = ANIMATION_DURATION
        return pullSet
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

      AnimationType.ANIMATION_PULL -> {
        val pullSet = AnimatorSet()



        pullSet.playTogether(
            ObjectAnimator.ofFloat(agileToast.getContentView(), "translationY", 0f,
                agileToast.getContentView().measuredHeight.toFloat()),
            ObjectAnimator.ofFloat(agileToast.getContentView(), "alpha", 1f, 0f)
        )
        pullSet.duration = ANIMATION_DURATION
        return pullSet
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
