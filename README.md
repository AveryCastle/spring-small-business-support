spring-local-government-support
==============

Requirements:
------------
- Spring Boot
- Java
- Maven

Features:
-----------
- 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API 개발
- 지원하는 지자체 목록 검색 API 개발
- 지원하는 지자체명을 입력 받아 해당 지자체의 지원정보를 출력하는 API 개발
- 지원하는 지자체 정보 수정 기능 API 개발
- 지원한도 컬럼에서 지원금액으로 내림차순 정렬(지원금액이 동일하면 이차보전 평균 비율이 적은
    순서)하여 특정 개수만 출력하는 API 개발 
  - 입력: 출력 개수 K 
  - 출 력: K 개의 지자체명 (e.g. { 강릉시, 강원도, 거제시, 경기도,경상남도 } )
- 이차보전 컬럼에서 보전 비율이 가장 작은 추천 기관명을 출력하는 API 개발


Build Setup
-----------
``` bash
# make sure you have built the project:
mvn clean install

# run service
mvn spring-boot:run

```

Strategy
-----------
- JPA OneToOne
- LocalGovernement: 지원지자(기관) 엔티티
- SupportedItem: 지자체 지원정보 엔티티
