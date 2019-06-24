package service;

import api.ScoreService;
import org.junit.Test;

/**
 * Created by xujia on 2019/2/24
 */
public class ScoreServiceImpl implements ScoreService {

    @Override
    public int getScore(int i) {
        return 0;
    }

    public static void main (String[] args) {
        ScoreServiceImpl scoreService = new ScoreServiceImpl();
        System.out.println(scoreService.getScore(10));
        System.out.println(scoreService.getEnglishScore(10));
        System.out.println(scoreService.getMathScore(10));
    }

    @Test
    public void test01() {
        String s = "0";
        switch (s) {
            case "0" :
                System.out.println("0");
            case "1" :
                System.out.println("1");
                break;
            case "2" :
                System.out.println("2");
            default:
                System.out.println("defult");
        }
    }
}
