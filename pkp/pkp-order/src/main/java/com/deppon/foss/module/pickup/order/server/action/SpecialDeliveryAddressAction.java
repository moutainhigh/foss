package com.deppon.foss.module.pickup.order.server.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.order.api.server.service.ISpecialDeliveryAddressService;
import com.deppon.foss.module.pickup.order.api.shared.domain.SpecialDeliveryAddressEntity;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.module.pickup.order.api.shared.vo.SpecialDeliveryAddressVo;

/**
 * @ClassName: SpecialDeliveryAddressAction
 * @Description: 特殊送货地址action 类
 * @author 237982-foss-fangwenjun
 * @date 2015-4-17 下午2:11:52
 * 
 */
public class SpecialDeliveryAddressAction extends AbstractAction {

	/**
	 * 特殊送货地址序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 特殊送货地址Vo
	 */
	private SpecialDeliveryAddressVo specialDeliveryAddressVo;

	/**
	 * 特殊送货地址的Id
	 */
	private String id;

	/**
	 * 特殊送货地址多个Id
	 */
	private String ids;

	/**
	 * 特殊送货地址Entity
	 */
	private SpecialDeliveryAddressEntity specialDeliveryAddressEntity;

	/**
	 * 特殊送货地址集合
	 */
	private List<SpecialDeliveryAddressEntity> deliveryAddressList;

	/**
	 * 特殊送货地址Service
	 */
	private ISpecialDeliveryAddressService specialDeliveryAddressService;

	/**
	 * @Title: deleteSpecialDeliveryAddress
	 * @Description: 删除特殊送货地址
	 * @return
	 */
	public String deleteSpecialDeliveryAddress() {
		try {
			specialDeliveryAddressService.deleteSpecialDeliveryAddressById(id);
			// 返回结果
			return super.returnSuccess();
		} catch (DispatchException e) {
			return returnError(e.getMessage());
		}
	}

	/**
	 * @Title: bindVehicle
	 * @Description: 给特殊送货地址绑定车辆
	 * @return
	 */
	public String bindVehicle() {
		try {
			String[] items = null;
			if (ids != null && !"".equals(ids.trim())) {
				items = ids.split(",");
			} else {
				return super.returnError("ids不能为空");
			}
			// 获取当前用户
			UserEntity currentUser = FossUserContext.getCurrentUser();
			// 判断是否获取当前用户
			if (currentUser != null && currentUser.getEmployee() != null) {
				// 设置操作日期
				specialDeliveryAddressEntity.setOperateDate(new Date());
				// 设置操作部门编码
				specialDeliveryAddressEntity.setOperateOrgCode(FossUserContext.getCurrentDeptCode());
				// 设置操作部门名称
				specialDeliveryAddressEntity.setOperateOrgName(FossUserContext.getCurrentDeptName());
				// 设置操作人编码
				specialDeliveryAddressEntity.setOperatorCode(currentUser.getEmployee().getEmpCode());
				// 设置操作人名称
				specialDeliveryAddressEntity.setOperatorName(currentUser.getEmployee().getEmpName());
			} else {
				return returnError("无法获取当前登录人信息！");
			}
			specialDeliveryAddressService.updateSpecialDeliveryAddress(items, specialDeliveryAddressEntity);
			// 返回成功
			return super.returnSuccess();
		} catch (DispatchException e) {
			// 返回失败
			return returnError(e.getMessage());
		}

	}

	/**
	 * @Title: querySpecialDeliveryAddress
	 * @Description: 查询特殊送货地址
	 * @return
	 */
	public String querySpecialDeliveryAddress() {
		try {
			this.totalCount = specialDeliveryAddressService.selectSpecialDeliveryAddressCount(specialDeliveryAddressVo);
			if (this.totalCount != null && this.totalCount > 0L) {
				this.deliveryAddressList = specialDeliveryAddressService.selectSpecialDeliveryAddressList(
						specialDeliveryAddressVo, this.getStart(), this.getLimit());
			} else {
				this.deliveryAddressList = null;
			}
			// 返回成功
			return super.returnSuccess();
		} catch (DispatchException e) {
			// 返回失败
			return returnError(e);
		}
	}	

	/**
	 * 获取specialDeliveryAddressVo
	 * 
	 * @return the specialDeliveryAddressVo
	 */
	public SpecialDeliveryAddressVo getSpecialDeliveryAddressVo() {
		return specialDeliveryAddressVo;
	}

	/**
	 * 设置specialDeliveryAddressVo
	 * 
	 * @param specialDeliveryAddressVo
	 *            要设置的specialDeliveryAddressVo
	 */
	public void setSpecialDeliveryAddressVo(SpecialDeliveryAddressVo specialDeliveryAddressVo) {
		this.specialDeliveryAddressVo = specialDeliveryAddressVo;
	}

	/**
	 * 获取id
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 *            要设置的id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取ids
	 * 
	 * @return the ids
	 */
	public String getIds() {
		return ids;
	}

	/**
	 * 设置ids
	 * 
	 * @param ids
	 *            要设置的ids
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 获取specialDeliveryAddressEntity
	 * 
	 * @return the specialDeliveryAddressEntity
	 */
	public SpecialDeliveryAddressEntity getSpecialDeliveryAddressEntity() {
		return specialDeliveryAddressEntity;
	}

	/**
	 * 设置specialDeliveryAddressEntity
	 * 
	 * @param specialDeliveryAddressEntity
	 *            要设置的specialDeliveryAddressEntity
	 */
	public void setSpecialDeliveryAddressEntity(SpecialDeliveryAddressEntity specialDeliveryAddressEntity) {
		this.specialDeliveryAddressEntity = specialDeliveryAddressEntity;
	}

	/**
	 * 设置specialDeliveryAddressService
	 * 
	 * @param specialDeliveryAddressService
	 *            要设置的specialDeliveryAddressService
	 */
	@Resource
	public void setSpecialDeliveryAddressService(ISpecialDeliveryAddressService specialDeliveryAddressService) {
		this.specialDeliveryAddressService = specialDeliveryAddressService;
	}

	/**
	 * 获取deliveryAddressList
	 * 
	 * @return the deliveryAddressList
	 */
	public List<SpecialDeliveryAddressEntity> getDeliveryAddressList() {
		return deliveryAddressList;
	}

	/**
	 * 设置deliveryAddressList
	 * 
	 * @param deliveryAddressList
	 *            要设置的deliveryAddressList
	 */
	public void setDeliveryAddressList(List<SpecialDeliveryAddressEntity> deliveryAddressList) {
		this.deliveryAddressList = deliveryAddressList;
	}
}
