package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressAutoComplementManageDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressAutoComplementManageEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
/**
 * 
 * @author 218392 zhangyongxue
 * @date 2015-05-18 14:10:28
 *
 */
/**
 * 
 * @author 218392
 *
 */
public class ExpressAutoComplementManageDao extends SqlSessionDaoSupport implements IExpressAutoComplementManageDao{

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.expressAutoComplementManage.";
	
	/**
	 * 新增自动补码管理信息
	 */
	@Override
	public int addExpressAutoComplementManageDao(
			ExpressAutoComplementManageEntity expressAutoComplementManageEntity) {
		
		return this.getSqlSession().insert(NAMESPACE+"insertExpressAutoComplementManage", expressAutoComplementManageEntity);
	}
	

	/**
	 * 根据传入的对象查询符合条件所有快递自动补码管理信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressAutoComplementManageEntity> queryAllExpressAutoComplementManage(
			ExpressAutoComplementManageEntity entity, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryAllExpressAutoComplementManage", entity, rowBounds);
	}

	/**
	 * 给中转的接口，传入城市编码，返回城市状态，1为关闭，2为静默，3位开启，如果没有该城市记录、或者该城市状态为空，返回1。
	 */
	@Override
	public String queryListCityCodeByCodeStatus(ExpressAutoComplementManageEntity entity) {
		return (String) this.getSqlSession().selectOne(NAMESPACE + "queryListCityCodeByCodeStatus", entity);
	}
	
	/**
	 * 统计记录数
	 */
	@Override
	public Long queryCount(ExpressAutoComplementManageEntity entity) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryCount", entity);
	}
	
	/**
	 * 根据城市code查询城市记录数
	 */
	@Override
	public Long queryCountByCityCode(ExpressAutoComplementManageEntity entity) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryCountByCityCode", entity);
	}

	/**
	 * 开启快递自动补码管理信息
	 */
	@Override
	public int openExpressAutoComplementManage(List<String> idList) {
		//获取当前登陆人的工号姓名
		UserEntity user = (UserEntity)UserContext.getCurrentUser();
		String userCode = user.getEmployee().getEmpCode();
		String userName = user.getEmployee().getEmpName();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idList", idList);
		map.put("modifyUser", userCode);
		map.put("modifyUserName", userName);
		map.put("createDate", new Date());
		map.put("status", "3");
		return this.getSqlSession().update(NAMESPACE + "updateExpressAutoComplementManage", map);
	}
	
	/**
	 * 关闭快递自动补码管理信息
	 */
	@Override
	public int closeExpressAutoComplementManage(List<String> idList) {
		
		UserEntity user = (UserEntity)UserContext.getCurrentUser();
		String userCode = user.getEmployee().getEmpCode();
		String userName = user.getEmployee().getEmpName();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idList", idList);
		map.put("modifyUser", userCode);
		map.put("modifyUserName", userName);
		map.put("createDate", new Date());
		map.put("status", "1");
		return this.getSqlSession().update(NAMESPACE + "updateExpressAutoComplementManage", map);
	}
	/**
	 * 静默快递自动补码管理信息
	 */
	@Override
	public int silentExpressAutoComplementManage(List<String> idList) {
		
		UserEntity user = (UserEntity)UserContext.getCurrentUser();
		String userCode = user.getEmployee().getEmpCode();
		String userName = user.getEmployee().getEmpName();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idList", idList);
		map.put("modifyUser", userCode);
		map.put("modifyUserName", userName);
		map.put("createDate", new Date());
		map.put("status", "2");
		return this.getSqlSession().update(NAMESPACE + "updateExpressAutoComplementManage", map);
	}
	/**
	 * 修改快递自动补码管理信息
	 */
	@Override
	public int updateExpressAutoComplementManage(
			ExpressAutoComplementManageEntity entity) {
		UserEntity user = (UserEntity)UserContext.getCurrentUser();
		String userCode = user.getEmployee().getEmpCode();
		String userName = user.getEmployee().getEmpName();
		
		entity.setModifyUserName(userName);
		entity.setModifyUser(userCode);
		entity.setCreateDate(new Date());
		return this.getSqlSession().update(NAMESPACE + "updateExpressAutoComplementManageNotes", entity);
	}



}
