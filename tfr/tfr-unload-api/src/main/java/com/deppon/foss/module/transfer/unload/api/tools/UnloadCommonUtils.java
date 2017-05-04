package com.deppon.foss.module.transfer.unload.api.tools;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;

/**
 * @description 卸车常用工具类
 * @version 1.0
 * @author 328768-foss-gaojianfu
 * @update 2016年6月6日 上午8:47:56
 */
public class UnloadCommonUtils {

	/**
	 * @description 此方法用来区分是否是快递交接单</br>
	 * 快递交接单区分规则：</br>
	 * 1) 外场-外场长途交接单号为L开头+年月日6位+5位数字,如 L16012700001
	 * 2）营业部-外场短途交接单为S开头+年月日6位+5位数字,如 S16012700001
	 * 3）外场-外场的空运装车生成为T开头+年月日6位+5位数字,如T16012700001
	 * 4）如果是pda创建的交接单前面如上，会在末尾加上p
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年6月12日 上午8:50:50
	 */
	public static boolean isExpressHandOver(String billNo){
		if(StringUtils.isEmpty(billNo)){
		 	    return false;
		}
		if(((billNo.startsWith("L")&&StringUtils.isNumeric(billNo.replace("L","").trim())
				||(billNo.startsWith("L")&&(billNo.endsWith("p")||billNo.endsWith("P"))
						&&StringUtils.isNumeric(billNo.substring(1, billNo.length()-1))))
				||(billNo.startsWith("S")&&StringUtils.isNumeric(billNo.replace("S","").trim())
				||(billNo.startsWith("S")&&(billNo.endsWith("p")||billNo.endsWith("P"))
						&&StringUtils.isNumeric(billNo.substring(1, billNo.length()-1))))
				||(billNo.startsWith("T")&&StringUtils.isNumeric(billNo.replace("T","").trim())
				||(billNo.startsWith("T")&&(billNo.endsWith("p")||billNo.endsWith("P"))
						&&StringUtils.isNumeric(billNo.substring(1, billNo.length()-1)))))&&billNo.length()>UnloadConstants.SONAR_NUMBER_11){
				return true;
				}
		        return false;
	}
	
	/**
	* @description 快递长途
	* @param billNo
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年6月12日 下午3:24:15
	 */
	public static boolean isExpressLong(String billNo){
		if(StringUtils.isEmpty(billNo)){
	 	    return false;
	}
		if((billNo.startsWith("L")&&StringUtils.isNumeric(billNo.replace("L","").trim())
				||(billNo.startsWith("L")&&(billNo.endsWith("p")||billNo.endsWith("P"))
						&&StringUtils.isNumeric(billNo.substring(1, billNo.length()-1))))&&billNo.length()>UnloadConstants.SONAR_NUMBER_11){
				return true;
				}
		return false;
	}
	

	/**
	* @description 快递短途
	* @param billNo
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年6月12日 下午3:24:15
	 */
	public static boolean isExpressShort(String billNo){
		if(StringUtils.isEmpty(billNo)){
	 	    return false;
	}
		if((billNo.startsWith("S")&&StringUtils.isNumeric(billNo.replace("S","").trim())
				||(billNo.startsWith("S")&&(billNo.endsWith("p")||billNo.endsWith("P"))
						&&StringUtils.isNumeric(billNo.substring(1, billNo.length()-1))))&&billNo.length()>UnloadConstants.SONAR_NUMBER_11){
				return true;
				}
		return false;
	}
	
	/**
	* @description 快递空运
	* @param billNo
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年6月12日 下午3:24:15
	 */
	public static boolean isExpressAir(String billNo){
		if(StringUtils.isEmpty(billNo)){
	 	    return false;
	}
		
		if((billNo.startsWith("T")&&StringUtils.isNumeric(billNo.replace("T","").trim())
				||(billNo.startsWith("T")&&(billNo.endsWith("p")||billNo.endsWith("P"))
						&&StringUtils.isNumeric(billNo.substring(1, billNo.length()-1))))&&billNo.length()>UnloadConstants.SONAR_NUMBER_11){
				return true;
				}
		return false;
	}
	
	
	/**
	 * @description 此方法用来判断是否是零担的交接单</br>
	 * 零担的交接单为纯数字或者pda创建的为纯数字末尾加p</br>
	 * @param billNo
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年6月12日 上午9:47:31
	 */
	public static boolean isLingDanHandOver(String billNo){
		if(StringUtils.isEmpty(billNo)){
	 	    return false;
	}
		if(StringUtils.isNumeric(billNo)
				||((billNo.endsWith("p")||billNo.endsWith("P"))&&StringUtils.isNumeric(billNo.substring(0,billNo.length()-1)))){
			return true;
		}
		return false;
	}
	
	/**
	* @description 此方法用来区分是否是快递包/笼/运单
	* @param wayBillNo
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年6月8日 上午8:20:11
	 */
	public static boolean isExpressWayBill(String wayBillNo){
		int[] eXwayBill={0,UnloadConstants.SONAR_NUMBER_5,UnloadConstants.SONAR_NUMBER_6,UnloadConstants.SONAR_NUMBER_7,UnloadConstants.SONAR_NUMBER_8,UnloadConstants.SONAR_NUMBER_9};
		wayBillNo=wayBillNo.trim();
		if(wayBillNo.toUpperCase().startsWith("B")
				&&StringUtils.isNumeric(wayBillNo.toUpperCase().replace("B","").trim())){
			return true;
		}
	    if(wayBillNo.toUpperCase().startsWith("C")
				&&StringUtils.isNumeric(wayBillNo.toUpperCase().replace("C","").trim())){
	    	return true;
	    }
	    if(ArrayUtils.contains(eXwayBill,Integer.parseInt(wayBillNo.substring(0,1)))
	    		&&(wayBillNo.length()==UnloadConstants.SONAR_NUMBER_10||wayBillNo.length()==UnloadConstants.SONAR_NUMBER_14)){
	    	return true;
	    }
		return false;
	}
	
	/**
	 * @description 此方法用来判断是否是零担的配载单
	 * @param billNo
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月16日 下午9:53:16
	 */
	public static  boolean isAssemble(String billNo){
		if(StringUtils.isEmpty(billNo)){
	 	    return false;
	}
		String[] arr=billNo.split("");
		if((!StringUtils.isNumeric(arr[1]))&&(!StringUtils.isNumeric(arr[2]))
				&&billNo.toLowerCase().indexOf("ky")==-1 
				&&billNo.toLowerCase().indexOf("yy")==-1){
			return true;
		}
		return false;
	}
}
