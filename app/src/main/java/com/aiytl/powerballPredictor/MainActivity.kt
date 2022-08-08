package com.aiytl.powerballPredictor

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aiytl.powerballPredictor.databinding.ActivityMainBinding
import com.aiytl.powerballPredictor.databinding.LayoutDialogNumbersBinding
import com.aiytl.powerballPredictor.model.SixNumber
import com.android.billingclient.api.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.math.BigInteger

class MainActivity : BaseActivity(), View.OnClickListener {
    private lateinit var context: Context
    private lateinit var binding: ActivityMainBinding
    private lateinit var dialogBinding: LayoutDialogNumbersBinding
    private lateinit var dialog: Dialog
    private var selectedNumber = ArrayList<String>()
    private val listOfFirst5Cols = listOf<String>(
        "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
        "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
        "51", "52", "53", "54", "55", "56", "57", "58", "59", "60",
        "61", "62", "63", "64", "65", "66", "67", "68", "69", "70",
        "71", "72", "73", "74", "75", "76", "77", "78", "79", "80"
    )
    private val listOfFirst6Col = listOf<String>(
        "10",
        "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
        "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
        "31", "32", "33", "34", "35", "36", "37", "38", "39", "40"
    )

    private var sumCol17 : Int? = 0
    private var sumCol28 : Int? = 0
    private var sumCol39 : Int? = 0
    private var sumCol410 : Int? = 0
    private var sumCol511 : Int? = 0
    private var sumCol612 : Int? = 0

    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "MainActivity"
    private lateinit var billingClient : BillingClient
    lateinit var skulList : ArrayList<String>
    private lateinit var fbInterstitialAdd : InterstitialAd


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this

        MobileAds.initialize(this)
        binding.bannerAdview.loadAd(AdRequest.Builder().build())



        binding.recyclerview6column.layoutManager = LinearLayoutManager(this)

        binding.llFirst5col.setOnClickListener(this)
        binding.llFirst6col.setOnClickListener(this)
        binding.llTv1.setOnClickListener(this)
        binding.llTv2.setOnClickListener(this)
        binding.llTv3.setOnClickListener(this)
        binding.llTv4.setOnClickListener(this)
        binding.llTv5.setOnClickListener(this)
        binding.llTv6.setOnClickListener(this)
        binding.llTv7.setOnClickListener(this)
        binding.llTv8.setOnClickListener(this)
        binding.llTv9.setOnClickListener(this)
        binding.llTv10.setOnClickListener(this)
        binding.llTv11.setOnClickListener(this)
        binding.llTv12.setOnClickListener(this)

        binding.btnGen2nos.setOnClickListener(this)
        binding.btnGen40nos.setOnClickListener(this)

        if (!GooglePlayBillingPreferences.isPurchasedForThreeNo()){
            binding.btnGen40nos.text = "Buy Now"
        }else{
            binding.btnGen40nos.text = "Generate 40's Rows"
        }

        val purchasesUpdatedListener =
            PurchasesUpdatedListener { billingResult, purchases ->
                // To be implemented in a later section.
            }

        billingClient = BillingClient.newBuilder(context)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()

        skulList = ArrayList<String>()
        skulList.add("android.test.purchased")

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode ==  BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                }
            }
            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })

    }

    override fun onClick(view: View?) {
        dialogBinding = LayoutDialogNumbersBinding.inflate(layoutInflater)
        dialogBinding.rvNumbers.layoutManager = GridLayoutManager(this@MainActivity, 10)

        dialog = Dialog(this@MainActivity)
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(false)
        val window = dialog.window
        window!!.setLayout(
            GridLayout.LayoutParams.MATCH_PARENT,
            GridLayout.LayoutParams.WRAP_CONTENT
        )

        when (view!!.id) {
            R.id.ll_first_5col -> {
                dialog.show()
                val adapter = MyNumbersAdapter(listOfFirst5Cols, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@MainActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                binding.tv1First5column.text = listOfFirst5Cols[position]
                                dialog.dismiss()
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_first_6col ->{
                dialog.show()
                val adapter = MyNumbersAdapter(listOfFirst6Col, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@MainActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                binding.tv1First6column.text = listOfFirst6Col[position]
                                dialog.dismiss()
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            }

            R.id.ll_tv1 ->{
                if(binding.tv1First5column.text == "-"){
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tv1First5column.text.toString())){
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@MainActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if(selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])){
                                    customToast("Already selected, please select another number.")
                                }else {
                                    binding.tv1.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv2 ->{
                if(binding.tv1First5column.text == "-"){
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tv1First5column.text.toString())){
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@MainActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if(selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])){
                                    customToast("Already selected, please select another number.")
                                }else {
                                    binding.tv2.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv3 ->{
                if(binding.tv1First5column.text == "-"){
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tv1First5column.text.toString())){
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@MainActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if(selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])){
                                    customToast("Already selected, please select another number.")
                                }else {
                                    binding.tv3.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv4 ->{
                if(binding.tv1First5column.text == "-"){
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tv1First5column.text.toString())){
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@MainActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if(selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])){
                                    customToast("Already selected, please select another number.")
                                }else {
                                    binding.tv4.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv5 ->{
                if(binding.tv1First5column.text == "-"){
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tv1First5column.text.toString())){
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@MainActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if(selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])){
                                    customToast("Already selected, please select another number.")
                                }else {
                                    binding.tv5.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv6 ->{
                if(binding.tv1First6column.text == "-"){
                    customToast("First, Select a number from first 6 box.")
                    return
                }
                else if(binding.tv1.text.toString() == "-" || binding.tv2.text.toString() == "-" || binding.tv3.text.toString() == "-"
                    || binding.tv4.text.toString() == "-" || binding.tv5.text.toString() == "-"){
                    customToast("Please select previous box/boxes of this row.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tv1First6column.text.toString())){
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@MainActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if(selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])){
                                    customToast("Already selected, please select another number.")
                                }else{
                                    binding.tv6.text = list[position]
                                    selectedNumber.clear()
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv7 ->{
                if(binding.tv1First5column.text == "-"){
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tv1First5column.text.toString())){
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@MainActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if(selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])){
                                    customToast("Already selected, please select another number.")
                                }
                                binding.tv7.text = list[position]
                                selectedNumber.add(list[position])
                                dialog.dismiss()
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv8 ->{
                if(binding.tv1First5column.text == "-"){
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tv1First5column.text.toString())){
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@MainActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if(selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])){
                                    customToast("Already selected, please select another number.")
                                }else {
                                    binding.tv8.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv9 ->{
                if(binding.tv1First5column.text == "-"){
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tv1First5column.text.toString())){
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@MainActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if(selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])){
                                    customToast("Already selected, please select another number.")
                                }else {
                                    binding.tv9.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv10 ->{
                if(binding.tv1First5column.text == "-"){
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tv1First5column.text.toString())){
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@MainActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if(selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])){
                                    customToast("Already selected, please select another number.")
                                }else {
                                    binding.tv10.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv11 ->{
                if(binding.tv1First5column.text == "-"){
                    customToast("First, Select a number from first 5 box.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tv1First5column.text.toString())){
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@MainActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if(selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])){
                                    customToast("Already selected, please select another number.")
                                }else {
                                    binding.tv11.text = list[position]
                                    selectedNumber.add(list[position])
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv12 ->{
                if(binding.tv1First6column.text == "-"){
                    customToast("First, Select a number from first 6 box.")
                    return
                }
                else if(binding.tv7.text.toString() == "-" || binding.tv8.text.toString() == "-" || binding.tv9.text.toString() == "-"
                    || binding.tv10.text.toString() == "-" || binding.tv11.text.toString() == "-"){
                    customToast("Please select previous box/boxes of this row.")
                    return
                }
                dialog.show()

                var list = ArrayList<String>()
                for (i in 1..Integer.parseInt(binding.tv1First6column.text.toString())){
                    list.add(i.toString())
                }
                val adapter = MyNumbersAdapter(list, selectedNumber)
                dialogBinding.rvNumbers.adapter = adapter
                dialogBinding.tvClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rvNumbers.addOnItemTouchListener(
                    RecyclerItemClickListenr(this@MainActivity, dialogBinding.rvNumbers,
                        object : RecyclerItemClickListenr.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                if(selectedNumber.isNotEmpty() && selectedNumber.contains(list[position])){
                                    customToast("Already selected, please select another number.")
                                }else {
                                    binding.tv12.text = list[position]
                                    selectedNumber.clear()
                                    dialog.dismiss()
                                }
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.btn_gen_2nos -> {
                var adRequest = AdRequest.Builder().build()
                InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        adError?.toString()?.let { Log.d(TAG, it) }
                        mInterstitialAd = null
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        Log.d(TAG, "Ad was loaded.")
                        mInterstitialAd = interstitialAd
                    }
                })

                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(this)
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.")
                }

                if(binding.tv1First5column.text.toString() == "-" || binding.tv1First6column.text.toString() == "-"
                    || binding.tv1.text.toString() == "-" || binding.tv2.text.toString() == "-" || binding.tv3.text.toString() == "-"
                    || binding.tv4.text.toString() == "-" || binding.tv5.text.toString() == "-" || binding.tv6.text.toString() == "-"
                    || binding.tv7.text.toString() == "-" || binding.tv8.text.toString() == "-" || binding.tv9.text.toString() == "-"
                    || binding.tv10.text.toString() == "-" || binding.tv11.text.toString() == "-" || binding.tv12.text.toString() == "-"){
                    customToast("Result can't predict, please select number by clicking box.")
                    return
                }
                sumCol17 = Integer.parseInt(binding.tv1.text.toString()).plus(Integer.parseInt(binding.tv7.text.toString()))
                sumCol28 = Integer.parseInt(binding.tv2.text.toString()).plus(Integer.parseInt(binding.tv8.text.toString()))
                sumCol39 = Integer.parseInt(binding.tv3.text.toString()).plus(Integer.parseInt(binding.tv9.text.toString()))
                sumCol410 = Integer.parseInt(binding.tv4.text.toString()).plus(Integer.parseInt(binding.tv10.text.toString()))
                sumCol511 = Integer.parseInt(binding.tv5.text.toString()).plus(Integer.parseInt(binding.tv11.text.toString()))
                sumCol612 = Integer.parseInt(binding.tv6.text.toString()).plus(Integer.parseInt(binding.tv12.text.toString()))

                var sumDigits17 : Int = getSumOfDigits(sumCol17!!)
                var sumDigits28 : Int = getSumOfDigits(sumCol28!!)
                var sumDigits39 : Int = getSumOfDigits(sumCol39!!)
                var sumDigits410 : Int = getSumOfDigits(sumCol410!!)
                var sumDigits511 : Int = getSumOfDigits(sumCol511!!)
                var sumDigits612 : Int = getSumOfDigits(sumCol612!!)

                val powSumDigits17 = BigInteger(sumDigits17.toString()).pow(80)
                val powSumDigits28 = BigInteger(sumDigits28.toString()).pow(80)
                val powSumDigits39 = BigInteger(sumDigits39.toString()).pow(80)
                val powSumDigits410 = BigInteger(sumDigits410.toString()).pow(80)
                val powSumDigits511 = BigInteger(sumDigits511.toString()).pow(80)
                val powSumDigits612 = BigInteger(sumDigits612.toString()).pow(80)

                val result17 = powSumDigits17.toString().replace('0', '1').map { it.toString()}.toTypedArray()
                val result28 = powSumDigits28.toString().replace('0', '1').map { it.toString()}.toTypedArray()
                val result39 = powSumDigits39.toString().replace('0', '1').map { it.toString()}.toTypedArray()
                val result410 = powSumDigits410.toString().replace('0', '1').map { it.toString()}.toTypedArray()
                val result511 = powSumDigits511.toString().replace('0', '1').map { it.toString()}.toTypedArray()
                val result612 = powSumDigits612.toString().replace('0', '1').map { it.toString()}.toTypedArray()
                var num1 : String? = null
                var num2 : String? = null
                var num3 : String? = null
                var num4 : String? = null
                var num5 : String? = null
                var num6 : String? = null

                var sixNumberList = ArrayList<SixNumber>()
                for (i in 1..2){
                    if (i==1){
                        num1 = result17[25]+result17[26]
                        num2 = result28[26]+result28[33]
                        num3 = result39[33]+result39[34]
                        num4 = result410[34]+result410[35]
                        num5 = result511[35]+result511[26]
                        num6 = result612[26]+result612[25]
                        if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                        }else{
                            num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                        }
                        if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                        }else{
                            num2 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                        }
                        if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                        }else{
                            num3 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                        }
                        if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                        }else{
                            num4 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                        }
                        if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                        }else{
                            num5 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                        }
                        if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                        }else{
                            num6 = binding.tv1First6column.text.toString().toInt()!!.minus(9).toString()
                        }
                        if (num1.toInt() == num2.toInt()) num2 = num2.toInt().plus(9).toString()
                        if (num1.toInt() == num3.toInt()) num3 = num3.toInt().plus(9).toString()
                        if (num1.toInt() == num4.toInt()) num4 = num4.toInt().plus(9).toString()
                        if (num1.toInt() == num5.toInt()) num5 = num5.toInt().plus(9).toString()
                        if (num1.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                        if (num2.toInt() == num3.toInt()) num3 = num3.toInt().plus(9).toString()
                        if (num2.toInt() == num4.toInt()) num4 = num4.toInt().plus(9).toString()
                        if (num2.toInt() == num5.toInt()) num5 = num5.toInt().plus(9).toString()
                        if (num2.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                        if (num3.toInt() == num4.toInt()) num4 = num4.toInt().plus(9).toString()
                        if (num3.toInt() == num5.toInt()) num5 = num5.toInt().plus(9).toString()
                        if (num3.toInt() == num6.toInt()) num6 = num2.toInt().plus(9).toString()

                        if (num4.toInt() == num5.toInt()) num5 = num5.toInt().plus(9).toString()
                        if (num4.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                        if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                        var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==2){
                        num1 = result28[8]+result28[9]
                        num2 = result28[8]+result28[9]
                        num3 = result39[9]+result39[33]
                        num4 = result410[33]+result410[11]
                        num5 = result511[11]+result612[12]
                        num6 = result612[12]+result511[27]
                        if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                        }else{
                            num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                        }
                        if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                        }else{
                            num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                        }
                        if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                        }else{
                            num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                        }
                        if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                        }else{
                            num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                        }
                        if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                        }else{
                            num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                        }
                        if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                        }else{
                            num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                        }
                        if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                        if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                        if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                        if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                        if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                        if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                        if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                        if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                        if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                        if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                        if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                        if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                        if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                        if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                        if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                        var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                        sixNumberList.add(sixNumbers)
                    }
                }

                var sixNumberAdapter = SixNumberAdapter(sixNumberList)
                binding.recyclerview6column.adapter = sixNumberAdapter

            }

            R.id.btn_gen_40nos -> {
                if (GooglePlayBillingPreferences.isPurchasedForThreeNo()) {
                    generate40sRows()
                } else {
                    billingClient.startConnection(object : BillingClientStateListener {
                        override fun onBillingSetupFinished(billingResult: BillingResult) {
                            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                                val params = SkuDetailsParams.newBuilder()
                                params.setSkusList(skulList)
                                    .setType(BillingClient.SkuType.INAPP)

                                billingClient.querySkuDetailsAsync(params.build()) { billingResult, skuDetailList ->

                                    for (skuDetail in skuDetailList!!) {
                                        val flowPurchase = BillingFlowParams.newBuilder()
                                            .setSkuDetails(skuDetail)
                                            .build()

                                        val responseCode = billingClient.launchBillingFlow(
                                            this@MainActivity,
                                            flowPurchase
                                        ).responseCode
                                        if (responseCode == 0) {
                                            GooglePlayBillingPreferences.setPurchasedValueForThreeNo(true)
                                            binding.btnGen40nos.text = "Generate 40's Rows"
                                        }
                                    }
                                }
                            }
                        }

                        override fun onBillingServiceDisconnected() {
                            // Try to restart the connection on the next request to
                            // Google Play by calling the startConnection() method.
                            GooglePlayBillingPreferences.setPurchasedValueForThreeNo(false)
                        }
                    })
                }
            }

        }
    }

    /* function to get sum of digits */
    fun getSumOfDigits(number: Int): Int {
        var number = number
        var sum = 0
        while (number > 0) {
            val r = number % 10
            sum += r
            number /= 10
        }
        return sum
    }

    private fun generate40sRows(){
        if(binding.tv1First5column.text.toString() == "-" || binding.tv1First6column.text.toString() == "-"
            || binding.tv1.text.toString() == "-" || binding.tv2.text.toString() == "-" || binding.tv3.text.toString() == "-"
            || binding.tv4.text.toString() == "-" || binding.tv5.text.toString() == "-" || binding.tv6.text.toString() == "-"
            || binding.tv7.text.toString() == "-" || binding.tv8.text.toString() == "-" || binding.tv9.text.toString() == "-"
            || binding.tv10.text.toString() == "-" || binding.tv11.text.toString() == "-" || binding.tv12.text.toString() == "-"){
            customToast("Result can't predict, please select number by clicking box.")
            return
        }
        sumCol17 = Integer.parseInt(binding.tv1.text.toString()).plus(Integer.parseInt(binding.tv7.text.toString()))
        sumCol28 = Integer.parseInt(binding.tv2.text.toString()).plus(Integer.parseInt(binding.tv8.text.toString()))
        sumCol39 = Integer.parseInt(binding.tv3.text.toString()).plus(Integer.parseInt(binding.tv9.text.toString()))
        sumCol410 = Integer.parseInt(binding.tv4.text.toString()).plus(Integer.parseInt(binding.tv10.text.toString()))
        sumCol511 = Integer.parseInt(binding.tv5.text.toString()).plus(Integer.parseInt(binding.tv11.text.toString()))
        sumCol612 = Integer.parseInt(binding.tv6.text.toString()).plus(Integer.parseInt(binding.tv12.text.toString()))

        var sumDigits17 : Int = getSumOfDigits(sumCol17!!)
        var sumDigits28 : Int = getSumOfDigits(sumCol28!!)
        var sumDigits39 : Int = getSumOfDigits(sumCol39!!)
        var sumDigits410 : Int = getSumOfDigits(sumCol410!!)
        var sumDigits511 : Int = getSumOfDigits(sumCol511!!)
        var sumDigits612 : Int = getSumOfDigits(sumCol612!!)

        val powSumDigits17 = BigInteger(sumDigits17.toString()).pow(80)
        val powSumDigits28 = BigInteger(sumDigits28.toString()).pow(80)
        val powSumDigits39 = BigInteger(sumDigits39.toString()).pow(80)
        val powSumDigits410 = BigInteger(sumDigits410.toString()).pow(80)
        val powSumDigits511 = BigInteger(sumDigits511.toString()).pow(80)
        val powSumDigits612 = BigInteger(sumDigits612.toString()).pow(80)

        val result17 = powSumDigits17.toString().replace('0', '1').map { it.toString()}.toTypedArray()
        val result28 = powSumDigits28.toString().replace('0', '1').map { it.toString()}.toTypedArray()
        val result39 = powSumDigits39.toString().replace('0', '1').map { it.toString()}.toTypedArray()
        val result410 = powSumDigits410.toString().replace('0', '1').map { it.toString()}.toTypedArray()
        val result511 = powSumDigits511.toString().replace('0', '1').map { it.toString()}.toTypedArray()
        val result612 = powSumDigits612.toString().replace('0', '1').map { it.toString()}.toTypedArray()

        var num1 : String? = null
        var num2 : String? = null
        var num3 : String? = null
        var num4 : String? = null
        var num5 : String? = null
        var num6 : String? = null

        if(result17.size == 1 || result28.size == 1 || result39.size == 1 || result410.size == 1 || result511.size == 1 || result612.size == 1){
            customToast("Result can't predict, please select number by clicking box.")
            return
        }
        var sixNumberList = ArrayList<SixNumber>()

        for (i in 1..40){
            if (i==1){
                num1 = result17[25]+result17[26]
                num2 = result28[26]+result28[33]
                num3 = result39[33]+result39[34]
                num4 = result410[34]+result410[35]
                num5 = result511[35]+result511[26]
                num6 = result612[26]+result612[25]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==2){
                num1 = result28[8]+result28[9]
                num2 = result28[8]+result28[9]
                num3 = result39[9]+result39[33]
                num4 = result410[33]+result410[11]
                num5 = result511[11]+result612[12]
                num6 = result612[12]+result511[27]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==3){
                num1 = result28[13]+result28[14]
                num2 = result28[14]+result28[15]
                num3 = result39[15]+result39[16]
                num4 = result410[16]+result410[17]
                num5 = result511[17]+result612[18]
                num6 = result612[18]+result511[19]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==4){
                num1 = result28[19]+result28[36]
                num2 = result28[36]+result28[21]
                num3 = result39[21]+result39[22]
                num4 = result410[22]+result410[23]
                num5 = result511[23]+result612[24]
                num6 = result612[24]+result511[25]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==5){
                num1 = result28[24]+result28[23]
                num2 = result28[23]+result28[22]
                num3 = result39[22]+result39[21]
                num4 = result410[21]+result410[36]
                num5 = result511[36]+result612[19]
                num6 = result612[19]+result511[26]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==6){
                num1 = result28[18]+result28[17]
                num2 = result28[27]+result28[28]
                num3 = result39[16]+result39[15]
                num4 = result410[15]+result410[13]
                num5 = result511[14]+result612[13]
                num6 = result612[29]+result511[30]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==7){
                num1 = result28[12]+result28[11]
                num2 = result28[11]+result28[33]
                num3 = result39[33]+result39[19]
                num4 = result410[25]+result410[28]
                num5 = result511[18]+result612[27]
                num6 = result612[17]+result511[28]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==8){
                num1 = result28[16]+result28[36]
                num2 = result28[15]+result28[19]
                num3 = result39[14]+result39[23]
                num4 = result410[23]+result410[22]
                num5 = result511[27]+result612[18]
                num6 = result612[14]+result511[13]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==9){
                num1 = result28[11]+result28[13]
                num2 = result28[13]+result28[15]
                num3 = result39[15]+result39[17]
                num4 = result410[27]+result410[9]
                num5 = result511[9]+result612[11]
                num6 = result612[11]+result511[27]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==10){
                num1 = result28[11]+result28[13]
                num2 = result28[13]+result28[15]
                num3 = result39[15]+result39[17]
                num4 = result410[17]+result410[19]
                num5 = result511[19]+result612[12]
                num6 = result612[21]+result511[19]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==11){
                num1 = result28[21]+result28[23]
                num2 = result28[23]+result28[25]
                num3 = result39[25]+result39[33]
                num4 = result410[33]+result410[35]
                num5 = result511[35]+result612[27]
                num6 = result612[27]+result511[35]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==12){
                num1 = result28[27]+result28[33]
                num2 = result28[33]+result28[13]
                num3 = result39[13]+result39[16]
                num4 = result410[16]+result410[19]
                num5 = result511[19]+result612[22]
                num6 = result612[22]+result511[19]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==13){
                num1 = result28[22]+result28[24]
                num2 = result28[24]+result28[25]
                num3 = result39[25]+result39[26]
                num4 = result410[26]+result410[33]
                num5 = result511[33]+result612[34]
                num6 = result612[34]+result511[25]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==14){
                num1 = result28[26]+result28[34]
                num2 = result28[34]+result28[26]
                num3 = result39[26]+result39[26]
                num4 = result410[8]+result410[18]
                num5 = result511[18]+result612[12]
                num6 = result612[12]+result511[26]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==15){
                num1 = result28[12]+result28[14]
                num2 = result28[14]+result28[16]
                num3 = result39[16]+result39[18]
                num4 = result410[18]+result410[36]
                num5 = result511[36]+result612[22]
                num6 = result612[22]+result511[21]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==16){
                num1 = result28[24]+result28[18]
                num2 = result28[18]+result28[12]
                num3 = result39[12]+result39[26]
                num4 = result410[26]+result410[26]
                num5 = result511[26]+result612[25]
                num6 = result612[25]+result511[26]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==17){
                num1 = result28[33]+result28[26]
                num2 = result28[26]+result28[9]
                num3 = result39[9]+result39[12]
                num4 = result410[12]+result410[15]
                num5 = result511[15]+result612[18]
                num6 = result612[18]+result511[17]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==18){
                num1 = result28[34]+result28[8]
                num2 = result28[8]+result28[12]
                num3 = result39[12]+result39[16]
                num4 = result410[16]+result410[36]
                num5 = result511[36]+result612[24]
                num6 = result612[24]+result511[26]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==19){
                num1 = result28[24]+result28[36]
                num2 = result28[36]+result28[16]
                num3 = result39[16]+result39[12]
                num4 = result410[12]+result410[8]
                num5 = result511[8]+result612[34]
                num6 = result612[34]+result511[8]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==20){
                num1 = result28[18]+result28[15]
                num2 = result28[15]+result28[12]
                num3 = result39[12]+result39[9]
                num4 = result410[9]+result410[26]
                num5 = result511[26]+result612[33]
                num6 = result612[33]+result511[26]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==21){
                num1 = result28[25]+result28[26]
                num2 = result28[26]+result28[26]
                num3 = result39[26]+result39[12]
                num4 = result410[12]+result410[18]
                num5 = result511[18]+result612[24]
                num6 = result612[24]+result511[23]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==22){
                var sixNumbers = SixNumber(result17[27]+result17[34], result28[14]+result28[21], result39[21]+result39[26], result410[26]+result410[34], result511[34]+result511[26], result612[26]+result612[26])
                sixNumberList.add(sixNumbers)
            }
            if (i==23){
                num1 = result28[26]+result28[34]
                num2 = result28[34]+result28[26]
                num3 = result39[26]+result39[21]
                num4 = result410[21]+result410[14]
                num5 = result511[14]+result612[27]
                num6 = result612[27]+result511[26]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==24){
                num1 = result28[8]+result28[16]
                num2 = result28[16]+result28[24]
                num3 = result39[24]+result39[8]
                num4 = result410[8]+result410[33]
                num5 = result511[33]+result612[12]
                num6 = result612[12]+result511[11]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==25){
                num1 = result28[9]+result28[18]
                num2 = result28[18]+result28[14]
                num3 = result39[14]+result39[16]
                num4 = result410[16]+result410[18]
                num5 = result511[18]+result612[36]
                num6 = result612[36]+result511[14]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==26){
                num1 = result28[33]+result28[36]
                num2 = result28[36]+result28[21]
                num3 = result39[21]+result39[22]
                num4 = result410[22]+result410[23]
                num5 = result511[23]+result612[24]
                num6 = result612[24]+result511[26]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==27){
                num1 = result28[11]+result28[36]
                num2 = result28[22]+result28[33]
                num3 = result39[33]+result39[26]
                num4 = result410[26]+result410[9]
                num5 = result511[9]+result612[12]
                num6 = result612[12]+result511[33]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==28){
                num1 = result28[12]+result28[9]
                num2 = result28[9]+result28[26]
                num3 = result39[26]+result39[33]
                num4 = result410[33]+result410[22]
                num5 = result511[22]+result612[26]
                num6 = result612[11]+result511[21]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==29){
                num1 = result28[12]+result28[24]
                num2 = result28[24]+result28[15]
                num3 = result39[15]+result39[18]
                num4 = result410[18]+result410[21]
                num5 = result511[21]+result612[24]
                num6 = result612[24]+result511[26]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==30){
                num1 = result28[13]+result28[12]
                num2 = result28[12]+result28[11]
                num3 = result39[11]+result39[33]
                num4 = result410[33]+result410[11]
                num5 = result511[11]+result612[21]
                num6 = result612[12]+result511[26]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==31){
                num1 = result28[26]+result28[25]
                num2 = result28[25]+result28[34]
                num3 = result39[34]+result39[33]
                num4 = result410[33]+result410[26]
                num5 = result511[26]+result612[26]
                num6 = result612[35]+result511[34]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==32){
                num1 = result28[8]+result28[27]
                num2 = result28[27]+result28[33]
                num3 = result39[33]+result39[9]
                num4 = result410[9]+result410[21]
                num5 = result511[12]+result612[21]
                num6 = result612[11]+result511[26]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==33){
                num1 = result28[14]+result28[13]
                num2 = result28[13]+result28[16]
                num3 = result39[16]+result39[15]
                num4 = result410[15]+result410[18]
                num5 = result511[18]+result612[27]
                num6 = result612[17]+result511[26]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==34){
                num1 = result28[19]+result28[18]
                num2 = result28[18]+result28[26]
                num3 = result39[21]+result39[26]
                num4 = result410[36]+result410[22]
                num5 = result511[22]+result612[12]
                num6 = result612[21]+result511[26]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==35){
                num1 = result28[24]+result28[23]
                num2 = result28[23]+result28[9]
                num3 = result39[9]+result39[25]
                num4 = result410[33]+result410[11]
                num5 = result511[11]+result612[26]
                num6 = result612[12]+result511[21]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(4).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(4).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==36){
                num1 = result28[25]+result28[34]
                num2 = result28[34]+result28[8]
                num3 = result39[8]+result39[21]
                num4 = result410[12]+result410[16]
                num5 = result511[16]+result612[26]
                num6 = result612[36]+result511[26]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(7).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(7).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(7).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==37){
                num1 = result28[24]+result28[35]
                num2 = result28[35]+result28[15]
                num3 = result39[15]+result39[36]
                num4 = result410[36]+result410[11]
                num5 = result511[11]+result612[21]
                num6 = result612[12]+result511[26]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(5).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==38){
                num1 = result28[8]+result28[11]
                num2 = result28[11]+result28[34]
                num3 = result39[14]+result39[17]
                num4 = result410[17]+result410[36]
                num5 = result511[36]+result612[33]
                num6 = result612[23]+result511[26]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(6).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(6).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==39){
                num1 = result28[9]+result28[13]
                num2 = result28[13]+result28[17]
                num3 = result39[17]+result39[21]
                num4 = result410[21]+result410[11]
                num5 = result511[11]+result612[21]
                num6 = result612[12]+result511[23]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(6).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(6).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
            if (i==40){
                num1 = result28[34]+result28[27]
                num2 = result28[27]+result28[11]
                num3 = result39[11]+result39[15]
                num4 = result410[15]+result410[19]
                num5 = result511[19]+result612[23]
                num6 = result612[23]+result511[19]
                if (num1.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num1 = binding.tv1First5column.text.toString().toInt()!!.minus(9).toString()
                }
                if (num2.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num2 = binding.tv1First5column.text.toString().toInt()!!.minus(8).toString()
                }
                if (num3.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num3 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num4.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num4 = binding.tv1First5column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num5.toInt() <= binding.tv1First5column.text.toString().toInt()){

                }else{
                    num5 = binding.tv1First5column.text.toString().toInt()!!.minus(5).toString()
                }
                if (num6.toInt() <= binding.tv1First6column.text.toString().toInt()){

                }else{
                    num6 = binding.tv1First6column.text.toString().toInt()!!.minus(6).toString()
                }
                if (num1.toInt() == num2.toInt()) num2 = num2.toInt().minus(9).toString()
                if (num1.toInt() == num3.toInt()) num3 = num3.toInt().minus(8).toString()
                if (num1.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num1.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num1.toInt() == num6.toInt()) num6 = num6.toInt().minus(10).toString()

                if (num2.toInt() == num3.toInt()) num3 = num3.toInt().minus(9).toString()
                if (num2.toInt() == num4.toInt()) num4 = num4.toInt().minus(8).toString()
                if (num2.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num2.toInt() == num6.toInt()) num6 = num6.toInt().minus(10).toString()

                if (num3.toInt() == num4.toInt()) num4 = num4.toInt().minus(5).toString()
                if (num3.toInt() == num5.toInt()) num5 = num5.toInt().minus(6).toString()
                if (num3.toInt() == num6.toInt()) num6 = num2.toInt().minus(9).toString()

                if (num4.toInt() == num5.toInt()) num5 = num5.toInt().minus(7).toString()
                if (num4.toInt() == num6.toInt()) num6 = num6.toInt().minus(8).toString()

                if (num5.toInt() == num6.toInt()) num6 = num6.toInt().plus(9).toString()

                var sixNumbers = SixNumber(num1, num2, num3, num4, num5,  num6)
                sixNumberList.add(sixNumbers)
            }
        }

        var sixNumberAdapter = SixNumberAdapter(sixNumberList)
        binding.recyclerview6column.adapter = sixNumberAdapter
    }

    private fun initFBIntAdd(){

    }

}
