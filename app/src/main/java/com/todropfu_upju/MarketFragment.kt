package com.todropfu_upju

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
import com.todropfu_upju.databinding.ItemGoodsBinding
import kotlinx.android.synthetic.main.fragment_market.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class MarketFragment : Fragment() {
    data class GoodsData(val title: String, val content: String, val price: String)
    private val mGoodsDatas = ObservableArrayList<Any>().apply {
        add(GoodsData("트럭마을산 두부", "트럭마을 아조시가 열심히 기른 두부입니다", "2,000"))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_market, container, false)
        LinearLayoutManager(context).let {
            it.orientation = LinearLayout.VERTICAL
            view.marketRecycler.layoutManager = it
        }
        LastAdapter(mGoodsDatas, BR.goods)
                .map<GoodsData>(Type<ItemGoodsBinding>(R.layout.item_goods)
                        .onBind {
                            it.binding.goodsEdit.onClick {
                                toast("click")
                            }
                        }
                ).into(view.marketRecycler)
        view.fabAdd.onClick {
            startActivity<AddGoodsActivity>()
        }
//        view.marketItemBg.setImageBitmap(resourceInSmapleSizeImg(context.resources, R.drawable.bg_login, 16))
        return view
    }

    companion object {

        fun newInstance(): MarketFragment {
            val fragment = MarketFragment()
            return fragment
        }
    }

}// Required empty public constructor
