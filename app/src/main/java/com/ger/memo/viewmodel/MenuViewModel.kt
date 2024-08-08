package com.ger.memo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ger.memo.StoreData
import kotlinx.coroutines.launch

class MenuViewModel(app: Application) : AndroidViewModel(app) {

    private val storeData = StoreData(app)

    var isSoundEnabled = storeData.getSoundPref

    fun toggleSound(newValue: Boolean) {
        viewModelScope.launch {
            storeData.saveSoundPref(newValue)
        }
    }

}