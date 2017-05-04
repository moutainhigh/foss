package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanDetaiEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadbindTrayDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadbindTrayService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadbindTrayEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadbindTrayExpressEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskbindTrayDetailseriaNo;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadbindTrayDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadbindTrayQueryConditionDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 105795
 * @date 2013-12-24
 * @desc 卸车任务绑定托盘service
 * */
public class UnloadbindTrayService implements IUnloadbindTrayService{

	/**
	 * @author 105795
	 * @date   2013-12-24
	 * @param  conditionDto
	 * @see    查询卸车任务绑定情况
	 * */
	static final Logger logger = LoggerFactory
			.getLogger(CreateForkliftWorkService.class);
	IUnloadbindTrayDao unloadbindTrayDao;
	
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	private ISaleDepartmentService saleDepartmentService;

	public IPDACommonService pdaCommonService;
	
	private IConfigurationParamsService configurationParamsService;
	/**
	 * @author 105795
	 * @date   2013-12-30
	 * @param  conditionDto
	 * @param  limit start
	 * @see    查询卸车任务绑定情况  分页 零担
	 * */
	public List<UnloadbindTrayEntity> queryUnloadTaskbindTrayList(UnloadbindTrayQueryConditionDto conditionDto,int limit,int start)
	{
		
		List<UnloadbindTrayEntity> unloadbindTrayList;

		//参数检查
		if(conditionDto==null)
		{
			logger.error("参数不合法");
			throw new TfrBusinessException("参数不合法");
		}
		
		//当前部门Code
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//查询对应外场编码
		String outfiledCode=getCenterCode(currentDeptCode);
		//初始化外场编码
		conditionDto.setOutfiledCode(outfiledCode);	
		//查询卸车任务绑定托盘信息
		try {
			unloadbindTrayList= unloadbindTrayDao.queryUnloadTaskbindTrayList(conditionDto,limit,start);
			
			if(CollectionUtils.isEmpty(unloadbindTrayList)||unloadbindTrayList.size()<1)
			{
				throw new TfrBusinessException("没有查询到数据");
			}
			
			return calculateUnloadTaskbindTray(unloadbindTrayList);
			
		} catch (TfrBusinessException e) {
			logger.error(e.getMessage());
			throw new TfrBusinessException(e.getMessage());
		}
		
	}
	
	/**
	 * @author 105795
	 * @date   2013-12-30
	 * @param  conditionDto
	 * @param  limit start
	 * @see    查询卸车任务绑定情况  分页 快递
	 * */
	public List<UnloadbindTrayExpressEntity> queryUnloadTaskbindTrayListExpress(UnloadbindTrayQueryConditionDto conditionDto,int limit,int start)
	{
		
		List<UnloadbindTrayExpressEntity> unloadbindTrayList=null;
		
		//参数检查
		if(conditionDto==null)
		{
			logger.error("参数不合法");
			throw new TfrBusinessException("参数不合法");
		}
		//查询卸车任务绑定托盘信息
		try {
			String jsonData= unloadbindTrayDao.queryUnloadTaskbindTrayListExpress(conditionDto,limit,start);
			if(StringUtil.isEmpty(jsonData))
			{
				throw new TfrBusinessException("程序异常！");
			}else{
				unloadbindTrayList = new ArrayList<UnloadbindTrayExpressEntity>();
				JSONObject resultJson=JSONObject.fromObject(jsonData);//字符串格式转换为JSON格式
				System.out.println("resultJson="+resultJson.toString());
				if(resultJson.getString("data")==null||"[]".equals(resultJson.getString("data"))){
					throw new TfrBusinessException("没有查询到数据");
				}
				for (int i = 0; i < resultJson.getJSONArray("data").size(); i++) {
					UnloadbindTrayExpressEntity unload=new UnloadbindTrayExpressEntity();
					int a=(StringUtils.equals(resultJson.getJSONArray("data").getJSONObject(i).getString("bindedTotalPrices"), "null") ? 0:resultJson.getJSONArray("data").getJSONObject(i).getInt("bindedTotalPrices"));
					unload.setBindPieces(a);//已经绑定件数
					
					unload.setBindRate(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("trayBindingPro"))+"%");//托盘绑定率
					
					a=(StringUtils.equals(resultJson.getJSONArray("data").getJSONObject(i).getString("forkliftTricketsCount"), "null") ? 0:resultJson.getJSONArray("data").getJSONObject(i).getInt("forkliftTricketsCount"));
					unload.setForkliftTicks(a);//叉车票数
					
					a=(StringUtils.equals(resultJson.getJSONArray("data").getJSONObject(i).getString("forkliftScanPricesCount"), "null") ? 0:resultJson.getJSONArray("data").getJSONObject(i).getInt("forkliftScanPricesCount"));
					unload.setScanPieces(a);//叉车扫描件数
					
					a=(StringUtils.equals(resultJson.getJSONArray("data").getJSONObject(i).getString("scanTaskCount"), "null") ? 0:resultJson.getJSONArray("data").getJSONObject(i).getInt("scanTaskCount"));
					unload.setScannedTotal(a);//已扫描的托盘任务总数
					
					
					a=(StringUtils.equals(resultJson.getJSONArray("data").getJSONObject(i).getString("totalTrickets"), "null") ? 0:resultJson.getJSONArray("data").getJSONObject(i).getInt("totalTrickets"));
					unload.setTotalTicks(a);//总票数
					
					a=(StringUtils.equals(resultJson.getJSONArray("data").getJSONObject(i).getString("bindingTasksCount"), "null") ? 0:resultJson.getJSONArray("data").getJSONObject(i).getInt("bindingTasksCount"));
					unload.setTrayTaskTotal(a);//绑定的托盘任务总数
					
					
					a=(StringUtils.equals(resultJson.getJSONArray("data").getJSONObject(i).getString("unloadedTotalPrices"), "null") ? 0:resultJson.getJSONArray("data").getJSONObject(i).getInt("unloadedTotalPrices"));
					unload.setUnLoadScanPieces(a);//卸车扫描件数
					
					unload.setUnloadCreator(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("unloaderName")));//卸车创建人
					unload.setUnloadCreatorCode(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("unloaderNo")));//卸车创建人编码
					
					unload.setUnloadTaskCreateTime(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("unloadTaskCreateTime")));//创建任务时间
					
					unload.setUnloadTaskNo(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("unloadTaskNo")));//卸车任务号
					unload.setVehicleNo(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("vehicleNo")));//卸车任务车牌号
					unload.setCount(resultJson.getLong("totalRows"));
					unloadbindTrayList.add(unload);
				}
			}
			
			return unloadbindTrayList;
		} //sonar 优化 降低复杂度 218427
		/*catch (TfrBusinessException e) {
			logger.error(e.getMessage());
			throw new TfrBusinessException(e.getMessage());
		}*/ catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new TfrBusinessException(ex.getMessage());
		}
		
	}
	
	//把“null”字符串转换为""
	public String isNotNull(String sta){
		if("null".equals(sta)){
			return "";
		}
		return sta;
	}
	
	/**
	 * @author 105795
	 * @date   2013-12-30
	 * @param  conditionDto
	 * @param  limit start
	 * @see    查询卸车任务绑定情况 零担
	 * */
	public List<UnloadbindTrayEntity> queryUnloadTaskbindTray(UnloadbindTrayQueryConditionDto conditionDto)
	{
		
		List<UnloadbindTrayEntity> unloadbindTrayList;

		//参数检查
		if(conditionDto==null)
		{
			logger.error("参数不合法");
			throw new TfrBusinessException("参数不合法");
		}
	
		//当前部门Code
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//查询对应外场编码
		String outfiledCode=getCenterCode(currentDeptCode);
		//初始化外场编码
		conditionDto.setOutfiledCode(outfiledCode);	
		//查询卸车任务绑定托盘信息
		try {
			unloadbindTrayList= unloadbindTrayDao.queryUnloadTaskbindTray(conditionDto);
			
			if(CollectionUtils.isEmpty(unloadbindTrayList)||unloadbindTrayList.size()<1)
			{
				throw new TfrBusinessException("没有查询到数据");
			}
			
			return calculateUnloadTaskbindTray(unloadbindTrayList);
			
		} catch (TfrBusinessException e) {
			logger.error(e.getMessage());
			throw new TfrBusinessException(e.getMessage());
		}
		
	}
	
	/**
	 * @author 105795
	 * @date   2013-12-30
	 * @param  conditionDto
	 * @param  limit start
	 * @see    查询卸车任务绑定情况  快递
	 * */
	public List<UnloadbindTrayExpressEntity> queryUnloadTaskbindTrayExpress(UnloadbindTrayQueryConditionDto conditionDto)
	{
		
		List<UnloadbindTrayExpressEntity> unloadbindTrayList=new ArrayList<UnloadbindTrayExpressEntity>();

		//参数检查
		if(conditionDto==null)
		{
			logger.error("参数不合法");
			throw new TfrBusinessException("参数不合法");
		} 
		//查询卸车任务绑定托盘信息
		try {
			//String resultData= unloadbindTrayDao.queryUnloadTaskbindTrayExpress(conditionDto);
			//JSONObject resultJson=JSONObject.fromObject(resultData);
			unloadbindTrayList = unloadbindTrayDao.queryUnloadTaskbindTrayExpress(conditionDto);
			
			
			return unloadbindTrayList;
			
		} catch (TfrBusinessException e) {
			logger.error(e.getMessage());
			throw new TfrBusinessException(e.getMessage());
		}
		
	}
	
	/**
	 * @author 105795
	 * @date   2013-12-30
	 * @param  conditionDto
	 * @param  limit start
	 * @see    计算 卸车任务叉车票数、绑定件数、叉车扫描件数、绑定率
	 * */
	private List<UnloadbindTrayEntity> calculateUnloadTaskbindTray(List<UnloadbindTrayEntity> unloadbindTrayList)
	{
		
		List<String> unloadTaskList=new ArrayList<String>();
		
		if(CollectionUtils.isNotEmpty(unloadbindTrayList)&&unloadbindTrayList.size()>0)
		{
			
				for(int i=0;i<unloadbindTrayList.size();i++)
				{
					if(StringUtil.isNotEmpty(unloadbindTrayList.get(i).getUnloadTaskNo()))
					{
						unloadTaskList.add(unloadbindTrayList.get(i).getUnloadTaskNo());
					}
			    }
				
			//根据卸车任务hao查询卸车任务绑定叉车票数、绑定的托盘任务总数、已扫描任务总数
			List<UnloadbindTrayEntity> unloadbindTaskforkliftTicksList=queryTotalforkliftTicksByUnloadTaskNo(unloadTaskList);
			if(unloadbindTaskforkliftTicksList.size()>0)
			{
				for(int i=0;i<unloadbindTrayList.size();i++)
				{
					for(int j=0;j<unloadbindTaskforkliftTicksList.size();j++)
					{
						if(unloadbindTrayList.get(i).getUnloadTaskNo().trim().
								equals(unloadbindTaskforkliftTicksList.get(j).getUnloadTaskNo().trim()))
						{
							unloadbindTrayList.get(i).setForkliftTicks(unloadbindTaskforkliftTicksList.get(j).getForkliftTicks());
							unloadbindTrayList.get(i).setTrayTaskTotal(unloadbindTaskforkliftTicksList.get(j).getTrayTaskTotal());
							unloadbindTrayList.get(i).setScannedTotal(unloadbindTaskforkliftTicksList.get(j).getScannedTotal());
						}
						
					}
					
				}
				
			}
		   //根据卸车任务号查询绑定托盘任务件数
			List<UnloadbindTrayEntity> unloadbindTaskPiecesList=queryBindTrayTaskPiecesByUnloadTaskNo(unloadTaskList);
			if(unloadbindTaskPiecesList.size()>0)
			{
				for(int i=0;i<unloadbindTrayList.size();i++)
				{
					for(int j=0;j<unloadbindTaskPiecesList.size();j++)
					{
						if(unloadbindTrayList.get(i).getUnloadTaskNo().trim().
								equals(unloadbindTaskPiecesList.get(j).getUnloadTaskNo().trim()))
						{
							unloadbindTrayList.get(i).setBindPieces(unloadbindTaskPiecesList.get(j).getBindPieces());
						}
						
					}
					
				}
				
			}
			
		   //根据卸车任务号查询绑定托盘任务中叉车扫描件数
			List<UnloadbindTrayEntity> unloadbindTaskScanPiecesList=queryBindTrayTaskScanPiecesByUnloadTaskNo(unloadTaskList);
			if(unloadbindTaskScanPiecesList.size()>0)
			{
				for(int i=0;i<unloadbindTrayList.size();i++)
				{
					for(int j=0;j<unloadbindTaskScanPiecesList.size();j++)
					{
						if(unloadbindTrayList.get(i).getUnloadTaskNo().trim().
								equals(unloadbindTaskScanPiecesList.get(j).getUnloadTaskNo().trim()))
						{
							unloadbindTrayList.get(i).setScanPieces(unloadbindTaskScanPiecesList.get(j).getScanPieces());
						}
						
					}
					
				}
			
		   }
		 //根据卸车任务号查询卸车任务中的总件数与总票数
			List<UnloadbindTrayEntity> unloadbindTaskTotalPiecesAndTotalVotesList=queryUnloadTaskTotalVotesAndTotalPiecesByUnloadTaskNo(unloadTaskList);
			if(unloadbindTaskTotalPiecesAndTotalVotesList.size()>0)
			{
				for(int i=0;i<unloadbindTrayList.size();i++)
				{
					for(int j=0;j<unloadbindTaskTotalPiecesAndTotalVotesList.size();j++)
					{
						if(unloadbindTrayList.get(i).getUnloadTaskNo().trim().
								equals(unloadbindTaskTotalPiecesAndTotalVotesList.get(j).getUnloadTaskNo().trim()))
						{
							//总票数
							unloadbindTrayList.get(i).setTotalTicks(unloadbindTaskTotalPiecesAndTotalVotesList.get(j).getTotalTicks());
							//总件数
							unloadbindTrayList.get(i).setTotalPieces(unloadbindTaskTotalPiecesAndTotalVotesList.get(j).getTotalPieces());

						}
						
					}
					
				}
			
		   }
		 	
			 ////根据卸车任务号查询PDA已经扫描件数
				List<UnloadbindTrayEntity> unloadTaskScanPiecesList=queryPDASanPiecesByUnloadTaskNo(unloadTaskList);
				if(unloadTaskScanPiecesList.size()>0)
				{
					for(int i=0;i<unloadbindTrayList.size();i++)
					{
						for(int j=0;j<unloadTaskScanPiecesList.size();j++)
						{
							if(unloadbindTrayList.get(i).getUnloadTaskNo().trim().
									equals(unloadTaskScanPiecesList.get(j).getUnloadTaskNo().trim()))
							{
								//PDA扫描件数
								unloadbindTrayList.get(i).setUnLoadScanPieces(unloadTaskScanPiecesList.get(j).getUnLoadScanPieces());

							}
							
						}
						
					}
				
			   }
			
		}
		//计算卸车任务绑定托盘绑定率
		calculateTrayBindRate(unloadbindTrayList);
		
		return unloadbindTrayList;
	}
	
	/**  零担
	 * @author 105795
	 * @date   2014-01-06
	 * @param  unloadTaskNo
	 * @see    查询对应卸车任务下绑定的托盘扫描任务
	 * */
	@Override
	public List<UnloadbindTrayDetailDto> queryUnloadbindTrayDetailByUnloadTaskNo(String unloadTaskNo,int limit,int start)
	{
		
		return unloadbindTrayDao.queryUnloadbindTrayDetailByUnloadTaskNo(unloadTaskNo, limit, start);
	}
	
	/**  快递
	 * @author 105795
	 * @date   2014-01-06
	 * @param  unloadTaskNo
	 * @see    查询对应卸车任务下绑定的托盘扫描任务
	 * */
	@Override
	public List<UnloadbindTrayDetailDto> queryUnloadbindTrayDetailByUnloadTaskNoExpress(String unloadTaskNo,String deptCode,int limit,int start)
	{
		List<UnloadbindTrayDetailDto> resultList = null;
		String resultData = null;
		try {
			//获取JSON格式的字符串数据
			resultData =unloadbindTrayDao.queryUnloadbindTrayDetailByUnloadTaskNoExpress(unloadTaskNo,deptCode, limit, start);
		}catch (Exception e) {
			logger.error("查询对应卸车任务下绑定的托盘扫描任务失败");
			throw new TfrBusinessException("查询对应卸车任务下绑定的托盘扫描任务失败");
		}
		if (resultData == null || "".equals(resultData)) {
			throw new TfrBusinessException("程序异常！");
		}
		//把数据转换成JSON格式
		JSONObject resultJson=JSONObject.fromObject(resultData);
		if(resultJson.getString("data") == null || "null".equals(resultJson.getString("data"))){
			logger.error("没有查询到数据");
			throw new TfrBusinessException("没有查询到数据");
		}else{
			resultList = new ArrayList<UnloadbindTrayDetailDto>();
			
			for(int i=0;i<resultJson.getJSONArray("data").size();i++){
				UnloadbindTrayDetailDto unload=new UnloadbindTrayDetailDto();
				unload.setTaskNo(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("trayBindingTaskNo")));//托盘任务编号
				
				unload.setWaybillNo(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("preceNo")));//运单号
				
				unload.setBindUserName(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("createName")));//绑定人姓名
				
				unload.setBindUserCode(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("createNo")));//绑定人工号
				
				unload.setBindTraytaskTime(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("createTime")));//绑定托盘任务时间
				
				unload.setForkliftDriverName(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("forkliftDriverName")));//叉车司机姓名
				
				unload.setForkliftDriverCode(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("forkliftDriverNo")));//叉车司机工号
				
				unload.setTrayscanTime(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("forkliftScanTime")));//叉车扫描时间
				
				unload.setPackageNo(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("preceNo")));//包号
				
				unload.setCount(resultJson.getLong("totalRows"));
				resultList.add(unload);
			}
			
		}
		return resultList;
	}
	
	/**  零担
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 查询对应卸车任务下绑定的托盘扫描任务
	 * 
	 * */
	@Override
	public List<UnloadbindTrayDetailDto> queryUnloadbindTrayDetailListByUnloadTaskNo(String unloadTaskNo)
	{
		//查询
		return unloadbindTrayDao.queryUnloadbindTrayDetailListByUnloadTaskNo(unloadTaskNo);
	}
	
	/**  快递
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 查询对应卸车任务下绑定的托盘扫描任务
	 * 
	 * */
	@Override
	public List<UnloadbindTrayDetailDto> queryUnloadbindTrayDetailListByUnloadTaskNoExpress(String unloadTaskNo)
	{
		//查询
		return unloadbindTrayDao.queryUnloadbindTrayDetailListByUnloadTaskNoExpress(unloadTaskNo);
	}
	
	/**
	 * @author 105795
	 * @date   2014-01-06
	 * @param  unloadTaskNo
	 * @see    根据卸车任务hao查询卸车任务绑定托盘总票数 
	 * */
	public List<UnloadbindTrayEntity> queryUnLoadTaskbindTrayTotalVotes (List<String> unloadTaskNoList)
	{
		if(CollectionUtils.isEmpty(unloadTaskNoList)&&unloadTaskNoList.size()==0)
		{
			
			throw new TfrBusinessException("卸车任务编号为空");
		}
		return unloadbindTrayDao.queryUnLoadTaskbindTrayTotalVotes(unloadTaskNoList);
			
		
	}
	
	/**
	 * @author 105795
	 * @date   2014-01-06
	 * @param  unloadTaskNo
	 * @see    根据卸车任务hao查询卸车任务绑定托盘总件数(包括已经解绑定)
	 * */
	public List<UnloadbindTrayEntity> queryUnLoadTaskbindTrayTotalPieces (List<String> unloadTaskNoList)
	{
		if(CollectionUtils.isEmpty(unloadTaskNoList)&&unloadTaskNoList.size()==0)
		{
			
			throw new TfrBusinessException("卸车任务编号为空");
		}
		return unloadbindTrayDao.queryUnLoadTaskbindTrayTotalPieces(unloadTaskNoList);
		
	}

	
	
	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 根据卸车任务号查询卸车任务计算叉车票数
	 * */
	public List<UnloadbindTrayEntity> queryTotalforkliftTicksByUnloadTaskNo(List<String> unloadTaskNoList)
	{
		return unloadbindTrayDao.queryTotalforkliftTicksByUnloadTaskNo(unloadTaskNoList);

	}
	
	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 根据卸车任务号查询绑定托盘任务件数
	 * */
	public List<UnloadbindTrayEntity> queryBindTrayTaskPiecesByUnloadTaskNo(List<String> unloadTaskNoList)
	{
		
		return unloadbindTrayDao.queryBindTrayTaskPiecesByUnloadTaskNo(unloadTaskNoList);

	}
	
	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 根据卸车任务号查询绑定托盘任务中叉车扫描件数
	 * */
	public List<UnloadbindTrayEntity> queryBindTrayTaskScanPiecesByUnloadTaskNo(List<String> unloadTaskNoList)
	{
		
		return unloadbindTrayDao.queryBindTrayTaskScanPiecesByUnloadTaskNo(unloadTaskNoList);

	}
	
	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 根据卸车任务号查询卸车任务中的总件数与总票数
	 * */
	public List<UnloadbindTrayEntity> queryUnloadTaskTotalVotesAndTotalPiecesByUnloadTaskNo(List<String> unloadTaskNoList)

	{
		
		return unloadbindTrayDao.queryUnloadTaskTotalVotesAndTotalPiecesByUnloadTaskNo(unloadTaskNoList);

	}
	
	
	/**
	 * @author 105795
	 * @date 2014-01-09
	 * @param unloadTaskNo
	 * @desc 根据任务号与运单号查询托盘绑定任务明细
	 * */
	public List<TrayScanDetaiEntity> queryTrayScanDetailByTaskNoAndWaybillNo(String taskNo,String waybillNo)
	{
		return unloadbindTrayDao.queryTrayScanDetailByTaskNoAndWaybillNo(taskNo,waybillNo);

	}
	/**
	 * @author 105795
	 * @date 2014-01-08
	 * @param unloadTaskNo
	 * @desc 计算卸车任务绑定托盘绑定率
	 * */
	private void calculateTrayBindRate(List<UnloadbindTrayEntity> unloadbindTrayList)
	{
		//绑定率
		String bindRate;
		
		//绑定件数
		double bindPieces;
		//PDA已扫描件数
		double unloadTaskScan;
		
		double result;
		
		if(CollectionUtils.isEmpty(unloadbindTrayList)||unloadbindTrayList.size()<1)
		{
			throw new TfrBusinessException("不存在卸车任务，不能计算托盘绑定率");
			
		}
		//循环计算每个卸车任务下的绑定率
		for(int i=0;i<unloadbindTrayList.size();i++)
		{
			
			 bindPieces=unloadbindTrayList.get(i).getBindPieces()/1.0;
			 unloadTaskScan=unloadbindTrayList.get(i).getUnLoadScanPieces()/1.0;
			
			 result=bindPieces/unloadTaskScan;
			//百分号后保留2位小数	
			 NumberFormat format = NumberFormat.getPercentInstance();
			 format.setMinimumFractionDigits(2);
			 bindRate=format.format(result);
			 //保留6位小数	
			 unloadbindTrayList.get(i).setBindRate(bindRate);
		}
	}
	
	/**
	 * @author 105795
	 * @date 2014-01-06
	 * @param unloadTaskNo
	 * @desc 根据卸车任务号查询PDA已经扫描件数
	 * */
	public List<UnloadbindTrayEntity> queryPDASanPiecesByUnloadTaskNo(List<String> unloadTaskNoList)

	{
		
		return unloadbindTrayDao.queryPDASanPiecesByUnloadTaskNo(unloadTaskNoList);

	}
	
	/**
	 *  零担
	 * @author 105795
	 * @date   2013-12-30
	 * @param  conditionDto
	 * @see    导出卸车任务绑定托盘情况
	 * */
	@Override
	public List exportUnloadTaskbindTrayExcel(
			UnloadbindTrayQueryConditionDto conditionDto) {

		List<UnloadbindTrayEntity> unloadbindTrayList;

		List<List<String>> rowList = new ArrayList<List<String>>();
		//参数检查
		if(conditionDto==null)
		{
			throw new TfrBusinessException("参数不合法");
		}
		
		//当前部门Code
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//查询对应外场编码
		String outfiledCode=getCenterCode(currentDeptCode);
		//初始化外场编码
		conditionDto.setOutfiledCode(outfiledCode);	
		unloadbindTrayList= unloadbindTrayDao.queryUnloadTaskbindTrayExcel(conditionDto);

		
		//如果查询结果为空，则导出空文件
		if(CollectionUtils.isEmpty(unloadbindTrayList)){
			List<String> columnList = new ArrayList<String>();
			rowList.add(columnList);
		}
		//计算 卸车任务绑定总票数、总件数、绑定件数、叉车扫描件数、绑定率
		unloadbindTrayList=calculateUnloadTaskbindTray(unloadbindTrayList);
		
		for(UnloadbindTrayEntity unloadbindTray : unloadbindTrayList){
			List<String> columnList = new ArrayList<String>();
			//卸车任务编号
			columnList.add(unloadbindTray.getUnloadTaskNo()==null ?"":unloadbindTray.getUnloadTaskNo());
			//卸车创建人
			columnList.add(unloadbindTray.getUnloadCreator()==null ?"":unloadbindTray.getUnloadCreator());
			//总票数
			columnList.add(unloadbindTray.getTotalTicks()+"");
			//总件数
			columnList.add(unloadbindTray.getTotalPieces()+"");
			//已卸车件数
			columnList.add(unloadbindTray.getUnLoadScanPieces()+"");
			//已绑定件数
			columnList.add(unloadbindTray.getBindPieces()+"");
			//托盘绑定率
			columnList.add(unloadbindTray.getBindRate());
			//绑定任务数
			columnList.add(unloadbindTray.getTrayTaskTotal()+"");
			//扫描任务数
			columnList.add(unloadbindTray.getScannedTotal()+"");
			//叉车扫描件数
			columnList.add(unloadbindTray.getScanPieces()+"");
			//车牌号
			columnList.add(unloadbindTray.getVehicleNo()==null?"":unloadbindTray.getVehicleNo());
			//叉车票数
			columnList.add(unloadbindTray.getForkliftTicks()+"");
			//任务类型
			//columnList.add(unloadbindTray.getTrayscanType());
			rowList.add(columnList);
		}
		
		String[] rowHeaders={
				"卸车任务编号",
				"卸车创建人",
				"总票数",
				"总件数",
				"已卸车件数",
				"已绑定件数",
				"托盘绑定率",
				"绑定任务数",
				"扫描任务数",
				"叉车扫描件数",
				"车牌号",
				"叉车票数"	
		};
		//导出资源类
		ExportResource exportResource = new ExportResource();
		//设置导出文件的表头
	    exportResource.setHeads(rowHeaders);
	    //设置导出数据
	    exportResource.setRowList(rowList);
	    //导出设置
	    ExportSetting exportSetting = new ExportSetting();
	    //设置sheetname
	    exportSetting.setSheetName("卸车任务绑定托盘列表");
	    //设置sheet行数
	    exportSetting.setSize(unloadbindTrayList.size() + 1);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    List list = new ArrayList();
	    InputStream excelStream=null;
		try {
			// 获取输入流
			 excelStream = objExporterExecutor.exportSync(
					exportResource, exportSetting);

			// 文件名
			String name = "卸车任务绑定托盘明细";
			String fileName;

			String agent = (String) ServletActionContext.getRequest()
					.getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") == -1) {
				fileName = new String(name.getBytes("UTF-8"), "iso-8859-1");
			} else {
				fileName = URLEncoder.encode(name, "UTF-8");
			}
			list.add(fileName);
			list.add(excelStream);
		} catch (UnsupportedEncodingException e) {
			//抛出业务异常
			throw new TfrBusinessException(e.getMessage());
		} //设置fileName
		finally{
			if(excelStream!=null)
			{
				try {
					excelStream.close();
				} catch (IOException e) {
					logger.error("{exportUnloadTaskbindTrayExcel：} 文件关闭失败");
					throw new TfrBusinessException("文件关闭失败", "");
					
				}
			}
		}
        
       
        //返回action
        return list;
	}
	
	/**
	 *  快递
	 * @author 105795
	 * @date   2013-12-30
	 * @param  conditionDto
	 * @see    导出卸车任务绑定托盘情况
	 * */
	@Override
	public List exportUnloadTaskbindTrayExcelExpress(
			UnloadbindTrayQueryConditionDto conditionDto) {

		
		boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
		logger.error("导出卸车任务绑定托盘开关" + tfrSwitch4Ecs);
		if (!tfrSwitch4Ecs) {
			return new ArrayList();
		}
		List<UnloadbindTrayExpressEntity> unloadbindTrayList;

		List<List<String>> rowList = new ArrayList<List<String>>();
		//参数检查
		if(conditionDto==null)
		{
			throw new TfrBusinessException("参数不合法");
		}
		
		//当前部门Code
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//查询对应外场编码
		String outfiledCode=getCenterCode(currentDeptCode);
		//初始化外场编码
		conditionDto.setOutfiledCode(outfiledCode);	
		unloadbindTrayList= queryUnloadTaskbindTrayListExpress(conditionDto,ConstantsNumberSonar.SONAR_NUMBER_10000,0);

		
		//如果查询结果为空，则导出空文件
		if(CollectionUtils.isEmpty(unloadbindTrayList)){
			List<String> columnList = new ArrayList<String>();
			rowList.add(columnList);
		}
		//计算 卸车任务绑定总票数、总件数、绑定件数、叉车扫描件数、绑定率
		/*unloadbindTrayList=calculateUnloadTaskbindTray(unloadbindTrayList);*/
		
		for(UnloadbindTrayExpressEntity unloadbindTray : unloadbindTrayList){
			List<String> columnList = new ArrayList<String>();
			//卸车任务编号
			columnList.add(unloadbindTray.getUnloadTaskNo()==null ?"":unloadbindTray.getUnloadTaskNo());
			//卸车创建人
			columnList.add(unloadbindTray.getUnloadCreator()==null ?"":unloadbindTray.getUnloadCreator());
			//卸车创建人 工号
			columnList.add(unloadbindTray.getUnloadCreatorCode()==null ?"":unloadbindTray.getUnloadCreatorCode());
			//总票数
			columnList.add(unloadbindTray.getTotalTicks()+"");
			//已卸车件数
			columnList.add(unloadbindTray.getUnLoadScanPieces()+"");
			//已绑定件数
			columnList.add(unloadbindTray.getBindPieces()+"");
			//托盘绑定率
			columnList.add(unloadbindTray.getBindRate());
			//绑定任务数
			columnList.add(unloadbindTray.getTrayTaskTotal()+"");
			//扫描任务数
			columnList.add(unloadbindTray.getScannedTotal()+"");
			//叉车扫描件数
			columnList.add(unloadbindTray.getScanPieces()+"");
			//车牌号
			columnList.add(unloadbindTray.getVehicleNo()==null?"":unloadbindTray.getVehicleNo());
			//叉车票数
			columnList.add(unloadbindTray.getForkliftTicks()+"");
			//任务类型
			//columnList.add(unloadbindTray.getTrayscanType());
			rowList.add(columnList);
		}
		
		String[] rowHeaders={
				"卸车任务编号",
				"卸车创建人",
				"卸车创建人工号",
				"总票数",
				"已卸车件数",
				"已绑定件数",
				"托盘绑定率",
				"绑定任务数",
				"扫描任务数",
				"叉车扫描件数",
				"车牌号",
				"叉车票数"	
		};
		//导出资源类
		ExportResource exportResource = new ExportResource();
		//设置导出文件的表头
	    exportResource.setHeads(rowHeaders);
	    //设置导出数据
	    exportResource.setRowList(rowList);
	    //导出设置
	    ExportSetting exportSetting = new ExportSetting();
	    //设置sheetname
	    exportSetting.setSheetName("卸车任务绑定托盘列表");
	    //设置sheet行数
	    exportSetting.setSize(unloadbindTrayList.size() + 1);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    List list = new ArrayList();
	    InputStream excelStream=null;
		try {
			// 获取输入流
			 excelStream = objExporterExecutor.exportSync(
					exportResource, exportSetting);

			// 文件名
			String name = "卸车任务绑定托盘明细";
			String fileName;

			String agent = (String) ServletActionContext.getRequest()
					.getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") == -1) {
				fileName = new String(name.getBytes("UTF-8"), "iso-8859-1");
			} else {
				fileName = URLEncoder.encode(name, "UTF-8");
			}
			list.add(fileName);
			list.add(excelStream);
		} catch (UnsupportedEncodingException e) {
			//抛出业务异常
			throw new TfrBusinessException(e.getMessage());
		} //设置fileName
		finally{
			if(excelStream!=null)
			{
				try {
					excelStream.close();
				} catch (IOException e) {
					logger.error("{exportUnloadTaskbindTrayExcel：} 文件关闭失败");
					throw new TfrBusinessException("文件关闭失败", "");
					
				}
			}
		}
        
       
        //返回action
        return list;
	}
	
	
	/**
	 *  零担
	 * @author 105795
	 * @date 2014-01-10
	 * @param unloadTaskNo
	 * @desc 	根据卸车任务号导出卸车任务绑定托盘流水号明细list--
	 * */
	public List exportUnloadTaskbindTrayDetailExcelByUnloadTaskNo(String unloadTaskNo)
	{
		
		
		List<UnloadTaskbindTrayDetailseriaNo> unloadbindTraySeriaNoList;

		List<List<String>> rowList = new ArrayList<List<String>>();
		//参数检查
		if(StringUtil.isEmpty(unloadTaskNo))
		{
			throw new TfrBusinessException("卸车任务号为空");
		}
	    //访问后台获取数据
		unloadbindTraySeriaNoList=unloadbindTrayDao.exportUnloadTaskbindTrayDetailExcelByUnloadTaskNo(unloadTaskNo);
		//如果查询结果为空，则导出空文件
		if(CollectionUtils.isEmpty(unloadbindTraySeriaNoList)){
			List<String> columnList = new ArrayList<String>();
			rowList.add(columnList);
		}else{
			
			for(UnloadTaskbindTrayDetailseriaNo unloadbindTrayseriaNo : unloadbindTraySeriaNoList){
				List<String> columnList = new ArrayList<String>();
				//托盘任务编号
				columnList.add(unloadbindTrayseriaNo.getTaskNo()==null ?"":unloadbindTrayseriaNo.getTaskNo());
				//包号
				columnList.add(unloadbindTrayseriaNo.getPackageNo()==null ?"":unloadbindTrayseriaNo.getPackageNo());
				//运单号
				columnList.add(unloadbindTrayseriaNo.getWaybillNo()==null ?"":unloadbindTrayseriaNo.getWaybillNo());
				//流水号
				columnList.add(unloadbindTrayseriaNo.getSeriaNo()==null ?"":unloadbindTrayseriaNo.getSeriaNo());
				//绑定人姓名
				columnList.add(unloadbindTrayseriaNo.getBindUserName()==null ?"":unloadbindTrayseriaNo.getBindUserName());
				//绑定人工号
				columnList.add(unloadbindTrayseriaNo.getBindUserCode()==null ?"":unloadbindTrayseriaNo.getBindUserCode());
				//绑定托盘任务时间
				columnList.add(unloadbindTrayseriaNo.getBindTraytaskTime()==null ?"":unloadbindTrayseriaNo.getBindTraytaskTime());
				//叉车司机姓名
				columnList.add(unloadbindTrayseriaNo.getForkliftDriverName()==null ?"":unloadbindTrayseriaNo.getForkliftDriverName());
				//叉车司机工号
				columnList.add(unloadbindTrayseriaNo.getForkliftDriverCode()==null ?"":unloadbindTrayseriaNo.getForkliftDriverCode());
				//叉车扫描时间
				columnList.add(unloadbindTrayseriaNo.getTrayscanTime()==null ?"":unloadbindTrayseriaNo.getTrayscanTime());
				
				rowList.add(columnList);
			}
		}
		
		String[] rowHeaders={
				"托盘任务编号",
				"包号",
				"运单号",
				"流水号",
				"绑定人姓名",
				"绑定人工号",
				"绑定托盘任务时间",
				"叉车司机姓名",
				"叉车司机工号",
				"叉车扫描时间"	
		};
		//导出资源类
		ExportResource exportResource = new ExportResource();
		//设置导出文件的表头
	    exportResource.setHeads(rowHeaders);
	    //设置导出数据
	    exportResource.setRowList(rowList);
	    //导出设置
	    ExportSetting exportSetting = new ExportSetting();
	    //设置sheetname
	    exportSetting.setSheetName("卸车任务托盘绑定明细");
	    //设置sheet行数
	    exportSetting.setSize(unloadbindTraySeriaNoList.size() + 1);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    //获取输入流
	    InputStream excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);

        //文件名
        String name ="托盘绑定托盘明细";
        String fileName ;  
		try {
			String agent = (String)ServletActionContext.getRequest().getHeader("USER-AGENT");
	    	if(agent != null && agent.indexOf("MSIE") == -1) {
	    		fileName = new String(name.getBytes("UTF-8"), "iso-8859-1");
	    	} else {
	    		fileName = URLEncoder.encode(name, "UTF-8");
	    	}
		} catch (UnsupportedEncodingException e) {
			//抛出业务异常
			throw new TfrBusinessException(e.getMessage());
			
		}finally{
			if(excelStream!=null)
			{
				try {
					excelStream.close();
				} catch (IOException e) {
					logger.error("{exportUnloadTaskbindTrayExcel：} 文件关闭失败");
					throw new TfrBusinessException("文件关闭失败", "");
					
				}
			}
		} 
		//设置fileName
        List list = new ArrayList();
        list.add(fileName);
        list.add(excelStream);
        //返回action
        return list;
	}
	
	
	/**
	 *  快递
	 * @author 105795
	 * @date 2014-01-10
	 * @param unloadTaskNo
	 * @desc 	根据卸车任务号导出卸车任务绑定托盘流水号明细list--
	 * */
	public List exportUnloadTaskbindTrayDetailExcelByUnloadTaskNoExpress(String unloadTaskNo,String deptCode)
	{
		
		
		List<UnloadbindTrayDetailDto> unloadbindTraySeriaNoList;

		List<List<String>> rowList = new ArrayList<List<String>>();
		//参数检查
		if(StringUtil.isEmpty(unloadTaskNo))
		{
			throw new TfrBusinessException("卸车任务号为空");
		}
	    //访问后台获取数据
		unloadbindTraySeriaNoList=queryUnloadbindTrayDetailByUnloadTaskNoExpress(unloadTaskNo,deptCode,ConstantsNumberSonar.SONAR_NUMBER_10000,0);
		//如果查询结果为空，则导出空文件
		if(CollectionUtils.isEmpty(unloadbindTraySeriaNoList)){
			List<String> columnList = new ArrayList<String>();
			rowList.add(columnList);
		}else{
			
			for(UnloadbindTrayDetailDto unloadbindTrayseriaNo : unloadbindTraySeriaNoList){
				List<String> columnList = new ArrayList<String>();
				//托盘任务编号
				columnList.add(unloadbindTrayseriaNo.getTaskNo()==null ?"":unloadbindTrayseriaNo.getTaskNo());
				//运单号
				columnList.add(unloadbindTrayseriaNo.getWaybillNo()==null ?"":unloadbindTrayseriaNo.getWaybillNo());
				//绑定人姓名
				columnList.add(unloadbindTrayseriaNo.getBindUserName()==null ?"":unloadbindTrayseriaNo.getBindUserName());
				//绑定人工号
				columnList.add(unloadbindTrayseriaNo.getBindUserCode()==null ?"":unloadbindTrayseriaNo.getBindUserCode());
				//绑定托盘任务时间
				columnList.add(unloadbindTrayseriaNo.getBindTraytaskTime()==null ?"":unloadbindTrayseriaNo.getBindTraytaskTime());
				//叉车司机姓名
				columnList.add(unloadbindTrayseriaNo.getForkliftDriverName()==null ?"":unloadbindTrayseriaNo.getForkliftDriverName());
				//叉车司机工号
				columnList.add(unloadbindTrayseriaNo.getForkliftDriverCode()==null ?"":unloadbindTrayseriaNo.getForkliftDriverCode());
				//叉车扫描时间
				columnList.add(unloadbindTrayseriaNo.getTrayscanTime()==null ?"":unloadbindTrayseriaNo.getTrayscanTime());
				
				rowList.add(columnList);
			}
		}
		
		String[] rowHeaders={
				"托盘任务编号",
				"运单/包/笼号",
				"绑定人姓名",
				"绑定人工号",
				"绑定托盘任务时间",
				"叉车司机姓名",
				"叉车司机工号",
				"叉车扫描时间"	
		};
		//导出资源类
		ExportResource exportResource = new ExportResource();
		//设置导出文件的表头
	    exportResource.setHeads(rowHeaders);
	    //设置导出数据
	    exportResource.setRowList(rowList);
	    //导出设置
	    ExportSetting exportSetting = new ExportSetting();
	    //设置sheetname
	    exportSetting.setSheetName("卸车任务托盘绑定明细");
	    //设置sheet行数
	    exportSetting.setSize(unloadbindTraySeriaNoList.size() + 1);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    //获取输入流
	    InputStream excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);

        //文件名
        String name ="托盘绑定托盘明细";
        String fileName ;  
		try {
			String agent = (String)ServletActionContext.getRequest().getHeader("USER-AGENT");
	    	if(agent != null && agent.indexOf("MSIE") == -1) {
	    		fileName = new String(name.getBytes("UTF-8"), "iso-8859-1");
	    	} else {
	    		fileName = URLEncoder.encode(name, "UTF-8");
	    	}
		} catch (UnsupportedEncodingException e) {
			//抛出业务异常
			throw new TfrBusinessException(e.getMessage());
			
		}finally{
			if(excelStream!=null)
			{
				try {
					excelStream.close();
				} catch (IOException e) {
					logger.error("{exportUnloadTaskbindTrayExcel：} 文件关闭失败");
					throw new TfrBusinessException("文件关闭失败", "");
					
				}
			}
		} 
		//设置fileName
        List list = new ArrayList();
        list.add(fileName);
        list.add(excelStream);
        //返回action
        return list;
	}
	
	/**
	 * 调用综合接口通过当前部门编码查询对应外场编码，如果查询不到则返当前部门编码
	 * 
	 * @author wqh
	 * @date 2013-12-27 
	 * @param orgCode
	 * */
	private String getCenterCode(String orgCode) {
		OrgAdministrativeInfoEntity unloadOrg = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(orgCode);
		String transferCenterCode = orgCode;
		if (unloadOrg != null) {
			if (FossConstants.YES.equals(unloadOrg.getSalesDepartment())) {
				SaleDepartmentEntity saleDetp = saleDepartmentService
						.querySaleDepartmentByCode(unloadOrg.getCode());
				if (saleDetp != null
						&& FossConstants.YES.equals(saleDetp.getStation())) {
					unloadOrg = orgAdministrativeInfoService
							.queryOrgAdministrativeInfoByCode(saleDetp
									.getTransferCenter());
				}
			} else {
				unloadOrg = pdaCommonService.getCurrentOutfieldCode(orgCode);
			}
		}
		if (unloadOrg == null) {
			logger.error("查询不到组织：" + orgCode + "对应的外场!");
		} else {
			transferCenterCode = unloadOrg.getCode();
		}

		return transferCenterCode;
	}

	
	public void setUnloadbindTrayDao(IUnloadbindTrayDao unloadbindTrayDao) {
		this.unloadbindTrayDao = unloadbindTrayDao;
	}

	/**
	 * Sets the org administrative info service.
	 * 
	 * @param orgAdministrativeInfoService
	 *            the new org administrative info service
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * Sets the sale department service.
	 * 
	 * @param saleDepartmentService
	 *            the new sale department service
	 */
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	/**
	 * Sets the pda common service.
	 * 
	 * 
	 * @param pdaCommonService
	 *            the new pda common service
	 */
	public void setPdaCommonService(IPDACommonService pdaCommonService) {
		this.pdaCommonService = pdaCommonService;
	}
	
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

}
