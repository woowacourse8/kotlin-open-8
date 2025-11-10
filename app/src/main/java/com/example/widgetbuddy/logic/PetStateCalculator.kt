package com.example.widgetbuddy.logic

import androidx.datastore.preferences.core.MutablePreferences
import com.example.widgetbuddy.data.PetDataStoreKeys
import com.example.widgetbuddy.util.*
import kotlin.random.Random

/**
 * 펫의 상태를 계산하고 업데이트하는 로직을 담당한다.
 */
object PetStateCalculator {
    private const val ONE_HOUR_MS = 60 * 60 * 1000L
    private const val ONE_DAY_MS = 24 * ONE_HOUR_MS

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
        prefs[PetDataStoreKeys.PET_STATE] = PetState.IDLE.name
        prefs[PetDataStoreKeys.PET_HUNGER] = 0
        prefs[PetDataStoreKeys.PET_HAPPINESS] = 100
        prefs[PetDataStoreKeys.LAST_UPDATED_TIMESTAMP] = currentTime
        prefs[PetDataStoreKeys.LAST_MAIN_APP_VISIT_TIMESTAMP] = currentTime

        return prefs
    }

    fun applyPassiveUpdates(prefs: MutablePreferences): MutablePreferences {
        val currentTime = System.currentTimeMillis()
        val lastUpdatedTime = prefs[PetDataStoreKeys.LAST_UPDATED_TIMESTAMP] ?: currentTime
        val lastAppVisitTime = prefs[PetDataStoreKeys.LAST_MAIN_APP_VISIT_TIMESTAMP] ?: currentTime

        val elapsedTime = currentTime - lastUpdatedTime
        val elapsedHours = (elapsedTime / ONE_HOUR_MS).toInt()

        // 1시간 이상 경과했을 때 업데이트
        if (elapsedTime > 0) {
            val currentHunger = prefs[PetDataStoreKeys.PET_HUNGER] ?: 0
            val currentHappiness = prefs[PetDataStoreKeys.PET_HAPPINESS] ?: 100

            val newHunger = (currentHunger + elapsedHours * 5).coerceAtMost(100)
            val newHappiness = (currentHappiness - elapsedHours * 5).coerceAtLeast(0)

            prefs[PetDataStoreKeys.LAST_UPDATED_TIMESTAMP] = currentTime
        }

        if (currentTime - lastAppVisitTime > ONE_DAY_MS) {
            prefs[PetDataStoreKeys.PET_STATE] = PetState.NEEDS_LOVE.name
        }

        return prefs
    }
}