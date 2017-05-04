package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillAdvancedPaymentEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillAdvancedPaymentEntityDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 预付单公共DAO接口实现类
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-18 下午1:04:18
 */
public class BillAdvancedPaymentEntityDao extends iBatis3DaoImpl implements IBillAdvancedPaymentEntityDao {
    
	private static final String NAMESPACES = "foss.stl.BillAdvancedPaymentEntityDao.";
	

    /**
     * 修改预付单的对账单号及是否生成对账单字段值
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-17 下午7:30:36
     * @param entity 预付单
     * @return
     */
	@Override
	public int updateBillAdvancedPaymentByMakeStatement(BillAdvancedPaymentEntity entity) {
		return this.getSqlSession().update( NAMESPACES +"updateByStatementMake", entity);
	}

    /**
	 * 根据传入的一到多个来源单号，获取一到多条应收单信息
	 * @author foss-pengzhen
	 * @date 2012-10-18 上午11:52:05
	 * @param sourceBillNos
	 *            来源单号集合
	 * @param active
	 *            是否有效
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<BillAdvancedPaymentEntity> queryBillAdvancedPaymentNos(List<String> advancesNos, String active){
		
		//来源单号集合不能为空，和查询状态不能为空
		if( CollectionUtils.isNotEmpty(advancesNos) && StringUtils.isNotEmpty(active) ){
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("advancesNos", advancesNos);
    		map.put("active", FossConstants.ACTIVE);
    		return (List<BillAdvancedPaymentEntity>) this.getSqlSession().selectList(NAMESPACES+"queryBillAdvancedPaymentNos",map);
		}
		return null;
	}

	/**
	 * 核销空运预付的金额
	 * @author foss-pengzhen
	 * @date 2012-10-22 下午1:54:20
	 * @param id
	 * @param writeOffAmount 核销金额
	 * @return
	 */
	public int writeoffAdvancedPayment(BillAdvancedPaymentEntity entity, BigDecimal writeOffAmount){
		//预付实体不能为空，和核销金额不能为空
		if(entity != null && writeOffAmount != null){
    		Map<String, Object> map = new HashMap<String, Object>();
    		Date now = new Date();
    		map.put("id", entity.getId());
    		map.put("modifyTime", now);
    		map.put("active", FossConstants.ACTIVE);
    		map.put("versionNo", entity.getVersionNo());
    		map.put("writeOffAmount", writeOffAmount);
    		return this.getSqlSession().update(NAMESPACES+"writeoffAdvancedPayment", map);
		}
		return 0;
	}

	/**
	 * 根据单个单号查询数据
	 * @author foss-pengzhen
	 * @date 2012-10-23 下午5:00:57
	 * @param advancesNo 预付单号
	 * @param active 是否有效
	 * @return
	 * @see
	 */
	public BillAdvancedPaymentEntity queryBillAdvancedPaymentNo(String advancesNo,String active){
		//预付单号和是否有效不为空，进行下面的查询
		if( StringUtils.isNotEmpty(advancesNo) && StringUtils.isNotEmpty(active) ){
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("advancesNo", advancesNo);
    		map.put("active", FossConstants.ACTIVE);
    		return (BillAdvancedPaymentEntity) this.getSqlSession().selectOne(NAMESPACES + "queryBillAdvancedPaymentNo", map);
		}
		return null;
	}

	/**
	 * 新增预收单
	 * @author foss-pengzhen
	 * @date 2012-11-12 下午5:30:59
	 * @param entity 预付单
	 * @return
	 * @see
	 */
	public int addAdvancedPaymentEntity(BillAdvancedPaymentEntity entity){
		return this.getSqlSession().insert(NAMESPACES + "insert", entity);
	}

	/**
	 * 修改审批状态
	 * @author foss-pengzhen
	 * @date 2013-1-7 下午4:24:23
	 * @param entity
	 * @return
	 * @see
	 */
	public int updateAdvancePaymentAuditStatus(BillAdvancedPaymentEntity entity){
		return this.getSqlSession().update(NAMESPACES + "updateAdvancePaymentAuditStatus", entity);
	}
	
	/**
	 * 根据预付单，更新费控产生工作流号到Foss
	 * @author 095793-foss-LiQin
	 * @date 2012-11-26 下午4:14:12
	 * @param entity 预付单
	 * @return
	 */
	public int updatePaymentBillWorkFlow(BillAdvancedPaymentEntity entity){
		return this.getSqlSession().update(NAMESPACES + "updatePaymentBillWorkFlow",
				entity);
	}
	
	/**
	 * 更新费控返回审批结果
	 * @author 095793-foss-LiQin
	 * @date 2012-11-26 下午8:07:40
	 * @param entity 预付单
	 * @return
	 */
	public int updatePaymentBillResult(BillAdvancedPaymentEntity entity){
		return this.getSqlSession().update(NAMESPACES + "updatePaymentBillResult",entity);
	}

	/**
	 * 作废预付单
	 * @author 095793-foss-LiQin
	 * @date 2012-11-28 下午6:33:02
	 * @param entity 预付单
	 * @return
	 */
	public int writeBackAdvancePay(BillAdvancedPaymentEntity entity){
		return this.getSqlSession().update(NAMESPACES+"writeBackAdvancePay",entity); 
	}

	/**
	 * 批量修改应付单的对账单单号
	 * @author foss-pengzhen
	 * @date 2012-12-4 下午8:06:47
	 * @param dto 预付单Dto
	 * @return
	 */
	@Override
	public int updateBatchByMakeStatement(BillAdvancedPaymentEntityDto dto) {
		return this.getSqlSession().update(NAMESPACES+"updateBatchByMakeStatement", dto);
	}

	/**
	 * 更新费控返回审批结果，审批失败
	 * @author foss-liqin
	 * @date 2013-02-28 下午8:06:47
	 * @param dto 预付单Dto
	 * @return
	 */
	@Override
	public int updatePaymentBillResultFail(BillAdvancedPaymentEntity entity) {
		return this.getSqlSession().update(NAMESPACES + "updatePaymentBillResultFail",entity);
	}
}
