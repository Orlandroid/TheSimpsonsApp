package com.example.presentation.features.simpsons

import androidx.navigation.fragment.navArgs
import com.example.domain.simpsons.Doc
import com.example.presentation.R
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentCharacterDetailBinding
import com.example.presentation.extensions.fromJson
import com.example.presentation.extensions.loadImage
import com.example.presentation.extensions.setTextFromHtml
import com.example.presentation.features.MainActivity

class CharacterDetailFragment :
    BaseFragment<FragmentCharacterDetailBinding>(R.layout.fragment_character_detail) {

    override fun configureToolbar() =
        MainActivity.ToolbarConfiguration(
            showToolbar = true,
            toolbarTitle = args.character.fromJson<Doc>().Nombre
        )

    private val args: CharacterDetailFragmentArgs by navArgs()
    override fun setUpUi() {
        bind(args.character.fromJson())
    }

    private fun bind(character: Doc) = with(binding) {
        image.loadImage(character.Imagen)
        tvHistory.text = character.Historia
        tvStatus.setTextFromHtml(templateHTML(label = "Estado", value = character.Estado))
        tvGenero.setTextFromHtml(templateHTML(label = "Genero", value = character.Genero))
        tvOccupation.setTextFromHtml(templateHTML(label = "Ocupacion", value = character.Ocupacion))
    }

    private fun templateHTML(label: String, value: String) = """
        <b>$label</b>: $value
    """.trimIndent()

}