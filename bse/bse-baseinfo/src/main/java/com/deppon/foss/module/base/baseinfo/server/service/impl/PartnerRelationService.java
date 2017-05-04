package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPartnerRelationDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISaleDepartmentDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPartnerRelationService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PartnerRelationEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.PartnerRelationException;
import com.deppon.foss.module.base.baseinfo.server.service.impl.esb.SyncPartnerRelationService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.UUIDUtils;

public class PartnerRelationService implements IPartnerRelationService{
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PartnerRelationService.class);
    /**
     * 注入partnerRelationDao
     */
    private IPartnerRelationDao partnerRelationDao;
    
	public void setPartnerRelationDao(IPartnerRelationDao partnerRelationDao) {
		this.partnerRelationDao = partnerRelationDao;
	}
	/**
	 * 注入saleDepartmentDao
	 */
	private ISaleDepartmentDao saleDepartmentDao; 
	
    public void setSaleDepartmentDao(ISaleDepartmentDao saleDepartmentDao) {
		this.saleDepartmentDao = saleDepartmentDao;
	}
    
    /**
     * 注入syncPartnerRelationService类（网点映射关系同步给ptp系统）
     */
    private SyncPartnerRelationService syncPartnerRelationService;
    
    public void setSyncPartnerRelationService(
			SyncPartnerRelationService syncPartnerRelationService) {
		this.syncPartnerRelationService = syncPartnerRelationService;
	}
	/**
    * 
    * 添加 
    * @author 308861 
    * @date 2016-9-1 下午7:34:35
    * @param entity
    * @return 
    */
	@Override
	public PartnerRelationEntity addPartnerRelation(PartnerRelationEntity entity) {
		if(entity ==null){
			throw new PartnerRelationException("参数不能为空");
		}
		/*
		 * SPBP-用户需求说明书-FOSS合伙人网点映射基础资料优化V0.1-308861
		 * if(!entity.getOneSubCompanyCode().equals(entity.getTwoSubCompanyCode())){
			throw new PartnerRelationException("一级网点所属子公司与二级网点所属子公司必须一致");
		}*/
		if(entity.getOnePartnerCode()==null){
			throw new PartnerRelationException("一级网点不能为空");
		}
		if(entity.getTwoPartnerCode()==null){
			throw new PartnerRelationException("二级网点不能为空");
		}
		/**
		 * 二级网点只能对应一个一级网点
		 */
		String oneCode=partnerRelationDao.oneCodeByTwo(entity.getTwoPartnerCode());
		if(oneCode != null){
			throw new PartnerRelationException("此二级网点已经存在对应的一级网点");
		}
		//根据一级二级网点编码查询
		PartnerRelationEntity result=partnerRelationDao.queryByCode(entity);
		if(result != null){
			throw new PartnerRelationException("已经存在此网点映射关系");
		}
		//网点映射id
		entity.setId(UUIDUtils.getUUID());
		
		entity.setSubCompanyCode(entity.getOneSubCompanyCode());
		entity.setSubCompanyName(entity.getOneSubCompanyName());
		
		//一级网点标杆编码
		String ontUnified=partnerRelationDao.unifideByCode(entity.getOnePartnerCode());
		entity.setOnePartnerUnifiedCode(ontUnified);
		//二级网点标杆编码
		String twoUnified=partnerRelationDao.unifideByCode(entity.getTwoPartnerCode());
		entity.setTwoPartnerUnifiedCode(twoUnified);
		
		//创建人姓名
		entity.setCreateUser(getCurrentUser().getEmpName());
		//创建人工号
		entity.setCreateCode(getCurrentUser().getEmpCode());
		//创建时间 
		entity.setCreateDate(new Date());
		//修改人姓名
		entity.setModifyUser(getCurrentUser().getEmpName());
		//修改人工号
		entity.setModifyCode(getCurrentUser().getEmpCode());
		//修改时间  默认为2999
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		//有效
		entity.setActive("Y");
		int resultNum=partnerRelationDao.addPartnerRelation(entity);
		if(resultNum > 0){
			//1表示新增
			entity.setOperateType(1);
			List<PartnerRelationEntity> list=new ArrayList<PartnerRelationEntity>();
			list.add(entity);
			//同步网点映射关系到ptp系统
			syncPartnerRelationDataToPtp(list);
		}
		return resultNum  > 0 ? entity : null; 
	}
	/**
	 * 
	 * 修改 
	 * @author 308861 
	 * @date 2016-8-24 下午4:22:38
	 * @param entity
	 * @return 
	 */
	@Override
	public PartnerRelationEntity updatePartnerRelation(
			PartnerRelationEntity entity) {
		if(entity == null){
			throw new PartnerRelationException("参数不能为空");
		}
		/*
		 * SPBP-用户需求说明书-FOSS合伙人网点映射基础资料优化V0.1-308861
		 * if(!entity.getOneSubCompanyCode().equals(entity.getTwoSubCompanyCode())){
			throw new PartnerRelationException("一级网点所属子公司与二级网点所属子公司必须一致");
		}*/
		if(entity.getOnePartnerCode()==null){
			throw new PartnerRelationException("一级网点不能为空");
		}
		if(entity.getTwoPartnerCode()==null){
			throw new PartnerRelationException("二级网点不能为空");
		}
		/**
		 * 二级网点只能对应一个一级网点
		 */
		String oneCode=partnerRelationDao.oneCodeByTwo(entity.getTwoPartnerCode());
		if(oneCode != null){
			throw new PartnerRelationException("此二级网点已经存在对应的一级网点");
		}
		//根据一级二级网点编码查询
		PartnerRelationEntity result=partnerRelationDao.queryByCode(entity);
		if(result != null){
			throw new PartnerRelationException("已经存在此网点映射关系");
		}
		
		PartnerRelationEntity  e=partnerRelationDao.getEntityById(entity.getId());
		entity.setOnePartnerUnifiedCode(e.getOnePartnerUnifiedCode());
		entity.setTwoPartnerUnifiedCode(e.getTwoPartnerUnifiedCode());
		
		//作废人姓名
		entity.setModifyUser(getCurrentUser().getEmpName());
		//作废人工号
		entity.setModifyCode(getCurrentUser().getEmpCode());
		//作废时间
		entity.setModifyDate(new Date());
		//先作废
		int resultNum=partnerRelationDao.deletePartnerRelation(entity);
		if(resultNum > 0){
			PartnerRelationEntity newEntity=new PartnerRelationEntity();
			
			//网点映射id
			newEntity.setId(UUIDUtils.getUUID());
			
			newEntity.setOnePartnerCode(entity.getOnePartnerCode());
			newEntity.setOnePartnerName(entity.getOnePartnerName());
			
			newEntity.setTwoPartnerCode(entity.getTwoPartnerCode());
			newEntity.setTwoPartnerName(entity.getTwoPartnerName());
			
			newEntity.setSubCompanyCode(entity.getOneSubCompanyCode());
			newEntity.setSubCompanyName(entity.getOneSubCompanyName());
			/**
			 * 保存二级网点所属子公司编码和名称
			 */
			newEntity.setTwoSubCompanyCode(entity.getTwoSubCompanyCode());
			newEntity.setTwoSubCompanyName(entity.getTwoSubCompanyName());
			//一级网点标杆编码
			String ontUnified=partnerRelationDao.unifideByCode(entity.getOnePartnerCode());
			//二级网点标杆编码
			String twoUnified=partnerRelationDao.unifideByCode(entity.getTwoPartnerCode());
			newEntity.setOnePartnerUnifiedCode(ontUnified);
			newEntity.setTwoPartnerUnifiedCode(twoUnified);
			
			//创建人姓名
			newEntity.setCreateUser(getCurrentUser().getEmpName());
			//创建人工号
			newEntity.setCreateCode(getCurrentUser().getEmpCode());
			//创建时间 
			newEntity.setCreateDate(new Date());
			//修改人姓名
			newEntity.setModifyUser(getCurrentUser().getEmpName());
			//修改人工号
			newEntity.setModifyCode(getCurrentUser().getEmpCode());
			//修改时间  默认为2999
			newEntity.setModifyDate(new Date(NumberConstants.ENDTIME));
			//有效
			newEntity.setActive("Y");
			//后添加
			int num=partnerRelationDao.addPartnerRelation(newEntity);
			if(num > 0){
				entity.setOperateType(2);//先作废
				newEntity.setOperateType(1);//后新增
				List<PartnerRelationEntity> list=new ArrayList<PartnerRelationEntity>();
				list.add(entity);
				list.add(newEntity);
				//同步网点映射关系到ptp系统
				syncPartnerRelationDataToPtp(list);
			}
		}
		return entity;
	}
	
	/**
	 * 
	 * 作废  
	 * @author 308861 
	 * @date 2016-9-2 下午2:32:47
	 * @param ids 
	 */
	@Override
	public void deletePartnerRelation(
			List<String> ids) {
		if(ids == null){
			throw new PartnerRelationException("请选择需要作废的数据");
		}
		//循环id逐个作废
		for (String id : ids) {
			PartnerRelationEntity result=partnerRelationDao.getEntityById(id);
			if(result == null){
				throw new PartnerRelationException("该数据不存在，作废失败");
			}else{
				PartnerRelationEntity entity=new PartnerRelationEntity();
				entity.setOnePartnerUnifiedCode(result.getOnePartnerUnifiedCode());
				entity.setTwoPartnerUnifiedCode(result.getTwoPartnerUnifiedCode());
				//id
				entity.setId(id);
				//作废人姓名
				entity.setModifyUser(getCurrentUser().getEmpName());
				//作废人工号
				entity.setModifyCode(getCurrentUser().getEmpCode());
				//作废时间
				entity.setModifyDate(new Date());
				partnerRelationDao.deletePartnerRelation(entity);
				//2表示作废
				entity.setOperateType(2);
				List<PartnerRelationEntity> list=new ArrayList<PartnerRelationEntity>();
				list.add(entity);
				//同步网点映射关系到ptp系统
				syncPartnerRelationDataToPtp(list);
			}
		}
	}

	/**
	 * 
	 * 查询 
	 * @author 308861 
	 * @date 2016-8-24 下午4:23:03
	 * @param entity
	 * @return 
	 */
	@Override
	public List<PartnerRelationEntity> queryPartnerRelationByEntity(
			PartnerRelationEntity entity,int start,int limit) {
		return partnerRelationDao.queryPartnerRelationByEntity(entity, start, limit);
	}
	/**
	 * 
	 * 统计 
	 * @author 308861 
	 * @date 2016-8-25 上午9:31:37
	 * @param entity
	 * @return 
	 */
	@Override
	public long queryPartnerRelationByEntityCount(PartnerRelationEntity entity) {
		return partnerRelationDao.queryPartnerRelationByEntityCount(entity);
	}
	/**
	 * 
	 * 根据code查询网点所属子公司 
	 * @author 308861 
	 * @date 2016-8-25 下午4:55:14
	 * @param code
	 * @return 
	 */
	@Override
	public PartnerRelationEntity getSubCompanyNameByCode(String code) {
		return partnerRelationDao.getSubCompanyNameByCode(code);
	}
	
	 /**
	  * 
	  * 获取当前的登录用户 
	  * @author 308861 
	  * @date 2016-8-29 下午2:44:48
	  * @return
	  * @see
	  */
    private CurrentInfo getCurrentUser(){
    	CurrentInfo user = FossUserContext.getCurrentInfo();
    	return  user;
    }
	/**
	 * 
	 * 根据二级code查询一级网点信息（为结算准备）
	 * @author 308861 
	 * @date 2016-9-2 下午5:51:08
	 * @param twoCode
	 * @return 
	 */
	@Override
	public SaleDepartmentEntity oneEntityByTwoCode(String twoCode) {
		if(twoCode != null){
			String oneCode=partnerRelationDao.oneCodeByTwo(twoCode);
			if(oneCode != null){
				SaleDepartmentEntity entity=saleDepartmentDao.
						querySaleDepartmentByCode(oneCode);
				return entity;
			}
		}
		return null;
	}
	/**
	 * 
	 * 根据一级code查询二级网点信息（为结算准备）
	 * @author 308861 
	 * @date 2016-9-2 下午5:51:32
	 * @param oneCode
	 * @return 
	 */
	@Override
	public  List<SaleDepartmentEntity> twoEntiyByOne(String oneCode) {
		if(oneCode == null){
			String msg="一级网点编码不能为空";
			LOGGER.error(msg);
			throw new PartnerRelationException(msg);
		}
		List<String> codes=partnerRelationDao.twoCodeByOne(oneCode);
		if(codes == null){
			String msg1="根据一级网点编码查询没有对应的二级网点编码";
			LOGGER.error(msg1);
			throw new PartnerRelationException(msg1);
		}
		String[] twoCodes = (String[]) codes.toArray();
		List<SaleDepartmentEntity> list=saleDepartmentDao.querySaleDepartmentBatchByCode(twoCodes);
		//313353 sonar优化
//		if(list.isEmpty()){
//			
//		}
		return list;
	}
	
	/**
	 * 
	 * 同步网点映射关系信息到ptp系统 
	 * @author 308861 
	 * @date 2016-9-6 下午7:17:11
	 * @param entityList
	 * @see
	 */
	private void syncPartnerRelationDataToPtp(
			List<PartnerRelationEntity> entityList) {
		syncPartnerRelationService.syncPartnerRelationDataToPtp(entityList);
	}
}
