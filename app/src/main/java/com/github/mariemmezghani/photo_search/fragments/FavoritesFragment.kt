package com.github.mariemmezghani.photo_search.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.github.mariemmezghani.photo_search.FavoritesAdapter
import com.github.mariemmezghani.photo_search.Injection
import com.github.mariemmezghani.photo_search.MainViewModel
import com.github.mariemmezghani.photo_search.database.PhotoDatabase
import com.github.mariemmezghani.photo_search.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    val adapter = FavoritesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFavoritesBinding.inflate(inflater)
        binding.toolbar.title = "Favorites"
        // database
        val application = requireNotNull(this.activity).application
        val database = PhotoDatabase.getInstance(application).photoDAO
        // viewModel
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(database))
            .get(MainViewModel::class.java)

        binding.favoritesRecyclerview.adapter = adapter
        binding.viewModel = viewModel
        viewModel.favorites.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }


        return binding.root
    }
}