package com.deppon.foss.module.transfer.load.server.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverBaseDto;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadManagerExceptionService;
import com.deppon.foss.module.transfer.load.api.shared.vo.LoadManagerExceptionVo;

/**
 * 装车任务异常查询界面.
 *
 * @author dp-ruilibao
 * @date 2017-02-26 下午3:00:36
 */
public class LoadManagerExceptionAction extends AbstractAction {

	/** 
	 * The Constant serialVersionUID. 
	 */
	private static final long serialVersionUID = -3415916257657518326L;
	/**
	 * 页面VO传入数据
	 */
	private LoadManagerExceptionVo loadManagerExceptionVo;
	
	/**
	 * 装车异常查询界面
	 */
	private ILoadManagerExceptionService loadManagerExceptionService;
	/**
	 * 记录日志
	 */
	public final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 功能：查询装车异常记录，在页面中进行修改
	 * 
	 * @return the string
	 */
	public String queryLoadManagerException() {
		try {
			// 查询总记录
			Long totCount = loadManagerExceptionService.queryLoadManagerExceptionCount(loadManagerExceptionVo);
			this.setTotalCount(totCount);
			if (totCount > 0) {
				// 查询记录用于返回页面
				loadManagerExceptionVo.setReturnList(loadManagerExceptionService
						.queryLoadManagerException(loadManagerExceptionVo, this.getLimit(), this.getStart()));
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
		return this.returnSuccess();
	}
	
	
	public String editLoadTaskVehicleNo(){
		try{
			loadManagerExceptionService.editLoadTaskVehicleNo(loadManagerExceptionVo);
		}catch(BusinessException e){
			return returnError(e);
		}
		return this.returnSuccess();
	}

	
	/**
	 * 获取司机信息
	 * @author 332209-FOSS-ruilibao
	 * @date 2017年4月5日
	 * @return
	 */
	public String queryDriverMsgByVehicleNo(){
		
		//获取传入的车牌号
		String vehicleNo = loadManagerExceptionVo.getVehicleNo();
		try{
			//调用service，获取车牌关联的司机信息
			DriverBaseDto driverInfo = loadManagerExceptionService.queryDriverInfoByVehicleNo(vehicleNo);
			//返回司机信息
			loadManagerExceptionVo.setDriverBaseDto(driverInfo);
		}catch(Exception e){
			logger.error(e.getMessage()+"----"+e.getStackTrace());
			//此处忽略一切异常
			return returnSuccess();
		}
		//返回success
		return SUCCESS;
	}
	

	/**
	 * @param loadManagerExceptionService the loadManagerExceptionService to set
	 */
	public void setLoadManagerExceptionService(ILoadManagerExceptionService loadManagerExceptionService) {
		this.loadManagerExceptionService = loadManagerExceptionService;
	}
	
	public void setLoadManagerExceptionVo(LoadManagerExceptionVo loadManagerExceptionVo) {
		this.loadManagerExceptionVo = loadManagerExceptionVo;
	}
	
	public LoadManagerExceptionVo getLoadManagerExceptionVo() {
		return loadManagerExceptionVo;
	}
}