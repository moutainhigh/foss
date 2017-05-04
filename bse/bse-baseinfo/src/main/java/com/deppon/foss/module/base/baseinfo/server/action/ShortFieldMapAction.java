package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IShortFieldMapService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ShortFieldMapEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ShortFieldMapException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ShortFieldMapVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 *  短距离外场映射的Action层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2015-3-26 下午5:11:58,content: </p>
 * @author 232607 
 * @date 2015-3-26 下午5:11:58
 * @since
 * @version
 */
public class ShortFieldMapAction extends AbstractAction {
	/**
	 *  序列化UID
	 */
	private static final long serialVersionUID = 9201036192532364190L;
	/**
	 *  日志
	 */
	private static final Logger LOGGER =LoggerFactory.getLogger(ShortFieldMapAction.class);

	/**
	 *  实现本模块的VO
	 */
	private ShortFieldMapVo shortFieldMapVo;
	/**
	 *  实现本模块的service
	 */
	private IShortFieldMapService shortFieldMapService;
	/**
	 *  这个service用来查询登录人的顶级外场
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * <p> VO的get、set方法用来前后台传输数据，service的set方法用来注入service的Bean</p> 
	 * @author 232607 
	 * @date 2015-3-26 下午3:54:34
	 * @return
	 * @see
	 */
	public ShortFieldMapVo getShortFieldMapVo() {
		return shortFieldMapVo;
	}
	public void setShortFieldMapVo(ShortFieldMapVo shortFieldMapVo) {
		this.shortFieldMapVo = shortFieldMapVo;
	}
	public void setShortFieldMapService(IShortFieldMapService shortFieldMapService) {
		this.shortFieldMapService = shortFieldMapService;
	}
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	/**
	 * <p> 本方法用来查询登陆人的顶级外场</p> 
	 * @author 232607 
	 * @date 2015-3-11 下午2:20:30
	 * @return 顶级外场的部门编号和名称
	 * @see
	 */
	@JSON 
	public String queryTransferCenter(){
		//新建一个字符串集合，存入要查询的组织类型（外场）
		List<String> bizTypesList = new ArrayList<String>();
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//查询登陆人的顶级外场信息，返回该外场的实体对象，查询参数为登录人的部门编号和字符串集合
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoByCode(FossUserContext.getCurrentDeptCode(), bizTypesList);
		//如果没有查到顶级外场信息，则返回给前台“无对应顶级外场信息”
		if(orgAdministrativeInfoEntity==null){
			return returnError("无对应顶级外场信息");
		}
		//新建一个实体，存入查到的顶级外场的部门编号和名称
		ShortFieldMapEntity shortFieldMapEntity=new ShortFieldMapEntity();
		shortFieldMapEntity.setCode(orgAdministrativeInfoEntity.getCode());
		shortFieldMapEntity.setName(orgAdministrativeInfoEntity.getName());
		//将实体放入VO
		shortFieldMapVo.setShortFieldMapEntity(shortFieldMapEntity);
		return returnSuccess();
	}
	/**
	 * <p> 通过所有条件进行分页查询</p> 
	 * @author 232607 
	 * @date 2015-3-26 下午4:02:48
	 * @return 实体集合
	 * @see
	 */
	@JSON
	public String queryShortFieldMapListByCondition() {
		try {
			//调用service层的方法，通过所有条件进行分页查询
			List<ShortFieldMapEntity> shortFieldMapEntityList = shortFieldMapService.queryShortFieldMapListByCondition(shortFieldMapVo.getShortFieldMapEntity(), start, limit);
			//将结果存入VO
			shortFieldMapVo.setShortFieldMapEntityList(shortFieldMapEntityList);
			//调用service层的方法，查询总数
			long totalCount =shortFieldMapService.queryShortFieldMapListByConditionCount(shortFieldMapVo.getShortFieldMapEntity());
			//将总数存入TotalCount
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (ShortFieldMapException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * <p> 批量作废（非物理删除，是将数据的状态改为不可用）</p> 
	 * @author 232607 
	 * @date 2015-3-26 下午4:15:03
	 * @return
	 * @see
	 */
	@JSON
	public String deleteShortFieldMap() {
		try {
			//调用service层的方法，传入ID集合，批量作废数据
			shortFieldMapService.deleteShortFieldMap(shortFieldMapVo.getIds());
			return returnSuccess(MessageType.DELETE_SUCCESS);//删除成功！
		} catch (ShortFieldMapException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * <p> 新增映射关系</p> 
	 * @author 232607 
	 * @date 2015-3-26 下午4:46:25
	 * @return
	 * @see
	 */
	@JSON
	public String addShortFieldMap() {
		try {
			//调用service层的方法，传入实体信息，新增映射关系
			shortFieldMapService.addShortFieldMap(shortFieldMapVo.getShortFieldMapEntity());
			return returnSuccess(MessageType.SAVE_SUCCESS);//保存成功！
		} catch (ShortFieldMapException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * <p> 修改映射关系</p> 
	 * @author 232607 
	 * @date 2015-3-26 下午4:47:32
	 * @return
	 * @see
	 */
	@JSON
	public String updateShortFieldMap() {
		try {
			//调用service层的方法，传入实体信息，修改映射关系
			shortFieldMapService.updateShortFieldMap(shortFieldMapVo.getShortFieldMapEntity());
			return returnSuccess(MessageType.UPDATE_SUCCESS);//更新成功！
		} catch (ShortFieldMapException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
}
