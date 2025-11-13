package com.example.widgetbuddy

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.preferencesDataStore
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.lifecycleScope
import com.example.widgetbuddy.data.PetDataStoreKeys
import com.example.widgetbuddy.data.dataStore
import com.example.widgetbuddy.logic.PetStateCalculator.checkAndGrantDailyAffection
import com.example.widgetbuddy.util.PetState
import com.example.widgetbuddy.widget.PetWidget
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        healPetOnAppVisit()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                NamingScreen()
            }
        }
    }

    @Composable
    fun NamingScreen() {
        var petNameInput by remember { mutableStateOf("") }
        var userNameInput by remember { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "펫의 새 이름을 지어주세요!",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = petNameInput,
                onValueChange = { petNameInput = it },
                label = { Text("펫 이름") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (petNameInput.isNotBlank()) {
                    coroutineScope.launch {
                        context.dataStore.updateData { prefs ->
                            prefs.toMutablePreferences().apply {
                                set(PetDataStoreKeys.PET_NAME, petNameInput)
                            }
                        }
                        PetWidget().updateAll(context)
                        Toast.makeText(context, "이름 저장 완료!", Toast.LENGTH_SHORT).show()
                        petNameInput = ""
                    }
                }
            }) {
                Text("이름 저장하기")
            }

            Spacer(modifier = Modifier.height(48.dp))
            // --- 유저 이름 입력 ---
            Text(
                text = "주인님 이름을 알려주세요!",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = userNameInput,
                onValueChange = { userNameInput = it },
                label = { Text("주인님 이름") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (userNameInput.isNotBlank()) {
                    coroutineScope.launch {
                        context.dataStore.updateData { prefs ->
                            prefs.toMutablePreferences().apply {
                                set(PetDataStoreKeys.USER_NAME, userNameInput)
                            }
                        }
                        PetWidget().updateAll(context)
                        Toast.makeText(context, "주인님 이름 저장!", Toast.LENGTH_SHORT).show()
                        userNameInput = ""
                    }
                }
            }) {
                Text("주인님 이름 저장하기")
            }
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

                checkAndGrantDailyAffection(mutablePrefs)

                val currentUserName = mutablePrefs[PetDataStoreKeys.USER_NAME]
                if (currentUserName.isNullOrBlank()) {
                    mutablePrefs[PetDataStoreKeys.USER_NAME] = "주인님"
                }

                mutablePrefs
            }
            PetWidget().updateAll(this@MainActivity)
        }
    }
}
