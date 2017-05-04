/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/action/AbandonGoodsApplicationAction.java
 * 
 * FILE NAME        	: AbandonGoodsApplicationAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.action;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IAbandonGoodsApplicationService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonGoodsApplicationDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.AbandonGoodsApplicationVo;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.AbandonedGoodsSearchVo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;

/**
 * 弃货处理Action
 * 
 * @author ibm-lizhiguo
 * @date 2012-10-25 下午5:17:11
 */
public class AbandonGoodsApplicationAction extends AbstractAction {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 视图对象
	 */
	private AbandonGoodsApplicationVo vo = new AbandonGoodsApplicationVo();

	/**
	 * 视图对象
	 */
	private AbandonedGoodsSearchVo abandonedGoodsSearchVo = new AbandonedGoodsSearchVo();

	/**
	 * 弃货处理service
	 */
	private IAbandonGoodsApplicationService abandonGoodsApplicationService;

	/**
	 * 开单管理service
	 */
	private IWaybillManagerService waybillManagerService;

	/**
	 * 部门服务
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * get vo
	 * 
	 * @return
	 */
	public AbandonGoodsApplicationVo getVo() {
		return vo;
	}

	/**
	 * set对象
	 */
	public void setVo(AbandonGoodsApplicationVo vo) {
		this.vo = vo;
	}

	/**
	 * get对象
	 */
	public AbandonedGoodsSearchVo getAbandonedGoodsSearchVo() {
		return abandonedGoodsSearchVo;
	}

	/**
	 * set对象
	 */
	public void setAbandonedGoodsSearchVo(AbandonedGoodsSearchVo abandonedGoodsSearchVo) {
		this.abandonedGoodsSearchVo = abandonedGoodsSearchVo;
	}

	/**
	 * set对象
	 */
	public void setAbandonGoodsApplicationService(IAbandonGoodsApplicationService abandonGoodsApplicationService) {
		this.abandonGoodsApplicationService = abandonGoodsApplicationService;
	}

	/**
	 * set对象
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * @param orgAdministrativeInfoService : set the property
	 *            orgAdministrativeInfoService.
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 
	 * 获得弃货LIST信息
	 * 
	 * @author ibm-lizhiguo
	 * @date 2012-10-29 下午3:19:14
	 */
	@JSON
	public String searchAbandonGoodsList() {
		try {
			//查询获得弃货
			abandonedGoodsSearchVo.setAbandonGoodsResultDtoList(abandonGoodsApplicationService.searchAbandonGoodsList(abandonedGoodsSearchVo.getAbandonedGoodsSearchDto()));
			//total count
			this.setTotalCount(totalCount);
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		// 返回成功
		return returnSuccess();
	}

	/**
	 * 
	 * 转弃货申请
	 * 
	 * @author ibm-lizhiguo
	 * @date 2012-10-29 下午3:19:22
	 */
	@JSON
	public String createAbandonGoodsApplication() {
		try {
			abandonGoodsApplicationService.insertAbandonGoodsApplication(vo.getAbandonGoodsApplicationEntity(), vo.getAttachementFiles());
		}  catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		// 返回成功
		return returnSuccess();
	}

	/**
	 * 
	 * 页面上点击查看弃货信息，获得该弃货的详细信息
	 * 
	 * @author ibm-lizhiguo
	 * @date 2012-10-30 上午10:36:02
	 */
	@JSON
	public String searchAbandonGoodsDetail() {
		try {
			//查询获得弃货详细信息
			abandonedGoodsSearchVo.setAbandonedGoodsDetailDto(abandonGoodsApplicationService.getAbandonGoodsDetailById(abandonedGoodsSearchVo.getId()));
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		// 返回成功
		return returnSuccess();
	}

	/**
	 * 
	 * 转弃货申请获得转弃货信息，更具运单号
	 * 
	 * @author ibm-lizhiguo
	 * @date 2012-11-9 下午4:48:47
	 */
	@JSON
	public String searchAbandonGoodsApplicationInfo() {
		try {
			AbandonGoodsApplicationDto dto = vo.getAbandonGoodsApplicationDto();
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
			if (waybillEntity == null || "".equals(waybillEntity.getId())) {
				throw new BusinessException("该运单不存在");
			}
			//获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 异常_ID--页面
			// 仓储时长(天)--前台页面获得
			// 操作人--前台页面获得
			// 始发部门名称--前台页面获得
			// 运单号----前台页面获得
			// 始发部门CODE
			dto.setOrigOrgName(getNameByCode(dto.getOrigOrgCode()));
			// 发货人(运单--发货客户名称)
			dto.setDeliveryCustomerContact(waybillEntity.getDeliveryCustomerContact());
			//			dto.setDeliveryCustomerName(waybillEntity.getDeliveryCustomerName());
			// 发货人手机(运单--发货客户手机)
			dto.setDeliveryCustomerMobilephone(waybillEntity.getDeliveryCustomerMobilephone());
			// 体积(方)--(运单--货物总体积)
			dto.setGoodsVolumeTotal(waybillEntity.getGoodsVolumeTotal());

			// 入库时间--(运单库存--入库时间)
			dto.setInStockTime(abandonGoodsApplicationService.getInStockTimeByWaybillNoAndOrgCode(dto.getWaybillNo(), currentInfo.getCurrentDeptCode()));
			vo.setAbandonGoodsApplicationDto(dto);

		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		// 返回成功
		return returnSuccess();
	}

	/**
	 * 保存导入内部带货
	 * 
	 * @date 2012-11-9 下午4:48:47
	 */
	@JSON
	public String createAbandonGoodsImport() {
		try {
			//保存导入内部带货
			abandonGoodsApplicationService.createAbandonGoodsImport(vo.getId());
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		// 返回成功
		return returnSuccess();
	}

	/**
	 * 弃货工作流 启动工作流接口调用结果
	 * 
	 * @date 2012-11-9 下午4:48:47
	 */
	@JSON
	public String startDiscardWorkflow() {
		try {
			//弃货工作流
			vo.setResultDto(abandonGoodsApplicationService.startOaDiscardWorkflow(vo.getId()));
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		// 返回成功
		return returnSuccess();
	}

	/**
	 * 定时任务测试用
	 * 
	 * @date 2012-11-9 下午4:48:47
	 */
	@JSON
	public String preprocess() {
		try {
			//定时任务测试用
			abandonGoodsApplicationService.preprocess();
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		// 返回成功
		return returnSuccess();
	}

	/**
	 * 
	 * <p>
	 * 根据CODE获得Name<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-2-22
	 * @param code
	 * @return String
	 */
	private String getNameByCode(String code) {
		//获得部门--发货部门
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(code);
		if (org != null) {
			return org.getName();
		}
		return null;
	}
	@JSON
	public String editAbandonGoodsApplication(){
		try{
			abandonGoodsApplicationService.editAbandonGoodAttachFiles(vo.getAbandonGoodsApplicationEntity(), vo.getAttachementFiles());
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 处理弃货加载时做的一些处理
	 * @return
	 */
	@JSON
	public String processAbandonGoods(){
		try{
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
}