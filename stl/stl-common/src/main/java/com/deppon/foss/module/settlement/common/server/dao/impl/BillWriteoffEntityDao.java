package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillWriteoffEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffQueryParaDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 核销单DAO接口
 * 
 * @author foss-wangxuemin
 * @date Nov 20, 2012 10:47:09 AM
 * @version
 */
public class BillWriteoffEntityDao extends iBatis3DaoImpl implements IBillWriteoffEntityDao {

	private static final String NAMESPACES = "foss.stl.BillWriteoffEntityDao.";

	/**
	 * 增加核销单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-17 下午7:35:34
	 * @param entity
	 *  核销单
	 * @return
	 */
	@Override
	public int add(BillWriteoffEntity entity) {

		// 增加核销单
		return this.getSqlSession().insert(NAMESPACES + "insert", entity);
	}

	/**
	 * 根据ID查询核销单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-17 下午7:35:45
	 * @param id
	 * @return
	 */
	@Override
	public BillWriteoffEntity queryByPrimaryKey(String id) {
		// 设置查询参数
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(id)) {
			map.put("id", id);
			// 根据ID查询核销单
			return (BillWriteoffEntity) this.getSqlSession().selectOne(NAMESPACES + "selectByPrimaryKey", map);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BillWriteoffEntity> queryByPrimaryKeys(List<String> detailIds) {
			// 根据ID查询核销单
			return this.getSqlSession().selectList(NAMESPACES + "selectByPrimaryKeys", detailIds);
	}
	

	/**
	 * 根据ID更新核销单的生效标志
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-26 下午3:08:38
	 * @param billWriteoffEntity
	 *            核销单
	 * @return
	 */
	@Override
	public int updateActiveById(BillWriteoffEntity billWriteoffEntity) {
		// 设置查询参数
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != billWriteoffEntity
				&& StringUtils.isNotEmpty(billWriteoffEntity.getId())
				&& StringUtils.isNotEmpty(billWriteoffEntity.getActive())
				&& null != billWriteoffEntity.getVersionNo()
				&& null != billWriteoffEntity.getAccountDate()) {
			map.put("id", billWriteoffEntity.getId());
			map.put("active", billWriteoffEntity.getActive());
			map.put("versionNo", billWriteoffEntity.getVersionNo());
			map.put("accountDate", billWriteoffEntity.getAccountDate());
			// 根据ID更新核销单的生效标志
			return this.getSqlSession().update(NAMESPACES + "updateActiveById", map);
		}
		return 0;
	}

	/**
	 * 根据开始来源编号查询核销单记录
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-24 下午2:58:58
	 * @param beginNo
	 *  开始单号
	 * @param active
	 *  是否有效
	 * @param createType
	 *  创建类型
	 * @param writeoffType
	 *  核销类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillWriteoffEntity> queryByBeginNo(String beginNo,String active, String createType, String writeoffType) {
		// 设置查询参数
		Map<String, Object> map = new HashMap<String, Object>();
		// 属性不为空，用map进行设置属性值
		if (StringUtils.isNotEmpty(beginNo)) {
			map.put("beginNo", beginNo);
		} else {
			return null;
		}
		// 是否有效
		if (StringUtils.isNotEmpty(active)) {
			map.put("active", active);
		}
		// 创建方式
		if (StringUtils.isNotEmpty(createType)) {
			map.put("createType", createType);
		}
		// 核销类型
		if (StringUtils.isNotEmpty(writeoffType)) {
			map.put("writeoffType", writeoffType);
		}
		// 根据开始来源编号查询核销单记录
		if (map != null && map.size() > 0) {
			return this.getSqlSession().selectList(NAMESPACES + "selectByBeginNo", map);
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<BillWriteoffEntity> queryByBeginNoR(String beginNo,String active, String createType, String writeoffType,int start,int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		// 设置查询参数
		Map<String, Object> map = new HashMap<String, Object>();
		// 属性不为空，用map进行设置属性值
		if (StringUtils.isNotEmpty(beginNo)) {
			map.put("beginNo", beginNo);
		} else {
			return null;
		}
		// 是否有效
		if (StringUtils.isNotEmpty(active)) {
			map.put("active", active);
		}
		// 创建方式
		if (StringUtils.isNotEmpty(createType)) {
			map.put("createType", createType);
		}
		// 核销类型
		if (StringUtils.isNotEmpty(writeoffType)) {
			map.put("writeoffType", writeoffType);
		}
		// 根据开始来源编号查询核销单记录
		if (map != null && map.size() > 0) {
			return this.getSqlSession().selectList(NAMESPACES + "selectByBeginNoR", map ,rowBounds);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> queryByBeginNoRepay(String beginNo, String active,String createType, String writeoffType) {
		// 设置查询参数
		Map<String, Object> map = new HashMap<String, Object>();
		// 属性不为空，用map进行设置属性值
		if (StringUtils.isNotEmpty(beginNo)) {
			map.put("beginNo", beginNo);
		} else {
			return null;
		}
		// 是否有效
		if (StringUtils.isNotEmpty(active)) {
			map.put("active", active);
		}
		// 创建方式
		if (StringUtils.isNotEmpty(createType)) {
			map.put("createType", createType);
		}
		// 核销类型
		if (StringUtils.isNotEmpty(writeoffType)) {
			map.put("writeoffType", writeoffType);
		}
		// 根据开始来源编号查询核销单记录
		if (map != null && map.size() > 0) {
			return  this.getSqlSession().selectList(NAMESPACES + "selectByBeginNoRepay", map);
		}
		return null;
	}

	
	/**
	 * 
	 * 根据运单号查询是否存在手工核销的应收单
	 * 
	 * @Title: queryHandWriteoffReceivableByWaybillNo
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-12 上午11:34:36
	 * @param @param waybillNo
	 * @param @return 设定文件
	 * @return List<BillWriteoffEntity> 返回类型
	 */
	@SuppressWarnings("unchecked")
	public List<BillWriteoffEntity> queryHandWriteoffReceivableByWaybillNo(String waybillNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("waybillNo", waybillNo);
		params.put("active", FossConstants.YES);
		params.put("createType",SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
		return this.getSqlSession().selectList(NAMESPACES + "queryHandWriteoffReceivableByWaybillNo", params);
	}

	/**
	 * 根据目的来源编号查询核销单记录
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-24 下午3:04:03
	 * @param endNo
	 * 截止单号
	 * @param active
	 * 是否有效
	 * @param createType
	 * 创建类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillWriteoffEntity> queryByEndNo(String endNo, String active,String createType) {
		// 设置查询参数
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(endNo)) {
			map.put("endNo", endNo);
		} else {
			return null;
		}
		if (StringUtils.isNotEmpty(active)) {
			map.put("active", active);
		}
		if (StringUtils.isNotEmpty(createType)) {
			map.put("createType", createType);
		}

		// 根据目的来源编号查询核销单记录
		if (map != null && map.size() > 0) {
			return this.getSqlSession().selectList(NAMESPACES + "selectByEndNo", map);
		}
		return null;
	}

	/**
	 * 根据开始来源编号和目的来源编号查询核销单记录
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 27, 2012 4:07:29 PM
	 * @param queryParaDto
	 * 查询条件DTO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillWriteoffEntity> queryByBeginNoAndEndNo(BillWriteoffQueryParaDto queryParaDto) {

		// 判断查询条件
		if (null != queryParaDto
				&& StringUtils.isNotEmpty(queryParaDto.getBeginNo())
				&& StringUtils.isNotEmpty(queryParaDto.getEndNo())
				&& StringUtils.isNotEmpty(queryParaDto.getActive())
				&& StringUtils.isNotEmpty(queryParaDto.getCreateType())
				&& StringUtils.isNotEmpty(queryParaDto.getWriteoffType())) {
			// 根据开始来源编号和目的来源编号查询核销单记录
			return this.getSqlSession().selectList(
					NAMESPACES + "selectByBeginNoAndEndNo", queryParaDto);
		} else {
			return null;
		}
	}

	/**
	 * 根据对账单号查询核销单记录
	 * 
	 * @author foss-wangxuemin
	 * @date Mar 20, 2013 3:59:52 PM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillWriteoffEntity> queryByStatementBillNo(String statementBillNo, String beginNo, String endNo,String writeoffType, String active) {
		// 判断查询条件
		if (StringUtils.isNotEmpty(statementBillNo)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("statementBillNo", statementBillNo);
			if (StringUtils.isNotEmpty(beginNo)) {
				map.put("beginNo", beginNo);
			}
			if (StringUtils.isNotEmpty(endNo)) {
				map.put("endNo", endNo);
			}
			if (StringUtils.isNotEmpty(writeoffType)) {
				map.put("writeoffType", writeoffType);
			}
			if (StringUtils.isNotEmpty(active)) {
				map.put("active", active);
			}
			// 根据对帐单号查询核销记录
			return this.getSqlSession().selectList(NAMESPACES + "selectByStatementBillNo", map);
		} else {
			return null;
		}
	}

}
