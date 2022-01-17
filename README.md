 ⁃ 목적 : 
 1. 데이터를 수집하고 조회한다.
 2. 특정 조건(시나리오)의 데이터가 발견되면 고객번호를 저장한다.
  3. 시나리오 등록 화면 구현한다 

 ⁃ 데이터 예시:
JSON {
 time=‘yyyyMMddss’,
 menuId=‘이체, 대출, 로그인’,
 custId=‘00001’,
 amount=‘10000’,
 vpnUseYn=‘Y’,
 uuid=‘abcd’ //단말기 고유ID
}
 ⁃ 화면 예시 : 
 1. 수집 데이터 조회
 2. 탐지 데이터 조회
 3. 시나리오 관리

 ⁃ 시나리오 예시 :
 1. 5분이내에 동일한 단말(uuid)로 로그인 한 다른 고객이 2명이상이고, 로그인후 30분내에 대출 신청시
- 00:01 1번 고객 abcd단말 로그인
- 00:02 2번 고객 abcd단말 로그인
- 00:15 2번 고객 abcd단말 대출 -> 탐지
 2. vpn을 사용한 고객이 로그인 한 후 10분이내 10000원이상 이체 시도시
- 00:01 3번 고객 vpn사용 로그인
- 00:09 3번 고객 이체 ->탐지

권장사항:
 1. Docker 컨테이너화
 2. 기술스택
JSON -> 
               
               
               수집AP[kafka, Spring] -> Elastic search -> 화면[Vue.js]


                탐지AP[Spring, Redis] -> MySQL -> 화면[Vue.js]
                                                  
기타:
 1. 가능한 단계까지 구현(e.g. 시나리오 등록화면 구현이 불가능하면 소스로 처리)
 2. 기술스택은 편한 것으로 변경 가능
 3. 데이터 전송은 postman 등의 툴 사용하거나 구현


상태 테이블
[ custId | login | vpnUseYn | uuid | time]

이체 테이블
[custId | amount | time ]

대출 테이블
[custId | amount | time]


