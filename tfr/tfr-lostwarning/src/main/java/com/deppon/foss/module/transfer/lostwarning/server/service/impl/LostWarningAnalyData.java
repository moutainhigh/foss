package com.deppon.foss.module.transfer.lostwarning.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearless;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseDto;
import com.deppon.foss.module.transfer.lostwarning.api.server.dao.ILostWarningDataDao;
import com.deppon.foss.module.transfer.lostwarning.api.server.define.LostWarningConstant;
import com.deppon.foss.module.transfer.lostwarning.api.server.domain.LostWarningDataEntity;
import com.deppon.foss.module.transfer.lostwarning.api.server.domain.WayBillSerialInfoEntity;
import com.deppon.foss.module.transfer.lostwarning.api.server.service.ILostWarningDataService;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostWarningDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostWarningLogDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostWarningTempDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.TransferMotorcadeDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.WayBillInfoDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.WayBillPkgInfoDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.utils.Utils;
import com.deppon.foss.util.UUIDUtils;

/**
 * 分析丢货数据
 * 
 * 项目名称：tfr-lostwarning
 * 
 * 类名称：LostWarningAnalyData
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-6-13 上午8:41:48
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class LostWarningAnalyData {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LostWarningAnalyData.class);
	
	private ILostWarningDataDao lostWarningDataDao;
	
	private ILostWarningDataService lostWarningDataService;
	
	//综合管理 组织信息 Service
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	//查询部门信息接口 
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	//查询员工信息接口
	private IEmployeeService employeeService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public void setLostWarningDataDao(ILostWarningDataDao lostWarningDataDao) {
		this.lostWarningDataDao = lostWarningDataDao;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	public void setLostWarningDataService(
			ILostWarningDataService lostWarningDataService) {
		this.lostWarningDataService = lostWarningDataService;
	}
	
	/**
	 * @Description: 全业务丢货数据公共处理模块(属性：流水号、是否整票丢失、丢货件数、丢货包装信息、车牌号、交接单、包号、建包部门编码、建包部门名称、
	 * 										解包部门编码、解包部门名称、责任货区、上报场景、系统上报ID、处理时间)
	 * @date 2015-6-18 上午8:41:52   
	 * @author 263072 
	 * @param bean 丢货信息
	 * @param list  丢货流水信息
	 * @param wayBillInfo  运单信息
	 * @return
	 */
	public LostWarningDataEntity commonAnalyData(LostWarningDataEntity bean,List<LostWarningDto> list,WayBillInfoDto wayBillInfo){
		//将运单表信息属性值复制到丢货数据对象中
		bean = (LostWarningDataEntity)Utils.copyObjProperties(wayBillInfo, bean);
		
		//判断是否为整票丢失
		if((list.size()+"").equals(bean.getGoodsNum())){
			bean.setIsWholeTicket("2");//整票丢失 1：否 2：是
		}else{
			bean.setIsWholeTicket("1");
		}
		
		bean.setLoseNum(list.size()+"");// 丢货件数
		bean.setLoseGoodsPackage(bean.getGoodsPackage());//丢货包装信息
		
		//按运单流水号存放流水号丢货信息
		Map<String,WayBillSerialInfoEntity> serailMap = new HashMap<String,WayBillSerialInfoEntity>();
		
		//流水号信息、责任货区信息、车辆信息、交接单信息
		for(LostWarningDto lwDto:list){
			WayBillSerialInfoEntity serailBean = new WayBillSerialInfoEntity();
			serailBean.setFlowCode(lwDto.getSerialNo());
			//货区信息 按照  "货区编码-货区名称" 格式拼接
			if(!Utils.isStrNull(lwDto.getRespAreaCode())&&!"N/A".equals(lwDto.getRespAreaCode())){
				//sonar-352203-set值抽取为方法
				initSerailBeanSetRespAreaName(lwDto, serailBean);
			}
			
			if(!Utils.isStrNull(lwDto.getCarCode())){
				serailBean.setCarCode(lwDto.getCarCode());
			}
			
			if(!Utils.isStrNull(lwDto.getTransferBill())){
				serailBean.setTransferBill(lwDto.getTransferBill());
			}
			
			serailMap.put(lwDto.getSerialNo(), serailBean);
		}
		
		
		bean.setSysAutoPkId(lostWarningDataDao.getSeqNextVal()+"");//系统上报ID
		bean.setRepTime(sdf.format(new Date()));//处理时间
		
		//判断是否为快递 Y是 N否
		if("N".equals(wayBillInfo.getIs_express())){
			bean.setRepScene("1");//上报场景 1：零担 2：快递
		}else{
			bean.setRepScene("2");//上报场景 1：零担 2：快递
			
			//当运单为快递时才会有可能有打包记录
			//遍历结果集，拼接包号、建包部门信息 、解包部门信息
			List<WayBillPkgInfoDto> pkgList = lostWarningDataDao.searchWayBillPkgList(wayBillInfo.getWaybillNum());
			if(pkgList!=null&&pkgList.size()>0){
				for(WayBillPkgInfoDto pkgInfo:pkgList){
					if(!serailMap.containsKey(pkgInfo.getSerialNo())){
						continue;
					}
					WayBillSerialInfoEntity serailBean=serailMap.get(pkgInfo.getSerialNo());
					initSerialEntityPkgInfo(pkgInfo, serailBean);
				}
				
			}
		}
		
		List<WayBillSerialInfoEntity> flowCodeList = new ArrayList<WayBillSerialInfoEntity>();
		for (Map.Entry<String, WayBillSerialInfoEntity> entry : serailMap.entrySet()) {
			flowCodeList.add(entry.getValue());
		}
		bean.setFlowCodeList(flowCodeList);
		return bean;
	}
	/**
	 * @param lwDto
	 * @param serailBean
	 */
	private void initSerailBeanSetRespAreaName(LostWarningDto lwDto,
			WayBillSerialInfoEntity serailBean) {
		//判断是否为虚拟货区 OPERATED_RETURN_CODE WHOLE_GOODS_AREA BULK_GOODS_AREA
		if("OPERATED_RETURN_CODE".equals(lwDto.getRespAreaCode())){
			serailBean.setRespAreaName(lwDto.getRespAreaCode()+"-返货开单虚拟货区");
		}else if("WHOLE_GOODS_AREA".equals(lwDto.getRespAreaCode())){
			serailBean.setRespAreaName(lwDto.getRespAreaCode()+"-整车虚拟货区");
		}else if("BULK_GOODS_AREA".equals(lwDto.getRespAreaCode())){
			serailBean.setRespAreaName(lwDto.getRespAreaCode()+"-散货虚拟货区");
		}else{
			if(!Utils.isStrNull(lwDto.getRespAreaName())){
				serailBean.setRespAreaName(lwDto.getRespAreaCode()+"-"+lwDto.getRespAreaName());
			}
			else{
				serailBean.setRespAreaName(lwDto.getRespAreaCode());
			}
		}
	}
	
    /**
	 * @Description: 公共处理方法-单责，且所有流水号同一责任部门信息  ：用于获取责任部门信息、责任人信息、责任事业部信息 (部门负责人为责任人)、短信通知对象信息
	 * @date 2015-7-3 下午4:40:01   
	 * @author 263072 
	 * @param bean
	 * @param deptCode 责任部门编码
	 * @return
	 */
	public LostWarningDataEntity commGetResponsibilityInfo(LostWarningDataEntity bean,String deptCode){
		//责任部门名称
		String respDeptName = "";
		//责任人工号 
		String respEmpCode = "";
		//责任人姓名
		String respEmpName = "";
		//责任事业部编码
		String respDivisionCode = "";
		//责任事业部名称
		String respDivisionName = "";
		//查询部门信息
		OrgAdministrativeInfoEntity org = getDepartmentInfoBycode(deptCode);
		if(org!=null){
			respDeptName = org.getName();//责任部门名称
			respEmpCode = org.getPrincipalNo();//责任人工号 
			//查询员工姓名
			EmployeeEntity employee = getEmployeeInfoBycode(org.getPrincipalNo());
			if(employee!=null){
				respEmpName = employee.getEmpName();//责任人名称
			}
			//查询责任事业部信息
			OrgAdministrativeInfoEntity division = getDivisionInfoBycode(deptCode);
			if(division!=null){
				respDivisionCode = division.getCode();//责任事业部编码
				respDivisionName = division.getName();//责任事业部名称
			}
		}
		for(WayBillSerialInfoEntity serialEntity:bean.getFlowCodeList()){
			serialEntity.setRespDeptCode(deptCode);
			serialEntity.setRespDeptName(respDeptName);
			serialEntity.setRespEmpcode(respEmpCode);
			serialEntity.setRespEmpname(respEmpName);
			serialEntity.setRespDivisionCode(respDivisionCode);
			serialEntity.setRespDivisionName(respDivisionName);
		}
		bean.setShortMessageCodes(respEmpCode);
		bean.setShortMessageNames(respEmpName);
		return bean;
	}
	
	/**
	 * @Description: 公共处理方法-双责部门查询  ：用于获取责任部门信息、责任人信息、责任事业部信息 (部门负责人为责任人)、短信通知对象信息
	 * @date 2015-7-15 上午9:38:13   
	 * @author 263072 
	 * @param deptOrg1 必填项
	 * @param deptOrg2 可为空
	 * @return
	 */
	public WayBillSerialInfoEntity commGetDoubleResponsibilityInfo(WayBillSerialInfoEntity serialEntity,
			OrgAdministrativeInfoEntity deptOrg1,OrgAdministrativeInfoEntity deptOrg2){
		//责任部门编码
		StringBuffer respDeptCode = new StringBuffer();
		//责任部门名称
		StringBuffer respDeptName = new StringBuffer();
		//责任人工号
		StringBuffer respEmpCode = new StringBuffer();
		//责任人姓名
		StringBuffer respEmpName = new StringBuffer();
		//责任事业部编码
		StringBuffer respDivisionCode = new StringBuffer();
		//责任事业部名称
		StringBuffer respDivisionName = new StringBuffer();
		
		/****责任部门1属性****/
		if(deptOrg1!=null){
			respDeptCode.append(deptOrg1.getCode());
			respDeptName.append(deptOrg1.getName());
			respEmpCode.append(deptOrg1.getPrincipalNo());
			//查询员工姓名
			EmployeeEntity employee1 = getEmployeeInfoBycode(deptOrg1.getPrincipalNo());
			if(employee1!=null){
				respEmpName.append(employee1.getEmpName());//责任人名称
			}
			//查询责任事业部信息
			OrgAdministrativeInfoEntity division1 = getDivisionInfoBycode(deptOrg1.getCode());
			if(division1!=null){
				respDivisionCode.append(division1.getCode());//责任事业部编码
				respDivisionName.append(division1.getName());//责任事业部名称
			}
		}
		
		/****责任部门2属性****/
		if(deptOrg2!=null){
			respDeptCode.append(",").append(deptOrg2.getCode());
			respDeptName.append(",").append(deptOrg2.getName());
			respEmpCode.append(",").append(deptOrg2.getPrincipalNo());
			
			//查询员工姓名
			EmployeeEntity employee2 = getEmployeeInfoBycode(deptOrg2.getPrincipalNo());
			if(employee2!=null){
				respEmpName.append(",").append(employee2.getEmpName());//责任人名称
			}
			//查询责任事业部信息
			OrgAdministrativeInfoEntity division2 = getDivisionInfoBycode(deptOrg2.getCode());
			if(division2!=null){
				respDivisionCode.append(",").append(division2.getCode());//责任事业部编码
				respDivisionName.append(",").append(division2.getName());//责任事业部名称
			}
		}
		
		serialEntity.setRespDeptCode(respDeptCode.toString());
		serialEntity.setRespDeptName(respDeptName.toString());
		serialEntity.setRespEmpcode(respEmpCode.toString());
		serialEntity.setRespEmpname(respEmpName.toString());
		serialEntity.setRespDivisionCode(respDivisionCode.toString());
		serialEntity.setRespDivisionName(respDivisionName.toString());
		return serialEntity;
	}
	
	/**
	 * @Description: 根据部门编码获取部门信息
	 * @date 2015-7-2 下午3:55:32   
	 * @author 263072 
	 * @param code
	 * @return
	 */
	public OrgAdministrativeInfoEntity getDepartmentInfoBycode(String code){
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.
				queryOrgAdministrativeInfoByCode(code);
		return org;
	}
	
	/**
	 * @Description: 根据工号查询员工信息
	 * @date 2015-7-2 下午4:15:06   
	 * @author 263072 
	 * @param code
	 * @return
	 */
	public EmployeeEntity getEmployeeInfoBycode(String code){
		EmployeeEntity bean = employeeService.
				queryEmployeeByEmpCode(code);
		return bean;
	}
	
	/**
	 * @Description: 根据组织编码获取责任事业部信息
	 * @date 2015-7-2 下午4:24:48   
	 * @author 263072 
	 * @param code
	 * @return
	 */
	public OrgAdministrativeInfoEntity getDivisionInfoBycode(String code){
		List<String> bizTypesList = new ArrayList<String>();
		//事业部
		bizTypesList.add(BizTypeConstants.ORG_DIVISION);
		//查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoIncludeSelfByCode(code, bizTypesList);
		
		return orgAdministrativeInfoEntity;
	}
	
	/**
	 * @Description: 查询日志记录去除已上报丢货数据
	 * @date 2015-11-28 下午8:00:19   
	 * @author 263072 
	 * @param wayBillNo
	 * @param list
	 * @return
	 */
	public List<LostWarningDto> commonRemoveAlUploadData(String wayBillNo,List<LostWarningDto> list){
		//查询成功上报且未处理的丢货信息
		List<LostWarningLogDto> warningLogList = lostWarningDataDao.queryUploadLogSuccByWayBill(wayBillNo);
		for(LostWarningLogDto logDto:warningLogList){
			for(int i=0;i<list.size();i++){
				LostWarningDto warningData = list.get(i);
				if(StringUtils.equals(logDto.getSerialNo(),warningData.getSerialNo())){
					list.remove(i);
					i--;
				}
			}
		}
		
		if(list.size()==0){
			return list;
		}
		
		/******* 接口查询QMS是否重复上报（包含人工处理部分）  *******/
    	String alreadyReportGoods = FossToMcewService.getInstatce().queryReportGoodsByBill(wayBillNo);
    	if(!"".equals(alreadyReportGoods)){
    		try{
        		JSONObject jsonObj = JSONObject.fromObject(alreadyReportGoods); 
        		String alreadyReportSerial = jsonObj.get("flowCode").toString();
        		for(String serialNo:alreadyReportSerial.split(",")){
        			if(serialNo==null || "".equals(serialNo)){
        				continue;
        			}
        				for(int i=0;i<list.size();i++){
        					LostWarningDto warningData = list.get(i);
        					if(StringUtils.equals(serialNo,warningData.getSerialNo())){
        						//已存在上报记录，排除该流水信息
        						list.remove(i);
        						i--;
        					}
        				}
//        			}
            	}
    		}
    		catch(Exception e){
    			return null;
    			//上报失败
        		//lostWarningDataService.saveUploadFalseInfo(bean, alreadyReportGoods,taskData.getRepType(),"");
    		}
    	}
		return list;
	}
	
	/**
	 * @Description: 分析出发库存丢货数据
	 * @date 2015-6-13 上午9:01:25   
	 * @author 263072 
	 * @return
	 */
	public LostWarningDataEntity analyWarningData_startDtpStock(LostWarningTempDto dto){
		LostWarningDataEntity bean = new LostWarningDataEntity();
		//1.根据运单号 查询快车或慢车运输方式中 出发部门库存的丢货信息
		List<LostWarningDto> list = lostWarningDataDao.searchStartDptLostData_Fast(dto.getWayBillNo());
		if(list==null||list.size()==0){
			list = lostWarningDataDao.searchStartDptLostData_Slow(dto.getWayBillNo());
			if(list==null||list.size()==0){
				return null;
			}
		}
		//2.查询运单表信息
		WayBillInfoDto wayBillInfo = lostWarningDataDao.searchWayBillInfo(dto.getWayBillNo());
		if(wayBillInfo==null){
			return null;
		}
		
		try{
			/*** 去除该运单已上报过的丢货货物 2015-11-28 20:15:28 ***/
			list = commonRemoveAlUploadData(dto.getWayBillNo(),list);
			if(list==null||list.size()==0){
				return null;
			}
			
			//调用公共处理模块
			bean = commonAnalyData(bean, list, wayBillInfo);
			if(bean==null){
				return null;
			}
			
			//业务环节编码 
			String activeCode = "";
			//判断是否为集中接货 Y是 N否
			if("N".equals(wayBillInfo.getIs_express())){
				//零担
				if("N".equals(wayBillInfo.getPickup_centralized())){
					activeCode = "1";//业务环节编码  1: 客户-营业部非集中接货
				}else{
					activeCode = "2";//业务环节编码  2：客户-营业部上门发货
				}
				
			}else{
				//快递
				if("N".equals(wayBillInfo.getPickup_centralized())){
					activeCode= "2";//业务环节编码  2：客户上门发货少货
				}else{
					activeCode = "1";//业务环节编码 1：快递员-营业部卸车
				}
			}
			for(WayBillSerialInfoEntity serialEntity:bean.getFlowCodeList()){
				serialEntity.setActiveCode(activeCode);
				serialEntity.setChannelName("营业部");
			}
			
			//调用公共处理模块：责任部门等信息
			bean = commGetResponsibilityInfo(bean, wayBillInfo.getTakeOverDeptCode());
			bean.setRepType("1");//上报方式  1、出发库存丢货上报
			//以下信息在该业务环节下暂定为空 ：车牌号、 装车部门信息、 装车部门信息、 交接单号
		}
		catch (Exception e) {
			bean = null;
			e.printStackTrace();
		}
		
		return bean;
	}
	
	/**
	 * @Description: 集中接货丢货预警处理
	 * @date 2015-6-16 下午6:24:42   
	 * @author 263072 
	 * @param dto
	 * @return
	 */
	public LostWarningDataEntity analyWarningData_JZReceive(LostWarningTempDto dto){
		LostWarningDataEntity bean = new LostWarningDataEntity();
		//1.根据运单号 查询
		List<LostWarningDto> list = lostWarningDataDao.searchJZReceiveLostData(dto.getWayBillNo());
		if(list==null||list.size()==0){
			return null;
		}
		//2.查询运单表信息
		WayBillInfoDto wayBillInfo = lostWarningDataDao.searchWayBillInfo(dto.getWayBillNo());
		if(wayBillInfo==null){
			return null;
		}
		try{
			/**判断如果为快递 则中断该流程 2015-11-25 21:02:10**/
			if("Y".equals(wayBillInfo.getIs_express())){
				return null;
			}
			
			/*** 去除该运单已上报过的丢货货物 2015-11-28 20:15:28 ***/
			list = commonRemoveAlUploadData(dto.getWayBillNo(),list);
			if(list==null||list.size()==0){
				return null;
			}
			//添加过滤条件如果该运单有装车扫描、卸车扫描、单票入库、清仓扫描则不进行上报
			List<String> listJZReceive = lostWarningDataDao.searchSanAndSignleInStockJZReceive(dto.getWayBillNo());
			if(listJZReceive==null||listJZReceive.size()==0){
				return null;
			}        		
			//调用公共处理模块
			bean = commonAnalyData(bean, list, wayBillInfo);
			
			//短信通知工号
			StringBuffer shortMessageCodeSBuf = new StringBuffer();
			//短信通知姓名
			StringBuffer shortMessageNameSBuf = new StringBuffer();
			
			for(WayBillSerialInfoEntity serialEntity:bean.getFlowCodeList()){
				for(LostWarningDto lwDto:list){
					if(StringUtils.equals(serialEntity.getFlowCode(),lwDto.getSerialNo())){
						
						serialEntity.setUnloadingDeptCode(lwDto.getUnloadingDeptCode());
						serialEntity.setUnloadingDeptName(lwDto.getUnloadingDeptName());
						//查询部门信息
						OrgAdministrativeInfoEntity unloadingDept = getDepartmentInfoBycode(lwDto.getUnloadingDeptCode());
						if(unloadingDept==null){
							break;
						}
						
						//查询车队信息
						OrgAdministrativeInfoEntity motorOrg = null;
						if(StringUtils.isNotEmpty(lwDto.getRespDeptCode())){
//							TransferMotorcadeDto motorCade = LostWarningConstant.motorCadeMap.get(lwDto.getUnloadingDeptCode());
							motorOrg = getDepartmentInfoBycode(lwDto.getRespDeptCode()/*motorCade.getMotorcadeCode()*/);
						}
						serialEntity = commGetDoubleResponsibilityInfo(serialEntity,unloadingDept,motorOrg);
						
						shortMessageCodeSBuf.append(serialEntity.getRespEmpcode()).append(",");
						shortMessageNameSBuf.append(serialEntity.getRespEmpname()).append(",");
						
						
						String transferChannelName = getTransferServiceChannel(lwDto.getUnloadingDeptCode());
						if(!Utils.isStrNull(transferChannelName)){
							serialEntity.setChannelName("车队/"+transferChannelName);//业务渠道名称
						}
						break;
					}
				}
				if(shortMessageCodeSBuf.length()>0){
					//短信通知对象工号
					bean.setShortMessageCodes(shortMessageCodeSBuf.toString().substring(0,shortMessageCodeSBuf.length()-1));
				}
				if(shortMessageNameSBuf.length()>0){
					//短信通知对象姓名
					bean.setShortMessageNames(shortMessageNameSBuf.toString().substring(0,shortMessageNameSBuf.length()-1));
				}
				serialEntity.setActiveCode("23");//业务环节编码 
				serialEntity.setChannelName("车队/外场");//业务渠道名称
			}
			bean.setRepType("8");//上报方式  8、集中接货
			//以下信息在该环节为空 : 责任货区名称 装车部门信息
		}
		catch (Exception e) {
			bean = null;
			e.printStackTrace();
		}
		return bean;
	}
	
	
	/**
	 * @Description: 运输丢货预警处理
	 * @date 2015-6-19 上午11:26:23   
	 * @author 263072 
	 * @param dto
	 * @return
	 */
	public LostWarningDataEntity analyWarningData_transfer(LostWarningTempDto dto){
		LostWarningDataEntity bean = new LostWarningDataEntity();
 		//1.根据运单号 查询快车或慢车运输方式中 出发部门库存的丢货信息
		List<LostWarningDto> list = lostWarningDataDao.searchTransferLostData(dto.getWayBillNo());
		if(list==null||list.size()==0){
			return null;
		}
		//2.查询运单表信息
		WayBillInfoDto wayBillInfo = lostWarningDataDao.searchWayBillInfo(dto.getWayBillNo());
		if(wayBillInfo==null){
			return null;
		}
		try{
			/*** 去除该运单已上报过的丢货货物 2015-11-28 20:15:28 ***/
			list = commonRemoveAlUploadData(dto.getWayBillNo(),list);
			if(list==null||list.size()==0){
				return null;
			}
			//添加过滤条件如果该运单有装车扫描、卸车扫描、单票入库、清仓扫描则不进行上报
		    if(lostWarningDataDao.isSanAndSignleInStockTransfer(dto.getWayBillNo()))
			{
				return null;
			} 
			//调用公共处理模块
			bean = commonAnalyData(bean, list, wayBillInfo);
			
			//短信通知工号
			StringBuffer shortMessageCodeSBuf = new StringBuffer();
			//短信通知姓名
			StringBuffer shortMessageNameSBuf = new StringBuffer();
			
			/**
			 * 组装 装车、卸车部门信息、业务渠道、业务环节编码、责任部门、责任人信息
			 * 该环节责任部门为装车部门和车队
			 */
			for(WayBillSerialInfoEntity serialEntity:bean.getFlowCodeList()){
				//业务渠道名称
				serialEntity.setChannelName("车队");
				for(LostWarningDto lwDto:list){
					//sonar-352203
					if(!StringUtils.equals(serialEntity.getFlowCode(),lwDto.getSerialNo())){
						continue;
					}
						//装卸车部门信息
						serialEntity.setLoadingDeptCode(lwDto.getLoadingDeptCode());
						serialEntity.setLoadingDeptName(lwDto.getLoadingDeptName());
						serialEntity.setUnloadingDeptCode(lwDto.getUnloadingDeptCode());
						serialEntity.setUnloadingDeptName(lwDto.getUnloadingDeptName());
						
						//查询部门信息
						OrgAdministrativeInfoEntity loadingDept = getDepartmentInfoBycode(lwDto.getLoadingDeptCode());
						OrgAdministrativeInfoEntity unloadingDept = getDepartmentInfoBycode(lwDto.getUnloadingDeptCode());
						if(loadingDept==null||unloadingDept==null){
							break;
						}
						/**** 根据装车、卸车部门属性判断业务环节 （零担三种，快递五种类型）  *****/
						if("N".equals(wayBillInfo.getIs_express())){
							if("Y".equals(loadingDept.getSalesDepartment())&&"Y".equals(unloadingDept.getTransferCenter())){
								serialEntity.setActiveCode("4");//4：营业部-外场短途运输
							}else if("Y".equals(loadingDept.getTransferCenter())&&"Y".equals(unloadingDept.getTransferCenter())){
								serialEntity.setActiveCode("13");//13：外场-外场长途运输
							}else if("Y".equals(loadingDept.getTransferCenter())&&"Y".equals(unloadingDept.getSalesDepartment())){
								serialEntity.setActiveCode("9");//9：外场-营业部短途运输
							}
						}else{
						    if("Y".equals(loadingDept.getExpressBranch())&&"Y".equals(unloadingDept.getSalesDepartment())){
						    	serialEntity.setActiveCode("31");//31：分部-营业部短途运输
						    }else if("Y".equals(loadingDept.getTransferCenter())&&"Y".equals(unloadingDept.getExpressBranch())){
								serialEntity.setActiveCode("30");//30：外场-分部短途运输
							}else if("Y".equals(loadingDept.getTransferCenter())&&"Y".equals(unloadingDept.getSalesDepartment())){
								serialEntity.setActiveCode("29");//29：外场-营业部短途运输
							}else if("Y".equals(loadingDept.getTransferCenter())&&"Y".equals(unloadingDept.getTransferCenter())){
								serialEntity.setActiveCode("28");//28：外场-外场长途运输
							}else if("Y".equals(loadingDept.getSalesDepartment())&&"Y".equals(unloadingDept.getTransferCenter())){
								serialEntity.setActiveCode("27");//27：营业部-外场短途运输
							}
						}
						
						/**** 该环节责任部门为：装车部门和所服务的车队  
						 * 责任部门为  装车部门和卸车部门  2015-11-25 22:27:02
						 * ****/
						//查询车队信息
//						OrgAdministrativeInfoEntity motorOrg = null;
//						if(LostWarningConstant.motorCadeMap.containsKey(lwDto.getLoadingDeptCode())){
//							TransferMotorcadeDto motorCade = LostWarningConstant.motorCadeMap.get(lwDto.getLoadingDeptCode());
//							motorOrg = getDepartmentInfoBycode(motorCade.getMotorcadeCode());
//						}
						serialEntity = commGetDoubleResponsibilityInfo(serialEntity,loadingDept,unloadingDept);
						
						shortMessageCodeSBuf.append(serialEntity.getRespEmpcode()).append(",");
						shortMessageNameSBuf.append(serialEntity.getRespEmpname()).append(",");
						break;
//					}
				}
				
			}
			
			if(shortMessageCodeSBuf.length()>0){
				//短信通知对象工号
				bean.setShortMessageCodes(shortMessageCodeSBuf.toString().substring(0,shortMessageCodeSBuf.length()-1));
			}
			if(shortMessageNameSBuf.length()>0){
				//短信通知对象姓名
				bean.setShortMessageNames(shortMessageNameSBuf.toString().substring(0,shortMessageNameSBuf.length()-1));
			}
			bean.setRepType("2");//2、运输丢货
			//以下信息在该环节为空 责任货区
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * @Description: 已到达丢货预警处理 超时未完成卸车操作， 零担+72H  快递+36H  
	 * @date 2015-6-24 上午10:04:58   
	 * @author 263072 
	 * @param dto
	 * @return
	 */
	public LostWarningDataEntity analyWarningData_alreadyArrive(LostWarningTempDto dto){
		LostWarningDataEntity bean = new LostWarningDataEntity();
		//1.根据运单号 查询快车或慢车运输方式中 出发部门库存的丢货信息
		
		List<LostWarningDto> list = lostWarningDataDao.searchAlreadyArriveLostData(dto.getWayBillNo());
		if(list==null||list.size()==0){
			return null;
		}
		//2.查询运单表信息
		WayBillInfoDto wayBillInfo = lostWarningDataDao.searchWayBillInfo(dto.getWayBillNo());
		if(wayBillInfo==null){
			return null;
		}
		try{
			/*** 去除该运单已上报过的丢货货物 2015-11-28 20:15:28 ***/
			list = commonRemoveAlUploadData(dto.getWayBillNo(),list);
			if(list==null||list.size()==0){
				return null;
			}
			//添加过滤条件如果该运单有装车扫描、卸车扫描、单票入库、清仓扫描则不进行上报
			if(lostWarningDataDao.isSanAndSignleInStockArrive(dto.getWayBillNo()))
			{
				return null;
			}
			//调用公共处理模块
			bean = commonAnalyData(bean, list, wayBillInfo);
			
			//短信通知工号
			StringBuffer shortMessageCodeSBuf = new StringBuffer();
			//短信通知姓名
			StringBuffer shortMessageNameSBuf = new StringBuffer();
			
			
			/**
			 * 装车、卸车部门信息、业务渠道、业务环节编码、责任部门、责任人信息
			 * 该环节责任部门为装车部门和卸车部门
			 */
			for(WayBillSerialInfoEntity serialEntity:bean.getFlowCodeList()){
				
				for(LostWarningDto lwDto:list){
					//sonar-352203
					if(!StringUtils.equals(serialEntity.getFlowCode(),lwDto.getSerialNo())){
						continue;
					}
						//装卸车部门信息
						serialEntity.setLoadingDeptCode(lwDto.getLoadingDeptCode());
						serialEntity.setLoadingDeptName(lwDto.getLoadingDeptName());
						serialEntity.setUnloadingDeptCode(lwDto.getUnloadingDeptCode());
						serialEntity.setUnloadingDeptName(lwDto.getUnloadingDeptName());
						
						//查询部门信息
						OrgAdministrativeInfoEntity loadingDept = getDepartmentInfoBycode(lwDto.getLoadingDeptCode());
						OrgAdministrativeInfoEntity unloadingDept = getDepartmentInfoBycode(lwDto.getUnloadingDeptCode());
					
						//外场业务渠道类型
						String transferChannelNameLoadDept = getTransferServiceChannel(lwDto.getLoadingDeptCode());
						String transferChannelNameUnloadingDept = getTransferServiceChannel(lwDto.getUnloadingDeptCode());
						
						if(loadingDept==null||unloadingDept==null){
							break;
						}
						/**** 根据装车、卸车部门属性判断业务环节 （零担三种，快递五种类型）  *****/
						if("N".equals(wayBillInfo.getIs_express())){
							//sonar-352203-set值抽取为方法
							initChannelNameActiveCode(serialEntity,
									loadingDept, unloadingDept,
									transferChannelNameLoadDept,
									transferChannelNameUnloadingDept);
						}else{
							//sonar-352203-set值抽取为方法
						    initChannelNameActiveCode1(serialEntity,
									loadingDept, unloadingDept,
									transferChannelNameLoadDept,
									transferChannelNameUnloadingDept);
						}
						
						/**** 该环节责任部门为：装车部门和卸车部门 ****/
						serialEntity = commGetDoubleResponsibilityInfo(serialEntity,loadingDept,unloadingDept);
						shortMessageCodeSBuf.append(serialEntity.getRespEmpcode()).append(",");
						shortMessageNameSBuf.append(serialEntity.getRespEmpname()).append(",");
						break;
//					}
				}
			}
			bean.setRepType("3");//3、已到达丢货上报
			if(shortMessageCodeSBuf.length()>0){
				//短信通知对象工号
				bean.setShortMessageCodes(shortMessageCodeSBuf.toString().substring(0,shortMessageCodeSBuf.length()-1));
			}
			if(shortMessageNameSBuf.length()>0){
				//短信通知对象姓名
				bean.setShortMessageNames(shortMessageNameSBuf.toString().substring(0,shortMessageNameSBuf.length()-1));
			}
			//以下信息在该环节为空：  责任货区 respArea
		}catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	/**
	 * @param serialEntity
	 * @param loadingDept
	 * @param unloadingDept
	 * @param transferChannelNameLoadDept
	 * @param transferChannelNameUnloadingDept
	 */
	private void initChannelNameActiveCode1(
			WayBillSerialInfoEntity serialEntity,
			OrgAdministrativeInfoEntity loadingDept,
			OrgAdministrativeInfoEntity unloadingDept,
			String transferChannelNameLoadDept,
			String transferChannelNameUnloadingDept) {
		if("Y".equals(loadingDept.getExpressBranch())&&"Y".equals(unloadingDept.getSalesDepartment())){
			serialEntity.setActiveCode("20");//20：分部--营业部短途卸车少货
			//业务渠道名称
			serialEntity.setChannelName("分部/营业部");
		}else if("Y".equals(loadingDept.getTransferCenter())&&"Y".equals(unloadingDept.getExpressBranch())){
			serialEntity.setActiveCode("18");//18：外场-分部短途卸车少货
			//业务渠道名称
			serialEntity.setChannelName("外场/分部");
			if(!Utils.isStrNull(transferChannelNameLoadDept)){
				serialEntity.setChannelName(transferChannelNameLoadDept+"/分部");
			}
		}else if("Y".equals(loadingDept.getTransferCenter())&&"Y".equals(unloadingDept.getSalesDepartment())){
			serialEntity.setActiveCode("22");//22：外场--营业部短途卸车少货
			//业务渠道名称
			serialEntity.setChannelName("外场/营业部");
			if(!Utils.isStrNull(transferChannelNameLoadDept)){
				serialEntity.setChannelName(transferChannelNameLoadDept+"/营业部");
			}
		}else if("Y".equals(loadingDept.getTransferCenter())&&"Y".equals(unloadingDept.getTransferCenter())){
			serialEntity.setActiveCode("6");//6：外场-外场长途卸车少货
			//业务渠道名称
			serialEntity.setChannelName("外场/外场");
			if(!Utils.isStrNull(transferChannelNameLoadDept)&&!Utils.isStrNull(transferChannelNameUnloadingDept)){
				serialEntity.setChannelName(transferChannelNameLoadDept+"/"+transferChannelNameUnloadingDept);
			}else if(Utils.isStrNull(transferChannelNameLoadDept)&&!Utils.isStrNull(transferChannelNameUnloadingDept)){
				serialEntity.setChannelName("外场/"+transferChannelNameUnloadingDept);
			}else if(!Utils.isStrNull(transferChannelNameLoadDept)&&Utils.isStrNull(transferChannelNameUnloadingDept)){
				serialEntity.setChannelName(transferChannelNameLoadDept+"/外场");
			}
		}else if("Y".equals(loadingDept.getSalesDepartment())&&"Y".equals(unloadingDept.getTransferCenter())){
			serialEntity.setActiveCode("4");//4：营业部-外场短途卸车少货
			//业务渠道名称
			serialEntity.setChannelName("营业部/外场");
			if(!Utils.isStrNull(transferChannelNameUnloadingDept)){
				serialEntity.setChannelName("营业部/"+transferChannelNameUnloadingDept);//业务渠道名称
			}
		}
	}
	/**
	 * @param serialEntity
	 * @param loadingDept
	 * @param unloadingDept
	 * @param transferChannelNameLoadDept
	 * @param transferChannelNameUnloadingDept
	 */
	private void initChannelNameActiveCode(
			WayBillSerialInfoEntity serialEntity,
			OrgAdministrativeInfoEntity loadingDept,
			OrgAdministrativeInfoEntity unloadingDept,
			String transferChannelNameLoadDept,
			String transferChannelNameUnloadingDept) {
		if("Y".equals(loadingDept.getSalesDepartment())&&"Y".equals(unloadingDept.getTransferCenter())){
			serialEntity.setActiveCode("6");//6：营业部-外场短途卸车
			//业务渠道名称
			serialEntity.setChannelName("营业部/外场");
			
			if(!Utils.isStrNull(transferChannelNameUnloadingDept)){
				serialEntity.setChannelName("营业部/"+transferChannelNameUnloadingDept);//业务渠道名称
			}
		}else if("Y".equals(loadingDept.getTransferCenter())&&"Y".equals(unloadingDept.getTransferCenter())){
			serialEntity.setActiveCode("12");//12：外场-外场长途卸车
			//业务渠道名称
			serialEntity.setChannelName("外场/外场");
			if(!Utils.isStrNull(transferChannelNameLoadDept)&&!Utils.isStrNull(transferChannelNameUnloadingDept)){
				serialEntity.setChannelName(transferChannelNameLoadDept+"/"+transferChannelNameUnloadingDept);
			}else if(Utils.isStrNull(transferChannelNameLoadDept)&&!Utils.isStrNull(transferChannelNameUnloadingDept)){
				serialEntity.setChannelName("外场/"+transferChannelNameUnloadingDept);
			}else if(!Utils.isStrNull(transferChannelNameLoadDept)&&Utils.isStrNull(transferChannelNameUnloadingDept)){
				serialEntity.setChannelName(transferChannelNameLoadDept+"/外场");
			}
		}else if("Y".equals(loadingDept.getTransferCenter())&&"Y".equals(unloadingDept.getSalesDepartment())){
			serialEntity.setActiveCode("10");//10：外场-营业部短途卸车
			//业务渠道名称
			serialEntity.setChannelName("外场/营业部");
			if(!Utils.isStrNull(transferChannelNameLoadDept)){
				serialEntity.setChannelName(transferChannelNameLoadDept+"/营业部");
			}
		}
	}
	
	/**
	 * @Description: 中转库存丢货处理
	 * @date 2015-6-25 上午11:01:36   
	 * @author 263072 
	 * @param dto
	 * @return
	 */
	public LostWarningDataEntity analyWarningData_transferStore(LostWarningTempDto dto){
		LostWarningDataEntity bean = new LostWarningDataEntity();
		//1.根据运单号 查询中转库存的丢货信息
		List<LostWarningDto> list = lostWarningDataDao.searchTransferStoreData(dto.getWayBillNo());
		if(list==null||list.size()==0){
			return null;
		}
		//2.查询运单表信息
		WayBillInfoDto wayBillInfo = lostWarningDataDao.searchWayBillInfo(dto.getWayBillNo());
		if(wayBillInfo==null){
			return null;
		}
		try{
			/*** 去除该运单已上报过的丢货货物 2015-11-28 20:15:28 ***/
			list = commonRemoveAlUploadData(dto.getWayBillNo(),list);
			if(list==null||list.size()==0){
				return null;
			}
			
			//调用公共处理模块
			bean = commonAnalyData(bean, list, wayBillInfo);
			//业务环节编码 
			String activeCode = "";
			if("N".equals(wayBillInfo.getIs_express())){
				//零担
				activeCode = "5";
			}else{
				//快递
				activeCode = "15";
			}
			//外场业务渠道类型
			String transferChannelName = getTransferServiceChannel(list.get(0).getRespDeptCode());
			if(Utils.isStrNull(transferChannelName)){
				transferChannelName = "外场";
			}
			
			for(WayBillSerialInfoEntity serialEntity:bean.getFlowCodeList()){
				serialEntity.setActiveCode(activeCode);
				serialEntity.setChannelName(transferChannelName);//业务渠道名称
			}
			//调用公共处理模块：责任部门等信息
			bean = commGetResponsibilityInfo(bean, list.get(0).getRespDeptCode());
			bean.setRepType("5");//上报方式  5、中转库存丢货上报
			//以下信息在该业务环节下暂定为空：车牌号、装车部门、卸车部门、交接单号
		}
		catch (Exception e) {
			bean = null;
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * @Description: 已交接丢货数据处理 分派送交接和非派送交接
	 * @date 2015-6-26 下午7:02:09   
	 * @author 263072 
	 * @param dto
	 * @return
	 */
	public LostWarningDataEntity analyWarningData_handover(LostWarningTempDto dto){
		LostWarningDataEntity bean = new LostWarningDataEntity();
		//1.根据运单号 查询已交接-正常装车的丢货信息
		List<LostWarningDto> list = lostWarningDataDao.searchHandoverLostData(dto.getWayBillNo());
		if(list==null||list.size()==0){
			//根据运单号查询已交接-派送装车的丢货信息
			//list = lostWarningDataDao.searchHandoverForDeliverData(dto.getWayBillNo());
			//if(list==null||list.size()==0){
				return null;
			//}
		}
		//2.查询运单表信息
		WayBillInfoDto wayBillInfo = lostWarningDataDao.searchWayBillInfo(dto.getWayBillNo());
		if(wayBillInfo==null){
			return null;
		}
		try{
			/**判断如果为快递 则中断该流程  2015-11-25 21:19:04**/
			if("Y".equals(wayBillInfo.getIs_express())){
				return null;
			}
			
			/*** 去除该运单已上报过的丢货货物 2015-11-28 20:15:28 ***/
			list = commonRemoveAlUploadData(dto.getWayBillNo(),list);
			if(list==null||list.size()==0){
				return null;
			}
			//添加过滤条件如果该运单有装车扫描、卸车扫描、单票入库、清仓扫描则不进行上报
			if(lostWarningDataDao.isSanAndSignleInStockLostData(dto.getWayBillNo()))
			{		
			    return null;
			}
			//调用公共处理模块
			bean = commonAnalyData(bean, list, wayBillInfo);
			
			//短信通知工号
			StringBuffer shortMessageCodeSBuf = new StringBuffer();
			//短信通知姓名
			StringBuffer shortMessageNameSBuf = new StringBuffer();
			
			/**
			 * 装车、卸车部门信息、业务渠道、业务环节编码、责任部门、责任人信息
			 * 该环节责任部门为装车部门和卸车部门
			 */
			for(WayBillSerialInfoEntity serialEntity:bean.getFlowCodeList()){
				for(LostWarningDto lwDto:list){
					//352203-sonar
					if(!StringUtils.equals(serialEntity.getFlowCode(),lwDto.getSerialNo())){
						continue;
					}
						//装卸车部门信息
						serialEntity.setLoadingDeptCode(lwDto.getLoadingDeptCode());
						serialEntity.setLoadingDeptName(lwDto.getLoadingDeptName());
						serialEntity.setUnloadingDeptCode(lwDto.getUnloadingDeptCode());
						serialEntity.setUnloadingDeptName(lwDto.getUnloadingDeptName());
						
						//查询部门信息
						OrgAdministrativeInfoEntity loadingDept = getDepartmentInfoBycode(lwDto.getLoadingDeptCode());
						if(loadingDept==null){
							break;
						}
						
						//外场业务渠道类型
						String transferChannelName = getTransferServiceChannel(lwDto.getLoadingDeptCode());
						
						/**** 根据装车部门属性判断业务环节    *****/
						//sonar-352203-set值抽取为方法
						initSerialEntityCodeAndName(serialEntity, loadingDept,
								transferChannelName);
						/**** 该环节责任部门为：装车部门和卸车部门 ****/
						serialEntity = commGetDoubleResponsibilityInfo(serialEntity,loadingDept,null);
						
						shortMessageCodeSBuf.append(serialEntity.getRespEmpcode()).append(",");
						shortMessageNameSBuf.append(serialEntity.getRespEmpname()).append(",");
						
						break;
//					}
				}
			}
			
			bean.setRepType("6");//6、已交接丢货上报
			if(shortMessageCodeSBuf.length()>0){
				//短信通知对象工号
				bean.setShortMessageCodes(shortMessageCodeSBuf.toString().substring(0,shortMessageCodeSBuf.length()-1));
			}
			if(shortMessageNameSBuf.length()>0){
				//短信通知对象姓名
				bean.setShortMessageNames(shortMessageNameSBuf.toString().substring(0,shortMessageNameSBuf.length()-1));
			}
			//以下信息在该环节为空  责任货区
		}
		catch (Exception e) {
			bean = null;
			e.printStackTrace();
		}
		return bean;
	}
	/**
	 * @param serialEntity
	 * @param loadingDept
	 * @param transferChannelName
	 */
	private void initSerialEntityCodeAndName(
			WayBillSerialInfoEntity serialEntity,
			OrgAdministrativeInfoEntity loadingDept, String transferChannelName) {
		if("Y".equals(loadingDept.getSalesDepartment())){
			serialEntity.setActiveCode("14");//14: 营业部清仓
			//业务渠道名称
			serialEntity.setChannelName("营业部");
		}else if("Y".equals(loadingDept.getTransferCenter())){
			serialEntity.setActiveCode("5");//5：外场清仓
			//业务渠道名称
			serialEntity.setChannelName("外场");
			if(!Utils.isStrNull(transferChannelName)){
				serialEntity.setChannelName(transferChannelName);
			}
		}
	}
	
	/**
	 * @Description: 异常库存（零担三次以上，快递一次以上） 丢货预警处理
	 * @date 2015-6-30 下午1:50:42   
	 * @author 263072 
	 * @param dto
	 * @return
	 */
	public LostWarningDataEntity analyWarningData_differStock(LostWarningTempDto dto){
		LostWarningDataEntity bean = new LostWarningDataEntity();
		//1.根据运单号 查询异常库存的丢货信息
		List<LostWarningDto> list = lostWarningDataDao.searchDifferStockData(dto.getWayBillNo());	
		if(list==null||list.size()==0){
			return null;
		}
		//2.查询运单表信息
		WayBillInfoDto wayBillInfo = lostWarningDataDao.searchWayBillInfo(dto.getWayBillNo());
		if(wayBillInfo==null){
			return null;
		}
		try{
			/*** 去除该运单已上报过的丢货货物 2015-11-28 20:15:28 ***/
			list = commonRemoveAlUploadData(dto.getWayBillNo(),list);
			if(list==null||list.size()==0){
				return null;
			}
			//添加过滤条件如果该运单有装车扫描、卸车扫描、单票入库、清仓扫描则不进行上报
			List<String> listJZReceive = lostWarningDataDao.searchSanAndSignleIDifferStock(dto.getWayBillNo());
			if(listJZReceive==null||listJZReceive.size()==0){
				return null;
			}
			//调用公共处理模块
			bean = commonAnalyData(bean, list, wayBillInfo);
			
			//短信通知工号
			StringBuffer shortMessageCodeSBuf = new StringBuffer();
			//短信通知姓名
			StringBuffer shortMessageNameSBuf = new StringBuffer();
			
			//获取业务环节编码、业务渠道名称
			for(WayBillSerialInfoEntity serialEntity:bean.getFlowCodeList()){
				for(LostWarningDto lwDto:list){
					//352203-sonar
					if(!StringUtils.equals(serialEntity.getFlowCode(),lwDto.getSerialNo())){
						continue;
					}
						//查询部门信息
						OrgAdministrativeInfoEntity loadingDept = getDepartmentInfoBycode(lwDto.getRespDeptCode());
						if(loadingDept==null){
							break;
						}
						//外场业务渠道类型
						String transferChannelName = getTransferServiceChannel(lwDto.getRespDeptCode());
					
						if("N".equals(wayBillInfo.getIs_express())){
							//sonar-352203
							initSerialEntityCodeAndName(serialEntity,
									loadingDept, transferChannelName);
							
						}else{
							//快递
							//sonar-352203-set值抽取为方法
							initSerialEntityCodeAndName1(serialEntity,
									loadingDept, transferChannelName);
						}
						
						/**** 该环节责任部门为：装车部门和卸车部门 ****/
						serialEntity = commGetDoubleResponsibilityInfo(serialEntity,loadingDept,null);
						
						shortMessageCodeSBuf.append(serialEntity.getRespEmpcode()).append(",");
						shortMessageNameSBuf.append(serialEntity.getRespEmpname()).append(",");
						break;
//					}
				}
			}
			
			
			if("N".equals(wayBillInfo.getIs_express())){
				bean.setRepType("7");//零担 上报方式  7、到达库存丢货上报
			}else{
				bean.setRepType("6");//快递 上报方式  6、到达库存丢货上报
			}
			
			if(shortMessageCodeSBuf.length()>0){
				//短信通知对象工号
				bean.setShortMessageCodes(shortMessageCodeSBuf.toString().substring(0,shortMessageCodeSBuf.length()-1));
			}
			if(shortMessageNameSBuf.length()>0){
				//短信通知对象姓名
				bean.setShortMessageNames(shortMessageNameSBuf.toString().substring(0,shortMessageNameSBuf.length()-1));
			}
			
			//以下信息在该业务环节下暂定为空 :车牌号、装车部门、卸车部门、交接单号、责任货区
		}
		catch (Exception e) {
			bean = null;
			e.printStackTrace();
		}
		return bean;
	}
	/**
	 * @param serialEntity
	 * @param loadingDept
	 * @param transferChannelName
	 */
	private void initSerialEntityCodeAndName1(
			WayBillSerialInfoEntity serialEntity,
			OrgAdministrativeInfoEntity loadingDept, String transferChannelName) {
		if("Y".equals(loadingDept.getSalesDepartment())){
			serialEntity.setActiveCode("23");//23：营业部清仓少货
			//业务渠道名称
			serialEntity.setChannelName("营业部");
		}else if("Y".equals(loadingDept.getTransferCenter())){
			serialEntity.setActiveCode("15");//15：外场清仓少货
			//业务渠道名称
			serialEntity.setChannelName("外场");
			if(!Utils.isStrNull(transferChannelName)){
				serialEntity.setChannelName(transferChannelName);
			}
		}
	}
	
	/**
	 * @Description: 分析派送丢货预警数据
	 * @date 2015-7-3 下午3:22:32   
	 * @author 263072 
	 * @param dto
	 * @return
	 */
	public LostWarningDataEntity analyWarningData_deliver(LostWarningTempDto dto){
		LostWarningDataEntity bean = new LostWarningDataEntity();
		//1.根据运单号 查询派送丢货信息
		List<LostWarningDto> list = lostWarningDataDao.searchDeliverLostData(dto.getWayBillNo());	
		if(list==null||list.size()==0){
			return null;
		}
		//2.查询运单表信息
		WayBillInfoDto wayBillInfo = lostWarningDataDao.searchWayBillInfo(dto.getWayBillNo());
		if(wayBillInfo==null){
			return null;
		}
		try{
			/*** 去除该运单已上报过的丢货货物 2015-11-28 20:15:28 ***/
			list = commonRemoveAlUploadData(dto.getWayBillNo(),list);
			if(list==null||list.size()==0){
				return null;
			}
			
			//调用公共处理模块
			bean = commonAnalyData(bean, list, wayBillInfo);
			if(bean==null) {
				return null;
			}
			
			//短信通知工号
			StringBuffer shortMessageCodeSBuf = new StringBuffer();
			//短信通知姓名
			StringBuffer shortMessageNameSBuf = new StringBuffer();
			
			//获取业务环节编码、业务渠道名称
			for(WayBillSerialInfoEntity serialEntity:bean.getFlowCodeList()){
				for(LostWarningDto lwDto:list){
					//352203-sonar
					if(!StringUtils.equals(serialEntity.getFlowCode(),lwDto.getSerialNo())){
						continue;
					}
						
						serialEntity.setLoadingDeptCode(lwDto.getLoadingDeptCode());
						serialEntity.setLoadingDeptName(lwDto.getLoadingDeptName());
						
						//查询部门信息
						OrgAdministrativeInfoEntity loadingDept = getDepartmentInfoBycode(lwDto.getLoadingDeptCode());
						if(loadingDept==null){
							break;
						}
						
						//查询车队信息
						String motorCode = lwDto.getRespDeptCode();
						if(StringUtils.isEmpty(motorCode)){
//							TransferMotorcadeDto motorCade = LostWarningConstant.motorCadeMap.get(lwDto.getUnloadingDeptCode());
							motorCode = wayBillInfo.getCustomerPickupOrgCode();
						}
						OrgAdministrativeInfoEntity motorOrg = getDepartmentInfoBycode(motorCode/*motorCade.getMotorcadeCode()*/);
						
						//外场业务渠道类型
						String transferChannelName = getTransferServiceChannel(lwDto.getLoadingDeptCode());
						
						/***该环节  零担有两种  快递只有一种 情景**/
						//sonar-352203-抽取为方法
						checkSetSerialEntityCodeName(wayBillInfo, serialEntity,
								loadingDept, transferChannelName);
						
						/**** 该环节责任部门为：装车部门和卸车部门 ****/
						serialEntity = commGetDoubleResponsibilityInfo(serialEntity,loadingDept,motorOrg);
						shortMessageCodeSBuf.append(serialEntity.getRespEmpcode()).append(",");
						shortMessageNameSBuf.append(serialEntity.getRespEmpname()).append(",");
						break;
//					}
				}
			}
			
			if("N".equals(wayBillInfo.getIs_express())){
				bean.setRepType("9");//零担 上报方式  9、派送丢货上报
			}else{
				bean.setRepType("7");//快递 上报方式  7、派送丢货
			}
			if(shortMessageCodeSBuf.length()>0){
				//短信通知对象工号
				bean.setShortMessageCodes(shortMessageCodeSBuf.toString().substring(0,shortMessageCodeSBuf.length()-1));
			}
			if(shortMessageNameSBuf.length()>0){
				//短信通知对象姓名
				bean.setShortMessageNames(shortMessageNameSBuf.toString().substring(0,shortMessageNameSBuf.length()-1));
			}
			//以下信息在该业务环节下暂定为空: 责任货区、卸车部门
			
		}catch (Exception e) {
			bean = null;
			e.printStackTrace();
		}
		return bean;
	}
	/**
	 * @param wayBillInfo
	 * @param serialEntity
	 * @param loadingDept
	 * @param transferChannelName
	 */
	private void checkSetSerialEntityCodeName(WayBillInfoDto wayBillInfo,
			WayBillSerialInfoEntity serialEntity,
			OrgAdministrativeInfoEntity loadingDept, String transferChannelName) {
		if("N".equals(wayBillInfo.getIs_express())){
			//零担
			if("Y".equals(loadingDept.getSalesDepartment())){
				serialEntity.setActiveCode("11");//11：营业部-客户送货
				//业务渠道名称
				serialEntity.setChannelName("营业部");
			}else if("Y".equals(loadingDept.getTransferCenter())){
				serialEntity.setActiveCode("19");//19：外场-客户派送
				//业务渠道名称
				serialEntity.setChannelName("外场");
				if(!Utils.isStrNull(transferChannelName)){
					serialEntity.setChannelName(transferChannelName);
				}
			}
			
		}else{
			//快递
			serialEntity.setActiveCode("24");//24：快递派送少货
			//业务渠道名称
			serialEntity.setChannelName("营业部");
		}
	}
	
	/**
	 * @Description: 分析空运外发丢货数据
	 * @date 2015-7-6 下午4:51:44   
	 * @author 263072 
	 * @param dto
	 * @return
	 */
	public LostWarningDataEntity analyWarningData_airTransfer(LostWarningTempDto dto){
		LostWarningDataEntity bean = new LostWarningDataEntity();
		//1.根据运单号 查询空运外发的丢货信息
		List<LostWarningDto> list = lostWarningDataDao.searchAirTransferLostData(dto.getWayBillNo());	
		if(list==null||list.size()==0){
			return null;
		}
		//2.查询运单表信息
		WayBillInfoDto wayBillInfo = lostWarningDataDao.searchWayBillInfo(dto.getWayBillNo());
		if(wayBillInfo==null){
			return null;
		}
		try{
			/**判断如果为快递 则中断该流程  2015-11-25 21:16:46**/
			if("Y".equals(wayBillInfo.getIs_express())){
				return null;
			}
			
			/*** 去除该运单已上报过的丢货货物 2015-11-28 20:15:28 ***/
			list = commonRemoveAlUploadData(dto.getWayBillNo(),list);
			if(list==null||list.size()==0){
				return null;
			}
			
			//调用公共处理模块
			bean = commonAnalyData(bean, list, wayBillInfo);
			if(bean==null) {
				return null;
			}
			//短信通知工号
			StringBuffer shortMessageCodeSBuf = new StringBuffer();
			//短信通知姓名
			StringBuffer shortMessageNameSBuf = new StringBuffer();
			//获取业务环节编码、业务渠道名称
			for(WayBillSerialInfoEntity serialEntity:bean.getFlowCodeList()){
				serialEntity.setActiveCode("17");
				serialEntity.setChannelName("空运");
				for(LostWarningDto lwDto:list){
					if(StringUtils.equals(serialEntity.getFlowCode(),lwDto.getSerialNo())){
						
						serialEntity.setLoadingDeptCode(lwDto.getLoadingDeptCode());
						serialEntity.setLoadingDeptName(lwDto.getLoadingDeptName());
						
						//查询部门信息
						OrgAdministrativeInfoEntity loadingDept = getDepartmentInfoBycode(lwDto.getLoadingDeptCode());
						if(loadingDept==null){
							break;
						}
						
						/**** 该环节责任部门为：装车部门和卸车部门 ****/
						serialEntity = commGetDoubleResponsibilityInfo(serialEntity,loadingDept,null);
						
						shortMessageCodeSBuf.append(serialEntity.getRespEmpcode()).append(",");
						shortMessageNameSBuf.append(serialEntity.getRespEmpname()).append(",");
						break;
					}
				}
			}
			if(shortMessageCodeSBuf.length()>0){
				//短信通知对象工号
				bean.setShortMessageCodes(shortMessageCodeSBuf.toString().substring(0,shortMessageCodeSBuf.length()-1));
			}
			if(shortMessageNameSBuf.length()>0){
				//短信通知对象姓名
				bean.setShortMessageNames(shortMessageNameSBuf.toString().substring(0,shortMessageNameSBuf.length()-1));
			}
			bean.setRepType("10");//零担 上报方式  10、空运丢货上报
			//以下信息在该业务环节下暂定为空 :车牌号、责任货区、卸车部门信息
			
		}catch (Exception e) {
			bean = null;
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * @Description: 分析快递外发丢货数据
	 * @date 2015-7-6 下午7:14:26   
	 * @author 263072 
	 * @param dto
	 * @return
	 */
	public LostWarningDataEntity analyWarningData_expressExternal(LostWarningTempDto dto){
		LostWarningDataEntity bean = new LostWarningDataEntity();
		//1.根据运单号 查询快递外发的丢货信息
		List<LostWarningDto> list = lostWarningDataDao.searchExpressExternalLostData(dto.getWayBillNo());	
		if(list==null||list.size()==0){
			return null;
		}
		//2.查询运单表信息
		WayBillInfoDto wayBillInfo = lostWarningDataDao.searchWayBillInfo(dto.getWayBillNo());
		if(wayBillInfo==null){
			return null;
		}
		try{
			/**判断如果为零担 则中断该流程  2015-11-25 21:06:54**/
			if("N".equals(wayBillInfo.getIs_express())){
				return null;
			}
			
			/*** 去除该运单已上报过的丢货货物 2015-11-28 20:15:28 ***/
			list = commonRemoveAlUploadData(dto.getWayBillNo(),list);
			if(list==null||list.size()==0){
				return null;
			}
			
			//调用公共处理模块
			bean = commonAnalyData(bean, list, wayBillInfo);
			if(bean==null) {
				return null;
			}
			
			//短信通知工号
			StringBuffer shortMessageCodeSBuf = new StringBuffer();
			//短信通知姓名
			StringBuffer shortMessageNameSBuf = new StringBuffer();
			
			//获取业务环节编码、业务渠道名称
			for(WayBillSerialInfoEntity serialEntity:bean.getFlowCodeList()){
				serialEntity.setActiveCode("25");
				serialEntity.setChannelName("外发");
				for(LostWarningDto lwDto:list){
					if(StringUtils.equals(serialEntity.getFlowCode(),lwDto.getSerialNo())){
						
						serialEntity.setLoadingDeptCode(lwDto.getLoadingDeptCode());
						serialEntity.setLoadingDeptName(lwDto.getLoadingDeptName());
						
						//查询部门信息
						OrgAdministrativeInfoEntity loadingDept = getDepartmentInfoBycode(lwDto.getLoadingDeptCode());
						if(loadingDept==null){
							break;
						}
						
						/**** 该环节责任部门为：装车部门和卸车部门 ****/
						serialEntity = commGetDoubleResponsibilityInfo(serialEntity,loadingDept,null);
						
						shortMessageCodeSBuf.append(serialEntity.getRespEmpcode()).append(",");
						shortMessageNameSBuf.append(serialEntity.getRespEmpname()).append(",");
						break;
					}
				}
			}
			
			bean.setRepType("8");//上报方式  8、外发丢货 
			
			if(shortMessageCodeSBuf.length()>0){
				//短信通知对象工号
				bean.setShortMessageCodes(shortMessageCodeSBuf.toString().substring(0,shortMessageCodeSBuf.length()-1));
			}
			if(shortMessageNameSBuf.length()>0){
				//短信通知对象姓名
				bean.setShortMessageNames(shortMessageNameSBuf.toString().substring(0,shortMessageNameSBuf.length()-1));
			}
			//以下信息在该业务环节下暂定为空 :车牌号、责任货区、卸车部门信息
		}catch (Exception e) {
			bean = null;
			e.printStackTrace();
		}
		return bean;
	}
	
	
	
	/**
	 * @Description: 上报卸车少货（供卸车差异报告模块使用）
	 * @date 2015-7-10 下午6:25:01   
	 * @author 263072 
	 * @param bean
	 * @param loadOrg 装车部门
	 * @param unloadOrg 新车部门
	 * @return
	 */
	public ResponseDto analyWarningData_unloadData(OaReportClearless bean,OrgAdministrativeInfoEntity loadOrg,
			OrgAdministrativeInfoEntity unloadOrg){
		ResponseDto dto = new ResponseDto();
		LostWarningDataEntity entity = new LostWarningDataEntity();
		/******** 组装上报字段 ********/
		WayBillInfoDto wayBillInfo = lostWarningDataDao.searchWayBillInfo(bean.getWayBillId());
		entity = (LostWarningDataEntity)Utils.copyObjProperties(wayBillInfo, entity);
		
		//流水号信息
		String serialStr =  bean.getSerialNoList();
		//判断是否为整票丢失
		if((serialStr.split(",").length+"").equals(entity.getGoodsNum())){
			entity.setIsWholeTicket("2");//整票丢失 1：否 2：是
		}else{
			entity.setIsWholeTicket("1");
		}
		entity.setLoseNum(serialStr.split(",").length+"");
		//丢货包装信息
		entity.setLoseGoodsPackage(wayBillInfo.getGoodsPackage());
		
		//短信通知工号
		StringBuffer shortMessageCodeSBuf = new StringBuffer();
		//短信通知姓名
		StringBuffer shortMessageNameSBuf = new StringBuffer();
		
		List<WayBillSerialInfoEntity> serialList = new ArrayList<WayBillSerialInfoEntity>();
		for(String serial : serialStr.split(",")){
			WayBillSerialInfoEntity serialBean = new WayBillSerialInfoEntity();
			serialBean.setFlowCode(serial);
			//车牌号
			serialBean.setCarCode(bean.getCarNumber());
			
			String activeCode="";
			String channelName="";
			
			//装车部门
			if(loadOrg!=null){
				serialBean.setLoadingDeptCode(loadOrg.getCode());
				serialBean.setLoadingDeptName(loadOrg.getName());
			}
			//卸车部门
			if(unloadOrg!=null){
				serialBean.setUnloadingDeptCode(unloadOrg.getCode());
				serialBean.setUnloadingDeptName(unloadOrg.getName());
				
				//外场业务渠道类型
				String transferChannelNameLoadDept = getTransferServiceChannel(loadOrg.getCode());
				String transferChannelNameUnloadingDept = getTransferServiceChannel(unloadOrg.getCode());
				
				
				//判断业务环节，零担4种，快递5种
				if("N".equals(wayBillInfo.getIs_express())){
					//零担
					if(loadOrg==null){
						activeCode="22,23";//22：客户-外场集中接货 23：客户-外场集中接货卸车
						channelName="车队,车队/外场";
						if(!Utils.isStrNull(transferChannelNameUnloadingDept)){
							channelName = "车队,车队/"+transferChannelNameUnloadingDept;
						}
					}else if("Y".equals(loadOrg.getSalesDepartment())&&"Y".equals(unloadOrg.getTransferCenter())){
						activeCode="3,6";//3：营业部-外场短途装车 6：营业部-外场短途卸车
						channelName="营业部,营业部/外场";
						if(!Utils.isStrNull(transferChannelNameUnloadingDept)){
							channelName = "营业部,营业部/"+transferChannelNameUnloadingDept;
						}
					}else if("Y".equals(loadOrg.getTransferCenter())&&"Y".equals(unloadOrg.getTransferCenter())){
						activeCode="7,12";//7：外场-外场长途装车 12：外场-外场长途卸车
						channelName="外场,外场/外场";
						if(!Utils.isStrNull(transferChannelNameLoadDept)&&!Utils.isStrNull(transferChannelNameUnloadingDept)){
							channelName=transferChannelNameLoadDept+","+
									transferChannelNameLoadDept+"/"+transferChannelNameUnloadingDept;
						}else if(Utils.isStrNull(transferChannelNameLoadDept)&&!Utils.isStrNull(transferChannelNameUnloadingDept)){
							channelName="外场,外场/"+transferChannelNameUnloadingDept;
						}else if(!Utils.isStrNull(transferChannelNameLoadDept)&&Utils.isStrNull(transferChannelNameUnloadingDept)){
							channelName=transferChannelNameLoadDept+","+transferChannelNameLoadDept+"/外场";
						}
					}else if("Y".equals(loadOrg.getTransferCenter())&&"Y".equals(unloadOrg.getSalesDepartment())){
						activeCode="8,10";//8：外场-营业部短途装车 10：外场-营业部短途卸车
						channelName="外场,外场/营业部";
						if(!Utils.isStrNull(transferChannelNameLoadDept)){
							channelName=transferChannelNameLoadDept+","+transferChannelNameLoadDept+"/营业部";
						}
					}
				}else{
					//快递
				    if("Y".equals(loadOrg.getExpressBranch())&&"Y".equals(unloadOrg.getSalesDepartment())){
				    	activeCode = "19,20";//19：分部--营业部短途装车少货 20：分部--营业部短途卸车少货
				    	channelName = "分部,分部/营业部";
				    }else if("Y".equals(loadOrg.getTransferCenter())&&"Y".equals(unloadOrg.getExpressBranch())){
				    	activeCode = "17,18";//17：外场-分部短途装车少货 18：外场-分部短途卸车少货
				    	channelName = "外场,外场/分部";
				    	if(!Utils.isStrNull(transferChannelNameLoadDept)){
							channelName=transferChannelNameLoadDept+","+transferChannelNameLoadDept+"/分部";
						}
					}else if("Y".equals(loadOrg.getTransferCenter())&&"Y".equals(unloadOrg.getSalesDepartment())){
						activeCode = "21,22";//21：外场--营业部短途装车少货 22：外场--营业部短途卸车少货
						channelName = "外场,外场/营业部";
						if(!Utils.isStrNull(transferChannelNameLoadDept)){
							channelName=transferChannelNameLoadDept+","+transferChannelNameLoadDept+"/营业部";
						}
					}else if("Y".equals(loadOrg.getTransferCenter())&&"Y".equals(unloadOrg.getTransferCenter())){
						activeCode = "5,6";//5：外场-外场长途装车少货 6：外场-外场长途卸车少货
						channelName = "外场,外场/外场";
						if(!Utils.isStrNull(transferChannelNameLoadDept)&&!Utils.isStrNull(transferChannelNameUnloadingDept)){
							channelName=transferChannelNameLoadDept+","+
									transferChannelNameLoadDept+"/"+transferChannelNameUnloadingDept;
						}else if(Utils.isStrNull(transferChannelNameLoadDept)&&!Utils.isStrNull(transferChannelNameUnloadingDept)){
							channelName="外场,外场/"+transferChannelNameUnloadingDept;
						}else if(!Utils.isStrNull(transferChannelNameLoadDept)&&Utils.isStrNull(transferChannelNameUnloadingDept)){
							channelName=transferChannelNameLoadDept+","+transferChannelNameLoadDept+"/外场";
						}
					}else if("Y".equals(loadOrg.getSalesDepartment())&&"Y".equals(unloadOrg.getTransferCenter())){
						activeCode = "3,4";//3：营业部-外场短途装车少货 4：营业部-外场短途卸车少货
						channelName = "营业部,营业部/外场";
						if(!Utils.isStrNull(transferChannelNameUnloadingDept)){
							channelName = "营业部,营业部/"+transferChannelNameUnloadingDept;
						}
					}
				}
			}
			
			//业务环节编码
			serialBean.setActiveCode(activeCode);
			//业务渠道
			serialBean.setChannelName(channelName);
			//交接单
			serialBean.setTransferBill(bean.getReplayBill());
			
			serialBean = commGetDoubleResponsibilityInfo(serialBean, loadOrg, unloadOrg);
			shortMessageCodeSBuf.append(serialBean.getRespEmpcode()).append(",");
			shortMessageNameSBuf.append(serialBean.getRespEmpname()).append(",");
			serialList.add(serialBean);
		}
		entity.setFlowCodeList(serialList);
		
		if(shortMessageCodeSBuf.length()>0){
			//短信通知对象工号
			entity.setShortMessageCodes(shortMessageCodeSBuf.toString().substring(0,shortMessageCodeSBuf.length()-1));
		}
		if(shortMessageNameSBuf.length()>0){
			//短信通知对象姓名
			entity.setShortMessageNames(shortMessageNameSBuf.toString().substring(0,shortMessageNameSBuf.length()-1));
		}
		
		
		//判断是否为快递，如果快递，则查询是否有建包信息
		if("N".equals(wayBillInfo.getIs_express())){
			entity.setRepScene("1");//上报场景 1：零担 2：快递
		}else{
			entity.setRepScene("2");//上报场景 1：零担 2：快递
			//遍历结果集，拼接包号、建包部门信息 、解包部门信息
			List<WayBillPkgInfoDto> pkgList = lostWarningDataDao.searchWayBillPkgList(wayBillInfo.getWaybillNum());
			if(pkgList!=null&&pkgList.size()>0){
				for(WayBillPkgInfoDto pkgInfo:pkgList){
					for(WayBillSerialInfoEntity serialEntity:entity.getFlowCodeList()){
						//352203-sonar
						if(!StringUtils.equals(serialEntity.getFlowCode(),pkgInfo.getSerialNo())){
							continue;
						}
						//sonar-352203-set值抽取为方法
							initSerialEntityPkgInfo(pkgInfo, serialEntity);
							break;
//						}
					}
				}
				
			}
		}
		
		entity.setSysAutoPkId(UUIDUtils.getUUID());//系统上报ID
		entity.setRepTime(sdf.format(new Date()));//处理时间
		entity.setRepType("4");//4：卸车校验
		
		//以下字段该环节为空 :责任货区
		
		
		List<WayBillSerialInfoEntity> serialEntityList = entity.getFlowCodeList();
		/******* 接口查询QMS是否重复上报（包含人工处理部分）  *******/
    	String alreadyReportGoods = FossToMcewService.getInstatce().queryReportGoodsByBill(entity.getWaybillNum());
    	if(!"".equals(alreadyReportGoods)){
    		try{
        		JSONObject jsonObj = JSONObject.fromObject(alreadyReportGoods); 
        		String alreadyReportSerial = jsonObj.get("flowCode").toString();
        		for(int i=0;i<serialEntityList.size();i++){
            		WayBillSerialInfoEntity serialEntity = serialEntityList.get(i);
            		for(String serialNo:alreadyReportSerial.split(",")){
            			if(StringUtils.equals(serialEntity.getFlowCode(),serialNo)){
        					//已存在上报记录，排除该流水信息
            				serialEntityList.remove(i);
            				i--;
            				break;
            			}
            		}
            	}
    		}
    		catch(Exception e){
    			//上报失败
        		lostWarningDataService.saveUploadFalseInfo(entity, alreadyReportGoods,null,"");
        		return dto;
    		}
        		
    	}
    	
    	//判断丢货列表是否为空
    	if(serialEntityList.size()==0){
    		return dto;
    	}
		
		List<LostWarningDataEntity> list = new ArrayList<LostWarningDataEntity>();
		list.add(entity);
		
		/**** 上报数据 ***/
		try{
			String uploadMsg = JSONArray.fromObject(list).toString();
        	String respStr = FossToMcewService.getInstatce().reportWarningData(uploadMsg);
			if(!Utils.isStrNull(respStr)){
				try{
        		    JSONObject obj = JSONObject.fromObject(respStr); 
                	//上报状态标示符   0：失败 1：成功
        			String retStatus = obj.get("retStatus").toString();
        			//丢货编号
        			String lostRepCode = obj.get("lostRepCode").toString();
        			
        			/*** 4.记录日志并删除中间表记录 ****/
                	if("1".equals(retStatus)){
                		dto.setErrorsNo(lostRepCode);
                		//上报成功
                		lostWarningDataService.saveUploadSuccInfo(entity, lostRepCode,null,uploadMsg);
                	}else{
                		//上报失败
                		lostWarningDataService.saveUploadFalseInfo(entity, respStr,null,uploadMsg);
                	}
        		}catch(Exception e){
        			//上报失败
        			lostWarningDataService.saveUploadFalseInfo(entity, respStr,null,uploadMsg);
        		}
				dto.setMessage(respStr);
				
			}else{
				//上报失败
				lostWarningDataService.saveUploadFalseInfo(entity, "上报异常",null,uploadMsg);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	/**
	 * @param pkgInfo
	 * @param serialEntity
	 */
	private void initSerialEntityPkgInfo(WayBillPkgInfoDto pkgInfo,
			WayBillSerialInfoEntity serialEntity) {
		//包号
		if(!Utils.isStrNull(pkgInfo.getPackageNo())){
			serialEntity.setPackageNumber(pkgInfo.getPackageNo());
		}
		//建包部门编码
		if(!Utils.isStrNull(pkgInfo.getDepartOrgCode())){
			serialEntity.setPackDeptCode(pkgInfo.getDepartOrgCode());
		}
		//建包部门名称
		if(!Utils.isStrNull(pkgInfo.getDepartOrgName())){
			serialEntity.setPackDeptName(pkgInfo.getDepartOrgName());
		}
		//解包部门编码
		if(!Utils.isStrNull(pkgInfo.getArriveOrgCode())){
			serialEntity.setUnpackDeptCode(pkgInfo.getArriveOrgCode());
		}
		//解包部门名称
		if(!Utils.isStrNull(pkgInfo.getArriveOrgName())){
			serialEntity.setUnpackDeptName(pkgInfo.getArriveOrgName());
		}
	}
	
	/**
	 * @Description: 根据外场部门编码获取转运外场业务渠道类型
	 * @date 2015-9-9 下午4:57:34   
	 * @author 263072 
	 * @param deptCode
	 * @return
	 */
	public String getTransferServiceChannel(String deptCode){
		String result = "";
		//判断外场具体属性
		if(LostWarningConstant.motorCadeMap.containsKey(deptCode)){
			TransferMotorcadeDto bean = LostWarningConstant.motorCadeMap.get(deptCode);
			if(StringUtils.equals(bean.getTransferServiceChannel(), DictionaryValueConstants.HUB_OUTFIELD)){
				result = "枢纽外场";
			}else if(StringUtils.equals(bean.getTransferServiceChannel(), DictionaryValueConstants.ONE_LEVEL_TRANSPORT_OUTFIELD)){
				result = "一级转运外场";
			}else if(StringUtils.equals(bean.getTransferServiceChannel(), DictionaryValueConstants.TWO_LEVEL_TRANSPORT_OUTFIELD)){
				result = "二级转运外场";
			}else if(StringUtils.equals(bean.getTransferServiceChannel(), DictionaryValueConstants.THREE_LEVEL_TRANSPORT_OUTFIELD)){
				result = "三级转运外场";
			}else if(StringUtils.equals(bean.getTransferServiceChannel(), DictionaryValueConstants.ONE_LEVEL_DISTRIBUTION_OUTFIELD)){
				result = "一级集配外场";
			}else if(StringUtils.equals(bean.getTransferServiceChannel(), DictionaryValueConstants.TWO_LEVEL_DISTRIBUTION_OUTFIELD)){
				result = "二级集配外场";
			}else if(StringUtils.equals(bean.getTransferServiceChannel(), DictionaryValueConstants.OPERATION_OUTFIELD)){
				result = "运作外场";
			}
		}
		return result;
	}
	
}
