package work.wanghao.agiletoast.widget

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.annotation.StyleRes
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
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

open class AgileToast constructor(context: Context) {
  val mContext: Context = context
  private var mMessage: String? = null
  private var mType: ToastType = ToastType.NORMAL
  private val mLayoutInflater = LayoutInflater.from(context)
  var mOnDismissCallback: OnDismissCallback? = null

  private var mToastHandler: ToastHandler? = null
  var mCustomDuration: Long = 2000
    private set
  var mDuration: Duration = Duration.SHORT
    private set

  var mContentView: View? = null
    private set

  private var mToastBackGroundDrawable: GradientDrawable? = null
  private var mMessageTextView: TextView? = null
  private var mTextSize = 14.toFloat()
  private var mTextColor = Color.WHITE
  private var mToastBackgroundColor = 0
  private var mGravity: Int = Gravity.CENTER
  private var mStyle = ToastStyle.NORMAL

  private var mOffsetX: Int = 0
  private var mOffsetY: Int = 0
  private var mHeight: Int = FrameLayout.LayoutParams.WRAP_CONTENT
  private var mAnimationType: AnimationType = AnimationType.DEFAULT

  private var mCompatNougatGrant = false
  private var mCompatNougatRequest = false
  private var mCompatNougat = false

  @StyleRes private var mAnimation: Int? = null

  fun isShowing(): Boolean {
    return mContentView != null && mContentView!!.isShown
  }

  fun show() {
    if (mMessage.isNullOrEmpty()) return
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
    layoutParams.height = mHeight
    when (mStyle) {
      ToastStyle.FILL -> layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT
      else -> {
        layoutParams.width = FrameLayout.LayoutParams.WRAP_CONTENT
      }
    }
    layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
    layoutParams.format = PixelFormat.TRANSLUCENT
    layoutParams.windowAnimations = AnimationUtils.getWindowShowAnimation(mAnimationType)
    if (mAnimation != null) {
      layoutParams.windowAnimations = mAnimation!!
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
        if (mCompatNougat) compatNougat(layoutParams)
        else layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST
      } else layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST
    } else layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
    layoutParams.gravity = mGravity
    layoutParams.y = mOffsetY
    layoutParams.x = mOffsetX
    return layoutParams
  }

  private fun onFinalExecute() {
    when (mType) {
      ToastType.ERROR -> {
        if (mStyle == ToastStyle.NORMAL) mToastBackGroundDrawable = ViewUtils.getNormalBackground(
            ResourcesCompat.getColor(mContext.resources, R.color.colorError, null))
        else if (mStyle == ToastStyle.FILL) mToastBackGroundDrawable = ViewUtils.getFillBackground(
            ResourcesCompat.getColor(mContext.resources, R.color.colorError, null))
        else mToastBackGroundDrawable = ViewUtils.getCornerBackground(
            ResourcesCompat.getColor(mContext.resources, R.color.colorError, null))
      }
      ToastType.SUCCESS -> {
        if (mStyle == ToastStyle.NORMAL) mToastBackGroundDrawable = ViewUtils.getNormalBackground(
            ResourcesCompat.getColor(mContext.resources, R.color.colorSuccess, null))
        else if (mStyle == ToastStyle.FILL) mToastBackGroundDrawable = ViewUtils.getFillBackground(
            ResourcesCompat.getColor(mContext.resources, R.color.colorSuccess, null))
        else mToastBackGroundDrawable = ViewUtils.getCornerBackground(
            ResourcesCompat.getColor(mContext.resources, R.color.colorSuccess, null))
      }
      ToastType.WARNING -> {
        if (mStyle == ToastStyle.NORMAL) mToastBackGroundDrawable = ViewUtils.getNormalBackground(
            ResourcesCompat.getColor(mContext.resources, R.color.colorWarning, null))
        else if (mStyle == ToastStyle.FILL) mToastBackGroundDrawable = ViewUtils.getFillBackground(
            ResourcesCompat.getColor(mContext.resources, R.color.colorWarning, null))
        else mToastBackGroundDrawable = ViewUtils.getCornerBackground(
            ResourcesCompat.getColor(mContext.resources, R.color.colorWarning, null))
      }
      else -> {
        if (mStyle == ToastStyle.NORMAL) mToastBackGroundDrawable = ViewUtils.getNormalBackground(
            Color.GRAY)
        else if (mStyle == ToastStyle.FILL) mToastBackGroundDrawable = ViewUtils.getFillBackground(
            Color.GRAY)
        else mToastBackGroundDrawable = ViewUtils.getCornerBackground(Color.GRAY)
        if (mToastBackgroundColor != 0) {
          mToastBackGroundDrawable?.setColor(mToastBackgroundColor)
        }
      }
    }
    mContentView?.background = mToastBackGroundDrawable
  }

  private fun compatNougat(layoutParams: WindowManager.LayoutParams) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      if (!Settings.canDrawOverlays(mContext) && !mCompatNougatRequest) {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
        intent.data = Uri.parse("package:${mContext.applicationContext.packageName}")
        ContextCompat.startActivity(mContext, intent, null)
      }
      mCompatNougatGrant = Settings.canDrawOverlays(mContext)
      if (mCompatNougatGrant) layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
      else layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST
    }
  }

  private fun onPrepareExecute() {
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

  private fun prepareTipsType(@DrawableRes drawable: Int) {
    mContentView = mLayoutInflater.inflate(R.layout.view_toast_tips, null)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) mContentView?.elevation = 3f
    val icon = mContentView?.findViewById(R.id.iv_icon) as ImageView
    icon.setImageResource(drawable)
    mMessageTextView = mContentView?.findViewById(
        work.wanghao.agiletoast.R.id.tv_message) as TextView?
    mMessageTextView?.setTextColor(mTextColor)
    mMessageTextView?.textSize = mTextSize
    mMessageTextView?.text = mMessage

  }

  private fun prepareNormalType() {
    mContentView = mLayoutInflater.inflate(work.wanghao.agiletoast.R.layout.view_toast_normal,
        null)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) mContentView?.elevation = 3f
    mMessageTextView = mContentView?.findViewById(
        work.wanghao.agiletoast.R.id.tv_message) as TextView?
    mMessageTextView?.setTextColor(mTextColor)
    mMessageTextView?.textSize = mTextSize
    mMessageTextView?.text = mMessage
  }


  constructor(context: Context, msg: String?) : this(context) {
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

  fun text(msg: String?): AgileToast {
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
    if (duration == Duration.CUSTOM) {
      if (customDuration == null || customDuration < 800) throw IllegalArgumentException(
          "when duration=Duration.CUSTOM the customDuration must NOT NULL and the value >800")
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

  fun height(height: Int): AgileToast {
    mHeight = height
    return this
  }

  fun animation(@StyleRes animation: Int): AgileToast {
    mAnimation = animation
    return this
  }

  fun nougatCompat(compatNougatAnimation: Boolean): AgileToast {
    mCompatNougat = compatNougatAnimation
    return this
  }
}