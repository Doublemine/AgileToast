package work.wanghao.agiletoast.widget

import android.content.Context
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.WindowManager
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

  private val mQueue: Queue<AgileToast> = LinkedList()

  fun add(toast: AgileToast) {
    mQueue.offer(toast)
    showNextToast()
  }

  override fun handleMessage(msg: Message?) {
    if (msg != null) {
      when (msg.what) {
        STATUS_SHOW_TOAST -> showToast(msg.obj as AgileToast)
        STATUS_HIDE_TOAST -> removeToast(msg.obj as AgileToast)
        STATUS_SHOW_NEXT_TOAST -> showNextToast()
      }

    }
    super.handleMessage(msg)
  }

  fun removeToast(toast: AgileToast) {
    if (!toast.isShowing()) {
      mQueue.remove(toast)
      return
    }

    if (!mQueue.contains(toast)) return

    (toast.getContext().applicationContext.getSystemService(
        Context.WINDOW_SERVICE) as WindowManager?)?.removeView(toast.getContentView())
    sendEmptyMessage(STATUS_SHOW_NEXT_TOAST)
    mQueue.poll()
    if (toast.getDismissCallback() != null) toast.getDismissCallback()!!.onDismissCallback(toast)

  }

  fun showToast(toast: AgileToast) {
    if (toast.isShowing()) return

    (toast.getContext().applicationContext.getSystemService(
        Context.WINDOW_SERVICE) as WindowManager?)?.addView(toast.getContentView(),
        toast.getWindowManagerParams())

    val message = Message.obtain()
    message.what = STATUS_HIDE_TOAST
    message.obj = toast
    when (toast.getDuration()) {
      Duration.CUSTOM -> sendMessageDelayed(message, toast.getCustomDuration())
      Duration.LONG -> sendMessageDelayed(message, 2000)
      Duration.SHORT -> sendMessageDelayed(message, 3500)
    }
  }

  fun showNextToast() {
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