package webService.wsimport;

/**
 * Created by xujia on 2019/10/31
 */
public class MobileInfoTest {

    public static void main(String[] args) {
        // 生成服务对象
        MobileCodeWS mobileCodeWS = new MobileCodeWS();
        // 获得webService服务的访问方式
        MobileCodeWSSoap mobileCodeWSSoap = mobileCodeWS.getMobileCodeWSSoap();

        // 查询当前手机号归属地省份、地区和手机卡类型信息
        String mobileInfo = mobileCodeWSSoap.getMobileCodeInfo("13511280059", null);
        System.out.println(mobileInfo);

        // 查询国内手机号归属地数据库信息，返回数据：一维字符串数组（省份 城市 记录数量）
        ArrayOfString arrayOfString = mobileCodeWSSoap.getDatabaseInfo();
        arrayOfString.getString().forEach(System.out::println);
    }
}
