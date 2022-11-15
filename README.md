## ccoli  
Mobile Programming Personal Assignment  
파이어베이스를 이용한 온라인 마켓형 안드로이드 앱 프로젝트입니다.  
파이어베이스 계정관리와 RealtimeDatabase, Storage를 이용해 계정과 상품 리스트를 관리할 수 있도록 구현한 점이 특징입니다.  
-------------------------
### SDK 버전
> compileSdk 31 (Android 12)  
> minSdk 21  
> targetSdk 31  
--------------------------
+ 첫번째 화면  
Relative Layout을 이용해 화면을 구성하였습니다. ImageView, EditText, TextView를 이용해 화면 구성을 적절하게 구현하였습니다. 기능적인 측면으로는 파이어베이스를 이용해 로그인 기능을 구현하였습니다.  
  
+ 두번째 화면  
LinearLayout을 이용해 화면을 구성하였습니다. 이용 약관 동의를 Radio버튼을 이용하였습니다. 비밀번호의 자릿수/특수키/숫자 규칙을 체크하고, 그 이외에 정보들이 빠진 것이 있는지 체크하여 정상적인 회원가입이 될 수 있도록 구현하였습니다. 또한 회원 정보를 Database에 저장하여 회원 정보를 나중에 불러올 수 있도록 구현하였습니다.  
  
+ 세번째 화면  
Constraint Layout을 이용해 화면을 구성하였습니다. RecycleView를 이용해 상품 리스트를 보여주었습니다. 상품 리스트를 Storage에 저장된 사진과 Database에 저장된 상품 정보를 불러와 RecyclerView에 적용해 보여주도록 구현하였습니다.   
(데이터를 불러오는데 다소 시간이 걸릴 수 있습니다. 화면이 보이지 않더라도 잠깐 기다려주세요.)  
  
+ 네번째 화면 (유저관리)  
Database에 저장된 회원 정보를 불러와 보여주도록 구현하였습니다.  