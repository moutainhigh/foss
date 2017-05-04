package com.deppon.foss.print.labelprint;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import com.deppon.foss.print.labelprint.util.Log;

public abstract class LabelPrintWorker {
	
	public static final String KEY_DPWL_CN = "德邦物流";

	public void executePrintProcess(LabelPrintContext lblPrintContext)
			throws Exception {
		
		prepareLabelPrintForm(lblPrintContext);
		
		Log.debug("PrepareLabelPrintForm Success:"+lblPrintContext.getPrintForm());
		Log.debug("PrepareLabelPrintFormParam Success:"+lblPrintContext.getPrintFormParam());

		prepareLabelPrintService(lblPrintContext);

		PrintService _prtservice = findFirstPrintService(lblPrintContext);
		lblPrintContext.setmPrintService(_prtservice);

		executeLabelPrint(lblPrintContext);
	}

	public abstract void prepareLabelPrintForm(LabelPrintContext lblPrintContext)
			throws Exception;

	public abstract void prepareLabelPrintService(
			LabelPrintContext lblPrintContext) throws Exception;

	public abstract void beforeExecuteLabelPrint(
			LabelPrintContext lblPrintContext) throws Exception;

	public PrintService findFirstPrintService(LabelPrintContext lblPrintContext)
			throws Exception {
		if (lblPrintContext.getmDeviceName() == null) {
			throw new Exception("[No Print Service Name setting ]");
		}
		PrintService service = null;
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		PrintService[] pss = PrintServiceLookup.lookupPrintServices(flavor,
				pras);
		for (int i = 0; i < pss.length; i++) {
			String name = pss[i].getName();
			if (name.equals(lblPrintContext.getmDeviceName())) {
				service = pss[i];
				break;
			}
		}
		if (service == null) {
			service = pss[0];
		}
		return service;
	}

	public void executeLabelPrint(LabelPrintContext lblPrintContext)
			throws Exception {
		try {
			beforeExecuteLabelPrint(lblPrintContext);

			PrintService _printService = lblPrintContext.getmPrintService();
			if (_printService == null) {
				throw new Exception("No Print Service Set ");
			}

			String strPrintFormStr = lblPrintContext.getPrintForm();
			if (strPrintFormStr == null) {
				throw new Exception("No Print Form String Set ");
			}

			doPrintJob(_printService, lblPrintContext.getPrintForm());
			if (lblPrintContext.getPrintFormParam() != null) {
				doPrintJob(_printService, lblPrintContext.getPrintFormParam());
			}

		} catch (Exception e) {
			Log.info("executeLabelPrint Error: "+ e.toString() ,e );
			throw e;
		}
	}

	protected void doPrintJob(PrintService pPrintService, String printStr)
			throws Exception {
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		DocPrintJob job = pPrintService.createPrintJob();
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		java.io.ByteArrayInputStream str = new java.io.ByteArrayInputStream(printStr.getBytes("GBK"));
		Doc doc = new SimpleDoc(str, flavor, null);

		job.print(doc, pras);
	}

	public boolean isNull(String str){
		if(str==null || "".equals(str) || "null".equalsIgnoreCase(str) || " ".equals(str)){
			return true;
		}
		return false;
	}	
	
	protected String getDigit(String weight, int digit) throws Exception {
		StringBuffer sb = new StringBuffer("");
		if (weight != null) {
			int length = weight.length();
			sb.append(weight);
			if (length < digit) {
				for (int i = length; i < digit; i++) {
					sb.insert(0, "0");
				}
			} else if (length > digit) {
				sb = new StringBuffer(sb.substring(0, digit));
			}

		} else {
			throw new Exception("getDigit, @param: weight is null");
		}
		return sb.toString();
	}

}
