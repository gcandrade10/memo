package com.ger.memo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ger.memo.StoreData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel
@Inject
constructor(private val storeData: StoreData) : ViewModel() {

    var isSoundEnabled = storeData.getSoundPref

    fun toggleSound(newValue: Boolean) {
        viewModelScope.launch {
            storeData.saveSoundPref(newValue)
        }
    }

}