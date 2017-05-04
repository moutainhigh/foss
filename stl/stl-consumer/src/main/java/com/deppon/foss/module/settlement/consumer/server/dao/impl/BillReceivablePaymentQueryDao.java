package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IBillReceivablePaymentQueryDao;

/**
 * 查询应收应付单Dao实现
 * 
 * @author foss-zhangxiaohui
 * @date Oct 29, 2012 10:24:08 AM
 */
public class BillReceivablePaymentQueryDao extends iBatis3DaoImpl implements IBillReceivablePaymentQueryDao {

	/**
	 * 命名空间路径
	 */
	public static final String NAMESPACES = "foss.stl.BillReceivablePaymentQueryDao.";

	/**
	 * 通过运单单号查询收款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 10:25:08 AM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillRepaymentEntity> queryRepaymentBillByWayBillNOs(List<String> wayBillNos, String active) {
		//声明传参的Map
		Map<String, Object> map = new HashMap<String, Object>();
		//判断参数运单集合非空
		if (CollectionUtils.isNotEmpty(wayBillNos)) {
			//如果运单集合非空则添加到map里面
			map.put("wayBillNos",wayBillNos);
			//判断active是否非空
			if(StringUtils.isNotEmpty(active)){
				//如果active非空则添加到map里面
				map.put("active",active);
			}
			//返回查询结果List
			return this.getSqlSession().selectList(NAMESPACES + "queryRepaymentBillByWayBillNos", map);
		}
		//默认返回空
		return null;
	}
	
	/**
	 * 通过运单单号,收入部门查询收款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 10:25:08 AM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillRepaymentEntity> queryRepaymentBillByWayBillNOsAndOrgCodes(List<String> wayBillNos, List<String> orgCodes, String active ,String isRedBack) {
		//声明传参的Map
		Map<String, Object> map = new HashMap<String, Object>();
		//判断参数运单集合非空
		if (CollectionUtils.isNotEmpty(wayBillNos)) {
			//如果运单集合非空则添加到map里面
			map.put("wayBillNos",wayBillNos);
			
			map.put("orgCodes",orgCodes);
			
			//判断active是否非空
			if(StringUtils.isNotEmpty(active)){
				//如果active非空则添加到map里面
				map.put("active",active);
			}
			
			//判断是否红单
			if(StringUtils.isNotEmpty(isRedBack)){
				//如果active非空则添加到map里面
				map.put("isRedBack",isRedBack);
			}
			
			
			//返回查询结果List
			return this.getSqlSession().selectList(NAMESPACES + "queryRepaymentBillByWayBillNos", map);
		}
		//默认返回空
		return null;
	}
	
	/**
	 * 通过运单单号查询付款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 10:27:08 AM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentEntity> queryPaymentBillByWayBillNOs(
			List<String> wayBillNos, String active) {
		//声明传参的Map
		Map<String, Object> map = new HashMap<String, Object>();
		//判断参数运单集合非空
		if (CollectionUtils.isNotEmpty(wayBillNos)) {
			//如果运单集合非空则添加到map里面
			map.put("wayBillNos",wayBillNos);
			//判断active是否非空
			if(StringUtils.isNotEmpty(active)){
				//如果active非空则添加到map里面
				map.put("active",active);
			}
			//返回查询结果List
			return this.getSqlSession().selectList(NAMESPACES + "queryPaymentBillByWayBillNos", map);
		}
		//默认返回空
		return null;
	}
	
	/**
	 * 通过运单单号、付款部门查询付款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 10:27:08 AM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentEntity> queryPaymentBillByWayBillNOsAndOrgCodes(
			List<String> wayBillNos, List<String> orgCodes, String active) {
		//声明传参的Map
		Map<String, Object> map = new HashMap<String, Object>();
		//判断参数运单集合非空
		if (CollectionUtils.isNotEmpty(wayBillNos)) {
			//如果运单集合非空则添加到map里面
			map.put("wayBillNos",wayBillNos);
			
			map.put("orgCodes",orgCodes);
			
			//判断active是否非空
			if(StringUtils.isNotEmpty(active)){
				//如果active非空则添加到map里面
				map.put("active",active);
			}
			//返回查询结果List
			return this.getSqlSession().selectList(NAMESPACES + "queryPaymentBillByWayBillNos", map);
		}
		//默认返回空
		return null;
	}

	/**
	 * 通过运单单号查询核销单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 10:29:08 AM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillWriteoffEntity> queryWriteoffBillByWayBillNOs(
			List<String> wayBillNos, String active) {
		//声明传参的Map
		Map<String, Object> map = new HashMap<String, Object>();
		//判断参数运单集合非空
		if (CollectionUtils.isNotEmpty(wayBillNos)) {
			//如果运单集合非空则添加到map里面
			map.put("wayBillNos",wayBillNos);
			//判断active是否非空
			if(StringUtils.isNotEmpty(active)){
				//如果active非空则添加到map里面
				map.put("active",active);
			}
			//返回查询结果List
			return this.getSqlSession().selectList(NAMESPACES + "queryWriteoffBillByWayBillNos", map);
		}
		//默认返回空
		return null;
	}
	
	/**
	 * 通过运单单号查询核销单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 10:29:08 AM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillWriteoffEntity> queryWriteoffBillByWayBillNOsAndOrgCodes(
			List<String> wayBillNos, List<String> orgCodes, String active) {
		//声明传参的Map
		Map<String, Object> map = new HashMap<String, Object>();
		//判断参数运单集合非空
		if (CollectionUtils.isNotEmpty(wayBillNos)) {
			//如果运单集合非空则添加到map里面
			map.put("wayBillNos",wayBillNos);
			
			map.put("orgCodes",orgCodes);
			
			//判断active是否非空
			if(StringUtils.isNotEmpty(active)){
				//如果active非空则添加到map里面
				map.put("active",active);
			}
			//返回查询结果List
			return this.getSqlSession().selectList(NAMESPACES + "queryWriteoffBillByWayBillNos", map);
		}
		//默认返回空
		return null;
	}

	/**
	 * 通过运单单号查询应收单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 10:29:08 AM
	 */
	@Override
	public List<BillReceivableEntity> queryByWaybillNosAndOrgCodes(
			List<String> waybillNos, List<String> orgCodeList, String active,
			CurrentInfo currentInfo) {
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
	 * 通过运单单号查询应付单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 10:29:08 AM
	 */
	@Override
	public List<BillPayableEntity> queryPayableByWaybillNosAndOrgCodes(
			List<String> waybillNos, List<String> orgCodeList, String active,
			CurrentInfo currentInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNos", waybillNos);
		map.put("orgCodeList", orgCodeList);
		map.put("active", active);
		map.put("currentInfo", currentInfo);
		return this.getSqlSession().selectList(NAMESPACES+"selectPayableByWaybillNosAndOrgCodes",map);
	}

	/**
	 * 通过来源单号查询现金收款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 10:29:08 AM
	 */
	@Override
	public List<BillCashCollectionEntity> queryBySourceBillNOsAndOrgCodes(
			List<String> sourceBillNos, String sourceBillType,
			List<String> orgCodes, String active, CurrentInfo currentInfo) {
		//new 一个map 存放查询需要的属性值
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sourceBillNos", sourceBillNos);
		map.put("sourceBillType", sourceBillType);
		map.put("orgCodes", orgCodes);
		map.put("active", active);
		map.put("currentInfo", currentInfo);//操作者权限
		
		return this.getSqlSession().selectList(NAMESPACES+"selectBySourceBillNOsAndOrgCodes",map);
	}


}
