🌳 본 프로젝트는 [인프런 워밍업 클럽 BE 과정](https://www.inflearn.com/course/inflearn-warmup-club-study-0)에서
진행한 [자바와 스프링 부트로 생애 최초 서버 만들기, 누구나 쉽게 개발부터 배포까지!](https://www.inflearn.com/course/%EC%9E%90%EB%B0%94-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EC%84%9C%EB%B2%84%EA%B0%9C%EB%B0%9C-%EC%98%AC%EC%9D%B8%EC%9B%90/dashboard)
강의의 미니 프로젝트입니다.

# 회사 관리 시스템(Commute)

- ### 1단계 미션

    - [x] 팀 등록 기능
        - 예외처리
            - 같은 이름의 팀을 등록할때에는 에러로 처리
        - Json형식
      > - { "teamName" : "팀이름" }
    - [x] 팀 조회 기능
        - get조회
      > - {base주소}/team
    - [x] 팀장 등록 기능.
    - Json형식 , Put메서드
    ```
        {
         "teamName":"디자인",
         "leaderCode":2
         }

    ```
    - [x] 직원 조회 기능
        - get조회
        ```
            [
                {
                "name" : "직원이름",
                "teamName" : "소속 팀 이름",
                "role" : "MANAGER" or "MEMBER", // 팀의 매니저인지 멤버인지
                "birthday" : "1989-0101",
                "workStarDate" : "2024-01-01"
                }, ...
            ]
      ```
- ### 2단계 미션
    - [x] 출근기능
    - [x] 퇴근기능
      - 출 퇴근 기능
      - 아이디를 파라미터로 받아서 직원 정보를 조회하고 출 퇴근 기록을 저장
      - 데이터를 조회해서 당일 출퇴근 기록이없는경우 새로 출퇴근기록을 저장
      - 데이터를 조회해서 당일 출근기록은 있지만 퇴근이 없는경우 퇴근시간을 업데이트
      - 데이터를 조회해서 당일 출퇴근 기록이 전부 있는경우 새로 출근 기록을 저장.
      #### 예외처리
      -[ ] 등록되지않은 직원이출근
        - Jpa로 조회시 Optional에서 IllegalArgumentException처리
      -[ ] 출근한 직원이 다시 출근
        - 출퇴근 컨트롤러가 하나로 되어있어 출근한직원이 다시 요청하는경우 퇴근으로 처리
      -[ ] 퇴근하려는 직원이 출근하지 않았던 경우
        - 출퇴근 컨트롤러가 하나로 되어있어 처음 찍는경우 무조건 출근처리.
      -[ ] 그 날, 출근했다가 퇴근한 직원이 다시 출근하는경우 
        - 출근했다가 퇴근하고 다시출근하는경우 출근기록을 추가로 생성 .
    - [x] 특정 직원의 날짜별 근무시간을 조회하는기능
        - 특정직원 `id` 와 `2024-01` 과 같이 연/월을 받으면, 날ㅉ별 근무 시간과 총 합을 반환해야한다.
            - 근무시간은 분단위로 계산된다
          ```
          {
            "detail":[
                {
                    "date":"2024-01-01",
                    "workingMinutes":480,  
                },
                {
                    "date":"2024-01-02",
                    "workingMinutes":490,  
                },
                ...// 2024년 1월 31일까지 존재할 수 있다.
            ]
            "sum":10560         
          }  
          ```
        
    - #### 예외처리
       - 요청시 직원의 아이디와 연월정보는 null,빈값,공백이 아니어야한다.
       - 존재하지않는 직원의 아이디를 조회하는경우 예외처리

- ### 3단계 미션

    - [x] 연차 등록 기능
    - 직원id,연차시작일 ,연차종료일을 받아서 연차를 등록
        - 예외처리
            - [x] 부서별로 연차 사용일을 기준으로 며칠전에 연차등록 부서의 연차 신청전 사전보고일보다 늦게 신청하는경우 에러처리
            - [x] 연차신청기간이 직원의 연차보유일보다 큰경우 예외처리
            - [ ] 연차가 신청되어있는기간에 중복으로 신청시 에러처리
            - [ ] 요청 객체에서 연차종료일이 연차시작일보다 빠른경우 예외처리

    - ```
       {
             "employeeId":1,
             "startDate":"2024-03-15",
             "endDate":"2024-04-02"
       }
      ```

    - [x] 연차 조회 기능
    - 직원의 아이디를 파라미터로 조회
        - 결과
      ```
          {
              "annualLeave": 11
          }
        ```
    - [x] 직원의 입사일에따라 연차 세팅
        - 연차에대한 규정을 조회해서 직원의 입사연도에따라 연차를 세팅
        - 당해 입사직원 11일 그외 15일

- ### 4 단계 미션
    1. 초과근무계산
       - 모든 직원은 하루 근무 8시간을 기준으로 근로 계약을 맺고 있습니다.
       - 근무 시간은 주말과 법정 공휴일은 제외하고 계산되어야 합니다. 
       - ex 
         - 2024 1월 전체근무일은 22일로 , 토/일 8일과 1월1일 공휴일 1일이 빠진 수치입니다.
         - (31일 - 8일 - 1일 = 22일) 22일 * 24시간 176시간이 기준 근무시간이 됩니다.
       - 초과 근무 계산 API는 2024-01과 같이 연/월을 받아 모든 직원의 초과 근무시간을 분단위로 계산해주어야 합니다.
       - ex
         - ```
           [
              {
                "id":1,
                "name": "김 *수",
                "overtimeMinutes": 120 // 2024년 1월에 2시간을 초과 근무한 직원 
              },
              {
                "id":2,
                "name": "이*희",
                "overTimeMinutes":0// 2024년 1월에는 초과 근무를 하지 않은 직원
              }
           ]
           ```