package netty;

import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

/**
 * 传统IO模型客户端
 * Created by xujia on 2019/10/12
 */
public class IOClient {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                // 每隔两秒向服务端写入hello world
                while (true) {
                    try {
                        OutputStream outputStream = socket.getOutputStream();
                        outputStream.write((new Date() + ": hello world").getBytes());
                        outputStream.flush();
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
