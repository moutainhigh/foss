package com.deppon.foss.print.labelprint.test;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

public class Test {

	public static void main(String[] args) {
		String str1 = "FK\"DEPPON\"\n"              
				+"FS\"DEPPON\"\n"
				+"V00,40,N,\"\"\n"
				+"V01,20,N,\"\"\n"
				+"V02,20,N,\"\"\n"
				+"V03,20,N,\"\"\n"
				+"V04,10,N,\"\"\n"
				+"V05,10,N,\"\"\n"
				+"V06,20,N,\"\"\n"
				+"V07,20,N,\"\"\n"
				+"V08,20,N,\"\"\n"
				+"V09,18,N,\"\"\n"
				+"V10,10,N,\"\"\n"
				+"V11,10,N,\"\"\n"
				+"V12,10,N,\"\"\n"
				+"V13,10,N,\"\"\n"
				+"V14,10,N,\"\"\n"
				+"V15,10,N,\"\"\n"
				+"V16,10,N,\"\"\n"
				+"V17,10,N,\"\"\n"
				+"V18,10,N,\"\"\n"
				+"V19,10,N,\"\"\n"
				+"V20,20,N,\"\"\n"
				+"Q720,24\n"
				+"q440\n"
				+"S4\n"
				+"D7\n"
				+"JB\n"
				+"ZB\n"
				+"R180,0\n"
				+"X10,8,2,324,688\n"
				+"A432,30,1,8,1,1,N,\"德邦物流\"\n"
				+"LO394,16,3,160\n"
				+"B424,244,1,1,2,12,98,N,V00\n"
				+"A300,16,1,8,1,1,N,V01\n"
				+"A388,36,1,8,1,1,N,V02\n"
				+"A360,20,1,8,1,1,N,V03\n"
				+"A318,100,1,8,2,2,N,V04\n"
				+"A321,508,1,8,2,1,N,V05\n"
				+"A320,580,1,4,2,2,N,V06\n"
				+"A261,484,1,8,1,1,N,V07\n"
				+"A194,10,1,8,1,1,N,V08\n"
				+"A210,95,1,8,2,2,N,V09\n"
				+"A200,515,1,4,2,2,N,V10\n"
				+"A136,515,1,4,2,2,N,V11\n"
				+"A118,26,1,4,2,1,N,V12\n"
				+"A118,140,1,4,2,1,N,V13\n"
				+"A118,260,1,4,2,1,N,V14\n"
				+"A118,380,1,4,2,1,N,V15\n"
				+"A60,26,1,4,2,1,N,V16\n"
				+"A60,140,1,4,2,1,N,V17\n"
				+"A60,260,1,4,2,1,N,V18\n"
				+"A60,380,1,4,2,1,N,V19\n"
				+"A57,520,1,8,1,1,R,V20\n"
				+"LO226,8,3,680\n"
				+"LO120,8,3,472\n"
				+"LO65,8,3,680\n"
				+"LO10,126,110,3\n"
				+"LO10,244,110,3\n"
				+"LO10,362,110,3\n"
				+"LO10,480,314,3\n"
				+"FE\n";
		
		String str2 = "N\n"
				+"FR\"DEPPON\"\n"
				+"rY\n"
				+"?\n"
				+"102511110002000D020013\n"
				+" \n"
				+"045925\n"
				+"2012-10-25\n"
				+"上海\n"
				+"535\n"
				+"/ \n"
				+"3123\n"
				+"广州-\n"
				+"上海崇明\n"
				+"1025\n"
				+"1111\n"
				+"D01\n"
				+"D02\n"
				+" \n"
				+" \n"
				+"73\n"
				+"104\n"
				+" \n"
				+" \n"
				+"精准卡航\n"
				+"P1\n";
		
		try{
			Test t  = new Test();
			PrintService prt = t.findFirstPrintService("ZDesigner 888-TT");
			t.doPrintJob(prt,str1);
			t.doPrintJob(prt,str2);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public PrintService findFirstPrintService(String prt)
			throws Exception {
		
		PrintService service = null;
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		PrintService[] pss = PrintServiceLookup.lookupPrintServices(flavor,pras);
		for (int i = 0; i < pss.length; i++) {
			String name = pss[i].getName();
			if (name.equals("ZDesigner 888-TT")) {
				service = pss[i];
				break;
			}
		}
		if (service == null) {
			service = pss[0];
		}
		return service;
	}
	
	public void doPrintJob(PrintService pPrintService, String printStr)
			throws Exception {
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		DocPrintJob job = pPrintService.createPrintJob();
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		java.io.ByteArrayInputStream str = new java.io.ByteArrayInputStream(printStr.getBytes("GBK"));
		Doc doc = new SimpleDoc(str, flavor, null);
		job.print(doc, pras);
	}

	public static String convert(String str) {
		String tmp;
		StringBuffer sb = new StringBuffer(1000);
		char c;
		int i, j;
		sb.setLength(0);
		for (i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if (c > 255) {
				sb.append("\\u");
				j = (c >>> 8);
				tmp = Integer.toHexString(j);
				if (tmp.length() == 1)
					sb.append("0");
				sb.append(tmp);
				j = (c & 0xFF);
				tmp = Integer.toHexString(j);
				if (tmp.length() == 1)
					sb.append("0");
				sb.append(tmp);
			} else {
				sb.append(c);
			}

		}
		return (new String(sb));
	}

	private static String PREFIX = "//u";

	public static String ascii2Native(String str) {
		StringBuilder sb = new StringBuilder();
		int begin = 0;
		int index = str.indexOf(PREFIX);
		while (index != -1) {
			sb.append(str.substring(begin, index));
			sb.append(ascii2Char(str.substring(index, index + 6)));
			begin = index + 6;
			index = str.indexOf(PREFIX, begin);
		}
		sb.append(str.substring(begin));
		return sb.toString();
	}

	private static char ascii2Char(String str) {
		if (str.length() != 6) {
			throw new IllegalArgumentException(
					"Ascii string of a native character must be 6 character.");
		}
		if (!PREFIX.equals(str.substring(0, 2))) {
			throw new IllegalArgumentException("");
		}
		String tmp = str.substring(2, 4);
		int code = Integer.parseInt(tmp, 16) << 8;
		tmp = str.substring(4, 6);
		code += Integer.parseInt(tmp, 16);
		return (char) code;
	}
}
