package com.aiytl.powerballPredictor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    public fun customToast(msg : String){
        val customToastLayout = layoutInflater.inflate(R.layout.layout_custom_toast,null)
        val customToast = Toast(this)
        customToast.view = customToastLayout
        var tvMsg = customToastLayout.findViewById<TextView>(R.id.tv_msg)
        tvMsg.text = msg
        customToast.setGravity(Gravity.BOTTOM,0,150)
        customToast.duration = Toast.LENGTH_LONG
        customToast.show()
    }
}