package com.yhklsdf.demo_kotlin.adapter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yhklsdf.demo_kotlin.bean.Picture

class JTK {
    private val url = ""

    fun test(): Picture? {
        return Gson().fromJson<Picture>(url, object : TypeToken<List<Picture>>() {}.type)
    }
}
