package work.wanghao.agiletoast.widget

import android.animation.Animator
import android.os.Looper
import android.os.Message
import java.util.*

/**
 * @author doublemine
 *         Created  on 2017/02/08 15:52.
 *         Summary:
 */

class ToastHandler private constructor(looper: Looper) : android.os.Handler() {
  private val STATUS_SHOW_TOAST = 0x12
  private val STATUS_HIDE_TOAST = 0x23
  private val STATUS_SHOW_NEXT_TOAST = 0x34

  private val mQueue: Queue<AgileToast> = LinkedList<AgileToast>()

  fun add(toast: AgileToast) {
    mQueue.offer(toast)
    showNextToast(toast)
  }

  override fun handleMessage(msg: Message?) {
    if (msg != null) {
      if (msg.obj is AgileToast) {
        when (msg.what) {
          STATUS_SHOW_TOAST -> showToast(msg.obj as AgileToast)
          STATUS_HIDE_TOAST -> hideToast(msg.obj as AgileToast)
          STATUS_SHOW_NEXT_TOAST -> showNextToast(msg.obj as AgileToast)
        }
      }
    }
    super.handleMessage(msg)
  }

  fun hideToast(toast: AgileToast) {
    if (!toast.isShowing()) {
      mQueue.remove(toast)
      return
    }
    if (!mQueue.contains(toast)) return

    val animationSet = toast.getHideAnimationSet()
    animationSet.addListener(object : Animator.AnimatorListener {
      override fun onAnimationRepeat(p0: Animator?) {}

      override fun onAnimationEnd(p0: Animator?) {
        toast.getRootView().removeView(toast.getContainerView())
        if (toast.getDismissCallback() != null) {
          toast.getDismissCallback()?.onDismissCallback(toast)
        }
        sendEmptyMessage(STATUS_SHOW_NEXT_TOAST)
      }

      override fun onAnimationCancel(p0: Animator?) {}

      override fun onAnimationStart(p0: Animator?) {}

    })
    animationSet.start()
    mQueue.poll()
  }

  fun showToast(toast: AgileToast) {
    if (toast.isShowing()) return
    toast.getRootView().addView(toast.getContentView())

    val animationSet = toast.getShowAnimationSet()
    animationSet.start()
    val message = Message.obtain()
    message.what = STATUS_HIDE_TOAST
    message.obj = toast
    when (toast.getDuration()) {
      Duration.DURATION_CUSTOM -> sendMessageDelayed(message, toast.getCustomDuration())
      Duration.DURATION_LONG -> sendMessageDelayed(message, 2000)
      Duration.DURATION_SHORT -> sendMessageDelayed(message, 3500)
    }
  }

  fun showNextToast(toast: AgileToast) {
    if (mQueue.isEmpty()) return
    val queueToast = mQueue.peek()
    if (!queueToast.isShowing()) {
      val message = Message.obtain()
      message.what = STATUS_SHOW_TOAST
      message.obj = queueToast
      sendMessage(message)
    }
  }

  companion object {
    fun get(): ToastHandler {
      return Inner.single
    }
  }

  private object Inner {
    val single = ToastHandler(Looper.getMainLooper())
  }
}