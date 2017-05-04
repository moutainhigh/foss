package com.deppon.foss.module.base.baseinfo.server.action;


import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPartnerRelationService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PartnerRelationEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PartnerRelationVo;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
/**
 * 
 * 网点映射关系
 * @author 308861 
 * @date 2016-9-8 上午8:46:39
 * @since
 * @version
 */
public class PartnerRelationAction extends AbstractAction{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -2457717831510656384L;	
	/**
	 * 日志
	 */
	private static final Logger LOGGER =LoggerFactory.getLogger(PartnerRelationAction.class);
	/**
	 * 前后台交互
	 */
	private PartnerRelationVo vo;
	
	public PartnerRelationVo getVo() {
		return vo;
	}

	public void setVo(PartnerRelationVo vo) {
		this.vo = vo;
	}
	/**
	 * 注入partnerRelationService
	 */
	private IPartnerRelationService partnerRelationService;

	public void setPartnerRelationService(
			IPartnerRelationService partnerRelationService) {
		this.partnerRelationService = partnerRelationService;
	}
	
	/**
	 * 
	 * 新增
	 * @author 308861 
	 * @date 2016-8-25 上午9:58:29
	 * @return
	 * @see
	 */
	@JSON
	public String addNetworkMappingServiceDate(){
		try {
			//获取新增参数
			PartnerRelationEntity addRelation=vo.getPartnerRelationEntity();
			//执行新增
			partnerRelationService.addPartnerRelation(addRelation);
			return returnSuccess("添加成功");
		} catch (Exception e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	
	/**
	 * 
	 * 修改 
	 * @author 308861 
	 * @date 2016-9-2 下午2:47:54
	 * @return
	 * @see
	 */
	@JSON
	public String updateNetworkMappingServiceDate(){
		try {
			//获取修改参数
			PartnerRelationEntity updateEntiy=vo.getPartnerRelationEntity();
			//执行修改
			partnerRelationService.updatePartnerRelation(updateEntiy);
			return returnSuccess("修改成功");
		} catch (Exception e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage(),e);
		}
	}
	
	/**
	 * 
	 * 作废网点映射关系 
	 * @author 308861 
	 * @date 2016-8-29 上午11:27:47
	 * @return
	 * @see
	 */
	@JSON
	public String deleteNetworkMappingServiceDate(){
		//获取作废参数
		List<String> ids=vo.getIdList();
		try {
			//执行作废
			partnerRelationService.deletePartnerRelation(ids);
			return returnSuccess("网点映射关系作废成功");
		} catch (Exception e) {
			return returnError(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * 条件查询网点映射关系 
	 * @author 308861 
	 * @date 2016-8-25 上午9:35:08
	 * @return
	 * @see
	 */
	@JSON
	public String queryNetworkMappingServiceDateList(){
		try {
			//获取查询条件
			PartnerRelationEntity entity=vo.getPartnerRelationEntity();
			//前后台交互的集合
			vo.setPartnerRelationList(partnerRelationService.
					queryPartnerRelationByEntity(entity, start, limit));
			//总页数
			long totalCount=partnerRelationService.
					queryPartnerRelationByEntityCount(entity);
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 * 根据一级网点编码查询所属子公司（名称 编码） 
	 * @author 308861 
	 * @date 2016-8-25 下午3:23:40
	 * @return
	 * @see
	 */
	@JSON
	public String getSubCompanyNameByOneCode(){
		try {
			//查询
			if(vo.getOneVo()!=null){
				PartnerRelationEntity entity=partnerRelationService.getSubCompanyNameByCode(vo.getOneVo().getCode());
				//查询结果包含一级网点所属子公司和所属子公司编码
				vo.getOneVo().setEntityByOneCode(entity);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	/**
	 * 
	 * 根据二级网点编码查询所属子公司（名称 编码）  
	 * @author 308861 
	 * @date 2016-8-26 下午2:37:07
	 * @return
	 * @see
	 */
	@JSON
	public String getSubCompanyNameByTwoCode(){
		try {
			if(vo.getTwoVo()!=null){
				PartnerRelationEntity entity=partnerRelationService.
						getSubCompanyNameByCode(vo.getTwoVo().getCode());
				//查询结果包含二级网点所属子公司和子公司编码
				vo.getTwoVo().setEntityByTwoCode(entity);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
}
