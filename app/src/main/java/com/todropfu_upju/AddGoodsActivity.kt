package com.todropfu_upju

import android.Manifest
import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_add_goods.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import android.provider.MediaStore
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.media.Image
import android.net.Uri
import android.os.Build
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.image
import org.jetbrains.anko.info
import java.io.File
import java.io.FileNotFoundException
import java.net.URI


class AddGoodsActivity : AppCompatActivity(), AnkoLogger {
    var path: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_goods)

        setSupportActionBar(addGoodsToolbar)
        supportActionBar?.let {
            title = "상품 추가하기"
            it.setDisplayHomeAsUpEnabled(true)
            it.setDefaultDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_home)
        }

        btnImageAdd.onClick {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if( checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 200)
                }else{
                    getImage()
                }
            }else{
                getImage()
            }
        }
    }

    fun getImage(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, 1234)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1234) {
                val ImageUri: Uri = data.data
                try {
//                    val file = File(ImageUri.path)
                    path = getPathFromUri(ImageUri)
                    info { path }
                    thumImage.image = Drawable.createFromPath(getPathFromUri(ImageUri))
//                    val sticker = DrawableSticker(Drawable.createFromStream(contentResolver.openInputStream(mImageUri), mImageUri.toString()))
//                    stickerView.addSticker(sticker)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }

            }
        }
    }
    fun getPathFromUri(uri: Uri): String{
        val cursor: Cursor = contentResolver.query(uri, null, null, null, null )
        cursor.moveToNext()
        val path: String = cursor.getString( cursor.getColumnIndex( "_data" ) )
        cursor.close()

        return path
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        getImage()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> finish()
            R.id.okay -> {
//                val file =
                Util.network.addTruckgoods(Util.getPref(this@AddGoodsActivity, "_id", "")!!, goods_name.text.toString(),
                        goods_desc.text.toString(), goods_price.text.toString(), File(path!!), {
                    request, response, result -> finish()
                })
            }
        }
        return true
    }
}
