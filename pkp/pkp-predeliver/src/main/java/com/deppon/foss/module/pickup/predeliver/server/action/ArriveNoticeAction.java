package com.deppon.foss.module.pickup.predeliver.server.action;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveNoticeService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.NotifyCustomerVo;

/**
 * 计划提前通知
* @ClassName: ArriveNoticeAction 
* @author 329757-foss-liuxiangcheng 
* @date 2016-6-15 下午5:26:05 
*
 */
public class ArriveNoticeAction extends AbstractAction{

	/**
	 * serial version Id
	 */
	private static final long serialVersionUID = 2L;
	
	/**
	 * 客户通知VO
	 */
	private NotifyCustomerVo vo = new NotifyCustomerVo();
	/**
	 *派送提前通知Service
	 */
	private IArriveNoticeService arriveNoticeService;
	

	/**
	 * 根据查询条件查询通知信息
	* @Title: queryBeforeNotices 
	* @author 329757-foss-liuxiangcheng 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@JSON
	public String queryArriveNotices() {
		try {
			// 查询符合条件的运单总记录数 
			Long count= this.arriveNoticeService.queryWaybillInfoCount(vo.getConditionDto());
			// 根据运单总记录数
			if (count != null && count>0) {
				this.setTotalCount(count);
				// 查询符合条件的记录列表
				List<NotifyCustomerDto> list = this.arriveNoticeService.queryWaybillInfoList(vo.getConditionDto(), this.getStart(), this.getLimit());
				vo.setDtoList(list);
			} else {
				vo.setDtoList(null);
				// 设置页面显示的记录总数
				this.setTotalCount(Long.valueOf(0));
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	/**
	 * 
	 * 保存派送提前通知信息
	 *  @author 159231-foss-meiying
	 * @date 2013-12-27 上午11:03:57
	 */
	@JSON
	public String addArriveNotice() {
		try {
			// 判断vo属性
			if (vo != null) {
				// 新增通知相关信息
				this.arriveNoticeService.addNotificationInfo(vo.getConditionDto());
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	
	/**
	 * 
	 * 批量保存派送提前通知信息
	 *  @author 159231-foss-meiying
	 * @date 2014-5-26 上午11:03:57
	 */
	@JSON
	public String arriveNoticeBatchNotify() {
		try {
			// 判断vo属性
			if (vo != null) {
				// 新增通知相关信息
				this.arriveNoticeService.batchNotify(vo.getConditionDto());
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	
	
	/**
	 * @return 
	 * 		the vo
	 */
	public NotifyCustomerVo getVo() {
		return vo;
	}

	
	
	public void setArriveNoticeService(IArriveNoticeService arriveNoticeService) {
		this.arriveNoticeService = arriveNoticeService;
	}


	public void setVo(NotifyCustomerVo vo) {
		this.vo = vo;
	}
	
	
}
