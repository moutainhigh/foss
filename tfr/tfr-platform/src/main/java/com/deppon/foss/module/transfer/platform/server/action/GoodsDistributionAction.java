package com.deppon.foss.module.transfer.platform.server.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.platform.api.server.service.IGoodsDistributionService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.GoodsDistributionEntity;
import com.deppon.foss.module.transfer.platform.api.shared.vo.GoodsDistributionVo;
import com.deppon.foss.util.CollectionUtils;

/**
 * 货量流动分布
 * @author 200978
 * 2015-3-10
 */
public class GoodsDistributionAction extends AbstractAction {

	private static final Logger LOG = LoggerFactory.getLogger(GoodsDistributionAction.class);
	
	/**
	 * 导出Excel 文件流
	 */
	private transient InputStream excelStream;
	/**
	 * 导出Excel 文件名
	 */
	private String fileName;
	
	private GoodsDistributionVo goodsDistributionVo = new GoodsDistributionVo();
	private IGoodsDistributionService goodsDistributionService;
	
	public void setGoodsDistributionService(
			IGoodsDistributionService goodsDistributionService) {
		this.goodsDistributionService = goodsDistributionService;
	}
	public GoodsDistributionVo getGoodsDistributionVo() {
		return goodsDistributionVo;
	}
	public void setGoodsDistributionVo(GoodsDistributionVo goodsDistributionVo) {
		this.goodsDistributionVo = goodsDistributionVo;
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
	
	
	/**
	 * 转运场货量流动分布跳转首页
	 * 
	 * @Author: 200978 xiaobingcheng 2015-1-23
	 * @return
	 */
	public String goodsDistributionIndex() {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		try {
			Map<String, String> transferCenter = goodsDistributionService
					.queryParentTfrCtrCode(currentDeptCode);
			if (transferCenter != null) {
				goodsDistributionVo.setTransferCenterCode(transferCenter
						.get("code"));
				goodsDistributionVo.setTransferCenterName(transferCenter
						.get("name"));
				goodsDistributionVo.setOperationDeptCode(null);
				goodsDistributionVo.setOperationDeptName(null);
			} else {
				Map<String, String> operationDept = this.goodsDistributionService
						.queryOperationDeptCode(currentDeptCode);
				if (operationDept != null) {
					goodsDistributionVo.setTransferCenterCode(null);
					goodsDistributionVo.setTransferCenterName(null);
					goodsDistributionVo.setOperationDeptCode(operationDept
							.get("code"));
					goodsDistributionVo.setOperationDeptName(operationDept
							.get("name"));
				} else {

					goodsDistributionVo.setTransferCenterCode(null);
					goodsDistributionVo.setTransferCenterName(null);
					goodsDistributionVo.setOperationDeptCode(null);
					goodsDistributionVo.setOperationDeptName(null);
				}
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOG.error(e.getStackTrace().toString());
			return returnError(e);
		}
	}
	
	/**
	 * 查询每日转运场货量流动分布
	 * @Author 200978
	 * 2015-3-12
	 * @return
	 */
	public String queryGoodsDistributionByDay(){
		GoodsDistributionEntity goodsDistributionEntity = new GoodsDistributionEntity();
		goodsDistributionEntity.setTransferCenterCode(this.getGoodsDistributionVo().getTransferCenterCode());
		goodsDistributionEntity.setOperationDeptCode(this.getGoodsDistributionVo().getOperationDeptCode());
		goodsDistributionEntity.setStaDate(this.getGoodsDistributionVo().getStaDate());
		try{
			List<GoodsDistributionEntity> list = this.goodsDistributionService.queryGoodsDistributionByDay(goodsDistributionEntity);
			/**
			 * @dessc 日数据
			 * @author 105795
			 * @date 2015-05-29
			 * */
			List<GoodsDistributionEntity> handlerList=new ArrayList<GoodsDistributionEntity>();
			//将整点的数据剔除调
			if(CollectionUtils.isNotEmpty(list)&&list.size()>0){
				//先将00:00将加入handlerList
				handlerList.add(list.get(0));
				for(int i=0;i<list.size();i++){
					String timeStr=list.get(i).getStaTime();
					if(StringUtil.isNotEmpty(timeStr)&&timeStr.contains(":30")&&i!=(list.size()-1)){
						handlerList.add(list.get(i));
					}
					//将最后一个加入
					if(i==list.size()-1){
						handlerList.add(list.get(i));
					}
				}
				
			}
			
			this.goodsDistributionVo.setGoodsDistributionList(handlerList);
			return returnSuccess();
		} catch (BusinessException e) {
			LOG.error(e.getStackTrace().toString());
			return returnError(e);
		}
	}
	
	/**
	 * 查询每月转运场货量流动分布
	 * @Author 200978
	 * 2015-3-12
	 * @return
	 */
	public String queryGoodsDistributionByMonth(){
		GoodsDistributionEntity goodsDistributionEntity = new GoodsDistributionEntity();
		goodsDistributionEntity.setTransferCenterCode(this.getGoodsDistributionVo().getTransferCenterCode());
		goodsDistributionEntity.setOperationDeptCode(this.getGoodsDistributionVo().getOperationDeptCode());
		goodsDistributionEntity.setStaDate(this.getGoodsDistributionVo().getStaDate());
		try{
			List<GoodsDistributionEntity> list = this.goodsDistributionService.queryGoodsDistributionByMonth(goodsDistributionEntity);
			
			List<GoodsDistributionEntity> handlerList=new ArrayList<GoodsDistributionEntity>();
			//将整点的数据剔除调
			if(CollectionUtils.isNotEmpty(list)&&list.size()>0){
				//先将00:00将加入handlerList
				handlerList.add(list.get(0));
				for(int i=0;i<list.size();i++){
					String timeStr=list.get(i).getStaTime();
					if(StringUtil.isNotEmpty(timeStr)&&timeStr.contains(":30")&&i!=(list.size()-1)){
						handlerList.add(list.get(i));
					}
					//将最后一个加入
					if(i==list.size()-1){
						handlerList.add(list.get(i));
					}
				}
				
			}
			this.goodsDistributionVo.setGoodsDistributionList(handlerList);
			return returnSuccess();
		} catch (BusinessException e) {
			LOG.error(e.getStackTrace().toString());
			return returnError(e);
		}
	}
	
	
	/**
	 * 转运场货量流动导出  按日
	 * @Author 200978
	 * 2015-3-13
	 * @return
	 */
	public String goodsDistributionByDayExport() {
		try {
			/**
			 * 设置文件名
			 */
			fileName = this.goodsDistributionService.encodeFileName(PlatformConstants.TRUCK_EFFICIENCY_DAY_SHEET_NAME);
			
			GoodsDistributionEntity goodsDistributionEntity = new GoodsDistributionEntity();
			goodsDistributionEntity.setStaDate(goodsDistributionVo.getStaDate());
			goodsDistributionEntity.setTransferCenterCode(goodsDistributionVo.getTransferCenterCode());
			goodsDistributionEntity.setOperationDeptCode(goodsDistributionVo.getOperationDeptCode());
			/**
			 * 获取文件内容流
			 */
			excelStream = goodsDistributionService.goodsDistributionByDayExport(goodsDistributionEntity);
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 转运场货量流动导出  按月均
	 * @Author 200978
	 * 2015-3-13
	 * @return
	 */ 
	public String goodsDistributionByMonthExport() {
		try {
			/**
			 * 设置文件名
			 */
			fileName = this.goodsDistributionService.encodeFileName(PlatformConstants.TRUCK_EFFICIENCY_DAY_SHEET_NAME);
			
			GoodsDistributionEntity goodsDistributionEntity = new GoodsDistributionEntity();
			goodsDistributionEntity.setStaDate(goodsDistributionVo.getStaDate());
			goodsDistributionEntity.setTransferCenterCode(goodsDistributionVo.getTransferCenterCode());
			goodsDistributionEntity.setOperationDeptCode(goodsDistributionVo.getOperationDeptCode());
			/**
			 * 获取文件内容流
			 */
			excelStream = goodsDistributionService.goodsDistributionByMonthExport(goodsDistributionEntity);
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	
}
