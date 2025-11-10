package com.example.widgetbuddy.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.widgetbuddy.data.PetDataStoreKeys
import com.example.widgetbuddy.util.*

/**
 * 펫 위젯의 UI 구성을 담당한다.
 */
class PetWidget : GlanceAppWidget() {
    private val TAG = "PetWidget"

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val prefs = currentState<Preferences>()
            Content(prefs = prefs)
        }
    }

    @SuppressLint("RestrictedApi")
    @Composable
    fun Content(prefs: Preferences?) {
        val petStateString = prefs?.get(PetDataStoreKeys.PET_STATE) ?: PetState.EGG.name
        val petTypeString = prefs?.get(PetDataStoreKeys.PET_TYPE) ?: PetType.NONE.name

        val petState = PetState.fromString(petStateString)
        val petType = PetType.fromString(petTypeString)

        Log.d(TAG, "Content: petState = $petState, petType = $petType")

        val imageRes = PetVisualMapper.getImageResource(petType, petState)

        val textToShow = when (petState) {
            PetState.EGG -> "뽀짝 뽀짝"
            PetState.IDLE -> "오늘 (배고픔 ${prefs?.get(PetDataStoreKeys.PET_HUNGER) ?: 0}"
            PetState.NEEDS_LOVE -> "외로워요.. 앱을 방문해 주세요!"
        }

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.White),
            verticalAlignment = Alignment.Vertical.CenterVertically,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            Image(
                provider = ImageProvider(imageRes),
                contentDescription = petState.name,
                modifier = GlanceModifier.padding(8.dp)
            )
            Text(
                text = textToShow,
                style = TextStyle(color = ColorProvider(Color.Black))
            )
        }
    }
}