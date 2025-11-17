package com.example.widgetbuddy.util

object PetDialogueMapper {
    fun getDialogue(
        state: PetState, satiety: Int, joy: Int, petName: String, userName: String, petMessage: String
    ): String {
        if (petMessage.isNotBlank()) return petMessage

        return when (state) {
            PetState.EGG -> "..."
            PetState.IDLE -> getIdleDialogues(petName, satiety, joy, userName).random()
            PetState.NEEDS_LOVE -> getNeedsLoveDialogue().random()
            PetState.SATIETY_LOW -> "배고파... 밥 줘! (포만감: $satiety)"
            PetState.BORED -> "심심해...놀아줘! (즐거움: $joy)"
            PetState.WARNING -> getWarningDialogue().random()
            PetState.RUNAWAY -> getRunAwayDialogue().random()
        }
    }

    private fun getIdleDialogues(
        petName: String, satiety: Int, joy: Int, userName: String
    ): List<String> {
        val statsText = "포만감: $satiety, 즐거움: $joy"

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

    private fun getWarningDialogue(): List<String> {
        return listOf(
            "이젠..정말 지쳤어.",
            "아무래도.. 우리 인연이 아닌가 봐",
            "나..혹시..버려진 거야? ㅠㅠ",
            "나.. 완전히 잊힌 거 같아.",
            "떠날 준비를.. 해야 할지도 모르겠어."
        )
    }

    private fun getRunAwayDialogue(): List<String> {
        return listOf(
            "잠시.. 멀리 여행 좀 다녀올게.",
            "기다렸는데.. 결국 오지 않았네. \n 잘 지내.",
            "더이상 여기 있을 이유가 없어진 것 같아. \n 안녕.",
            "나 혹시 귀찮아졌어..? \n 조용히 사라져줄게."
        )
    }
}