package com.example.widgetbuddy.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

/**
 * WorkManager에 의해 주기적으로 실행되어
 * 펫의 수동적 상태 업데이트를 처리한다.
 */
class PetTickWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        // TODO: DataStore 업데이트
        return Result.success()
    }
}