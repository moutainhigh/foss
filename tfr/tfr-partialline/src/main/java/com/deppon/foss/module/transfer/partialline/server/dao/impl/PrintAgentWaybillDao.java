package com.deppon.foss.module.transfer.partialline.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IPrintAgentWaybillDao;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillRecordEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.PrintAgentWaybillDto;

public class PrintAgentWaybillDao extends iBatis3DaoImpl implements IPrintAgentWaybillDao {

	private static String prefix = "foss.partialline.printAgentWaybillMapper.";
	
	/**
	 * @author nly
	 * @date 2014年9月5日 下午4:00:26
	 * @function 查询打印代理面单的运单信息
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PrintAgentWaybillEntity> queryWaybills(PrintAgentWaybillDto dto,int start ,int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(prefix + "queryWaybills",dto,rowBounds);
	}

	/**
	 * @author nly
	 * @date 2014年9月9日 下午2:39:17
	 * @function 查询运单总数
	 * @param dto
	 * @return
	 */
	@Override
	public Long queryWaybillCount(PrintAgentWaybillDto dto) {
		return (Long) this.getSqlSession().selectOne(prefix + "queryWaybillCount",dto);
	}

	/**
	 * @author chigo
	 * @date 2014-9-27上午9:50:15
	 * @function 
	 * @param printAgentWaybillRecordEntity
	 * @return
	 */
	@Override
	public Integer addPrintrecord(PrintAgentWaybillRecordEntity printAgentWaybillRecordEntity) {
		// TODO Auto-generated method stub
		return (Integer) this.getSqlSession().insert(prefix + "insert",printAgentWaybillRecordEntity);
	}
	
	/**
	 * 
	 * @author chigo
	 * @date 2014-9-27上午9:50:58
	 * @function 
	 * @param printAgentWaybillRecordEntity
	 * @return
	 */
	@Override
	public Integer updateActive(
			PrintAgentWaybillRecordEntity printAgentWaybillRecordEntity) {
		// TODO Auto-generated method stub
		return (Integer) this.getSqlSession().update(prefix + "updateActive",printAgentWaybillRecordEntity);
	}

	/**
	 * @author chigo
	 * @date 2014-10-11上午11:27:57
	 * @function 
	 * @param agentWaybillRecordEntity
	 * @param start
	 * @param limit
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PrintAgentWaybillRecordEntity> queryWaybillsRecord(
			PrintAgentWaybillRecordEntity agentWaybillRecordEntity, int start,
			int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(prefix + "queryWaybillsRecord",agentWaybillRecordEntity,rowBounds);
		
	}
	/**
	 * @author nly
	 * @date 2015年2月2日 下午5:17:14
	 * @function 根据代理单号查询打印记录
	 * @param agentWaybillNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PrintAgentWaybillRecordEntity> queryRecordByAgentWaybillNo(String agentWaybillNo) {
		return this.getSqlSession().selectList(prefix + "queryRecordByAgentWaybillNo", agentWaybillNo);
	}
	/**
	 * @author nly
	 * @date 2015年2月4日 下午2:37:57
	 * @function 查询运单绑定记录
	 * @param waybillNo
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PrintAgentWaybillRecordEntity> queryRecordByWaybillNo(String waybillNo, String type) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("waybillNo", waybillNo);
		map.put("type", type);
		return this.getSqlSession().selectList(prefix + "queryRecordByWaybillNo", map);
	}
	/**
	 * @author chigo
	 * @date 2015年2月3日 上午11:26:29
	 * @function 查询营业部绑定的运单信息
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PrintAgentWaybillRecordEntity> queryBundleWaybills(
			PrintAgentWaybillDto dto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(prefix + "queryBundleWaybills",dto,rowBounds);
	}
	/**
	 * @author chigo
	 * @date 2015年2月3日 上午11:26:29
	 * @function 查询运单总数
	 * @param dto
	 * @return
	 */
	@Override
	public Long queryBundleWaybillsCount(PrintAgentWaybillDto dto) {
		return (Long) this.getSqlSession().selectOne(prefix + "queryBundleWaybillsCount",dto);
	}
	/**
	 * @author chigo
	 * @date 2015年2月4日 下午21:34:56
	 * @function 存营业部运单和快递单号的绑定关系
	 * @param entity
	 * @return
	 */
	@Override
	public Integer bundleSdExternalBill(PrintAgentWaybillRecordEntity entity) {
		// TODO Auto-generated method stub
		return (Integer) this.getSqlSession().insert(prefix + "bundleSdExternalBill",entity);
	}
	/**
	 * @author chigo
	 * @date 2015年2月4日 下午21:45:59
	 * @function 存营业部绑定运单和快递单号的外发费用
	 * @param entity
	 * @return
	 */
	@Override
	public Integer bundleFrightFee(PrintAgentWaybillRecordEntity entity) {
		// TODO Auto-generated method stub
		return (Integer) this.getSqlSession().insert(prefix + "bundleFrightFee",entity);
	}
	/**
	 * @author chigo
	 * @date 2015-2-5上午11:19:29
	 * @function 作废绑定营业部运单和代理单号
	 * @param dto
	 * @return
	 */
	@Override
	public Integer unBundleSdExternalBill(PrintAgentWaybillRecordEntity entity) {
		return (Integer) this.getSqlSession().update(prefix + "unBundleSdExternalBill",entity);
	}
	/**
	 * @author chigo
	 * @date 2015-2-5上午11:20:56
	 * @function 作废绑定营业部运单外发费
	 * @param dto
	 * @return
	 */
	@Override
	public Integer unBundleFrightFee(PrintAgentWaybillRecordEntity entity) {
		return (Integer) this.getSqlSession().update(prefix + "unBundleFrightFee",entity);

	}
	/**
	 * @author chigo
	 * @date 2015-2-5下午4:50:59
	 * @function 绑定之前验证该运单是否已绑定
	 * @param entity
	 * @return
	 */
	@Override
	public Long validateBundle(PrintAgentWaybillRecordEntity entity) {
		return (Long) this.getSqlSession().selectOne(prefix + "validateBundle",entity);
	}

	/**
	 * @author nly
	 * @date 2015年4月28日 下午3:39:05
	 * @function 作废快递代理绑定
	 * @param entity
	 * @return
	 */
	@Override
	public int invalidLdpBindRecord(PrintAgentWaybillRecordEntity entity) {
		return (Integer) this.getSqlSession().update(prefix + "invalidLdpBindRecord",entity);
	}

	/**
	 * @author nly
	 * @date 2015年4月29日 下午12:43:47
	 * @function 查询该条记录是否存在
	 * @param waybillNo
	 * @param serialNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PrintAgentWaybillRecordEntity> queryRecordByWaybillNoAndSerialNo(String waybillNo, String serialNo) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("waybillNo", waybillNo);
		map.put("serialNo", serialNo);
		return this.getSqlSession().selectList(prefix + "queryRecordByWaybillNoAndSerialNo", map);
	}

	/**
	 * @author nly
	 * @date 2015年4月29日 下午1:11:28
	 * @function
	 * @param waybillNo
	 * @param serialNo
	 * @param deptCode
	 * @return
	 */
	@Override
	public int queryStockAndHandOverBillCount(String waybillNo,	String serialNo, String deptCode) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("waybillNo", waybillNo);
		map.put("serialNo", serialNo);
		map.put("deptCode", deptCode);
		return (Integer) this.getSqlSession().selectOne(prefix + "queryStockAndHandOverBillCount", map);
	}
	
	public int queryOuterNetWorkNumByWaybillNo(String waybillNo){
		return (Integer) this.getSqlSession().selectOne(prefix + "queryOuterNetWorkNumByWaybillNo",waybillNo);
	}
	/**
	 * 查询已订阅的记录
	 * 1，根据运单号，流水号查询
	 * 2，根据代理公司，代理单号查询
	 * @param entity
	 * @return
	 * @author 257220
	 * @date 2015-8-3下午3:14:51
	 */
	@SuppressWarnings("unchecked")
	public List<PrintAgentWaybillRecordEntity> queryOrderedRecord(PrintAgentWaybillRecordEntity entity){
		return this.getSqlSession().selectList(prefix + "queryOrderedRecord", entity);
	}

	/**
	 * 根据代理单号、代理公司、运单号、流水号更新落地配订阅状态
	 * @param entity
	 * @return
	 * @function 更新订阅状态信息
	 * @author 257220
	 * @date 2015-8-3下午3:14:51
	 */
	public int updatePrintAgentPushState(
			PrintAgentWaybillRecordEntity printAgentWaybillRecordEntity) {
		return (Integer) this.getSqlSession().update(prefix + "updatePrintAgentPushState",printAgentWaybillRecordEntity);
	}

	/**
	 * @param entity
	 * @return 绑定实体
	 * @function 根据代理单号与代理公司查询绑定信息
	 * @author 257220
	 * @date 2015-8-10下午19:02:51
	 */
	@SuppressWarnings("unchecked")
	public List<PrintAgentWaybillRecordEntity> queryRecordByAgentWaybillNo(
			String agentWaybillNo, String agentCompanyCode) {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("agentWaybillNo", agentWaybillNo);
		paramMap.put("agentCompanyCode", agentCompanyCode);
		return this.getSqlSession().selectList(prefix + "queryRecordByAgentWaybillNoAndCompany", paramMap);
	}
}
