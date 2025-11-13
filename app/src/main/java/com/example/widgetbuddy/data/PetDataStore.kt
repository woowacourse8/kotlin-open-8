package com.example.widgetbuddy.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.glance.state.GlanceStateDefinition
import java.io.File

/**
 * 앱 전체에서 사용할 DataStore 인스턴스
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "pet_prefs")

object PetStateDefinition : GlanceStateDefinition<Preferences> {
    override suspend fun getDataStore(context: Context, fileKey: String): DataStore<Preferences> {
        return context.dataStore
    }

    override fun getLocation(context: Context, fileKey: String): File {
        return context.preferencesDataStoreFile("pet_prefs")
    }
}

/**
 * DataStore의 Preference 키를 관리한다.
 */
object PetDataStoreKeys {
    // 펫 현재 상태
    val PET_STATE = stringPreferencesKey("pet_state")

    // 펫 종류
    val PET_TYPE = stringPreferencesKey("pet_type")

    // 펫 스탯
    val PET_HUNGER = intPreferencesKey("pet_hunger")
    val PET_HAPPINESS = intPreferencesKey("pet_happiness")
    val PET_JOY = intPreferencesKey("pet_joy")

    // 타임스탬프
    val LAST_UPDATED_TIMESTAMP = longPreferencesKey("last_updated_timestamp")
    val LAST_MAIN_APP_VISIT_TIMESTAMP = longPreferencesKey("last_main_app_visit_timestamp")

    // 펫 애정도
    val PET_AFFECTION_COUNT = intPreferencesKey("pet_affection_count")
    val LAST_AFFECTION_UPDATE_DATE = stringPreferencesKey("last_affection_update_date")
}
