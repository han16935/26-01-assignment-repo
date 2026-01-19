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

