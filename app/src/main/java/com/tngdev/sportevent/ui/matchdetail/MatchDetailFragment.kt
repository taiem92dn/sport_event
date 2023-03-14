package com.tngdev.sportevent.ui.matchdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar
import com.tngdev.sportevent.Constants
import com.tngdev.sportevent.R
import com.tngdev.sportevent.databinding.FragmentMatchDetailBinding
import com.tngdev.sportevent.model.MatchItem
import com.tngdev.sportevent.util.convertTimeStr
import com.tngdev.sportevent.worker.MatchOneTimeWorker
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
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

        // call to update data for "Set Reminder" button
        viewModel.getReminderWorker(args.matchItem.date)
    }

    private fun bindEvents() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btMatchReminder.setOnClickListener {
            if (viewModel.isSetReminder.value == true) {
                cancelReminderWorker(args.matchItem)
            } else {
                setReminderForMatch(args.matchItem)
            }
        }
    }

    private fun observeData() {
    }

    private fun bindData() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            matchItem = args.matchItem
            isSetReminder = viewModel.isSetReminder
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


    private fun cancelReminderWorker(matchItem: MatchItem) {
        val idStr = viewModel.getReminderWorker(matchItem.date) ?: return
        val arr = idStr.split(" ")

        arr.map { id ->
            WorkManager.getInstance(requireContext())
                .cancelWorkById(
                    UUID.fromString(id)
                )
        }
        viewModel.deleteReminderWorker(matchItem.date)

        showToast(getString(R.string.text_cancel_reminder_successfully))
    }

    private fun setReminderForMatch(matchItem: MatchItem) {
        val startTime = convertTimeStr(matchItem.date)
        // use for testing
//        val startTime = System.currentTimeMillis() + 10*60*1000L
        if (startTime == 0L) {
            showSnackbar(getString(R.string.text_date_in_a_wrong_format))
        }
        else {
            // remind before 10 min the match starts
            val worker1Id = startWorker(
                startTime - 10*60*1000L,
//                startTime - 2*60*1000L,
                String.format(
                    getString(R.string.text_remind_the_match_start_before_10_minutes),
                    matchItem.description
                )
            )

            // remind right at the time the match starts
            val worker2Id = startWorker(
                startTime,
                String.format(
                    getString(R.string.text_remind_the_match_start_right_now),
                    matchItem.description
                )
            )

            // save both workers into storage
            viewModel.saveReminderWorker(
                args.matchItem.date,
                "$worker1Id $worker2Id"
            )

            // show set reminder successfully
            showToast(getString(R.string.text_set_reminder_successfully))
        }
    }

    private fun startWorker(startTime: Long, text: String): String {
        // - min repeat interval time of periodic worker is 15 minutes
        // - if the app is force close or clear in recent app, the worker will only start when the app
        // is launched again
        val workRequest = OneTimeWorkRequestBuilder<MatchOneTimeWorker>()
            .setInitialDelay(startTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .setInputData(
                Data.Builder()
                    .put(Constants.KEY_MESSAGE, text)
                    .build()
            )
            .build()

        WorkManager.getInstance(requireContext()).enqueue(workRequest)

        return workRequest.stringId
    }

    private fun showToast(message: CharSequence) {
        Toast.makeText(requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showSnackbar(message: CharSequence) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}