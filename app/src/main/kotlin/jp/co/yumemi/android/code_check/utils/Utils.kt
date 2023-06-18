package jp.co.yumemi.android.code_check.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.DisplayMetrics
import android.util.Log
import android.view.View


/**
 * Utils class for Common methods
 */
class Utils {
    companion object {
        /**
         * Check Internet Status
         */
        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
        }

        /**
         * Convert dp values to px
         */
        private fun convertDpToPixel(dp: Float, context: Context?): Float {
            if (context != null)
                return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
            return 0.0f
        }

        /**
         * Change Element with according to the screen size
         * @param context Context of the View
         * @param view layout element
         * @param widthRatio View width according to widthRatio*(display/width)
         * @param width Screen divide in to (display/width)
         * Ex: width=4 and widthRatio=3 means Actual element width= (Screen Width/4)*3
         * @param margin Horizontal Margin
         */
        fun changeUiSize(context: Context?, view: View, widthRatio: Int, width: Int, margin: Int) {
            if (context != null) {
                val display = context.resources.displayMetrics
                val params = view.layoutParams
                params.width = (display.widthPixels * widthRatio / width - convertDpToPixel(
                    margin.toFloat(),
                    context
                ) * 2).toInt()
                view.layoutParams = params
            }
        }

        /**
         * Change Element with according to the screen size
         * Element has no margin
         * @param context Context of the View
         * @param view layout element
         * @param widthRatio View width according to widthRatio*(display/width)
         * @param width Screen divide in to (display/width)
         * Ex: width=4 and widthRatio=3 means Actual element width= (Screen Width/4)*3
         */
        fun changeUiSize(context: Context, view: View, widthRatio: Int, width: Int) {
            val display = context.resources.displayMetrics
            val params = view.layoutParams
            params.width = display.widthPixels * widthRatio / width
            view.layoutParams = params
        }


    }

}