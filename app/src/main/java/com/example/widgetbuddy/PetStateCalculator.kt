package com.example.widgetbuddy

import androidx.datastore.preferences.core.MutablePreferences

/**
 * 펫의 상태를 계산하고 업데이트하는 로직을 담당한다.
 */
object PetStateCalculator {
    fun hatchPet(prefs: MutablePreferences): MutablePreferences {
        // TODO: 알 부화
        return prefs
    }

    fun applyPassiveUpdates(prefs: MutablePreferences): MutablePreferences {
        // TODO: 시간 경과에 따른 수동적 업데이트
        return prefs
    }
}