package com.tngdev.sportevent.worker

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.provider.SyncStateContract
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.tngdev.sportevent.Constants
import com.tngdev.sportevent.R
import com.tngdev.sportevent.util.NotificationUtils
import timber.log.Timber

class MatchOneTimeWorker (
    val context: Context, workerParameters: WorkerParameters
): Worker(context, workerParameters) {
    override fun doWork(): Result {
        val message = inputData.getString(Constants.KEY_MESSAGE).orEmpty()

        val logMessage = "${MatchOneTimeWorker::class.java.simpleName} " +
                "start message: $message "
        Timber.d( "$logMessage ${System.currentTimeMillis()}")
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, logMessage, Toast.LENGTH_SHORT).show()
        }

        NotificationUtils.showNotification(
            context,
            context.getString(R.string.notification_title_reminder),
            message,
            extras = null
        )

        return Result.success()
    }

}