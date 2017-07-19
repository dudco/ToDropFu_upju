package com.todropfu_upju

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class RegisterActivity : AppCompatActivity() {

    val token: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setBg(2)

        btnToNext.onClick {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if( checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 200)
                }else{
                    mainActivityStart()
                }
            }else{
                mainActivityStart()
            }
        }
    }

    fun mainActivityStart(){
        val name = reg_editStoreName.text.toString()
        val type = reg_editType.text.toString()
        val isInapp = if(checkInApp.isChecked) 1 else 0
        Log.d("dudco", isInapp.toString())
        val isCredit = if(checkCard.isChecked) 1 else 0
        Util.network.connectTruck(name, type, isInapp.toString(), isCredit.toString(), {request, response, result ->
            Log.d("dudco", response.toString())
            Log.d("dudco", result.get().obj().toString())

            val json = result.get().obj()
            Util.setPref(this@RegisterActivity, "_id", json.get("_id").toString())
            Util.setPref(this@RegisterActivity, "name", json.get("name").toString())
            Util.setPref(this@RegisterActivity, "goods_type", json.get("goods_type").toString())
            Util.setPref(this@RegisterActivity, "init", true)
        })
        startActivity<MainActivity>()
    }

    fun setBg(sampleSize: Int){
        regContainer.background = BitmapDrawable(resources, Util.resourceInSmapleSizeImg(resources, R.drawable.bg_signup, sampleSize))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == 200){
            mainActivityStart()
        }
    }
}
