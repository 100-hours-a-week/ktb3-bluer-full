# Community API

Spring Boot 기반의 게시글/댓글 커뮤니티 백엔드입니다. 회원 가입·로그인부터 게시글/댓글 CRUD, 좋아요, Swagger 기반 문서와 Spring Security 쿠키 인증까지 포함되어 있으며, H2 파일
DB를 사용합니다.

## Tech Stack

- 개발 환경(OS) : MacOS 15.5
- Java 21, Spring Boot 3.4.3
- Spring Web, Validation, Spring Data JPA, Spring Security
- H2 Database (file mode), Lombok
- springdoc-openapi로 자동 문서화

## 주요 기능

- 사용자
    - 회원가입/로그인/중복검사
    - 로그인 시 HttpOnly `AUTH_TOKEN` 쿠키 발급, Spring Security stateless 필터로 인증
    - 프로필 조회/수정, 비밀번호 변경, 회원 탈퇴
- 게시글
    - 무한 스크롤 형태의 목록 조회(cursor 기반) 및 상세 조회
    - 작성/수정/삭제, 좋아요/좋아요 취소
- 댓글
    - 게시글별 댓글 목록 조회, 작성/수정/삭제
- 공통
    - 표준 `ApiResponse` wrapper
    - Swagger UI (`/swagger-ui/index.html`)
    - 예외 처리(`GlobalExceptionHandler`)

## 사전 준비

1. **JDK 21** 이상 설치
2. H2 파일 DB 경로 생성
   ```bash
   mkdir -p /Users/foo/Documents/projects/h2-local-db
   ```
   기본 설정은 `application.yml`의 `jdbc:h2:file:/Users/foo/Documents/projects/h2-local-db/test`를 사용합니다. 다른 위치를 쓰고
   싶다면 `application.yml` 혹은 `.env`의 `H2_DB_PATH`를 수정해야 합니다.

## 로컬 실행

```bash
./gradlew bootRun
```

- 서버: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- H2 Console: `http://localhost:8080/h2-console` (JDBC URL은 `jdbc:h2:file:/.../test`)

## 인증 흐름

1. `POST /users/signin`에 이메일/비밀번호를 보내면 `SignInResponse`와 함께 HttpOnly `AUTH_TOKEN` 쿠키가 발급됩니다. (토큰은 `TokenService`의 인메모리
   저장소에 저장)
2. 브라우저/클라이언트는 후속 요청에 이 쿠키를 자동으로 포함해야 합니다.
    - 브라우저 Fetch/Axios: `credentials: 'include'` 또는 `withCredentials: true`
    - CORS 설정에서 `allowCredentials(true)`와 허용 오리진을 명시해야 쿠키가 전달됩니다.
3. `TokenAuthenticationFilter`가 쿠키에서 토큰을 읽어 `SecurityContext`에 인증 객체를 주입합니다.
4. 로그아웃 혹은 회원 탈퇴 시 토큰 삭제 후 만료 쿠키를 내려 쿠키를 정리하도록 구현하면 됩니다. (`AuthCookieProvider#createExpiredCookie`)


## 주요 패키지 구조

```
com.example.community
├── controller        # REST 컨트롤러
├── service           # 비즈니스 로직, TokenService 포함
├── repository        # JPA 기반 DB 접근
├── domain/entity     # 도메인/엔티티 모델
├── dto               # 요청/응답 DTO
├── security          # Spring Security 설정 및 필터
├── common            # 공통 응답/에러/예외 처리
└── docs              # Swagger 문서 어노테이션
```

## 참고

- `schema.sql`이 애플리케이션 기동 시 테이블을 생성합니다.
- 커스텀 설정이나 빌드 옵션은 `build.gradle`을 참고하세요.
