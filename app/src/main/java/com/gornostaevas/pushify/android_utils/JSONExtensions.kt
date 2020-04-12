package com.gornostaevas.pushify.android_utils

import org.json.JSONObject

fun JSONObject.unwrap(rootElement: String) : JSONObject {
    return getJSONObject(rootElement)
}

fun JSONObject.unwrapToString(rootElement: String) : String {
    return getJSONObject(rootElement).toString()
}