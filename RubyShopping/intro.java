import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.rubypaper.shopping.biz.domain.Order;

/*
 * 
 * 1. ���ø����̼��� ���� �帧
 *    - server �� context.xml 
 *      ������ ���ø����̼��� WEB-INF/web.xml
 *      
 *    - ���ø����̼��� WEB-INF/web.xml
 *      Spring �����̳� ���� ����
 *        classpath:spring/business-layer.xml
 *      Spring MVC���� View ������ �ϼ��ϴ� ���� ����
 *        /WEB-INF/spring/presentation-layer.xml
 * 
 *    - spring/business-layer.xml
 *        JPA, EntityManagerFactory, Transaction ��
 *        
 *    - presentation-layer.xml
 *        Ŭ���̾�Ʈ�� ��û�� ���� ���� ������ ������ �ϼ�.
 *        viewResolver �� prefix, suffix ���� ����
 *        
 *    ����� ���� ������ �������� �� ���ø����̼� ���� �غ� ��.
 *    
 * 2. �������� ��û ó�� �帧
 *    Client(�� ������) request -> Dispatcher Servlet -> Controller  
 *    -> ModelandView ��ȯ -> ViewResolver -> response ���� �ϼ� -> Client ����.
 *    
 *    Model : Controller ���� ó���� ������(JPA�� ����)�� ����.
 *    View : Ŭ���̾�Ʈ���� ���� �� �������� ��� �� �̸��� ����.
 *    
 * 3. ȸ�� ������ ó�� ����
 *    - Ŭ���̾�Ʈ�� request( ������ ��û )
 *      http://localhost:8080/customer/new -> insertCustomer.jsp
 *    - ��û�� ���� ó���� ���ؼ� �޼ҵ带 ã�ƾ� ��.
 *      <form action="/customer/new" method="post"> 
 *      
 *      CustomerController ���� 
 *      @PostMapping("/customer/new") �� ������ insertCustomer() ȣ��
 *    - ���� �� repository( JPA, H2DB )
 *    - request ó���� ���� response ó��
 *      "redirect:/getCustomerList";
 *      
 *      response�� ���� ó�� ��� : redirect(Ŭ���̾�Ʈ�� ��û), forward(�������� ��ü������)
 *    
 *      Ŭ���̾�Ʈ�� /getCustomerList �� �ٽ� ��û�ϴ� ����.
 *      => controller �� ��û �����߿��� "/getCustomerList" �� ����.
 *      => @GetMapping("/getCustomerList") �� ������ �޼ҵ带 ȣ��.
 *      => getCustomerList() �޼ҵ尡 ȣ���.
 *      => DB���� ��ȸ�� ����� ���� ����Ʈ�� Model(key, value) �� ����.
 *      => View ���� ����
 *         ViewResolver �� prefix, suffix ������ ���ļ� view ������ �ϼ�.
 *         prefix + "customer/getCustomerList" + suffix
 *         /WEB-INF/jsp/ + "customer/getCustomerList" + .jsp
 *      => Model �� ����� ����� ����Ʈ ������ getCustomerList.jsp ��
 *         display �ؾ� ��.( model �� view )
 *      => response page �� �ϼ��� �Ǿ� Ŭ���̾�Ʈ���� ����.
 *       
 * 4. Servlet
 *    - ���� �ʿ��� ����Ǹ鼭 Ŭ���̾�Ʈ�� ��û�� ���� �������� ���񽺸� �����ϴ�
 *      �ڹ� Ŭ����.
 *    - ��Ĺ�� ���� JSP/Servlet �����̳ʿ��� �����.
 *    - Servlet �� ����.
 *      Ŭ���̾�Ʈ���� html �� ���� ������ �ؾ� ��.
 *      ������ java + html(javascript, css) �� ������ ��.
 *      => �����ڿ� �����̳��� ������ �������. => JSP   
 *      
 *      ������ java ��  ȭ��(JSP)���� �и���.
 *      ���������δ� JSP�� servlet �� �����ϵǰ�, tomcat ���� �����.
 *    - �����ֱ�(������, ��Ĺ)
 *      �ʱ�ȭ : init()
 *      �۾����� : doGet() get ����� ��û�� ���� ó��, doPost() post ����� ��û�� ���� ó��
 *      ���� : destroy()
 *      
 *      ���񽺸� ���� => �����ֽ� �޼ҵ� ����
 *         
 * 5. MVC(model 2) ���� ��ҿ� ���
 *    - Controller
 *      . ������ ��Ʈ�ѷ��� ������ ��.
 *      . Ŭ���̾�Ʈ�� ��û�� �м�.
 *      . ��û�� ���� �ʿ��� ���� ȣ��.
 *      . Model ���� ó���� ����� �����ֱ� ���ؼ� JSP �� ȣ��.
 *      
 *    - Model
 *      . �����ͺ��̽� ������ ���� ����Ͻ� ������ ó��.
 *      . �Ϲ������� DAO.
 *    - View
 *      . JSP�� ���.
 *      . Model(Kye, value) ���� ó���� ����� ȭ�鿡 ǥ��.
 *  
 * 6. Entity �� ���� ����
 *    Customer
 *    Order
 *    Product
 *    Item 
 *    
 *    - Customer <-> Order
 *    
 *    - Order <-> Item <-> Product
 *      Item �� ������, Order�� Product �� �ٴ�� ����.
 *      Item �� �����Ƿ�, ��:1 �Ǵ� 1:�� ������. => ��ĺ� ���� => ��������(Ȯ�强) ���Ƽ���.
 *      
 *    - entity�� �ٴ�� �������� ����
 *      Order <-> Item <-> Product
 *      
 *      a. entity �� ���� ���� �����ϴ� ���.
 *      b. �ĺ�����( �������� Ŭ���� ���� )
 *         Order �� Product�� PK�� FK�� �ؼ� ����Ű�� ���.
 *      c. ��ĺ�����( �������� Ŭ���� ���� )
 *         Order �� Product�� PK�� FK�� �ϰ�, ������  �ĺ��ڸ� ���.
 *           
 *    - ���� �߿��� ����Ͻ� ����
 *      ��ǰ �ֹ�, ��ǰ ���
 *      
 *      a. ��ǰ �ֹ�
 *         �ֹ� ȭ�� ����
 *            - header.jsp, /order/new ��ũ�� ���ؼ� ������ ��û
 *            - OrderController.insertOrder(Model model)
 *              ȸ�� ����Ʈ(customerList)�� ��ǰ ����Ʈ(productList)�� ��ȸ�ؼ� Model �� ����.
 *              order/insertOrder �� ��ȯ. => view ȭ�� ���� ����.
 *               => /WEB-INF/jsp/order/insertOrder.jsp
 *               
 *         �ֹ� ȭ�鿡�� �ֹ� ���� �Է�
 *             - insertOrder.jsp
 *               �ֹ� ���� �Է� �� "��ǰ �ֹ�" ��ư Ŭ�� => �ֹ��� ������ ��û( request �۽� )
 *               
 *               <form action="/order/new" method="post">
 *               
 *             - �������� request �� ����.
 *               /order/new �� ���εǴ� ��Ʈ�η��� �޼ҵ带 Ž��.(������)
 *      		 OrderController.insertOrder(Long customerId, Long productId, int count)
 *      
 *             - ���� Ŭ���� ȣ��
 *               orderService.insertOrder(customerId, productId, count)
 *               �ֹ� ó���� ���� 1, 2, 3 ���� ���� ó��(orderService ����.)
 *               
 *               => ���� �������� ��û�� �ֹ��� �Ϸᰡ ��. => client ���� response �� �ʿ���.
 *               
 *             - response( View �����ؼ� ���� )
 *               "redirect:/getOrderList"
 *               
 *               Ŭ���̾�Ʈ(������)��
 *               OrderController.getOrderList() �� ȣ����.
 *               
 *               �ֹ� ��� ��ȸ. orderService.getOrderList(order);
 *               �ֹ� ��� ����Ʈ�� Model(orderList) �� ����.
 *               
 *             - response view �� ����.( order/getOrderList )
 *               /WEB-INF/jsp/order/getOrderList.jsp
 *                Model(orderList)�� jsp�� ���. => response view �ϼ�.
 *                
 *             - Ŭ���̾�Ʈ���� response view �� ����.
 *      
 *      
 *      b. ��ǰ ���
 *         - Ŭ���̾�Ʈ
 *           ��ǰ �ֹ� ��� ȭ�鿡�� �ֹ� ���.
 *           getOrderList.jsp
 *           <a href="/order/${order.id}/orderCancel">�ֹ����</a>
 *           �ֹ� �������(request)�� �۽�.
 *         
 *           Ŭ���̾�Ʈ���� �ֹ� ��� ��û�� �Ϸ�.
 *         
 *         - ����
 *           OrderController.orderCancel() ȣ��
 *           
 *           ����� �ֹ� ID�� �Ű������� ���񽺿� ����.
 *           orderService.orderCancel(orderId);
 *           
 *           ���� �������� �ֹ� ��Ҵ� �Ϸᰡ ��.
 *         
 *           response view �� ����.( order/getOrderList )
 *           /WEB-INF/jsp/order/getOrderList.jsp
 *           Model(orderList)�� jsp�� ���. => response view �ϼ�.
 *                
 *         - Ŭ���̾�Ʈ���� response view �� ����.
 */ 
 