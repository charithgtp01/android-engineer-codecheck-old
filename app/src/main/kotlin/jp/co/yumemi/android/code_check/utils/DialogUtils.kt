package jp.co.yumemi.android.code_check.utils

import android.app.Dialog
import android.content.Context
import android.text.Html
import android.view.Window
import android.widget.TextView
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.utils.Utils.Companion.changeUiSize

class DialogUtils {
    companion object {
        fun showProgressDialog(context: Context?, message: String?): Dialog? {
            var dialog: Dialog? = null
            if (context != null) {
                dialog = Dialog(context, R.style.AppTheme_DialogNoActionBar)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.progress_dialog_layout)
                changeUiSize(context, dialog.findViewById(R.id.dialogMainLayout), 1, 1, 30)
                changeUiSize(context, dialog.findViewById(R.id.icon), 1, 3)
                val tvMessage = dialog.findViewById<TextView>(R.id.tvMessage)
                tvMessage.setText(Html.fromHtml(message), TextView.BufferType.SPANNABLE)
            }
            return dialog
        }
    }

}