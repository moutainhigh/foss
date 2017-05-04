package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDeptTransferMappingService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DeptTransferMappingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DeptTransferMappingDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.DeptTransferMappingException;
import com.deppon.foss.util.CollectionUtils;

/**
 * 营业部交接映射管理实体类
 * @author 273296
 *
 */
public class DeptTransferMappingAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DeptTransferMappingAction.class);
	
	private DeptTransferMappingDto vo=new DeptTransferMappingDto();

	public DeptTransferMappingDto getVo() {
		return vo;
	}

	public void setVo(DeptTransferMappingDto vo) {
		this.vo = vo;
	}
	
	private IDeptTransferMappingService deptTransferMappingService;
	

	public void setDeptTransferMappingService(
			IDeptTransferMappingService deptTransferMappingService) {
		this.deptTransferMappingService = deptTransferMappingService;
	}
	
	/**
	 * 查询营业部 交接映射管理数据
	 *273296
	 * @return
	 */
	@JSON
	public String queryDeptTransferMappingList(){
		try{
			List<DeptTransferMappingEntity>	entitys=deptTransferMappingService.queryDeptTransferMappingList(vo,start, limit);
			long totalCount = deptTransferMappingService.queryDeptTransferMappingCount(vo);
			this.setTotalCount(totalCount);
			vo.setDeptTransferMappingList(entitys);
			if(CollectionUtils.isEmpty(entitys)){
				return returnSuccess("未查询到数据");
			}else{
				return returnSuccess();
			}
		}catch (DeptTransferMappingException e) {
			LOGGER.error(e.getMessage());
			return returnError("查询失败");
		}
		
	}
	
	/**
	 * 作废营业部 交接映射管理数据
	 *273296
	 * @return
	 */
	@JSON
	public String deleteDeptTransferMappingById(){
		try{
			long count=deptTransferMappingService.deleteDeptTransferMappingById(vo.getId());
			if(count>0){
				return returnSuccess("作废成功");
			}else{
				return returnError("作废失败");
			}
		}catch (DeptTransferMappingException e) {
			LOGGER.error(e.getMessage());
			return returnError("查询失败");
		}
	}
	
	public String deleteDeptTransferMappingsByIdList(){
		try{
			long count=deptTransferMappingService.deleteDeptTransferMappingsByIdList(vo.getIdList());
			if(count>0){
				return returnSuccess("作废成功");
			}else{
				return returnError("作废失败");
			}
		}catch (DeptTransferMappingException e) {
			LOGGER.error(e.getMessage());
			return returnError("查询失败");
		}
	}
	
	/**
	 *新增 营业部 交接映射管理
	 *273296
	 * @param vo
	 * @return
	 */
	@JSON
	public String addDeptTransferMapping(){
		try {
			deptTransferMappingService.addDeptTransferMapping(vo
					.getDeptTransferMappingList());
			return returnSuccess("保存成功");
		} catch (DeptTransferMappingException e) {
			LOGGER.error(e.getMessage());
			return returnError("保存失败");
		}
	}
	
	/**
	 *修改 营业部 交接映射管理
	 *273296
	 * @param vo
	 * @return
	 */
	@JSON
	public String updateDeptTransferMapping(){
		try {
			deptTransferMappingService.updateDeptTransferMapping(vo);
			return returnSuccess("修改成功");
		} catch (DeptTransferMappingException e) {
			LOGGER.error(e.getMessage());
			return returnError("保存失败");
		}
	}
	
	/**
	 * 根据部门code查询是否外场
	 *273296
	 * @param vo
	 * @return
	 */
	@JSON
	public String checkIsOutField(){
		try {

			String outField = deptTransferMappingService.findOutFieldByCode(vo
					.getDeptCode());
			if (outField == null || outField.equals("")) {
				outField = "N";
			}
			vo.setIsOutfield(outField);
			return returnSuccess();
		} catch (DeptTransferMappingException e) {
			LOGGER.error(e.getMessage());
			return returnError(e.getMessage());
		}
	}
	
	/**
	 * 根据部门code,一级网点查找记录 
	 *273296
	 * @param vo
	 * @return
	 */
	@JSON
	public String queryDeptTransferMappingListByDeptCodeAndFthNetwork(){
		try{
			List<DeptTransferMappingEntity>	entitys=deptTransferMappingService.queryDeptTransferMappingListByDeptCodeAndFthNetwork(vo);
			vo.setDeptTransferMappingList(entitys);
			if(CollectionUtils.isEmpty(entitys)){
				return returnSuccess("未查询到数据");
			}else{
				return returnSuccess();
			}
		}catch (DeptTransferMappingException e) {
			LOGGER.error(e.getMessage());
			return returnError("查询失败");
		}
		
	}
	
	
	/**
	 * 根据一级网点查找记录 是否存在
	 *273296
	 * @param vo
	 * @return
	 */
	@JSON
	public String queryDeptTransferMappingModelByFthNetworkCode(){
		try{
		long count=deptTransferMappingService.queryDeptTransferMappingModelByFthNetworkCode(vo.getFthNetworkCode());
			if(count>0){
				vo.setIsExists(true);
			}else{
				vo.setIsExists(false);
			}
			return returnSuccess();
		}catch (DeptTransferMappingException e) {
			LOGGER.error(e.getMessage());
			return returnError(e.getMessage());
		}
		
	}
	
	
	/**
	 * 根据营业部和一级网点作废记录
	 *273296
	 * @return
	 */
	@JSON
	public String deleteDeptTransferMappingsByDeptCodeAndFthNetworkCode(){
		try{
			long count=deptTransferMappingService.deleteDeptTransferMappingsByDeptCodeAndFthNetworkCode(vo);
			if(count>0){
				return returnSuccess("作废成功");
			}else{
				return returnError("作废失败");
			}
		}catch (DeptTransferMappingException e) {
			LOGGER.error(e.getMessage());
			return returnError("查询失败");
		}
	}
	
	/**
	 * 查询是否存在二级网点
	 *273296
	 * @return
	 */
	@JSON
	public String queryDeptTransferMappingModelBySecNetworkCode(){
		try{
			long count=deptTransferMappingService.queryDeptTransferMappingModelBySecNetworkCode(vo.getSecNetworkCode());
			if(count>0){
				vo.setIsExists(true);
			}else{
				vo.setIsExists(false);
			}
			return returnSuccess();
		}catch (DeptTransferMappingException e) {
			LOGGER.error(e.getMessage());
			return returnError(e.getMessage());
		}
	}

}
