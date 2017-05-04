package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.WayBillNoSectionEntity;
/**
 * 运单号段管理DAO接口
 * @author 262036 HuangWei
 *
 * @date 2015-6-17 上午11:24:24
 */
public interface IWayBillNoSectionDao {

	/**
	 * 根据传入对象查询符合条件所有运单号段
	 * @return List<WayBillNoSectionEntity>
	 * @description
	 * @author 262036 HuangWei
	 * @date 2015-6-23上午11:16:23
	 */
	List<WayBillNoSectionEntity> queryWayBillNoSection(WayBillNoSectionEntity entity, int limit, int start);

	/**
	 * 
	 * @return Long
	 * @description 统计总记录数
	 * @author 262036 HuangWei
	 * @date 2015-6-23下午2:14:06
	 */
	Long queryRecordCount(WayBillNoSectionEntity entity);
	
	/**
	 * @return int
	 * @description 新增运单号段信息
	 * @author 262036 HuangWei
	 * @date 2015-6-23下午1:37:49
	 */
	int addWayBillNoSection(WayBillNoSectionEntity entity);
	
	/**
	 * @return Long
	 * @description 查询运单号参数起始值
	 * @author 262036 HuangWei
	 * @date 2015-6-23下午3:30:57
	 */
	Long queryWayBillStart(WayBillNoSectionEntity entity);
	
    /**
     * @return int
     * @description 修改运单号参数起始值
     * @author 262036 HuangWei
     * @date 2015-6-23下午3:32:38
     */
    int updateWayBillStart(WayBillNoSectionEntity entity);
	
}
