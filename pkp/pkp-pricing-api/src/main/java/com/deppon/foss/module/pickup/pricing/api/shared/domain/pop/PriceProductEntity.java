package com.deppon.foss.module.pickup.pricing.api.shared.domain.pop;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * <p>
 * Description:价格产品条目<br />
 * </p>
 * @title PriceProductEntity.java
 * @package com.deppon.foss.module.pickup.pricing.api.shared.domain.pop 
 * @author xx
 * @version 0.1 2014年10月13日
 */
public class PriceProductEntity extends BaseEntity {
	/**
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月23日
	 * @return
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((tableId == null) ? 0 : tableId.hashCode());
		result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
		return result;
	}
	/**
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月23日
	 * @param obj
	 * @return
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		if(this.getClass()!=obj.getClass()){
			return false;
		}
		PriceProductEntity other = (PriceProductEntity) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code)){
			return false;
		}
		return true;
	}
	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = -1127302674889665017L;
	/**
	 * 产品code
	 */
	private String  code;
	/**
	 * 产品编码
	 */
	private String name;
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
	 * Description:code<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public String getCode() {
		return code;
	}
	/**
	 * <p>
	 * Description:code<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * <p>
	 * Description:name<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public String getName() {
		return name;
	}
	/**
	 * <p>
	 * Description:name<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setName(String name) {
		this.name = name;
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
