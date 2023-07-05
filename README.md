# 3구 멀티탭 README

<p style="text-align: center;">
    <img style="border-radius: 8px;" src="./screenshots/power-strip.png" width=200>
</p>

💡 몰입캠프 1주차 과제: 이혜민, 허도영

<img src="https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=android&logoColor=white"> <img src="https://img.shields.io/badge/Android%20Studio-3DDC84?style=flat-square&logo=Android%20Studio&logoColor=white"> <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=Kotlin&logoColor=white">

## Main

앱을 처음 실행하면, 3개의 `BottomNavigator`를 가지는 MainActivity가 실행됩니다. 3개의 탭에 따라 3개의 Fragment가 존재합니다.

|Contacts|Photos|Google Map|
|--|--|--|
|<img width=200 height=400 src="./screenshots/Untitled%200.png">|<img width=200 height=400 src="./screenshots/Untitled%209.png">|<img width=200 height=400 src="./screenshots/Untitled%2011.png">|

## Tab1 : 연락처

> 연락처 `READ`, `CREATE`, `DELETE`가 가능하고, 상세 연락처 페이지로부터 전화 및 문자 전송이 가능합니다. 로컬 연락처와 연동되어 있어서, 앱에서 추가 / 삭제한 연락처는 로컬 연락처에도 적용됩니다.
> 
- ### 연락처 조회
    <img width=240 src="./screenshots/Untitled%200.png">
    
    - 연락처를 디바이스 로컬 정보로부터 가져오기 위하여
        - `READ_CONTACTS` 권한을 요청
        - `contentResolver`의 query를 이용하여 `ContactsContract.CommonDataKinds.Phone.*CONTENT_URI`* 정보를 가져옴
    - `RecyclerView`를 이용하여 연락처 정보를 리스트로 보여줌
        - 각 item을 클릭하면 해당되는 연락처의 정보를 intent로 넘겨주면서, 상세 연락처 페이지가 보여짐
    - Add Floating action button을 클릭하여 연락처 추가 화면으로 넘어갈 수 있음
- ### 연락처 추가

    <img width=240 src="./screenshots/Untitled%201.png"> <img width=240 src="./screenshots/Untitled%202.png"> <img width=240 src="./screenshots/Untitled%203.png">
    
    name, phone number, email 세가지 정보를 `EditText`를 통해 받아오며, Add Floating Action Button을 통해 add action을 confirm할 수 있음
    

- ### 연락처 상세 페이지
    
    <img width=240 src="./screenshots/Untitled%204.png">

    
    - 전화걸기
        
        <img width=240 src="./screenshots/Untitled%205.png">

        - 전화 버튼을 누르면 해당 번호로 전화앱이 연결됨
    - 문자 보내기
        - 문자 버튼을 누르면 해당 번호로 문자앱이 연결됨
            
        <img width=240 src="./screenshots/Untitled%206.png"> <img width=240 src="./screenshots/Untitled%207.png"> <img width=240 src="./screenshots/Untitled%208.png">

            
    - 연락처 삭제하기
        - 우상단의 휴지통 버튼을 눌러서 해당되는 연락처를 삭제할 수 있음

## Tab2 : 갤러리

> 로컬의 이미지 폴더 내부에 있는 사진들을 로드하여, 3열의 스크롤 형태로 사진들을 전부 보여줍이다. RecyclerView를 이용하였습니다.
> 
- ### 사진 콜렉터

<img width=240 src="./screenshots/Untitled%209.png"> <img width=240 src="./screenshots/Untitled%2010.png">

## Tab3 : 실시간 환율 확인

> krw, jpy, usd, eur, cny 5개의 화폐에 대해 오늘 환율 정보를 가져와 계산할 수 있습니다.

<img width=240 src="./screenshots/Untitled%2011.png"> <img width=240 src="./screenshots/Untitled%2012.png">

- ### 환율 정보 받아오기(API 처리)
    - android에서 통신용 라이브러리로 유명하며 third party library인 retrofit을 이용
- ### 사용자 input 받아오기
    - 화폐 코드는 `spinner`로 받아옴
    - 변환할 amount는 `EditText`를 통해 받아옴

## 최적화

### Coroutines

```
Warning: skipped 100 frames! the application may be doing too much work on its main thread.
```

Main Thread는 기본 프로세스 하나에 지정된 하나의 쓰레드입니다. 만약에 onCreate()에서 무한 루프를 돈다면, 다른 것들을 거의 동작을 못하게 됩니다. 이렇게 뭔가 헤비한 작업이 돌아간다면 (리스트뷰를 매우 많이 화면에 표시하거나), 해당 기능을 별도의 쓰레드로 실행하면 메인 쓰레드는 기본적인 사용자의 터치, 클릭, 스와이프 등의 사용자 interaction에 빠르게 반응할 수 있습니다.

그래서, 서버 통신, 화면에 다수의 콘텐츠를 뿌려주기, 그리기 등은 Runnable이나 AsynTask와 같은 별도의 쓰레드로 동작하게 만드는 것입니다. 따라서 헤비한 로직이 있다면 Runnable, Async Task에서 동작하도록 구조를 바꾸면, Main Thread는 사용자 Interaction에만 집중할 수 있습니다.

그중에서도 코틀린 코루틴을 적용하면 장시간 작업(Long-running tasks)으로 인한 메인 스레드(Main Thread) 블로킹 현상 줄일 수 있으며, 비동기 작업 중 예외 발생에 따른 메모리 누수를 방지할 수 있습니다.

### Singleton

API 접근용 Retrofit 인스턴스를 싱글톤으로 처리하여 과도한 GC를 방지합니다.
