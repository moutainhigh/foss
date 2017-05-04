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
 *  PROJECT NAME  : tfr-package
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/server/action/QueryUnpackAction.java
 *  
 *  FILE NAME          :QueryUnpackAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.transfer.packaging.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPdaPackageMainPriceService;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPDAPackConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPDAPackResultEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.vo.QueryPackedPriceVo;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;


/**
 * 
 * PDA端扫描生成包装金额查询
 * @author foss-189284-zx
 * @version 创建时间：2014-5-9 上午10:20:19 
 */
public class QueryPdapackPriceAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryPdapackPriceAction.class);
	/**
	 * 查询pda端扫描生产主包装金额 业务层对象
	 */
	private IPdaPackageMainPriceService pdaPackageMainPriceService;	
	/**
	 * vo对象
	 */
	private QueryPackedPriceVo queryPackedPriceVo=new QueryPackedPriceVo();	
	/**
	 * 导出Excel 文件流
	 */
	transient InputStream excelStream;
	/**
	 * 导出Excel 文件名
	 */
	private String fileName;
	/**
	 * PDA端扫描生成包装金额查询
	* @author foss-189284-zx
	* @version 创建时间：2014-5-9 上午10:20:19 
	* @return String//PriceByWaybillNoOrMaterial
	 */
	@JSON
	public String queryPdaPackage(){
		//获取当前组织信息
		try {
			QueryPDAPackConditionEntity pdaQueryPackConditionEntity=queryPackedPriceVo.getPdaQueryPackConditionEntity();
			List<QueryPDAPackResultEntity> pdaQueryPackResultList =
					pdaPackageMainPriceService.queryPdaPackagePriceByWaybillNoOrMaterial(
							pdaQueryPackConditionEntity,this.getLimit(), this.getStart());	
			//查询总条数	
			long totalCount=pdaPackageMainPriceService.queryPdaPackagePriceList(pdaQueryPackConditionEntity);
			//设置总条数
			this.setTotalCount(totalCount);
			queryPackedPriceVo.setPdaQueryPackResultList(pdaQueryPackResultList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}	
	/**
	 * 
	* @author foss-189284-zx
	* @date 创建时间：2014-5-26 上午10:49:25 
	* @return String
	 */
	public String exportPdaExcel(){
		try {
			// 导出文件名
			fileName = this.encodeFileName("PDA端扫描生成包装金额信息");
			// 导出文件流
			excelStream = pdaPackageMainPriceService.exportExcelStream(queryPackedPriceVo.getPdaQueryPackConditionEntity());
		} catch (BusinessException e) {
			LOGGER.error(e.getErrorCode());
			return returnError(e);
		}catch (Exception e) {
			//LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			return super.returnError(e.toString(), "");
		}
		return returnSuccess();
	}
	
	/**
	 * 已下是get，set方法
	 */

	/**
	 * 获取queryPackedPriceVo
	 * @return  queryPackedPriceVo
	 */
	public QueryPackedPriceVo getQueryPackedPriceVo() {
		return queryPackedPriceVo;
	}

	/**
	 *设置queryPackedPriceVo
	 */
	public void setQueryPackedPriceVo(QueryPackedPriceVo queryPackedPriceVo) {
		this.queryPackedPriceVo = queryPackedPriceVo;
	}

	/**
	 *设置pdaPackageMainPriceService查询pda端扫描生产主包装金额 业务层对象
	 */
	public void setPdaPackageMainPriceService(
			IPdaPackageMainPriceService pdaPackageMainPriceService) {
		this.pdaPackageMainPriceService = pdaPackageMainPriceService;
	}


	/**
	 * 获取excelStream
	 * @return  excelStream
	 */
	public InputStream getExcelStream() {
		return excelStream;
	}
	
	
	/**
	 * 获取fileName
	 * @return  fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * 生成导出文件名称
	 * @author yuyongxiang
	 * @date 2013年10月11日 11:38:30
	 */
	public String encodeFileName(String fileName) throws BusinessException {
		try {
			String returnStr;
			String agent = (String) ServletActionContext.getRequest().getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") == -1) {
				returnStr = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			} else {
				returnStr = URLEncoder.encode(fileName, "UTF-8");
			}
			return returnStr;
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("转换文件名编码失败", e);
			throw new BusinessException(StockException.EXPORT_FILE_ERROR_CODE, "");
		}
	}
	/**
	 * 审核PDA包装金额
	 * @author foss-heyongdong
	 * @date 2014年6月25日 10:55:00
	 * */
	@JSON
	public String auditPacked(){
		
		try {
			List<String> id=queryPackedPriceVo.getIds();
			String auditType = queryPackedPriceVo.getAuditType();
			CurrentInfo user = FossUserContext.getCurrentInfo();
			pdaPackageMainPriceService.auditPacked(id,auditType,user);
			return super.returnSuccess();
			
		} catch(TfrBusinessException e){
			return returnError(e);
		}catch (Exception e) {
			return super.returnError(e.toString());
		}
	}
	
	
	
}