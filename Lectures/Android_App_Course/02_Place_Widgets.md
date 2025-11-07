# 2. 위젯 배치하기

### 📅 날짜
2025-11-07

### 🔗 강의 링크
* https://www.youtube.com/watch?v=TtzCEja8HSQ

---

### 💡 핵심 요약
* 화면에 텍스트와 버튼을 배치해본다.

### 🗒️ 노트
지난 시간에, `Empty Activity` 템플릿을 선택해, 프로젝트를 생성했었다.
그런데, 강의는 2021년도의 안드로이드 스튜디오 버전을 따라서 강의대로 진행하기 위해
`Empty Views Activity` 템플릿으로 새로 프로젝트를 만들었다.
<img width="785" height="554" alt="image" src="https://github.com/user-attachments/assets/58a53fab-36dd-4108-8f6b-edfaefa7d899" />
-> Practice3 라는 프로젝트 생성함.

### 🔑 주요 개념 및 코드

#### 1. 텍스트 레이아웃에 제한 조건이 없다면, 기본값은 왼쪽/위쪽이다.
<img width="286" height="584" alt="image" src="https://github.com/user-attachments/assets/83106746-de59-40f6-86e6-8c4c765ec68c" />

#### 2. 레이아웃 설정은 3가지가 가능하다.
* wrap_content
<img width="715" heigt="497" alt="image" src="https://github.com/user-attachments/assets/6a0eedbb-80e4-4d8b-bfeb-197ea1028689" />
지정해놓은 위치에 텍스트 상자의 크기가 유연하게 배치된다.

* match constraint
<img width="720" height="524" alt="image" src="https://github.com/user-attachments/assets/4a34b38c-462e-4257-a812-ed0688edbf6d" />
지정한 마진에 맞춰 텍스트 상자가 늘어난다.

* 고정
<img width="799" height="617" alt="image" src="https://github.com/user-attachments/assets/217cd152-b53c-4a7b-be57-eddd72cdaf6f" />
텍스트의 위치가 지정해놓은 곳에 고정되고, 텍스트가 텍스트 상자를 벗어나면 화면에 표시되지 않는다.
