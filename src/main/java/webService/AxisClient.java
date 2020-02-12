package webService;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.MessageElement;
import org.apache.axis.message.SOAPHeader;
import org.apache.axis.message.SOAPHeaderElement;
import org.w3c.dom.*;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import java.net.URL;
import java.util.Iterator;

/**
 * 基于Axis1实现的webservice客户端
 * Created by xujia on 2019/10/29
 */
public class AxisClient {

    public static void main(String[] args) {
        Service service = new Service();

        try {
            Call call = (Call) service.createCall();
            String url = "http://localhost:9000/HelloWorld?wsdl";
            // 设置服务端地址
            call.setTargetEndpointAddress(new URL(url));
            // 设置要执行的方法
            call.setOperationName(new QName("http://example.xj.test", "sayHelloWorldFrom"));
            // 如果WSDL的方法上有要求必须要填SOAPAction，则必须加上如下这一行，否则会报服务器无法识别HTTP头SOAPAction的值
            //call.setSOAPActionURI("http://example.xj.test/sayHelloWorldFrom");

            // 设置要传入的参数，如果没有要传入的参数则无需该行
            call.addParameter("from", XMLType.XSD_STRING, ParameterMode.IN);
            // 设置返回类型
            call.setReturnType(XMLType.XSD_STRING);

            // 设置header信息
            SOAPHeaderElement element = new SOAPHeaderElement(new QName("Element"));

            MessageElement user = new MessageElement(new QName("Username"), "admin");
            MessageElement password = new MessageElement(new QName("Passord"), "admin123");
            element.addChild(user);
            element.addChild(password);
            call.addHeader(element);

            // 调用webservice服务
            String from = "webService客户端";
            String result = (String) call.invoke(new Object[] {from});
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public static void main(String[] args) {
//        Service service = new Service();
//        try {
//            Call call = (Call) service.createCall();
//            String url = "http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx?wsdl";
//            // 设置服务端地址
//            call.setTargetEndpointAddress(new URL(url));
//            // 设置要执行的方法
//            call.setOperationName(new QName("http://WebXml.com.cn/", "getMobileCodeInfo"));
//            // 如果WSDL的方法上有要求必须要填SOAPAction，则必须加上如下这一行，否则会报服务器无法识别HTTP头SOAPAction的值
//            call.setSOAPActionURI("http://WebXml.com.cn/getMobileCodeInfo");
//
//            // 设置要传入的参数，如果没有要传入的参数则无需该行
//            call.addParameter("mobileCode", XMLType.XSD_STRING, ParameterMode.IN);
//            call.addParameter("userID", XMLType.XSD_STRING, ParameterMode.IN);
//            // 设置返回类型
//            call.setReturnType(XMLType.XSD_STRING);
//            //call.setReturnQName(new QName("http://WebXml.com.cn/", "getMobileCodeInfoResult"));
//
//            // 调用webservice服务
//            String result = (String) call.invoke(new Object[] {"13511280059", null});
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}
