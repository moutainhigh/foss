package com.deppon.foss.module.pickup.waybill.server.dao.impl;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPdaAppInfoDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.PdaAppInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillChangeWeightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillChangeWeightQueryDto;

public class PdaAppInfoDao extends iBatis3DaoImpl implements IPdaAppInfoDao {
	
	private static final String NAMESPACE_PDA_APP_INFO_MAPPER = "foss.pkp.PdaAppInfoMapper.";

	/**
	 * 更新操作
	 */
	@Override
	public int updateSelectiveByWaybillNo(PdaAppInfoEntity entity){
		if(entity != null && StringUtils.isNotEmpty(entity.getWaybillNo())){
			return getSqlSession().update(NAMESPACE_PDA_APP_INFO_MAPPER+"updatePdaAppInfoSelective", entity);
		}else{
			return 0;
		}
	}
	/**
	 * 插入重量体积数据日志
	 */
	public int insertImportWeightAndVolumeLog(LTLEWaybillChangeWeightDto ltlEWaybillChangeWeightDto){
		return getSqlSession().insert(NAMESPACE_PDA_APP_INFO_MAPPER+"insertImportWeightAndVolumeLog", ltlEWaybillChangeWeightDto);
	}
	/**
	 * 查询更改重量体积的结果信息
	 */
	public List<LTLEWaybillChangeWeightDto> queryLTLEWaybillChangeWeightResult(LTLEWaybillChangeWeightQueryDto dto){
		return getSqlSession().selectList(NAMESPACE_PDA_APP_INFO_MAPPER+"queryChangeResultByCondition", dto);
	}

	
	/**
	 * 添加PdpAppInfoEntity
	 */
	public int savePdaAppInfo(PdaAppInfoEntity entity){
		return getSqlSession().insert(NAMESPACE_PDA_APP_INFO_MAPPER+"insert", entity);
	}
	
	/**
	 * 根据运单号查询
	 * @param waybillNO
	 * @return
	 */
	public PdaAppInfoEntity queryPdaAppInfoByWaybillNO(String waybillNO){
		if (waybillNO == null || waybillNO.equals("")) {
			return null;
		}
		return (PdaAppInfoEntity)getSqlSession().selectOne(NAMESPACE_PDA_APP_INFO_MAPPER+"queryPdaAppInfoByWaybillNO",waybillNO);
	}

}
