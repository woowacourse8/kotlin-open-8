package com.example.widgetbuddy.logic

import androidx.datastore.preferences.core.MutablePreferences
import com.example.widgetbuddy.data.PetDataStoreKeys
import com.example.widgetbuddy.util.*
import java.time.LocalDate
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * 펫의 상태를 계산하고 업데이트하는 로직을 담당한다.
 */
object PetStateCalculator {
    private const val TEST_TIME = 15L
    private const val REAL_TIME = 60L
    private val ONE_HOUR_MS = TimeUnit.MINUTES.toMillis(REAL_TIME)
    private val ONE_DAY_MS = TimeUnit.DAYS.toMillis(1)

    fun hatchPet(prefs: MutablePreferences): MutablePreferences {
        val petTypeString = prefs[PetDataStoreKeys.PET_TYPE] ?: PetType.NONE.name
        val petType = PetType.fromString(petTypeString)

        if (petType != PetType.NONE) {
            return prefs
        }

        val currentTime = System.currentTimeMillis()

        val newPetType = if (Random.nextBoolean()) PetType.BAPSAE else PetType.DRAGON

        // DataStore 값 업데이트
        prefs[PetDataStoreKeys.PET_TYPE] = newPetType.name
        prefs[PetDataStoreKeys.PET_NAME] = "뽀짝이"

        initializeStat(prefs)
        updateTimeNow(prefs)

        prefs[PetDataStoreKeys.PET_AFFECTION_COUNT] = 0
        prefs[PetDataStoreKeys.LAST_AFFECTION_UPDATE_DATE] = LocalDate.now().toString()

        return prefs
    }

    fun feedPet(prefs: MutablePreferences): MutablePreferences {
        prefs[PetDataStoreKeys.PET_HUNGER] = 0
        val currentJoy = prefs[PetDataStoreKeys.PET_JOY] ?: 90
        prefs[PetDataStoreKeys.PET_JOY] = (currentJoy + 10).coerceAtMost(100)
        prefs[PetDataStoreKeys.LAST_UPDATED_TIMESTAMP] = System.currentTimeMillis()

        checkAndGrantDailyAffection(prefs)
        return prefs
    }

    fun playWithPet(prefs: MutablePreferences): MutablePreferences {
        prefs[PetDataStoreKeys.PET_JOY] = 100
        prefs[PetDataStoreKeys.LAST_UPDATED_TIMESTAMP] = System.currentTimeMillis()

        checkAndGrantDailyAffection(prefs)
        return prefs
    }

    fun bringPetBack(prefs: MutablePreferences): MutablePreferences {
        initializeStat(prefs)
        updateTimeNow(prefs)

        prefs[PetDataStoreKeys.LAST_AFFECTION_UPDATE_DATE] = LocalDate.now().toString()
        return prefs
    }

    fun applyPassiveUpdates(prefs: MutablePreferences): MutablePreferences {
        val currentTime = System.currentTimeMillis()
        val lastUpdatedTime = prefs[PetDataStoreKeys.LAST_UPDATED_TIMESTAMP] ?: currentTime

        val elapsedTime = currentTime - lastUpdatedTime
        val elapsedHours = (elapsedTime / ONE_HOUR_MS).toInt()

        // 1시간 이상 경과했을 때 업데이트
        if (elapsedTime > 0) {
            val currentHunger = prefs[PetDataStoreKeys.PET_HUNGER] ?: 0
            val currentJoy = prefs[PetDataStoreKeys.PET_JOY] ?: 100
            var currentHappiness = prefs[PetDataStoreKeys.PET_HAPPINESS] ?: 100
            var currentMisery = prefs[PetDataStoreKeys.PET_MISERY] ?: 0

            val newHunger = (currentHunger + elapsedHours * 5).coerceAtMost(100)
            val newJoy = (currentJoy - elapsedHours * 5).coerceAtLeast(0)

            if (newJoy < 20) {
                currentHappiness = (currentHappiness - elapsedHours * 5).coerceAtLeast(0)
            }

            currentMisery = if (newHunger > 90 || newJoy < 10) {
                (currentMisery + elapsedHours * 5).coerceAtMost(100)
            } else {
                (currentMisery - elapsedHours * 5).coerceAtLeast(0)
            }

            prefs[PetDataStoreKeys.PET_HUNGER] = newHunger
            prefs[PetDataStoreKeys.PET_JOY] = newJoy
            prefs[PetDataStoreKeys.PET_HAPPINESS] = currentHappiness
            prefs[PetDataStoreKeys.PET_MISERY] = currentMisery
            prefs[PetDataStoreKeys.LAST_UPDATED_TIMESTAMP] = currentTime
        }

        val happiness = prefs[PetDataStoreKeys.PET_HAPPINESS] ?: 100
        val lastAppVisitTime = prefs[PetDataStoreKeys.LAST_MAIN_APP_VISIT_TIMESTAMP] ?: currentTime
        val misery = prefs[PetDataStoreKeys.PET_MISERY] ?: 0

        val isSad = happiness < 30
        val isLonely = (currentTime - lastAppVisitTime) > ONE_DAY_MS

        val hasRunAway = misery >= 100
        val isWarning = misery >= 80

        // 상태 우선 순위: 1. 가출 2. 경고 3. 외로움
        if (hasRunAway) {
            prefs[PetDataStoreKeys.PET_STATE] = PetState.RUNAWAY.name
        } else if (isWarning) {
            prefs[PetDataStoreKeys.PET_STATE] = PetState.WARNING.name
        } else if (isSad || isLonely) {
            prefs[PetDataStoreKeys.PET_STATE] = PetState.NEEDS_LOVE.name
        } else {
            val currentState = PetState.fromString(prefs[PetDataStoreKeys.PET_STATE])
            if (currentState != PetState.EGG) {
                prefs[PetDataStoreKeys.PET_STATE] = PetState.IDLE.name
            }
        }

        return prefs
    }



    internal fun checkAndGrantDailyAffection(prefs: MutablePreferences): MutablePreferences {
        val today = LocalDate.now().toString()
        val lastUpdateDate = prefs[PetDataStoreKeys.LAST_AFFECTION_UPDATE_DATE] ?: ""

        if (today != lastUpdateDate) {
            val currentAffection = prefs[PetDataStoreKeys.PET_AFFECTION_COUNT] ?: 0
            prefs[PetDataStoreKeys.PET_AFFECTION_COUNT] = currentAffection + 1
            prefs[PetDataStoreKeys.LAST_AFFECTION_UPDATE_DATE] = today
        }

        return prefs
    }

    private fun initializeStat(prefs: MutablePreferences) {
        prefs[PetDataStoreKeys.PET_STATE] = PetState.IDLE.name
        prefs[PetDataStoreKeys.PET_HUNGER] = 0
        prefs[PetDataStoreKeys.PET_JOY] = 100
        prefs[PetDataStoreKeys.PET_HAPPINESS] = 100
        prefs[PetDataStoreKeys.PET_MISERY] = 0
    }

    private fun updateTimeNow(prefs: MutablePreferences) {
        val currentTime = System.currentTimeMillis()
        prefs[PetDataStoreKeys.LAST_UPDATED_TIMESTAMP] = currentTime
        prefs[PetDataStoreKeys.LAST_MAIN_APP_VISIT_TIMESTAMP] = currentTime
    }
}