package com.deppon.pda.bdm.module.foss.packagemanager.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPackageService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.PackageManagerConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.foss.packagemanager.shared.domain.GetPackageTask;

/**
 * 
  * @ClassName GetPackageTaskService 
  * @Description TODO 获取建包任务
  * @author mt 
  * @date 2013-7-25 上午9:07:37
 */
public class GetPackageTaskService implements IBusinessService<List<GetPackageTask>, Void> {
	private IPDAExpressPackageService pdaExpressPackageService;
	
	/**
	 * 
	 * <p>TODO获取建包任务</p> 
	 * @author mt
	 * @date 2013年7月30日9:58:39
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 */
	@Override
	public Void parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		return null;
	}
	
	/**
	 * 
	 * <p>TODO(获取建包任务)</p> 
	 * @author mt
	 * @date 2013年7月30日9:58:42
	 * @param asyncMsg 请求消息
	 * @param param 获取建包任务信息
	 * @return
	 * @throws PdaBusiException 
	 */
	@Transactional
	@Override
	public List<GetPackageTask> service(AsyncMsg asyncMsg, Void param)
			throws PdaBusiException {
		List<GetPackageTask> result = null;
		List<ExpressPackageDto> list = null;
		try {
			Calendar calendar = Calendar.getInstance();	
			Date endTime = calendar.getTime();
			calendar.add(Calendar.DATE, -3); 		
			Date startTime = calendar.getTime();
			//取三天内的建包任务
			list = pdaExpressPackageService.queryUnFinishedPackage(asyncMsg.getUserCode(), startTime, endTime);
			if(list != null){
				result = wrapPdaPackageDto(list);
			}
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return result;
	}
	
	/**
	 * 
	* @Description: TODO 封装实体
	* @param list
	* @return
	* @return List<GetPackageTask>    
	* @author mt
	* @date 2013年7月30日9:58:46
	 */
	private List<GetPackageTask> wrapPdaPackageDto(List<ExpressPackageDto> list){
		List<GetPackageTask> result = new ArrayList<GetPackageTask>();
		for (int i = 0; i < list.size(); i++) {
			ExpressPackageDto packDto = list.get(i);
			GetPackageTask packTask = new GetPackageTask();
			packTask.setPackageCode(packDto.getPackageNo());
			packTask.setState(packDto.getStatus());
			packTask.setUserCode(packDto.getUserCode());
			packTask.setDestination(packDto.getArriveOrgCode());
			
//			packTask.setWayBillCode(packDto.getWayBillCode());
			packTask.setDestinationName(packDto.getArriveOrgName());
			/*if("Y".equals(packDto.getIsThrough())){//直达包
				packTask.setExpressPackageType("1");//获取包类型  直达包/普通包
			}else{//普通包
				packTask.setExpressPackageType("0");//获取包类型  直达包/普通包
			}*/
			if("NORMAL_ARRIVE".equals(packDto.getExpressPackageType())){
				packTask.setExpressPackageType("0");//普通包
			}else if("THROUGH_ARRIVE".equals(packDto.getExpressPackageType())){
				packTask.setExpressPackageType("1");//直达包
			}else if("SECONDCAR_ARRIVE".equals(packDto.getExpressPackageType())){
				packTask.setExpressPackageType("2");//接驳包
			}else if("EXPRESS_AIR".equals(packDto.getExpressPackageType())){
				packTask.setExpressPackageType("3");//空运包
			}
			
			
			result.add(packTask);
		}
		return result;
	}
	
	@Override
	public String getOperType() {
		return PackageManagerConstant.OPER_TYPE_PACKAGE_TASK_GET.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}
	
	public void setPdaExpressPackageService(
			IPDAExpressPackageService pdaExpressPackageService) {
		this.pdaExpressPackageService = pdaExpressPackageService;
	}
}
