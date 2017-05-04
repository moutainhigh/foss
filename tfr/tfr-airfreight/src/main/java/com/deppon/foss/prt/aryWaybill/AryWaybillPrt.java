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
 *  FILE PATH     : src/main/java/com/deppon/foss/prt/aryWaybill/AryWaybillPrt.java
 *  
 *  FILE NAME          :AryWaybillPrt.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  操作员打开界面：
 *  
1、	配载类型默认为全部；
2、	空运总调默认为当前操作人所在部门，根据用户数据权限可以修改，不可为空；
3、	制单起始时间默认为当天00:00:00，截止时间默认为当天23:59:59，不为空；
4、	目的站默认为空，输入范围为空运线路信息中的城市名称；
5、	航空公司默认为全部，来源于航空公司二字代码；
6、	正单号默认为空；
7、	默认不执行查询操作；

 *  点击查询按钮，执行查询操作，默认按制单时间降序排列；
点击重置按钮，重置查询条件为默认值；
点击操作列中的编辑控件，可对正单进行修改，
修改条件：
1、	操作员有修改正单权限；
2、	未收银的正单可以修改，判断正单是否收银；
3、	正单新增部门必须为本部门；

 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.prt.aryWaybill;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperPrintManager;
public class AryWaybillPrt extends JFrame {
	
	private static final long serialVersionUID = -3330186421365799299L;

	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(new Dimension(ConstantsNumberSonar.SONAR_NUMBER_400,ConstantsNumberSonar.SONAR_NUMBER_400));
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args ){
		try{
			AryWaybillPrt prt = new AryWaybillPrt();
			
			JasperContext jctx = new JasperContext();
			jctx.setPrtkey("aryWaybill");
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