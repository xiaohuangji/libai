package com.dajie.wika.push.client;

import java.util.Date;

import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dajie.wika.constants.returncode.ResultCodeConstant;
import com.dajie.wika.model.Message;
import com.dajie.wika.model.UserDevice;

public class ApplePushClient implements PushClient {
	/**
	 * Logger for this class
	 */
	private Logger logger = LoggerFactory.getLogger(ApplePushClient.class);

	private static ApplePushClient pushClient = null;

	public final String PASSWORD = "123456";

	private static final String apnsKey = "APS_Weika_Production.p12";
	private static final String sanbox_apnsKey = "APS_Weika_dev.p12";

	public static final String PUSH_CERTIFICATE_FILE_PATH_DEV = Thread
			.currentThread().getContextClassLoader().getResource("").getPath()
			+ apnsKey;

	public static final String SANBOX_PUSH_CERTIFICATE_FILE_PATH_DEV = Thread
			.currentThread().getContextClassLoader().getResource("").getPath()
			+ sanbox_apnsKey;

	private static PushNotificationManager pushManager = new PushNotificationManager();

	private static PushNotificationManager sanbox_pushManager = new PushNotificationManager();

	public static ApplePushClient getInstance() {
		if (pushClient == null) {
			pushClient = new ApplePushClient();
		}
		return pushClient;
	}

	private ApplePushClient() {
		try {
			pushManager
					.initializeConnection(new AppleNotificationServerBasicImpl(
							PUSH_CERTIFICATE_FILE_PATH_DEV, PASSWORD, true));
			sanbox_pushManager
					.initializeConnection(new AppleNotificationServerBasicImpl(
							SANBOX_PUSH_CERTIFICATE_FILE_PATH_DEV, PASSWORD,
							false));
		} catch (CommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeystoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public int pushone(UserDevice userDevice, Message message,
			int unreadMsgCount) {
		// TODO Auto-generated method stub
		if (null == userDevice.getDeviceToken()
				|| "".equals(userDevice.getDeviceToken())) {
			return ResultCodeConstant.OP_FAIL;
		}
		PushNotificationPayload payload = new PushNotificationPayload();
		try {

			// basic info这里组装显示的push
			payload.addAlert(message.getContent()); // 消息内容
			payload.addSound("default"); // 声音提示文件
			payload.addBadge(unreadMsgCount); // iphone应用图标上小红圈上的数值

			// custom info
			payload.addCustomDictionary("type", message.getType());
			payload.addCustomDictionary("fromId", message.getFromId());
			payload.addCustomDictionary("createTime",
					String.valueOf(message.getCreateTime()));
			payload.addCustomDictionary("id", String.valueOf(message.getId()));

			int Maxlength = payload.getMaximumPayloadSize();
			int length = payload.getPayloadSize();

			// push长度限制
			if (length <= Maxlength) {

				// 设备
				Device device = new BasicDevice();
				device.setToken(userDevice.getDeviceToken());

				PushedNotification NotificationResult = pushManager
						.sendNotification(device, payload, false);

				PushedNotification sanboxResult = sanbox_pushManager
						.sendNotification(device, payload, false);

				if (NotificationResult.isSuccessful()) {
//					 logger.debug("ios push message success.");
				} else {
					logger.warn("ios push message failed.");
					logger.warn("product channel: "+NotificationResult.toString());
				}

				if (sanboxResult.isSuccessful()) {
//					logger.debug("ios push message success.");
				} else {
					logger.warn("ios push message failed in sanbox");
					logger.warn("sandbox channel: "+sanboxResult.toString());
				}

			} else {
				logger.error("Can't send push message success for total payload size beyond maxSize:256");
			}
		} catch (CommunicationException e) {
			logger.error("ios push communicationException:", e);
		} catch (KeystoreException e) {
			logger.error("ios push keystoreException:", e);
		} catch (Exception e) {
			logger.error("ios push exception:", e);
		}

		return ResultCodeConstant.OP_SUCC;
	}

	public static void main(String[] args) {
		UserDevice userDevice=new UserDevice(66, "0bf3d3bc1c03cd447aacd1b4c05a4eced1e6fed84e628ee446ee8f65587f1bed", 1);
		Message message=new Message(0,66,68);
		message.setContent("测试消息");
		message.setCreateTime(new Date());
		ApplePushClient client=new ApplePushClient();
		System.out.println(client.pushone(userDevice, message, 1));
	}
}
