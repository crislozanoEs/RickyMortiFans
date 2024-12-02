package com.crislozano.rickymortyfans.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crislozano.rickymortyfans.R
import com.crislozano.rickymortyfans.databinding.FragmentListBinding
import com.crislozano.rickymortyfans.presentation.commons.State
import org.koin.java.KoinJavaComponent.inject
import com.crislozano.rickymortyfans.domain.entities.Result
import com.crislozano.rickymortyfans.presentation.MainActivity
import com.crislozano.rickymortyfans.presentation.details.DetailsFragment

/**
 * A [ListFragment] that holds the information of a list of characters.
 */

class ListFragment : Fragment(), CharacterItemAdapter.OnItemClickListener {

    private val viewModel: ListFragmentVM by inject(ListFragmentVM::class.java)
    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = CharacterItemAdapter(this)

        viewModel.loadData()

        if(activity != null) {
            (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
            (activity as MainActivity).supportActionBar?.title = getString(R.string.list_title)
        }

        binding.reloadList.setOnClickListener {
            viewModel.resetPage()
            (binding.list.adapter as CharacterItemAdapter).clearItems()
            viewModel.loadData()
        }

        binding.list.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val visibleItemCount = layoutManager.childCount
                    val firstVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    viewModel.loadMoreData(firstVisibleItems, visibleItemCount, totalItemCount)
                }
            }
        )

        viewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                is State.Loading -> {
                    binding.loadingView.visibility = View.VISIBLE
                    binding.errorContainer.visibility = View.GONE
                }

                is State.Success -> {
                    binding.loadingView.visibility = View.GONE
                    binding.errorContainer.visibility = View.GONE
                    if(it.data is List<*>) {
                        (binding.list.adapter as CharacterItemAdapter).setItems(it.data as List<Result>)
                    }

                }
                is State.Error -> {
                    binding.loadingView.visibility = View.GONE
                    binding.errorContainer.visibility = View.VISIBLE
                    binding.errorTextView.text = it.message
                    binding.retryButton.setOnClickListener {
                        viewModel.loadData()
                    }
                }
            }
        }

        return binding.root
    }

    override fun onItemClick(item: Result) {
        viewModel.resetPage()
        navigateToDetail(item)
    }

    private fun navigateToDetail(item: Result) {
        findNavController().navigate(R.id.action_listFragment_to_detailsFragment, DetailsFragment.createBundle(item))
    }
}