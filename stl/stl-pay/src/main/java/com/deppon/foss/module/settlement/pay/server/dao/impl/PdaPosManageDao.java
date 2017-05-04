package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;
import com.deppon.foss.module.settlement.pay.api.server.dao.IPdaPosManageDao;

/**
 * 提供接口功PDA和FOSS系统对POS刷卡管理和明细数据的新增和修改
 * 
 * @ClassName: IPdaPosManageService
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-1-17 下午1:38:55
 */
public class PdaPosManageDao extends iBatis3DaoImpl implements IPdaPosManageDao {
	public static final String NAMESPACES = "foss.stl.PdaPosManageDao.";

	/**
	 * 插入POS刷卡流水信息
	 * 
	 * @Title: insertPosCardMessage
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @param entity
	 *            :POS刷卡产生的数据
	 */
	@Override
	public int addPosCardMessage(PosCardEntity entity) {
		int result = this.getSqlSession().insert(
				NAMESPACES + "insertPdaPosCardManage", entity);
		return result;
	}

	/**
	 * 插入POS刷卡明细数据
	 * 
	 * @Title: insertPosCardDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@Override
	public int addPosCardDetail(List<PosCardDetailEntity> list) {
		int result = this.getSqlSession().insert(
				NAMESPACES + "insertPdaPosCardDetail", list);
		return result;
	}

	/**
	 * 更新POS刷卡金额
	 * 
	 * @Title: updatePosCardMessage
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@Override
	public int updatePosCardMessageAmount(PosCardEntity entity) {
		int result = this.getSqlSession().update(
				NAMESPACES + "updatePdaPosCardMessageAmount", entity);
		return result;
	}

	/**
	 * 更新明细金额
	 * 
	 * @Title: updatePosCardMessage
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@Override
	public int updatePosCardDetailAmount(PosCardManageDto dto) {
		int result = this.getSqlSession().update(
				NAMESPACES + "updatePdaPosCardDetailAmount", dto);
		return result;
	}

	/**
	 * 插入明细数据
	 * 
	 * @Title: addPosCardDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@Override
	public int addPosCardDetail(PosCardDetailEntity entity) {
		int result = this.getSqlSession().update(
				NAMESPACES + "addPosCardDetail", entity);
		return result;
	}

	/**
	 * 跟新明细数据
	 * 
	 * @Title: updateSinglePosCardDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@Override
	public int updateSinglePosCardDetail(PosCardDetailEntity entity) {
		int result = this.getSqlSession().update(
				NAMESPACES + "updateSinglePosCardDetail", entity);
		return result;
	}

	/**
	 * 根据交易流水号去更新T+0报表已使用流水号金额和未使用金额
	 * 
	 * @Title: updatePosCardByNumber
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@Override
	public int updatePosCardByNumber(PosCardDetailEntity entity) {
		int result = this.getSqlSession().update(
				NAMESPACES + "updatePosCardByNumber", entity);
		return result;
	}

	/**
	 * 根据交易流水号或单据号去查询刷卡数据
	 * 
	 * @Title: queryPosCardData
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PosCardEntity> queryPosCardData(PosCardManageDto dto) {
		List<PosCardEntity> list = this.getSqlSession().selectList(
				NAMESPACES + "queryPosCardData", dto);
		return list;
	}

	/**
	 * 根据运单查询到达应收部门
	 * 
	 * @Title: queryDRDeptByWaybillNo
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PosCardEntity queryDRDeptByWaybillNo(PosCardDetailEntity entity) {
		List<PosCardEntity> list = this.getSqlSession().selectList(
				NAMESPACES + "queryDRDeptByWaybillNo", entity);
		return list.get(0);
	}

	/**
	 * 根据运单查询到达应收部门
	 * 
	 * @Title: queryDRORDeptByWaybillNo
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PosCardEntity queryDRORDeptByWaybillNo(PosCardDetailEntity entity) {
		List<PosCardEntity> list = this.getSqlSession().selectList(
				NAMESPACES + "queryDRORDeptByWaybillNo", entity);
		return list.get(0);
	}

	/**
	 * 更具单据号去查询对账单实体
	 * 
	 * @Title: PdaStatementManageEntity
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PosCardEntity queryUnpaidAmountByNo(String invoiceNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("number", invoiceNo);
		List<PosCardEntity> list = this.getSqlSession().selectList(
				NAMESPACES + "queryUnpaidAmountByNo", map);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据单据号获取结清货款未核销金额
	 * 
	 * @Title: queryUnverifyAmountByNo
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PosCardEntity queryUnverifyAmountByNo(String invoiceNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("number", invoiceNo);
		List<PosCardEntity> list = this.getSqlSession().selectList(
				NAMESPACES + "queryUnverifyAmountByNo", map);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**************** 业务异常处理 ***************/
	/**
	 * 运单更改，金额减少 更新明细中已使用流水号金额和单据总金额， 更新T+0已使用流水号金额，未使用金额
	 * 
	 * 参数：单据号和流水号金额和减少的金额
	 * 
	 * @Title: updatePosCardByNumber
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@Override
	public int updatePosByNoForMoney(PosCardDetailEntity entity) {
		int result = this.getSqlSession().update(
				NAMESPACES + "updatePosByNoForMoney",entity);
		return result;
	}

	/**
	 * 运单已支付，然后作废 作废明细数据，释放T+0中的已使用流水号金额和未使用金额 参数：单据号和交易流水号、单据金额
	 * 
	 * @Title: updatePosCardByNumber
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@Override
	public int deleteDetailByNo(PosCardDetailEntity entity) {
		int result = this.getSqlSession().update(
				NAMESPACES + "deleteDetailByNo",entity);
		return result;
	}

	/**
	 * 根据单据号和交易流水号去查询明细数据
	 * 
	 * @Title: queryDetailsByNo
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PosCardDetailEntity> queryDetailsByNo(PosCardDetailEntity entity) {
		List<PosCardDetailEntity> list = this.getSqlSession().selectList(
				NAMESPACES + "queryDetailsByNo",entity);
		return list;
	}

	/**
	 * 根据单据号去查询明细，存在则更新，不存在则插入数据
	 * 
	 * @Title: insertOrUpdateDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@Override
	public int insertOrUpdateDetail(PosCardDetailEntity entity) {
		return this.getSqlSession().insert(NAMESPACES+"insertOrUpdateDetail",entity); 
	}
	
	/**
	 * 根据单据号去更新对账单总金额
	 * 
	 * @Title: updateStatementAmout
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-4-15 下午7:44:31
	 */
	@Override
	public int updateStatementAmount(PosCardDetailEntity entity) {
		int result = this.getSqlSession().update(NAMESPACES+"updateStatementAmount", entity);
		return result;
	}
	
	/**
	 * 更新流水号金额，异常数据操作
	 * 
	 * @Title: updateExceptionDataByNum
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-5-29 下午1:35:49
	 */
	@Override
	public int updateExceptionDataByNum(PosCardDetailEntity entity){
		int result = this.getSqlSession().update(NAMESPACES+"updateExceptionDataByNum", entity);
		return result;
	}

	/**
	 * 删除手动导入的异常流水数据
	 *
	 * @Title: deleteExceptionData
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-11-1 下午2:02:07
	 */
	@Override
	public int deleteExceptionData(PosCardEntity posCardEntity) {
		int result = this.getSqlSession().update(NAMESPACES+"deleteExceptionData", posCardEntity);
		return result;
	}
	
	/**
	 * 
	 */
	@Override
	public int cancelPosCardbyParam(Map<String, String> map) {
		int result = this.getSqlSession().update(NAMESPACES+"cancelPosCardbyParam", map);
		return result;
	}

	/**
	 * 暂用于更新冻结状态
	 *
	 * @Title: updatePosCardEntitys
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-11-1 下午1:35:49
	 */
	@Override
	public int updatePosCardEntitys(PosCardEntity posCardEntity) {
		int result = this.getSqlSession().update(NAMESPACES+"updatePosCardEntity", posCardEntity);
		return result;
	}

}
