package com.tngdev.sportevent.ui.matchdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tngdev.sportevent.databinding.FragmentMatchDetailBinding
import timber.log.Timber

class MatchDetailFragment : Fragment() {

    companion object {
        fun newInstance() = MatchDetailFragment()
    }

    private val viewModel by lazy { ViewModelProvider(this)[MatchDetailViewModel::class.java] }

    private var _binding: FragmentMatchDetailBinding? = null
    private val binding get() = _binding!!

    val args: MatchDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMatchDetailBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("onViewCreated")

        observeData()
        bindEvents()
        bindData()
        initUI()

    }

    private fun bindEvents() {


        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeData() {
    }

    private fun bindData() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            matchItem = args.matchItem
        }
    }

    private fun initUI() {
        initExoPlayer()
    }

    private fun initExoPlayer() {
        val player = ExoPlayer.Builder(requireContext()).build()
        binding.playerView.player = player
        player.repeatMode = Player.REPEAT_MODE_ONE

        args.matchItem.highlights?.let {
            val mediaItem = MediaItem.fromUri(it)
            player.setMediaItem(mediaItem)
            player.prepare()
            player.play()
        } ?: run {
            // hide PlayerView if highlight is not available
            binding.playerView.visibility = View.GONE
            binding.tvHighlight.visibility = View.GONE
        }
    }
}