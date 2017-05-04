package com.deppon.foss.print.labelprint.impl.zebra;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
 * Zebra Comm Label Print Program
 * @author 220125  yangxiaolong
 * @date 2015-01-14 
 */
public class ExpLabelPrintWorker extends LabelPrintWorker {
 
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
	 * 快递标签优化需求：
	 * 打印机获取页面参数时的所需字符串形式         需要Js页面的11个字段
	 * 当前用户 工号 optusernum
	 * 单号 number
	 * 打印流水号 serialnos
	 * 始发站(出发城市) leavecity
	 * 目的站编码  stationnumber             对应Dto中的destinationcode
	 * 最终外场城市名称 finaloutname              对应Dto中的LastTransCenterCity
	 * 件数 totalpieces
	 * 打印日期 printdate
	 * 是否送货 deliver
	 * 第二城市外场secLoadOrgName            
	 * 判定是否出发快递营业部isNoStop
	 */
	public LabelPrintForm getLabelPrintForm(LabelPrintContext context)
			throws Exception {
		


		// 当前用户 工号
		String userName = (String) context.get("optusernum");
		// 单号
		String number1 = (String) context.get("number");
		//判定是否出发快递营业部，用该字段进行判断
		String isNoStop=(String)context.get("isNoStop");

		//打印件的流水号
		String serialnos = (String)context.get("serialnos");
		if(isNull(serialnos)){
			throw new Exception("输入的流水号不正确，无法进行标签打印");
		}
		String[] serialnoarr = serialnos.split(",");
		
		// 始发站
		String departure = (String) context.get("leavecity");
		//第二城市外场
		String secondLoadOrgName = (String)context.get("secLoadOrgName");
		if(null!=secondLoadOrgName){
			String [] destinationArrays = secondLoadOrgName.split("");
			StringBuilder bf = new StringBuilder();
			for (String value : destinationArrays) {
				if(value.equals("浉")){
					value = "氵"+"师";
				}else if(value.equals("洺")){
					value = "氵"+"名";
				}
				bf.append(value);
			}
			secondLoadOrgName = bf.toString();
		}
		
		 

		
		if (isNull(number1)) {
			throw new Exception("运单号不正确，无法进行标签打印");
		}
		
		if ("".equals(departure) || departure==null ) {
			departure = " ";
		}

		
		
		if ("".equals(secondLoadOrgName) || secondLoadOrgName==null ) {
			secondLoadOrgName = " ";
		}
		// 目的站编码 
		String stationNumber = "0000";
		if(isNull((String)context.get("stationnumber"))){
			throw new Exception("目的站编码不存在，无法进行标签打印");
		}
		else {
			stationNumber = (String)context.get("stationnumber");
		}

		
		// 最终外场城市名称
		String finalOutName = "";
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
		
		// 标签打印信息对象
		LabelPrintForm form = new LabelPrintForm();
		// 上边距
		form.setTop(context.getPrtTop());
		// 左边距
		form.setLeft(context.getPrtLeft());
		//是否是快递部门为出发快递营业部,是否打印@标记
		if("Y".equals(isNoStop)){
					form.setIsNonStop("Y");
				}else{form.setIsNonStop("N");}
		// 目的站编码
		form.setDestionalCode(stationNumber);

		// 出发城市
		form.setLeaveCity(departure);

		// 第二城市外场
		form.setSecLoadOrgName(secondLoadOrgName);
		
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
	
		
		// 用户工号
		form.setOptNum(userName);

		// 最终外场名称
		form.setFinalOutfield(finalOutName == null ? " " : finalOutName);

		// 打印日期
		String printDateStr = (String) context.get("printdate");
		form.setPrintDate(printDateStr);
		// 是否送货 【送】
/*		if("送".equals((String) context.get("deliver"))){
			form.setIsDelivery("【送】");
		}
		else {
			form.setIsDelivery("");
		}*/
		
		
		
		//要打印的件数
		int pieces = serialnoarr.length;
		

		// 设置条码内容
		try{
			List<String> printPiecesList = new ArrayList<String>(pieces);
			List<String> barcodeList = new ArrayList<String>(pieces);
				for (int i = 0; i <pieces; i++) {
	String serial_no = serialnoarr[i];
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
		
		List<byte[]> outputlist = new ArrayList<byte[]>();
		List<String> printPiecesList = labelForm.getPrintPiecesList();
		List<String> barcodeList = labelForm.getBarcode();
		for (int i = 1; i <= barcodeList.size(); i++){
			ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
			
			String barcode = barcodeList.get(i - 1);
			String printPiece = printPiecesList.get(i - 1);
			int totalpieces = labelForm.getTotalPieces();
			String outPieces = "";

			if(totalpieces<1000){ 
				outPieces = printPiece + "/" + totalpieces ;

			}
			else { 
				outPieces = printPiece + "/";

			}
			
			String outleaveCity = labelForm.getLeaveCity();
			if (outleaveCity.length()>=3)
			{
				outleaveCity = outleaveCity.substring(2,outleaveCity.length());
			}
/*			
^XA
^CW1,E:CLJGBK.TTF
^CI28
^FO5,6^GB469,308,1^FS
^FO10,218^GB458,0,1^FS
^FO19,251^GB113,0,1^FS
^FO30,247^A1N,16,26^FH\^FD德邦快递^FS
^FO36,273^A0N,15,28^FH\^FD220125^FS
^FO25,293^A0N,16,19^FH\^FD2015-01-05^FS
^BY2,3,49^FT152,281^BCN,,Y,N
^FD>;123654789987456321^FS
^FO62,7^GB0,213,1^FS
^FO68,122^GB403,0,1^FS
^FO46,192^FPV,0^A1N,28,28^FH\^FD上海市^FS
^FO117,104^A1N,58,36^FH\^FD北京转运中心^FS
^FO75,39^A1N,24,33^FH\^FD【送】^FS
^FO378,38^A1N,21,28^FH\^FD001/02^FS
^FO114,192^A1N,54,28^FH\^FD广州转运中心^FS
^PQ1,0,1,Y^XZ
			*/
			//打印指令   666*444
			StringBuffer printStrTemplate = new StringBuffer("");
			printStrTemplate.append("^XA\r\n");
			//printStrTemplate.append("^CW1,E:CLJGBK.TTF\r\n");
			//printStrTemplate.append("^CI28\r\n");
			printStrTemplate.append("^SEE:GB18030.DAT^CW1,E:CLJGBK.TTF^CI28\r\n");
			//printStrTemplate.append("^CW1,E:CLJGBK.TTF^CI28\r\n");
			//printStrTemplate.append("^^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR2,2~SD20^JUS^LRN^CI0^XZ\r\n");
			printStrTemplate.append("^MMP\r\n");
			printStrTemplate.append("^PW480\r\n");
			printStrTemplate.append("^LL0320\r\n");
			printStrTemplate.append("^LS0\r\n");
			//设置左边距和右边距
			printStrTemplate.append("^LH" + labelForm.getLeft() + "," + labelForm.getTop() + "\n");
			//^FO5,6^GB469,308,8^FS
			printStrTemplate.append("^FO5,6^GB469,308,1^FS\r\n");
			//^FO10,218^GB458,0,8^FS
			printStrTemplate.append("^FO10,218^GB458,0,1^FS\r\n");
			//^FO19,251^GB113,0,8^FS
			printStrTemplate.append("^FO19,251^GB113,0,1^FS\r\n");
			//^FO62,7^GB0,213,8^FS
			printStrTemplate.append("^FO62,7^GB0,213,1^FS\r\n");
			//^FO68,122^GB403,0,8^FS
			printStrTemplate.append("^FO68,122^GB403,0,1^FS\r\n");		
			//获取字段进行打印
			//^FT31,229^A0N,14,14^FH\^FDDDDDDDBBBB^FS
			//printStrTemplate.append("^FO31,227^AON,20,20^FH\\^FD德邦快递^FS\r\n");
			printStrTemplate.append("^FO31,227^A1N,20,20^FH\\^FD德邦快递^FS\r\n");

			//^FT89,41^A0N,28,24^FH\^FDSS^FS
			printStrTemplate.append("^FO63,20^A1N,28,24^FH\\^FD"+labelForm.getIsDelivery().trim()+"^FS\r\n");
			
			//留作备用！！！
			
			//货物类型
			//printStrTemplate.append("^FO163,20^AZN,28,24^FDA^FS\r\n");
			//打印星标
			//printStrTemplate.append("^FO420,61^AZN,50,50^FD★^FS\r\n");
			
			
			
			//该条指令需要打印垂直字体，或者获取其字串，进行垂直拼接
			
			
			//^FT137,75^A0N,40,28^FH\^FDBEIJINGBBBBBBBJJJJJJJ^FS
			//最终外场打印
			//改行用于打印黑底白字的框
			//printStrTemplate.append("^FO135,46^GB270,0,65^FS\r\n");
	/*		if (labelForm.getFinalOutfield().length() <=2)
			{
				printStrTemplate.append("^FO140,46^GB110,0,65^FS\r\n");
				printStrTemplate.append("^FO145,61^A1N,58,48^LRY^FH\\^FD"+labelForm.getFinalOutfield()+"^FS\r\n");
			} 
			else if(labelForm.getFinalOutfield().length()==3){
				printStrTemplate.append("^FO140,46^GB170,0,65^FS\r\n");
				printStrTemplate.append("^FO145,61^A1N,58,48^LRY^FH\\^FD"+labelForm.getFinalOutfield()+"^FS\r\n");
			}
			else*/ 
			if(labelForm.getFinalOutfield().length()<=8){
				printStrTemplate.append("^FO66,46^GB410,0,65^FS\r\n");
				printStrTemplate.append("^FO71,61^A1N,58,48^LRY^FH\\^FD"+labelForm.getFinalOutfield()+"^FS\r\n");
			}else if(labelForm.getFinalOutfield().length()>8&&labelForm.getFinalOutfield().length()<=11){
				printStrTemplate.append("^FO66,46^GB410,0,65^FS\r\n");
				printStrTemplate.append("^FO71,61^A1N,50,35^LRY^FH\\^FD"+labelForm.getFinalOutfield()+"^FS\r\n");
			}else if(labelForm.getFinalOutfield().length()>11){
				printStrTemplate.append("^FO66,46^GB410,0,65^FS\r\n");
				printStrTemplate.append("^FO71,61^A1N,50,35^LRY^FH\\^FD"+labelForm.getFinalOutfield().substring(0, 11)+"^FS\r\n");
			}
			//^FT142,164^A0N,48,24^FH\^FDXIANXXXXXXXAAAAAAAAAAA^FS
			//第二外场打印
			if (labelForm.getSecLoadOrgName().length() <= 8)
			{
				printStrTemplate.append("^FO71,146^A1N,58,48^FH\\^FD"+labelForm.getSecLoadOrgName()+"^FS\r\n");
			}else if(labelForm.getSecLoadOrgName().length()>8&&labelForm.getSecLoadOrgName().length() <= 11){
				printStrTemplate.append("^FO71,146^A1N,50,35^FH\\^FD"+labelForm.getSecLoadOrgName()+"^FS\r\n");
			}else if(labelForm.getSecLoadOrgName().length()>11){
				printStrTemplate.append("^FO71,146^A1N,50,35^FH\\^FD"+labelForm.getSecLoadOrgName().substring(0, 11)+"^FS\r\n");
			}
			
		//出发城市打印
			//^FT50,148^A0B,28,28^FH\^FDSHANGHAI^FS
			if (labelForm.getLeaveCity().length() <=4)
			{
				printStrTemplate.append("^FO19,36^FPV,0^A1N,45,35^FH\\^FD"+labelForm.getLeaveCity()+"^FS\r\n");
			} else if(labelForm.getLeaveCity().length() >=5){
				printStrTemplate.append("^FO19,36^FPV,0^A1N,45,35^FH\\^FD"+labelForm.getLeaveCity().substring(0, 4)+"^FS\r\n");
			}
			
			
			//^FT50,148^A0B,28,28^FH\^FDSHANGHAI^FS
			//printStrTemplate.append("^FO19,36^FPV,0^AZN45,35^FD"+labelForm.getLeaveCity()+"^FS\r\n");
			//^FT35,257^A0N,16,28^FH\^FD220125^FS
			printStrTemplate.append("^FO35,257^A1N,25,25^FH\\^FD"+labelForm.getOptNum()+"^FS\r\n");
			//^FT35,273^A0N,13,14^FH\^FD2015-01-10^FS
			printStrTemplate.append("^FO13,277^A1N,25,25^FH\\^FD"+labelForm.getPrintDate()+"^FS\r\n");
			//^FT380,30^A0N,15,28^FH\^FD001/22^FS
			printStrTemplate.append("^FO365,23^A1N,25,25^FH\\^FD"+outPieces+"^FS\r\n");
			
			
			
			if(labelForm.getIsNonStop() != null && "Y".equals(labelForm.getIsNonStop())){
				printStrTemplate.append("^FO426,245^A1N,46,46^FH\\^FD@^FS\r\n");
		}
			
			
			//条码样式
//			^BY2,3,84^FT166,280^BCN,,Y,N
//			^FD>;123654789987456213^FS
			
			printStrTemplate.append("^FO158,223^BY2,3,52^BCN,60,Y,N^FD>;"+barcode+"^FS\r\n");//条形码
			
      //必要的结束命令
			printStrTemplate.append("^XZ\r\n");
			baos.write(printStrTemplate.toString().getBytes("utf-8"));
			baos.write("\r\n".getBytes());
			StringBuffer end = new StringBuffer();
			baos.write(end.toString().getBytes());
			baos.close();
			outputlist.add(baos.toByteArray());
		      
		}
		
		return outputlist;
			}
	}

/*//打印指令  666*555
StringBuffer printStrTemplate = new StringBuffer("");
printStrTemplate.append("^XA\r\n");
printStrTemplate.append("^SEE:GB18030.DAT^CWZ,E:CLJGBK.TTF^CI26\r\n");
//printStrTemplate.append("^^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR2,2~SD15^JUS^LRN^CI0^XZ\r\n");
printStrTemplate.append("^MMT\r\n");
printStrTemplate.append("^PW480\r\n");
printStrTemplate.append("^LL0400\r\n");
printStrTemplate.append("^LS0\r\n");
//设置左边距和右边距
printStrTemplate.append("^LH" + labelForm.getLeft() + "," + labelForm.getTop() + "\n");
//^FO8,10^GB465,383,8^FS
printStrTemplate.append("^FO8,10^GB465,383,1^FS\r\n");
//^FO18,306^GB130,0,8^FS
printStrTemplate.append("^FO18,306^GB130,0,1^FS\r\n");
//^FO8,257^GB459,0,8^FS
printStrTemplate.append("^FO8,257^GB459,0,1^FS\r\n");
//^FO65,11^GB0,248,8^FS
printStrTemplate.append("^FO65,11^GB0,248,1^FS\r\n");
//^FO74,154^GB398,0,8^FS
printStrTemplate.append("^FO74,154^GB398,0,1^FS\r\n");
 

//另一种指令   参考
//printStrTemplate.append("^FO05,675^AZN,45,45^FD德邦快递^FS\r\n");

//^FT118,226^A0N,58,28^FH\^FDGUANGZHOUZHUANYUN^FS
printStrTemplate.append("^FO118,195^AZN,50,50^FD广州转运中心^FS\r\n");
//^FT118,129^A0N,62,33^FH\^FDBEIJINGZHUANYUN^FS
printStrTemplate.append("^FO118,90^AZN,50,50^FD北京转运中心^FS\r\n");
//^FT24,300^A0N,28,16^FH\^FDDEBANGDDDBBB^FS
printStrTemplate.append("^FO24,280^AZN,30,30^FD德邦快递^FS\r\n");
//^FT88,59^A0N,31,33^FH\^FDSS^FS
printStrTemplate.append("^FO65,22^AZN,30,30^FD【送】^FS\r\n");

//该条指令需要打印垂直字体，或者获取其字串，进行垂直拼接
//^FT50,208^A0B,28,28^FH\^FDSHANGHAISHI^FS
printStrTemplate.append("^FO24,80^FPV,0^AZN40,40^FD上海市^FS\r\n");
//^FT36,335^A0N,20,28^FH\^FD220125^FS
printStrTemplate.append("^FO36,309^AZN,30,30^FD220125^FS\r\n");
//^FT20,359^A0N,22,21^FH\^FD2015-01-05^FS
printStrTemplate.append("^FO15,335^AZN,30,30^FD2015-01-05^FS\r\n");
//^FT367,49^A0N,28,28^FH\^FD0002/01^FS
printStrTemplate.append("^FO367,22^AZN,30,30^FD0001/02^FS\r\n");


//^BY2,3,83^FT164,361^BCN,,Y,N
//^FD>;123654789789654321^FS
//printStrTemplate.append("^BY2,3,83^FT164,361^BCN,,Y,N\r\n");
//printStrTemplate.append("^FD>;123654987789456123^FS\r\n");
//printStrTemplate.append("^PQ1,0,1,Y^XZ\r\n");
printStrTemplate.append("^FO185,280^BY2,3,88^BCN,60,Y,N^FD>;"+"123654987789456123"+"^FS\r\n");//条形码
*/


/*//打印指令  777*444
StringBuffer printStrTemplate = new StringBuffer("");
printStrTemplate.append("^XA\r\n");
printStrTemplate.append("^SEE:GB18030.DAT^CWZ,E:CLJGBK.TTF^CI26\r\n");
//printStrTemplate.append("^^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR2,2~SD15^JUS^LRN^CI0^XZ\r\n");
printStrTemplate.append("^MMT\r\n");
printStrTemplate.append("^PW559\r\n");
printStrTemplate.append("^LL0320\r\n");
printStrTemplate.append("^LS0\r\n");
//设置左边距和右边距
printStrTemplate.append("^LH" + labelForm.getLeft() + "," + labelForm.getTop() + "\n");
//^FO13,6^GB537,307,8^FS
printStrTemplate.append("^FO13,6^GB537,307,1^FS\r\n");
//^FO14,217^GB530,0,8^FS
printStrTemplate.append("^FO14,217^GB530,0,1^FS\r\n");
//^FO83,8^GB0,209,8^FS
printStrTemplate.append("^FO83,8^GB0,209,1^FS\r\n");
//^FO87,131^GB463,0,8^FS
printStrTemplate.append("^FO87,131^GB463,0,1^FS\r\n");
//^FO25,254^GB118,0,8^FS
printStrTemplate.append("^FO25,254^GB118,0,1^FS\r\n");
 

//另一种指令   参考
//printStrTemplate.append("^FO05,675^AZN,45,45^FD德邦快递^FS\r\n");

//^FT148,200^A0N,61,28^FH\^FDGUANGZHOUZHUANYUN^FS
printStrTemplate.append("^FO148,144^AZN,55,40^FD广州转运中心^FS\r\n");
//^FT148,110^A0N,61,31^FH\^FDBEIJINGSHIZHUANYUN^FS
printStrTemplate.append("^FO148,59^AZN,55,40^FD北京转运中心^FS\r\n");
//^FT34,252^A0N,15,14^FH\^FDDEBANGDDDBBB^FS
printStrTemplate.append("^FO31,228^AZN,20,20^FD德邦快递^FS\r\n");
//^FT101,46^A0N,31,33^FH\^FDSS^FS
printStrTemplate.append("^FO79,22^AZN,28,24^FD【送】^FS\r\n");

//该条指令需要打印垂直字体，或者获取其字串，进行垂直拼接
//^FT55,172^A0B,28,26^FH\^FDSHANGHAISHI^FS
printStrTemplate.append("^FO23,36^FPV,0^AZN45,35^FD上海市^FS\r\n");
//^FT41,278^A0N,16,28^FH\^FD220125^FS
printStrTemplate.append("^FO35,257^AZN,25,25^FD220125^FS\r\n");
//^FT26,293^A0N,13,21^FH\^FD2015-01-05^FS
printStrTemplate.append("^FO20,277^AZN,25,25^FD2015-01-05^FS\r\n");
//^FT442,40^A0N,25,28^FH\^FD0001/02^FS
printStrTemplate.append("^FO398,25^AZN,25,25^FD0001/02^FS\r\n");


//^BY2,3,84^FT166,280^BCN,,Y,N
//^FD>;123654789987456213^FS
^BY2,3,57^FT216,285^BCN,,Y,N
^FD>;123654789789456123^FS
//printStrTemplate.append("^PQ1,0,1,Y^XZ\r\n");

printStrTemplate.append("^FO180,223^BY2,3,52^BCN,60,Y,N^FD>;"+"123654987789456123"+"^FS\r\n");//条形码
*/

