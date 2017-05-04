package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffQueryParaDto;

/**
 * 核销单DAO接口
 * 
 * @author foss-wangxuemin
 * @date Nov 20, 2012 10:47:09 AM
 */
public interface IBillWriteoffEntityDao {

	/**
	 * 增加核销单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-17 下午7:35:34
	 * @param entity
	 *            核销单
	 * @return
	 */
	int add(BillWriteoffEntity entity);

	/**
	 * 根据ID查询核销单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-17 下午7:35:45
	 * @param id
	 * @return
	 */
	BillWriteoffEntity queryByPrimaryKey(String id);
	
	/**
	 * 根据ID集合查询核销单
	 * */
	List<BillWriteoffEntity> queryByPrimaryKeys(List<String> detailIds);

	/**
	 * 根据ID更新核销单的生效标志
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-26 下午3:08:38
	 * @param billWriteoffEntity
	 *            核销单
	 * @return
	 */
	int updateActiveById(BillWriteoffEntity billWriteoffEntity);

	/**
	 * 根据开始来源编号查询核销单记录
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-24 下午2:58:58
	 * @param beginNo
	 *            开始单号
	 * @param active
	 *            是否有效
	 * @param createType
	 *            创建类型
	 * @param writeoffType
	 *            核销类型
	 * @return
	 */
	List<BillWriteoffEntity> queryByBeginNo(String beginNo, String active,
			String createType, String writeoffType);

	/**
	 * 根据开始来源编号查询核销单记录
	 * 分页查询(查询非已经申请的)
	 * bochenlong
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-25 上午11:12:23 
	 * @param beginNo
	 * @param active
	 * @param createType
	 * @param writeoffType
	 * @param start
	 * @param limit
	 * @return
	 */
	List<BillWriteoffEntity> queryByBeginNoR(String beginNo, String active,
			String createType, String writeoffType ,int start ,int limit);
	
	List<Map<String,Object>> queryByBeginNoRepay(String beginNo, String active,
			String createType, String writeoffType);
	
	/**
	 * 
	 * 根据运单号查询是否存在手工核销的应收单
	 * @Title: queryHandWriteoffReceivableByWaybillNo 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-12 上午11:34:36
	 * @param @param waybillNo
	 * @param @return    设定文件 
	 * @return List<BillWriteoffEntity>    返回类型 
	 * @throws
	 */
	List<BillWriteoffEntity> queryHandWriteoffReceivableByWaybillNo(String waybillNo);

	/**
	 * 根据目的来源编号查询核销单记录
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-24 下午3:04:03
	 * @param endNo
	 *            截止单号
	 * @param active
	 *            是否有效
	 * @param createType
	 *            创建类型
	 * @return
	 */
	List<BillWriteoffEntity> queryByEndNo(String endNo, String active,
			String createType);

	/**
	 * 根据开始来源编号和目的来源编号查询核销单记录
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 27, 2012 4:07:29 PM
	 * @param queryParaDto
	 *            查询条件DTO
	 * @return
	 */
	List<BillWriteoffEntity> queryByBeginNoAndEndNo(
			BillWriteoffQueryParaDto queryParaDto);

	/**
	 * 根据对账单号查询核销单记录
	 * 
	 * @author foss-wangxuemin
	 * @date Mar 20, 2013 3:59:52 PM
	 */
	List<BillWriteoffEntity> queryByStatementBillNo(String statementBillNo,
			String beginNo, String endNo, String writeoffType, String active);
}
