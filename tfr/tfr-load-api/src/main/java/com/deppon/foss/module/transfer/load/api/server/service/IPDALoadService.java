/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.server.service
 * FILE    NAME: IPDALoadService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PackagePathLoaderDto;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;

/**
 * 没有锁的派送/中转装车扫描
 * 为了解锁前提交扫描事务
 * @author dp-duyi
 * @date 2013-3-20 上午8:59:01
 */
public interface IPDALoadService extends IPDATransferLoadService{
	public LoadGoodsDetailDto unlockLoadScan(LoadScanDetailDto scanRecord);
	public void oaReportMoreGoods(String taskId,String loaderCode,String origOrgCode);
	public void noticeDeliverOrg(LoadTaskEntity loadTask,String loaderCode,String loaderName);
	public String finishLoadAndSoOn(LoadTaskEntity loadTask, Date loadEndTime,String deviceNo,String loaderCode);
	public void moreGoodsHandler(String origOrgCode,String taskId,String loaderCode,String loaderName);
	public void inStockCanceledDeliverScan(String taskId,String orgCode,String loaderCode,String loaderName);
	public List<String> queryOuterBranchCodesByTaskId(String partnerCodes);
	public void loadInStock(List<InOutStockEntity> goodsList,int inStockTimes);
	public void instock(InOutStockEntity goods);
	/**快递:根据任务ID查询落地配网点Codes*/
	List<String> queryLDPDeptCodesByTaskId(String taskId);
	public LoadGoodsDetailDto unlockPackageLoadScan(LoadScanDetailDto scanRecord);
	/**
	 * 装车包扫描新方法 zwd 200968 2015.4.7
	 * */
	public LoadGoodsDetailDto unlockPackageLoadScanNew(LoadScanDetailDto scanRecord);
	/**
	 * FOSS根据PDA传过来的运单号和当前所在转运场，查找出该运单走货路径中的下一转运场，并把结果返回给PDA。
	 * @author zwd 200968 2015-07-21
	 * @param waybillNo
	 * @param orgCode
	 * @return 
	 */
	public PackagePathLoaderDto unlockPackagePathDetail(String waybillNo,String orgCode);
}
