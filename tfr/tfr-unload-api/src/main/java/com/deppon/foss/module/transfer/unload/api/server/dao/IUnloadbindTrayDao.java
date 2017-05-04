package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanDetaiEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadbindTrayEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadbindTrayExpressEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskbindTrayDetailseriaNo;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadbindTrayDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadbindTrayQueryConditionDto;

/**
 * @author 105795
 * @date 2013-12-24
 * @see 卸车任务绑定托盘
 * */
public interface IUnloadbindTrayDao {
	

	
	public List<UnloadbindTrayEntity> queryUnloadTaskbindTrayExcel(	UnloadbindTrayQueryConditionDto conditionDto);
	
	/**
	 * @author 105795
	 * @date 2013-12-30
	 * @param conditionDto
	 * @desc 查询卸车任务绑定托盘   分页查询
	 * 
	 * */
	public List<UnloadbindTrayEntity> queryUnloadTaskbindTrayList(UnloadbindTrayQueryConditionDto conditionDto,int limit,int start);
	
	/**
	 * @author 105795
	 * @date 2013-12-30
	 * @param conditionDto
	 * @desc 查询卸车任务绑定托盘   分页查询 快递
	 * 
	 * */
	public String queryUnloadTaskbindTrayListExpress(UnloadbindTrayQueryConditionDto conditionDto,int limit,int start);
	
	
	/**
	 * @author 105795
	 * @date 2013-12-30
	 * @param conditionDto
	 * @desc 查询卸车任务绑定托盘  零担
	 * 
	 * */
	public List<UnloadbindTrayEntity> queryUnloadTaskbindTray(UnloadbindTrayQueryConditionDto conditionDto);
	
	/**
	 * @author 105795
	 * @date 2013-12-30
	 * @param conditionDto
	 * @desc 查询卸车任务绑定托盘  快递
	 * 
	 * */
	public List<UnloadbindTrayExpressEntity> queryUnloadTaskbindTrayExpress(UnloadbindTrayQueryConditionDto conditionDto);
	
	
	/**  零担
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 查询对应卸车任务下绑定的托盘扫描任务
	 * 
	 * */
	public List<UnloadbindTrayDetailDto> queryUnloadbindTrayDetailByUnloadTaskNo(String unloadTaskNo,int limit,int start);
	
	/**  快递
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 查询对应卸车任务下绑定的托盘扫描任务
	 * 
	 * */
	public String queryUnloadbindTrayDetailByUnloadTaskNoExpress(String unloadTaskNo,String deptCode,int limit,int start);
	
	
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
	 * @desc 查询对应卸车任务下绑定的托盘扫描任务
	 * 
	 * */
	public List<UnloadbindTrayDetailDto> queryUnloadbindTrayDetailListByUnloadTaskNoExpress(String unloadTaskNo);
	

	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 根据卸车任务hao查询卸车任务绑定托盘总票数 
	 * */
	public List<UnloadbindTrayEntity> queryUnLoadTaskbindTrayTotalVotes (List<String> unloadTaskNoList);
	
	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 根据卸车任务号查询卸车任务绑定托盘总件数
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
	 * @desc 根据卸车任务号查询绑定托盘任务中叉车扫描件数
	 * */
	public List<UnloadbindTrayEntity> queryUnloadTaskTotalVotesAndTotalPiecesByUnloadTaskNo(List<String> unloadTaskNoList);
	
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
	public List<UnloadTaskbindTrayDetailseriaNo> exportUnloadTaskbindTrayDetailExcelByUnloadTaskNo(String unloadTaskNo);
	
	/**
	 *  快递
	 * @author 105795
	 * @date 2014-01-10
	 * @param unloadTaskNo
	 * @desc 	根据卸车任务号导出卸车任务绑定托盘流水号明细list--
	 * */
	public List<UnloadTaskbindTrayDetailseriaNo> exportUnloadTaskbindTrayDetailExcelByUnloadTaskNoExpress(String unloadTaskNo);

	
	List<UnloadbindTrayEntity> queryUnloadTaskbindTrayExcelExpress(
			UnloadbindTrayQueryConditionDto conditionDto);
	
}
