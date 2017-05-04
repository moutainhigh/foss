package com.deppon.foss.module.pickup.order.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.order.api.server.dao.ISpecialDeliveryAddressDao;
import com.deppon.foss.module.pickup.order.api.server.service.ISpecialDeliveryAddressService;
import com.deppon.foss.module.pickup.order.api.shared.domain.SpecialDeliveryAddressEntity;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.module.pickup.order.api.shared.vo.SpecialDeliveryAddressVo;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/** 
 * @ClassName: SpecialDeliveryAddressService 
 * @Description: 特殊地址Service 实现类 
 * @author 237982-foss-fangwenjun 
 * @date 2015-4-17 上午11:17:36 
 *  
 */
public class SpecialDeliveryAddressService implements
		ISpecialDeliveryAddressService {
	
	/**
	 * 特殊送货地址Dao
	 */
	private ISpecialDeliveryAddressDao specialDeliveryAddressDao;
	
	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 部门 复杂查询 service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * 设置specialDeliveryAddressDao
	 * @param specialDeliveryAddressDao 要设置的specialDeliveryAddressDao
	 */
	@Resource
	public void setSpecialDeliveryAddressDao(
			ISpecialDeliveryAddressDao specialDeliveryAddressDao) {
		this.specialDeliveryAddressDao = specialDeliveryAddressDao;
	}

	/**
	 * 设置orgAdministrativeInfoService
	 * @param orgAdministrativeInfoService 要设置的orgAdministrativeInfoService
	 */
	@Resource
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 设置orgAdministrativeInfoComplexService
	 * @param orgAdministrativeInfoComplexService 要设置的orgAdministrativeInfoComplexService
	 */
	@Resource
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	private void setCurrentUserInfo(SpecialDeliveryAddressEntity specialDeliveryAddressEntity) {
		// 获取当前用户
		UserEntity currentUser = FossUserContext.getCurrentUser();
		// 判断是否获取当前用户
		if (currentUser != null && currentUser.getEmployee() != null) {
			// 创建当前时间
			Date date = new Date();
			// 设置操作日期
			specialDeliveryAddressEntity.setOperateDate(date);
			// 设置操作部门编码
			specialDeliveryAddressEntity.setOperateOrgCode(FossUserContext.getCurrentDeptCode());
			// 设置操作部门名称
			specialDeliveryAddressEntity.setOperateOrgName(FossUserContext.getCurrentDeptName());
			// 设置操作人编码
			specialDeliveryAddressEntity.setOperatorCode(currentUser.getEmployee().getEmpCode());
			// 设置操作人名称
			specialDeliveryAddressEntity.setOperatorName(currentUser.getEmployee().getEmpName());
			// 设置创建日期
			specialDeliveryAddressEntity.setCreateDate(date);
			// 设置创建人编码
			specialDeliveryAddressEntity.setCreaterCode(currentUser.getEmployee().getEmpCode());
			// 设置创建人名称
			specialDeliveryAddressEntity.setCreaterName(currentUser.getEmployee().getEmpName());
			// 设置创建部门编码
			specialDeliveryAddressEntity.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
			// 设置创建部门名称
			specialDeliveryAddressEntity.setCreateOrgName(FossUserContext.getCurrentDeptName());
		} else {
			throw new DispatchException("无法获取当前登录人信息！");
		}
	}

	/**
	 * @Title: insertSpecialDeliveryAddress
	 * @Description: 添加特殊送货地址
	 * @param specialDeliveryAddressEntity 特殊送货地址对象
	 * @return 受影响的行数
	 */
	@Override
	public int insertSpecialDeliveryAddress(
			SpecialDeliveryAddressEntity specialDeliveryAddressEntity) {
		if (specialDeliveryAddressEntity == null) {
			throw new DispatchException("特殊送货地址对象对象不能为空");
		}
		
		if (specialDeliveryAddressEntity.getDeliveryAddress() == null 
				|| "".equals(specialDeliveryAddressEntity.getDeliveryAddress().trim())){
			throw new DispatchException("送货地址不能为空");
		}
		// 设置操作人信息
		setCurrentUserInfo(specialDeliveryAddressEntity);
		
		specialDeliveryAddressEntity.setId(UUIDUtils.getUUID());
		return specialDeliveryAddressDao.insertSpecialDeliveryAddress(specialDeliveryAddressEntity);
	}

	/**
	 * @Title: deleteSpecialDeliveryAddressById
	 * @Description: 根据id删除特殊送货地址
	 * @param id 特殊送货地址主键
	 * @return 受影响的行数
	 */
	@Override
	public int deleteSpecialDeliveryAddressById(String id) {
		if(id == null || "".equals(id.trim())){
			throw new DispatchException("特殊送货地址主键不能为空");
		}
		return specialDeliveryAddressDao.deleteById(id);
	}

	/**
	 * @Title: updateSpecialDeliveryAddress
	 * @Description: 修改特殊送货地址
	 * @param specialDeliveryAddressEntity 特殊送货地址对象
	 * @return 受影响的行数
	 */
	@Override
	public int updateSpecialDeliveryAddress(
			SpecialDeliveryAddressEntity specialDeliveryAddressEntity) {
		if (specialDeliveryAddressEntity == null){
			throw new DispatchException("特殊送货地址对象不能为空");
		}
		
		if (specialDeliveryAddressEntity.getDeliveryAddress() == null 
				|| "".equals(specialDeliveryAddressEntity.getDeliveryAddress().trim())){
			throw new DispatchException("送货地址不能为空");
		}
		
		// 设置操作人信息
		setCurrentUserInfo(specialDeliveryAddressEntity);
		
		return specialDeliveryAddressDao.updateSpecialDeliveryAddress(specialDeliveryAddressEntity);
	}

	/**
	 * @Title: updateSpecialDeliveryAddress
	 * @Description: 批量修改特殊送货地址
	 * @param ids 特殊送货地址id数组
	 * @param specialDeliveryAddressEntity 特殊地址对象
	 * @return 受影响的行数
	 */
	@Override
	public int updateSpecialDeliveryAddress(String[] ids,
			SpecialDeliveryAddressEntity specialDeliveryAddressEntity) {
		if(ids == null || ids.length == 0){
			throw new DispatchException("特殊送货地址id数组不能为空");
		}
		if(specialDeliveryAddressEntity == null){
			throw new DispatchException("特殊送货地址对象不能为空");
		}
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		paramMap.put("ids", ids);

		paramMap.put("specialDeliveryAddressEntity", specialDeliveryAddressEntity);
		
		return specialDeliveryAddressDao.updateSpecialDeliveryAddress(paramMap);
	}
	
	/**
	 * @Title: getOrgList
	 * @Description: 获取当前部门下的车队部门
	 * @return 部门集合
	 */
	private List<String> getOrgList(){
		String orgCode = FossUserContextHelper.getOrgCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(orgCode);
		List<String> orgConditionList = new ArrayList<String>();
		if (orgAdministrativeInfoEntity != null) {
			if (FossConstants.YES.equals(orgAdministrativeInfoEntity.getSalesDepartment())) {
				orgConditionList.add(orgCode);
			} else {
				// 调用综合组的服务获取当前组织所在的顶级车队 先找到当前部门对应的顶级车队，然后再遍历往下找车队和车队组 把所有的车队
				// 车队组 都添加到查询条件
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity1 = orgAdministrativeInfoComplexService.getTopFleetByCode(orgCode);
				if (orgAdministrativeInfoEntity1 != null) {
					// 查询顶级车队下的所有车队
					List<OrgAdministrativeInfoEntity> fleetList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoSubByMotorcade(orgAdministrativeInfoEntity1.getCode(),BizTypeConstants.ORG_TRANS_DEPARTMENT);
					// 查询车队组
					List<OrgAdministrativeInfoEntity> teamList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoSubByMotorcade(orgAdministrativeInfoEntity1.getCode(),BizTypeConstants.ORG_TRANS_TEAM);
					//添加本部门
					orgConditionList.add(orgCode);
					// 添加本顶级车队
					orgConditionList.add(orgAdministrativeInfoEntity1.getCode());
					// 赋值
					if (fleetList != null) {
						for (OrgAdministrativeInfoEntity fleet : fleetList) {
							orgConditionList.add(fleet.getCode());
						}
					}
					// 赋值
					if (teamList != null) {
						for (OrgAdministrativeInfoEntity team : teamList) {
							orgConditionList.add(team.getCode());
						}
					}
				} else {
					orgConditionList.add(orgCode);
				}
			}
		} else {
			orgConditionList.add(orgCode);
		}
		return orgConditionList;
	}

	/**
	 * @Title: selectListByParam
	 * @Description: 根据特殊送货地址值对象分页查询特殊送货地址
	 * @param specialDeliveryAddressVo 特殊送货地址值对象
	 * @return 特殊送货地址对象集合
	 */
	@Override
	public List<SpecialDeliveryAddressEntity> selectSpecialDeliveryAddressList(
			SpecialDeliveryAddressVo specialDeliveryAddressVo, int start,
			int limit) {
		if (specialDeliveryAddressVo == null) {
			return null;
		}
		if(specialDeliveryAddressVo.getOperateDateStart() == null || specialDeliveryAddressVo.getOperateDateEnd() == null){
			return null;
		}
		// 设置部门编码集合
		specialDeliveryAddressVo.setOrgList(getOrgList());
		return specialDeliveryAddressDao.selectListByParam(specialDeliveryAddressVo, start, limit);
	}

	/**
	 * @Title: selectCountByParam
	 * @Description: 根据特殊送货地址值对象查询特殊送货地址总数
	 * @param specialDeliveryAddressVo 特殊送货地址值对象
	 * @return 特殊送货地址总数
	 */
	@Override
	public Long selectSpecialDeliveryAddressCount(
			SpecialDeliveryAddressVo specialDeliveryAddressVo) {
		if (specialDeliveryAddressVo == null) {
			return 0L;
		}
		if(specialDeliveryAddressVo.getOperateDateStart() == null || specialDeliveryAddressVo.getOperateDateEnd() == null){
			return null;
		}
		// 设置部门编码集合
		specialDeliveryAddressVo.setOrgList(getOrgList());
		return specialDeliveryAddressDao.selectCountByParam(specialDeliveryAddressVo);
	}

	/**
	 * @Title: selectOneByDeliveryAddress
	 * @Description: 根据送货地址查询特殊送货地址
	 * @param deliveryAddress 送货地址
	 * @return 特殊送货地址
	 */
	@Override
	public SpecialDeliveryAddressEntity selectSpecialDeliveryAddress(
			String deliveryAddress) {
		
		if (deliveryAddress == null || "".equals(deliveryAddress.trim())) {
			return null;
		}
		
		return specialDeliveryAddressDao.selectOneByDeliveryAddress(deliveryAddress);
	}

	@Override
	public int deleteSpecialDeliveryAddressByAddress(String deliveryAddress) {
		
		if (StringUtils.isBlank(deliveryAddress)) {
			return 0;
		}
		
		return specialDeliveryAddressDao.deleteByAddress(deliveryAddress);
	}

}
