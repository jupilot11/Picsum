package com.joeydee.picsum.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.joeydee.picsum.PicsumApplication
import com.joeydee.picsum.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val binding: FragmentDetailBinding by lazy {
        FragmentDetailBinding.inflate(requireActivity().layoutInflater)
    }
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            tvAuthorName.text = args.person.author
            val circularProgressDrawable = CircularProgressDrawable(requireActivity())
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Glide.with(PicsumApplication.context).load(args.person.download_url)
                .placeholder(circularProgressDrawable)
                .into(image)
        }
    }

}