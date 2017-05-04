package com.deppon.foss.module.transfer.partialline.server.dao.impl;

import java.util.Date;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.transfer.partialline.api.server.dao.ISalesDeptDeliveryProcDao;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.SalesdeptDeliveryEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class SalesdeptDeliveryProcDao extends SqlSessionDaoSupport implements
		ISalesDeptDeliveryProcDao {

	private static final String NAMESPACE = "foss.partialline.salesdeptDeliveryProc.";

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return 0; 
	}

	@Override
	public int insert(SalesdeptDeliveryEntity record) {
		record.setCreatetime(new Date());
		record.setConfirmationTime(new Date());
		record.setCreateuser(FossUserContext.getCurrentInfo().getEmpCode()); 
		record.setOrgCode(FossUserContext.getCurrentDeptCode());
		record.setActive(FossConstants.ACTIVE);
		return 0;
	}

	/**
	 * 交货确认新增
	* <p>Title: insertSelective</p> 
	* <p>Description: </p> 
	* @param record
	* @return 
	* @see com.deppon.foss.module.base.querying.server.dao.ISalesDeptDeliveryProcDao#insertSelective(com.deppon.foss.module.base.querying.shared.domain.SalesdeptDeliveryEntity)
	 */
	@Override
	public int insertSelective(SalesdeptDeliveryEntity record) {
		record.setId(UUIDUtils.getUUID());
		record.setActive(FossConstants.ACTIVE);
		record.setCreatetime(new Date());
		record.setConfirmationTime(new Date());
		record.setCreateuser(FossUserContext.getCurrentInfo().getEmpCode());
		record.setOrgCode(FossUserContext.getCurrentDeptCode());
		record.setModifytime(new Date(NumberConstants.ENDTIME));
		return this.getSqlSession().insert(NAMESPACE+"insert", record);
	}

	@Override
	public SalesdeptDeliveryEntity selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据ID为条件进行修改
	 */
	@Override
	public int updateByPrimaryKeySelective(SalesdeptDeliveryEntity record) {
		 
		return this.getSqlSession().update(NAMESPACE+"updateByPrimaryKeySelective", record);
	}

	/**
	 * 删除后再新增
	* <p>Title: updateByPrimaryKey</p> 
	* <p>Description: </p> 
	* @param record
	* @return 
	* @see com.deppon.foss.module.base.querying.server.dao.ISalesDeptDeliveryProcDao#updateByPrimaryKey(com.deppon.foss.module.base.querying.shared.domain.SalesdeptDeliveryEntity)
	 */
	@Override
	public int updateByPrimaryKey(SalesdeptDeliveryEntity record) {
		this.deleteByWayBillNo(record);
		record.setId(UUIDUtils.getUUID());
		record.setActive(FossConstants.ACTIVE);
		record.setCreatetime(new Date());
		record.setConfirmationTime(new Date());
		record.setCreateuser(FossUserContext.getCurrentInfo().getEmpCode());
		record.setOrgCode(FossUserContext.getCurrentDeptCode());
		record.setModifytime(new Date(NumberConstants.ENDTIME));
		return this.getSqlSession().insert(NAMESPACE+"insert", record);
	}

	/**
	 * 根据运单号查询
	 * @param deleteEntity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SalesdeptDeliveryEntity> salesDeptDeliveryQuery(
			SalesdeptDeliveryEntity record) {
		return this.getSqlSession().selectList(
				NAMESPACE + "salesDeptDeliveryQuery", record);
	}
	/**
	 * 根据id或者运单号删除
	 * @param deleteEntity
	 * @return
	 */
	@Override
	public Long deleteByNoOrId(SalesdeptDeliveryEntity deleteEntity){
		deleteEntity.setActive(FossConstants.INACTIVE);
		deleteEntity.setModifytime(new Date());
		deleteEntity.setModifyuser(FossUserContext.getCurrentInfo().getEmpCode());
		return (long) this.getSqlSession().update(NAMESPACE+"deleteByNoOrId",deleteEntity);
	}
	 
	/**
	 * 批量插入
	 * @param deleteEntity
	 * @return
	 */
	@Override
	public void salesdeptDeProcInsert(SalesdeptDeliveryEntity entity){
		this.getSqlSession().insert(NAMESPACE+"insert", entity);
	}
	/**
	* <p>Title: deleteByWayBillNo</p> 
	* <p>Description: </p> 
	* @param revord 
	* @see com.deppon.foss.module.base.querying.server.dao.ISalesDeptDeliveryProcDao#deleteByWayBillNo(com.deppon.foss.module.base.querying.shared.domain.SalesdeptDeliveryEntity)
	 */
	@Override
	public void deleteByWayBillNo(SalesdeptDeliveryEntity revord){
		SalesdeptDeliveryEntity entity=new SalesdeptDeliveryEntity();
		entity.setActive(FossConstants.INACTIVE);
		entity.setModifytime(new Date());
		entity.setModifyuser(FossUserContext.getCurrentInfo().getEmpCode());
		entity.setWayBillNo(revord.getWayBillNo());
		this.getSqlSession().update(NAMESPACE+"deleteByWayBillNo", entity);
	}
}
