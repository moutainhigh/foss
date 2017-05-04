package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillRepaymentEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffAmountDto;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * 还款单dao
 * @author 099995-foss-wujiangtao
 * @date 2012-10-12 下午6:20:40
 * @since
 * @version
 */
public class BillRepaymentEntityDao extends iBatis3DaoImpl implements IBillRepaymentEntityDao{
   
	private static final String NAMESPACES = "foss.stl.BillRepaymentEntityDao.";

	// -------------------- write methods --------------------
	
    /**
     * 
     * 新增还款单
     * @author 099995-foss-wujiangtao
     * @date 2012-10-17 下午8:51:34
     * @param entity
     * @return 
     */
	@Override
	public int add(BillRepaymentEntity entity) {
		return this.getSqlSession().insert(NAMESPACES +"insert",entity);
	}
	
	/**
	 * 红冲还款单
	 * @author ibm-zhuwei
	 * @date 2012-10-18 上午11:24:19
	 * @param 还款单
	 * @return
	 */
	@Override
	public int updateByWriteBack(BillRepaymentEntity entity) {
		return this.getSqlSession().update(NAMESPACES + "updateByWriteBack", entity);
	}

	/**
	 * 反核销-修改还款单的反核销金额
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-29 下午2:45:32
	 * @param id
	 * @param bverifyAmount
	 * @return 
	 */
	@Override
	public int updateByReverseWriteoff(BillWriteoffAmountDto dto) {
		return this.getSqlSession().update(NAMESPACES + "updateByReverseWriteoff", dto);
	}

	/**
	 * 批量审核/反审核还款单
     * 
     * @author 099995-foss-wujiangtao
     * @date 2012-11-12 上午11:07:07
     * @param dto 还款单DTO
     * @return
	 */
	@Override
	public int updateByBatchAudit(BillRepaymentDto dto) {
		return this.getSqlSession().update(NAMESPACES + "updateByBatchAudit", dto);
	}

	/** 
	 * 批量确认收银还款单
	 * @author ibm-zhuwei
	 * @date 2012-12-18 下午2:21:55
	 * @param dto 还款单DTO
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IBillRepaymentEntityDao#updateByConfirmCashier(com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentDto)
	 */
	@Override
	public int updateByConfirmCashier(BillRepaymentDto dto) {
		return this.getSqlSession().update(NAMESPACES + "updateByConfirmCashier", dto);
	}

	// -------------------- read methods --------------------
	
	/**
     * 
     * 根据传入的一到多个还款单ID号，获取一到多条还款单信息
     * @author foss-pengzhen
     * @date 2013-03-25 下午6:55:54
     * @param repaymentNos   还款单id号集合
     * @param active		 是否有效
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
	public List<BillRepaymentEntity> queryByRepaymentIds(List<String> repaymentIds,String active){
		if (CollectionUtils.isEmpty(repaymentIds)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("repaymentIds", repaymentIds);
		if(StringUtils.isNotEmpty(active)){
			map.put("active",active);
		}
		return this.getSqlSession().selectList(NAMESPACES + "selectByRepaymentIds", map);
    }

    /**
     * 
     * 根据传入的一到多个还款单号，获取一到多条还款单信息
     * @author 099995-foss-wujiangtao
     * @date 2012-10-12 下午6:55:54
     * @param repaymentNos   还款单号集合
     * @param active		 是否有效
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
	public List<BillRepaymentEntity> queryByRepaymentNOs(List<String> repaymentNos,String active){
		if (CollectionUtils.isEmpty(repaymentNos)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("repaymentNos", repaymentNos);
		if(StringUtils.isNotEmpty(active)){
			map.put("active",active);
		}
		return this.getSqlSession().selectList(NAMESPACES + "selectByRepaymentNos", map);
    }
    
    /**
     * 根据传入的一到多个来源单号，获取一到多条还款单信息 
     * @author dp-wujiangtao
     * @date 2012-10-17 下午7:55:56
     * @param sourceBillNos 来源单据号集合
     * @param active        是否有效
     * @return
     */
    @SuppressWarnings("unchecked")
	@Override
	public List<BillRepaymentEntity> queryBySourceBillNOs(
			List<String> sourceBillNos, String active) {
		if (CollectionUtils.isEmpty(sourceBillNos)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sourceBillNos",sourceBillNos);
		if(StringUtils.isNotEmpty(active)){
			map.put("active",active);
		}
		return this.getSqlSession().selectList(NAMESPACES + "selectBySourceBillNos", map);
		
	}
    
    /**
	 * 根据传入的到达实收单编号，查询是否存在有效的还款单
     * [到达实收单编号存储在还款单的来源单号属性]
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-17 下午8:09:16
	 * @param sourceBillNo
	 * @param sourceBillType
	 * @return 
	 */
	@Override
	public String queryBySourceBillNO(String sourceBillNo,String sourceBillType) {
		if(StringUtils.isEmpty(sourceBillNo)||StringUtils.isEmpty(sourceBillType)){
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sourceBillNo", sourceBillNo);
		map.put("sourceBillType", sourceBillType);
		map.put("active", FossConstants.ACTIVE);
		@SuppressWarnings("unchecked")
		List<BillRepaymentEntity> list=this.getSqlSession().selectList(NAMESPACES +"selectBySourceBillNo",map);
		if(CollectionUtils.isNotEmpty(list)){
			BillRepaymentEntity entity=list.get(0);
			return entity.getId();
		}
		return null;
	}

	/**
	 * 根据外发单号和外发代理编码是否已存在有效的还款单记录
     * 
     * @author 099995-foss-wujiangtao
     * @date 2012-11-19 下午7:01:29
     * @param dto 还款单条件DTO
     * @return
	 */
	@Override
	public int queryBillRepaymentByExternalBillNo(BillRepaymentConditionDto dto) {
		Object obj=this.getSqlSession().selectOne(NAMESPACES + "selectBillRepaymentByExternalBillNo",dto);
		if(obj!=null){
			return Integer.valueOf(obj.toString());
		}
		return 0;
	}

	/**
	 * 查询符合条件的还款单信息
     * 
     * @author 099995-foss-wujiangtao
     * @date 2012-11-21 上午8:20:33
     * @param dto 还款单条件DTO
     * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillRepaymentEntity> queryBillRepaymentByCondition(
			BillRepaymentConditionDto dto) {
		if (dto != null && (StringUtils.isNotEmpty(dto.getSourceBillNo()) || StringUtils.isNotEmpty(dto.getRepaymentNo())
				|| StringUtils.isNotEmpty(dto.getOnlinePaymentNo()) || StringUtils.isNotEmpty(dto.getWaybillNo()))) {
			return this.getSqlSession().selectList(NAMESPACES + "selectBillRepaymentByCondition", dto);
		}
		return null;
	}

	/**
	 * 根据来源单号集合和部门编码集合，查询还款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-22 上午9:35:33
	 * @param sourceBillNos
	 * @param orgCodes
	 * @param active
	 * @param currentInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillRepaymentEntity> queryBySourceBillNOsAndOrgCodes(List<String> sourceBillNos, List<String> orgCodes, String active,CurrentInfo currentInfo) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("sourceBillNos", sourceBillNos);
		map.put("orgCodes", orgCodes);
		map.put("active", active);
		map.put("currentInfo", currentInfo);
		return this.getSqlSession().selectList(NAMESPACES+"selectBySourceBillNOsAndOrgCodes",map);
	}
	
	/**
	 * 根据应收单号查询还款单信息
	 * @author 231434-foss-bieyexiong
	 * @date 2016-10-03 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillRepaymentEntity> queryRepaymentByReceivableNo(String receivableNo,String active,String writeoffType){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("receivableNo", receivableNo);
		map.put("active", active);
		map.put("writeoffType", writeoffType);
		return this.getSqlSession().selectList(NAMESPACES+"queryRepaymentByReceivableNo",map);
	}

}
