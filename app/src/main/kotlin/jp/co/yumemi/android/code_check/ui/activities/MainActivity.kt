/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.activities

import androidx.appcompat.app.AppCompatActivity
import jp.co.yumemi.android.code_check.R
import java.util.*

class MainActivity : AppCompatActivity(R.layout.activity_top) {

    companion object {
        lateinit var lastSearchDate: Date
    }
}