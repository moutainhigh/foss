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
 *  PROJECT NAME  : tfr-stockchecking
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/server/action/StReportAction.java
 *  
 *  FILE NAME          :StReportAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.server.action;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StReportDetailDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StReportDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.vo.StReportVO;

/**
 * 处理清仓差异报告相关页面
 * 
 * 判断清仓差异报告结果规则
 * 1、清仓结果状态为"正常"的，并且在快照表中存在的为正常的清仓结果。
 * 2、在清仓结果中不存在于快照表中件，为"少货"情况
 * 3、清仓结果状态为"多货"的
 *  3.1、查询本部门非本货区是否存在此件
 *   3.1.1、如果存在，差异结果为"放错货区"
 *   3.1.2、如果不存在，在本部门入库此件，("并同时从上一部门出库，并通知上一部门;3.1.2.2.1此动作在入库服务中实现);查询此件是否在当前对应的走货路径上
 *    3.1.2.1、如果在走货路径上，清仓差异结果为"多货-夹带"
 *    3.1.2.2、如果不在走货路径上，清仓差异结果为"多货-异地夹带"
 *     3.1.2.2.1、更新此件对应的走货路径
 * 
 * IConfigurationParamsService.queryConfigurationParamsByOrgCode
 * @author foss-wuyingjie
 * @date 2012-11-19 下午2:10:02
 * @param
 * @return String
 */
public class StReportAction extends AbstractAction{

	private static final long serialVersionUID = -992858651168067390L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StReportAction.class);
//	清仓差异报告相关页面所用VO
	private StReportVO stReportVO = new StReportVO();
//	处理清仓差异报告业务
	private IStReportService stReportService;
	
	/** 导出Excel 文件流*/
	private InputStream excelStream;  
	/** 导出Excel 文件名*/
	private String fileName; 
	
	/**
	 * 查询清仓差异报告
	 * @author foss-wuyingjie
	 * @date 2012-11-20 下午3:35:28
	 * @param
	 * @return String
	 */
	@JSON
	public String queryStReport(){
		try{
			
			List<StReportDto> stReportDtoList = stReportService.queryStReportDtoList(stReportVO.getStReportDto(), this.getStart(), this.getLimit());
			Long totalCount = stReportService.queryStReportDtoListCount(stReportVO.getStReportDto());

			stReportVO.setStReportDtoList(stReportDtoList);
			this.setTotalCount(totalCount);
			
			return returnSuccess();
		}catch(TfrBusinessException e){
			LOGGER.error("查询清仓差异异常:" + e);
			
			return super.returnError(e);
		}
	}
	
	/**
	 * 通过清仓差异报告ID获取差异明细列表
	 * @author foss-wuyingjie
	 * @date 2012-11-22 上午9:10:59
	 * @param
	 * @return String
	 */
	@JSON
	public String queryReportDetailById(){
		try{
			List<StReportDetailDto> stReportDetailDtoList = stReportService.queryStReportDetailDtoListsById(stReportVO.getStReportDto().getId(),this.getStart(),this.getLimit());
			Long totalDetailCount = stReportService.getStReportDetailDtoCount(stReportVO.getStReportDto().getId());
			stReportVO.setStReportDetailDtoList(stReportDetailDtoList);
			this.setTotalCount(totalDetailCount);
			
			return returnSuccess();
		}catch(TfrBusinessException e){
			LOGGER.error("查询清仓差异明细异常:" + e);
			
			return super.returnError(e);
		}
	}
	/**
	 * @author niuly
	 * @function 根据差异报告id、单号、流水号查询两天内的相关联差异报告
	 * @date 2013-6-25
	 * @param
	 * @return String
	 */
	@JSON
	public String queryReportRelativeDetail() {
		try{
			List<StReportDetailDto> stReportRelativeDetailDtoList = stReportService.queryReportRelativeDetail(stReportVO.getStReportDto().getId(),
					stReportVO.getStReportDto().getWaybillNo(),stReportVO.getStReportDto().getSerailNosList());
			
			stReportVO.setStReportRelativeDetailDtoList(stReportRelativeDetailDtoList);
			
			return returnSuccess();
		}catch(TfrBusinessException e){
			LOGGER.error("查询清仓差异相关联明细异常:" + e);
			
			return super.returnError(e);
		}
		
	}
	/**
	 * @author niuly
	 * @function 根据差异报告id、单号查询差异报告明细
	 * @date 2013-7-26
	 * @param
	 * @return String
	 */
	@JSON
	public String queryReportDetailByWaybillNo() {
		try{
			List<StReportDetailDto> stReportDetailDtoList = stReportService.queryReportDetailByWaybillNo(stReportVO.getStReportDto().getId(),stReportVO.getStReportDto().getWaybillNo());
			stReportVO.setStReportDetailDtoList(stReportDetailDtoList);
			
			return returnSuccess();
		}catch(TfrBusinessException e){
			LOGGER.error("查询清仓差异明细异常:" + e);
			
			return super.returnError(e);
		}
	}
	
	/**
	 * <pre>
	 * 夹带、异地夹带的，需调用库存接口对当前部门做入库
	 * 少货确认找到后，更新库存信息，从特殊库区移回
	 * 更新清仓差异报告处理结果状态
	 * 
	 * </pre>
	 * @author foss-wuyingjie
	 * @date 2012-11-22 下午4:17:46
	 * @param
	 * @return String
	 */
	@JSON
	public String updateReportDetail(){
		try{
			UserEntity user = FossUserContext.getCurrentUser();
			
			stReportService.updateReportDetail(stReportVO.getStDifferDetail(), user.getEmpName(), user.getEmpCode());
			
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error("更新清仓差异明细异常:" + ExceptionUtils.getFullStackTrace(e));
			
			return super.returnError(e);
		}
	}
	/**
	 * @author niuly
	 * @date 2013-07-25 17:12:25
	 * @function 批量更新清仓差异报告明细
	 * @param
	 * @return String
	 */
	@JSON
	public String updateBatchReportDetail() {
		try{
			CurrentInfo user = FossUserContext.getCurrentInfo();
			
			stReportService.updateBatchReportDetail(stReportVO.getStDifferDetailList(), user.getEmpName(), user.getEmpCode());
			
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error("批量更新清仓差异明细异常:" + ExceptionUtils.getFullStackTrace(e));
			
			return super.returnError(e);
		}
	}
	/**
	 * 导出清仓差异报告明细信息
	 * @return: 
	 *
	 * @author foss-wuyingjie
	 * @date 2013-3-12 下午4:32:01
	 * @param
	 * @return String
	 */
	public String exportReportDetailById(){
		try{
			fileName = stReportService.createFileName("清仓任务差异报告明细信息");
			excelStream = stReportService.exportStDifferDetailById(stReportVO.getStReportDto().getId());
			
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error("导出清仓差异异常:" + e);
			
			return super.returnError(e);
		}
	}
	
	/**
	 * 
	 * @author 097457-foss-zenghaibin
	 * @date 2014-10-20 下午2:39:38
	 * @param
	 * @return String
	 */
	@JSON
	public String goodsCountInfo(){
		try{
			stReportVO=stReportService.goodsCountInfo();
		return returnSuccess();
		}catch(BusinessException e){
			return returnError(e);
		}
	}
	/**
	 * @author niuly
	 * @function 查询当前部门对应的外场code
	 * @date 2013-7-10
	 * @param
	 * @return String
	 */
	public String queryTransferCode() {
		try{
			String transferCode = stReportService.queryTransferCode();
			String transferCenter= stReportService.queryTransferCenter();
			stReportVO.setTransferCode(transferCode);
			stReportVO.setTransferCenter(transferCenter);
			return returnSuccess();
		} catch(BusinessException e) {
			LOGGER.error("查询外场code清仓差异异常:" + e);
			return super.returnError(e);
		}
	}
	public StReportVO getStReportVO() {
		return stReportVO;
	}

	public void setStReportVO(StReportVO stReportVO) {
		this.stReportVO = stReportVO;
	}

	public void setStReportService(IStReportService stReportService) {
		this.stReportService = stReportService;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}