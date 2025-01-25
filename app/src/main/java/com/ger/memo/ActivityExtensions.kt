package com.ger.memo

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun Activity.launchMarket() {
    val uri = Uri.parse("market://details?id=com.ger.memo")
    val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
    try {
        startActivity(myAppLinkToMarket)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show()
    }
}