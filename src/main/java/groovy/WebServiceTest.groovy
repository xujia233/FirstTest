package groovy

import org.apache.axis.client.Call
import org.apache.axis.client.Service

import javax.xml.namespace.QName
import javax.xml.rpc.ParameterMode
import javax.xml.rpc.encoding.XMLType

def result = doWebService("groovy", "http://10.1.240.196:9000/HelloWorld?wsdl", "http://example/", "sayHelloWorldFrom")
println(result)
return result

/**
 * 调用webService接口
 * @param requestXml 请求报文
 * @param wsdl webService地址
 * @param targetNamespace 命名空间
 * @param webServiceMthod 调用的方法名
 * @return
 */
def doWebService(String requestXml, String wsdl, String targetNamespace, String webServiceMthod) {
    Service service = new Service()
    Call call = (Call) service.createCall()
    call.setTargetEndpointAddress(new URL(wsdl))
    call.setOperationName(new QName(targetNamespace, webServiceMthod))
    call.addParameter("arg0", XMLType.XSD_STRING, ParameterMode.IN)
    call.setReturnType(XMLType.XSD_STRING)
    Object[] object = [requestXml]
    return call.invoke(object);
}
