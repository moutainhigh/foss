package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IReverseSignSettleDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSCODEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSCodAuditEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSResverSettleRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSWaybillSignResultEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSWaybillTransactionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillEntityForVTS;
import com.deppon.foss.module.settlement.common.api.shared.dto.ActualFreightVTSEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.VTSCodAuditDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.WaybillFRcQueryByWaybillNoVTSDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.WaybillQueryArgsVTSDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * @author 218392 zhangyongxue
 * @date 2016-06-12 17:12:20
 * vts自动反签收反结清Dao
 *
 */
public class ReverseSignSettleDao extends iBatis3DaoImpl implements IReverseSignSettleDao{
	
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE="foss.stl.reverseSignSettleMapper.";

	/**
	 * 根据条件查询代收货款支付审核
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<VTSCodAuditEntity> selectByCondition(VTSCodAuditDto dto) {

		if(dto == null){
			throw new SettlementException("vts查询代收货款支付审核参数为空！");
		}
		return this.getSqlSession().selectList(NAMESPACE + "selectByCondition", dto);
	}

	/**
	 * 根据条件查询代收货款，进而判断代收货款的状态
	 */
	@SuppressWarnings("unchecked")
	@Override
	public VTSCODEntity queryByWaybillNo(VTSCODEntity dto) {
		if(dto == null){
			throw new SettlementException("vts查询代收货款参数为空！");
		}
		List<VTSCODEntity> vTSCODEntityList = this.getSqlSession().selectList(NAMESPACE + "queryByWaybillNo", dto);
		if(CollectionUtils.isEmpty(vTSCODEntityList)){
			return null;
		}else{
			return vTSCODEntityList.get(0);
		}
	
	}

	/**
	 * 反业务完结 @218392 zhangyongxue 
	 * @date 2016-06-13 10:37:00
	 */
	@Override
	public boolean updateReverseBusinessOver(
			VTSWaybillTransactionEntity waybillTransactionEntity) {
		if(waybillTransactionEntity == null){
			return false;
		}
		return this.getSqlSession().update(
				NAMESPACE + "updateReverseBusinessOver", waybillTransactionEntity) > 0 ? true : false;
	}
	
	
	/**
	 * 据运单号,active = 'Y'查询运单签结果里的运单信息
	 * @author 218392 张永雪
	 * @date 2016-06-13  17:18:30
	 * @param entity
	 */
	@SuppressWarnings("unchecked")
	@Override
	public VTSWaybillSignResultEntity queryWaybillSignResult(VTSWaybillSignResultEntity entity) {
		
		if(entity == null){
			throw new SettlementException("VTS查询运单签收结果表传入参数为空！");
		}
		List<VTSWaybillSignResultEntity> waybillSignResultEntitys =
				this.getSqlSession().selectList(NAMESPACE + "queryWaybillSignResult",entity);
		return CollectionUtils.isEmpty(waybillSignResultEntitys) ? null : waybillSignResultEntitys.get(0);
	}
	
	/**
	 * 修改运单签收结果(作废当前运单)
	 * @author FOSS结算开发组 218392 张永雪
	 * @date 2016-06-13 17:32:00
	 */
	@Override
	public int updateWaybillSignResult(VTSWaybillSignResultEntity entity) {
		if(entity == null){
			throw new SettlementException("VTS修改运单签收结果表传入参数为空！");
		}
		return this.getSqlSession().update(NAMESPACE + "updateById",entity);
	}

	 /**
	  * 根据ID查询到达付款信息表
	  * @218392 zhangyongxue 
	  * @date 2016-06-15 21:15:20
	  */
	@SuppressWarnings("unchecked")
	@Override
	public List<VTSRepaymentEntity> searchRepaymentById(List<String> ids) {
		
		if(StringUtils.isEmpty(ids)){
			throw new SettlementException("查询到达付款信息传入参数ids为空");
		}
		
		return this.getSqlSession().selectList(NAMESPACE + "selectRepaymentById", ids);
		
	}

	 /** 修改付款数据
	 * @author FOSS结算开发组 @218392 张永雪
	 * @date 2016-06-16  10:30:00
	 */
	@Override
	public int updateRepaymentById(VTSRepaymentEntity record) {
		if(record == null){
			throw new SettlementException("VTS修改到达付款信息传入参数为空！");
		}
		record.setActive("N");
		return getSqlSession().update(NAMESPACE + "updateRepaymentById", record);
	}

	/**
	 * 根据单据号和交易流水号去查询明细数据
	 * 
	 * @Title: queryDetailsByNo
	 * @author：218392 zhangyongxue
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PosCardDetailEntity> queryDetailsByNo(PosCardDetailEntity entity) {
		if(entity == null){
			throw new SettlementException("VTS自动反结清，查询T+0明细传入参数为空！");
		}
		List<PosCardDetailEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "queryDetailsByNo",entity);
		return list;
	}

	/**
	 * 反结清 更新T+0已使用流水号金额，未使用金额
	 * 参数：单据号和流水号金额和减少的金额
	 * @Title: updatePosByNoForMoney
	 * @author： 218392 zhangyongxue
	 */
	@Override
	public int updatePosByNoForMoney(PosCardDetailEntity entity) {
		if(entity == null){
			throw new SettlementException("VTS自动反结清，更新T+0已使用流水号金额传入参数为空！");
		}
		int result = this.getSqlSession().update(
				NAMESPACE + "updatePosByNoForMoney",entity);
		return result;
	}

	/**
	 * 反结清 释放T+0中的已使用流水号金额和未使用金额 参数：单据号和交易流水号、单据金额
	 * @Title: deleteDetailByNo
	 * @author： 218392 zhangyongxue
	 */
	@Override
	public int deleteDetailByNo(PosCardDetailEntity entity) {
		if(entity == null){
			throw new SettlementException("VTS自动反结清，释放T+0中的已使用流水号金额和未使用金额传入参数为空！");
		}
		int result = this.getSqlSession().update(
				NAMESPACE + "deleteDetailByNo",entity);
		return result;
	}

	/**
	 * 根据交易流水号和单据号去查询明细数据，存在则更新，不存在则插入数据
	 */
	@Override
	public int insertOrUpdateDetail(PosCardDetailEntity entity) {
		if(entity == null){
			throw new SettlementException("VTS自动反结清，根据交易流水号和单据号去查询明细数据传入参数为空！");
		}
		
		return this.getSqlSession().insert(NAMESPACE+"insertOrUpdateDetail",entity);
		
	}

	/**
	 * 根据运单号查询有效的运单信息
	 */
	@Override
	public WaybillEntityForVTS queryWaybillByNo(String waybillNo) {
		if(StringUtils.isEmpty(waybillNo)){
			throw new SettlementException("VTS自动反结清，根据运单号查询有效的运单信息传入参数为空！");
		}
		// 封装查询条件
		WaybillQueryArgsVTSDto argsDto = new WaybillQueryArgsVTSDto();
		argsDto.setWaybillNo(waybillNo);
		argsDto.setActive(FossConstants.YES);

		return (WaybillEntityForVTS) this.getSqlSession().selectOne(
				NAMESPACE + "selectByWaybillNoAndOrderNo", argsDto);
		
	}

	/**
	 * 根据运单号查询是否更中
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryWaybillRfcByWaybillNo(WaybillFRcQueryByWaybillNoVTSDto waybillFRcQueryByWaybillNoVTSDto) {
		if(waybillFRcQueryByWaybillNoVTSDto == null){
			throw new SettlementException("VTS反结清查询运单更改单传入参数为空！");
		}
		List<String> waybills = this.getSqlSession().selectList(NAMESPACE + "queryWaybillRFcByWaybillNo", waybillFRcQueryByWaybillNoVTSDto);
		if (waybills != null && waybills.size() > 0) {
			return waybills.get(0);
		}
		return null;
		
	}

	/**
	 * 反实际承运表中结清状态为未结清：N
	 */
	@Override
	public void updateActualFreightSettleStatusByNo(
			ActualFreightVTSEntity actualFreightVTSEntity) {
		if(actualFreightVTSEntity == null){
			throw new SettlementException("VTS反结清-反实际承运表结清状态为N传入参数为空！");
		}
		actualFreightVTSEntity.setModifyTime(new Date());
		 this.getSqlSession().update(NAMESPACE + "updateArriveByWaybillNo",actualFreightVTSEntity);
		
	}

	/**
	 * 查询结清货款的时候，到达付款信息表：运单号，有效，财务单据已生成，财务单据未生成
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<VTSResverSettleRepaymentEntity> queryRepaymentList(
			VTSResverSettleRepaymentEntity re) {
		
		return getSqlSession().selectList(NAMESPACE + "queryRepaymentList", re);
		
	}
	
	

}
