package service;

import com.sun.deploy.net.URLEncoder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.tree.TreeNode;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xujia on 2019/3/29
 */
@Slf4j
public class SMSTest {

    private static Logger logger = LoggerFactory.getLogger(SMSTest.class);

    @Test
    public void test02() {
        log.info("fk");
        logger.info("sadasdas");
    }


    /**
     * 使用restful格式对接阿里大于
     * @throws Exception
     */
    @Test
    public void test01() throws Exception{
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, String> params = new HashMap<>();
        // 当前时间戳
        params.put("timestamp", dateTimeFormatter.format(localDateTime));

        // api协议版本，默认2.0
        params.put("v", "2.0");
        // 自己申请的apikey
        params.put("app_key", "23393019");
        // api接口名称
        params.put("method", "alibaba.aliqin.fc.sms.num.send");
        // 签名算法，统一以hmac进行计算
        params.put("sign_method", "hmac");
        // 响应结果格式
        params.put("format", "json");
        // 短信类型，默认normal
        params.put("sms_type", "normal");
        // 接收手机号码，多个以逗号隔开
        params.put("rec_num", "17816898173");
        // 短信签名，必须是阿里大于审核通过的方可使用
        params.put("sms_free_sign_name", "优云");
        //params.put("sms_free_sign_name", URLEncoder.encode("优云", "UTF-8"));
        // 短信模板ID，必须是阿里大于中可用的模板ID
        params.put("sms_template_code", "SMS_11690120");


        params.put("force_sensitive_param_fuzzy", "true");
        params.put("extend", "123456");
        params.put("partner_id", "top-apitools");



        // 短信模板变量，变量名称需与模板中的一致，格式形如：{"exception", "xx"}
        Map<String, String> smsParam = new HashMap<>();
        smsParam.put("exception", "代码调用阿");

        //params.put("sms_param", JsonUtil.encode(smsParam));

        // 计算签名
        Map<String, String> map = signTopRequest(params, "dd2811da27cb4f2840d07d5f75f9cb09");
        params.put("sign", map.get("sign"));
//        // 对参数进行编码
//        System.out.println(map.get("encodeStr"));
        String url = "http://gw.api.taobao.com/router/rest";
        //System.out.println(params);

        System.out.println("sign :" + params.get("sign"));
//        String json = HttpClientUtil.get(url, null, params);
//        Map<String, Object> dataMap = JsonUtil.decode(json, Map.class);
//        System.out.println("结果：" + dataMap);

    }

    public static Map<String, String> signTopRequest(Map<String, String> params, String secret) throws IOException {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        for (String key : keys) {
            String value = params.get(key);
            if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
                query.append(key).append(value);
            }
        }

        // 第三步：使用HMAC加密
        byte[] bytes = encryptHMAC(query.toString(), secret);


        // 第四步：把二进制转化为大写的十六进制(正确签名应该为32大写字符串，此方法需要时使用)
        Map<String, String> map = new HashMap<>();
        map.put("sign", byte2hex(bytes));
        map.put("encodeStr", URLEncoder.encode(query.toString(), "UTF-8"));
        return map;
    }

    public static byte[] encryptHMAC(String data, String secret) throws IOException {
        byte[] bytes = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }

    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
}
