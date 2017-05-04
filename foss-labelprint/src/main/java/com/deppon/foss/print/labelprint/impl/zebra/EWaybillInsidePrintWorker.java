package com.deppon.foss.print.labelprint.impl.zebra;

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
import com.deppon.foss.print.labelprint.impl.EWaybillStubCopyForm;
/**
 * 电子运单内部打印模版
 * @author foss-218438
 */
public class EWaybillInsidePrintWorker extends LabelPrintWorker {

	@Override
	public void prepareLabelPrintForm(LabelPrintContext lblPrintContext)
			throws Exception {
		// TODO Auto-generated method stub

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
	
	@Override
	public void executePrintProcess(LabelPrintContext lblPrintContext)
			throws Exception {
		//获取字节码
		List<byte[]> cmdByteList = printEWaybillByZebra(lblPrintContext);
		
		PrintService _prtservice = findFirstPrintService(lblPrintContext);
		lblPrintContext.setmPrintService(_prtservice);
		PrintService _printService = lblPrintContext.getmPrintService();
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		for(byte[] bytes : cmdByteList){
			DocPrintJob job = _printService.createPrintJob();
			PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
			ByteArrayInputStream str = new ByteArrayInputStream( bytes);
			Doc doc = new SimpleDoc(str, flavor, null);
			job.print(doc, pras);
		}
	}

	/**
	 * 获取打印所需数据以及打印指令
	 * @author foss-218438
	 * @param lblPrintContext
	 * @return
	 */
	private List<byte[]> printEWaybillByZebra(LabelPrintContext context) throws Exception {
		List<byte[]> outputlist = new ArrayList<byte[]>();
		
		EWaybillStubCopyForm labelForm =  getLabelPrintForm(context);
		List<String> barcodeList = labelForm.getBarcode();
		for (int i = 1; i <= barcodeList.size(); i++){
			String barcode = barcodeList.get(i-1);
			String serialNo = labelForm.getSerialNoList().get(i-1);
			ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
			
			//出发城市
			String leaveCity = revertUnow(labelForm.getLeavecity());
			//进行相关数据的转化
			if(leaveCity != null && leaveCity.length() > 0){
				if (leaveCity.length()<3){
					leaveCity +="—";
				}else if(leaveCity.length()<=4){
					leaveCity = leaveCity.substring(2 ,leaveCity.length());
				}else{
					leaveCity = leaveCity.substring(2 ,4);
				}
			}
			//发货人联系方式
			String deliverPhone = "";
			if(!isBlank(labelForm.getDeliveryCustomerPhone())){
				deliverPhone = isBlank(labelForm.getDeliveryCustomerPhone()) ? "" : labelForm.getDeliveryCustomerPhone();
				if(!isBlank(labelForm.getDeliveryCustomerMobilephone())){
					deliverPhone = deliverPhone + "/" + labelForm.getDeliveryCustomerMobilephone();
				}
			}else{
				deliverPhone = labelForm.getDeliveryCustomerMobilephone();
			}
			//收货人联系方式
			String receiverPhone = "";
			if(!isBlank(labelForm.getReceiveCustomerPhone())){
				receiverPhone =  isBlank(labelForm.getReceiveCustomerPhone()) ? "" : labelForm.getReceiveCustomerPhone();
				if(!isBlank(labelForm.getReceiveCustomerMobilephone())){
					receiverPhone = receiverPhone + "/" + labelForm.getReceiveCustomerMobilephone();
				}
			}else{
				receiverPhone = labelForm.getReceiveCustomerMobilephone();
			}
			String insuranceAmount = isBlank(labelForm.getInsuranceAmount()) ? "" : labelForm.getInsuranceAmount();
			String codAmount = isBlank(labelForm.getCodAmount()) ? "" : labelForm.getCodAmount();
			
			//电子运单模板打印指令
			StringBuffer printStrTemplate = new StringBuffer("");
			printStrTemplate.append("^XA\r\n");
			printStrTemplate.append("^SEE:GB18030.DAT^CWZ,E:CLJGBK.TTF^CI26\r\n");
			//printStrTemplate.append("^^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR2,2~SD20^JUS^LRN^CI0^XZ\r\n");
			printStrTemplate.append("^MMT\r\n");
			printStrTemplate.append("^PW799\r\n");
			printStrTemplate.append("^LL1439\r\n");
			printStrTemplate.append("^LS0\r\n");
			// 设置左边距和右边距
			printStrTemplate.append("^LH" + labelForm.getLeft() + ","
					+ labelForm.getTop() + "\n");
			//画框和线
			printStrTemplate.append("^FO7,8^GB780,1437,2^FS\r\n");//矩形框
			printStrTemplate.append("^FO7,887^GB780,0,5^FS\r\n");//第1，2联分割线
			printStrTemplate.append("^FO7,88^GB780,0,2^FS\r\n");//第1联第一条横线
			printStrTemplate.append("^FO7,200^GB780,0,2^FS\r\n");//第1联第二条横线
			printStrTemplate.append("^FO7,292^GB780,0,2^FS\r\n");//第1联第三条横线
			printStrTemplate.append("^FO7,405^GB520,0,2^FS\r\n");//第1联收件和寄件中间的线
			printStrTemplate.append("^FO7,516^GB780,0,2^FS\r\n");//第1联条形码上面的线
			printStrTemplate.append("^FO7,725^GB780,0,2^FS\r\n");//第1联条形码下面的线
			printStrTemplate.append("^FO7,995^GB780,0,2^FS\r\n");//第2联收件件上面的线
			printStrTemplate.append("^FO7,1110^GB550,0,2^FS\r\n");//第2联收件和寄件中间的线
			printStrTemplate.append("^FO7,1225^GB780,0,2^FS\r\n");//第2联最后一条横线
			printStrTemplate.append("^FO526,200^GB0,320,2^FS\r\n");//第1联最长的竖线
			printStrTemplate.append("^FO302,726^GB0,165,2^FS\r\n");//第1联最后一栏左边的竖线
			printStrTemplate.append("^FO604,726^GB0,165,2^FS\r\n");//第1联最后一栏右边的竖线
			printStrTemplate.append("^FO67,292^GB0,221,2^FS\r\n");//第1联收件和寄件右边的竖线
			printStrTemplate.append("^FO67,995^GB0,230,2^FS\r\n");//第2联收件和寄件右边的竖线
			printStrTemplate.append("^FO554,995^GB0,230,2^FS\r\n");//第2联二维码左边的竖线
			
			//填充数据
			if(codAmount != "" && !"0.0".equals(codAmount)){
				printStrTemplate.append("^FT609,68^AZN,34,36^FD代收货款^FS\r\n");
			}
			//第二外场到最终外场
			printStrTemplate.append("^FT65,165^AZN,50,50^FD"+labelForm.getSecondOutfieldName()+"-"+labelForm.getLastLoadOrgName()+"^FS\r\n");
			printStrTemplate.append("^FT60,269^AZN,40,40^FD"+labelForm.getLastLoadOrgName()+"^FS\r\n");
			//运输性质
			printStrTemplate.append("^FT540,263^AZN,40,40^FD"+labelForm.getProductName()+"^FS\r\n");
			
			//第1联收件信息
			printStrTemplate.append("^FT20,350^FPV,0^AZN,28,28^FD收件^FS\r\n");
			//收件联系人和联系方式
			printStrTemplate.append("^FO70,305^AZN,20,20^FD"+labelForm.getReceiveCustomerContact()+"^FS\r\n");
			printStrTemplate.append("^FO70,325^AZN,20,20^FD"+receiverPhone+"^FS\r\n");
			if(labelForm.getReceiveCustomerAddress().length() <= 22){
				printStrTemplate.append("^FO70,345^AZN,20,20^FD"+labelForm.getReceiveCustomerAddress()+"^FS\r\n");
			}else if(labelForm.getReceiveCustomerAddress().length() >22&&labelForm.getReceiveCustomerAddress().length() <=44){
				printStrTemplate.append("^FO70,345^AZN,20,20^FD"+labelForm.getReceiveCustomerAddress().substring(0, 22)+"^FS\r\n");   
				printStrTemplate.append("^FO70,365^AZN,20,20^FD"+labelForm.getReceiveCustomerAddress().substring(22, labelForm.getReceiveCustomerAddress().length())+"^FS\r\n");
			}else if(labelForm.getReceiveCustomerAddress().length() >44){
				printStrTemplate.append("^FO70,345^AZN,20,20^FD"+labelForm.getReceiveCustomerAddress().substring(0, 22)+"^FS\r\n");  
				printStrTemplate.append("^FO70,365^AZN,20,20^FD"+labelForm.getReceiveCustomerAddress().substring(22, 44)+"^FS\r\n");  
				printStrTemplate.append("^FO70,385^AZN,20,20^FD"+labelForm.getReceiveCustomerAddress().substring(44, labelForm.getReceiveCustomerAddress().length())+"^FS\r\n");
			}	
			
			//第1联寄件信息
			printStrTemplate.append("^FT20,450^FPV,0^AZN,28,28^FD寄件^FS\r\n");
			//关于寄件人信息
			if("N".equals(labelForm.getIsHideDeliveryCustomerInfo())){
				printStrTemplate.append("^FO70,415^AZN,20,20^FD"+labelForm.getDeliveryCustomerContact()+"^FS\r\n");
				printStrTemplate.append("^FO70,435^AZN,20,20^FD"+deliverPhone+"^FS\r\n");
				if(labelForm.getDeliveryCustomerAddress().length() <= 22){
					printStrTemplate.append("^FO70,455^AZN,20,20^FD"+labelForm.getDeliveryCustomerAddress()+"^FS\r\n");
				}else if(labelForm.getDeliveryCustomerAddress().length() >22&&labelForm.getDeliveryCustomerAddress().length() <=44){
					printStrTemplate.append("^FO70,455^AZN,20,20^FD"+labelForm.getDeliveryCustomerAddress().substring(0, 22)+"^FS\r\n");   
					printStrTemplate.append("^FO70,475^AZN,20,20^FD"+labelForm.getDeliveryCustomerAddress().substring(22, labelForm.getDeliveryCustomerAddress().length())+"^FS\r\n");
				}else if(labelForm.getDeliveryCustomerAddress().length() >44){
					printStrTemplate.append("^FO70,440^AZN,20,20^FD"+labelForm.getDeliveryCustomerAddress().substring(0, 22)+"^FS\r\n");  
					printStrTemplate.append("^FO70,465^AZN,20,20^FD"+labelForm.getDeliveryCustomerAddress().substring(22, 44)+"^FS\r\n");  
					printStrTemplate.append("^FO70,490^AZN,20,20^FD"+labelForm.getDeliveryCustomerAddress().substring(44, labelForm.getDeliveryCustomerAddress().length())+"^FS\r\n");
				}	
			}
			
			if(serialNo != null && Integer.parseInt(serialNo) == 1){
				printStrTemplate.append("^FO540,310^AZN,26,26^FD件数:"+labelForm.getGoodsQtyTotal()+"^FS\r\n");	
			}else{
				String s=String.valueOf(Integer.parseInt(serialNo));
				printStrTemplate.append("^FO540,310^AZN,26,26^FD件数:" + labelForm.getGoodsQtyTotal()+ "-" +s+"^FS\r\n");
			}
			//关于计费重量
			if("N".equals(labelForm.getIsHideBillWeight())){
				printStrTemplate.append("^FO540,335^AZN,26,26^FD计费重量:"+labelForm.getBillWeight()+"^FS\r\n");
			}else{
				printStrTemplate.append("^FO540,335^AZN,26,26^FD计费重量:^FS\r\n");
			}
			
			printStrTemplate.append("^FO540,360^AZN,26,26^FD付款方式:"+labelForm.getPaidMethod()+"^FS\r\n");
			printStrTemplate.append("^FO540,385^AZN,26,26^FD保价费用:"+insuranceAmount+"^FS\r\n");
			if(labelForm.getReturnBillType().length()<=4){
				printStrTemplate.append("^FO540,410^AZN,26,26^FD返单类型:"+labelForm.getReturnBillType()+"^FS\r\n");
				printStrTemplate.append("^FO540,435^AZN,26,26^FD代收金额:"+codAmount+"^FS\r\n");
				//关于费用合计	
				if("N".equals(labelForm.getIsHideTotalFee())){
					printStrTemplate.append("^FO540,460^AZN,26,26^FD费用合计:"+labelForm.getTotalFee()+"^FS\r\n");
				}else{
					printStrTemplate.append("^FO540,460^AZN,26,26^FD费用合计:^FS\r\n");
				}
			}else{
				printStrTemplate.append("^FO540,410^AZN,26,26^FD返单类型:"+labelForm.getReturnBillType().substring(0, 4)+"^FS\r\n");
				printStrTemplate.append("^FO540,435^AZN,26,26^FD"+labelForm.getReturnBillType().substring(4,labelForm.getReturnBillType().length())+"^FS\r\n");
				printStrTemplate.append("^FO540,460^AZN,26,26^FD代收金额:"+codAmount+"^FS\r\n");
				//关于费用合计	
				if("N".equals(labelForm.getIsHideTotalFee())){
					printStrTemplate.append("^FO540,485^AZN,26,26^FD费用合计:"+labelForm.getTotalFee()+"^FS\r\n");
				}else{
					printStrTemplate.append("^FO540,485^AZN,26,26^FD费用合计:^FS\r\n");
				}
			}
			
			//第一联条形码
			printStrTemplate.append("^BY4,3,142^FT175,673^BCN,,Y,N\r\n");
			printStrTemplate.append("^FD>;"+barcode+"^FS\r\n");
			
			printStrTemplate.append("^FO20,740^AZN,21,21^FD快件到达收件人地址，收件人^FS\r\n");
			printStrTemplate.append("^FO20,765^AZN,21,21^FD或寄件人允许签收，视为描述：^FS\r\n");
			printStrTemplate.append("^FO20,790^AZN,21,21^FD您的签字代表您已验收此包裹，^FS\r\n");
			printStrTemplate.append("^FO20,815^AZN,21,21^FD并已确认包商品完好无损、没^FS\r\n");
			printStrTemplate.append("^FO20,840^AZN,21,21^FD有划痕、没有破损等质量问题。^FS\r\n");
			
			printStrTemplate.append("^FO320,736^AZN,28,28^FD签收人:^FS\r\n");
			printStrTemplate.append("^FO320,850^AZN,28,28^FD时间:^FS\r\n");
			
			//第1联二维码
			printStrTemplate.append("^FT630,900^BQ,2,4^FDLAhttp://a.app.qq.com/o/simple.jsp?pkgname=com.deppon.dpapp^FS\r\n");
			//第2联二维码
			printStrTemplate.append("^FT590,1200^BQ,2,5^FDLAhttp://a.app.qq.com/o/simple.jsp?pkgname=com.deppon.dpapp^FS\r\n");
			printStrTemplate.append("^FT600,1210^AZN,28,28^FD扫码有惊喜^FS\r\n");
			//第2联条形码
			printStrTemplate.append("^BY3,3,57^FT500,955^BCN,,Y,N\r\n");
			printStrTemplate.append("^FD>;"+labelForm.getWaybillNo()+"^FS\r\n");
			
			//第2联收件信息
			printStrTemplate.append("^FT22,1060^FPV,0^AZN,28,28^FD收件^FS\r\n");
			//收件联系人和联系方式
			printStrTemplate.append("^FO70,1005^AZN,20,20^FD"+labelForm.getReceiveCustomerContact()+"^FS\r\n");
			printStrTemplate.append("^FO70,1025^AZN,20,20^FD"+ receiverPhone+"^FS\r\n");
			if(labelForm.getReceiveCustomerAddress().length() <= 24){
				printStrTemplate.append("^FO70,1045^AZN,20,20^FD"+labelForm.getReceiveCustomerAddress()+"^FS\r\n");
			}else if(labelForm.getReceiveCustomerAddress().length() >24&&labelForm.getReceiveCustomerAddress().length() <=48){
				printStrTemplate.append("^FO70,1045^AZN,20,20^FD"+labelForm.getReceiveCustomerAddress().substring(0, 24)+"^FS\r\n");   
				printStrTemplate.append("^FO70,1065^AZN,20,20^FD"+labelForm.getReceiveCustomerAddress().substring(24, labelForm.getReceiveCustomerAddress().length())+"^FS\r\n");
			}else if(labelForm.getReceiveCustomerAddress().length() >48){
				printStrTemplate.append("^FO70,1045^AZN,20,20^FD"+labelForm.getReceiveCustomerAddress().substring(0, 24)+"^FS\r\n");  
				printStrTemplate.append("^FO70,1065^AZN,20,20^FD"+labelForm.getReceiveCustomerAddress().substring(24, 48)+"^FS\r\n");  
				printStrTemplate.append("^FO70,1085^AZN,20,20^FD"+labelForm.getReceiveCustomerAddress().substring(48, labelForm.getReceiveCustomerAddress().length())+"^FS\r\n");
			}
			
			//第2联寄件信息
			printStrTemplate.append("^FT24,1159^FPV,0^AZN,28,28^FD寄件^FS\r\n");
			//关于寄件人信息
			if("N".equals(labelForm.getIsHideDeliveryCustomerInfo())){
				printStrTemplate.append("^FO70,1120^AZN,20,20^FD"+labelForm.getDeliveryCustomerContact()+"^FS\r\n");
				printStrTemplate.append("^FO70,1140^AZN,20,20^FD"+deliverPhone+"^FS\r\n");
				if(labelForm.getDeliveryCustomerAddress().length() <= 24){
					printStrTemplate.append("^FO70,1160^AZN,20,20^FD"+labelForm.getDeliveryCustomerAddress()+"^FS\r\n");
				}else if(labelForm.getDeliveryCustomerAddress().length() >24&&labelForm.getDeliveryCustomerAddress().length() <=48){
					printStrTemplate.append("^FO70,1160^AZN,20,20^FD"+labelForm.getDeliveryCustomerAddress().substring(0, 24)+"^FS\r\n");   
					printStrTemplate.append("^FO70,1180^AZN,20,20^FD"+labelForm.getDeliveryCustomerAddress().substring(24, labelForm.getDeliveryCustomerAddress().length())+"^FS\r\n");
				}else if(labelForm.getDeliveryCustomerAddress().length() >48){
					printStrTemplate.append("^FO70,1160^AZN,20,20^FD"+labelForm.getDeliveryCustomerAddress().substring(0, 24)+"^FS\r\n");  
					printStrTemplate.append("^FO70,1180^AZN,20,20^FD"+labelForm.getDeliveryCustomerAddress().substring(24, 48)+"^FS\r\n");  
					printStrTemplate.append("^FO70,1200^AZN,20,20^FD"+labelForm.getDeliveryCustomerAddress().substring(48, labelForm.getDeliveryCustomerAddress().length())+"^FS\r\n");
				}
			}
			
			if(labelForm.getOrderNotes().length()<=25){
				printStrTemplate.append("^FO20,1238^AZN,28,28^FD备注："+labelForm.getOrderNotes()+"^FS\r\n");
			}else if(labelForm.getOrderNotes().length()>25 &&
					labelForm.getOrderNotes().length()<=52){
				printStrTemplate.append("^FO20,1238^AZN,28,28^FD备注:"+labelForm.getOrderNotes().substring(0, 25)+"^FS\r\n");
				printStrTemplate.append("^FO20,1268^AZN,28,28^FD"+labelForm.getOrderNotes().substring(25, labelForm.getOrderNotes().length())+"^FS\r\n");
			}else if(labelForm.getOrderNotes().length()>52 &&
					labelForm.getOrderNotes().length()<=79){
					printStrTemplate.append("^FO20,1238^AZN,28,28^FD备注:"+labelForm.getOrderNotes().substring(0, 25)+"^FS\r\n");
					printStrTemplate.append("^FO20,1268^AZN,28,28^FD"+labelForm.getOrderNotes().substring(25, 52)+"^FS\r\n");
					printStrTemplate.append("^FO20,1298^AZN,28,28^FD"+labelForm.getOrderNotes().substring(52, labelForm.getOrderNotes().length())+"^FS\r\n");
			}else if(labelForm.getOrderNotes().length()>79 &&
					labelForm.getOrderNotes().length()<=106){
					printStrTemplate.append("^FO20,1238^AZN,28,28^FD备注:"+labelForm.getOrderNotes().substring(0, 25)+"^FS\r\n");
					printStrTemplate.append("^FO20,1268^AZN,28,28^FD"+labelForm.getOrderNotes().substring(25, 52)+"^FS\r\n");
					printStrTemplate.append("^FO20,1298^AZN,28,28^FD"+labelForm.getOrderNotes().substring(52, 79)+"^FS\r\n");
					printStrTemplate.append("^FO20,1328^AZN,28,28^FD"+labelForm.getOrderNotes().substring(79, labelForm.getOrderNotes().length())+"^FS\r\n");
			}else if(labelForm.getOrderNotes().length()>106 &&
					labelForm.getOrderNotes().length()<=133){
				printStrTemplate.append("^FO20,1238^AZN,28,28^FD备注:"+labelForm.getOrderNotes().substring(0, 25)+"^FS\r\n");
				printStrTemplate.append("^FO20,1268^AZN,28,28^FD"+labelForm.getOrderNotes().substring(25, 52)+"^FS\r\n");
				printStrTemplate.append("^FO20,1298^AZN,28,28^FD"+labelForm.getOrderNotes().substring(52, 79)+"^FS\r\n");
				printStrTemplate.append("^FO20,1328^AZN,28,28^FD"+labelForm.getOrderNotes().substring(79, 106)+"^FS\r\n");
				printStrTemplate.append("^FO20,1358^AZN,28,28^FD"+labelForm.getOrderNotes().substring(106, labelForm.getOrderNotes().length())+"^FS\r\n");
			}else if(labelForm.getOrderNotes().length()>133 &&
					labelForm.getOrderNotes().length()<=150){
				printStrTemplate.append("^FO20,1238^AZN,28,28^FD备注:"+labelForm.getOrderNotes().substring(0, 25)+"^FS\r\n");
				printStrTemplate.append("^FO20,1268^AZN,28,28^FD"+labelForm.getOrderNotes().substring(25, 52)+"^FS\r\n");
				printStrTemplate.append("^FO20,1298^AZN,28,28^FD"+labelForm.getOrderNotes().substring(52, 79)+"^FS\r\n");
				printStrTemplate.append("^FO20,1328^AZN,28,28^FD"+labelForm.getOrderNotes().substring(79, 106)+"^FS\r\n");
				printStrTemplate.append("^FO20,1358^AZN,28,28^FD"+labelForm.getOrderNotes().substring(106, 133)+"^FS\r\n");
				printStrTemplate.append("^FO20,1388^AZN,28,28^FD"+labelForm.getOrderNotes().substring(133, labelForm.getOrderNotes().length())+"^FS\r\n");
			}else{
				printStrTemplate.append("^FO20,1238^AZN,28,28^FD备注:"+labelForm.getOrderNotes().substring(0, 25)+"^FS\r\n");
				printStrTemplate.append("^FO20,1268^AZN,28,28^FD"+labelForm.getOrderNotes().substring(25, 52)+"^FS\r\n");
				printStrTemplate.append("^FO20,1298^AZN,28,28^FD"+labelForm.getOrderNotes().substring(52, 79)+"^FS\r\n");
				printStrTemplate.append("^FO20,1328^AZN,28,28^FD"+labelForm.getOrderNotes().substring(79, 106)+"^FS\r\n");
				printStrTemplate.append("^FO20,1358^AZN,28,28^FD"+labelForm.getOrderNotes().substring(106, 133)+"^FS\r\n");
				printStrTemplate.append("^FO20,1388^AZN,28,28^FD"+labelForm.getOrderNotes().substring(133, 150)+"^FS\r\n");
			}
			
			printStrTemplate.append("^FO680,1410^AZN,28,28^FD已验视^FS\r\n");
			//^XZ
			printStrTemplate.append("^XZ\r\n");
			baos.write(printStrTemplate.toString().getBytes("GBK"));
			baos.write("\r\n".getBytes());
			StringBuffer end = new StringBuffer();
			baos.write(end.toString().getBytes());
			baos.close();
			outputlist.add(baos.toByteArray());
		}		
		return outputlist;
	}
	private EWaybillStubCopyForm getLabelPrintForm(LabelPrintContext context){
		EWaybillStubCopyForm printForm = new EWaybillStubCopyForm();
		// 运单号
		String waybillNo = (String) context.get("waybillNo");
		printForm.setWaybillNo( (String) context.get("waybillNo"));
		// 订单号
		printForm.setOrderNo((String) context.get("orderNo"));
		//订单备注
		printForm.setOrderNotes((String) context.get("orderNotes"));
		// 发货客户名称
		printForm.setDeliveryCustomerContact((String) context.get("deliveryCustomerContact"));
		// 发货客户手机
		printForm.setDeliveryCustomerMobilephone((String) context.get("deliveryCustomerMobilephone"));
		// 发货客户电话
		printForm.setDeliveryCustomerPhone((String) context.get("deliveryCustomerPhone"));
		// 发货具体地址
		printForm.setDeliveryCustomerAddress((String) context.get("deliveryCustomerAddress"));
		//收货客户名称
		printForm.setReceiveCustomerContact((String) context.get("receiveCustomerContact"));
		//收货客户电话
		printForm.setReceiveCustomerPhone((String) context.get("receiveCustomerPhone"));
		//收货客户手机
		printForm.setReceiveCustomerMobilephone((String) context.get("receiveCustomerMobilephone"));
		//收货具体地址
		printForm.setReceiveCustomerAddress((String) context.get("receiveCustomerAddress"));
		//货物总件数
		int goodTotal = Integer.parseInt(context.get("goodsQtyTotal").toString());
		printForm.setGoodsQtyTotal(goodTotal);
		//货物总体积
		printForm.setGoodsVolumeTotal((String) context.get("goodsVolumeTotal"));
		//计费重量
		printForm.setBillWeight((String) context.get("billWeight"));
		//运输性质名称
		printForm.setProductName((String) context.get("productName"));
		//开单付款方式
		printForm.setPaidMethod((String) context.get("paidMethod"));
		//总费用
		printForm.setTotalFee((String) context.get("totalFee"));
		//保价声明价值
		printForm.setInsuranceAmount((String) context.get("insuranceAmount"));
		//返单类别
		printForm.setReturnBillType((String) context.get("returnBillType"));
		//代收金额
		printForm.setCodAmount((String) context.get("codAmount"));
		//最终配载部门名称
		printForm.setLastLoadOrgName((String) context.get("lastLoadOrgName"));
		//第二外场
		printForm.setSecondOutfieldName((String) context.get("secondOutfieldName"));
		//出发城市
		printForm.setLeavecity((String) context.get("leavecity"));
		// 上边距
		printForm.setTop(context.getPrtTop());
		// 左边距
		printForm.setLeft(context.getPrtLeft());
		//是否隐藏计费重量
		printForm.setIsHideBillWeight((String) context.get("isHideBillWeight"));
		//是否隐藏发货人信息
		printForm.setIsHideDeliveryCustomerInfo((String) context.get("isHideDeliveryCustomerInfo"));
		//是否隐藏费用合计
		printForm.setIsHideTotalFee((String) context.get("isHideTotalFee"));
		//是否隐藏运费
		printForm.setIsHideTransportFee((String) context.get("isHideTransportFee"));
		//第一外场名称
		printForm.setOuterField1((String) context.get("outerField1"));
		//目的站获取
		String destination=(String)context.get("destination");
		printForm.setDestination(destination);
		//当前打印人
		printForm.setOptusernum((String) context.get("optusernum"));
		String[] printSerialNos = ((String) context.get("printSerialNos")).split(",");
		List<String> printPiecesList = new ArrayList<String>(goodTotal);
		List<String> barcodeList = new ArrayList<String>(goodTotal);
		for (int i = 0; i <printSerialNos.length; i++) {
			//运单号9位 + 流水号4位 
			barcodeList.add(waybillNo + printSerialNos[i] ); //运单号9位 + 流水号4位 
			printPiecesList.add(printSerialNos[i]);
		}
		printForm.setBarcode(barcodeList);
		printForm.setSerialNoList(printPiecesList);	
		return printForm;
	}

	/**
	 * 转换生僻字
	 * @param destination
	 * @return
	 */
	public String revertUnow(String destination){
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
		return destination;
	}
	
	/**
	 * 判定是否为空的情况
	 * @author Foss-220125-yangxiaolong
	 * @date 2015-2-2 22:22:22
	 * @param cs
	 * @return
	 */
	private boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
}
