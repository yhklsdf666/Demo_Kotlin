package com.yhklsdf.demo_kotlin.adapter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yhklsdf.demo_kotlin.db.PicturesData

class JTK {
    private val url = ""

    fun test(): PicturesData? {
        return Gson().fromJson<PicturesData>(url, object : TypeToken<List<PicturesData>>() {}.type)
    }
}
