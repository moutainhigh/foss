package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.pay.api.server.dao.IBillDepositReceivedUnverifyDao;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillDepositReceivedPayDto;

/**
 * 预收单dao
 * @author foss-pengzhen
 * @date 2012-11-19 下午4:28:20
 * @since
 * @version
 */
public class BillDepositReceivedUnverifyDao  extends iBatis3DaoImpl implements IBillDepositReceivedUnverifyDao{

	public static final String NAMESPACES = "foss.stl.BillDepositReceivedEntityDao.";
	
	/**
	 * 根据日期等参数查询对应的预收单信息
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<BillDepositReceivedEntity> queryDepositReceivedUnverifyParams(
			BillDepositReceivedPayDto billDepositReceivedPayDto,
			int start,int limit) {
		if(billDepositReceivedPayDto != null){
			RowBounds rowBounds = new RowBounds(start,limit);
			return this.getSqlSession().selectList(NAMESPACES
					+ "queryDepositReceivedUnverifyParams", billDepositReceivedPayDto,rowBounds);
		}
		return null;
	}
	
	/**
	 * 根据日期等参数查询对应的预收单信息(不分页)
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<BillDepositReceivedEntity> queryDepositReceivedUnverifyParam(
			BillDepositReceivedPayDto billDepositReceivedPayDto) {
		if(billDepositReceivedPayDto != null){
			return this.getSqlSession().selectList(NAMESPACES
					+ "queryDepositReceivedUnverifyParams", billDepositReceivedPayDto);
		}
		return null;
	}
	
	/**
	 * 根据日期等参数查询对应的预收单总数
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	public BillDepositReceivedPayDto queryDepositReceivedUnverifyParamsCount(
			BillDepositReceivedPayDto billDepositReceivedPayDto){
		if(billDepositReceivedPayDto != null){
			return (BillDepositReceivedPayDto) this.getSqlSession().selectOne(NAMESPACES + "queryDepositReceivedUnverifyParamsCount",billDepositReceivedPayDto);
		}
		return null;
	}

	/**
	 * 根据预收单参数查询对应的预收单信息
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<BillDepositReceivedEntity> queryDepositReceivedNos(
			List<String> depositReceivedNos, CurrentInfo currentInfo,String active,
			int start,int limit) {
		if(CollectionUtils.isNotEmpty(depositReceivedNos)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("depositReceivedNos", depositReceivedNos);
			paramMap.put("empCode",currentInfo.getEmpCode());
			paramMap.put("active", active);
			RowBounds rowBounds = new RowBounds(start,limit);
			return this.getSqlSession().selectList(NAMESPACES
					+ "queryDepositReceivedNos", paramMap,rowBounds);
		}
		return null;
	}

	/**
	 * 根据预收单参数查询对应的预收单总数
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	public BillDepositReceivedPayDto queryDepositReceivedNosCount(
			List<String> depositReceivedNos, CurrentInfo currentInfo,String active){
		if(CollectionUtils.isNotEmpty(depositReceivedNos)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("depositReceivedNos", depositReceivedNos);
			paramMap.put("empCode",currentInfo.getEmpCode());
			paramMap.put("active", active);
			return (BillDepositReceivedPayDto) this.getSqlSession().selectOne(NAMESPACES
					+ "queryDepositReceivedNosCount", paramMap);
		}
		return null;
	}
}
