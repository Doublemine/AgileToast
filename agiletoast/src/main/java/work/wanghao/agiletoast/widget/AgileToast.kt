package work.wanghao.agiletoast.widget

import android.animation.AnimatorSet
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import work.wanghao.agiletoast.callback.OnDismissCallback

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
  private var mCustomDuration: Long = 3500
  private var mDuration: Duration? = null

  private var mRootView: ViewGroup? = null
  private var mContentView: View? = null

  fun isShowing(): Boolean {
    TODO()
  }

  fun getHideAnimationSet(): AnimatorSet {
    TODO()
  }

  fun getShowAnimationSet(): AnimatorSet {
    TODO()
  }

  fun getRootView(): ViewGroup {
    TODO()
  }

  fun getContainerView(): ViewGroup {
    TODO()
  }

  fun getContentView(): View {
    return mContentView!!
  }

  fun getDuration(): Duration {
    return mDuration!!
  }

  fun getCustomDuration(): Long {
    return mCustomDuration
  }

  fun getDismissCallback(): OnDismissCallback? {
    return mOnDismissCallback
  }

  fun show() {

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

  }

  fun setupTopView() {

  }

  fun setupBottomView() {

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

}