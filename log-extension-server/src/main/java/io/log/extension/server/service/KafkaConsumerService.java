package io.log.extension.server.service;

import io.log.extension.server.entity.DefaultMessage;
import io.log.extension.server.util.JsonUtil;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Properties;

@Service
public class KafkaConsumerService {
    @Value("${kafka.topics}")
    private String topics;
    @Value("${kafka.bootstrap.servers}")
    private String bootstrapServers;
    @Value("${kafka.group.id}")
    private String groupId;
    @Value("${kafka.enable.auto.commit}")
    private String enableAutoCommit;
    @Value("${kafka.auto.commit.interval.ms}")
    private String autoCommitIntervalMs;
    @Value("${kafka.session.timeout.ms}")
    private String sessionTimeoutMs;
    @Value("${kafka.key.deserializer}")
    private String keyDeserializer;
    @Value("${kafka.value.deserializer}")
    private String valueDeserializer;
    KafkaConsumer<byte[], byte[]> consumer = null;

    @Autowired
    private MessageService messageService;

    private void init() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,
                autoCommitIntervalMs);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                valueDeserializer);
        consumer = new KafkaConsumer<byte[], byte[]>(props);
        consumer.subscribe(Arrays.asList(topics.split(",")));
    }

    public void test() {
        if (null == consumer) {
            init();
        }

        while (true) {

            ConsumerRecords<byte[], byte[]> records = consumer.poll(20);
            for (ConsumerRecord<byte[], byte[]> record : records) {

                String value = new String(record.value());
                System.out.println("=============value:" + value);

                String begin = value.substring(0, 1);
                System.out.println("=============begin:" + begin);
                if ("{".equals(begin)) {
                    DefaultMessage defaultMessage = JsonUtil.unmarshal(value, DefaultMessage.class);
                    messageService.handleDefaultMessage(defaultMessage);

                }

            }

        }
    }
}
