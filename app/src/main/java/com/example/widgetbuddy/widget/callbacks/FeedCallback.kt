package com.example.widgetbuddy.widget.callbacks

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.example.widgetbuddy.data.PetDataStoreKeys
import com.example.widgetbuddy.data.dataStore
import com.example.widgetbuddy.logic.PetStateCalculator
import com.example.widgetbuddy.util.PetState
import com.example.widgetbuddy.widget.PetWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FeedCallback : ActionCallback {
    private val scope = CoroutineScope(Dispatchers.IO)

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        context.dataStore.updateData { immutablePrefs ->
            val mutablePrefs = immutablePrefs.toMutablePreferences()
            PetStateCalculator.feedPet(mutablePrefs)
            mutablePrefs[PetDataStoreKeys.PET_STATE] = PetState.FULL_FEEDBACK.name
            mutablePrefs
        }

        PetWidget().update(context, glanceId)

        scope.launch {
            delay(5000L) // 5초 대기

            context.dataStore.updateData { immutablePrefs ->
                val mutablePrefs = immutablePrefs.toMutablePreferences()

                mutablePrefs[PetDataStoreKeys.PET_STATE] = PetState.IDLE.name
                mutablePrefs
            }

            PetWidget().update(context, glanceId)
        }
    }
}