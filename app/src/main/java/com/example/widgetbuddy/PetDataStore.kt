package com.example.widgetbuddy

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/**
 * 앱 전체에서 사용할 DataStore 인스턴스
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "pet_prefs")

/**
 * DataStore의 Preference 키를 관리한다.
 */
object PetDataStoreKeys {
    // TODO: 키 정의
}
