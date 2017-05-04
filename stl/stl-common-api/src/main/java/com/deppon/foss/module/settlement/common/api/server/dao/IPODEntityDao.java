package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.PODEntity;

/**
 * 财务签收记录表 Dao 接口
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-10 下午1:46:59
 * @since
 * @version
 */
public interface IPODEntityDao {
	
	/**
	 * 新增财务签收记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-10 下午1:48:01
	 * @param entity 财务签收记录
	 * @return
	 */
	int add(PODEntity entity);
	
	/**
	 * 根据运单号查询运单的财务签收记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-10 下午1:57:02
	 * @param waybillNo 运单号
	 * @param podType   签收/反签收类型
	 * @return
	 */
	List<PODEntity> queryByWaybillNo(String waybillNo, String podType);
	
	/**
	 * 根据运单号查询运单的最新财务签收记录
	 * @author 092036-foss-bochenlong
	 * @date 2013-07-24 下午6:43:00
	 * @param waybillNo  运单号
	 * @return PODEntity
	 */
	PODEntity queryNewestPOD(String waybillNo);
}
