package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ouyang 外场预算人员查询显示列表dto
 */
public class TfrCtrPersonnelBudgetDto implements Serializable {

	private static final long serialVersionUID = -6261183359673454496L;

	/**
	 * 预算人员记录id
	 */
	private String idBudget;

	/**
	 * 实际人员记录id
	 */
	private String idActual;

	/**
	 * 预算人员创建时间
	 */
	private Date createTimeBudget;

	/**
	 * 实际人员创建时间
	 */
	private Date createTimeActual;

	/**
	 * 预算人员修改时间
	 */
	private Date modifyTimeBudget;

	/**
	 * 时间人员修改时间
	 */
	private Date modifyTimeActual;

	/**
	 * 预算生效开始日期
	 */
	private Date beginEffectiveDateBudget;

	/**
	 * 实际生效截至日期
	 */
	private Date beginEffectiveDateActual;

	/**
	 * 预算生效开始日期
	 */
	private Date endEffectiveDateBudget;

	/**
	 * 实际生效截至日期
	 */
	private Date endEffectiveDateActual;

	/**
	 * 外场编码
	 */
	private String transferCenterCode;

	/**
	 * 外场名称
	 */
	private String transferCenterName;

	/**
	 * 总监预算
	 */
	private Integer directorBudget;

	/**
	 * 总监实际
	 */
	private Integer directorActual;

	/**
	 * 总监差额
	 */
	private Integer directorRemainder;

	/**
	 * 总监统计员预算
	 */
	private Integer directorStatisticianBudget;

	/**
	 * 总监统计员实际
	 */
	private Integer directorStatisticianActual;

	/**
	 * 总监统计员差额
	 */
	private Integer directorStatisticianRemainder;

	/**
	 * 高级经理预算
	 */
	private Integer seniorManagerBudget;

	/**
	 * 高级经理实际
	 */
	private Integer seniorManagerActual;

	/**
	 * 高级经理差额
	 */
	private Integer seniorManagerRemainder;

	/**
	 * 经理预算
	 */
	private Integer managerBudget;

	/**
	 * 经理实际
	 */
	private Integer managerActual;

	/**
	 * 经理差额
	 */
	private Integer managerRemainder;

	/**
	 * 副经理预算
	 */
	private Integer subManagerBudget;

	/**
	 * 副经理实际
	 */
	private Integer subManagerActual;

	/**
	 * 副经理差额
	 */
	private Integer subManagerRemainder;

	/**
	 * 调度预算
	 */
	private Integer dispatchBudget;

	/**
	 * 调度实际
	 */
	private Integer dispatchActual;

	/**
	 * 调度差额
	 */
	private Integer dispatchRemainder;

	/**
	 * 叉车安全员预算
	 */
	private Integer forkliftSecurityGuardBudget;

	/**
	 * 叉车安全员实际
	 */
	private Integer forkliftSecurityGuardActual;

	/**
	 * 叉车安全员差额
	 */
	private Integer forkliftSecurityGuardRemainder;

	/**
	 * 货区管理员预算
	 */
	private Integer goodsAreaGuardBudget;

	/**
	 * 货区管理员实际
	 */
	private Integer goodsAreaGuardActual;

	/**
	 * 货区管理员差额
	 */
	private Integer goodsAreaGuardRemainder;

	/**
	 * 月台管理员预算
	 */
	private Integer platformGuardBudget;

	/**
	 * 月台管理员实际
	 */
	private Integer platformGuardActual;

	/**
	 * 月台管理员差额
	 */
	private Integer platformGuardRemainder;

	/**
	 * 文职预算
	 */
	private Integer civilServiceBudget;

	/**
	 * 文职实际
	 */
	private Integer civilServiceActual;

	/**
	 * 文职差额
	 */
	private Integer civilServiceRemainder;

	/**
	 * 外发员预算
	 */
	private Integer partialMemberBudget;

	/**
	 * 外发员实际
	 */
	private Integer partialMemberActual;

	/**
	 * 外发员差额
	 */
	private Integer partialMemberRemainder;

	/**
	 * 理货员预算
	 */
	private Integer tallyClerkBudget;

	/**
	 * 理货员实际
	 */
	private Integer tallyClerkActual;

	/**
	 * 理货员差额
	 */
	private Integer tallyClerkRemainder;

	/**
	 * 电叉司机预算
	 */
	private Integer electricForkliftDriverBudget;

	/**
	 * 电叉司机实际
	 */
	private Integer electricForkliftDriverActual;

	/**
	 * 电叉司机差额
	 */
	private Integer electricForkliftDriverRemainder;

	/**
	 * 机叉司机预算
	 */
	private Integer machineForkliftDriverBudget;

	/**
	 * 机叉司机实际
	 */
	private Integer machineForkliftDriverActual;

	/**
	 * 机叉司机差额
	 */
	private Integer machineForkliftDriverRemainder;

	/**
	 * 木工预算
	 */
	private Integer carpenterBudget;

	/**
	 * 木工实际
	 */
	private Integer carpenterActual;

	/**
	 * 木工差额
	 */
	private Integer carpenterRemainder;

	/**
	 * 修理工预算
	 */
	private Integer repairerBudget;

	/**
	 * 修理工实际
	 */
	private Integer repairerActual;

	/**
	 * 修理工差额
	 */
	private Integer repairerRemainder;

	/**
	 * 分拣员预算
	 */
	private Integer sorterBudget;

	/**
	 * 分拣员实际
	 */
	private Integer sorterActual;

	/**
	 * 分拣员差额
	 */
	private Integer sorterRemainder;

	/**
	 * 其他预算
	 */
	private Integer otherBudget;

	/**
	 * 其他实际
	 */
	private Integer otherActual;

	/**
	 * 其他差额
	 */
	private Integer otherRemainder;

	/**
	 * 总预算
	 */
	private Integer totalQtyBudget;

	/**
	 * 总实际
	 */
	private Integer totalQtyActual;

	/**
	 * 总差额
	 */
	private Integer totalQtyRemainder;

	/**
	 * 当天离职数
	 */
	private Integer dimission;

	/**
	 * 月累计离职数
	 */
	private Integer dimissionAccumulated;

	public String getIdBudget() {
		return idBudget;
	}

	public void setIdBudget(String idBudget) {
		this.idBudget = idBudget;
	}

	public String getIdActual() {
		return idActual;
	}

	public void setIdActual(String idActual) {
		this.idActual = idActual;
	}

	public Date getCreateTimeBudget() {
		return createTimeBudget;
	}

	public void setCreateTimeBudget(Date createTimeBudget) {
		this.createTimeBudget = createTimeBudget;
	}

	public Date getCreateTimeActual() {
		return createTimeActual;
	}

	public void setCreateTimeActual(Date createTimeActual) {
		this.createTimeActual = createTimeActual;
	}

	public Date getModifyTimeBudget() {
		return modifyTimeBudget;
	}

	public void setModifyTimeBudget(Date modifyTimeBudget) {
		this.modifyTimeBudget = modifyTimeBudget;
	}

	public Date getModifyTimeActual() {
		return modifyTimeActual;
	}

	public void setModifyTimeActual(Date modifyTimeActual) {
		this.modifyTimeActual = modifyTimeActual;
	}

	public Date getBeginEffectiveDateBudget() {
		return beginEffectiveDateBudget;
	}

	public void setBeginEffectiveDateBudget(Date beginEffectiveDateBudget) {
		this.beginEffectiveDateBudget = beginEffectiveDateBudget;
	}

	public Date getBeginEffectiveDateActual() {
		return beginEffectiveDateActual;
	}

	public void setBeginEffectiveDateActual(Date beginEffectiveDateActual) {
		this.beginEffectiveDateActual = beginEffectiveDateActual;
	}

	public Date getEndEffectiveDateBudget() {
		return endEffectiveDateBudget;
	}

	public void setEndEffectiveDateBudget(Date endEffectiveDateBudget) {
		this.endEffectiveDateBudget = endEffectiveDateBudget;
	}

	public Date getEndEffectiveDateActual() {
		return endEffectiveDateActual;
	}

	public void setEndEffectiveDateActual(Date endEffectiveDateActual) {
		this.endEffectiveDateActual = endEffectiveDateActual;
	}

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public String getTransferCenterName() {
		return transferCenterName;
	}

	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}

	public Integer getDirectorBudget() {
		return directorBudget;
	}

	public void setDirectorBudget(Integer directorBudget) {
		this.directorBudget = directorBudget;
	}

	public Integer getDirectorActual() {
		return directorActual;
	}

	public void setDirectorActual(Integer directorActual) {
		this.directorActual = directorActual;
	}

	public Integer getDirectorRemainder() {
		return directorRemainder;
	}

	public void setDirectorRemainder(Integer directorRemainder) {
		this.directorRemainder = directorRemainder;
	}

	public Integer getDirectorStatisticianBudget() {
		return directorStatisticianBudget;
	}

	public void setDirectorStatisticianBudget(Integer directorStatisticianBudget) {
		this.directorStatisticianBudget = directorStatisticianBudget;
	}

	public Integer getDirectorStatisticianActual() {
		return directorStatisticianActual;
	}

	public void setDirectorStatisticianActual(Integer directorStatisticianActual) {
		this.directorStatisticianActual = directorStatisticianActual;
	}

	public Integer getDirectorStatisticianRemainder() {
		return directorStatisticianRemainder;
	}

	public void setDirectorStatisticianRemainder(
			Integer directorStatisticianRemainder) {
		this.directorStatisticianRemainder = directorStatisticianRemainder;
	}

	public Integer getSeniorManagerBudget() {
		return seniorManagerBudget;
	}

	public void setSeniorManagerBudget(Integer seniorManagerBudget) {
		this.seniorManagerBudget = seniorManagerBudget;
	}

	public Integer getSeniorManagerActual() {
		return seniorManagerActual;
	}

	public void setSeniorManagerActual(Integer seniorManagerActual) {
		this.seniorManagerActual = seniorManagerActual;
	}

	public Integer getSeniorManagerRemainder() {
		return seniorManagerRemainder;
	}

	public void setSeniorManagerRemainder(Integer seniorManagerRemainder) {
		this.seniorManagerRemainder = seniorManagerRemainder;
	}

	public Integer getManagerBudget() {
		return managerBudget;
	}

	public void setManagerBudget(Integer managerBudget) {
		this.managerBudget = managerBudget;
	}

	public Integer getManagerActual() {
		return managerActual;
	}

	public void setManagerActual(Integer managerActual) {
		this.managerActual = managerActual;
	}

	public Integer getManagerRemainder() {
		return managerRemainder;
	}

	public void setManagerRemainder(Integer managerRemainder) {
		this.managerRemainder = managerRemainder;
	}

	public Integer getSubManagerBudget() {
		return subManagerBudget;
	}

	public void setSubManagerBudget(Integer subManagerBudget) {
		this.subManagerBudget = subManagerBudget;
	}

	public Integer getSubManagerActual() {
		return subManagerActual;
	}

	public void setSubManagerActual(Integer subManagerActual) {
		this.subManagerActual = subManagerActual;
	}

	public Integer getSubManagerRemainder() {
		return subManagerRemainder;
	}

	public void setSubManagerRemainder(Integer subManagerRemainder) {
		this.subManagerRemainder = subManagerRemainder;
	}

	public Integer getDispatchBudget() {
		return dispatchBudget;
	}

	public void setDispatchBudget(Integer dispatchBudget) {
		this.dispatchBudget = dispatchBudget;
	}

	public Integer getDispatchActual() {
		return dispatchActual;
	}

	public void setDispatchActual(Integer dispatchActual) {
		this.dispatchActual = dispatchActual;
	}

	public Integer getDispatchRemainder() {
		return dispatchRemainder;
	}

	public void setDispatchRemainder(Integer dispatchRemainder) {
		this.dispatchRemainder = dispatchRemainder;
	}

	public Integer getForkliftSecurityGuardBudget() {
		return forkliftSecurityGuardBudget;
	}

	public void setForkliftSecurityGuardBudget(
			Integer forkliftSecurityGuardBudget) {
		this.forkliftSecurityGuardBudget = forkliftSecurityGuardBudget;
	}

	public Integer getForkliftSecurityGuardActual() {
		return forkliftSecurityGuardActual;
	}

	public void setForkliftSecurityGuardActual(
			Integer forkliftSecurityGuardActual) {
		this.forkliftSecurityGuardActual = forkliftSecurityGuardActual;
	}

	public Integer getForkliftSecurityGuardRemainder() {
		return forkliftSecurityGuardRemainder;
	}

	public void setForkliftSecurityGuardRemainder(
			Integer forkliftSecurityGuardRemainder) {
		this.forkliftSecurityGuardRemainder = forkliftSecurityGuardRemainder;
	}

	public Integer getGoodsAreaGuardBudget() {
		return goodsAreaGuardBudget;
	}

	public void setGoodsAreaGuardBudget(Integer goodsAreaGuardBudget) {
		this.goodsAreaGuardBudget = goodsAreaGuardBudget;
	}

	public Integer getGoodsAreaGuardActual() {
		return goodsAreaGuardActual;
	}

	public void setGoodsAreaGuardActual(Integer goodsAreaGuardActual) {
		this.goodsAreaGuardActual = goodsAreaGuardActual;
	}

	public Integer getGoodsAreaGuardRemainder() {
		return goodsAreaGuardRemainder;
	}

	public void setGoodsAreaGuardRemainder(Integer goodsAreaGuardRemainder) {
		this.goodsAreaGuardRemainder = goodsAreaGuardRemainder;
	}

	public Integer getPlatformGuardBudget() {
		return platformGuardBudget;
	}

	public void setPlatformGuardBudget(Integer platformGuardBudget) {
		this.platformGuardBudget = platformGuardBudget;
	}

	public Integer getPlatformGuardActual() {
		return platformGuardActual;
	}

	public void setPlatformGuardActual(Integer platformGuardActual) {
		this.platformGuardActual = platformGuardActual;
	}

	public Integer getPlatformGuardRemainder() {
		return platformGuardRemainder;
	}

	public void setPlatformGuardRemainder(Integer platformGuardRemainder) {
		this.platformGuardRemainder = platformGuardRemainder;
	}

	public Integer getCivilServiceBudget() {
		return civilServiceBudget;
	}

	public void setCivilServiceBudget(Integer civilServiceBudget) {
		this.civilServiceBudget = civilServiceBudget;
	}

	public Integer getCivilServiceActual() {
		return civilServiceActual;
	}

	public void setCivilServiceActual(Integer civilServiceActual) {
		this.civilServiceActual = civilServiceActual;
	}

	public Integer getCivilServiceRemainder() {
		return civilServiceRemainder;
	}

	public void setCivilServiceRemainder(Integer civilServiceRemainder) {
		this.civilServiceRemainder = civilServiceRemainder;
	}

	public Integer getPartialMemberBudget() {
		return partialMemberBudget;
	}

	public void setPartialMemberBudget(Integer partialMemberBudget) {
		this.partialMemberBudget = partialMemberBudget;
	}

	public Integer getPartialMemberActual() {
		return partialMemberActual;
	}

	public void setPartialMemberActual(Integer partialMemberActual) {
		this.partialMemberActual = partialMemberActual;
	}

	public Integer getPartialMemberRemainder() {
		return partialMemberRemainder;
	}

	public void setPartialMemberRemainder(Integer partialMemberRemainder) {
		this.partialMemberRemainder = partialMemberRemainder;
	}

	public Integer getTallyClerkBudget() {
		return tallyClerkBudget;
	}

	public void setTallyClerkBudget(Integer tallyClerkBudget) {
		this.tallyClerkBudget = tallyClerkBudget;
	}

	public Integer getTallyClerkActual() {
		return tallyClerkActual;
	}

	public void setTallyClerkActual(Integer tallyClerkActual) {
		this.tallyClerkActual = tallyClerkActual;
	}

	public Integer getTallyClerkRemainder() {
		return tallyClerkRemainder;
	}

	public void setTallyClerkRemainder(Integer tallyClerkRemainder) {
		this.tallyClerkRemainder = tallyClerkRemainder;
	}

	public Integer getElectricForkliftDriverBudget() {
		return electricForkliftDriverBudget;
	}

	public void setElectricForkliftDriverBudget(
			Integer electricForkliftDriverBudget) {
		this.electricForkliftDriverBudget = electricForkliftDriverBudget;
	}

	public Integer getElectricForkliftDriverActual() {
		return electricForkliftDriverActual;
	}

	public void setElectricForkliftDriverActual(
			Integer electricForkliftDriverActual) {
		this.electricForkliftDriverActual = electricForkliftDriverActual;
	}

	public Integer getElectricForkliftDriverRemainder() {
		return electricForkliftDriverRemainder;
	}

	public void setElectricForkliftDriverRemainder(
			Integer electricForkliftDriverRemainder) {
		this.electricForkliftDriverRemainder = electricForkliftDriverRemainder;
	}

	public Integer getMachineForkliftDriverBudget() {
		return machineForkliftDriverBudget;
	}

	public void setMachineForkliftDriverBudget(
			Integer machineForkliftDriverBudget) {
		this.machineForkliftDriverBudget = machineForkliftDriverBudget;
	}

	public Integer getMachineForkliftDriverActual() {
		return machineForkliftDriverActual;
	}

	public void setMachineForkliftDriverActual(
			Integer machineForkliftDriverActual) {
		this.machineForkliftDriverActual = machineForkliftDriverActual;
	}

	public Integer getMachineForkliftDriverRemainder() {
		return machineForkliftDriverRemainder;
	}

	public void setMachineForkliftDriverRemainder(
			Integer machineForkliftDriverRemainder) {
		this.machineForkliftDriverRemainder = machineForkliftDriverRemainder;
	}

	public Integer getCarpenterBudget() {
		return carpenterBudget;
	}

	public void setCarpenterBudget(Integer carpenterBudget) {
		this.carpenterBudget = carpenterBudget;
	}

	public Integer getCarpenterActual() {
		return carpenterActual;
	}

	public void setCarpenterActual(Integer carpenterActual) {
		this.carpenterActual = carpenterActual;
	}

	public Integer getCarpenterRemainder() {
		return carpenterRemainder;
	}

	public void setCarpenterRemainder(Integer carpenterRemainder) {
		this.carpenterRemainder = carpenterRemainder;
	}

	public Integer getRepairerBudget() {
		return repairerBudget;
	}

	public void setRepairerBudget(Integer repairerBudget) {
		this.repairerBudget = repairerBudget;
	}

	public Integer getRepairerActual() {
		return repairerActual;
	}

	public void setRepairerActual(Integer repairerActual) {
		this.repairerActual = repairerActual;
	}

	public Integer getRepairerRemainder() {
		return repairerRemainder;
	}

	public void setRepairerRemainder(Integer repairerRemainder) {
		this.repairerRemainder = repairerRemainder;
	}

	public Integer getSorterBudget() {
		return sorterBudget;
	}

	public void setSorterBudget(Integer sorterBudget) {
		this.sorterBudget = sorterBudget;
	}

	public Integer getSorterActual() {
		return sorterActual;
	}

	public void setSorterActual(Integer sorterActual) {
		this.sorterActual = sorterActual;
	}

	public Integer getSorterRemainder() {
		return sorterRemainder;
	}

	public void setSorterRemainder(Integer sorterRemainder) {
		this.sorterRemainder = sorterRemainder;
	}

	public Integer getOtherBudget() {
		return otherBudget;
	}

	public void setOtherBudget(Integer otherBudget) {
		this.otherBudget = otherBudget;
	}

	public Integer getOtherActual() {
		return otherActual;
	}

	public void setOtherActual(Integer otherActual) {
		this.otherActual = otherActual;
	}

	public Integer getOtherRemainder() {
		return otherRemainder;
	}

	public void setOtherRemainder(Integer otherRemainder) {
		this.otherRemainder = otherRemainder;
	}

	public Integer getTotalQtyBudget() {
		return totalQtyBudget;
	}

	public void setTotalQtyBudget(Integer totalQtyBudget) {
		this.totalQtyBudget = totalQtyBudget;
	}

	public Integer getTotalQtyActual() {
		return totalQtyActual;
	}

	public void setTotalQtyActual(Integer totalQtyActual) {
		this.totalQtyActual = totalQtyActual;
	}

	public Integer getTotalQtyRemainder() {
		return totalQtyRemainder;
	}

	public void setTotalQtyRemainder(Integer totalQtyRemainder) {
		this.totalQtyRemainder = totalQtyRemainder;
	}

	public Integer getDimission() {
		return dimission;
	}

	public void setDimission(Integer dimission) {
		this.dimission = dimission;
	}

	public Integer getDimissionAccumulated() {
		return dimissionAccumulated;
	}

	public void setDimissionAccumulated(Integer dimissionAccumulated) {
		this.dimissionAccumulated = dimissionAccumulated;
	}

}
