package jp.co.yumemi.android.code_check.di

import android.util.Log
import jp.co.yumemi.android.code_check.constants.Constants.API_HEADER_TYPE
import jp.co.yumemi.android.code_check.constants.Constants.TAG
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Includes Network Request and Headers
 */
class ServiceInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        /*
         * Modify network requests and headers
         */
        val modifiedRequest: Request = originalRequest.newBuilder()
            .header("Accept", API_HEADER_TYPE)
            .build()

        val url = modifiedRequest.url()
        Log.d(TAG, url.toString())
        Log.d(TAG, modifiedRequest.headers().toString())
        return chain.proceed(modifiedRequest)
    }

}
