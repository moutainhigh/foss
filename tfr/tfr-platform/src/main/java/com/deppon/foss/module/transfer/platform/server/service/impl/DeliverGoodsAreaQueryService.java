/**   
* @Title: DeliverGoodsAreaQueryService.java 
* @Package com.deppon.foss.module.transfer.platform.server.service.impl 
* @Description:
* @author shiwei shiwei@outlook.com
* @date 2014年3月3日 上午9:50:43 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;




import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserOrgRoleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.platform.api.server.dao.IQueryWaybillAvgTimeDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IDeliverGoodsAreaQueryService;
import com.deppon.foss.module.transfer.platform.api.server.service.IPullbackRateService;
import com.deppon.foss.module.transfer.platform.api.server.service.IReturnRateService;
import com.deppon.foss.module.transfer.platform.api.server.service.ISendRateService;
import com.deppon.foss.module.transfer.platform.api.server.service.IStockPairService;
import com.deppon.foss.module.transfer.platform.api.shared.define.DeliverConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.PullbackRateEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ReturnRateEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.SendRateEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TransCenterOrgEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.WaybillAvgTimeEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.module.transfer.platform.api.shared.vo.DeliverGoodsAreaQueryVo;
import com.deppon.foss.util.DateUtils;

/** 
 * @ClassName: DeliverGoodsAreaQueryService 
 * @Description: 派送情况查询service类
 * @author shiwei shiwei@outlook.com
 * @date 2014年3月3日 上午9:50:43 
 *  
 */
public class DeliverGoodsAreaQueryService implements IDeliverGoodsAreaQueryService {
	
	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	/**
	 * 组织部门service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * 拉回率Service
	* @fields pullbackRateService
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午4:41:25
	* @version V1.0
	*/
	private IPullbackRateService pullbackRateService;
	
	/**
	 * 退单率Service
	* @fields returnRateService
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午4:41:28
	* @version V1.0
	*/
	private IReturnRateService returnRateService;
	
	
	/**
	 * 派送率Service
	* @fields sendRateService
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:40:31
	* @version V1.0
	*/
	private ISendRateService sendRateService;
	
	
	/**
	 * 库存副表的Service
	* @fields stockPairService
	* @author 14022-foss-songjie
	* @update 2014年3月13日 下午2:06:12
	* @version V1.0
	*/
	private IStockPairService stockPairService; 
	
	/**
	 * 查询角色信息Service
	*/
	private IUserOrgRoleService userOrgRoleService;
	
	
	private IQueryWaybillAvgTimeDao queryWaybillAvgTimeDao;
	
	

	public void setQueryWaybillAvgTimeDao(
			IQueryWaybillAvgTimeDao queryWaybillAvgTimeDao) {
		this.queryWaybillAvgTimeDao = queryWaybillAvgTimeDao;
	}



	/** 
	 * @param orgAdministrativeInfoComplexService 要设置的 orgAdministrativeInfoComplexService 
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}



	/**
	* @description 设置拉回率Service
	* @param pullbackRateService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午4:42:12
	*/
	public void setPullbackRateService(IPullbackRateService pullbackRateService) {
		this.pullbackRateService = pullbackRateService;
	}

	
	/**
	* @description 设置退单率Service
	* @param returnRateService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午4:42:35
	*/
	public void setReturnRateService(IReturnRateService returnRateService) {
		this.returnRateService = returnRateService;
	}


	
	/**
	* @description 设置派送率Service
	* @param sendRateService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午9:40:56
	*/
	public void setSendRateService(ISendRateService sendRateService) {
		this.sendRateService = sendRateService;
	}
	
	public void setUserOrgRoleService(IUserOrgRoleService userOrgRoleService) {
		this.userOrgRoleService = userOrgRoleService;
	}
	
	
	/**
	* @description 设置 库存副表的Service
	* @param stockPairService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月13日 下午2:07:10
	*/
	public void setStockPairService(IStockPairService stockPairService) {
		this.stockPairService = stockPairService;
	}

	/** 
	 * @Title: queryOutfieldInfo 
	 * @Description: 查询外场信息，查询不到则视为统计部门
	 * @author shiwei shiwei@outlook.com
	 * @date 2014年3月3日 上午9:50:43 
	 * @param @return    设定文件 
	 * @throws 
	 */
	@Override
	public String[] queryOutfieldInfo() {
		//获取当前登录部门code
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		//设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			//返回部门code name字符串数组
			return new String[]{orgAdministrativeInfoEntity.getCode(),orgAdministrativeInfoEntity.getName()};
		}else{
			//获取上级部门失败
			LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级转运场失败，视为统计部门登录！##########");
			return null;
		}
	}


	/**
	* @description 日派送率分页查询
	* @param sendRateEntity
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月19日 上午8:38:19
	*/
	@Override
	public List<SendRateEntity> querySendRateList(
			SendRateEntity sendRateEntity, int start, int limit) {
		List<SendRateEntity> backList = sendRateService.querySendRateList(sendRateEntity, start, limit);
		if(backList!=null){
			List<String> orgCodeList = new ArrayList<String>(); 
			for (SendRateEntity pojo : backList) {
				orgCodeList.add(pojo.getOrgCode());
			}
			Date currentDate = new Date();
			currentDate = DateUtils.convert(DateUtils.convert(currentDate), DateUtils.DATE_FORMAT);
			//Date forecastDate = DateUtils.addDayToDate(currentDate, 1);
			
			//List<ForecastQuantityEntity> forecastList = forecastServicePlatform.queryForecastQtyByRelevantOrgCode(orgCodeList, forecastDate);
				for (SendRateEntity pojo2 : backList) {
					/*List<QuantityStaDto> staDto = quantityStaService.query2ndDepartDeliverFcst(pojo2.getOrgCode(),currentDate);
					if(staDto!=null && staDto.size()>0){
						实体中有orgCode外场编码，forecastWeightTotal预测重量
						，forecastVolumeTotal预测体积，forecastQtyTotal预测票数；
						QuantityStaDto quantityStaDto = staDto.get(0);
						//预测票数
						if(quantityStaDto.getForecastQtyTotal()!=null){
							pojo2.setTomorrowStockWaybill(Long.parseLong(quantityStaDto.getForecastQtyTotal()+""));
						}else{
							pojo2.setTomorrowStockWaybill(0L);
						}
						//预测体积
						if(quantityStaDto.getForecastVolumeTotal()!=null){
							pojo2.setTomorrowStockVolume(quantityStaDto.getForecastVolumeTotal());
						}else{
							pojo2.setTomorrowStockVolume(BigDecimal.ZERO);
						}
						//预测重量
						if(quantityStaDto.getForecastWeightTotal()!=null){
							pojo2.setTomorrowStockWeight(quantityStaDto.getForecastWeightTotal());
						}else{
							pojo2.setTomorrowStockWeight(BigDecimal.ZERO);
						}
					}*/
				  Date nowDate=new Date();
				  Calendar calendar = Calendar.getInstance();  //得到日历
				  calendar.setTime(nowDate);//把当前时间赋给日历
				  calendar.add(Calendar.DAY_OF_MONTH, 1);  //设置为后一天
				  Date nextDate=calendar.getTime();//得到第二天的时间
				  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
                  String queryDate=sdf.format(nextDate);
				  List<SendRateEntity> 	sendRateList=sendRateService.queryForeCastDeliverGoodsByDate(sendRateEntity.getOrgCode(), queryDate);
				  if(CollectionUtils.isNotEmpty(sendRateList)&&sendRateList.size()>0){
					  for(SendRateEntity srEntity:sendRateList)
					  {
						  if(pojo2.getOrgCode().equalsIgnoreCase(srEntity.getOrgCode())){
							  pojo2.setTomorrowStockWaybill(srEntity.getTomorrowStockWaybill()==null?0:srEntity.getTomorrowStockWaybill());
							  pojo2.setTomorrowStockVolume(srEntity.getTomorrowStockVolume()==null?BigDecimal.ZERO:srEntity.getTomorrowStockVolume());
							  pojo2.setTomorrowStockWeight(srEntity.getTomorrowStockWeight()==null?BigDecimal.ZERO:srEntity.getTomorrowStockWeight());

						  }
						  
					  }
					  
					  
				  }else{
					  pojo2.setTomorrowStockWaybill(0L);
					  pojo2.setTomorrowStockVolume(BigDecimal.ZERO);
					  pojo2.setTomorrowStockWeight(BigDecimal.ZERO);

				  }	
				  
				  
				}
			
		}
		return backList;
	}
	
	/**
	* @description 日派送率分页查询 总记录数
	* @param sendRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月19日 上午8:38:22
	*/
	@Override
	public long querySendRateListCount(SendRateEntity sendRateEntity) {
		return sendRateService.querySendRateListCount(sendRateEntity);
	}

	/**
	* @description 日派送率导出结果
	* @param sendRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月19日 上午8:40:06
	*/
	@Override
	public InputStream sendRateDayExport(SendRateEntity sendRateEntity) {
		InputStream excelStream = null;
		try {
			 List<SendRateEntity> querySendRateList;
			 querySendRateList = this.querySendRateList(sendRateEntity, -1, -1);
			 List<TransCenterOrgEntity> transCenterList = stockPairService.queryAllTransOrg();
			// 行List
			List<List<String>> rowList = new ArrayList<List<String>>();
			if (querySendRateList != null) {
				for (SendRateEntity dto : querySendRateList) {
					// 每行的列List
					//"外场","日期","前一日剩余派送量票数","前一日剩余派送量重量","前一日剩余派送量体积",
					//"当日入库货量票数","当日入库货量重量","当日入库货量体积","已派送量票数","已派送量重量","已派送量体积","派送率(F)",
					//"预计后一日派送量票数","预计后一日派送量重量","预计后一日派送量体积"
					List<String> columnList = new ArrayList<String>();
					//本部门
					columnList.add(queryNameOrgUp(dto.getOrgCode(),transCenterList).getBigdept());
					//事业部
					columnList.add(queryNameOrgUp(dto.getOrgCode(),transCenterList).getDivision());
					columnList.add(dto.getOrgName());
					columnList.add(DateUtils.convert(dto.getStatisticsTimeTheory(),DateUtils.DATE_FORMAT));
					columnList.add(dto.getYesterdayStockWaybill() + "");
					columnList.add(dto.getYesterdayStockWeight() + "");
					columnList.add(dto.getYesterdayStockVolume() + "");
					columnList.add(dto.getDayStockWaybill() + "");
					columnList.add(dto.getDayStockWeight() + "");
					columnList.add(dto.getDayStockVolume() + "");
					columnList.add(dto.getDaySendWaybill() + "");
					columnList.add(dto.getDaySendWeight() + "");
					columnList.add(dto.getDaySendVolume() + "");
					columnList.add(dto.getSendRate()==null?"0":dto.getSendRate() + "%");
					columnList.add(dto.getTomorrowStockWaybill() + "");
					columnList.add(dto.getTomorrowStockWeight() + "");
					columnList.add(dto.getTomorrowStockVolume() + "");
					rowList.add(columnList);
				}
			}
						
			SheetData sheetData = new SheetData();
			sheetData.setRowHeads(DeliverConstants.SEND_ROW_HEADS);
			sheetData.setRowList(rowList);

			ExcelExport excelExportUtil = new ExcelExport();
			excelStream = excelExportUtil.inputToClient(excelExportUtil
					.exportExcel(sheetData, DeliverConstants.SEND_SHEET_NAME,
							DeliverConstants.SHEET_SIZE));
		} catch (BusinessException e) {
			LOGGER.error("导出日派送率异常", e);
			throw new BusinessException("导出日派送率异常", e);
		}
		return excelStream;
	}

	/**
	* @description 退单率查询分页
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.service.IDeliverGoodsAreaQueryService#queryReturnRateList(com.deppon.foss.module.transfer.platform.api.shared.domain.ReturnRateEntity)
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午10:10:28
	* @version V1.0
	*/
	@Override
	public List<ReturnRateEntity> queryReturnRateList(
			ReturnRateEntity returnRateEntity,int start,int limit) {
		return this.returnRateService.queryReturnRateList(returnRateEntity, start, limit);
	}

	
	/**
	* @description 退单率查询分页总记录数
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.service.IDeliverGoodsAreaQueryService#queryReturnRateListCount()
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午10:10:31
	* @version V1.0
	*/
	@Override
	public long queryReturnRateListCount(ReturnRateEntity returnRateEntity) {
		return this.returnRateService.queryReturnRateListCount(returnRateEntity);
	}

	
	
	/**
	* @description 导出日退单率
	* @param returnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 下午4:13:59
	*/
	@Override
	public InputStream returnRateDayExport(ReturnRateEntity returnRateEntity) {
		InputStream excelStream = null;
		try {
			 List<ReturnRateEntity> queryReturnRateList;
			queryReturnRateList = this.queryReturnRateList(returnRateEntity, -1, -1);
			List<TransCenterOrgEntity> transCenterList = stockPairService.queryAllTransOrg();
			// 行List
			List<List<String>> rowList = new ArrayList<List<String>>();
			if (queryReturnRateList != null) {
				for (ReturnRateEntity dto : queryReturnRateList) {
					// 每行的列List
					//"外场","日期","排单票数","装车票数","退单票数","退单率
					List<String> columnList = new ArrayList<String>();
					//本部门
					columnList.add(queryNameOrgUp(dto.getOrgCode(),transCenterList).getBigdept());
					//事业部
					columnList.add(queryNameOrgUp(dto.getOrgCode(),transCenterList).getDivision());
					columnList.add(dto.getOrgName());
					columnList.add(DateUtils.convert(dto.getStatisticsTimeTheory(),DateUtils.DATE_FORMAT));
					columnList.add(dto.getForecastWaybill() + "");
					columnList.add(dto.getQuantityCarReality() + "");
					columnList.add(dto.getQuantityReturn() + "");
					columnList.add(dto.getReturnRate()==null?"0":dto.getReturnRate() + "%");
					rowList.add(columnList);
				}
			}
						
			SheetData sheetData = new SheetData();
			sheetData.setRowHeads(DeliverConstants.RETURN_ROW_HEADS);
			sheetData.setRowList(rowList);

			ExcelExport excelExportUtil = new ExcelExport();
			excelStream = excelExportUtil.inputToClient(excelExportUtil
					.exportExcel(sheetData, DeliverConstants.RETURN_SHEET_NAME,
							DeliverConstants.SHEET_SIZE));
		} catch (BusinessException e) {
			LOGGER.error("导出日退单率异常", e);
			throw new BusinessException("导出日退单率异常", e);
		}
		return excelStream;
	}

	/**
	* @description  拉回率查询分页
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.service.IDeliverGoodsAreaQueryService#queryPullbackRateList(com.deppon.foss.module.transfer.platform.api.shared.domain.PullbackRateEntity)
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午10:10:34
	* @version V1.0
	*/
	@Override
	public List<PullbackRateEntity> queryPullbackRateList(
			PullbackRateEntity pullbackRateEntity,int start,int limit) {
		return this.pullbackRateService.queryPullbackRateList(pullbackRateEntity, start, limit);
	}

	
	/**
	* @description  拉回率查询分页总记录数
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.service.IDeliverGoodsAreaQueryService#queryPullbackRateListCount(com.deppon.foss.module.transfer.platform.api.shared.domain.PullbackRateEntity)
	* @author 14022-foss-songjie
	* @update 2014年3月6日 上午10:10:37
	* @version V1.0
	*/
	@Override
	public long queryPullbackRateListCount(PullbackRateEntity pullbackRateEntity) {
		return this.pullbackRateService.queryPullbackRateListCount(pullbackRateEntity);
	}
	
	/**
	* @description 导出日退单率
	* @param returnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 下午4:13:59
	*/
	@Override
	public InputStream pullbackRateDayExport(PullbackRateEntity pullbackRateEntity) {
		InputStream excelStream = null;
		try {
			 List<PullbackRateEntity> queryPullbackRateList;
			 queryPullbackRateList = this.queryPullbackRateList(pullbackRateEntity, -1, -1);
			 List<TransCenterOrgEntity> transCenterList = stockPairService.queryAllTransOrg();
			// 行List
			List<List<String>> rowList = new ArrayList<List<String>>();
			if (queryPullbackRateList != null) {
				for (PullbackRateEntity dto : queryPullbackRateList) {
					// 每行的列List
					//"外场","日期","装车票数","拉回票数","拉回率"
					List<String> columnList = new ArrayList<String>();
					//本部门
					columnList.add(queryNameOrgUp(dto.getOrgCode(),transCenterList).getBigdept());
					//事业部
					columnList.add(queryNameOrgUp(dto.getOrgCode(),transCenterList).getDivision());
					columnList.add(dto.getOrgName());
					columnList.add(DateUtils.convert(dto.getStatisticsTimeTheory(),DateUtils.DATE_FORMAT));
					columnList.add(dto.getQuantityCar() + "");
					columnList.add(dto.getQuantityPullback() + "");
					columnList.add(dto.getPullbackRate()==null?"0":dto.getPullbackRate() + "%");
					rowList.add(columnList);
				}
			}
						
			SheetData sheetData = new SheetData();
			sheetData.setRowHeads(DeliverConstants.PULLBACK_ROW_HEADS);
			sheetData.setRowList(rowList);

			ExcelExport excelExportUtil = new ExcelExport();
			excelStream = excelExportUtil.inputToClient(excelExportUtil
					.exportExcel(sheetData, DeliverConstants.PULLBACK_SHEET_NAME,
							DeliverConstants.SHEET_SIZE));
		} catch (BusinessException e) {
			LOGGER.error("导出日拉回率异常", e);
			throw new BusinessException("导出日拉回率异常", e);
		}
		return excelStream;
	}
	
	/**
	* @description 累计拉回率查询分页
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 上午10:51:13
	*/
	@Override
	public List<PullbackRateEntity> queryPullbackRateLogList(
			PullbackRateEntity pullbackRateEntity, int start, int limit) {
		return pullbackRateService.queryPullbackRateLogList(pullbackRateEntity, start, limit);
	}
	/**
	* @description 累计拉拉回率查询分页总记录数
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 上午10:51:13
	*/
	@Override
	public long queryPullbackRateLogListCount(
			PullbackRateEntity pullbackRateEntity) {
		return pullbackRateService.queryPullbackRateLogListCount(pullbackRateEntity);
	}
	/**
	* @description 导出累计拉回率
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 上午10:51:13
	*/
	@Override
	public InputStream pullbackRateLogExport(
			PullbackRateEntity pullbackRateEntity) {
		InputStream excelStream = null;
		try {
			 List<PullbackRateEntity> queryPullbackRateLogList;
			 queryPullbackRateLogList = this.queryPullbackRateLogList(pullbackRateEntity, -1, -1);
			 
			 List<TransCenterOrgEntity> transCenterList = stockPairService.queryAllTransOrg();

			// 行List
			List<List<String>> rowList = new ArrayList<List<String>>();
			if (queryPullbackRateLogList != null) {
				for (PullbackRateEntity dto : queryPullbackRateLogList) {
					// 每行的列List
					//"外场","日期","装车票数","拉回票数","日拉回率","月累计拉回率"
					List<String> columnList = new ArrayList<String>();
					//本部门
					columnList.add(queryNameOrgUp(dto.getOrgCode(),transCenterList).getBigdept());
					//事业部
					columnList.add(queryNameOrgUp(dto.getOrgCode(),transCenterList).getDivision());
					columnList.add(dto.getOrgName());
					columnList.add(DateUtils.convert(dto.getStatisticsTimeTheory(), DateUtils.DATE_FORMAT));
					columnList.add(dto.getQuantityCar()+ "");
					columnList.add(dto.getQuantityPullback() + "");
					columnList.add(dto.getPullbackRate()==null?"":dto.getPullbackRate()+ "%");
					columnList.add(dto.getPullbackRateAll()==null?"":dto.getPullbackRateAll() + "%");
					rowList.add(columnList);
				}
			}
						
			SheetData sheetData = new SheetData();
			sheetData.setRowHeads(DeliverConstants.PULLBACK_LOG_ROW_HEADS);
			sheetData.setRowList(rowList);

			ExcelExport excelExportUtil = new ExcelExport();
			excelStream = excelExportUtil.inputToClient(excelExportUtil
					.exportExcel(sheetData, DeliverConstants.PULLBACK_LOG_SHEET_NAME,
							DeliverConstants.SHEET_SIZE));
		} catch (BusinessException e) {
			LOGGER.error("导出累计拉回率异常", e);
			throw new BusinessException("导出累计拉回率异常", e);
		}
		return excelStream;
	}

	/**
	* @description  累计派送率查询
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.service.IDeliverGoodsAreaQueryService#querySendRateLogList(com.deppon.foss.module.transfer.platform.api.shared.domain.SendRateEntity, int, int)
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午4:34:21
	* @version V1.0
	*/
	@Override
	public List<SendRateEntity> querySendRateLogList(
			SendRateEntity sendRateEntity, int start, int limit) {
		return sendRateService.querySendRateLogList(sendRateEntity, start, limit);
	}

	
	/**
	* @description 累计派送率查询的总和
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.service.IDeliverGoodsAreaQueryService#querySendRateLogListCount(com.deppon.foss.module.transfer.platform.api.shared.domain.SendRateEntity)
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午4:34:25
	* @version V1.0
	*/
	@Override
	public long querySendRateLogListCount(SendRateEntity sendRateEntity) {
		return sendRateService.querySendRateLogListCount(sendRateEntity);
	}

	
	/**
	* @description  累计派送率导出
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.service.IDeliverGoodsAreaQueryService#sendRateLogExport(com.deppon.foss.module.transfer.platform.api.shared.domain.SendRateEntity)
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午4:34:28
	* @version V1.0
	*/
	@Override
	public InputStream sendRateLogExport(SendRateEntity sendRateEntity) {
		InputStream excelStream = null;
		try {
			 List<SendRateEntity> querySendRateLogList;
			 querySendRateLogList = this.querySendRateLogList(sendRateEntity, -1, -1);
			 
			 List<TransCenterOrgEntity> transCenterList = stockPairService.queryAllTransOrg();

			// 行List
			List<List<String>> rowList = new ArrayList<List<String>>();
			if (querySendRateLogList != null) {
				for (SendRateEntity dto : querySendRateLogList) {
					// 每行的列List
					//"本部","事业部","外场","日期","已派送量票数","已派送量重量","已派送量体积","前一日剩余派送量票数","前一日剩余派送量重量","前一日剩余派送量体积","当日入库货量票数","当日入库货量重量","当日入库货量体积","日派送率(F)","月累计派送率(F)"
					List<String> columnList = new ArrayList<String>();
					//本部门
					columnList.add(queryNameOrgUp(dto.getOrgCode(),transCenterList).getBigdept());
					//事业部
					columnList.add(queryNameOrgUp(dto.getOrgCode(),transCenterList).getDivision());
					columnList.add(dto.getOrgName());
					columnList.add(DateUtils.convert(dto.getStatisticsTimeTheory(), DateUtils.DATE_FORMAT));
					columnList.add(dto.getYesterdayStockWaybill() + "");
					columnList.add(dto.getYesterdayStockWeight() + "");
					columnList.add(dto.getYesterdayStockVolume()+ "");
					columnList.add(dto.getDayStockWaybill() + "");
					columnList.add(dto.getDayStockWeight() + "");
					columnList.add(dto.getDayStockVolume()+ "");
					columnList.add(dto.getDaySendWaybill() + "");
					columnList.add(dto.getDaySendWeight() + "");
					columnList.add(dto.getDaySendVolume()+ "");
					columnList.add(dto.getSendRate()==null?"0":dto.getSendRate()+ "%");
					columnList.add(dto.getSendRateAll()==null?"0":dto.getSendRate()+ "%");
					
					rowList.add(columnList);
				}
			}
						
			SheetData sheetData = new SheetData();
			sheetData.setRowHeads(DeliverConstants.SEND_LOG_ROW_HEADS);
			sheetData.setRowList(rowList);

			ExcelExport excelExportUtil = new ExcelExport();
			excelStream = excelExportUtil.inputToClient(excelExportUtil
					.exportExcel(sheetData, DeliverConstants.SEND_LOG_SHEET_NAME,
							DeliverConstants.SHEET_SIZE));
		} catch (BusinessException e) {
			LOGGER.error("导出累计派送率异常", e);
			throw new BusinessException("导出累计派送率异常", e);
		}
		return excelStream;
	}
	
	/**
	* @description 累计退单率查询分页
	* @param ReturnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午10:13:06
	*/
	@Override
	public List<ReturnRateEntity> queryReturnRateLogList(
			ReturnRateEntity returnRateEntity, int start, int limit) {
		return returnRateService.queryReturnRateLogList(returnRateEntity, start, limit);
	}
	/**
	* @description 累计退单率查询分页总记录数
	* @param ReturnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午10:13:06
	*/
	@Override
	public long queryReturnRateLogListCount(ReturnRateEntity returnRateEntity) {
		return returnRateService.queryReturnRateLogListCount(returnRateEntity);
	}
	/**
	* @description 累计退单率导出
	* @param returnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午10:13:06
	*/
	@Override
	public InputStream returnRateLogExport(ReturnRateEntity returnRateEntity) {
		InputStream excelStream = null;
		try {
			 List<ReturnRateEntity> queryReturnRateLogList;
			 queryReturnRateLogList = this.queryReturnRateLogList(returnRateEntity, -1, -1);
			 
			 List<TransCenterOrgEntity> transCenterList = stockPairService.queryAllTransOrg();

			// 行List
			List<List<String>> rowList = new ArrayList<List<String>>();
			if (queryReturnRateLogList != null) {
				for (ReturnRateEntity dto : queryReturnRateLogList) {
					// 每行的列List
					//"本部","事业部","外场","日期","排单票数","装车票数","退单票数","日退单率","月累计退单率"
					List<String> columnList = new ArrayList<String>();
					//本部门
					columnList.add(queryNameOrgUp(dto.getOrgCode(),transCenterList).getBigdept());
					//事业部
					columnList.add(queryNameOrgUp(dto.getOrgCode(),transCenterList).getDivision());
					columnList.add(dto.getOrgName());
					columnList.add(DateUtils.convert(dto.getStatisticsTimeTheory(), DateUtils.DATE_FORMAT));
					columnList.add(dto.getForecastWaybill() + "");
					columnList.add(dto.getQuantityCarReality() + "");
					columnList.add(dto.getQuantityReturn()+ "");
					columnList.add(dto.getReturnRate()==null?"0":dto.getReturnRate() + "%");
					columnList.add(dto.getReturnRateAll()==null?"0":dto.getReturnRateAll() + "%");
					
					rowList.add(columnList);
				}
			}
						
			SheetData sheetData = new SheetData();
			sheetData.setRowHeads(DeliverConstants.RETURN_LOG_ROW_HEADS);
			sheetData.setRowList(rowList);

			ExcelExport excelExportUtil = new ExcelExport();
			excelStream = excelExportUtil.inputToClient(excelExportUtil
					.exportExcel(sheetData, DeliverConstants.RETURN_LOG_SHEET_NAME,
							DeliverConstants.SHEET_SIZE));
		} catch (BusinessException e) {
			LOGGER.error("导出累计退单率异常", e);
			throw new BusinessException("导出累计退单率异常", e);
		}
		return excelStream;
	}

	/**
	* @description  获取上级的名称
	* @param orgCode
	* @param upLevel  事业部 BizTypeConstants.ORG_DIVISION    BizTypeConstants.ORG_BIG_REGION
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月13日 上午9:14:44
	*/
	public TransCenterOrgEntity queryNameOrgUp(String orgCode,List<TransCenterOrgEntity> transCenterList){
		/**
		List<String> bizTypesList = new ArrayList<String>();
		//事业部 BizTypeConstants.ORG_DIVISION
		//大区     BizTypeConstants.ORG_BIG_REGION
		bizTypesList.add(upLevel);
		//查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			return orgAdministrativeInfoEntity.getName();
		}else{
			return "";
		}
		*/
		TransCenterOrgEntity backPojo = new TransCenterOrgEntity();
		if(transCenterList!=null){
			for (TransCenterOrgEntity transCenterOrgEntity : transCenterList) {
				if(StringUtils.endsWith(transCenterOrgEntity.getOrgCode(), orgCode)){
					backPojo = transCenterOrgEntity;
					break;
				}
			}
			return backPojo;
		}else{
			return backPojo;
		}
	}
	
	public List<UserOrgRoleEntity> queryOrgRoleByCode(String userCode){
		UserOrgRoleEntity userOrgRoleEntity=new UserOrgRoleEntity();
		userOrgRoleEntity.setEmpCode(userCode);
		List<UserOrgRoleEntity> resultList;
		try {
			resultList=userOrgRoleService.queryUserOrgRoleListBySelective(userOrgRoleEntity);
		} catch (Exception e) {
			throw new BusinessException("调用综合接口查询角色信息异常", e);
		}
		return 	resultList;
	}

	/**
	* @description 查询票均时长
	* @param waybillAvgTimeEntity
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 218427-foss-hongwy
	* @update 2015年3月25日 上午09:53:29
	*/
	public List<WaybillAvgTimeEntity> queryWaybillAvgTimeEntityList(DeliverGoodsAreaQueryVo deliverGoodsAreaQueryVo, int start, int limit){
	     
		
		return  queryWaybillAvgTimeDao.queryWaybillAvgTimeEntityList(deliverGoodsAreaQueryVo, start, limit);
	}
	
	/**
	* @description 查询票均时长分页记录
	* @param waybillAvgTimeEntity
	* @return
	* @version 1.0
	* @author 218427-foss-hongwy
	* @update 2015年3月25日 上午09:53:29
	*/
	public long queryWaybillAvgTimeEntityListCount(DeliverGoodsAreaQueryVo deliverGoodsAreaQueryVo){
    	return queryWaybillAvgTimeDao.queryWaybillAvgTimeEntityListCount(deliverGoodsAreaQueryVo);
    }

	
}
