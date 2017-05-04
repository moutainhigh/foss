package com.deppon.foss.module.base.uumsitf.esb.server;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.pojo.transformer.json.SendOrgInfoResponseTrans;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.MdmOrgInfo;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.SendMdmOrgProcessReult;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.SendMdmOrgRequest;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.SendMdmOrgResponse;
import com.eos.system.utility.StringUtil;

/**
 * 主数据同步项目--关于uums同步给foss 新的组织信息同步接口
 * @author dujunhui-187862
 * @date 2015-4-12 上午10:23:24
 *
 */
public class OrgInfoProcessor implements IProcess{
	//日志记录
	private static final Logger LOGGER = LoggerFactory.getLogger(OrgInfoProcessor.class);
	/**
	 * 组织信息service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	@Override
	public Object process(Object req) throws ESBBusinessException {
		//Request
		SendMdmOrgRequest orgRequest = (SendMdmOrgRequest)req;
		//Response
		SendMdmOrgResponse orgResponse = new SendMdmOrgResponse();
		//判断
		if(null !=orgRequest){
			LOGGER.info("------------------------------------同步主数据项目：组织信息新增至中间表begin-----------------------------------");
			// 成功失败次数声明
			int successCount = 0, failCount = 0;
			//处理明细list
			List<SendMdmOrgProcessReult> detailList = new ArrayList<SendMdmOrgProcessReult>();	
			
			List<MdmOrgInfo> orgInfos  =orgRequest.getAdminOrgInfoList();
			//非空处理
			if(CollectionUtils.isNotEmpty(orgInfos)){
				//批处理来自UUMS的组织信息-187862-dujunhui
				//存入信息至FOSS上游中间表
				for(MdmOrgInfo info:orgInfos){
					SendMdmOrgProcessReult result=new SendMdmOrgProcessReult();
					OrgAdministrativeInfoEntity myEntity= this.transToFossforUpdateOrAdd(info, null, false);
					OrgAdministrativeInfoEntity addEntity=orgAdministrativeInfoService.addUUMSToFOSS(myEntity);
					if(addEntity!=null){//插入中间表成功
						successCount++;
						result.setResult(true);
						result.setOrgBenchmarkCode(addEntity.getUnifiedCode());
						result.setOrgChangeId(addEntity.getId());
					}
					else{
						failCount++;
						result.setResult(false);
						result.setOrgBenchmarkCode(myEntity.getUnifiedCode());
						result.setOrgChangeId(myEntity.getId());
						result.setReason("UUMS组织信息新增至FOSS中间表失败!");
					}
					detailList.add(result);
				}
				
				//设置成功条数
				orgResponse.setSuccessCount(successCount);
				orgResponse.setFailedCount(failCount);
				orgResponse.setProcessResultList(detailList);
				orgResponse.setSysName("FOSS");
				LOGGER.info("UUMS调用FOSS同步行政组织接口，返回报文:\n"
						+ new SendOrgInfoResponseTrans()
								.fromMessage(orgResponse));
			}
			LOGGER.info("------------------------------------同步主数据项目：组织信息新增至中间表end-----------------------------------");
		}
		return orgResponse;
	}

	/**
	 * 将同步过来的Object 转换成Foss对象
	 * @param mdmOrgInfo
	 * @param orginfoResult
	 * @return
	 */
	private OrgAdministrativeInfoEntity transToFossforUpdateOrAdd(
			MdmOrgInfo mdmOrgInfo, OrgAdministrativeInfoEntity fossEntity,boolean isUpdate) {
		//若是新增，新建实体
		if(!isUpdate && null ==fossEntity){
			fossEntity = new OrgAdministrativeInfoEntity();
		}
		//保存UUMS传过来的组织变动ID
		fossEntity.setId(mdmOrgInfo.getOrgChangeId());
		//是否启用
		fossEntity.setActive(mdmOrgInfo.getActive());
		//组织编码
		if(StringUtil.isNotEmpty(mdmOrgInfo.getOrgCode())){
			fossEntity.setCode(mdmOrgInfo.getOrgCode().trim());
		}
		//组织名称
		if(StringUtil.isNotEmpty(mdmOrgInfo.getOrgName())){
			fossEntity.setName(mdmOrgInfo.getOrgName().trim());
		}
		//地址
		fossEntity.setAddress(mdmOrgInfo.getOrgAddress());
		//是否可空运配载
		fossEntity.setAirDispatch(mdmOrgInfo.getAirDispatch());
		//地区编码（已作废）
		//fossEntity.setAreaCode();
		//理货业务类型
		fossEntity.setArrangeBizType(mdmOrgInfo.getArrangeBizType());
		//理货部门所属外场
		fossEntity.setArrangeOutfield(mdmOrgInfo.getArrangeOutField());
		 
		//开始时间
		fossEntity.setBeginTimeOfUU(mdmOrgInfo.getCreateDate());
		//结束时间
		fossEntity.setEndTimeOfUU(mdmOrgInfo.getModifyDate());
		//创建时间
		fossEntity.setCreateDateOfUU(mdmOrgInfo.getBeginTime());//配合UUMS时间拉链
		//更新时间
		fossEntity.setModifyDateOfUU(mdmOrgInfo.getEndTime());//配合UUMS时间拉链
		
		//是否营业大区
		fossEntity.setBigRegion(mdmOrgInfo.getBigRegion());
		//是否集中开单组
		fossEntity.setBillingGroup(mdmOrgInfo.getBillingGroup());
		//城市编码
		fossEntity.setCityCode(mdmOrgInfo.getCityCode());
		//部门补码简称
		fossEntity.setComplementSimpleName(mdmOrgInfo.getComplementSimpleName());
		//成本中心（已作废）
		//fossEntity.setCostCenterCode(mdmOrgInfo.getc)
		//成本中心名称（已作废）
		//fossEntity.setCostCenterName(costCenterName)
		//国家地区
		fossEntity.setCountryRegion(mdmOrgInfo.getCountryRegion());
		//地区编码
		fossEntity.setCountyCode(mdmOrgInfo.getCountyCode());
		//创建人
		fossEntity.setCreateUser(mdmOrgInfo.getCreateUser());
		//修改人
		fossEntity.setModifyUser(mdmOrgInfo.getModifyUser());
		//排单部门所属外场编码
		fossEntity.setDeliverOutfield(mdmOrgInfo.getDeliverOutField());
		//部门坐标标杆编码
		fossEntity.setDepCoordinate(mdmOrgInfo.getDepCoordinate());
		//组织传真
		fossEntity.setDepFax(mdmOrgInfo.getOrgFax());
		//部门面积
		fossEntity.setDeptArea(mdmOrgInfo.getDeptArea()==null?null:new BigDecimal(mdmOrgInfo.getDeptArea()));
		//部门属性
		fossEntity.setDeptAttribute(mdmOrgInfo.getDeptAttribute());
		//部门描述
		fossEntity.setDeptDesc(mdmOrgInfo.getDeptDesc());
		//部门电话
		fossEntity.setDepTelephone(mdmOrgInfo.getOrgPhone());
		//部门邮箱
		fossEntity.setDeptEmail(mdmOrgInfo.getOrgEmail());
		//是否车队调度组
		fossEntity.setDispatchTeam(mdmOrgInfo.getDispatchTeam());
		//是否事业部
		fossEntity.setDivision(mdmOrgInfo.getIsDivision());
		//事业部编码
		fossEntity.setDivisionCode(mdmOrgInfo.getDivisionCode());
		//是否可空运配载
		fossEntity.setDoAirDispatch(mdmOrgInfo.getDoAirDispatch());
		//ehr部门编码（已作废）
		//fossEntity.setEhrDeptCode(mdmOrgInfo.geto)
		//是否快递大区
		fossEntity.setExpressBigRegion(mdmOrgInfo.getIsExpressRegion());
		//是否快递点部
		fossEntity.setExpressPart(mdmOrgInfo.getIsExpressPart());
		//是否快递分部
		fossEntity.setExpressBranch(mdmOrgInfo.getIsExpressBranch());
		//是否虚拟快递营业部
		fossEntity.setExpressSalesDepartment(mdmOrgInfo.getIsvirualLading());
		//是否快递分拣
		fossEntity.setExpressSorting(mdmOrgInfo.getIsExpressSorting());
		//是否理货部门
		fossEntity.setIsArrangeGoods(mdmOrgInfo.getIsArrangeGoods());
		//是否排单部门
		fossEntity.setIsDeliverSchedule(mdmOrgInfo.getIsDeliverSchedule());
		//是否财务实体部
		fossEntity.setIsEntityFinance(mdmOrgInfo.getIsEntityFinance());
		//是否叶子节点
		fossEntity.setIsLeaf(mdmOrgInfo.isIsLeaf()?FossConstants.YES:FossConstants.NO);
		//是否保安组
		fossEntity.setIsSecurity(mdmOrgInfo.getIsSecurity());
		//部门层级
		if((Integer)(mdmOrgInfo.getOrgLevel())!=null){
			fossEntity.setDeptLevel(String.valueOf(mdmOrgInfo.getOrgLevel()));
		}
		//上级编码
		if(StringUtil.isNotEmpty(mdmOrgInfo.getParentOrgCode())){
			fossEntity.setParentOrgCode(mdmOrgInfo.getParentOrgCode().trim());
		}
		//上级名称
		if(StringUtil.isNotEmpty(mdmOrgInfo.getParentOrgName())){
			fossEntity.setParentOrgName(mdmOrgInfo.getParentOrgName().trim());
		}
		//上级标杆编码
		fossEntity.setParentOrgUnifiedCode(mdmOrgInfo.getParentOrgStandCode());
		//部门拼音
		fossEntity.setPinyin(mdmOrgInfo.getPinYin());
		//部门负责人
		fossEntity.setPrincipalNo(mdmOrgInfo.getOrgManager());
		//省编码
		fossEntity.setProvCode(mdmOrgInfo.getProvCode());
		//是否营业部
		fossEntity.setSalesDepartment(mdmOrgInfo.getSalesDepartment());
		//是否营业小区
		fossEntity.setSmallRegion(mdmOrgInfo.getSmallRegion());
		//部门状态
		fossEntity.setStatus(String.valueOf(mdmOrgInfo.getOrgStatus()));
		//所属财务公司编码
		fossEntity.setSubsidiaryCode(mdmOrgInfo.getOrgCompanyCode());
		//所属公司名称
		fossEntity.setSubsidiaryName(mdmOrgInfo.getSubCompName());
		//是否车队
		fossEntity.setTransDepartment(mdmOrgInfo.getTransDepartment());
		//是否外场
		fossEntity.setTransferCenter(mdmOrgInfo.getTransferCenter());
		//是否车队组
		fossEntity.setTransTeam(mdmOrgInfo.getTransTeam());
		//部门标杆编码
		if(StringUtil.isNotEmpty(mdmOrgInfo.getOrgBenchmarkCode())){
			fossEntity.setUnifiedCode(mdmOrgInfo.getOrgBenchmarkCode().trim());
		}
		//uumsId
		fossEntity.setUumsId(String.valueOf(mdmOrgInfo.getOrgId()));
		//parantId
		fossEntity.setUumsParentId(String.valueOf(mdmOrgInfo.getParentOrgId()));
		//邮箱id
		fossEntity.setZipCode(mdmOrgInfo.getOrgZipCode());
		//uumsID序列
		fossEntity.setUumsIds(String.valueOf(mdmOrgInfo.getDeptSeq()));
		//部门简称
		if(StringUtil.isNotEmpty(mdmOrgInfo.getDeptShortName())){
			fossEntity.setOrgSimpleName(mdmOrgInfo.getDeptShortName().trim());
		}
		
		return fossEntity;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		
		return null;
	}
}
