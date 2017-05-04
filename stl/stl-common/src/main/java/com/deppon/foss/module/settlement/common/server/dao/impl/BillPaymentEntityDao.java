package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillPaymentEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPaymentDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPmtAutoPayDto;
/**
 * 
 * 付款单dao
 * @author ibm-zhuwei
 * @date 2012-10-13 下午5:18:09
 * @version
 */
public class BillPaymentEntityDao extends iBatis3DaoImpl implements IBillPaymentEntityDao {

	private static final String NAMESPACES = "foss.stl.BillPaymentEntityDao.";

	/**
	 * 新增付款单
     * @author ibm-zhuwei
     * @date 2012-10-24 上午11:32:28
     * @param entity 付款单
     * @return
	 */
	@Override
	public int add(BillPaymentEntity entity) {
		return this.getSqlSession().insert(NAMESPACES + "insert", entity);
	}
	
	
	/**
	 * 红冲付款单，原记录置为失效状态
     * @author ibm-zhuwei
     * @date 2012-10-23 下午3:35:31
     * @param entity 付款单
     * @return
	 */
	@Override
	public int updateByWriteBack(BillPaymentEntity entity) {
		return this.getSqlSession().update(NAMESPACES + "updateByWriteBack", entity);
	}

	/**
	 * 批量审核/反审核付款单
     * @author 099995-foss-wujiangtao
     * @date 2012-11-26 上午10:29:17
     * @param dto 付款单DTO
     * @return
	 */
	@Override
	public int updateBillPaymentByBatchAudit(BillPaymentDto dto) {
		return this.getSqlSession().update(
				NAMESPACES + "updateBillPaymentByBatchAudit",dto);
	}
	
	/**
	 * 批量更新工作流号
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 上午8:40:48
	 * @param dto 付款单DTO
     * @return
	 */
	@Override
	public int updateBillPaymentByBatchWorkflow(BillPaymentDto dto) {
		return this.getSqlSession().update(NAMESPACES + "updateBillPaymentByBatchWorkflow",dto);
	}

	/**
	 * 批量更备用金工作流号
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 下午4:43:04
	 * @param dto 付款单DTO
     * @return
	 */
	@Override
	public int updateBillPaymentByBatchApplyWorkflow(BillPaymentDto dto) {
		return this.getSqlSession().update(NAMESPACES + "updateBillPaymentByBatchApplyWorkflow", dto);
	}

	/** 
	 * 更新付款单的退款状态
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 下午5:10:49
	 * @param dto 付款单DTO
     * @return
	 */
	@Override
	public int batchReverseRemitStatusBillPayment(BillPaymentDto dto) {
		return this.getSqlSession().update(NAMESPACES + "batchReverseRemitStatusBillPayment", dto);
	}
	
	/** 
	 * 根据付款单号集合，批量更新付款状态
	 * @author 302346-foss-Jaing Xun
	 * @date 2016-06-02 上午11:39:00
	 * @param dto 付款单DTO
     * @return
	 */
	@Override
	public int updateRemitStatusByPmtNos(BillPmtAutoPayDto dto) {
		return this.getSqlSession().update(NAMESPACES + "updateRemitStatusByPmtNos", dto);
	}
	
	/** 
	 * 根据汇款状态、付款类型，查询应付单
	 * @author 302346-foss-Jaing Xun
	 * @date 2016-06-004 上午10:47:00
	 * @param entity 付款单ENTITY
     * @return 付款单集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentEntity> queryBillPaymentByPmtType(BillPaymentEntity entity){
		return this.getSqlSession().selectList(NAMESPACES + "queryBillPaymentByPmtType", entity);
	}

	/**
	 *  根据一到多个付款单号，获取一到多条付款单记录
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-25 上午10:24:13
	 * @param paymentNos
	 * @param active
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentEntity> queryBillPaymentByPaymentNOs(List<String> paymentNos, String active) {
		List<BillPaymentEntity> list=new ArrayList<BillPaymentEntity>();
		//付款单号不为空，进行查询
		if(CollectionUtils.isNotEmpty(paymentNos)){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("paymentNos", paymentNos);
			if(StringUtils.isNotEmpty(active)){
				map.put("active", active);
			}
			list=this.getSqlSession().selectList(NAMESPACES +"selectBillPaymentByPaymentNos",map);
		}
		return list;
	}

	/** 
	 * 根据一到多个付款单id，获取一到多条付款单记录
     * @author 045738-foss-maojianqiang
     * @date 2012-12-1 上午9:01:51
     * @param ids    ID集合
     * @param active 是否有效
     * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentEntity> queryBillPaymentByPaymentIds(List<String> ids, String active) {
		List<BillPaymentEntity> list=new ArrayList<BillPaymentEntity>();
		//付款ID集合不为空，进行查询操作
		if(CollectionUtils.isNotEmpty(ids)){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("ids", ids);
			if(StringUtils.isNotEmpty(active)){
				map.put("active", active);
			}
			list=this.getSqlSession().selectList(NAMESPACES +"selectBillPaymentByPaymentIds",map);
		}
		return list;
	}
	
	/**
	 * 根据一到多个来源单号，获取一到多条付款单记录
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-25 上午10:24:30
	 * @param sourceBillNos
	 * @param active
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentEntity> queryBillPaymentBySourceBillNOs(List<String> sourceBillNos,String sourceBillType, String active) {
		List<BillPaymentEntity> list=new ArrayList<BillPaymentEntity>();
		//来源单号不为空，进行下面的查询
		if(CollectionUtils.isNotEmpty(sourceBillNos)){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("sourceBillNos", sourceBillNos);
			map.put("sourceBillType", sourceBillType);
			if(StringUtils.isNotEmpty(active)){
				map.put("active", active);
			}
			list=this.getSqlSession().selectList(NAMESPACES +"selectBillPaymentBySourceBillNos",map);
		}
		return list;
	}

	/**
	 * 根据应付单号，或来源单号、单据类型等查询条件查询付款单信息
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-25 上午10:34:01
	 * @param dto
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentEntity> queryBillPaymentByCondition(BillPaymentConditionDto dto) {
		List<BillPaymentEntity> list=new ArrayList<BillPaymentEntity>();
		//必须满足一种财务单据号码不为空，才进行下面的查询
		if(dto!=null && (StringUtils.isNotEmpty(dto.getPaymentNo())||StringUtils.isNotEmpty(dto.getSourceBillNo()))){
			list=this.getSqlSession().selectList(NAMESPACES + "queryBillPaymentByCondition", dto);
		}
		return list;
	}
	
	/**
	 * 根据一到多个付款单号，获取一到多条付款单的汇款状态
     * @author 099995-foss-wujiangtao
     * @date 2012-10-25 上午9:27:02
     * @param paymentNos 付款单号集合
     * @param active     是否有效
     * @return
	 */
	 @SuppressWarnings("unchecked")
	public List<BillPaymentEntity> queryPaymentRemitStatusByPaymentNOs(List<String> paymentNos,String active){
		 List<BillPaymentEntity> list=new ArrayList<BillPaymentEntity>();
		 //付款单号集合不为空，进行下面的查询
		 if(CollectionUtils.isNotEmpty(paymentNos)){
			 Map<String,Object> map=new HashMap<String,Object>();
			 map.put("paymentNos", paymentNos);
			 if (StringUtils.isNotEmpty(active)) {
				 map.put("active", active);
			 }
			 list=this.getSqlSession().selectList(NAMESPACES +"selectPaymentRemitStatusByPaymentNos",map);
		 }
		return list;	
	}

	/**
	 * 根据外发单号和外发代理编码是否已存在有效的付款单信息
     * 
     * @author 099995-foss-wujiangtao
     * @date 2012-11-19 下午7:05:15
     * @param dto 付款单查询条件DTO
     * @return
	 */
	@Override
	public int queryPaymentByExternalBillNo(BillPaymentConditionDto dto) {
		Object obj=this.getSqlSession().selectOne(NAMESPACES+"selectPaymentByExternalBillNo",dto);
		if(obj!=null){
			return Integer.valueOf(obj.toString());
		}
		return 0;
	}


	/** 
	 * 根据批次号（即付款编号）来查询付款单
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-14 下午5:11:31
	 * @param payNos
	 * @param active
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IBillPaymentEntityDao#queryPaymentByBatchNos(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentEntity> queryPaymentByBatchNos(String payNos,String active) {
		List<BillPaymentEntity> list=new ArrayList<BillPaymentEntity>();
		if(StringUtils.isNotBlank(payNos)){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("payNos", payNos);
			if(StringUtils.isNotEmpty(active)){
				map.put("active", active);
			}
			list=this.getSqlSession().selectList(NAMESPACES +"selectBillPaymentByPaymentByBatchNos",map);
		}
		return list;
	}

    /**
     * 根据来源单号集合和部门编码集合，查询付款单信息 
     * 
     * @author 099995-foss-wujiangtao
     * @date 2013-1-22 上午9:34:59
     * @param sourceBillNos
     * @param sourceBillType
     * @param orgCodes
     * @param active
     * @param currentInfo
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentEntity> queryBySourceBillNOsAndOrgCodes(List<String> sourceBillNos, String sourceBillType,List<String> orgCodes, String active,CurrentInfo currentInfo) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("sourceBillNos", sourceBillNos);
		map.put("sourceBillType", sourceBillType);
		map.put("orgCodes", orgCodes);
		map.put("active", active);
		map.put("currentInfo", currentInfo);
		return this.getSqlSession().selectList(NAMESPACES+"selectBySourceBillNOsAndOrgCodes",map);
	}


	/**
	 * 根据单个付款单号，查询付款单的汇款状态
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-3-7 下午4:56:34
	 * @param paymentNo
	 * @param active
	 * @return
	 */
	@Override
	public String queryPaymentRemitStatusByPaymentNO(String paymentNo,String active) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("paymentNo", paymentNo);
		map.put("active", active);
		Object obj=this.getSqlSession().selectOne(NAMESPACES + "selectPaymentRemitStatusByPaymentNO", map);
		return obj!=null ? obj.toString() : null;
	}


	@Override
	public int updatePaymentBatchNoBack(BillPaymentDto billPaymentDto) {
		return this.getSqlSession().update(NAMESPACES + "updatePaymentBatchNoBack", billPaymentDto);
	}


	@Override
	public int updatePaymentByBatchNo(Map<Object, String> map) {
		int rownum =  this.getSqlSession().update(NAMESPACES + "updatePaymentByBatchNo", map);
		return rownum;
	}
	/**
	 * 合伙人到付运费自动付款重推更新付款单信息
	 * @author 231438
	 * @param billPaymentDto
	 * @return int
	 */
	@Override
	public int updateFcusPaymentByPaymentNos(BillPmtAutoPayDto billPaymentDto)	{
		return this.getSqlSession().update(NAMESPACES + "updateFcusPaymentByPmtNos", billPaymentDto);
	}
}
