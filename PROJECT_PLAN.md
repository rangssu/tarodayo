# 타로다요 (Tarodayo) 프로젝트 기획서

## 프로젝트 개요
AI 타로 리딩 웹 서비스. 사용자가 카드를 선택하면 AI가 과거/현재/미래를 해석해준다.

---

## 기술 스택

| 역할 | 기술 |
|---|---|
| 백엔드 | Spring Boot 4.1.0 |
| AI | Spring AI + Google Gemini 1.5 Flash |
| 프론트 | Thymeleaf + HTML/CSS/JS |
| DB | H2 (인메모리) |
| 빌드 | Gradle |
| Java | 21 |

---

## 핵심 기능

### 카드 뽑기 플로우
```
질문 입력
→ 22장 중 랜덤 5장 뒤집힌 상태로 제시
→ 사용자가 그 중 3장 클릭해서 선택 (클릭하면 바로 뒤집힘)
→ 정방향 / 역방향 랜덤 결정
→ AI가 과거 / 현재 / 미래 해석 출력
```

### 화면 구성
1. **메인 페이지** - 질문 입력창 + 시작 버튼
2. **카드 선택 페이지** - 5장 뒤집힌 카드, 3장 클릭 선택
3. **결과 페이지** - 카드 공개 + AI 해석

### UX 규칙
- 3장 다 선택하면 자동으로 결과 페이지 이동
- 선택한 카드는 테두리로 구분
- 나머지 2장은 흐릿하게 처리

---

## 타로 카드 데이터
- 메이저 아르카나 22장 사용
- 각 카드: 이름(한/영), 정방향 키워드, 역방향 키워드, 이미지

---

## 프로젝트 구조
```
tarodayo/
├── src/main/java/com/taro/tarodayo/
│   ├── controller/       # 웹 요청 처리
│   ├── service/          # 비즈니스 로직, AI 호출
│   ├── domain/           # 타로 카드 엔티티
│   └── repository/       # DB 접근
├── src/main/resources/
│   ├── templates/        # Thymeleaf HTML
│   ├── static/           # CSS, JS, 카드 이미지
│   └── application.properties
└── build.gradle
```

---

## 개발 순서

- [ ] 1. build.gradle Spring AI 의존성 추가 및 Gradle 새로고침
- [ ] 2. 타로 카드 22장 Entity / Repository 구성
- [ ] 3. 카드 데이터 초기화 (DataInitializer)
- [ ] 4. 카드 뽑기 서비스 로직 (랜덤 5장, 정/역방향)
- [ ] 5. Gemini AI 해석 서비스 구현
- [ ] 6. Controller 구현
- [ ] 7. Thymeleaf 메인 페이지 UI
- [ ] 8. 카드 선택 페이지 UI + 뒤집기 애니메이션
- [ ] 9. 결과 페이지 UI

---

## 환경 설정

### application.properties 주요 설정
```properties
# H2 Database
spring.datasource.url=jdbc:h2:mem:tarotdb
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.hibernate.ddl-auto=create-drop

# Google Gemini AI
spring.ai.google.gemini.api-key=${GEMINI_API_KEY}
spring.ai.google.gemini.chat.options.model=gemini-1.5-flash
spring.ai.google.gemini.chat.options.temperature=0.8
```

### Gemini API 키 설정 (IntelliJ)
```
Run → Edit Configurations
→ Environment Variables
→ GEMINI_API_KEY=발급받은키입력
```

---

## 참고 사항
- Gemini API 무료 티어: 분당 15 요청, 하루 1,500 요청
- API 키는 절대 코드에 직접 입력하지 말 것 (환경변수 사용)
- API 키는 aistudio.google.com 에서 발급
