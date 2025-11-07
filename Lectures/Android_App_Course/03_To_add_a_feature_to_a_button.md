# 3. 버튼에 기능 추가하기

### 📅 날짜
2025-11-07

### 🔗 강의 링크
* https://www.youtube.com/watch?v=ngmjy5DFu8E

---

### 💡 핵심 요약
* 버튼을 클릭했을 때, 텍스트 뷰의 텍스트 내용을 변경한다.

### 🔑 주요 개념 및 코드

#### 1. 텍스트 뷰의 텍스트 내용 바꾸기
```kotlin
# 예시 코드
val textView: TextView = findViewById(R.id.android_text) as TextView
        textView.setOnClickListener {
            textView.text = getString(R.string.name)
        }
```

#### 2. 버튼 클릭시, "안녕"으로 텍스트 내용 바꾸기
```kotlin
val tv: TextView = findViewById(R.id.tv_hello)
        val btn: Button = findViewById(R.id.btn_kor)

        btn.setOnClickListener {
            tv.text = "안녕"
        }
```
