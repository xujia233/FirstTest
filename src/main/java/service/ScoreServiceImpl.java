package service;

import api.ScoreService;

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
}
