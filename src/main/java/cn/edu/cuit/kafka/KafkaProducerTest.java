package cn.edu.cuit.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class KafkaProducerTest {

	
	public void product(String topic, Integer key, String value) {
		Properties properties = new Properties();
		Producer<Integer, String> producer = new KafkaProducer<Integer, String>(properties);
		
		ProducerRecord<Integer, String> record = new ProducerRecord<Integer, String>(topic, key, value);
		producer.send(record, new Callback() {

			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				// TODO Auto-generated method stub
				
			}
			
		});
		producer.close();
	}
	
	public static void main(String[] args) {
		KafkaProducerTest kp = new KafkaProducerTest();
		kp.product("test1", 1, "1");
	}
}
