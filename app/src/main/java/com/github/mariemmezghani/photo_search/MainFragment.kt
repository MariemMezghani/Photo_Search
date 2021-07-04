package com.github.mariemmezghani.photo_search

import android.app.SearchManager
import android.database.Cursor
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
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

        // get the view model
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
                .get(MainViewModel::class.java)

        binding.lifecycleOwner = this

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
                this.findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(it))
                // reset state to make sure we only navigate once even after configuration change
                viewModel.navigationCompleted()
            }

        })
        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.about -> this.findNavController().navigate(R.id.action_mainFragment_to_aboutFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}