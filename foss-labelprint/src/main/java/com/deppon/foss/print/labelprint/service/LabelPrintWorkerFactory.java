package com.deppon.foss.print.labelprint.service;

import com.deppon.foss.print.labelprint.LabelPrintWorker;
import com.deppon.foss.print.labelprint.util.ObjectCreator;


public class LabelPrintWorkerFactory {

	public static LabelPrintWorker createLabelPrintWorker(String lblprtworker){
		try {
			return (LabelPrintWorker)ObjectCreator.createObject(lblprtworker);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
