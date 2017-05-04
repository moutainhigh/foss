package com.deppon.foss.module.transfer.load.server.service.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.ITrayScanDao;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.server.service.ITrayScanService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanDetaiEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanExpressEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.TrayScanDto;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATrayScanService;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

public class TrayScanService implements ITrayScanService{
	
	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	/**
	 * 用于获取小部门所属的大部门
	 */
	private ILoadService loadService;
	
	/**
	 * 托盘扫描模块Dao
	 * */
	private ITrayScanDao trayScanDao;
	
	/**
	 * 用于获取小部门所属的大部门
	 */
	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}

	/**
	 * 托盘扫描模块Dao，set方法
	 */
	public void setTrayScanDao(ITrayScanDao trayScanDao) {
		this.trayScanDao = trayScanDao;
	}
	/**部门 复杂查询 service**/
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 设置 部门 复杂查询 service*.
	 *
	 * @param orgAdministrativeInfoComplexService the new 部门 复杂查询 service*
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
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
	
	public IPDATrayScanService  pdaTrayScanService;
	
	public void setPdaTrayScanService(IPDATrayScanService pdaTrayScanService) {
		this.pdaTrayScanService = pdaTrayScanService;
	}

	/**
	 * 获取当前部门的上级部门code
	 * @author 105869-foss-heyongdong
	 * @date 2013-7-30 下午2:58:53
	 */
	@Override
	public String querySuperiorOrgCode() {
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		OrgAdministrativeInfoEntity superEntity = loadService.querySuperiorOrgByOrgCode(orgCode);
		if(superEntity == null || StringUtils.isBlank(superEntity.getCode())){
			LOGGER.error("###################根据部门（code：" + orgCode + "）获取上级营业部、派送部、总调、外场、结果为空！");
			throw new TfrBusinessException("获取本部门的上级组织失败(包括营业部、派送部、总调、外场)！");
		}else{
			return superEntity.getCode();
		}
	}

	/**
	 * @function:查询托盘扫描任务  零担
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-1 9:09:45
	 * @param limit
	 * @param start
	 * @return List<TrayScanDto>
	 * @exception 无
	 * @param queryHandOverBillConditionDto 查询条件dto
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITrayScanService#queryTrayScanList(com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto)
	 */
	@Override
	public List<TrayScanDto> queryTrayScanList(
			QueryTrayScanConditionDto queryTrayScanConditiondto ,int limit,int start) {
		String orgCode = this.querySuperiorOrgCode();
		queryTrayScanConditiondto.setCurrentDept(this.getCenterCode(orgCode));
		List<TrayScanDto> queryTrayScanList = trayScanDao.queryTrayScanList(queryTrayScanConditiondto,limit,start);
		return queryTrayScanList;
	}
	
	/**
	 * @function:查询托盘扫描任务  快递
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-1 9:09:45
	 * @param limit
	 * @param start
	 * @return List<TrayScanDto>
	 * @exception 无
	 * @param queryHandOverBillConditionDto 查询条件dto
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITrayScanService#queryTrayScanList(com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto)
	 */
	@Override
	public List<TrayScanExpressEntity> queryTrayScanListExpress(
			QueryTrayScanConditionDto queryTrayScanConditiondto ,int limit,int start) {
		String orgCode = this.querySuperiorOrgCode();
		queryTrayScanConditiondto.setCurrentDept(this.getCenterCode(orgCode));
		List<TrayScanExpressEntity> queryTrayScanList = new ArrayList<TrayScanExpressEntity>();
		//定义时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//获取数据
		String resultData=trayScanDao.queryTrayScanListExpress(queryTrayScanConditiondto,limit,start);
		//判断返回的数据是否为空；
		if(StringUtils.isEmpty(resultData)){ 
			throw new TfrBusinessException("当前没有数据！"); 
		}
		
		//数据转成JSON格式
		JSONObject resultJson=JSONObject.fromObject(resultData);
		System.out.println("resultJson.Data="+resultJson.getString("data"));
		if(resultJson.getString("data") == null || resultJson.getString("data").equals("null")){
			System.out.println("返回数据为空");
		}else{
			String aa="";
			for (int i = 0; i < resultJson.getJSONArray("data").size(); i++) {
				try {
					//JSON数据封装成实体类；
					TrayScanExpressEntity  trayScan=new TrayScanExpressEntity();
					trayScan.setId(resultJson.getJSONArray("data").getJSONObject(i).getString("id"));// id
					
					trayScan.setTaskNo(resultJson.getJSONArray("data").getJSONObject(i).getString("trayBindingTaskNo"));// 绑定托盘的任务号
					Date date=(resultJson.getJSONArray("data").getJSONObject(i).getString("forkliftScanTime").equals("null") ? null:sdf.parse(resultJson.getJSONArray("data").getJSONObject(i).getString("forkliftScanTime")));
					trayScan.setTrayscanTime(date);//叉车扫描时间
					
					trayScan.setForkliftDriverCode(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("forkliftDriverNo")));//叉车司机工号

					trayScan.setForkliftDriverName(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("forkliftDriverName")));//叉车司机姓名

					trayScan.setForkliftDepartment(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("forkliftDriverDeptName")));//叉车司机所在部门

					trayScan.setBindingCode(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("taskBinderNo")));//绑定人工号

					trayScan.setBindingName(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("taskBinderName")));//绑定人姓名

					trayScan.setBindingDept(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("taskBinderDeptName")));//绑定人部门
					
					date=(resultJson.getJSONArray("data").getJSONObject(i).getString("taskBindTime").equals("null") ? null:sdf.parse(resultJson.getJSONArray("data").getJSONObject(i).getString("taskBindTime")));
					trayScan.setTraytaskCreatTime(date);//托盘绑定任务时间

					trayScan.setStatu(resultJson.getJSONArray("data").getJSONObject(i).getString("manageStatus"));//任务状态

					aa=(resultJson.getJSONArray("data").getJSONObject(i).getString("forkliftTicketsCount").equals("null") ? "0":resultJson.getJSONArray("data").getJSONObject(i).getString("forkliftTicketsCount"));
					trayScan.setForklifrCount(aa);//叉车票数
					
					trayScan.setCount(resultJson.getLong("totalRows"));//数据总数量
					queryTrayScanList.add(trayScan);
				} catch (ParseException e) {
					LOGGER.error("查询托盘扫描任务失败"+e.getMessage());
				}

			}
		}
		return queryTrayScanList;
	}
	
	//把“null”字符串转化为“”
	public String isNotNull(String sta){
		if("null".equals(sta)){
			return "";
		}
		return sta;
	}

	/**
	 * @function:查询托盘扫描任务总数  零担
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-1 9:09:45
	 * @return Long
	 * @exception 无
	 * @param queryHandOverBillConditionDto 查询条件dto
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITrayScanService#queryTrayScanList(com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto)
	 */
	@Override
	public Long getTrayScanListCount(
			QueryTrayScanConditionDto queryTrayScanConditiondto) {
		String orgCode = this.querySuperiorOrgCode();
		queryTrayScanConditiondto.setCurrentDept(this.getCenterCode(orgCode));
		Long totalCount = trayScanDao.getTrayScanListCount(queryTrayScanConditiondto);
		return totalCount;
	}
	
	/**
	 * @function:查询托盘扫描任务总数  快递
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-1 9:09:45
	 * @return Long
	 * @exception 无
	 * @param queryHandOverBillConditionDto 查询条件dto
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITrayScanService#queryTrayScanList(com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto)
	 */
	@Override
	public Long getTrayScanListCountExpress(
			QueryTrayScanConditionDto queryTrayScanConditiondto) {
		String orgCode = this.querySuperiorOrgCode();
		queryTrayScanConditiondto.setCurrentDept(this.getCenterCode(orgCode));
		Long totalCount = trayScanDao.getTrayScanListCountExpress(queryTrayScanConditiondto);
		return totalCount;
	}
	
	/**  零担
	 * @function:查询托盘扫描任务明细
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-2 15:27:40
	 * @return List<TrayScanDetaiEntity>
	 * @exception 无
	 * @param String traytaskCode
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITrayScanService#queryWaybillByTaskNo(java.lang.String)
	 */
	@Override
	public List<TrayScanDetaiEntity> queryWaybillByTaskNo(String traytaskCode) {
		List<TrayScanDetaiEntity> serialNoList = trayScanDao.queryWaybillByTaskNo(traytaskCode);
		return serialNoList;
	}
	
	/**  快递
	 * @function:查询托盘扫描任务明细
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-2 15:27:40
	 * @return List<TrayScanDetaiEntity>
	 * @exception 无
	 * @param String traytaskCode
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITrayScanService#queryWaybillByTaskNo(java.lang.String)
	 */
	@Override
	public List<TrayScanExpressEntity> queryWaybillByTaskNoExpress(String traytaskCode) {
		List<TrayScanExpressEntity> serialNoList =new ArrayList<TrayScanExpressEntity>();
		//获取JSON字符串返回的数据
		String resultData= trayScanDao.queryWaybillByTaskNoExpress(traytaskCode);
		//判断是否为空
		if(!StringUtils.isEmpty(resultData)){
			//字符串转化成JSON格式
			JSONObject resultJson=JSONObject.fromObject(resultData);
			//判断是否有数据
			if(resultJson.getString("data") != null && !"null".equals(resultJson.getString("data"))){
				//数据封装到实体类
				for (int i = 0; i < resultJson.getJSONArray("data").size(); i++) {
					TrayScanExpressEntity trayScan=new TrayScanExpressEntity();
					trayScan.setWaybillNumber(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("pieceNo")));//运单号
					trayScan.setDestinationStation(isNotNull(resultJson.getJSONArray("data").getJSONObject(i).getString("destStationName")));//目的站
					serialNoList.add(trayScan);
				}
			}
		}
		return serialNoList;
	}
	
	/**
	 *  零担
	 * @function:导出托盘扫描任务
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-2 16:10:59
	 * @return List
	 * @exception 无
	 * @param queryHandOverBillConditionDto 查询条件dto
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITrayScanService#getTrayScanTaskInputStream(com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getTrayScanTaskInputStream(
			QueryTrayScanConditionDto queryTrayScanConditiondto) {
		String orgCode = this.querySuperiorOrgCode();
		queryTrayScanConditiondto.setCurrentDept(this.getCenterCode(orgCode));
		List<TrayScanDto> trayScanList = trayScanDao.queryTrayScanListNoPage(queryTrayScanConditiondto);
		List<List<String>> rowList = new ArrayList<List<String>>();
		//如果查询结果为空，则导出空文件
		if(CollectionUtils.isEmpty(trayScanList)){
			List<String> columnList = new ArrayList<String>();
			rowList.add(columnList);
		}
		for(TrayScanDto trayscan : trayScanList){
			List<String> columnList = new ArrayList<String>();
			//托盘任务号
			columnList.add(trayscan.getTaskNo());
			//托盘任务号
			if(StringUtils.endsWith(trayscan.getStatu(),"SCANNED")){
				columnList.add("已扫描");
			}else if(StringUtils.endsWith(trayscan.getStatu(),"UNSCAN")){
				columnList.add("未扫描");
			}else{
				columnList.add("手动拉车");
			}
			
			//叉车扫描时间
			columnList.add(trayscan.getTrayscanTime()==null?"":DateUtils.convert(trayscan.getTrayscanTime(),DateUtils.DATE_TIME_FORMAT ));
			//叉车司机工号
			columnList.add(trayscan.getForkliftDriverCode()==null?"":trayscan.getForkliftDriverCode());
			//叉车司机姓名
			columnList.add(trayscan.getForkliftDriverName()==null?"":trayscan.getForkliftDriverName());
			//叉车司机部门
			columnList.add(trayscan.getForkliftDepartment()==null?"":trayscan.getForkliftDepartment());
			//绑定人工号
			columnList.add(trayscan.getBindingCode()==null?"":trayscan.getBindingCode());
			//绑定人姓名
			columnList.add(trayscan.getBindingName()==null?"":trayscan.getBindingName());
			//绑定人部门
			columnList.add(trayscan.getBindingDept()==null?"":trayscan.getBindingDept());
			//任务生成时间
			columnList.add(DateUtils.convert(trayscan.getTraytaskCreatTime(),DateUtils.DATE_TIME_FORMAT));
			//叉车票数
			columnList.add(trayscan.getForkliftCount().toString());
			//任务类型
			//columnList.add(trayscan.getTrayscanType());
			rowList.add(columnList);
		}
		
		String[] rowHeaders={
				"托盘任务编号",
				"扫描状态",
				"叉车扫描时间",
				"叉车司机工号",
				"叉车司机姓名",
				"叉车司机部门",
				"绑定人工号",
				"绑定人姓名",
				"绑定人部门",
				"绑定托盘任务时间",
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
	    exportSetting.setSheetName("托盘扫描任务列表");
	    //设置sheet行数
	    exportSetting.setSize(trayScanList.size() + 1);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    //获取输入流
	    InputStream excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);

        //文件名
        String fileName = null;
		try {
			fileName = URLEncoder.encode("CCGZL", "UTF-8");
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
	 * @function:导出托盘扫描任务
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-2 16:10:59
	 * @return List
	 * @exception 无
	 * @param queryHandOverBillConditionDto 查询条件dto
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITrayScanService#getTrayScanTaskInputStream(com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getTrayScanTaskInputStreamExpress(
			QueryTrayScanConditionDto queryTrayScanConditiondto) {
		String orgCode = this.querySuperiorOrgCode();
		queryTrayScanConditiondto.setCurrentDept(this.getCenterCode(orgCode));
		List<TrayScanExpressEntity> trayScanList = queryTrayScanListExpress(queryTrayScanConditiondto,LoadConstants.SONAR_NUMBER_10000,0);
		List<List<String>> rowList = new ArrayList<List<String>>();
		//如果查询结果为空，则导出空文件
		if(CollectionUtils.isEmpty(trayScanList)){
			List<String> columnList = new ArrayList<String>();
			rowList.add(columnList);
		}
		for(TrayScanExpressEntity trayscan : trayScanList){
			List<String> columnList = new ArrayList<String>();
			//托盘任务号
			columnList.add(trayscan.getTaskNo());
			//托盘状态
			if(StringUtils.endsWith(trayscan.getStatu(),"SCANNED")){
				columnList.add("已扫描");
			}else if(StringUtils.endsWith(trayscan.getStatu(),"UNSCAN")){
				columnList.add("未扫描");
			}else{
				columnList.add("手动拉车");
			}
			
			//叉车扫描时间
			columnList.add(trayscan.getTrayscanTime()==null?"":DateUtils.convert(trayscan.getTrayscanTime(),DateUtils.DATE_TIME_FORMAT ));
			//叉车司机工号
			columnList.add(trayscan.getForkliftDriverCode()==null?"":trayscan.getForkliftDriverCode());
			//叉车司机姓名
			columnList.add(trayscan.getForkliftDriverName()==null?"":trayscan.getForkliftDriverName());
			//叉车司机部门
			columnList.add(trayscan.getForkliftDepartment()==null?"":trayscan.getForkliftDepartment());
			//绑定人工号
			columnList.add(trayscan.getBindingCode()==null?"":trayscan.getBindingCode());
			//绑定人姓名
			columnList.add(trayscan.getBindingName()==null?"":trayscan.getBindingName());
			//绑定人部门
			columnList.add(trayscan.getBindingDept()==null?"":trayscan.getBindingDept());
			//任务生成时间
			columnList.add(DateUtils.convert(trayscan.getTraytaskCreatTime(),DateUtils.DATE_TIME_FORMAT));
			//叉车票数
			columnList.add(trayscan.getForklifrCount().toString());
			//任务类型
			//columnList.add(trayscan.getTrayscanType());
			rowList.add(columnList);
		}
		
		String[] rowHeaders={
				"托盘任务编号",
				"扫描状态",
				"叉车扫描时间",
				"叉车司机工号",
				"叉车司机姓名",
				"叉车司机部门",
				"绑定人工号",
				"绑定人姓名",
				"绑定人部门",
				"绑定托盘任务时间",
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
	    exportSetting.setSheetName("托盘扫描任务列表");
	    //设置sheet行数
	    exportSetting.setSize(trayScanList.size() + 1);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    //获取输入流
	    InputStream excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);

        //文件名
        String fileName = null;
		try {
			fileName = URLEncoder.encode("CCGZL", "UTF-8");
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
	
	/**  零担
	 * @function:查询托盘扫描任务叉车总票数
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-5 12:23:34
	 * @return List<TrayScanDto>
	 * @exception 无
	 * @param queryTrayScanConditiondto
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITrayScanService#queryWaybillByTaskNo(com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto)
	 */
	@Override
	public List<TrayScanDto> queryTrayScanSummary(
			QueryTrayScanConditionDto queryTrayScanConditiondto) {
		//获取当前部门
		String orgCode = this.querySuperiorOrgCode();
		//设置部门属性到查询对象中
		queryTrayScanConditiondto.setCurrentDept(this.getCenterCode(orgCode));
		//调用用dao查询叉车总票数
		List<TrayScanDto> queryTrayScanSummary = trayScanDao.queryTrayScanSummary(queryTrayScanConditiondto);
		return queryTrayScanSummary;
	}
	
	/**  快递
	 * @function:查询托盘扫描任务叉车总票数
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-5 12:23:34
	 * @return List<TrayScanDto>
	 * @exception 无
	 * @param queryTrayScanConditiondto
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITrayScanService#queryWaybillByTaskNo(com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto)
	 */
	@Override
	public List<TrayScanDto> queryTrayScanSummaryExpress(
			QueryTrayScanConditionDto queryTrayScanConditiondto) {
		//调用用dao查询叉车总票数
		List<TrayScanDto> queryTrayScanSummary = new ArrayList<TrayScanDto>();
		TrayScanDto trayScanDto = new TrayScanDto();
		//获取数据
		String resultData=trayScanDao.queryTrayScanListExpress(queryTrayScanConditiondto,LoadConstants.SONAR_NUMBER_10000,0);
		//判断是否为空
		if(resultData != null && !"".equals(resultData)){
			//字符串转化成JSON格式
			JSONObject resultJson=JSONObject.fromObject(resultData);
			Integer total=0;
			//判断是否有数据
			if(resultJson.getString("data") != null && !"null".equals(resultJson.getString("data"))){
				for (int i = 0; i < resultJson.getJSONArray("data").size(); i++) {
					total += (resultJson.getJSONArray("data").getJSONObject(i).getString("forkliftTicketsCount").equals("null") ? 0:resultJson.getJSONArray("data").getJSONObject(i).getInt("forkliftTicketsCount"));
				}
				System.out.println("统计的数据="+total);
				trayScanDto.setTotalCount(total);
				queryTrayScanSummary.add(trayScanDto);
			}
		}
		
		return queryTrayScanSummary;
	}
	
	/**
	 * 根据部门code查询出当前部门下(包括当前部门)所有的子部门
	 * @author foss-heyongdong
	 * @date 2013年8月24日9:04:50
	 */
	private List<String> getChildDept(String orgCode) {
		if(StringUtils.isEmpty(orgCode)) {
			return null;
		}
		//根据部门编码获取所属及下属部门信息 此部门及下属的所有部门。
		List<OrgAdministrativeInfoEntity> orgAdministrativeInfos = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoEntityAllSubByCode(orgCode);
		List<String> orgCodes = new ArrayList<String>(orgAdministrativeInfos.size());
		for(OrgAdministrativeInfoEntity orgAdministrativeInfo : orgAdministrativeInfos) {
			orgCodes.add(orgAdministrativeInfo.getCode());
		}
		//返回部门code
		return orgCodes;
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
		if(null != unloadOrg){
			transferCenterCode = unloadOrg.getCode();
		}
		return transferCenterCode;
	}
	
	/**
	 *提供装车接口，按运单，流水，外场编码，判断是否已绑定托盘兵扫描
	 *@author 045923 205109-foss-zenghaibin
	 *@date 2014/11/29 15:55
	 *@param wayBillNo seriano outFieldCode
	 ***/
	@Override
	public Long queryTrayScanTaskDtailCount(String wayBillNo,String seriano,String outFieldCode){
		if(StringUtils.isBlank(wayBillNo.trim())||StringUtils.isBlank(seriano.trim())||StringUtils.isBlank(outFieldCode.trim())){
			throw new TfrBusinessException("运单、流水号或外场编码为空");
		}
		Long count=0L;
		HashMap map=new HashMap();
		map.put("wayBillNo", wayBillNo);
		map.put("seriano", seriano);
		map.put("outFieldCode", outFieldCode);
		count=trayScanDao.queryTrayScanTaskDtailCount(map);
		if(count==0L){
			count=trayScanDao.queryOfflienTrayScanTaskDtailCount(map);
		}
		return count;
	}
}
