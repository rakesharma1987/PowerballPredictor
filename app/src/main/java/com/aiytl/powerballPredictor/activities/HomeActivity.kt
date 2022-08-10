package com.aiytl.powerballPredictor.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.aiytl.powerballPredictor.R
import com.aiytl.powerballPredictor.databinding.ActivityHomeBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPowerball.setOnClickListener(this)
        binding.btnRateApp.setOnClickListener(this)
        binding.btnOtherApp.setOnClickListener(this)

        MobileAds.initialize(this)
        binding.bannerAdview.loadAd(AdRequest.Builder().build())

    }

    override fun onClick(view : View?) {
        when(view!!.id){
            R.id.btn_powerball ->{
                // TODO:
            }

            R.id.btn_other_app ->{
                // TODO:

            }

            R.id.btn_rate_app ->{
                val uri = Uri.parse("market://details?id="+"5422523287693933202")
                val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
                try {
                    startActivity(myAppLinkToMarket)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this, "Impossible to find an application for the market", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}