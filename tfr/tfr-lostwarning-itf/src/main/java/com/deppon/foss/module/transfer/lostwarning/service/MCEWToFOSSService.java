package com.deppon.foss.module.transfer.lostwarning.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.lostwarning.api.server.dao.ILostWarningDataDao;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.WayBillPkgInfoDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.utils.Utils;
import com.deppon.foss.module.transfer.lostwarning.domain.WayBillPkgInfo;

/**
 * 向丢货预警项目提供接口服务
 * 
 * 项目名称：tfr-lostwarning-itf
 * 
 * 类名称：MCEWToFOSSService
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-7-30 下午3:46:00
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class MCEWToFOSSService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MCEWToFOSSService.class);
	
	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;
	
	private ILostWarningDataDao lostWarningDataDao;
	
	public void setLostWarningDataDao(ILostWarningDataDao lostWarningDataDao) {
		this.lostWarningDataDao = lostWarningDataDao;
	}
	
	//查询部门信息接口 
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}




	/**
	 * @Description: 根据运单号和流水号查询建包信息
	 * @date 2015-7-30 下午3:56:21   
	 * @author 263072 
	 * @param requestStr
	 * @return
	 */
	@POST
	@Produces("application/json;charset=utf-8")
	@Path("/queryWayBillPkg")
	public String queryWayBillPkgInfo(String requestStr){
		LOGGER.info("requestStr>>>>>>" + requestStr);	
		String responseStr = "";
		try{
			//加入ESBcode
			response.setHeader("ESB-ResultCode","1");
			if(!Utils.isStrNull(requestStr)){
				JSONObject obj = JSONObject.fromObject(requestStr); 
	        	//上报状态标示符   0：失败 1：成功
				String wayBillNo = obj.get("wayBillNo").toString();
				//丢货编号
				String serialStr = obj.get("serialList").toString();
				//处理的业务环节
				String bizType = obj.get("bizType").toString();
				
				if(Utils.isStrNull(wayBillNo)||Utils.isStrNull(serialStr)||Utils.isStrNull(bizType)){
					return responseStr;
				}
				
				List<String> serialList = new ArrayList<String>();
				for(String serialNo:serialStr.split(",")){
					serialList.add(serialNo);
				}
				List<WayBillPkgInfoDto> list = lostWarningDataDao.queryWayBillPkgBySerialList(wayBillNo, serialList);
				List<WayBillPkgInfo> resultList = new ArrayList<WayBillPkgInfo>();
				
				/**
				 * 处理业务环节:
				 {"key":"7","value":"营业部-外场建包少货"},
			     {"key":"8","value":"营业部-外场解包少货"},
			     {"key":"9","value":"外场-外场建包少货"},
			     {"key":"10","value":"外场-外场解包少货"},
			     {"key":"11","value":"外场-分部建包少货"},
			     {"key":"12","value":"外场-分部解包丢货"},
			     {"key":"13","value":"外场-营业部建包少货"},
			     {"key":"14","value":"外场-营业部解包少货"}
				 * 其中建包业务环节只获取建包部门信息，解包业务环节获取建包、解包部门信息
				 */
				for(WayBillPkgInfoDto dto :list){
					WayBillPkgInfo bean = new WayBillPkgInfo();
					bean.setFlowCode(dto.getSerialNo());
					bean.setPackageNumber(dto.getPackageNo());
					bean.setPackDeptCode(dto.getDepartOrgCode());
					bean.setPackDeptName(dto.getDepartOrgName());
					bean.setUnpackDeptCode(dto.getArriveOrgCode());
					bean.setUnpackDeptName(dto.getArriveOrgName());
					
					//建包部门信息
					OrgAdministrativeInfoEntity packDeptBean = getDepartmentInfoBycode(dto.getDepartOrgCode());
					//解包部门信息
					OrgAdministrativeInfoEntity unpackDeptBean = getDepartmentInfoBycode(dto.getArriveOrgCode());
					
					//营业部————外场
					if("Y".equals(packDeptBean.getSalesDepartment())&&"Y".equals(unpackDeptBean.getTransferCenter())){
						if("7".equals(bizType)){
							bean.setUnpackDeptCode("");
							bean.setUnpackDeptName("");
							resultList.add(bean);
						}else if("8".equals(bizType)){
							resultList.add(bean);
						}
					}
					//外场————外场
					else if("Y".equals(packDeptBean.getTransferCenter())&&"Y".equals(unpackDeptBean.getTransferCenter())){
						if("9".equals(bizType)){
							bean.setUnpackDeptCode("");
							bean.setUnpackDeptName("");
							resultList.add(bean);
						}else if("10".equals(bizType)){
							resultList.add(bean);
						}
					}
					//外场————分部
					else if("Y".equals(packDeptBean.getTransferCenter())&&"Y".equals(unpackDeptBean.getExpressBranch())){
						if("11".equals(bizType)){
							bean.setUnpackDeptCode("");
							bean.setUnpackDeptName("");
							resultList.add(bean);
						}else if("12".equals(bizType)){
							resultList.add(bean);
						}
					}
					//外场————营业部
					else if("Y".equals(packDeptBean.getTransferCenter())&&"Y".equals(unpackDeptBean.getSalesDepartment())){
						if("13".equals(bizType)){
							bean.setUnpackDeptCode("");
							bean.setUnpackDeptName("");
							resultList.add(bean);
						}else if("14".equals(bizType)){
							resultList.add(bean);
						}
					}
				}
				responseStr = JSONArray.fromObject(resultList).toString();
			}
		}catch (Exception e) {
			LOGGER.error("处理MCEW查询建包信息请求异常:  请求的参数："+requestStr+
					" 异常信息："+ExceptionUtils.getFullStackTrace(e));
		}
		return responseStr;
	}
	
	/**
	 * @Description: 根据部门编码查询部门信息
	 * @date 2015-8-3 上午10:03:18   
	 * @author 263072 
	 * @param code
	 * @return
	 */
	public OrgAdministrativeInfoEntity getDepartmentInfoBycode(String code){
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.
				queryOrgAdministrativeInfoByCode(code);
		return org;
	}

}
