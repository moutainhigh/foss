package com.deppon.pda.bdm.module.ocb.server.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IBindingLeasedVehicleService;
import com.deppon.foss.module.pickup.pda.api.shared.domain.BindingLeasedTruckEntity;
import com.deppon.pda.bdm.module.core.server.cache.DeptCache;
import com.deppon.pda.bdm.module.core.server.cache.UserCache;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.OcbConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.UserEntity;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.login.PasswordErrorException;
import com.deppon.pda.bdm.module.core.shared.exception.login.UserNotFoundException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.UUIDUtils;
import com.deppon.pda.bdm.module.ocb.server.dao.IDeviceRegistDao;
import com.deppon.pda.bdm.module.ocb.shared.domain.DeviceRegistBean;

/**
 * @ClassName DeviceRegistService.java
 * @Description Android设备自动注册功能
 * 1、首先检查设备更新
 * 2、如果抛异常：设备号不存在
 * 3、调用设备注册接口
 * @author 201638
 * @date 2015-1-19
 */
/**
 * @ClassName DeviceRegistService.java
 * @Description
 * @author 201638
 * @date 2015-1-19
 */
public class DeviceRegistService implements IBusinessService<Void, DeviceRegistBean> {
	private static final Logger LOG = Logger.getLogger(DeviceRegistService.class);
	private IDeviceRegistDao deviceRegistDao;
	private UserCache userCache;
	private DeptCache deptCache;
	private IBindingLeasedVehicleService bindingLeasedVehicleService;

	@Override
	public DeviceRegistBean parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		// 解析body
		DeviceRegistBean deviceRegistBean = JsonUtil.parseJsonToObject(DeviceRegistBean.class, asyncMsg.getContent());
		return deviceRegistBean;
	}

	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, DeviceRegistBean param) throws PdaBusiException {
		//校验参数
		this.validate(asyncMsg, param);
		LOG.info("检验用户"+param.getUserCode()+",密码"+param.getPassword());
		UserEntity userEntity = null;
		DeviceRegistBean device = new DeviceRegistBean();
		//外请车司机不叫校验密码
		if(Constant.EXTERNAL_DRIVER.equals(param.getUserCode())){	
			if(param.getTruckCode()==null){
				throw new FossInterfaceException(null, "车牌号为空!");
			}
			//查询部门信息
			BindingLeasedTruckEntity trunckEntity = bindingLeasedVehicleService.queryBindingLeasedDate(param.getTruckCode());
			if(trunckEntity==null||trunckEntity.getOperatorCode()==null){
				throw new FossInterfaceException(null, "车牌号:"+param.getTruckCode()+"没有绑定顶级车队的操作人!");
			}
			userEntity = userCache.getUser(trunckEntity.getOperatorCode());
			device.setRemark("!000000_外请车司机专用!");// 自动注册
		} else{
			// 校验工号密码是否正确
			this.checkPwd(param);
			//获取用户信息
			userEntity = userCache.getUser(param.getUserCode());
			device.setRemark("自动注册");// 自动注册
		}
		String deptCode = deptCache.getDept(userEntity.getDeptId()).getDeptCode();
		LOG.info("Android设备注册");
		// 保存设备信息到数据库
		String dvcCode = asyncMsg.getPdaCode();// 设备编号
		device.setId(UUIDUtils.getUUID());// uuid
		device.setDvcCode(dvcCode);// 设备编号
		
		device.setSimCardCode(dvcCode);// sim卡号
		device.setModel(asyncMsg.getPdaType());// 设备类型 Android/PDA
		device.setCompanyCode(deptCode);// 部门编码
		device.setPurDate(new Date());// 购买日期
		device.setUserCode(param.getUserCode());// 工号
		device.setLastUpdDate(new Date());// 最后更新日期
		device.setStatus("1");// 状态
		device.setSernbr(dvcCode);// 固定资产编号
		device.setHasPen("1");// 是否有笔
		device.setHasPackage("1");// 是否有包
		device.setSystemType(asyncMsg.getPdaType());// 系统类型 Android
		try {
			if(deviceRegistDao.checkDeviceIsExist(dvcCode)){
				throw new FossInterfaceException(null, "设备号已成功注册");
			} else{
				// 保存注册信息
				deviceRegistDao.addDevice(device);
			}
			LOG.info("Android设备注册成功");
		}catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return null;
	}

	/**
	 * @description 校验工号和密码是否正确
	 * @param userCode
	 *            工号
	 * @author 201638
	 * @date 2015-1-19
	 */
	private void checkPwd(DeviceRegistBean param) {
		UserEntity userEntity = userCache.getUser(param.getUserCode());
		// 用户账号信息不为空，抛出用户不存在异常
		if (userEntity == null) {
			throw new UserNotFoundException();
		}
		// 用户密码信息校验，抛出密码不正确异常
		if (!param.getPassword().equals(userEntity.getPassword())) {
			userCache.invalid(param.getUserCode());
			userEntity = userCache.getUser(param.getUserCode());
			if (!param.getPassword().equals(userEntity.getPassword())) {
				throw new PasswordErrorException();
			}
		}
	}

	/**
	 * @description 操作类型
	 * @return OCB_08
	 * @author 201638
	 * @date 2015-1-19
	 */
	@Override
	public String getOperType() {
		return OcbConstant.OPER_TYPE_OCB_DEVICE_REGIST.VERSION;
	}

	/**
	 * @description 同步接口还是异步接口
	 * @return false 同步接口
	 * @author 201638
	 * @date 2015-1-19
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	public void setDeptCache(DeptCache deptCache) {
		this.deptCache = deptCache;
	}

	public void setDeviceRegistDao(IDeviceRegistDao deviceRegistDao) {
		this.deviceRegistDao = deviceRegistDao;
	}
	//校验参数
	private void validate(AsyncMsg asyncMsg,DeviceRegistBean bean)throws IllegalArgumentException{
		Argument.notNull(asyncMsg, "AsyncMsg");
		Argument.notNull(bean, "DeviceRegistBean");
		//校验用户信息
		Argument.hasText(bean.getUserCode(), "bean.userCode");
		//校验PDA设备号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
	}

	public void setBindingLeasedVehicleService(
			IBindingLeasedVehicleService bindingLeasedVehicleService) {
		this.bindingLeasedVehicleService = bindingLeasedVehicleService;
	}
	

}
