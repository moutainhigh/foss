package com.deppon.foss.module.boot.client.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 获取当前操作系统信息
 * 
 * @author 272311 sangwenhao 2015-11-24
 */
public class OSUtil {

	public static final Logger logger = LoggerFactory.getLogger(OSUtil.class);
	
	public static InetAddress getLocalIP() throws UnknownHostException{
		// 得到IP，输出SH-272311/10.224.68.116
		return InetAddress.getLocalHost();
	}
	public static String getLocalMac(InetAddress inetAddress) throws SocketException {
		logger.info("传入的IP地址："+inetAddress);
		StringBuffer sb = new StringBuffer("");
		// 获取网卡地址
		byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
		for (int i = 0; i < mac.length; i++) {
			if (i != 0) {
				sb.append("-");
			}
			// 字节转换为整数
			int temp = mac[i] & 0xff;
			String str = Integer.toHexString(temp);
			if (str.length() == 1) {
				sb.append("0" + str);
			} else {
				sb.append(str);
			}
		}
		logger.info("mac地址："+sb.toString());
		return sb.toString();
	}
}
