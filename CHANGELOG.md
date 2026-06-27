# 타로다요 (Tarodayo) 작업 이력

AI 타로 리딩 웹 서비스. Spring Boot + Gemini AI 기반.

---

## 프로젝트 기본 정보

| 항목 | 내용 |
|------|------|
| GitHub | https://github.com/rangssu/tarodayo |
| 배포 플랫폼 | Railway (push → 자동 배포) |
| 기술 스택 | Spring Boot 4.1, Java 21, Gradle 8.14, Spring AI + Gemini 2.5 Flash, Thymeleaf, H2 |

---

## 1차 작업 — 초기 UI/UX 개선 (피드백 반영)

### 변경 내용

**모바일 레이아웃 개선**
- 카드 선택 화면에서 모바일 기기의 카드 겹침 문제 보고됨
- `flex-direction: column` 적용으로 모바일에서 1열 정렬 시도 (→ 이후 재수정)

**3단계 진행 표시기 추가**
- 각 HTML 페이지 상단에 `사연 입력 → 카드 선택 → 결과 확인` 스텝 바 삽입
- 완료 단계 `✓` 체크, 현재 단계 보라색 하이라이트

**효과음 및 시각 이펙트 추가**
- Web Audio API 기반 효과음 (외부 파일 없이 브라우저에서 직접 생성)
  - 카드 뒤집기: 소프트 하강 톤 (700Hz → 350Hz)
  - 3장 선택 완료: 상승 3음 코드 (도-미-솔)
  - 결과 페이지 등장: 5음 아르페지오
- 카드 클릭 시 파티클 스파클 이펙트 (✦ ★ ✧ 기호, CSS animation)
- 페이지 전환 페이드인 애니메이션 (`pageIn` keyframes)
- 결과 페이지 카드 순차 슬라이드인 (`cardReveal` keyframes)

---

## 2차 작업 — 전면 UX 재설계 (피드백 반영)

### 변경 내용

**카드 선택 → 결과 화면 분리**
- 기존: 3장 선택 시 700ms 후 자동 폼 제출
- 변경: 3장 선택 후 **"운명을 확인하기 ✨"** 버튼 등장, 사용자가 직접 눌러야 다음 단계로 이동
- 버튼 중복 클릭 방지 (`disabled` 처리) 추가

**카드 레이아웃 전면 교체**
- 기존: `display: flex; flex-wrap: wrap` → 선택 시 카드 위치 이동 문제
- 변경: `display: grid; grid-template-columns: repeat(5, 1fr)` 5열 고정 그리드
- `aspect-ratio: 2/3` 적용으로 화면 크기에 비례한 동일 비율 유지
- hover `translateY` 제거 → 카드 제자리 고정

**과거 / 현재 / 미래 라벨 추가**
- 카드 선택 순서대로 카드 앞면에 라벨 표시 (1번째 = 과거, 2번째 = 현재, 3번째 = 미래)
- 선택 해제 시 라벨 재배정 처리

**반응형 개선**
- `clamp()` 함수로 gap 크기 뷰포트에 비례 조정
- 결과 페이지 모바일에서 카드 1열 정렬

---

## 3차 작업 — 버그 수정 1 (PC/모바일 이슈)

### 보고된 문제

1. PC에서 step-indicator가 좌상단에 세로로 깨져 표시됨
2. 카드 선택 시 선택된 카드가 아래로 이동하여 겹침
3. PC에서 "운명을 확인하기" 버튼이 보이지 않음
4. "운명을 확인하기" 버튼을 눌러도 반응 없음
5. 모바일에서 버튼이 카드에 가려 보임

### 원인 분석

**문제 2~5의 공통 원인: `aspect-ratio` + `position: absolute` 자식 조합의 높이 붕괴**
- `card-slot`에 `aspect-ratio: 2/3` 설정 시 절대 위치 자식만 있으면 일부 브라우저에서 높이가 0으로 붕괴
- 높이가 0이면 그리드 셀 높이도 0 → 카드들이 한 점에 겹쳐 렌더링
- 버튼도 카드에 가려짐 (z-index 문제)

### 수정 내용

- `aspect-ratio` → `padding-bottom: 150%` 트릭으로 교체 (높이 0 붕괴 방지)
- `card-inner`: `position: absolute; inset: 0` 적용
- `confirm-area`에 `position: relative; z-index: 10` 추가 → 버튼이 카드 위에 표시
- `submitForm()` 함수에 선택 수 검증 추가

---

## 4차 작업 — step-indicator PC 표시 문제 재수정

### 보고된 문제

step-indicator가 이전 수정 후에도 PC에서 여전히 좌상단 구석에 세로로 표시됨.

### 원인 분석

**PC 브라우저 CSS 캐시 문제**
- 브라우저가 step-indicator CSS가 존재하지 않던 초기 버전의 `style.css`를 캐시하고 있음
- `display: flex` 자체가 적용 안 된 상태 (`.step-circle`, `.step-label` 스타일 전혀 없음)
- 모바일은 다른 기기라 캐시가 없어 정상 동작

**추가 구조 문제**
- step-indicator가 `.main-container` / `.select-container` 외부에 있어 flex 컨텍스트 영향 불안정

### 수정 내용

- CSS 링크에 `?v=5` 버전 파라미터 추가 → 브라우저 캐시 강제 무효화
  ```html
  <link rel="stylesheet" th:href="@{/css/style.css(v=5)}">
  ```
- `page-wrap` 컴포넌트 추가: `display: flex; flex-direction: column; min-height: 100vh`
- step-indicator를 `page-wrap` 내부 최상단에 배치 → 외부 컨텍스트 영향 제거
- `position: fixed` 제거, `flex-direction: row` 명시적 선언으로 가로 정렬 확실히 보장
- 각 컨테이너 `padding-top` 보정값 제거, `flex: 1`로 변경

---

## 5차 작업 — 카드 3D Flip 및 500 에러 수정

### 보고된 문제

1. 카드 선택 시 선택된 카드가 여전히 아래로 이동
2. 결과 페이지에서 500 에러 발생

### 원인 분석

**카드 이동 원인: `filter` 속성의 3D transform 평탄화**
```css
.card-slot.selected {
    filter: drop-shadow(...);  /* 이 filter가 자식의 preserve-3d를 파괴 */
}
```
CSS `filter` 속성은 자식 요소의 `transform-style: preserve-3d`를 강제로 평탄화(flatten)함.
결과적으로 `perspective` + `rotateY` 3D 계산이 깨져 카드가 이상한 위치에 렌더링됨.

**500 에러 원인**
- `/result` POST 엔드포인트에서 세션 만료 시 `drawnCards == null` → NullPointerException
- Gemini API 호출 실패 시 예외 미처리

### 수정 내용

- `filter: drop-shadow` → `box-shadow`로 교체 (3D transform에 영향 없음)
- `border-radius: 14px` 추가로 box-shadow 모서리 일치
- `TarotController.java` 수정:
  ```java
  // 세션 만료 처리
  if (drawnCards == null || question == null || selectedIndexes == null) {
      return "redirect:/";
  }
  // Gemini API 예외 처리
  try {
      interpretation = geminiService.interpret(...);
  } catch (Exception e) {
      interpretation = "AI 해석 중 오류가 발생했습니다.";
  }
  ```

---

## 6차 작업 — 카드 위치 이탈 근본 원인 해결

### 보고된 문제

수정 후에도 카드 선택 시 여전히 아래로 이탈.

### 원인 분석 (최종)

**`position: relative` 이중 선언으로 인한 높이 2배 초과**

```css
/* 공유 규칙 — 둘 다 absolute */
.card-back, .card-front {
    position: absolute;
    width: 100%;
    height: 100%;
}

/* 각자 규칙에서 relative로 덮어씀 ← 버그 */
.card-back  { position: relative; }
.card-front { position: relative; }
```

`position: relative`(일반 흐름)로 선언된 두 카드가 card-inner 안에 세로로 쌓이면서:
- card-back: height 100% = 1.5W
- card-front: height 100% = 1.5W
- 합계: 3W → card-inner(1.5W) 높이 2배 초과 → 아래로 넘침

### 수정 내용

- `.card-back`, `.card-front` 각자 규칙에서 `position: relative` 제거
- 공유 규칙의 `position: absolute` 유지 → card-inner의 `inset: 0` 공간을 각자 채워 제자리 flip 보장

**textarea resize 비활성화**
- `resize: vertical` → `resize: none` (사용자 임의 크기 변경 방지)

---

## 커밋 히스토리 요약

| 커밋 | 내용 |
|------|------|
| `05852e0` | feat: 모바일 반응형, 3단계 진행 표시기, 효과음/이펙트 추가 |
| `5ae9f24` | feat: 카드 선택 UX 전면 개선 (5열 Grid, 확인 버튼, 과거/현재/미래 라벨) |
| `2f82343` | fix: step-indicator 상단 고정, 카드 높이 붕괴 해결, 확인 버튼 정상화 |
| `aa11c83` | fix: CSS 버전 파라미터로 캐시 무효화, step-indicator 구조 개선 |
| `d6f685e` | fix: 카드 3D flip 복원 (filter→box-shadow), 결과 페이지 500 에러 처리 |
| `0f2ac72` | fix: 카드 위치 이탈 근본 원인 제거, textarea 리사이즈 비활성화 |

---

## 현재 상태 (2026-06-23 기준)

- [x] Spring Boot 4.1 + Gemini AI 풀스택 구현
- [x] Railway 배포 완료 (GitHub push → 자동 배포)
- [x] 3단계 흐름: 사연 입력 → 카드 선택 → 결과 확인
- [x] 5열 CSS Grid 카드 레이아웃 (PC/모바일 공통)
- [x] 카드 제자리 3D Flip (위치 이탈 없음)
- [x] 과거/현재/미래 라벨 (선택 순서 기반)
- [x] "운명을 확인하기" 버튼 정상 동작
- [x] 결과 페이지 500 에러 처리 (세션 만료 + Gemini API 예외)
- [x] 효과음 + 스파클 이펙트
- [x] textarea resize 비활성화
- [x] step-indicator PC/모바일 공통 정상 표시
