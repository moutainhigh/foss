package com.deppon.foss.module.transfer.unload.server.dao.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.shared.domain.RequestParameterEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanDetaiEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadbindTrayDao;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadbindTrayEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadbindTrayExpressEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskbindTrayDetailseriaNo;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadbindTrayDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadbindTrayQueryConditionDto;
public class UnloadbindTrayDao extends iBatis3DaoImpl implements IUnloadbindTrayDao{

	/**
	 * @author 105795
	 * @date 2013-12-24
	 * @param conditionDto
	 * @desc 查询卸车任务绑定托盘
	 * 
	 * */
	private static final String NAMESPACE="tfr-nuload-trayscan.";
	
	private  IFOSSToWkService fossToWkService;
	
	public void setFossToWkService(IFOSSToWkService fossToWkService) {
		this.fossToWkService = fossToWkService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UnloadbindTrayEntity> queryUnloadTaskbindTrayExcel(
			UnloadbindTrayQueryConditionDto conditionDto) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadTaskbindTrayList", conditionDto);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UnloadbindTrayEntity> queryUnloadTaskbindTrayExcelExpress(
			UnloadbindTrayQueryConditionDto conditionDto) {
		// TODO Auto-generated method stub
		List<UnloadbindTrayExpressEntity> unloadbindTrayExpressEntity=this.getSqlSession().selectList(NAMESPACE+"queryUnloadTaskbindTrayListExpress", conditionDto);
		List<UnloadbindTrayEntity> unloadbindTrayEntity=new ArrayList<UnloadbindTrayEntity>();
		UnloadbindTrayEntity tray=new UnloadbindTrayEntity();
		//把UnloadbindTrayExpressEntity类型转为UnloadbindTrayEntity类型返回过去
		for(int i=0;i < unloadbindTrayExpressEntity.size(); i++){
			tray.setUnloadTaskNo(unloadbindTrayExpressEntity.get(i).getUnloadTaskNo());
			tray.setUnloadCreator(unloadbindTrayExpressEntity.get(i).getUnloadCreator());
			tray.setUnloadCreatorCode(unloadbindTrayExpressEntity.get(i).getUnloadCreatorCode());
			tray.setTotalTicks(unloadbindTrayExpressEntity.get(i).getTotalTicks());
			tray.setUnLoadScanPieces(unloadbindTrayExpressEntity.get(i).getUnLoadScanPieces());
			tray.setBindPieces(unloadbindTrayExpressEntity.get(i).getBindPieces());
			tray.setBindRate(unloadbindTrayExpressEntity.get(i).getBindRate());
			tray.setTrayTaskTotal(unloadbindTrayExpressEntity.get(i).getTrayTaskTotal());
			tray.setScannedTotal(unloadbindTrayExpressEntity.get(i).getScannedTotal());
			tray.setScanPieces(unloadbindTrayExpressEntity.get(i).getScanPieces());
			tray.setVehicleNo(unloadbindTrayExpressEntity.get(i).getVehicleNo());
			tray.setForkliftTicks(unloadbindTrayExpressEntity.get(i).getForkliftTicks());
			unloadbindTrayEntity.add(tray);
		}
		return unloadbindTrayEntity;
	}

	/**
	 * @author 105795
	 * @date 2013-12-30
	 * @param conditionDto
	 * @desc 查询卸车任务绑定托盘   分页查询 零担
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public List<UnloadbindTrayEntity> queryUnloadTaskbindTrayList(UnloadbindTrayQueryConditionDto conditionDto,int limit,int start)
	{
		//分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//查询
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadTaskbindTrayList", conditionDto,rowBounds);

	}
	
	/**
	 * @author 105795
	 * @date 2013-12-30
	 * @param conditionDto
	 * @desc 查询卸车任务绑定托盘   分页查询 快递
	 * 
	 * */
	public String queryUnloadTaskbindTrayListExpress(UnloadbindTrayQueryConditionDto conditionDto,int limit,int start)
	{
		/*//分页
		RowBounds rowBounds = new RowBounds(start, limit);*/
		String zz="";
		try {
			conditionDto.setCurrentPageNo(start);
			conditionDto.setPageSize(limit);
			RequestParameterEntity requestParameter=new RequestParameterEntity();
			requestParameter.setRequestEntity(conditionDto);
			//zz="{\"data\":[{\"optionOrgCode\":null,\"outfiledCode\":\"12315\",\"unloadedTotalPrices\":140,\"currentPageNo\":0,\"createrCode\":\"操作人工号\",\"createStartDate\":null,\"createEndDate\":null,\"pageSize\":0,\"vehicleNo\":\"京A88888\",\"unloadTaskNo\":\"AAAAA114\",\"bindingTasksCount\":10,\"trayBindingPro\":57.14,\"forkliftTricketsCount\":10,\"forkliftScanPricesCount\":8000,\"scanTaskCount\":10,\"bindedTotalPrices\":8000,\"totalTrickets\":50,\"unloadPersonName\":\"操作人名称\"}],\"status\":1,\"exMsg\":null,\"totalRows\":1}";
			
			zz=fossToWkService.queryUnloadTaskbindTrayListExpress(requestParameter);
			return zz;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new TfrBusinessException(e.getMessage());
		}
		//查询
		//return this.getSqlSession().selectList(NAMESPACE+"queryUnloadTaskbindTrayListExpress", conditionDto,rowBounds);
		
	}
	
	/**
	 * @author 105795
	 * @date 2013-12-30
	 * @param conditionDto
	 * @desc 查询卸车任务绑定托盘  零担
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public List<UnloadbindTrayEntity> queryUnloadTaskbindTray(UnloadbindTrayQueryConditionDto conditionDto)
	{
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadTaskbindTrayList", conditionDto);
	}
	
	/**
	 * @author 105795
	 * @date 2013-12-30
	 * @param conditionDto
	 * @desc 查询卸车任务绑定托盘  快递
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public List<UnloadbindTrayExpressEntity> queryUnloadTaskbindTrayExpress(UnloadbindTrayQueryConditionDto conditionDto)
	{
		
		
		try {
			RequestParameterEntity requestParameter=new RequestParameterEntity();
			requestParameter.setRequestEntity(conditionDto);
			//result = fossToWkService.queryUnloadTaskbindTrayListExpress(requestParameter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return result;
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadTaskbindTrayListExpress", conditionDto);
	}


	/**
	 *  零担
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 查询对应卸车任务下绑定的托盘扫描任务
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public List<UnloadbindTrayDetailDto> queryUnloadbindTrayDetailByUnloadTaskNo(String unloadTaskNo,int limit,int start)
	{
		//分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//查询
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadbindTrayDetailByUnloadTaskNo", unloadTaskNo,rowBounds);
	}
	
	/**
	 *  快递
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 查询对应卸车任务下绑定的托盘扫描任务
	 * 
	 * */
	public String queryUnloadbindTrayDetailByUnloadTaskNoExpress(String unloadTaskNo,String deptCode,int limit,int start)
	{
		//参数用实体类带过去
		UnloadbindTrayQueryConditionDto conditionDto=new UnloadbindTrayQueryConditionDto();
		conditionDto.setUnloadTaskNo(unloadTaskNo);
		conditionDto.setOptionOrgCode(deptCode);
		conditionDto.setCurrentPageNo(start);
		conditionDto.setPageSize(limit);
		//查询
		String result="";
		try {
			RequestParameterEntity requestParameter=new RequestParameterEntity();
			requestParameter.setRequestEntity(conditionDto);
			result = fossToWkService.queryUnloadbindTrayDetailByUnloadTaskNoExpress(requestParameter);
		} catch (Exception e) {
			logger.error("没有查询到数据");
			throw new TfrBusinessException("没有查询到数据");
		}
		return result;
		//return this.getSqlSession().selectList(NAMESPACE+"queryUnloadbindTrayDetailByUnloadTaskNoExpress", unloadTaskNo,rowBounds);
	}
	
	/**
	 *  零担
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 查询对应卸车任务下绑定的托盘扫描任务
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public List<UnloadbindTrayDetailDto> queryUnloadbindTrayDetailListByUnloadTaskNo(String unloadTaskNo)
	{
		//查询
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadbindTrayDetailByUnloadTaskNo", unloadTaskNo);
	}
	
	/**
	 *  快递
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 查询对应卸车任务下绑定的托盘扫描任务
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public List<UnloadbindTrayDetailDto> queryUnloadbindTrayDetailListByUnloadTaskNoExpress(String unloadTaskNo)
	{
		//查询
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadbindTrayDetailByUnloadTaskNoExpress", unloadTaskNo);
	}
	
	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 根据卸车任务hao查询卸车任务绑定托盘总票数 
	 * */
	@SuppressWarnings("unchecked")
	public List<UnloadbindTrayEntity> queryUnLoadTaskbindTrayTotalVotes (List<String> unloadTaskNoList)
	{
		return this.getSqlSession().selectList(NAMESPACE+"queryUnLoadTaskbindTrayTotalVotes", unloadTaskNoList);

	}
	
	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 根据卸车任务号查询卸车任务绑定托盘总件数
	 * */
	@SuppressWarnings("unchecked")
	public List<UnloadbindTrayEntity> queryUnLoadTaskbindTrayTotalPieces (List<String> unloadTaskNoList)
	{
		return this.getSqlSession().selectList(NAMESPACE+"queryUnLoadTaskbindTrayTotalPieces", unloadTaskNoList);

		
	}

	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 根据卸车任务号查询卸车任务计算叉车票数
	 * */
	@SuppressWarnings("unchecked")
	public List<UnloadbindTrayEntity> queryTotalforkliftTicksByUnloadTaskNo(List<String> unloadTaskNoList)
	{
		return this.getSqlSession().selectList(NAMESPACE+"queryTotalforkliftTicksByUnloadTaskNo", unloadTaskNoList);

	}
	
	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 根据卸车任务号查询绑定托盘任务件数
	 * */
	@SuppressWarnings("unchecked")
	public List<UnloadbindTrayEntity> queryBindTrayTaskPiecesByUnloadTaskNo(List<String> unloadTaskNoList)
	{
		
		return this.getSqlSession().selectList(NAMESPACE+"queryBindTrayTaskPiecesByUnloadTaskNo", unloadTaskNoList);

	}
	
	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 根据卸车任务号查询绑定托盘任务中叉车扫描件数
	 * */
	@SuppressWarnings("unchecked")
	public List<UnloadbindTrayEntity> queryBindTrayTaskScanPiecesByUnloadTaskNo(List<String> unloadTaskNoList)
	{
		
		return this.getSqlSession().selectList(NAMESPACE+"queryBindTrayTaskScanPiecesByUnloadTaskNo", unloadTaskNoList);

	}
	
	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 根据卸车任务号查询绑定托盘任务中叉车扫描件数
	 * */
	@SuppressWarnings("unchecked")
	public List<UnloadbindTrayEntity> queryUnloadTaskTotalVotesAndTotalPiecesByUnloadTaskNo(List<String> unloadTaskNoList)

	{
		
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadTaskTotalVotesAndTotalPiecesByUnloadTaskNo", unloadTaskNoList);

	}
	
	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 根据卸车任务号查询PDA已经扫描件数
	 * */
	@SuppressWarnings("unchecked")
	public List<UnloadbindTrayEntity> queryPDASanPiecesByUnloadTaskNo(List<String> unloadTaskNoList)

	{
		
		return this.getSqlSession().selectList(NAMESPACE+"queryPDASanPiecesByUnloadTaskNo", unloadTaskNoList);

	}
	
	/**
	 * @author 105795
	 * @date 2014-01-09
	 * @param unloadTaskNo
	 * @desc 根据任务号与运单号查询托盘绑定任务明细
	 * */
	@SuppressWarnings("unchecked")
	public List<TrayScanDetaiEntity> queryTrayScanDetailByTaskNoAndWaybillNo(String taskNo,String waybillNo)
	{
		Map<String ,String > dataMap=new HashMap<String,String>(); 
		dataMap.put("taskNo", taskNo);
		dataMap.put("waybillNo", waybillNo);
		return this.getSqlSession().selectList(NAMESPACE+"queryTrayScanDetailByTaskNoAndWaybillNo", dataMap);

	}
	
	/**
	 *  零担
	 * @author 105795
	 * @date 2014-01-10
	 * @param unloadTaskNo
	 * @desc 	根据卸车任务号导出卸车任务绑定托盘流水号明细list--
	 * */
	@SuppressWarnings("unchecked")
	public List<UnloadTaskbindTrayDetailseriaNo> exportUnloadTaskbindTrayDetailExcelByUnloadTaskNo(String unloadTaskNo)
	{
		return this.getSqlSession().selectList(NAMESPACE+"exportUnloadTaskbindTrayDetailExcelByUnloadTaskNo", unloadTaskNo);

	}
	
	/**
	 *  快递
	 * @author 105795
	 * @date 2014-01-10
	 * @param unloadTaskNo
	 * @desc 	根据卸车任务号导出卸车任务绑定托盘流水号明细list--
	 * */
	@SuppressWarnings("unchecked")
	public List<UnloadTaskbindTrayDetailseriaNo> exportUnloadTaskbindTrayDetailExcelByUnloadTaskNoExpress(String unloadTaskNo)
	{
		return this.getSqlSession().selectList(NAMESPACE+"exportUnloadTaskbindTrayDetailExcelByUnloadTaskNoExpress", unloadTaskNo);

	}
}
