
package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ITitleBaseInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TitleBaseInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.TitleBaseInfoVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 仓库预警短信接收岗位基础资料Action
 * @author dujunhui-187862
 * @date 2014-08-08 下午4:28:47
 * 
*/
public class TitleBaseInfoAction extends AbstractAction {

    /**
	 * TODO（序列化）
	 */
	private static final long serialVersionUID = 6473759635450200793L;

	/**
     * 仓库预警短信接收岗位基础资料Service
     */
    private ITitleBaseInfoService titleBaseInfoService;
    
    /**
     * 仓库预警短信接收岗位基础资料Vo
     */
    private TitleBaseInfoVo titleBaseInfoVo;
    
	/**
	 * @param titleBaseInfoService the titleBaseInfoService to set
	 */
	public void setTitleBaseInfoService(ITitleBaseInfoService titleBaseInfoService) {
		this.titleBaseInfoService = titleBaseInfoService;
	}

	/**
	 * @return  the titleBaseInfoVo
	 */
	public TitleBaseInfoVo getTitleBaseInfoVo() {
		return titleBaseInfoVo;
	}

	/**
	 * @param titleBaseInfoVo the titleBaseInfoVo to set
	 */
	public void setTitleBaseInfoVo(TitleBaseInfoVo titleBaseInfoVo) {
		this.titleBaseInfoVo = titleBaseInfoVo;
	}




	/**
	 * 新增信息
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-08 下午4:33:47
	*/
	@JSON
	public String addTitleBaseInfo() {
		try {
			if(StringUtil.isEmpty(titleBaseInfoVo.getTitleBaseInfoEntity().getReceiveTitleCode())){
				return returnError("该部门下不存在岗位，不允许空岗位保存！");
			}
			
			List<TitleBaseInfoEntity> queryList=titleBaseInfoService.queryTitleBaseInfoEntityByCondition(titleBaseInfoVo.
					getTitleBaseInfoEntity(), 1, 0);
			if(queryList.size()>0){
				return returnError("该条记录已存在！");
			}
			
			String userCode = FossUserContext.getCurrentInfo().getEmpCode();// 当前登录用户empCode
			
			titleBaseInfoVo.getTitleBaseInfoEntity().setCreateUser(userCode);
			titleBaseInfoVo.getTitleBaseInfoEntity().setCreateDate(new Date());
			
			titleBaseInfoService.addTitleBaseInfo(titleBaseInfoVo.getTitleBaseInfoEntity());
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 删除信息
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-08 下午4:50:13
	*/
	@JSON
	public String deleteTitleBaseInfo() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户empCode
			
			List<String> codeList=titleBaseInfoVo.getCodeList();
			String[] codeArray=new String[codeList.size()];
			codeList.toArray(codeArray);

			titleBaseInfoService.deleteTitleBaseInfo(codeArray, userCode);
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 修改信息
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-08 下午4:48:56
	*/
	@JSON
	public String updateTitleBaseInfo() {
		try {
			if(StringUtil.isEmpty(titleBaseInfoVo.getTitleBaseInfoEntity().getReceiveTitleCode())){
				return returnError("该部门下不存在岗位，不允许空岗位保存！");
			}
			
			String userCode = FossUserContext.getCurrentInfo().getEmpCode();// 当前登录用户empCode
			titleBaseInfoVo.getTitleBaseInfoEntity().setModifyUser(userCode);
			titleBaseInfoService.updateTitleBaseInfo(titleBaseInfoVo.getTitleBaseInfoEntity());
			
			List<TitleBaseInfoEntity> queryList=titleBaseInfoService.queryTitleBaseInfoEntityByCondition(titleBaseInfoVo.
					getTitleBaseInfoEntity(), 1, 0);
			if(queryList.size()>0){
				return returnError("该条记录已存在！");
			}
			
			titleBaseInfoVo.getTitleBaseInfoEntity().setCreateUser(userCode);
			titleBaseInfoVo.getTitleBaseInfoEntity().setCreateDate(new Date());
			
			titleBaseInfoService.addTitleBaseInfo(titleBaseInfoVo.getTitleBaseInfoEntity());
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 根据条件查询信息
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-08 下午4:55:22
	*/
	@JSON
	public String queryTitleBaseInfo() {
		try {
			TitleBaseInfoEntity queryEntity=titleBaseInfoVo.getTitleBaseInfoEntity();
			List<TitleBaseInfoEntity> resultlist=titleBaseInfoService.
					queryTitleBaseInfoEntityByCondition(queryEntity, limit, start);
			titleBaseInfoVo.setTitleBaseInfoEntityList(resultlist);
			totalCount=titleBaseInfoService.queryRecordCount(titleBaseInfoVo.getTitleBaseInfoEntity());
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 根据ID查询信息详情
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-08 下午4:59:41
	*/
	@JSON
	public String queryTitleBaseInfoByID() {
		try {
			titleBaseInfoVo.setTitleBaseInfoEntity(titleBaseInfoService.
					queryTitleBaseInfoEntityByID(titleBaseInfoVo.
							getTitleBaseInfoEntity().getId()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	    
}
