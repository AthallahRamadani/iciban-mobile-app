package com.example.iciban.fragment.select

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectBannerViewModel() : ViewModel() {
    val bannerSelected = MutableLiveData<String>()
}