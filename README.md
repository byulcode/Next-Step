# 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 프로젝트를 자신의 계정으로 Fork한다. Github 우측 상단의 Fork 버튼을 클릭하면 자신의 계정으로 Fork된다.
* Fork한 프로젝트를 eclipse 또는 터미널에서 clone 한다.
* Fork한 프로젝트를 eclipse로 import한 후에 Maven 빌드 도구를 활용해 eclipse 프로젝트로 변환한다.(mvn eclipse:clean eclipse:eclipse)
* 빌드가 성공하면 반드시 refresh(fn + f5)를 실행해야 한다.

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다. 
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다. 

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
- `in` 은 `InputStream`, 즉 바이트스트림이므로 `InputStreamReader`를 사용해 문자스트림인 Reader로 변환해주었다. 그리고 스트림을 한줄 단위로 읽기 위해 BufferReader을 생성하고, readline() 메소드를 사용했다.
- 로그 메시지 작성
    - log level = trace → debug → info → warn → error(가장 심각)
    - 일반적으로 개발 서버는 debug, 운영 서버는 info로 설정한다. 그래서 이번 실습의 log레벨은 debug를 사용했다.(설정된 level 이하의 상태는 출력하지 않는다.)
- null 과 “”(공백)의 차이
    - 널(null)은 어떤 값으로도 초기화가 되지 않은 것. 그래서 힙메모리상에 데이터를 만들어내지 않는다.
    - “”(공백)은 하나의 스트링이다. 즉 빈 값을 메모리에 할당한 행동이다.
    - 널은 사용하겠다고 예고만 해놓은 상태, 공백은 이미 사용한 상태라고 이해할 수 있다.
- DataOutputStream
    - `DataOutputStream`은 자바의 기본 데이터 타입별로 출력하는 별도의 메소드들이 있으며, 기본 데이터를 매개 변수로 호출한다.
- Files.readAllBytes(Path)
    - 파일의 경로를 가져와 파일에서 읽은 바이트를 포함하는 바이트 배열을 반환한다.
    - 문자열 형식으로 출력을 얻으려면 바이트 배열을 String 생성자에 전달하면 된다.
      (`new String(byte[])`)
- flush()
    - flush() 메소드는 버퍼에 데이터가 가득 차 있건 아니건, 버퍼에서 강제로 밀어내도록 하는 메소드이다. wirte에 저장된 값을 출력함과 동시에 비워준다.

### 요구사항 2 - get 방식으로 회원가입
- .indexOf(String str) - 특정 문자나 문자열이 앞에서부터 처음 발견되는 인덱스를 반환한다.
- .substring(int startIndex) - startIndex부터 끝까지의 문자열을 반환한다.
- .substring(int startIndex, int endIndex) - startIndex부터 endIndex(불포함)까의 문자열을 반환한다.
- User 객체가 잘 만들어졌는지에 대한 테스트 코드 작성하는 법 알아보기.

### 요구사항 3 - post 방식으로 회원가입
- 로그에 객체 자체를 출력하면 다음과 같이 객체의 속성도 모두 보여준다.
  - `User [userId=byul, password=1234, name=Byulyi, email=byulcode%40gmail.com]`

### 요구사항 4 - redirect 방식으로 이동
* 

### 요구사항 5 - cookie
* 

### 요구사항 6 - stylesheet 적용
* 

### heroku 서버에 배포 후
* 