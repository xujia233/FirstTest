package design;

/**
 * 外观模式测试类，该模式比较简单，即整合后端接口，对外暴露一个简单方法，解除系统与客户端的耦合程度
 * Created by xujia on 2019/7/8
 */
public class FacadeTest {

    public static void main(String[] args) {
        ComputerFacade computerFacade = new ComputerFacade();
        computerFacade.start();
    }
}

/**
 * 定义硬件基础接口
 */
interface HardWare {
    void start();
}

/**
 * 定义CPU类实现基础接口
 */
class Cpu implements HardWare {
    @Override
    public void start() {
        System.out.println("Start CPU");
    }
}

/**
 * 定义内存类实现基础接口
 */
class Ram implements HardWare {
    @Override
    public void start() {
        System.out.println("Start RAM");
    }
}

/**
 * 定义硬盘类实现基础接口
 */
class Ssd implements HardWare {
    @Override
    public void start() {
        System.out.println("Start SSD");
    }
}

/**
 * 定义电脑整机外观类，隔离客户端与底层的业务逻辑（CPU、内存、硬盘、线等）
 */
class ComputerFacade implements HardWare{

    private Cpu cpu;
    private Ram ram;
    private Ssd ssd;

    public ComputerFacade() {
        cpu = new Cpu();
        ram = new Ram();
        ssd = new Ssd();
    }

    /**
     * 启动电脑方法
     */
    @Override
    public void start() {
        // 这里就是用来处理与系统之间的业务逻辑，此处就是依次启动CPU、内存、硬盘
        // 一般来说，客户端无需关注底层的启动信息，这边为了方便演示将cpu等的启动信息也一并输出，实际上只要启动即可并在最后告知用户电脑已成功启动便可
        cpu.start();
        ram.start();
        ssd.start();
        System.out.println("电脑已成功启动");
    }
}
