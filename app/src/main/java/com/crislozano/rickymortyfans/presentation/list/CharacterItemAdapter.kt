package com.crislozano.rickymortyfans.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.crislozano.rickymortyfans.R
import com.crislozano.rickymortyfans.databinding.CharacterItemBinding
import com.crislozano.rickymortyfans.domain.entities.Result

class CharacterItemAdapter(private val onItemClicked: OnItemClickListener? = null): RecyclerView.Adapter<CharacterItemAdapter.ViewHolder>() {

    private val values: MutableList<Result> = mutableListOf()

    fun setItems(items: List<Result>) {
        val starPosition = values.size
        values.addAll(items)
        notifyItemRangeChanged(starPosition, items.size)
    }

    override fun getItemCount(): Int = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClicked?.onItemClick(item)
        }
    }

    fun clearItems() {
        values.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(item: Result)
    }

    inner class ViewHolder(private val binding: CharacterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val name = binding.characterName
        private val image = binding.characterImage
        private val status = binding.characterStatus
        private val species = binding.characterSpecies
        private val gender = binding.characterGender

        fun bind(item: Result) {
            name.text = item.name
            status.text = binding.root.context.getString(R.string.status_character_item_title, item.status)
            species.text = binding.root.context.getString(R.string.species_character_item_title, item.species)
            gender.text = binding.root.context.getString(R.string.gender_character_item_title, item.gender)
            Glide.with(itemView.context).load(item.image).into(image)
        }

    }
}