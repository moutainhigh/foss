package com.deppon.foss.prt.billdepositreceived;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.util.DateUtils;

/**
 * 打印数据绑定
 * 
 * @author foss-pengzhen
 * @date 2012-11-3 下午12:51:55
 */
public class DepositReceivedPrtDataSource implements JasperDataSource {

	/**
	 * 设置form表单的值
	 * @author foss-pengzhen
	 * @date 2012-11-3 下午12:53:19
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	public Map<String, Object> createParameterDataSource(JasperContext jasperContext) throws Exception{
		String customerCode = (String) jasperContext.get("customerCode");
		String customerName = (String) jasperContext.get("customerName");
		String generatingOrgCode = (String) jasperContext.get("generatingOrgCode");
		String generatingOrgName = (String) jasperContext.get("generatingOrgName");
		String strBus = (String) jasperContext.get("businessDate");
		Date dateBus = (Date) DateUtils.convert(strBus,DateUtils.DATE_FORMAT);
		String businessDate = DateUtils.convert(dateBus,DateUtils.DATE_CH_FORMAT);
		String paymentType = (String) jasperContext.get("paymentType");
		String amount = (String) jasperContext.get("amount");
	    
		//转中文数字
		String amountChinaChar = amount;
		
		if(StringUtils.isNotBlank(amountChinaChar)){
			char[] hunit = { '拾', '佰', '仟' };// 段内位置表示 
			char[] vunit = { '万', '亿' }; // 段名表示 
		    char[] digit = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
		    
		    String head = ""; //整数部分字符串
		    String rail = ""; //小数部分字符串
		    
		    if(amountChinaChar.indexOf(".")!=-1){
		    	rail = amountChinaChar.substring(amountChinaChar.length() - 2); // 取小数部分
		    	if(rail.indexOf(".")==0){
		    		head = amountChinaChar.substring(0,amountChinaChar.length()-2); // 取整数部分 
		    	}else{
		    		head = amountChinaChar.substring(0,amountChinaChar.length()-3); // 取整数部分 
		    	}
		    }else{
		    	head = amountChinaChar;
		    }

		    StringBuilder prefix = new StringBuilder(); // 整数部分转化的结果 
		    StringBuilder suffix = new StringBuilder(); // 小数部分转化的结果
		    
		    // 处理小数点后面的数 
		    if(StringUtils.isNotEmpty(rail)){
		    	if(rail.indexOf(".")==0){
		    		suffix.append(digit[rail.charAt(1) - '0']).append("角");
		    	}else{
    		    	suffix.append(digit[rail.charAt(0) - '0']).append("角")
    		    		.append(digit[rail.charAt(1) - '0']).append("分"); // 否则把角分转化出来
		    	}
		    }
		    // 处理小数点前面的数 
		    for (int i = 0; i < head.length(); i++) { // 为区别正负数，循环处理每个数字 
		    	if("-".equals(head.substring(i,i+1))){
		    		throw new SettlementException("红单不允许打印，请操作非红单的数据!");
		    	}
		    }
		    boolean flag = false;
		    if(StringUtils.equals(head, "0")){
		    	flag = true;
		    }
		    char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组.
		    char zero = '0'; // 标志'0'表示出现过0 
		    byte zeroSerNum = 0; // 连续出现0的次数 
		    final int numberOfFour = 4;
		    for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字 
		       int idx = (chDig.length - i - 1) % numberOfFour; // 取段内位置 
		       int vidx = (chDig.length - i - 1) / numberOfFour; // 取段位置 
		       if (chDig[i] == '0') { // 如果当前字符是0 
	               zeroSerNum++; // 连续0次数递增 
	               if (zero == '0' && idx!=0) { // 标志 ,连续零，仅读一次零，
	    	           zero = digit[0];   //解决问题2,当一个零位于第0位时，不输出“零”，仅输出“段名”.
	    	       } else if (idx == 0 && vidx > 0 && zeroSerNum < numberOfFour) {
	    	           prefix.append(vunit[vidx - 1]);
	    	           zero = '0';
	    	       }
	    	       continue;
	    	    }
	    	    zeroSerNum = 0; // 连续0次数清零 
	    	    if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的 
	    	    	prefix.append(zero);
	    	        zero = '0';
	    	    }
	    
	    	    //取到该位对应数组第几位。
	    	    int position=chDig[i] - '0'; 
	    	  //解决问题3 ,即处理10读"拾",而不读"壹拾"
	    	    if(position==1 && i==0 && idx==1){
	    	    	//nothing
	    	    }else{
	    	         prefix.append(digit[position]); // 转化该数字表示 
	    	    }
	    	    
	    	   
	    	    if (idx > 0) {     // 段内位置表示的值
	    	    	prefix.append(hunit[idx - 1]);
	    	    }
	    	    if (idx == 0 && vidx > 0) {      // 段名表示的值 
	    	        prefix.append(vunit[vidx - 1]);   // 段结束位置应该加上段名如万,亿 
	    	    }
		    }
		    if(flag){
		    	amountChinaChar = prefix.append("零元").append(suffix).toString(); // 返回正确表示
		    }else{
		    	amountChinaChar = prefix.append("元").append(suffix).toString(); // 返回正确表示
		    }
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("customerCode", customerCode);
		map.put("customerName", customerName);
		map.put("generatingOrgCode", generatingOrgCode);
		map.put("generatingOrgName", generatingOrgName);
		map.put("businessDate", businessDate);
		map.put("paymentType", DictUtil.rendererSubmitToDisplay(paymentType,DictionaryConstants.SETTLEMENT__PAYMENT_TYPE));
		map.put("amountChinaChar", amountChinaChar);
		map.put("amount", amount);
		return map;
	}

	/**
	 * 设置grid的值
	 * @author foss-pengzhen
	 * @throws Exception 
	 * @date 2012-11-3 下午12:53:40
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createDetailDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	public List<Map<String, Object>> createDetailDataSource(JasperContext jasperContext) throws Exception {
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		listMap.add(map);
		return listMap;
	}
}
