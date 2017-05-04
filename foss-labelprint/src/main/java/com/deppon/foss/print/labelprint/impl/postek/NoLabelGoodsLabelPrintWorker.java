package com.deppon.foss.print.labelprint.impl.postek;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.print.labelprint.LabelPrintWorker;

/**
 * Zebra888TT 无标签货物  Label Print Program
 * @author niujian
 */
public class NoLabelGoodsLabelPrintWorker extends LabelPrintWorker {
 
	@Override
	public void prepareLabelPrintForm(LabelPrintContext lblPrintContext)
			throws Exception {
		printByZebra(lblPrintContext);
	}

	@Override
	public void prepareLabelPrintService(LabelPrintContext lblPrintContext)
			throws Exception {
	}

	@Override
	public void beforeExecuteLabelPrint(LabelPrintContext lblPrintContext)
			throws Exception {
	}
	
	public String[] parseToStringArray(String pValue){
		if(isNull(pValue)){
			return null;
		}
		else {
			return pValue.split(",");
		}
	}
	
	/**
	 * @param context
	 * param for label print 斑马打印机进行打印操作
	 * lblprtworker:NoLabelGoodsLabelPrintWorker
     * 当前用户 工号 optusernum (一条)
	 * 打印日期 printdate (一条)
	 * 单号 number (多条逗号隔开)
	 * 打印流水号 serialnos[0001,0002](多条逗号隔开)
	 * 总件数 totalpieces(多条逗号隔开)
	 * 包装 packing (多条逗号隔开)
	 * 当前部门 currentOrgName(一条)
	 * 货区  goodsAreaName(多条逗号隔开)
	 * 交接单号 handoverNo(多条逗号隔开)
	 * 品名 goodsName(多条逗号隔开)
	 * 重量 weight(多条逗号隔开)
	 * 体积 volume(多条逗号隔开)
	 */
	public void printByZebra(LabelPrintContext lblPrintContext) throws Exception 
	{
		int left = lblPrintContext.getPrtLeft();
		int top = lblPrintContext.getPrtTop();
		
		//当前用户 工号
		String userName = (String)lblPrintContext.get("optusernum");
		// 打印日期
		String printDateStr = (String)lblPrintContext.get("printdate");
		// 当前部门
		String currentOrgName = (String)lblPrintContext.get("currentOrgName");
		
		List<Map> lst = new ArrayList<Map>();
		
		//打印件的流水号
		String serialnos = (String)lblPrintContext.get("serialnos");
		if(isNull(serialnos)){
			throw new Exception("输入的流水号不正确，无法进行标签打印");
		}
		
		if(serialnos.indexOf(",")!=-1){
			// 多个标签打印
			String[] serialnoarr = serialnos.split(",");
			
			String[] totalpiecesarr = parseToStringArray((String)lblPrintContext.get("totalpieces"));//总件数
			if(totalpiecesarr==null || (totalpiecesarr!=null && totalpiecesarr.length!=serialnoarr.length )){
				throw new Exception("输入总件数与流水号个数不匹配，无法进行标签打印");
			}
			
			String[] packingarr = parseToStringArray((String)lblPrintContext.get("packing"));//包装
			if(packingarr==null || (packingarr!=null && packingarr.length!=serialnoarr.length)){
				throw new Exception("输入包装个数与流水号个数不匹配，无法进行标签打印");
			}
			
			String[] numberarr = parseToStringArray((String) lblPrintContext.get("number"));// 单号
			if(numberarr==null || (numberarr!=null && numberarr.length!=serialnoarr.length)){
				throw new Exception("输入单号个数与流水号个数不匹配，无法进行标签打印");
			}
			
			boolean isGoodsAreaNameMatch = true;
			String[] goodsareanamearr = parseToStringArray((String)lblPrintContext.get("goodsAreaName"));//货区
			if(goodsareanamearr==null || ( goodsareanamearr!=null && goodsareanamearr.length!=serialnoarr.length) ){
				isGoodsAreaNameMatch = false;
			}
			boolean isHandoverNoMatch = true;
			String[] handovernoarr = parseToStringArray((String)lblPrintContext.get("handoverNo"));//交接单号
			if(handovernoarr==null || (handovernoarr!=null && handovernoarr.length!=serialnoarr.length) ){
				isHandoverNoMatch = false;
			}
			if(!isGoodsAreaNameMatch && !isHandoverNoMatch){
				throw new Exception("输入货区/交接单号个数均与流水号个数不匹配，无法进行标签打印");
			}
			
			String[] goodsnamearr = parseToStringArray((String)lblPrintContext.get("goodsName"));//品名
			if(goodsnamearr==null || (goodsnamearr!=null && goodsnamearr.length!=serialnoarr.length)){
				throw new Exception("输入品名个数与流水号个数不匹配，无法进行标签打印");
			}
			
			String[] weightarr = parseToStringArray((String)lblPrintContext.get("weight"));//重量
			if(weightarr==null || (weightarr!=null && weightarr.length!=serialnoarr.length)){
				throw new Exception("输入重量个数与流水号个数不匹配，无法进行标签打印");
			}
			
			String[] volumearr = parseToStringArray((String)lblPrintContext.get("volume"));//体积
			if(volumearr==null || (volumearr!=null && volumearr.length!=serialnoarr.length)){
				throw new Exception("输入体积个数与流水号个数不匹配，无法进行标签打印");
			}
			
			for(int i=0;i<serialnoarr.length;i++){
				Map m = new HashMap();
				String serialno = serialnoarr[i];
				try{
					m.put("serialno",Integer.valueOf(serialno)); // 流水号
				}catch (Exception e) {
					throw new Exception("输入的流水号不能转换为件数数值，无法进行标签打印");
				}
				String waybillnumber = numberarr[i];
				if (isNull(waybillnumber)) {
					throw new Exception("运单号不正确，无法进行标签打印");
				}
				String barcode = waybillnumber + serialno;
				m.put("barcode",barcode); // 条码
				
				if(waybillnumber.startsWith("w") || waybillnumber.startsWith("W")){
					waybillnumber = waybillnumber.replace("w", "");
					waybillnumber = waybillnumber.replace("W", "");
				}
				
				int length = waybillnumber.length();
				String waybillnumber1 = "";
				String waybillnumber2 = "";
				if (length == 8) {
					waybillnumber1 = waybillnumber.substring(0, length - 4);
					waybillnumber2 = waybillnumber.substring(length - 4);
				} else if (length == 9) {
					waybillnumber1 = waybillnumber.substring(0, length - 5);
					waybillnumber2 = waybillnumber.substring(length - 5);
				} else if (length == 10) {
					waybillnumber1 = waybillnumber.substring(0, length - 5);
					waybillnumber2 = waybillnumber.substring(length - 5);
				}
				m.put("waybillnumber1",waybillnumber1); // 单号1
				m.put("waybillnumber2",waybillnumber2); // 单号2
				m.put("totalpieces",totalpiecesarr[i]); // 总件数
				m.put("packing",packingarr[i]);         // 包装
				String output_goodsareaname_handoverno = "";
				String output_goodsareaname_handoverno_lbl = "";
				if(isGoodsAreaNameMatch){
					output_goodsareaname_handoverno_lbl = "【货区】";
					output_goodsareaname_handoverno = goodsareanamearr[i];
				}
				else if(isHandoverNoMatch){
					output_goodsareaname_handoverno_lbl = "【交接单号】";
					output_goodsareaname_handoverno = handovernoarr[i];
				}
				m.put("goodsareanameorhandovernolbl",output_goodsareaname_handoverno_lbl); // 货区/交接单号lbl
				m.put("goodsareanameorhandoverno",output_goodsareaname_handoverno); // 货区/交接单号
				
				m.put("goodsname",goodsnamearr[i]);     // 品名
				m.put("weight",weightarr[i]);           // 重量
				m.put("volume",volumearr[i]);           // 体积

				lst.add(m);
			}
		}
		else {
			// 单个标签打印
			Map m = new HashMap();
			String serialno = serialnos;
			try{
				m.put("serialno",Integer.valueOf(serialno)); // 流水号
			}catch (Exception e) {
				throw new Exception("输入的流水号不能转换为件数数值，无法进行标签打印");
			}
			String waybillnumber = (String) lblPrintContext.get("number");
			if (isNull(waybillnumber)) {
				throw new Exception("运单号不正确，无法进行标签打印");
			}
			String barcode = waybillnumber + serialno;
			m.put("barcode",barcode); // 条码
			
			if(waybillnumber.startsWith("w") || waybillnumber.startsWith("W")){
				waybillnumber = waybillnumber.replace("w", "");
				waybillnumber = waybillnumber.replace("W", "");
			}
			
			int length = waybillnumber.length();
			String waybillnumber1 = "";
			String waybillnumber2 = "";
			if (length == 8) {
				waybillnumber1 = waybillnumber.substring(0, length - 4);
				waybillnumber2 = waybillnumber.substring(length - 4);
			} else if (length == 9) {
				waybillnumber1 = waybillnumber.substring(0, length - 5);
				waybillnumber2 = waybillnumber.substring(length - 5);
			} else if (length == 10) {
				waybillnumber1 = waybillnumber.substring(0, length - 5);
				waybillnumber2 = waybillnumber.substring(length - 5);
			}
			m.put("waybillnumber1",waybillnumber1); // 单号1
			m.put("waybillnumber2",waybillnumber2); // 单号2
			m.put("totalpieces",(String) lblPrintContext.get("totalpieces")); // 总件数
			m.put("packing",(String) lblPrintContext.get("packing"));         // 包装
			
			String goodsareaname = (String)lblPrintContext.get("goodsAreaName");
			String handoverno = (String)lblPrintContext.get("handoverNo");
			String output_goodsareaname_handoverno = "";
			String output_goodsareaname_handoverno_lbl = "";
			if(!isNull(goodsareaname)){
				output_goodsareaname_handoverno_lbl = "【货区】";
				output_goodsareaname_handoverno = goodsareaname;
			}
			else if(!isNull(handoverno)){
				output_goodsareaname_handoverno_lbl = "【交接单号】";
				output_goodsareaname_handoverno = handoverno;
			}
			m.put("goodsareanameorhandovernolbl",output_goodsareaname_handoverno_lbl); // 货区/交接单号lbl
			m.put("goodsareanameorhandoverno",output_goodsareaname_handoverno); // 货区/交接单号
			
			m.put("goodsname",(String)lblPrintContext.get("goodsName"));     // 品名
			m.put("weight",(String)lblPrintContext.get("weight"));           // 重量
			m.put("volume",(String)lblPrintContext.get("volume"));           // 体积
			lst.add(m);
		}
		
		// 开始填入参数值,完成打印脚本指令
		StringBuffer printStrParam = new StringBuffer("");
		for (Map m : lst){
			
			
			printStrParam.append("N\r\n");
			printStrParam.append("Q720,24\r\n");
			printStrParam.append("q440\r\n");
			printStrParam.append("S1\r\n");
			printStrParam.append("D7\r\n");
			printStrParam.append("JB\r\n");
			printStrParam.append("ZB\r\n");
			//printStrParam.append("R"+left+","+top+"\r\n");
			printStrParam.append("X8,8,2,424,688\r\n");
			printStrParam.append("LO88,8,3,680\r\n");//第一条竖线
			printStrParam.append("LO163,8,3,460\r\n");//第二条竖线
			printStrParam.append("LO238,8,3,680\r\n");//第三条竖线
			printStrParam.append("LO313,8,3,680\r\n");//第四条竖线
			printStrParam.append("LO8,258,80,3\r\n");//第一条横线
			printStrParam.append("LO88,468,335,3\r\n");//第二条横线
			
			
			
			//输出内容
			printStrParam.append("A420,18,1,8,1,1,N,\"德邦物流\"\r\n");              //打印公司LOGO
			printStrParam.append("LO390,18,3,130\r\n");                             //LOGO下面的线
			printStrParam.append("A385,18,1,4,1,1,N,\""+userName+"\"\r\n");         //工号	
			if(printDateStr.length()<11){
				printStrParam.append("A360,18,1,4,1,1,N,\""+printDateStr+"\"\r\n");     //打印日期
			}else{
			    printStrParam.append("A360,18,1,4,1,1,N,\""+printDateStr.substring(0,10)+"\"\r\n");     //打印日期	
			    printStrParam.append("A360,18,1,4,1,1,N,\""+printDateStr.substring(10,printDateStr.length()-1)+"\"\r\n");     //打印日期	
			  }
			printStrParam.append("B420,190,1,1,2,12,98,N,\""+(String)m.get("barcode")+"\"\r\n");    //条码
			printStrParam.append("A400,490,1,8,2,1,N,\"无标签多货\"\r\n");             //打印中文【无标签】			
			String pcsAndTotalpcs = (Integer)m.get("serialno")+"/"+(String)m.get("totalpieces");
			printStrParam.append("A298,480,1,4,1,1,N,\""+pcsAndTotalpcs+"\"\r\n");//总件数
			printStrParam.append("A280,480,1,8,1,1,N,\""+(String)m.get("packing")+"\"\r\n");//包装  
			printStrParam.append("A220,500,1,4,2,2,N,\"W\"\r\n");                           //W[无标签开头拼音字母]
			printStrParam.append("A220,525,1,4,2,2,N,\""+(String)m.get("waybillnumber1")+"\"\r\n");//单号1
			printStrParam.append("A175,500,1,4,2,2,N,\""+(String)m.get("waybillnumber2")+"\"\r\n");//单号2
			printStrParam.append("A305,18,1,8,2,2,N,\""+currentOrgName+"\"\r\n");                 //当前部门
			printStrParam.append("A240,18,1,8,1,1,N,\""+(String)m.get("goodsareanameorhandovernolbl")+"\"\r\n");//货区/交接单号lbl
			printStrParam.append("A220,120,1,4,2,2,N,\""+(String)m.get("goodsareanameorhandoverno")+"\"\r\n"); //货区/交接单号
//			if(lblPrintContext.getTestEnvInd()!=null && lblPrintContext.getTestEnvInd().length()>0 ){
//				printStrParam.append("A460,290,0,4,1,2,R,\""+lblPrintContext.getTestEnvInd()+"\"\r\n");
//			}
			printStrParam.append("A158,18,1,8,1,1,N,\"【品名】\"\r\n");
			printStrParam.append("A145,120,1,8,2,2,N,\""+(String)m.get("goodsname")+"\"\r\n");//品名
			printStrParam.append("A80,18,1,8,1,1,N,\"【重量】\"\r\n");                          //重量
			printStrParam.append("A60,110,1,4,2,2,N,\""+(String)m.get("weight")+"\"\r\n");     //重量
			printStrParam.append("A80,265,1,8,1,1,N,\"【体积】\"\r\n");
			printStrParam.append("A60,360,1,4,2,2,N,\""+(String)m.get("volume")+"\"\r\n");     //体积
			printStrParam.append("P1\r\n");
		}
		
		lblPrintContext.setPrintForm(printStrParam.toString());
		lblPrintContext.setPrintFormParam(null);
	}
}
