package com.deppon.foss.module.pickup.pricing.api.shared.domain.pop;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title PriceIndustryEntity.java
 * @package com.deppon.foss.module.pickup.pricing.api.shared.domain.pop 
 * @author xx
 * @version 0.1 2014年10月13日
 */
public class PriceIndustryEntity extends BaseEntity {

	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = 7712599278741480825L;
	/**
	 * 一级行业编码
	 */
	private String firstTradecode;
	/**
	 * 一级行业名字
	 */
	private String firstTradeName;
	/**
	 * 二级行业编码
	 */
	private String sencondTradecode;
	/**
	 * 二级行业名字
	 */
	private String sencondTradeName;
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 表id
	 */
	private String tableId;
	/**
	 * <p>
	 * Description:firstTradecode<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public String getFirstTradecode() {
		return firstTradecode;
	}
	/**
	 * <p>
	 * Description:firstTradecode<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setFirstTradecode(String firstTradecode) {
		this.firstTradecode = firstTradecode;
	}
	/**
	 * <p>
	 * Description:firstTradeName<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public String getFirstTradeName() {
		return firstTradeName;
	}
	/**
	 * <p>
	 * Description:firstTradeName<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setFirstTradeName(String firstTradeName) {
		this.firstTradeName = firstTradeName;
	}
	/**
	 * <p>
	 * Description:sencondTradecode<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public String getSencondTradecode() {
		return sencondTradecode;
	}
	/**
	 * <p>
	 * Description:sencondTradecode<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setSencondTradecode(String sencondTradecode) {
		this.sencondTradecode = sencondTradecode;
	}
	/**
	 * <p>
	 * Description:sencondTradeName<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public String getSencondTradeName() {
		return sencondTradeName;
	}
	/**
	 * <p>
	 * Description:sencondTradeName<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setSencondTradeName(String sencondTradeName) {
		this.sencondTradeName = sencondTradeName;
	}
	/**
	 * <p>
	 * Description:tableName<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月22日
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * <p>
	 * Description:tableName<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月22日
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * <p>
	 * Description:tableId<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月22日
	 */
	public String getTableId() {
		return tableId;
	}
	/**
	 * <p>
	 * Description:tableId<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月22日
	 */
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}


}
