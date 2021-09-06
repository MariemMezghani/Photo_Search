package com.github.mariemmezghani.photo_search.fragments

import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mariemmezghani.photo_search.DetailViewModel
import com.github.mariemmezghani.photo_search.DetailViewModelFactory
import com.github.mariemmezghani.photo_search.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val photo = DetailFragmentArgs.fromBundle(requireArguments()).photo
        if (photo.isFavorite) {
            binding.favouriteButton.isChecked = true
        }
        val application = requireActivity().application

        val factory = DetailViewModelFactory(application = application, photo = photo)
        val detailViewModel =
            ViewModelProvider(this, factory).get(DetailViewModel::class.java)
        binding.detailViewModel = detailViewModel

        detailViewModel.createChannel(CHANNEL_ID, "channel")

        // register the BroadcastReciever
        context?.registerReceiver(
            detailViewModel.receiver,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
        binding.button.setOnClickListener {
            detailViewModel.download(photo)
        }

        binding.favouriteButton.setOnClickListener {
            if (binding.favouriteButton.isChecked) {
                photo.isFavorite = true
                detailViewModel.onAddFavorite(photo)
            } else {
                photo.isFavorite = false
                detailViewModel.onDeleteFavorite(photo)
            }
        }

        return binding.root
    }


    companion object {
        const val CHANNEL_ID = "channelId"
        var downloadId: Long = 0
    }
}