package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WayBillNoSectionEntity;

/**
 * 运单号段SERVICE接口
 * @author 262036 HuangWei
 *
 * @date 2015-6-17 上午11:27:32
 */
public interface IWayBillNoSectionService extends IService{

	/**
	 * @return List<WayBillNoSectionEntity>
	 * @description 根据传入对象查询符合条件所有的运单号段信息
	 * @author 262036 HuangWei
	 * @date 2015-6-23下午2:17:15
	 */
	List<WayBillNoSectionEntity> queryWayBillNoSection(WayBillNoSectionEntity entity, int limit, int start);
	
	/**
	 * @return Long
	 * @description 统计总记录数
	 * @author 262036 HuangWei
	 * @date 2015-6-23下午2:18:21
	 */
	Long queryRecordCount(WayBillNoSectionEntity entity);
	
	/**
	 * @return WayBillNoSectionEntity
	 * @description 新增运单号段信息
	 * @author 262036 HuangWei
	 * @date 2015-6-23下午1:38:43
	 */
	WayBillNoSectionEntity addWayBillNoSection(WayBillNoSectionEntity entity);
	
	/**
	 * @return Long
	 * @description 查询运单号参数起始值
	 * @author 262036 HuangWei
	 * @date 2015-6-23下午3:33:44
	 */
	Long queryWayBillStart(WayBillNoSectionEntity entity);
	
	/**
	 * @return int
	 * @description 修改运单号参数起始值
	 * @author 262036 HuangWei
	 * @date 2015-6-23下午3:33:57
	 */
	int updateWayBillStart(WayBillNoSectionEntity entity);
}
