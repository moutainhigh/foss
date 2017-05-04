package com.deppon.foss.module.transfer.scheduling.server.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITemprentalMarkService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ShortRentalMarkEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ShortRentalMarkDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.ShortRentalMarkVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.ShortTempRentalMatkVo;

/**
 *临时租车标记
 *@author zenghaibin
 *@date 2014-5-28  
 **/
/**
 * @author 332209
 *
 */
public class ShortTemprentalMarkAction extends AbstractAction{
	
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	private ShortRentalMarkVo shortRentalMarkVo = new ShortRentalMarkVo();
	private ShortRentalMarkDto shortRentalMarkDto = new ShortRentalMarkDto();
	private ShortTempRentalMatkVo shortTempRentalMatkVo = new ShortTempRentalMatkVo();
	private static final Logger LOGGER = LoggerFactory.getLogger(ShortTemprentalMarkAction.class);
	
	/**
	 * 租车标记service
	 */
	private ITemprentalMarkService temprentalMarkService;
	/**
	 * 业务锁service
	 */
	private IBusinessLockService businessLockService;
	
	
	/**
	 *获取部门编号和标识
	 *@author ruilibao
	 *@date 2016-12-10
	 *@return String
	 **/
	@JSON
	public String shortTemprentalmarkIndex(){
		
		LOGGER.error("打开页面入口");
		//获取部门标识
		String departmentSignle = temprentalMarkService.queryOrgCode();
		//设置部门标识
		shortRentalMarkDto.setDepartmentSignle(departmentSignle);
		if (StringUtils.isNotEmpty(departmentSignle)) {
			if ("SalesDepartment".equals(departmentSignle.split("_")[0])){
				shortRentalMarkDto.setUseCarDepartment(FossUserContext.getCurrentInfo().getCurrentDeptName());
			}
		}
		//设置到VO里，以供页面可以取到
		shortRentalMarkVo.setShortRentalMarkDto(shortRentalMarkDto);
		LOGGER.error("需要传到页面的当前部门标识" + departmentSignle);
		LOGGER.error("成功打开页面");
		return returnSuccess();
	}
	
	/**
	 * 查询短途租车标记数据
	 * @author ruilibao
	 * @date 2016-12-10 
	 * @return   rentalMarkEntityList
	 **/
	@SuppressWarnings("unchecked")
	@JSON
	public String shortTemprentalmarkQuery(){
		
		LOGGER.error("租车标记查询方法开始...");
		//定义返回对象集合
		HashMap<String,Object> hsp = new HashMap<String,Object>();
		//获取返回对象
		hsp = temprentalMarkService.queryShortRentalMarkEntityList(shortRentalMarkVo,this.getLimit(),this.getStart());
		setTotalCount((Long)hsp.get("totalCount"));
		LOGGER.error("查询出来的记录数" + totalCount);
		shortRentalMarkVo.setShortRentalMarkEntityList((List<ShortRentalMarkEntity>)hsp.get("shortRentalMarkEntityList"));
		LOGGER.error("租车标记查询方法结束...");
		return returnSuccess();
	}
	
	/**
	 * 查询约车编号
	 * @author zenghaibin
	 * @date 2014-06-13
	 * @return
	 **/
	@JSON
	public String  queryShortInviteVehicleNo(){
		shortRentalMarkVo=temprentalMarkService.queryShortInviteVehicleNo(shortRentalMarkVo.getInviteVehicleNo());
		return returnSuccess();
	}
	
	
	/**
	 * 交接单和派送单
	 * 查询租车标记明细表里是否有list里对应单号数据
	 * 有，说明已经被标记过。
	 * author zenghaibin
	 * @modifiy 273247
	 *@date 2014-6-25  15:51:10
	 **/
	@JSON
	public String truckTaskRepeatMark(){
		try {
			Long count = temprentalMarkService.queryTruckTaskRepeatMark(shortRentalMarkVo.getShortRentalMarkDto().getTruckTaskIdList());			
			List<ShortRentalMarkEntity> shortEntityList=new ArrayList<ShortRentalMarkEntity>();			
			ShortRentalMarkEntity shortRentalMarkEntity=null;			
			List<String> origOrgCodeList=shortRentalMarkVo.getShortRentalMarkDto().getOrigOrgCodeList();						
			List<String> destOrgCodeList=shortRentalMarkVo.getShortRentalMarkDto().getDestOrgCodeList();
			for(int i=0;i<origOrgCodeList.size();i++ ){
				shortRentalMarkEntity=new ShortRentalMarkEntity();
				shortRentalMarkEntity.setOrigOrgCode(origOrgCodeList.get(i)); 
				shortRentalMarkEntity.setDestOrgCode(destOrgCodeList.get(i)); 
				shortEntityList.add(shortRentalMarkEntity);
			}			
			Long distance=temprentalMarkService.queryMaxDistanceShort(shortEntityList);
			if(count>0){
				shortRentalMarkVo.setIsrepeatMark("Y");
			}else{
				shortRentalMarkVo.setIsrepeatMark("N");
			}
			shortRentalMarkVo.setKmsNum(distance.toString());
		}catch(Exception e){
			return returnError(e.toString());
		}
		return returnSuccess();
	}
	

	/**
	 *添加租车标记
	 * @author zenghaibin
	 * @date 2014-06-13
	 * @return String
	 **/
	@JSON
	public String addShortTempRentalMark(){
		//是否锁定标志，如果flag=true则不锁定
		try{
			temprentalMarkService.addShortTempRentalMark(shortTempRentalMatkVo);
		}catch(TfrBusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}



	@SuppressWarnings("unused")
	private void unlock(List<MutexElement> mutexList){
		if(mutexList!=null&&!mutexList.isEmpty()&&mutexList.size()>0){
			 businessLockService.unlock(mutexList);
		 }
	}

	/**
	 * @return the shortRentalMarkVo
	 */
	public ShortRentalMarkVo getShortRentalMarkVo() {
		return shortRentalMarkVo;
	}

	/**
	 * @param shortRentalMarkVo the shortRentalMarkVo to set
	 */
	public void setShortRentalMarkVo(ShortRentalMarkVo shortRentalMarkVo) {
		this.shortRentalMarkVo = shortRentalMarkVo;
	}

	/**
	 * @return the shortRentalMarkDto
	 */
	public ShortRentalMarkDto getShortRentalMarkDto() {
		return shortRentalMarkDto;
	}

	/**
	 * @param shortRentalMarkDto the shortRentalMarkDto to set
	 */
	public void setShortRentalMarkDto(ShortRentalMarkDto shortRentalMarkDto) {
		this.shortRentalMarkDto = shortRentalMarkDto;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setTemprentalMarkService(
			ITemprentalMarkService temprentalMarkService) {
		this.temprentalMarkService = temprentalMarkService;
	}
	public void setShortTempRentalMatkVo(ShortTempRentalMatkVo shortTempRentalMatkVo) {
		this.shortTempRentalMatkVo = shortTempRentalMatkVo;
	}
	public ShortTempRentalMatkVo getShortTempRentalMatkVo() {
		return shortTempRentalMatkVo;
	}


	
}
