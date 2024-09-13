# 과제

## 긴급 연락망 API

연락처를 저장하고 조회할 수 있는 웹 서버를 구축합니다.

## 필수 구현

- [ ]  연락처 등록 API 구현
    - [ ]  POST /api/employee
    - [ ]  csv 파일로 업로드할 수 있다.
        - 파일 내에 데이터 예시

       ```
       김철수, charles@clovf.com, 01075312468, 2018.03.07
       박영희, matilda@clovf.com, 01087654321, 2021.0428
       홍길동, kildong.hong@clovf.com, 01012345678, 2015.08.15
       ```

    - [ ]  json 파일로 업로드할 수 있다.
        - 파일 내에 데이터 예시

       ```json
       [
           {
               "name": "김클로",
               "email": "clo@clovf.com",
               "tel": "010-1111-2424",
               "joined": "2012-01-05"
           },
           {
               "name": "박마블",
               "email": "md@clovf.com",
               "tel": "010-3535-7979",
               "joined": "2013-07-01"
           },
           {
               "name": "홍커넥",
               "email": "connect@clovf.com",
               "tel": "010-8531-7942",
               "joined": "2019-12-05"
           }
       ]
       ```

    - [ ]  csv 텍스트로 업로드할 수 있다.
    - [ ]  json 텍스트로 업로드할 수 있다.
- [ ]  연락처 목록 조회 API
    - [ ]  GET /api/employee?page={page}&pageSize={pageSize}
    - [ ]  프론트에서 페이징이 구현 가능하도록 구성해야한다.
- [ ]  이름으로 연락처 조회 API
    - [ ]  GET /api/empolyee/{name}
    - 이름은 unique하다라는 전제로 구현한다.

## 추가 구현

- [ ]  CQRS 패턴 형태의 코드 구현
- [ ]  로그 기능 구현
- [ ]  OpenAPI를 이용해 API spec 노출 구현
    - SpringDoc을 통해서 구현
- [ ]  테스트 코드 작성
- [ ]  설계가 변경 될 때, 반영하기 쉬운 코드 형태
    - [ ]  파일의 형식이 추가될 때 쉽게 추가할 수 있는 형태로 구현
    - [ ]  `content-type`이 변경되더라도 추가할 수 있는 형태로 구현
    - [ ]  여러가지 날짜 형식을 추가할 수 있는 형태로 구현
    - [ ]  여러가지 번호 형식을 추가할 수 있는 형태로 구현

## Persistence Layer

- 추가설정이 필요 없도록 `H2` 사용
