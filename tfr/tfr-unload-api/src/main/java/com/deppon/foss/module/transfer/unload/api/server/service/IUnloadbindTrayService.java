package com.deppon.foss.module.transfer.unload.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanDetaiEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadbindTrayEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadbindTrayExpressEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadbindTrayDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadbindTrayQueryConditionDto;

/**
 * @author 105795
 * @date   2013-12-24
 * @desc   卸车任务绑定托盘任务
 * */
public interface IUnloadbindTrayService extends IService{
	
	/**
	 * @author 105795
	 * @date   2013-12-30
	 * @param  conditionDto
	 * @param  limit start
	 * @see    查询卸车任务绑定情况  分页 零担
	 * */
	public List<UnloadbindTrayEntity> queryUnloadTaskbindTrayList(UnloadbindTrayQueryConditionDto conditionDto,int limit,int start );
	
	/**
	 * @author 105795
	 * @date   2013-12-30
	 * @param  conditionDto
	 * @param  limit start
	 * @see    查询卸车任务绑定情况  分页 快递
	 * */
	public List<UnloadbindTrayExpressEntity> queryUnloadTaskbindTrayListExpress(UnloadbindTrayQueryConditionDto conditionDto,int limit,int start );
	
	/**
	 * @author 105795
	 * @date   2013-12-30
	 * @param  conditionDto
	 * @param  limit start
	 * @see    查询卸车任务绑定情况  零担
	 * */
	public List<UnloadbindTrayEntity> queryUnloadTaskbindTray(UnloadbindTrayQueryConditionDto conditionDto);

	
	/**
	 * @author 105795
	 * @date   2013-12-30
	 * @param  conditionDto
	 * @param  limit start
	 * @see    查询卸车任务绑定情况  快递
	 * */
	public List<UnloadbindTrayExpressEntity> queryUnloadTaskbindTrayExpress(UnloadbindTrayQueryConditionDto conditionDto);

	
	/**
	 * @author 105795
	 * @date   2013-12-30
	 * @param  conditionDto
	 * @see    导出卸车任务绑定托盘情况
	 * */
	public  List exportUnloadTaskbindTrayExcel(UnloadbindTrayQueryConditionDto conditionDto);
	
	/**
	 * @author 105795
	 * @date   2014-01-06
	 * @param  unloadTaskNo
	 * @see    查询对应卸车任务下绑定的托盘扫描任务
	 * */
	public List<UnloadbindTrayDetailDto> queryUnloadbindTrayDetailByUnloadTaskNo(String unloadTaskNo,int limit,int start);
	
	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 查询对应卸车任务下绑定的托盘扫描任务
	 * 
	 * */
	public List<UnloadbindTrayDetailDto> queryUnloadbindTrayDetailListByUnloadTaskNo(String unloadTaskNo);

	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 根据卸车任务号查询卸车任务中的总件数与总票数
	 * */
	public List<UnloadbindTrayEntity> queryUnloadTaskTotalVotesAndTotalPiecesByUnloadTaskNo(List<String> unloadTaskNoList);
	
	/**
	 * @author 105795
	 * @date   2014-01-06
	 * @param  unloadTaskNo
	 * @see    根据卸车任务hao查询卸车任务绑定托盘总件数(包括已经解绑定)
	 * */
	public List<UnloadbindTrayEntity> queryUnLoadTaskbindTrayTotalPieces (List<String> unloadTaskNoList);
	
	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 根据卸车任务号查询卸车任务计算叉车票数
	 * */
	public List<UnloadbindTrayEntity> queryTotalforkliftTicksByUnloadTaskNo(List<String> unloadTaskNoList);
	
	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 根据卸车任务号查询绑定托盘任务件数
	 * */
	public List<UnloadbindTrayEntity> queryBindTrayTaskPiecesByUnloadTaskNo(List<String> unloadTaskNoList);
	
	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 根据卸车任务号查询绑定托盘任务中叉车扫描件数
	 * */
	public List<UnloadbindTrayEntity> queryBindTrayTaskScanPiecesByUnloadTaskNo(List<String> unloadTaskNoList);

	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 根据卸车任务号查询PDA已经扫描件数
	 * */
	public List<UnloadbindTrayEntity> queryPDASanPiecesByUnloadTaskNo(List<String> unloadTaskNoList);

	/**
	 * @author 105795
	 * @date 2014-01-09
	 * @param unloadTaskNo
	 * @desc 根据任务号与运单号查询托盘绑定任务明细
	 * */
	public List<TrayScanDetaiEntity> queryTrayScanDetailByTaskNoAndWaybillNo(String taskNo,String waybillNo);

	/**
	 *  零担
	 * @author 105795
	 * @date 2014-01-10
	 * @param unloadTaskNo
	 * @desc 	根据卸车任务号导出卸车任务绑定托盘流水号明细list--
	 * */
	public List exportUnloadTaskbindTrayDetailExcelByUnloadTaskNo(String unloadTaskNo);

	/**
	 *  快递
	 * @author 105795
	 * @date 2014-01-10
	 * @param unloadTaskNo
	 * @param deptCode 
	 * @desc 	根据卸车任务号导出卸车任务绑定托盘流水号明细list--
	 * */
	public List exportUnloadTaskbindTrayDetailExcelByUnloadTaskNoExpress(String unloadTaskNo, String deptCode);

	
	List exportUnloadTaskbindTrayExcelExpress(
			UnloadbindTrayQueryConditionDto conditionDto);

	List<UnloadbindTrayDetailDto> queryUnloadbindTrayDetailListByUnloadTaskNoExpress(
			String unloadTaskNo);

	public List<UnloadbindTrayDetailDto> queryUnloadbindTrayDetailByUnloadTaskNoExpress(
			String unloadTaskNo,String deptCode, int limit, int start);

}
