# 2022 - 4-1 창의공학설계 프로젝트
> Kotlin을 이용한 앱 개발 
> 대학교의 시간표를 사용자가 원하는대로 자동으로 짜주는 앱

## 개요
- MySQL에서 학교의 과목들을 받아와서 앱에서 출력해준다.
- 자동으로 짜준 시간표를 RoomDB를 이용하여 자체적으로 앱에 저장되도록 한다.

## 개발기간 및 환경
- 기간 : 2022.03 ~ 2022.06 (3개월)
- OS : Windows10
- 메인 언어 : Kotlin
- 태그 : ```Kotlin```, ```MySQL```

## 개발내용
- AutoTable.kt : 자동으로 짠 시간표를 보여주는 역할
- CalendarActivity.kt : 캘린더 역할을 수행
- DatabaseAdapter.kt : DB에서 받아온 데이터를 View를 통해 사용하기 위한 파일
- Dialog.kt : Dialog 라이브러리를 이용해 다이어로그를 띄우는 역할
- GradeManagement : RoomDB의 과목들을 불러와 학점을 계산해주는 파일
- InnerDabtabase.kt : 자체 내장 DB인 RoomDB를 사용하기 위한 파일
- InnerDBViewModel.kt : InnerDatabase를 사용하기 위한 ViewModel
- ListDialog.kt : 수동시간표의 List를 만들기위한 Dialog
- LoginActivity.kt : 앱을 처음시작할 때 사용자의 정보를 받기 위한 파일
- MainActivity.kt : 메인화면을 담당하며 다른 액티비티로 넘어갈 수 있는 코드가 작성되어 있음
- SettingActivity.kt : 설정창에 관련된 파일
- SubjectList.kt : 수동 시간표에서 과목들을 찾기위한 파일
- TimetableAdd.kt : 수동 시간표에서 선택한 과목을 추가하기 위한 파일
- TimetableGeneration.kt : 자동 시간표를 짜기 위한 파일
- VolleyRequest.kt : HTTP 통신을 위한 파일 HTTP 사이트에서 정보가져오려면 꼭 필요한 통신 라이브러리
-ViewModel.kt : VolleyRequest를 통해 연결된 서버를 이용해 정보를 가져와 각 규격에 맞춰 분류하는 파일
