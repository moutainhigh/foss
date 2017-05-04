/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
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
 *  PROJECT NAME  : tfr-departure
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/server/action/VehicleInfoAction.java
 *  
 *  FILE NAME          :VehicleInfoAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.server.action;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.departure.api.server.service.IVehicleInfoService;
import com.deppon.foss.module.transfer.departure.api.shared.dto.VehicleDetailDTO;
import com.deppon.foss.module.transfer.departure.api.shared.vo.VehicleInfoVO;
/**
 * 
 * 1、 点击查询车辆放行情况界面的【查询条件结果显示】中的车牌号字段，
 * 链接车辆明细弹出框。 2、 【车辆明细】界面信息： 2.1
 * 【车辆信息】：车牌号
 * 、车辆归属类型、车辆业务类型、车辆归属部门、车型、载重/
 * 净空、是否安装GPS，来源车辆基础资料。 2.2
 * 【联系信息】：司机、司机电话
 * 、车队部门经理电话，司机、电话来源参见“通过车牌号查找司机信息
 * ”；车队部门经理电话来源车辆基础资料。 2.3
 * 【车辆任务信息】：信息来源交接单/配载单/派送任务单。
 * 任务标识号：生成规则参见SR-11。 货柜号：公司长途车辆才有货柜号。
 * 2.4
 * 【申请放行】出发放行类型、放行事项、申请部门、申请时间、申请人、备注
 * 、放行方式
 * ，信息来源系统用例“申请车辆放行（自动）、申请车辆放行（人工）”。 2.5
 * 【出发/到达信息】：信息来源车辆出发信息、车辆到达信息，参见系统用例“
 * 申请车辆放行（自动）、查看车辆到达情况”，车辆状态参见SR- 2.6
 * 【在途信息】：信息来源在途跟踪，参见系统用例“GPS获取在途跟踪信息、
 * 查询待跟踪车辆”。 2.7
 * 【车辆明细】字段的填充规则，参见数据元素1.8.3。 2.8
 * 车辆明细中的信息，随着业务操作开展，信息依次填充显示。 3、 功能按钮 
 * 打印车辆条码： 3.1 点击打印车辆条码按钮，按照条码打印模板，
 * 将车牌号及打印时间记录在放行条码中。 3.2 模板：
 * 条码扫描区：包含车牌号及打印时间。 文本显示区：车牌号及打印时间。
 * 德邦logo标志。 具体参见上图。
 * 颜色美工来定。依据打印机而定，是否支持彩打。
 * 打印模板边缘需留一定的边框，便于人工张贴。
 * （说明：扫描区分布不影响扫描率，已咨询PDA推广员王辉）。  关闭
 * 2.1
 * 点击关闭按钮，假如已录入的车牌号，未执行打印车辆条码时，系统提示：“
 * 请确认是退出，还是打印？”，假如录入框为空时，直接关闭页面，引用SR-7。
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2013-3-19 上午10:55:18
 */
public class VehicleInfoAction extends AbstractAction {
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager
			.getLogger(VehicleInfoAction.class);
	/**
	 * 通过车牌号查询车辆的信息，需要调用接口.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-8 下午2:06:58
	 */
	@JSON
	public String queryVehicleInfoInfo() {
		try {
			vehicleInfoVO.setVehicleInfoEntity(vehicleInfoService
					.getVehicleInfo(vehicleInfoVO.getQueryEntity()
							.getVehicleNo()));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}
	/**
	 * 通过车牌号查询司机的信息，需要调用接口.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-8 下午2:07:30
	 */
	@JSON
	public String queryRelationInfo() {
		try {
			
			String vehicleNo = vehicleInfoVO.getQueryEntity().getVehicleNo();
			String id = vehicleInfoVO.getQueryEntity().getId();
			if(StringUtils.isEmpty(id)) {
				vehicleInfoVO.setRelationInfoEntity(vehicleInfoService.getRelationInfo(vehicleNo));
			} else {
				vehicleInfoVO.setRelationInfoEntity(vehicleInfoService.getRelationInfo(vehicleNo, id));
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}
	/**
	 * 查询车辆业务信息.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-26 下午2:36:49
	 */
	@JSON
	public String queryBusinessInfo() {
		try {
			VehicleDetailDTO dto = new VehicleDetailDTO();
			if ("no".equals(vehicleInfoVO.getQueryEntity().getIsDepart())) {
				dto.setTaskDetailId(vehicleInfoVO.getQueryEntity().getId());
			} else {
				dto.setDepartId(vehicleInfoVO.getQueryEntity().getId());
			}
			vehicleInfoVO.setBusinessInfoEntity(vehicleInfoService
					.getBusinessInfo(dto));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}
	/**
	 * 查询车辆交接单配载单信息.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-26 下午2:37:18
	 */
	@JSON
	public String queryTaskBill() {
		try {
			VehicleDetailDTO dto = new VehicleDetailDTO();
			if ("no".equals(vehicleInfoVO.getQueryEntity().getIsDepart())) {
				dto.setTaskDetailId(vehicleInfoVO.getQueryEntity().getId());
				
				//zyx
				vehicleInfoVO.setTaskBillSummaryList(vehicleInfoService.queryTaskBillSummary(dto));
				//zyx end
			} else {
				dto.setDepartId(vehicleInfoVO.getQueryEntity().getId());
			}
			vehicleInfoVO.setTaskBillList(vehicleInfoService.queryTaskBill(dto));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}
	/**
	 * 申请放行信息.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-23 下午2:04:57
	 */
	@JSON
	public String queryDepartInfo() {
		try {
			VehicleDetailDTO dto = new VehicleDetailDTO();
			if ("no".equals(vehicleInfoVO.getQueryEntity().getIsDepart())) {
				dto.setTaskDetailId(vehicleInfoVO.getQueryEntity().getId());
			} else {
				dto.setDepartId(vehicleInfoVO.getQueryEntity().getId());
			}
			vehicleInfoVO.setDepartInfoEntity(vehicleInfoService
					.getDepartInfo(dto));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}
	/**
	 * 出发到达信息.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-26 下午2:37:51
	 */
	@JSON
	public String queryArrivalInfo() {
		try {
			VehicleDetailDTO dto = new VehicleDetailDTO();
			if ("no".equals(vehicleInfoVO.getQueryEntity().getIsDepart())) {
				dto.setTaskDetailId(vehicleInfoVO.getQueryEntity().getId());
			} else {
				dto.setDepartId(vehicleInfoVO.getQueryEntity().getId());
			}
			vehicleInfoVO.setArrivalInfoEntity(vehicleInfoService
					.getArrivalInfo(dto));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}
	/**
	 * 在途信息.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-26 下午2:38:04
	 */
	@JSON
	public String queryOnTheWayInfo() {
		try {
			VehicleDetailDTO dto = new VehicleDetailDTO();
			if ("no".equals(vehicleInfoVO.getQueryEntity().getIsDepart())) {
				dto.setTaskDetailId(vehicleInfoVO.getQueryEntity().getId());
			} else {
				dto.setDepartId(vehicleInfoVO.getQueryEntity().getId());
			}
			vehicleInfoVO.setOnTheWayInfoEntity(vehicleInfoService
					.getOnTheWayInfo(dto));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}
	/**
	 * ******************** 默认配置
	 * **********************.
	 */
	private static final long serialVersionUID = -2956858602690171822L;
	/** ***** 车辆明细信息的接口 ******. */
	private IVehicleInfoService vehicleInfoService;
	/** ***** 底层数据结构******. */
	private VehicleInfoVO vehicleInfoVO = new VehicleInfoVO();
	/**
	 * Sets the vehicle info service.
	 * 
	 * @param vehicleInfoService
	 *            the new vehicle info
	 *            service
	 */
	public void setVehicleInfoService(IVehicleInfoService vehicleInfoService) {
		this.vehicleInfoService = vehicleInfoService;
	}
	/**
	 * Gets the vehicle info vo.
	 * 
	 * @return the vehicle info vo
	 */
	public VehicleInfoVO getVehicleInfoVO() {
		return vehicleInfoVO;
	}
	/**
	 * Sets the vehicle info vo.
	 * 
	 * @param vehicleInfoVO
	 *            the new vehicle info
	 *            vo
	 */
	public void setVehicleInfoVO(VehicleInfoVO vehicleInfoVO) {
		this.vehicleInfoVO = vehicleInfoVO;
	}
}