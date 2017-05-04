package com.deppon.foss.print.labelprint.test;

import com.idCard.info.Termb;



public class TestIdCard {
	
	public static void main(String[] args){
		System.loadLibrary("termbjava"); 
		Termb.CloseComm();
		Termb.InitComm(1001);
		Termb.Authenticate();
		Termb.ReadContent(1);
		Termb.CloseComm();
	}
}
