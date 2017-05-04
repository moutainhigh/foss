package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressPrintStarVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * TODO(ExpressPrintStar的Action)
 * @author 187862-dujunhui
 * @date 2014-5-22 上午10:09:40
 * @since
 * @version
 */
public class ExpressPrintStarAction extends AbstractAction {

	
	private static final long serialVersionUID = 38527124814229622L;

	private ExpressPrintStarVo expressPrintStarVo;
	
	private IExpressPrintStarService expressPrintStarService;
	
	
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * @return  the expressPrintStarVo
	 */
	public ExpressPrintStarVo getExpressPrintStarVo() {
		return expressPrintStarVo;
	}

	/**
	 * @param expressPrintStarVo the expressPrintStarVo to set
	 */
	public void setExpressPrintStarVo(ExpressPrintStarVo expressPrintStarVo) {
		this.expressPrintStarVo = expressPrintStarVo;
	}

	/**
	 * @param expressPrintStarService the expressPrintStarService to set
	 */
	public void setExpressPrintStarService(
			IExpressPrintStarService expressPrintStarService) {
		this.expressPrintStarService = expressPrintStarService;
	}
	
	/**
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 */
	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * <p>新增信息</p>
	 * @author 187862-dujunhui
	 * @date 2014-5-22 上午10:16:22
	 * @since 
	 */
	@JSON
	public String addExpressPrintStar() {
		try {
			ExpressPrintStarEntity addEntity = expressPrintStarVo.getExpressPrintStarEntity();
			//如果星标编码为空则设定为N
			if(StringUtils.isBlank(addEntity.getAsteriskCode())){
				addEntity.setAsteriskCode(FossConstants.NO);
			}else{  //如果星标编码不为空则设定为1号线
				addEntity.setAsteriskCode(DictionaryValueConstants.ASTERISK_TYPE_LINE1);
			}
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户
			expressPrintStarVo.getExpressPrintStarEntity().setCreateUser(userCode);
			expressPrintStarVo.getExpressPrintStarEntity().setOrganizationCode("W2400020730");
			expressPrintStarVo.getExpressPrintStarEntity().setTransferCode("D16");
			expressPrintStarService.addExpressPrintStar(expressPrintStarVo.getExpressPrintStarEntity());
			return returnSuccess(MessageType.SAVE_EXPRESSPRINTSTAR_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * <p>批量作废信息</p>
	 * @author 187862-dujunhui
	 * @date 2014-5-22 上午10:27:13
	 * @since 
	 */
	@JSON
	public String deleteExpressPrintStar() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户
			expressPrintStarService.deleteExpressPrintStars(expressPrintStarVo.getExpressPrintStarVirtualCodes(), userCode);
			return returnSuccess(MessageType.DELETE_EXPRESSPRINTSTAR_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * <p>修改库区信息</p>
	 * @author 187862-dujunhui
	 * @date 2014-5-22 上午10:46:46
	 * @since 
	 */
	@JSON
	public String updateExpressPrintStar() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户
			if(StringUtils.isBlank(expressPrintStarVo.getExpressPrintStarEntity().getAsteriskCode())){
				expressPrintStarVo.getExpressPrintStarEntity().setAsteriskCode(FossConstants.NO);
			}else{  //如果星标编码不为空则设定为1号线
				expressPrintStarVo.getExpressPrintStarEntity().setAsteriskCode(DictionaryValueConstants.ASTERISK_TYPE_LINE1);
			}
			expressPrintStarVo.getExpressPrintStarEntity().setModifyUser(userCode);
			expressPrintStarVo.getExpressPrintStarEntity().setOrganizationCode("W2400020730");
			expressPrintStarVo.getExpressPrintStarEntity().setTransferCode("D16");
			expressPrintStarService.updateExpressPrintStar(expressPrintStarVo.getExpressPrintStarEntity());
			return returnSuccess(MessageType.UPDATE_EXPRESSPRINTSTAR_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * <p>查询库区详细信息</p>
	 * @author 187862-dujunhui
	 * @date 2014-5-22 上午10:52:22
	 * @since 
	 */
	@JSON
	public String queryExpressPrintStarByVirtualCode() {
		try {
			ExpressPrintStarEntity queryEntity = expressPrintStarService.
					queryExpressPrintStarByVirtualCode(expressPrintStarVo.getExpressPrintStarEntity().getVirtualCode());
			if(StringUtil.isNotEmpty(queryEntity.getArriveRegionCode())){
			    queryEntity.setArriveRegionName(orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(queryEntity.getArriveRegionCode()).getName());
			    }
			else queryEntity.setArriveRegionName("");
			queryEntity.setTransferCode("D16");
			queryEntity.setOrganizationCode("W2400020730");
			queryEntity.setOrganizationName("青岛转运中心");
			expressPrintStarVo.setExpressPrintStarEntity(queryEntity);
			return returnSuccess(MessageType.SAVE_EXPRESSPRINTSTAR_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * <p>根据条件查询所有的库区</p>
	 * @author 187862-dujunhui
	 * @date 2014-5-22 上午10:58:35
	 * @since 
	 */
	@JSON
	public String queryExpressPrintStarByCondition() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户
			String currentOrgCode = user.getEmployee().getOrgCode();
			ExpressPrintStarEntity myEntity=expressPrintStarVo.getExpressPrintStarEntity();
			myEntity.setOrganizationCode("W2400020730");
			myEntity.setTransferCode("D16");
			this.setTotalCount(expressPrintStarService.countExpressPrintStarByCondition(myEntity,userCode, currentOrgCode));
			List<ExpressPrintStarEntity> expressPrintStarEntityList = expressPrintStarService.queryExpressPrintStarByCondition(myEntity, start, limit,userCode, currentOrgCode);
			for(ExpressPrintStarEntity entity:expressPrintStarEntityList){
				if(StringUtil.isNotEmpty(entity.getArriveRegionCode())){
				OrgAdministrativeInfoEntity infoEntity=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getArriveRegionCode());
				String arriveRegionName= infoEntity.getName();
			    entity.setArriveRegionName(arriveRegionName);
				}
				else entity.setArriveRegionName("");
			    entity.setTransferCode("D16");
			    entity.setOrganizationCode("W2400020730");
			    entity.setOrganizationName("青岛转运中心");
			}
			expressPrintStarVo.setExpressPrintStarEntityList(expressPrintStarEntityList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
}
