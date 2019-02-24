package api;

/**
 * Created by xujia on 2019/2/24
 */
public interface ScoreService {

    int getScore(int i);

    default int getEnglishScore(int i) {
        return i;
    }

    default int getMathScore(int i) {
        return i * 10;
    }
}
