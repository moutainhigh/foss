package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliveryMapManageService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryMapManageEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressDeliveryMapManageException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressDeliveryMapManageVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.eos.system.utility.StringUtil;

/**
 * 快递派送电子地图管理Action
 * @author dujunhui-187862
 * @date 2014-12-19 下午1:45:23
 */
public class ExpressDeliveryMapManageAction extends AbstractAction {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -4298531223500267757L;
	/*
	 * 快递派送电子地图管理Service
	 */
	private IExpressDeliveryMapManageService expressDeliveryMapManageService;
	/*
	 * 快递派送电子地图管理Vo
	 */
	private ExpressDeliveryMapManageVo expressDeliveryMapManageVo;
	/*
	 * 导出文件文件名
	 */
	private String downloadFileName;
	/*
	 * 导出文件数据流
	 */
	private InputStream inputStream;
	
	public ExpressDeliveryMapManageVo getExpressDeliveryMapManageVo() {
		return expressDeliveryMapManageVo;
	}
	public void setExpressDeliveryMapManageVo(
			ExpressDeliveryMapManageVo expressDeliveryMapManageVo) {
		this.expressDeliveryMapManageVo = expressDeliveryMapManageVo;
	}
	public void setExpressDeliveryMapManageService(
			IExpressDeliveryMapManageService expressDeliveryMapManageService) {
		this.expressDeliveryMapManageService = expressDeliveryMapManageService;
	}
	
	public String getDownloadFileName() {
		return downloadFileName;
	}
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	/*
	 * 按条件查询快递派送电子地图管理信息列表
	 * @author dujunhui-187862
	 * @date 2014-12-19 下午2:13:41
	 */
	@JSON
	public String queryExpressDeliveryMapManage(){
		try{
			//获取前台查询实体
			if(expressDeliveryMapManageVo==null){
				throw new ExpressDeliveryMapManageException("查询实体为空！");//防止分页控件产生的空指针异常
			}
			ExpressDeliveryMapManageEntity queryEntity=expressDeliveryMapManageVo.getEntity();
			if(null!=queryEntity&&StringUtil.equals(queryEntity.getVerifyState(), 
					DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_NOT_EDITED)){
				queryEntity.setStatus("NOT_EDITED");
				queryEntity.setVerifyState(null);
			}
			//通过前台条件查询实体链表并返回
			List<ExpressDeliveryMapManageEntity> entityList=expressDeliveryMapManageService.
					queryExpressDeliveryMapManageByCondition(queryEntity, start, limit);
			if(!CollectionUtils.isEmpty(entityList)){
				expressDeliveryMapManageVo.setEntityList(entityList);
			}
			 
			this.setTotalCount(expressDeliveryMapManageService.queryExpressDeliveryMapManageCountByCondition(queryEntity));
			//设置查询出的实体条数
			return returnSuccess();
		}catch(BusinessException e){
			return returnError(e);
		}
	}
	
	/*
	 * 根据salesDepartmentCode查询快递派送电子地图管理实体
	 * @author dujunhui-187862
	 * @date 2014-12-19 下午2:22:25
	 */
	@JSON
	public String queryExpressDeliveryMapManageByCode(){
		try{
			//获取前台salesDepartmentCode
			String salesDepartmentCode=expressDeliveryMapManageVo.getEntity().getSalesDepartmentCode();
			//根据salesDepartmentCode查询实体并返回
			expressDeliveryMapManageVo.setEntity(expressDeliveryMapManageService.
					queryExpressDeliveryMapManageByCode(salesDepartmentCode));
			return returnSuccess();
		}catch(BusinessException e){
			return returnError(e);
		}
	}
	
	/*
	 * 根据Code数组查询快递派送电子地图管理实体列表
	 * @author dujunhui-187862
	 * @date 2014-12-19 下午2:46:15
	 */
	@JSON
	public String queryExpressDeliveryMapManageBatchView(){
		try{
			//获取前台codes
			String[] codes=expressDeliveryMapManageVo.getCodeList();
			//批量查询实体信息
			expressDeliveryMapManageVo.setEntityList(expressDeliveryMapManageService.
					queryExpressDeliveryMapManageBatchView(codes));
			return returnSuccess();
		}catch(BusinessException e){
			return returnError(e);
		}
	}
	
	/*
	 * 审核营业部快递派送电子地图130134--
	 * @author dujunhui-187862
	 * @date 2014-12-30 下午3:08:36
	 */
	@JSON
	public String verifyExpressDeliveryMapManage(){
		try{
			//获取前台选中营业部数据
			String[] codes=expressDeliveryMapManageVo.getCodeList();
			//审核操作
			expressDeliveryMapManageService.
				verifyExpressDeliveryMapManage(codes);
			return returnSuccess(MessageType.VERIFY_EXPRESS_DELIVERY_MAP_MANAGE_SUCCESS);//审核成功
		}catch(BusinessException e){
			return returnError(e);
		}
	}
	
	/*
	 * 生效营业部快递派送电子地图130134
	 * @author dujunhui-187862
	 * @date 2014-12-31 上午11:11:11
	 */
	@JSON
	public String activateExpressDeliveryMapManage(){
		try{
			//获取前台选中营业部数据
			String[] codes=expressDeliveryMapManageVo.getCodeList();
			//生效操作
			expressDeliveryMapManageService.activateExpressDeliveryMapManage(codes);
			return returnSuccess(MessageType.VALID_SUCCESS);//生效成功
		}catch(BusinessException e){
			return returnError(e);
		}
	}
	
	/*
	 * 退回营业部快递派送电子地图130134
	 * @author dujunhui-187862
	 * @date 2014-12-31 下午3:54:44
	 */
	@JSON
	public String turnBackExpressDeliveryMapManage(){
		try{
			//生效操作
			expressDeliveryMapManageService.turnBackExpressDeliveryMapManage(expressDeliveryMapManageVo);
			return returnSuccess(MessageType.TURNBACK_EXPRESS_DELIVERY_MAP_MANAGE_SUCCESS);//退回成功
		}catch(BusinessException e){
			return returnError(e);
		}
	}
	
	/*
	 * 根据查询条件查询数据并导出
	 * @author dujunhui-187862
	 * @date 2014-12-19 下午3:12:53
	 */
	@JSON
	public String queryExpressDeliveryMapManageForExprot(){
		try {
			//获取前台查询实体
			ExpressDeliveryMapManageEntity entity = expressDeliveryMapManageVo.getEntity();

			//通过前台条件查询实体链表并返回
			List<ExpressDeliveryMapManageEntity> exportSource = expressDeliveryMapManageService.
					queryExpressDeliveryMapManageByCondition(entity, 0, Integer.MAX_VALUE);
			
			//导出文件名
			downloadFileName =new String(ColumnConstants.
					EXPORT_EXPRESSDELIVERY_MAPMANAGE_NAME.getBytes("UTF-8"), "iso-8859-1");
		
			final ExportSetting settings = new ExportSetting();
			settings.setFileName(ColumnConstants.EXPORT_EXPRESSDELIVERY_MAPMANAGE_NAME);
			settings.setSize(ComnConst.SINGLE_ASYN_EXPORT_FILE_MAX_SIZE);
			settings.setFileDesc(String.format("%s导出", settings.getFileName()));
			
			ExportResource resource = new ExportResource();
			//设置表头
			resource.setHeads(ComnConst.EXPRESSDELIVERY_MAPMANAGE_TITLE);
			//数据封装
			List<List<String>> resultList = new ArrayList<List<String>>();
			SimpleDateFormat formatHMS=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(!CollectionUtils.isEmpty(exportSource)) {
				for(ExpressDeliveryMapManageEntity entityList:exportSource){
					List<String> list = new ArrayList<String>();
					list.add(entityList.getSalesDepartmentName());//营业部名称
					//审核状态的设置
					if(StringUtil.equals(entityList.getVerifyState(), 
							DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_NOT_EDITED)){
						list.add("未编辑");//未编辑状态
					}else if(StringUtil.equals(entityList.getVerifyState(), 
							DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_NOT_VERIFIED)){
						list.add("未审核");//未审核状态
					}else if(StringUtil.equals(entityList.getVerifyState(), 
							DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_IS_VERIFIED)){
						list.add("已审核");//已审核状态
					}else if(StringUtil.equals(entityList.getVerifyState(), 
							DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_IS_TURNBACK)){
						list.add("已退回");//已退回状态
					}else if(StringUtil.equals(entityList.getVerifyState(), 
							DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_IS_ACTIVED)){
						list.add("生效");//生效状态
					}
					list.add(entityList.getDistrictName());//行政区域
					list.add(entityList.getExpressManNum()+"");//快递员人数
					list.add(entityList.getDepartServiceArea()+"");//营业部服务面积
					list.add(entityList.getApplyManName());//申请人
					if(entityList.getApplyTime()!=null){//非空判断
						list.add(formatHMS.format(entityList.getApplyTime()));//申请时间
					}
					list.add(entityList.getVerifyManName());//审核人
					if(entityList.getVerifyTime()!=null){//非空判断
						list.add(formatHMS.format(entityList.getVerifyTime()));//审核时间
					}
					resultList.add(list);
				}
			}
			//获取excel 信息
			resource.setRowList(resultList);
			ExporterExecutor executor = new ExporterExecutor();
			inputStream =executor.exportSync(resource, settings);
		    return returnSuccess("导出成功！文件已下载至浏览器的默认下载路径中！");
		} catch (BusinessException e) {
		    return returnError(e);
		}catch (UnsupportedEncodingException e) {
		    return returnError("UnsupportedEncodingException", e);
		}
	}
	
	/*
	 * 更新保存GIS派送区域电子地图坐标
	 * @author dujunuhi-187862
	 * @date 2015-1-7 下午3:31:23 
	 */
	@JSON
	public String updateExpressDeliveryGISMap(){
		try{
			ExpressDeliveryMapManageEntity updateGISEntity=expressDeliveryMapManageVo.getEntity();
//			expressDeliveryMapManageService.updateExpressDeliveryGISMap(updateGISEntity); FossUserContext.getCurrentUser().getEmployee()
			updateGISEntity.setVerifyState(DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_NOT_VERIFIED);
			updateGISEntity.setApplyTime(new Date());
			updateGISEntity.setApplyManCode(FossUserContext.getCurrentUser().getEmployee().getEmpCode());// 设置审核人工号
			updateGISEntity.setApplyManName(FossUserContext.getCurrentUser().getEmployee().getEmpName());
			updateGISEntity.setCodeList(new String[]{updateGISEntity.getSalesDepartmentCode()});
			updateGISEntity.setVerifyManCode(null);
			updateGISEntity.setVerifyManName(null);
			updateGISEntity.setVerifyTime(null);
			expressDeliveryMapManageService.addSalesDepartmentInfo(updateGISEntity);
			return returnSuccess("GIS派送区域坐标成功保存至FOSS！");
		}catch(BusinessException e){
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@JSON
	public String deleteExpressDeliveryGISMap(){
		try{
			ExpressDeliveryMapManageEntity updateGISEntity=expressDeliveryMapManageVo.getEntity();
			if(null==updateGISEntity){
				throw new ExpressDeliveryMapManageException("数据异常!");//防止分页控件产生的空指针异常
			}
			expressDeliveryMapManageService.deleteExpressDeliveryMapManageEntityById(updateGISEntity);
			return returnSuccess("GIS派送区域坐标成功保存至FOSS！");	
		}catch (BusinessException e) {
			return returnError(e);
		}
	}
	
}
