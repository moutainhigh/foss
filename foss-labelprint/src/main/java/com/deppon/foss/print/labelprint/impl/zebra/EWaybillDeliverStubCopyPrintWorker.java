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
/**
 * 发货客户存根联
 * @author Foss-105888-Zhangxingwang
 * @date 2014-8-19 20:52:45
 */
public class EWaybillDeliverStubCopyPrintWorker extends LabelPrintWorker {

	@Override
	public void prepareLabelPrintForm(LabelPrintContext lblPrintContext) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void prepareLabelPrintService(LabelPrintContext lblPrintContext) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeExecuteLabelPrint(LabelPrintContext lblPrintContext) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void executePrintProcess(LabelPrintContext lblPrintContext) throws Exception {
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
			
			//最终配载部门
			String lastLoadOrgName = labelForm.getLastLoadOrgName() == null ? "" : labelForm.getLastLoadOrgName();
			//出发城市
			String leaveCity = labelForm.getLeavecity() == null ? "" : labelForm.getLeavecity();
			//第二城市外场
			String secondOutField = labelForm.getSecondOutfieldName() == null ? "" : labelForm.getSecondOutfieldName();
			//发货人联系方式
			String deliverPhone = "";
			if(labelForm.getDeliveryCustomerMobilephone() != null){
				deliverPhone = labelForm.getDeliveryCustomerMobilephone();
			}else{
				deliverPhone = labelForm.getDeliveryCustomerPhone();
			}
			//收货人联系方式
			String receiverPhone = "";
			if(labelForm.getReceiveCustomerMobilephone() != null){
				receiverPhone = labelForm.getReceiveCustomerMobilephone();
			}else{
				receiverPhone = labelForm.getReceiveCustomerPhone() == null ? "" : labelForm.getReceiveCustomerPhone();
			}
			String accountNo = labelForm.getAccountNo()== null ? "" : labelForm.getAccountNo();
			String codeAmount = labelForm.getCodAmount()== null ? "" : labelForm.getCodAmount();
			
			//打印指令
			StringBuffer printStrTemplate = new StringBuffer("");
			//^XA
			printStrTemplate.append("^XA\r\n");
			//^PW719
//			printStrTemplate.append("^PW719\r\n");//设置打印宽度
			//^LL0599
//			printStrTemplate.append("LL0599\r\n");//打印纸长度
			printStrTemplate.append("^SEE:GB18030.DAT^CWZ,E:CLJGBK.TTF^CI26\r\n");
			//^SEE:GB18030.DAT^CWZ,E:CLJGBK.TTF^CI26
			//^FO4,4^GB708,589,1^FS
			printStrTemplate.append("^FO4,4^GB690,589,1^FS\r\n");//画大框框
			//^FO3,167^GB710,0,1^FS
			printStrTemplate.append("^FO4,167^GB690,0,1^FS\r\n");//第一根横线
			//^FO20,83^BY2,3,10^BCN,60,Y,N^FD
			printStrTemplate.append("^FO20,83^BY2,3,58^BCN,60,Y,N^FD>;"+barcode+"^FS\r\n");
			//^FO365,88^GB345,0,1^FS
			printStrTemplate.append("^FO365,88^GB345,0,1^FS\r\n");
			//^FO364,4^GB0,164,1^FS
			printStrTemplate.append("^FO364,4^GB0,164,1^FS\r\n");
			
			//^FO2,504^GB687,0,2^FS
//			printStrTemplate.append("^FO4,504^GB687,0,1^FS\r\n");
			//^FO3,241^GB710,0,1^FS
			printStrTemplate.append("^FO4,241^GB690,0,1^FS\r\n");
			//^FO4,346^GB710,0,1^FS
			printStrTemplate.append("^FO4,346^GB690,0,1^FS\r\n");
			
			//^FO207,345^GB0,246,1^FS
			printStrTemplate.append("^FO197,348^GB0,246,1^FS\r\n");
			//^FO448,348^GB0,243,1^FS
			printStrTemplate.append("^^FO438,348^GB0,243,1^FS\r\n");
			//^FO688,348^GB0,245,2^FS
			printStrTemplate.append("^FO668,348^GB0,245,2^FS\r\n");
			
			//^FT431,65^A0N,48,31^FH\^FDzuizhongwaichang^FS
			printStrTemplate.append("^FO425,45^AZN,38,38^FD"+lastLoadOrgName+"^FS\r\n");
			
			if(labelForm.getIsDeliver() != null || "Y".equals(labelForm.getIsDeliver())){
				//^FT367,57^A0N,28,28^FH\^FDsong^FS
				printStrTemplate.append("^FO352,45^AZN,24,28^FD【送】^FS\r\n");
			}
			if(labelForm.getIsPrintAt() != null || "Y".equals(labelForm.getIsPrintAt())){
				//^FT680,137^A0N,42,28^FH\^FD@^FS
				printStrTemplate.append("^FO658,45^AZN,40,40^FD@^FS\r\n");
			}
			if(labelForm.getIsPrintStar() != null || "Y".equals(labelForm.getIsPrintStar())){
				//^FT668,63^A0N,39,28^FH\^FDxin^FS
				printStrTemplate.append("^FO655,100^AZN,39,28^FD★^FS\r\n");
			}
			//^FT367,134^A0N,31,26^FH\^FDshanghai^FS
			printStrTemplate.append("^FO367,100^AZN,31,31^FD"+leaveCity).append("—^FS\r\n");
			//^FT466,142^A0N,48,38^FH\^FDdierwaichang^FS
			printStrTemplate.append("^FO425,100^AZN,38,38^FD"+secondOutField+"^FS\r\n");
			//^FT7,497^A0N,23,43^FH\^FDjijianxinxix^FS
			//printStrTemplate.append("^FO7,497^AZN,23,43^FD寄件信息^FS\r\n");
			//^FT8,197^A0N,28,31^FH\^FDjijianxinxi^FS
			printStrTemplate.append("^FO8,170^AZN,31,31^FD寄件信息^FS\r\n");
			//^FT7,229^A0N,23,38^FH\^FDjijianxinxi^FS
			
			printStrTemplate.append("^FO7,200^AZN,24,24^FD"+labelForm.getDeliveryCustomerName()+"    "+labelForm.getDeliveryCustomerContact()+"    "+deliverPhone+"^FS\r\n");
			
			//^FT7,271^A0N,28,28^FH\^FDshoujianxinxi^FS
			printStrTemplate.append("^FO7,240^AZN,31,31^FD收件信息^FS\r\n");
			//^FT6,302^A0N,23,38^FH\^FDshoujianrengongsi^FS
			printStrTemplate.append("^FO6,270^AZN,24,24^FD"+labelForm.getReceiveCustomerName()+"    "+labelForm.getReceiveCustomerContact()+"    "+receiverPhone+"^FS\r\n");
			//^FT7,332^A0N,23,38^FH\^FDshoujianrenjutidizhi^FS
			printStrTemplate.append("^FO7,300^AZN,24,24^FD"+labelForm.getReceiveCustomerAddress()+"^FS\r\n");

			//^FT209,377^A0N,28,28^FH\^FDzengzhifuwu^FS
			printStrTemplate.append("^FO209,350^AZN,31,31^FD增值服务^FS\r\n");
			//^FT209,408^A0N,23,33^FH\^FDbaojiajine:20000^FS
			printStrTemplate.append("^FO209,375^AZN,24,24^FD保价金额:"+labelForm.getInsuranceAmount()+"^FS\r\n");
			//^FT209,438^A0N,23,36^FH\^FDqianhuidan:000^FS
			printStrTemplate.append("^FO209,400^AZN,24,24^FD签回单:"+labelForm.getReturnBillType()+"^FS\r\n");
			//^FT210,498^A0N,23,26^FH\^FDdaishouzhanghao:00^FS
			printStrTemplate.append("^FO210,425^AZN,24,24^FD代收账号:"+accountNo+"^FS\r\n");
			//^FT210,467^A0N,23,28^FH\^FDdaishouhuokuan:00^FS
			printStrTemplate.append("^FO210,450^AZN,24,24^FD代收货款:"+codeAmount+"^FS\r\n");

			//^FT453,377^A0N,28,28^FH\^FDyunshuxingzhi^FS
			printStrTemplate.append("^FO452,350^AZN,24,24^FD运输性质:"+labelForm.getProductName()+"^FS\r\n");
			//^FT452,408^A0N,23,38^FH\^FDjifeizhongliang^FS
			printStrTemplate.append("^FO452,375^AZN,24,24^FD计费重量:"+labelForm.getBillWeight()+"^FS\r\n");
			//^FT452,438^A0N,23,38^FH\^FDfukuanfangshi:^FS
			printStrTemplate.append("^FO452,400^AZN,24,24^FD付款方式:"+labelForm.getPaidMethod()+"^FS\r\n");
			//^FT451,469^A0N,23,38^FH\^FDyunfei:200^FS
			printStrTemplate.append("^FO451,425^AZN,24,24^FD运费:"+labelForm.getTransportFee()+"^FS\r\n");
			//^FT450,499^A0N,23,33^FH\^FDzongfeiyong:500^FS
			printStrTemplate.append("^FO450,450^AZN,24,24^FD总费用:"+labelForm.getTotalFee()+"^FS\r\n");
			
			//^FT7,376^A0N,28,28^FH\^FDhuowuxinxi^FS
			printStrTemplate.append("^FO7,350^AZN,31,31^FD货物信息^FS\r\n");
			//^FT7,407^A0N,23,40^FH\^FDpinming:00^FS
			printStrTemplate.append("^FO7,375^AZN,24,24^FD品名:"+labelForm.getGoodsName()+"^FS\r\n");
			//^FT7,437^A0N,23,28^FH\^FDjianshu:0001/20^FS
			if(labelForm.getGoodsQtyTotal() >0 && labelForm.getGoodsQtyTotal() == 1){
				printStrTemplate.append("^FO7,400^AZN,24,24^FD件数:"+labelForm.getGoodsQtyTotal()+"^FS\r\n");				
			}else{
				printStrTemplate.append("^FO7,400^AZN,24,24^FD件数:"+serialNo+"/"+labelForm.getGoodsQtyTotal()+"^FS\r\n");			
			}
			//^FT5,466^A0N,23,33^FH\^FDzhongliang:20^FS
			printStrTemplate.append("^FO5,425^AZN,24,24^FD重量:"+labelForm.getGoodsWeightTotal()+"^FS\r\n");

			/*//^FT8,536^A0N,28,28^FH\^FDshoujianyuan^FS
			printStrTemplate.append("^FO7,510^AZN,24,24^FD收件员:^FS\r\n");
			//^FT7,579^A0N,23,38^FH\^FDpaijianyuan^FS
			printStrTemplate.append("^FO7,560^AZN,24,24^FD派件员:^FS\r\n");
			
			//^FT452,537^A0N,28,28^FH\^FDshoufangqianming^FS
			printStrTemplate.append("^FO452,510^AZN,24,24^FD收方签名:^FS\r\n");
			//^FT454,580^A0N,23,38^FH\^FDshoufangyueri^FS
			printStrTemplate.append("^FO454,560^AZN,24,24^FD     月     日^FS\r\n");
			
			//^FT211,536^A0N,28,28^FH\^FDjifangqianming^FS
			printStrTemplate.append("^FO211,510^AZN,24,24^FD寄方签名:^FS\r\n");
			//^FT210,583^A0N,23,36^FH\^FDshoujiannianyue^FS
			printStrTemplate.append("^FO210,560^AZN,24,24^FD     月     日^FS\r\n");*/
			
			//^FT692,430^A0N,28,19^FH\^FDde^FS
			printStrTemplate.append("^FO670,400^AZN,28,19^FD德^FS\r\n");
			printStrTemplate.append("^FO670,425^AZN,28,19^FD邦^FS\r\n");
			printStrTemplate.append("^FO670,450^AZN,28,19^FD快^FS\r\n");
			printStrTemplate.append("^FO670,475^AZN,28,19^FD递^FS\r\n");
			printStrTemplate.append("^FO670,500^AZN,28,19^FD存^FS\r\n");
			printStrTemplate.append("^FO670,530^AZN,28,19^FD根^FS\r\n");
			printStrTemplate.append("^FO670,565^AZN,28,19^FD联^FS\r\n");
			
			//^LRY^FO426,5^GB283,0,84^FS^LRN
			printStrTemplate.append("^LRY^FO426,5^GB283,0,84^FS^LRN\r\n");
			//^XZ
			printStrTemplate.append("^XZ\r\n");
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

	private EWaybillStubCopyForm getLabelPrintForm(LabelPrintContext context) {
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
		//代收账号
		printForm.setCodAmount((String) context.get("codAmount"));

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
		printForm.setIsPrintStar((String) context.get("isDeliver"));
		//货物总件数
		printForm.setIsPrintStar((String) context.get("isPrintStar"));
		
		//当前打印人
		printForm.setOptusernum((String) context.get("optusernum"));
		//是否打印@
		printForm.setIsPrintAt((String) context.get("isPrintAt"));
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
}
