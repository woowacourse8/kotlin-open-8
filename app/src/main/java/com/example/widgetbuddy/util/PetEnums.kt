package com.example.widgetbuddy.util

import com.example.widgetbuddy.R

/**
 * 펫의 상태를 나타내는 Enum
 * DataStore 에는 이 Enum의 'name' 이 String 으로 저장됨.
 */
enum class PetState {
    EGG,
    IDLE,
    NEEDS_LOVE,
    SATIETY_LOW,
    BORED,
    WARNING,
    RUNAWAY;

    companion object {
        fun fromString(value: String?): PetState {
            return entries.find { it.name == value } ?: EGG
        }
    }
}

/**
 * 펫의 종류를 나타내는 Enum
 * 각 Enum 자체가 자신에게 필요한 리소스 ID를 속성으로 가짐
 */
enum class PetType(
    val idleImage: Int,
    val lonelyImage: Int,
    val warningImage: Int
) {
    BAPSAE(
        idleImage = R.drawable.bapsae_idle,
        lonelyImage = R.drawable.bapsae_lonely,
        warningImage = R.drawable.bapsae_warning
    ),
    DRAGON(
        idleImage = R.drawable.dragon_idle,
        lonelyImage = R.drawable.dragon_lonely,
        warningImage = R.drawable.dragon_warning
    ),
    NONE(
        idleImage = R.drawable.egg,
        lonelyImage = R.drawable.egg,
        warningImage = R.drawable.egg
    );

    companion object {
        fun fromString(value: String?): PetType {
            return entries.find { it.name == value } ?: NONE
        }
    }
}
