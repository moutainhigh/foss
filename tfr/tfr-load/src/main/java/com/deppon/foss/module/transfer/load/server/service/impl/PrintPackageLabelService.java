package com.deppon.foss.module.transfer.load.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IPrintPackageLabelDao;
import com.deppon.foss.module.transfer.load.api.server.service.IPrintPackageLabelService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.dto.PackagePrintLogDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.PrintPackageVo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class PrintPackageLabelService implements IPrintPackageLabelService {
	
	private IPrintPackageLabelDao printPackageLabelDao;
	private ITfrCommonService tfrCommonService;//生成包编号序列号的service 
	private IUserService userService;//校验用户和密码
	private static final Logger LOGGER = LogManager
			.getLogger(PrintPackageLabelService.class);
	/**
	 * 校验包标签是否正确
	 * @author 205109-zenghaibin
	 * @date 2014-10-25 上午10:17:34
	 * @param packageNo 包号
	 */
	@Override
	public String validatePackageNo(String packageNo) {
		if(StringUtils.isBlank(packageNo)){
			
			throw new TfrBusinessException("包号为空");
		}
		Long result=0L;
		result=printPackageLabelDao.validatePackageNo(packageNo);
		if(result>0){
			
			return FossConstants.YES;
		}else{
			return FossConstants.NO;
		}
		
	}
	
	/**
	 * 打印指定包标签的时候
	 * 校验包号和用户名是否正确
	 * @author 205109-zenghaibin
	 * @date 2014-10-25 上午10:17:34
	 * @param packageNo 包号
	 */
	@Override
	public String validation(PrintPackageVo printPackageVo) {
		LOGGER.info("TFR-load 打印指定包标签校验用户名和密码Start。。。");
		String packageNo=printPackageVo.getPackageNo();
		String empCode=printPackageVo.getEmpCode();
		String empPassword=printPackageVo.getEmpPassword();
		String packageResult="";//包校验结果
		String empResult="";//用户密码校验结果
		if(StringUtils.isBlank(packageNo)){
			throw new TfrBusinessException("包号为空");
		}
		if(StringUtils.isBlank(empCode)||StringUtils.isBlank(empPassword)){
			throw new TfrBusinessException("请输入用户名和密码");
		}
		packageResult=this.validatePackageNo(packageNo);
		if(!StringUtils.equals(packageResult, FossConstants.YES)){
			throw new TfrBusinessException("请输入正确的包号");
		}
		empResult=this.validateEmp(empCode, empPassword);
		if(!StringUtils.equals(empResult, FossConstants.YES)){
			
			throw new TfrBusinessException("用户或密码错误");
		}
		LOGGER.info("TFR-load 打印指定包标签校验用户名和密码end。。。");
		return FossConstants.YES;
	
	}
	
	/**
	 * 打印并生成包号
	 * 校验包号和用户名是否正确
	 * @author 205109-zenghaibin
	 * @date 2014-10-25 上午10:17:34
	 * @param printPackageVo 包号
	 */
	@Override
	public List<String> creatPackageNo(PrintPackageVo printPackageVo){
		LOGGER.info("TFR-load 打印随机包标签Start。。。");
		String empCode=printPackageVo.getEmpCode();
		String empPassword=printPackageVo.getEmpPassword();
		String empResult="";//用户密码校验结果
		List<String> packageNoList=new ArrayList<String>();
		int numbers=printPackageVo.getNumbers();
		if(StringUtils.isBlank(empCode)||StringUtils.isBlank(empPassword)){
			throw new TfrBusinessException("请输入用户名和密码");
		}
		empResult=this.validateEmp(empCode, empPassword);//校验用户密码
		if(!StringUtils.equals(empResult, FossConstants.YES)){
			
			throw new TfrBusinessException("用户或密码错误");
		}
		if(numbers<=0||numbers>LoadConstants.SONAR_NUMBER_50){
			throw new TfrBusinessException("请确认打印份数");
		}
		LOGGER.info("TFR-load 生成随机包号start。。。");
		packageNoList=createPackage(numbers);
		LOGGER.info("TFR-load 生成指定包结束。。。");
		return packageNoList;
	}
	
	/**
	 * 添加包标签打印日志
	 * @author 205109-zenghaibin
	 * @date 2014-10-25 上午10:17:34
	 * @param printPackageVo 包号，打印人等信息addPackagePrintLog
	 */
	@Override
	public void addPackagePrintLog(PrintPackageVo printPackageVo){
		LOGGER.info("TFR-load 插入打印日志start。。。");
		CurrentInfo user = FossUserContext.getCurrentInfo();//当前登入人员对象
		PackagePrintLogDto dto=new PackagePrintLogDto();
		dto.setId(UUIDUtils.getUUID());//ID
		dto.setDeptCode(user.getCurrentDeptCode());//登录部门编码
		dto.setDeptName(user.getCurrentDeptName());//登录部门名称
		dto.setPackageNo(printPackageVo.getPackageNo());//包号
		dto.setPrintTime(new Date());
		dto.setEmpCode(user.getEmpCode());//登录人编码
		dto.setPrintPersonCode(printPackageVo.getEmpCode());//打打印人编码
		printPackageLabelDao.addPackagePrintLog(dto);
		LOGGER.info("TFR-load 插入打印日志end。。。");
	}
	
	
	/**
	 * 生成包号规则为“B+6位日期（8位日期不取前两位）+5为随机数”
	 * @author 205109-zenghaibin
	 * @date 2014-10-28 上午09:39
	 * @param numers 份数，生成多少个包号
	 ***/
	private List<String> createPackage(int numbers){
		List<String> packageNoList=new ArrayList<String>();
		for(int i=0;i<numbers;i++){
		String packageNo=tfrCommonService.generateSerialNumberRequireNew(TfrSerialNumberRuleEnum.BH,"");
			if(!StringUtils.isBlank(packageNo)&&packageNo.length()==LoadConstants.SONAR_NUMBER_13){
				int length=packageNo.length();
				packageNo="B"+packageNo.substring(2, length);
				packageNoList.add(packageNo);
				LOGGER.info("TFR-load打印包标签 生成包号"+packageNo);
			}
		}
		if(packageNoList.isEmpty()){
			throw new TfrBusinessException("包号生成失败");
		}
		return packageNoList;
	}
	
	/**
	 * 校验包号和用户名是否正确
	 * @author 205109-zenghaibin
	 * @date 2014-10-25 上午10:17:34
	 * @param empCode empPassword 用户名和密码
	 */
	private	 String validateEmp(String empCode,String empPassword){
		boolean flag=false;
		try{
		 flag=userService.queryUserForValidationCorrectness(empCode,empPassword);
		}catch(Exception e){
			throw new TfrBusinessException("调用综合验证密码接口异常");
		}
		if(flag){
			return FossConstants.YES;
		}else{
			return FossConstants.NO;
		}
	}
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setPrintPackageLabelDao(IPrintPackageLabelDao printPackageLabelDao) {
		this.printPackageLabelDao = printPackageLabelDao;
	}

	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}
	
}
