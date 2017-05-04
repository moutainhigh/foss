package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISendDistrictMapService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictMapEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SendDistrictMapVo;
/**
 * 分区名称公共选择器action
 * @author WeiXing
 *
 */
public class CommonZoneNameAction extends AbstractAction {

	private static final long serialVersionUID = -7056075818412238993L;
	/**
	 * 交互实体
	 */
	private SendDistrictMapVo objectVo =new SendDistrictMapVo();
	/**
	 * service
	 */
	private ISendDistrictMapService sendDistrictMapService;
	
	public SendDistrictMapVo getObjectVo() {
		return objectVo;
	}
	public void setObjectVo(SendDistrictMapVo objectVo) {
		this.objectVo = objectVo;
	}

	public void setSendDistrictMapService(
			ISendDistrictMapService sendDistrictMapService) {
		this.sendDistrictMapService = sendDistrictMapService;
	}
	/**
	 * 查询公共选择器
	 * @return
	 */
	public String searchZoneName(){
		try {
			List<SendDistrictMapEntity> resultList =sendDistrictMapService.querySendDistrictMap(objectVo.getSendDistrictMapEntity(), limit, start);
			objectVo.setSendDistrictMapEntities(resultList);
			this.setTotalCount(sendDistrictMapService.queryRecordCount(objectVo.getSendDistrictMapEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
}
