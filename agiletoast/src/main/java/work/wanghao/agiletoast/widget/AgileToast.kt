package work.wanghao.agiletoast.widget

import android.animation.AnimatorSet
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.support.annotation.LayoutRes
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import work.wanghao.agiletoast.callback.OnDismissCallback
import work.wanghao.agiletoast.utils.AnimationType
import work.wanghao.agiletoast.utils.AnimationUtils

/**
 * @author doublemine
 *         Created  on 2017/02/08 15:25.
 *         Summary:
 */
class AgileToast constructor(context: Context) {
  private val mContext: Context = context
  private var mMessage: String? = null
  private var mType: ToastType = ToastType.NORMAL
  private val mLayoutInflater = LayoutInflater.from(context)
  private var mOnDismissCallback: OnDismissCallback? = null

  private var mToastHandler: ToastHandler? = null
  private var mCustomDuration: Long = 2000
  private var mDuration: Duration = Duration.DURATION_SHORT

  private var mRootView: ViewGroup? = null
  private var mContainerView: ViewGroup? = null
  private var mContentView: View? = null

  private var mToastBackGroundDrawable: Drawable? = null
  private var mMessageTextView: TextView? = null
  private var mTextSize = 20f
  private var mTextColor = Color.WHITE
  private var mToastBackgroundColor = 0

  private var mShowAnimationType: AnimationType = AnimationType.ANIMATION_DEFAULT
  private var mHideAnimationType: AnimationType = AnimationType.ANIMATION_DEFAULT
  private var mShowAnimationSet: AnimatorSet? = null
  private var mHideAnimationSet: AnimatorSet? = null

  fun isShowing(): Boolean {
    return mContentView != null && mContentView!!.isShown
  }

  fun getHideAnimationSet(): AnimatorSet {
    return mHideAnimationSet!!
  }

  fun getShowAnimationSet(): AnimatorSet {
    return mShowAnimationSet!!
  }

  fun getRootView(): ViewGroup {
    return mRootView!!
  }

  fun getContainerView(): ViewGroup {
    return mContainerView!!
  }

  fun getContentView(): View {
    return mContentView!!
  }

  fun getDuration(): Duration {
    return mDuration
  }

  fun getCustomDuration(): Long {
    return mCustomDuration
  }

  fun getDismissCallback(): OnDismissCallback? {
    return mOnDismissCallback
  }

  fun show() {
    initialize()
    mHideAnimationSet = AnimationUtils.getHideAnimation(this, mHideAnimationType)
    mShowAnimationSet = AnimationUtils.getShowAnimation(this, mShowAnimationType)
    mToastHandler = ToastHandler.get()
    mToastHandler?.add(this)
  }

  fun initialize() {
    mRootView = (mContext as Activity).findViewById(android.R.id.content) as ViewGroup?
    when (mType) {
      ToastType.BOTTOM -> setupBottomView()
      ToastType.NORMAL -> setupNormalView()
      ToastType.TOP -> setupTopView()
      ToastType.CENTER -> setupCenterView()
      ToastType.CUSTOM -> TODO()
    }

    val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1,
        View.MeasureSpec.AT_MOST)
    val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1,
        View.MeasureSpec.AT_MOST)
    mContentView?.measure(widthMeasureSpec, heightMeasureSpec)
  }

  fun setupTopView() {

  }

  fun setupBottomView() {
    mContainerView = LinearLayout(mContext)
    val viewGroupParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
        FrameLayout.LayoutParams.WRAP_CONTENT)
    viewGroupParams.gravity = Gravity.BOTTOM or Gravity.CENTER
    viewGroupParams.bottomMargin = 200
    mContainerView?.layoutParams = viewGroupParams
    mRootView?.addView(mContainerView)


    if (mContentView == null) {
      mContentView = mLayoutInflater.inflate(work.wanghao.agiletoast.R.layout.view_toast_normal,
          mContainerView, false)
      mToastBackGroundDrawable = mContentView?.background as GradientDrawable
      mMessageTextView = mContentView?.findViewById(
          work.wanghao.agiletoast.R.id.tv_message) as TextView?
      mMessageTextView?.setTextColor(mTextColor)
      mMessageTextView?.textSize = mTextSize
      mMessageTextView?.text = mMessage
      if (mToastBackgroundColor != 0) {
        (mToastBackGroundDrawable as GradientDrawable).setColor(mToastBackgroundColor)
      }
    }
  }

  fun setupNormalView() {

  }

  fun setupCenterView() {

  }

  constructor(context: Context, msg: String) : this(context) {
    mMessage = msg
  }

  constructor(context: Context, msg: String, type: ToastType) : this(context, msg) {
    mType = type
  }

  /* builder */

  companion object {
    fun build(context: Context): AgileToast {
      return AgileToast(context)
    }
  }

  fun setText(msg: String): AgileToast {
    mMessage = msg
    return this
  }

  fun setTextColor(color: Int): AgileToast {
    mTextColor = color
    return this
  }

  fun setTextSize(size: Float): AgileToast {
    mTextSize = size
    return this
  }

  fun setToastBackgroundColor(bgColor: Int): AgileToast {
    mToastBackgroundColor = bgColor
    return this
  }

  fun setShowAnimationType(animationType: AnimationType): AgileToast {
    mShowAnimationType = animationType
    return this
  }

  fun setHideAnimationType(animationType: AnimationType): AgileToast {
    mHideAnimationType = animationType
    return this
  }

  fun setAnimationType(animationType: AnimationType): AgileToast {
    mShowAnimationType = animationType
    mHideAnimationType = animationType
    return this
  }

  fun setContentView(contentView: View): AgileToast {
    mContentView = contentView
    return this
  }

  fun setContentView(@LayoutRes contentView: Int): AgileToast {
    mContentView = mLayoutInflater.inflate(contentView, null)
    return this
  }

  fun setOnDismissCallback(callback: OnDismissCallback): AgileToast {
    mOnDismissCallback = callback
    return this
  }

  fun setDuration(duration: Duration, customDuration: Long?): AgileToast {
    if (duration == Duration.DURATION_CUSTOM) {
      if (customDuration == null || customDuration < 800) throw IllegalArgumentException(
          "when duration=Duration.DURATION_CUSTOM the customDuration must NOT NULL and the value >800")
      mCustomDuration = customDuration
    }
    mDuration = duration
    return this
  }

  fun setToastType(toastType: ToastType): AgileToast {
    mType = toastType
    return this
  }
}