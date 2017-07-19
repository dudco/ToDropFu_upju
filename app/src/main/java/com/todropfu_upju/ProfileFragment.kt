package com.todropfu_upju

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.todropfu_upju.databinding.FragmentProfileBinding
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import kotlin.properties.Delegates


class ProfileFragment : Fragment() {
    data class UserData(val name: String?)

    data class CreditCardData(val name: String, val date: String, val num1: String, val num2: String)

    var binding: FragmentProfileBinding by Delegates.notNull<FragmentProfileBinding>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentProfileBinding>(inflater, R.layout.fragment_profile, container, false)
        binding.setVariable(BR.card, CreditCardData("신한카드", "05/21", "6223", "5147"))
        val name = Util.getPref(context, "name", "")
//        val add = Util.getPref(context, "address", "")
        binding.setVariable(BR.udata, UserData(name))
//
//        binding.btnAddCard.onClick {
//            startActivity<AddCardActivity>()
//        }
        return binding.root
    }

    companion object {

        fun newInstance(): ProfileFragment {
            val fragment = ProfileFragment()
            return fragment
        }
    }

}
