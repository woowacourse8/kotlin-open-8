package com.example.widgetbuddy.util

import com.example.widgetbuddy.R

/**
 * 펫의 상태(type, state)를 기반으로
 * 적절한 시각적 리소스(이미지) ID를 반환(매핑)합니다.
 */
object PetVisualMapper {
    fun getImageResource(type: PetType, state: PetState): Int {
        if (state == PetState.EGG) {
            R.drawable.egg
        }

        return when (state) {
            PetState.IDLE -> type.idleImage
            PetState.NEEDS_LOVE -> type.lonelyImage
            PetState.WARNING -> type.warningImage
            PetState.RUNAWAY -> R.drawable.message
            else -> type.idleImage
        }
    }
}