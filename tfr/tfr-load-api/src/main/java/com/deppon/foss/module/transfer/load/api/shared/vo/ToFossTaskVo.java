package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.io.Serializable;
import java.util.List;

public class ToFossTaskVo implements Serializable {

	/**
	 * @fields serialVersionUID
	 * @author 322360-ecs-hehao
	 * @update 2016年4月20日 下午6:27:52
	 * @version V1.0
	 */

	private static final long serialVersionUID = -2881202750708603763L;

	private List<ToFossTaskDetailVo> toFossTaskDetailDtoList; // 获取装车任务明细
	private List<ToFossWaybillVo> toFossWaybillDtoList; // 获取运单信息

	
	/**
	* @description 返回获取装车任务明细
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月23日 下午2:26:41
	*/
	public List<ToFossTaskDetailVo> getToFossTaskDetailDtoList() {
		return toFossTaskDetailDtoList;
	}

	
	/**
	* @description 设置获取装车任务明细
	* @param toFossTaskDetailDtoList
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月23日 下午2:26:44
	*/
	public void setToFossTaskDetailDtoList(List<ToFossTaskDetailVo> toFossTaskDetailDtoList) {
		this.toFossTaskDetailDtoList = toFossTaskDetailDtoList;
	}

	
	/**
	* @description 返回获取运单信息
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月23日 下午2:26:46
	*/
	public List<ToFossWaybillVo> getToFossWaybillDtoList() {
		return toFossWaybillDtoList;
	}

	
	/**
	* @description 设置获取运单信息
	* @param toFossWaybillDtoList
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月23日 下午2:26:49
	*/
	public void setToFossWaybillDtoList(List<ToFossWaybillVo> toFossWaybillDtoList) {
		this.toFossWaybillDtoList = toFossWaybillDtoList;
	}

}
