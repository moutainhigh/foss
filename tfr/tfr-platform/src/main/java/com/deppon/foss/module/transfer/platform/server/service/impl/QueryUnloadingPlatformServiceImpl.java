package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadEfficiencyTonService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyTonEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.platform.api.server.dao.IQueryUnloadingPlatformDao;
import com.deppon.foss.module.transfer.platform.api.server.dao.IUnloadGoodsDetailNoDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IQueryUnloadingPlatformService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.define.UnloadPlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.NoUnloadGoodsDetail;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LoadAndUnloadStandardDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QueryBillInfoResultPlatformDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QueryUnloadingPlatformConditionDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QueryUnloadingPlatformResultDto;
import com.deppon.foss.module.transfer.platform.api.shared.exception.QueryUnloadProgressException;
import com.deppon.foss.util.define.FossConstants;

public class QueryUnloadingPlatformServiceImpl implements
		IQueryUnloadingPlatformService {
	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/**
	 * dao接口
	 * 
	 * 
	 */
	private IQueryUnloadingPlatformDao queryUnloadingPlatformDao;
	
	
	/**
	 * 卸车进度里的未卸车明细
	* @fields unloadGoodsDetailNoDao
	* @author 14022-foss-songjie
	* @update 2014年4月16日 下午5:07:45
	* @version V1.0
	*/
	private IUnloadGoodsDetailNoDao unloadGoodsDetailNoDao;
	/**
	 *  获取指定部门接口
	 *  
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 
	 * 装卸车标准
	 * 
	 */
	private ILoadAndUnloadEfficiencyTonService loadAndUnloadEfficiencyTonService;
	/**
	 * 
	 * 查询卸车进度
	 * 
	 * 
	 * @param queryUnloadingProgressConditionDto
	 * 
	 *            查询卸车进度条件
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-12 下午3:16:30
	 */
	@Override
	public List<QueryUnloadingPlatformResultDto> queryUnloadingProgressInfo(
			QueryUnloadingPlatformConditionDto queryUnloadingProgressConditionDto,int limit,int start) {
		/**
		 * 先对查询条件进行处理
		 */
		QueryUnloadingPlatformConditionDto afterHandle = handleCondition(queryUnloadingProgressConditionDto);
		// 获取查询返回结果
		List<QueryUnloadingPlatformResultDto> resultList = queryUnloadingPlatformDao
				.queryUnloadingProgressInfo(afterHandle,limit,start);

		/**
		 * 
		 * 遍历查询结果，通过任务号查询单据编号以及在分配卸车任务表中的数据
		 */
		for (int i = 0; i < resultList.size(); i++) {
			QueryUnloadingPlatformResultDto resultDto = resultList.get(i);
			// 卸车任务id
			String id = resultDto.getId();
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			List<QueryBillInfoResultPlatformDto> billList = queryUnloadingPlatformDao
					.queryBillInfo(map);
			// 单据编号，多个单据编号用逗号隔开
			StringBuffer sBuffer = new StringBuffer();
			//出发部门，多个部门用逗号隔开
			StringBuffer deptBuffer = new StringBuffer();
			//获取预计完成时间
			long currentTime = new Date().getTime();	
			long planTime;
			if(resultDto.getPredictCompleteMinutes()==null){
				planTime = 0;
			}else{
				planTime = resultDto.getPredictCompleteMinutes().multiply(new BigDecimal(PlatformConstants.SONAR_NUMBER_60*PlatformConstants.SONAR_NUMBER_1000)).longValue();
			}
			Date predictCompleteTime = new Date(currentTime + planTime);
			resultList.get(i).setPredictCompleteTime(predictCompleteTime);
			int length = billList.size();
			for (int j = 0; j < length; j++) {
				QueryBillInfoResultPlatformDto billDto = billList.get(j);
				sBuffer.append(billDto.getBillNumber());
				if (length > 1 && j != length - 1) {
					sBuffer.append(UnloadPlatformConstants.QUERY_UNLOAD_PROGRESS_COMMA);
				}
				
				deptBuffer.append(billDto.getLeaveDept());
				if(length > 1 && j != length - 1){
					deptBuffer.append(UnloadPlatformConstants.QUERY_UNLOAD_PROGRESS_COMMA);
				}

			}
			/**
			 * 为卸车进度查询结果设置总体积、总重量、总件数、到达时间和分配时间
			 */	
			//单据编号
			resultList.get(i).setBillNumber(sBuffer.toString());
			//出发部门
			resultList.get(i).setLeaveDept(deptBuffer.toString());
			
		}
		/**
		 * 增加对进度的处理
		 */
		return handleUnloadingProgressResult(resultList);
	}
	/**
	 * 
	 * 查询卸车进度
	 * 
	 * 
	 * @param queryUnloadingProgressConditionDto
	 * 
	 *            查询卸车进度条件
	 * @author 134019-yuyongxiang
	 * @date 2013年7月15日 19:13:04
	 */
	@Override
	public Long queryUnloadingProgressInfoCount(
			QueryUnloadingPlatformConditionDto queryUnloadingProgressConditionDto) {
		/**
		 * 先对查询条件进行处理
		 */
		QueryUnloadingPlatformConditionDto afterHandle = handleCondition(queryUnloadingProgressConditionDto);
		
		return  queryUnloadingPlatformDao.queryUnloadingProgressInfoCount(afterHandle);
	}
	/**
	 * 
	 * 处理查询结果
	 * 
	 * 
	 * 
	 * @param queryLoadingProgressResultList
	 *            查询结果集
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-22 下午3:13:03
	 */
	private List<QueryUnloadingPlatformResultDto> handleUnloadingProgressResult(
			List<QueryUnloadingPlatformResultDto> queryUnloadingProgressResultList) {
		// 设置百分比格式    UnloadConstants.QUERY_UNLOAD_PROGRESS_PERCENT ##%
		DecimalFormat df = new DecimalFormat(UnloadPlatformConstants.QUERY_UNLOAD_PROGRESS_PERCENT);
		for (int i = 0; i < queryUnloadingProgressResultList.size(); i++) {
			QueryUnloadingPlatformResultDto qprDto = queryUnloadingProgressResultList
					.get(i);
			// 已卸体积
			BigDecimal unloadedVolume = qprDto.getUnloadedVolume();
			// 已卸重量
			BigDecimal unloadedWeight = qprDto.getUnloadedWeight();
			// 总体积
			BigDecimal totalVolume = qprDto.getTotalVolume();
			// 总重量
			BigDecimal totalWeight = qprDto.getTotalWeight();
			if(unloadedVolume==null){
				unloadedVolume = BigDecimal.ZERO;
				qprDto.setLeftVolume(qprDto.getTotalVolume());
			}
			if(unloadedWeight==null){
				unloadedWeight = BigDecimal.ZERO;
				qprDto.setLeftWeight(qprDto.getTotalWeight());
			}
			if(qprDto.getUnloadedPieces()==0){
				qprDto.setLeftPieces(qprDto.getTotalPieces());
			}
			// 设置体积进度百分比
			if (unloadedVolume != null 
					&& totalVolume != null 
					&& totalVolume.floatValue() != 0) {
				double volume = unloadedVolume.divide(totalVolume, 2,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				//获取百分比表示
				String volumeProgress = df.format(volume);
				queryUnloadingProgressResultList.get(i).setVolumeProgress(volumeProgress);
			}else{
				//暂无操作
			}
			// 设置重量进度百分比
			if (unloadedWeight != null 
					&& totalWeight != null
					&& totalWeight.floatValue() != 0) {
				double weight = unloadedWeight.divide(totalWeight, 2,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				//获取百分比表示
				String weightPorgress = df.format(weight);
				queryUnloadingProgressResultList.get(i).setWeightProgress(weightPorgress);
			}else{
				//暂无操作
			}
			//体积进度字符表示
			String unloadingVolumeProgress = "";
			if (totalVolume != null) {
				unloadingVolumeProgress = unloadedVolume==null?"":unloadedVolume.toString() + 
						UnloadPlatformConstants.QUERY_UNLOAD_PROGRESS_SLASH + 
						totalVolume.toString();
			}
			//重量进度字符表示
			String unloadingWeightProgress = unloadedWeight==null?"":unloadedWeight.toString() + 
					UnloadPlatformConstants.QUERY_UNLOAD_PROGRESS_SLASH + 
					totalWeight== null ? "" : totalWeight.toString();
			//设置体积进度字符表示
			queryUnloadingProgressResultList.get(i).setUnLoadingVolumnProgress(unloadingVolumeProgress);
			//设置重量进度字符表示
			queryUnloadingProgressResultList.get(i).setUnLoadingWeightProgress(unloadingWeightProgress);
		}
		//返回结果
		return queryUnloadingProgressResultList;
	}
	/**
	 * 
	 * 处理卸车查询条件
	 * 
	 * 
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-12 下午4:09:09
	 */
	private QueryUnloadingPlatformConditionDto handleCondition(
			QueryUnloadingPlatformConditionDto condition) {
		
		if(condition == null){
			throw new QueryUnloadProgressException("查询条件为空，请重新选择查询条件后查询！");
		}
		
		condition.setCurrentDate(new Date());
		String deptCode = obtainCurrentDeptCode();
		//将当前部门作为查询条件
		condition.setDeptCode(deptCode);
		
		//排序类型  从大到小 从小到大
		String sequence = condition.getSequence();
		//排序方式  任务创建时间、重量、体积等
		String sequenceType = condition.getSequenceType();
		//拼接查询条件
		String sortRule = sequenceType + UnloadPlatformConstants.QUERY_UNLOAD_PROGRESS_UNDERLINE + sequence;
		condition.setSortRule(sortRule);
		//创建任务理货员，默认显示的理货员为创建任务的理货员
		condition.setIsCreator(FossConstants.YES);
		return condition;
	}
	/**
	 * 
	 * 查询当前部门装卸车标准
	 * 
	 * 
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-18 下午9:16:13
	 */
	@Override
	public LoadAndUnloadStandardDto queryLoadAndUnloadStd() {
		LoadAndUnloadStandardDto standarDto = new LoadAndUnloadStandardDto();
		//获取当前部门
		String deptCode = obtainCurrentDeptCode();
		
		LoadAndUnloadEfficiencyTonEntity  effiEntity = loadAndUnloadEfficiencyTonService.queryLoadAndUnloadEfficiencyTonByOrgCode(deptCode);
		if(effiEntity==null){
			throw new QueryUnloadProgressException("未找到本部门的卸车标准！");
		}
		//设置 卸车体积标准.
		standarDto.setUnloadVolumeStd(effiEntity.getUnloadVolumeStd());
		//设置 卸车重量标准.
		standarDto.setUnloadWeightStd(effiEntity.getUnloadWeightStd());
		return standarDto;
	}
	/**
	 * 
	 * 获取指定部门
	 * 
	 * 
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-18 下午2:16:59
	 */
	private String obtainCurrentDeptCode() {
		// 当前操作部门
		List<String> bizTypes = new ArrayList<String>();
		// 设置外场类型
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity activeDeptInfo = FossUserContext.getCurrentDept();
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(activeDeptInfo.getCode(),bizTypes);
		if (org != null) {
			return org.getCode();
		} else {
			throw new QueryUnloadProgressException("当前登录人直属部门无对应外场部门");
		}
	}
	
	
	
	/**
	* @description 未卸车明细 卡货
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月18日 下午4:45:13
	*/
	@Override
	public List<NoUnloadGoodsDetail> queryNoUnloadGoodsDetailListKH(
			String taskId, int start, int limit) {
		return queryNoUnloadGoodsDetailComm(taskId,UnloadPlatformConstants.NO_UNLOAD_GOODS_DETAIL_KH,start,limit);
	}
	
	
	/**
	* @description 未卸车明细 卡货 总记录
	* @param taskId
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月18日 下午5:35:55
	*/
	@Override
	public long queryNoUnloadGoodsDetailListKHCount(String taskId) {
		return queryNoUnloadGoodsDetailCountComm(taskId,UnloadPlatformConstants.NO_UNLOAD_GOODS_DETAIL_KH);
	}
	
	
	
	
	/**
	* @description  未卸车明细 卡货导出
	* @param taskId
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月19日 上午10:19:22
	*/
	@Override
	public InputStream exportNoUnloadGoodsDetailKH(String taskId) {
		InputStream excelStream = null;
		try{
			List<NoUnloadGoodsDetail> dbList = queryNoUnloadGoodsDetailListKH(taskId,-1,-1);
			// 行List
			List<List<String>> rowList = new ArrayList<List<String>>();
			if(dbList!=null && dbList.size()>0){
				for (NoUnloadGoodsDetail noUnloadGoodsEntity : dbList) {
					List<String> columnList = new ArrayList<String>();
					//"运单号","开单件数","交接件数","未卸件数","开单重量(公斤)","开单体积(方)","下一目的站"
					columnList.add(noUnloadGoodsEntity.getWaybillNo());
					columnList.add(noUnloadGoodsEntity.getGoodsQtyTotal()+"");
					columnList.add(noUnloadGoodsEntity.getHandoverGoodsQty()+"");
					columnList.add(noUnloadGoodsEntity.getNounloadGoodsQty()+"");
					columnList.add(noUnloadGoodsEntity.getGoodsWeightTotal()+"");
					columnList.add(noUnloadGoodsEntity.getGoodsVolumeTotal()+"");
					columnList.add(noUnloadGoodsEntity.getNextOrgname());
					rowList.add(columnList);
				}
			}
			SheetData sheetData = new SheetData();
			sheetData.setRowHeads(UnloadPlatformConstants.NO_UNLOAD_GOODS_DETAIL_CJ_ROW_HEADS);
			sheetData.setRowList(rowList);
			
			ExcelExport excelExportUtil = new ExcelExport();
			excelStream = excelExportUtil.inputToClient(excelExportUtil
					.exportExcel(sheetData, UnloadPlatformConstants.NO_UNLOAD_GOODS_DETAIL_CJ_SHEET_NAME,
							UnloadPlatformConstants.SHEET_SIZE));
			
		}catch(Exception e){
			LOGGER.error("未卸车明细 城际 导出异常", e);
			throw new BusinessException("未卸车明细 城际 导出异常", e);
		}
		return excelStream;
	}
	/**
	* @description 未卸车明细 城际
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月18日 下午4:45:13
	*/
	@Override
	public List<NoUnloadGoodsDetail> queryNoUnloadGoodsDetailListCJ(
			String taskId, int start, int limit) {
		return queryNoUnloadGoodsDetailComm(taskId,UnloadPlatformConstants.NO_UNLOAD_GOODS_DETAIL_CJ,start,limit);
	}
	
	
	
	/**
	* @description 未卸车明细 城际 总记录
	* @param taskId
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月18日 下午5:35:53
	*/
	@Override
	public long queryNoUnloadGoodsDetailListCJCount(String taskId) {
		return queryNoUnloadGoodsDetailCountComm(taskId,UnloadPlatformConstants.NO_UNLOAD_GOODS_DETAIL_CJ);
	}
	
	
	
	/**
	* @description  未卸车明细 城际 导出
	* @param taskId
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月19日 上午10:19:34
	*/
	@Override
	public InputStream exportNoUnloadGoodsDetailCJ(String taskId) {
		InputStream excelStream = null;
		try{
			List<NoUnloadGoodsDetail> dbList = queryNoUnloadGoodsDetailListCJ(taskId,-1,-1);
			// 行List
			List<List<String>> rowList = new ArrayList<List<String>>();
			if(dbList!=null && dbList.size()>0){
				for (NoUnloadGoodsDetail noUnloadGoodsEntity : dbList) {
					List<String> columnList = new ArrayList<String>();
					//"运单号","开单件数","交接件数","未卸件数","开单重量(公斤)","开单体积(方)","下一目的站"
					columnList.add(noUnloadGoodsEntity.getWaybillNo());
					columnList.add(noUnloadGoodsEntity.getGoodsQtyTotal()+"");
					columnList.add(noUnloadGoodsEntity.getHandoverGoodsQty()+"");
					columnList.add(noUnloadGoodsEntity.getNounloadGoodsQty()+"");
					columnList.add(noUnloadGoodsEntity.getGoodsWeightTotal()+"");
					columnList.add(noUnloadGoodsEntity.getGoodsVolumeTotal()+"");
					columnList.add(noUnloadGoodsEntity.getNextOrgname());
					rowList.add(columnList);
				}
			}
			SheetData sheetData = new SheetData();
			sheetData.setRowHeads(UnloadPlatformConstants.NO_UNLOAD_GOODS_DETAIL_CJ_ROW_HEADS);
			sheetData.setRowList(rowList);
			
			ExcelExport excelExportUtil = new ExcelExport();
			excelStream = excelExportUtil.inputToClient(excelExportUtil
					.exportExcel(sheetData, UnloadPlatformConstants.NO_UNLOAD_GOODS_DETAIL_CJ_SHEET_NAME,
							UnloadPlatformConstants.SHEET_SIZE));
			
		}catch(Exception e){
			LOGGER.error("未卸车明细 城际 导出异常", e);
			throw new BusinessException("未卸车明细 城际 导出异常", e);
		}
		return excelStream;
	}
	/**
	* @description 未卸车明细 公共方法
	* @param taskId
	* @param productCode
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月18日 下午5:24:59
	*/
	private List<NoUnloadGoodsDetail> queryNoUnloadGoodsDetailComm(String taskId,String productCode, int start, int limit){
		Map<String,String> map = new HashMap<String, String>();
		map.put("taskId", taskId);
		map.put("productCode",productCode);
		List<NoUnloadGoodsDetail> dbList = unloadGoodsDetailNoDao.queryNoUnloadGoodsDetailList(map,start,limit);
		
		
		if(dbList!=null && dbList.size()>0){
			//计算总重量和总体积
			NoUnloadGoodsDetail backSumPojo = queryNoUnloadGoodsDetailListSum(taskId,productCode);
			String unloadOrgCode = dbList.get(0).getUnloadOrgCode();
			List<String> waybillNo = new ArrayList<String>();
			for (NoUnloadGoodsDetail noUnloadGoodsDetail : dbList) {
				waybillNo.add(noUnloadGoodsDetail.getWaybillNo());
			}
			List<NoUnloadGoodsDetail> dbNextOrgNameList = unloadGoodsDetailNoDao.queryNextNameByOrgCode(unloadOrgCode, waybillNo);
			for (NoUnloadGoodsDetail detailPojo : dbList) {
				String detailWaybill = detailPojo.getWaybillNo();
				for (NoUnloadGoodsDetail nextNamePojo : dbNextOrgNameList) {
					String nextNameWaybill = nextNamePojo.getWaybillNo();
					if(StringUtils.equals(detailWaybill, nextNameWaybill)){
						detailPojo.setNextOrgname(nextNamePojo.getNextOrgname());
						break;
					}
					
				}
				//如果数值有null的赋值为0
				BigDecimal tempZero = new BigDecimal(0);
				if(detailPojo.getGoodsQtyTotal()==null){
					detailPojo.setGoodsQtyTotal(tempZero);
				}
				if(detailPojo.getGoodsVolumeTotal()==null){
					detailPojo.setGoodsVolumeTotal(tempZero);
				}else{
					//4舍5入
					detailPojo.setGoodsVolumeTotal(detailPojo.getGoodsVolumeTotal().setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				if(detailPojo.getGoodsWeightTotal()==null){
					detailPojo.setGoodsWeightTotal(tempZero);
				}else{
					//4舍5入
					detailPojo.setGoodsWeightTotal(detailPojo.getGoodsWeightTotal().setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				if(detailPojo.getHandoverGoodsQty()==null){
					detailPojo.setHandoverGoodsQty(tempZero);
				}
				if(detailPojo.getNounloadGoodsQty()==null){
					detailPojo.setNounloadGoodsQty(tempZero);
				}
				//总重量和总体积
				detailPojo.setGoodsVolumeSum(backSumPojo.getGoodsVolumeTotal());
				detailPojo.setGoodsWeightSum(backSumPojo.getGoodsWeightTotal());
			}
		}else{
			return null;
		}
		return dbList;
	} 
	

	/**
	* @description 未卸车明细 的总重量和总体积
	* @param taskId
	* @param type
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月19日 上午10:49:32
	*/
	@Override
	public NoUnloadGoodsDetail queryNoUnloadGoodsDetailListSum(String taskId,
			String type) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("taskId", taskId);
		map.put("productCode",type);
		NoUnloadGoodsDetail backPojo = unloadGoodsDetailNoDao.queryNoUnloadGoodsDetailListSum(map); 
		//重量转换成吨
		if(backPojo!=null){
		if(backPojo.getGoodsWeightTotal()!=null){
			backPojo.setGoodsWeightTotal(kiloToTon(backPojo.getGoodsWeightTotal()));
		}else{
			backPojo.setGoodsWeightTotal(new BigDecimal(0));
		}
		
		if(backPojo.getGoodsVolumeTotal()==null){
			backPojo.setGoodsVolumeTotal(new BigDecimal(0));
		}}else{
			backPojo = new NoUnloadGoodsDetail();
			backPojo.setGoodsWeightTotal(new BigDecimal(0));
			backPojo.setGoodsVolumeTotal(new BigDecimal(0));
		}
		return backPojo;
	}
	
	/**
	* @description 千克转吨
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.service.IStockSaturationService#kiloToTon(java.math.BigDecimal)
	* @author 14022-foss-songjie
	* @update 2014年4月10日 下午4:03:20
	* @version V1.0
	*/
	private BigDecimal kiloToTon(BigDecimal kilo) {
		BigDecimal back = new BigDecimal(0);
		if(kilo!=null){
			BigDecimal b = new BigDecimal(PlatformConstants.SONAR_NUMBER_1000);
			try{
				back = kilo.divide(b, 2, BigDecimal.ROUND_HALF_UP);
			}catch(Exception e){
				return back;
			}
		}
		return back;
	}
	
	/**
	* @description  未卸车明细 总记录 公共方法
	* @param taskId
	* @param productCode
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月18日 下午5:38:46
	*/
	private long queryNoUnloadGoodsDetailCountComm(String taskId,String productCode){
		Map<String,String> map = new HashMap<String, String>();
		map.put("taskId", taskId);
		map.put("productCode",productCode);
		return unloadGoodsDetailNoDao.queryNoUnloadGoodsDetailListCount(map);
	}
	
	/**
	 * 设置 dao接口.
	 * 
	 *
	 * @param queryUnloadingProgressDao the new dao接口
	 */
	public void setQueryUnloadingPlatformDao(
			IQueryUnloadingPlatformDao queryUnloadingPlatformDao) {
		this.queryUnloadingPlatformDao = queryUnloadingPlatformDao;
	}
	
	/**
	 * 设置 获取指定部门接口.
	 *
	 * @param orgAdministrativeInfoComplexService the new 获取指定部门接口
	 */
	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	/**
	 * 设置 装卸车标准.
	 *
	 * @param loadAndUnloadEfficiencyTonService the new 装卸车标准
	 */
	public void setLoadAndUnloadEfficiencyTonService(ILoadAndUnloadEfficiencyTonService loadAndUnloadEfficiencyTonService) {
		this.loadAndUnloadEfficiencyTonService = loadAndUnloadEfficiencyTonService;
	}
	
	/**
	* @description 卸车进度里的未卸车明细
	* @param unloadGoodsDetailNoDao
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月16日 下午5:08:10
	*/
	public void setUnloadGoodsDetailNoDao(
			IUnloadGoodsDetailNoDao unloadGoodsDetailNoDao) {
		this.unloadGoodsDetailNoDao = unloadGoodsDetailNoDao;
	}
	
	
}
