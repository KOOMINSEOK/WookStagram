WookStagram: Java Swing 기반 인스타그램 기능 구현

개요

서버나 데이터베이스 없이 Java 파일 입출력(File I/O)만으로 인스타그램의 핵심 소셜 네트워킹 기능을 구현한 데스크톱 애플리케이션입니다.

구현한 기능

1. 계정 관리: 회원가입 및 로그인 기능

<img width="466" height="457" alt="image" src="https://github.com/user-attachments/assets/50f00eb3-9cb4-4a81-ba45-1e842c329c3a" />

2. 메인 피드: 팔로우한 유저들의 게시물을 시간 순으로 정렬하여 표시 

<img width="481" height="475" alt="image" src="https://github.com/user-attachments/assets/fe0fd362-d049-44b2-9075-aca63ddc1163" />

3. 사용자 검색 및 팔로우: 다른 사용자를 검색하고 팔로우/언팔로우 관계 관리 

<img width="475" height="464" alt="image" src="https://github.com/user-attachments/assets/c45e0374-c207-4932-afab-2616e006443e" />

4. 게시물 작성: 다중 사진 업로드 및 텍스트 작성을 통한 게시물 생성 

<img width="529" height="518" alt="image" src="https://github.com/user-attachments/assets/06905059-4b5c-46d6-b9c0-1327e2961a7b" />

5. 프로필 페이지: 사용자 정보, 게시물 썸네일 목록 확인 및 프로필 편집

 <img width="435" height="424" alt="image" src="https://github.com/user-attachments/assets/0e8ee6c5-5724-4a18-98ee-2c55fa4ff271" />
 


트러블 슈팅

유기적인 데이터 구성

문제 상황

서버 및 데이터베이스이 금지된 환경에서, Java 파일 입출력만으로 사용자와 게시물 데이터를 관리하고,
팔로우 관계에 따라 동적으로 변하는 소셜 기능들을 구현해야 했습니다. 
특히, 파일 내용을 저장하고 수정하는데 있어서의 최적화와 여러 기능 간의 무결성을 보장한 유기적인 데이터 연결을 구현하는 것이 중요했습니다.

해결

디렉토리 구조 설계: users/유저ID/Post/PostID.dat 와 같은 계층적 폴더 구조를 설계하여 데이터를 체계적으로 관리했습니다.

커스텀 바이너리 파일 포맷 설계: 게시물 정보를 안정적으로 관리하기 위해, 헤더에 메타 정보(총 사진 개수, 설명 길이 등)를 먼저 기록하고,
그 뒤에 각 데이터의 길이와 실제 값을 순차적으로 저장하는 커스텀 파일 포맷을 설계했습니다.

<img width="764" height="232" alt="image" src="https://github.com/user-attachments/assets/af1a4225-05c2-4273-bcfe-9af8be2ea897" />


I/O 로직 최적화:
데이터 입출력시에는 데이터 추가는 파일 끝에 이어쓰는 방식, 데이터 수정/삭제는 메모리에 접근하는 방식을
사용해서 I/O성능을 최적화하고 무결성을 보장했습니다.

주요 성과 및 배운점

파일 포맷 설계 경험:
저수준(Low-level)의 데이터 구조를 정의하고 구현하면서 데이터를 효율적으로 관리하는 법을 학습했습니다.

DB 역할의 직접 구현:
디렉토리와 파일을 이용해 데이터베이스의 핵심 역할(저장, 조회, 수정)을 직접 구현해보며,
DB 내부 동작 원리와 설계의 중요성에 대해 이해할 수 있었습니다.
