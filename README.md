# Corca Ads Demo

### 이 프로젝트는 두 가지 주요 구성 요소로 이루어져 있습니다:

- `server/`: Spring Boot 백엔드
- `web/`: Nuxt.js 프론트엔드

</br>

### 필수 요구 사항

- Docker
- Docker Compose

#### 📍 위 두 가지에 대해 설치가 필요한 경우, macOS, Windows, Linux(Ubuntu) 등 운영체제에 맞게 설치해주세요.

</br>

### 실행 방법

1. 레포지토리 클론:

   ```shell
   $ git clone https://github.com/corca-ai/corca-ads-demo.git

   $ cd corca-ads-demo
   ```

2. 데모 프로젝트 빌드 및 실행:

   ```shell
   $ make run-demo
   ```

3. 웹 애플리케이션 접속:

   ```shell
   http://localhost:3000   # 웹 브라우저에서 url 접속
   ```

   ![image](https://github.com/user-attachments/assets/72fbbeb4-15e7-4ab5-830e-325ddea00d02)

4. 실행을 종료하고 Docker 환경을 초기화 하고 싶은 경우:

   ```shell
   $ make clear
   ```
