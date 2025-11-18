package com.example.widgetbuddy.widget.callbacks

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.example.widgetbuddy.data.PetDataStoreKeys
import com.example.widgetbuddy.data.dataStore
import com.example.widgetbuddy.logic.PetStateCalculator
import com.example.widgetbuddy.widget.PetWidget

class TalkCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        context.dataStore.updateData { immutablePrefs ->
            val mutablePrefs = immutablePrefs.toMutablePreferences()
            mutablePrefs[PetDataStoreKeys.PET_MESSAGE] = ""
            PetStateCalculator.checkAndGrantDailyAffection(mutablePrefs)
            mutablePrefs[PetDataStoreKeys.LAST_UPDATED_TIMESTAMP] = System.currentTimeMillis()
            mutablePrefs
        }

        PetWidget().update(context, glanceId)
    }
}
