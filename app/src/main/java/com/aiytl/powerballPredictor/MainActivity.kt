package com.aiytl.powerballPredictor

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.aiytl.powerballPredictor.databinding.ActivityMainBinding
import com.aiytl.powerballPredictor.databinding.LayoutDialogNumbersBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity(), View.OnClickListener {
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
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
        "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
        "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
        "31", "32", "33", "34", "35", "36", "37", "38", "39", "40"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this)
        binding.bannerAdview.loadAd(AdRequest.Builder().build())

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
                if(Integer.parseInt(binding.tv1First5column.text.toString()) == 0){
                    Toast.makeText(this, "First, Select a number from first 5 box.", Toast.LENGTH_SHORT).show()
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
                                binding.tv1.text = list[position]
                                dialog.dismiss()
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv2 ->{
                if(Integer.parseInt(binding.tv1First5column.text.toString()) == 0){
                    Toast.makeText(this, "First, Select a number from first 5 box.", Toast.LENGTH_SHORT).show()
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
                                binding.tv2.text = list[position]
                                dialog.dismiss()
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv3 ->{
                if(Integer.parseInt(binding.tv1First5column.text.toString()) == 0){
                    Toast.makeText(this, "First, Select a number from first 5 box.", Toast.LENGTH_SHORT).show()
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
                                binding.tv3.text = list[position]
                                dialog.dismiss()
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv4 ->{
                if(Integer.parseInt(binding.tv1First5column.text.toString()) == 0){
                    Toast.makeText(this, "First, Select a number from first 5 box.", Toast.LENGTH_SHORT).show()
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
                                binding.tv4.text = list[position]
                                dialog.dismiss()
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv5 ->{
                if(Integer.parseInt(binding.tv1First5column.text.toString()) == 0){
                    Toast.makeText(this, "First, Select a number from first 5 box.", Toast.LENGTH_SHORT).show()
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
                                binding.tv5.text = list[position]
                                dialog.dismiss()
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv6 ->{
                if(Integer.parseInt(binding.tv1First6column.text.toString()) == 0){
                    Toast.makeText(this, "First, Select a number from first 6 box.", Toast.LENGTH_SHORT).show()
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
                                binding.tv6.text = list[position]
                                dialog.dismiss()
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv7 ->{
                if(Integer.parseInt(binding.tv1First5column.text.toString()) == 0){
                    Toast.makeText(this, "First, Select a number from first 5 box.", Toast.LENGTH_SHORT).show()
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
                                binding.tv7.text = list[position]
                                dialog.dismiss()
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv8 ->{
                if(Integer.parseInt(binding.tv1First5column.text.toString()) == 0){
                    Toast.makeText(this, "First, Select a number from first 5 box.", Toast.LENGTH_SHORT).show()
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
                                binding.tv8.text = list[position]
                                dialog.dismiss()
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv9 ->{
                if(Integer.parseInt(binding.tv1First5column.text.toString()) == 0){
                    Toast.makeText(this, "First, Select a number from first 5 box.", Toast.LENGTH_SHORT).show()
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
                                binding.tv9.text = list[position]
                                dialog.dismiss()
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv10 ->{
                if(Integer.parseInt(binding.tv1First5column.text.toString()) == 0){
                    Toast.makeText(this, "First, Select a number from first 5 box.", Toast.LENGTH_SHORT).show()
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
                                binding.tv10.text = list[position]
                                dialog.dismiss()
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv11 ->{
                if(Integer.parseInt(binding.tv1First5column.text.toString()) == 0){
                    Toast.makeText(this, "First, Select a number from first 5 box.", Toast.LENGTH_SHORT).show()
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
                                binding.tv11.text = list[position]
                                dialog.dismiss()
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

            R.id.ll_tv12 ->{
                if(Integer.parseInt(binding.tv1First6column.text.toString()) == 0){
                    Toast.makeText(this, "First, Select a number from first 6 box.", Toast.LENGTH_SHORT).show()
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
                                binding.tv12.text = list[position]
                                dialog.dismiss()
                            }

                            override fun onItemLongClick(view: View?, position: Int) {
                                TODO("Not yet implemented")
                            }
                        })
                )

            }

        }
    }
}
