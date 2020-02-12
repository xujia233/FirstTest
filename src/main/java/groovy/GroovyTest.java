package groovy;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by xujia on 2019/10/23
 */
@Slf4j
public class GroovyTest {

    public static void main(String[] args) {

    }

    @Test
    public void testa() {
        Date date = new Date();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(localDateTime.format(dateTimeFormatter));
    }

    @Test
    public void test03() {

        long start = System.currentTimeMillis();

        // 加载第三方jar包
        String filePath = System.getProperty("user.dir") + "/thirdLib";
        String[] fileNames = getFileNames(filePath);
        System.out.println(fileNames[0]);
        String urlPath = "file:" + filePath;
        URLClassLoader urlClassLoader = getUrlClassLoader(urlPath, fileNames, Thread.currentThread().getContextClassLoader());

        GroovyShell shell = new GroovyShell(urlClassLoader);
        try {
            //String str = "import org.apache.http.client.HttpClient";

            shell.getClassLoader().loadClass("org.apache.http.client.HttpClient;");
            shell.getClassLoader().loadClass("org.apache.http.Header");
            shell.getClassLoader().loadClass("org.apache.http.HttpEntity");
            shell.getClassLoader().loadClass("org.apache.http.HttpResponse");
            shell.getClassLoader().loadClass("org.apache.http.client.methods.HttpGet");
            shell.getClassLoader().loadClass("org.apache.http.impl.client.HttpClientBuilder");
            shell.getClassLoader().loadClass("org.apache.http.util.EntityUtils");
        } catch (ClassNotFoundException e) {
            System.out.println("class HttpClient is not found");
        }


//        Object returnValue = shell.evaluate("def count = 5\n" +
//                "def res = 0\n" +
//                "(1..< count).each {n -> res += count}\n" +
//                "return res\n");
        Object returnValue = shell.evaluate("import org.apache.http.Header\n" +
                "import org.apache.http.HttpEntity\n" +
                "import org.apache.http.HttpResponse\n" +
                "import org.apache.http.client.HttpClient\n" +
                "import org.apache.http.client.methods.HttpGet\n" +
                "import org.apache.http.impl.client.HttpClientBuilder\n" +
                "import org.apache.http.util.EntityUtils\n" +
                "\n" +
                "def url = \"http://10.1.62.134/itsm/openapi/v3/tickets/get?ticket_id=eae362e8bbf74262b1960074d45dfca9&apikey=e10adc3949ba59abbe56e057f2gg88dd\"\n" +
                "HttpClientBuilder createBuilder = HttpClientBuilder.create();\n" +
                "HttpClient httpClient = createBuilder.build();\n" +
                "\n" +
                "HttpResponse response = null;\n" +
                "def headers = [\"Content-Type\":\"application/json;charset=UTF-8\"]\n" +
                "response = doGet(httpClient, url, headers)\n" +
                "\n" +
                "Header[] resHeaders = response.getAllHeaders();\n" +
                "StringBuilder strBuilder = new StringBuilder();\n" +
                "for (Header header : resHeaders) {\n" +
                "    strBuilder.append(header.getName());\n" +
                "    strBuilder.append(\":\");\n" +
                "    strBuilder.append(header.getValue());\n" +
                "    strBuilder.append(\"\\n\");\n" +
                "}\n" +
                "\n" +
                "HttpEntity entity = response.getEntity();\n" +
                "\n" +
                "codeNum = response.getStatusLine().getStatusCode();\n" +
                "statusCode = String.valueOf(codeNum);\n" +
                "responseHeaders\t= strBuilder.toString();\n" +
                "responseData = EntityUtils.toString(entity, \"UTF-8\");\n" +
                "\n" +
                "//println(responseHeaders)\n" +
                "//println(statusCode)\n" +
                "//println(codeNum)\n" +
                "//println(responseData)\n" +
                "return statusCode\n" +
                "\n" +
                "def doGet(httpclient, url, headers) {\n" +
                "    HttpGet getRequest = new HttpGet(url);\n" +
                "    for (Map.Entry<String, String> entry : headers.entrySet()) {\n" +
                "        getRequest.setHeader(entry.getKey(), entry.getValue());\n" +
                "    }\n" +
                "\n" +
                "    HttpResponse httpResponse = httpclient.execute(getRequest);\n" +
                "    return httpResponse;\n" +
                "}\n");
        System.out.println(returnValue);

        System.out.println("脚本执行耗时:" + (System.currentTimeMillis() - start));
    }

    @Test
    public void ttt() {
        String filePath = System.getProperty("user.dir") + "/thirdLib";
        String[] fileNames = getFileNames(filePath);
        System.out.println(fileNames[0]);
        String urlPath = "file:" + filePath;
        URLClassLoader urlClassLoader = getUrlClassLoader(urlPath, fileNames, Thread.currentThread().getContextClassLoader());
        Thread.currentThread().setContextClassLoader(urlClassLoader);

        try {
            Class clz = Thread.currentThread().getContextClassLoader().loadClass("org.apache.http.client.HttpClient");

        } catch (ClassNotFoundException e) {
            System.out.println("class HttpClient is not found");
        }

    }

    public URLClassLoader getUrlClassLoader(String path, String[] fileNames, ClassLoader parent) {
        URL[] urls = new URL[3];
        int i = 0;
        // 加载指定目录下所有jar包
        for (String fileName : fileNames) {
            try {
                URL url = new URL(path + "/" + fileName);
                urls[i++] = url;
            } catch (MalformedURLException e) {
                log.error("Error loading jar package in " + path + " directory");
            }
        }
        try {
            URL url = new URL("file:" + System.getProperty("user.dir") + "/thirdLib1/httpcore-4.4.1.jar");
            urls[i] = url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new URLClassLoader(urls, parent);
    }

    public String[] getFileNames(String path) {
        File file = new File(path);
        if (!file.exists())
            file.mkdir();
        return file.list();
    }





    @Test
    public void test() throws ScriptException {
        long start = System.currentTimeMillis();

        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        // 每次生成一个engine实例
        ScriptEngine engine = scriptEngineManager.getEngineByName("groovy");
        System.out.println(engine.toString());

        Object returnValue = engine.eval("import org.apache.http.Header\n" +
                "import org.apache.http.HttpEntity\n" +
                "import org.apache.http.HttpResponse\n" +
                "import org.apache.http.client.HttpClient\n" +
                "import org.apache.http.client.methods.HttpGet\n" +
                "import org.apache.http.impl.client.HttpClientBuilder\n" +
                "import org.apache.http.util.EntityUtils\n" +
                "\n" +
                "def url = \"http://10.1.62.134/itsm/openapi/v3/tickets/get?ticket_id=eae362e8bbf74262b1960074d45dfca9&apikey=e10adc3949ba59abbe56e057f2gg88dd\"\n" +
                "HttpClientBuilder createBuilder = HttpClientBuilder.create();\n" +
                "HttpClient httpClient = createBuilder.build();\n" +
                "\n" +
                "HttpResponse response = null;\n" +
                "def headers = [\"Content-Type\":\"application/json;charset=UTF-8\"]\n" +
                "response = doGet(httpClient, url, headers)\n" +
                "\n" +
                "Header[] resHeaders = response.getAllHeaders();\n" +
                "StringBuilder strBuilder = new StringBuilder();\n" +
                "for (Header header : resHeaders) {\n" +
                "    strBuilder.append(header.getName());\n" +
                "    strBuilder.append(\":\");\n" +
                "    strBuilder.append(header.getValue());\n" +
                "    strBuilder.append(\"\\n\");\n" +
                "}\n" +
                "\n" +
                "HttpEntity entity = response.getEntity();\n" +
                "\n" +
                "codeNum = response.getStatusLine().getStatusCode();\n" +
                "statusCode = String.valueOf(codeNum);\n" +
                "responseHeaders\t= strBuilder.toString();\n" +
                "responseData = EntityUtils.toString(entity, \"UTF-8\");\n" +
                "\n" +
                "//println(responseHeaders)\n" +
                "//println(statusCode)\n" +
                "//println(codeNum)\n" +
                "//println(responseData)\n" +
                "return statusCode\n" +
                "\n" +
                "def doGet(httpclient, url, headers) {\n" +
                "    HttpGet getRequest = new HttpGet(url);\n" +
                "    for (Map.Entry<String, String> entry : headers.entrySet()) {\n" +
                "        getRequest.setHeader(entry.getKey(), entry.getValue());\n" +
                "    }\n" +
                "\n" +
                "    HttpResponse httpResponse = httpclient.execute(getRequest);\n" +
                "    return httpResponse;\n" +
                "}\n");
        System.out.println(returnValue);

        System.out.println("脚本执行耗时:" + (System.currentTimeMillis() - start));
    }

    @Test
    public void test01() {

    }
}
