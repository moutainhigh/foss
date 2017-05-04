/*
 * PROJECT NAME: tfr-unload
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.server.service.impl
 * FILE    NAME: SortingScanService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDASortingDao;
import com.deppon.foss.module.transfer.unload.api.server.service.ISortingScanService;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SortingScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.SortingScanCompareDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.SortingScanDto;
import com.deppon.foss.module.transfer.unload.api.shared.vo.SortingScanVo;

/**
 * SortingScanService
 * @author dp-duyi
 * @date 2013-7-31 上午11:18:53
 */
public class SortingScanService implements ISortingScanService{
	private IPDASortingDao pdaSortingDao;
	private IPDACommonService pdaCommonService;
	
	private Long totalCount;
	
	public void setPdaSortingDao(IPDASortingDao pdaSortingDao) {
		this.pdaSortingDao = pdaSortingDao;
	}
	public void setPdaCommonService(IPDACommonService pdaCommonService) {
		this.pdaCommonService = pdaCommonService;
	}

	@Override
	public List<SortingScanEntity> selectSortingScan(SortingScanVo vo, int limit, int start){
		OrgAdministrativeInfoEntity org = pdaCommonService.getTopCurrentOutfieldOrSalesDept(FossUserContext.getCurrentDeptCode());
		if(org != null){
			vo.setOrgCode(org.getCode());
		}else{
			throw new TfrBusinessException("无效部门");
		}
		return pdaSortingDao.selectSortingScan(vo, limit, start);
	}
	
	/**
	 * 获取分拣扫描 记录数
	 * @author 332209 ruilibao
	 * 2017-03-23
	 * 
	 */
	@Override
	public Long sortingScanCount(SortingScanVo vo){
		OrgAdministrativeInfoEntity org = pdaCommonService.getTopCurrentOutfieldOrSalesDept(FossUserContext.getCurrentDeptCode());
		if(org != null){
			vo.setOrgCode(org.getCode());
		}else{
			throw new TfrBusinessException("无效部门");
		}
		return pdaSortingDao.querySortingScanCount(vo);
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List exportSortingScanToExcel(SortingScanVo vo, int limit, int start){
		OrgAdministrativeInfoEntity org = pdaCommonService.getTopCurrentOutfieldOrSalesDept(FossUserContext.getCurrentDeptCode());
		if(org != null){
			vo.setOrgCode(org.getCode());
		}else{
			throw new TfrBusinessException("无效部门");
		}
		List<SortingScanEntity> sortingScans = pdaSortingDao.selectSortingScan(vo);
		List<List<String>> rowList = new ArrayList<List<String>>();
		if(CollectionUtils.isEmpty(sortingScans)){
			List<String> columnList = new ArrayList<String>();
			rowList.add(columnList);
		}
		for(SortingScanEntity sortingScan : sortingScans){
			List<String> columnList = new ArrayList<String>();
			//运单号
			columnList.add(sortingScan.getWayBillNo());
			//流水号
			columnList.add(sortingScan.getSerialNo());
			//扫描类型
			if(TransferPDADictConstants.SORTING_SCAN_TYPE_UP.equals(sortingScan.getScanType())){
				columnList.add("上分拣扫描");
			}else if(TransferPDADictConstants.SORTING_SCAN_TYPE_DOWN.equals(sortingScan.getScanType())){
				columnList.add("下分拣扫描");
			}
			
			//操作人
			columnList.add(sortingScan.getOperatorName());
			//操作部门
			columnList.add(sortingScan.getOrgName());
			//操作时间
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			columnList.add(df.format(sortingScan.getScanTime()));
			//扫描方式
			if(StringUtils.equals(UnloadConstants.SORT_SCAN_MODE_BSC,sortingScan.getScanMode())){
				columnList.add("巴枪扫描");
			}else{
				columnList.add("PDA扫描");
			}
			rowList.add(columnList);
		}
		//定义表头
		String[] rowHeads = {"运单号",
										"流水号",
										"扫描类型",
										"操作人",
										"操作部门",
										"操作时间",
										"扫描方式"
										};
		//导出资源类
		ExportResource exportResource = new ExportResource();
		//设置导出文件的表头
	    exportResource.setHeads(rowHeads);
	    //设置导出数据
	    exportResource.setRowList(rowList);
	    //导出设置
	    ExportSetting exportSetting = new ExportSetting();
	    //设置sheetname
	    exportSetting.setSheetName("运单列表");
	    //设置sheet行数
	    if(CollectionUtils.isEmpty(sortingScans)){
	    	exportSetting.setSize(1);
	    }else{
	    	exportSetting.setSize(sortingScans.size() + 1);
	    }
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    //获取输入流
	    InputStream excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);

        //文件名
        String fileName = null;
		try {
			fileName = this.encodeFileName("分拣扫描明细");
		} catch (Exception e) {
			//抛出业务异常
			throw new TfrBusinessException(e.getMessage());
		} //设置fileName
        List list = new ArrayList();
        list.add(fileName);
        list.add(excelStream);
		return list;
	}
	/** 
	 * @Title: encodeFileName 
	 * @Description: 将文件名转成UTF-8编码以防止乱码
	 * @param string
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService#encodeFileName(java.lang.String)
	 * @author: duyi
	 * @throws 
	 * Date:2013-10-17上午08:30:37
	 */ 
	private String encodeFileName(String fileName) {
		try {
			String returnStr;
			String agent = (String) ServletActionContext.getRequest().getHeader(
					"USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") == -1) {
				returnStr = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			} else {
				returnStr = URLEncoder.encode(fileName, "UTF-8");
			}
			return returnStr;
		} catch (UnsupportedEncodingException e) {
			throw new StockException("将文件名转成UTF-8编码时出错","");
		}
	}
	/**
	 * 查询分拣扫描与库存数据的比对结果
	 * @Author: 200978  xiaobingcheng
	 * 2014-9-2
	 * @param sortingScanDto
	 * @param start
	 * @param limit
	 * @return
	 */
	@Transactional
	public List<SortingScanCompareDto> querySortingScanCompare(SortingScanDto sortingScanDto,int start,int limit){
		OrgAdministrativeInfoEntity org = pdaCommonService.getTopCurrentOutfieldOrSalesDept(FossUserContext.getCurrentDeptCode());
		if(org != null){
			sortingScanDto.setOrgCode(org.getCode());
		}else{
			throw new TfrBusinessException("无效部门");
		}
		//比对结果
		List<SortingScanCompareDto> compareDtoList = new ArrayList<SortingScanCompareDto>();
		//扫描数据
		List<SortingScanEntity> scanList =  pdaSortingDao.querySortingScan(sortingScanDto);
		//库存数据
		List<SortingScanCompareDto> stockList = pdaSortingDao.querySortingScanStock(sortingScanDto);
		
		for (SortingScanCompareDto stock : stockList) {
			//标记，如果为true，说明有差异
			boolean compareFlag = true;
			int goodsCount = 0;//货物数量
			int goodsTotalCount = stock.getStockGoodsQty().intValue();//该运单的库存数量
			
			for (SortingScanEntity sortingScanEntity : scanList) {
				//如果运单相同
				if(StringUtils.equals(sortingScanEntity.getWayBillNo(), stock.getWaybillNo())){
					//如果货物数量大于等于库存数量,说明没有差异
					if(++goodsCount  >= goodsTotalCount){
						compareFlag = false;
						break;
					}
				}
			}
			//有差异
			if(compareFlag){
				compareDtoList.add(stock);
			}
		}
		this.setSortingScanCompareCount(Long.parseLong(String.valueOf(compareDtoList.size())));//设置比对结果总条数
		//设置最大记录条数
		int endIndex = start+limit;
		if(endIndex > compareDtoList.size()){
			endIndex = compareDtoList.size();
		}
		return compareDtoList.subList(start, endIndex);
	}
	
	/**
	 * 通过接送货传参查询出电子运单分拣记录
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @author 218427 foss-hongwy 
	 */
	public List<SortingScanEntity> queryEwayBillRecords(SortingScanDto dto,int start,int limit){
		 List<SortingScanEntity> list = pdaSortingDao.queryEwayBillRecords(dto,start,limit);
		return list;
		
	}
	/**查询出电子运单分拣记录数
	 * @author 218427 hwy
	 * @return
	 * @date 2016-2-29
	 */
	public Long queryEwayBillRecordsCount(SortingScanDto dto){
		
		return pdaSortingDao.queryEwayBillRecordsCount(dto);
	}
	/**
	 * 获取比对结果总条数
	 * @Author: 200978  xiaobingcheng
	 * 2014-9-2
	 * @return
	 */
	public Long querySortingScanCompareCount(){
		return this.totalCount;
	}
	/**
	 * 设置比对结果总条数
	 * @Author: 200978  xiaobingcheng
	 * 2014-9-2
	 * @param totalCount
	 */
	private void setSortingScanCompareCount(Long totalCount){
		this.totalCount = totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	
}
