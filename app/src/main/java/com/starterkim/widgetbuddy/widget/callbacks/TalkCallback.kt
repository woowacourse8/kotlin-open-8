package com.starterkim.widgetbuddy.widget.callbacks

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.starterkim.widgetbuddy.data.PetDataStoreKeys
import com.starterkim.widgetbuddy.data.dataStore
import com.starterkim.widgetbuddy.logic.PetStateCalculator
import com.starterkim.widgetbuddy.widget.PetWidget

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
