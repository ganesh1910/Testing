package com.gk.testing.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.gk.testing.R
import com.gk.testing.adapter.ImageAdapter
import com.gk.testing.databinding.FragmentImagePickBinding
import com.gk.testing.other.Constant.GRID_SPAN_COUNT
import javax.inject.Inject

class ImagePickerFragment @Inject constructor(
    val adapter: ImageAdapter
): Fragment() {

    lateinit var viewModel: ShoppingViewModel
    private lateinit var binding: FragmentImagePickBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentImagePickBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]

        setupRecycleView()

        adapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setCurImageUrl(it)
        }
    }

    private fun setupRecycleView(){
        binding.rvImages.apply {
            adapter = adapter
            layoutManager = GridLayoutManager(requireContext(),GRID_SPAN_COUNT)
        }
    }
}