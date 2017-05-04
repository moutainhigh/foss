package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.inteface.domain.ludanworkflow.ExpressAutomaticMakeup;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IEamDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEamService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IFossWithOthersForEmaService;
import com.deppon.foss.module.pickup.waybill.shared.domain.EamDto;
import com.deppon.foss.module.pickup.waybill.shared.util.ExpressAutomaticMakeupUtil;
import com.deppon.foss.module.pickup.waybill.shared.vo.ExpressAutoMakeupVo;

/**
 *@项目：快递自动补录
 *@功能：订单表（刷单表）与日志表的增删改查
 *@author:218371-foss-zhaoyanjun
 *@date:2015-06-01下午14:06
 */
public class EamService implements IEamService{
	/*
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EamService.class);
	
	/*
	 *数据库操作实现层
	 */
	private IEamDao eamDao;
	/**
	 * 回传补录运单成功与否给录单中心
	 */
	IFossWithOthersForEmaService fossWithOthersForEmaService;
	
	public void setFossWithOthersForEmaService(
			IFossWithOthersForEmaService fossWithOthersForEmaService) {
		this.fossWithOthersForEmaService = fossWithOthersForEmaService;
	}
	//新增eam订单表(SUCCESS:插入成功，FAIL:插入失败)
	@Override
	public Map<String,List<String>> eamOrderInsert(
			List<EamDto> eamDtos) {
		// TODO Auto-generated method stub
		if(eamDtos==null){
			return null;
		}
		Map<String,List<String>> resultMap=new HashMap<String,List<String>>();
		List<String> s=new ArrayList<String>();
		List<String> f=new ArrayList<String>();
		for(EamDto eam:eamDtos){
			try {
				if(eamDao.eamOrderInsert(eam)>0){
					s.add(eam.getWaybillNo());
				}
				else{
					if(eam.getRemarks()==null){
						eam.setRemarks("insertOrder:false");
					}else{
						eam.setRemarks(eam.getRemarks()+",insertOrder:false");
					}
					eam.setBillNumberState("N");
					f.add(eam.getWaybillNo());
				}
			} catch (Exception e) {
				// TODO: handle exception
				if(eam.getRemarks()==null){
					eam.setRemarks("insertOrder:false");
				}else{
					eam.setRemarks(",insertOrder:false");
				}
				eam.setBillNumberState("N");
				f.add(eam.getWaybillNo());
			}
		}
		resultMap.put("SUCCESS", s);
		resultMap.put("FAIL", f);
		return resultMap;
	}
	
	//删除eam订单表(SUCCESS:删除成功，FAIL：删除失败)
	@Override
	public String eamOrderDelete(List<String> wayBillNos) {
		// TODO Auto-generated method stub
		try{
			eamDao.eamOrderDelete(wayBillNos);
			return "SUCCESS";
		}catch(Exception e){
			return "FAIL";
		}
	}
	
	//查询eam订单表(SUCCESS：查询到的，FAIL：没查到的)
	@Override
	public Map<String,List<EamDto>> eamOrderQuery(List<String> wayBillNos) {
		// TODO Auto-generated method stub
		if(wayBillNos==null||wayBillNos.isEmpty()){
			return null;
		}
		Map<String,List<EamDto>> resultMap=new HashMap<String,List<EamDto>>();
		List<EamDto> s=new ArrayList<EamDto>();
		List<EamDto> f=new ArrayList<EamDto>();
		List<EamDto> eams=null;
		for(String waybillNo:wayBillNos){
			if(waybillNo==null){
				continue;
			}
			eams=eamDao.eamOrderQuery(waybillNo);
			if(eams==null){
				EamDto eam=new EamDto();
				eam.setWaybillNo(waybillNo);
				f.add(eam);
			}else{
				s.addAll(eams);
			}
		}
		resultMap.put("SUCCESS", s);
		resultMap.put("FAIL", f);
		return resultMap;
	}

	//新增eam日志表(SUCCESS：插入成功的，FAIL：插入失败的)
	@Override
	public Map<String,List<String>> eamLogInsert(
			List<EamDto> eamDtos) {
		// TODO Auto-generated method stub
		if(eamDtos==null){
			return null;
		}
		Map<String,List<String>> resultMap=new HashMap<String,List<String>>();
		List<String> s=new ArrayList<String>();
		List<String> f=new ArrayList<String>();
		for(EamDto eam:eamDtos){
			if(eam==null||eam.getWaybillNo()==null){
				continue;
			}
			try {
				if(eamDao.eamLogInsert(eam)>0){
					s.add(eam.getWaybillNo());
				}else{
					f.add(eam.getWaybillNo());
				}
			} catch (Exception e) {
				// TODO: handle exception
				f.add(eam.getWaybillNo());
			}
		}
		resultMap.put("SUCCESS", s);
		resultMap.put("FAIL", f);
		return resultMap;
	}
	
	//删除eam日志表
	@Override
	public String eamLogDelete(List<String> wayBillNos) {
		// TODO Auto-generated method stub
		try{
			eamDao.eamLogDelete(wayBillNos);
			return "SUCCESS";
		}catch(Exception e){
			return "FAIL";
		}
	}
	
	//修改eam日志表(SUCCESS:更改成功，FAIL：更改失败)
	@Override
	public Map<String,List<String>> eamLogeUpdate(
			List<EamDto> eamDtos) {
		// TODO Auto-generated method stub
		if(eamDtos==null){
			return null;
		}
		Map<String,List<String>> resultMap=new HashMap<String, List<String>>();
		List<String> s=new ArrayList<String>();
		List<String> f=new ArrayList<String>();
		for(EamDto eam:eamDtos){
			if(eam==null||eam.getWaybillNo()==null){
				continue;
			}
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("waybillNo", eam.getWaybillNo());
			map.put("billNumberState", eam.getBillNumberState());
			try {
				if(eamDao.eamLogeUpdate(map)>0){
					s.add(eam.getWaybillNo());
				}else{
					f.add(eam.getWaybillNo());
				}
			} catch (Exception e) {
				// TODO: handle exception
				f.add(eam.getWaybillNo());
			}
		}
		resultMap.put("SUCCESS", s);
		resultMap.put("FAIL", f);
		return resultMap;
	}
	
	//查询eam日志表(SUCCESS：查询到的，FAIL：没查到的)
	@Override
	public Map<String,List<EamDto>> eamLogeQuery(List<String> wayBillNos) {
		// TODO Auto-generated method stub
		if(wayBillNos==null){
			return null;
		}
		Map<String,List<EamDto>> resultMap=new HashMap<String,List<EamDto>>();
		List<EamDto> s=new ArrayList<EamDto>();
		List<EamDto> f=new ArrayList<EamDto>();
		List<EamDto> eams=null;
		for(String waybillNo:wayBillNos){
			if(waybillNo==null){
				continue;
			}
			eams=eamDao.eamLogeQuery(waybillNo);
			if(eams==null){
				EamDto eam=new EamDto();
				eam.setWaybillNo(waybillNo);
				f.add(eam);
			}else{
				s.addAll(eams);
			}
		}
		resultMap.put("SUCCESS", s);
		resultMap.put("FAIL", f);
		return resultMap;
	}
	
	

	public IEamDao geteamDao() {
		return eamDao;
	}

	public void seteamDao(IEamDao eamDao) {
		this.eamDao = eamDao;
	}

	/**
	 * @项目：快递自动补录项目
	 * @功能：验证是否满足expressAutomaticMakeupInput接口开单要求之前，要求/500单一次处理，防止内存溢出
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-06-01上午10:15
	 */
	@Override
	public Map<String,List<EamDto>> validateEAM(List<ExpressAutomaticMakeup> expressAutomaticMakeups){
		if(expressAutomaticMakeups==null){
			return null;
		}
		Map<String,List<EamDto>> resultMapTotle=new HashMap<String, List<EamDto>>();
		// 校验成功组
		List<EamDto> s = new ArrayList<EamDto>();
		// 校验不成功组
		List<EamDto> f = new ArrayList<EamDto>();
		resultMapTotle.put("SUCCESS", s);
		resultMapTotle.put("FAIL", f);
		List<EamDto> templist=new ArrayList<EamDto>();
		for(int i=0;i<expressAutomaticMakeups.size();i++){
			EamDto dto=new EamDto();
			try {
				PropertyUtils.copyProperties(dto,expressAutomaticMakeups.get(i));
				dto.setUploadTime(expressAutomaticMakeups.get(i).getUploadTime());
				dto.setBillNumberState("C");//未验证前状态
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			templist.add(dto);
			//不论验证是否通过，先把里录单中心传过来的数据保存 065340 liutao
			if(null!=templist && templist.size()>0){
				eamLogInsert(templist);
			}
			if((i!=0&&i% NumberConstants.NUMBER_500==0)||i+1==expressAutomaticMakeups.size()){
				Map<String,List<EamDto>> tempMap=valiteExpressMessage(templist);
				s.addAll(tempMap.get("SUCCESS"));
				f.addAll(tempMap.get("FAIL"));
				templist.clear();
			}
		}
		return resultMapTotle;
	}
	
	/**
	 * @项目：快递自动补录项目
	 * @功能：验证是否满足expressAutomaticMakeupInput接口开单要求
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-06-01上午10:15
	 */
	private Map<String,List<EamDto>> valiteExpressMessage(List<EamDto> eamDtos){
		boolean flag=false;
		//将每一个订单分类好的返回的结果分为（"SUCCESS"组和"FAIL"组）
		Map<String,List<EamDto>> resultMapTotle=new HashMap<String, List<EamDto>>();
		//校验成功组
		List<EamDto> s=new ArrayList<EamDto>();
		//校验不成功组
		List<EamDto> f=new ArrayList<EamDto>();
		resultMapTotle.put("SUCCESS", s);
		resultMapTotle.put("FAIL", f);
		//每一个订单的结果
		Map<String,String> resultMap=new HashMap<String, String>();
		for(EamDto e:eamDtos){
			resultMap.put("flag", "true");
			resultMap.put("remarks", "");
			resultMap.put("deliveryCustomerMobilephone", "true");
			resultMap.put("deliveryCustomerphone", "true");
			resultMap.put("receiveCustomerMobilephone", "true");
			resultMap.put("receiveCustomerphone", "true");
			flag=false;
			//订单号校验
			flag=testWaybillNo(e.getWaybillNo());
			assembleVerificationInformation(resultMap,"waybillNo",flag);
			//发货客户姓名
			flag=ExpressAutomaticMakeupUtil.testDeliveryCustomerName(e.getDeliveryCustomerName());
			assembleVerificationInformation(resultMap,"deliveryCustomerName",flag);
			//发货客户手机号码
			flag=ExpressAutomaticMakeupUtil.testMobilephone(e.getDeliveryCustomerMobilePhone());
			assembleVerificationInformation(resultMap,"deliveryCustomerMobilephone",flag);
			//发货客户电话号码
			flag=ExpressAutomaticMakeupUtil.testPhone(e.getDeliveryCustomerPhone());
			assembleVerificationInformation(resultMap,"deliveryCustomerPhone",flag);
			//收货客户姓名
			flag=ExpressAutomaticMakeupUtil.testDeliveryCustomerName(e.getReceiveCustomerName());
			assembleVerificationInformation(resultMap,"receiveCustomerName",flag);
			//收货客户手机号码
			flag=ExpressAutomaticMakeupUtil.testMobilephone(e.getReceiveCustomerMobilePhone());
			assembleVerificationInformation(resultMap,"receiveCustomerMobilephone",flag);
			//收货客户电话号码
			flag=ExpressAutomaticMakeupUtil.testPhone(e.getReceiveCustomerPhone());
			assembleVerificationInformation(resultMap,"receiveCustomerPhone",flag);
			//收货人省
			flag=ExpressAutomaticMakeupUtil.testReceiveCustomerPOrC(e.getReceiveCustomerProvCode());
			assembleVerificationInformation(resultMap,"receiveCustomerProvCode",flag);
			//收货人市
			flag=ExpressAutomaticMakeupUtil.testReceiveCustomerPOrC(e.getReceiveCustomerCityCode());
			assembleVerificationInformation(resultMap,"receiveCustomerCityCode",flag);
			//收货人区
			flag=ExpressAutomaticMakeupUtil.testReceiveCustomerPOrC(e.getReceiveCustomerDistCode());
			assembleVerificationInformation(resultMap,"receiveCustomerDistCode",flag);
			//收货人镇
			flag=ExpressAutomaticMakeupUtil.testDeliveryCustomerPOrC(e.getReceiveCustomerTownCode());
			assembleVerificationInformation(resultMap,"receiveCustomerTownCode",flag);
			//收货人详细地址
			flag=ExpressAutomaticMakeupUtil.testReceiveCustomerAddress(e.getReceiveCustomerAddress());
			assembleVerificationInformation(resultMap,"receiveCustomerAddress",flag);
			//货物名称
			flag=ExpressAutomaticMakeupUtil.testDeliveryCustomerName(e.getGoodsName());
			assembleVerificationInformation(resultMap,"goodsName",flag);
			//保险申明价值
			flag=ExpressAutomaticMakeupUtil.testInsuranceAmount(e.getInsuranceAmount());
			if(flag==false){
				e.setInsuranceAmount(new BigInteger("0"));
			}
//			AssembleVerificationInformation(resultMap,"insuranceAmount",true);
			//代收货款退款类型
			flag=ExpressAutomaticMakeupUtil.testRefundType(e.getRefundType());
			assembleVerificationInformation(resultMap,"refundType",flag);
			//代收货款金额
			flag=ExpressAutomaticMakeupUtil.testInsuranceAmount(e.getCodAmount());
			if(flag==false){
				e.setCodAmount(new BigInteger("0"));
			}
//			AssembleVerificationInformation(resultMap,"codAmount",true);
			//开户名
			flag=ExpressAutomaticMakeupUtil.testAccountName(e.getAccountName());
			assembleVerificationInformation(resultMap,"accountName",flag);
			//开户行
			flag=ExpressAutomaticMakeupUtil.testAccountName(e.getAccountBank());
			assembleVerificationInformation(resultMap,"accountBank",flag);
			//收款帐号
			flag=ExpressAutomaticMakeupUtil.testAccountName(e.getCollectionAccount());
			assembleVerificationInformation(resultMap,"collectionAccount",flag);
			//包装费用
			flag=ExpressAutomaticMakeupUtil.testInsuranceAmount(e.getPackageFeeCanvas());
			if(flag==false){
				e.setPackageFeeCanvas(new BigInteger("0"));
			}
//			AssembleVerificationInformation(resultMap,"packageFeeCanvas",true);
			//发货人省
			flag=ExpressAutomaticMakeupUtil.testDeliveryCustomerPOrC(e.getDeliveryCustomerProvCode());
			assembleVerificationInformation(resultMap,"deliveryCustomerProvCode",flag);
			//发货人市
			flag=ExpressAutomaticMakeupUtil.testDeliveryCustomerPOrC(e.getDeliveryCustomerCityCode());
			assembleVerificationInformation(resultMap,"deliveryCustomerCityCode",flag);
			//发货人区
			flag=ExpressAutomaticMakeupUtil.testDeliveryCustomerPOrC(e.getDeliveryCustomerDistCode());
			assembleVerificationInformation(resultMap,"deliveryCustomerDistCode",flag);
			//发货人详细地址
			flag=ExpressAutomaticMakeupUtil.testDeliveryCustomerAddress(e.getDeliveryCustomerAddress());
			assembleVerificationInformation(resultMap,"deliveryCustomerAddress",flag);
			//图片上传时间
			flag=ExpressAutomaticMakeupUtil.testUplordTime(e.getUploadTime());
			assembleVerificationInformation(resultMap,"uplordTime",flag);
			//筛选该订单，并装进返还的Map里
			screenOrder(e,resultMap,resultMapTotle);
		}
		return resultMapTotle;
	}
	
	/**
	 * @项目：快递自动补录项目
	 * @功能：组装验证不通过信息和校验是否满足验证
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-06-01上午10:15
	 */
	private void assembleVerificationInformation(Map<String,String> resultMap,String variableName,boolean flag){
		// 保存不满足验证的信息(注意：发货客户手机号码与电话号码有一个即可，收货客户同发货客户)
		String remarks = resultMap.get("remarks");
		if (!flag) {
			if (remarks==null) {
				remarks = variableName + ":false";
			} else {
				remarks = remarks + "," + variableName + ":false";
			}
		}
		//校验是否满足验证(注意：发货客户手机号码与电话号码有一个即可，收货客户同发货客户)
		if("true".equals(resultMap.get("flag"))){
			if("deliveryCustomerMobilephone".equals(variableName)&&!flag){
				resultMap.put("deliveryCustomerMobilephone", "false");
				if("false".equals(resultMap.get("deliveryCustomerMobilephone"))&&"false".equals(resultMap.get("deliveryCustomerPhone"))){
					resultMap.put("flag", "false");
				}
				
			}else if("deliveryCustomerPhone".equals(variableName)&&!flag){
				resultMap.put("deliveryCustomerPhone", "false");
				if("false".equals(resultMap.get("deliveryCustomerMobilephone"))&&"false".equals(resultMap.get("deliveryCustomerPhone"))){
					resultMap.put("flag", "false");
				}
			}else if("receiveCustomerMobilephone".equals(variableName)&&!flag){
				resultMap.put("receiveCustomerMobilephone", "false");
				if("false".equals(resultMap.get("receiveCustomerMobilephone"))&&"false".equals(resultMap.get("receiveCustomerPhone"))){
					resultMap.put("flag", "false");
				}
			}else if("receiveCustomerPhone".equals(variableName)&&!flag){
				resultMap.put("receiveCustomerPhone", "false");
				if("false".equals(resultMap.get("receiveCustomerMobilephone"))&&"false".equals(resultMap.get("receiveCustomerPhone"))){
					resultMap.put("flag", "false");
				}
			}else{
				if(!flag){
					resultMap.put("flag", "false");
				}
			}
		}
		resultMap.put("remarks", remarks);
	}
	
	/**
	 * @项目：快递自动补录项目
	 * @功能：筛选出合格的订单并存入Map中，相应组为"SUCCESS"组和"FAIL"组
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-06-01上午10:15
	 */
	private void screenOrder(EamDto e,Map<String,String> resultMap,Map<String,List<EamDto>> resultMapTotle){
		List<EamDto> s=resultMapTotle.get("SUCCESS");
		List<EamDto> f=resultMapTotle.get("FAIL");
		//若发货客户有手机号码或者电话号码时，取消该号码验证不成功信息，收货客户同发货客户
		String remarks=resultMap.get("remarks");
		remarks=validateRemarks(remarks,"deliveryCustomerMobilephone","deliveryCustomerPhone");
		remarks=validateRemarks(remarks,"receiveCustomerMobilephone","receiveCustomerPhone");
		e.setRemarks(remarks);
		//判断该单号是否验证成功，收入对应的list中
		if("true".equals(resultMap.get("flag"))){
			e.setBillNumberState("Y");
			s.add(e);
		}else{
			e.setBillNumberState("N");
			f.add(e);
		}
	}
	
	/**
	 * @项目：快递自动补录项目
	 * @功能：将日志信息的发货人与收货人电话重新验证是否符合规范，符合规范的日志删除
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-06-01上午10:15
	 */
	private String validateRemarks(String remarks,String mobilePhone,String phone){
		String[] personRemarks=remarks.split(",");
		boolean flag=true;
		outer:for(int i=0;i<personRemarks.length;i++){
			if(personRemarks[i].indexOf(mobilePhone)>=0){
				for(int j=0;j<personRemarks.length;j++){
					if(personRemarks[j].indexOf(phone)>=0){
						flag=false;
						break outer;
					}
				}
			}
		}
		List<String> tempPersonRemark=new ArrayList<String>();
		for(String temp:personRemarks){
			if((temp.indexOf(mobilePhone)>=0||temp.indexOf(phone)>=0)&&flag){
				continue;
			}
			tempPersonRemark.add(temp);
		}
		remarks=tempPersonRemark.toString();
		return remarks;
	}
	
	/**
	 * @项目：快递自动补录项目
	 * @功能：验证订单号是否重复
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-06-01上午10:15
	 */
	public boolean testWaybillNo(String waybillNo){
		        if(waybillNo==null
				||waybillNo.isEmpty()
				||eamDao.eamOrderQuery(waybillNo)!=null&&eamDao.eamOrderQuery(waybillNo).size()>0
//				||eamDao.eamLogeQuery(waybillNo)!=null&&eamDao.eamLogeQuery(waybillNo).size()>0
				||(waybillNo.indexOf(0)== NumberConstants.NUMBER_8||waybillNo.indexOf(0)== NumberConstants.NUMBER_9)){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * @项目：快递自动补录项目
	 * @功能：补录完成后处理扫描表和日志表数据
	 * @author:065340 liutao
	 */
		@Override
		@Transactional
		public void handleOrderAndLog(EamDto eamDto,String billNumberState) {
			List<String> wayBillNos = new ArrayList<String>();
			wayBillNos.add(eamDto.getWaybillNo());
			//无论补录成功与否，都要删除扫描表数据在日志表中插入数据
			//根据单号删除扫描表
			eamDao.eamOrderDelete(wayBillNos);
			List<EamDto> eamDtos = new ArrayList<EamDto>();
			eamDto.setBillNumberState(billNumberState);
			eamDto.setModifyTime(new Date());
			eamDtos.add(eamDto);
			//新增日志标的数据
			for(EamDto eam:eamDtos){
				eamDao.eamLogInsert(eam);
			}
			//回传运单补录状态给录单中心
			try {
				String context="";
				if("Y".equals(eamDto.getBillNumberState())){
					context="单号："+eamDto.getWaybillNo()+"，"+"开单成功";
				}else{
					String reg=".*null.*";  
					if(eamDto.getRemarks().matches(reg)){
						context="单号："+eamDto.getWaybillNo()+"，"+"系统异常....，请手动补录";
					}else{
						context="单号："+eamDto.getWaybillNo()+"，"+eamDto.getRemarks().substring(0, NumberConstants.NUMBER_100)+"....,请手动补录!";
					}
				}
				fossWithOthersForEmaService.postRecordCenter(eamDto.getWaybillNo(), billNumberState,eamDto.getUploadTime(),context);
			} catch (Exception e) {
				LOGGER.info("调用录单中心接口异常，请确认是否开启录单中心服务");
			}
		}
	
	/**
	 * @项目：快递自动补录项目
	 * @功能：修改jobID
	 * @author：218371-foss-zhaoyanjun
	 * @date:2015-07-11上午08:48
	 */
	public int updateJobIDTopByRowNum(ExpressAutoMakeupVo vo){
		return eamDao.updateJobIDTopByRowNum(vo);
	}
	
	/**
	 * @项目：快递自动补录项目
	 * @功能：修改jobID
	 * @author：218371-foss-zhaoyanjun
	 * @date:2015-07-11上午08:48
	 */
	public List<EamDto> queryGenerateUnActiveEamByJobID(String jobId){
		return eamDao.queryGenerateUnActiveEamByJobID(jobId);
	}
}
