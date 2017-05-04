package com.deppon.foss.print.labelprint.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.print.labelprint.LabelPrintWorker;

public class GODEXOldLabelPrintWorker extends LabelPrintWorker {

	// 左边距
	private Integer leftDistance = new Integer("130");
	// 上边距
	private Integer topDistance = new Integer("0");

	// 送货情况,分为"送",不送货则为"",如果是送货时,会打印个"送"字
	String deliver = "\t";
	// 包装
	String packing = "\t";

	@Override
	public void prepareLabelPrintForm(LabelPrintContext lblPrintContext)
			throws Exception {
		/*
		lblPrintContext.setPrintForm(printNewLabel(lblPrintContext,
				lblPrintContext.getLabelPrintInfo()));
				*/
	}

	@Override
	public void prepareLabelPrintService(LabelPrintContext lblPrintContext)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeExecuteLabelPrint(LabelPrintContext lblPrintContext)
			throws Exception {
		// TODO Auto-generated method stub

	}
	
	private int addLength(int stringLength, int length, int returnLength) {
		if (returnLength > 0) {
			if (stringLength >= length)
				return 0;
		}
		return returnLength;
	}
	
	private void printNewLabel(LabelPrintContext lblPrintContext,
			LabelPrintInfo labelPrintInfo) throws Exception {
		

		if (labelPrintInfo.isIsUnusual()) {
			packing += "【异】";
		}

		int printIndexFrom = labelPrintInfo.getStartPieceIndex();
		int printIndexEnd = labelPrintInfo.getEndPieceIndex();
		String beginPieces = "" + printIndexFrom;
		if (beginPieces.length() == 1) {
			beginPieces = "000" + beginPieces;
		} else if (beginPieces.length() == 2) {
			beginPieces = "00" + beginPieces;
		} else if (beginPieces.length() == 3) {
			beginPieces = "0" + beginPieces;
		}

		BigDecimal printPiece = new BigDecimal(printIndexEnd - printIndexFrom);

		// 获得打印件数
		int pieces = lblPrintContext.getPrintPiece();

		String str = String.valueOf(1 + pieces);
		String serial_no = getDigit(str, 4);
		
    	ecWebBridge  bridge = new ecWebBridge("ezweb");
    	System.out.println(bridge.getDriverExists());
    	System.out.println(bridge.getDriverLoaded());
    	boolean bool= bridge.openPort(lblPrintContext.getmDeviceName()) ;//打开端口
    	
		bridge.sendCommand("^Y96,N,8,1") ;   //端口设置
		bridge.beginJob(55,3,2,0,2,0) ;//开始工作
		bridge.sendCommand("~MDEL");//清除内存
		bridge.sendCommand("^L");
		
		bridge.sendCommand("C0," + serial_no + ",+1,A1");
		bridge.sendCommand("^E10");//    &&出纸位置
		bridge.sendCommand("^R" + leftDistance);//   左边距
		bridge.sendCommand("^P" + printPiece.add(new BigDecimal(1)));//打印页数
		bridge.sendCommand("^H10");//颜色浓度
		bridge.sendCommand("^Q55,5");//
		bridge.sendCommand("Q900,550");//标签长度
		
		bridge.ecTextOutR(50,10,30,"黑体", "德邦物流",0,40,0);
		bridge.sendCommand("Le,50,47,175,49");//直线;
		bridge.ecTextOutR(60,50,30,"黑体", "DEPPON",0,50,0);
		
		bridge.sendCommand("BU,260,9,3,9,60,0,0,D" + labelPrintInfo.getNumber() + "^C0"); //右上角条码
		bridge.sendCommand("BU,20,90,2,9,50,1,0,D" + labelPrintInfo.getNumber() + "^C0"); //左下角条码
		
		bridge.sendCommand("R24,80,660,360,3,3");//矩形
		bridge.sendCommand("Le,24,140,660,142");//第一条直线
		bridge.sendCommand("Le,24,195,660,197");//第二条直线
		bridge.sendCommand("Le,24,245,660,247");//第三条直线
		bridge.sendCommand("Le,24,310,660,312");//第四条直线
		
		bridge.sendCommand("Le,81,80,83,197");//第一条竖线
		bridge.sendCommand("Le,193,80,195,197");//第二条竖线
		bridge.sendCommand("Le,305,80,307,197");//第三条竖线
		bridge.sendCommand("Le,417,80,419,197");//第四条竖线
		bridge.sendCommand("Le,529,80,531,197");//第五条竖线
		
		bridge.sendCommand("Le,81,247,83,312");//第六条竖线
		bridge.sendCommand("Le,305,247,307,312");//第七条竖线
		bridge.sendCommand("Le,367,247,369,312");//第八条竖线
		bridge.sendCommand("Le,104,195,106,247");//第九条竖线
		bridge.sendCommand("Le,385,195,387,247");//第十条竖线
		bridge.sendCommand("Le,457,195,459,247");//第十一条竖线
		bridge.sendCommand("Le,128,310,130,360");//第十二条竖线
		
		bridge.ecTextOutR(40,88,25,"宋体", "地",0,40,0);
		bridge.ecTextOutR(40,113,25,"宋体", "区",0,40,0);
		bridge.ecTextOutR(40,145,25,"宋体", "编",0,40,0);
		bridge.ecTextOutR(40,170,25,"宋体", "号",0,40,0);
		bridge.ecTextOutR(40,250,25,"宋体", "件",0,40,0);
		bridge.ecTextOutR(40,280,25,"宋体", "数",0,40,0);
		bridge.ecTextOutR(320,250,25,"宋体", "单",0,40,0);
		bridge.ecTextOutR(320,280,25,"宋体", "号",0,40,0);
		bridge.ecTextOutR(32,202,35,"宋体", "目的",0,40,0);
		bridge.ecTextOutR(384,202,35,"宋体", "包装",0,40,0);
		bridge.ecTextOutR(40,320,30,"宋体", "收货人",0,40,0);
		
		
		bridge.ecTextOutR(97,85,60,"黑体", labelPrintInfo.getLocationOne(),15,50,0); // 地区1
		bridge.ecTextOutR(209,85,60,"黑体", labelPrintInfo.getLocationTwo(),15,50,0); // 地区2
		bridge.ecTextOutR(311,85,60,"黑体", labelPrintInfo.getLocationThree(),15,50,0); // 地区3
		bridge.ecTextOutR(433,85,60,"黑体", labelPrintInfo.getLocationFour(),15,50,0); // 地区4
		bridge.ecTextOutR(545,85,60,"黑体", labelPrintInfo.getLocationFive(),15,50,0); // 地区5
		bridge.ecTextOutR(97,140,70,"黑体", labelPrintInfo.getStoreOneNum(),25,70,0); // 编码1
		bridge.ecTextOutR(209,140,70,"黑体", labelPrintInfo.getStoreTwoNum(),25,70,0); // 编码2
		bridge.ecTextOutR(311,140,70,"黑体", labelPrintInfo.getStoreThreeNum(),25,70,0); // 编码3
		bridge.ecTextOutR(433,140,70,"黑体", labelPrintInfo.getStoreFourNum(),25,70,0); // 编码4
		bridge.ecTextOutR(545,140,70,"黑体", labelPrintInfo.getStoreFiveNum(),25,70,0); // 编码5
		
		/*
		 * --------------------------------------------------------- 
		 * @author 寻 港
		 * @date 2009-09-22
		 * @description 最多支持9个字符;
		 *  					随着字数增多, 逐渐压缩字符大小;
		 *  ---------------------------------------------------------
		 */
		String destination = labelPrintInfo.getDestination();
		int txtLength = labelPrintInfo.getDestination().length();
		if(txtLength <= 6){
			if(txtLength < 6){
				bridge.ecTextOutR(120,202,45,"黑体",labelPrintInfo.getDestination(),0,60,0);
				//bridge.ecTextOutR(400,130,52,"黑体",destination1,0,60,0);
			}else{
				bridge.ecTextOutR(120,202,45,"黑体",labelPrintInfo.getDestination(),0,60,0);
				//bridge.ecTextOutR(370,130,52,"黑体",destination1,0,60,0);
			}
		}else if(txtLength <= 7 && txtLength > 6){
			bridge.ecTextOutR(120 + addLength(txtLength,5,50),202,35,"黑体",labelPrintInfo.getDestination(),0,60,0);
		}else if(txtLength <= 9 && txtLength > 7){
			bridge.ecTextOutR(120 + addLength(txtLength,5,50),202,28,"黑体",labelPrintInfo.getDestination(),0,60,0);
		}
		else
		{
			//目的站长度已达 " + txtLength + " 个字符 \n  系统将自动压缩为9个字, 是否仍然打印;
			bridge.ecTextOutR(350 + addLength(destination.length(),5,50),202,28,"黑体",destination,0,60,0);
		}
		
		
		//必须打印件数
		int printIndexEndlength = ("" + printIndexEnd).length();
		bridge.ecTextOutR(100 + addLength(printIndexEndlength,3,10),250,70,"黑体",labelPrintInfo.getPieces(),0,40,0);
		
		if(printIndexEndlength == 1){
			bridge.sendCommand("AB," + (177 + printIndexEndlength*40) + ",280,1,1,2,0,^C0");			
			bridge.ecTextOutR((150 + printIndexEndlength*40),280,27,"宋体", "第",0,40,0);
		}else if(printIndexEndlength == 2){
			bridge.sendCommand("AB," + (145 + printIndexEndlength*38) + ",280,1,1,2,0,^C0");
			bridge.ecTextOutR((110 + printIndexEndlength*40),280,27,"宋体", "第",0,40,0);
		}else{
			bridge.sendCommand("AB," + (110 + printIndexEndlength*38) + ",280,1,1,2,0,^C0");
			bridge.ecTextOutR((80 + printIndexEndlength*40),280,27,"宋体", "第",0,40,0);
		}
		
		bridge.ecTextOutR(380,250,60,"黑体",labelPrintInfo.getNumber(),0,70,0); // 单号
		
		String consigneeLength = deliver + labelPrintInfo.getConsignee();
		if(consigneeLength.length() > 20)
		{
			bridge.ecTextOutR(132,320,27,"宋体",deliver + labelPrintInfo.getConsignee(),0,50,0);
		}
		else
		{
			bridge.ecTextOutR(132,320,36,"宋体",deliver + labelPrintInfo.getConsignee(),0,50,0);
		}
		
		
		bridge.ecTextOutR(466,202,36,"黑体",packing,0,45,0); // 包装
		
		
		bridge.ecTextOutR(390,376,48,"黑体", labelPrintInfo.getTransProperty(),0,45,0);
		if ("精 准 卡 航".equals(labelPrintInfo.getTransProperty())
				|| "精 准 城 运".equals(labelPrintInfo.getTransProperty())) {
			bridge.sendCommand("Le,388,376,707,437");//第五条竖线
		}
	
		bridge.ecTextOutR(10,383,27,"宋体", labelPrintInfo.getUserName() + "*" + labelPrintInfo.getCurrentDept() ,0,40,0);
		
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern("yy-MM-dd");// 设置打印时间的格式
		String printDate = format.format(new Date());// 打印时间
		bridge.ecTextOutR(10,410,27,"宋体", printDate,0,40,0);
		
		bridge.sendCommand("E") ;
		bridge.sendCommand("~MDEL");
		bridge.endJob() ;//结束打印工作
		bridge.closePort() ;//关闭输出端口
		
	}
}
