package jp.co.yumemi.android.code_check.utils

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.text.Html.fromHtml
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.interfaces.ErrorDialogButtonClickListener
import jp.co.yumemi.android.code_check.utils.Utils.Companion.changeUiSize

/**
 * Utils class for Dialogs and Alerts
 */
class DialogUtils {
    companion object {
        /**
         * Error Dialog with icon
         * @param error Error Message
         */
        fun showErrorDialog(
            context: Context,
            error: String?,
            errorDialogButtonClickListener: ErrorDialogButtonClickListener
        ) {
            Handler(Looper.getMainLooper()).post {
                Dialog(context, R.style.AppTheme_DialogNoActionBar).apply {
                    requestWindowFeature(Window.FEATURE_NO_TITLE)
                    setCancelable(false)
                    setContentView(R.layout.alert_dialog_layout)
                    changeUiSize(context, findViewById(R.id.dialogMainLayout), 1, 1, 30)
                    changeUiSize(context, findViewById(R.id.icon), 1, 3)
                    val tvMessage = findViewById<TextView>(R.id.tvMessage)
                    val button =
                        findViewById<Button>(R.id.button)

                    button.setOnClickListener {
                        dismiss()
                        errorDialogButtonClickListener.onButtonClick()
                    }
                    tvMessage.text = error
                    show()
                }
            }
        }

        /**
         * Progress Dialog
         * @param message progress message
         */
        fun showProgressDialog(context: Context?, message: String?): Dialog? {
            var dialog: Dialog? = null
            if (context != null) {
                dialog = Dialog(context, R.style.AppTheme_DialogNoActionBar).apply {
                    requestWindowFeature(Window.FEATURE_NO_TITLE)
                    setCancelable(false)
                    setContentView(R.layout.progress_dialog_layout)
                    changeUiSize(context, findViewById(R.id.dialogMainLayout), 1, 1, 30)
                    changeUiSize(context, findViewById(R.id.icon), 1, 3)
                    val tvMessage = findViewById<TextView>(R.id.tvMessage)
                    tvMessage.text = message
                }
            }
            return dialog
        }
    }

}