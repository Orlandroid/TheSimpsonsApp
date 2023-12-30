package com.example.presentation.features.simpsons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.simpsons.Doc
import com.example.presentation.databinding.ItemCharacterBinding
import com.example.presentation.extensions.click
import com.example.presentation.extensions.loadImage


class SimpsonsAdapter(private val clickOnCharacter: (Doc) -> Unit) :
    PagingDataAdapter<Doc, SimpsonsAdapter.CharacterViewHolder>(CharacterComparator) {


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CharacterViewHolder {
        return CharacterViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }


    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Doc) = with(binding) {
            image.loadImage(character.Imagen)
            tvName.text = character.Nombre
            root.click {
                clickOnCharacter(character)
            }
        }
    }

    object CharacterComparator : DiffUtil.ItemCallback<Doc>() {
        override fun areItemsTheSame(oldItem: Doc, newItem: Doc): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Doc, newItem: Doc): Boolean {
            return oldItem == newItem
        }
    }

}
