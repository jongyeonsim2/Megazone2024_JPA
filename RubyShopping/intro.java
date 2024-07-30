import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.rubypaper.shopping.biz.domain.Order;

/*
 * 
 * 1. 애플리케이션의 실행 흐름
 *    - server 의 context.xml 
 *      배포된 애플리케이션의 WEB-INF/web.xml
 *      
 *    - 애플리케이션의 WEB-INF/web.xml
 *      Spring 컨테이너 설정 파일
 *        classpath:spring/business-layer.xml
 *      Spring MVC에서 View 정보를 완성하는 설정 파일
 *        /WEB-INF/spring/presentation-layer.xml
 * 
 *    - spring/business-layer.xml
 *        JPA, EntityManagerFactory, Transaction 등
 *        
 *    - presentation-layer.xml
 *        클라이언트의 요청에 대한 응답 페이지 정보를 완성.
 *        viewResolver 의 prefix, suffix 설정 정보
 *        
 *    상기의 설정 정보를 바탕으로 웹 애플리케이션 서비스 준비가 됨.
 *    
 * 2. 스프링의 요청 처리 흐름
 *    Client(웹 브라우저) request -> Dispatcher Servlet -> Controller  
 *    -> ModelandView 반환 -> ViewResolver -> response 정보 완성 -> Client 응답.
 *    
 *    Model : Controller 에서 처리한 데이터(JPA를 통한)를 저장.
 *    View : 클라이언트에게 보여 줄 페이지의 경로 및 이름을 저장.
 *    
 * 3. 회원 가입의 처리 순서
 *    - 클라이언트의 request( 서버로 요청 )
 *      http://localhost:8080/customer/new -> insertCustomer.jsp
 *    - 요청에 대한 처리를 위해서 메소드를 찾아야 함.
 *      <form action="/customer/new" method="post"> 
 *      
 *      CustomerController 에서 
 *      @PostMapping("/customer/new") 로 설정된 insertCustomer() 호출
 *    - 서비스 및 repository( JPA, H2DB )
 *    - request 처리에 대한 response 처리
 *      "redirect:/getCustomerList";
 *      
 *      response에 대한 처리 방법 : redirect(클라이언트가 요청), forward(서버에서 자체적으로)
 *    
 *      클라이언트가 /getCustomerList 를 다시 요청하는 것임.
 *      => controller 의 요청 매핑중에서 "/getCustomerList" 를 매핑.
 *      => @GetMapping("/getCustomerList") 로 설정된 메소드를 호출.
 *      => getCustomerList() 메소드가 호출됨.
 *      => DB에서 조회된 사용자 정보 리스트를 Model(key, value) 에 저장.
 *      => View 정보 생성
 *         ViewResolver 가 prefix, suffix 정보를 합쳐서 view 정보를 완성.
 *         prefix + "customer/getCustomerList" + suffix
 *         /WEB-INF/jsp/ + "customer/getCustomerList" + .jsp
 *      => Model 에 저장된 사용자 리스트 정보를 getCustomerList.jsp 에
 *         display 해야 함.( model 과 view )
 *      => response page 가 완성이 되어 클라이언트에게 전송.
 *       
 * 4. Servlet
 *    - 서버 쪽에서 실행되면서 클라이언트의 요청에 따라 동적으로 서비스를 제공하는
 *      자바 클래스.
 *    - 톰캣과 같은 JSP/Servlet 컨테이너에서 실행됨.
 *    - Servlet 의 단점.
 *      클라이언트에게 html 로 만들어서 전송을 해야 함.
 *      서블릿은 java + html(javascript, css) 로 구성이 됨.
 *      => 개발자와 디자이너의 협업이 힘들어짐. => JSP   
 *      
 *      서블릿이 java 와  화면(JSP)으로 분리됨.
 *      내부적으로는 JSP는 servlet 로 컴파일되고, tomcat 에서 실행됨.
 *    - 생명주기(스레드, 톰캣)
 *      초기화 : init()
 *      작업수행 : doGet() get 방식의 요청에 대한 처리, doPost() post 방식의 요청에 대한 처리
 *      종료 : destroy()
 *      
 *      서비스를 구현 => 생명주시 메소드 구현
 *         
 * 5. MVC(model 2) 구성 요소와 기능
 *    - Controller
 *      . 서블릿이 컨트롤러의 역할을 함.
 *      . 클라이언트의 요청을 분석.
 *      . 요청에 대한 필요한 모델을 호출.
 *      . Model 에서 처리한 결과를 보여주기 위해서 JSP 를 호출.
 *      
 *    - Model
 *      . 데이터베이스 연동과 같은 비즈니스 로직을 처리.
 *      . 일반적으로 DAO.
 *    - View
 *      . JSP가 담당.
 *      . Model(Kye, value) 에서 처리한 결과를 화면에 표시.
 *  
 * 6. Entity 의 연관 관계
 *    Customer
 *    Order
 *    Product
 *    Item 
 *    
 *    - Customer <-> Order
 *    
 *    - Order <-> Item <-> Product
 *      Item 이 없으면, Order와 Product 는 다대다 관계.
 *      Item 이 있으므로, 다:1 또는 1:다 관계임. => 비식별 관계 => 유지보수(확장성) 좋아서임.
 *      
 *    - entity의 다대다 연관관계 매핑
 *      Order <-> Item <-> Product
 *      
 *      a. entity 두 개로 관계 설정하는 방법.
 *      b. 식별관계( 연관관계 클래스 존재 )
 *         Order 와 Product의 PK를 FK로 해서 복합키로 사용.
 *      c. 비식별관계( 연관관계 클래스 존재 )
 *         Order 와 Product의 PK를 FK로 하고, 별도의  식별자를 사용.
 *           
 *    - 가장 중요한 비즈니스 로직
 *      상품 주문, 상품 취소
 *      
 *      a. 상품 주문
 *         주문 화면 생성
 *            - header.jsp, /order/new 링크를 통해서 서버로 요청
 *            - OrderController.insertOrder(Model model)
 *              회원 리스트(customerList)와 상품 리스트(productList)를 조회해서 Model 에 저장.
 *              order/insertOrder 을 반환. => view 화면 정보 생성.
 *               => /WEB-INF/jsp/order/insertOrder.jsp
 *               
 *         주문 화면에서 주문 정보 입력
 *             - insertOrder.jsp
 *               주문 정보 입력 후 "상품 주문" 버튼 클릭 => 주문을 서버로 요청( request 송신 )
 *               
 *               <form action="/order/new" method="post">
 *               
 *             - 서버에서 request 를 수신.
 *               /order/new 와 매핑되는 컨트로러의 메소드를 탐색.(스프링)
 *      		 OrderController.insertOrder(Long customerId, Long productId, int count)
 *      
 *             - 서비스 클래스 호출
 *               orderService.insertOrder(customerId, productId, count)
 *               주문 처리를 위한 1, 2, 3 번에 대한 처리(orderService 참조.)
 *               
 *               => 서버 차원에서 요청된 주문이 완료가 됨. => client 에게 response 가 필요함.
 *               
 *             - response( View 생성해서 전달 )
 *               "redirect:/getOrderList"
 *               
 *               클라이언트(브라우저)가
 *               OrderController.getOrderList() 을 호출함.
 *               
 *               주문 목록 조회. orderService.getOrderList(order);
 *               주문 목록 리스트를 Model(orderList) 에 저장.
 *               
 *             - response view 를 생성.( order/getOrderList )
 *               /WEB-INF/jsp/order/getOrderList.jsp
 *                Model(orderList)를 jsp에 출력. => response view 완성.
 *                
 *             - 클라이언트에게 response view 를 전송.
 *      
 *      
 *      b. 상품 취소
 *         - 클라이언트
 *           상품 주문 목록 화면에서 주문 취소.
 *           getOrderList.jsp
 *           <a href="/order/${order.id}/orderCancel">주문취소</a>
 *           주문 취소정보(request)를 송신.
 *         
 *           클라이언트에서 주문 취소 요청이 완료.
 *         
 *         - 서버
 *           OrderController.orderCancel() 호출
 *           
 *           취소할 주문 ID를 매개변수로 서비스에 전달.
 *           orderService.orderCancel(orderId);
 *           
 *           서버 차원에서 주문 취소는 완료가 됨.
 *         
 *           response view 를 생성.( order/getOrderList )
 *           /WEB-INF/jsp/order/getOrderList.jsp
 *           Model(orderList)를 jsp에 출력. => response view 완성.
 *                
 *         - 클라이언트에게 response view 를 전송.
 */ 
 