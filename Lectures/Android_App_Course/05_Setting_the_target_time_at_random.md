# 5. 목표 시간 랜덤으로 정하기

### 📅 날짜
2025-11-07

### 🔗 강의 링크
* [목표 시간 랜덤으로 정하기 (링크)](https://www.youtube.com/watch?v=CWx0xFhrb1Q)

---

### 💡 핵심 요약
* 어플을 실행하면 랜덤 시간 값이 나오고, 버튼을 누르면 타이머가 가동/비가동된다.
* 랜덤 시간 값과 타이머 차이 값으로 포인트를 구한다.

### 🔑 주요 개념 및 코드

#### 1. 랜덤 인수 값 구하기
```kotlin
val randomBox = Random()
        val randomNum = randomBox.nextInt(1001)
        tvR.text = (randomNum.toFloat() / 100).toString()
```

#### 2. 정지한 타이머와 랜덤 인수 값 차이로 포인트 구하기
```kotlin
btn.setOnClickListener {
            isRunning = !isRunning

            if (isRunning) {
                timerTask = timer(period = 10) {
                    sec++
                    runOnUiThread {
                        tvT.text = (sec / 100).toString()
                    }
                }
            } else {
                timerTask?.cancel()
                val point = abs(sec - randomNum) / 100
                tvP.text = point.toString()
            }
        }
```
#### 🎥 시연영상
[PracticeApp.webm](https://github.com/user-attachments/assets/252302df-1cc2-43e2-8b82-b18296ccda36)

