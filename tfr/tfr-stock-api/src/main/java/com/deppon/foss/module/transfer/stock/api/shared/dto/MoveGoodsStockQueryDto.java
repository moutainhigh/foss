package com.deppon.foss.module.transfer.stock.api.shared.dto;

import java.util.Date;


/**
* @description	库存迁移表 与 库存迁移部门表 关联查询结果数据
* @version 1.0
* @author 218381-foss-lijie
* @update 2014-12-22 下午2:33:23
*/
public class MoveGoodsStockQueryDto implements java.io.Serializable{	
	private static final long serialVersionUID = 1L;
	/**
	 * id(主表)
	 */
	private String id;
	
	/**
	* @description 获取id(主表)
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:10:36
	*/
	public String getId() {
		return id;
	}

	
	/**
	* @description 设置id(主表)
	* @param id
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:10:48
	*/
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 状态
	 */
	private String state;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 申请时间     
	 */
	private Date applicant_time;
	/**
	 * 申请人姓名
	 */
	private String applicant_name;
	/**
	 * 申请人工号
	 */
	private String applicant_code;
	
	/**
	* @description 获取状态
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:11:04
	*/
	public String getState() {
		return state;
	}

	
	/**
	* @description 设置状态
	* @param state
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:11:19
	*/
	public void setState(String state) {
		this.state = state;
	}

	
	/**
	* @description 获取备注
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:11:28
	*/
	public String getRemarks() {
		return remarks;
	}

	
	/**
	* @description 设置备注
	* @param remarks
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:11:42
	*/
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	/**
	* @description 获取申请时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:11:55
	*/
	public Date getApplicant_time() {
		return applicant_time;
	}

	
	/**
	* @description 设置申请时间
	* @param applicant_time
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:12:08
	*/
	public void setApplicant_time(Date applicantTime) {
		this.applicant_time = applicantTime;
	}

	
	/**
	* @description 获取申请人姓名
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:12:22
	*/
	public String getApplicant_name() {
		return applicant_name;
	}

	
	/**
	* @description 设置申请人姓名
	* @param applicant_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:12:32
	*/
	public void setApplicant_name(String applicantName) {
		this.applicant_name = applicantName;
	}

	
	/**
	* @description 获取申请人code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:12:42
	*/
	public String getApplicant_code() {
		return applicant_code;
	}

	
	/**
	* @description 设置申请人code
	* @param applicant_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:12:53
	*/
	public void setApplicant_code(String applicantCode) {
		this.applicant_code = applicantCode;
	}

	
	/**
	* @description 获取部门名称
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:13:06
	*/
	public String getOrg_name() {
		return org_name;
	}

	
	/**
	* @description 设置部门名称
	* @param org_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:13:19
	*/
	public void setOrg_name(String orgName) {
		this.org_name = orgName;
	}

	
	/**
	* @description 获取部门code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:13:34
	*/
	public String getOrg_code() {
		return org_code;
	}

	
	/**
	* @description 设置部门code
	* @param org_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:13:45
	*/
	public void setOrg_code(String orgCode) {
		this.org_code = orgCode;
	}

	
	/**
	* @description 获取审核时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:13:57
	*/
	public Date getAuditor_time() {
		return auditor_time;
	}

	
	/**
	* @description 设置审核时间
	* @param auditor_time
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:14:35
	*/
	public void setAuditor_time(Date auditorTime) {
		this.auditor_time = auditorTime;
	}

	
	/**
	* @description 获取审核人姓名
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:15:00
	*/
	public String getAuditor_name() {
		return auditor_name;
	}

	
	/**
	* @description 设置审核人姓名
	* @param auditor_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:15:10
	*/
	public void setAuditor_name(String auditorName) {
		this.auditor_name = auditorName;
	}

	
	/**
	* @description 获取审核人code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:15:20
	*/
	public String getAuditor_code() {
		return auditor_code;
	}

	
	/**
	* @description 设置审核人code
	* @param auditor_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:15:30
	*/
	public void setAuditor_code(String auditorCode) {
		this.auditor_code = auditorCode;
	}

	
	/**
	* @description 获取作废时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:15:40
	*/
	public Date getInvalidate_time() {
		return invalidate_time;
	}

	
	/**
	* @description 设置作废时间
	* @param invalidate_time
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:16:03
	*/
	public void setInvalidate_time(Date invalidateTime) {
		this.invalidate_time = invalidateTime;
	}

	
	/**
	* @description 获取作废人姓名
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:16:13
	*/
	public String getInvalidate_name() {
		return invalidate_name;
	}

	
	/**
	* @description 设置作废人姓名
	* @param invalidate_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:16:23
	*/
	public void setInvalidate_name(String invalidateName) {
		this.invalidate_name = invalidateName;
	}

	
	/**
	* @description 获取作废人code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:16:34
	*/
	public String getInvalidate_code() {
		return invalidate_code;
	}

	
	/**
	* @description 设置作废人code
	* @param invalidate_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:16:44
	*/
	public void setInvalidate_code(String invalidateCode) {
		this.invalidate_code = invalidateCode;
	}

	
	/**
	* @description 获取退回时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:16:53
	*/
	public Date getReturn_time() {
		return return_time;
	}

	
	/**
	* @description 设置退回时间
	* @param return_time
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:17:08
	*/
	public void setReturn_time(Date returnTime) {
		this.return_time = returnTime;
	}

	
	/**
	* @description 获取退回人姓名
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:17:25
	*/
	public String getReturn_name() {
		return return_name;
	}

	
	/**
	* @description 设置退回人姓名
	* @param return_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:17:37
	*/
	public void setReturn_name(String returnName) {
		this.return_name = returnName;
	}

	
	/**
	* @description 获取退回人code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:17:48
	*/
	public String getReturn_code() {
		return return_code;
	}

	
	/**
	* @description 设置退回人code
	* @param return_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:18:00
	*/
	public void setReturn_code(String returnCode) {
		this.return_code = returnCode;
	}

	
	/**
	* @description 获取确认迁移时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:18:11
	*/
	public Date getConfirm_time() {
		return confirm_time;
	}

	
	/**
	* @description 设置确认迁移时间
	* @param confirm_time
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:18:40
	*/
	public void setConfirm_time(Date confirmTime) {
		this.confirm_time = confirmTime;
	}

	
	/**
	* @description 获取确认迁移人姓名
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:18:49
	*/
	public String getConfirm_name() {
		return confirm_name;
	}

	
	/**
	* @description 设置确认迁移人姓名
	* @param confirm_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:19:03
	*/
	public void setConfirm_name(String confirmName) {
		this.confirm_name = confirmName;
	}

	
	/**
	* @description 获取确认迁移人code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:19:14
	*/
	public String getConfirm_code() {
		return confirm_code;
	}

	
	/**
	* @description 设置确认迁移人code
	* @param confirm_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:19:26
	*/
	public void setConfirm_code(String confirmCode) {
		this.confirm_code = confirmCode;
	}

	
	/**
	* @description 获取撤销时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:19:35
	*/
	public Date getRevocation_time() {
		return revocation_time;
	}

	
	/**
	* @description 设置撤销时间
	* @param revocation_time
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:20:07
	*/
	public void setRevocation_time(Date revocationTime) {
		this.revocation_time = revocationTime;
	}

	
	/**
	* @description 获取撤销人姓名
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:20:18
	*/
	public String getRevocation_name() {
		return revocation_name;
	}

	
	/**
	* @description 设置撤销人姓名
	* @param revocation_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:20:28
	*/
	public void setRevocation_name(String revocationName) {
		this.revocation_name = revocationName;
	}

	
	/**
	* @description 获取撤销人code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:20:39
	*/
	public String getRevocation_code() {
		return revocation_code;
	}

	
	/**
	* @description 设置撤销人code
	* @param revocation_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:20:49
	*/
	public void setRevocation_code(String revocationCode) {
		this.revocation_code = revocationCode;
	}

	
	/**
	* @description 获取修改时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:21:01
	*/
	public Date getModify_time() {
		return modify_time;
	}

	
	/**
	* @description 设置修改时间
	* @param modify_time
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:21:11
	*/
	public void setModify_time(Date modifyTime) {
		this.modify_time = modifyTime;
	}

	
	/**
	* @description 获取货物类型
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:21:21
	*/
	public Integer getGoods_type() {
		return goods_type;
	}

	
	/**
	* @description 设置获取类型
	* @param goods_type
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:21:39
	*/
	public void setGoods_type(Integer goodsType) {
		this.goods_type = goodsType;
	}

	
	/**
	* @description 获取移出部门名称
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:21:48
	*/
	public String getMoveout_name() {
		return moveout_name;
	}

	
	/**
	* @description 设置移出部门名称
	* @param moveout_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:22:08
	*/
	public void setMoveout_name(String moveoutName) {
		this.moveout_name = moveoutName;
	}

	
	/**
	* @description 获取移出部门code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:22:34
	*/
	public String getMoveout_code() {
		return moveout_code;
	}

	
	/**
	* @description 设置移出部门code
	* @param moveout_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:22:49
	*/
	public void setMoveout_code(String moveoutCode) {
		this.moveout_code = moveoutCode;
	}

	
	/**
	* @description 获取移出部门库区名称
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:23:02
	*/
	public String getMoveout_areaname() {
		return moveout_areaname;
	}

	
	/**
	* @description 设置移出部门库区名称
	* @param moveout_areaname
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:23:18
	*/
	public void setMoveout_areaname(String moveoutAreaname) {
		this.moveout_areaname = moveoutAreaname;
	}

	
	/**
	* @description 获取移出部门库区code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:23:30
	*/
	public String getMoveout_areacode() {
		return moveout_areacode;
	}

	
	/**
	* @description 设置移出部门库区code
	* @param moveout_areacode
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:23:50
	*/
	public void setMoveout_areacode(String moveoutAreacode) {
		this.moveout_areacode = moveoutAreacode;
	}

	
	/**
	* @description 获取移入部门名称
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:24:02
	*/
	public String getMovein_name() {
		return movein_name;
	}

	
	/**
	* @description 设置移入部门名称
	* @param movein_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:24:16
	*/
	public void setMovein_name(String moveinName) {
		this.movein_name = moveinName;
	}

	
	/**
	* @description 获取移入部门code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:24:28
	*/
	public String getMovein_code() {
		return movein_code;
	}

	
	/**
	* @description 设置移入部门code
	* @param movein_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:24:39
	*/
	public void setMovein_code(String moveinCode) {
		this.movein_code = moveinCode;
	}

	
	/**
	* @description 获取移入部门库区名称
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:24:50
	*/
	public String getMovein_areaname() {
		return movein_areaname;
	}

	
	/**
	* @description 设置移入部门库区名称
	* @param movein_areaname
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:25:05
	*/
	public void setMovein_areaname(String moveinAreaname) {
		this.movein_areaname = moveinAreaname;
	}

	
	/**
	* @description 获取移入部门库区code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:25:21
	*/
	public String getMovein_areacode() {
		return movein_areacode;
	}

	
	/**
	* @description 设置移入部门库区code
	* @param movein_areacode
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:25:32
	*/
	public void setMovein_areacode(String moveinAreacode) {
		this.movein_areacode = moveinAreacode;
	}

	/**
	 * 申请人所在部门
	 */
	private String org_name;
	/**
	 * 申请人所在部门编号
	 */
	private String org_code;
	/**
	 * 审核时间
	 */
	private Date auditor_time;
	/**
	 * 审核人姓名
	 */
	private String auditor_name;
	/**
	 * 审核人工号
	 */
	private String auditor_code;
	
	/**
	 * 作废时间
	 */
	private Date invalidate_time;
	/**
	 * 作废人姓名
	 */
	private String invalidate_name;
	/**
	 * 作废人工号
	 */
	private String invalidate_code;
	
	/**
	 * 退回时间
	 */
	private Date return_time;
	/**
	 * 退回人姓名
	 */
	private String return_name;
	/**
	 * 退回人工号
	 */
	private String return_code;
	
	/**
	 * 确认迁移时间
	 */
	private Date confirm_time;
	/**
	 * 确认迁移人姓名
	 */
	private String confirm_name;
	/**
	 * 确认迁移人工号
	 */
	private String confirm_code;
	
	/**
	 * 撤销时间
	 */
	private Date revocation_time;
	/**
	 * 撤销人姓名
	 */
	private String revocation_name;
	/**
	 * 撤销人工号
	 */
	private String revocation_code;
	
	/**
	 * 修改时间
	 */
	private Date modify_time;
	/**
	 * 修改人姓名
	 */
	private Date modify_name;
	/**
	 * 修改人工号
	 */
	private Date modify_code;
	
	
	/**
	* @description 获取修改人姓名
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:25:56
	*/
	public Date getModify_name() {
		return modify_name;
	}

	
	/**
	* @description 设置修改人姓名
	* @param modify_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:26:07
	*/
	public void setModify_name(Date modifyName) {
		this.modify_name = modifyName;
	}

	
	/**
	* @description 获取修改人code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:26:17
	*/
	public Date getModify_code() {
		return modify_code;
	}

	
	/**
	* @description 设置修改人code
	* @param modify_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:26:27
	*/
	public void setModify_code(Date modifyCode) {
		this.modify_code = modifyCode;
	}

	/**
	* @fields 货物类型
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:25:51
	* @version V1.0
	*/
	private Integer goods_type;
	
	
	/**
	* @fields 库存移出部门名称
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:26:07
	* @version V1.0
	*/
	private String moveout_name;
	
	
	/**
	* @fields 库存移出部门编号
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:26:23
	* @version V1.0
	*/
	private String moveout_code;
	
	/**
	* @fields 库存移出部门库区名称
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:26:27
	* @version V1.0
	*/
	private String moveout_areaname;
	
	/**
	* @fields 库存移出部门库区编号
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:26:30
	* @version V1.0
	*/
	private String moveout_areacode;
	
	/**
	* @fields 库存移入部门名称
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:26:37
	* @version V1.0
	*/
	private String movein_name;
	
	/**
	* @fields 库存移入部门编号
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:26:41
	* @version V1.0
	*/
	private String movein_code;
	
	/**
	* @fields 库存移入部门库区名称
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:26:44
	* @version V1.0
	*/
	
	private String movein_areaname;
	
	/**
	* @fields 库存移入部门库区编号
	* @author 218381-foss-lijie
	* @update 2014-12-22 下午2:26:48
	* @version V1.0
	*/
	private String movein_areacode;
	
}
