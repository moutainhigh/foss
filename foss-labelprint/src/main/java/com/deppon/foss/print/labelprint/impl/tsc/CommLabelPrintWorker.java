package com.deppon.foss.print.labelprint.impl.tsc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.print.labelprint.LabelPrintWorker;
import com.deppon.foss.print.labelprint.impl.LabelPrintForm;

/**
 * Zebra888TT Comm Label Print Program
 * @author niujian
 */
public class CommLabelPrintWorker extends LabelPrintWorker {
 
	@Override
	public void prepareLabelPrintForm(LabelPrintContext lblPrintContext)
			throws Exception {
		//printByZebra(lblPrintContext);
	}

	@Override
	public void prepareLabelPrintService(LabelPrintContext lblPrintContext)
			throws Exception {
	}

	@Override
	public void beforeExecuteLabelPrint(LabelPrintContext lblPrintContext)
			throws Exception {
	}
	
	@Override
	public void executePrintProcess(LabelPrintContext lblPrintContext)
			throws Exception {
		
		List<byte[]> cmdbytelst = printByZebra(lblPrintContext);
		
		PrintService _prtservice = findFirstPrintService(lblPrintContext);
		lblPrintContext.setmPrintService(_prtservice);
		PrintService _printService = lblPrintContext.getmPrintService();
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		for(byte[] bytes : cmdbytelst){
			DocPrintJob job = _printService.createPrintJob();
			PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
			ByteArrayInputStream str = new ByteArrayInputStream( bytes);
			Doc doc = new SimpleDoc(str, flavor, null);
			job.print(doc, pras);
		}
	}
	
	/*
	 * param for label print 
	 * 地区1 addr1
	 * 地区2 addr2
	 * 地区3 addr3
	 * 地区4 addr4
	 * 编号1 location1
	 * 编号2 location2
	 * 编号3 location3
	 * 编号4 location4
	 * 当前用户 工号 optusernum
	 * 单号 number
	 * 打印流水号 serialnos[0001,0002]
	 * 始发站(出发城市) leavecity
	 * 目的站 destination
	 * 是否偏线 isagent[true]
	 * 目的站编码  stationnumber
	 * 最终外场编码 deptno
	 * 最终外场ID finaloutfieldid
	 * 最终外场城市名称 finaloutname
	 * 重量 weight
	 * 件数 totalpieces
	 * 包装 packing
	 * 异常货 unusual[异]
	 * 运输类型/运输性质 transtype[中文]
	 * 打印日期 printdate
	 * 是否送货 deliver[送]
	 * 货物类型 goodstype[A/B/ ]
	 * 航班早班 preassembly[早班]
	 */
	public LabelPrintForm getLabelPrintForm(LabelPrintContext context)
			throws Exception {

		// 地区1
		String locationOne = " ";
		locationOne = (String) context.get("addr1");
		
		// 地区2
		String locationTwo = " ";
		locationTwo = (String) context.get("addr2");
		
		// 地区3
		String locationThree = " ";
		locationThree = (String) context.get("addr3");
		
		// 地区4
		String locationFour = " ";
		locationFour = (String) context.get("addr4");
		
		// 编号1
		String storeOneNum = " ";
		storeOneNum = (String) context.get("location1");
		
		// 编号2
		String storeTwoNum = " ";
		storeTwoNum = (String) context.get("location2");
		
		// 编号3
		String storeThreeNum = " ";
		storeThreeNum = (String) context.get("location3");
		
		// 编号4
		String storeFourNum = " ";
		storeFourNum = (String) context.get("location4");

		// 当前用户 工号
		String userName = (String) context.get("optusernum");
		// 单号
		String number1 = (String) context.get("number");
		
		//发货大客户
		String deliveryBigCustomer = (String) context.get("deliveryBigCustomer");
		
		//收货大客户
		String receiveBigCustomer = (String) context.get("receiveBigCustomer");
		//展会货标识 
		String isExhibitCargo = " ";
		isExhibitCargo = (String) context.get("isExhibitCargo");
		//是否显示“德邦物流”
		String isPrintLogo=" ";
		isPrintLogo=(String)context.get("isPrintLogo");


		
		// wutao 200945
		//DMANA-3745：FOSS标签打印：货物标签左上角添加收货地址行政区域
		String countyRegion = "";
		countyRegion = (String) context.get("countyRegion");
		
		//打印件的流水号
		String serialnos = (String)context.get("serialnos");
		if(isNull(serialnos)){
			throw new Exception("输入的流水号不正确，无法进行标签打印");
		}
		String[] serialnoarr = serialnos.split(",");
		
		// 始发站
		String departure = (String) context.get("leavecity");
		// 目的站
		String destination = (String) context.get("destination");
		boolean isAgent = "true".equals((String) context.get("isagent"));

		// 如果是偏线，最多只打5个汉字
		if (isAgent) {
			int index = destination.indexOf("转");
			if (index != -1) {
				destination = destination.substring(index);
				if (destination.length() > 5) {
					destination = destination.substring(0, 5);
				}
			}
		}
		
		if (isNull(number1)) {
			throw new Exception("运单号不正确，无法进行标签打印");
		}
		
		if ("".equals(departure) || departure==null ) {
			departure = " ";
		}

		if ("".equals(destination) || destination==null ) {
			destination = " ";
		}

		//查询目的站编码和最终外场编码，增加对最终外场不存在的判断
		// 目的站编码 
		String stationNumber = "0000";
		if(isNull((String)context.get("stationnumber"))){
			throw new Exception("目的站编码不存在，无法进行标签打印");
		}
		else {
			stationNumber = (String)context.get("stationnumber");
		}

		// 最终外场编码
//		String deptNo = "D00";
//		if(isNull((String)context.get("deptno"))){
//			throw new Exception("最终外场编码不存在，无法进行标签打印");
//		}
//		else {
//			deptNo = (String)context.get("deptno");
//		}
//		
//		// 最终外场ID
//		String finalOutFieldId = "";
//		if(isNull((String)context.get("finaloutfieldid"))){
//			throw new Exception("最终外场ID不存在，无法进行标签打印");
//		}
//		else {
//			finalOutFieldId = (String)context.get("finaloutfieldid");
//		}
		
		// 最终外场城市名称
		String finalOutName = "";
		if(isNull((String)context.get("finaloutname"))){
			throw new Exception("最终外场城市名称不存在，无法进行标签打印");
		}
		else {
			finalOutName = (String)context.get("finaloutname");
		}
		
		// 标签打印信息对象
		LabelPrintForm form = new LabelPrintForm();
		//设置是否打星标，有自动分拣的外场需要标记星标来区分
		boolean signFlag = Boolean.parseBoolean((String)context.get("signFlag"));
		form.setSignFlag(signFlag);
		// 上边距
		form.setTop(context.getPrtTop());
		// 左边距
		form.setLeft(context.getPrtLeft());
		
		// 库位1
		form.setAddr1(locationOne);
		form.setLocation1(storeOneNum);
		// 库位2
		form.setAddr2(locationTwo);
		form.setLocation2(storeTwoNum);
		// 库位3
		form.setAddr3(locationThree);
		form.setLocation3(storeThreeNum);
		// 库位4
		form.setAddr4(locationFour);
		form.setLocation4(storeFourNum);

		// 出发城市
		form.setLeaveCity(departure);
		// 是否偏线
		form.setAgent(isAgent);
		// 目的站
		form.setDestination(destination);
		//展会货标识
				if("Y".equals(isExhibitCargo) ){
				form.setIsExhibit("Y");}
				else{
					form.setIsExhibit("N");
					}
		//显示“德邦物流”		
		form.setIsPrintLogo(isPrintLogo);		
		// 单号
		int length = number1.length();
		if (length == 8) {
			form.setWaybillNum1(number1.substring(0, length - 4));
			form.setWaybillNum2(number1.substring(length - 4));
		} else if (length == 9) {
			form.setWaybillNum1(number1.substring(0, length - 4));
			form.setWaybillNum2(number1.substring(length - 4));
		} else if (length == 10) {
			form.setWaybillNum1(number1.substring(0, length - 5));
			form.setWaybillNum2(number1.substring(length - 5));
		}
		
		// 件数
		form.setTotalPieces(Integer.parseInt((String) context.get("totalpieces")));
		// 包装
		String packing = (String) context.get("packing");
		form.setPacking(packing);
		if(packing!=null && packing.length()>6){
			packing = packing.substring(0, 6);
		}
		
		//异常货
		//String unusual = (String) context.get("unusual");
		//if("异".equals(unusual)){
		//	packing += "【异】";
		//} 根据 RCMS2012121901-标签界面优化需求V1.0（新） （提出人-Yue YL Luo）注销此处功能
		
		form.setPacking(packing);
		// 运输类型
		String transType = (String) context.get("transtype");
		// 运输性质
		form.setTransportProperty(transType);
		// 用户工号
		form.setOptNum(userName);
		// 最终外场编码
//		form.setFinalOutCode(deptNo);
		// 最终外场名称
		form.setFinalOutfield(finalOutName == null ? " " : finalOutName);
		//区域：
		form.setCountyRegion(countyRegion == null ? " " : countyRegion); 		
		// 目的站编码
		form.setDestionalCode(stationNumber);
		// 打印日期
		String printDateStr = (String) context.get("printdate");
		form.setPrintDate(printDateStr);
		// 是否送货 【送】
		if("送".equals((String) context.get("deliver"))){
			form.setIsDelivery("【送】");
		}
		else {
			form.setIsDelivery(" ");
		}
		
		//发货客户和收货客户有一个是大客户就打印“vip”
		if("Y".equals(deliveryBigCustomer) || "Y".equals(receiveBigCustomer)){
			form.setBigCustomer("Y");
		}else{
			form.setBigCustomer("N");
		}
		
		
		// 设置货物类型 A/B/" "
		form.setGoodsType((String) context.get("goodstype"));

		// 航班早班显示(一)，其他显示(二)
		if ("MORNING_FLIGHT".equals((String) context.get("preassembly"))) {
			form.setPreAssembly("(一)");
		} else {
			form.setPreAssembly("(二)");
		}
		
		//要打印的件数
		int pieces = serialnoarr.length;
		
		// 重量
		try{
			String strWeight = (String) context.get("weight");
			BigDecimal weight = (new BigDecimal(strWeight)).multiply(new BigDecimal(1000)).divide(new BigDecimal(pieces), 0, BigDecimal.ROUND_HALF_UP);
			String average_weight = getDigit(weight.toString(), 3);
			// 平均重量
			form.setWeight(average_weight);
		}catch (Exception e) {
			throw new Exception("打印标签计算平均重量出错，"+e.toString());
		}
		
		// 设置条码内容
		try{
			List<String> printPiecesList = new ArrayList(pieces);
			List<String> barcodeList = new ArrayList(pieces);
			for (int i = 0; i <pieces; i++) {
				String serial_no = serialnoarr[i];
				//barcodeList.add(number1 + serial_no + form.getWeight()+ form.getFinalOutCode() + form.getDestionalCode());
				barcodeList.add(number1 + serial_no + form.getDestionalCode()); //运单号9位 + 流水号4位 + 目的站编码4位
				printPiecesList.add( serialnoarr[i]);
			}
			form.setBarcode(barcodeList);
			form.setPrintPiecesList(printPiecesList);		
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("打印流水号转换成件数序号错误，无法进行标签打印"+e.toString());
		}
		return form;
	}

	//斑马打印机进行打印操作
	public List<byte[]> printByZebra(LabelPrintContext lblPrintContext) throws Exception 
	{
		LabelPrintForm labelForm =  getLabelPrintForm(lblPrintContext);
		//定义传送带标示★
		String signFlag = "";
		//先判断是否是传送带区域
		if(labelForm.isSignFlag()) {
			signFlag = "★";
		}
		//如果是空运则显示空运航班标志
		if(labelForm.getTransportProperty().indexOf("空运") != -1) {
			signFlag = labelForm.getPreAssembly();
		}
		
		//读取德邦logo图片
//		ClassLoader cl = AirWayBillLabelPrintWorker.class.getClassLoader();
//		InputStream in = null;
//		byte[] pcxBytes = new byte[4096];
//		int len = 0;
//		try {
//			in = cl.getResourceAsStream("logo/dplogo.pcx");
//			len = in.read(pcxBytes);
//		} finally {
//			if(in!=null){
//				in.close();
//			}
//			else {
//				
//			}
//		}
		
		List<byte[]> outputlist = new ArrayList<byte[]>();
		List<String> printPiecesList = labelForm.getPrintPiecesList();
		List<String> barcodeList = labelForm.getBarcode();
		for (int i = 1; i <= barcodeList.size(); i++){
			ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
			
			String barcode = barcodeList.get(i - 1);
			String printPiece = printPiecesList.get(i - 1);
			int totalpieces = labelForm.getTotalPieces();
			String outPieces = "";
			String outPacking = "";
			if(totalpieces<1000){ // <1000
				outPieces = printPiece + "/" + totalpieces ;
				outPacking = labelForm.getPacking();
			}
			else { // >=1000
				outPieces = printPiece + "/";
				outPacking = totalpieces + " " + labelForm.getPacking();
			}
			
			String outleaveCity = labelForm.getLeaveCity();
			if (outleaveCity.length()<3)
			{
				outleaveCity +="—";
			}
			else
			{
				outleaveCity = outleaveCity.substring(2,outleaveCity.length());
			}
			
			//打印指令
			StringBuffer printStrTemplate = new StringBuffer("");
			printStrTemplate.append("N\r\n");
			printStrTemplate.append("Q720,24\r\n");//设置标签纸高度及标签之间的间隙
			printStrTemplate.append("q440\r\n");//设置标签纸宽度
			printStrTemplate.append("S6\n");//设置打印速度
			printStrTemplate.append("D7\n");//设置打印头温度
			printStrTemplate.append("JB\n");//取消打印回转功能
			printStrTemplate.append("ZB\n");//从标签右下角开始打印
			printStrTemplate.append("R" + labelForm.getLeft() + "," + labelForm.getTop() + "\n");//设置打印左边距和上边距
			
			printStrTemplate.append("X130,1,2,440,700\r\n");//画矩形框
			printStrTemplate.append("LO346,1,3,700\r\n");//第一条竖线
			printStrTemplate.append("LO250,1,3,482\r\n");//第二条竖线
			printStrTemplate.append("LO190,1,3,700\r\n");//第三条竖线
			printStrTemplate.append("LO130,126,120,3\r\n");//第一条横线
			printStrTemplate.append("LO130,244,120,3\r\n");//第二条横线
			printStrTemplate.append("LO130,362,120,3\r\n");//第三条横线
			printStrTemplate.append("LO130,480,310,3\r\n");//第四条横线
			
			if("N".equals(labelForm.getIsPrintLogo()))
			{}//无需执行任何逻辑
			else{
			printStrTemplate.append("A115,30,1,8,1,1,N,\"德邦物流\"\r\n");//打印公司LOGO
			    }
			printStrTemplate.append("LO80,10,3,160\r\n");//LOGO下面的竖线
			printStrTemplate.append("A70,45,1,4,1,1,N,\""+labelForm.getOptNum()+"\"\r\n");//工号
			printStrTemplate.append("A48,10,1,4,1,1,N,\""+labelForm.getPrintDate()+"\"\r\n");//打印日期
			printStrTemplate.append("B125,185,1,1,2,12,75,B,\""+barcode+"\"\r\n");//条码
			
			printStrTemplate.append("A410,10,1,8,1,1,N,\""+labelForm.getIsDelivery()+"\"\r\n");//是否送货
			//如果为展会货，在右下角打印“展”字
			if("Y".equals(labelForm.getIsExhibit())){
			printStrTemplate.append("A80,670,1,8,1,1,N,\"展\"\r\n");}
			//打印大客户标记
			if("Y".equals(labelForm.getBigCustomer())){
				//如果是空运，则位置向下移
				if(labelForm.getTransportProperty().indexOf("空运") != -1) {
					printStrTemplate.append("X35,540,4,85,620\r\n");//画VIP矩形框
					printStrTemplate.append("A70,560,1,4,1,1,N,\"VIP\"\r\n");//打印VIP字样
				}else{
					printStrTemplate.append("X55,540,4,105,620\r\n");//画VIP矩形框
					printStrTemplate.append("A90,560,1,4,1,1,N,\"VIP\"\r\n");//打印VIP字样
				}
			}
			
			//wutao 
			//最终外场字符串拼接
			String _finalOutField = "";
			if(labelForm.getFinalOutfield().length()<=5){
				if("".equals(labelForm.getCountyRegion())){
					_finalOutField = labelForm.getFinalOutfield() + signFlag ;
				}else{
					_finalOutField = labelForm.getFinalOutfield() + signFlag + "—" + labelForm.getCountyRegion();
				}
			}else{
				if("".equals(labelForm.getCountyRegion())){
					_finalOutField = labelForm.getFinalOutfield() + signFlag ;
				}else{
					_finalOutField = labelForm.getFinalOutfield().substring(0,6) + signFlag + "—" + labelForm.getCountyRegion();
				}
			}
			//wutao === end
			if(_finalOutField.length()<=6){
				printStrTemplate.append("A428,90,1,8,2,2,N,\""+_finalOutField+"\"\r\n");//最终外场名称
			}else if(_finalOutField.length()>6 && _finalOutField.length()<=10){
				printStrTemplate.append("A428,90,1,8,2,1,N,\""+_finalOutField+"\"\r\n");//最终外场名称
			}else{
				printStrTemplate.append("A428,90,1,8,2,1,N,\""+_finalOutField.substring(0,10)+"\"\r\n");//最终外场名称
			}
			
			//件数/总件数
			if(labelForm.getTotalPieces() > 0
					&& labelForm.getTotalPieces() < 10) {
				printStrTemplate.append("A435,526,1,8,2,1,N,\""+outPieces+"\"\r\n");
			}else if(labelForm.getTotalPieces() >= 10
					&& labelForm.getTotalPieces() < 100){
				printStrTemplate.append("A435,516,1,8,2,1,N,\""+outPieces+"\"\r\n");
			}else if(labelForm.getTotalPieces() >= 100
					&& labelForm.getTotalPieces() < 1000) {
				printStrTemplate.append("A435,508,1,8,2,1,N,\""+outPieces+"\"\r\n");
			}else if(labelForm.getTotalPieces() >= 1000
					&& labelForm.getTotalPieces() < 10000){
				printStrTemplate.append("A435,486,1,8,2,1,N,\""+outPieces+"\"\r\n");
			}else {
				printStrTemplate.append("A435,486,1,8,1,1,N,\""+outPieces+"\"\r\n");
			}
			
			// 根据 RCMS2012121901-标签界面优化需求V1.0（新） （提出人-Yue YL Luo 修改货物类型输出位置）
			//如果是大客户，则往上移
			//if("Y".equals(labelForm.getBigCustomer())) {
				printStrTemplate.append("A120,495,1,8,1,1,N,\""+labelForm.getGoodsType()+"\"\r\n");//货物类型
			//}else{
			//	printStrTemplate.append("A80,640,1,8,1,1,N,\""+labelForm.getGoodsType()+"\"\r\n");//货物类型
			//}			
			printStrTemplate.append("A380,484,1,8,1,1,N,\""+outPacking+"\"\r\n");//总件数 包装
			
			//始发城市
			if (labelForm.getLeaveCity().length() < 3)
			{
				printStrTemplate.append("A314,10,1,8,1,1,N,\""+outleaveCity+"\"\r\n");
			} else if(labelForm.getLeaveCity().length() == 3) {
				printStrTemplate.append("A336,10,1,8,1,1,N,\"" + labelForm.getLeaveCity().substring(0, 2) + "\"\r\n");
				printStrTemplate.append("A320,72,1,8,1,1,N,\"" + "—" + "\"\r\n");
				printStrTemplate.append("A304,27,1,8,1,1,N,\""+outleaveCity+"\"\r\n");
			}else{
				printStrTemplate.append("A336,10,1,8,1,1,N,\"" + labelForm.getLeaveCity().substring(0, 2) + "\"\r\n");
				printStrTemplate.append("A320,72,1,8,1,1,N,\"" + "—" + "\"\r\n");
				printStrTemplate.append("A304,10,1,8,1,1,N,\""+outleaveCity+"\"\r\n");
			}
			
			//目的站
			if(labelForm.getDestination().length() <= 6) {
				printStrTemplate.append("A330,95,1,8,2,2,N,\""+labelForm.getDestination()+"\"\r\n");//180
			}else {
				printStrTemplate.append("A330,95,1,8,2,1,N,\""+labelForm.getDestination()+"\"\r\n");//218
			}
			// 打印foss 试运行特殊标记
			if(lblPrintContext.getTestEnvInd()!=null && lblPrintContext.getTestEnvInd().length()>0 ){
				printStrTemplate.append("A330,380,1,8,2,1,R,\""+lblPrintContext.getTestEnvInd()+"\"\r\n");
			}
			
			printStrTemplate.append("A320,515,1,4,2,2,N,\""+labelForm.getWaybillNum1()+"\"\r\n");//单号1
			printStrTemplate.append("A256,515,1,4,2,2,N,\""+labelForm.getWaybillNum2()+"\"\r\n");//单号2
			printStrTemplate.append("A240,26,1,8,1,1,N,\""+(labelForm.getAddr1()==null?"":labelForm.getAddr1())+"\"\r\n");//编码1
			printStrTemplate.append("A240,140,1,8,1,1,N,\""+(labelForm.getAddr2()==null?"":labelForm.getAddr2())+"\"\r\n");//编码2
			printStrTemplate.append("A240,260,1,8,1,1,N,\""+(labelForm.getAddr3()==null?"":labelForm.getAddr3())+"\"\r\n");//编码3
			printStrTemplate.append("A240,380,1,8,1,1,N,\""+(labelForm.getAddr4()==null?"":labelForm.getAddr4())+"\"\r\n");//编码4
			printStrTemplate.append("A180,26,1,8,1,1,N,\""+(labelForm.getLocation1()==null?"":labelForm.getLocation1()) +"\"\r\n");//库位1
			printStrTemplate.append("A180,140,1,8,1,1,N,\""+(labelForm.getLocation2()==null?"":labelForm.getLocation2()) +"\"\r\n");//库位2
			printStrTemplate.append("A180,260,1,8,1,1,N,\""+(labelForm.getLocation3()==null?"":labelForm.getLocation3()) +"\"\r\n");//库位3
			printStrTemplate.append("A180,380,1,8,1,1,N,\""+(labelForm.getLocation4()==null?"":labelForm.getLocation4()) +"\"\r\n");//库位4
			
			//运输性质
			if (labelForm.getTransportProperty().indexOf("汽运") != -1 
					|| labelForm.getTransportProperty().indexOf("空运") != -1)
			{
				printStrTemplate.append("A178,480,1,8,1,1,N,\""+labelForm.getTransportProperty()+"\"\r\n");
			} else
			{
				printStrTemplate.append("A178,520,1,8,1,1,R,\""+labelForm.getTransportProperty()+"\"\r\n");
			}
			
			// 打印德邦logo
			//printStrTemplate.append("GM\"DPLOGO\"").append(len).append("\r\n");
			baos.write(printStrTemplate.toString().getBytes("GBK"));
			//baos.write(pcxBytes);
			baos.write("\r\n".getBytes());
			StringBuffer end = new StringBuffer();
			//end.append("GG67,10,\"DPLOGO\"").append("\r\n");
			end.append("P1\r\n");
			baos.write(end.toString().getBytes());
			baos.close();
			outputlist.add(baos.toByteArray());
		}
		
		return outputlist;
	}
}
