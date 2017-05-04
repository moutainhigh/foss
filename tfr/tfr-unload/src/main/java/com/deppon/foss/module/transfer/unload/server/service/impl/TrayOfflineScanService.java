package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.unload.api.server.dao.ITrayOfflineScanDao;
import com.deppon.foss.module.transfer.unload.api.server.service.ITrayOfflineScanService;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.TrayOfflineScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.TrayOfflineScanExpressEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryTrayOfflineScanConditionDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

public class TrayOfflineScanService implements ITrayOfflineScanService {

	/**
	 * The pda common service.
	 * 
	 * 
	 */
	public IPDACommonService pdaCommonService;

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
	/**
	 * 
	 * The sale department service.
	 * 
	 */
	private ISaleDepartmentService saleDepartmentService;

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
	 * The org administrative info service.
	 * 
	 * 
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

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
	private ITrayOfflineScanDao trayOfflineScanDao;
	
	public ITrayOfflineScanDao getTrayOfflineScanDao() {
		return trayOfflineScanDao;
	}



	public void setTrayOfflineScanDao(ITrayOfflineScanDao trayOfflineScanDao) {
		this.trayOfflineScanDao = trayOfflineScanDao;
	}

	//未使用-sonar-352203
//	private IPDATrayScanService pdaTrayScanService;

	
/*	public void setPdaTrayScanService(IPDATrayScanService pdaTrayScanService) {
		this.pdaTrayScanService = pdaTrayScanService;
	}*/



	/**
	 * 查询叉车离线扫描信息（离线扫描与托盘任务匹配）零担
	 * @author foss-heyongdong
	 * @Date 2014年1月6日 14:20:55
	 * */
	@Override
	public List<TrayOfflineScanEntity> querytrayOfflineScanInfo(
			QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto,int limit,int start) {
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		
		queryTrayOfflineScanConditionDto.setBeginTrayCtreateTime(DateUtils.addDayToDate(queryTrayOfflineScanConditionDto.getBeginOfflineTrayScanTime(), -UnloadConstants.SONAR_NUMBER_3));
		queryTrayOfflineScanConditionDto.setEndTrayCtreateTime(DateUtils.addDayToDate(queryTrayOfflineScanConditionDto.getEndOfflineTrayScanTime(), +UnloadConstants.SONAR_NUMBER_3));
		queryTrayOfflineScanConditionDto.setOutFieldCoade(getCenterCode(orgCode));
		

		List<TrayOfflineScanEntity> trayOfflineScanList= trayOfflineScanDao.querytrayOfflineScanInfo(queryTrayOfflineScanConditionDto,limit,start);
		return trayOfflineScanList;
	}
	
	/**
	 * 查询叉车离线扫描信息（离线扫描与托盘任务匹配）快递
	 * @author foss-heyongdong
	 * @Date 2014年1月6日 14:20:55
	 * */
	@Override
	public List<TrayOfflineScanExpressEntity> querytrayOfflineScanInfoExpress(
			QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto,int limit,int start) {
		/*String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		queryTrayOfflineScanConditionDto.setBeginTrayCtreateTime(DateUtils.addDayToDate(queryTrayOfflineScanConditionDto.getBeginOfflineTrayScanTime(), -3));
		queryTrayOfflineScanConditionDto.setEndTrayCtreateTime(DateUtils.addDayToDate(queryTrayOfflineScanConditionDto.getEndOfflineTrayScanTime(), +3));
		queryTrayOfflineScanConditionDto.setOutFieldCoade(getCenterCode(orgCode));*/
		
		List<TrayOfflineScanExpressEntity> trayOfflineScanList = new ArrayList<TrayOfflineScanExpressEntity>();
		//定义时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//获取的数据源	
		String resultData= trayOfflineScanDao.querytrayOfflineScanInfoExpress(queryTrayOfflineScanConditionDto,limit,start);
		if(StringUtils.isNotEmpty(resultData)){
			JSONObject resultJson=JSONObject.fromObject(resultData);
			if(resultJson.getString("data") != null && !"null".equals(resultJson.getString("data"))){
				for (int i = 0; i < resultJson.getJSONArray("data").size(); i++) {
					try {
						TrayOfflineScanExpressEntity trayOfflineScan=new TrayOfflineScanExpressEntity();
						/**运单/包/笼号*/
						trayOfflineScan.setWaybillNo(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("pieceNo")));
						/**离线扫描时间*/
						Date date=(StringUtils.equals(resultJson.getJSONArray("data").getJSONObject(i).getString("trayOfflineScanTime"), "null") ? null:sdf.parse(resultJson.getJSONArray("data").getJSONObject(i).getString("trayOfflineScanTime")));
						trayOfflineScan.setTrayOfflineScanTime(date);
						/**叉车司机姓名*/
						trayOfflineScan.setForkLiftDriverName(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("forkliftDriverName")));
						/**叉车司机工号*/
						trayOfflineScan.setForkLiftDriverCode(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("forkliftDriverNo")));
						/**叉车司机部门*/
						trayOfflineScan.setForkLiftDept(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("forkliftDeptName")));
						/**叉车离线扫描任务号*/
						trayOfflineScan.setOfflineTaskNo(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("offlineScanTaskNo")));
						/**托盘任务号*/
						trayOfflineScan.setTaskNo(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("trayBindingTaskNo")));
						/**创建人*/
						trayOfflineScan.setCreateUserName(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("taskBinderName")));
						/**创建人工号*/
						trayOfflineScan.setCreateUserCode(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("taskBinderNo")));
						/**创建人部门*/
						trayOfflineScan.setCreaterDept(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("taskBinderDeptName")));
						//数据总数
						trayOfflineScan.setCount(resultJson.getLong("totalRows"));
						//实体类封装到List里面
						trayOfflineScanList.add(trayOfflineScan);
					} catch (ParseException e) {
						System.out.println("数据转换失败！:"+e.getMessage());
					}
				}
			}
		}
		
		return trayOfflineScanList;
	}
	
	//把“null”转换为“”
	public String isNotNull(String sta){
		if("null".equals(sta)){
			return "";
		}
		return sta;
	}
	
	/**
	 * 查询叉车离线扫描信息总条数 零担
	 * @author foss-heyongdong
	 * @Date 2014年1月8日 09:36:46
	 * @param queryTrayOfflineScanConditionDto
	 * @return
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.ITrayOfflineScanService#querytrayOfflineScanTotal(QueryTrayOfflineScanConditionDto)
	 */
	@Override
	public Long querytrayOfflineScanTotal(QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto) {
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		queryTrayOfflineScanConditionDto.setBeginTrayCtreateTime(DateUtils.addDayToDate(queryTrayOfflineScanConditionDto.getBeginOfflineTrayScanTime(), -UnloadConstants.SONAR_NUMBER_3));
		queryTrayOfflineScanConditionDto.setEndTrayCtreateTime(DateUtils.addDayToDate(queryTrayOfflineScanConditionDto.getEndOfflineTrayScanTime(), +UnloadConstants.SONAR_NUMBER_3));
		queryTrayOfflineScanConditionDto.setOutFieldCoade(getCenterCode(orgCode));
		List<TrayOfflineScanEntity> trayOfflineScanList = trayOfflineScanDao.querytrayOfflineScanInfoNoPage(queryTrayOfflineScanConditionDto);
		if(trayOfflineScanList==null){
			//返回0
			return 0l;
		}else{
			//返回总条数
			int a=  trayOfflineScanList.size();
			return (long) a;
		}
		 
		
	}
	
	/**
	 * 查询叉车离线扫描信息总条数 快递
	 * @author foss-heyongdong
	 * @Date 2014年1月8日 09:36:46
	 * @param queryTrayOfflineScanConditionDto
	 * @return
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.ITrayOfflineScanService#querytrayOfflineScanTotal(QueryTrayOfflineScanConditionDto)
	 */
	@Override
	public Long querytrayOfflineScanTotalExpress(QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto) {
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		queryTrayOfflineScanConditionDto.setBeginTrayCtreateTime(DateUtils.addDayToDate(queryTrayOfflineScanConditionDto.getBeginOfflineTrayScanTime(), -UnloadConstants.SONAR_NUMBER_3));
		queryTrayOfflineScanConditionDto.setEndTrayCtreateTime(DateUtils.addDayToDate(queryTrayOfflineScanConditionDto.getEndOfflineTrayScanTime(), +UnloadConstants.SONAR_NUMBER_3));
		queryTrayOfflineScanConditionDto.setOutFieldCoade(getCenterCode(orgCode));
		List<TrayOfflineScanExpressEntity> trayOfflineScanList = trayOfflineScanDao.querytrayOfflineScanInfoNoPageExpress(queryTrayOfflineScanConditionDto);
		if(trayOfflineScanList==null){
			//返回0
			return 0l;
		}else{
			//返回总条数
			int a=  trayOfflineScanList.size();
			return (long) a;
		}
		 
		
	}
	
	/**
	 *  零担
	 * 导出叉车离线扫描信息
	 * @author foss-heyongdong
	 * @Date 2014年1月8日 10:54:51
	 * @param queryTrayOfflineScanConditionDto
	 * @return List
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.ITrayOfflineScanService#getTrayOfflineScanTaskInputStream(QueryTrayOfflineScanConditionDto)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getTrayOfflineScanTaskInputStream(QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto) {
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		queryTrayOfflineScanConditionDto.setBeginTrayCtreateTime(DateUtils.addDayToDate(queryTrayOfflineScanConditionDto.getBeginOfflineTrayScanTime(), -UnloadConstants.SONAR_NUMBER_3));
		queryTrayOfflineScanConditionDto.setEndTrayCtreateTime(DateUtils.addDayToDate(queryTrayOfflineScanConditionDto.getEndOfflineTrayScanTime(), +UnloadConstants.SONAR_NUMBER_3));
		queryTrayOfflineScanConditionDto.setOutFieldCoade(getCenterCode(orgCode));
		List<TrayOfflineScanEntity> trayOfflineScanList = trayOfflineScanDao.querytrayOfflineScanInfoNoPage(queryTrayOfflineScanConditionDto);
		//数据集合List
		List<List<String>> rowList = new ArrayList<List<String>>();
		//为空则导出空文件
		if(CollectionUtils.isEmpty(trayOfflineScanList)){
			List<String> columnList = new ArrayList<String>();
			rowList.add(columnList);
		}
		for(TrayOfflineScanEntity entity:trayOfflineScanList){
			List<String> columnList = new ArrayList<String>();
			//离线扫描时间
			columnList.add(DateUtils.convert(entity.getTrayOfflineScanTime(),DateUtils.DATE_TIME_FORMAT ));
			//包号
			if(entity.getPackageNo()==null){
				columnList.add("");
			}else{
				columnList.add(entity.getPackageNo());
			}
			//运单号
			columnList.add(entity.getWaybillNo());
			//流水号
			columnList.add(entity.getSerialNo());
			//叉车司机姓名
			columnList.add(entity.getForkLiftDriverName());
			//叉车司机工号
			columnList.add(entity.getForkLiftDriverCode());
			//叉车司机部门
			columnList.add(entity.getForkLiftDept());
			//叉车离线扫描任务号
			columnList.add(entity.getOfflineTaskNo());
			if(entity.getTaskNo()==null){
				columnList.add("");
			}else{
				//托盘任务号
				columnList.add(entity.getTaskNo());
			}
			if(entity.getCreateUserName()==null){
				columnList.add("");
			}else{
				//绑定人姓名
				columnList.add(entity.getCreateUserName());
			}
			
			if(entity.getCreateUserCode()==null){
				columnList.add("");
			}else{
				//绑定人工号
				columnList.add(entity.getCreateUserCode());
			}
			if(entity.getCreaterDept()==null){
				columnList.add("");
			}else{
				//绑定人部门
				columnList.add(entity.getCreaterDept());
			}
			
			if(entity.getForkliftCount()==null){
				columnList.add("0");
			}else{
				//叉车票数
				columnList.add(entity.getForkliftCount().toString());
			}
			//卸车任务创建人工号
			if(entity.getLoaderCode()==null){
				columnList.add("");
			}else{
				columnList.add(entity.getLoaderCode());
			}
			//卸车任务创建人姓名
			if(entity.getLoaderName()==null){
				columnList.add("");
			}else{
				columnList.add(entity.getLoaderName());
			}
			//卸车任务创建人部门
			if(entity.getLoadOrgName()==null){
				columnList.add("");
			}else{
				columnList.add(entity.getLoadOrgName());
			}
			rowList.add(columnList);
		}
		
		String[] rowHeaders={
				"离线扫描时间",
				"包号",
				"运单号",
				"流水号",
				"叉车司机姓名",
				"叉车司机工号",
				"叉车司机部门",
				"叉车离线扫描任务号",
				"托盘任务号",
				"绑定人姓名",
				"绑定人工号",
				"绑定人部门",
				"叉车票数"	,
				"卸车任务创建人工号",
				"卸车任务创建人姓名",
				"卸车任务创建人部门"
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
	    exportSetting.setSheetName("叉车离线扫描信息列表");
	    //设置sheet行数
	    exportSetting.setSize(trayOfflineScanList.size() + 1);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    //获取输入流
	    InputStream excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);

        //文件名
        String fileName = "叉车离线工作量管理";
		try {
			String agent = (String) ServletActionContext.getRequest().getHeader(
    				"USER-AGENT");

    		if (agent != null && agent.indexOf("MSIE") == -1) {
    			fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
    		} else {
    			fileName = URLEncoder.encode(fileName, "UTF-8");
    		}
		} catch (UnsupportedEncodingException e) {
			//抛出业务异常
			throw new TfrBusinessException(e.getMessage());
		} //设置fileName
        List list = new ArrayList();
        list.add(fileName);
        list.add(excelStream);
        //返回action
        return list;
	}
	
	/**
	 *  快递
	 * 导出叉车离线扫描信息
	 * @author foss-heyongdong
	 * @Date 2014年1月8日 10:54:51
	 * @param queryTrayOfflineScanConditionDto
	 * @return List
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.ITrayOfflineScanService#getTrayOfflineScanTaskInputStream(QueryTrayOfflineScanConditionDto)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getTrayOfflineScanTaskInputStreamExpress(QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto) {
		/*String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		queryTrayOfflineScanConditionDto.setBeginTrayCtreateTime(DateUtils.addDayToDate(queryTrayOfflineScanConditionDto.getBeginOfflineTrayScanTime(), -3));
		queryTrayOfflineScanConditionDto.setEndTrayCtreateTime(DateUtils.addDayToDate(queryTrayOfflineScanConditionDto.getEndOfflineTrayScanTime(), +3));
		queryTrayOfflineScanConditionDto.setOutFieldCoade(getCenterCode(orgCode));*/
		List<TrayOfflineScanExpressEntity> trayOfflineScanList = querytrayOfflineScanInfoExpress(queryTrayOfflineScanConditionDto,ConstantsNumberSonar.SONAR_NUMBER_10000,0);
		//数据集合List
		List<List<String>> rowList = new ArrayList<List<String>>();
		//为空则导出空文件
		if(CollectionUtils.isEmpty(trayOfflineScanList)){
			List<String> columnList = new ArrayList<String>();
			rowList.add(columnList);
		}
		for(TrayOfflineScanExpressEntity entity:trayOfflineScanList){
			List<String> columnList = new ArrayList<String>();
			//离线扫描时间
			columnList.add(DateUtils.convert(entity.getTrayOfflineScanTime(),DateUtils.DATE_TIME_FORMAT ));
			//运单号
			columnList.add(entity.getWaybillNo());
			//叉车司机姓名
			columnList.add(entity.getForkLiftDriverName());
			//叉车司机工号
			columnList.add(entity.getForkLiftDriverCode());
			//叉车司机部门
			columnList.add(entity.getForkLiftDept());
			//叉车离线扫描任务号
			columnList.add(entity.getOfflineTaskNo());
			if(entity.getTaskNo()==null){
				columnList.add("");
			}else{
				//托盘任务号
				columnList.add(entity.getTaskNo());
			}
			if(entity.getCreateUserName()==null){
				columnList.add("");
			}else{
				//绑定人姓名
				columnList.add(entity.getCreateUserName());
			}
			
			if(entity.getCreateUserCode()==null){
				columnList.add("");
			}else{
				//绑定人工号
				columnList.add(entity.getCreateUserCode());
			}
			if(entity.getCreaterDept()==null){
				columnList.add("");
			}else{
				//绑定人部门
				columnList.add(entity.getCreaterDept());
			}
			rowList.add(columnList);
		}
		
		String[] rowHeaders={
				"离线扫描时间",
				"运单/包/笼号",
				"叉车司机姓名",
				"叉车司机工号",
				"叉车司机部门",
				"叉车离线扫描任务号",
				"托盘任务号",
				"绑定人姓名",
				"绑定人工号",
				"绑定人部门"
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
	    exportSetting.setSheetName("叉车离线扫描信息列表");
	    //设置sheet行数
	    exportSetting.setSize(trayOfflineScanList.size() + 1);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    //获取输入流
	    InputStream excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);

        //文件名
        String fileName = "叉车离线工作量管理";
		try {
			String agent = (String) ServletActionContext.getRequest().getHeader(
    				"USER-AGENT");

    		if (agent != null && agent.indexOf("MSIE") == -1) {
    			fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
    		} else {
    			fileName = URLEncoder.encode(fileName, "UTF-8");
    		}
		} catch (UnsupportedEncodingException e) {
			//抛出业务异常
			throw new TfrBusinessException(e.getMessage());
		} //设置fileName
        List list = new ArrayList();
        list.add(fileName);
        list.add(excelStream);
        //返回action
        return list;
	}
	
	/**
	 * 调用综合接口通过当前部门编码查询对应外场编码，如果查询不到则返当前部门编码
	 * 
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-13 10:33:01
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
		if (unloadOrg != null) {
			transferCenterCode = unloadOrg.getCode();
		}

		return transferCenterCode;
	}



	



	
}
