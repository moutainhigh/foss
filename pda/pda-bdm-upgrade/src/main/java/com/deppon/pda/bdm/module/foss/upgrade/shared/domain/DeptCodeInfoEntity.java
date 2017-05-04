package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * TODO(城市实体类)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-4-10 下午2:27:58,content:TODO </p>
 * @author chengang
 * @date 2013-4-10 下午2:27:58
 * @since
 * @version
 */
public class DeptCodeInfoEntity extends BaseEntity {
	/**
	 * TODO（UID）
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 省
	 */
	private String provCode;
	/**
	 * 市
	 */
	private String cityCode;
	/**
	 * 区号
	 */
	private String countyCode;
	
}
