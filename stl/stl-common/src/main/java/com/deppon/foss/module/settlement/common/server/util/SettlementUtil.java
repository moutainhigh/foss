package com.deppon.foss.module.settlement.common.server.util;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
/**
 * 结算模块公共方法处理类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-17 上午10:53:04
 */
public class SettlementUtil {

	public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

	private static List<String> PACKAGE_CODE_LIST = new ArrayList<String>();

	/**
	 * 根据前台输入的单号转换出应收单号数组、应付单号数组、预收单号数组、预付单号数组和来源单号数组
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-30 下午4:32:08
	 */
	public static Map<String, List<String>> convertBillNos(String[] billNos) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> receivableBillNosList = new ArrayList<String>(); // 应收单号集合
		List<String> payableBillNosList = new ArrayList<String>(); // 应付单号集合
		List<String> depositReceivedBillNosList = new ArrayList<String>(); // 预收单号集合
		List<String> advancedPaymentBillNosList = new ArrayList<String>(); // 预付单号集合
		List<String> sourceBillNosList = new ArrayList<String>(); // 来源单号集合
		if (billNos.length > 0) {
			int len = billNos.length;
			for (int i = 0; i < len; i++) {
				String billNo = billNos[i].trim();
				if (isReceiveable(billNo)) {
					receivableBillNosList.add(billNo);
				} else if (isPayable(billNo)) {
					payableBillNosList.add(billNo);
				} else if (isDepositReceived(billNo)) {
					depositReceivedBillNosList.add(billNo);
				} else if (isAdvancedPayment(billNo)) {
					advancedPaymentBillNosList.add(billNo);
				} else {
					sourceBillNosList.add(billNo);
				}
				// 应收单单号集合
				map.put(SettlementConstants.BILL_PREFIX_YS,
						receivableBillNosList);
				// 应付单号集合
				map.put(SettlementConstants.BILL_PREFIX_YF, payableBillNosList);
				// 预收单号集合
				map.put(SettlementConstants.BILL_PREFIX_US,
						depositReceivedBillNosList);
				// 预付单号集合
				map.put(SettlementConstants.BILL_PREFIX_UF,
						advancedPaymentBillNosList);
				// 来源单号集合
				map.put(SettlementConstants.BILL_PREFIX_LY, sourceBillNosList);
			}

		}
		return map;
	}

	/**
	 * 判断该单号是否为应收单号
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-30 下午4:39:45
	 */
	public static boolean isReceiveable(String billNo) {
		if (StringUtil.isNotEmpty(billNo)) {
			if (billNo.substring(0, 2).equals(
					SettlementConstants.BILL_PREFIX_YS)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断该单号是否为应付单号
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-30 下午4:39:45
	 */
	public static boolean isPayable(String billNo) {
		if (StringUtil.isNotEmpty(billNo)) {
			if (billNo.substring(0, 2).equals(
					SettlementConstants.BILL_PREFIX_YF)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @author 218392 张永雪 FOSS结算开发组
	 * @date 2016-06-20 17:08:10
	 * 判断是否ZC开头的合同编码
	 */
	public static boolean isPayableZc(String billNo) {
		if (StringUtil.isNotEmpty(billNo)) {
			if (billNo.substring(0, 2).equals(
					SettlementConstants.BILL_PREFIX_ZC)) {
				return true;
			}
		}
		return false;
	}
	

	/**
	 * 判断该单号是否为预收单号
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-30 下午4:39:45
	 */
	public static boolean isDepositReceived(String billNo) {
		if (StringUtil.isNotEmpty(billNo)) {
			if (billNo.substring(0, 2).equals(
					SettlementConstants.BILL_PREFIX_US)||billNo.substring(0, 2).equals(
							SettlementConstants.BILL_PREFIX_DP)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断该单号是否为预付单号
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-30 下午4:39:45
	 */
	public static boolean isAdvancedPayment(String billNo) {
		if (StringUtil.isNotEmpty(billNo)) {
			if (billNo.substring(0, 2).equals(
					SettlementConstants.BILL_PREFIX_UF)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * 判断运单号字符串是否合法
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-6 上午11:10:31
	 * @param waybillNoString
	 * @param num 限制运单个数
	 * @return
	 */
	public static boolean isValidateWaybillNo(String waybillNoString,int num) {

		String regexp = "^([0-9]{7,14})(,[0-9]{7,14}){0,"+(num-1)+"},?$";

		return Pattern.matches(regexp, waybillNoString);
	}

	/**
	 * 
	 * 解析
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-6 下午2:03:24
	 */
	public static Date parseDateTime(String dateTimeString) {

		Date dateTime = null;

		try {

			// 解析时间
			if (StringUtils.isNotEmpty(dateTimeString)) {
				dateTime = DateUtils.parseDate(dateTimeString,
						new String[] { PATTERN_DATE_TIME });
			}

		} catch (Exception ex) {

		}

		return dateTime;
	}

	/**
	 * 将Date类转换为XMLGregorianCalendar
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static XMLGregorianCalendar dateToXmlDate(Date date)
			throws Exception {
		if(null == date){ return null;}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		DatatypeFactory dtf = null;
		try {
			dtf = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new SettlementException(e.getMessage());
		}
		XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();
		dateType.setYear(cal.get(Calendar.YEAR));
		// 由于Calendar.MONTH取值范围为0~11,需要加1
		dateType.setMonth(cal.get(Calendar.MONTH) + 1);
		dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));
		dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));
		dateType.setMinute(cal.get(Calendar.MINUTE));
		dateType.setSecond(cal.get(Calendar.SECOND));
		return dateType;
	}
	/**
	 * XMLGregorianCalendar to date
	 *
	 * @param xmlDate
	 * @return date
	 * @throws Exception
	 */
	public static Date xmlDateToDate(XMLGregorianCalendar xmlDate)
			throws Exception {

		return xmlDate.toGregorianCalendar().getTime();
	}

	/**
	 * 
	 * 费控调用Foss ，设置的用户名和部门信息
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-28 下午8:49:08
	 */
	public static CurrentInfo getFKCurrentInfo() {
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode(SettlementConstants.COSTCONTROL_CODE);
		employee.setEmpName(SettlementConstants.COSTCONTROL_NAME);
	
		
		
		user.setEmployee(employee);
		user.setUserName(SettlementConstants.COSTCONTROL_NAME);

		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode(SettlementConstants.COSTCONTROL_CODE);
		dept.setName(SettlementConstants.COSTCONTROL_NAME);

		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		return currentInfo;
	}

	/**
	 * 
	 * 对接银企系统默认用户
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-28 下午8:49:08
	 */
	public static CurrentInfo getYQCurrentInfo() {
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode(SettlementConstants.BANK_CODE);
		employee.setEmpName(SettlementConstants.BANK_NAME);
		user.setEmployee(employee);
		user.setUserName(SettlementConstants.BANK_NAME);

		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode(SettlementConstants.BANK_CODE);
		dept.setName(SettlementConstants.BANK_NAME);

		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		return currentInfo;
	}

	/**
	 * 
	 * 对接CRM系统默认用户
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-28 下午8:49:08
	 */
	public static CurrentInfo getCRMCurrentInfo() {
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode(SettlementConstants.CRM_CODE);
		employee.setEmpName(SettlementConstants.CRM_NAME);
		user.setEmployee(employee);
		user.setUserName(SettlementConstants.CRM_NAME);

		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode(null);
		dept.setName(null);

		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		return currentInfo;
	}
	
	/**
	 * 对接ECS系统用户
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-19 20:50
	 */
	public static CurrentInfo getECSCurrentInfo(String empCode, String empName,String deptCode,String deptName){
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode(empCode);
		employee.setEmpName(empName);
		user.setEmployee(employee);
		user.setUserName(empName);
		
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode(deptCode);
		dept.setName(deptName);
		
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		return currentInfo;
	}
	
	/**
	 * 后台获取数据字典
	 * @author SVMSUNG
	 * @date 2013-4-19 上午10:19:26
	 * @param termCodes 需要转换的字典集合
	 * @return
	 * @see
	 */
	public static Map<String,Map<String,Object>> getDataDictionaryByTermsCodes(List<String> termCodes){
		Map<String,Map<String,Object>> map = new HashMap<String,Map<String,Object>>();
		//获取全部待转换缓存
		List<DataDictionaryEntity> listData=DictUtil.getDataByTermsCodes(termCodes);
		if(CollectionUtils.isNotEmpty(listData)){
			//循环封装map
			for(DataDictionaryEntity entity:listData){
				Map<String,Object> valueMap = new HashMap<String, Object>();
				if(CollectionUtils.isNotEmpty(entity.getDataDictionaryValueEntityList())){
					//循环封装valueMap
					for(DataDictionaryValueEntity valueEntity:entity.getDataDictionaryValueEntityList()){
						valueMap.put(valueEntity.getValueCode(), valueEntity.getValueName());
					}
				}
				map.put(entity.getTermsCode(),valueMap);
			}
		}
		return map;
	}
	
	/**
	 * 后台获取数据字典转换
	 * @author SVMSUNG
	 * @date 2013-4-19 上午10:31:39
	 * @param map 转换的字典集合
	 * @param key 数据字典 例：SETTLEMENT__PAYMENT_TYPE
	 * @param valueCode 需要转换的字典valueCode 例：YF
	 * @return
	 * @see
	 */
	public static String getDataDictionaryByTermsName(Map<String,Map<String,Object>> map,String key,String valueCode){
		Map<String,Object> valueMap = map.get(key);
		if(valueMap!=null && null != valueMap.get(valueCode)){
			return (String) valueMap.get(valueCode);
		}
		return null;
	}
	
	/**
	 * 进行单据子类型转化
	 * @author 045738-foss-maojianqiang
	 * @date 2013-4-8 下午7:07:48
	 * @param billType
	 * @param billParentBillType
	 * @return
	 */
	public static String getConvertedBillType(Map<String,Map<String,Object>> map,String billType,String billParentBillType){
		String value = billType;
		//判空
		if(StringUtils.isBlank(billType)||StringUtils.isBlank(billParentBillType)){
			return value;
		}
		//单据子类型转化 --现金收款单
		if(billParentBillType.equals(SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__XS)){
			value = getDataDictionaryByTermsName(map,DictionaryConstants.BILL_CASH_COLLECTION__BILL_TYPE,value);
			
		//单据子类型转化 --应收单	
		}else if(billParentBillType.equals(SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__YS)){
			value = getDataDictionaryByTermsName(map,DictionaryConstants.BILL_RECEIVABLE__BILL_TYPE,value);
			
		//单据子类型转化 --应付单	
		}else if(billParentBillType.equals(SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__YF)){
			value = getDataDictionaryByTermsName(map,DictionaryConstants.BILL_PAYABLE__BILL_TYPE,value);
		
		//单据子类型转化 --还款单	
		}else if(billParentBillType.equals(SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__HK)){
			value = getDataDictionaryByTermsName(map,DictionaryConstants.BILL_REPAYMENT__BILL_TYPE,value);
			
		//单据子类型转化 --预收单	
		}else if(billParentBillType.equals(SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__US)){
			value = getDataDictionaryByTermsName(map,DictionaryConstants.BILL_DEPOSIT_RECEIVED__BILL_TYPE,value);
			
		//单据子类型转化 --对账单
		}else if(billParentBillType.equals(SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__DZ)){
		
		
		//单据子类型转化 --付款单
		}else if(billParentBillType.equals(SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__FK)){
			value = getDataDictionaryByTermsName(map,DictionaryConstants.BILL_PAYMENT__BILL_TYPE,value);
			
		//小票没有单据子类型
		}else if(billParentBillType.equals(SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__XP)){
	
		//坏账没有单据子类型		
		}else if(billParentBillType.equals(SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__HZ)){
		
		//单据子类型转化 -- 预付单
		}else if(SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__UF.equals(billParentBillType)){
			value = getDataDictionaryByTermsName(map,DictionaryConstants.BILL_ADVANCED_PAYMENT__BILL_TYPE,value);
		}
		return value;
	}
	
	/**
	 * <p>
	 * 校验是否为空
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-5-8 上午10:16:48
	 * @param o
	 * @param code
	 * @param msg
	 * @see
	 */
	public static void valideIsNull(Object o, String msg) {
		if (o == null) {
			throw new SettlementException(msg);
		}

		if ((o instanceof Collection) && ((Collection) o).isEmpty()) {
			throw new SettlementException(msg);
		}
		if (o instanceof String) {
			if (((String) o).length() == 0) {
				throw new SettlementException(msg);
			}
		}
	}
	
	/**
	 * <p>通过对象属性name反射get方法获取</p> 
	 * @author 105762
	 * @date 2014-6-13 下午3:39:33
	 * @return object
	 * @throws SettlementException 
	 */
	public static final Object gainPropertyByName(Object obj, String propertyName) throws SettlementException {
		Object result = null;
		try {
			PropertyDescriptor pd = new PropertyDescriptor(propertyName, obj.getClass());
			result = pd.getReadMethod().invoke(obj);
		} catch (Exception e) {
			throw new SettlementException("获取属性失败");
		}
		return result;
	}

	/**
	 * 判断传入字符串是否快递代理产品类型
	 * @author 105762
	 * @param productCode
	 * @return bolean
	 * 
	 * @update chenzhuang 325369  新增三级产品国际快递-快、国际快递-标、快递报关通-标、快递报关通-快
	 */
	public static boolean isPackageProductCode(String productCode) {
		return PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(productCode)
				|| PricingConstants.ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE.equals(productCode)
				|| PricingConstants.ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE.equals(productCode)
				|| PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.equals(productCode)
				|| SettlementConstants.PRICING_PRODUCT_EXPRESS_INTERNATIONAL_BIAO.equals(productCode)
				|| SettlementConstants.PRICING_PRODUCT_EXPRESS_INTERNATIONAL_KUAI.equals(productCode)
				|| SettlementConstants.PRICING_PRODUCT_EXPRESS_CUSTOMS_CLEARANCE_BIAO.equals(productCode)
				|| SettlementConstants.PRICING_PRODUCT_EXPRESS_CUSTOMS_CLEARANCE_KUAI.equals(productCode);

	}

	/**
	 * 获取快递代理产品类型 List(String)
	 * @author 105762
	 * @return 
	 */
	public static List<String> createPackageProductCodeList() {
		if(CollectionUtils.isEmpty(PACKAGE_CODE_LIST)){
			if(PACKAGE_CODE_LIST == null){
				PACKAGE_CODE_LIST = new ArrayList<String>();
			}
			PACKAGE_CODE_LIST.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
			PACKAGE_CODE_LIST.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE);
			PACKAGE_CODE_LIST.add(PricingConstants.ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE);
			PACKAGE_CODE_LIST.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT);
			PACKAGE_CODE_LIST.add(SettlementConstants.PRICING_PRODUCT_EXPRESS_INTERNATIONAL_BIAO);
			PACKAGE_CODE_LIST.add(SettlementConstants.PRICING_PRODUCT_EXPRESS_INTERNATIONAL_KUAI);
			PACKAGE_CODE_LIST.add(SettlementConstants.PRICING_PRODUCT_EXPRESS_CUSTOMS_CLEARANCE_BIAO);
			PACKAGE_CODE_LIST.add(SettlementConstants.PRICING_PRODUCT_EXPRESS_CUSTOMS_CLEARANCE_KUAI);
		}
		return PACKAGE_CODE_LIST;
	}

	/**
	 * 获取传入日期的所属月份第一天
	 * @author 045738
	 * @date 2014-6-13 下午3:39:33
	 * @return object
	 * @throws SettlementException 
	 */
	public static final Date getFirstDayOfMonth(Date date){
		//判断传入月份
		if(date==null){
			throw new SettlementException("传入月份不能为空！");
		}
		//获取日期处理工具
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		return cal.getTime();
	}
	
	/**
	 * 相对当前日期，增加或减少月
	 * @author 045738
	 * @date 2014-6-13 下午3:39:33
	 * @return object
	 * @throws SettlementException 
	 */
	public static final Date addMonthToDate(Date date,int months){
		//判断传入月份
		if(date==null){
			throw new SettlementException("传入月份不能为空！");
		}
		//获取日期处理工具
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH,months);
	    return cal.getTime();
	}

    /**
     * 转换Excel中文字符名称
     * @param name
     * @return ISO编码的字符串
     */
    public static final String convertExcelName(String name){
        try {
            return new String(name.getBytes(
                    System.getProperty("file.encoding")), SettlementConstants.UNICODE_ISO);
        } catch (UnsupportedEncodingException e) {
            return name;
        }
    }

    /**
     *判断当前时间是否在两个时间整点之间
     *@param start,end
     *@return True or False
     */
    public static boolean isInTimePeriod(int start, int end) {
        if(!rangeInDefined(start, 0 , SettlementReportNumber.TWENTY_FOUR) || !rangeInDefined(end, 0 , SettlementReportNumber.TWENTY_FOUR)){
            throw new SettlementException("传入数字不合法！");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if(hour >= start && hour <= end){
            return true;
        }
        return false;
    }
    /**
    *判断一个整数是否在某个整数区间
    *@param current,min,max
    *@return True or False
    */
    public static boolean rangeInDefined(int current, int min, int max){
        return Math.max(min,current) == Math.min(current, max);
    }
    /**
    *判断一个时间是否在当前月
    *@param date
    *@return True or False
    */
    public static boolean isInCurrentMonth(Date date){
        if(null == date){
            throw new SettlementException("传入日期不能为空！");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String currentMonth = sdf.format(new Date());
        if(currentMonth.equals(sdf.format(date))){
            return true;
        }
        return false;
    }
}
