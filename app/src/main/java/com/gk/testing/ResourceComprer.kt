package com.gk.testing

import android.content.Context

class ResourceComprer {

    fun isEqual(context: Context, resId: Int, string: String): Boolean {
        return context.getString(resId) == string
    }
}