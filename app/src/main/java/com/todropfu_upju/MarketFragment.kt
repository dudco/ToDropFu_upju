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
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso
import com.todropfu_upju.databinding.ItemGoodsBinding
import kotlinx.android.synthetic.main.fragment_market.view.*
import kotlinx.android.synthetic.main.item_goods.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.json.JSONArray

class MarketFragment : Fragment(), AnkoLogger{
    data class GoodsData(@SerializedName("name") val title: String, @SerializedName("description") val content: String, val price: String, val thumbnail: String)
    private val mGoodsDatas = ObservableArrayList<Any>().apply {
//        add(GoodsData("트럭마을산 두부", "트럭마을 아조시가 열심히 기른 두부입니다", "2,000"))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_market, container, false)

        Util.network.getTruckData(Util.getPref(context, "_id", "")!!, { request, response, result ->
            val json = result.get().obj()
            val goods: JSONArray = json.get("goods") as JSONArray
            info{ goods }
            for(i in 0..goods.length() - 1){
                val data = goods[i]
                info{data}
                val gson = Gson()
                val rData = gson.fromJson(data.toString(), GoodsData::class.java)
                mGoodsDatas.add(rData)
            }
        })

        info{ "ㅁㄴㅇㄹ " + Util.getPref(context, "_id", "asdf")!! }

        LinearLayoutManager(context).let {
            it.orientation = LinearLayout.VERTICAL
            view.marketRecycler.layoutManager = it
        }
        LastAdapter(mGoodsDatas, BR.goods)
                .map<GoodsData>(Type<ItemGoodsBinding>(R.layout.item_goods)
                        .onBind {
                            val pos = it.layoutPosition
                            Picasso.with(getContext()).load("http://kafuuchino.one:3000${(mGoodsDatas.get(pos) as GoodsData).thumbnail}").into(it.binding.billTitleImage)
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
