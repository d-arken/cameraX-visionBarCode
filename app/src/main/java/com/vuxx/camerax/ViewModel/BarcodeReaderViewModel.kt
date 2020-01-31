package com.vuxx.camerax.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BarcodeReaderViewModel : ViewModel() {
    var barcode = MutableLiveData("12312")
    var pauseReader = MutableLiveData(false)
}