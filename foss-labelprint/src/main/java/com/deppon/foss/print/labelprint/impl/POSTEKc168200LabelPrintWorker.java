package com.deppon.foss.print.labelprint.impl;

import java.text.SimpleDateFormat;

import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.print.labelprint.LabelPrintWorker;

public class POSTEKc168200LabelPrintWorker extends LabelPrintWorker {

    //左边距
    private Integer leftDistance = new Integer("130");
    //上边距
    private Integer topDistance = new Integer("0");
    //送货情况,分为"送",不送货则为"",如果是送货时,会打印个"送"字
    private String deliver = "\t";    
    // 指令指示黑底白字
    private String command = "";
    
	@Override
	public void prepareLabelPrintForm(LabelPrintContext lblPrintContext)
			throws Exception {
		lblPrintContext.setPrintForm(getPostekPrintForm(lblPrintContext.getLabelPrintInfo()));
		lblPrintContext.setPrintFormParam(getPostekPrintParam(lblPrintContext, lblPrintContext.getLabelPrintInfo()));
	}

	@Override
	public void prepareLabelPrintService(LabelPrintContext lblPrintContext)
			throws Exception {

	}

	@Override
	public void beforeExecuteLabelPrint(LabelPrintContext lblPrintContext)
			throws Exception {

	}

	private String getPostekPrintForm(LabelPrintInfo labelPrintInfo)
    {
    	//控制左边距变量
    	int moveLeft = this.leftDistance.intValue();
    	//控制上边距变量
		int moveTop = this.topDistance.intValue();
		// 设置表名称
		String depponForm = "FK\"DEPPON\"" + '\r' + '\n' 
				+ "FS\"DEPPON\""+ '\r' + '\n' 
				+ "V0,40,N,\"\"" + '\r' + '\n'
				+ "V1,20,N,\"\"" + '\r' + '\n'
				+ "V2,20,N,\"\"" + '\r' + '\n'
				+ "V3,20,N,\"\"" + '\r' + '\n'
				+ "V4,20,N,\"\"" + '\r' + '\n'
				+ "V5,20,N,\"\"" + '\r' + '\n'
				+ "V6,20,N,\"\"" + '\r' + '\n'
				+ "V7,20,N,\"\"" + '\r' + '\n'
				+ "V8,20,N,\"\"" + '\r' + '\n'
				+ "V9,20,N,\"\"" + '\r' + '\n'
				+ "V10,20,N,\"\"" + '\r' + '\n'
				+ "V11,20,N,\"\"" + '\r' + '\n'
				+ "V12,20,N,\"\"" + '\r' + '\n'
				+ "V13,20,N,\"\"" + '\r' + '\n'
				+ "V14,30,N,\"\"" + '\r' + '\n'
				+ "V15,20,N,\"\"" + '\r' + '\n'
				+ "V16,20,N,\"\"" + '\r' + '\n'
				+ "V17,20,N,\"\"" + '\r' + '\n'
				+ "V18,30,N,\"\"" + '\r' + '\n'
				+ "V19,20,N,\"\"" + '\r' + '\n'
				+ "V20,20,N,\"\"" + '\r' + '\n'
				+ "V21,40,N,\"\"" + '\r' + '\n'
				+ "V22,20,N,\"\"" + '\r' + '\n'   
				; 
		//打印机设置
		String headStr = "Q440,16" + '\r' + '\n' 
		+ "q760" + '\r' + '\n'
		+ "S4" + '\r' + '\n'//速度
		+ "H10" + '\r' + '\n' //温度
		+ "JB" + '\r' + '\n' //正反
		+ "ZB" + '\r' + '\n';//吐纸
        //要打印的字符串
		StringBuffer printStr = new StringBuffer(
			// 外框
			"X" + (43 + moveLeft) + "," + (84+moveTop) + ",2," + (675+moveLeft) + "," + (383+moveTop) + '\r' + '\n'
			+ "T"+(45+moveLeft)+","+(88+moveTop)+",0,6,2,1,N,\"地\"" + '\r' + '\n'
			+ "T"+(45+moveLeft)+","+(118+moveTop)+",0,6,2,1,N,\"区\"" + '\r' + '\n'
			// 第一根横线
			+"LO" + (43+moveLeft) + "," + (150+moveTop) + ",632,2" + '\r' + '\n'
			+ "T"+(45+moveLeft)+","+(155+moveTop)+",0,6,2,1,N,\"编\"" + '\r' + '\n'
			+ "T"+(45+moveLeft)+","+(185+moveTop)+",0,6,2,1,N,\"号\"" + '\r' + '\n'
			// 第二根横线
			+"LO" + (43+moveLeft) + "," + (216+moveTop) + ",632,2" + '\r' + '\n'
			+ "T"+(50+moveLeft)+","+(274+moveTop)+",0,6,2,1,N,\"件\"" + '\r' + '\n'
			+ "T"+(50+moveLeft)+","+(304+moveTop)+",0,6,2,1,N,\"数\"" + '\r' + '\n'
			// 第三根横线
			+"LO" + (43+moveLeft) + "," + (270+moveTop) + ",632,2" + '\r' + '\n'
			+ "T"+(50+moveLeft)+","+(220+moveTop)+",0,6,1,2,N,\"目的\"" + '\r' + '\n'
			// 第四根横线
			+"LO" + (43+moveLeft) + "," + (330+moveTop) + ",632,2" + '\r' + '\n'
			
			// 第一根竖线
			+"LO"+(95+moveLeft)+","+(86+moveTop)+",2,130"+'\r' + '\n'
			// 第二根竖线
			+"LO"+(211+moveLeft)+","+(86+moveTop)+",2,130"+'\r' + '\n'
			// 第三根竖线
			+"LO"+(316+moveLeft)+","+(86+moveTop)+",2,130"+'\r' + '\n'
			// 第四根竖线
			+"LO"+(433+moveLeft)+","+(86+moveTop)+",2,130"+'\r' + '\n'
			// 第五根竖线
			+"LO"+(549+moveLeft)+","+(86+moveTop)+",2,130"+'\r' + '\n'
			+ "T"+(318+moveLeft)+","+(274+moveTop)+",0,6,2,1,N,\"单\"" + '\r' + '\n'
			+ "T"+(318+moveLeft)+","+(304+moveTop)+",0,6,2,1,N,\"号\"" + '\r' + '\n'
			// 第六根竖线
			+"LO"+(95+moveLeft)+","+(270+moveTop)+",2,60"+'\r' + '\n'
			// 第七根竖线
			+"LO"+(317+moveLeft)+","+(270+moveTop)+",2,60"+'\r' + '\n'
			// 第八根竖线
			+"LO"+(369+moveLeft)+","+(270+moveTop)+",2,60"+'\r' + '\n'
			// 第九根竖线
			+"LO"+(125+moveLeft)+","+(218+moveTop)+",2,53"+'\r' + '\n'
			+ "T"+(49+moveLeft)+","+(335+moveTop)+",0,6,1,2,N,\"收货人\"" + '\r' + '\n'
			// 第十根竖线
			+"LO"+(149+moveLeft)+","+(332+moveTop)+",2,52"+'\r' + '\n'
			// 第十一根竖线
			+"LO"+(395+moveLeft)+","+(218+moveTop)+",2,53"+'\r' + '\n'
			+ "T"+(400+moveLeft)+","+(220+moveTop)+",0,6,1,2,N,\"包装\"" + '\r' + '\n'
			// 第十二根竖线
			+"LO"+(455+moveLeft)+","+(218+moveTop)+",2,53"+'\r' + '\n'						
			// 最上面中文与英文的分隔线
			+"LO"+(40+moveLeft)+","+(51+moveTop)+",112,3" + '\r' + '\n'
			+ "T"+(43+moveLeft)+","+(15+moveTop)+",0,4,1,1,N,\""+KEY_DPWL_CN+"\"" + '\r' + '\n'
			+ "T"+(43+moveLeft)+","+(60+moveTop)+",0,4,1,1,N,\"DEPPON\"" + '\r' + '\n'
			// 条行码
			+"B"+(220+moveLeft)+","+(15+moveTop)+",0,1,2,2,60,N,V0" + '\r' + '\n');
			// 地区1
		    if(labelPrintInfo.getLocationOne().length()>2)
		    {
		    	printStr.append("T"+(102+moveLeft)+","+(90+moveTop)+",0,4,1,2,N,V1" + '\r' + '\n');
		    }
		    else
		    {
		    	printStr.append("T"+(102+moveLeft)+","+(90+moveTop)+",0,4,2,2,N,V1" + '\r' + '\n');
		    }
			// 地区2
		    if(labelPrintInfo.getLocationTwo().length()>2)
		    {
		    	printStr.append("T"+(215+moveLeft)+","+(90+moveTop)+",0,4,1,2,N,V2" + '\r' + '\n' );
		    }
		    else
		    {
		    	printStr.append("T"+(215+moveLeft)+","+(90+moveTop)+",0,4,2,2,N,V2" + '\r' + '\n' );
		    }
		    //地区3
		    if(labelPrintInfo.getLocationThree().length()>2)
		    {
		    	printStr.append("T"+(321+moveLeft)+","+(90+moveTop)+",0,4,1,2,N,V3" + '\r' + '\n');
		    }
		    else
		    {
		    	printStr.append("T"+(321+moveLeft)+","+(90+moveTop)+",0,4,2,2,N,V3" + '\r' + '\n');
		    }
		    //地区4
		    if(labelPrintInfo.getLocationFour().length()>2)
		    {
		    	printStr.append("T"+(437+moveLeft)+","+(90+moveTop)+",0,4,1,2,N,V4" + '\r' + '\n');
		    }
		    else
		    {
		    	printStr.append("T"+(437+moveLeft)+","+(90+moveTop)+",0,4,2,2,N,V4" + '\r' + '\n');
		    }
		    //地区5
		    if(labelPrintInfo.getLocationFive().length()>2)
		    {
		    	printStr.append("T"+(653+moveLeft)+","+(90+moveTop)+",0,4,1,2,N,V5" + '\r' + '\n');
		    }
		    else
		    {
		    	printStr.append("T"+(653+moveLeft)+","+(90+moveTop)+",0,4,2,2,N,V5" + '\r' + '\n');
		    }
			
			// 编号1
			printStr.append("T"+(100+moveLeft)+","+(160+moveTop)+",0,4,2,2,N,V6" + '\r' + '\n' 
			// 编号2
			+ "T"+(205+moveLeft)+","+(160+moveTop)+",0,4,2,2,N,V7" + '\r' + '\n' 
			// 编号3
			+ "T"+(321+moveLeft)+","+(160+moveTop)+",0,4,2,2,N,V8" + '\r' + '\n' 
			// 编号4
			+ "T"+(437+moveLeft)+","+(160+moveTop)+",0,4,2,2,N,V9" + '\r' + '\n'
			// 编号5
			+ "T"+(553+moveLeft)+","+(160+moveTop)+",0,4,2,2,N,V10" + '\r' + '\n' 
			// 件数
			+ "T"+(102+moveLeft)+","+(274+moveTop)+",0,4,2,2,N,V11" + '\r' + '\n' 
			// 第？件
			+ "T"+(225+moveLeft)+","+(304+moveTop)+",0,4,1,1,N,V12" + '\r' + '\n' 
			// 单号
			+ "T"+(383+moveLeft)+","+(284+moveTop)+",0,4,2,2,N,V13" + '\r' + '\n' 
			// 是否送货
			+ "T"+(155+moveLeft)+","+(335+moveTop)+",0,4,1,2,N,V14" + '\r' + '\n' 
			// 异形
			+ "T"+(585+moveLeft)+","+(335+moveTop)+",0,4,1,2,N,V15" + '\r' + '\n' 
			// 目的地
			+ "T"+(133+moveLeft)+","+(220+moveTop)+",0,4,1,2,N,V16" + '\r' + '\n' 
			// 包装
			+ "T"+(473+moveLeft)+","+(220+moveTop)+",0,4,1,2,N,V17" + '\r' + '\n' 
			// 本部门
			+ "T"+(45+moveLeft)+","+(384+moveTop)+",0,4,1,1,N,V18" + '\r' + '\n' 
			
			// 日期
			+ "T"+(45+moveLeft)+","+(416+moveTop)+",0,4,1,1,N,V19" + '\r' + '\n' 
			// 运输类型
			+ "T"+(473+moveLeft)+","+(384+moveTop)+",0,4,1,2," + command + ",V20" + '\r' + '\n' 
			// 左方条码
			+ "B"+(730+moveLeft)+","+(30+moveTop)+",1,1,2,1,45,N,V21" + '\r' + '\n');
			//收货人
		    if("".equals(this.deliver.trim())){
		    	printStr.append("T"+(155+moveLeft)+","+(335+moveTop)+",0,4,1,2,N,V22" + '\r' + '\n');
		    }
		    else{
		    	printStr.append("T"+(245+moveLeft)+","+(335+moveTop)+",0,4,1,2,N,V22" + '\r' + '\n');
		    }
		    //框架结束
			printStr.append("FE" + '\r' + '\n') ;
		
		return depponForm+headStr+printStr.toString();
    }
	
	private String getPostekPrintParam(LabelPrintContext lblPrintContext, LabelPrintInfo labelPrintInfo) throws Exception 
    {
    	//设置打印日期格式
    	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    	//打印指令
		StringBuffer parameter=new StringBuffer();
		parameter.append(
				//清除图像缓存
				"N"+'\r'+'\n' 
				);
		
		int printIndexFrom = labelPrintInfo.getStartPieceIndex();
		int printIndexEnd = labelPrintInfo.getEndPieceIndex();
		
        // 获得打印件数
        int pieces = lblPrintContext.getPrintPiece();

		//为件数前加"0"
		for(int i = printIndexFrom,j = 0;i <= printIndexEnd;i ++,j ++)
		{
			//获得四位唯一标识符
			String str = String.valueOf(j + 1 + pieces);
			String serial_no = getDigit(str,4);
			//条码前加"D"
			String barcode = labelPrintInfo.getNumber() + serial_no + labelPrintInfo.getWeight() + labelPrintInfo.getFinalCode() + labelPrintInfo.getDestCode();
			parameter.append(
					"FR\"DEPPON\""+'\r'+'\n'
					+"?"+'\r'+'\n'
					+ barcode +'\r'+'\n'
					// 地区1
					+ labelPrintInfo.getLocationOne() +'\r'+'\n'
					// 地区2
					+ labelPrintInfo.getLocationTwo() +'\r'+'\n'
					// 地区3
					+ labelPrintInfo.getLocationThree() +'\r'+'\n'
					// 地区4
					+ labelPrintInfo.getLocationFour() +'\r'+'\n'
					// 地区5
					+ labelPrintInfo.getLocationFive() +'\r'+'\n'
					// 编号1
					+ labelPrintInfo.getStoreOneNum() +'\r'+'\n'
					// 编号2
					+ labelPrintInfo.getStoreTwoNum() +'\r'+'\n'
					// 编号3
					+ labelPrintInfo.getStoreThreeNum() +'\r'+'\n'
					// 编号4
					+ labelPrintInfo.getStoreFourNum() +'\r'+'\n'
					// 编号5
					+ labelPrintInfo.getStoreFiveNum() +'\r'+'\n'
					// 件数
					+ labelPrintInfo.getPieces() +'\r'+'\n'
					// 第？件
					+ "第"+i+"件" +'\r'+'\n'
					// 单号
					+ labelPrintInfo.getNumber() + '\r'+'\n'
					// 送货否？
					+ deliver + '\r'+'\n'
					// 异形货物
					+ labelPrintInfo.getGoodsType() +'\r'+'\n'
					// 目的地
					+ labelPrintInfo.getDestination() +'\r'+'\n'
					// 包装
					+ labelPrintInfo.getPacking() +'\r'+'\n'
					// 本部门
					+ labelPrintInfo.getUserName() + "*" + labelPrintInfo.getCurrentDept() +'\r'+'\n'
					// 日期
					+ format.format(new java.util.Date()) +'\r'+'\n'
					// 运输类型
					+ labelPrintInfo.getTransProperty() +'\r'+'\n'
					// 左方条码
					+ barcode +'\r'+'\n'
					// 收货人
					+ labelPrintInfo.getConsignee() + '\r'+'\n' 
					 //打印1份
					+"W1"+'\r'+'\n' 
					);
			
		}
		return parameter.toString();
    }
}
