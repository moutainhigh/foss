package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IAgentDeliveryFeeSchemeDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IAgentDeliveryFeeSchemeService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.AgentDeliveryFeeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.AgentDeliveryFeeSchemeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.AgentDeliveryFeeSchemeException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;
/**
 * @author 092020-lipengfei
 * 偏线代理送货费方案service
 */
public class AgentDeliveryFeeSchemeService implements IAgentDeliveryFeeSchemeService {
	/**
	 * 偏线代理送货费方案dao
	 */
	private IAgentDeliveryFeeSchemeDao agentDeliveryFeeSchemeDao;
	public void setAgentDeliveryFeeSchemeDao(
			IAgentDeliveryFeeSchemeDao agentDeliveryFeeSchemeDao) {
		this.agentDeliveryFeeSchemeDao = agentDeliveryFeeSchemeDao;
	}
	
	@Inject
	private IEmployeeService employeeService;
	/**
	 * 设置 职员service.
	 *
	 * @param employeeService the new 职员service
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据参数查询偏线代理送货费方案
	 * @param entity
	 * @return String
	 */
	@Override
	public List<AgentDeliveryFeeSchemeEntity> queryAgentDeliveryFeeSchemeByParams(
			AgentDeliveryFeeSchemeEntity entity,int limit, int start) { 
		List<AgentDeliveryFeeSchemeEntity> entityList=new ArrayList<AgentDeliveryFeeSchemeEntity>();
		if(null==entity){
			entity=new AgentDeliveryFeeSchemeEntity();
		}
		entityList=agentDeliveryFeeSchemeDao.queryAgentDeliveryFeeSchemeByParams(entity, limit, start);
		
		
		if(CollectionUtils.isNotEmpty(entityList)){
			for(AgentDeliveryFeeSchemeEntity agentDeliveryFeeSchemeEntity:entityList){
				if(StringUtil.isNotBlank(agentDeliveryFeeSchemeEntity.getModifyUser())){
					EmployeeEntity employeeEntity= employeeService.queryEmployeeByEmpCode(agentDeliveryFeeSchemeEntity.getModifyUser());//查询修改人信息
					if(employeeEntity!=null){
						//设置修改人姓名
						agentDeliveryFeeSchemeEntity.setModifyUserName(employeeEntity.getEmpName());
					}
				}
			}
		}
		
		
		return entityList;
	}
	/**
	 * @author 092020-lipengfei
	 * @description 查询偏线代理送货费方案总量
	 * @param entity
	 * @return Long
	 */
	@Override
	public Long queryRecordCount(AgentDeliveryFeeSchemeEntity entity) {
		Long count=null;
		if(null!=entity){
			count=agentDeliveryFeeSchemeDao.queryRecordCount(entity);
		}
		return count;
	}
	/**
	 * @author 092020-lipengfei
	 * @description 新增偏线代理送货费方案
	 * @param entity
	 * @return Integer
	 */
	@Transactional
	@Override
	public int addAgentDeliveryFeeScheme(AgentDeliveryFeeSchemeEntity entity) {
		if(null==entity){
			return FossConstants.FAILURE;
		}else{
			/*
			 * 若该方案中无按照重量新增或按照体积新增的记录时，点击“提交”按钮时，系统提示“请维护送货费基础数据”，且无法提交该方案；
			 * */
			if(null==entity.getFeeEntityList()||entity.getFeeEntityList().size()==0){
				throw new AgentDeliveryFeeSchemeException("请维护送货费基础数据");
			}
			List<AgentDeliveryFeeEntity> aentityList =entity.getFeeEntityList();
			List<AgentDeliveryFeeEntity> volumeAentityList= new ArrayList<AgentDeliveryFeeEntity>();
			List<AgentDeliveryFeeEntity> weightAentityList= new ArrayList<AgentDeliveryFeeEntity>();
			if(aentityList.size()>1){
				for(AgentDeliveryFeeEntity aentity:aentityList){
					String  type =aentity.getPricingManner();
					if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(type)){
						weightAentityList.add(aentity);
					}
					if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(type)){
						volumeAentityList.add(aentity);
					}
					
				}
				for(AgentDeliveryFeeEntity volumeAentity:volumeAentityList){
					BigDecimal startPoint=volumeAentity.getStartPoint();
					BigDecimal endPoint=volumeAentity.getTerminalPoint();
					if(startPoint.compareTo(endPoint)>=0){
						throw new AgentDeliveryFeeSchemeException("起点不能大于等于终点");
					}
					for(AgentDeliveryFeeEntity newAentity:volumeAentityList){
						BigDecimal newStartPoint=newAentity.getStartPoint();
						BigDecimal newSndPoint=newAentity.getTerminalPoint();
						if((startPoint.compareTo(newStartPoint)>0 && startPoint.compareTo(newSndPoint)<0)
							||
							(endPoint.compareTo(newStartPoint)>0 && endPoint.compareTo(newSndPoint)<0)
							){
							throw new AgentDeliveryFeeSchemeException("基础数据有重复");
						}
					}
				}
				for(AgentDeliveryFeeEntity weightAentity:weightAentityList){
					BigDecimal startPoint=weightAentity.getStartPoint();
					BigDecimal endPoint=weightAentity.getTerminalPoint();
					if(startPoint.compareTo(endPoint)>=0){
						throw new AgentDeliveryFeeSchemeException("起点不能大于等于终点");
					}
					for(AgentDeliveryFeeEntity newAentity:weightAentityList){
						BigDecimal newStartPoint=newAentity.getStartPoint();
						BigDecimal newSndPoint=newAentity.getTerminalPoint();
						if((startPoint.compareTo(newStartPoint)>0 && startPoint.compareTo(newSndPoint)<0)
							||
							(endPoint.compareTo(newStartPoint)>0 && endPoint.compareTo(newSndPoint)<0)
							){
							throw new AgentDeliveryFeeSchemeException("基础数据有重复");
						}
					}
				}
			}
			/*
			 * 同一目的站只能建一条送货费方案，若某一目的站已经存在送货费方案，
			 * 当再次新增之后点击提交按钮时，系统提示“该目的站已经存在送货费方案，不允许再次新增”，无法继续完成提交操作；
			 */
			int countScheme=agentDeliveryFeeSchemeDao.countSchemeByagentDeptCode(entity.getAgentDeptCode());
			//wutao 修改 DEFECT-4086	
			AgentDeliveryFeeSchemeEntity entityTemp =  agentDeliveryFeeSchemeDao.queryAgentDeliveryFeeSchemeByDeptCode(entity.getAgentDeptCode());
			if(countScheme>0){
				throw new AgentDeliveryFeeSchemeException("目的站【"+entityTemp.getAgentDeptName()+"】已经存在送货费方案，不允许再次新增");
			}
			AgentDeliveryFeeSchemeEntity oldEntity=agentDeliveryFeeSchemeDao.queryAgentDeliveryFeeSchemeByName(entity.getSchemeName());
			if(null!=oldEntity){
				throw new AgentDeliveryFeeSchemeException("方案名称【"+entity.getSchemeName()+"】已存在，不允许重复！");
			}else{
				entity.setId(UUIDUtils.getUUID());
				entity.setVersionNo(System.currentTimeMillis());
				Date createDate=new Date();
				
				
				UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
				// 当前登录用户empcode
				String userCode = user.getEmployee().getEmpCode();
				//修改人、修改时间为展示内容，新增时，修改人即新增人
				entity.setCreateDate(createDate);
				entity.setCreateUser(userCode);
				entity.setModifyDate(createDate);
				entity.setModifyUser(userCode);
				//新增方案
				int result=agentDeliveryFeeSchemeDao.addAgentDeliveryFeeScheme(entity);
				if(result>0){
					List<AgentDeliveryFeeEntity> feeEntityList=entity.getFeeEntityList();
					if(null!=feeEntityList && feeEntityList.size()>0){
						for(AgentDeliveryFeeEntity feeEntity:feeEntityList){
							feeEntity.setSchemeId(entity.getId());
							feeEntity.setVersionNo(entity.getVersionNo());
						}
						agentDeliveryFeeSchemeDao.addAgentDeliveryFee(feeEntityList);//新增计费
					}
				}
				return result;
			}
		}
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据ID删除代理送货费方案
	 * @param idList
	 * @return Integer
	 */
	@Transactional
	@Override
	public int deleteAgentDeliveryFeeSchemeById(List<String> idList) {
		int result=0;
		if(null!=idList && idList.size()>0){
			result=agentDeliveryFeeSchemeDao.deleteAgentDeliveryFeeSchemeById(idList);//删除方案
			if(result>0){
				agentDeliveryFeeSchemeDao.deleteAgentDeliveryFeeByschemeId(idList);//删除计费
			}
		}
		return result;
	}
	/**
	 * @author 092020-lipengfei
	 * @description 更新偏线代理送货费方案
	 * @param entity
	 * @return Integer
	 */
	@Transactional
	@Override
	public int updateAgentDeliveryFeeScheme(AgentDeliveryFeeSchemeEntity entity) {
		int result=0;
		if(null==entity || StringUtil.isBlank(entity.getId())){
			return FossConstants.FAILURE;
		}else{
			/*
			 * 若该方案中无按照重量新增或按照体积新增的记录时，点击“提交”按钮时，系统提示“请维护送货费基础数据”，且无法提交该方案；
			 * */
			if(null==entity.getFeeEntityList()||entity.getFeeEntityList().size()==0){
				throw new AgentDeliveryFeeSchemeException("请维护送货费基础数据");
			}
			List<AgentDeliveryFeeEntity> aentityList =entity.getFeeEntityList();
			List<AgentDeliveryFeeEntity> volumeAentityList= new ArrayList<AgentDeliveryFeeEntity>();
			List<AgentDeliveryFeeEntity> weightAentityList= new ArrayList<AgentDeliveryFeeEntity>();
			if(aentityList.size()>1){
				for(AgentDeliveryFeeEntity aentity:aentityList){
					String  type =aentity.getPricingManner();
					if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(type)){
						weightAentityList.add(aentity);
					}
					if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(type)){
						volumeAentityList.add(aentity);
					}
					
				}
				for(AgentDeliveryFeeEntity volumeAentity:volumeAentityList){
					BigDecimal startPoint=volumeAentity.getStartPoint();
					BigDecimal endPoint=volumeAentity.getTerminalPoint();
					if(startPoint.compareTo(endPoint)>=0){
						throw new AgentDeliveryFeeSchemeException("起点不能大于等于终点");
					}
					for(AgentDeliveryFeeEntity newAentity:volumeAentityList){
						BigDecimal newStartPoint=newAentity.getStartPoint();
						BigDecimal newSndPoint=newAentity.getTerminalPoint();
						if((startPoint.compareTo(newStartPoint)>0 && startPoint.compareTo(newSndPoint)<0)
							||
							(endPoint.compareTo(newStartPoint)>0 && endPoint.compareTo(newSndPoint)<0)
							){
							throw new AgentDeliveryFeeSchemeException("基础数据有重复");
						}
					}
				}
				for(AgentDeliveryFeeEntity weightAentity:weightAentityList){
					BigDecimal startPoint=weightAentity.getStartPoint();
					BigDecimal endPoint=weightAentity.getTerminalPoint();
					if(startPoint.compareTo(endPoint)>=0){
						throw new AgentDeliveryFeeSchemeException("起点不能大于等于终点");
					}
					for(AgentDeliveryFeeEntity newAentity:weightAentityList){
						BigDecimal newStartPoint=newAentity.getStartPoint();
						BigDecimal newSndPoint=newAentity.getTerminalPoint();
						if((startPoint.compareTo(newStartPoint)>0 && startPoint.compareTo(newSndPoint)<0)
							||
							(endPoint.compareTo(newStartPoint)>0 && endPoint.compareTo(newSndPoint)<0)
							){
							throw new AgentDeliveryFeeSchemeException("基础数据有重复");
						}
					}
				}
			}
			/*
			 * 同一目的站只能建一条送货费方案，若某一目的站已经存在送货费方案，
			 * 当再次新增之后点击提交按钮时，系统提示“该目的站已经存在送货费方案，不允许再次新增”，无法继续完成提交操作；
			 */
			int countScheme=agentDeliveryFeeSchemeDao.countSchemeByagentDeptCode(entity.getAgentDeptCode());
			AgentDeliveryFeeSchemeEntity entityTemp =  agentDeliveryFeeSchemeDao.queryAgentDeliveryFeeSchemeByDeptCode(entity.getAgentDeptCode());
			if(countScheme>1){
				throw new AgentDeliveryFeeSchemeException("目的站【"+entityTemp.getAgentDeptName()+"】已经存在送货费方案，不允许再次新增");
			}
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			// 当前登录用户empcode
			String userCode = user.getEmployee().getEmpCode();
			entity.setVersionNo(System.currentTimeMillis());
			entity.setModifyDate(new Date());
			entity.setModifyUser(userCode);
			result=agentDeliveryFeeSchemeDao.updateAgentDeliveryFeeScheme(entity);//更新方案
			if(result>0){
				List<String> idList=new ArrayList<String>();
				idList.add(entity.getId());
				agentDeliveryFeeSchemeDao.deleteAgentDeliveryFeeByschemeId(idList);//删除原方案中计费
				List<AgentDeliveryFeeEntity> feeEntityList=entity.getFeeEntityList();
				if(null!=feeEntityList && feeEntityList.size()>0){
					for(AgentDeliveryFeeEntity feeEntity:feeEntityList){
						feeEntity.setSchemeId(entity.getId());
						feeEntity.setVersionNo(entity.getVersionNo());
					}
					agentDeliveryFeeSchemeDao.addAgentDeliveryFee(feeEntityList);//新增计费
				}
			}
		}
		return result;
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据ID查询偏线代理送货费方案
	 * @param schemeId
	 * @return AgentDeliveryFeeSchemeEntity
	 */
	@Override
	public AgentDeliveryFeeSchemeEntity queryAgentDeliveryFeeSchemeById(
			String schemeId) {
		AgentDeliveryFeeSchemeEntity entity=null;
		if(StringUtil.isNotBlank(schemeId)){
			entity=agentDeliveryFeeSchemeDao.queryAgentDeliveryFeeSchemeById(schemeId);
			List<AgentDeliveryFeeEntity> feeEntityList=null;
			if(entity!=null){
				feeEntityList=agentDeliveryFeeSchemeDao.queryAgentDeliveryFeeBySchemeId(schemeId);
				entity.setFeeEntityList(feeEntityList);
			}
		}
		return entity;
	}
	/**
	 * @author 092020-lipengfei
	 * @description 查询偏线代理送货费方案（产品类型，目的站，重量，体积）(提供给中转使用)
	 * @param productType
	 * @param targetOrgCode
	 * @param weight
	 * @param volumn
	 * @return Integer
	 */
	@Override
	public AgentDeliveryFeeSchemeEntity queryAgentDeliveryCharge(
			String productType, String targetOrgCode, BigDecimal weight,
			BigDecimal volumn) {
		if(StringUtil.isBlank(targetOrgCode)){
			throw new AgentDeliveryFeeSchemeException("目的站不能为空");
		}
		//根据目的站查询偏线代理送货费方案
		AgentDeliveryFeeSchemeEntity entity=agentDeliveryFeeSchemeDao.querySchemeByagentDeptCode(targetOrgCode);
		if(null==entity){
			throw new AgentDeliveryFeeSchemeException("目的站【"+targetOrgCode+"】不存在送货费方案");
		}
		//根据重量、体积、方案ID查询送货费
		List<AgentDeliveryFeeEntity> feeEntityList=agentDeliveryFeeSchemeDao.queryAgentDeliveryFeeBySchemeId(entity.getId());
		//筛选符合条件的内容
		List<AgentDeliveryFeeEntity> agenTitylist = new ArrayList<AgentDeliveryFeeEntity>();
		if(CollectionUtils.isNotEmpty(feeEntityList)){
			for(AgentDeliveryFeeEntity aentity:feeEntityList){
				BigDecimal startPoint = aentity.getStartPoint();
				BigDecimal endPoint= aentity.getTerminalPoint();
				if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(aentity.getPricingManner())){
					if(volumn.compareTo(startPoint)>=0 && volumn.compareTo(endPoint)<0){
						agenTitylist.add(aentity);
					}
				}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(aentity.getPricingManner())){
					if(weight.compareTo(startPoint)>=0 && weight.compareTo(endPoint)<0){
						agenTitylist.add(aentity);
					}
				}
			}
		}
		entity.setFeeEntityList(agenTitylist);
		return entity;
	}

}
