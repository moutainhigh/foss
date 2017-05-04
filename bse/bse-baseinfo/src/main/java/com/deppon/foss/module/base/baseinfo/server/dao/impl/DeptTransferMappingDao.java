package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IDeptTransferMappingDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DeptTransferMappingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.DeptTransferMappingException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 营业部交接映射管理-Dao实现类
 * @author 273296
 *
 */
public class DeptTransferMappingDao extends SqlSessionDaoSupport implements IDeptTransferMappingDao{
	
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.deptTransferMapping.";

	/**
	 * 根据部门code查询是否外场
	 *273296
	 * @param vo
	 * @return
	 */
	@Override
	public String findOutFieldByCode(String deptCode) {
		Object outField=this.getSqlSession().selectOne(NAMESPACE+"findOutFieldByCode",deptCode);
		return outField==null?"":outField.toString();
	}

	
	/**
	 *新增 营业部 交接映射管理
	 *273296
	 * @param vo
	 * @return
	 * @throws DeptTransferMappingException 
	 */
	@Override
	public void saveDeptTransferMappingEntity(DeptTransferMappingEntity entity) {
		this.getSqlSession().insert(NAMESPACE+"addDeptTransferMappingEntity",entity);
		
	}


	/**
	 * 查询营业部 交接映射管理记录数
	 *273296
	 * @return
	 */
	@Override
	public long queryDeptTransferMappingCount(DeptTransferMappingEntity entity) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryDeptTransferMappingCount",entity);
	}


	/**
	 * 作废营业部 交接映射管理数据
	 *273296
	 * @return
	 */
	@Override
	public long deleteDeptTransferMappingById(String id) {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		DeptTransferMappingEntity entity=new DeptTransferMappingEntity();
		entity.setId(id);
		entity.setModifyTime(format.format(new Date()));
		entity.setActive("N");
		return this.getSqlSession().update(NAMESPACE+"deleteDeptTransferMapping",entity);
	}


	/**
	 * 根据部门code,一级网点查找记录 
	 *273296
	 * @param vo
	 * @return
	 */
	@Override
	public List<DeptTransferMappingEntity> queryDeptTransferMappingListByDeptCodeAndFthNetwork(
			DeptTransferMappingEntity entity) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(NAMESPACE+"queryDeptTransferMappingListByDeptCodeAndFthNetwork",entity);
	}


	/**
	 * 根据营业部和一级网点作废记录
	 *273296
	 * @return
	 */
	@Override
	public long deleteDeptTransferMappingsByDeptCodeAndFthNetworkCode(
			DeptTransferMappingEntity entity) {
		return this.getSqlSession().update(NAMESPACE+"deleteDeptTransferMapping",entity);
	}


	/**
	 * 根据Id查询
	 *273296
	 * @param id
	 * @return
	 */
	@Override
	public DeptTransferMappingEntity findById(String id) {
		return (DeptTransferMappingEntity)this.getSqlSession().selectList(NAMESPACE+"findById",id).get(0);
	}

	/**
	 * 查询是否存在二级网点
	 *273296
	 * @return
	 */
	@Override
	public long queryDeptTransferMappingModelBySecNetworkCode(
			String secNetworkCode) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryDeptTransferMappingModelBySecNetworkCode",secNetworkCode);
	}


	/**
	 * 查询营业部 交接映射管理数据
	 *273296
	 * @return
	 */
	@Override
	public List<DeptTransferMappingEntity> queryDeptTransferMappingList(
			DeptTransferMappingEntity entity, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"queryDeptTransferMappingList",entity,rowBounds);
	}


	/**
	 * 根据 编码  查询 所有记录
	 */
	@Override
	public List<DeptTransferMappingEntity> queryDeptTransferMappingListByCode(
			String code) {
		return this.getSqlSession().selectList(NAMESPACE+"queryDeptTransferMappingListByCode",code);
	}

	/**
	 * 查询是否存在一级网点
	 *273296
	 * @return
	 */
	@Override
	public long queryDeptTransferMappingModelByFthNetworkCode(
			String fthNetworkCode) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryDeptTransferMappingModelByFthNetworkCode",fthNetworkCode);
	}

	/**
	 * 修改营业部 交接映射管理数据
	 *273296
	 * @return 
	 * @return
	 */
	@Override
	public void updateDeptTransferMapping(DeptTransferMappingEntity entity) {
		this.getSqlSession().update(NAMESPACE+"updateDeptTransferMapping",entity);
	}

	/**
	 * 根据一级网点查询数据
	 *273296
	 * @return
	 */
	@Override
	public List<DeptTransferMappingEntity> queryDeptTransferMappingByFthNetworkCode(
			String code) {
		return (List<DeptTransferMappingEntity>)this.getSqlSession().selectList(NAMESPACE+"queryDeptTransferMappingByFthNetworkCode",code);
	}

	/**
	 * 根据二级网点查询数据
	 *273296
	 * @return
	 */
	@Override
	public DeptTransferMappingEntity queryDeptTransferMappingBySecNetworkCode(
			String code) {
		List<DeptTransferMappingEntity> entitys=(List<DeptTransferMappingEntity>)this.getSqlSession().selectList(NAMESPACE+"queryDeptTransferMappingBySecNetworkCode",code);
		if(CollectionUtils.isNotEmpty(entitys)){
			return entitys.get(0);
		}
		return null;
	}

}
