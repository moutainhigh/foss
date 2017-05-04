
package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPrintMarketingContentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PrintMarketingContentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PrintMarketingContentVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 打印营销内容基础资料Action
 * @author dujunhui-187862
 * @date 2014-08-26 下午2:32:47
 * 
*/
public class PrintMarketingContentAction extends AbstractAction {

    /**
	 * TODO（序列化）
	 */
	private static final long serialVersionUID = 6473759635450200793L;

	/**
     * 打印营销内容基础资料Service
     */
    private IPrintMarketingContentService printMarketingContentService;
    
    /**
     * 打印营销内容基础资料Vo
     */
    private PrintMarketingContentVo printMarketingContentVo;
    
	/**
	 * @return  the printMarketingContentVo
	 */
	public PrintMarketingContentVo getPrintMarketingContentVo() {
		return printMarketingContentVo;
	}

	/**
	 * @param printMarketingContentVo the printMarketingContentVo to set
	 */
	public void setPrintMarketingContentVo(
			PrintMarketingContentVo printMarketingContentVo) {
		this.printMarketingContentVo = printMarketingContentVo;
	}

	/**
	 * @param printMarketingContentService the printMarketingContentService to set
	 */
	public void setPrintMarketingContentService(
			IPrintMarketingContentService printMarketingContentService) {
		this.printMarketingContentService = printMarketingContentService;
	}

	/**
	 * 新增信息
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-26 下午3:23:47
	*/
	@JSON
	public String addPrintMarketingContent() {
		try {
			PrintMarketingContentEntity addEntity=printMarketingContentVo.getPrintMarketingContentEntity();
			
			PrintMarketingContentEntity existEntity=new PrintMarketingContentEntity();
			existEntity.setCityName(addEntity.getCityName());
			existEntity.setCityCode(addEntity.getCityCode());
			existEntity.setCityPattern(addEntity.getCityPattern());//城市名与城市类型不可重复保存
			List<PrintMarketingContentEntity> queryList=printMarketingContentService.
					queryPrintMarketingContentByCondition(existEntity, 1, 0);
			if(queryList.size()>0){
				return returnError("该城市与类型匹配已存在！");
			}
			
			String userCode = FossUserContext.getCurrentInfo().getEmpCode();// 当前登录用户empCode
			
			addEntity.setCreateUser(userCode);
			addEntity.setCreateDate(new Date());
			
			printMarketingContentService.addPrintMarketingContent(addEntity);
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 删除信息
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-26下午3:25:34
	*/
	@JSON
	public String deletePrintMarketingContent() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户empCode
			
			List<String> codeList=printMarketingContentVo.getCodeList();
			String[] codeArray=new String[codeList.size()];
			codeList.toArray(codeArray);

			printMarketingContentService.deletePrintMarketingContents(codeArray, userCode);
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 修改信息
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-26 下午3:25:45
	*/
	@JSON
	public String updatePrintMarketingContent() {
		try {
			String userCode = FossUserContext.getCurrentInfo().getEmpCode();// 当前登录用户empCode
			PrintMarketingContentEntity updateEntity=printMarketingContentVo.getPrintMarketingContentEntity();
			updateEntity.setModifyUser(userCode);
			printMarketingContentService.updatePrintMarketingContent(updateEntity);
			
			PrintMarketingContentEntity existEntity=new PrintMarketingContentEntity();
			existEntity.setCityName(updateEntity.getCityName());
			existEntity.setCityCode(updateEntity.getCityCode());
			existEntity.setCityPattern(updateEntity.getCityPattern());//城市名与城市类型不可重复保存
			List<PrintMarketingContentEntity> queryList=printMarketingContentService.
					queryPrintMarketingContentByCondition(existEntity, 1, 0);
			if(queryList.size()>0){
				return returnError("该城市与类型匹配已存在！");
			}
			
			updateEntity.setCreateUser(userCode);
			updateEntity.setCreateDate(new Date());
			
			printMarketingContentService.addPrintMarketingContent(updateEntity);
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 根据条件查询信息
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-26 下午3:31:22
	*/
	@JSON
	public String queryPrintMarketingContent() {
		try {
			PrintMarketingContentEntity queryEntity=printMarketingContentVo.getPrintMarketingContentEntity();
			List<PrintMarketingContentEntity> resultList=printMarketingContentService.
					queryPrintMarketingContentByCondition(queryEntity, limit, start);
			printMarketingContentVo.setPrintMarketingContentEntityList(resultList);
			totalCount=printMarketingContentService.queryRecordCount(queryEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 根据ID查询信息详情
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-26 下午3:34:41
	*/
	@JSON
	public String queryPrintMarketingContentByID() {
		try {
			printMarketingContentVo.setPrintMarketingContentEntity(printMarketingContentService.
					queryPrintMarketingContentEntityByID(printMarketingContentVo.
							getPrintMarketingContentEntity().getId()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	    
}
