package com.aiytl.powerballPredictor

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aiytl.powerballPredictor.databinding.ActivityMainBinding
import com.aiytl.powerballPredictor.databinding.LayoutDialogNumbersBinding
import com.aiytl.powerballPredictor.model.SixNumber
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import java.math.BigInteger

class MainActivity : BaseActivity(), View.OnClickListener {
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

                var sixNumberList = ArrayList<SixNumber>()
                for (i in 1..2){
                    if (i==1){
                        var sixNumbers = SixNumber(result17[1]+result17[2], result28[2]+result28[3], result39[3]+result39[4], result410[4]+result410[5], result511[5]+result511[6], result612[6]+result612[1])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==2){
                        var sixNumbers = SixNumber(result17[7]+result17[8], result28[8]+result28[9], result39[9]+result39[10], result410[10]+result410[11], result511[11]+result612[12], result612[12]+result511[7])
                        sixNumberList.add(sixNumbers)
                    }
                }

                var sixNumberAdapter = SixNumberAdapter(sixNumberList)
                binding.recyclerview6column.adapter = sixNumberAdapter

            }

            R.id.btn_gen_40nos -> {
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
                if(result17.size == 1 || result28.size == 1 || result39.size == 1 || result410.size == 1 || result511.size == 1 || result612.size == 1){
                    customToast("Result can't predict, please select number by clicking box.")
                    return
                }
                var sixNumberList = ArrayList<SixNumber>()

                for (i in 1..40){
                    if (i==1){
                        var sixNumbers = SixNumber(result17[1]+result17[2], result28[2]+result28[3], result39[3]+result39[4], result410[4]+result410[5], result511[5]+result511[6], result612[6]+result612[1])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==2){
                        var sixNumbers = SixNumber(result17[7]+result17[8], result28[8]+result28[9], result39[9]+result39[10], result410[10]+result410[11], result511[11]+result612[12], result612[12]+result511[7])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==3){
                        var sixNumbers = SixNumber(result17[13]+result17[14], result28[14]+result28[15], result39[15]+result39[16], result410[16]+result410[17], result511[17]+result612[18], result612[18]+result511[13])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==4){
                        var sixNumbers = SixNumber(result17[19]+result17[20], result28[20]+result28[21], result39[21]+result39[22], result410[22]+result410[23], result511[23]+result511[24], result612[24]+result612[19])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==5){
                        var sixNumbers = SixNumber(result17[24]+result17[23], result28[23]+result28[22], result39[22]+result28[21], result410[21]+result410[20], result511[20]+result511[19], result612[19]+result612[0])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==6){
                        var sixNumbers = SixNumber(result17[18]+result17[17], result28[17]+result28[16], result39[16]+result39[15], result410[15]+result410[13], result511[14]+result511[13], result612[13]+result612[18])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==7){
                        var sixNumbers = SixNumber(result17[12]+result17[11], result28[11]+result28[10], result39[10]+result39[9], result410[9]+result410[8], result511[8]+result511[7], result612[7]+result612[8])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==8){
                        var sixNumbers = SixNumber(result17[6]+result17[5], result28[5]+result28[4], result39[4]+result39[3], result410[3]+result410[2], result511[2]+result511[1], result612[1]+result612[3])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==9){
                        var sixNumbers = SixNumber(result17[1]+result17[3], result28[3]+result28[5], result39[5]+result39[7], result410[7]+result410[9], result511[9]+result511[11], result612[11]+result612[7])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==10){
                        var sixNumbers = SixNumber(result17[11]+result17[13], result28[13]+result28[15], result39[15]+result39[17], result410[17]+result410[19], result511[19], result612[21]+result511[19])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==11){
                        var sixNumbers = SixNumber(result17[21]+result17[23], result28[23]+result28[1], result39[1]+result39[3], result410[3]+result410[5], result511[5]+result511[7], result612[7]+result612[5])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==12){
                        var sixNumbers = SixNumber(result17[7]+result17[10], result28[10]+result28[13], result39[13]+result39[16], result410[16]+result410[19], result511[19]+result511[22], result612[22]+result612[19])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==13){
                        var sixNumbers = SixNumber(result17[22]+result17[24], result28[24]+result28[1], result39[1]+result39[2], result410[2]+result410[3], result511[3]+result511[4], result612[4]+result612[1])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==14){
                        var sixNumbers = SixNumber(result17[2]+result17[4], result28[4]+result28[6], result39[6]+result39[6], result410[8]+result410[18], result511[18]+result511[12], result612[12]+result612[6])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==15){
                        var sixNumbers = SixNumber(result17[12]+result17[14], result28[14]+result28[16], result39[16]+result39[18], result410[18]+result410[20], result511[20]+result511[22], result612[22]+result612[21])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==16){
                        var sixNumbers = SixNumber(result17[24]+result17[18], result28[18]+result28[12], result39[12]+result39[6], result410[6]+result410[2], result511[2]+result511[1], result612[1]+result612[0])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==17){
                        var sixNumbers = SixNumber(result17[3]+result17[6], result28[6]+result28[9], result39[9]+result39[12], result410[12]+result410[15], result511[15]+result511[18], result612[18]+result612[17])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==18){
                        var sixNumbers = SixNumber(result17[4]+result17[8], result28[8]+result28[12], result39[12]+result39[16], result410[16]+result410[20], result511[20]+result511[24], result612[24]+result612[0])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==19){
                        var sixNumbers = SixNumber(result17[24]+result17[20], result28[20]+result28[16], result39[16]+result39[12], result410[12]+result410[8], result511[8]+result511[4], result612[4]+result612[0])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==20){
                        var sixNumbers = SixNumber(result17[18]+result17[15], result28[15]+result28[12], result39[12]+result39[9], result410[9]+result410[6], result511[6]+result511[3], result612[3]+result612[2])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==21){
                        var sixNumbers = SixNumber(result17[1]+result17[2], result28[2]+result28[6], result39[6]+result39[12], result410[12]+result410[18], result511[18]+result511[24], result612[24]+result612[23])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==22){
                        var sixNumbers = SixNumber(result17[7]+result17[4], result28[14]+result28[21], result39[21]+result39[2], result410[2]+result410[4], result511[4]+result511[6], result612[6]+result612[0])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==23){
                        var sixNumbers = SixNumber(result17[6]+result17[4], result28[4]+result28[2], result39[2]+result39[21], result410[21]+result410[14], result511[14]+result511[7], result612[7]+result612[6])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==24){
                        var sixNumbers = SixNumber(result17[8]+result17[16], result28[16]+result28[24], result39[24]+result39[8], result410[8]+result410[10], result511[10]+result511[12], result612[12]+result612[11])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==25){
                        var sixNumbers = SixNumber(result17[9]+result17[18], result28[18]+result28[14], result39[14]+result39[16], result410[16]+result410[18], result511[18]+result511[20], result612[20]+result612[0])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==26){
                        var sixNumbers = SixNumber(result17[10]+result17[20], result28[20]+result28[21], result39[21]+result39[22], result410[22]+result410[23], result511[23]+result511[24], result612[24]+result612[0])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==27){
                        var sixNumbers = SixNumber(result17[11]+result17[20], result28[22]+result28[3], result39[3]+result39[6], result410[6]+result410[9], result511[9]+result511[12], result612[12]+result612[0])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==28){
                        var sixNumbers = SixNumber(result17[12]+result17[9], result28[9]+result28[6], result39[6]+result39[3], result410[3]+result410[22], result511[22]+result511[0], result612[11]+result612[21])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==29){
                        var sixNumbers = SixNumber(result17[12]+result17[24], result28[24]+result28[15], result39[15]+result39[18], result410[18]+result410[21], result511[21]+result511[24], result612[24]+result612[0])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==30){
                        var sixNumbers = SixNumber(result17[13]+result17[12], result28[12]+result28[11], result39[11]+result39[10], result410[10]+result410[11], result511[11]+result511[21], result612[12]+result612[0])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==31){
                        var sixNumbers = SixNumber(result17[2]+result17[1], result28[1]+result28[4], result39[4]+result39[3], result410[3]+result410[6], result511[6]+result511[0], result612[5]+result612[4])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==32){
                        var sixNumbers = SixNumber(result17[8]+result17[7], result28[7]+result28[10], result39[10]+result39[9], result410[9]+result410[21], result511[12]+result511[21], result612[11]+result612[0])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==33){
                        var sixNumbers = SixNumber(result17[14]+result17[13], result28[13]+result28[16], result39[16]+result39[15], result410[15]+result410[18], result511[18]+result511[7], result612[17]+result612[0])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==34){
                        var sixNumbers = SixNumber(result17[19]+result17[18], result28[18], result39[21]+result39[2], result410[20]+result410[22], result511[22]+result511[12], result612[21]+result612[0])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==35){
                        var sixNumbers = SixNumber(result17[24]+result17[23], result28[23]+result28[9], result39[9]+result39[1], result410[10]+result410[11], result511[11]+result511[0], result612[12]+result612[21])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==36){
                        var sixNumbers = SixNumber(result17[1]+result17[4], result28[4]+result28[8], result39[8]+result39[21], result410[12]+result410[16], result511[16]+result511[2], result612[20]+result612[0])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==37){
                        var sixNumbers = SixNumber(result17[24], result28[5], result39[15], result410[20], result511[11], result612[12])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==38){
                        var sixNumbers = SixNumber(result17[8]+result17[11], result28[11]+result28[4], result39[14]+result39[17], result410[17]+result410[20], result511[20]+result511[3], result612[23]+result612[0])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==39){
                        var sixNumbers = SixNumber(result17[9]+result17[13], result28[13]+result28[17], result39[17]+result39[21], result410[21]+result410[11], result511[11]+result511[21], result612[12]+result612[23])
                        sixNumberList.add(sixNumbers)
                    }
                    if (i==40){
                        var sixNumbers = SixNumber(result17[4]+result17[7], result28[7]+result28[11], result39[11]+result39[15], result410[15]+result410[19], result511[19]+result511[23], result612[23]+result612[19])
                        sixNumberList.add(sixNumbers)
                    }
                }

                var sixNumberAdapter = SixNumberAdapter(sixNumberList)
                binding.recyclerview6column.adapter = sixNumberAdapter
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
}
