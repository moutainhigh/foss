package com.deppon.foss.print.labelprint.impl.zebra;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
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
		List<byte[]> cmdbytelst;
		if("Y".equals((String)lblPrintContext.get("sortingResult"))){
			cmdbytelst = printByZebraForLightGoods(lblPrintContext);
		}else{
		    cmdbytelst = printByZebra(lblPrintContext);
		}
		
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
	 * 其他包装 otherPackage 
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
		//打印件的流水号
		String serialnos = (String)context.get("serialnos");
		if(isNull(serialnos)){
			throw new Exception("输入的流水号不正确，无法进行标签打印");
		}
		String[] serialnoarr = serialnos.split(",");
		
		// 始发站
		String departure = (String) context.get("leavecity");
		//350909      郭倩云         零担轻货上分拣取得是城市简称
		String simpleLeaveCity = (String) context.get("simpleLeaveCity");
		// 目的站
		String destination = (String) context.get("destination");
		if(null!=destination){
			String [] destinationArrays = destination.split("");
			StringBuilder bf = new StringBuilder();
			for (String value : destinationArrays) {
				if(value.equals("浉")){
					value = "氵"+"师";
				}else if(value.equals("洺")){
					value = "氵"+"名";
				}
				bf.append(value);
			}
			destination = bf.toString();
		}
		 
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
		//350909     郭倩云         零担轻货上分拣取得是城市简称
		if ("".equals(simpleLeaveCity) || simpleLeaveCity==null ) {
			simpleLeaveCity = " ";
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

//		// 最终外场编码
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
		//350909     郭倩云     最终外场简称
		String lastFirstFinalOutName="";
		//350909     郭倩云     倒数第二外场简称
		String lastSecondFinalOutName="";
		//350909     郭倩云     倒数第三外场简称
		String lastThirdFinalOutName="";
		if(isNull((String)context.get("finaloutname"))){
			//throw new Exception("最终外场城市名称不存在，无法进行标签打印");
		}
		else {
			finalOutName = (String)context.get("finaloutname");
			if(null!=finalOutName){
				String [] finalOutNameArrays = finalOutName.split("");
				StringBuilder bf = new StringBuilder();
				for (String value : finalOutNameArrays) {
					if(value.equals("浉")){
						value = "氵"+"师";
					}else if(value.equals("洺")){
						value = "氵"+"名";
					}
					bf.append(value);
				}
				finalOutName = bf.toString();
			}
		}
		//350909     郭倩云     倒数第二外场简称
		lastFirstFinalOutName = (String)context.get("lastFirstFinalOutName");
		if(null!=lastFirstFinalOutName){
			String [] finalOutNameArrays = lastFirstFinalOutName.split("");
			StringBuilder bf = new StringBuilder();
			for (String value : finalOutNameArrays) {
				if(value.equals("浉")){
					value = "氵"+"师";
				}else if(value.equals("洺")){
					value = "氵"+"名";
				}
				bf.append(value);
			}
			lastFirstFinalOutName = bf.toString();
		}
		//350909     郭倩云     倒数第三外场简称
		lastSecondFinalOutName = (String)context.get("lastSecondFinalOutName");
		if(null!=lastSecondFinalOutName){
			String [] finalOutNameArrays = lastSecondFinalOutName.split("");
			StringBuilder bf = new StringBuilder();
			for (String value : finalOutNameArrays) {
				if(value.equals("浉")){
					value = "氵"+"师";
				}else if(value.equals("洺")){
					value = "氵"+"名";
				}
				bf.append(value);
			}
			lastSecondFinalOutName = bf.toString();
		}
		
		lastThirdFinalOutName = (String)context.get("lastThirdFinalOutName");
		if(null!=lastThirdFinalOutName){
			String [] finalOutNameArrays = lastThirdFinalOutName.split("");
			StringBuilder bf = new StringBuilder();
			for (String value : finalOutNameArrays) {
				if(value.equals("浉")){
					value = "氵"+"师";
				}else if(value.equals("洺")){
					value = "氵"+"名";
				}
				bf.append(value);
			}
			lastThirdFinalOutName = bf.toString();
		}
	
		//对接外场
		String partnerBillingLogo="";
		partnerBillingLogo=(String)context.get("partnerBillingLogo");
		// wutao 200945
		//DMANA-3745：FOSS标签打印：货物标签左上角添加收货地址行政区域
		String countyRegion = "";
		countyRegion = (String) context.get("countyRegion");
					
		// 标签打印信息对象
		LabelPrintForm form = new LabelPrintForm();
		//设置是否打星标，有自动分拣的外场需要标记星标来区分
		boolean signFlag = Boolean.parseBoolean((String)context.get("signFlag"));
		form.setSignFlag(signFlag);
		if(null!=partnerBillingLogo && !"null".equals(partnerBillingLogo) &&!"".equals(partnerBillingLogo) && partnerBillingLogo.length()>3){
			//对接外场
			form.setPartnerBillingLogo(partnerBillingLogo.substring(0, partnerBillingLogo.length()-3));
		}else{
			//对接外场
			form.setPartnerBillingLogo(partnerBillingLogo);
		}
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
		//350909     郭倩云        零担轻货上分拣取得是城市简称
		form.setSimpleLeaveCity(simpleLeaveCity);
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
		if(packing!=null && packing.length()>11){
			packing = packing.substring(0, 11);
		}
		form.setPacking(packing);
		
		//其他包装
		String otherPackage = (String) context.get("otherPackage");
		if(null != otherPackage && !"".equals(otherPackage.trim())){
			otherPackage = otherPackage.trim();
			if(otherPackage.length() > 11){
				otherPackage = otherPackage.substring(0,11);
			}
		}else{
			otherPackage = "";
		}
		form.setOtherPackage(otherPackage);
		
		//发货客户和收货客户有一个是大客户就打印“vip”
		if("Y".equals(deliveryBigCustomer) || "Y".equals(receiveBigCustomer)){
			form.setBigCustomer("Y");
		}else{
			form.setBigCustomer("N");
		}
		// 运输类型
		String transType = (String) context.get("transtype");
		// 运输性质
		form.setTransportProperty(transType);
		// 用户工号
		form.setOptNum(userName);
	/*	// 最终外场编码
		form.setFinalOutCode(deptNo);*/
		// 最终外场名称
		form.setFinalOutfield(finalOutName == null ? " " : finalOutName);
		//350909      郭倩云         最终外场简称
		form.setLastFirstFinalOutField((lastFirstFinalOutName == null ? " " : lastFirstFinalOutName));
		//350909     郭倩云          倒数第二外场简称
		form.setLastSecondFinalOutField((lastSecondFinalOutName == null ? " " : lastSecondFinalOutName));
		//350909     郭倩云          倒数第三外场简称
		form.setLastThirdFinalOutField((lastThirdFinalOutName == null ? " " : lastThirdFinalOutName));
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
			form.setIsDelivery("");
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
			if(strWeight!=null && !"".equals(strWeight) && !"null".equalsIgnoreCase(strWeight)){
				BigDecimal weight = (new BigDecimal(strWeight)).multiply(new BigDecimal(1000)).divide(new BigDecimal(pieces), 0, BigDecimal.ROUND_HALF_UP);
				String average_weight = getDigit(weight.toString(), 3);
				// 平均重量
				form.setWeight(average_weight);
			}			
		}catch (Exception e) {
			
			StringBuffer sb = new StringBuffer();
			sb.append("打印标签计算平均重量出错，\r\n");
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			sb.append(sw.toString());
			
			throw new Exception(sb.toString());
		}
		
		// 设置条码内容
		try{
			List<String> printPiecesList = new ArrayList<String>(pieces);
			List<String> barcodeList = new ArrayList<String>(pieces);
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
		
		//是否零担电子运单
		form.setIsElecLtlWaybill((String)context.get("isElecLtlWaybill"));
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
//			if(totalpieces<1000){ // <1000
				outPieces = printPiece + "/" + totalpieces ;
				
				/**
				 * dp-329834 张璠
				 * 根据需求人胡彪描述，对于零担电子运单，包装信息显示其他包装。
				 * 而未发更改时，other_package与goods_package值相同
				 * 发更改之后，goods_package包含了other_package
				 */
				outPacking = labelForm.getPacking();
				//如果是零担电子运单，包装信息截取8个字
				if("Y".equals(labelForm.getIsElecLtlWaybill())){
					if(outPacking!=null&&!"".equals(outPacking)){
						outPacking = outPacking.trim();
						if(outPacking.length()>8){
							outPacking = outPacking.substring(0,8);
						}
					}
				}
//			}
//			else { // >=1000
//				outPieces = printPiece + "/";
//				outPacking = totalpieces + " " + labelForm.getPacking();
//			}
			
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
			printStrTemplate.append("LO250,1,3,444\r\n");//第二条竖线
			printStrTemplate.append("LO190,1,3,700\r\n");//第三条竖线
			printStrTemplate.append("LO130,117,120,3\r\n");//第一条横线
			printStrTemplate.append("LO130,226,120,3\r\n");//第二条横线
			printStrTemplate.append("LO130,335,120,3\r\n");//第三条横线
			printStrTemplate.append("LO130,444,310,3\r\n");//第四条横线
			//关于满足客户个性需求，标签打印不显示“德邦物流”
			if("N".equals(labelForm.getIsPrintLogo())){
				//什么逻辑都不执行！！
			}else{
				printStrTemplate.append("A115,30,1,8,1,1,N,\"德邦物流\"\r\n");//打印公司LOGO
			}
			printStrTemplate.append("LO80,10,3,160\r\n");//LOGO下面的竖线
			printStrTemplate.append("A70,45,1,4,1,1,N,\""+labelForm.getOptNum()+"\"\r\n");//工号
			printStrTemplate.append("A48,10,1,4,1,1,N,\""+labelForm.getPrintDate()+"\"\r\n");//打印日期
			printStrTemplate.append("B125,185,1,1,2,12,75,B,\""+barcode+"\"\r\n");//条码
			printStrTemplate.append("A430,10,1,8,1,1,N,\""+labelForm.getIsDelivery()+"\"\r\n");//是否送货
			//如果为展会货，在右下角打印“展”字
			if("Y".equals(labelForm.getIsExhibit())){
				printStrTemplate.append("A178,650,1,8,1,1,N,\"展\"\r\n");}
			//如果到达部门是合伙人就显示
			if(!"".equals(labelForm.getPartnerBillingLogo()) && !(labelForm.getPartnerBillingLogo()==null)){
				if((labelForm.getPartnerBillingLogo().length()<=21 && labelForm.getPartnerBillingLogo().length()>14) || labelForm.getPartnerBillingLogo().length()>21){
					printStrTemplate.append("A130,480,1,8,1,1,N,\""+labelForm.getPartnerBillingLogo().substring(0, 7)+"\"\r\n");
					printStrTemplate.append("A95,480,1,8,1,1,N,\""+labelForm.getPartnerBillingLogo().substring(7, 14)+"\"\r\n");
					printStrTemplate.append("A60,480,1,8,1,1,N,\""+labelForm.getPartnerBillingLogo().substring(14, labelForm.getPartnerBillingLogo().length())+"\"\r\n");
				}else if(labelForm.getPartnerBillingLogo().length()<=7){
					printStrTemplate.append("A90,480,1,8,1,1,N,\""+labelForm.getPartnerBillingLogo().substring(0,labelForm.getPartnerBillingLogo().length())+"\"\r\n");
				}else{
					printStrTemplate.append("A130,480,1,8,1,1,N,\""+labelForm.getPartnerBillingLogo().substring(0, 7)+"\"\r\n");
					printStrTemplate.append("A95,480,1,8,1,1,N,\""+labelForm.getPartnerBillingLogo().substring(7,labelForm.getPartnerBillingLogo().length())+"\"\r\n");
				}	
			}else if("".equals(labelForm.getPartnerBillingLogo()) || labelForm.getPartnerBillingLogo()==null){
				printStrTemplate.append("A90,480,1,8,1,1,N,\""+""+"\"\r\n");
			}
			//打印大客户标记
			/**
			 * dp-329834 张璠
			 * 零担电子运单项目中，根据需求人胡彪描述，如果是零担电子运单，不打印大客户标记
			 */
			if("Y".equals(labelForm.getBigCustomer()) && !"Y".equals(labelForm.getIsElecLtlWaybill()) ) {
				//如果是空运，则位置向下移
				printStrTemplate.append("A385,25,1,4,1,1,N,\"VIP\"\r\n");//打印VIP字样		
			}
			//wutao ==== start
			// 将区域级字段 countyRegion与_finalOutField 进行 拼接
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
			//最终外场
			if(_finalOutField.length()<=5){
				printStrTemplate.append("A413,90,1,8,2,2,N,\""+_finalOutField+"\"\r\n");//最终外场名称
			}else if(_finalOutField.length() >= 6 && _finalOutField.length() < 11 ){
				printStrTemplate.append("A420,95,1,8,2,1,N,\""+_finalOutField+"\"\r\n");//最终外场名称
			}else if(_finalOutField.length()>10){
				printStrTemplate.append("A413,90,1,8,2,1,N,\""+_finalOutField.substring(0,10)+"\"\r\n");//最终外场名称
			}
			
			//件数/总件数
			if(labelForm.getTotalPieces() > 0
					&& labelForm.getTotalPieces() < 10) {
				printStrTemplate.append("A435,480,1,8,2,1,N,\""+outPieces+"\"\r\n");
			}else if(labelForm.getTotalPieces() >= 10
					&& labelForm.getTotalPieces() < 100){
				printStrTemplate.append("A435,470,1,8,2,1,N,\""+outPieces+"\"\r\n");
			}else if(labelForm.getTotalPieces() >= 100
					&& labelForm.getTotalPieces() < 1000) {
				printStrTemplate.append("A435,464,1,8,2,1,N,\""+outPieces+"\"\r\n");
			}else if(labelForm.getTotalPieces() >= 1000
					&& labelForm.getTotalPieces() < 10000){
				printStrTemplate.append("A435,440,1,8,2,1,N,\""+outPieces+"\"\r\n");
			}else {
				printStrTemplate.append("A435,440,1,8,1,1,N,\""+outPieces+"\"\r\n");
			}
			
			/*
			 * dp-zhangfan 329834 20160509
			 * 零担电子运单需求中， 根据需求人胡彪描述， 货物类型目前在业务上没有用处，可以去掉 
			 *
			
				// 根据 RCMS2012121901-标签界面优化需求V1.0（新） （提出人-Yue YL Luo 修改货物类型输出位置）
				//如果是大客户，则往上移
				if("Y".equals(labelForm.getBigCustomer())) {
					printStrTemplate.append("A120,480,1,8,1,1,N,\""+labelForm.getGoodsType()+"\"\r\n");//货物类型
				}else{
					printStrTemplate.append("A105,480,1,8,1,1,N,\""+labelForm.getGoodsType()+"\"\r\n");//货物类型
				}
			 */
			printStrTemplate.append("A380,446,1,8,1,1,N,\""+outPacking+"\"\r\n");//总件数 包装
			
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
			//目的站字数小于等于5的时候，自提号扩大一倍，其他的字数自动缩小一倍
			if(labelForm.getDestination().length() <= 5){
				printStrTemplate.append("A330,95,1,8,2,2,N,\""+labelForm.getDestination()+"\"\r\n");
			}else if(labelForm.getDestination().length() <= 6) {
				printStrTemplate.append("A330,95,1,8,2,1,N,\""+labelForm.getDestination()+"\"\r\n");//180
			}else if(labelForm.getDestination().length() > 6 && labelForm.getDestination().length() < 11){
				printStrTemplate.append("A330,95,1,8,2,1,N,\""+labelForm.getDestination()+"\"\r\n");//218
			}else if(labelForm.getDestination().length()>10){
				printStrTemplate.append("A330,95,1,8,2,1,N,\""+labelForm.getDestination().substring(0,11)+"\"\r\n");//218
			}
			
			//补一个指令
			printStrTemplate.append("A315,490,1,8,1,1,N,\""+" "+"\"\r\n");//218
			
//			// 打印foss 试运行特殊标记
//			if(lblPrintContext.getTestEnvInd()!=null && lblPrintContext.getTestEnvInd().length()>0 ){
//				printStrTemplate.append("A330,345,1,8,2,1,R,\""+lblPrintContext.getTestEnvInd()+"\"\r\n");
//			}
			
			printStrTemplate.append("A320,500,1,4,2,2,N,\""+labelForm.getWaybillNum1()+"\"\r\n");//单号1
			printStrTemplate.append("A256,500,1,4,2,2,N,\""+labelForm.getWaybillNum2()+"\"\r\n");//单号2
			printStrTemplate.append("A240,26,1,8,1,1,N,\""+(labelForm.getAddr1()==null?"":labelForm.getAddr1())+"\"\r\n");//编码1
			printStrTemplate.append("A240,140,1,8,1,1,N,\""+(labelForm.getAddr2()==null?"":labelForm.getAddr2())+"\"\r\n");//编码2
			printStrTemplate.append("A240,252,1,8,1,1,N,\""+(labelForm.getAddr3()==null?"":labelForm.getAddr3())+"\"\r\n");//编码3
			printStrTemplate.append("A240,365,1,8,1,1,N,\""+(labelForm.getAddr4()==null?"":labelForm.getAddr4())+"\"\r\n");//编码4
			printStrTemplate.append("A180,26,1,8,1,1,N,\""+(labelForm.getLocation1()==null?"":labelForm.getLocation1()) +"\"\r\n");//库位1
			printStrTemplate.append("A180,140,1,8,1,1,N,\""+(labelForm.getLocation2()==null?"":labelForm.getLocation2()) +"\"\r\n");//库位2
			printStrTemplate.append("A180,252,1,8,1,1,N,\""+(labelForm.getLocation3()==null?"":labelForm.getLocation3()) +"\"\r\n");//库位3
			printStrTemplate.append("A180,365,1,8,1,1,N,\""+(labelForm.getLocation4()==null?"":labelForm.getLocation4()) +"\"\r\n");//库位4
			
//			//运输性质
//			if (labelForm.getTransportProperty().indexOf("汽运") != -1 
//					|| labelForm.getTransportProperty().indexOf("空运") != -1)
//			{
//				printStrTemplate.append("A178,480,1,8,1,1,N,\""+labelForm.getTransportProperty()+"\"\r\n");
//			} else
//			{
//				printStrTemplate.append("A178,520,1,8,1,1,R,\""+labelForm.getTransportProperty()+"\"\r\n");
//			}
			
			/**
			 * @author 200945
			 * wutao 
			 * DEFECT-5335
			 * 精准大票标签，精准大票卡航和精准大票城运加黑色底框
			 * <p>由于第一次提交没有部署上，故在此提交</p>
			 */
			//运输性质目前现有产品名称分为4个字和8个字如果是4个字打印2号字体。 如果8字打1号字体
			if(labelForm.getTransportProperty().length()==4 ){
				//DMANA-8166 FOSS将PDA信息同步到GPS接口优化start liutao 2015/01/05
				 if("汽运偏线".equals(labelForm.getTransportProperty())){
					   printStrTemplate.append("A178,510,1,8,1,1,N,\""+labelForm.getTransportProperty()+"\"\r\n");
					 }else{
					   printStrTemplate.append("A178,510,1,8,1,1,R,\""+labelForm.getTransportProperty()+"\"\r\n");
					 }
			   //DMANA-8166 FOSS将PDA信息同步到GPS接口优化end liutao 2015/01/05
			}else if(labelForm.getTransportProperty().length()==6){
				printStrTemplate.append("A178,448,1,8,1,1,R,\""+labelForm.getTransportProperty()+"\"\r\n");
			}else{
				printStrTemplate.append("A178,448,1,8,1,1,N,\""+labelForm.getTransportProperty()+"\"\r\n");
			}
			
			//是否零担电子运单
			if("Y".equals(labelForm.getIsElecLtlWaybill())){
				printStrTemplate.append("A90,495,1,8,1,1,R,\"零担电子运单\"\r\n");
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

	
	/**
	 * author 350905 306486 
	 * date 2017年4月9日
	 * 零担轻货上分拣需求打印模板
	 */
	private List<byte[]> printByZebraForLightGoods(LabelPrintContext lblPrintContext) throws Exception{
		LabelPrintForm labelForm =  getLabelPrintForm(lblPrintContext);
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
			outPieces = printPiece + "/" + totalpieces ;
			/**
			 * dp-329834 张璠
			 * 根据需求人胡彪描述，对于零担电子运单，包装信息显示其他包装。
			 * 而未发更改时，other_package与goods_package值相同
			 * 发更改之后，goods_package包含了other_package
			 */
			outPacking = labelForm.getPacking();
			//如果是零担电子运单，包装信息截取8个字
			if("Y".equals(labelForm.getIsElecLtlWaybill())){
				if(outPacking!=null&&!"".equals(outPacking)){
					outPacking = outPacking.trim();
					if(outPacking.length()>8){
						outPacking = outPacking.substring(0,8);
					}
				}
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
			//画图填数据
			printStrTemplate.append("X130,1,2,440,700\r\n");//画矩形框
			//第一个参数为横坐标 第二个参数为纵横坐标 第三个参数是线的粗细宽度 第四个参数为长度
			printStrTemplate.append("LO338,1,3,700\r\n");//第一条竖线
			printStrTemplate.append("LO220,70,3,630\r\n");//第二条竖线
			printStrTemplate.append("LO175,70,3,419\r\n");//第三条竖线
			//第一个参数为横坐标 第二个参数为纵横坐标 第三个参数是线的粗细宽度 第四个参数为长度
			printStrTemplate.append("LO130,70,210,3\r\n");//第一条横线
			printStrTemplate.append("LO130,175,90,3\r\n");//第二条横线
			printStrTemplate.append("LO130,280,90,3\r\n");//第三条横线
			printStrTemplate.append("LO130,385,90,3\r\n");//第四条横线
			printStrTemplate.append("LO130,490,310,3\r\n");//第五条横线
			//关于满足客户个性需求，标签打印不显示“德邦物流”
			if("N".equals(labelForm.getIsPrintLogo())){
				//什么逻辑都不执行！！
			}else{
				printStrTemplate.append("A115,30,1,8,1,1,N,\"德邦物流\"\r\n");//打印公司LOGO
			}
			printStrTemplate.append("LO80,10,3,160\r\n");//LOGO下面的竖线
			printStrTemplate.append("A70,45,1,4,1,1,N,\""+labelForm.getOptNum()+"\"\r\n");//工号
			printStrTemplate.append("A48,10,1,4,1,1,N,\""+labelForm.getPrintDate()+"\"\r\n");//打印日期
			printStrTemplate.append("B125,185,1,1,2,12,75,B,\""+barcode+"\"\r\n");//条码
			
			//TODO 根据送字 和VIP调整VIP位置
			//		a)	运单为本部派送且VIP货物：【送】+开单时收货人地址区域+提货网点部门简称；
			//		b)	运单为本部派送且为VIP货物运单【送】+【VIP】+开单时收货人地址区域+提货网点部门简称；
			//		c)	运单为分部派送且非VIP货物：【送】+提货网点部门简称；
			//		d)	运单为分部派送且为VIP货物运单【送】+【VIP】+提货网点部门简称；
			//		e)	运单为本部自提且非VIP货物：自提区+提货网点部门简称
			//		f)	运单为本部自提且为VIP货物：【VIP】+自提区+提货网点部门简称；
			//		g)	运单为分部自提且非VIP货物：提货网点部门简称；
			//		h)	运单为分部自提且为VIP货物运单：【VIP】+提货网点部门简称；
			//【送】-【展】-【vip】
			//打印大客户标记
			//是否送货
			String isDelivery=labelForm.getIsDelivery();
			String isExhibit=labelForm.getIsExhibit();
			String isBigCustomer=labelForm.getBigCustomer();
			if(isDelivery!=null&&!"".equals(isDelivery)){
				//[送]
				printStrTemplate.append("A325,72,1,8,1,1,N,\""+labelForm.getIsDelivery()+"\"\r\n");
				if("Y".equals(isExhibit)){
					//[送]-[展]
					printStrTemplate.append("A325,152,1,8,1,1,N,\"-【展】\"\r\n");
					if("Y".equals(isBigCustomer) && !"Y".equals(labelForm.getIsElecLtlWaybill())) {
						 //[送]-[展]-[VIP]
						 printStrTemplate.append("A325,252,1,8,1,1,N,\"-【VIP】\"\r\n");//打印VIP字样
					}
				}else{
					//[送]-[VIP]
					if("Y".equals(isBigCustomer) && !"Y".equals(labelForm.getIsElecLtlWaybill())) {
						 printStrTemplate.append("A325,152,1,8,1,1,N,\"-【VIP】\"\r\n");
					}
				}
			}else{
				if("Y".equals(labelForm.getIsExhibit())){
					//[展]
					printStrTemplate.append("A325,72,1,8,1,1,N,\"【展】\"\r\n");
					if("Y".equals(isBigCustomer) && !"Y".equals(labelForm.getIsElecLtlWaybill())) {
						//[展]-[VIP]
						printStrTemplate.append("A325,152,1,8,1,1,N,\"-【VIP】\"\r\n");//打印VIP字样
					}
				}else{
					if("Y".equals(isBigCustomer) && !"Y".equals(labelForm.getIsElecLtlWaybill())) {
						//[VIP]
						printStrTemplate.append("A325,72,1,8,1,1,N,\"【VIP】\"\r\n");//打印VIP字样
					}
				}
			}
			
 		    //如果到达部门是合伙人就显示
			if(!"".equals(labelForm.getPartnerBillingLogo()) && (labelForm.getPartnerBillingLogo()!=null)){
	            if((labelForm.getPartnerBillingLogo().length()<=21 && labelForm.getPartnerBillingLogo().length()>14) || labelForm.getPartnerBillingLogo().length()>21){
	                printStrTemplate.append("A130,480,1,8,1,1,N,\""+labelForm.getPartnerBillingLogo().substring(0, 7)+"\"\r\n");
	                printStrTemplate.append("A95,480,1,8,1,1,N,\""+labelForm.getPartnerBillingLogo().substring(7, 14)+"\"\r\n");
	                printStrTemplate.append("A60,480,1,8,1,1,N,\""+labelForm.getPartnerBillingLogo().substring(14, 21)+"\"\r\n");
	            }else if(labelForm.getPartnerBillingLogo().length()<=7){
	                printStrTemplate.append("A90,480,1,8,1,1,N,\""+labelForm.getPartnerBillingLogo().substring(0,labelForm.getPartnerBillingLogo().length())+"\"\r\n");
	            }else{
	                printStrTemplate.append("A130,480,1,8,1,1,N,\""+labelForm.getPartnerBillingLogo().substring(0, 7)+"\"\r\n");
	                printStrTemplate.append("A95,480,1,8,1,1,N,\""+labelForm.getPartnerBillingLogo().substring(7,labelForm.getPartnerBillingLogo().length())+"\"\r\n");
	            }
	        }else if("".equals(labelForm.getPartnerBillingLogo()) || labelForm.getPartnerBillingLogo()==null){
	            printStrTemplate.append("A90,480,1,8,1,1,N,\""+""+"\"\r\n");
	        }
			
			String _finalOutField = "";
			//外场名称总字数
			int finalOutFieldLength = 0;
			//连接符数量
			int _count = 0;
			String first=labelForm.getLastFirstFinalOutField().trim();
			String second=labelForm.getLastSecondFinalOutField().trim();
			String third=labelForm.getLastThirdFinalOutField().trim();
			//最终外场
			if((third==null||"".equals(third))
				&&(second==null||"".equals(second))
				&&(first==null||"".equals(first))){
				 _finalOutField = "";
			}else if((third==null||"".equals(third))
					&&(second==null||"".equals(second))){
				_finalOutField = first;
				finalOutFieldLength=first.length();
			}else if(third==null||"".equals(third)){
				_finalOutField = second+"-"+first;
				finalOutFieldLength=second.length()+first.length();
				_count=1;
			}else{
				_finalOutField =third+"-"+ second+"-"+first;
				finalOutFieldLength=third.length()+second.length()+first.length();
				_count=2;
			}
			//杭州-上海-苏州
			if(finalOutFieldLength<=6&&_count==2||finalOutFieldLength<=7&&_count<2){
				int totalLength=490;
				int fontLength = finalOutFieldLength*66;
				int _countLenght=_count*33;
				int prefix =(totalLength-fontLength-_countLenght)/2;
				int suffix=prefix+fontLength+_countLenght;
				//前缀
				printStrTemplate.append("LO342,1,98,"+prefix+"\r\n");
				printStrTemplate.append("A437,"+(prefix+3)+",1,8,3,2,R,\""+_finalOutField+"\"\r\n");//最终外场名称
				if(finalOutFieldLength==6&&_count==2){
					suffix +=6;
				}
				//后缀
				if(finalOutFieldLength<=7&&_count<=1){
					printStrTemplate.append("LO342,"+(suffix-6)+",98,"+(prefix+6)+"\r\n");
				}else{
					printStrTemplate.append("LO342,"+(suffix-6)+",98,"+prefix+"\r\n");
				}
			
			}else{
				int totalLength=480;
				int fontLength = finalOutFieldLength*32;
				int _countLenght=_count*16;
				int prefix =(totalLength-fontLength-_countLenght)/2;
				int suffix=prefix+fontLength+_countLenght+8;
				//前缀
				printStrTemplate.append("LO356,1,69,"+prefix+"\r\n");
				//最终外场名称
				printStrTemplate.append("A422,"+(prefix+3)+",1,8,2,1,R,\""+_finalOutField+"\"\r\n");
				//后缀
				printStrTemplate.append("LO356,"+suffix+",69,"+(prefix+3)+"\r\n");
				printStrTemplate.append("LO425,1,18,490\r\n");
				printStrTemplate.append("LO341,1,15,490\r\n");
			}
			
			//件数/总件数
			if(labelForm.getTotalPieces() > 0
					&& labelForm.getTotalPieces() < 10) {
				printStrTemplate.append("A437,534,1,8,2,1,N,\""+outPieces+"\"\r\n");
			}else if(labelForm.getTotalPieces() >= 10
					&& labelForm.getTotalPieces() < 100){
				printStrTemplate.append("A437,527,1,8,2,1,N,\""+outPieces+"\"\r\n");
			}else if(labelForm.getTotalPieces() >= 100
					&& labelForm.getTotalPieces() < 1000) {
				printStrTemplate.append("A437,512,1,8,2,1,N,\""+outPieces+"\"\r\n");
			}else if(labelForm.getTotalPieces() >= 1000
					&& labelForm.getTotalPieces() < 10000){
				printStrTemplate.append("A437,497,1,8,2,1,N,\""+outPieces+"\"\r\n");
			}else {
				printStrTemplate.append("A437,493,1,8,2,1,N,\""+outPieces+"\"\r\n");
			}
			printStrTemplate.append("A377,493,1,8,1,1,N,\""+outPacking+"\"\r\n");//总件数 包装
	
			//始发区域
			String countyRegionDestination="";
			if(labelForm.getCountyRegion()==null||"".equals(labelForm.getCountyRegion())){
				countyRegionDestination=labelForm.getDestination();
			}else if(labelForm.getDestination()==null||"".equals(labelForm.getDestination())){
				countyRegionDestination=labelForm.getCountyRegion();
			}else{
				countyRegionDestination=labelForm.getCountyRegion()+"-"+labelForm.getDestination();
			}
			if(countyRegionDestination.length()<=12){
				printStrTemplate.append("A290,80,1,8,2,1,N,\""+countyRegionDestination+"\"\r\n");
			}else{
				printStrTemplate.append("A290,80,1,8,2,1,N,\""+countyRegionDestination.substring(0,12)+"\"\r\n");
			}
	
			//出发城市
			if(labelForm.getSimpleLeaveCity().length()==1){
	            printStrTemplate.append("A318,15,1,8,1,1,N,\""+labelForm.getSimpleLeaveCity().substring(0,1)+"\"\r\n");
	        }else if(labelForm.getSimpleLeaveCity().length()==2){
	            printStrTemplate.append("A318,15,1,8,1,1,N,\""+labelForm.getSimpleLeaveCity().substring(0,1)+"\"\r\n");
	            printStrTemplate.append("A258,15,1,8,1,1,N,\""+labelForm.getSimpleLeaveCity().substring(1,2)+"\"\r\n");
	        }else if(labelForm.getSimpleLeaveCity().length()==3){
	            printStrTemplate.append("A318,15,1,8,1,1,N,\""+labelForm.getSimpleLeaveCity().substring(0,1)+"\"\r\n");
	            printStrTemplate.append("A258,15,1,8,1,1,N,\""+labelForm.getSimpleLeaveCity().substring(1,2)+"\"\r\n");
	            printStrTemplate.append("A198,15,1,8,1,1,N,\""+labelForm.getSimpleLeaveCity().substring(2,3)+"\"\r\n");
	        }else if(labelForm.getSimpleLeaveCity().length()>=4){
				printStrTemplate.append("A328,15,1,8,1,1,N,\""+labelForm.getSimpleLeaveCity().substring(0,1)+"\"\r\n");
				printStrTemplate.append("A283,15,1,8,1,1,N,\""+labelForm.getSimpleLeaveCity().substring(1,2)+"\"\r\n");
				printStrTemplate.append("A238,15,1,8,1,1,N,\""+labelForm.getSimpleLeaveCity().substring(2,3)+"\"\r\n");
				printStrTemplate.append("A193,15,1,8,1,1,N,\""+labelForm.getSimpleLeaveCity().substring(3,4)+"\"\r\n");
			}
			printStrTemplate.append("A328,514,1,4,2,2,N,\""+labelForm.getWaybillNum1()+"\"\r\n");//单号1
			printStrTemplate.append("A274,514,1,4,2,2,N,\""+labelForm.getWaybillNum2()+"\"\r\n");//单号2
			printStrTemplate.append("A217,97,1,8,1,1,N,\""+(labelForm.getAddr1()==null?"":labelForm.getAddr1())+"\"\r\n");//编码1
			printStrTemplate.append("A217,200,1,8,1,1,N,\""+(labelForm.getAddr2()==null?"":labelForm.getAddr2())+"\"\r\n");//编码2
			printStrTemplate.append("A217,300,1,8,1,1,N,\""+(labelForm.getAddr3()==null?"":labelForm.getAddr3())+"\"\r\n");//编码3
			printStrTemplate.append("A217,410,1,8,1,1,N,\""+(labelForm.getAddr4()==null?"":labelForm.getAddr4())+"\"\r\n");//编码4
			printStrTemplate.append("A170,97,1,8,1,1,N,\""+(labelForm.getLocation1()==null?"":labelForm.getLocation1()) +"\"\r\n");//库位1
			printStrTemplate.append("A170,200,1,8,1,1,N,\""+(labelForm.getLocation2()==null?"":labelForm.getLocation2()) +"\"\r\n");//库位2
			printStrTemplate.append("A170,300,1,8,1,1,N,\""+(labelForm.getLocation3()==null?"":labelForm.getLocation3()) +"\"\r\n");//库位3
			printStrTemplate.append("A170,410,1,8,1,1,N,\""+(labelForm.getLocation4()==null?"":labelForm.getLocation4()) +"\"\r\n");//库位4


		/**
         * @author 200945
         * wutao
         * DEFECT-5335
         * 精准大票标签，精准大票卡航和精准大票城运加黑色底框
         * <p>由于第一次提交没有部署上，故在此提交</p>
         */
		//运输性质目前现有产品名称分为4个字和8个字如果是4个字打印2号字体。 如果8字打1号字体
		if(labelForm.getTransportProperty().length()==4 ){
            //DMANA-8166 FOSS将PDA信息同步到GPS接口优化start liutao 2015/01/05
            if("精准卡航".equals(labelForm.getTransportProperty())
                    ||"精准城运".equals(labelForm.getTransportProperty())
                    ||"精准包裹".equals(labelForm.getTransportProperty()))
            {
                printStrTemplate.append("A210,527,1,8,2,1,R,\""+labelForm.getTransportProperty()+"\"\r\n");
                printStrTemplate.append("LO210,490,10,210\r\n");//上填充
                printStrTemplate.append("LO130,490,15,210\r\n");//下填充
                printStrTemplate.append("LO130,490,90,40\r\n");//左填充
                printStrTemplate.append("LO130,656,90,45\r\n");//右填充
            }else{
                printStrTemplate.append("A210,527,1,8,2,1,N,\""+labelForm.getTransportProperty()+"\"\r\n");
            }
            //DMANA-8166 FOSS将PDA信息同步到GPS接口优化end liutao 2015/01/05
        }else if(labelForm.getTransportProperty().length()==6){
            printStrTemplate.append("A207,520,1,8,1,1,R,,\""+labelForm.getTransportProperty().substring(0,4)+"\"\r\n");
            printStrTemplate.append("A167,530,1,8,1,1,R,,\""+labelForm.getTransportProperty().substring(4,6)+"\"\r\n");
        }else{
            printStrTemplate.append("A207,520,1,8,1,1,N,\""+labelForm.getTransportProperty().substring(0,4)+"\"\r\n");
            printStrTemplate.append("A167,530,1,8,1,1,N,\""+labelForm.getTransportProperty().substring(4,labelForm.getTransportProperty().length())+"\"\r\n");
        }
		
		//是否零担电子运单
		if("Y".equals(labelForm.getIsElecLtlWaybill())){
			printStrTemplate.append("A90,495,1,8,1,1,R,\"零担电子运单\"\r\n");
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
