package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.predeliver.server.dao.ITestDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryArgsDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.util.define.FossConstants;

public class TestDao extends iBatis3DaoImpl implements ITestDao {



	/**
	 * 通过运单编号查询运单
	 * 
	 * @param waybill
	 */
	@Override
	public WaybillEntity queryWaybillByNo(String waybillNo) {
		// 封装查询条件
		WaybillQueryArgsDto argsDto = new WaybillQueryArgsDto();
		argsDto.setWaybillNo(waybillNo);
		argsDto.setActive(FossConstants.YES);
		SqlSession s = this.getSqlSession();
		WaybillEntity w = (WaybillEntity)s.selectOne("foss.pkp.WaybillEntityMapper." + "selectByWaybillNoAndOrderNo", argsDto);
		return w;
//		return (WaybillEntity) this.getSqlSession().selectOne(
//						"foss.pkp.WaybillEntityMapper." + "selectByWaybillNoAndOrderNo", argsDto);
	}


	@Override
	public List<PathDetailEntity> queryPathDetailByNos(String waybillNo,List<String> serialNoList) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("waybillNo", waybillNo);
		map.put("serialNoList", serialNoList);
		
		return this.getSqlSession().selectList("Foss.pathDetail." + "queryPathDetailByNos", map);
	}

    /**
     * 通过 标识编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:30:54
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISaleDepartmentDao#querySaleDepartmentByCode(java.lang.String)
     */
	@Override
	public SaleDepartmentEntity querySaleDepartmentByCode(String code) {
		// 检查参数
		if (StringUtils.isBlank(code)) {
			return null;
		}
		// 构造查询条件：
		SaleDepartmentEntity entity = new SaleDepartmentEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setCode(code);
		return (SaleDepartmentEntity) getSqlSession().selectOne(ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + 
				".saleDepartment." + "querySaleDepartmentByCode", entity);
	}
	
	
	  /**
     * 
     * <p>code 精确查询</p> 
     * @author 273311 
     * @date 2016-8-23 下午3:58:24
     * @param code
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IlclDeliveryToCashManagementDeliveryDao#queryLclDeliveryToCashManagementDeliveryByOrgCode(java.lang.String)
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<LclDeliveryToCashManagementDeliveryEntity> queryLclDeliveryToCashManagementDeliveryByOrgCode(String code) {
		// 构造查询条件：
		LclDeliveryToCashManagementDeliveryEntity entity = new LclDeliveryToCashManagementDeliveryEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setOrgCode(code);
		List<LclDeliveryToCashManagementDeliveryEntity> entitys = this.getSqlSession().selectList(
						 ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX+ ".lclDeliveryToCashManagementDelivery."
								+ "queryLclDeliveryToCashManagementDeliveryByOrgCode",entity);
		if (CollectionUtils.isEmpty(entitys)) {
			return null;
		} else {
			return entitys;
		}
	}
	
	
	  /**
     * 
     * <p>根据部门编码查询</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:26:15
     * @param startOrgCode
     * @param reachOrgCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILclDeliveryToCashManagementUnloadingDao#queryLclDeliveryToCashManagementUnloadingEntitytByCode(java.lang.String, java.lang.String)
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<LclDeliveryToCashManagementUnloadingEntity> queryLclDeliveryToCashManagementUnloadingEntitytByCode(
			String startOrgCode, String reachOrgCode) {

		// 构造查询条件：
		LclDeliveryToCashManagementUnloadingEntity entity = new LclDeliveryToCashManagementUnloadingEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setStartOrgCode(startOrgCode);
		entity.setReachOrgCode(reachOrgCode);
		List<LclDeliveryToCashManagementUnloadingEntity> entitys = this.getSqlSession().selectList(
					ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX+ ".lclDeliveryToCashManagementUnloading."
								+ "queryLclDeliveryToCashManagementUnloadingEntitytByCode",entity);
		if (CollectionUtils.isEmpty(entitys)) {
			return null;
		} else {
			return entitys;
		}

	}
	
	
}