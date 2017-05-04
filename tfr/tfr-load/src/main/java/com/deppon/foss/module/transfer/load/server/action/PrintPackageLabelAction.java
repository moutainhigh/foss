package com.deppon.foss.module.transfer.load.server.action;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IPrintPackageLabelService;
import com.deppon.foss.module.transfer.load.api.shared.vo.PrintPackageVo;
import com.deppon.foss.util.define.FossConstants;

public class PrintPackageLabelAction extends AbstractAction{

	private static final long serialVersionUID = 9166752079380663524L;
	private PrintPackageVo printPackageVo=new PrintPackageVo();
	private IPrintPackageLabelService printPackageLabelService;
	/**
	 * 报包标签打印的入口action
	 * @author 205109-zenghaibin
	 * @date 2014-10-23 上午10:17:34
	 */
	@JSON
	public String printPackageLabelIndex(){
		
		return  returnSuccess();
	}
	
	/**
	 * 校验包标签是否正确
	 * @author 205109-zenghaibin
	 * @date 2014-10-23 上午10:17:34
	 */
	@JSON
	public String validatePackageNo(){
		try{
			String packageNo=printPackageVo.getPackageNo();
			String isRight=printPackageLabelService.validatePackageNo(packageNo);
			printPackageVo.setIsRight(isRight);
		}catch(TfrBusinessException e){
			return returnError(e);
		}catch(Exception e){
			return returnError(e.toString());
		}
		return  returnSuccess();
	
	}
	

	/**
	 * 校验包号和用户名是否正确
	 * @author 205109-zenghaibin
	 * @date 2014-10-25 上午10:17:34
	 */
	@JSON
	public String validation(){
		try{
			
			String isRight=printPackageLabelService.validation(printPackageVo);
			printPackageVo.setIsRight(isRight);
		}catch(TfrBusinessException e){
			return returnError(e);
		}catch(Exception e){
			return returnError(e.toString());
		}
		return  returnSuccess();
	
	}
	/**
	 * 生成是否正确
	 * @author 205109-zenghaibin
	 * @date 2014-10-25 上午10:17:34
	 */
	@JSON
	public String creatPackageNo(){
		try{
			List<String> printPackageNoList= printPackageLabelService.creatPackageNo(printPackageVo);
			printPackageVo.setIsRight(FossConstants.YES);
			printPackageVo.setPrintPackageNoList(printPackageNoList);
		}catch(TfrBusinessException e){
			return returnError(e);
		}catch(Exception e){
			return returnError(e.toString());
		}
		return  returnSuccess();
	
	}
	/**
	 * 添加包标签打印日志
	 * @author 205109-zenghaibin
	 * @date 2014-10-25 上午10:17:34
	 */
	@JSON
	public String addPackagePrintLog(){
		
			 printPackageLabelService.addPackagePrintLog(printPackageVo);
		
			 return  returnSuccess();
	
	}
	
	
	
	public PrintPackageVo getPrintPackageVo() {
		return printPackageVo;
	}

	public void setPrintPackageVo(PrintPackageVo printPackageVo) {
		this.printPackageVo = printPackageVo;
	}

	public void setPrintPackageLabelService(
			IPrintPackageLabelService printPackageLabelService) {
		this.printPackageLabelService = printPackageLabelService;
	}
	
	
}
