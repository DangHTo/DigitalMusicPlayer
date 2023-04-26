package com.example.t05

import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.json.JSONObject

class UploadWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    override fun doWork(): Result {

        val json = JSONObject()
        json.put("username", MainActivity.USERNAME)
        json.put("event", inputData.getString("event"))

        Log.d(MainActivity.TAG, "params:" + json.toString() + " url:" + MainActivity.URL)
        return uploadLog(json, MainActivity.URL)
    }

    fun uploadLog(json: JSONObject, url: String): Result{
        var call = TrackerRetrofitService.create(url).postLog(json)
        call.execute()
        return Result.success()
    }
}