package com.enma.app.simple.simpleapp

interface ApiURLs {


    val base:String get() = "http://stg.api.fawasell.com/v1/"

    val APP_ID:String get() = "14378174"

    val APP_SECRET:String get() = "cFJiGxZVTGf3rPNMK1o08WQWWpnr8M9RmZOyGxcM"

    val HASH_KEY:String get() = "diBIGrtVCAH00GtMVLupirbNdFEjooqk8YPJUtUw"

    val GET_CATEGORIES_URL:String get() = base + "categories?app_id=" + APP_ID + "&app_secret=" + APP_SECRET

    val GET_POSTS_URL:String get() = base + "posts?"

}
