/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/prt/deliverbill/Deliverbill.java
 * 
 * FILE NAME        	: Deliverbill.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.prt.deliverbill;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperPrintManager;

public class Deliverbill extends JFrame {

	private static final long serialVersionUID = -1138059141451298827L;
	private static final Logger LOGGER = LoggerFactory.getLogger(Deliverbill.class);
	
	private static final int NUMBER_400 = 400;
	
	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(new Dimension(NUMBER_400,NUMBER_400));
	}
	
	public static void main(String[] args ){
		try{
			JasperContext jctx = new JasperContext();
			jctx.setPrtkey("deliverbill");
			jctx.put("deliverbillId", "test_deliver_no_001");
			JasperPrintManager jasperPrintManager = new JasperPrintManager(jctx);
			jasperPrintManager.processPrintResultInPreviewer(jctx);
		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}