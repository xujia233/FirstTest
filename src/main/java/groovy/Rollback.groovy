package groovy

import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.codehaus.jackson.map.ObjectMapper

// 该脚本的案例为：当工单流转结束后关联指定工单，若关联失败则不允许流转即回滚当前工单
def mapper = new ObjectMapper()
def url = "http://10.1.62.134/itsm/openapi/v3/tickets/relate?apikey=e10adc3949ba59abbe56e057f2gg88dd&src_id=${ticket.ticketId}&dest_id=1c4fd220350d4174944b809c0ae51ea6"
//def url = "http://10.1.62.134/itsm/openapi/v3/tickets/relate?apikey=e10adc3949ba59abbe56e057f2gg88dd&src_id=0da283158c9b441e868cec0c0c0b78de&dest_id=1c4fd220350d4174944b809c0ae51ea6"

def httpClient = HttpClientBuilder.create().build()
def headers = ["Content-Type":"application/json;charset=UTF-8"]

def response = doGet(httpClient, url, headers)

resultCode = response?.getStatusLine()?.getStatusCode()
if (200 != resultCode) {
    return "relate ticket success"
} else {
    def data = [handle_type:4, ticket_id:${ticket.ticketId}, activity_id:"0798bdeacc714a6ea3a3bdda3acff587"]
    url = "http://10.1.62.134/itsm/openapi/v3/tickets/handle?apikey=e10adc3949ba59abbe56e057f2gg88dd"
    response = doPost(httpClient, url, headers, mapper.writeValueAsString(data))
    resultCode = response?.getStatusLine()?.getStatusCode()
    if (200 == resultCode)
        return "relate ticket faild and rollback success"
    else
        return "relate ticket faild and rollback faild"
}

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

/**
 * 调用http get方法
 * @param httpclient 主体对象
 * @param url 目标地址
 * @param headers 请求体
 * @return
 */
def doGet(HttpClient httpclient, String url, Map<String, String> headers) {
    HttpGet getRequest = new HttpGet(url)
    for (Map.Entry<String, String> entry : headers.entrySet()) {
        getRequest.setHeader(entry.getKey(), entry.getValue())
    }
    return httpclient.execute(getRequest)
}
