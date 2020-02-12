package groovy

import org.apache.http.Header
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import sun.nio.cs.UTF_8

import java.nio.charset.Charset

// def url = "http://10.1.62.134/itsm/openapi/v3/tickets/get?ticket_id=eae362e8bbf74262b1960074d45dfca9&apikey=e10adc3949ba59abbe56e057f2gg88dd"

def url = "http://10.1.62.134/itsm/openapi/v3/tickets/query?apikey=e10adc3949ba59abbe56e057f2gg88dd"
HttpClientBuilder createBuilder = HttpClientBuilder.create();
HttpClient httpClient = createBuilder.build();

HttpResponse response = null;
def headers = ["Content-Type":"application/json;charset=UTF-8"]
//response = doGet(httpClient, url, headers)
def postData = ["keyword":"ASDSDA1910290001"]
Map<String, String> map = new HashMap<>();
map.put("keyword", "ASDSDA1910290001")
String data = "{\"keyword\":\"ASDSDA1910290001\"}"
response = doPost(httpClient, url, headers, data)

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
statusCode = String.valueOf(codeNum);
responseHeaders	= strBuilder.toString();
responseData = EntityUtils.toString(entity, "UTF-8");

//println(responseHeaders)
println(statusCode)
//println(codeNum)
println(responseData)
return statusCode

///**
// * 调用http get方法
// * @param httpclient 主体对象
// * @param url 目标地址
// * @param headers 请求体
// * @return
// */
//def doGet(HttpClient httpclient, String url, Map<String, String> headers) {
//    HttpGet getRequest = new HttpGet(url);
//    for (Map.Entry<String, String> entry : headers.entrySet()) {
//        getRequest.setHeader(entry.getKey(), entry.getValue());
//    }
//    return httpclient.execute(getRequest);
//}

/**
 * 调用http post方法
 * @param httpclient 主体对象
 * @param url 目标地址
 * @param headers 请求体
 * @param data 入参json
 * @return
 */
def doPost(HttpClient httpclient, String url, Map<String, String> headers, String data){
    HttpPost postRequest = new HttpPost(url);
    String contentType = "application/json;charset=UTF-8";
    for (Map.Entry<String, String> entry : headers.entrySet()) {
        postRequest.setHeader(entry.getKey(), entry.getValue());
        if ("Content-Type".equals(entry.getKey())) {
            contentType = entry.getValue();
        }
    }
    StringEntity entity = new StringEntity(data, "utf-8")
    entity.setContentType(contentType)
    postRequest.setEntity(entity)
    return httpclient.execute(postRequest);
}
