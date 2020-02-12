package groovy

import org.apache.http.Header
import org.apache.http.HttpEntity
import org.apache.http.util.EntityUtils

// get请求参数可直接拼接在url上，或者参数单独写一个map，见如下
//def url = "http://10.1.62.134/itsm/openapi/v3/tickets/relate?apikey=e10adc3949ba59abbe56e057f2gg88dd&src_id=" + ticketId + "&dest_id=1c4fd220350d4174944b809c0ae51ea6"
//def headers = ["Content-Type":"application/json;charset=UTF-8"]
//def response = toolMethod.doHttpGet(url, headers, null)

// get请求参数可单独写一个map
def url = "http://10.1.62.134/itsm/openapi/v3/tickets/relate"
def headers = ["Content-Type":"application/json;charset=UTF-8"]
def params = [apikey:"e10adc3949ba59abbe56e057f2gg88dd",src_id:ticketId,dest_id:"1c4fd220350d4174944b809c0ae51ea6"]
def response = toolMethod.doHttpGet(url, headers, params)


Header[] resHeaders = response.getAllHeaders();
StringBuilder strBuilder = new StringBuilder();
for (Header header : resHeaders) {
    strBuilder.append(header.getName());
    strBuilder.append(":");
    strBuilder.append(header.getValue());
    strBuilder.append("\n");
}

HttpEntity entity = response.getEntity();

codeNum = response.getStatusLine().getStatusCode();
responseHeaders	= strBuilder.toString();
responseData = EntityUtils.toString(entity, "UTF-8");

toolMethod.printLog("请求头信息:" + responseHeaders)
toolMethod.printLog("请求结果码:" + codeNum)
toolMethod.printLog("请求结果数据:" + responseData)
