package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillCashCollectionEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillCashCollectionDto;

/**
 *现金收款单 DAO
 *@author 099995-foss-wujiangtao
 * @date 2012-10-12 下午6:20:08
 * @since
 * @version
 */
public class BillCashCollectionEntityDao extends iBatis3DaoImpl implements IBillCashCollectionEntityDao{
	
	/**
	 * 现金收款单namespace定义
	 */
	private static final String NAMESPACE = "foss.stl.BillCashCollectionEntityDao.";
    
	// -------------------- write methods --------------------
    
    /**
     * 新增现金收款单
     * @author ibm-zhuwei
     * @date 2012-10-24 上午11:33:00
     * @param entity 现金收款单
     * @return
     */
	@Override
	public int add(BillCashCollectionEntity entity) {
		return this.getSqlSession().insert(NAMESPACE + "insert", entity);
	}

    /**
     * 红冲现金收款单，原记录置为失效状态
     * @author ibm-zhuwei
     * @date 2012-10-23 下午3:35:31
     * @param entity 现金收款单
     * @return
     */
	@Override
	public int updateByWriteBack(BillCashCollectionEntity entity) {
		return this.getSqlSession().update(NAMESPACE + "updateByWriteBack", entity);
	}

	/**
	 * 签收确认(反确认)收入日期
     * @author 099995-foss-wujiangtao
     * @date 2012-10-22 上午11:52:25
     * @param entity 现金收款单
     * @return
	 */
	@Override
	public int updateByConfirmIncome(BillCashCollectionEntity entity) {
		return this.getSqlSession().update(NAMESPACE +"updateConrevenByConfirmIncome",entity);
	}

    /**
     * 收银确认
     * @author ibm-zhuwei
     * @date 2012-12-17 上午11:53:26
     * @param dto 现金收款单DTO
     * @return
     */
	@Override
	public int updateByConfirmCashier(BillCashCollectionDto dto) {
		return this.getSqlSession().update(NAMESPACE + "updateByConfirmCashier", dto);
    }

	// -------------------- read methods --------------------
	
	/**
	 * 根据传入的一到多个现金收款单Id号，获取一到多条现金收款单信息
	 * @author foss-pengzhen
	 * @date 2013-3-26 上午9:26:46
	 * @param cashCollectionIds  现金收款单Id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillCashCollectionEntity> queryByCashCollectionIds(List<String> cashCollectionIds, String active) {
		// 输入参数校验
		if (CollectionUtils.isEmpty(cashCollectionIds)) {
			return null;
		}
		//new 一个map 存放查询需要的属性值
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cashCollectionIds", cashCollectionIds);
		if(StringUtils.isNotEmpty(active)){
			map.put("active", active);
		}
		return this.getSqlSession().selectList(
				NAMESPACE + "selectByCashCollectionIds", map);
	}
	
    /**
     * 根据传入的一到多个现金收款单号，获取一到多条现金收款单信息
     * @author 099995-foss-wujiangtao
     * @date 2012-10-12 下午6:35:53
     * @param cashCollectionNos  现金收款单号集合
     * @param active             是否有效
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<BillCashCollectionEntity> queryByCashCollectionNOs(List<String> cashCollectionNos,String active){
    	//cashCollectionNos为空直接返回
    	if (CollectionUtils.isEmpty(cashCollectionNos)) {
			return null;
		}
    	//new 一个map 存放查询需要的属性值
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cashCollectionNos", cashCollectionNos);
		if(StringUtils.isNotEmpty(active)){
			map.put("active", active);
		}
		return this.getSqlSession().selectList(NAMESPACE + "selectByCashCollectionNos", map);
    }

	/**
	 * 根据传入的一到多个来源单号和来源单据类型，获取一到多条现金收款单信息
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-22 下午7:37:03
	 * @param sourceBillNos  来源单号集合
	 * @param sourceBillType 来源单据类型
	 * @param active    是否有效
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<BillCashCollectionEntity> queryBySourceBillNOs(List<String> sourceBillNos,String sourceBillType,String active){
		//来源单号集合为空，直接返回
		if (CollectionUtils.isEmpty(sourceBillNos)) {
			return null;
		}
		//new 一个map 存放查询需要的属性值
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sourceBillNos", sourceBillNos);
		map.put("sourceBillType", sourceBillType);
		if(StringUtils.isNotEmpty(active)){
			map.put("active", active);
		} 
		return this.getSqlSession().selectList(NAMESPACE + "selectBySourceBillNosAndSuBillType", map);
	}

	/**
	 * 根据来源单号集合和部门编码集合，查询现金收款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-22 上午9:26:58
	 * @param sourceBillNos 来源单号集合
	 * @param sourceBillType 来源单据类型
	 * @param orgCodes  部门编码集合
	 * @param active    是否有效
	 * @param currentInfo  当前操作者
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillCashCollectionEntity> queryBySourceBillNOsAndOrgCodes(
			List<String> sourceBillNos, String sourceBillType,
			List<String> orgCodes, String active,CurrentInfo currentInfo) {
		//new 一个map 存放查询需要的属性值
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sourceBillNos", sourceBillNos);
		map.put("sourceBillType", sourceBillType);
		map.put("orgCodes", orgCodes);
		map.put("active", active);
		map.put("currentInfo", currentInfo);//操作者权限
		
		return this.getSqlSession().selectList(NAMESPACE+"selectBySourceBillNOsAndOrgCodes",map);
	}

	 /**
     * 根据银联交易流水号，查询现金收款单信息
     * @author 045738-foss-maojianqiang
     * @date 2013-7-22 上午9:18:52
     * @param sourceBillNos
     * @param sourceBillType
     * @param orgCodes
     * @param active
     * @param currentInfo
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<BillCashCollectionEntity> queryByBatchNos(List<String> batchNos,String active, CurrentInfo currentInfo) {
		//new 一个map 存放查询需要的属性值
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("batchNos", batchNos);//银联交易流水号
		map.put("active", active);//银联交易流水号
		map.put("empCode", currentInfo.getEmpCode());//银联交易流水号
		return  this.getSqlSession().selectList(NAMESPACE+"selectCashCollectionByBatchNos",map);
	}

}
