package com.example.widgetbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.widgetbuddy.data.dataStore
import androidx.glance.appwidget.updateAll
import com.example.widgetbuddy.data.PetDataStoreKeys
import com.example.widgetbuddy.ui.theme.WidgetBuddyTheme
import com.example.widgetbuddy.util.PetState
import com.example.widgetbuddy.widget.PetWidget
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        healPetOnAppVisit()
        setContent {
            Text("펫에게 사랑을 줬습니다! ❤️")
        }
    }

    private fun healPetOnAppVisit() {
        this.lifecycleScope.launch {
            dataStore.updateData { immutablePrefs ->
                val mutablePrefs = immutablePrefs.toMutablePreferences()

                mutablePrefs[PetDataStoreKeys.PET_HAPPINESS] = 100
                mutablePrefs[PetDataStoreKeys.LAST_MAIN_APP_VISIT_TIMESTAMP] =
                    System.currentTimeMillis()
                mutablePrefs[PetDataStoreKeys.PET_STATE] = PetState.IDLE.name

                mutablePrefs
            }
            PetWidget().updateAll(this@MainActivity)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WidgetBuddyTheme {
        Greeting("Android")
    }
}
