package com.idCard.info;

public class Termb {

	//初始化USB端口
	public static native int InitComm(int iPort);
	//关闭USB端口
	public static native int CloseComm();
	//验证身份证真假
	public static native int Authenticate();
	//读取身份证信息
	public static native int ReadContent(int act);
}
