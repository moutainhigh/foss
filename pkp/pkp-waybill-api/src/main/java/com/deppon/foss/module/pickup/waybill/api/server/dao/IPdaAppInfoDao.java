package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.PdaAppInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillChangeWeightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillChangeWeightQueryDto;

public interface IPdaAppInfoDao {
	
	/**
	 * 零担电子运单导入重量体积
	 * @author 305082
	 */
	public int updateSelectiveByWaybillNo(PdaAppInfoEntity entity);
	
	/**
	 * 导入重量体积日志表
	 * @author 305082
	 */
	public int insertImportWeightAndVolumeLog(LTLEWaybillChangeWeightDto ltlEWaybillChangeWeightDto);

	/**
	 * 查询更改重量体积的结果信息
	 */
	public List<LTLEWaybillChangeWeightDto> queryLTLEWaybillChangeWeightResult(LTLEWaybillChangeWeightQueryDto dto);

	/**
	 * 添加PdpAppInfoEntity
	 */
	public int savePdaAppInfo(PdaAppInfoEntity pdaAppInfoEntity);

	/**
	 * 查询扫描表数据是否存在扫描过的数据
	 * @param waybillNo
	 * @return
	 */
	public PdaAppInfoEntity queryPdaAppInfoByWaybillNO(String waybillNo);

}
