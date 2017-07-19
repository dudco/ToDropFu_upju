package com.todropfu_upju

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import com.todropfu_upju.databinding.FragmentBillBinding
import com.todropfu_upju.databinding.ItemBillBinding
import kotlinx.android.synthetic.main.fragment_bill.view.*
import kotlin.properties.Delegates

class BillFragment : Fragment() {
    data class BillData(val username: String, val title: String, val price: String, val year: String, val month: String, val day: String)
    data class SalesData(val year: String, val month: String, val day: String)
    val mBillData = ObservableArrayList<Any>().apply {
        add(BillData("두부넘버원", "트럭마을산 두부", "2,000", "2017", "5", "23"))
        add(BillData("두부넘버원", "트럭마을산 두부", "2,000", "2017", "5", "23"))
    }
    var binding by Delegates.notNull<FragmentBillBinding>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater!!, R.layout.fragment_bill, container, false)
        binding.setVariable(BR.sales, SalesData("523원", "121,121,121원", "523원"))
//        val view = inflater!!.inflate(R.layout.fragment_bill, container, false)
        LinearLayoutManager(context).let {
            it.orientation = LinearLayout.VERTICAL
            binding.billRecycler.layoutManager = it
        }
        LastAdapter(mBillData, BR.bill).map<BillData>(Type<ItemBillBinding>(R.layout.item_bill)).into(binding.billRecycler)
        return binding.root
    }

    companion object {
        fun newInstance(): BillFragment {
            val fragment = BillFragment()
            return fragment
        }
    }

}
