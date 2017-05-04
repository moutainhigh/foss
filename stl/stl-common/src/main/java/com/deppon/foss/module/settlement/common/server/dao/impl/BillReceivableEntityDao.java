package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillReceivableEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableOnlineQueryDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffAmountDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.WaybillReceivableDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 操作应收单实体DAO层
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-10-10 下午3:40:32
 * @since
 * @version
 */
public class BillReceivableEntityDao extends iBatis3DaoImpl implements IBillReceivableEntityDao {

	/**
	 *  命名空间路径
	 */
	private static final String NAMESPACES = "foss.stl.BillReceivableEntityDao.";

	/**
	 * 
	 * 保存应收单记录
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-10 下午3:26:32
	 * @param entity 应收单
	 * @return
	 */
	@Override
	public int add(BillReceivableEntity entity) {
		return this.getSqlSession().insert(NAMESPACES + "insert", entity);
	}

	/**
	 * 根据ID与分区键查询应收单
     * @author ibm-zhuwei
     * @date 2012-11-29 上午11:34:42
     * @param dto 应收单DTO
     * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryPartitionsByIds(BillReceivableDto dto) {
		return this.getSqlSession().selectList(NAMESPACES + "selectPartitionsByIds", dto);
	}

	/**
	 * 根据应收单号与分区键查询应收单
     * @author ibm-zhuwei
     * @date 2012-11-29 上午11:34:42
     * @param dto 应收单DTO
     * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryPartitionsByReceivableNos(BillReceivableDto dto) {
		return this.getSqlSession().selectList(NAMESPACES + "selectPartitionsByReceivableNos", dto);
	}

	/**
	 * 根据传入的一到多个应收单号，获取一到多条应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-12 下午6:38:48
	 * @param receivableNos
	 *            //应收单号集合
	 * @param  active 是否有效
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryByReceivableNOs(List<String> receivableNos, String active) {
		if (CollectionUtils.isEmpty(receivableNos)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receivableNos", receivableNos);
		if (StringUtils.isNotEmpty(active)) {
			map.put("active", active);
		}
		return this.getSqlSession().selectList(NAMESPACES + "selectByReceivableNos", map);
		
	}

	/**
	 * 
	 * 根据传入的一到多个来源单号，获取一到多条应收单信息
     * @author 099995-foss-wujiangtao
     * @date 2012-10-15 上午11:52:05
     * @param sourceBillNos  来源单号集合
     * @param sourceBillType 来源单据类型
     * @param active         是否有效
     * @return
     * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryBySourceBillNOs(List<String> sourceBillNos, String sourceBillType, String active) {
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
	 * 根据传入的运单号和单据类型等参数，查询[到付运费/始发/代收货款]有效应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-15 下午7:42:29
	 * @param dto
	 *            [查询未核销时，请传入dto的verify不为空即可]
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryBillReceivableByCondition(BillReceivableConditionDto dto) {
		// 满足以下(运单号和来源单据类型不能为空)条件才能进入，此查询方法
		if (dto != null && (StringUtils.isNotEmpty(dto.getReceivableNo()) || StringUtils.isNotEmpty(dto.getWaybillNo()) 
						|| StringUtils.isNotEmpty(dto.getSourceBillNo()))) {
			return this.getSqlSession().selectList(NAMESPACES + "selectByCondition", dto);
		}
		return null;
	}
	
	/**
	 * 查询始发月结
	 * ECS-327090
	 * @date 2016-5-24 
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryBillReceivableMonthlyStatement(
			BillReceivableConditionDto dto) {
		if (dto != null && StringUtils.isNotEmpty(dto.getWaybillNo())) {
			return this.getSqlSession().selectList(NAMESPACES + "selectMonthlyStatement", dto);
		}
		return null;
	}

	/**
	 * 
	 * 根据传入应收单红冲原应收单信息
     * @author 099995-foss-wujiangtao
     * @date 2012-10-15 下午7:33:16
     * @param entity   应收单实体信息
     * @return
     * @see
	 */
	@Override
	public int updateBillReceivableByWriteBack(BillReceivableEntity entity) {
		return this.getSqlSession().update(NAMESPACES + "updateByWriteBack", entity);
	}

	/**
	 * 应收单核销
     * @author ibm-zhuwei
     * @date 2012-10-19 上午11:03:14
     * @param dto 核销DTO
     * @return
	 */
	@Override
	public int updateBillReceivableByWriteoff(BillWriteoffAmountDto dto) {
		return this.getSqlSession().update(NAMESPACES + "updateByWriteoff", dto);
	}

	/**
	 * (反确认)签收时 确认收入日期
     * @author 099995-foss-wujiangtao
     * @date 2012-10-22 上午9:50:33
     * @param entity 应收单
     * @return
     * @see
	 */
	@Override
	public int updateBillReceivableByConfirmIncome(BillReceivableEntity entity) {
		return this.getSqlSession().update(NAMESPACES + "updateByConfirmIncome", entity);
	}

	/**
	 * 批量审核/反审核空运其他应收单
     * @author 099995-foss-wujiangtao
     * @date 2012-10-30 下午7:18:51
     * @param dto 应收单DTO
     * @return
     * @see
	 */
	@Override
	public int updateBillReceivableByAirAudit(BillReceivableDto dto) {
		
		return this.getSqlSession().update(
				NAMESPACES + "updateBillReceivableByAirAudit", dto);
	}

	/**
	 * 根据运单号查询客户的应收单到付金额和应收代收货款金额
     * @author 099995-foss-wujiangtao
     * @date 2012-11-2 上午8:13:55
     * @param dto 应收单条件DTO
     * @see
	 */
	@SuppressWarnings("unchecked")
	public List<BillReceivableEntity> queryReceivableAmountByCondition(BillReceivableConditionDto dto) {
		
		return this.getSqlSession().selectList(NAMESPACES + "selectReceivableAmountByCondition", dto);
	}

	/**
	 * 锁定/解锁应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-7 上午10:23:51
	 * @param dto
	 * @return
	 */
	@Override
	public int updateBillReceivableByLock(BillReceivableDto dto) {
		return this.getSqlSession().update(NAMESPACES + "updateBillReceivableByLock", dto);
	}

	/**
	 * 根据运单号和外发单号、客户编码、判断是否已存在有效的偏线到达应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-19 下午3:19:00
	 * @param dto
	 * @return
	 */
	public int queryReceivableByExternalBillNo(BillReceivableConditionDto dto) {
		Object obj = this.getSqlSession().selectOne(NAMESPACES + "selectReceivableByExternalBillNo", dto);
		if (obj != null) {
			return Integer.valueOf(obj.toString());
		}
		return 0;
	}

	/**
	 * 批量修改应收单的对账单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午6:33:11
	 * @param dto
	 * @return
	 */
	@Override
	public int updateBatchByMakeStatement(BillReceivableDto dto) {
		return this.getSqlSession().update(NAMESPACES + "updateBatchByMakeStatement", dto);
	}

	/**
	 * 按照运单号和应付部门编码集合查询应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-28 下午3:27:19
	 * @param waybillNos
	 * @param orgCodeList
	 * @param active
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryByWaybillNosAndOrgCodes(
			List<String> waybillNos, List<String> orgCodeList, String active,CurrentInfo currentInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNos", waybillNos);
		map.put("orgCodeList", orgCodeList);
		if(StringUtils.isNotEmpty(active)){
			map.put("active", active);
		}
		map.put("currentInfo", currentInfo);//操作者信息
		return this.getSqlSession().selectList(NAMESPACES+"selectByWaybillNosAndOrgCodes",map);
	}

	/**
	 * 根据运单号，查询开单付款方式为网上支付的应收单的未核销金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-15 下午5:26:08
	 * @param waybillNo
	 * @return
	 */
	@Override
	public WaybillReceivableDto queryReceivableAmountByWaybillNO(String waybillNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNo", waybillNo);
		map.put("active", FossConstants.ACTIVE);//有效
		map.put("isRedBack", SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);//非红的
		map.put("billType", SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);//根据运单号，查询开单为网上支付的应收单的未核销金额
		map.put("paymentType", SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE);//付款方式：网上支付
		List<WaybillReceivableDto> list=this.getSqlSession().selectList(NAMESPACES+"selectReceivableAmountByWaybillNo",map);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据运单号集合或来源单号结合，单据类型集合查询应收单信息
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-16 下午3:05:39
	 * @param waybillNos
	 * @param sourceBillNos
	 * @param sourceBillType
	 * @param billTypes
	 * @param active
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryByWaybillNosOrSourceBillNosAndBillTypes(List<String> waybillNos, List<String> sourceBillNos,String sourceBillType,List<String> billTypes,String active,String isRedBack) {
		Map<String,Object> map=new HashMap<String,Object>();
		//运单号集合
		map.put("waybillNos", waybillNos);
		//来源单号集合
		map.put("sourceBillNos", sourceBillNos);
		//来源单据类型
		map.put("sourceBillType", sourceBillType);
		//单据类型
		map.put("billTypes", billTypes);
		//是否有效
		map.put("active", active);
		//是否红单
		map.put("isRedBack",isRedBack );
		return this.getSqlSession().selectList(NAMESPACES+"selectByWaybillNosOrSourceBillNosAndTypes",map);
	}

	/**
	 * 根据客户编码，获取客户的应收未核销金额 
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-16 下午8:36:56
	 * @param customerCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryReceivableAmountByCustomerCode(String customerCode) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("customerCode", customerCode);
		//是否有效
		map.put("active", FossConstants.ACTIVE);
		//是否红单
		map.put("isRedBack",SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO );
		return this.getSqlSession().selectList(NAMESPACES+"selectReceivableAmountByCustomerCode",map);
	}

	/**
	 * 根据来源单号集合和应收部门编码集合，查询应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-22 下午4:42:04
	 * @param sourceBillNos
	 * @param orgCodes
	 * @param sourceBillType
	 * @param active
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryBySourceBillNOsAndOrgCodes(List<String> sourceBillNos, List<String> orgCodes,String sourceBillType, String active,CurrentInfo currentInfo) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("sourceBillNos", sourceBillNos);
		map.put("orgCodes", orgCodes);
		map.put("sourceBillType", sourceBillType);
		map.put("active", active);
		//操作者信息
		map.put("currentInfo", currentInfo);
		return this.getSqlSession().selectList(NAMESPACES + "selectBySourceBillNOsAndOrgCodes", map);
	}

	/**
	 * 锁定应收单信息
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-23 上午9:05:22
	 */
	@Override
	public int updateReceiveBillInfoForLock(BillReceivableOnlineQueryDto queryDto) {
		return this.getSqlSession().update(NAMESPACES+"updateByUnlockDate", queryDto);
	}
	
	/**
	 * 根据应收单号集合批量标记和反标记状态
	 * @author 045738-foss-maojianqiang
	 * @date 2013-5-21 下午6:25:17
	 * @param numbers
	 * @param stampFlag
	 * @param active
	 * @return
	 */
	@Override
	public int updateStampByNumbers(BillReceivableDto dto) {				
		return this.getSqlSession().update(NAMESPACES + "updateStampByNumbers", dto);
	}

	/**
	 * 根据应收单号和数据权限对应的部门查询应收单
	 * @author 045738-foss-maojianqiang
	 * @date 2013-6-12 下午6:33:19
	 * @param waybillNos
	 * @param active
	 * @param currentInfo
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IBillReceivableEntityDao#queryByReceivableNosAndOrgCodes(java.util.List, java.lang.String, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@SuppressWarnings("unchecked")
	public List<BillReceivableEntity> queryByReceivableNosAndOrgCodes(
			List<String> receivableNos, String active, CurrentInfo currentInfo) {
		Map<String,Object> map=new HashMap<String,Object>();
		//应收单号
		map.put("receivableNos", receivableNos);
		//是否有效
		map.put("active", active);
		//操作者信息
		map.put("currentInfo", currentInfo);
		return this.getSqlSession().selectList(NAMESPACES+"selectByReceivableNosAndOrgCodes",map);
	}
    
	/**
	 * 根据条件查询最早时间
	 * @author lianghaisheng
	 * @date 2014-05-08
	 * @param dto 查询条件
	 */
	@Override
	public Date queryMinTimebyCondition(BillReceivableConditionDto dto) {
		return (Date) this.getSqlSession().selectOne(NAMESPACES+"selectMinTimebyCondition",dto);
	}

	/**
	 * 根据传入的一到多个运单单号，获取一到多条应收单信息
     * @author 邓大伟
     * @date 2014-12-12
     * @param wayBillNos  运单单号集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryByWaybillNOs(List<String> waybillNos) {
		if (CollectionUtils.isEmpty(waybillNos)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNos", waybillNos);
		return this.getSqlSession().selectList(NAMESPACES + "selectByWayBillNos", map);
	}

	/**
	 * 是否开单付款方式为到付(其他)且已在网上支付(其他)的运单
	 * @author 239284-foss-xiedejie
	 * @date 2015-4-2 上午9:30:00
	 * @param   billTypes 单据类型集合 <br/>
	 *  wayBillNo 运单号  <br/>
	 *  rePaymentNo 还款单号  <br/>
	 *  sourceNo   来源单号 
	 *  actualPayType  实际付款方式 
	 */
	@Override
	public int queryIsOrPayByBillNo(String[] billTypes, String wayBillNo,String rePaymentNo, String sourceNo,  String actualPayType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("billTypes", billTypes);
		map.put("wayBillNo", wayBillNo);
		map.put("rePaymentNo", rePaymentNo);
		map.put("sourceNo", sourceNo);
		map.put("actualPayType", actualPayType);
		return (Integer)this.getSqlSession().selectOne(NAMESPACES + "selectIsOrPayByBillNo", map);
	}

    /**
     * 修改应收单扣款状态:扣款失败,扣款成功
     *@author 099995-foss-hemingyu
     * @date 2016-01-08 上午11:23:05
     * @param entity 应收单
     * @return
     * @see
     */
    @Override
    public int updateBillReceivableWithholdStatus(BillReceivableEntity entity) {
        return (Integer)this.getSqlSession().update(NAMESPACES + "updateBillReceivableWithholdStatus", entity);
    }

    /*
    *@author 099995-foss-hemingyu
     * @date 2016-01-14 上午15:47:38
     * @param wayBillNo
     *            运单号
     * @param billType
     *            应收类型
     */
    public List<BillReceivableEntity> selectByWayBillNoAndBillType(String wayBillNo,String billType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("billType", billType);
        map.put("wayBillNo", wayBillNo);
        map.put("active","Y");
        return this.getSqlSession().selectList(NAMESPACES + "selectByWayBillNoAndBillType", map);
    }


	/**
	 * 根据应收单号更新对账单单号
	 * @author 尤坤
	 * @param map
	 * @return
	 */
	public int updateByReceivableNo(Map<String, Object> map) {
		 return (Integer)this.getSqlSession().update(NAMESPACES + "updateByReceivableNo", map);
	}

	/**
	 * @author ddw
	 * 根据对账单单号查询应收单
	 * @param statementBillNoList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryReceivableByStatementBillNo(List<String> statementBillNoList) {
		return this.getSqlSession().selectList(NAMESPACES + "queryReceivableByStatementBillNo", statementBillNoList);
	}
	
	/**
     * @author zlp 根据对账单单号查询应收单
     * @param statementBillNoList
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BillReceivableEntity> queryReceivableBySBNO(List<String> statementBillNoList) {
        return this.getSqlSession().selectList(NAMESPACES + "queryReceivableBySBNO", statementBillNoList);
    }

	/**
	 * @author ddw
	 * 根据应收单号查询应收单
	 * @param receivableNo
	 * @return
	 */
	@Override
	public BillReceivableEntity queryBillReceivableByReceivableNO(String receivableNo, String active) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("receivableNo", receivableNo);
		map.put("active", active);
		return (BillReceivableEntity) this.getSqlSession().selectOne(NAMESPACES + "queryBillReceivableByReceivableNO", map);
	}
	
	/**
	 * @author 367752
	 * 根据应收单号查询应收单
	 * @param statementBillNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryReceivableByStatementBillNoAuto(String statementBillNo){
		return this.getSqlSession().selectList(NAMESPACES + "queryReceivableByStatementBillNoAuto", statementBillNo);
	}

	/* 
	 * 根据查询条件校验合伙人应收单是否重复
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IBillReceivableEntityDao#checkReceivableBillRepeated(com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> checkReceivableBillRepeated(
			BillReceivableEntity queryEntity) {
		return this.getSqlSession().selectList(NAMESPACES + "checkReceivableBillRepeated", queryEntity);
	}

	/**
	 * 根据传入运单号查询其所有应收单已核销金额（如有多个应收单就取多个的和）
	 *@author 379106
	 * @param waybills
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<BillReceivableEntity> queryReceivableVeryfyAmountsByWaybill(List<String> waybills) {
		return this.getSqlSession().selectList(NAMESPACES + "queryReceivableVeryfyAmountsByWaybill", waybills);
	}
}
