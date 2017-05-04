package com.deppon.foss.module.transfer.stock.server.action;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.stock.api.server.service.IFindGoodsAdminService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.FindGoodsAdminDetailEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.FindGoodsAdminEntity;
import com.deppon.foss.module.transfer.stock.api.shared.vo.FindGoodsAdminVo;
/**
 * 
* @ClassName: FindGoodsAdminAction
* @Description: 找货管理 Action
* @author 189284--ZX
* @date 2015-7-11 下午3:55:32
*
 */
public class FindGoodsAdminAction extends AbstractAction {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = 1L;
	
	private FindGoodsAdminVo findGoodsAdminVo=new FindGoodsAdminVo();
	
	private IFindGoodsAdminService findGoodsAdminService;
	
	/**
	 * 获取 
	 * @return findGoodsAdminVo
	 */
	public FindGoodsAdminVo getFindGoodsAdminVo() {
		return findGoodsAdminVo;
	}
	/**
	 * 设置 
	 * @param findGoodsAdminVo the findGoodsAdminVo to set
	 */
	public void setFindGoodsAdminVo(FindGoodsAdminVo findGoodsAdminVo) {
		this.findGoodsAdminVo = findGoodsAdminVo;
	}
	/**
	 * 设置 
	 * @param findGoodsAdminService the findGoodsAdminService to set
	 */
	public void setFindGoodsAdminService(
			IFindGoodsAdminService findGoodsAdminService) {
		this.findGoodsAdminService = findGoodsAdminService;
	}
	/**
	 * 
	* @Title: qureyFindGoodsAdmin
	* @Description: 查询 找货管理信息 分页
	* @author 189284--ZX
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@JSON
	public String qureyFindGoodsAdmin(){
		try {
			FindGoodsAdminEntity findGoodsAdminEntity=findGoodsAdminVo.getFindGoodsAdminEntity();
			List<FindGoodsAdminEntity> findGoodsAdminEntitys=
					findGoodsAdminService.qureyFindGoodsAdmin(findGoodsAdminEntity, start, limit);
			findGoodsAdminVo.setFindGoodsAdminEntitys(findGoodsAdminEntitys);
			totalCount=findGoodsAdminService.qureyFindGoodsAdminCount(findGoodsAdminEntity);
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: querySuperiorOrgByOrgCode
	* @Description: 根据当前部门code获取上级外场  或者营业部
	* @author 189284--ZX
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@JSON
	public String querySuperiorOrgByOrgCode() {
		try{
			//当前部门Code
			String currentDeptCode = FossUserContext.getCurrentDeptCode();
			//当前部门顶级组织
			OrgAdministrativeInfoEntity administrativeInfo = findGoodsAdminService.querySuperiorOrgByOrgCode(currentDeptCode);
			//当前部门顶级组织code
			//String orgCode = administrativeInfo.getCode();
			findGoodsAdminVo.setAdministrativeInfo(administrativeInfo);
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	 /**
	  * 
	 * @Title: queryFindGoodsAdminDetail
	 * @Description: 根据 任务编号 查询找货管理明细
	 * @author 189284--ZX
	 * @param @param taskNo
	 * @param @return    设定文件
	 * @return List<FindGoodsAdminDetailEntity>    返回类型
	 * @throws
	  */
	@JSON
	public String queryFindGoodsAdminDetail(){
		try {
			String taskNo=findGoodsAdminVo.getTaskNo();
			List<FindGoodsAdminDetailEntity> findGoodsAdminDetailEntitys=findGoodsAdminService.qureyFGoodsDetailBytaskNo(taskNo);
			findGoodsAdminVo.setFindGoodsAdminDetailEntitys(findGoodsAdminDetailEntitys);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 
	* @Title: commitFindGoodsAdmin
	* @Description: 根据 任务编号 提交找货管理信息
	* @author 189284--ZX
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@JSON
	public String commitFindGoodsAdmin(){
		try {
			 String taskNo=findGoodsAdminVo.getTaskNo();
			 String user= FossUserContext.getCurrentInfo().getEmpCode();
			 findGoodsAdminService.commitFindGoodsAdmin(taskNo,user);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
}
