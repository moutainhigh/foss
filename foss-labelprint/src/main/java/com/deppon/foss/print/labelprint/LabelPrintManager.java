package com.deppon.foss.print.labelprint;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import com.deppon.foss.print.labelprint.service.DefaultSetupUtil;
import com.deppon.foss.print.labelprint.service.LabelPrintWorkerFactory;
import com.deppon.foss.print.labelprint.service.LblPrtServiceConst;
import com.deppon.foss.print.labelprint.util.PropertiesUtil;

public class LabelPrintManager {

	public static void doLabelPrintAction(String keyPrtPrg,
			LabelPrintContext lblCtx) throws Exception {
		DefaultSetupUtil defutil = DefaultSetupUtil.getInstance();

		String masterprter = defutil
				.loadSetDefaultValue(LblPrtServiceConst.key_set_default_local_printer_master);
		if(isLocalPrinterOK(masterprter)){
			lblCtx.setmDeviceName(masterprter);
		}
		else {
			String subprter = defutil
					.loadSetDefaultValue(LblPrtServiceConst.key_set_default_local_printer_sub);
			if(isLocalPrinterOK(subprter)){
				lblCtx.setmDeviceName(subprter);
			}
		}
		if(lblCtx.getmDeviceName()==null){
			throw new Exception ("没有找到准备就绪的默认打印机");
		}
		
		String prtertype = defutil
				.loadSetDefaultValue(LblPrtServiceConst.key_set_default_printer_type);
		if(prtertype==null){
			throw new Exception ("打印机类型设置不正确");
		}

		try{
			String left = defutil
					.loadSetDefaultValue(LblPrtServiceConst.key_set_default_printer_left);
	
			String top = defutil
					.loadSetDefaultValue(LblPrtServiceConst.key_set_default_printer_top);
	
			lblCtx.setPrtLeft(Integer.parseInt(left));
			lblCtx.setPrtTop(Integer.parseInt(top));
		}catch (Exception e) {
			throw new Exception ("打印机[上:左]偏移量设置不正确");
		}
		
		// set test env indicator 
		PropertiesUtil.initProperties();
		String testenvind = PropertiesUtil.get(LblPrtServiceConst.key_lblprt_testenv_indicator);
		lblCtx.setTestEnvInd(testenvind);

		String prgclz = LblPrtServiceConst.key_lblprt_program_package + prtertype + "." + keyPrtPrg;
		LabelPrintWorker workder = LabelPrintWorkerFactory
				.createLabelPrintWorker(prgclz);
		workder.executePrintProcess(lblCtx);

	}

	public static boolean isLocalPrinterOK(String prterName) throws Exception {

		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		PrintService[] pss = PrintServiceLookup.lookupPrintServices(flavor,pras);
		for (int i = 0; i < pss.length; i++) {
			String name = pss[i].getName();
			if(name.equals(prterName)){
				return true;
			}
		}
		return false;
	}
}
