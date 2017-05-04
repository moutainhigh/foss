package com.deppon.foss.module.pickup.predeliver.server.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.predeliver.api.server.service.ICustomerReceiptHabitService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptHabitEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.CustomerReceiptHabitVo;

/** 
 * @ClassName: CustomerReceiptHabitAction 
 * @Description: 收货习惯action
 * @author 237982-foss-fangwenjun 
 * @date 2015-4-1 上午11:29:03 
 *  
 */
public class CustomerReceiptHabitAction  extends AbstractAction{

	/** 
	 * 序列号 
	 */ 
	private static final long serialVersionUID = -7714584408973347574L;

	/** 
	 * 客户收货习惯Id  input
	 */ 
	public String id;
	
	/** 
	 *  客户收货习惯Id 数组  input
	 */ 
	private String ids;
	
	/** 
	 * 客户收货习惯 
	 */ 
	public CustomerReceiptHabitEntity customerReceiptHabitEntity;
	
	/** 
	 * 客户收货习惯vo 
	 */ 
	public CustomerReceiptHabitVo customerReceiptHabitVo;
	
	/** 
	 * 客户收货习惯集合 
	 */ 
	public List<CustomerReceiptHabitEntity> customerReceiptHabitList;
	
	/** 
	 * 客户收货习惯service 
	 */ 
	public ICustomerReceiptHabitService customerReceiptHabitService;
	
	/**
	 * @Title: insertReceiptHabit
	 * @Description: 插入收货习惯
	 * @return
	 */
	public String insertReceiptHabit(){
		try {
			if (customerReceiptHabitEntity == null) {
				return returnError("传入参数不能为空");
			}
			// 获取当前用户
			UserEntity currentUser = FossUserContext.getCurrentUser();
			// 判断是否获取当前用户
			if(currentUser != null && currentUser.getEmployee()!= null){
				// 设置收货习惯操作人编码
				customerReceiptHabitEntity.setModifyUser(currentUser.getEmployee().getEmpCode());
				// 设置收货习惯操作人名称
				customerReceiptHabitEntity.setOperatorName(currentUser.getEmployee().getEmpName());
				// 设置收货习惯当前操作日期
				customerReceiptHabitEntity.setModifyDate(new Date());
				// 设置收货习惯操作人部门名称
				customerReceiptHabitEntity.setOperateOrgName(FossUserContext.getCurrentDeptName());
				// 设置收货习惯操作人部门编码
				customerReceiptHabitEntity.setOperateOrgCode(FossUserContext.getCurrentDeptCode());
				// 设置收货习惯创建人姓名
				customerReceiptHabitEntity.setCreaterName(currentUser.getEmployee().getEmpName());
				// 设置收货习惯创建人编码
				customerReceiptHabitEntity.setCreateUser(currentUser.getEmployee().getEmpCode());
				// 设置收货习惯创建日期
				customerReceiptHabitEntity.setCreateDate(new Date());
			} else {
				return returnError("无法获取当前登录人信息！");
			}
			int result = customerReceiptHabitService.insertCustomerReceiptHabit(customerReceiptHabitEntity);
			if (result == 1) {
				return returnSuccess();
			} else if (result == -1) {
				 return returnError("存在不应该为空的参数传入!");
			} else {
				 return returnError("添加失败!");
			}
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		
	}
	
	/**
	 * @Title: deleteReceiptHabit
	 * @Description: 删除收货习惯
	 * @return
	 */
	public String deleteReceiptHabit() {
		try {
			int result = customerReceiptHabitService.deleteReceiptHabitById(id);
			if (result == 1) {
				return returnSuccess();
			} else {
				return  returnError("删除内容不存在");
			}
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
	}
	
	/**
	 * @Title: deleteReceiptHabitList
	 * @Description: 批量删除收货习惯
	 * @return
	 */
	public String deleteReceiptHabitList() {
		try {
			String[] idItems = null;
			if (ids != null || !"".equals(ids.trim())){
				idItems = ids.split(",");
			}
			int result = customerReceiptHabitService.deleteReceiptHabitByIds(idItems);
			if (result > 0) {
				return returnSuccess();
			} else {
				return  returnError("删除内容不存在");
			}
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
	}
	
	/**
	 * @Title: updateReceiptHabit
	 * @Description: 修改收货习惯
	 * @return
	 */
	public String updateReceiptHabit(){
		try {
			if (customerReceiptHabitEntity == null) {
				return returnError("传入参数不能为空");
			}
			// 获取当前用户
			UserEntity currentUser = FossUserContext.getCurrentUser();
			// 判断是否获取当前用户
			if(currentUser != null && currentUser.getEmployee()!= null){
				// 设置收货习惯操作人编码
				customerReceiptHabitEntity.setModifyUser(currentUser.getEmployee().getEmpCode());
				// 设置收货习惯操作人名称
				customerReceiptHabitEntity.setOperatorName(currentUser.getEmpName());
				// 设置收货习惯当前操作日期
				customerReceiptHabitEntity.setModifyDate(new Date());
				// 设置收货习惯操作人部门编码
				customerReceiptHabitEntity.setOperateOrgCode(FossUserContext.getCurrentDeptCode());
			} else {
				return returnError("无法获取当前登录人信息！");
			}
			int result = customerReceiptHabitService.updateReceiptHabit(customerReceiptHabitEntity);
			if (result == 1) {
				return returnSuccess();
			} else if (result == -1) { 
				return returnError("存在不应该为空的参数传入!");
			} else {
				return  returnError("修改内容不存在！");
			}
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
	}
	
	/**
	 * @Title: queryReceiptHabit
	 * @Description: 查询收货习惯
	 * @return
	 */
	public String queryReceiptHabit(){
		try {
			this.totalCount = customerReceiptHabitService.selectReceiptHabitCount(customerReceiptHabitVo);
			if(this.totalCount != null && this.totalCount > 0L) {
				this.customerReceiptHabitList = customerReceiptHabitService.selectReceiptHabitList(customerReceiptHabitVo, super.getStart(), super.getLimit());
			} else {
				this.customerReceiptHabitList = null;
			}
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		return returnSuccess();
	}
	
	public String addReceiptHabit() {
		try {
			if (customerReceiptHabitEntity == null) {
				return returnError("传入参数不能为空");
			}
			// 获取当前用户
			UserEntity currentUser = FossUserContext.getCurrentUser();
			// 判断是否获取当前用户
			if(currentUser != null && currentUser.getEmployee()!= null){
				// 设置收货习惯操作人编码
				customerReceiptHabitEntity.setModifyUser(currentUser.getEmployee().getEmpCode());
				// 设置收货习惯操作人名称
				customerReceiptHabitEntity.setOperatorName(currentUser.getEmployee().getEmpName());
				// 设置收货习惯当前操作日期
				customerReceiptHabitEntity.setModifyDate(new Date());
				// 设置收货习惯操作人部门名称
				customerReceiptHabitEntity.setOperateOrgName(FossUserContext.getCurrentDeptName());
				// 设置收货习惯操作人部门编码
				customerReceiptHabitEntity.setOperateOrgCode(FossUserContext.getCurrentDeptCode());
				// 设置收货习惯创建人姓名
				customerReceiptHabitEntity.setCreaterName(currentUser.getEmployee().getEmpName());
				// 设置收货习惯创建人编码
				customerReceiptHabitEntity.setCreateUser(currentUser.getEmployee().getEmpCode());
				// 设置收货习惯创建日期
				customerReceiptHabitEntity.setCreateDate(new Date());
			} else {
				return returnError("无法获取当前登录人信息！");
			}
			
			int result = 0;
			
			CustomerReceiptHabitEntity selectReceiptHabitByParam = customerReceiptHabitService.selectReceiptHabitByParam(customerReceiptHabitEntity);
			
			if(selectReceiptHabitByParam == null) {
				result = customerReceiptHabitService.insertOneReceiptHabit(customerReceiptHabitEntity);
			} else {
				return returnError("该客户收货习惯已存在,不能在做添加...");
			}
			
			if (result == 1) {
				return returnSuccess();
			} else if (result == -1) {
				 return returnError("存在不应该为空的参数传入!");
			} else {
				 return returnError("添加失败!");
			}
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		
	}

	/**
	 * 获取id
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置id
	 * @param id 要设置的id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 获取ids
	 * @return the ids
	 */
	public String getIds() {
		return ids;
	}

	/**
	 * 设置ids
	 * @param ids 要设置的ids
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 获取customerReceiptHabitEntity
	 * @return the customerReceiptHabitEntity
	 */
	public CustomerReceiptHabitEntity getCustomerReceiptHabitEntity() {
		return customerReceiptHabitEntity;
	}

	/**
	 * 设置customerReceiptHabitEntity
	 * @param customerReceiptHabitEntity 要设置的customerReceiptHabitEntity
	 */
	public void setCustomerReceiptHabitEntity(
			CustomerReceiptHabitEntity customerReceiptHabitEntity) {
		this.customerReceiptHabitEntity = customerReceiptHabitEntity;
	}

	/**
	 * 获取customerReceiptHabitVo
	 * @return the customerReceiptHabitVo
	 */
	public CustomerReceiptHabitVo getCustomerReceiptHabitVo() {
		return customerReceiptHabitVo;
	}

	/**
	 * 设置customerReceiptHabitVo
	 * @param customerReceiptHabitVo 要设置的customerReceiptHabitVo
	 */
	public void setCustomerReceiptHabitVo(
			CustomerReceiptHabitVo customerReceiptHabitVo) {
		this.customerReceiptHabitVo = customerReceiptHabitVo;
	}

	/**
	 * 获取customerReceiptHabitList
	 * @return the customerReceiptHabitList
	 */
	public List<CustomerReceiptHabitEntity> getCustomerReceiptHabitList() {
		return customerReceiptHabitList;
	}

	/**
	 * 设置customerReceiptHabitList
	 * @param customerReceiptHabitList 要设置的customerReceiptHabitList
	 */
	public void setCustomerReceiptHabitList(
			List<CustomerReceiptHabitEntity> customerReceiptHabitList) {
		this.customerReceiptHabitList = customerReceiptHabitList;
	}

	/**
	 * 设置customerReceiptHabitService
	 * @param customerReceiptHabitService 要设置的customerReceiptHabitService
	 */
	@Resource
	public void setCustomerReceiptHabitService(
			ICustomerReceiptHabitService customerReceiptHabitService) {
		this.customerReceiptHabitService = customerReceiptHabitService;
	}
	
}
