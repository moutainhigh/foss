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
import com.deppon.foss.print.labelprint.impl.EWaybillStubCopyForm;
import com.deppon.foss.print.labelprint.impl.LabelPrintForm;
/**
 * 德邦存根联打印模板
 * @author Foss-105888-Zhangxingwang
 * @date 2014-8-19 20:31:11
 */
public class EWaybillOwnStubCopyPrintWorker extends LabelPrintWorker {

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
	 * @date 2014-7-2 08:50:17
	 * @author Foss-105888-Zhangxingwang
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
			
			//最终配载部门的转化
			String lastLoadOrgName = revertUnow(labelForm.getLastLoadOrgName());
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
			//第二城市外场
			String secondOutField = revertUnow(labelForm.getSecondOutfieldName());
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
			String accountNo = isBlank(labelForm.getAccountNo()) ? "" : labelForm.getAccountNo();
			String codeAmount = isBlank(labelForm.getCodAmount()) ? "" : labelForm.getCodAmount();
			
			//打印指令
			StringBuffer printStrTemplate = new StringBuffer("");
			printStrTemplate.append("^XA\r\n");
			printStrTemplate.append("^SEE:GB18030.DAT^CWZ,E:CLJGBK.TTF^CI26\r\n");
			printStrTemplate.append("^MMT\r\n");
			printStrTemplate.append("^PW719\r\n");
			printStrTemplate.append("^LL1600\r\n");
			printStrTemplate.append("^LS0\r\n");
			//设置左边距和右边距
			printStrTemplate.append("^LH" + labelForm.getLeft() + "," + labelForm.getTop() + "\n");
			printStrTemplate.append("^FO4,10^GB687,580,1^FS\r\n");//第一个框框

			printStrTemplate.append("^FO348,88^GB335,0,1^FS\r\n");
			
			//printStrTemplate.append("^FO4,490^GB667,0,1^FS\r\n");
			printStrTemplate.append("^FO4,336^GB687,0,1^FS\r\n");
			printStrTemplate.append("^FO4,240^GB687,0,1^FS\r\n");
			printStrTemplate.append("^FO4,166^GB687,0,1^FS\r\n");
			
			printStrTemplate.append("^FO348,10^GB0,156,1^FS\r\n");//第二外场与最终外场的竖线
			
			printStrTemplate.append("^FO200,338^GB0,250,1^FS\r\n");//货物与增值服务竖线
			printStrTemplate.append("^FO440,338^GB0,250,1^FS\r\n");//运输性质与增值服务竖线
			
			printStrTemplate.append("^FO4,655^GB687,569,1^FS\r\n");//第二个框框
			printStrTemplate.append("^FO4,1055^GB245,0,1^FS\r\n");
			printStrTemplate.append("^FO4,902^GB687,0,1^FS\r\n");
			printStrTemplate.append("^FO4,801^GB687,0,1^FS\r\n");
			printStrTemplate.append("^FO4,732^GB687,0,1^FS\r\n");
			printStrTemplate.append("^FO250,900^GB0,324,1^FS\r\n");//这跟竖线
			
			printStrTemplate.append("^FO60,30^AZN,45,45^FD德邦快递^FS\r\n");
			/*printStrTemplate.append("^FO194,25^AZN,24,24^FD400 830 5555^FS\r\n");
			printStrTemplate.append("^FO194,50^AZN,21,21^FDwww.deppon.com^FS\r\n");*/

			printStrTemplate.append("^FO38,83^BY2,3,52^BCN,60,Y,N^FD>;"+barcode+"^FS\r\n");//条形码
			
			//^FO426,24^GB243,0,48^FS
			//^FO427,39^AZN,38,38
			//^LRY^FD廊坊枢纽中心^FS
			//最终配载部门
			//最终配载部门
			printStrTemplate.append("^FO400,28^GB290,0,48^FS\r\n");
			if(lastLoadOrgName.length() <= 6){
				printStrTemplate.append("^FO400,39^AZN,40,40^LRY^FD"+lastLoadOrgName+"^FS\r\n");
			}else if(lastLoadOrgName.length() <= 7){
				printStrTemplate.append("^FO400,39^AZN,36,36^LRY^FD"+lastLoadOrgName+"^FS\r\n");
			}else if(lastLoadOrgName.length() <= 8){
				printStrTemplate.append("^FO400,39^AZN,32,32^FD"+lastLoadOrgName+"^FS\r\n");
			}else if(lastLoadOrgName.length() <= 9){
				printStrTemplate.append("^FO400,39^AZN,28,28^FD"+lastLoadOrgName+"^FS\r\n");
			}else{
				printStrTemplate.append("^FO400,39^AZN,24,24^FD"+lastLoadOrgName.substring(0, 10)+"^FS\r\n");
			}
			
			if(labelForm.getLeavecity().length() < 3){
				printStrTemplate.append("^FO345,110^AZN,24,24^FD"+labelForm.getLeavecity()+"-^FS\r\n");
			}else {
				printStrTemplate.append("^FO345,97^AZN,24,24^FD"+labelForm.getLeavecity().substring(0, 2)+"-^FS\r\n");
				printStrTemplate.append("^FO348,127^AZN,24,24^FD"+leaveCity+"^FS\r\n");
			}
			
			if(secondOutField.length() <= 6){
				printStrTemplate.append("^FO400,110^AZN,40,40^LRY^FD"+secondOutField+"^FS\r\n");				
			}else if(secondOutField.length() <= 7){
				printStrTemplate.append("^FO400,110^AZN,36,36^LRY^FD"+secondOutField+"^FS\r\n");				
			}else if(secondOutField.length() <= 8){
				printStrTemplate.append("^FO400,110^AZN,32,32^LRY^FD"+secondOutField+"^FS\r\n");
			}else if(secondOutField.length() <= 9){
				printStrTemplate.append("^FO400,110^AZN,28,28^LRY^FD"+secondOutField+"^FS\r\n");
			}else{
				printStrTemplate.append("^FO400,110^AZN,24,24^LRY^FD"+secondOutField.substring(0, 10)+"^FS\r\n");
			}
			if(labelForm.getIsDeliver() != null && "Y".equals(labelForm.getIsDeliver())){
				printStrTemplate.append("^FO338,41^AZN,24,24^FD【送】^FS\r\n");
			}
			if(labelForm.getIsPrintAt() != null && "Y".equals(labelForm.getIsPrintAt())){
				printStrTemplate.append("^FO645,97^AZN,55,55^FD@^FS\r\n");
			}
			if(labelForm.getIsPrintStar() != null && "Y".equals(labelForm.getIsPrintStar())){
				printStrTemplate.append("^FO645,28^AZN,55,55^FD★^FS\r\n");
			}

			if(labelForm.getDeliveryBigCustomer() != null && "Y".equals(labelForm.getDeliveryBigCustomer())){
				printStrTemplate.append("^FO4,166^GB687,0,74^FS\r\n");
				//寄件人电话、联系方式
				printStrTemplate.append("^FO05,175^AZN,24,24^LRY^FD"+"寄件人信息VIP"+"^FS\r\n");
			}else{
				//寄件人电话、联系方式
				printStrTemplate.append("^FO05,175^AZN,24,24^LRY^FD"+"寄件人信息"+"^FS\r\n");
			}
			
			String deliverInfo = labelForm.getDeliveryCustomerContact()+"   "+ deliverPhone;
			if(deliverInfo.length() <= 28){
				printStrTemplate.append("^FO05,210^AZN,24,24^FD" + deliverInfo + "^FS\r\n");
			}else if(deliverInfo.length() <= 56){
				printStrTemplate.append("^FO05,205^AZN,24,24^FB687,2^FD" + deliverInfo + "^FS\r\n");
			}else{
				printStrTemplate.append("^FO05,205^AZN,24,24^FB687,2^FD" + deliverInfo.substring(0, 56) + "^FS\r\n");
			}
			//收件人信息
			printStrTemplate.append("^FO05,245^AZN,24,24^FD收件人信息^FS\r\n");
			if(labelForm.getReceiveCustomerAddress().length() <= 28){
				printStrTemplate.append("^FO05,270^AZN,24,24^FD"+labelForm.getReceiveCustomerContact()+"   "+ receiverPhone+"^FS\r\n");
				printStrTemplate.append("^FO05,295^AZN,24,24^FB687,1^FD"+labelForm.getReceiveCustomerAddress()+"^FS\r\n");
			}else if(labelForm.getReceiveCustomerAddress().length() <= 56){
				printStrTemplate.append("^FO05,268^AZN,24,24^FD"+labelForm.getReceiveCustomerContact()+"   "+ receiverPhone+"^FS\r\n");
				printStrTemplate.append("^FO05,292^AZN,24,24^FB687,2^FD"+labelForm.getReceiveCustomerAddress()+"^FS\r\n");
			}else{
				printStrTemplate.append("^FO05,268^AZN,24,24^FD"+labelForm.getReceiveCustomerContact()+"   "+ receiverPhone+"^FS\r\n");
				printStrTemplate.append("^FO05,292^AZN,24,24^FB687,2^FD"+labelForm.getReceiveCustomerAddress().substring(0, 56)+"^FS\r\n");
			}
			
			printStrTemplate.append("^FO05,340^AZN,24,24^FD货物信息^FS\r\n");
			//进行货物名称长度进行 限制
			String goodNameFirst = "品名:" + labelForm.getGoodsName();
			if(goodNameFirst.length() <= 8){
				printStrTemplate.append("^FO05,370^AZN,24,24^FD"+goodNameFirst+"^FS\r\n");
				//不对件数进行判定
				if(serialNo != null && Integer.parseInt(serialNo) == 1){
					printStrTemplate.append("^FO05,400^AZN,24,24^FD件数:" +labelForm.getGoodsQtyTotal()+"^FS\r\n");
				}else{
					printStrTemplate.append("^FO05,400^AZN,24,24^FD件数:" +serialNo + "/" + labelForm.getGoodsQtyTotal()+"^FS\r\n");
				}
				printStrTemplate.append("^FO05,430^AZN,24,24^FD重量:"+labelForm.getGoodsWeightTotal()+"^FS\r\n");
			}else if(goodNameFirst.length() <= 16){
				printStrTemplate.append("^FO05,370^AZN,24,24^FB196,2^FD"+goodNameFirst+"^FS\r\n");
				//不对件数进行判定
				if(serialNo != null && Integer.parseInt(serialNo) == 1){
					printStrTemplate.append("^FO05,420^AZN,24,24^FD件数:" +labelForm.getGoodsQtyTotal()+"^FS\r\n");
				}else{
					printStrTemplate.append("^FO05,420^AZN,24,24^FD件数:" +serialNo + "/" + labelForm.getGoodsQtyTotal()+"^FS\r\n");
				}
				printStrTemplate.append("^FO05,450^AZN,24,24^FD重量:"+labelForm.getGoodsWeightTotal()+"^FS\r\n");
			}else{
				printStrTemplate.append("^FO05,370^AZN,24,24^FB196,2^FD"+goodNameFirst.substring(0, 16)+"^FS\r\n");
				//不对件数进行判定
				if(serialNo != null && Integer.parseInt(serialNo) == 1){
					printStrTemplate.append("^FO05,420^AZN,24,24^FD件数:" +labelForm.getGoodsQtyTotal()+"^FS\r\n");
				}else{
					printStrTemplate.append("^FO05,420^AZN,24,24^FD件数:" +serialNo + "/" + labelForm.getGoodsQtyTotal()+"^FS\r\n");
				}
				printStrTemplate.append("^FO05,450^AZN,24,24^FD重量:"+labelForm.getGoodsWeightTotal()+"^FS\r\n");
			}
			
			printStrTemplate.append("^FO205,340^AZN,24,24^FD增值服务^FS\r\n");
			printStrTemplate.append("^FO205,370^AZN,24,24^FD保价金额:"+labelForm.getInsuranceAmount()+"^FS\r\n");
			String returnBillType = "签回单:" + labelForm.getReturnBillType();
			if(returnBillType.length() <= 9){
				printStrTemplate.append("^FO205,400^AZN,24,24^FD"+returnBillType+"^FS\r\n");
				printStrTemplate.append("^FO205,430^AZN,24,24^FD代收货款:"+codeAmount+"^FS\r\n");
				printStrTemplate.append("^FO205,460^AZN,24,24^FD代收账号:"+accountNo+"^FS\r\n");
			}else if(returnBillType.length() <= 18){
				printStrTemplate.append("^FO205,395^AZN,24,24^FB246,2^FD"+returnBillType+"^FS\r\n");
				printStrTemplate.append("^FO205,445^AZN,24,24^FD代收货款:"+codeAmount+"^FS\r\n");
				printStrTemplate.append("^FO205,470^AZN,24,24^FD代收账号:"+accountNo+"^FS\r\n");
			}else{
				printStrTemplate.append("^FO205,395^AZN,24,24^FB246,2^FD"+returnBillType.substring(0, 18)+"^FS\r\n");
				printStrTemplate.append("^FO205,445^AZN,24,24^FD代收货款:"+codeAmount+"^FS\r\n");
				printStrTemplate.append("^FO205,470^AZN,24,24^FD代收账号:"+accountNo+"^FS\r\n");
			}
			
			printStrTemplate.append("^FO444,340^AZN,24,24^FD运输性质:"+labelForm.getProductName()+"^FS\r\n");
			printStrTemplate.append("^FO444,370^AZN,24,24^FD计费重量:"+labelForm.getBillWeight()+"^FS\r\n");
			printStrTemplate.append("^FO444,400^AZN,24,24^FD付款方式:"+labelForm.getPaidMethod()+"^FS\r\n");
			printStrTemplate.append("^FO490,440^AZN,45,45^FD已验视^FS\r\n");
//			printStrTemplate.append("^FO444,430^AZN,24,24^FD运费:"+labelForm.getTransportFee()+"^FS\r\n");
//			printStrTemplate.append("^FO444,460^AZN,24,24^FD总费用:"+labelForm.getTotalFee()+"^FS\r\n");

			/*printStrTemplate.append("^FO05,500^AZN,24,24^FD收件员:^FS\r\n");
			printStrTemplate.append("^FO05,545^AZN,24,24^FD派件员:^FS\r\n");

			printStrTemplate.append("^FO205,500^AZN,24,24^FD寄方签名:^FS\r\n");
			printStrTemplate.append("^FO410,565,1^AZN,24,24^FD月      日^FS\r\n");
			
			printStrTemplate.append("^FO446,500^AZN,24,24^FD收方签名:^FS\r\n");
			printStrTemplate.append("^FO640,565,1^AZN,24,24^FD月      日^FS\r\n");*/
			
			printStrTemplate.append("^FO05,675^AZN,45,45^FD德邦快递^FS\r\n");
			printStrTemplate.append("^FO194,670^AZN,24,24^FD400 830 5555^FS\r\n");
			printStrTemplate.append("^FO194,695^AZN,21,21^FDwww.deppon.com^FS\r\n");
			printStrTemplate.append("^FO10,755^AZN,45,45^FD运单号码:"+labelForm.getWaybillNo()+"^FS\r\n");

			printStrTemplate.append("^FO05,810^AZN,24,24^FD收件人信息^FS\r\n");
			//收件人详细信息
			if(labelForm.getReceiveCustomerAddress().length() <= 28){
				printStrTemplate.append("^FO05,840^AZN,24,24^FD"+labelForm.getReceiveCustomerContact()+"   "+receiverPhone +"^FS\r\n");
				printStrTemplate.append("^FO05,870^AZN,24,24^FB687,1^FD"+labelForm.getReceiveCustomerAddress()+"^FS\r\n");
			}else if(labelForm.getReceiveCustomerAddress().length() <= 56){
				printStrTemplate.append("^FO05,832^AZN,24,24^FD"+labelForm.getReceiveCustomerContact()+"   "+receiverPhone +"^FS\r\n");
				printStrTemplate.append("^FO05,858^AZN,24,24^FB687,2^FD"+labelForm.getReceiveCustomerAddress()+"^FS\r\n");
			}else{
				printStrTemplate.append("^FO05,832^AZN,24,24^FD"+labelForm.getReceiveCustomerContact()+"   "+receiverPhone +"^FS\r\n");
				printStrTemplate.append("^FO05,858^AZN,24,24^FB687,2^FD"+labelForm.getReceiveCustomerAddress().substring(0, 56)+"^FS\r\n");
			}
			printStrTemplate.append("^FO05,910^AZN,24,24^FD货物信息^FS\r\n");
			String goodName = "品名:" + labelForm.getGoodsName();
			if(goodName.length() <= 9){
				printStrTemplate.append("^FO05,940^AZN,24,24^FD"+goodName+"^FS\r\n");
				if(serialNo != null && Integer.parseInt(serialNo) == 1){
					//打印出分割线
					printStrTemplate.append("^FO4,490^GB667,0,1^FS\r\n");
					printStrTemplate.append("^FO05,970^AZN,24,24^FD件数:"+labelForm.getGoodsQtyTotal()+"^FS\r\n");
					printStrTemplate.append("^FO05,500^AZN,24,24^FD收件员:^FS\r\n");
					printStrTemplate.append("^FO05,545^AZN,24,24^FD派件员:^FS\r\n");

					printStrTemplate.append("^FO205,500^AZN,24,24^FD寄方签名:^FS\r\n");
					printStrTemplate.append("^FO410,565,1^AZN,24,24^FD月      日^FS\r\n");
					      
					printStrTemplate.append("^FO446,500^AZN,24,24^FD收方签名:^FS\r\n");
					printStrTemplate.append("^FO640,565,1^AZN,24,24^FD月      日^FS\r\n");
					printStrTemplate.append("^FO668,395^FPV,0^AZN,28,19^FD德邦快递存根联^FS\r\n");
				}else{
					printStrTemplate.append("^FO05,970^AZN,24,24^FD件数:"+serialNo +"/"+labelForm.getGoodsQtyTotal()+"^FS\r\n");
					printStrTemplate.append("^FO668,395^FPV,0^AZN,28,19^FD收件客户存根联^FS\r\n");
				}
				printStrTemplate.append("^FO05,1000^AZN,24,24^FD重量:"+labelForm.getGoodsWeightTotal()+"^FS\r\n");
			}else if(goodName.length() <= 18){
				printStrTemplate.append("^FO05,940^AZN,24,24^FB226,2^FD"+goodName+"^FS\r\n");
				if(serialNo != null && Integer.parseInt(serialNo) == 1){
					//打印出分割线
					printStrTemplate.append("^FO4,490^GB667,0,1^FS\r\n");
					printStrTemplate.append("^FO05,990^AZN,24,24^FD件数:"+labelForm.getGoodsQtyTotal()+"^FS\r\n");
					
					printStrTemplate.append("^FO05,500^AZN,24,24^FD收件员:^FS\r\n");
					printStrTemplate.append("^FO05,545^AZN,24,24^FD派件员:^FS\r\n");

					printStrTemplate.append("^FO205,500^AZN,24,24^FD寄方签名:^FS\r\n");
					printStrTemplate.append("^FO410,565,1^AZN,24,24^FD月      日^FS\r\n");
					      
					printStrTemplate.append("^FO446,500^AZN,24,24^FD收方签名:^FS\r\n");
					printStrTemplate.append("^FO640,565,1^AZN,24,24^FD月      日^FS\r\n");
					printStrTemplate.append("^FO668,395^FPV,0^AZN,28,19^FD德邦快递存根联^FS\r\n");
				}else{
					printStrTemplate.append("^FO05,990^AZN,24,24^FD件数:"+serialNo +"/"+labelForm.getGoodsQtyTotal()+"^FS\r\n");
					printStrTemplate.append("^FO668,395^FPV,0^AZN,28,19^FD收件客户存根联^FS\r\n");
				}
				printStrTemplate.append("^FO05,1020^AZN,24,24^FD重量:"+labelForm.getGoodsWeightTotal()+"^FS\r\n");
			}else{
				printStrTemplate.append("^FO05,940^AZN,24,24^FB226,2^FD"+goodName.substring(0, 18)+"^FS\r\n");
				if(serialNo != null && Integer.parseInt(serialNo) == 1){
					//打印出分割线
					printStrTemplate.append("^FO4,490^GB667,0,1^FS\r\n");
					printStrTemplate.append("^FO05,990^AZN,24,24^FD件数:"+labelForm.getGoodsQtyTotal()+"^FS\r\n");
					
					printStrTemplate.append("^FO05,500^AZN,24,24^FD收件员:^FS\r\n");
					printStrTemplate.append("^FO05,545^AZN,24,24^FD派件员:^FS\r\n");

					printStrTemplate.append("^FO205,500^AZN,24,24^FD寄方签名:^FS\r\n");
					printStrTemplate.append("^FO410,565,1^AZN,24,24^FD月      日^FS\r\n");
					      
					printStrTemplate.append("^FO446,500^AZN,24,24^FD收方签名:^FS\r\n");
					printStrTemplate.append("^FO640,565,1^AZN,24,24^FD月      日^FS\r\n");
					printStrTemplate.append("^FO668,395^FPV,0^AZN,28,19^FD德邦快递存根联^FS\r\n");
				}else{
					printStrTemplate.append("^FO05,990^AZN,24,24^FD件数:"+serialNo +"/"+labelForm.getGoodsQtyTotal()+"^FS\r\n");
					printStrTemplate.append("^FO668,395^FPV,0^AZN,28,19^FD收件客户存根联^FS\r\n");
				}
				printStrTemplate.append("^FO05,1020^AZN,24,24^FD重量:"+labelForm.getGoodsWeightTotal()+"^FS\r\n");
			}
			
			printStrTemplate.append("^FO05,1060^AZN,24,24^FD运输性质:"+labelForm.getProductName()+"^FS\r\n");
			printStrTemplate.append("^FO05,1090^AZN,24,24^FD计费重量:"+labelForm.getBillWeight()+"^FS\r\n");
			printStrTemplate.append("^FO05,1120^AZN,24,24^FD付款方式:"+labelForm.getPaidMethod()+"^FS\r\n");
//			printStrTemplate.append("^FO05,1120^AZN,24,24^FD总费用:"+labelForm.getTotalFee()+"^FS\r\n");
//			printStrTemplate.append("^FO05,1150^AZN,24,24^FD运费:"+labelForm.getTransportFee()+"^FS\r\n");
			
			//标示标签属性的竖线
			printStrTemplate.append("^FO667,336^GB0,252,1^FS\r\n");

			printStrTemplate.append("^FO667,902^GB0,323,1^FS\r\n");//标示标签属性的竖线
			printStrTemplate.append("^FO668,1030^FPV,0^AZN,28,19^FD收件客户存根联^FS\r\n");
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
		printForm.setWaybillNo(waybillNo);
		// 订单号
		printForm.setOrderNo((String) context.get("orderNo"));
		// 付款方式
		printForm.setOrderPaidMethod((String) context.get("orderPaidMethod"));
		
		// 发货客户名称
		printForm.setDeliveryCustomerName((String) context.get("deliveryCustomerContact"));
		// 发货客户手机
		printForm.setDeliveryCustomerMobilephone((String) context.get("deliveryCustomerMobilephone"));
		// 发货客户电话
		printForm.setDeliveryCustomerPhone((String) context.get("deliveryCustomerPhone"));
		// 发货客户联系人
		printForm.setDeliveryCustomerContact((String) context.get("deliveryCustomerContact"));
		// 发货具体地址
		printForm.setDeliveryCustomerAddress((String) context.get("deliveryCustomerAddress"));
		
		//收货客户名称
		printForm.setReceiveCustomerName((String) context.get("receiveCustomerContact"));
		//收货客户手机
		printForm.setReceiveCustomerPhone((String) context.get("receiveCustomerPhone"));
		//收货客户手机/联系方式
		printForm.setReceiveCustomerMobilephone((String) context.get("receiveCustomerMobilephone"));
		printForm.setReceiveCustomerContact((String) context.get("receiveCustomerContact"));
		//收获具体地址
		printForm.setReceiveCustomerAddress((String) context.get("receiveCustomerAddress"));
		
		//收货部门
		printForm.setReceiveOrgName((String) context.get("receiveOrgName"));
		//开单部门
		printForm.setCreateOrgName((String) context.get("createOrgName"));
		//收货部门
		printForm.setCustomerPickupOrgName((String) context.get("customerPickupOrgName"));
		//货物名称
		printForm.setGoodsName((String) context.get("goodsName"));
		//货物总件数
		int goodTotal = Integer.parseInt(context.get("goodsQtyTotal").toString());
		printForm.setGoodsQtyTotal(goodTotal);
		//货物总体积
		printForm.setGoodsVolumeTotal((String) context.get("goodsVolumeTotal"));
		//货物总重量
		printForm.setGoodsWeightTotal((String) context.get("goodsWeightTotal"));
		//计费重量
		printForm.setBillWeight((String) context.get("billWeight"));
		//运输性质名称
		printForm.setProductName((String) context.get("productName"));
		//提货方式
		printForm.setPaidMethod((String) context.get("paidMethod"));
		//开单付款方式
		printForm.setTotalFee((String) context.get("totalFee"));
		//总运费
		printForm.setTransportFee((String) context.get("transportFee"));
		
		//货物类型名称
		printForm.setGoodsTypeName((String) context.get("goodsTypeName"));
		//是否贵重物品
		printForm.setPreciousGoods((String) context.get("preciousGoods"));
		//货物包装
		printForm.setGoodsPackage((String) context.get("goodsPackage"));
		//保价声明价值
		printForm.setInsuranceAmount((String) context.get("insuranceAmount"));
		//包装费
		printForm.setPackageFee((String) context.get("packageFee"));
		//返单类别
		printForm.setReturnBillType((String) context.get("returnBillType"));
		//代收金额
		printForm.setCodAmount((String) context.get("codAmount"));
		//代收货款账号
		printForm.setAccountNo((String) context.get("accountNo"));
		//快递员(收)
		printForm.setDeliverMan((String) context.get("deliverMan"));
		//收获具体地址
		printForm.setReceiveDate((String) context.get("receiveDate"));
		//收获具体地址
		printForm.setReceiverMan((String) context.get("receiverMan"));
		//派送日期
		printForm.setDeliverDate((String) context.get("deliverDate"));
		//第二城市外场名称
		printForm.setSecondOutfieldName((String) context.get("secondOutfieldName"));
		//最终配载部门名称
		printForm.setLastLoadOrgName((String) context.get("lastLoadOrgName"));
		//出发城市
		printForm.setLeavecity((String) context.get("leavecity"));
		//派送日期
		printForm.setDeliverDate((String) context.get("deliverDate"));
		// 上边距
		printForm.setTop(context.getPrtTop());
		// 左边距
		printForm.setLeft(context.getPrtLeft());
		//六大外场名称
		printForm.setOuterField1((String) context.get("outerField1"));
		printForm.setOuterField2((String) context.get("outerField2"));
		printForm.setOuterField3((String) context.get("outerField3"));
		printForm.setOuterField4((String) context.get("outerField4"));
		printForm.setOuterField5((String) context.get("outerField5"));
		printForm.setOuterField6((String) context.get("outerField6"));
		
		//六大库位
		printForm.setLocation1((String) context.get("location1"));
		printForm.setLocation2((String) context.get("location2"));
		printForm.setLocation3((String) context.get("location3"));
		printForm.setLocation4((String) context.get("location4"));
		printForm.setLocation5((String) context.get("location5"));
		printForm.setLocation6((String) context.get("location6"));
		//是否打印送字
		String isDeliver = (String) context.get("isDeliver");
		printForm.setIsDeliver(isDeliver);
		//货物总件数
		String isPrintStar = (String) context.get("isPrintStar");
		printForm.setIsPrintStar(isPrintStar);
		
		//当前打印人
		printForm.setOptusernum((String) context.get("optusernum"));
		//是否打印@
		String isPrintAt = (String) context.get("isPrintAt");
		printForm.setIsPrintAt(isPrintAt);
		//发货大客户
		printForm.setDeliveryBigCustomer((String) context.get("deliveryBigCustomer"));
		//收获大客户
		printForm.setReceiveBigCustomer((String) context.get("receiveBigCustomer"));
		
		String stationnumber = (String) context.get("stationnumber");
		String[] printSerialNos = ((String) context.get("printSerialNos")).split(",");
		List<String> printPiecesList = new ArrayList(goodTotal);
		List<String> barcodeList = new ArrayList(goodTotal);
		for (int i = 0; i <printSerialNos.length; i++) {
			//运单号9位 + 流水号4位 + 目的站编码4位
			barcodeList.add(waybillNo + printSerialNos[i] + stationnumber); //运单号9位 + 流水号4位 + 目的站编码4位
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
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-9-27 10:20:20
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
