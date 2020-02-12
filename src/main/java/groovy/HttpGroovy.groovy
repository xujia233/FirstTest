//package groovy
//
//import java.io.IOException;
//import java.security.KeyManagementException;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import javax.net.ssl.SSLContext;
//
//import org.apache.http.Header;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpDelete;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpPut;
//import org.apache.http.conn.ssl.NoopHostnameVerifier;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.conn.ssl.TrustStrategy;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.ssl.SSLContextBuilder;
//import org.apache.http.util.EntityUtils;
//
//
//
//HttpClientBuilder createBuilder = HttpClientBuilder.create();
//
//println "destURL: " + destURL;
//println "queryString: " + queryString;
//println "requestHeaders: " + requestHeaders;
//println "postData: " + postData;
//
//HttpClient httpclient = createBuilder.build();
//
//if (!StringUtils.isBlank(queryString)) {
//    destURL = destURL + "?" + queryString;
//}
//
//Map<String, String> headers = parseHeaders(requestHeaders);
//
//HttpResponse response = null;
//if (method.equals("GET")) {
//    response = doGet(httpclient, destURL, headers);
//} else if (method.equals("POST")) {
//    response = doPost(httpclient, destURL, headers, postData);
//} else if (method.equals("PUT")) {
//    response = doPut(httpclient, destURL, headers, postData);
//} else if (method.equals("DELETE")) {
//    response = doDelete(httpclient, destURL, headers);
//} else {
//    throw new IllegalArgumentException("请求类型不正确：" + method);
//}
//
//Header[] resHeaders = response.getAllHeaders();
//StringBuilder strBuilder = new StringBuilder();
//for (Header header : resHeaders) {
//    strBuilder.append(header.getName());
//    strBuilder.append(":");
//    strBuilder.append(header.getValue());
//    strBuilder.append("\n");
//}
//
//
//HttpEntity entity = response.getEntity();
//
//codeNum = response.getStatusLine().getStatusCode();
//statusCode = String.valueOf(codeNum);
//responseHeaders	= strBuilder.toString();
//responseData = EntityUtils.toString(entity, "UTF-8");
//
//if(200 == codeNum)
//    return true;
//else
//    return false;
//
//def doGet(httpclient, url, headers) {
//    HttpGet getRequest = new HttpGet(url);
//    for (Entry<String, String> entry : headers.entrySet()) {
//        getRequest.setHeader(entry.getKey(), entry.getValue());
//    }
//
//    HttpResponse httpResponse = httpclient.execute(getRequest);
//    return httpResponse;
//}
//
//def doPost(httpclient, url, headers, postData){
//    HttpPost postRequest = new HttpPost(url);
//    String contentType = "application/json;charset=UTF-8";
//    for (Entry<String, String> entry : headers.entrySet()) {
//        postRequest.setHeader(entry.getKey(), entry.getValue());
//        if ("Content-Type".equals(entry.getKey())) {
//            contentType = entry.getValue();
//        }
//    }
//
//    StringEntity se = new StringEntity(postData, "utf-8");
//    se.setContentType(contentType);
//    postRequest.setEntity(se);
//    HttpResponse httpResponse = httpclient.execute(postRequest);
//
//    return httpResponse;
//}
//
//def doPut(httpclient, url, headers, postData)
//        throws ClientProtocolException, IOException {
//    HttpPut postRequest = new HttpPut(url);
//    String contentType = "application/json;charset=UTF-8";
//    for (Entry<String, String> entry : headers.entrySet()) {
//        postRequest.setHeader(entry.getKey(), entry.getValue());
//        if ("Content-Type".equals(entry.getKey())) {
//            contentType = entry.getValue();
//        }
//    }
//
//    StringEntity se = new StringEntity(postData, "utf-8");
//    se.setContentType(contentType);
//    postRequest.setEntity(se);
//    HttpResponse httpResponse = httpclient.execute(postRequest);
//    return httpResponse;
//}
//
//def doDelete(httpclient, url, headers){
//    HttpDelete postRequest = new HttpDelete(url);
//    for (Entry<String, String> entry : headers.entrySet()) {
//        postRequest.setHeader(entry.getKey(), entry.getValue());
//    }
//    HttpResponse httpResponse = httpclient.execute(postRequest);
//
//    return httpResponse;
//}
//
//def parseHeaders(requestHeaders) {
//    Map<String, String> headerMap = new HashMap<>();
//    if (StringUtils.isBlank(requestHeaders))
//        return headerMap;
//
//    String[] headerSplit = requestHeaders.split("\n");
//    if (headerSplit != null) {
//        for (String headerStr : headerSplit) {
//            String[] split = headerStr.split(":");
//            if (split != null && split.length == 2) {
//                headerMap.put(split[0], split[1]);
//            }
//        }
//    }
//    return headerMap;
//}
//
//
