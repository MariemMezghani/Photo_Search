package com.github.mariemmezghani.photo_search.fragments

import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.github.mariemmezghani.photo_search.*
import com.github.mariemmezghani.photo_search.database.PhotoDatabase
import com.github.mariemmezghani.photo_search.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    val adapter = PhotoAdapter(PhotoListener { photo ->
        viewModel.onItemClicked(photo)
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(
            context as Activity?, binding.drawerLayout, binding.toolbar, R.string.open_drawer,
            R.string.close_drawer
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // database
        val application = requireNotNull(this.activity).application
        val database = PhotoDatabase.getInstance(application).photoDAO

        // viewModel
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(database))
            .get(MainViewModel::class.java)

        binding.viewModel = viewModel

        binding.photosRecyclerview.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PhotoLoadStateAdapter { adapter.retry() },
            footer = PhotoLoadStateAdapter { adapter.retry() },
        )
        binding.buttonRetry.setOnClickListener {
            adapter.retry()
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(this.lifecycle, it)

        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                photosRecyclerview.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    photosRecyclerview.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }
        // Add an Observer on the state variable when an item is clicked
        viewModel.navigate.observe(viewLifecycleOwner, Observer { photo ->
            photo?.let {
                this.findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailFragment(
                        it
                    )
                )
                // reset state to make sure we only navigate once even after configuration change
                viewModel.navigationCompleted()
            }

        })

        // menu click
        binding.menu.aboutMenu.setOnClickListener {
            this.findNavController()
                .navigate(MainFragmentDirections.actionMainFragmentToAboutFragment())

        }
        binding.menu.favorites.setOnClickListener {
            this.findNavController().navigate(MainFragmentDirections.actionMainFragmentToFavoritesFragment())
        }


        setHasOptionsMenu(true)

        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.searchText)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    // make sure the recycler view scrolls to position 0
                    //binding.photosRecyclerview.scrollToPosition(0)
                    viewModel.getPhotosList(query)
                    //remove keyboard
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }


}
