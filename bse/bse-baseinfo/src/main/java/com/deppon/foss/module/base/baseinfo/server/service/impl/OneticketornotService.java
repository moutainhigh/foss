package com.deppon.foss.module.base.baseinfo.server.service.impl;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOneticketornotDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOneticketornotService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OneticketornotEntity;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.ws.syncdata.CommonException;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.OneticketornotSyncRequest;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.OneticketornotSyncResponse;
/**
 * CRM是否一票多件信息Service接口实现
 * @author 273311
 * @date 2015-12-16 
 * @since
 * @version
 */
public class OneticketornotService implements IOneticketornotService {
	
	/**
	 * 定义常量
	 */
	private static final String USER_CODE = "CRM";
	
	 /**
     * CRM否一票多件信息DAO接口.
     */
	private IOneticketornotDao oneticketornotDao;
   
	public void setOneticketornotDao(IOneticketornotDao oneticketornotDao) {
		this.oneticketornotDao = oneticketornotDao;
	}
	/**
	 * 新增是否一票多件信息
	 * @author 273311
	 * @date 2015-12-16 
	 * @param entity 是否一票多件信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Override
	public int addOneticketornot(OneticketornotEntity entity) {
		if(null==entity){
		return FossConstants.FAILURE;	
		}
		return oneticketornotDao.addOneticketornot(entity);
	}
	/**
	 * <p>根据客户编码查询信息实体</p>
	 * @author 273311
	 * @date 2015-12-16 
	 * @param 
	 * @return
	 * @see
	 */
	@Override
	public OneticketornotEntity queryOneticketornotBycode(String code) {
		if (StringUtil.isBlank(code)){
		return null;
	}
		return oneticketornotDao.queryOneticketornotBycode(code);
	}
	/**
	 * CRM一票多件信息进行操作的接口
	 * @author 273311
	 * @date   2015-12-21 
	 *
	 */
	@Override
	public OneticketornotSyncResponse syncOneticketornotInfo(OneticketornotSyncRequest request) throws CommonException {
		OneticketornotEntity entity = convertOneticketornot(request);
		String qcode=null;
		if(StringUtil.isNotBlank(entity.getCode())){
			qcode=entity.getCode();
		}else{
			return null;
		}
		// 验证在数据库是否存在
		//OneticketornotEntity isFlag = this.queryOneticketornotBycode1(qcode);
		boolean isFlag = oneticketornotDao.queryOneticketornotBycode1(qcode);
		OneticketornotSyncResponse response = new OneticketornotSyncResponse();
		if(isFlag){//存在作废,不存在直接新增
			oneticketornotDao.deleteOneticketornot(entity);
		}else{//再新增
			oneticketornotDao.addOneticketornot(entity);
		}
		response.setIfSuccess("1");
		return response;
	}
	/**
     * <p>
     * 把CRM同步过来的是否一票多件转化为FOSS是否一票多件信息实体
     * </p>.
     *
     * @param obj 
     * @return 
     * @author 273311
     * @date 2015-12-21
     * @see
     */
	 private OneticketornotEntity  convertOneticketornot(OneticketornotSyncRequest obj){
		 OneticketornotEntity entity = new OneticketornotEntity();
		 if(obj!=null){
		 entity.setCode(obj.getCode());
		 entity.setIsoneticketornot(obj.getIsoneticketornot());
		 entity.setTicketdescription(obj.getTicketdescription());
		 entity.setCreateUser(USER_CODE);
		 if("1".equals(obj.getIsoneticketornot())){
				//启用状态
			    entity.setActive(FossConstants.ACTIVE);
			}else{
				//启用状态
			    entity.setActive(FossConstants.INACTIVE);
			}
		 }
		 return entity;
		 
	 }
}
