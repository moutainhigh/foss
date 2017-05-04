package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.shared.define.QmsErrorConstants;
import com.deppon.foss.module.transfer.common.api.shared.dto.QmsResponseDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.PrintLabelEntity;
import com.deppon.foss.module.transfer.load.api.server.service.IComplementService;
import com.deppon.foss.module.transfer.load.api.shared.domain.ComplementLogEntity;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.unload.api.server.service.ITfrForQmsService;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadWaybillDetailEntity;

public class TfrForQmsService implements ITfrForQmsService {
	private static final Logger LOGGER = LogManager.getLogger(TfrForQmsService.class);
	
	private IComplementService complementService;
	
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	private IEmployeeService employeeService;
	
	private IPrintLabelService printLabelService;
	
	private IUnloadTaskService unloadTaskService;
	
	private IStockService stockService;
	
	private ICalculateTransportPathService calculateTransportPathService;
	
	private IWaybillManagerService waybillManagerService;
	/**
	 * @author nly
	 * @date 2015年5月4日 上午9:15:51
	 * @function QMS差错服务中转入口
	 * @param errorType
	 * @param waybillNo
	 * @return
	 */
	@Override
	public String getInfoFromTfrForQms(String errorType, String waybillNo ,String standardCode) {
		QmsResponseDto dto = new QmsResponseDto();
		if(StringUtils.isEmpty(errorType) || StringUtils.isEmpty(waybillNo)) {
			return JSONObject.toJSON(dto).toString();
		}
		
		if(QmsErrorConstants.EXPRESS_ILLEGAL_RETURN_CODE.equals(errorType)) {//K违规返货差错
			
			dto = this.queryForExpressIllegalReturn(waybillNo,standardCode);
			
		} else if(QmsErrorConstants.EXPRESS_ONLINE_COMPLETE_CODE.equals(errorType)) {//K线上补码差错
			
			dto = this.queryForExpressOnlineComplete(waybillNo);
			
		} else if(QmsErrorConstants.EXPRESS_OFFLINE_COMPLETE_CODE.equals(errorType)) {//K线下补码差错
			
			dto = this.queryForExpressOfflineComplete(waybillNo);
			
		} else if(QmsErrorConstants.EXPRESS_LABEL_CODE.equals(errorType)) {//K标签差错
			
			dto = this.queryForExpressLabel(waybillNo);
			
		} else if(QmsErrorConstants.EXPRESS_PACKAGE_CODE.equals(errorType)) {//K建包差错
			
			dto = this.queryForExpressPackage(waybillNo);
			
		} else if(QmsErrorConstants.QMS_K_YCH.equals(errorType)) {//K异常货物管理
			
			dto = this.queryForExpressException(waybillNo);
			
		}
		
		return JSONObject.toJSON(dto).toString();
	}
	/**
	 * @author nly
	 * @date 2015年5月5日 下午1:58:33
	 * @function K违规返货差错
	 * @param waybillNo
	 * @return
	 */
	private QmsResponseDto queryForExpressIllegalReturn(String waybillNo,String standardCode) {
		QmsResponseDto dto = new QmsResponseDto();
		if(StringUtils.isEmpty(standardCode)) {
			return dto;
		}
		
	      /*K违规返货标准1  "2132";
		    K违规返货标准2  "2133";
		    return：到达部门负责人；
		
		    K违规返货标准3  "2134";
		    return：补码部门负责人*/
		if("2132".equals(standardCode) || "2133".equals(standardCode)) {
			WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillNo);
			if(null != waybill) {
				//到达部门即提货网点
				String arriveDeptCode = waybill.getCustomerPickupOrgCode();
				Map<String,String> map = this.getPrincipal(arriveDeptCode);
				if(null != map) {
					dto.setIllegalReturnPrincipalNo(map.get("userCode"));
					dto.setIllegalReturnPrincipalName(map.get("userName"));
				}
			}
		} else if("2134".equals(standardCode)) {
			//补码部门负责人,无则往上找至大区
			ComplementLogEntity entity = complementService.queryLastComplementLog(waybillNo);
			if(null != entity) {
				String deptCode = entity.getOperationOrgCode();
				Map<String,String> map = this.getPrincipal(deptCode);
				if(null != map) {
					dto.setComplementManagerCode(map.get("userCode"));
					dto.setComplementOperatorName(map.get("userName"));
				}
			}
		}
		/*//外发部门负责人：落地配外发人所在部门负责人，若不存在则找上级部门负责人
		List<LdpExternalBillDto> ldpList = ldpExternalBillService.queryByWaybillNo(waybillNo);
		String transferCode = "";
		if(CollectionUtils.isNotEmpty(ldpList)) {
			
			LdpExternalBillDto lastEntity = ldpList.get(0);
			if(null != lastEntity ) {
				transferCode = lastEntity.getExternalOrgCode();
				//外发人员
				EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(lastEntity.getExternalUserCode());
				if(null != employee) {
					String deptCode = employee.getOrgCode();
					Map<String,String> map = this.getPrincipal(deptCode);
					if(null != map) {
						dto.setExternalOrgPrincipalNo(map.get("userCode"));
						dto.setExternalOrgPrincipalName(map.get("userName"));
					}
				} else {
					//外发人不存在，则取外发外场的责任人
					Map<String,String> map = this.getPrincipal(lastEntity.getExternalOrgCode());
					if(null != map) {
						dto.setExternalOrgPrincipalNo(map.get("userCode"));
						dto.setExternalOrgPrincipalNo(map.get("userName"));
					}
					
				}
				
				//快递中转场负责人：落地配外发部门（均为外发人员所在外场）下的快递中转场负责人，若快递中转场不存在或负责人不存在，则找上级部门负责人
				List<OrgAdministrativeInfoEntity> entitys = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpDown(transferCode,false);
				//外场下是否有快递中转场
				boolean hasExpressCenter = false;
				if(CollectionUtils.isNotEmpty(entitys)) {
					for(OrgAdministrativeInfoEntity entity : entitys) {
						if(StringUtils.isNotEmpty(entity.getName()) && entity.getName().contains("快递中转场")) {
							hasExpressCenter = true;
							Map<String,String> map = this.getPrincipal(entity.getCode());
							if(null != map) {
								dto.setTransferCenterPrincipalNo(map.get("userCode"));
								dto.setTransferCenterPrincipalName(map.get("userName"));
							}
							break;
						}
					}
				} else {
					//外场无下属部门时
					Map<String,String> map = this.getPrincipal(transferCode);
					if(null != map) {
						dto.setTransferCenterPrincipalNo(map.get("userCode"));
						dto.setTransferCenterPrincipalName(map.get("userName"));
					}
				}
				//外场下无快递中转场
				if(!hasExpressCenter) {
					Map<String,String> map = this.getPrincipal(transferCode);
					if(null != map) {
						dto.setTransferCenterPrincipalNo(map.get("userCode"));
						dto.setTransferCenterPrincipalName(map.get("userName"));
					}
				}
			}
		}*/
	
		return dto;
	}
	/**
	 * @author nly
	 * @date 2015年5月5日 下午2:03:48
	 * @function K线上补码差错
	 * @param waybillNo
	 * @return
	 */
	private QmsResponseDto queryForExpressOnlineComplete(String waybillNo){
		QmsResponseDto dto = new QmsResponseDto();
		//补码员
		ComplementLogEntity entity = complementService.queryLastComplementLog(waybillNo);
		if(null != entity) {
			dto.setComplementOperatorCode(entity.getOperatorCode());
			dto.setComplementOperatorName(entity.getOperatorName());
		}
		return dto;
	}
	/**
	 * @author nly
	 * @date 2015年5月5日 下午2:05:42
	 * @function K线下补码差错
	 * @param waybillNo
	 * @return
	 */
	private QmsResponseDto queryForExpressOfflineComplete(String waybillNo){
		QmsResponseDto dto = new QmsResponseDto();
		//补码部门负责人
		ComplementLogEntity entity = complementService.queryLastComplementLog(waybillNo);
		if(null != entity) {
			String deptCode = entity.getOperationOrgCode();
			Map<String,String> map = this.getPrincipal(deptCode);
			if(null != map) {
				dto.setComplementManagerCode(map.get("userCode"));
				dto.setComplementManagerName(map.get("userName"));
			}
		}
		return dto;
	}
	/**
	 * @author nly
	 * @date 2015年5月5日 下午2:08:11
	 * @function K标签差错
	 * @param waybillNo
	 * @return
	 */
	private QmsResponseDto queryForExpressLabel(String waybillNo){
		QmsResponseDto dto = new QmsResponseDto();
		//最后标签打印人
		PrintLabelEntity printEntity = printLabelService.queryLastLabelPrintByWaybillNo(waybillNo);
		if(null != printEntity) {
			dto.setLastPrintUserCode(printEntity.getPrintUserCode());
			dto.setLastPrintUserName(printEntity.getPrintUserName());
		}
		return dto;
	} 
	/**
	 * @author nly
	 * @date 2015年5月5日 下午2:10:10
	 * @function K建包差错
	 * @param waybillNo
	 * @return
	 */
	private QmsResponseDto queryForExpressPackage(String waybillNo) {
		QmsResponseDto dto = new QmsResponseDto();
		//上一环节装车部门
		List<UnloadWaybillDetailEntity> unloadList = unloadTaskService.queryUnloadWaybillDetailByNo(waybillNo);
		if(CollectionUtils.isNotEmpty(unloadList)) {
			if(null != unloadList.get(0)) {
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(unloadList.get(0).getOrigOrgCode());
				if(null != org) {
					dto.setPreviousloadingOrgCode(org.getUnifiedCode());
					dto.setPreviousloadingOrgName(org.getName());
				}
			}
		}
		return dto;
	}
	
	/**
	 * @author nly
	 * @date 2015年5月5日 下午2:12:41
	 * @function K异常货物管理
	 * @param waybillNo
	 * @return
	 */
	private QmsResponseDto queryForExpressException(String waybillNo) {
		QmsResponseDto dto = new QmsResponseDto();
		//运单经手上一环节部门:最后一次入库部门（除 丢货改善管理组 库存） 的上一部门
		List<InOutStockEntity> inStockList = stockService.queryInStockInfoByType(waybillNo,null,null);
		String deptCode = "";
		String serialNo = "";
		if(CollectionUtils.isNotEmpty(inStockList)) {
			int i = 0;
			for(InOutStockEntity entity : inStockList) {
				deptCode = entity.getOrgCode();
				//丢货改善管理组
				if(!"W01000301050203".equals(deptCode)) {
					i++;
					if(i == 2) {
						OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
						if(null != org) {
							deptCode = entity.getOrgCode();
							serialNo = entity.getSerialNO();
							dto.setPreviousloadingOrgCode(org.getUnifiedCode());
							dto.setPreviousloadingOrgName(org.getName());
						}
						break;
					}
				}	
			}
		}
		//经手部门:最后入库货物（用于分批情况）实际走货路径中的所有部门（根据实际经过走货路径传递，多个部门以逗号隔开）；
		try{
			List<String> serialNoList = new ArrayList<String>();
			serialNoList.add(serialNo);
			
			List<String>  deptCodes = calculateTransportPathService.queryPassDeptCodes(waybillNo, serialNoList);
				
			StringBuffer depts = new StringBuffer();
			StringBuffer deptNames =  new StringBuffer();
			if(CollectionUtils.isNotEmpty(deptCodes)) {
				for(String code : deptCodes){
					OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(code);
					if(null != org) {
						depts.append(org.getUnifiedCode());
						depts.append(",");
						deptNames.append(org.getName());
						deptNames.append(",");
					}
				}
			}
			if(depts.length() >= 2) {
				dto.setPassOrgCode(depts.toString().substring(0, depts.length() - 2));
			}
			
			if(deptNames.length() >= 2) {
				dto.setPassOrgName(deptNames.toString().substring(0, deptNames.length() - 2));
			}	 
				
		} catch(Exception e) {
			LOGGER.error("QMS接口-调用走货路径异常");
		}
		return dto;
	}

	/**
	 * @author nly
	 * @date 2015年5月5日 上午10:39:21
	 * @function 根据部门code查询部门负责人，向上查找，最高至大区
	 * @param deptCode
	 * @return
	 */
	private Map<String, String> getPrincipal(String deptCode) {
		Map<String,String> map = new HashMap<String,String>();
		//是否已到大区
		String bigRegion = "N";
		for(int i =0;i < ConstantsNumberSonar.SONAR_NUMBER_6;i++) {
			//最高判断至大区
			if(StringUtils.equals("Y", bigRegion)) {
				return null;
			}
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
			if(null == org) {
				return null;
			}
			bigRegion = org.getBigRegion();
			//负责人工号
			String userCode = org.getPrincipalNo();
			if(StringUtils.isNotEmpty(userCode)) {
				
				EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(userCode);
				if(null != employee) {
					String userName = employee.getEmpName();
					map.put("userCode", userCode);
					map.put("userName", userName);
					return map;
				} else {
					//查找上一部门负责人
					deptCode = org.getParentOrgCode();
				}
			} else {
				//查找上一部门负责人
				deptCode = org.getParentOrgCode();
			}	
		}
		
		return null;
	}
	public void setComplementService(IComplementService complementService) {
		this.complementService = complementService;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	public void setPrintLabelService(IPrintLabelService printLabelService) {
		this.printLabelService = printLabelService;
	}
	public void setUnloadTaskService(IUnloadTaskService unloadTaskService) {
		this.unloadTaskService = unloadTaskService;
	}
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
}
