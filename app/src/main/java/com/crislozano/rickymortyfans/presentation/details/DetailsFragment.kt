package com.crislozano.rickymortyfans.presentation.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.crislozano.rickymortyfans.R
import com.crislozano.rickymortyfans.databinding.FragmentDetailsBinding
import com.crislozano.rickymortyfans.domain.entities.Result
import com.crislozano.rickymortyfans.domain.entities.SingleCharacter
import com.crislozano.rickymortyfans.presentation.MainActivity
import com.crislozano.rickymortyfans.presentation.commons.State
import com.crislozano.rickymortyfans.presentation.commons.capitalizeWords
import com.crislozano.rickymortyfans.presentation.list.ListFragment
import org.koin.java.KoinJavaComponent.inject

/**
 * A [Details] that holds the information of the detail of a character.
 */

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private var id: Int? = null
    private val viewModel: DetailsFragmentVM by inject(DetailsFragmentVM::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ID_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        id?.let {
            viewModel.loadData(it)
        } ?: {
            binding.errorContainer.visibility = View.VISIBLE
            binding.errorTextView.text = getString(R.string.error_no_id)
            binding.retryButton.setOnClickListener {
               goBack()
            }
        }
        if(activity != null) {
            (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as MainActivity).supportActionBar?.title = getString(R.string.detail_title)
        }
        viewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                is State.Loading -> {
                    binding.loadingView.visibility = View.VISIBLE
                    binding.errorContainer.visibility = View.GONE
                }
                is State.Success -> {
                    binding.loadingView.visibility = View.GONE
                    binding.errorContainer.visibility = View.GONE
                    if(it.data is SingleCharacter) {
                        binding.detailCharName.text = it.data.name
                        Glide.with(binding.characterImage.context).load(it.data.image).into(binding.characterImage)
                        binding.detailCharStatus.text = it.data.status.capitalizeWords().ifBlank { getString(R.string.hello_blank_fragment) }
                        binding.detailCharOrigin.text = it.data.origin.name.capitalizeWords().ifBlank { getString(R.string.hello_blank_fragment) }
                        binding.detailCharSpecie.text = it.data.species.capitalizeWords().ifBlank { getString(R.string.hello_blank_fragment) }
                        binding.detailCharLocation.text = it.data.location.name.capitalizeWords().ifBlank { getString(R.string.hello_blank_fragment) }
                        binding.detailCharGender.text = it.data.gender.capitalizeWords().ifBlank { getString(R.string.hello_blank_fragment) }
                        binding.detailCharType.text = it.data.type.capitalizeWords().ifBlank { getString(R.string.hello_blank_fragment) }
                    }
                }
                is State.Error -> {
                    binding.loadingView.visibility = View.GONE
                    binding.errorContainer.visibility = View.VISIBLE
                    binding.errorTextView.text = it.message
                    binding.retryButton.setOnClickListener {
                        id?.let{ idValidated ->
                            viewModel.loadData(idValidated)
                        }
                    }
                }
            }
        }

        return binding.root
    }

    private fun goBack() {
        findNavController().popBackStack()
    }

    companion object {
        const val ID_PARAM = "id"
        fun createBundle(item: Result): Bundle {
            val bundle = Bundle()
            bundle.putInt(ID_PARAM, item.id)
            return bundle
        }
    }
}