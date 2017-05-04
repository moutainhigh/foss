package com.deppon.foss.module.transfer.partialline.server.service.impl;
/**
 * @author foss 257200
 * 2015-9-1
 * 257220
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IInputWeightVolumnDao;
import com.deppon.foss.module.transfer.partialline.api.server.service.IInputWeightVolumnService;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.InputWeightVolumnEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.InputWeightVolumnDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 257220
 *
 */
public class InputWeightVolumnService implements IInputWeightVolumnService{

	private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;
	private ILdpExternalBillService ldpExternalBillService;
	private IInputWeightVolumnDao inputWeightVolumnDao;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private IHandOverBillService handOverBillService;
	private IWaybillManagerService waybillManagerService;
	/**
	 * <p>查询录入重量体积dto</p>
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @author 257220
	 * @date 2015-9-1上午9:02:31
	 */
	public List<InputWeightVolumnDto> queryInputWeightVolumnBaseInfo(
			InputWeightVolumnDto dto, int start, int limit) {
		String deptCode = "";
		CurrentInfo user = FossUserContext.getCurrentInfo();
		if(user != null) {
			String currentDeptCode = user.getCurrentDeptCode();
			OrgAdministrativeInfoEntity entity = this.getBigOrg(currentDeptCode);
			if(entity != null ) {
				deptCode = entity.getCode();
				dto.setDeptCode(deptCode);
			}
		}
		return inputWeightVolumnDao.queryInputWeightVolumnBaseInfo(dto, start, limit);
	}
	/**
	 * 
	 *@param dto
	 *@return
	 *@date  2015-11-14下午4:19:40
	 *@author 257220
	 */
	@Override
	public Long queryInputWeightVolumnBaseInfoCount(InputWeightVolumnDto dto){
		String deptCode = "";
		CurrentInfo user = FossUserContext.getCurrentInfo();
		if(user != null) {
			String currentDeptCode = user.getCurrentDeptCode();
			OrgAdministrativeInfoEntity entity = this.getBigOrg(currentDeptCode);
			if(entity != null ) {
				deptCode = entity.getCode();
				dto.setDeptCode(deptCode);
			}
		}
		return inputWeightVolumnDao.queryInputWeightVolumnBaseInfoCount(dto);
	}
	
	/**
	 * @param parentWaybillNo
	 * @author 257220
	 * @date 2015-9-7下午12:23:09
	 *(non-Javadoc)
	 * @see com.deppon.foss.module.transfer.partialline.api.server.service.IInputWeightVolumnService#queryInputWeightVolumnInfo(java.lang.String)
	 */
	@Override
	public List<InputWeightVolumnDto> queryInputWeightVolumnInfo(String parentWaybillNo) {
		return inputWeightVolumnDao.queryInputWeightVolumnInfo(parentWaybillNo);
	}
	/**
	 * <ul>批量保存修改的重量体积方法<ul>
	 * <li>如果有录入则修改</li>
	 * <li>如果未曾录入则新增</li>
	 * @param list
	 * @author 257220
	 * @date 2015-9-8下午5:52:31
	 */
	@Transactional
	public void save(List<InputWeightVolumnDto> list){
		if(list == null || list.size()==0){
			return;
		}
		UserEntity user = FossUserContext.getCurrentUser();
		OrgAdministrativeInfoEntity orgInfo = FossUserContext.getCurrentDept();
		for(InputWeightVolumnDto inputWeightVolumnDto : list){
			//完善更新用户机构等信息
			inputWeightVolumnDto = completeWeightVolumnDto(inputWeightVolumnDto,user,orgInfo);
			//保存一条记录
			save(inputWeightVolumnDto);
		}
	}

	/**
	* <ul>保存修改的重量体积方法<ul>
	 * <li>如果有录入则修改</li>
	 * <li>如果未曾录入则新增</li>
	 * @param inputWeightVolumnDto
	 * @author 257220
	 * @date 2015-9-9下午3:18:28
	 */
	public void save(InputWeightVolumnDto inputWeightVolumnDto){
		//是否新增过,新增过则修改
		if(hasAdded(inputWeightVolumnDto)){
			updateWeightVolumnInfo(inputWeightVolumnDto);
		}else{
			inputWeightVolumnDto.setId(UUIDUtils.getUUID());
			addWeightVolumnInfo(inputWeightVolumnDto);
		}
	}
	/**
	 * 批量保存数据（数据来源excle导入）
	 * @return 保存数据条数
	 * @author 257220
	 * @date 2015-9-9下午3:48:51
	 */
	public List<String> batchSaveDataFromExcel(List<InputWeightVolumnDto> list){
		//验证数据
		List<String> parentList= validDataFromExcel(list);
		//保存数据
		save(list);
		return parentList;
	}
	/**
	 * 新增一条重量体积信息
	 * @param inputWeightVolumnDto
	 * @author 257220
	 * @date 2015-9-8下午6:05:58
	 */
	public void addWeightVolumnInfo(InputWeightVolumnDto inputWeightVolumnDto) {
		inputWeightVolumnDao.addWeightVolumnInfo(inputWeightVolumnDto);
	}
	
	/**
	 * 更新一条重量体积信息
	 * @param inputWeightVolumnDto
	 * @author 257220
	 * @date 2015-9-8下午6:05:40
	 */
	public void updateWeightVolumnInfo(
			InputWeightVolumnDto inputWeightVolumnDto) {
		inputWeightVolumnDao.updateWeightVolumnInfo(inputWeightVolumnDto);
	}
	/**
	 * 提供给外界接口
	 * 根据运单号流水号查询子母件运单或一票多件重量体积信息
	 * @return
	 */
	public InputWeightVolumnEntity queryInputWeightVolumnByWaybillNo(String waybillNo,String serialNo){
		InputWeightVolumnEntity inputWeightVolumnEntity = null;
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		if(waybillEntity == null){
			return null;
		}
		if(!hook(waybillEntity)){
			return null;
		}
		//一票多件信息
		if(waybillEntity.getGoodsQtyTotal()>0){
				inputWeightVolumnEntity = inputWeightVolumnDao.queryInputWeightVolumnByWaybillNo(waybillNo,serialNo);
		}else{//子母件信息
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("waybillNo", waybillNo);
			paramMap.put("active", FossConstants.ACTIVE);
			//判断是否子母件
			TwoInOneWaybillDto twoInOneWaybillDto = waybillRelateDetailEntityService.queryWaybillRelateByWaybillOrOrderNo(paramMap);
			if(twoInOneWaybillDto != null){
				String twoInOne = twoInOneWaybillDto.getIsTwoInOne();
				if(StringUtils.equals(twoInOne, FossConstants.ACTIVE)){
					//获取重量体积信息
					inputWeightVolumnEntity = inputWeightVolumnDao.queryInputWeightVolumnByWaybillNo(waybillNo,serialNo);
				}
			}
		}
		return inputWeightVolumnEntity;
	}
	/**
	 * 根据运单号查询运单重量体积信息
	 * @return InputWeightVolumnEntity
	 */
	@Override
	public InputWeightVolumnEntity getInputWeightVolumnByWaybillNo(
			String waybillNo,String serialNo) {
		return inputWeightVolumnDao.queryInputWeightVolumnByWaybillNo(waybillNo,serialNo);
	}
	/**
	 * 填充用户机构等信息
	 * @return
	 * @author 257220
	 * @date 2015-9-8下午6:39:11
	 */
	private InputWeightVolumnDto completeWeightVolumnDto(InputWeightVolumnDto inputWeightVolumnDto,UserEntity currentUser,OrgAdministrativeInfoEntity currentOrg){
		inputWeightVolumnDto.setModifyUserCode(currentUser.getEmployee().getEmpCode());
		inputWeightVolumnDto.setModifyUserName(currentUser.getEmpName());
		inputWeightVolumnDto.setModifyOrgCode(currentOrg.getCode());
		inputWeightVolumnDto.setModifyOrgName(currentOrg.getName());
		//子母件默认流水号“0001”
		inputWeightVolumnDto.setSerialNo("0001");
		return inputWeightVolumnDto;
	}
	/**
	 * <ul>校验excel导入的list合法性
	 * 	<li>校验每一票的重量需是否大于0</li>
	 * 	<li>校验每一票是否生成外发单</li>
	 *  <li>未生成交接单的不能导入</li>
	 * 	<li>校验票运单所对应的所有子件、母件运单号是否有数据</li>
	 * </ul>
	 * @param list
	 * @author 257220
	 * @return 校验通过的母件集合
	 * @date 2015-9-9下午3:23:43
	 */
	private List<String> validDataFromExcel(List<InputWeightVolumnDto> list){
		if(list == null){
			return null;
		}
		//将excel里的数据按母件放入到map中，key:母件单号，value:子件单号集合
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		List<String> parentList = new ArrayList<String>();//母件集合
		for (InputWeightVolumnDto inputWeightVolumnDto : list) {
			String parentWaybillNo = inputWeightVolumnDto.getParentWaybillNo();
			String waybillNo = inputWeightVolumnDto.getWaybillNo();
			//校验重量是否小于等于0
			BigDecimal weight = inputWeightVolumnDto.getWeight();
			if(weight !=null && weight.compareTo(BigDecimal.ZERO) <= 0){
				throw new BusinessException("运单号 "+ waybillNo + "的重量小于等于0，请检查！","运单号 "+ waybillNo + "的重量小于等于0，请检查！");
			}
			//校验是否存在外发单
			List<LdpExternalBillDto> listExternalBillDtos = this.queryLdpExternalBill(waybillNo);
			if(listExternalBillDtos!=null && listExternalBillDtos.size() > 0){
				throw new BusinessException("运单号 "+ waybillNo + "已生成外发单！","运单号 "+ waybillNo + "已生成外发单！");
			}
			//校验是否生成交接单
			boolean existHandOverBill = handOverBillService.queryBeLdpHandOveredByWaybillNo(waybillNo);
			if(!existHandOverBill){
				throw new BusinessException("运单号 "+ waybillNo + "未生成交接单,不能导入！","运单号 "+ waybillNo + "未生成交接单,不能导入！");
			}
			//子件单号集合，包含母件
			List<String> chirldWaybillList = map.get(parentWaybillNo);
			if(chirldWaybillList == null){
				chirldWaybillList = new ArrayList<String>();
				map.put(parentWaybillNo, chirldWaybillList);
			}
			chirldWaybillList.add(waybillNo);
		}
		//校验某一票录入表格，则该票运单所对应的所有子件、母件运单号均要出现在表中
		 for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			 String parentWaybillNo = entry.getKey();
			 List<String> chirldWaybillList = entry.getValue();
			 parentList.add(parentWaybillNo);
			 //根据母件在库中查询所有的子件
			 List<String> chirldWaybillListFromDB = waybillRelateDetailEntityService.queryWaybillNoListByParentWaybillNo(parentWaybillNo);
			 if(chirldWaybillListFromDB == null || chirldWaybillListFromDB.size() == 0){
				 throw new BusinessException("未找到母件单号 "+ parentWaybillNo + "对应的子母件运单，请检查是否录入正确！","未找到母件单号 "+ parentWaybillNo + "对应的子母件运单，请检查是否录入正确！");
			 }
			 if(!(chirldWaybillList.containsAll(chirldWaybillListFromDB) && chirldWaybillListFromDB.containsAll(chirldWaybillList))){
				 throw new BusinessException("母件单号 "+ parentWaybillNo + "对应的子母件单不全！","母件单号 "+ parentWaybillNo + "对应所有子母件运单有误！");
			 }
		}
		 return parentList;
	}
	
	/**
	 * 判断是否有记录（已新增）
	 * @param inputWeightVolumnDto
	 * @return true:有，false:无
	 * @author 257220
	 * @date 2015-9-8下午5:56:47
	 */
	private boolean hasAdded(InputWeightVolumnDto inputWeightVolumnDto){
		String waybillNo = inputWeightVolumnDto.getWaybillNo();
		int count = inputWeightVolumnDao.queryInputWeightVolumnCount(waybillNo);
		return count > 0;
	}
	/**
	 * @author nly
	 * @date 2014年9月22日 下午5:18:27
	 * @function 获取上级外场
	 * @param currentOrg
	 * @return
	 */
	private OrgAdministrativeInfoEntity getBigOrg(String currentOrgCode){
			//设置查询参数
			List<String> bizTypesList = new ArrayList<String>();
			//外场类型
			bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			OrgAdministrativeInfoEntity bigOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(currentOrgCode, bizTypesList);
			if(bigOrg == null){
				throw new TfrBusinessException("查询当前部门所属外场失败","查询当前部门所属外场失败");
			}
			return bigOrg;
	}
	
	/**
	 * <ul>判断能否修改,条件：</ul>
	 * <li>未做交接单</li>
	 * <li>已生成外发单</li>
	 * @param inputWeightVolumnDto
	 * @return
	 */
	public InputWeightVolumnDto checkCanModify(InputWeightVolumnDto inputWeightVolumnDto){
		String waybillNo = inputWeightVolumnDto.getWaybillNo();
		//是否存在有效的快递代理外发单
		List<LdpExternalBillDto> ldpExternalBillDtos = queryLdpExternalBill(waybillNo);
		if(ldpExternalBillDtos!=null && ldpExternalBillDtos.size() > 0){
			inputWeightVolumnDto.setCanModify("N");//不能修改
			return inputWeightVolumnDto;
		}
		//未做交接单
		boolean existHandOverBill = handOverBillService.queryBeLdpHandOveredByWaybillNo(waybillNo);
		if(!existHandOverBill){
			inputWeightVolumnDto.setCanModify("N");//不能修改
			return inputWeightVolumnDto;
		}
		inputWeightVolumnDto.setCanModify("Y");
		return inputWeightVolumnDto;
	}
	/**
	 * @author 257220
	 * @date 2015年4月29日 上午11:05:12
	 * @function 是否存在有效的快递代理外发单
	 * @param waybillNos
	 * @return
	 * @throws Exception 
	 */
	
	private List<LdpExternalBillDto> queryLdpExternalBill(String waybillNo) {
			LdpExternalBillDto dto = new LdpExternalBillDto();
			dto.setWaybillNo(waybillNo);
			dto.setTransferExternal(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO);
			List<String> statusList = new ArrayList<String>();
			statusList.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
			statusList.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
			statusList.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);
			dto.setList(statusList);
			List<LdpExternalBillDto> resultList = ldpExternalBillService.queryOrigLdpExternalBillList(dto);
			return resultList;
	}
	/**
	 * 钩子函数
	 * 台湾件不重新录入的界面获取数据，待编号后，待需求优化后可放开
	 */
	private boolean hook(WaybillEntity waybillEntity){
		boolean hook = true;
		String customerPickUpOrg = waybillEntity.getCustomerPickupOrgCode();
		if(customerPickUpOrg.startsWith("LDP00174")||customerPickUpOrg.startsWith("LDP00175")||customerPickUpOrg.startsWith("LDP00183")){
			hook = false;
		}
		return hook;
	}
	/**
	 * @return the inputWeightVolumnDao
	 */
	public IInputWeightVolumnDao getInputWeightVolumnDao() {
		return inputWeightVolumnDao;
	}

	/**
	 * @param inputWeightVolumnDao the inputWeightVolumnDao to set
	 */
	public void setInputWeightVolumnDao(IInputWeightVolumnDao inputWeightVolumnDao) {
		this.inputWeightVolumnDao = inputWeightVolumnDao;
	}

	public void setLdpExternalBillService(
			ILdpExternalBillService ldpExternalBillService) {
		this.ldpExternalBillService = ldpExternalBillService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setWaybillRelateDetailEntityService(
			IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}

	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
}
