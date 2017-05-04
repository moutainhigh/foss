package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.server.service.IWaybillFromMachineService;
import com.deppon.foss.module.transfer.common.api.shared.domain.WaybillFromMachineEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.pda.api.server.service.IMachineWeightAndPartyService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.RequestMachineScanDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.ResponseParameter;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDASortingDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IBCMachSortScanService;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.BCMachSortScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SortingScanEntity;
import com.deppon.foss.util.UUIDUtils;


/**
* @description 计泡机称重量方信息接口服务
* @version 1.0
* @author 105869-foss-heyongdong
* @update 2015年5月8日 下午3:50:39
*/
public class MachineWeightAndPartyService implements IMachineWeightAndPartyService {
	static final Logger LOGGER = LoggerFactory.getLogger(MachineWeightAndPartyService.class);
	private IBCMachSortScanService bCMachSortScanService;
	private IWaybillFromMachineService waybillFromMachineService;
	public void setWaybillFromMachineService(
			IWaybillFromMachineService waybillFromMachineService) {
		this.waybillFromMachineService = waybillFromMachineService;
	}


	public void setbCMachSortScanService(
			IBCMachSortScanService bCMachSortScanService) {
		this.bCMachSortScanService = bCMachSortScanService;
	}

	private IEmployeeService employeeService;	
	
	private IPDACommonService pdaCommonService;
	
	private IPDASortingDao pdaSortingDao;
	//获取运单的所有信息
	private IWaybillDao waybillDao;
		
	public void setWaybillDao(IWaybillDao waybillDao) {
			this.waybillDao = waybillDao;
	}
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}


	public void setPdaCommonService(IPDACommonService pdaCommonService) {
		this.pdaCommonService = pdaCommonService;
	}

	public void setPdaSortingDao(IPDASortingDao pdaSortingDao) {
		this.pdaSortingDao = pdaSortingDao;
	}

	/**
	* @description 计泡机称重量方运单信息传递到foss
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.pda.api.server.service.IMachineWeightAndPartyService#scan(com.deppon.foss.module.transfer.pda.api.shared.domain.RequestMachineScanDetailEntity)
	* @author 105869-foss-heyongdong
	* @update 2015年5月8日 下午3:51:18
	* @version V1.0
	*/
	@Override
	public @ResponseBody ResponseParameter scan(@RequestBody RequestMachineScanDetailEntity requestParam) {
		ResponseParameter response = new ResponseParameter();
		try{
			
			if(requestParam==null){
				throw new TfrBusinessException("传递信息为空！");
			}
			LOGGER.error("计泡机称重量方开始，运单号:"+requestParam.getWaybillNo()+" 流水号："+requestParam.getSerialNo()+"");
			
			
			EmployeeEntity employee =null;
			if(requestParam.getOperatorCode()!=null){
				employee = employeeService.queryEmployeeByEmpCode(requestParam.getOperatorCode());	
			}
			String empCode="系统";
			String empName = "系统";
			String operateCode = "";
			if(employee!=null){
			  empName = employee.getEmpName();
			  empCode = employee.getEmpCode();
			  operateCode = employee.getOrgCode();
			}
			
			String orgCode = "外场";
			String orgName = "外场";
			OrgAdministrativeInfoEntity org = pdaCommonService.getTopCurrentOutfieldOrSalesDept(operateCode);
			if(org != null){
				orgCode = org.getCode();
				orgName = org.getName();
			}
			String waybillNo = requestParam.getWaybillNo();
			String serialNo = requestParam.getSerialNo();
			if(waybillNo==null || serialNo ==null){
				throw new TfrBusinessException("扫描的运单号或者流水号为空！");
			}
			Date scanTime = requestParam.getScanTime();
			if(scanTime == null){
				throw new TfrBusinessException("扫描时间不能为空！");
			}
		//	EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(requestParam.getOperatorCode());
			//插入扫描日志表中
			BCMachSortScanEntity sortScanlog = new BCMachSortScanEntity();
			sortScanlog.setId(UUIDUtils.getUUID());
			sortScanlog.setCreateTime(new Date());
			sortScanlog.setWaybillNo(waybillNo);
			sortScanlog.setSerialNo(serialNo);
			sortScanlog.setOperationDept(orgName);
			sortScanlog.setOperationDeptCode(orgCode);
			sortScanlog.setOperatorCode(empCode);
			sortScanlog.setOperatorName(empName);
			sortScanlog.setScantime(requestParam.getScanTime());
			sortScanlog.setScanType("UP");
			sortScanlog.setLength(requestParam.getLength());
			sortScanlog.setWidth(requestParam.getWidth());
			sortScanlog.setHeigth(requestParam.getHeight());
			sortScanlog.setWeight(requestParam.getWeight());
			sortScanlog.setVolumn(requestParam.getVolumn());
			
			//只插入运单每件货的最新的一条记录到该表中
			BCMachSortScanEntity entity = new BCMachSortScanEntity();
			entity.setId(UUIDUtils.getUUID());
			entity.setSortScanlogId(sortScanlog.getId());
			entity.setCreateTime(new Date());
			entity.setWaybillNo(waybillNo);
			entity.setSerialNo(serialNo);
			entity.setOperationDept(orgName);
			entity.setOperationDeptCode(orgCode);
			entity.setOperatorCode(empCode);
			entity.setOperatorName(empName);
			entity.setScantime(requestParam.getScanTime());
			entity.setScanType("UP");
			entity.setLength(requestParam.getLength());
			entity.setWidth(requestParam.getWidth());
			entity.setHeigth(requestParam.getHeight());
			entity.setWeight(requestParam.getWeight());
			entity.setVolumn(requestParam.getVolumn());
			//通过运单号，流水号获取 最新的扫描记录
			BCMachSortScanEntity sortEntity = bCMachSortScanService.queryBCMachSortScanBySeriaNo(waybillNo,serialNo);
			
			
			SortingScanEntity sortingScanEntity = new SortingScanEntity();
			sortingScanEntity.setDeviceNo(UnloadConstants.SORT_SCAN_MODE_BSC);
			sortingScanEntity.setOperatorCode(empCode);
			sortingScanEntity.setOperatorName(empName);
			sortingScanEntity.setOrgName(orgName);
			sortingScanEntity.setOrgCode(orgCode);
			sortingScanEntity.setScanType("UP");
			sortingScanEntity.setScanTime(requestParam.getScanTime());
			sortingScanEntity.setSerialNo(requestParam.getSerialNo());
			sortingScanEntity.setWayBillNo(requestParam.getWaybillNo());
			sortingScanEntity.setCreateTime(requestParam.getScanTime());
			sortingScanEntity.setId(UUIDUtils.getUUID());
			sortingScanEntity.setScanMode(UnloadConstants.SORT_SCAN_MODE_BSC);//扫描方式
			pdaSortingDao.insertSortingScan(sortingScanEntity);
			//保存扫描日志
			bCMachSortScanService.saveScanMsgLog(sortScanlog);
			//如果没有该运单流水信息则插入
			if(sortEntity == null){
				
				bCMachSortScanService.saveScanMsg(entity);
				//更新或者插入 运单信息总表
				bCMachSortScanService.updateOrInsertWaybillMsg(waybillNo);
			}else{
				//当前时间比 查询到的数据扫描时间晚则 插入新的数据删除 查询到的数据
				if(scanTime.after(sortEntity.getScanTime())){
					//删除信息
					bCMachSortScanService.deleteScanMsg(sortEntity);
					//插入信息
					bCMachSortScanService.saveScanMsg(entity);
					
					//更新或者插入运单信息总表 
					bCMachSortScanService.updateOrInsertWaybillMsg(waybillNo);
					
				}
			}
			//开始将计泡机传来的数据需要上报到qms中的数据添加到waybillFrommachine表中
			this.getToDoJobItems(waybillNo);
			response.setBeSuccess(true);
			
			LOGGER.error("计泡机称重量方结束");
		}catch(Exception e){
			LOGGER.error("计泡机称重量方异常，运单号:"+requestParam.getWaybillNo()+" 流水号："+requestParam.getSerialNo()+"称重量方部门："+requestParam.getOperationDeptCode()+"异常信息：");
			e.printStackTrace();
			response.setBeSuccess(false);
			response.setFailureReason(e.getMessage());
			return response;
		}
		
		return response;
	}

	/**
	 * 将计泡机传来的数据添加到WaybillFromMachine表中
	 * 
	 */
	
	public void getToDoJobItems(String waybillNo) {
		try {
			LOGGER.info("查询运单的实际信息开始(包括发更改之后的).....");
			WaybillEntity wayBill = waybillDao.queryWaybillByNo(waybillNo);//获取运单信息
			LOGGER.info("查询运单的实际信息结束(包括发更改之后的).....");
			//现业务需求不需要上报一票多件的情况
			if (wayBill != null && wayBill.getGoodsQtyTotal() == 1) {
				LOGGER.info("------------开始将要上报qms的运单信息添加到WaybillFromMachine表中开始-----------");
				WaybillFromMachineEntity entity = new WaybillFromMachineEntity();
				entity.setId(UUIDUtils.getUUID());
				entity.setWaybillNo(waybillNo);
				entity.setCreateTime(new Date());
				entity.setStatus("DOING");
				waybillFromMachineService.addData(entity);
				LOGGER.info("------------结束将要上报qms的运单信息添加到WaybillFromMachine表中结束-----------");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("向WaybillFromMachine表中添加待上报qms的信息失败:"+e.toString());
		}
	}
	
	

}
