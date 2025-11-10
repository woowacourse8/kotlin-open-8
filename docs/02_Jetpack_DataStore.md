# 2. Jetpack DataStore

### 1) Jetpack DataStore이란?
앱의 데이터를 저장하는 최신 기술이다. 예전의 `SharedPreferences`보다 훨씬 안전하고, 비동기적으로 데이터를 저장하고 읽을 수 있다. `Preferences DataStore`와 `Proto DataStore` 2가지 종류가 있다.

### 2) 본 프로젝트에 필요한 이유
* [Phase 1] 상태 저장: '용의 레벨', '배고픔', '행복도' 같은 데이터는 사용자가 폰을 껐다 켜도 사라지면 안 된다.
`DataStore`는 이 중요한 상태 값들을 파일에 안전하게 **저장(기억)** 하는 역할을 한다.
* [Phase 2] 타임스탬프: '살아있는 펫'을 구현하려면 '마지막으로 용 상태를 업데이트한 시간(`lastUpdatedTimestamp`)을 꼭 기억해야 한다.
`DataStore`가 이 시간 값을 저장해 둔다. 

### 3) Preferences DataStore
* 용도: `SharedPreferences`와 똑같이 '키-값' 쌍으로 간단한 데이터를 저장한다.
* 특징
  * 스키마(데이터 구조)를 미리 정의할 필요가 없어 사용이 간단하다.
  * 단순한 값을 저장하기에 좋다.
 
### 4) Proto DataStore
* 용도: 데이터 클래스(객체) 자체를 통째로 저장할 때 쓴다.
* 특징
  * `Protocol Buffers`라는 기술로 스키마를 미리 정의해야 해서 설정이 조금 더 복잡하다.
  * `Preferences Datastore`보다 타입에 더욱 안전하다.
