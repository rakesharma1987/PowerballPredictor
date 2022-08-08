package com.aiytl.powerballPredictor

import android.content.Context
import android.content.SharedPreferences

object GooglePlayBillingPreferences {
    private lateinit var prefs : SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private const val PREFS_NAME = "three_no"
    private const val IS_PURCHASED_3No : String = "is_purchased_three_no"
    private const val IS_PURCHASED_4No : String = "is_purchased_four_no"

    fun init(context: Context){
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = prefs.edit()
        editor.commit()
    }

    public fun setPurchasedValueForThreeNo(b : Boolean){
        editor.putBoolean(IS_PURCHASED_3No, b)
        editor.commit()
    }

    fun isPurchasedForThreeNo() : Boolean{
        return prefs.getBoolean(IS_PURCHASED_3No, false)
    }

    public fun setPurchasedValueForFourNo(b : Boolean){
        editor.putBoolean(IS_PURCHASED_4No, b)
        editor.commit()
    }

    fun isPurchasedForFourNo() : Boolean{
        return prefs.getBoolean(IS_PURCHASED_4No, false)
    }
}