package work.wanghao.agiletoast.callback

import work.wanghao.agiletoast.widget.AgileToast

/**
 * @author doublemine
 *         Created  on 2017/02/08 15:46.
 *         Summary:
 */
interface OnDismissCallback {

  /**
   * call when AgileToast dismiss
   */
  fun onDismissCallback(toast: AgileToast)

}