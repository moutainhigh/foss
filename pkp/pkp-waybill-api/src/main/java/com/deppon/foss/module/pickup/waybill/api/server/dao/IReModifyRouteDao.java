package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.ReModifyRouteEntity;

public interface IReModifyRouteDao{
	
	/**
	 * 新增一条欲修改走货路径数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-24 16:03:00
	 * @param record
	 * @return
	 */
	int addReModifyRouteRecord(ReModifyRouteEntity record);
	
	/**
	 * 修改数据
	 * @param record
	 * @return
	 */
	int updateReModifyRouteRecord(ReModifyRouteEntity record);
	
	/**
	 * 删除数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-24 16:03:24
	 */
	int deleteReModifyRouteRecord(String id);
	
	/**
	 * 分页查询数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-24 16:06:54
	 * @return
	 */
	List<ReModifyRouteEntity> searchReModifyRouteRecord(int start, int limited);
	/**
	 * 批量插入数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-24 18:54:13
	 */
	int insertSelectiveBatch(List<ReModifyRouteEntity> reModifyRouteList);
	/**
	 * 根据运单号,流水号ID查询是否后台存在此类数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-9 11:12:58
	 */
	List<ReModifyRouteEntity> queryIsExistRecordByWaybillSerial(String waybillNo, String labelGoodId);
	/**
	 * 根据JobId更新一定数据的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-11 15:18:31
	 */
	int updateReModifyRouteByJobId(String jobId, int updateNum);

	List<ReModifyRouteEntity> searchReModifyRouteRecordByJobId(int start,
			int limited, String jobId);
}
