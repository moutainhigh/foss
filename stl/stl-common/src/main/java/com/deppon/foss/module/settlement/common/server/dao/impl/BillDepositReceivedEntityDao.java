package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillDepositReceivedEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillDepositReceivedEntityDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 预收单公共DAO接口实现类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-18 上午11:31:34
 */
public class BillDepositReceivedEntityDao extends iBatis3DaoImpl implements IBillDepositReceivedEntityDao {

	private static final String NAME_SPACES = "foss.stl.BillDepositReceivedEntityDao.";// 命名空间路径

	/**
	 * 新增预收单
     * @author foss-qiaolifeng
     * @date 2012-11-12 上午11:40:32
     * @param entity 预收单
     * @return
	 */
	@Override
	public int add(BillDepositReceivedEntity entity) {
		return this.getSqlSession().insert(NAME_SPACES + "insert", entity);
	}

	/**
	 * 新增合伙人预收单
     * @author foss-guoxinru
     * @date 2016-1-12
     * @param entity 预收单
	 */
	@Override
	public int addPartner(BillDepositReceivedEntity entity) {
		return this.getSqlSession().insert(NAME_SPACES + "insertPartner", entity);
	}
	
	/**
	 * 修改预收单的对账单号及是否生成对账单字段值
     * @author 088933-foss-zhangjiheng
     * @date 2012-10-17 下午7:30:36
     * @param entity 预收单
     * @return
	 */
	@Override
	public int updateBillDepositReceivedByMakeStatement(BillDepositReceivedEntity entity) {
		return this.getSqlSession().update(NAME_SPACES + "updateByStatementMake", entity);
	}

	/**
	 * 批量修改应收单的对账单号及是否生成对账单字段值
     * @author foss-qiaolifeng
     * @date 2012-12-5 上午9:28:55
     * @param dto 预收单DTO
     * @return
	 */
	@Override
	public int batchUpdateBillDepositReceivedByMakeStatement(BillDepositReceivedEntityDto dto) {
		return this.getSqlSession().update(NAME_SPACES + "batchUpdateByStatementMake", dto);
	}

	/**
	 * 核销时修改预收单的金额等字段信息
     * @author foss-qiaolifeng
     * @date 2012-10-19 上午11:21:40
	 * @param entity 预收单
	 * @param writeOffAmount 核销金额
	 * @param currentInfo 当前用户
	 * @return
	 */
	@Override
	public int writeOffBillDepositReceived(BillDepositReceivedEntity entity,BigDecimal writeOffAmount, CurrentInfo currentInfo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Date now = new Date();
		paramMap.put("id", entity.getId());
		paramMap.put("modifyTime", now);
		paramMap.put("active", FossConstants.ACTIVE);
		paramMap.put("writeOffAmount", writeOffAmount);
		paramMap.put("modifyUserCode", currentInfo.getEmpCode());
		paramMap.put("modifyUserName", currentInfo.getEmpName());
		paramMap.put("versionNo", entity.getVersionNo());
		return this.getSqlSession().update(NAME_SPACES + "writeOffBillDepositReceived", paramMap);
	}

	/**
	 * 根据传入的多个预收单id号，获取预收单列表信息
     * @author foss-pengzhen
     * @date 2012-10-19 下午1:40:42
     * @param depositReceivedIds 预收单号集合
     * @param active 是否有效
     * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillDepositReceivedEntity> queryByDepositReceivedIds(List<String> depositReceivedIds, String active) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("depositReceivedIds", depositReceivedIds);
		if (StringUtils.isNotEmpty(active)) {
			paramMap.put("active", active);
		}
		return this.getSqlSession().selectList(NAME_SPACES + "selectByDepositReceivableIds", paramMap);
	}
	
	/**
	 * 根据传入的多个预收单号，获取预收单列表信息
     * @author foss-qiaolifeng
     * @date 2012-10-19 下午1:40:42
     * @param depositReceivedNos 预收单号集合
     * @param active 是否有效
     * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillDepositReceivedEntity> queryByDepositReceivedNOs(List<String> depositReceivedNos, String active) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("depositReceivedNos", depositReceivedNos);
		if (StringUtils.isNotEmpty(active)) {
			paramMap.put("active", active);
		}
		return this.getSqlSession().selectList(NAME_SPACES + "selectByDepositReceivableNos", paramMap);
	}

	/**
	 * 根据传入的一个预收单号，获取一条预收单
     * @author foss-qiaolifeng
     * @date 2012-10-22 下午2:07:57
     * @param depositReceivedNo 预收单号
     * @param active 是否有效
     * @return
	 */
	@Override
	public BillDepositReceivedEntity queryByDepositReceivedNo(String depositReceivedNo, String active) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("depositReceivedNo", depositReceivedNo);
		paramMap.put("active", active);
		return (BillDepositReceivedEntity) this.getSqlSession().selectOne(NAME_SPACES + "selectoneByDepositReceivableNo", paramMap);
	}

	/**
     * 收银确认
     * @author foss-pengzhen
     * @date 2012-12-17 上午11:53:26
	 * @param dto 预收单DTO
	 * @return
     */
	@Override
	public int updateByConfirmCashier(BillDepositReceivedEntityDto dto) {
		return this.getSqlSession().update(NAME_SPACES + "updateByConfirmCashier", dto);
    }
	
	/**
	 * 修改失效预收单
     * @author foss-qiaolifeng
     * @date 2012-11-27 上午9:11:06
     * @param entity 预收单
     * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IBillDepositReceivedEntityDao#updateByTakeEffect(com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity)
	 */
	@Override
	public int updateByTakeEffect(BillDepositReceivedEntity entity) {
		return this.getSqlSession().update(NAME_SPACES + "updateByTakeDisable",entity);
	}

	/**
	 * 修改预收单支付状态
     * @author foss-qiaolifeng
     * @date 2012-12-3 下午6:46:12
     * @param entity 预收单
     * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IBillDepositReceivedEntityDao#updateByPaymentStatus(com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity)
	 */
	@Override
	public int updateByPaymentStatus(BillDepositReceivedEntity entity) {
		return this.getSqlSession().update(NAME_SPACES + "updateByPaymentStatus", entity);
	}

	/**
	 * 根据付款单号查询预收单
     * @author foss-qiaolifeng
     * @date 2012-12-4 下午3:25:12
     * @param paymentNo 支付单号
     * @param active 是否有效
     * @param isRedBack 是否红冲
     * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IBillDepositReceivedEntityDao#queryByPaymentNo(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillDepositReceivedEntity> queryByPaymentNo(String paymentNo,String active, String isRedBack) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("paymentNo", paymentNo);
		//active不为空，设置active
		if (StringUtils.isNotEmpty(active)) {
			paramMap.put("active", active);
		}
		//isRedBack不为空，设置isRedBack
		if (StringUtils.isNotEmpty(isRedBack)) {
			paramMap.put("isRedBack", isRedBack);
		}
		return this.getSqlSession().selectList(NAME_SPACES + "selectListByPaymentNo", paramMap);
	}
	
	 /**
     * 根据付款单号查询预收单
     * @author foss-pengzhen
     * @date 2012-12-4 下午3:25:12
     * @param paymentNos 支付单号
     * @param active 是否有效
     * @param isRedBack 是否红冲
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
	public List<BillDepositReceivedEntity> queryByPaymentNos(List<String> paymentNos, String active,String isRedBack){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("paymentNos", paymentNos);
		if (StringUtils.isNotEmpty(active)) {
			paramMap.put("active", active);
		}
		if (StringUtils.isNotEmpty(isRedBack)) {
			paramMap.put("isRedBack", isRedBack);
		}
    	return this.getSqlSession().selectList(NAME_SPACES + "selectListByPaymentNos", paramMap);
    }

	/* 
	 * 根据汇款编号查询合伙人预收单
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IBillDepositReceivedEntityDao#queryByRemitNo(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillDepositReceivedEntity> queryByRemitNo(String remitNo,
			String active) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("remitNo", remitNo);
		params.put("active", active);
		return this.getSqlSession().selectList(NAME_SPACES + "queryByRemitNo", params);
	}

	
}
