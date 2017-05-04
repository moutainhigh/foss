package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPdaScanDao;
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
public class PdaScanDao  extends iBatis3DaoImpl implements IPdaScanDao{
	private static final String NAMESPACE = "foss.pkp.PdaScanEntityMapper.";

	/**
	 * 指定运单号的有效正扫扫描条数
	 * @author FOSS-136334-BaiLei
	 * @date 2014-12-30 14:43:42
	 * 
	 */
	public int countScanInfoByCondition(PdaScanQueryDto pdaScanQueryDto){
		return (Integer) getSqlSession().selectOne(NAMESPACE + "countScanInfoByCondition", pdaScanQueryDto);
	}
	
	/**
	 * 指定运单号的有效正扫扫描信息列表
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PdaScanEntity> queryScanInforBySecondCondition(PdaScanQueryDto pdaScanQueryDto){
		return this.getSqlSession().selectList(NAMESPACE + "queryScanInforByCondition", pdaScanQueryDto);		
	}
	
	
	/**
	 * 根据ID更改数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-1-6 16:51:07
	 */
	@Override
	public int updatePdaScanInfoById(PdaScanEntity pdaScanEntity){
		pdaScanEntity.setModifyTime(new Date());
		return this.getSqlSession().update(NAMESPACE+"updatePdaScanInfoById", pdaScanEntity);
	}
	
	/**
	 * 根据taskID更改数据
	 * @author 200972 lanhuilan
	 * @date 2015-2-4 16:51:07
	 */

	@Override
	public int updatePdaScanInfoByTaskId(PdaScanEntity pdaScanEntity) {
		pdaScanEntity.setModifyTime(new Date());
		return this.getSqlSession().update(NAMESPACE+"updatePdaScanInfoByTaskId", pdaScanEntity);
	}
	
	
	
	
	/**
	 * 通过运单编号查询PDA扫描信息
	 * 
	 * @param waybill
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PdaScanEntity> queryWaybillByNo(String waybillNo) {
		PdaScanQueryDto dto = new PdaScanQueryDto();
		dto.setWaybillNo(waybillNo);
		// 封装查询条件
		return this.getSqlSession().selectList(NAMESPACE + "queryScanInforByCondition", dto);
	}
	

	/**
	 * 更新PDAScan表
	 * @author FOSS-136334-BaiLei
	 * @date 2014-12-30 14:43:42
	 * 
	 */
	public int updatePdaScanInfor(PdaScanEntity pdaScanEntity){
		pdaScanEntity.setModifyTime(new Date());
		return getSqlSession().update(NAMESPACE + "updatePdaScanInfor", pdaScanEntity);
	}
	
	/**
	 * 插入PDAScan表
	 * @author FOSS-136334-BaiLei
	 * @return 
	 * @date 2014-12-30 14:43:42
	 * 
	 */
	public int insertPdaScanInfo(PdaScanEntity pdaScanEntity){
		pdaScanEntity.setModifyTime(new Date());
		return getSqlSession().insert(NAMESPACE + "insertSelectivePdaScanInfo", pdaScanEntity);
	}
   
	/**
	 * 根据指定条件查询不重复的运单号
	 * @author 200972 lanhuilan
	 * @date 2015-2-4 16:51:07
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PdaScanEntity> queryWaybillNoByCondition(PdaScanQueryDto pdaScanQueryDto) {
	 return this.getSqlSession().selectList(NAMESPACE + "queryWaybillNoByCondition", pdaScanQueryDto);
	}

	@Override
	public int deletePdaScanEntityByIdOrNo(PdaScanQueryDto pdaScanQueryDto) {
		return this.getSqlSession().delete(NAMESPACE+"deletePdaScanEntityByIdOrNo", pdaScanQueryDto);
	}

	/**
	 * 批量插入数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-3-4 19:45:10
	 */
	@Override
	public int batchInsertSelectivePdaScanInfo(List<PdaScanEntity> pdaScanEntityList) {
		return this.getSqlSession().insert(NAMESPACE+"batchInsertSelectivePdaScanInfo", pdaScanEntityList);
	}
	
	/**
	 * 查询该部门PDA快递员所有扫描的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-1-6 16:38:55
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EWaybillSalesDepartDto> queryEWaybillSalesDepart(ClientEWaybillConditionDto eWaybillConditionDto) {
		return this.getSqlSession().selectList(NAMESPACE+"queryEWaybillSalesDepart", eWaybillConditionDto);
	}

	/**
	 * 查询该部门PDA快递员所有扫描的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-1-6 16:38:55
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EWaybillSalesDepartDto> getAllOrderInfoFromDop(ClientEWaybillConditionDto eWaybillConditionDto) {
		return this.getSqlSession().selectList(NAMESPACE+"getAllOrderInfoFromDop", eWaybillConditionDto);
	}

	/**
	 * 查询数据是否数据在子母件数据里面
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-22 14:03:16
	 * @param pdaScanQueryDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PdaScanEntity> queryPdaScanInfoWaybillRelateByCondition(PdaScanQueryDto pdaScanQueryDto) {
		return this.getSqlSession().selectList(NAMESPACE+"queryPdaScanInfoWaybillRelateByCondition", pdaScanQueryDto);
	}

	/**
	 * 依据快递员编号查询运单信息
	 * @author Foss-liuyi
	 * @param ewaybillConditionDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EWaybillSalesDepartDto> queryEWaybillByDriverCode(
			ClientEWaybillConditionDto ewaybillConditionDto) {
		return this.getSqlSession().selectList(NAMESPACE+"queryEWaybillByDriverCode", ewaybillConditionDto);
	}
}