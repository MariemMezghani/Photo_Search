package com.github.mariemmezghani.photo_search

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.github.mariemmezghani.photo_search.databinding.FragmentDetailBinding
import com.github.mariemmezghani.photo_search.model.Photo

class DetailFragment : Fragment() {

    private var downloadId: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val photo = DetailFragmentArgs.fromBundle(requireArguments()).photo

        val factory = DetailViewModelFactory(photo = photo)
        val detailViewModel =
                ViewModelProvider(this, factory).get(DetailViewModel::class.java)
        binding.detailViewModel = detailViewModel
        binding.button.setOnClickListener {
            download(photo)
        }

        return binding.root
    }

    private fun download(photo: Photo) {
        //generate a unique filename
        val filename = "${System.currentTimeMillis()}.jpg"
        val request = DownloadManager.Request(Uri.parse(photo.url))
                .setTitle(filename)
                .setDescription(photo.title)
                // show notification bar when downloading
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setAllowedOverRoaming(true)
                // download is allowed on mobile network
                .setAllowedOverRoaming(true)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, filename)

        //define an instance of DownloadManager
        val downloadManager = context?.getSystemService(DOWNLOAD_SERVICE) as DownloadManager

        //enqueue function puts the download request in the queue and returns the rquest id
        downloadId = downloadManager.enqueue(request)


    }
}