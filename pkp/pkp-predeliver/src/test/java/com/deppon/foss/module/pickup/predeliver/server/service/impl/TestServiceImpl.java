package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.predeliver.server.dao.ITestDao;
import com.deppon.foss.module.pickup.predeliver.server.service.ITestService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;

public class TestServiceImpl implements ITestService{
	@Autowired
	private ITestDao testDao ;
	/** ======================== 业务方法 ====================== */
	/**
	 * 
	 * <p>
	 * 通过运单号获取运单基础信息
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午4:07:54
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#queryWaybillByNo(java.lang.String)
	 */
	@Override
	public WaybillEntity queryWaybillBasicByNo(String waybillNo) {
		// 返回运单基本信息
		return testDao.queryWaybillByNo(waybillNo); 
	}
	
	/**
	 * @author nly
	 * @date 2015年5月9日 下午2:39:48
	 * @function 根据运单号或运单号流水号查询走货路径明细
	 * @param waybillNo
	 * @param serialNoList
	 * @return
	 */
	@Override
	public List<PathDetailEntity>  queryPathDetailByNos(String waybillNo,List<String> serialNoList) {
		List<PathDetailEntity> pathDetailList = testDao.queryPathDetailByNos(waybillNo,serialNoList);
		return pathDetailList;
	}
	
	/**
	 * 精确查询 通过 CODE 查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:31:57
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISaleDepartmentService#querySaleDepartmentByCode(java.lang.String)
	 */
	@Override
	public SaleDepartmentEntity querySaleDepartmentByCode(String code) {
		// 对code进行非空判断
		if (StringUtils.isBlank(code)) {
			// 如果为空则返回null
			return null;
		}
		// 从缓存中查找
		SaleDepartmentEntity entityResult = null;

			// 执行查询
		entityResult = testDao.querySaleDepartmentByCode(code);
		// 返回查询结果
		return entityResult;
	}
	
	  /**
     * 
     * <p>根据code查询</p> 
     * 提供给结算使用
     * @author 273311 
     * @date 2016-8-23 下午3:42:38
     * @param code
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IlclDeliveryToCashManagementDeliveryService#queryLclDeliveryToCashManagementDeliveryByOrgCode(java.lang.String)
     */
	@Override
	public List<LclDeliveryToCashManagementDeliveryEntity> queryLclDeliveryToCashManagementDeliveryByOrgCode(
			String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		List<LclDeliveryToCashManagementDeliveryEntity> entityResult = testDao.queryLclDeliveryToCashManagementDeliveryByOrgCode(code);
		return entityResult;
	}
	
	   /**
     * 
     * <p>startOrgCode, reachOrgCode 精确查询</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:15:34
     * @param startOrgCode
     * @param reachOrgCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILclDeliveryToCashManagementUnloadingService#queryLclDeliveryToCashManagementUnloadingEntitytByCode(java.lang.String, java.lang.String)
     */
	@Override
	public List<LclDeliveryToCashManagementUnloadingEntity> queryLclDeliveryToCashManagementUnloadingEntitytByCode(
			String startOrgCode, String reachOrgCode) {
		if (StringUtils.isBlank(startOrgCode)
				|| StringUtils.isBlank(reachOrgCode)) {
			return null;
		}
		List<LclDeliveryToCashManagementUnloadingEntity> entityResult = testDao
				.queryLclDeliveryToCashManagementUnloadingEntitytByCode(startOrgCode, reachOrgCode);
		return entityResult;
	}
	
}
