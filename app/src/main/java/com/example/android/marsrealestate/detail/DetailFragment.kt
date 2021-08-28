package com.example.android.marsrealestate.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.marsrealestate.R
import com.example.android.marsrealestate.databinding.FragmentDetailBinding
import java.lang.IllegalArgumentException

/**
 * This [Fragment] will show the detailed information about a selected piece of Mars real estate.
 */
class DetailFragment : Fragment() {

    private lateinit var viewModel:DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        @Suppress("UNUSED_VARIABLE")
        setHasOptionsMenu(true)
        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val arguments = DetailFragmentArgs.fromBundle(arguments!!)
        val selectedProperty = arguments.selectedProperty
        val viewModelFactory = DetailViewModelFactory(selectedProperty, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.mainPhotoImage.setOnClickListener{
            val uriString=viewModel.selectedProperty.value?.imgSrcUrl!!
            openWebPage(uriString)
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_share_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when(item.itemId){
            R.id.share_mars_property->{
                //ShareCompat.IntentBuilder.from(this.activity!!).setText(viewModel.selectedProperty.value.toString())
                startActivity(Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT,viewModel.selectedProperty.value.toString()))
            }
            else-> throw IllegalArgumentException("I don't know about this item yet!🤔")
        }
        return true
    }

    fun openWebPage(uriString:String) {

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uriString))
        startActivity(browserIntent)
    }
}
