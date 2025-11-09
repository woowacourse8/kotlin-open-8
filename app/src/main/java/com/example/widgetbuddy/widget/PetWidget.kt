package com.example.widgetbuddy.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState

/**
 * 펫 위젯의 UI 구성을 담당한다.
 */
class PetWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val prefs = currentState<Preferences>()
            Content(prefs = prefs)
        }
    }

    @Composable
    fun Content(prefs: Preferences?) {

    }
}