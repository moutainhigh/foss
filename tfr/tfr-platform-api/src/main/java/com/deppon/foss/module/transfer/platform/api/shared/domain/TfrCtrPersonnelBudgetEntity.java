package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 外场预算人员实体，对应表结构tfr.t_opt_tc_personnel_budget
 * @author Ouyang
 */
public class TfrCtrPersonnelBudgetEntity extends BaseEntity {

	private static final long serialVersionUID = 6047717829473431642L;

	/**
	 * 外场编码
	 */
	private String transferCenterCode;

	/**
	 * 外场名称
	 */
	private String transferCenterName;

	/**
	 * 总监
	 */
	private Integer director;

	/**
	 * 总监统计员
	 */
	private Integer directorStatistician;

	/**
	 * 高级经理
	 */
	private Integer seniorManager;

	/**
	 * 经理
	 */
	private Integer manager;

	/**
	 * 副经理
	 */
	private Integer subManager;

	/**
	 * 外场调度
	 */
	private Integer dispatch;

	/**
	 * 叉车安全员
	 */
	private Integer forkliftSecurityGuard;

	/**
	 * 货区管理员
	 */
	private Integer goodsAreaGuard;

	/**
	 * 月台管理员
	 */
	private Integer platformGuard;

	/**
	 * 文职
	 */
	private Integer civilService;

	/**
	 * 外发员
	 */
	private Integer partialMember;

	/**
	 * 理货员
	 */
	private Integer tallyClerk;

	/**
	 * 电叉司机
	 */
	private Integer electricForkliftDriver;

	/**
	 * 机叉司机
	 */
	private Integer machineForkliftDriver;

	/**
	 * 木工
	 */
	private Integer carpenter;

	/**
	 * 修理工
	 */
	private Integer repairer;

	/**
	 * 分拣员
	 */
	private Integer sorter;

	/**
	 * 其他
	 */
	private Integer other;

	/**
	 * 总数
	 */
	private Integer totalQty;

	/**
	 * 生效开始日期
	 */
	private Date beginEffectiveDate;

	/**
	 * 生效截至日期
	 */
	private Date endEffectiveDate;

	/**
	 * 创建人编码
	 */
	private String createUserCode;

	/**
	 * 创建人名称
	 */
	private String createUserName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改人编码
	 */
	private String modifyUserCode;

	/**
	 * 修改人名称
	 */
	private String modifyUserName;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

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

	public Integer getDirector() {
		return director;
	}

	public void setDirector(Integer director) {
		this.director = director;
	}

	public Integer getDirectorStatistician() {
		return directorStatistician;
	}

	public void setDirectorStatistician(Integer directorStatistician) {
		this.directorStatistician = directorStatistician;
	}

	public Integer getSeniorManager() {
		return seniorManager;
	}

	public void setSeniorManager(Integer seniorManager) {
		this.seniorManager = seniorManager;
	}

	public Integer getManager() {
		return manager;
	}

	public void setManager(Integer manager) {
		this.manager = manager;
	}

	public Integer getSubManager() {
		return subManager;
	}

	public void setSubManager(Integer subManager) {
		this.subManager = subManager;
	}

	public Integer getDispatch() {
		return dispatch;
	}

	public void setDispatch(Integer dispatch) {
		this.dispatch = dispatch;
	}

	public Integer getForkliftSecurityGuard() {
		return forkliftSecurityGuard;
	}

	public void setForkliftSecurityGuard(Integer forkliftSecurityGuard) {
		this.forkliftSecurityGuard = forkliftSecurityGuard;
	}

	public Integer getGoodsAreaGuard() {
		return goodsAreaGuard;
	}

	public void setGoodsAreaGuard(Integer goodsAreaGuard) {
		this.goodsAreaGuard = goodsAreaGuard;
	}

	public Integer getPlatformGuard() {
		return platformGuard;
	}

	public void setPlatformGuard(Integer platformGuard) {
		this.platformGuard = platformGuard;
	}

	public Integer getCivilService() {
		return civilService;
	}

	public void setCivilService(Integer civilService) {
		this.civilService = civilService;
	}

	public Integer getPartialMember() {
		return partialMember;
	}

	public void setPartialMember(Integer partialMember) {
		this.partialMember = partialMember;
	}

	public Integer getTallyClerk() {
		return tallyClerk;
	}

	public void setTallyClerk(Integer tallyClerk) {
		this.tallyClerk = tallyClerk;
	}

	public Integer getElectricForkliftDriver() {
		return electricForkliftDriver;
	}

	public void setElectricForkliftDriver(Integer electricForkliftDriver) {
		this.electricForkliftDriver = electricForkliftDriver;
	}

	public Integer getMachineForkliftDriver() {
		return machineForkliftDriver;
	}

	public void setMachineForkliftDriver(Integer machineForkliftDriver) {
		this.machineForkliftDriver = machineForkliftDriver;
	}

	public Integer getCarpenter() {
		return carpenter;
	}

	public void setCarpenter(Integer carpenter) {
		this.carpenter = carpenter;
	}

	public Integer getRepairer() {
		return repairer;
	}

	public void setRepairer(Integer repairer) {
		this.repairer = repairer;
	}

	public Integer getSorter() {
		return sorter;
	}

	public void setSorter(Integer sorter) {
		this.sorter = sorter;
	}

	public Integer getOther() {
		return other;
	}

	public void setOther(Integer other) {
		this.other = other;
	}

	public Date getBeginEffectiveDate() {
		return beginEffectiveDate;
	}

	public void setBeginEffectiveDate(Date beginEffectiveDate) {
		this.beginEffectiveDate = beginEffectiveDate;
	}

	public Date getEndEffectiveDate() {
		return endEffectiveDate;
	}

	public void setEndEffectiveDate(Date endEffectiveDate) {
		this.endEffectiveDate = endEffectiveDate;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Integer totalQty) {
		this.totalQty = totalQty;
	}

	@Override
	public String toString() {
		return "TfrCtrPersonnelBudgetEntity [transferCenterCode="
				+ transferCenterCode + ", transferCenterName="
				+ transferCenterName + ", director=" + director
				+ ", directorStatistician=" + directorStatistician
				+ ", seniorManager=" + seniorManager + ", manager=" + manager
				+ ", subManager=" + subManager + ", dispatch=" + dispatch
				+ ", forkliftSecurityGuard=" + forkliftSecurityGuard
				+ ", goodsAreaGuard=" + goodsAreaGuard + ", platformGuard="
				+ platformGuard + ", civilService=" + civilService
				+ ", partialMember=" + partialMember + ", tallyClerk="
				+ tallyClerk + ", electricForkliftDriver="
				+ electricForkliftDriver + ", machineForkliftDriver="
				+ machineForkliftDriver + ", carpenter=" + carpenter
				+ ", repairer=" + repairer + ", sorter=" + sorter + ", other="
				+ other + ", totalQty=" + totalQty + ", beginEffectiveDate="
				+ beginEffectiveDate + ", endEffectiveDate=" + endEffectiveDate
				+ ", createUserCode=" + createUserCode + ", createUserName="
				+ createUserName + ", createTime=" + createTime
				+ ", modifyUserCode=" + modifyUserCode + ", modifyUserName="
				+ modifyUserName + ", modifyTime=" + modifyTime + "]";
	}

}
