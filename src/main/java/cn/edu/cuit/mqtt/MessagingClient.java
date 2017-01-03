package cn.edu.cuit.mqtt;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;


public class MessagingClient {

	private MqttManager mqttManager = MqttManager.getInstance();

	static {
		MqttManager.getInstance().init();
	}

	public void subscribe(String topic) {
		try {
			mqttManager.subscribe(topic);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public void unSubscribe(String topic) {
		try {
			mqttManager.unSubscribe(topic);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void publish(String topic, byte[] payload) throws Exception {
		if (topic == null) {
			throw new Exception("public message error, topic is " + topic);
		}
		try {
			mqttManager.publish(topic, payload);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	/**
	 * set message callback which for receive message
	 * 
	 * @param callBack
	 */
	public void setMessageCallBack(MqttCallback callBack) {
		this.mqttManager.setMessageCallBack(callBack);
	}

}
