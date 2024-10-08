package com.example.iciban.fragment.selectbanner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectBannerViewModel() : ViewModel() {
    val bannerSelected = MutableLiveData<String>()
}