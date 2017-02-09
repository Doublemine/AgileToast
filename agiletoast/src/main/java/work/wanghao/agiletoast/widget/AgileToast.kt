package work.wanghao.agiletoast.widget

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.GradientDrawable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.v4.content.res.ResourcesCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import work.wanghao.agiletoast.R
import work.wanghao.agiletoast.callback.OnDismissCallback
import work.wanghao.agiletoast.utils.AnimationType
import work.wanghao.agiletoast.utils.AnimationUtils
import work.wanghao.agiletoast.utils.ViewUtils


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

  private var mContentView: View? = null

  private var mToastBackGroundDrawable: GradientDrawable? = null
  private var mMessageTextView: TextView? = null
  private var mTextSize = 14.toFloat()
  private var mTextColor = Color.WHITE
  private var mToastBackgroundColor = 0
  private var mGravity: Int = Gravity.CENTER
  private var mStyle = ToastStyle.NORMAL

  private var mOffsetX: Int = 0
  private var mOffsetY: Int = 0

  private var mAnimationType: AnimationType = AnimationType.ANIMATION_DEFAULT

  fun isShowing(): Boolean {
    return mContentView != null && mContentView!!.isShown
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
    onPrepareExecute()
    mToastHandler = ToastHandler.get()
    onFinalExecute()
    mToastHandler?.add(this)
  }

  fun getContext(): Context {
    return mContext
  }

  fun getWindowManagerParams(): WindowManager.LayoutParams {
    val layoutParams = WindowManager.LayoutParams()
    layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT
    layoutParams.width = FrameLayout.LayoutParams.WRAP_CONTENT
    layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
    layoutParams.format = PixelFormat.TRANSLUCENT
    layoutParams.windowAnimations = AnimationUtils.getWindowShowAnimation(mAnimationType)
    layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST
    layoutParams.gravity = mGravity
    layoutParams.y = mOffsetY
    layoutParams.x = mOffsetX
    return layoutParams
  }

  fun onFinalExecute() {
    when (mType) {
      ToastType.ERROR -> {
        if (mStyle == ToastStyle.NORMAL) mToastBackGroundDrawable = ViewUtils.getNormalBackground(
            ResourcesCompat.getColor(mContext.resources, R.color.colorError, null))
        else mToastBackGroundDrawable = ViewUtils.getCornerBackground(
            ResourcesCompat.getColor(mContext.resources, R.color.colorError, null))
      }
      ToastType.SUCCESS -> {
        if (mStyle == ToastStyle.NORMAL) mToastBackGroundDrawable = ViewUtils.getNormalBackground(
            ResourcesCompat.getColor(mContext.resources, R.color.colorSuccess, null))
        else mToastBackGroundDrawable = ViewUtils.getCornerBackground(
            ResourcesCompat.getColor(mContext.resources, R.color.colorSuccess, null))
      }
      ToastType.WARNING -> {
        if (mStyle == ToastStyle.NORMAL) mToastBackGroundDrawable = ViewUtils.getNormalBackground(
            ResourcesCompat.getColor(mContext.resources, R.color.colorWarning, null))
        else mToastBackGroundDrawable = ViewUtils.getCornerBackground(
            ResourcesCompat.getColor(mContext.resources, R.color.colorWarning, null))
      }
      else -> {
        if (mStyle == ToastStyle.NORMAL) mToastBackGroundDrawable = ViewUtils.getNormalBackground(
            Color.GRAY)
        else mToastBackGroundDrawable = ViewUtils.getCornerBackground(Color.GRAY)
        if (mToastBackgroundColor != 0) {
          mToastBackGroundDrawable?.setColor(mToastBackgroundColor)
        }
      }
    }

    mContentView?.background = mToastBackGroundDrawable


  }

  fun onPrepareExecute() {
    when (mType) {
      ToastType.NORMAL -> prepareNormalType()
      ToastType.CLICK -> TODO()
      ToastType.SUCCESS -> prepareTipsType(R.drawable.ic_check_white_24dp)
      ToastType.WARNING -> prepareTipsType(R.drawable.ic_error_outline_white_24dp)
      ToastType.ERROR -> prepareTipsType(R.drawable.ic_clear_white_24dp)
    }
    val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1,
        View.MeasureSpec.AT_MOST)
    val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1,
        View.MeasureSpec.AT_MOST)
    mContentView?.measure(widthMeasureSpec, heightMeasureSpec)
  }

  fun prepareTipsType(@DrawableRes drawable: Int) {
    mContentView = mLayoutInflater.inflate(R.layout.view_toast_tips, null)
    val icon = mContentView?.findViewById(R.id.iv_icon) as ImageView
    icon.setImageResource(drawable)
    mMessageTextView = mContentView?.findViewById(
        work.wanghao.agiletoast.R.id.tv_message) as TextView?
    mMessageTextView?.setTextColor(mTextColor)
    mMessageTextView?.textSize = mTextSize
    mMessageTextView?.text = mMessage

  }

  fun prepareNormalType() {
    mContentView = mLayoutInflater.inflate(work.wanghao.agiletoast.R.layout.view_toast_normal,
        null)
    mMessageTextView = mContentView?.findViewById(
        work.wanghao.agiletoast.R.id.tv_message) as TextView?
    mMessageTextView?.setTextColor(mTextColor)
    mMessageTextView?.textSize = mTextSize
    mMessageTextView?.text = mMessage
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

  fun text(msg: String): AgileToast {
    mMessage = msg
    return this
  }

  fun textColor(@ColorInt color: Int): AgileToast {
    mTextColor = color
    return this
  }

  fun textSize(size: Float): AgileToast {
    mTextSize = size
    return this
  }

  fun bgColor(@ColorInt bgColor: Int): AgileToast {
    mToastBackgroundColor = bgColor
    return this
  }

  fun animation(animationType: AnimationType): AgileToast {
    mAnimationType = animationType
    return this
  }

  fun contentView(contentView: View): AgileToast {
    mContentView = contentView
    return this
  }

  fun contentView(@LayoutRes contentView: Int): AgileToast {
    mContentView = mLayoutInflater.inflate(contentView, null)
    return this
  }

  fun dismissCallback(callback: OnDismissCallback): AgileToast {
    mOnDismissCallback = callback
    return this
  }

  fun duration(duration: Duration, customDuration: Long?): AgileToast {
    if (duration == Duration.DURATION_CUSTOM) {
      if (customDuration == null || customDuration < 800) throw IllegalArgumentException(
          "when duration=Duration.DURATION_CUSTOM the customDuration must NOT NULL and the value >800")
      mCustomDuration = customDuration
    }
    mDuration = duration
    return this
  }

  fun type(toastType: ToastType): AgileToast {
    mType = toastType
    return this
  }

  fun gravity(gravity: Int): AgileToast {
    mGravity = gravity
    return this
  }

  fun offsetX(marginX: Int): AgileToast {
    mOffsetX = marginX
    return this
  }

  fun offsetY(marginY: Int): AgileToast {
    mOffsetY = marginY
    return this
  }

  fun style(style: ToastStyle): AgileToast {
    mStyle = style
    return this
  }
}