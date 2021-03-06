package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.Date;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SpecialdiscountEntity;



/**
 * 
 * 特惠活动客户打折扣dao接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:CSS,date:2015-7-28 下午2:37:25, </p>
 * @author 261997
 * @date 2015-7-28 下午2:37:25
 * @since
 * @version
 */
public interface ISpecialdiscountDao {

	 /**
     * 插入实体信息
     * @param entity
     * @return
     * @author css-261997
     * @date 2015-07-28 上午10:36:32
     */
	int insert(SpecialdiscountEntity entity);
	
	 /**
     * 修改实体信息
     * @param entity
     * @return
     * @author css-261997
     * @date 2015-07-28 上午10:36:32
     */
	int updateBySpecialdiscount(SpecialdiscountEntity entity);

	/**
	 * 根据传入的主键查询符合条件的实体信息
	 * @param entity	 
	 * @return
	 * @author css-261997
	 * @date 2015-07-28 上午10:36:32
	 */
	SpecialdiscountEntity selectByPrimaryKey(SpecialdiscountEntity specialdiscount);
	
	/**
	 * 根据传入的客户编码，与开单时间查询符合条件的实体信息
	 * 
	 * @param entity
	 * @return
	 * @author css-261997
	 * @date 2015-08-14 上午15:36:32
	 */
	SpecialdiscountEntity selectBySpecialdiscountYX(String code);
	
	SpecialdiscountEntity selectBySpecialdiscountTime(String code,Date createtime);
	
	boolean selectBySpecialdiscountKHFQ(String code,Date createtime);
}