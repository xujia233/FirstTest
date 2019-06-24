package notify;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 邮件发送测试，该deme以qq邮箱为发送服务器
 * Created by xujia on 2019/5/24
 */
@Slf4j
public class EmailSend {

    @Test
    public void test() {
        String host = "smtp.qq.com";
        int port = 465;
        String fromEmail = "857271710@qq.com";
        String password = "iakywxwquixhbeaa";
        List<String> toEmails = Lists.newArrayList("xuj_java@163.com");
        String content = "邮件内容";
        String subject = "邮件标题";
        // 设置附件
        File[] files = new File[1];
        // 为了测试全部写死
        File file = new File("/Users/xujia/Downloads/mydocument/WechatIMG2.jpeg");
        files[0] = file;

        sendEmail(host, port, fromEmail, password, toEmails, content, subject, files);
    }

    /**
     * 发送邮件具体方法
     * @param host 发送邮件主机名
     * @param port 主机端口
     * @param fromEmail 发件人邮箱地址
     * @param password 主机授权码
     * @param toEmails 接收人
     * @param content 邮件呢文本内容
     * @param subject 邮件标题
     * @param files 邮件附件
     * @return 是否发送成功
     */
    public boolean sendEmail(String host, int port, String fromEmail, String password, List<String> toEmails, String content, String subject, File[] files) {
        // 标识发送结果
        boolean result = false;
        // 封装会话基本参数
        Properties properties = new Properties();
        // 连接协议，可加可不加
        properties.put("mail.transport.protocol", "smtp");
        // 主机名，以qq服务器为例
        properties.put("mail.smtp.host", host);
        // 主机端口
        properties.put("mail.smtp.port", port);
        // 是否需要权限验证
        properties.put("mail.smtp.auth", "true");
        // 设置是否使用ssl安全连接，qq邮箱如果不设为true则会发送不了
        properties.put("mail.smtp.ssl.enable", "true");
        // 设置是否显示debug信息 true 会在控制台显示相关信息
        properties.put("mail.debug", "true");

        // 获取会话对象
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 设置发件人邮箱的账号及对应主机的授权码，授权码需要申请
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // 获取邮件对象
            Message message = new MimeMessage(session);
            // 设置发件人邮箱地址
            message.setFrom(new InternetAddress(fromEmail));
            // 设置收件人邮箱地址
            if (!CollectionUtils.isEmpty(toEmails)) {
                for (String to : toEmails) {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                }
            }
            // 设置密送人邮箱地址
            // message.addRecipient(Message.RecipientType.BCC, new InternetAddress("xxx@xxx"));
            // 设置抄送人邮箱地址
            // message.addRecipient(Message.RecipientType.CC, new InternetAddress("xxx@xxx"));
            message.setSubject(subject);

            // 设置发送内容，带附件，若无需附件可直接调用：message.setText("邮件内容");
            // 获取多重消息对象
            Multipart multipart = new MimeMultipart();
            if (null != files) {
                MimeBodyPart mimeBodyPart;
                FileDataSource fileDataSource;
                // 处理多附件情况
                for (File tempFile : files) {
                    // 获取附件对象
                    mimeBodyPart = new MimeBodyPart();
                    // 设置需要发送附件的文件路径
                    fileDataSource = new FileDataSource(tempFile);
                    mimeBodyPart.setDataHandler(new DataHandler(fileDataSource));
                    try {
                        // 处理附件名称中文乱码问题
                        mimeBodyPart.setFileName(MimeUtility.encodeText(fileDataSource.getName()));
                    } catch (Exception e) {
                        log.error("Encode file name error", e.getMessage());
                    }
                    multipart.addBodyPart(mimeBodyPart);
                }
            }

            MimeBodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(content, "text/html; charset=utf-8");
            multipart.addBodyPart(contentPart);
            message.setContent(multipart);
            // 设置发送时间
            message.setSentDate(new Date());
            Transport.send(message);
            result = true;
            log.debug("Send email success, from:{}, to:{}", fromEmail, toEmails);
        } catch (MessagingException e) {
            log.error("Send email error, error info:{}, ", e.getMessage());
        }
        return result;
    }
}
