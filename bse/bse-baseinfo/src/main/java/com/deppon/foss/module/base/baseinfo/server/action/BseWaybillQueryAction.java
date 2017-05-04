package com.deppon.foss.module.base.baseinfo.server.action;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TrackRecordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaybillMarkEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.UserDefinedQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.IntegrativeQueryVo;
import com.deppon.foss.module.base.baseinfo.server.service.impl.BseWaybillQueryService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/***
 * 综合信息查询读写操作类
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2013-6-1 下午3:21:56
 */
public class BseWaybillQueryAction extends AbstractAction {

	/**
	 * Id
	 */
	private static final long serialVersionUID = -8974682569221568365L;

	private BseWaybillQueryService bseWaybillQueryService;

	/**
	 * 综合查询 VO.
	 */
	private IntegrativeQueryVo objectVo = new IntegrativeQueryVo();

	 /**
     * 新增 自定义查询方案和条件.
     *
     * @return List<UserDefinedQueryDto>
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @see
     */
    public String addUserDefinedQueryDto() {
	UserDefinedQueryDto entity = objectVo.getUserDefinedQuery();
	String userCode = FossUserContext.getCurrentInfo().getEmpCode();
	entity.setUserCode(userCode);
	bseWaybillQueryService.addUserDefinedQueryDto(entity, userCode);
	return returnSuccess();
    }

    /**
     * 修改 自定义查询方案和条件.
     *
     * @return List<UserDefinedQueryDto>
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @see
     */
	public String updateUserDefinedQueryDto() {
		try{
			UserDefinedQueryDto dto = objectVo.getUserDefinedQuery();
			String userCode = FossUserContext.getCurrentInfo().getEmpCode();
			if (dto == null) {
				return returnError("传入参数为空");
			}
			dto.setUserCode(userCode);
			bseWaybillQueryService.updateUserDefinedQueryDto(dto, userCode);
			return returnSuccess();
		}catch (BusinessException e) {
			return  returnError(e.getMessage());
					
		}
		
	} 

    /**
     * 作废 自定义查询方案和条件.
     *
     * @return List<UserDefinedQueryDto>
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @see
     */
    public String deleteUserDefinedQueryDto() {
	bseWaybillQueryService.deleteUserDefinedQuerySchemeByCode(objectVo
		.getCodeStr(), FossUserContext.getCurrentInfo().getEmpCode());
	return returnSuccess();
    }
    /**
     * 新增 跟踪记录.
     *
     * @return List<UserDefinedQueryDto>
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @see
     */
    public String addTrackRecord() {
	TrackRecordEntity entity = objectVo.getTrackRecord();
	entity.setCreateUserCode(FossUserContext.getCurrentInfo().getEmpCode());
	entity.setCreateUserName(FossUserContext.getCurrentInfo().getEmpName());
	objectVo.setReturnInt(bseWaybillQueryService.addTrackRecord(entity));
	return returnSuccess();
    }
	
    /**
     * 新增 运单标记紧急状态.
     *
     * @return List<UserDefinedQueryDto>
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @see
     */
    public String addWaybillMark() {
	WaybillMarkEntity entity = objectVo.getWaybillMark();
	String userCode = FossUserContext.getCurrentInfo().getEmpCode();
	entity.setCreateUserCode(userCode);
	bseWaybillQueryService.addWaybillMark(entity);
	return returnSuccess();
    }
	
    /**
     * 
     *
     * @return 
     */
    public String addWaybillMarkList() {
	objectVo.setReturnInt(bseWaybillQueryService.addWaybillMarkList(objectVo.getCodeStr(), objectVo.getMarkStatus()));
	return returnSuccess();
    }
    
    /**
     * 修改 运单标记紧急状态.
     *
     * @return List<UserDefinedQueryDto>
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @see
     */
	public String updateWaybillMark() {
		try{
		UserDefinedQueryDto dto = objectVo.getUserDefinedQuery();
		String userCode = FossUserContext.getCurrentInfo().getEmpCode();
		if (dto == null) {
			return returnError("传入参数为空");
		}
		dto.setUserCode(userCode);
		bseWaybillQueryService.updateUserDefinedQueryDto(dto, userCode);
		return returnSuccess();
		}catch (BusinessException e) {
			return  returnError(e.getMessage());
					
		}
	}

	public IntegrativeQueryVo getObjectVo() {
		return objectVo;
	}

	public void setObjectVo(IntegrativeQueryVo objectVo) {
		this.objectVo = objectVo;
	}

	public void setBseWaybillQueryService(
			BseWaybillQueryService bseWaybillQueryService) {
		this.bseWaybillQueryService = bseWaybillQueryService;
	}

}