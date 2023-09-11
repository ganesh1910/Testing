package com.gk.testing.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.gk.testing.adapter.ImageAdapter
import javax.inject.Inject

class ShoppingFragmentFactory @Inject constructor(
    private val adapter: ImageAdapter,
    private val glide:RequestManager
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ImagePickerFragment::class.java.name -> ImagePickerFragment(adapter = adapter)
            AddShoppingItemFragment::class.java.name -> AddShoppingItemFragment(glide = glide)
            else -> super.instantiate(classLoader, className)
        }
    }
}