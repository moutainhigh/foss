package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFocusRecordManagementService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FocusRecordManagementEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FocusRecordManagementException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.FocusRecordManagementVo;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class FocusRecordManagementAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9201036192532364532L;
	
	private static final Logger LOGGER =LoggerFactory.getLogger(FocusRecordManagementAction.class);
	
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	private IFocusRecordManagementService focusRecordManagementService;
	
	private FocusRecordManagementVo focusRecordManagementVo;

	/**
	 * get set 方法
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public FocusRecordManagementVo getFocusRecordManagementVo() {
		return focusRecordManagementVo;
	}

	public void setFocusRecordManagementVo(
			FocusRecordManagementVo focusRecordManagementVo) {
		this.focusRecordManagementVo = focusRecordManagementVo;
	}

	public void setFocusRecordManagementService(
			IFocusRecordManagementService focusRecordManagementService) {
		this.focusRecordManagementService = focusRecordManagementService;
	}
	/**
	 * <p>分页查询</p> 
	 * @author 232608
	 * @date 2015-6-24 下午3:54:34
	 * @return
	 * @see
	 */
	public String queryFocusRecordManagementList(){
		try {		
			/**
			 * 查询出总数据 因为一个开单组对应多个营业部 在表中存的时候分开存的 但分页的时候只算一条，
			 * 因为在前台grid中显示的时候营业部都被拼接起来了,所以后台查询的时候要group by（去重）
			 */
			List<FocusRecordManagementEntity> entityList=
					focusRecordManagementService.queryFocusRecordManagementList(focusRecordManagementVo.getEntity(),start,limit);
			//遍历查询出来的集合，为每个部门添加name，自己的表里面不存name 所有name都去组织表中查询！防止部门名称变动，那个时候只会改组织表中的name
			for(FocusRecordManagementEntity entity:entityList){
				if(entity==null){
					continue;
				}
				//此操作是为了获取开单组的name
				OrgAdministrativeInfoEntity billingGroup=
						orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(entity.getBillingGroupCode());
				//获取车队name
				OrgAdministrativeInfoEntity transDepartment=
						orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(entity.getTransDepartmentCode());
				
				entity.setBillingGroupName(billingGroup.getName());
				
				entity.setTransDepartmentName(transDepartment.getName());
				//entity.setSalesDepartmentName(salesDepartment.getName());
				//营业部name用stringBuffer或StringBuilder进行拼接，不要用String 具体为什么请百度！！
				StringBuffer salesDepartmentCodeStr=new StringBuffer();
				StringBuffer salesDepartmentNameStr=new StringBuffer();
				StringBuffer idStr=new StringBuffer();
				//根据开单组code查询出所有的数据 不分组 ，不去重，为的就是将后台的所有营业部全部查出来
				List<FocusRecordManagementEntity> entities=
						focusRecordManagementService.queryFocusRecordManagementByCode(entity.getBillingGroupCode());
					if(entities!=null){
						for(int i=0;i<entities.size();i++){
							
							salesDepartmentCodeStr.append(entities.get(i).getSalesDepartmentCode());
							idStr.append(entities.get(i).getId());
							//获取营业部的name
							OrgAdministrativeInfoEntity salesDepartment=
									orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(entities.get(i).getSalesDepartmentCode());
							if(salesDepartment!=null){
								salesDepartmentNameStr.append(salesDepartment.getName());
							}
							if(i!=entities.size()-1){
								salesDepartmentCodeStr.append(",");
								idStr.append(",");
								salesDepartmentNameStr.append(",");
							}
						}
						//将数据全部塞进实体
						entity.setBillingAmount(entities.get(0).getBillingAmount());
						entity.setCreateDate(entities.get(0).getCreateDate());
						entity.setCreateUser(entities.get(0).getCreateUser());
						entity.setStartDate(entities.get(0).getStartDate());
						entity.setEndDate(entities.get(0).getEndDate());
					}
					entity.setId(idStr.toString());
					entity.setSalesDepartmentName(salesDepartmentNameStr.toString());
					entity.setSalesDepartmentCode(salesDepartmentCodeStr.toString());
			}
			focusRecordManagementVo.setEntityList(entityList);
			//查询总数用于分页
			long totalCount = focusRecordManagementService.queryCount(focusRecordManagementVo.getEntity());
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (FocusRecordManagementException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * <p>新增操作</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	@JSON
	public String addFocusRecordManagement(){
		try {
			focusRecordManagementService.addFocusRecordManagement(focusRecordManagementVo.getEntity());
			return returnSuccess("保存成功");
		} catch (FocusRecordManagementException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * <p>删除操作</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	@JSON
	public String deleteFocusRecordManagement() {
		try {
			//调用service层的方法，传入ID集合，批量作废数据
			focusRecordManagementService.deleteFocusRecordManagement(focusRecordManagementVo.getIds());
			return returnSuccess(MessageType.DELETE_SUCCESS);//删除成功！
		} catch (FocusRecordManagementException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * <p>修改操作</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	@JSON
	public String updateFocusRecordManagement() {
		try {
			//调用service层的方法
			focusRecordManagementService.updateFocusRecordManagement(focusRecordManagementVo.getEntity());
			return returnSuccess("修改成功");
		} catch (FocusRecordManagementException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
}
