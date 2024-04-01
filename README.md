# 2024_SGP_Project
2024학년도 1학기 스마트폰 게임 프로그래밍 프로젝트

2019180031 이윤석
##### 📄 작업 내용 노션 
<https://lead-guitar-e0c.notion.site/4-1-8ad81c2f27714ded8b15341199859121?pvs=4>
⠀
___
## 프로젝트 기획
### I. 게임 컨셉
- High Concept

  - 미니게임을 통해 재료를 수급하는 캐주얼 아케이드 장르 2D 게임
  - 수집한 재료를 이용해 가게를 운영하는 방치형 2D 게임
    




⠀
- 핵심 메카닉
  - 세 종의 미니게임
    
ㅤㅤㅤㅤ1. 쓰리 매치(Three Match) 게임

ㅤㅤㅤㅤㅤ![image](https://github.com/froggorf/2024_SGP_Project/assets/113008438/afe8a182-92bb-4cc6-abc0-5c0714ba06e3)
ㅤㅤㅤㅤㅤ위메이드 플레이, [애니팡]

ㅤㅤㅤㅤㅤ정해진 칸에 있는 블럭들 중 하나를 다른 블럭과 바꾸어 

ㅤㅤㅤㅤㅤ같은 종류의 블럭끼리 3개 이상 연결하는 게임

⠀
     
ㅤㅤㅤㅤ2. 액션 캐쥬얼(후르츠 닌자) 게임

ㅤㅤㅤㅤㅤ![image](https://github.com/froggorf/2024_SGP_Project/assets/113008438/fa0ceaf7-b3b5-41ce-9f7e-4d66df122aed)
ㅤㅤㅤㅤㅤHalfbrick Studios, [Fruit Ninja]

ㅤㅤㅤㅤㅤ날아오는 오브젝트를 그어 자르는 게임

⠀
     
ㅤㅤㅤㅤ3. 니편내편(청기백기) 게임

ㅤㅤㅤㅤㅤ![image](https://github.com/froggorf/2024_SGP_Project/assets/113008438/4d7993b3-9f27-4fef-86dd-65e989fb3e89)
ㅤㅤㅤㅤㅤ컴투스, [아쿠의 퍼즐 패밀리]

ㅤㅤㅤㅤㅤ현재 캐릭터에 적절한 버튼을 누르는 게임

⠀
         
  - 운영 시뮬레이션 방치형 게임

ㅤㅤㅤㅤㅤ![image](https://github.com/froggorf/2024_SGP_Project/assets/113008438/97c638c6-7a5b-4f22-afb4-ca7e51abf410)
ㅤㅤㅤㅤㅤTREEPLLA, [고양이 스낵바]

ㅤㅤㅤㅤㅤ요리사와 서빙 알바생이 일하는 가게를 지켜보고,

ㅤㅤㅤㅤㅤ본인의 재화를 사용하여 추가 증축하여 가게를 발전시키는 게임
     
⠀


### II. 개발 범위
- 마을 맵
  
  ![image](https://github.com/froggorf/2024_SGP_Project/assets/113008438/60dbb6c3-918b-4ffa-9119-03046d9e283f)
  넷마블, [야채부락리]
  
ㅤ전체적인 맵 배경에서,

ㅤ농장 / 재료 준비소 / 목장 / 가게 이미지를 눌러 입장 가능

ㅤ(마을, 농장, 재료준비소, 목장, 가게 등의 이미지 리소스 필요)

⠀

- 농장
  
  ![image](https://github.com/froggorf/2024_SGP_Project/assets/113008438/0e5793ab-ff38-47c6-b255-e84a3516ffbe)
  위메이드플레이, [니모의 아쿠아 팝]
  
ㅤ10x10의 보드에서 랜덤하게 채소(샐러리, 당근, 양파 등) 종류의 블럭이 랜덤하게 배치

ㅤ블럭이 옮겨졌을 때, 동일한 블럭 3개 이상 이어져 있다면 제거 후(**제거 알고리즘 구현**)

ㅤ본인의 재료 창고에 보관

ㅤ(10x10 보드판, N가지 종류의 채소, 채소 재료 창고 보관함 등의 이미지가 필요)

⠀

- 목장
  
  ![image](https://github.com/froggorf/2024_SGP_Project/assets/113008438/a0d397e2-5a4e-4d5e-a3cc-b864ea446a5c)
  컴투스, [아쿠의 퍼즐 패밀리]

  소, 돼지, 닭 등의 동물에 맞는 사료를 먹이는 게임
  
  중앙에 소, 돼지, 닭 이 랜덤하게 일렬로 서있으며,

  아래 버튼 세 개에 소 사료, 돼지 사료, 닭 모이 가 배치 되어

  동물에게 맞는 적절한 사료를 먹이는 게임

  (동물(소, 돼지, 닭) 이미지 및 스프라이트, 육류 재료 창고 보관함 이미지 등의 리소스가 필요)

⠀

- 재료 준비소
  
  ![image](https://github.com/froggorf/2024_SGP_Project/assets/113008438/6cfb5e13-f67f-4328-a0c0-08037573d51a)
  Halfbrick Studios, [Fruit Ninja]
  
  농장과 목장에서 얻은 채소, 육류 등의 재료를 이용해 재료 준비
  
  양쪽에서 채소, 육류 등이 날아오고 슬라이스(**이미지 슬라이스 로직 구현**)하여,

  음식물 재료 창고에 보관

  (음식물 재료 창고 등의 이미지 리소스가 필요)

⠀

- 가게
  
  ![image](https://github.com/froggorf/2024_SGP_Project/assets/113008438/a5edf858-1ea3-4d3e-8813-b42a6de278aa)
  TREEPLLA, [고양이 스낵바]
  
  요리사와 서빙 알바생이 손님에게 음식을 대접

  본인이 작업하는 것 없이, 요리사와 서빙 알바생이 직접 음식을 조리 및 서빙 하며
**(요리사 및 서빙 알바생의 간단한 AI 구현)**,

  플레이어는 요리 제작대(최대 조리 수), 요리사, 서빙 알바생, 테이블(최대 손님 수) 등을 
증축 가능

  (요리사(요리, 걷기, 쉬기 등)의 스프라이트,
  서빙 알바생(걷기, 쉬기 등)의 스프라이트,

  손님(걷기, 앉기, 먹기 등)의 스프라이트,
  가게 배경 이미지,
  
  가구(식탁, 테이블, 조리대 등)의 이미지
  등의 리소스 필요)

⠀

### III. 예상 게임 실행 흐름
ㅤㅤ1. 플레이어는 **농장** 혹은 **목장**에서의 미니게임을 통해 1차 재료(채소 및 육류)를 수급

ㅤ     ![image](https://github.com/froggorf/2024_SGP_Project/assets/113008438/0e5793ab-ff38-47c6-b255-e84a3516ffbe)
       위메이드플레이, [니모의 아쿠아 팝]
ㅤㅤ   ![image](https://github.com/froggorf/2024_SGP_Project/assets/113008438/4d7993b3-9f27-4fef-86dd-65e989fb3e89)
ㅤㅤㅤ  컴투스, [아쿠의 퍼즐 패밀리]


      

⠀

ㅤㅤ2. 플레이어는 수급한 1차 재료가 충분하다면 진행 가능한, 

ㅤㅤㅤ **재료 준비소**에서의 미니게임을 통해 2차 재료(손질된 채소 및 육류)를 수급
       ![image](https://github.com/froggorf/2024_SGP_Project/assets/113008438/21a79ce1-364b-4f9d-8ca0-433ba329a4c0)
ㅤㅤㅤ Halfbrick Studios, [Fruit Ninja]


      

⠀

ㅤ3-1. **가게**에서 테이블이 비어있다면 손님이 방문 및 주문


ㅤ3-2. 손님의 주문이 있다면, 요리사는 해당 요리를 2차 재료를 이용해 제작 및

ㅤㅤㅤ 요리가 완료되면 서빙 테이블로 전달

ㅤㅤㅤ (2차 재료가 부족하다면 가만히 휴식)


ㅤ3-3. 서빙 테이블에 해당 음식이 있다면 서빙 알바생이 손님에게 해당 음식을 서빙 및 재화 수급

ㅤ3-4. 플레이어는 수급된 재화를 토대로 가구(식탁, 테이블, 조리대) 등을 증축

ㅤㅤㅤ![image](https://github.com/froggorf/2024_SGP_Project/assets/113008438/0f400e07-fc0f-4fcd-802b-f39c14480958)
      TREEPLLA, [고양이 스낵바]
⠀

ㅤㅤ4. 게임이 종료 후 다시 접속할 경우,

ㅤㅤㅤ 이전 게임을 종료한 시간과 현재 게임에 접속한 시간을 계산하여

ㅤㅤㅤ 보유한 2차 재료의 수에 맞게 **오프라인 보상**을 획득 가능

ㅤㅤㅤ![image](https://github.com/froggorf/2024_SGP_Project/assets/113008438/34a15ab9-8eff-4da7-bc74-222b750192f0)
      투게디게임즈, [버스킹 냥밴드 키우기]


⠀

### IV. 개발 일정
2024.04.04 개발 시작을 기준으로 작성

**1주차(2024.04.04.목 ~ 2024.04.10.수)**
- 리소스 수집 및 개발 관련 데이터 수집

**2주차(2024.04.11.목 ~ 2024.04.17.수)**
- 마을 맵(메인 맵) 개발 진행
- 맵 내 장소들을 선택 시 비어있는 레벨(액티비티)이 실행되도록 구현

**3주차(2024.04.18.목 ~ 2024.04.24.수)**
- 농장 미니게임(쓰리매치) 구현
- 1차 재료에 대한 메인 변수 관리
- 목장 미니게임(니편내편) 구현

**4주차(2024.04.25.목 ~ 2024.05.01.수)**
- 이미지 자르기 로직 탐색 및 구현
- 재료 준비소 미니게임(후르츠닌자) 구현
- 2차 재료 및 재화(골드)에 대한 메인 변수 관리

**5주차(2024.05.02.목 ~ 2024.05.08.수)**
- 3~4주차 미니게임 제작 미달성 계획 구현

**6주차(2024.05.09.목 ~ 2024.05.15.수)**
- 가게 내 테이블 배치 및 증축 기능 구현

**7주차(2024.05.16.목 ~ 2024.05.22.수)**
- 요리사, 서빙 알바생의 AI 구현 및 배치

**8주차(2024.05.23.목 ~ 2024.05.29.수)**
- 6~7주차 가게 제작 미달성 계획 구현
- 오프라인 방치 재화 수급 시스템 구현

**9주차(2024.05.30.목 ~ 2024.06.05.수)**
- 버그 수정 및 릴리즈 제작
