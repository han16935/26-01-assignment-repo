### 1️⃣ 과제 분석
크게 JWT 기반 로그인과 API 기반 질문 및 답변이 메인, 그리고 이를 기반으로 한 피드백 및 관리 기능으로 보았습니다

### 2️⃣ AI 활용 방법
머릿속에 바로 생각나지 않는 부분은 AI에게 질문하고, 그동안 타 기능을 작성했습니다.

### 3️⃣ 구현하기 어려웠던 1개 이상 기능
코틀린 기반 로그인 등 보안 관련 기능이 기존 Java와 표현이 다른 부분이 종종 있어서 어려웠네요...

## 🚀 실행 방법 (Docker)

---

### 0️⃣ 실행 환경

- **Spring Boot**: 3.5.9
- **Kotlin**: 1.9.25
- **PostgreSQL**: 15.8

---

### 1️⃣ DB 실행

PostgreSQL 및 Redis 컨테이너를 Docker Compose로 실행합니다.

```
docker-compose up -d
```


### 2️⃣ 애플리케이션 컨테이너 실행 (local, dev 가능)
```
docker run -d \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  -e OPENAI_API_KEY=your_openai_api_key_here \
  --name assignment-january \
  han16935/assignment-january
```

