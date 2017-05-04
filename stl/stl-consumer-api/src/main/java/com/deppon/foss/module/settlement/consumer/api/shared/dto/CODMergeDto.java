package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.lang.reflect.MethodUtils;

import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODMergeEntity;

/**
 * 可合并代收货款明细DTO
 * 
 * @author 163576
 *
 */
public class CODMergeDto extends CODMergeEntity implements Comparable<CODMergeDto>{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1988579964315941087L;
	
	/**
	 * 运单号
	 */
	private String waybillNo;

    /**
     * 签收时间
     */
    private Date signDate;
    
	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	/**
	 * @return the signDate
	 */
	public Date getSignDate() {
		return signDate;
	}

	/**
	 * @param signDate the signDate to set
	 */
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	/**
	 * 比较方法
	 */
	@Override
	public int compareTo(CODMergeDto o) {
		// 按照该顺序比较
		// 同一部门、收款人、账号、银行、支行、账户类别、所属子公司、省、市、手机号
		// PayableOrgCode PayeeName AccountNo BankHqCode BankBranchCode PublicPrivateFlag
		// PayableComCode ProvinceCode CityCode PayeePhone
		String [] compareParms = {"PayableOrgCode",
		                          "PayeeName",
		                          "AccountNo",
		                          "BankHqCode",
		                          "BankBranchCode",
		                          "PublicPrivateFlag",
		                          "PayableComCode",
		                          "ProvinceCode",
		                          "CityCode",
		                          "PayeePhone"};
		
		for (int i = 0; i < compareParms.length; i++) {
			String methodName = "get".concat(compareParms[i]);
			
			try {
				Object obj1 = MethodUtils.invokeMethod(this, methodName,null);
				Object obj2 = MethodUtils.invokeMethod(o, methodName,null);
				
				int compare = this.compareObject(obj1, obj2);
				// 如果对比值不一致直接返回，或者直到循环结束，返回对比值
				if(compare != 0 || i == compareParms.length -1 ){
					return compare;
				}
			} catch (NoSuchMethodException e) {
				throw new SettlementException("NoSuchMethod："+methodName+"."+e.getMessage());
			} catch (IllegalAccessException e) {
				throw new SettlementException(e.getMessage());
			} catch (InvocationTargetException e) {
				throw new SettlementException(e.getMessage());
			} catch (Exception e) {
				throw new SettlementException(e.getMessage());
			}
		}
		return -1;
	}
	
	
	/**
	 * 按指定对象排序
	 * 
	 */
	private int compareObject(Object obj1, Object obj2) throws Exception {
		if (obj1 == null || obj2 == null) {
			return obj1 == null ? -1 : 1;
		}
		Class<? extends Object> cl = obj1.getClass();
		if (obj1 instanceof java.lang.Comparable) {
			// byte int long float..number, date , boolean , char
			Method getMethod = obj1.getClass().getMethod("compareTo",new Class[] { cl });
			return (Integer) getMethod.invoke(obj1, new Object[] { obj2 });
		}
		return -1;
	}
	
	
}
