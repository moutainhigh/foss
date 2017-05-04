package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IHandoverBillDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IHandoverBillInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.HandoverBillInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.HandoverBillInfoException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

public class HandoverBillInfoService implements IHandoverBillInfoService{
	private IHandoverBillDao handoverBillDao;
	/**
	 * 营业部 Service接口
	 */
	//private ISaleDepartmentService saleDepartmentService;
	/**
	 * 部门 复杂查询 service
	 */
	//private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 组织信息 Service
	 */
	//private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 
	 * <p>新增交单管理</p> 
	 * @author 189284 
	 * @date 2015-4-16 下午1:54:49
	 * @param handoverBillInfo
	 * @param empCode  
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IHandoverBillInfoService#addHandoverBillInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.HandoverBillInfoEntity, java.lang.String)
	 */
	@Override
	@Transactional
	public void addHandoverBillInfo(HandoverBillInfoEntity handoverBillInfo,
			String empCode) {
		List<HandoverBillInfoEntity> handoverBillInfoList=handoverBillDao.qurey(handoverBillInfo,null, 0, NumberConstants.NUMBER_10);
		if(handoverBillInfoList!=null&&handoverBillInfoList.size()>0){
			throw new HandoverBillInfoException("已经存在"+handoverBillInfoList.get(0).getDepartmentName()+"的交单管理信息");
		}		
		handoverBillInfo.setCreateDepartment(getEmpDepartment().getCode());
		handoverBillDao.addHandoverBill(handoverBillInfo, empCode);		
	}
	/**
	 * 
	 * <p>根据 条件 分页查询 交单信息</p> 
	 * @author 189284 
	 * @date 2015-4-17 上午10:01:31
	 * @param handoverBillInfo
	 * @param start
	 * @param limit
	 * @return 
	 * 交单时间配置权限，分给营业部、驻地派送部、车队调度、专业管理组（接送货规划管理组）
                （1）当前登陆部门为营业部属性时，查询的数据结果，为本部门操作（录入）的数据信息；
                （2）当前登陆部门为车队属性时，查询的数据结果，为本部门所属顶级车队【及其下属子部门】的新增的所有数据信息；
                （3）当前登陆部门为驻地派送部时，查询的数据结果，为本部门操作（录入）的或派送部所属驻地外场的所属顶级车队及其子部门录入的数据信息；
                （4）当前登陆部门，不属于如上业务属性时，系统查询显示，其本部门的数据权限中配置部门的数据信息；

	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IHandoverBillInfoService#queryHandoverBillInfoList(com.deppon.foss.module.base.baseinfo.api.shared.domain.HandoverBillInfoEntity, int, int)
	 */
	@Override
	public List<HandoverBillInfoEntity> queryHandoverBillInfoList(
			HandoverBillInfoEntity handoverBillInfo, int start, int limit) {
//		  handoverBillInfo.setCreateDepartment(getEmpDepartment().getCode());
//		 /**
//		   *  用户所拥有的部门信息ID集合
//		   */
//		   Set<String> orgids=FossUserContext.getCurrentUser().getOrgids();
//		  
//		   /**
//		    * OrgAdministrativeInfoEntity 当前登陆员工的部门对象
//		    */
//			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=getEmpDepartment();
//			/**
//			 * 根据部门编码查询 营业部信息
//			 */
//			SaleDepartmentEntity saleDepartmentEntity= saleDepartmentService.querySaleDepartmentByCode(orgAdministrativeInfoEntity.getCode());
//		   /**
//		    * （1）当前登陆部门为营业部属性时，查询的数据结果，为本部门操作（录入）的数据信息；
//		    */
//		   if(orgAdministrativeInfoEntity.checkSaleDepartment()){
//			   orgids.clear();
//			   orgids.add(orgAdministrativeInfoEntity.getCode());
//		   }
//		   /**
//		    * 当前登陆部门为车队属性时，查询的数据结果，为本部门所属顶级车队【及其下属子部门】的新增的所有数据信息；
//		    */
//		   else if(StringUtil.equals(FossConstants.ACTIVE, orgAdministrativeInfoEntity.getTransDepartment())){
//			   orgids.clear();
//			   /**
//			    * 获取顶级车队信息
//			    */
//			   orgAdministrativeInfoEntity=orgAdministrativeInfoComplexService.getTopFleetByCode(orgAdministrativeInfoEntity.getCode());
//			   /**
//			    * 根据部门编码获取所属及下属部门信息
//                * 此部门及下属的所有部门。
//			    */
//			   List<OrgAdministrativeInfoEntity>orgAdministrativeInfoEntitys= orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(orgAdministrativeInfoEntity.getCode());
//			   for (OrgAdministrativeInfoEntity orgAdministrativeInfoEntity2 : orgAdministrativeInfoEntitys) {
//				   orgids.add(orgAdministrativeInfoEntity2.getCode());
//			    }
//		   }
//		   /**
//		    * 当前登陆部门为驻地派送部时，查询的数据结果，为本部门操作（录入）的或派送部所属驻地外场的所属顶级车队及其子部门录入的数据信息；
//		    */
//		   else if(StringUtil.equals(FossConstants.ACTIVE, saleDepartmentEntity.getStation())){
//			   orgids.clear();
//			   /**
//			    * 派送部所属驻地外场的所属 组织信息
//			    */
//				OrgAdministrativeInfoEntity orgAdministrativeInfoEntityTmp=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(saleDepartmentEntity.getTransferCenter());
//			   
//			   /**
//			    * 获取派送部所属驻地外场的所属顶级车队  
//			    */
//			   orgAdministrativeInfoEntity=orgAdministrativeInfoComplexService.getTopFleetByCode(orgAdministrativeInfoEntityTmp.getCode());
//			   /**
//			    * 根据部门编码获取所属及下属部门信息
//                * 此部门及下属的所有部门。
//			    */
//			   List<OrgAdministrativeInfoEntity>orgAdministrativeInfoEntitys= orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(orgAdministrativeInfoEntity.getCode());
//			   for (OrgAdministrativeInfoEntity orgAdministrativeInfoEntity2 : orgAdministrativeInfoEntitys) {
//				   orgids.add(orgAdministrativeInfoEntity2.getCode());
//			    }
//		   }
		   
		return handoverBillDao.qurey(handoverBillInfo,null, start, limit);
	}
	/**
	 * 
	 * <p>根据条件  查询交单信息总数</p> 
	 * @author 189284 
	 * @date 2015-4-17 上午10:02:12
	 * @param handoverBillInfo
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IHandoverBillInfoService#queryHandoverBillInfoCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.HandoverBillInfoEntity)
	 */
	@Override
	public Long queryHandoverBillInfoCount(
			HandoverBillInfoEntity handoverBillInfo) {
//		handoverBillInfo.setCreateDepartment(getEmpDepartment().getCode());
//		 /**
//		   *  用户所拥有的部门信息ID集合
//		   */
//		   Set<String> orgids=FossUserContext.getCurrentUser().getOrgids();
//		  
//		   /**
//		    * OrgAdministrativeInfoEntity 当前登陆员工的部门对象
//		    */
//			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=getEmpDepartment();
//			/**
//			 * 根据部门编码查询 营业部信息
//			 */
//			SaleDepartmentEntity saleDepartmentEntity= saleDepartmentService.querySaleDepartmentByCode(orgAdministrativeInfoEntity.getCode());
//		   /**
//		    * （1）当前登陆部门为营业部属性时，查询的数据结果，为本部门操作（录入）的数据信息；
//		    */
//		   if(orgAdministrativeInfoEntity.checkSaleDepartment()){
//			   orgids.clear();
//			   orgids.add(orgAdministrativeInfoEntity.getCode());
//		   }
//		   /**
//		    * 当前登陆部门为车队属性时，查询的数据结果，为本部门所属顶级车队【及其下属子部门】的新增的所有数据信息；
//		    */
//		   else if(StringUtil.equals(FossConstants.ACTIVE, orgAdministrativeInfoEntity.getTransDepartment())){
//			   orgids.clear();
//			   /**
//			    * 获取顶级车队信息
//			    */
//			   orgAdministrativeInfoEntity=orgAdministrativeInfoComplexService.getTopFleetByCode(orgAdministrativeInfoEntity.getCode());
//			   /**
//			    * 根据部门编码获取所属及下属部门信息
//              * 此部门及下属的所有部门。
//			    */
//			   List<OrgAdministrativeInfoEntity>orgAdministrativeInfoEntitys= orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(orgAdministrativeInfoEntity.getCode());
//			   for (OrgAdministrativeInfoEntity orgAdministrativeInfoEntity2 : orgAdministrativeInfoEntitys) {
//				   orgids.add(orgAdministrativeInfoEntity2.getCode());
//			    }
//		   }
//		   /**
//		    * 当前登陆部门为驻地派送部时，查询的数据结果，为本部门操作（录入）的或派送部所属驻地外场的所属顶级车队及其子部门录入的数据信息；
//		    */
//		   else if(StringUtil.equals(FossConstants.ACTIVE, saleDepartmentEntity.getStation())){
//			   orgids.clear();
//			   /**
//			    * 派送部所属驻地外场的所属 组织信息
//			    */
//				OrgAdministrativeInfoEntity orgAdministrativeInfoEntityTmp=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(saleDepartmentEntity.getTransferCenter());
//			   
//			   /**
//			    * 获取派送部所属驻地外场的所属顶级车队  
//			    */
//			   orgAdministrativeInfoEntity=orgAdministrativeInfoComplexService.getTopFleetByCode(orgAdministrativeInfoEntityTmp.getCode());
//			   /**
//			    * 根据部门编码获取所属及下属部门信息
//              * 此部门及下属的所有部门。
//			    */
//			   List<OrgAdministrativeInfoEntity>orgAdministrativeInfoEntitys= orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(orgAdministrativeInfoEntity.getCode());
//			   for (OrgAdministrativeInfoEntity orgAdministrativeInfoEntity2 : orgAdministrativeInfoEntitys) {
//				   orgids.add(orgAdministrativeInfoEntity2.getCode());
//			    }
//		   }
		return  handoverBillDao.quryCount(handoverBillInfo,null);
	}
	/**
	 * 
	 * <p>修改交单信息</p> 
	 * @author 189284 
	 * @date 2015-4-17 上午10:03:29
	 * @param handoverBillInfo
	 * @param empCode 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IHandoverBillInfoService#updateHandoverBillInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.HandoverBillInfoEntity, java.lang.String)
	 */
	@Override
	@Transactional
	public void updateHandoverBillInfo(HandoverBillInfoEntity handoverBillInfo,
			String empCode) {
		
		  if(StringUtil.isNotEmpty(handoverBillInfo.getId())){
			  List<HandoverBillInfoEntity> handoverBillInfoList=handoverBillDao.qurey(handoverBillInfo,null, 0, NumberConstants.NUMBER_10);
				if(handoverBillInfoList!=null&&handoverBillInfoList.size()>0){
					throw new HandoverBillInfoException("已经存在"+handoverBillInfoList.get(0).getDepartmentName()+"的交单管理信息");
				}
			  handoverBillDao.updateHandoverBillInfo(handoverBillInfo,FossUserContext.getCurrentInfo().getEmpCode());
			  handoverBillInfo.setCreateDepartment(getEmpDepartment().getCode());
			  handoverBillDao.addHandoverBill(handoverBillInfo, empCode);
		  }
	}

	/**
	 * 
	 * <p>根据ids  批量作废交单信息</p> 
	 * @author 189284 
	 * @date 2015-4-17 上午10:03:54
	 * @param ids
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IHandoverBillInfoService#deleteHandoverBillInfo(java.util.List, java.lang.String)
	 */
	@Override
	public void deleteHandoverBillInfo(List<String> ids) {
		 if(ids!=null&&ids.size()>0){
			 handoverBillDao.deleteHandoverBillInfo(ids);
		 }
	}
	/**
	 * 
	 * <p>根据部门编码 查询交单信息</p> 
	 * @author 189284 
	 * @date 2015-5-21 上午10:31:25
	 * @param Department 交单部门code
	 * @return
	 * @see
	 */
	public  HandoverBillInfoEntity queryHandoverBillInfoByDepartment(String department ){
		return handoverBillDao.queryHandoverBillInfoByDepartment( department );
	}
	/**
	 * @param handoverBillDao the handoverBillDao to set
	 */
	public void setHandoverBillDao(IHandoverBillDao handoverBillDao) {
		this.handoverBillDao = handoverBillDao;
	}
	/**
	 * @param saleDepartmentService the saleDepartmentService to set
	 */
	/*public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}*/
	/**
	 * 
	 * <p>获取登录员工的  组织信息</p> 
	 * @author 189284 
	 * @date 2015-4-18 上午10:28:31
	 * @return
	 * @see
	 */
   private OrgAdministrativeInfoEntity getEmpDepartment(){
	  return FossUserContext.getCurrentUser().getEmployee().getDepartment();
   }
	/**
	 * 获取 部门 复杂查询 service
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 */
	/*public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}*/
	/**
	 * 获取 组织信息 service
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	/*public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}*/
   
}
