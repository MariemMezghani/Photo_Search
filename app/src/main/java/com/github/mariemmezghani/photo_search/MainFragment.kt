package com.github.mariemmezghani.photo_search

import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import com.github.mariemmezghani.photo_search.databinding.FragmentMainBinding

class MainFragment: Fragment() {
    private lateinit var viewModel: MainViewModel
    val adapter = PhotoAdapter()

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
        binding.searchText.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    // make sure the recycler view scrolls to position 0
                    binding.photosRecyclerview.scrollToPosition(0)
                    viewModel.getPhotosList(query)
                    //remove keyboard
                    binding.searchText.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        binding.searchText
            .setOnSuggestionListener(object :
                androidx.appcompat.widget.SearchView.OnSuggestionListener {
                override fun onSuggestionSelect(position: Int): Boolean {
                    return true
                }

                // when the user clicks on a query from the search history, results are searched again
                override fun onSuggestionClick(position: Int): Boolean {
                    val selectedView: androidx.cursoradapter.widget.CursorAdapter? =
                        binding.searchText.getSuggestionsAdapter()
                    val cursor: Cursor = selectedView?.getItem(position) as Cursor
                    val index: Int =
                        cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_1)
                    binding.searchText.setQuery(cursor.getString(index), true)
                    return true
                }
            })

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
        return binding.root

    }


}