package com.deppon.pda.bdm.module.foss.load.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.service.IDeptTransferMappingService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DeptTransferMappingEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.shared.domain.SalesDepartmentAreaEntity;
import com.deppon.pda.bdm.module.foss.load.shared.domain.SalesDepartmentAreaEntitys;
/**
 * @description 快递员当前城市的所有营业区及对应营业部
 * @author 268974
 * @Date:2015-12-17 comment:2016-01-05日常版本需求
 */
public class QueryCourierAllSalesDepartmentService implements IBusinessService<List<SalesDepartmentAreaEntitys>, SalesDepartmentAreaEntity>{
	//调用综合方法
	IDeptTransferMappingService deptTransferMappingService;
	
	@Override
	public SalesDepartmentAreaEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		SalesDepartmentAreaEntity kdAreaEntity = JsonUtil.parseJsonToObject(SalesDepartmentAreaEntity.class, asyncMsg.getContent());
		return kdAreaEntity;
	}

	
	@Override
	public List<SalesDepartmentAreaEntitys> service(AsyncMsg asyncMsg,
			SalesDepartmentAreaEntity param) throws PdaBusiException {
		List<SalesDepartmentAreaEntitys> areas = new ArrayList<SalesDepartmentAreaEntitys>();
		try {
			String areaCode = param.getCurDeptCode();
			if (areaCode != null && areaCode.length() != 0) {
				List<DeptTransferMappingEntity> list=deptTransferMappingService.queryDeptTransferMappingListByCode(areaCode);
				if(list!=null&&!list.isEmpty()){
					match(areas,areaCode,list);
				}
			}
			return areas;
		} catch (Exception e) {
			throw new FossInterfaceException(null, "数据库查询出现异常");
		}
	}

	//匹配出所有的网点
	private void match(List<SalesDepartmentAreaEntitys> areas, String areaCode, List<DeptTransferMappingEntity> list) {
		// TODO Auto-generated method stub
		int type=-1;
		for(DeptTransferMappingEntity found:list){
			if(areaCode.equals(found.getDeptCode())){
				type=0;
				break;
			}else if(areaCode.equals(found.getFthNetworkCode())){
				type=1;
				break;
			}else if(areaCode.equals(found.getSecNetworkCode())){
				type=2;
				break;
			}
		}
		//转换实体类
		SalesDepartmentAreaEntitys sdae;
		if(type==0){
			for(DeptTransferMappingEntity found:list){
				if(areaCode.equals(found.getDeptCode())&&"N".equals(found.getIsOutfield())
						&&found.getFthNetworkCode()!=null){
					sdae=new SalesDepartmentAreaEntitys();
					sdae.setOrgCode(found.getFthNetworkCode());
					sdae.setOrgName(found.getFthNetworkName());
					areas.add(sdae);
				}
			}
		}else if(type==1){
			for(DeptTransferMappingEntity found:list){
				if(areaCode.equals(found.getFthNetworkCode())){
					if(found.getDeptCode()!=null&&"N".equals(found.getIsOutfield())){
						sdae=new SalesDepartmentAreaEntitys();
						sdae.setOrgCode(found.getDeptCode());
						sdae.setOrgName(found.getDeptName());
						areas.add(sdae);
					}
					if(found.getSecNetworkCode()!=null){
						sdae=new SalesDepartmentAreaEntitys();
						sdae.setOrgCode(found.getSecNetworkCode());
						sdae.setOrgName(found.getSecNetworkName());
						areas.add(sdae);
					}
				}
			}
		}else if(type==2){
			for(DeptTransferMappingEntity found:list){
				if(areaCode.equals(found.getSecNetworkCode())&&found.getFthNetworkCode()!=null){
					sdae=new SalesDepartmentAreaEntitys();
					sdae.setOrgCode(found.getFthNetworkCode());
					sdae.setOrgName(found.getFthNetworkName());
					areas.add(sdae);
				}
			}
		}
	}


	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_SALES_DEPARTMENT_QUDE_YINGYEQU_YINGYEBU.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}


	public IDeptTransferMappingService getDeptTransferMappingService() {
		return deptTransferMappingService;
	}


	public void setDeptTransferMappingService(
			IDeptTransferMappingService deptTransferMappingService) {
		this.deptTransferMappingService = deptTransferMappingService;
	}
}
