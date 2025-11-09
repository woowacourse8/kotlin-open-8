# 1. Jetpack Glance

### Jetpack Glance란?
* 앱 위젯을 만드는 최신 방식이다. 
* 이전에는 `RemoteViews`라는 복잡한 방식으로 UI를 만들었지만, `Glance`는 Jetpack Compose처럼
선언형 코드로 훨씬 쉽고 직관적으로 UI를 만들 수 있게 해준다.  
`RemoteViews`) '아이스크림 내기'어플 강의를 선행 학습하면서 사용했던 방식이다.
XML 레이아웃 파일을 기반으로 UI를 만드는 전통적인 방식을 의미한다.

### 본 프로젝트에 필요한 이유
* [Phase 3] 위젯 표시: `DataStore`에 저장된 펫의 상태를 읽어와, 그에 맞는 펫의 이미지를 홈 스크린에 표시해야 한다.
`Glance`가 바로 이 표시를 담당한다.
* [Phase 4] 상호작용: 사용자가 위젯의 '먹이주기' 버튼을 누르는 것을 감지해야 한다.
`Glance`의 `actionRunCallback`이 바로 이 상호작용을 감지하고 처리하는 역할을 한다.
