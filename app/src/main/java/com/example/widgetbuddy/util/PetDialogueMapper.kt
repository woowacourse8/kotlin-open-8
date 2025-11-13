package com.example.widgetbuddy.util

object PetDialogueMapper {
    fun getDialogue(
        state: PetState, hunger: Int, joy: Int, petName: String, userName: String
    ): String {
        return when (state) {
            PetState.EGG -> "..."

            PetState.IDLE -> getIdleDialogues(petName, hunger, joy, userName).random()

            PetState.NEEDS_LOVE -> getNeedsLoveDialogue().random()
        }
    }

    private fun getIdleDialogues(
        petName: String, hunger: Int, joy: Int, userName: String
    ): List<String> {
        val statsText = "배고픔: $hunger, 즐거움: $joy"

        return listOf(
            statsText,
            "$userName 보고싶어!",
            "오늘 날씨 어때~",
            "헤헤...",
            "지금 몇 시지?",
            "뒹굴뒹굴...",
            "보고 있었어?",
            "$userName 뭐해?",
            "나 잘 지내고 있어!",
            "역시 집이 최고야.",
            "$petName 이뻐?"
        )
    }

    private fun getNeedsLoveDialogue(): List<String> {
        return listOf(
            "여기 너무 좁아.. ㅠㅠ",
            "더 넓은 곳에서 놀고 싶어!",
            "우리 집에 언제 올 거야?",
            "앱에서 나 좀 만나줘~",
            "할 말 있는데..(톡톡)",
            "(두리번)혹시 지금 시간 돼?"
        )
    }

}