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
 *  PROJECT NAME  : tfr-common
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/server/action/TfrJobProcessLogAction.java
 *  
 *  FILE NAME          :TfrJobProcessLogAction.java
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

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobProcessLogService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobProcessService;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.api.shared.vo.TfrJobProcessLogVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;

/**
 * JOB日志查询
 * 
 * @author foss-yuyongxiang
 * @date 2013年5月2日 10:00:55
 */
public class JobProcessLogAction extends AbstractAction {

	private static final long serialVersionUID = 3063185546809456467L;

	/**
	 * service 自动注入
	 */
	private ITfrJobProcessLogService tfrJobProcessLogService;	
	/**
	 * service 自动注入
	 */
	private ITfrJobProcessService tfrJobProcessService;

	/**
	 * JOB日志查询VO
	 */
	private TfrJobProcessLogVo vo;

	/**
	 * 初始化页面
	 * 
	 * @author foss-yuyongxiang
	 * @date 2013年5月4日 10:52:29
	 */
	public String jobProcessLogIndex() {
		return SUCCESS;
	}
	
	/************************** jobprocessLog start ****************************/

	/**
	 * 查询 JOB_PROCESS_LOG 表所有符合条件的数据
	 * 
	 * @author foss-yuyongxiang
	 * @date 2013年5月3日 15:02:40
	 * @return josn
	 */
	@JSON
	public String jobProcessLogsList() {
		try {

			// Vo 不能为空
			if (null == vo) {
				vo = new TfrJobProcessLogVo();
			}

			// 设置默认的查询条数
			if (super.limit == 0) {
				super.limit = ConstantsNumberSonar.SONAR_NUMBER_10;
			}

			// 查询list
			List<TfrJobProcessLogEntity> list = tfrJobProcessLogService
					.getJobProcessLogsList(vo, super.start, super.limit);
			// 填充JOB实体对象LIST
			vo.setJobProcessLogEList(list);

			//填充count
			super.setTotalCount(tfrJobProcessLogService
					.getJobProcessLogscount(vo));

			return super.returnSuccess();
		} catch (TfrBusinessException e) {
			e.printStackTrace();
			return super.returnError(e);
		}
	}

	/**
	 * 查询 JOB_PROCESS_LOG 表指定ID的数据
	 * 
	 * @author foss-yuyongxiang
	 * @date 2013年5月7日 11:09:04
	 * @return josn
	 */
	@JSON
	public String jobProcessLogById() {
		try {

			// Vo 不能为空
			if (null == vo) {
				vo = new TfrJobProcessLogVo();
			}

			// 查询JOBLOG的实体并且设置
			vo.setJobProcessLogE(tfrJobProcessLogService
					.getJobProcessLogByID(vo));

			return super.returnSuccess();
		} catch (TfrBusinessException e) {
			e.printStackTrace();
			return super.returnError(e);
		}
	}
	/************************** jobprocessLog end ****************************/
	/************************** jobprocess start ****************************/
	/**
	 * 查询 JOB_PROCESS 表所有符合条件的数据
	 * 
	 * @author foss-yuyongxiang
	 * @date 2013年5月13日 09:27:00
	 * @return josn
	 */
	@JSON
	public String jobProcessList() {
		try {

			// Vo 不能为空
			if (null == vo) {
				vo = new TfrJobProcessLogVo();
			}

			// 设置默认的查询条数
			if (super.limit == 0) {
				super.limit = ConstantsNumberSonar.SONAR_NUMBER_10;
			}

			// 查询list
			List<TfrJobProcessEntity> list = tfrJobProcessService
					.getJobProcessList(vo, super.start, super.limit);
			// 填充JOB实体对象LIST
			vo.setJobProcessEList(list);

			//填充count
			super.setTotalCount(tfrJobProcessService
					.getJobProcesscount(vo));

			return super.returnSuccess();
		} catch (TfrBusinessException e) {
			e.printStackTrace();
			return super.returnError(e);
		}
	}

	/**
	 * 查询 JOB_PROCESS_LOG 表指定ID的数据
	 * 
	 * @author foss-yuyongxiang
	 * @date 2013年5月13日 09:27:05
	 * @return josn
	 */
	@JSON
	public String jobProcessById() {
		try {

			// Vo 不能为空
			if (null == vo) {
				vo = new TfrJobProcessLogVo();
			}

			// 查询JOBLOG的实体并且设置
			vo.setJobProcessE(tfrJobProcessService
					.getJobProcessByID(vo));

			return super.returnSuccess();
		} catch (TfrBusinessException e) {
			e.printStackTrace();
			return super.returnError(e);
		}
	}
	
	
	/************************** jobprocess end ****************************/
	

	/************************** getter AND setter ****************************/

	/**
	 * @param tfrJobProcessLogService
	 *            the tfrJobProcessLogService to set
	 */
	public void setTfrJobProcessLogService(
			ITfrJobProcessLogService tfrJobProcessLogService) {
		this.tfrJobProcessLogService = tfrJobProcessLogService;
	}

	/**
	 * @return the vo
	 */
	public TfrJobProcessLogVo getVo() {
		return vo;
	}

	/**
	 * @param vo
	 *            the vo to set
	 */
	public void setVo(TfrJobProcessLogVo vo) {
		this.vo = vo;
	}
	
	/**
	 * @param tfrJobProcessService the tfrJobProcessService to set
	 */
	public void setTfrJobProcessService(ITfrJobProcessService tfrJobProcessService) {
		this.tfrJobProcessService = tfrJobProcessService;
	}

}