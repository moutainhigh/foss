/**   
* @Title: VehicleSealInfoDto.java 
* @Package com.deppon.foss.module.transfer.load.api.shared.dto 
* @Description: 用于新增交接单时对封签的校验操作
* @author ibm-zhangyixin
* @date 2013-3-27 下午4:36:57 
* @version V1.0   
*/ 
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;

/** 
 * @ClassName: VehicleSealInfoDto 
 * @Description: 用于新增交接单时对封签的校验操作
 * @author ibm-zhangyixin
 * @date 2013-3-27 下午4:36:57 
 *  
 */
public class VehicleSealInfoDto extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 3910170530564724211L;

	/**车辆任务id**/
	private String truckTaskId;
	
	/**封签id**/
	private String sealId;
	
	/**车辆任务状态**/
	private String status;
	
	/**封车部门**/
	private String sealdOrgCode;
	
	/**封车部门Code**/
	private String sealdOrgName;
	
	/**车辆任务明细**/
	List<TruckTaskDetailEntity> truckTaskDetails;

	/**   
	 * truckTaskId   
	 *   
	 * @return  the truckTaskId   
	 */
	
	public String getTruckTaskId() {
		return truckTaskId;
	}

	/**   
	 * @param truckTaskId the truckTaskId to set
	 * Date:2013-3-27下午4:41:27
	 */
	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}

	/**   
	 * sealId   
	 *   
	 * @return  the sealId   
	 */
	
	public String getSealId() {
		return sealId;
	}

	/**   
	 * @param sealId the sealId to set
	 * Date:2013-3-27下午4:41:27
	 */
	public void setSealId(String sealId) {
		this.sealId = sealId;
	}

	/**   
	 * status   
	 *   
	 * @return  the status   
	 */
	
	public String getStatus() {
		return status;
	}

	/**   
	 * @param status the status to set
	 * Date:2013-3-27下午4:41:27
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**   
	 * sealdOrgCode   
	 *   
	 * @return  the sealdOrgCode   
	 */
	
	public String getSealdOrgCode() {
		return sealdOrgCode;
	}

	/**   
	 * @param sealdOrgCode the sealdOrgCode to set
	 * Date:2013-3-27下午4:41:27
	 */
	public void setSealdOrgCode(String sealdOrgCode) {
		this.sealdOrgCode = sealdOrgCode;
	}

	/**   
	 * sealdOrgName   
	 *   
	 * @return  the sealdOrgName   
	 */
	
	public String getSealdOrgName() {
		return sealdOrgName;
	}

	/**   
	 * @param sealdOrgName the sealdOrgName to set
	 * Date:2013-3-27下午4:41:27
	 */
	public void setSealdOrgName(String sealdOrgName) {
		this.sealdOrgName = sealdOrgName;
	}

	/**   
	 * truckTaskDetails   
	 *   
	 * @return  the truckTaskDetails   
	 */
	
	public List<TruckTaskDetailEntity> getTruckTaskDetails() {
		return truckTaskDetails;
	}

	/**   
	 * @param truckTaskDetails the truckTaskDetails to set
	 * Date:2013-4-2上午10:34:02
	 */
	public void setTruckTaskDetails(List<TruckTaskDetailEntity> truckTaskDetails) {
		this.truckTaskDetails = truckTaskDetails;
	}
}
