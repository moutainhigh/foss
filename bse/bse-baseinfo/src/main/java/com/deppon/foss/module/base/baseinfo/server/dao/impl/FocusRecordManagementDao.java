package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IFocusRecordManagementDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FocusRecordManagementEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

public class FocusRecordManagementDao extends SqlSessionDaoSupport implements IFocusRecordManagementDao {

	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".focusRecordManagement.";
	/**
	 * <p>新增操作</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	@Override
	public void addFocusRecordManagement(FocusRecordManagementEntity entity) {
		// TODO Auto-generated method stub
		getSqlSession().insert(NAMESPACE + "addFocusRecordManagement",entity);
	}
	/**
	 * <p>分页查询</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FocusRecordManagementEntity> queryFocusRecordManagementList(
			FocusRecordManagementEntity entity, int start, int limit) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(NAMESPACE + "queryFocusRecordManagementList", entity, new RowBounds(start, limit));
	}
	/**
	 * <p>根据code查询</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FocusRecordManagementEntity> queryFocusRecordManagementByCode(
			String billingGroupCode) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("billingGroupCode", billingGroupCode);
		map.put("active","Y");
		return getSqlSession().selectList(NAMESPACE + "queryFocusRecordManagementByCode",map);
	}
	/**
	 * <p>分页查询count</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	@Override
	public long queryCount(FocusRecordManagementEntity entity) {
		// TODO Auto-generated method stub
		return (Long)getSqlSession().selectOne(NAMESPACE+"queryCount",entity);
	}
	/**
	 * <p>提供给新增修改用 查看数据是否已经存在 如果存在不让再添加</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FocusRecordManagementEntity> queryIfExist(
			FocusRecordManagementEntity entity) {
		// TODO Auto-generated method stub
		List<String> idStr=null;
		if(StringUtils.isNotBlank(entity.getId())){
			idStr=Arrays.asList(entity.getId().split(","));
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("ids", idStr);
		map.put("billingGroupCode",entity.getBillingGroupCode());
		return  getSqlSession().selectList(NAMESPACE + "queryIfExist",map);
	}
	/**
	 * <p>删除 后台是将active状态置为N</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	@Override
	public long deleteFocusRecordManagement(List<String> ids) {
		// TODO Auto-generated method stub
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("ids", ids);
		map.put("modifyDate", new Date());
		map.put("modifyUser", FossUserContext.getCurrentInfo().getEmpCode());
		return getSqlSession().update(NAMESPACE + "deleteFocusRecordManagement", map);
	}
	/**
	 * <p>查询所有集中开单组信息 提供给接送货</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FocusRecordManagementEntity> queryAllBillingGroup() {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(NAMESPACE + "queryAllBillingGroup");
	}
}
