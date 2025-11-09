# 4. 시간 표시 로직 적용하기

### 📅 날짜
2025-11-07

### 🔗 강의 링크
* [시간 표시 로직 적용하기 (링크)](https://www.youtube.com/watch?v=d6F7GoDISls)

---

### 💡 핵심 요약
* UI 조작은 메인 스레드에서만 가능하다.

### 🔑 주요 개념 및 코드

#### 1. 타이머 기능 만들기
```kotlin
# 1초마다 1씩 초가 증가하는 함수
var sec: Int = 0

        timer(period = 1000) {
            sec++
            println(sec)
        }
```
#### 2. 밖에서 UI 를 만지면 오류가 난다.
```kotlin
// 🚨 오류
timer(period = 1000) {
            sec++
            tv.text = sec.toString()
        }

// 🟢 정상 작동
timer(period = 1000) {
            sec++
            runOnUiThread {
                tv.text = sec.toString()
            }
        }
```
타이머(보조 작업자)가 UI(캔버스)를 직접 조작하는 것은 규칙 위반이다.  
데이터가 꼬이거나 충돌하거나 앱 크래시가 발생할 수 있기 때문이다.  
따라서 `runOnUiThread`를 사용한다. `runOnUiThread`는 타이머가 캔버스를 직접 만지게 
허용하는 것이 아닌, 타이머 같은 다른 스레드가 메인 스레드에게 작업을 요청하는 행위이다.

#### 3. 버튼 클릭시, 타이머 가동/비가동
```kotlin
var timerTask: Timer? = null

        var isRunning = false
        var sec: Int = 0
        val tv: TextView = findViewById(R.id.tv_hello)
        val btn: Button = findViewById(R.id.btn_kor)

        btn.setOnClickListener {
            isRunning = !isRunning

            if (isRunning) {
                timerTask = timer(period = 1000) {
                    sec++
                    runOnUiThread {
                        tv.text = sec.toString()
                    }
                }
            } else {
                timerTask?.cancel()
            }
        }
```
[기능 실행 영상](https://github.com/user-attachments/assets/155f5ccc-d217-4a4a-aefe-372c0c80014e)
