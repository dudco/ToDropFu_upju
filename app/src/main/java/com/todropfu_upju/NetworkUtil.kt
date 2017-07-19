package com.todropfu_upju

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import java.io.File

/**
 * Created by dudco on 2017. 7. 18..
 */

class NetworkUtil {

    init{
        FuelManager.instance.basePath = "http://kafuuchino.one:3000"
    }

    fun fbLogin(token: String,address: String, callback: (request: Request, response: Response, result: Result<Json, FuelError>)->Unit){
        "/auth/facebook/token".httpGet(listOf("access_token" to token, "address" to address )).responseJson {
            request, response, result -> callback(request, response, result)
        }
    }

    fun connectTruck(name: String, type: String, inapp_purchase: String, credit_purchase: String, callback:  (request: Request, response: Response, result: Result<Json, FuelError>)->Unit ){
        "/truck/add?name=$name&type=$type&inapp_purchase=$inapp_purchase&credit_purchase=$credit_purchase".httpPost(listOf("name" to name, "type" to type, "inapp_purchase" to inapp_purchase, "credit_purchase" to credit_purchase)).responseJson { request, response, result ->
            callback(request, response, result)
        }
    }

    fun getTruckData(id: String, callback: (request: Request, response: Response, result: Result<Json, FuelError>) -> Unit){
        "/truck/$id".httpGet().responseJson { request, response, result ->
            callback(request, response, result)
        }
    }

    fun addTruckgoods(id: String, titlename: String, desc: String, price: String, image: File, callback: (request: Request, response: Response, result: Result<Json, FuelError>) -> Unit){
        Fuel.upload("/truck/add/goods?name=$titlename&truck_id=$id&description=$desc&price=$price", Method.POST).source { request, url ->
            image
        }.name {
            "thumbnail"
        }.responseJson { request, response, result ->
            callback(request, response, result)
        }

    }
}