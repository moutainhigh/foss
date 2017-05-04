package com.deppon.foss.module.transfer.lostwarning.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
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
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.TransferMotorcadeDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.WayBillInfoDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.WayBillPkgInfoDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.utils.Utils;

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
public class LostWarningAnalyDataForLack {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LostWarningAnalyDataForLack.class);
	
	private ILostWarningDataDao lostWarningDataDao;
	
	private ILostWarningDataService lostWarningDataService;
	
	//综合管理 组织信息 Service
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	//查询员工信息接口
	private IEmployeeService employeeService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public void setLostWarningDataDao(ILostWarningDataDao lostWarningDataDao) {
		this.lostWarningDataDao = lostWarningDataDao;
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
		if(wayBillInfo == null){
			return dto;
		}
		
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
//			Logger.info("loadOrg is " + loadOrg);
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
					if("Y".equals(loadOrg.getSalesDepartment())&&"Y".equals(unloadOrg.getTransferCenter())){
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
						//sonar-352203
						if(!StringUtils.equals(serialEntity.getFlowCode(),pkgInfo.getSerialNo())){
							continue;
						}
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
							break;
//						}
					}
				}
				
			}
		}
		
		entity.setSysAutoPkId(lostWarningDataDao.getSeqNextVal()+"");//系统上报ID
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
        		String[] alreadyReportSerials = alreadyReportSerial != null ? alreadyReportSerial.split(",") : new String[]{};
        		for(int i=0;i<serialEntityList.size();i++){
            		WayBillSerialInfoEntity serialEntity = serialEntityList.get(i);
            		for(String serialNo:alreadyReportSerials){
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
//        		lostWarningDataService.saveUploadFalseInfo(entity, alreadyReportGoods,null,"");
        		return dto;
    		}
        		
    	}
    	
    	//判断丢货列表是否为空
    	if(serialEntityList.size()==0){
    		dto.setMessage("流水号已经全部上报");
    		return dto;
    	}
		
		List<LostWarningDataEntity> list = new ArrayList<LostWarningDataEntity>();
		list.add(entity);
		
		/**** 上报数据 ***/
		try{
			String uploadMsg = JSONArray.fromObject(list).toString();
			LOGGER.info("uploadMsg: " + uploadMsg);
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
//                		lostWarningDataService.saveUploadFalseInfo(entity, respStr,null,uploadMsg);
                	}
        		}catch(Exception e){
        			//上报失败
//        			lostWarningDataService.saveUploadFalseInfo(entity, respStr,null,uploadMsg);
        		}
				dto.setMessage(respStr);
				
			}else{
				//上报失败
//				lostWarningDataService.saveUploadFalseInfo(entity, "上报异常",null,uploadMsg);
			}
		}
		catch (Exception e) {
			LOGGER.error("卸车少货上报失败,错误:" + e.getMessage());
		
		}
		return dto;
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
