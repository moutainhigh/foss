package com.deppon.foss.print.labelprint;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

public class LabelPrinterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PrintService service = getPrintService();
		String printStr = printTSCForm();
		printToService(service, printStr);
	}
	
	private static void printToService(PrintService printService,String printStr){
		DocFlavor   flavor   =   DocFlavor.INPUT_STREAM.AUTOSENSE;
		DocPrintJob job = printService.createPrintJob();
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		java.io.ByteArrayInputStream str=new java.io.ByteArrayInputStream(printStr.getBytes());
		Doc doc = new SimpleDoc(str, flavor, null);
		try {
			job.print(doc, pras); 
		} catch (PrintException pe) {
			pe.printStackTrace();
		}
	}
	
	private static PrintService getPrintService() {
		PrintService service = null;
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		PrintService[] pss = PrintServiceLookup.lookupPrintServices(flavor, pras);
		for(int i = 0; i < pss.length; i++ ) {
			String name = pss[i].getName();
			System.out.println(name);
			if(name.indexOf("datamax") > -1){
				service = pss[i];
				break;
			}
			if(name.indexOf("tsc") > -1) {
				service = pss[i];
				break;
			}
		}
		return service;
	}
	
	private static String printTSCForm(){
		StringBuffer buff = new StringBuffer();
		buff.append("SIZE 55 mm,90 mm"+"\n");
		buff.append("GAP 3 mm,0"+"\n");
		buff.append("SPEED 6"+"\n");
		buff.append("DENSITY 10"+"\n");
		buff.append("SET TEAR ON"+"\n");
		buff.append("CLS"+"\n");
		buff.append("TEXT 9*8,72,\"FONT001\",90,2,2,\"15\""+"\n");
		buff.append("TEXT 88+32,72,\"FONT001\",90,2,2,\"D02\""+"\n");
		buff.append("TEXT 40+32,22*8,\"FONT001\",90,2,2,\"102\""+"\n");
		buff.append("TEXT 88+32,22*8,\"FONT001\",90,2,2,\"D21\""+"\n");
		buff.append("BARCODE 54*8,27*8,\"128\",88,0,90,2,4,\"12345678\""+"\n");
		buff.append("TEXT 60+16,63*8,\"FONT001\",90,2,2,\"\""+"\n");
		buff.append("PRINT 1,1"+"\n");
//		buff.append(""+"\n");
//		buff.append(""+"\n");
//		buff.append(""+"\n");
//		buff.append(""+"\n");
//		buff.append(""+"\n");
//		buff.append(""+"\n");
//		buff.append(""+"\n");
//		buff.append(""+"\n");
//		buff.append(""+"\n");
//		buff.append(""+"\n");
//		buff.append(""+"\n");
		return buff.toString();
	}
	
	private static String printDataMaxForm(){
		StringBuffer buff = new StringBuffer();
		
		return buff.toString();
	}
	
}
