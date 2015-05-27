#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* web.xml 로딩 ->  Servlet Context 객체 생성 -> web.xml에 작성된 초기화 파라미터들을 객체에 설정 -> 컨텍스트 초기화 또는 종료 이벤트를 listener로 등록된 객체들에게 알려줌 (이 이벤트가 발생했음을 통보 받고싶은 객체는 listner로 미리 등록을 해 두어야 하는데 그 등록은 @WebListener 어노테이션을 이용)  -> DispatcherServelet 초기화 -> DispatcherServlet 에서 init 메서드 실행 -> 리퀘스트 매핑 초기화

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.

* localhost:8080에 접근 -> welcome file 인  index.jsp 파일 호출 -> index.jsp에서 "/list.next" 로 리다이렉트 -> url이 "*.next"인 요청에 대해 처리하는 servlet인 DispatcherServelet에서 service()함수 실행 -> RequestMapping 에 따라 listController 실행 -> listController 에서 QuestionDao 객체를 호출 -> DB에서 퀘스트들을 가져와 jstl view "list.jsp" 에 퀘스트값을 넘기고 해당 뷰를 리턴 -> jstl view를 html화면으로 렌더링 -> 렌더링 된 화면을  http 통신으로 클라이언트에 내려줍니다.

#### 8. ListController와 ShowController가 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* UserService때문에. Default = singleton이므로, UserServices는 하나의 인스턴스를 멀티스레드가 공유한다. 그 결과 다양한 유저가 하나의 상태값을 사용할 수 있으며, 충돌이 발생할 수 있다. Prototype 스코프를 사용하면 GetBean시 마다 인스턴스를 새로 생성해 할당하므로 문제를 막을 수 있다.