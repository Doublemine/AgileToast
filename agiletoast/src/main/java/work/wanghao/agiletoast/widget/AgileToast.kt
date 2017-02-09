package work.wanghao.agiletoast.widget

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.GradientDrawable
import android.support.annotation.ColorInt
import android.support.annotation.LayoutRes
import android.view.*
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
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

  private var mContainerView: ViewGroup? = null
  private var mContentView: View? = null

  private var mToastBackGroundDrawable: GradientDrawable? = null
  private var mMessageTextView: TextView? = null
  private var mTextSize = 14.toFloat()
  private var mTextColor = Color.WHITE
  private var mToastBackgroundColor = 0

  private var mAnimationType: AnimationType = AnimationType.ANIMATION_DEFAULT

  fun isShowing(): Boolean {
    return mContentView != null && mContentView!!.isShown
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
    onPrepareExecute()
    mToastHandler = ToastHandler.get()
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
    layoutParams.gravity = Gravity.BOTTOM or Gravity.CENTER
    layoutParams.y = 200
    return layoutParams
  }

  fun onPrepareExecute() {
    when (mType) {
      ToastType.NORMAL -> prepareNormalType()
      ToastType.CLICK -> TODO()
      ToastType.SUCCESS_RADIUS -> TODO()
      ToastType.WARNING_RADIUS -> TODO()
      ToastType.ERROR_RADIUS -> TODO()
    }

    val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1,
        View.MeasureSpec.AT_MOST)
    val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1,
        View.MeasureSpec.AT_MOST)
    mContentView?.measure(widthMeasureSpec, heightMeasureSpec)
  }

  fun prepareNormalType() {
    mContainerView = LinearLayout(mContext)
    val viewGroupParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
        FrameLayout.LayoutParams.WRAP_CONTENT)
    viewGroupParams.gravity = Gravity.BOTTOM or Gravity.CENTER
    viewGroupParams.bottomMargin = 200
    mContainerView?.layoutParams = viewGroupParams

    if (mContentView == null) {
      mContentView = mLayoutInflater.inflate(work.wanghao.agiletoast.R.layout.view_toast_normal,
          mContainerView, false)
      mToastBackGroundDrawable = ViewUtils.getCornerBackground(Color.GREEN)
      mMessageTextView = mContentView?.findViewById(
          work.wanghao.agiletoast.R.id.tv_message) as TextView?
      mMessageTextView?.setTextColor(mTextColor)
      mMessageTextView?.textSize = mTextSize
      mMessageTextView?.text = mMessage
      if (mToastBackgroundColor != 0) {
        mToastBackGroundDrawable?.setColor(mToastBackgroundColor)
      }
      mContentView?.background = mToastBackGroundDrawable
    }
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

  fun setTextColor(@ColorInt color: Int): AgileToast {
    mTextColor = color
    return this
  }

  fun setTextSize(size: Float): AgileToast {
    mTextSize = size
    return this
  }

  fun setToastBackgroundColor(@ColorInt bgColor: Int): AgileToast {
    mToastBackgroundColor = bgColor
    return this
  }

  fun setAnimationType(animationType: AnimationType): AgileToast {
    mAnimationType = animationType
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