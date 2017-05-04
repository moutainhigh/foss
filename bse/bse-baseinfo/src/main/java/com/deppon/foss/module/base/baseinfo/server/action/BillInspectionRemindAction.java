package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillInspectionRemindEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.BillInspectionRemindVo;
import com.deppon.foss.module.base.baseinfo.server.service.impl.BillInspectionRemindService;

public class BillInspectionRemindAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2942079498618571932L;
	
	private BillInspectionRemindVo objectVo=new BillInspectionRemindVo();
	
	private BillInspectionRemindService inspectionRemindService;
	
	public void setInspectionRemindService(
			BillInspectionRemindService inspectionRemindService) {
		this.inspectionRemindService = inspectionRemindService;
	}
	
	/**
	 * 查询记录并分页
	 * @param entity
	 * @return
	 */
	@JSON
	public String queryBillInspectionRemind(){
		List<BillInspectionRemindEntity> billInspectionRemindEntitys=inspectionRemindService.queryAll(objectVo.getBillInspectionRemindEntity(), start, limit);
		objectVo.setBillInspectionRemindEntitys(billInspectionRemindEntitys);
		this.setTotalCount(inspectionRemindService.queryCount(objectVo.getBillInspectionRemindEntity()));
		return returnSuccess();
	}

	/**
	 * 删除记录
	 * @param entity
	 * @return
	 */
	@JSON
	public String deleteBillInspectionRemind(){
		try{
		inspectionRemindService.deleteBillInspectionRemind(objectVo.getIds());
		}catch (Exception e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	/**
	 * 更新记录
	 * @param entity
	 * @return
	 */
	@JSON
	public String updateBillInspectionRemind(){
		try{
		inspectionRemindService.updateBillInspectionRemind(objectVo.getBillInspectionRemindEntity());
		}catch (Exception e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	/**
	 * 新增记录
	 * @param entity
	 * @return
	 */
	@JSON
	public String addBillInspectionRemind(){
		try{
		inspectionRemindService.addBillInspectionRemind(objectVo.getBillInspectionRemindEntity());
		}catch (Exception e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}

	/**
	 * 
	 */
	public BillInspectionRemindVo getObjectVo() {
		return objectVo;
	}

	/**
	 * 
	 * @param objectVo
	 */
	public void setObjectVo(BillInspectionRemindVo objectVo) {
		this.objectVo = objectVo;
	}

	
	

}
