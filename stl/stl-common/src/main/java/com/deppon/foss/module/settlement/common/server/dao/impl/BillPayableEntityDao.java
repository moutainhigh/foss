package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillPayableEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffAmountDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 应付单 dao
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-10-12 下午6:01:33
 */
public class BillPayableEntityDao extends iBatis3DaoImpl implements IBillPayableEntityDao {

	private static final String NAMESPACES = "foss.stl.BillPayableEntityDao.";

	// -------------------- write methods --------------------
	
	/**
	 * 保存应付单
     * @author ibm-zhuwei
     * @date 2012-11-8 下午7:30:23
     * @param entity 应付单
     * @return
	 */
	@Override
	public int add(BillPayableEntity entity) {
		return this.getSqlSession().insert(NAMESPACES + "insert", entity);
	}

    /**
     * 红冲应付单，原记录置为失效状态
     * @author ibm-zhuwei
     * @date 2012-10-23 下午3:38:43
     * @param entity 应付单
     * @return
     */
	@Override
	public int updateByWriteBack(BillPayableEntity entity) {
		return this.getSqlSession().update(NAMESPACES + "updateByWriteBack", entity);
	}

    /**
     * 应付单核销
     * @author ibm-zhuwei
     * @date 2012-10-19 上午11:03:14
     * @param dto 核销DTO
     * @return
     */
	@Override
	public int updateByWriteoff(BillWriteoffAmountDto dto) {
		return this.getSqlSession().update(NAMESPACES + "updateByWriteoff", dto);
	}

	/**
	 * 应付单生效/失效
     * @author ibm-zhuwei
     * @date 2012-10-22 下午2:50:06
     * @param entity 应付单
     * @return
	 */
	@Override
	public int updateByTakeEffect(BillPayableEntity entity) {
		return this.getSqlSession().update(NAMESPACES + "updateByTakeEffect", entity);
	}

	/**
	 * 批量审核/反审核应付单
     * @author 099995-foss-wujiangtao
     * @date 2012-11-1 上午8:14:08
     * @param dto 应付单DTO
     * @return
     * @see
	 */
	@Override
	public int updateByBatchAudit(BillPayableDto dto) {
		return this.getSqlSession().update(NAMESPACES + "updateByBatchAudit", dto);
	}

	/**
	 * 冻结应付单
     * 
     * @author 099995-foss-wujiangtao
     * @date 2012-11-2 下午5:59:41
     * @param entity 应付单
     * @return
     * @see
	 */
	@Override
	public int updateByFrozen(BillPayableEntity entity) {
		return this.getSqlSession().update(NAMESPACES + "updateByFrozen", entity);
	}
	
    /**
     * 批量冻结应付单
	 *
     * @author foss-guxinhua
     * @date 2013-5-3 下午5:45:26
     * @param entity
     * @return
     */
	@Override
	public int updateByFrozenByBatch(BillPayableDto dto){
		return this.getSqlSession().update(NAMESPACES + "updateByFrozenByBatch", dto);
    }

	/**
	 * 取消冻结应付单
     * 
     * @author 099995-foss-wujiangtao
     * @date 2012-11-2 下午5:59:41
     * @param entity 应付单
     * @return
	 */
	@Override
	public int updateByCancelFrozen(BillPayableEntity entity) {
		
		return this.getSqlSession().update(NAMESPACES + "updateByCancelFrozen", entity);
	}
	
	/**
	 * 批量取消冻结应付单
	 * @author foss-guxinhua
	 * @date 2013-5-10 上午9:35:24
	 * @param dto
	 * @return
	 */
	@Override
	public int updateByCancelFrozenByBatch(BillPayableDto dto) {
		return this.getSqlSession().update(NAMESPACES + "updateByCancelFrozenByBatch", dto);
	}

	/**
	 * 修改应付单的支付状态、付款单号、付款金额等信息
     * @author 099995-foss-wujiangtao
     * @date 2012-11-6 下午1:57:31
     * @param dto 应付单DTO
     * @return
	 */
	@Override
	public int updateByBatchPay(BillPayableDto dto) {
		return this.getSqlSession().update(NAMESPACES + "updateByBatchPay", dto);
	}
	
	/**
	 * 签收时修改代收货款应付单的签收日期--针对不能满足生效代收货款应付单时，调用此接口
     * 
     * @author 099995-foss-wujiangtao
     * @date 2012-11-15 下午5:12:55
     * @param entity 应付单
     * @return
	 */
	public int updateBySignDate(BillPayableEntity entity){
		return this.getSqlSession().update(NAMESPACES+"updateBySignDate",entity);
	}
	
	/**
	 * 生效应付单(装卸费)Dao接口--生效应付单
	 * @author foss-zhangxiaohui
	 * @date Nov 20, 2012 9:54:39 AM
	 * @param billPayableEntityList 应付单集合
	 * @return
	 */
	@Override
	public int updatePayableBillLaborFee(List<BillPayableEntity> billPayableEntityList) {
		//判断参数是否为空
		if (billPayableEntityList == null){
			return 0;
		}
		// 获取当前时间
		Date now = Calendar.getInstance().getTime();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("effectiveStatus", FossConstants.YES);
		map.put("modifyTime",now);
		map.put("effectiveDate", now);
		map.put("billPayableEntityList",billPayableEntityList);
		return this.getSqlSession().update(NAMESPACES + "updateBillPayableLaborFee", map);
	}

	/**
	 * 批量修改应付单的对账单单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午6:26:40
	 * @param dto 应付单DTO
	 * @return
	 */
	@Override
	public int updateBatchByMakeStatement(BillPayableDto dto) {
		return this.getSqlSession().update(NAMESPACES+"updateBatchByMakeStatement", dto);
	}
	
	/**
	 * 批量生效应付单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 上午9:18:24
	 * @param dto
	 * @return
	 */
	@Override
	public int updateByBatchTakeEffect(BillPayableDto dto) {
		return this.getSqlSession().update(NAMESPACES+"updateByBatchTakeEffect",dto);
	}

	// -------------------- read methods --------------------
	
	/**
	 * 根据运单号和应付单类型集合，获取一到多条应付单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-19 上午10:28:29
	 * @param waybillNo
	 * @param billTypes
	 * @param active
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryBillPayableByCondition(BillPayableConditionDto dto) {
		// 满足以下(运单号和来源单据类型不能为空)条件才能进入，此查询方法
		if (dto != null && (StringUtils.isNotEmpty(dto.getPayableNo())
				|| StringUtils.isNotBlank(dto.getPaymentNo())
				|| StringUtils.isNotEmpty(dto.getWaybillNo())
				|| StringUtils.isNotEmpty(dto.getSourceBillNo()))) {
			return this.getSqlSession().selectList(
					NAMESPACES + "selectByCondition", dto);
		}
		return null;
	}

	/**
	 * 根据传入的一到多个应付单号，获取一到多条应付单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-12 下午6:03:24
	 * @param payableNos
	 *            应付单号集合
	 * @param active
	 *            是否有效
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryByPayableNOs(List<String> payableNos,String active) {
		//应付单号集合为空，直接返回
		if (CollectionUtils.isEmpty(payableNos)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("payableNos", payableNos);
		if (StringUtils.isNotEmpty(active)) {
			map.put("active", active);
		}
		return this.getSqlSession().selectList(NAMESPACES + "selectByPayableNos", map);
		
	}

	/**
	 * 根据传入的一到多个来源单号，获取一到多条应付单信息
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-15 下午6:37:17
	 * @param sourceBillNos 来源单号集合
	 * @param active 是否有效
	 * @return List<BillPayableEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryBySourceBillNOs(List<String> sourceBillNos, String sourceBillType, String active) {
		//来源单号为空，直接返回
		if (CollectionUtils.isEmpty(sourceBillNos)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sourceBillNos", sourceBillNos);
		map.put("sourceBillType", sourceBillType);
		if (StringUtils.isNotEmpty(active)) {
			map.put("active", active);
		}
		return this.getSqlSession().selectList(NAMESPACES + "selectBySourceBillNos", map);
	}

	/**
	 * 根据运单号和外发单号、客户编码、判断是否已存在有效的偏线成本应付单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-19 下午4:00:48
	 * @param dto 应付单查询条件DTO
	 * @return
	 */
	@Override
	public int queryBillPayableByExternalBillNo(BillPayableConditionDto dto) {
		Object obj=this.getSqlSession().selectOne(NAMESPACES+"selectBillPayableByExternalBillNo",dto);
		if(obj!=null){
			return Integer.valueOf(obj.toString());
		}
		return 0;
	}

	/** 
	 * 根据传入的一到多个应付单号，传入的部门范围，获取一到多条应付单信息
	 * 
	 * @author dp-maojianqiang
	 * @date 2012-11-22 上午11:58:36
	 * @param payableNos    应付单号集合
	 * @param deptCodeList  应付部门集合
	 * @param active        是否有效
	 * @param currentInfo 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryByPayableNosAndDeptCodes(
			List<String> payableNos, List<String> deptCodeList, String active,CurrentInfo currentInfo,String isPartner) {
		//应付单号集合为空，直接返回
		if (CollectionUtils.isEmpty(payableNos)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("payableNos", payableNos);
		if(CollectionUtils.isNotEmpty(deptCodeList)){
			map.put("deptCodeList", deptCodeList);
		}
		if (StringUtils.isNotEmpty(active)) {
			map.put("active", active);
		}
		map.put("isPartner", isPartner);
		map.put("currentInfo", currentInfo);
		return this.getSqlSession().selectList(NAMESPACES + "queryByPayableNosAndDeptCodes", map);
	}

	/** 
	 * 根据传入的一到多个来源单号，传入的部门范围,获取一到多条应付单信息
	 * 
	 * @author dp-maojianqiang
	 * @date 2012-11-22 上午11:58:36
	 * @param sourceBillNos  来源单号集合
	 * @param orgCodes   部门编码集合
	 * @param sourceBillType 来源单据类型
	 * @param active         是否有效
	 * @param currentInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryBySourceBillNosAndOrgCodes(List<String> sourceBillNos, List<String> orgCodes,String sourceBillType, String active,CurrentInfo currentInfo,String isPartner) {
		//来源单号结合为空，直接返回
		if (CollectionUtils.isEmpty(sourceBillNos)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if(CollectionUtils.isNotEmpty(orgCodes)){
			map.put("orgCodes", orgCodes);
		}
		map.put("sourceBillNos", sourceBillNos);
		map.put("sourceBillType", sourceBillType);
		if (StringUtils.isNotEmpty(active)) {
			map.put("active", active);
		}
		map.put("isPartner", isPartner);
		map.put("currentInfo", currentInfo);
		return this.getSqlSession().selectList(NAMESPACES + "selectBySourceBillNosAndOrgCodes", map);
	}

	/**
	 * 根据运单号集合和单据类型集合查询应付单信息
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-22 下午5:09:35
	 * @param waybillNos 运单号集合
	 * @param billTypes  单据类型集合
	 * @param active     是否有效
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryByWaybillNosAndByBillTypes(List<String> waybillNos, List<String> billTypes, String active) {
		//运单号集合为空，直接返回
		if(CollectionUtils.isEmpty(waybillNos)){
			return null;
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("waybillNos", waybillNos);
		map.put("billTypes", billTypes);
		map.put("active", active);
		return this.getSqlSession().selectList(NAMESPACES+"selectByWaybillNosAndByBillTypes", map);
	}
	
	/**
	 * 根据运单号集合和单据类型集合以及来源单号查询应付单信息
     * @author 367638-foss-caodajun
	 * @date 2016-12-13 下午3:59:32
     * @param waybillNos 运单号集合
     * @param billTypes  单据类型集合
     * @param sourceBillNo 来源单号集合
     * @param active     是否有效
     * @return	
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryBysourceBillNoAndByBillTypes(List<String> waybillNos, List<String> billTypes, List<String>sourceBillNo,String active) {
		//运单号集合为空，直接返回
		if(CollectionUtils.isEmpty(waybillNos)){
			return null;
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("waybillNos", waybillNos);
		map.put("billTypes", billTypes);
		map.put("sourceBillNo", sourceBillNo);
		map.put("active", active);
		return this.getSqlSession().selectList(NAMESPACES+"selectqueryBysourceBillNoAndByBillTypes", map);
	}
	
	/**
	 * 根据运单号集合和单据类型集合以及到达部门集合查询应付单信息
	 * @author 367638-foss-caodajun
	 * @date 2016-10-24 下午5:15:35
	 * @param waybillNos 运单号集合
	 * @param billTypes  单据类型集合
	 * @param active     是否有效
	 * @param destOrgCode 到达部门编码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryByWaybillNosAndByBillDTypes(List<String> waybillNos, List<String> billTypes, String active,List<String> destOrgCode) {
		//运单号集合为空，直接返回
		if(CollectionUtils.isEmpty(waybillNos)){
			return null;
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("waybillNos", waybillNos);
		map.put("billTypes", billTypes);
		map.put("active", active);
		map.put("destOrgCode", destOrgCode);
		return this.getSqlSession().selectList(NAMESPACES+"selectByWaybillNosAndByBillDTypes", map);
	}
	
	
	
	/**
	 * 根据来源单号查询应付单是否已经核销
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-28 下午6:28:04
	 * @param sourceBillNo    来源单号 
	 * @param sourceBillType  来源单据类型
	 * @param active          是否有效
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryBillPayableIsWriteOff(String sourceBillNo,String sourceBillType, String active) {
		//来源单号集合为空，直接返回
		if(StringUtils.isEmpty(sourceBillNo)){
			return null;
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("sourceBillNo", sourceBillNo);
		map.put("sourceBillType", sourceBillType);
		map.put("active", active);
		return this.getSqlSession().selectList(NAMESPACES+"selectBillPayableIsWriteOff",map);
	}

	

	/**
	 * 按照运单号和应付部门编码集合查询应付单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-28 下午3:24:26
	 * @param waybillNos
	 * @param orgCodeList
	 * @param active
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryByWaybillNosAndOrgCodes(List<String> waybillNos, List<String> orgCodeList, String active,CurrentInfo currentInfo,String isPartner) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNos", waybillNos);
		map.put("orgCodeList", orgCodeList);
		map.put("active", active);
		map.put("isPartner", isPartner);
		map.put("currentInfo", currentInfo);
		return this.getSqlSession().selectList(NAMESPACES+"selectByWaybillNosAndOrgCodes",map);
	}

	/**
	 * 根据付款单号集合查询应付单
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-14 下午3:25:06
	 * @param paymentNos
	 * @param active
	 * @param isRedBack
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BillPayableEntity> queryByPaymentNos(List<String> paymentNos,String active,String isRedBack){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("paymentNos", paymentNos);
		map.put("isRedBack", isRedBack);
		map.put("active", active);
		return this.getSqlSession().selectList(NAMESPACES+"selectByPaymentNos",map);
	}

	/**
	 * 根据运单号集合和单据类型，查询已签收且未生效应付单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-18 上午9:42:43
	 * @param waybillNos
	 * @param billType
	 * @param effectiveStatus
	 * @param productCode 产品类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryByWaybillNosAndBillType(List<String> waybillNos, String billType,String effectiveStatus,String productCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNos", waybillNos);
		map.put("active", FossConstants.ACTIVE);
		//生效状态为:未生效
		map.put("effectiveStatus", effectiveStatus);
		//运输性质--空运
		map.put("productCode", productCode);
		return this.getSqlSession().selectList(NAMESPACES+"selectByWaybillNosAndBillType",map);
	}
	
	
	/**
	 * 根据运单号集合和单据类型，查询已签收且未生效应付单信息
	 * @author 218392 zhangyongxue
	 * @date 2015-12-31 18:55:30
	 * @param effectiveStatus
	 * @param productCode 产品类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryByWaybillNosByBillType(List<String> waybillNos, String billType,String effectiveStatus,String productCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNos", waybillNos);
		map.put("active", FossConstants.ACTIVE);
		//生效状态为:未生效
		map.put("effectiveStatus", effectiveStatus);
		//单据子类型
		map.put("billType", billType);// @218392 
		//运输性质--空运
		map.put("productCode", productCode);
		return this.getSqlSession().selectList(NAMESPACES+"selectByWaybillNosAndBillType",map);
	}
	

	/**
	 * 根据来源单号集合和客户（代理）编码，查询应付单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-21 下午2:56:33
	 * @param sourceBillNos
	 * @param customerCode
	 * @param sourceBillType
	 * @param active
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryBySourceBillNOsAndCustomerCode(List<String> sourceBillNos, String customerCode,String sourceBillType, String active) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sourceBillNos", sourceBillNos);
		map.put("sourceBillType", sourceBillType);
		map.put("active", active);
		map.put("customerCode", customerCode);
		return this.getSqlSession().selectList(NAMESPACES+"selectBySourceBillNOsAndCustomerCode",map);
	}

	/**
	 * 根据运单号、客户编码、单据类型批量查询应付单  供空运变更清单服务调用
	 * @author 045738-foss-maojianqiang
	 * @date 2013-4-12 上午9:40:30
	 * @param list
	 * @param active
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IBillPayableEntityDao#selectByWayBillNOsAndCustomerCodeAndBillType(java.util.List, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<BillPayableEntity> selectBySourceBillNOsAndCustomerCodeAndBillType(List<BillPayableEntity> list, String active) {
		//判空
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("active", active);
		return this.getSqlSession().selectList(NAMESPACES+"selectBySourceBillNOsAndCustomerCodeAndBillType",map);

	}
	
	 /**
	  * 根据来源单号，来源单据类型查询对应的应付单 供空运中转提货调用
	  * 	查询中转提货清单运单对应的送货费应付单集合
	  * @author 092036-foss-bochenlong
	  * @date 2013-8-20 下午14:50:55
	  * @param sourceBillNos 来源单号集合 
	  * @param sourceBillTypes 来源单据类型集合
	  * @param active 是否有效
	  * @return List<BillPayableEntity>
	  */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> selectBySourceBillNosTypes(List<String> sourceBillNos, List<String> sourceBillTypes,String active) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sourceBillNos", sourceBillNos);
		map.put("sourceBillTypes", sourceBillTypes);
		map.put("active", active);
		return this.getSqlSession().selectList(NAMESPACES+"selectBySourceBillNosTypes",map);
	}
	/**
	 * 
	 * 功能：给临时租车使用，更新应付单上的工作流号
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-24
	 * @return
	 */
	public int updateWorkFlowNoByPayNo(List<BillPayableEntity> list, String workFolwNo,CurrentInfo cInfo){
		//判断参数是否为空
		if (list == null){
			return 0;
		}
		// 获取当前时间
		Date now = Calendar.getInstance().getTime();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modifyTime",now);
		map.put("modifyUserCode", cInfo.getEmpCode());
		map.put("modifyUserName", cInfo.getEmpName());
		map.put("workFolwNo", workFolwNo);
		map.put("list",list);
		return  this.getSqlSession().update(NAMESPACES+"updateWorkFlowNoByPayNo", map);
	}
	
	/**
	 * 功能：给临时租车使用，查询应付单数据，顺带关联查询出临时租车预提数据
	 * @author 045738-foss-maojianqiang
	 * @date 2014-8-05
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryByPayableNOsForRentCar(List<String> payableNos) {
		//应付单号集合为空，直接返回
		if (CollectionUtils.isEmpty(payableNos)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("payableNos", payableNos);
		return this.getSqlSession().selectList(NAMESPACES + "selectByPayableNosForRentCar", map);
		
	}
	
	/**
	 * 功能：给临时租车使用，查询应付单数据，顺带关联查询出临时租车预提数据
	 * @author 045738-foss-maojianqiang
	 * @date 2014-8-05
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryByPaymentNosForRentCar(List<String> paymentNos) {
		//应付单号集合为空，直接返回
		if (CollectionUtils.isEmpty(paymentNos)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("paymentNos", paymentNos);
		return this.getSqlSession().selectList(NAMESPACES + "selectByPaymentNosForRentCar", map);
		
	}
	
	/**
   	 * 功能：当更新报账预提接口不成功时，版本号-1
   	 * @author 045738-foss-maojianqiang
   	 * @date 2014-11-4
   	 * @return
   	*/
    public int updatePayableVersion(List<BillPayableEntity> list, CurrentInfo cInfo){
    	//判断参数是否为空
		if (list == null){
			return 0;
		}
		// 获取当前时间
		Date now = Calendar.getInstance().getTime();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modifyTime",now);
		map.put("modifyUserCode", cInfo.getEmpCode());
		map.put("modifyUserName", cInfo.getEmpName());
		map.put("list",list);
		return  this.getSqlSession().update(NAMESPACES+"updatePayableVersion", map);
    }

	@Override
	public String queryDiscountPayable(String waybillNo) {
		String statues = (String)this.getSqlSession().selectOne(NAMESPACES+"selectDiscountPayable", waybillNo);
		return statues;
	}

	/**
	 *  根据付款单号查询应付单
	 * @author foss-269044
	 * @date 2015-10-26
	 * @param List<BillPayableEntity> payableList
	 * @return List<BillPayableEntity> payableList
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryPaymentListByPaymentNo(List<BillPayableEntity> payableList) {
		//判空
		if(CollectionUtils.isEmpty(payableList)){
			return null;
		}
		List<BillPayableEntity> payableBillList = this.getSqlSession().selectList(NAMESPACES + "queryPayableListByPaymentNo" , payableList);
        return payableBillList;
	}

    public int updateBillPayableEffective(BillPayableEntity entity) {
        return (Integer)this.getSqlSession().update(NAMESPACES + "updateByTakeEffect", entity);
    }
    
    /**
     * 根据运单号集合查询应付单信息
     * @author foss-hemingyu
     * @date 2012-11-22 下午5:09:35
     * @param waybillNos 运单号集合
     * @return List<BillPayableEntity>
     */
    @SuppressWarnings("unchecked")
    public List<BillPayableEntity> queryByWaybillNo(String waybillNos) {
        //运单号集合为空，直接返回
        if(StringUtils.isEmpty(waybillNos)){
            return null;
        }
        return this.getSqlSession().selectList(NAMESPACES+"selectByWaybillNos", waybillNos);
    }
   
    /**
     * @author 331556 fanjingwei
     * @date 2016-05-28
     * @param waybillNos
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<BillPayableEntity> queryByWaybillNos(String waybillNos) {
        //运单号集合为空，直接返回
        if(StringUtils.isEmpty(waybillNos)){
            return null;
        }
        return this.getSqlSession().selectList(NAMESPACES+"queryByWaybillNos", waybillNos);
    }
    
    /**
     * 根据运单号查询第一条代收退款应付单金额
     * @author 326181
     * @param waybillNo 运单号
     * @return amount
     */
    public BigDecimal queryFirstPayableAmountByWaybillNo(String waybillNo){
    	return (BigDecimal) this.getSqlSession().selectOne(NAMESPACES+"queryFirstPayableAmountByWaybillNo", waybillNo);
    }
    
    /**
     * 长期未支付有效应付单自动限制付款
     * @author 340778-foss-zf
     * @date 2016-7-22 下午2:59:35
     * @description
     */
    public int updateBillPayableAutoLimit(){
    	return this.getSqlSession().update(NAMESPACES+"updateBillPayableAutoLimit");
    }
    
    /**
     * 长期未支付有效应付单自动限制付款解除
     * @author 340778-foss-zf
     * @date 2016-7-22 下午3:02:01
     * @description
     * @param payableNo 应付单号
     * @param currentInfo 当前用户信息
     */
    public int updateBillPayableAutoLimitRestore(Map<String, Object> map){
    	
    	return this.getSqlSession().update(NAMESPACES+"updateBillPayableAutoLimitRestore",map);
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> checkPayableBillRepeated(
			BillPayableEntity queryEntity) {
		return this.getSqlSession().selectList(
				NAMESPACES + "checkPayableBillRepeated", queryEntity);
	}
}
