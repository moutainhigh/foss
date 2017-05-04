package com.deppon.foss.print.labelprint.impl;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

public class PrinterZebra888 {
	
    private Integer leftDistance = new Integer("200");
    
    private Integer topDistance = new Integer("35");

	private String getNewPrintForm() throws Exception 
	{	
		int moveLeft = this.leftDistance.intValue();    //200
		
		int moveTop = this.topDistance.intValue()+85;	   //35
		
		String depponForm = "\n" + "FK\"DEPPON\"" + "\n" 
				+ "FS\"DEPPON\""+ "\n" 
				+ "V00,40,N,\"\"" + "\n"
				+ "V01,20,N,\"\"" + "\n"
				;
		
		StringBuffer form = new StringBuffer();
		form.append("Q720,24\n" );
		form.append("q400\n" );
		form.append("S4\n");
		form.append("D10\n");
		form.append("JB\n");
		form.append("ZB\n");
		form.append("R0,0\n");

		StringBuffer printStr = new StringBuffer(	
				
				"A" + (85 + moveLeft) + "," + (125 + moveTop)
						+ ",0,6,2,1,N,V00"+"\n"
				
				+"A" + (85 + moveLeft) + "," + (50+ moveTop)
						+ ",0,6,2,1,N,V01"+"\n"
		);

		printStr.append("FE" + "\n") ;
		return depponForm+form.toString()+printStr.toString();
	}
	
	class LabelInfo {
		String name = null;
		String number = null;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PrinterZebra888 pt = new PrinterZebra888();
		LabelInfo la = pt.new LabelInfo();
		la.setName("908214");
		la.setNumber("908211");
		try {
			pt.printByZebra(la);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
    private void printByZebra(LabelInfo labelInfo) throws Exception {
    	PrintService service = PrintServiceLookup.lookupDefaultPrintService();	
		printByZebra(service,getNewPrintForm());
		printByZebra(service,getNewPrintParam(labelInfo));
    }

	public void printByZebra(PrintService printService,String printStr){
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
    
    private String getNewPrintParam(LabelInfo labelPrintInfo) throws Exception
    {
		StringBuffer parameter = new StringBuffer();
		parameter.append(
				"N"+"\n" 
				+"FR\"DEPPON\""+"\n"
				+"rY" +"\n"
				+"D5\n"  
				);
		parameter.append(
					"?"+"\n"
					+ labelPrintInfo.getName() +"\n"
					+ labelPrintInfo.getNumber()+"\n"

					);
		parameter.append("P1"+"\n");
	
		return parameter.toString();
    }

}
