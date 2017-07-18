package com.todropfu_upju

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setBg(2)

        btnLoginFB.onClick {
            startActivity<RegisterActivity>()
        }
    }

    fun setBg(sampleSize: Int){
        imgBg.setImageBitmap(Util.resourceInSmapleSizeImg(resources, R.drawable.bg_login, sampleSize))
    }
}
