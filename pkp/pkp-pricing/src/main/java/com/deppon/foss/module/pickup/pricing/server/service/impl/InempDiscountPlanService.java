package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IInempDiscountPlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IInempDiscountPlanService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.InempDiscountPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.InempDiscountPlanException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;
/**
 * 内部员工折扣方案service实现
 * foss-dp-dongjialing
 * @author 225131
 *
 */
public class InempDiscountPlanService implements IInempDiscountPlanService {
	/**
	 * 内部员工折扣方案主信息Dao
	 */
	@Inject 
	private IInempDiscountPlanDao inempDiscountPlanDao;
	
	/**
	 * 员工信息Service
	 */
	@Inject
	IEmployeeService employeeService;

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	public void setInempDiscountPlanDao(IInempDiscountPlanDao inempDiscountPlanDao) {
		this.inempDiscountPlanDao = inempDiscountPlanDao;
	}
	/**
	 * 新增内部员工折扣方案
	 * dp-foss-dongjialing
	 * 225131
	 */
	@Override
	@Transactional
	public void insertSelective(InempDiscountPlanEntity entity) {
		packagingInempDiscountPlan(entity);
		inempDiscountPlanDao.insertSelective(entity);
	}
	/**
	 * 删除内部员工折扣方案
	 * dp-foss-dongjialing
	 * 225131
	 */
	@Override
	public int deleteByIds(List<String> inempDiscountPlanIds) {
		if(CollectionUtils.isEmpty(inempDiscountPlanIds)){
			throw new InempDiscountPlanException("请确认是否已经选择需要删除的快递折扣方案！", null);
		}
		
		return this.inempDiscountPlanDao.deleteByIds(inempDiscountPlanIds);
		
	}
	/**
	 * 修改内部员工折扣方案
	 * dp-foss-dongjialing
	 * 225131
	 */
	@Override
	@Transactional
	public int updateByIdSelective(InempDiscountPlanEntity entity) {
		Date updateDate = new Date();
		//获取当前登录人信息
		UserEntity userEntity=(UserEntity) UserContext.getCurrentUser();
		String userCode = userEntity.getEmployee().getEmpCode();
		String deptCode = userEntity.getEmployee().getDepartment().getCode();
		//修改主方案信息
		entity.setModifyTime(updateDate);
		entity.setModifyUserCode(userCode);
		entity.setModifyOrgCode(deptCode);
		return inempDiscountPlanDao.updateByIdSelective(entity);
	}
	/**
	 * 查询内部员工折扣方案
	 * dp-foss-dongjialing
	 * 225131
	 */
	@Override
	public List<InempDiscountPlanEntity> queryInempDiscountPlanList(
			InempDiscountPlanEntity entity, int start, int limit) {
		      //激活处理查询条件
				if(PricingConstants.ALL.equals(entity.getActive())){
					entity.setActive(null);
				}
				List<InempDiscountPlanEntity> inempDiscountEntityList = this.inempDiscountPlanDao.queryInempDiscountPlanList(entity, start, limit);
				//处理封装查询出的数据
				if(CollectionUtils.isNotEmpty(inempDiscountEntityList)){
					for(InempDiscountPlanEntity inempDiscountEntity:inempDiscountEntityList){
						
						EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(inempDiscountEntity.getModifyUserCode());
						if(null!=employee){
							inempDiscountEntity.setModifyUserName(employee.getEmpName());
						}
					}
				}
				return inempDiscountEntityList;
	}
	/**
	 * 查询内部员工折扣方案数量
	 * dp-foss-dongjialing
	 * 225131
	 */
	@Override
	public Long queryInempDiscountPlanListCount(InempDiscountPlanEntity entity) {
		return this.inempDiscountPlanDao.queryInempDiscountPlanListCount(entity);
	}
	/**
	 * 激活内部员工折扣方案
	 * dp-foss-dongjialing
	 * 225131
	 */
	@Override
	@Transactional
	public void activeInempDiscountPlan(InempDiscountPlanEntity entity) {
		if(null==entity){
			throw new InempDiscountPlanException("请选择需要激活的方案！",null);
		}
		Date beginTime = entity.getBeginTime();
		InempDiscountPlanEntity queryEntity = new InempDiscountPlanEntity();
		queryEntity.setId(entity.getId());
		//查询当前方案信息
		entity = this.inempDiscountPlanDao.queryInempDiscountPlanByCondition(queryEntity).get(0);
		//设置开始时间
		entity.setBeginTime(beginTime);
		//验证是否满足激活的条件
		activeInempPlanValidation(entity);
		Date nowDate = new Date();
		//获取当前登录人信息
		UserEntity userEntity=(UserEntity) UserContext.getCurrentUser();
		String userCode = userEntity.getEmployee().getEmpCode();
		String deptCode = userEntity.getEmployee().getDepartment().getCode();
		
		entity.setActive(FossConstants.YES);
		entity.setModifyTime(nowDate);
		entity.setModifyOrgCode(deptCode);
		entity.setModifyUserCode(userCode);
		this.inempDiscountPlanDao.updateByIdSelective(entity);
		
	}
	/**
	 * 中止内部员工折扣方案
	 * dp-foss-dongjialing
	 * 225131
	 */
	@Override
	@Transactional
	public void stopInempDiscountPlan(InempDiscountPlanEntity entity) {
		Date nowDate = new Date();
		//获取当前登录人信息
		UserEntity userEntity=(UserEntity) UserContext.getCurrentUser();
		String userCode = userEntity.getEmployee().getEmpCode();
		String deptCode = userEntity.getEmployee().getDepartment().getCode();
		
		entity.setModifyTime(nowDate);
		entity.setModifyOrgCode(deptCode);
		entity.setModifyUserCode(userCode);
		this.inempDiscountPlanDao.stopInempDiscountPlan(entity);
		
	}
	/**
	 * 根据id查询内部员工折扣方案
	 * dp-foss-dongjialing
	 * 225131
	 */
	@Override
	public InempDiscountPlanEntity queryInempDiscountPlanById(
			InempDiscountPlanEntity entity) {
		entity=this.inempDiscountPlanDao.queryInempDiscountPlanByCondition(entity).get(0);
		return entity;
	}
	/**
	 * 根据条件查询内部员工折扣方案
	 * dp-foss-dongjialing
	 * 225131
	 */
	@Override
	public List<InempDiscountPlanEntity> queryInempDiscountPanByCondition(
			InempDiscountPlanEntity entity) {
		
		return this.inempDiscountPlanDao.queryInempDiscountPlanByCondition(entity);
	}
	/**
	 * 封装折扣方案信息
	 */
	public void packagingInempDiscountPlan(InempDiscountPlanEntity entity) {
		Date nowDate = new Date();
		//获取当前登录人信息
		UserEntity userEntity=(UserEntity) UserContext.getCurrentUser();
		String userCode = userEntity.getEmployee().getEmpCode();
		String deptCode = userEntity.getEmployee().getDepartment().getCode();
		//封装折扣方案主信息
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateUserCode(userCode);
		entity.setCreateOrgCode(deptCode);
		entity.setCreateTime(nowDate);
		entity.setModifyUserCode(userCode);
		entity.setModifyOrgCode(deptCode);
		entity.setModifyTime(nowDate);
		entity.setActive(FossConstants.NO);
		//生成方案编码
		entity.setCode(createCode(nowDate));
	}
	/**
	 * 内部员工折扣方案升级
	 * dp-foss-dongjialing
	 * 225131
	 */
	@Override
	public void upgradeInempDiscountPlan(
			InempDiscountPlanEntity entity) {
		// TODO Auto-generated method stub
		
		String newInempDiscountPlanId = UUIDUtils.getUUID();
		InempDiscountPlanEntity queryEntity = new InempDiscountPlanEntity();
		queryEntity.setId(entity.getId());
		Date nowDate = new Date();
		//1.主方案
		InempDiscountPlanEntity copyInempDiscountEntity = this.inempDiscountPlanDao.queryInempDiscountPlanByCondition(queryEntity).get(0);
		copyInempDiscountEntity.setId(newInempDiscountPlanId);
		copyInempDiscountEntity.setActive(FossConstants.NO);
		UserEntity userEntity=(UserEntity) UserContext.getCurrentUser();
		String userCode = userEntity.getEmployee().getEmpCode();
		String deptCode = userEntity.getEmployee().getDepartment().getCode();
		copyInempDiscountEntity.setModifyUserCode(userCode); //修改员工编码
		copyInempDiscountEntity.setModifyOrgCode(deptCode); //修改部门编码
		copyInempDiscountEntity.setModifyDate(nowDate);
		copyInempDiscountEntity.setEndTime(new Date(NumberConstants.ENDTIME));//将结束时间设置为最大的时间
		//生成编码
		copyInempDiscountEntity.setCode(createCode(nowDate));
		this.inempDiscountPlanDao.insertSelective(copyInempDiscountEntity);
		
	}
	/**
	 * 验证激活是否满足条件
	 */
	public void activeInempPlanValidation(InempDiscountPlanEntity entity) {
		List<InempDiscountPlanEntity> inempDiscountPlanList = null;
		InempDiscountPlanEntity queryEntity = new InempDiscountPlanEntity();
		queryEntity.setActive(FossConstants.ACTIVE);
		queryEntity.setBeginTime(entity.getBeginTime());
		queryEntity.setEndTime(entity.getEndTime());
		inempDiscountPlanList=this.inempDiscountPlanDao.queryInempDiscountPlanByCondition(queryEntity);
		if(CollectionUtils.isNotEmpty(inempDiscountPlanList)) {
			throw new InempDiscountPlanException("存在与当前激活方案期间冲突的有效折扣方案,请中止后进行激活操作！",null);
		}
	}
	/**
	 * 根据时间生成方案编码
	 * @param nowDate
	 * @return
	 */
	public String createCode(Date nowDate) {
		StringBuffer code = new StringBuffer();
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = formatter.format(nowDate);
		String[] split = time.split(" ");
		for (String s : split[0].split("-")) {
			code.append(s);
		}
		for (String s : split[1].split(":")) {
			code.append(s);
		}
		return code.toString();
	}
}
