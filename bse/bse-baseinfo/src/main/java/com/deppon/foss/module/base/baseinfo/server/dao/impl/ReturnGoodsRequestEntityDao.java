package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IReturnGoodsRequestEntityDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;

/**
 * 返货工单Dao
 * @author 198771
 *
 */
public class ReturnGoodsRequestEntityDao extends SqlSessionDaoSupport implements IReturnGoodsRequestEntityDao{
	
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.returnGoods.";
	
	private static final Logger logger = LoggerFactory.getLogger(ReturnGoodsRequestEntityDao.class);

	//新增
	@Override
	public void addReturnGoodsRequestEntity(ReturnGoodsRequestEntity entity) {
		this.getSqlSession().insert(NAMESPACE+"addReturnGoodsRequestEntity", entity);
	}


	//查询
	@SuppressWarnings("unchecked")
	@Override
	public List<ReturnGoodsRequestEntity> queryReturnGoodsRequestEntityByCondition(
			ReturnGoodsRequestEntity condition) {
			if(condition !=null && (StringUtils.isNotBlank(condition.getFossId())
					|| StringUtils.isNotBlank(condition.getOriWaybill())
					||StringUtils.isNotBlank(condition.getDealnumber())
					||condition.getCreateStartTime()!=null
					||StringUtils.isNotBlank(condition.getReportDepartmentCode())
					||StringUtils.isNotBlank(condition.getCreateUserCode()))){
				
				return (List<ReturnGoodsRequestEntity>)this.getSqlSession().selectList(NAMESPACE+"selectReturnGoodsRequestEntityByCondition", condition);
			}else{
				logger.error("谁让你传空值的 搞的我们加班排查问题，亲 赶快去改");
				throw new BusinessException("传入参数实体为空，请上报IT服务台 ") ;
			}
	}
	
	//查询
		@SuppressWarnings("unchecked")
		@Override
		public Long queryIsHandleByWayBillNo(
				ReturnGoodsRequestEntity condition) {
			return (Long) this.getSqlSession().selectOne(NAMESPACE + "getIsHandleByWayBillNo",
					condition);
		}


	@Override
	public void updateReturnGoodsRequestEntity(
			ReturnGoodsRequestEntity condition) {
		this.getSqlSession().update(NAMESPACE+"updateReturnGoodsRequestEntity", condition);
	}
	
	@Override
	public void updateHandleResult(ReturnGoodsRequestEntity entity){
		this.getSqlSession().update(NAMESPACE+"updateReturnGoodsHandleResult", entity);
	}


	/**
	 * 验证处理编号是否存在
	 * @param dealNumber
	 * @return
	 */
	@Override
	public boolean queryExistsReturnGoodsRequestEntityByDealnumber(
			String dealNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dealNumber", dealNumber);
		long count=(Long)this.getSqlSession().selectOne(NAMESPACE+"queryExistsReturnGoodsRequestEntityByDealnumber", map);
		if(count>0){
			return true;
		}
		return false;
	}



}
