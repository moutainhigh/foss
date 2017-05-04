package com.deppon.foss.print.labelprint.impl.godex;

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
 * @author lifanghong
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
		

		//是否显示“德邦物流”
		String isPrintLogo=" ";
		isPrintLogo=(String)context.get("isPrintLogo");


		//发货大客户
		String deliveryBigCustomer = (String) context.get("deliveryBigCustomer");
		
		//收货大客户
		String receiveBigCustomer = (String) context.get("receiveBigCustomer");
		//展会货标识 
		String isExhibitCargo = " ";
		isExhibitCargo = (String) context.get("isExhibitCargo");
		
		
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
		
		// 最终外场ID
//		String finalOutFieldId = "";
//		if(isNull((String)context.get("finaloutfieldid"))){
//			throw new Exception("最终外场ID不存在，无法进行标签打印");
//		}
//		else {
//			finalOutFieldId = (String)context.get("finaloutfieldid");
//		}
		
		// 最终外场城市名称
		String finalOutName = "";
//		if(isNull((String)context.get("finaloutname"))){
//			throw new Exception("最终外场城市名称不存在，无法进行标签打印");
//		}
//		else {
//			finalOutName = (String)context.get("finaloutname");
//		}
		
		finalOutName = (String)context.get("finaloutname");
		// wutao 200945
		//DMANA-3745：FOSS标签打印：货物标签左上角添加收货地址行政区域
		String countyRegion = "";
		countyRegion = (String) context.get("countyRegion");
	
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
		
		//显示“德邦物流”		
		form.setIsPrintLogo(isPrintLogo);

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
		if(packing!=null && packing.length()>11){
			packing = packing.substring(0,11);
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
		form.setFinalOutfield(finalOutName == null ? "": finalOutName);
		//区域：
		form.setCountyRegion(countyRegion == null ? "" : countyRegion); 
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
			form.setIsDelivery("");
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
			form.setPreAssembly("（一）");
		} else {
			form.setPreAssembly("（二）");
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

	//godex打印机进行打印操作
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
//			printStrTemplate.append("^Y96,N,8,1\r\n");
			printStrTemplate.append("~MDEL\r\n");
			printStrTemplate.append("^L\r\n");
			printStrTemplate.append("^E10\r\n");
			printStrTemplate.append("^S3\n");//设置打印速度

			
			printStrTemplate.append("^R" + labelForm.getLeft()+ "\r\n");//设置打印左边距和上边距
			printStrTemplate.append("~Q+" + labelForm.getTop() + "\r\n");
			
			printStrTemplate.append("H107,6,1,1,310,700,2\r\n");//画矩形框
			printStrTemplate.append("Lo,326,6,328,706\r\n");//第一条竖线
			printStrTemplate.append("Lo,231,6,238,488\r\n");//第二条竖线
			printStrTemplate.append("Lo,170,6,172,706\r\n");//第三条竖线
			printStrTemplate.append("Lo,110,129,232,131\r\n");//第一条横线
			printStrTemplate.append("Lo,110,247,232,249\r\n");//第二条横线
			printStrTemplate.append("Lo,110,365,232,367\r\n");//第三条横线
			printStrTemplate.append("Lo,110,483,422,485\r\n");//第四条横线
			
			if("N".equals(labelForm.getIsPrintLogo()))
			{}//无需执行任何逻辑
			else{
			printStrTemplate.append("AZ,95,30,2,1,0,1,德邦物流\r\n");//打印公司LOGO
			    }
			printStrTemplate.append("Le,60,10,62,250\r\n");//LOGO下面的竖线
			
			
			printStrTemplate.append("AE,62,45,1,1,1,1,"+labelForm.getOptNum()+"\r\n");//工号
			printStrTemplate.append("AE,31,10,1,1,1,1,"+labelForm.getPrintDate()+"\r\n");//打印日期
			printStrTemplate.append("BQ,95,255,2,2,75,1,1,"+barcode+"\r\n");//条码
			//如果为展会货，在右下角打印“展”字
			if("Y".equals(labelForm.getIsExhibit())){
			printStrTemplate.append("AZ,50,667,2,2,1,1,展\r\n");}
			//打印大客户标记
			if("Y".equals(labelForm.getBigCustomer())){
				//如果是空运，则位置向下移
				if(labelForm.getTransportProperty().indexOf("空运") != -1) {					
					printStrTemplate.append("R1,580,51,660,4,4\r\n");//画VIP矩形框
					printStrTemplate.append("AE,50,590,1,1,1,1,VIP\r\n");//打印VIP字样					
				}else{
					printStrTemplate.append("R1,580,51,660,4,4\r\n");//画VIP矩形框
					printStrTemplate.append("AE,50,590,1,1,1,1,VIP\r\n");//打印VIP字样
				}
			}
			
			printStrTemplate.append("AZ,390,30,1,2,1,1,"+labelForm.getIsDelivery()+"\r\n");//是否送货
			//wutao 
			//最终外场字符串拼接
/*			String _finalOutField = "";
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
			}*/
			
			
			
			
			String _finalOutField = "";
			if(labelForm.getFinalOutfield().length()<=5){
				if(labelForm.getCountyRegion()!=null && !"".equals(labelForm.getCountyRegion())){
					_finalOutField = labelForm.getFinalOutfield() + signFlag + "—" + labelForm.getCountyRegion();
				}else{
					_finalOutField = labelForm.getFinalOutfield() + signFlag;
				}
			}else{
				if(labelForm.getCountyRegion()!=null && !"".equals(labelForm.getCountyRegion())){
					_finalOutField = labelForm.getFinalOutfield().substring(0,6) + signFlag+ "—" + labelForm.getCountyRegion() ;
				}else{
					_finalOutField = labelForm.getFinalOutfield() + signFlag ;
				}
			}
			
			
			
			
			
			
			//if(strWeight!=null && !"".equals(strWeight))
			//wutao === end
			//如果最终外场名称长度小于6个则字体大号
			if(_finalOutField!=null && _finalOutField.length()<6){
				printStrTemplate.append("AZ,380,105,1,2,0,1,"+_finalOutField+"\r\n");//最终外场名称
			}else if(_finalOutField.length() >=6 && _finalOutField.length() < 10){
				printStrTemplate.append("AZ,380,105,1,2,0,1,"+_finalOutField+"\r\n");//最终外场名称
			}else {
				printStrTemplate.append("AZ,380,105,1,2,0,1,"+_finalOutField.substring(0, 10)+"\r\n");//最终外场名称
			}
			//wutao --- 打印最终外场
			
//			件数/总件数
			if(labelForm.getTotalPieces() > 0
					&& labelForm.getTotalPieces() < 10) {
				printStrTemplate.append("AE,415,505,1,1,1,1,"+outPieces+"\r\n");
			}else if(labelForm.getTotalPieces() >= 10
					&& labelForm.getTotalPieces() < 100){
				printStrTemplate.append("AE,415,505,1,1,1,1,"+outPieces+"\r\n");
			}else if(labelForm.getTotalPieces() >= 100
					&& labelForm.getTotalPieces() < 1000) {
				printStrTemplate.append("AE,415,505,1,1,1,1,"+outPieces+"\r\n");
			}else if(labelForm.getTotalPieces() >= 1000
					&& labelForm.getTotalPieces() < 10000){
				printStrTemplate.append("AE,415,505,1,1,1,1,"+outPieces+"\r\n");
			}else {
				printStrTemplate.append("AE,415,505,1,1,1,1,"+outPieces+"\r\n");
			}
			
			// 根据 RCMS2012121901-标签界面优化需求V1.0（新） （提出人-Yue YL Luo 修改货物类型输出位置）			
			//如果是大客户，则往上移
			//if(!"".equals(labelForm.getGoodsType()) && null != labelForm.getGoodsType()){               //加
			if("Y".equals(labelForm.getBigCustomer())) {	
				if("A".equals(labelForm.getGoodsType())||"B".equals(labelForm.getGoodsType())){
					
					printStrTemplate.append("AE,90,560,1,1,1,1,"+labelForm.getGoodsType()+"\r\n");//打印VIP字样				
				}else{
					printStrTemplate.append("AZ,90,560,1,1,1,1,"+labelForm.getGoodsType()+"\r\n");//货物类型
				}
			
				}else{
					if("A".equals(labelForm.getGoodsType())||"B".equals(labelForm.getGoodsType())){
						
						printStrTemplate.append("AE,80,560,1,1,1,1,"+labelForm.getGoodsType()+"\r\n");//打印VIP字样				
					}else{
						printStrTemplate.append("AZ,80,560,1,1,1,1,"+labelForm.getGoodsType()+"\r\n");//货物类型
					}
			}
			//}//加
			char[] outPackingArray = outPacking.toCharArray();
			
			for (int j = 0; j < outPackingArray.length; j++) {
				int k = 480+j*20;
				// 由于中文的编码区间为：0x4e00--0x9fbb，故Java判断一个字符串是否有中文是利用Unicode编码来判断，
				if ((outPackingArray[j] >= 0x4e00) && (outPackingArray[j] <= 0x9fbb)) {
					printStrTemplate.append("AZ,360,"+k+",1,1,1,1,"+outPackingArray[j]+"\r\n");//总件数 包装
				} else {
					if(j==0){
						printStrTemplate.append("AE,375,480,1,1,1,1,"+outPackingArray[j]+"\r\n");//总件数 包装
					}else{
						printStrTemplate.append("AE,375,"+k+",1,1,1,1,"+outPackingArray[j]+"\r\n");//总件数 包装
					}
				}
			}
						
//			printStrTemplate.append("AZ,360,490,1,1,1,1,"+outPacking+"\r\n");//总件数 包装
			
//			始发城市
			if (labelForm.getLeaveCity().length() < 3)
			{
				printStrTemplate.append("AZ,294,30,1,1,1,1,"+outleaveCity+"\r\n");
			} else if(labelForm.getLeaveCity().length() == 3) {
				printStrTemplate.append("AZ,316,30,1,1,1,1," + labelForm.getLeaveCity().substring(0, 2) + "\r\n");
				printStrTemplate.append("AZ,300,92,1,1,1,1," + "—" + "\r\n");
				printStrTemplate.append("AZ,284,47,1,1,1,1,"+outleaveCity+"\r\n");
			}else{
				printStrTemplate.append("AZ,316,30,1,1,1,1," + labelForm.getLeaveCity().substring(0, 2) + "\r\n");
				printStrTemplate.append("AZ,300,92,1,1,1,1," + "—" + "\r\n");
				printStrTemplate.append("AZ,284,30,1,1,1,1,"+outleaveCity+"\r\n");
			}
			
////			//目的站
			if(labelForm.getDestination().length() <6) {
				printStrTemplate.append("AZ,300,115,3,2,1,1,"+labelForm.getDestination().trim()+"\r\n");//180
			}else if(labelForm.getDestination().length() >=6 && labelForm.getDestination().length() < 8){
				printStrTemplate.append("AZ,300,115,2,2,1,1,"+labelForm.getDestination().trim()+"\r\n");//最终外场名称
			}else {
				printStrTemplate.append("AZ,300,115,1,2,1,1,"+labelForm.getDestination().trim()+"\r\n");//218
			}
////			// 打印foss 试运行特殊标记
//			if(lblPrintContext.getTestEnvInd()!=null && lblPrintContext.getTestEnvInd().length()>0 ){
//				printStrTemplate.append("AE,300,370,1,1,2,1,"+lblPrintContext.getTestEnvInd()+"\r\n");
//			}
			                        
			printStrTemplate.append("AE,335,515,1,2,2,1,"+labelForm.getWaybillNum1()+"\r\n");//单号1
			printStrTemplate.append("AE,265,515,1,2,2,1,"+labelForm.getWaybillNum2()+"\r\n");//单号2
			
			//处理库位1
			char[] charArray1 = labelForm.getLocation1().toCharArray();
			for (int j = 0; j < charArray1.length; j++) {
				int k = 26+j*20;
				// 由于中文的编码区间为：0x4e00--0x9fbb，故Java判断一个字符串是否有中文是利用Unicode编码来判断，
				if ((charArray1[j] >= 0x4e00) && (charArray1[j] <= 0x9fbb)) {
					printStrTemplate.append("AZ,155,"+k+",1,1,1,1,"+charArray1[j]+"\r\n");
				} else {
					if(j==0){
						printStrTemplate.append("AE,170,"+20+",1,1,1,1,"+charArray1[j]+"\r\n");
					}else{
						printStrTemplate.append("AE,170,"+k+",1,1,1,1,"+charArray1[j]+"\r\n");
					}
				}
			}
			
			//处理库位2
			char[] charArray2 = labelForm.getLocation2().toCharArray();
			for (int j = 0; j < charArray2.length; j++) {
				int k = 140+j*20;
				if ((charArray2[j] >= 0x4e00) && (charArray2[j] <= 0x9fbb)) {
					printStrTemplate.append("AZ,155,"+k+",1,1,1,1,"+charArray2[j]+"\r\n");
				} else {
					if(j==0){
						printStrTemplate.append("AE,170,"+134+",1,1,1,1,"+charArray2[j]+"\r\n");
					}else{
						printStrTemplate.append("AE,170,"+k+",1,1,1,1,"+charArray2[j]+"\r\n");
					}
				}
			}
			
			
			//处理库位3
			char[] charArray3 = labelForm.getLocation3().toCharArray();
			for (int j = 0; j < charArray3.length; j++) {
				int k = 260+j*20;
				if ((charArray3[j] >= 0x4e00) && (charArray3[j] <= 0x9fbb)) {
					printStrTemplate.append("AZ,155,"+k+",1,1,1,1,"+charArray3[j]+"\r\n");
				} else {
					if(j==0){
						printStrTemplate.append("AE,170,"+254+",1,1,1,1,"+charArray3[j]+"\r\n");
					}else{
						printStrTemplate.append("AE,170,"+k+",1,1,1,1,"+charArray3[j]+"\r\n");
					}
				}
			}
			
			//处理库位4
			char[] charArray4 = labelForm.getLocation4().toCharArray();
			for (int j = 0; j < charArray4.length; j++) {
				int k = 380+j*20;
				if ((charArray4[j] >= 0x4e00) && (charArray4[j] <= 0x9fbb)) {
					printStrTemplate.append("AZ,155,"+k+",1,1,1,1,"+charArray4[j]+"\r\n");
				} else {
					if(j==0){
						printStrTemplate.append("AE,170,"+374+",1,1,1,1,"+charArray4[j]+"\r\n");
					}else{
						printStrTemplate.append("AE,170,"+k+",1,1,1,1,"+charArray4[j]+"\r\n");
					}
				}
			}
			
			printStrTemplate.append("AE,230,26,1,1,1,1,"+(labelForm.getAddr1()==null?"":labelForm.getAddr1()) +"\r\n");//编码1
			printStrTemplate.append("AE,230,140,1,1,1,1,"+(labelForm.getAddr2()==null?"":labelForm.getAddr2()) +"\r\n");//编码2
			printStrTemplate.append("AE,230,260,1,1,1,1,"+(labelForm.getAddr3()==null?"":labelForm.getAddr3()) +"\r\n");//编码3
			printStrTemplate.append("AE,230,380,1,1,1,1,"+(labelForm.getAddr4()==null?"":labelForm.getAddr4()) +"\r\n");//编码4
			
			//运输性质目前现有产品名称分为4个字和8个字如果是4个字打印2号字体。 如果8字打1号字体
			if(labelForm.getTransportProperty().length()==4){
				printStrTemplate.append("AZ,165,500,2,2,1,1,"+labelForm.getTransportProperty()+"\r\n");
			}else{
				if(labelForm.getTransportProperty().contains("(")){
					if(labelForm.getTransportProperty().indexOf('(')<=4){
						printStrTemplate.append("AZ,145,500,1,1,1,1,"+labelForm.getTransportProperty().substring(0,4)+"\r\n");
						printStrTemplate.append("AE,165,605,1,1,1,1,"+labelForm.getTransportProperty().substring(4,5)+"\r\n");
						printStrTemplate.append("AZ,145,625,1,1,1,1,"+labelForm.getTransportProperty().substring(5,7)+"\r\n");
						printStrTemplate.append("AE,165,675,1,1,1,1,"+labelForm.getTransportProperty().substring(7,8)+"\r\n");
					}else{
						printStrTemplate.append("AZ,145,500,1,1,1,1,"+labelForm.getTransportProperty().substring(0,6)+"\r\n");
						printStrTemplate.append("AE,165,650,1,1,1,1,"+labelForm.getTransportProperty().substring(6,7)+"\r\n");
						printStrTemplate.append("AZ,145,665,1,1,1,1,"+labelForm.getTransportProperty().substring(7,8)+"\r\n");
						printStrTemplate.append("AE,165,690,1,1,1,1,"+labelForm.getTransportProperty().substring(8,9)+"\r\n");
					}
				}else{
					/**
					 * @author 200945
					 * wutao 
					 * DEFECT-5335
					 * 精准大票标签，精准大票卡航和精准大票城运加黑色底框
					 *  <p>由于第一次提交没有部署上，故在此提交</p>
					 */
					printStrTemplate.append("AZ,165,500,1,2,1,1,"+labelForm.getTransportProperty()+"\r\n");
				}
			}
			
			
			 
			
			
			printStrTemplate.append("E\n");//设置打印头温度
			printStrTemplate.append("~MDEL\n");//取消打印回转功能
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
