package cn.edu.cuit.kafka;

import java.util.Properties;
import java.util.Set;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KafkaConsumerTest {
	
	public void consumer(Set<String> topics) {
		Properties properties = new Properties();
		Consumer<Integer, String> consumer = new KafkaConsumer<Integer, String>(properties);
		
		consumer.subscribe(topics);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
