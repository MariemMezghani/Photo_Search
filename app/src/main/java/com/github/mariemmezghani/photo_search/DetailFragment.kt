package com.github.mariemmezghani.photo_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.github.mariemmezghani.photo_search.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding=FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val photo = DetailFragmentArgs.fromBundle(requireArguments()).photo

        val factory = DetailViewModelFactory(photo = photo)
        val detailViewModel=
            ViewModelProvider(this,factory).get(DetailViewModel::class.java)
        binding.detailViewModel=detailViewModel

        return binding.root
    }
}