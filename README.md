# [오픈 미션] 위젯으로 키우는 펫 🐉

> 나만의 미션을 설계하고 구현한다.


## 📖 프로젝트 소개

* 이 프로젝트는 우아한테크코스 프리코스 4주차 미션을 위해 진행하는 **'안드로이드 홈 스크린 위젯'** 기반의 펫 키우기 게임입니다.
* 별도의 앱 실행 없이, 홈 스크린에 처음 나타난 알을 부화시키고, 간단한 터치(상호작용)를 통해 랜덤하게 등장한 펫(뱁새/용)을 육성하며, 사용자가 앱을 보지 않을 때도 펫이 스스로 상태가 변하는 '살아있는' 게임을 목표로 합니다. 
* **개발 기간:** 2025.11.05 ~ 2025.11.24 (3주)


## 🚀 미션 설계 의도

#### 1. 프리코스와의 연관성
프리코스 기간 중 **'계산기', '자동차 경주', '로또'** 미션을 수행하며, Kotlin 언어의 문법에 익숙해졌습니다.
또한, 객체지향 설계 원칙, TDD 접근 방식, IntelliJ 프로그램 학습, 명확한 커밋 메시지 작성 등 요구사항을 분석하고 코드로 구현하는 훈련을 했습니다.

이전 미션들이 주로 콘솔 환경에서 로직을 구현하는데 중점을 두었다면, 이번 오픈 미션에서는 프리코스에서 Kotlin 언어와 문제 해결 접근 방식을 **'안드로이드'** 라는 새로운 플랫폼에 적용하는 도전을 하고자 합니다.

#### 2. 이 주제를 선택한 이유

게임은 제가 올해 초, 처음 개발을 해보도록 만들어준 계기입니다. 게임을 만들어보고 싶어서 개발에
입문하게 되었습니다. 프리코스 과정에서 배운 Kotlin 언어로 게임을 만들어보는 것은,
제가 해보고 싶었던 일이자 생각만 해도 설레는 일입니다.

또한, '콘솔 앱 개발'에서 '안드로이드 위젯 개발'로의 도약은 저에게 큰 도전입니다.
이 도전을 위해 다음과 같은 마음가짐으로 미션에 임합니다.

* **학습 영역의 확장**  
콘솔 환경에서 익힌 Kotlin 지식을 Android UI 환경, 특히 '고전적 `RemoteViews`' 방식이 아닌, 선언형 UI를 제공하는 `Jetpack Glance`로 확장해 봅니다.
* **'스스로 학습'의 실천**  
프리코스에서 배운 '스스로 학습하고 적용하는 경험'을 바탕으로, 안드로이드 공식 문서와 '아이스크림 내기'
앱 튜토리얼 등을 탐색하며 새로운 기술을 주도적으로 익힙니다.
* **과정의 기록**  
2주간 완벽한 앱을 만들기보다, 새로운 기술에 도전하며 겪는 문제 해결 과정(고민, 버그, 해결책)을
`README`와 커밋 로그에 충실히 기록하는 것을 더 중요하게 생각합니다.


## 🎯 이번 미션의 도전 목표

1.  **`Jetpack Glance`의 이해 (핵심 목표)**
    * `AppWidgetProvider` 대신 `GlanceAppWidgetReceiver`와 `GlanceAppWidget`을 상속받아 위젯의 생명주기를 관리해 봅니다.
    * `RemoteViews`의 복잡함 대신, Jetpack Compose와 유사한 선언형 코드로 위젯 UI를 구성하고 업데이트하는 방법을 익힙니다.
2.  **안정적인 위젯-앱 상호작용 및 상태 관리 구현**
    * `PendingIntent`와 `BroadcastReceiver` 조합 대신, Glance의 `actionRunCallback`을 활용하여 위젯 내 클릭 이벤트를 간결하게 처리합니다.
    * `SharedPreferences`보다 타입-안정성 및 비동기 처리가 뛰어난 **`Jetpack DataStore`** 를 사용하여 펫의 상태를 기기에 저장하는 방법을 익힙니다.
(특히, 위젯과 메인 앱이 동일한 `DataStore` 인스턴스를 공유하도록 `GlanceStateDefinition`을 구현합니다.)
3.  **'살아있는 펫'을 위한 백그라운드 로직 구현 (핵심 도전)**
    * 안드로이드의 배터리 정책(App Standby Buckets)을 준수하면서 '시간의 흐름'을 시뮬레이션하기 위해 **`WorkManager`** 를 학습하고 적용합니다.
    * `WorkManager`가 비정기적으로 실행되더라도 , 마지막 업데이트 시간과의 차이를 계산하는 '타임스탬프 기반 로직'을 구현하여 펫의 상태가 정확하게 반영되도록 설계합니다.
4. **`Jetpack Compose`를 활용한 메인 앱 UI/UX 구현 (추가 목표)**
    * 단순한 `Activity`가 아닌, `Jetpack Compose`를 사용하여 선언형으로 '펫의 방' UI를 구현합니다.
    * `DataStore`의 데이터를 `collectAsState`로 실시간 관찰하여, 펫의 상태와 '꾸미기 포인트'에 따라 UI(가구 배치)가 동적으로 변경되도록 구현합니다.
    * `TextField`를 사용하여 사용자로부터 '펫 이름'과 '주인님 이름'을 입력받고, 이를 `DataStore`에 저장하여 위젯의 랜덤 대사에 반영합니다.
5.  **프리코스 경험의 적용**
    * **(Kotlin)** 프리코스에서 배운 Kotlin의 문법과 특성(object, Enum, when)을 안드로이드 프로젝트에 적용합니다.
    * **(TDD/Test)** 안드로이드 환경에 의존하지 않는 PetStateCalculator (순수 Kotlin) 객체를 분리하여 로직 테스트가 용이하도록 설계합니다.
    * **(Git)** 프리코스에서 연습한 의미 있는 단위의 커밋 메시지 규칙을 준수합니다.


## ✨ 주요 기능 (구현 완료)

* **[Phase 1] 핵심 로직 및 상태 저장 구현**
    * [X] 펫의 상태(PetState Enum) 및 스탯을 `DataStore`로 관리.
    * [X] 펫 이름, 사용자 이름을 `DataStore`에 저장.
    * [X] `PetStateCalculator` 객체를 구현: `hatchPet`(랜덤 부화), `feedPet`/`playWithPet`(즐거움 관리), `bringPetBack`(복귀) 등 핵심 로직 구현.
    * [x] `GlanceStateDefinition`을 구현하여 위젯과 메인 앱이 동일한 `DataStore` 인스턴스를 공유하도록 아키텍처 통일.

* **[Phase 2] 수동적 업데이트 (시간 경과) 구현 (핵심)**
    * [x] `WorkManager`를 사용하여 주기적인(1시간) 백그라운드 작업 스케줄링.
    * [x] '타임 스탬프 기반 로직' 구현: `applyPassiveUpdates`가 경과된 시간만큼 스탯(ex. 배고픔, 즐거움, 행복도, 불행)을 계산해서 `DataStore`에 저장.
    * [x] '가출 로직' 구현: 펫 방치 시 '불행' 수치가 누적되며, 일정 수치 도달 시 'WARNING' 및 'RUNAWAY' 상태로 자동 전이.

* **[Phase 3] 위젯 표시 (Read-Only)**
    * [x] `Jetpack Glance`를 사용해 홈 스크린에 위젯 추가 기능 구현.
    * [x] `DataStore`의 `PetState`를 `currentState()`로 실시간 관찰.
    * [x] `PetVisualMapper`를 통해 `PetState`에 맞는 이미지를 위젯 UI에 표시.
    * [x] `PetDialogueMapper`를 통해 `PetState` 및 스탯, 이름에 맞는 동적 랜덤 대사(ex. 뽀짝이 심심해!)를 위젯 UI에 표시.

* **[Phase 4] 위젯 상호작용 (Read-Write)**
    * [x] `petState`에 따라 '밥주기', '놀아주기' 버튼을 동적으로 표시/숨김.
    * [x] `pet_state`가 'Egg'일 때: `actionRunCallback<HatchCallback>`을 연결하여 `hatchPet` 로직 실행.
    * [x] 'Idle'/'Warning'/'NeedsLove' 상태일 때: '밥주기', '놀아주기' 로직 실행.
    * [x] `Callback` 실행 시 '일일 애정도'를 체크하여 하루에 한 번만 애정도 카운트 +1.
    * [x] 'NeedsLove'/'RunAway' 상태일 때: 위젯 클릭시 메인 앱을 실행하도록 구현.
    * [x] 모든 `Callback` 실행 후 `PetWidget().update()`를 호출하여 UI 즉시 새로고침.
 
* **[Phase 5] 메인 앱 연동 및 확장 기능 (추가)**
    * [x] `Jetpack Compose`로 `MainActivity` UI를 '펫의 방'으로 구현.
    * [x] '사랑 주기' 버튼 클릭 시 '꾸미기 포인트'를 적립, 포인트에 따라 방에 가구(Image)가 자동으로 '언락'되는 기능 구현.
    * [x] `TextField`를 사용하여 '펫 이름'과 '사용자 이름'을 입력받고 `DataStore`에 저장.
    * [x] 'RunAway' 상태에서 앱 방문 시, '[광고 시청] 다시 데려오기' 버튼을 표시하고, 클릭 시, `bringPetBack` 로직을 실행.


## 🗓️ 2주간의 개발 계획

### 1주차 (11.05 ~ 11.10): 핵심 로직 및 백그라운드 엔진 마련
* [X] (학습) '아이스크림 내기' 튜토리얼을 통해 Android Activity 기본 구조 선행 학습
* [X] (학습) `Jetpack Glance`, `DataStore`, `WorkManager` 공식 가이드 학습
* [X] [Phase 1] 프로젝트 생성(`WidgetBuddy`) 및 Git 초기 설정
* [x] [Phase 1] `DataStore` 설정 및 `Enum`(PetState, PetType) 정의
* [x] [Phase 1] `PetStateCalculator` 핵심 로직 구현 (부화, 스탯)
* [x] [Phase 2] `WorkManager` 스케줄링 및 '타임스탬프' 기반 로직 구현
* [x] 개발 일지 작성

### 2주차 (11.11 ~ 11.17): 위젯 UI, 상호작용, 심화 기능 완성
* [x] [Phase 3] `Jetpack Glance`로 기본 위젯 UI 구현 및 `Mapper`로 로직 구현
* [x] [Phase 4] `actionRunCallback`(부화, 밥주기, 놀아주기) 구현
* [x] [Phase 3, 4] '애정도(하트)' 및 '이름/대사' 시스템 구현
* [x] [Phase 5] `Jetpack Compose`로 메인 앱 UI('펫의 방', '이름짓기') 구현
* [x] [Phase 2, 5] 'RunAway' 및 '다시 데려오기(광고 시뮬)' 기능 구현
* [ ] Google AdMob SDK 연동 및 '보상형 광고' 실제 로직 구현.

### 3주차 (11.18 ~ 11.24): 폴리싱(Polishing) 및 배포 (추가)
* [ ] 픽셀 아트(도트) 이미지 제작 (펫 2종, 가구, 방 배경, 아이콘, 가출 쪽지 등)
* [ ] `PetVisualMapper` 및 `MainActivity` UI 에 실제 이미지 리소스 적용
* [ ] `Jetpack Compose`를 활용한 메인 앱 UI/UX 다듬기 (폰트, 색상, 애니메이션 시도)
* [ ] 테스트용 코드(15분 주기, REPLACE 정책 등) 실제 서비스용(1시간 주기, KEEP 정책)으로 되돌리기
* [ ] 버그 테스트 및 코드 리팩토링
* [ ] '릴리즈 빌드'용 `.aab` 파일 생성 및 Google Play Console 등록 준비
* [ ] Google Play 스토어 앱 제출
* [ ] `README` 최종 정리 및 미션 제출


## 🛠️ 사용 기술
* **Language:** Kotlin
* **IDE:** Android Studio
* **Core:** **`Jetpack Glance`** (for UI & Interaction)
* **Background:** **`WorkManager`** (for Passive Game Loop)
* **Data:** **`Jetpack DataStore`** (for State Persistence)
* **Version Control:** Git, GitHub


## 📚 학습 및 개발 기록
이 프로젝트를 진행하며 학습한 Jetpack Glance, WorkManager 등의 핵심 기술과 개발 과정에서 겪은 문제 해결 기록은 **[여기 (docs/)](docs/)** 에서 자세히 보실 수 있습니다.


## 💬 커밋 컨벤션
* `feat`: 새로운 기능 추가
* `fix`: 버그 수정
* `docs`: 문서 수정 (README 등)
* `refactor`: 코드 리팩토링
* `test`: 테스트 코드 추가/수정
* `chore`: 빌드 설정, 기타 잡무
