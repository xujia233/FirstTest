package kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by xujia on 2019/4/21
 */
public class KafkaConsumer22 {

    private final Consumer<String, String> consumer;

    private KafkaConsumer22() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.1.61.105:9192");//服务器ip:端口号，集群用逗号分隔
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("heihei"));
    }

    void consume() {
        while(true){
            ConsumerRecords<String, String> records = consumer.poll(100);
            if (records.count() > 0) {
                for (ConsumerRecord<String, String> record : records) {
                    String message = record.value();
                    System.out.println("从kafka接收到的消息是：" + message);
                }
            }
        }

    }

    public static void main(String[] args) {
        new KafkaConsumer22().consume();
    }
}
