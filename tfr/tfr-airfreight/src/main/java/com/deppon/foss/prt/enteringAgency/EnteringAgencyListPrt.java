/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/prt/enteringAgency/EnteringAgencyListPrt.java
 *  
 *  FILE NAME          :EnteringAgencyListPrt.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.prt.enteringAgency;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperPrintManager;
public class EnteringAgencyListPrt extends JFrame {
	
	private static final long serialVersionUID = -3330186421365799299L;

	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(new Dimension(ConstantsNumberSonar.SONAR_NUMBER_400,ConstantsNumberSonar.SONAR_NUMBER_400));
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args ){
		try{
			EnteringAgencyListPrt prt = new EnteringAgencyListPrt();
			JasperContext jctx = new JasperContext();
			jctx.setPrtkey("enteringAgency");
			JasperPrintManager jasperPrintManager = new JasperPrintManager(jctx);
			JasperPrint jp = jasperPrintManager.processPrintResult(jctx);
			JasperViewer jasperViewer = new JasperViewer(jp);
			prt.getContentPane().add((JComponent)jasperViewer.getComponent(0));
			prt.setSize(new Dimension(ConstantsNumberSonar.SONAR_NUMBER_700,ConstantsNumberSonar.SONAR_NUMBER_700));
			prt.show();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}