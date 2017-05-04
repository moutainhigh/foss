/**
 *  initial comments.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 */
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OrgAdministrativeInfoEmployeeDto;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverBillVaryStatusService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverBillVaryStatusEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao;
import com.deppon.foss.module.transfer.load.api.server.service.IAssignLoadTaskService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.AssignLoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.AssignLoadTaskQueryConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.DeliverBillQueryConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderQueryConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.exception.AssignLoadTaskExceptionCode;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 分配装车任务服务.
 *
 *
 *
 * @author 042795-foss-duyi
 * 
 * @date 2012-10-11 下午2:29:24
 * 
 * @since
 * @version
 */
public class AssignLoadTaskService implements IAssignLoadTaskService{
	private ISaleDepartmentService saleDepartmentService;
	
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	/** 
	 * 分配装车任务dao接口. 
	 * 
	 * 
	 */
	private IAssignLoadTaskDao assignLoadTaskDao;
	/** 
	 * 部门人员 复杂查询 service. 
	 * 
	 * 
	 */
	private IOrgAdministrativeInfoEmployeeService orgAdministrativeInfoEmployeeService;
	/** 
	 * 部门 复杂查询 service. 
	 * 
	 * 
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 派送单状态更新记录表Service
	 * 
	 * 
	 */
	private IDeliverBillVaryStatusService deliverBillVaryStatusService;
	/**
	 * Sets the assign load task dao.
	 *
	 * @param assignLoadTaskDao the new assign load task dao
	 */
	public void setAssignLoadTaskDao(IAssignLoadTaskDao assignLoadTaskDao) {
		this.assignLoadTaskDao = assignLoadTaskDao;
	}
	/**
	 * Sets the org administrative info employee service.
	 *
	 * @param orgAdministrativeInfoEmployeeService the new org administrative info employee service
	 */
	public void setOrgAdministrativeInfoEmployeeService(
			IOrgAdministrativeInfoEmployeeService orgAdministrativeInfoEmployeeService) {
		this.orgAdministrativeInfoEmployeeService = orgAdministrativeInfoEmployeeService;
	}
	/**
	 * Sets the org administrative info complex service.
	 *
	 * @param orgAdministrativeInfoComplexService the new org administrative info complex service
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	/** 
	 * The org administrative info service. 
	 * 
	 * 
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * Sets the org administrative info service.
	 *
	 * @param orgAdministrativeInfoService the new org administrative info service
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/** 
	 * The employee service. 
	 * 
	 * 
	 */
	private IEmployeeService employeeService;
	/**
	 * Sets the employee service.
	 *
	 * @param employeeService the new employee service
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	/** 
	 * 数据字典接口
	 */
	private IDataDictionaryValueService dataDictionaryValueService;
	
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}
	/**
	 * 查询理货员及工作量.
	 *
	 *
	 * @param loaderQCVo the loader qc vo
	 * 
	 * @param limit the limit
	 * 
	 * @param start the start
	 * 
	 * @return the list
	 * 
	 * @author 042795-foss-duyi
	 * 
	 * @date 2012-10-11 下午3:12:33
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAssignLoadTaskService#queryLoader(com.deppon.foss.module.transfer.load.api.shared.dto.LoaderQueryConditionDto, int, int)
	 */
	@Override
	public List<LoaderEntity> queryLoader(LoaderQueryConditionDto loaderQCVo, int limit, int start) {
		List<OrgAdministrativeInfoEmployeeDto> loaderDtos = new ArrayList<OrgAdministrativeInfoEmployeeDto>();
		//查询理货员:调用综合接口
		List<LoaderEntity> loaders = new ArrayList<LoaderEntity>();
		//人员
		EmployeeEntity employee = new EmployeeEntity();
		//理货员编码
		employee.setEmpCode(loaderQCVo.getLoader());
		//理货员职位
		employee.setTitle(loaderQCVo.getTitle());
		//查询登录部门
		OrgAdministrativeInfoEntity loginOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(FossUserContext.getCurrentDeptCode());
		if(loginOrg == null){
			return null;
		}
		//如果营业部则返回营业部下面人员
		if("Y".equals(loginOrg.getSalesDepartment())){
			//loaderDtos = new ArrayList<OrgAdministrativeInfoEmployeeDto>();
			//操作部门
			employee.setOrgCode(FossUserContext.getCurrentDeptCode());
			if(StringUtils.isNotBlank(loaderQCVo.getOrg())){
				employee.setOrgCode(loaderQCVo.getOrg());
			}
			List<EmployeeEntity> emps = employeeService.queryEmployeeExactByEntity(employee,start,limit);
			if(emps != null){
				OrgAdministrativeInfoEmployeeDto loaderEmp;
				for(EmployeeEntity emp : emps){
					loaderEmp = new OrgAdministrativeInfoEmployeeDto();
					//人员编号
					loaderEmp.setEmployeeEmpCode(emp.getEmpCode());
					//人员姓名
					loaderEmp.setEmployeeEmpName(emp.getEmpName());
					//组织
					loaderEmp.setOrgAdministrativeInfoCode(emp.getOrgCode());
					if(!emp.getOrgCode().equals(FossUserContext.getCurrentDeptCode())){
						OrgAdministrativeInfoEntity loaderOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(FossUserContext.getCurrentDeptCode());
						if(loaderOrg != null){
							loaderEmp.setOrgAdministrativeInfoName(loaderOrg.getName());
						}
					}else{
						loaderEmp.setOrgAdministrativeInfoName(loginOrg.getName());
					}
					//职位数据字典
					DataDictionaryValueEntity dictEntity1 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.UUMS_POSITION_TERMSCODE, emp.getTitle());
					//职位
					if(dictEntity1 != null){
						loaderEmp.setEmployeeTitleName(dictEntity1.getValueName());
					}
					loaderDtos.add(loaderEmp);
				}
			}
		}else{
			//组织
			OrgAdministrativeInfoEntity org = new OrgAdministrativeInfoEntity();
			//理货员部门
			org.setCode(loaderQCVo.getOrg());
			//当前理货部门所属外场
			try{
				org.setArrangeOutfield(this.getCurrentOutfieldCode());
			}catch(TfrBusinessException e){
				return null;
			}
			//理货业务类型:派送装车
			List<OrgAdministrativeInfoEmployeeDto> deliverLoadLoaderDtos;
			org.setArrangeBizType(BizTypeConstants.ORG_P_ARRANGE_BIZ_TYPE_DELIVER_LOAD);
			deliverLoadLoaderDtos = orgAdministrativeInfoEmployeeService.getPorter(org, employee, start, limit);
			//理货业务类型:装卸车
			List<OrgAdministrativeInfoEmployeeDto> loadUnloadLoaderDtos;
			org.setArrangeBizType(BizTypeConstants.ORG_P_ARRANGE_BIZ_TYPE_LOAD_AND_UNLOAD);
			loadUnloadLoaderDtos = orgAdministrativeInfoEmployeeService.getPorter(org, employee, start, limit);
			if(CollectionUtils.isNotEmpty(deliverLoadLoaderDtos)){
				loaderDtos.addAll(deliverLoadLoaderDtos);
			}
			if(CollectionUtils.isNotEmpty(loadUnloadLoaderDtos)){
				loaderDtos.addAll(loadUnloadLoaderDtos);
			}
		}
		if(CollectionUtils.isEmpty(loaderDtos)){
			return null;
		}
		LoaderEntity l;
		//遍历
		for(OrgAdministrativeInfoEmployeeDto loaderDto : loaderDtos){
			l = new LoaderEntity();
			l.setId(loaderDto.getEmployeeId());
			//Sets the 理货员编号
			l.setLoaderCode(loaderDto.getEmployeeEmpCode());
			//Sets the 理货员名称
			l.setLoaderName(loaderDto.getEmployeeEmpName());
			//Sets the 部门编号
			l.setOrgCode(loaderDto.getOrgAdministrativeInfoCode());
			//Sets the 职位.
			l.setOrgName(loaderDto.getOrgAdministrativeInfoName());
			//Sets the 职位.
			l.setTitle(loaderDto.getEmployeeTitleName());
			loaders.add(l);
		}
		if(CollectionUtils.isEmpty(loaders)){
			return null;
		}
		//查询已分配、已完成派送装车任务
		loaderQCVo.setLoaders(loaders);	
		List<LoaderEntity> finishedWorkLoads= assignLoadTaskDao.queryWorkLoadByTime(loaderQCVo);
		//查询当前未完成派送装车任务
		List<LoaderEntity> unfinishWorkLoads = assignLoadTaskDao.queryUnFinishWorkLoad(loaderQCVo);
		//绑定理货员与工作量
		LoaderEntity finishedWorkLoad = new LoaderEntity();
		LoaderEntity unfinishWorkLoad = new LoaderEntity();
		List<LoaderEntity> loaderWorkLoad = new ArrayList<LoaderEntity>();
		LoaderEntity loader;
		//遍历
		for(int i=0;i<loaders.size();i++){
			loader = new LoaderEntity();
			loader = loaders.get(i);
			if(StringUtils.isNotBlank(loader.getLoaderCode())){
				for(int j=0;j<finishedWorkLoads.size();j++){
					finishedWorkLoad = finishedWorkLoads.get(j);
					if(StringUtils.isNotBlank(finishedWorkLoad.getLoaderCode())){
						if(loader.getLoaderCode().equals(finishedWorkLoad.getLoaderCode())){	
							loader.setAssignedWeight(finishedWorkLoad.getAssignedWeight());
							loader.setFinishedWeight(finishedWorkLoad.getFinishedWeight());
						}
					}else{
						return null;
						//throw new TfrBusinessException(AssignLoadTaskExceptionCode.PARAMETERERROR_MESSAGECODE);
					}
				}
				for(int j=0;j<unfinishWorkLoads.size();j++){
					unfinishWorkLoad = unfinishWorkLoads.get(j);
					if(StringUtils.isNotBlank(unfinishWorkLoad.getLoaderCode())){
						if(loader.getLoaderCode().equals(unfinishWorkLoad.getLoaderCode())){	
							//Sets the 未完成任务数.
							loader.setUnfinishedTaskQty(unfinishWorkLoad.getUnfinishedTaskQty());
							//Sets the 未完成重量.
							loader.setUnfinishedWeight(unfinishWorkLoad.getUnfinishedWeight());
						}
					}else{
						return null;
						//throw new TfrBusinessException(AssignLoadTaskExceptionCode.PARAMETERERROR_MESSAGECODE);
					}
				}
				loaderWorkLoad.add(loader);
			}else{
				return null;
				//throw new TfrBusinessException(AssignLoadTaskExceptionCode.PARAMETERERROR_MESSAGECODE);
			}	
		}
		//返回值
		return loaderWorkLoad;
	}
	/**
	 * 查询理货员总数.
	 * 
	 * 
	 *
	 * @param loaderQCVo the loader qc vo
	 * 
	 * @return the loader count
	 * 
	 * @author 042795-foss-duyi
	 * 
	 * @date 2012-10-11 下午3:12:33
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAssignLoadTaskService#getLoaderCount(com.deppon.foss.module.transfer.load.api.shared.dto.LoaderQueryConditionDto)
	 */
	@Override
	public Long getLoaderCount(LoaderQueryConditionDto loaderQCVo) {
		OrgAdministrativeInfoEntity org = new OrgAdministrativeInfoEntity();
		EmployeeEntity employee = new EmployeeEntity();
		//理货员编码
		employee.setEmpCode(loaderQCVo.getLoader());
		//理货员职位
		employee.setTitle(loaderQCVo.getTitle());
		//理货员部门
		OrgAdministrativeInfoEntity loginOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(FossUserContext.getCurrentDeptCode());
		if(loginOrg == null){
				return NumberUtils.LONG_ZERO;
		}
		//如果营业部则返回营业部下面人员
		if("Y".equals(loginOrg.getSalesDepartment())){
			employee.setOrgCode(FossUserContext.getCurrentDeptCode());
			if(StringUtils.isNotBlank(loaderQCVo.getOrg())){
				employee.setOrgCode(loaderQCVo.getOrg());
			}
			//精确查询-查询总条数，用于分页 动态的查询条件。 
			//如果传入的对象为空，传入一个对象，可查出所有的数据，
			//如果传入的对象的属性不为空或者空白，则设置为查询条件
			return employeeService.queryEmployeeExactByEntityCount(employee);
		}else{
			org.setCode(loaderQCVo.getOrg());
			//当前理货部门所属外场
			try{
				org.setArrangeOutfield(this.getCurrentOutfieldCode());
			}catch(Exception e){
				return NumberUtils.LONG_ZERO;
			}
			//理货业务类型
			org.setArrangeBizType(BizTypeConstants.ORG_P_ARRANGE_BIZ_TYPE_DELIVER_LOAD);
			long count = orgAdministrativeInfoEmployeeService.getPorterCount(org, employee);
			//根据 理货部门所服务的外场，理货部门类型（装车/卸车/派送装车），部门的编号， 理货员职位，
			//理货员编号
			//返回： 理货员直属部门编码，理货员直属部门名称，理货员编码，理货员名称，理货员职位 动态条件，
			//精确查询 理货员信息 支持分页 queryOrgAdministrativeInfoEmployeeDto
			org.setArrangeBizType(BizTypeConstants.ORG_P_ARRANGE_BIZ_TYPE_LOAD_AND_UNLOAD);
			count = count + orgAdministrativeInfoEmployeeService.getPorterCount(org, employee);
			return count;
		}
	}
	/**
	 * 刷新.
	 * 
	 *
	 * @param limit the limit
	 * 
	 * @param start the start
	 * 
	 * @return the list
	 * 
	 * @author 042795-foss-duyi
	 * 
	 * @date 2012-10-11 下午3:12:33
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAssignLoadTaskService#queryAssignedLoadTask(int, int)
	 */
	@Override
	public List<AssignLoadTaskEntity> queryUnstartAssignLoadTask(int limit, int start) {
		AssignLoadTaskQueryConditionDto task = new AssignLoadTaskQueryConditionDto();
		String loginOrgCode =  this.getBigDeptCode(FossUserContext.getCurrentDeptCode());
		task.setLoginOrgCode(loginOrgCode);
		//已分配派送装车任务状态-未开始
		task.setTaskState(LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_UNSTART);
		List<AssignLoadTaskEntity> assignTasks = assignLoadTaskDao.queryUnstartAssignLoadTask(task,limit, start);
		if(CollectionUtils.isEmpty(assignTasks)){
			//拋异常***没有查看到数据
			//throw new TfrBusinessException(AssignLoadTaskExceptionCode.DATA_NOTFIND_MESSAGECODE);
			return null;
		}
		return assignTasks;
	}
	/**
	 * 刷新总数.
	 *
	 *
	 *
	 *
	 * @return the unstart assign load task count
	 * 
	 * @author 042795-foss-duyi
	 * 
	 * @date 2012-10-11 下午3:12:33
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAssignLoadTaskService#getUnstartAssignLoadTaskCount()
	 */
	@Override
	public Long getUnstartAssignLoadTaskCount() {
		AssignLoadTaskQueryConditionDto task = new AssignLoadTaskQueryConditionDto();
		String loginOrgCode =  this.getBigDeptCode(FossUserContext.getCurrentDeptCode());
		//登陆部门
		task.setLoginOrgCode(loginOrgCode);
		//已分配派送装车任务状态
		task.setTaskState(LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_UNSTART);
		//查询未开始的分配记录总数
		return assignLoadTaskDao.getUnstartAssignLoadTaskCount(task);
	}
	/**
	 * 分配派送装车任务.
	 *
	 *
	 *
	 *
	 * @param assignLoadTask the assign load task
	 * 
	 * 
	 * @return the int
	 * 
	 * @author 042795-foss-duyi
	 * 
	 * @date 2012-10-13 上午9:08:11
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAssignLoadTaskService#assign(com.deppon.foss.module.transfer.load.api.shared.domain.AssignLoadTaskEntity)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int assign(AssignLoadTaskEntity assignLoadTask) {
		if(StringUtils.isBlank(assignLoadTask.getBill().getBillNo())||StringUtils.isBlank(assignLoadTask.getLoader().getLoaderCode())||StringUtils.isBlank(assignLoadTask.getLoader().getLoaderName())){
			//拋异常***参数错误
			throw new TfrBusinessException(AssignLoadTaskExceptionCode.PARAMETERERROR_MESSAGECODE);
		}
		//判断派送单状态
		String deliverBillState = this.getDeliverBillState(assignLoadTask.getBill().getBillNo());
		if(!DeliverbillConstants.STATUS_SUBMITED.equals(deliverBillState)){
			//拋异常***派送单状态有误，请刷新派送单列表后重新操作！
			throw new TfrBusinessException(AssignLoadTaskExceptionCode.DELIVERBILLSTATEWRONG_MESSAGECODE);
		}
		//修改派送单状态
		DeliverBillEntity bill = new DeliverBillEntity();
		//设置单号
		bill.setBillNo(assignLoadTask.getBill().getBillNo());
		//已分配
		bill.setState(DeliverbillConstants.STATUS_ASSIGNED);
		//更新派送单状态
		int updateCount = assignLoadTaskDao.updateDeliverBillState(bill,DeliverbillConstants.STATUS_SUBMITED);
		if(updateCount != 1){
			throw new TfrBusinessException("数据过期，请重新查询派送单");
		}else{
			//如果派送单号和派送单状态不为空，添加-派送单状态更新记录
			if (StringUtils.isNotBlank(bill.getBillNo()) && StringUtils.isNotBlank(bill.getState()) ) {
				DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
				deliverBillVary.setDeliverBillNo(bill.getBillNo());//派送单号
				deliverBillVary.setDeliverBillStatus(bill.getState());//派送单状态
				deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
			}
		}
		//TODO 查询该派送单是否存在有效分配记录
		AssignLoadTaskQueryConditionDto assignTask = new AssignLoadTaskQueryConditionDto();
		assignTask.setBillNo(assignLoadTask.getBill().getBillNo());
		long assignCount = assignLoadTaskDao.queryAssignLoadTaskByConditionCount(assignTask);
		if(assignCount > 0){
			throw new TfrBusinessException("该派送单已被分配");
		}
		assignLoadTask.setTaskState(LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_UNSTART);
		Date date=new Date(); 
		//日期格式化
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		//设置id
		assignLoadTask.setId(UUIDUtils.getUUID());
		//设置分配时间
		assignLoadTask.setAssignTime(df.format(date));
		//当前操作用户
		UserEntity user = FossUserContext.getCurrentUser();
		//创建者编号
		assignLoadTask.setCreateUserCode(user.getEmpCode());
		//创建人
		assignLoadTask.setCreateUserName(user.getUserName());
		//修改人工号
		assignLoadTask.setModifyUserCode(user.getEmpCode());
		//修改人姓名
		assignLoadTask.setModifyUserName(user.getUserName());
		//当前操作部门
		OrgAdministrativeInfoEntity currentDept = FossUserContext.getCurrentDept();
		//操作部门id
		assignLoadTask.setCreateOrgCode(this.getBigDeptCode(currentDept.getCode()));
		//部门name
		assignLoadTask.setCreateOrgName(currentDept.getName());
		int insertCount = assignLoadTaskDao.insert(assignLoadTask);
		return insertCount;
	}
	/**
	 * 取消分配.
	 *
	 *
	 *
	 * @param assignTaskId the assign task id
	 * 
	 * @param assignBillNo the assign bill no
	 * 
	 * @return the int
	 * 
	 * @author 042795-foss-duyi
	 * 
	 * @date 2012-10-13 下午8:31:11
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAssignLoadTaskService#cancelAssign(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int cancelAssign(String assignTaskId, String assignBillNo) {
		if(StringUtils.isBlank(assignBillNo)||StringUtils.isBlank(assignTaskId)){
			//拋异常***参数错误
			throw new TfrBusinessException(AssignLoadTaskExceptionCode.PARAMETERERROR_MESSAGECODE);
		}
		AssignLoadTaskEntity assignLoadTask = assignLoadTaskDao.getAssignTaskStateById(assignTaskId);
		if(assignLoadTask==null){
			//拋异常***没有查看到数据
			throw new TfrBusinessException(AssignLoadTaskExceptionCode.DATA_NOTFIND_MESSAGECODE);
			
		}else{
			if(!FossConstants.NO.equals(assignLoadTask.getBeCancelled())){
				//拋异常***该任务已被取消
				throw new TfrBusinessException(AssignLoadTaskExceptionCode.TASKBECENCELED_MESSAGECODE);
			}else{
				if(!LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_UNSTART.equals(assignLoadTask.getTaskState())){
					//拋异常***该任务已执行不能取消
					throw new TfrBusinessException(AssignLoadTaskExceptionCode.TASKBESTARTED_MESSAGECODE);
				}else{
					//修改派送单状态
					DeliverBillEntity bill = new DeliverBillEntity();
					//分配单号
					bill.setBillNo(assignBillNo);
					//已提交
					bill.setState(DeliverbillConstants.STATUS_SUBMITED);
					//更新派送单状态
					int updateCount = assignLoadTaskDao.updateDeliverBillState(bill,DeliverbillConstants.STATUS_ASSIGNED);
					if(updateCount != 1){
						throw new TfrBusinessException("数据过期，请重新刷新界面");
					}else{
						//如果派送单号和派送单状态不为空，添加-派送单状态更新记录
						if (StringUtils.isNotBlank(bill.getBillNo()) && StringUtils.isNotBlank(bill.getState()) ) {
							DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
							deliverBillVary.setDeliverBillNo(bill.getBillNo());//派送单号
							deliverBillVary.setDeliverBillStatus(bill.getState());//派送单状态
							deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
						}
					}
					Date date=new Date(); 
					//日期格式
					SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					assignLoadTask.setModifyTime(df.format(date));
					assignLoadTask.setId(assignTaskId);
					//用户
					UserEntity user = FossUserContext.getCurrentUser();
					assignLoadTask.setModifyUserCode(user.getEmpCode());
					assignLoadTask.setModifyUserName(user.getUserName());
					assignLoadTaskDao.cancelAssign(assignLoadTask);
				}
			}
		}
		return 0;
	}
	/**
	 * Gets the deliver bill state.
	 *
	 *
	 *
	 * @param assignBillNo the assign bill no
	 * 
	 * @return the deliver bill state
	 */
	public String getDeliverBillState(String assignBillNo){
		//查询派送单状态
		String deliverBillState = assignLoadTaskDao.queryDeliverBillState(assignBillNo);
		if(StringUtils.isNotEmpty(deliverBillState)){
			return deliverBillState;
		}else{
			//拋异常***参数错误
			throw new TfrBusinessException(AssignLoadTaskExceptionCode.PARAMETERERROR_MESSAGECODE);
		}
		
	}
	/**
	 * 查询派送单.
	 *
	 *
	 * @param deliverBillQCVo the deliver bill qc vo
	 * 
	 * @param limit the limit
	 * 
	 * @param start the start
	 * 
	 * @return the list
	 * 
	 * @author 042795-foss-duyi
	 * 
	 * @date 2012-10-15 下午8:12:53
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAssignLoadTaskService#queryDeliverBill(com.deppon.foss.module.transfer.load.api.shared.dto.DeliverBillQueryConditionDto, int, int)
	 */
	@Override
	public List<DeliverBillEntity> queryDeliverBill(DeliverBillQueryConditionDto deliverBillQCVo, int limit, int start) {
		List<DeliverBillEntity> bills;
		String loginOrgCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity loginOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(loginOrgCode);
		if(loginOrg != null){
			if(FossConstants.YES.equals(loginOrg.getSalesDepartment())){
				deliverBillQCVo.setLoginOrgCode(loginOrgCode);
			}else{
				try{
					//外场
					deliverBillQCVo.setTransferCenterCode(getCurrentOutfieldCode());
				}catch(TfrBusinessException e){
					return null;
				}
			}
			
			deliverBillQCVo.setDeliverState(DeliverbillConstants.STATUS_SUBMITED);
			bills = assignLoadTaskDao.queryDeliverBill(deliverBillQCVo, limit, start);
			return bills;
		}else{
			return null;
		}
	}
	/**
	 * 获取当前登录部门所属外场编码.
	 *
	 *
	 *
	 * @return the current outfield code
	 * 
	 * 
	 * @throws TfrBusinessException the tfr business exception
	 * 
	 * 
	 * @author 042795-foss-duyi
	 * 
	 * @date 2012-10-15 下午8:12:53
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAssignLoadTaskService#queryDeliverBill(com.deppon.foss.module.transfer.load.api.shared.dto.DeliverBillQueryConditionDto, int, int)
	 */
	private String getCurrentOutfieldCode() throws TfrBusinessException{
				String currentOrgCode = FossUserContext.getCurrentDeptCode();
				List<String> bizTypes = new ArrayList<String>();
				bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
				try{
					OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(currentOrgCode,bizTypes);
					if(org != null){
						if(StringUtils.isNotBlank(org.getCode())){
							return org.getCode();
						}else{
							throw new TfrBusinessException(AssignLoadTaskExceptionCode.DATA_NOTFIND_MESSAGECODE);
						}
					}else{
						throw new TfrBusinessException(AssignLoadTaskExceptionCode.PARAMETERERROR_MESSAGECODE);//部门信息为空
					}
				}catch(BusinessException e){
					throw new TfrBusinessException(AssignLoadTaskExceptionCode.DATA_NOTFIND_MESSAGECODE);
				}
	}
	public String getBigDeptCode(String currentOrgCode){
		List<String> bizTypes = new ArrayList<String>();
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(currentOrgCode);
		if(org != null && FossConstants.YES.equals(org.getSalesDepartment())){
			SaleDepartmentEntity saleDetp = saleDepartmentService.querySaleDepartmentByCode(org.getCode());
			if(saleDetp != null && FossConstants.YES.equals(saleDetp.getStation())){
				return saleDetp.getTransferCenter();
			}
		}else if(org != null && !FossConstants.YES.equals(org.getSalesDepartment())){
			org = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(currentOrgCode,bizTypes);
		}
		if(org != null){
			return org.getCode();
		}else{
			return currentOrgCode;
		}
	}
	/**
	 * 查询派送单数量.
	 *
	 *
	 *
	 * @param deliverBillQCVo the deliver bill qc vo
	 * 
	 * @return the deliver bill count
	 * 
	 * @author 042795-foss-duyi
	 * 
	 * @date 2012-10-15 下午8:12:53
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAssignLoadTaskService#getDeliverBillCount(com.deppon.foss.module.transfer.load.api.shared.dto.DeliverBillQueryConditionDto)
	 */
	@Override
	public Long getDeliverBillCount(DeliverBillQueryConditionDto deliverBillQCVo) {
		//当前操作部门
		String loginOrgCode = FossUserContext.getCurrentDeptCode();
		//根据编码查询
		OrgAdministrativeInfoEntity loginOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(loginOrgCode);
		if(loginOrg != null){
			if(FossConstants.YES.equals(loginOrg.getSalesDepartment())){
				deliverBillQCVo.setLoginOrgCode(loginOrgCode);
			}else{
				try{
					//外场
					deliverBillQCVo.setTransferCenterCode(getCurrentOutfieldCode());
				}catch(TfrBusinessException e){
					return NumberUtils.LONG_ZERO;
				}
			}
			//已提交
			deliverBillQCVo.setDeliverState(DeliverbillConstants.STATUS_SUBMITED);
			//分配装车任务dao接口
			//查询派送单数量
			return assignLoadTaskDao.getDeliverBillCount(deliverBillQCVo);
		}else{
			//返回0
			return NumberUtils.LONG_ZERO;
		}
	}
	/**
	 * 按条件查询已分配派送装车任务.
	 *
	 *
	 *
	 * @param assignLoadTask the assign load task
	 * 
	 * @param limit the limit
	 * 
	 * @param start the start
	 * 
	 * @return the list
	 * 
	 * @author 042795-foss-duyi
	 * 
	 * @date 2012-10-16 下午4:57:14
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAssignLoadTaskService#queryAssignLoadTaskByCondition(com.deppon.foss.module.transfer.load.api.shared.dto.AssignLoadTaskQueryConditionDto, int, int)
	 */
	@Override
	public List<AssignLoadTaskEntity> queryAssignLoadTaskByCondition(AssignLoadTaskQueryConditionDto assignLoadTask, int limit, int start) {
		assignLoadTask = this.makeCondition(assignLoadTask);
		List<AssignLoadTaskEntity> assignLoadTasks = assignLoadTaskDao.queryAssignLoadTaskByCondition(assignLoadTask, limit, start);
		if(CollectionUtils.isEmpty(assignLoadTasks)){
			//拋异常***没有查看到数据
			//throw new TfrBusinessException(AssignLoadTaskExceptionCode.DATA_NOTFIND_MESSAGECODE);
			return null;
		}
		//返回结果
		return assignLoadTasks;
	}
	/**
	 * 按条件查询已分配派送装车任务数量.
	 *
	 *
	 * @param assignLoadTaskVo the assign load task vo
	 * 
	 * @return the long
	 * 
	 * @author 042795-foss-duyi
	 * 
	 * @date 2012-10-16 下午4:57:14
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAssignLoadTaskService#queryAssignLoadTaskByConditionCount(com.deppon.foss.module.transfer.load.api.shared.dto.AssignLoadTaskQueryConditionDto)
	 */
	@Override
	public Long queryAssignLoadTaskByConditionCount(AssignLoadTaskQueryConditionDto assignLoadTaskVo) {
		//调用dao查询
		assignLoadTaskVo = this.makeCondition(assignLoadTaskVo);
		return assignLoadTaskDao.queryAssignLoadTaskByConditionCount(assignLoadTaskVo);
	}
	/**
	 * Make condition.
	 *
	 * @param assignLoadTaskVo the assign load task vo
	 * 
	 * @return the assign load task query condition dto
	 */
	public AssignLoadTaskQueryConditionDto makeCondition(AssignLoadTaskQueryConditionDto assignLoadTaskVo){
		//查询所有使用
		if(LoadConstants.LOAD_TASK_TYPE_ALL.equals(assignLoadTaskVo.getTaskState())){
			assignLoadTaskVo.setTaskState(null);
		}
		assignLoadTaskVo.setLoginOrgCode(this.getBigDeptCode(FossUserContext.getCurrentDeptCode()));
		return assignLoadTaskVo;
	}
	/**
	 * 查询界面中弹出窗口获取未完成货量，未完成任务数.
	 *
	 *
	 *
	 * @param loaderCode the loader code
	 * 
	 * @return the loader entity
	 * 
	 * @author 042795-foss-duyi
	 * 
	 * @date 2012-10-17 上午8:29:04
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAssignLoadTaskService#queryUnfinishedWorkLoad(java.lang.String)
	 */
	@Override
	public LoaderEntity queryUnfinishedWorkLoad(String loaderCode) {
		//构建dto对象
		LoaderQueryConditionDto loaderQCVo = new LoaderQueryConditionDto();
		//理货员实体
		LoaderEntity loader = new LoaderEntity();
		//理货员编号
		loader.setLoaderCode(loaderCode);
		//理货员LIST
		List<LoaderEntity> loaders= new ArrayList<LoaderEntity>();
		//加入理货员
		loaders.add(loader);
		//Sets the 理货员list.
		loaderQCVo.setLoaders(loaders);
		//查询未完成工作量
		List<LoaderEntity> workLoads = assignLoadTaskDao.queryUnFinishWorkLoad(loaderQCVo);
		//如果为空
		if(CollectionUtils.isEmpty(workLoads)){
			//拋异常***没有查看到数据
			//throw new TfrBusinessException(AssignLoadTaskExceptionCode.DATA_NOTFIND_MESSAGECODE);
			return null;
		}
		return workLoads.get(0);
	}
	public void setDeliverBillVaryStatusService(
			IDeliverBillVaryStatusService deliverBillVaryStatusService) {
		this.deliverBillVaryStatusService = deliverBillVaryStatusService;
	}
}