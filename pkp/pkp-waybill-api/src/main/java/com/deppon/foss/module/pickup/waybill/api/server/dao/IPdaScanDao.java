/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;
import com.deppon.foss.module.pickup.waybill.shared.domain.PdaScanEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ClientEWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillSalesDepartDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaScanQueryDto;


/**
 * PDA盲扫DAO
 * @author FOSS-136334-BaiLei
 * @date 2014-12-30 14:43:42
 * 
 */
public interface IPdaScanDao {

	/**
	 * 指定运单号的有效正扫扫描条数
	 * @author FOSS-136334-BaiLei
	 * @date 2014-12-30 14:43:42
	 * 
	 */
	public int countScanInfoByCondition(PdaScanQueryDto pdaScanQueryDto);
	
	
	/**
	 * 更新PDAScan表
	 * @author FOSS-136334-BaiLei
	 * @date 2014-12-30 14:43:42
	 * 
	 */
	public int updatePdaScanInfor(PdaScanEntity pdaScanEntity);
	
	/**
	 * 插入PDAScan表
	 * @author FOSS-136334-BaiLei
	 * @date 2014-12-30 14:43:42
	 * 
	 */
	public int insertPdaScanInfo(PdaScanEntity pdaScanEntity);

	List<PdaScanEntity> queryScanInforBySecondCondition(PdaScanQueryDto pdaScanQueryDto);

	List<PdaScanEntity> queryWaybillByNo(String waybillNo);

	public int updatePdaScanInfoById(PdaScanEntity pdaScanEntity);

	public int updatePdaScanInfoByTaskId(PdaScanEntity pdaScanEntity);
	
	List<PdaScanEntity> queryWaybillNoByCondition(PdaScanQueryDto pdaScanQueryDto);
	int deletePdaScanEntityByIdOrNo(PdaScanQueryDto pdaScanQueryDto);
	
	int batchInsertSelectivePdaScanInfo(List<PdaScanEntity> pdaScanEntityList);
	
	/**
	 * 查询该部门PDA快递员所有扫描的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-1-6 16:38:55
	 */
	List<EWaybillSalesDepartDto> queryEWaybillSalesDepart(ClientEWaybillConditionDto eWaybillConditionDto);
	
	/**
	 * 查询该部门PDA快递员所有扫描的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-1-6 16:38:55
	 */
	List<EWaybillSalesDepartDto> getAllOrderInfoFromDop(ClientEWaybillConditionDto eWaybillConditionDto);

	/**
	 * 查询数据是否数据在子母件数据里面
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-22 14:03:16
	 * @param pdaScanQueryDto
	 * @return
	 */
	public List<PdaScanEntity> queryPdaScanInfoWaybillRelateByCondition(PdaScanQueryDto pdaScanQueryDto);


	public List<EWaybillSalesDepartDto> queryEWaybillByDriverCode(
			ClientEWaybillConditionDto ewaybillConditionDto);
}
