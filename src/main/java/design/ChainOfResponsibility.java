package design;

/**
 * 责任链模式，主要就是为了将请求发送者与请求接收者进行解耦，屏蔽请求的处理过程，作为请求者可以不用知道到底是由谁来处理的，这是责任链模式的核心
 * 换句话说就是：要么承担责任作出响应，要么向下传递请求，当然也可以不作任何处理
 *
 * 每一个接收者都拥有下一个接收者的引用，这所有接收者便组成了一条链，但是责任链模式不能保证请求一定被处理
 * 优点：1、降低耦合度，它将请求的发送者和接收者解耦 2、简化了对象，使得对象不需要知道链的结构
 * 3、增强给对象指派职责的灵活性，通过改变链内的成员或者调动它们的次序，允许动态的新增或者删除责任 4、增加新的请求处理类很方便
 * 缺点：1、不能保证请求一定被接收 2、调试起来比较麻烦
 * Created by xujia on 2019/12/25
 */
public class ChainOfResponsibility {

    public static void main(String[] args) {
        // 这里直接new出三个请求接收者，并组装成一条链
        Logger infoLogger = new InfoLogger(Logger.INFO);
        Logger debuLogger = new DebugLogger(Logger.DEBUG);
        Logger errorLogger = new ErrorLogger(Logger.ERROR);

        errorLogger.setNextLogger(debuLogger);
        debuLogger.setNextLogger(infoLogger);

        // 模拟发送请求给最外层的接收者，请求发送者无需知道该请求是由谁来处理的
        errorLogger.logMessage(Logger.INFO, "This is a info message");
        errorLogger.logMessage(Logger.DEBUG, "This is a debug message");
        errorLogger.logMessage(Logger.ERROR, "This is a error message");
    }
}


/**
 * 定义基本抽象日志类，包含日志级别
 */
abstract class Logger {
    public static final int INFO = 1;
    public static final int DEBUG = 2;
    public static final int ERROR = 3;

    /**
     * 当前日志记录器的级别，用来判断当前记录器是否有权限
     */
    protected int level;
    /**
     * 下一个接收者的引用
     */
    protected Logger nextLogger;

    public void setNextLogger(Logger nextLogger) {
        this.nextLogger = nextLogger;
    }

    /**
     * 具体判断、输出逻辑
     * @param level
     * @param msg
     */
    public void logMessage(int level, String msg) {
        if (this.level <= level) {
            write(msg);
        }
        if (null != nextLogger) {
            nextLogger.logMessage(level, msg);
        }
    }

    public abstract void write(String msg);
}

/**
 * Info日志实现类
 */
class InfoLogger extends Logger {

    public InfoLogger(int level) {
        this.level = level;
    }

    @Override
    public void write(String msg) {
        System.out.println("Info context:" + msg);
    }
}

/**
 * Debug日志实现类
 */
class DebugLogger extends Logger {

    public DebugLogger(int level) {
        this.level = level;
    }

    @Override
    public void write(String msg) {
        System.out.println("Debug context:" + msg);
    }
}

/**
 * Error日志实现类
 */
class ErrorLogger extends Logger {

    public ErrorLogger(int level) {
        this.level = level;
    }

    @Override
    public void write(String msg) {
        System.out.println("Error context:" + msg);
    }
}
