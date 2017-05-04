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
 *  PROJECT NAME  : tfr-management
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/action/CertificateBagAction.java
 *  
 *  FILE NAME          :CertificateBagAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.server.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService;
import com.deppon.foss.module.transfer.management.api.server.service.ICertificateBagService;
import com.deppon.foss.module.transfer.management.api.shared.define.CertificatebagConstant;
import com.deppon.foss.module.transfer.management.api.shared.define.ConfigOrgRelationConstant;
import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagQueryEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.CertificatebagDto;
import com.deppon.foss.module.transfer.management.api.shared.vo.CertificatebagVo;

/**
 * 证件包Action
 * 
 * @author 097457-foss-liming
 * @date 2012-11-6 上午10:11:00
 */
public class CertificateBagAction extends AbstractAction {
	//
	private static final long serialVersionUID = 2613267471653010806L;
	// 证件包Service
	private ICertificateBagService certificateBagService;
	// 证件包vo
	private CertificatebagVo vo = new CertificatebagVo();
	// 日志
	private static final Logger LOGGER = LoggerFactory.getLogger(CertificateBagAction.class);

	private IDepartureService departureService;

	/**
	 * @param departureService the departureService to set Date:2013-4-25下午3:47:57
	 */

	public void setDepartureService(IDepartureService departureService) {
		this.departureService = departureService;
	}

	// 分隔符
	private static final String EQUALS_SEPARATOR = "===============           ";

	/**
	 * 跳转至证件包列表页面，并查询证件包信息
	 * 
	 * @author 097457-foss-liming
	 * @date 2012-11-6 上午10:11:28
	 */
	@JSON
	public String queryCertificateBag() {
		try {
			// 查询证件包信息
			List<CertificatebagEntity> queryCertificateBagList = certificateBagService.queryCertificateBagList(vo.getCertificatebagDto(), start, limit);
			// modify by liangfuxiang 2013-3-18下午3:28:55 begin 去除魔鬼数据
			// LOGGER.info("查询数据 ===============           "+queryCertificateBagList.size());
			LOGGER.info(CertificatebagConstant.QUERY_DATA + EQUALS_SEPARATOR + queryCertificateBagList.size());
			// modify by liangfuxiang 2013-3-18下午3:29:12 end;
			// 放入值
			vo.setCertificatebagList(queryCertificateBagList);
			// 总条数
			Long totalCount = certificateBagService.queryCount(vo.getCertificatebagDto());
			// 放入总条数
			this.setTotalCount(totalCount);
			// 异常处理
		}
		catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 根据车牌号查找证件包明细
	 * 
	 * @author 097457-foss-liming
	 * @date 2012-11-7 晚上19:23:28
	 */
	@JSON
	public String queryCertificateBagByVehicleNo() {
		try {
			// 查找证件包明细
			List<CertificatebagEntity> certificateBagList = certificateBagService.displayCertificateBagDetail(vo.getCertificatebagDto());
			// 证件包列表
			vo.setCertificatebagList(certificateBagList);
		}
		catch (BusinessException e) {
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 归还证件包
	 * 
	 * @author 097457-foss-liming
	 * @date 2012-11-6 上午10:15:12
	 */
	@JSON
	public String returnCertificateBag() {
		try {
			// 归还证件包信息
			certificateBagService.updateCertificateBagStatus(vo.getReturnDto());
		}
		catch (BusinessException e) {
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 领取证件包
	 * 
	 * @author 097457-foss-liming
	 * @date 2012-11-6 上午10:20:21
	 */
	@JSON
	public String takeCertificateBag() {
		try {
			// 领取证件包
			certificateBagService.takeCertificateBag(vo.getTakeDto());
		}
		catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 根据关联refid找到领取证件包信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:36:10
	 */
	@JSON
	public String queryTakeByRefId() {
		try {
			// 根据关联refid找到领取证件包信息
			List<CertificatebagQueryEntity> certificatebagQueryList = certificateBagService.queryTakeRefId(vo.getCertificatebagDto());
			vo.setCertificatebagQueryList(certificatebagQueryList);
		}
		catch (BusinessException e) {
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 根据关联refid找到归还证件包信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:36:10
	 */
	@JSON
	public String queryReturnByRefId() {
		try {
			// 根据关联refid找到归还证件包信息
			List<CertificatebagQueryEntity> certificatebagQueryList = certificateBagService.queryReturnByRefId(vo.getCertificatebagDto());
			// 放入值
			vo.setCertificatebagQueryList(certificatebagQueryList);
		}
		catch (BusinessException e) {
			return returnError(e);
		}

		return returnSuccess();
	}

	// add by liangfuxiang 2013-4-9下午2:33:44 begin:BUG-6732
	/**
	 * 
	 * @Title: convertVehicleCode2Name
	 * @Description: 将英文车牌号转换为中文(例:YUE-X-20000------->粤X20000)
	 * @return 设定文件
	 * @return String 返回类型
	 * @see convertVehicleCode2Name
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-9 下午2:32:24
	 * @throws
	 */
	@JSON
	public String convertVehicleCode2Name() {
		try {
			// 转换
			vo.setVehicleNo(certificateBagService.convertVehicleCode2Name(vo.getVehicleNo()));
		}
		catch (BusinessException e) {
			return returnError(e);
		}

		return returnSuccess();
	}

	// add by liangfuxiang 2013-4-9下午2:34:10 end;

	// add by liangfuxiang 2013-4-9下午5:52:15 begin:BUG-5898 友好性提示：货柜被谁领取，请显示出来
	/**
	 * 
	 * @Title: getContainerTakenInfo
	 * @Description: 返回被领取的货柜信息
	 * @return 设定文件
	 * @return String 返回类型
	 * @see getContainerTakenInfo
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-9 下午5:51:00
	 * @throws
	 */
	@JSON
	public String queryContainerTakenInfo() {
		try {
			// 根据货柜号获取被领取的货柜信息
			CertificatebagDto certificatebagDto = vo.getCertificatebagDto();
			CertificatebagDto newCertificatebagDto = certificateBagService.getContainerTakenInfo(certificatebagDto);
			vo.setCertificatebagDto(newCertificatebagDto);
		}
		catch (BusinessException e) {
			return returnError(e);
		}

		return returnSuccess();
	}

	// add by liangfuxiang 2013-4-9下午5:53:12 end;

	// add by liangfuxiang 2013-4-13上午11:08:41 begin: ISSUE-2093
	/**
	 * 
	 * @Title: queryContainerCardItems
	 * @Description: 根据
	 * @return 查询当前部门的车柜信息
	 * @return String 返回类型
	 * @see queryContainerCardItems
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-13 上午11:09:27
	 * @throws
	 */
	@JSON
	public String queryContainerCardItems() {

		try {
			// 根据货柜号获取被领取的货柜信息
			vo.setConfigOrgRelationEntityList(certificateBagService.queryConfigItemsByConfType(ConfigOrgRelationConstant.CONF_TYPE_CODE_CONTAINERCARD));
		}
		catch (BusinessException e) {
			LOGGER.error("CertificateBagAction[queryContainerCardItems()]:" + e.getMessage());
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 
	 * @Title: queryVehicleItems
	 * @Description: 询当前部门的车辆证件信息
	 * @return
	 * @return String 返回类型
	 * @see queryVehicleItems
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-22 下午2:50:08
	 * @throws
	 */
	@JSON
	public String queryVehicleItems() {
		try {
			// 询当前部门的车辆证件信息
			vo.setConfigOrgRelationEntityList(certificateBagService.queryConfigItemsByConfType(ConfigOrgRelationConstant.CONF_TYPE_CODE_VEHICLE));
		}
		catch (BusinessException e) {
			LOGGER.error("CertificateBagAction[queryContainerCardItems()]:" + e.getMessage());
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 
	 * @Title: queryCarHeadItems
	 * @Description: 根据
	 * @return 查询当前部门的车头证信息
	 * @return String 返回类型
	 * @see queryCarHeadItems
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-13 上午11:09:27
	 * @throws
	 */
	@JSON
	public String queryCarHeadItems() {

		try {
			// 根据货柜号获取被领取的货柜信息
			vo.setConfigOrgRelationEntityList(certificateBagService.queryConfigItemsByConfType(ConfigOrgRelationConstant.CONF_TYPE_CODE_VEHICLEHEADCARD));
		}
		catch (BusinessException e) {
			LOGGER.error("CertificateBagAction[queryCarHeadItems()]:" + e.getMessage());
			return returnError(e);
		}

		return returnSuccess();
	}

	// add by liangfuxiang 2013-4-13上午11:08:50 end;

	// add by liangfuxiang 2013-4-25下午3:25:39 begin ISSUE-2673 ISSUE-2440
	/**
	 * 
	 * @Title: queryVehicleInfoForPrint
	 * @Description: 查询打印信息
	 * @return 设定文件
	 * @return String 返回类型
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-25 下午3:27:51
	 * @throws
	 */
	@JSON
	public String queryVehicleInfoForPrint() {
		try {
			// 查询打印信息
			vo.setVehiclePrintDTO(departureService.queryVehicleInfoForPrint(vo.getVehicleNo()));
		}
		catch (BusinessException e) {
			LOGGER.error("CertificateBagAction[queryVehicleInfoForPrint()]:" + e.getMessage());
			return returnError(e);
		}

		return returnSuccess();
	}

	// add by liangfuxiang 2013-4-25下午3:25:56 end;
	/**
	 * 获取 证件包Vo.
	 * 
	 * @return the 证件包Vo.
	 */
	public CertificatebagVo getVo() {
		return vo;
	}

	/**
	 * 放入 证件包Vo.
	 * 
	 * @return the 证件包Vo.
	 */
	public void setVo(CertificatebagVo vo) {
		this.vo = vo;
	}

	/**
	 * 获取 证件包Service
	 * 
	 * @return the 证件包Service.
	 */
	public void setCertificateBagService(ICertificateBagService certificateBagService) {
		this.certificateBagService = certificateBagService;
	}

	// modify by liangfuxiang 2013-5-17下午2:51:49 begin TE-1733
	/**
	 * 
	 * @Title: convertVehicleCode2Name
	 * @Description: 将英文车牌号转换为中文(例:YUE-X-20000------->粤X20000)
	 * @return 设定文件
	 * @return String 返回类型
	 * @see convertVehicleCode2Name
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-9 下午2:32:24
	 * @throws
	 */
	@JSON
	public String convertVehicleCode2NameForQuery() {
		try {
			// 转换
			vo.setVehicleNo(certificateBagService.convertVehicleCode2NameForQuery(vo.getVehicleNo()));
		}
		catch (BusinessException e) {
			return returnError(e);
		}

		return returnSuccess();
	}
	// modify by liangfuxiang 2013-5-17下午2:54:02 end TE-1733; 
}