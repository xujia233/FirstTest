package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * Created by xujia on 2019/4/21
 */
public class KafkaProducter {

    private final KafkaProducer<String, String> producer;
    public final static String TOPIC = "heihei";

    private KafkaProducter(){
        Properties properties = new Properties();
        //此处配置的是kafka的端口
        properties.put("bootstrap.servers", "10.1.61.105:9192");

        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        properties.put("request.required.acks","-1");

        producer = new KafkaProducer<>(properties);
    }

    void produce() {
        //发送100条消息
        int messageNo = 100;
        int count = 200;
        while (messageNo < count) {
            String key = String.valueOf(messageNo);
            String data = "hello kafka message " + key;
            long startTime = System.currentTimeMillis();
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, key, data);
            producer.send(record, new DataCallback(startTime, data));
            System.out.println(data);
            messageNo++;
        }
    }
    public static void main( String[] args )
    {
        new KafkaProducter().produce();
    }

}
