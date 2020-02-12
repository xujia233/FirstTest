package netty;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统io模型服务端
 * 传统模式下的弊端：当客户端数量较少时，整个模型可以正常运行，当有1w个客户端请求连接时，我们可以看到在这个模型下，每个连接都会创建一个线程
 * 来处理数据，并且带有一个while死循环，1w个连接则需要1w个线程来处理，这就带来如下几个问题：
 * 1、线程资源受限，当客户端数量过多时，所需要的线程数太多，而线程又是操作系统中非常宝贵的资源，同一时刻有大量的线程处于阻塞状态是非常浪费资源的
 * 2、线程切换效率低下，单机cpu核数固定，线程爆炸之后操作系统频繁进行线程切换，应用性能急剧下降
 * 3、数据读写是以字节为单位，效率不高
 * Created by xujia on 2019/10/12
 */
public class IOServer {

    public static void main(String[] args) throws Exception {
        // 创建一个serverSocket来监听8000端口
        ServerSocket serverSocket = new ServerSocket(8000);
        while (true) {
            try {
                // 1、阻塞方法获取新的连接
                Socket socket = serverSocket.accept();
                // 2、每一个连接都另起线程处理
                new Thread(() -> {
                    try {
                        System.out.println("新连接：" + Thread.currentThread().getName());
                        byte[] bytes = new byte[1024];
                        InputStream inputStream = socket.getInputStream();
                        int len;
                        // 3、按字节流方式读取数据
                        while ((len = inputStream.read(bytes)) != -1) {
                            System.out.println(new String(bytes, 0, len));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
