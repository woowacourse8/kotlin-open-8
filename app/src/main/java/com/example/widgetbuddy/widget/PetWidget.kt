package com.example.widgetbuddy.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.widgetbuddy.MainActivity
import com.example.widgetbuddy.data.*
import com.example.widgetbuddy.util.*
import com.example.widgetbuddy.widget.callbacks.*

/**
 * 펫 위젯의 UI 구성을 담당한다.
 */
class PetWidget : GlanceAppWidget() {
    override val stateDefinition = PetStateDefinition
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

        val petName = prefs?.get(PetDataStoreKeys.PET_NAME) ?: "뽀짝이"
        val userName = prefs?.get(PetDataStoreKeys.USER_NAME) ?: "주인님"

        val affectionCount = prefs?.get(PetDataStoreKeys.PET_AFFECTION_COUNT) ?: 0
        val imageRes = PetVisualMapper.getImageResource(petType, petState)

        val satiety = prefs?.get(PetDataStoreKeys.PET_SATIETY) ?: 100
        val joy = prefs?.get(PetDataStoreKeys.PET_JOY) ?: 100
        val petMessage = prefs?.get(PetDataStoreKeys.PET_MESSAGE) ?: ""

        val textToShow = PetDialogueMapper.getDialogue(
            petState,
            satiety,
            joy,
            petName,
            userName,
            petMessage
        )

        // --- UI Layout ---
        Column(
            modifier = GlanceModifier.fillMaxSize().background(Color.White).then(
                when (petState) {
                    PetState.EGG -> GlanceModifier.clickable(
                        onClick = actionRunCallback<HatchCallback>()
                    )
                    PetState.NEEDS_LOVE,
                    PetState.RUNAWAY-> GlanceModifier.clickable(
                        onClick = actionStartActivity<MainActivity>()
                    )
                    else -> GlanceModifier
                }
            ),
            verticalAlignment = Alignment.Vertical.CenterVertically,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            Text(
                text = petName,
                style = TextStyle(
                    color = ColorProvider(Color.Black),
                    fontWeight = FontWeight.Bold
                ),
                modifier = GlanceModifier.padding(bottom = 4.dp)
            )

            Box(
                modifier = GlanceModifier.padding(8.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Image(
                    provider = ImageProvider(imageRes),
                    contentDescription = petState.name,
                    modifier = GlanceModifier.size(80.dp)
                )
                Row(
                    verticalAlignment = Alignment.Vertical.CenterVertically
                ) {
                    Text(
                        text = "❤️",
                        style = TextStyle(color = ColorProvider(Color.Black))
                    )
                    Text(
                        text = "$affectionCount",
                        style = TextStyle(color = ColorProvider(Color.Black))
                    )
                }
            }

            Text(
                text = textToShow,
                style = TextStyle(
                    color = ColorProvider(Color.Black),
                    textAlign = TextAlign.Center
                ),
                modifier = GlanceModifier.padding(bottom = 8.dp)
            )

            if (petState != PetState.EGG && petState != PetState.RUNAWAY) {
                Row(modifier = GlanceModifier.padding(top = 8.dp)) {
                    Button(
                        text = "밥주기",
                        onClick = actionRunCallback<FeedCallback>()
                    )
                    Spacer(modifier = GlanceModifier.width(8.dp))
                    Button(
                        text = "놀아주기",
                        onClick = actionRunCallback<PlayCallback>()
                    )
                }
            }
        }
    }
}