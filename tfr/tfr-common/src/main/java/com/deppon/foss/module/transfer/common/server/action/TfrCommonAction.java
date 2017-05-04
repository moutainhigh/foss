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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/server/action/TfrCommonAction.java
 *  
 *  FILE NAME          :TfrCommonAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
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
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.server.action;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.api.shared.vo.TfrCommonVO;

/**
 * 处理中转常用页面公共处理
 * @author foss-wuyingjie
 * @date 2012-11-23 上午11:07:11
 */
public class TfrCommonAction extends AbstractAction{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2199452128756658178L;
	/**
	 * 注释service
	 */
	private ITfrCommonService tfrCommonService;
	/**
	 * tfrCommonVO
	 */
	private TfrCommonVO tfrCommonVO;

	/**
	 * 获取相关下拉框，传入PARMA_CODE
	 * 
	 * @author foss-wuyingjie
	 * 
	 * @date 2012-11-23 上午11:12:54
	 * 
	 */
	public String loadDictDataCombox(){
		try{
			List<BaseDataDictDto> baseDataDictDtoList = tfrCommonService.loadDictDataCombox(tfrCommonVO.getTermsCode());

			tfrCommonVO.setBaseDataDictDtoList(baseDataDictDtoList);

			return returnSuccess();
		}catch(TfrBusinessException e){
			return super.returnError(e);
		}
	}
	/**
	 * 获取相关下拉框，没有默认项
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-3 下午8:27:34
	 * 
	 * 
	 * 
	 */
	public String loadDictDataComboxNoDefault(){
		if(null ==tfrCommonVO){
			throw new TfrBusinessException("查询参数为空");
		}
		List<BaseDataDictDto> baseDataDictDtoList = tfrCommonService.loadDictDataComboxNoDefault(tfrCommonVO.getTermsCode());
		tfrCommonVO.setBaseDataDictDtoList(baseDataDictDtoList);
		return returnSuccess();
	}
	/**
	 * 验证运单号是否存在于系统中
	 *
	 * @return tfrCommonVO.resultSuccessful
	 *
	 * @author foss-wuyingjie
	 * @date 2013-1-14 上午11:56:13
	 * 
	 * 
	 */
	public String validateWaybillNoExist(){
		try{
			TfrCommonVO vo = tfrCommonService.validateWaybillNoExist(tfrCommonVO.getWaybillNo(), tfrCommonVO.getSerialNo());
			tfrCommonVO.setResultSuccessful(vo.getResultSuccessful());
			tfrCommonVO.setResultMessage(vo.getResultMessage());
		}catch(TfrBusinessException e){
			return super.returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * @param tfrCommonService
	 */
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}
	/**
	 * @return
	 */
	public TfrCommonVO getTfrCommonVO() {
		return tfrCommonVO;
	}
	/**
	 * 
	 * @param tfrCommonVO
	 */
	public void setTfrCommonVO(TfrCommonVO tfrCommonVO) {
		this.tfrCommonVO = tfrCommonVO;
	}
}