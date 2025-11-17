package com.example.widgetbuddy

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.lifecycleScope
import com.example.widgetbuddy.data.PetDataStoreKeys
import com.example.widgetbuddy.data.dataStore
import com.example.widgetbuddy.logic.PetStateCalculator
import com.example.widgetbuddy.ui.theme.WidgetBuddyTheme
import com.example.widgetbuddy.util.PetState
import com.example.widgetbuddy.util.PetType
import com.example.widgetbuddy.util.PetVisualMapper
import com.example.widgetbuddy.widget.PetWidget
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    private var mRewardedAd: RewardedAd? = null
    private val tag = "MainActivity"

    // 테스트 보상형 광고 단위 ID
    private val AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this) {}
        loadRewardedAd()

        setContent {
            WidgetBuddyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val petState by dataStore.data.map {
                        PetState.fromString(it[PetDataStoreKeys.PET_STATE])
                    }.collectAsState(initial = PetState.EGG)

                    val petType by dataStore.data.map {
                        PetType.fromString(it[PetDataStoreKeys.PET_TYPE])
                    }.collectAsState(initial = PetType.NONE)

                    val decorPoints by dataStore.data.map {
                        it[PetDataStoreKeys.DECOR_POINTS] ?: 0
                    }.collectAsState(initial = 0)

                    PetRoomScreen(petState, petType, decorPoints)
                }
            }
        }
    }

    @Composable
    fun PetRoomScreen(petState: PetState, petType: PetType, decorPoints: Int) {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        val petState by dataStore.data.map {
            PetState.fromString(it[PetDataStoreKeys.PET_STATE])
        }.collectAsState(initial = PetState.EGG)

        val petType by dataStore.data.map {
            PetType.fromString(it[PetDataStoreKeys.PET_TYPE])
        }.collectAsState(initial = PetType.NONE)

        val decorPoints by dataStore.data.map {
            it[PetDataStoreKeys.DECOR_POINTS] ?: 0
        }.collectAsState(initial = 0)

        // 메인 UI
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- 1. 펫의 방 (상단 50%) ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                // (나중에 R.drawable.room_background 이미지로 교체)

                // 펫 이미지 (가운데)
                Image(
                    painter = painterResource(
                        id = PetVisualMapper.getImageResource(petType, petState)
                    ),
                    contentDescription = "Pet",
                    modifier = Modifier.size(120.dp)
                )

                if (decorPoints >= 5) {
                    Image(
                        painter = painterResource(id = R.drawable.pot), // 👈 (drawable에 pot.png 추가 필요)
                        contentDescription = "화분",
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .size(50.dp)
                    )
                }

                if (decorPoints >= 10) {
                    Image(
                        painter = painterResource(id = R.drawable.cushion), // 👈 (drawable에 cushion.png 추가 필요)
                        contentDescription = "쿠션",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(60.dp)
                    )
                }
                // (포인트 15, 25 ... 계속 추가)
            }

            // --- 2. 컨트롤러 (하단) ---
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (petState != PetState.RUNAWAY) {
                    Button(onClick = {
                        lifecycleScope.launch {
                            val (totalPoints, didIncrease) = giveLoveAndGetPoints(context)

                            PetWidget().updateAll(context)

                            if (didIncrease) {
                                when (totalPoints) {
                                    5 -> Toast.makeText(
                                        context,
                                        "방구석에 예쁜 화분이 생겼다!",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    10 -> Toast.makeText(context, "푹신한 쿠션이 생겼다!", Toast.LENGTH_LONG)
                                        .show()
                                    // ...
                                    else -> Toast.makeText(
                                        context,
                                        "사랑 주기 완료! (현재 $totalPoints p)",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "오늘은 이미 사랑을 줬어요. (총 $totalPoints p)",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }) {
                        Text("사랑 주기 ❤️ (포인트 +1)")
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
                NamingScreen(currentPetState = petState)
            }
        }
    }

    @Composable
    fun NamingScreen(currentPetState: PetState) {
        var petNameInput by remember { mutableStateOf("") }
        var userNameInput by remember { mutableStateOf("") }

        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (currentPetState == PetState.RUNAWAY) {
                Text(
                    text = "펫이 가출했습니다...🥲",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    mRewardedAd?.let { ad ->
                        ad.show(this@MainActivity) { rewardItem ->
                            Log.d(tag, "User earned the reward.")
                            bringPetBackAfterAd()
                        }
                    } ?: run {
                        Log.d(tag, "The rewarded ad wasn't ready yet.")
                        Toast.makeText(
                            this@MainActivity,
                            "광고 로드 중.. 잠시 후 다시 시도하세요.",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }) {
                    Text("[광고 시청] 펫 다시 데려오기")
                }
                Spacer(modifier = Modifier.height(48.dp))
            }

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

    private suspend fun giveLoveAndGetPoints(context: Context): Pair<Int, Boolean> {
        var finalDecorPoints = 0
        var didPointsIncrease = false

        dataStore.updateData { immutablePrefs ->
            if (PetState.fromString(immutablePrefs[PetDataStoreKeys.PET_STATE]) == PetState.RUNAWAY) {
                finalDecorPoints = immutablePrefs[PetDataStoreKeys.DECOR_POINTS] ?: 0
                return@updateData immutablePrefs
            }

            val mutablePrefs = immutablePrefs.toMutablePreferences()

            mutablePrefs[PetDataStoreKeys.PET_HAPPINESS] = 100
            mutablePrefs[PetDataStoreKeys.LAST_MAIN_APP_VISIT_TIMESTAMP] =
                System.currentTimeMillis()
            mutablePrefs[PetDataStoreKeys.PET_STATE] = PetState.IDLE.name

            var currentPoints = mutablePrefs[PetDataStoreKeys.DECOR_POINTS] ?: 0

            val today = LocalDate.now().toString()
            val lastUpdateDate = mutablePrefs[PetDataStoreKeys.LAST_AFFECTION_UPDATE_DATE] ?: ""

            if (today != lastUpdateDate) {
                didPointsIncrease = true

                val currentAffection = mutablePrefs[PetDataStoreKeys.PET_AFFECTION_COUNT] ?: 0
                mutablePrefs[PetDataStoreKeys.PET_AFFECTION_COUNT] = currentAffection + 1
                mutablePrefs[PetDataStoreKeys.LAST_AFFECTION_UPDATE_DATE] = today

                currentPoints += 1
                mutablePrefs[PetDataStoreKeys.DECOR_POINTS] = currentPoints
            }

            val currentUserName = mutablePrefs[PetDataStoreKeys.USER_NAME]
            if (currentUserName == null || currentUserName.isBlank()) {
                mutablePrefs[PetDataStoreKeys.USER_NAME] = "주인님"
            }

            finalDecorPoints = currentPoints
            mutablePrefs
        }
        return Pair(finalDecorPoints, didPointsIncrease)
    }

    private fun loadRewardedAd() {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(this, AD_UNIT_ID, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(tag, adError.toString())
                mRewardedAd = null
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                Log.d(tag, "Ad was loaded.")
                mRewardedAd = rewardedAd
            }
        })
    }

    private fun bringPetBackAfterAd() {
        lifecycleScope.launch {
            dataStore.updateData { prefs ->
                PetStateCalculator.bringPetBack(prefs.toMutablePreferences())
            }
            PetWidget().updateAll(this@MainActivity)
            Toast.makeText(this@MainActivity, "펫이 돌아왔습니다!", Toast.LENGTH_SHORT).show()

            loadRewardedAd()
        }
    }
}
