/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.server.service
 * FILE    NAME: IPDALoadService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.shared.dto.FinshWKLoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadSaleGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadSaleTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadSaleTaskResultDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskResultDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PackagePathLoaderDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SmtWKLoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.WKLoadTaskDto;

/**
 * PDA装车接口
 * @author dp-duyi
 * @date 2012-12-15 上午10:33:48
 */
public interface IPDATransferLoadService {
	//建立装车任务
	public LoadTaskResultDto createLoadTask(LoadTaskDto loadTask);
	// 建立装车任务  20160709版
	public LoadTaskResultDto createLoadTask(WKLoadTaskDto wkLoadTask);
	//刷新装车任务
	public List<LoadGoodsDetailDto> refrushLoadTaskDetail(String taskNo);
	//非建立任务PDA完成任务
	public String finishLoadTask(String taskNo,Date loadEndTime,String deviceNo,String loaderCode);
	//非建立任务PDA完成任务  20160709版
	public String finishLoadTask(FinshWKLoadTaskDto finshWKLoadTaskDto);
	//建立任务PDA提交任务
	public String submitLoadTask(String taskNo,Date loadEndTime,String deviceNo,String loaderCode);
	//建立任务PDA提交任务(new )需要同步给悟空
	public String submitLoadTask(SmtWKLoadTaskDto dto);
	//强制提交任务
	public String forceSubmitLoadTask(String taskNo,Date loadEndTime,String deviceNo,String loaderCode);
	//中途添加/删除理货员
	public String modifyLoader(String taskNo,List<LoaderDto> loaderCode,Date operateTime,String loaderState);
	//撤销装车任务  +最后增加操作部门字段
	public String cancelLoadTask(String taskNo,String deviceNo,String operatorCode,Date cancelTime, String orgCode);
	//撤销装车任务
	public String cancelLoadTask(String taskNo,String deviceNo,String operatorCode,Date cancelTime);
	//装车扫描
	public String loadScan(LoadScanDetailDto scanRecord);
	//多货扫描
	public LoadGoodsDetailDto moreGoodsLoadScan(LoadScanDetailDto scanRecord);
	//刷新装车任务包明细
	public List<LoadGoodsDetailDto> refrushLoadTaskPackageDetail(String taskNo);
	//刷新快递装车任务明细
	public List<LoadGoodsDetailDto> refrushLoadTaskExpressDetail(String taskNo);
	
	/**
	 * FOSS根据PDA传过来的运单号和当前所在转运场，查找出该运单走货路径中的下一转运场，并把结果返回给PDA。
	 * @author zwd 200968 2015-07-21
	 * @param waybillNo
	 * @param orgCode
	 * @return 
	 */
	public PackagePathLoaderDto unlockPackagePathDetail(String waybillNo,String orgCode);
	//强制提交装车任务,生成悟空交接单 
	public String forceSubmitLoadTask(SmtWKLoadTaskDto packaging);
	//刷新快递装车任务明细
	public List<LoadSaleGoodsDetailDto> refrushSaleLoadTaskExpressDetail(String taskNo);
	//刷新装车任务包明细
	public List<LoadSaleGoodsDetailDto> refrushSaleLoadTaskPackageDetail(String taskNo);
	//PDA建立装车任务接口
	public LoadSaleTaskResultDto createSaleLoadTask(LoadSaleTaskDto loadTask);
	//刷新派送装车任务
	public List<LoadSaleGoodsDetailDto> refrushSaleLoadTaskDetail(String taskNo);
}
