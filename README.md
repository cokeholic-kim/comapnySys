🌳 본 프로젝트는 [인프런 워밍업 클럽 BE 과정](https://www.inflearn.com/course/inflearn-warmup-club-study-0)에서 진행한 [자바와 스프링 부트로 생애 최초 서버 만들기, 누구나 쉽게 개발부터 배포까지!](https://www.inflearn.com/course/%EC%9E%90%EB%B0%94-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EC%84%9C%EB%B2%84%EA%B0%9C%EB%B0%9C-%EC%98%AC%EC%9D%B8%EC%9B%90/dashboard) 강의의 미니 프로젝트입니다.

# 회사 관리 시스템(Commute)

- ### 1단계 미션

    - [x] 팀 등록 기능 
      - 예외처리
        - 같은 이름의 팀을 등록할때에는 에러로 처리
      - Json형식
      >- { "teamName" : "팀이름" }
    - [x] 팀 조회 기능
      - get조회
      >- {base주소}/team
    - [x] 팀장 등록 기능.
    - Json형식 , Put메서드
    ```
        {
         "teamName":"디자인",
         "leaderCode":2
         }

    ```

