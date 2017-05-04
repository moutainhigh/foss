package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IForkliftGoodsEfficiencyDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkliftDriverEffEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkliftDriverOrgEffEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkliftEffEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.ForkliftGoodsStayDurationDto;

/**
 * 电叉分货效率dao
 * @author 200978
 * 2015-2-5
 */
public class ForkliftGoodsEfficiencyDao extends iBatis3DaoImpl implements
		IForkliftGoodsEfficiencyDao {

	private static final String NAMESPACE = "Foss.platform.forkliftGoodsEfficiencyJob.";
	
	/**
	 * 查询所有有待叉区的非分部外场
	 * @Author 200978
	 * 2015-2-5
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ForkliftEffEntity> queryTransferCenterContainForklift(){
		return this.getSqlSession().selectList(NAMESPACE+"queryTransferCenterContainForklift");
	}
	
	/**
	 * 执行存储过程PRO_FORKLIFT_GOODS_EFFICIENCY   开始统计
	 * @Author 200978
	 * 2015-2-5
	 * @param staDate
	 * @param staMonth
	 * @param tfrCode
	 * @param tfrName
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void calculateForkliftGoodsEfficiencyPerTfr(Date staDate,int staMonth,String tfrCode,String tfrName){
		Map map = new HashMap();
		map.put("staDate",staDate);
		map.put("staMonth", staMonth);
		map.put("tfrCode", tfrCode);
		map.put("tfrName", tfrName);
		this.getSqlSession().selectOne(NAMESPACE+"calculateForkliftGoodsEfficiencyPerTfr", map);
	}
	
	/**
	 * 根据当前部门查询 他对应的经营本部
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-24
	 * @param code
	 * @return
	 */
	@Override
	public ForkliftEffEntity queryOperationDeptCodeByCurrentCode(String code){
		return (ForkliftEffEntity) this.getSqlSession().selectOne(NAMESPACE + "queryOperationDeptCodeByCurrentCode", code);
	}
	
	/***
	 * 查询电叉分货效率  转运场
	 * @Author 200978
	 * 2015-2-7
	 * @param forkliftEffEntity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ForkliftEffEntity> queryForkliftGoodsEfficiencyOfTfr(ForkliftEffEntity forkliftEffEntity){
		return this.getSqlSession().selectList(NAMESPACE+"queryForkliftGoodsEfficiencyOfTfr", forkliftEffEntity);
	}
	
	/***
	 * 电叉分货效率   叉车司机所属部门
	 * @Author 200978
	 * 2015-2-7
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ForkliftDriverOrgEffEntity> queryForkliftGoodsEfficiencyOfOrg(ForkliftDriverEffEntity forkliftDriverEffEntity,int start,int limit){
		RowBounds rb = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryForkliftGoodsEfficiencyOfOrg", forkliftDriverEffEntity,rb);
	}
	
	/***
	 * 电叉分货效率   叉车司机所属部门 count
	 * @Author 200978
	 * 2015-2-7
	 * @return
	 */
	@Override
	public Long queryForkliftGoodsEfficiencyOfOrgCount(ForkliftDriverEffEntity forkliftDriverEffEntity){
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryForkliftGoodsEfficiencyOfOrgCount", forkliftDriverEffEntity);
	}
	
	/**
	 * 电叉分货效率  叉车司机
	 * @Author 200978
	 * 2015-2-7
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ForkliftDriverEffEntity> queryForkliftGoodsEfficiencyOfDriver(ForkliftDriverEffEntity forkliftDriverEffEntity){
		return this.getSqlSession().selectList(NAMESPACE+"queryForkliftGoodsEfficiencyOfDriver", forkliftDriverEffEntity);
	}
	
	/**
	 * 查询待叉区停留时长占比
	 * @Author 200978
	 * 2015-2-7
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ForkliftGoodsStayDurationDto> queryForkliftStayDuration(ForkliftEffEntity forkliftEffEntity){
		return this.getSqlSession().selectList(NAMESPACE+"queryForkliftStayDuration", forkliftEffEntity);
	}
	
}
