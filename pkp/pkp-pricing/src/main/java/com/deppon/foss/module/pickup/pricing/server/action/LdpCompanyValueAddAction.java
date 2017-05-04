package com.deppon.foss.module.pickup.pricing.server.action;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.dict.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.server.service.IldpCompanyValueAddService;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.LdpCompanyValueAddedServiceVo;
import com.deppon.foss.util.DateUtils;

/**
 * 快递代理公司增值服务Action
 * 
 * @author WangPeng
 * @date 2013-08-14 11:43 AM
 * 
 */
public class LdpCompanyValueAddAction extends AbstractAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7776726059811433975L;
	/**
	 * 
	 * 日志处理
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LdpCompanyValueAddAction.class);

	// 前后台交互传递参数的对象
	LdpCompanyValueAddedServiceVo objectVo = new LdpCompanyValueAddedServiceVo();

	// 注入service接口
	IldpCompanyValueAddService ldpCompanyValueAddService;

	public LdpCompanyValueAddedServiceVo getObjectVo() {
		return objectVo;
	}

	public void setObjectVo(LdpCompanyValueAddedServiceVo objectVo) {
		this.objectVo = objectVo;
	}

	public void setLdpCompanyValueAddService(
			IldpCompanyValueAddService ldpCompanyValueAddService) {
		this.ldpCompanyValueAddService = ldpCompanyValueAddService;
	}

	/**
	 * 根据条件查询快递代理公司增值服务信息
	 * 
	 * @author WangPeng
	 * @Date 2013-8-14 上午10:47:22
	 * @param entity
	 * @param start
	 * @param limit
	 * @return List<PartbussValueAddEntity>
	 * 
	 * 
	 */
	@JSON
	public String queryEntityByParams() {
		try {
			objectVo.setPartbussValueAddEntityList(ldpCompanyValueAddService
					.queryEntityByParams(objectVo.getPartbussValueAddEntity(),
							start, limit));
			this.setTotalCount(ldpCompanyValueAddService
					.countQueryRecord(objectVo.getPartbussValueAddEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("根据条件查询快递代理公司增值服务信息: " + e.getMessage());
			return returnError(e.getMessage());
		}
	}

	/**
	 * 新增一条快递代理公司增值服务信息
	 * 
	 * @author WangPeng
	 * @Date 2013-8-14 上午10:48:48
	 * @param entity
	 * @return int 1:成功 -1：失败
	 * 
	 */
	@JSON
	public String addNewPartbussValueAddEntity() {
		try {
			  // 1：成功；-1：失败
		    int returnInt = ldpCompanyValueAddService.addNewPartbussValueAddEntity(objectVo
					.getPartbussValueAddEntity());
		    objectVo.setReturnInt(returnInt);
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("新增一条快递代理公司增值服务信息: " + e.getMessage());
			return returnError(e.getMessage());
		}
	}

	/**
	 * 根据id去删除快递代理公司增值服务信息
	 * 
	 * @author WangPeng
	 * @Date 2013-8-14 上午10:50:40
	 * @param ids
	 * @return int 1:成功 -1：失败
	 * 
	 */
	@JSON
	public String deletePartbussValueAddEntity() {
		try {
			  // 1：成功；-1：失败
		    int returnInt =  ldpCompanyValueAddService.deletePartbussValueAddEntity(objectVo
					.getIds());
		    objectVo.setReturnInt(returnInt);
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("根据id去删除快递代理公司增值服务信息: " + e.getMessage());
			return returnError(e.getMessage());
		}
	}

	/**
	 * 根据id去激活快递代理公司增值服务信息
	 * 
	 * @author WangPeng
	 * @Date 2013-8-14 上午10:50:40
	 * @param ids
	 * @return int 1:成功 -1：失败
	 * 
	 */
	@JSON
	public String activeValueAddedServices() {
		try {
			 // 1：成功；-1：失败
		    int returnInt =  ldpCompanyValueAddService.activeValueAddedServices(objectVo
					.getIds());
			objectVo.setReturnInt(returnInt);
			return returnSuccess(MessageType.ACTIVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("根据id去激活快递代理公司增值服务信息: " + e.getMessage());
			return returnError(e.getMessage());
		}
	}

	/**
	 * 更新快递代理公司增值服务信息
	 * 
	 * @author WangPeng
	 * @Date 2013-8-14 上午10:53:40
	 * @param entity
	 * @return int 1:成功 -1：失败
	 * 
	 */
	@JSON
	public String updatePartbussValueAddEntity() {
		try {
			 // 1：成功；-1：失败
		    int returnInt =  ldpCompanyValueAddService.updatePartbussValueAddEntity(objectVo
					.getPartbussValueAddEntity());
		    objectVo.setReturnInt(returnInt);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("更新快递代理公司增值服务信息: " + e.getMessage());
			return returnError(e.getMessage());
		}
	}

	/**
	 * 立即激活快递代理公司增值服务信息
	 * 
	 * @author WangPeng
	 * @Date 2013-8-14 上午10:56:58
	 * @param id
	 * @return int 1:成功 -1：失败
	 * 
	 */
	@JSON
	public String activeImmediatelyValueAddedServices() {
		try {
			 // 1：成功；-1：失败
		    int returnInt =  ldpCompanyValueAddService
					.activeImmediatelyValueAddedServices(objectVo
							.getPartbussValueAddEntity());
		    objectVo.setReturnInt(returnInt);
			return returnSuccess("立即激活成功！");
		} catch (BusinessException e) {
			LOGGER.error("立即激活快递代理公司增值服务信息: " + e.getMessage());
			return returnError(e.getMessage());
		}
	}

	/**
	 * 立即终止快递代理公司增值服务信息
	 * 
	 * @author WangPeng
	 * @Date 2013-8-14 上午10:57:04
	 * @param id
	 * @return int 1:成功 -1：失败
	 * 
	 */
	@JSON
	public String inActiveImmediatelyValueAddedServices() {
		try {
			 // 1：成功；-1：失败
		    int returnInt =  ldpCompanyValueAddService
					.inActiveImmediatelyValueAddedServices(objectVo
							.getPartbussValueAddEntity());
		    objectVo.setReturnInt(returnInt);
			return returnSuccess("立即终止成功！");
		} catch (BusinessException e) {
			LOGGER.error("立即终止快递代理公司增值服务信息: " + e.getMessage());
			return returnError(e.getMessage());
		}
	}

	/**
	 * 获取当前服务器时间
	 * 
	 * @author WangPeng
	 * @Date 2013-8-14 下午3:44:56
	 * @return String ：
	 * 
	 */
	@JSON
	public String getAppCurrentTime() {
		try {
			Date begintime=DateUtils.convert(DateUtils.convert(new Date(),DateUtils.DATE_FORMAT),DateUtils.DATE_FORMAT);//当天的0点00分00秒
			objectVo.setNowTime(begintime);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("获取服务器当前时间: " + e.getMessage());
			return returnError(e.getMessage());
		}
	}
}
