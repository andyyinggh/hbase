package cn.edu.cuit.mqtt;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import cn.edu.cuit.utils.ConfigUtil;
import cn.edu.cuit.utils.StringUtil;


/**
 * @author Dongdong.Su
 * 
 */
public class MqttManager {
	public static final String CONFIG_MQTT_IP = "mqtt.ip";
	public static final String CONFIG_MQTT_PORT = "mqtt.port";
	private static final String CONFIG_MQTT_QOS = "mqtt.qos";
	private static final String CONFIG_MQTT_USERNAME = "mqtt.username";
	private static final String CONFIG_MQTT_PASSWORD = "mqtt.password";
	private static final String CONFIG_MQTT_MAXINFLIGHT = "mqtt.maxinflight";
	
	private int qos;
	private boolean retained;
	private String broker;
	
	private String ip;
	private Integer port;

	private String clientId;
	private MemoryPersistence persistence;
	private MqttClient mqttClient;

	private MqttClient mqttClientSend;
	

	private static MqttManager instance = new MqttManager();

	private MqttManager() {

	}

	/**
	 * @return MqttManager
	 */
	public static MqttManager getInstance() {
		return instance;
	}

	/**
	 * @return MqttManager
	 */
	public MqttManager init() {
		// load broker config
		loadBrokerConfig();

		// init mqtt
		initMqtt();

		return getInstance();
	}

	private void initMqtt() {
		this.qos = ConfigUtil.getInteger(CONFIG_MQTT_QOS);
		if (this.qos < 0 || this.qos > 2) {
			this.qos = 0;
		}
		this.retained = false;
		this.clientId = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		this.persistence = new MemoryPersistence();
		try {
			this.mqttClient = new MqttClient(broker, clientId, persistence);
			this.mqttClientSend = new MqttClient(broker, clientId + "/SEND", persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			connOpts.setAutomaticReconnect(true);
			int maxInflight = ConfigUtil.getInteger(CONFIG_MQTT_MAXINFLIGHT);
			connOpts.setMaxInflight(maxInflight);
		
			String userName = ConfigUtil.getString(CONFIG_MQTT_USERNAME);
			String password = ConfigUtil.getString(CONFIG_MQTT_PASSWORD);
			
			if (!StringUtil.isEmpty(userName)) {
				connOpts.setUserName(userName);
			}
			
			if (!StringUtil.isEmpty(password)) {
				connOpts.setPassword(password.toCharArray());
			}
			
			this.mqttClient.connect(connOpts);
			this.mqttClientSend.connect(connOpts);
		} catch (MqttSecurityException e) {
		} catch (MqttException e) {
		} catch (Exception e) {
		}
	}

	private void loadBrokerConfig() {
		ip = ConfigUtil.getString(CONFIG_MQTT_IP);
		port = ConfigUtil.getInteger(CONFIG_MQTT_PORT);
	}

	/**
	 * @param topic
	 * @throws MqttException
	 */
	public void subscribe(final String topic) throws MqttException {
		try {
			mqttClient.subscribe(topic, qos);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	/**
	 * unSubscribe topic
	 * 
	 * @param topic
	 */
	public void unSubscribe(final String topic) {
		try {
			mqttClient.unsubscribe(topic);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param topic
	 * @param payload
	 * @throws MqttException
	 * @throws MqttPersistenceException
	 * 
	 */
	public void publish(String topic, byte[] payload) throws MqttPersistenceException, MqttException {
		this.mqttClientSend.publish(topic, payload, qos, retained);
	}

	/**
	 * 
	 * @param callBack
	 */
	public void setMessageCallBack(final MqttCallback callBack) {
		this.mqttClient.setCallback(new MqttCallback() {

			@Override
			public void connectionLost(Throwable arg0) {
				callBack.connectionLost(arg0);
			}

			@Override
			public void deliveryComplete(IMqttDeliveryToken arg0) {

			}

			@Override
			public void messageArrived(String topic, MqttMessage message) {
				try {
					callBack.messageArrived(topic, message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			
		});
	}

	public String getIp() {
		return ip;
	}

	public Integer getPort() {
		return port;
	}
}
