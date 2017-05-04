/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.api.server.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.AdvPayWriteoffBillPayDto;


/**
 * 预付冲应付service
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:jefferson,date:2012-10-12 下午5:40:18,content: </p>
 * @author foss-pengzhen
 * @date 2012-10-12 下午5:40:18
 * @since
 * @version
 */
public interface IAdvPayWriteoffBillPayService extends IService{
	
	/**
     * 根据传入的一到多个预付单号，获取一到多条预付单信息
     * 
     * @author foss-pengzhen
     * @date 2012-10-17 上午9:50:50
     * @param advPayWriteoffBillPayDto,currentInfo
	 *      	预付冲应付参数Dto,当前登录用户
	 * @return List<BillAdvancedPaymentEntity>
	 * 			空运预付单集合
     * @see
     */
    List<BillAdvancedPaymentEntity> queryBillAdvancedPaymentNos(AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto,CurrentInfo currentInfo);

    /**
     * 根据传入获取一到多条预付单信息
     * 
     * @author foss-pengzhen
     * @date 2012-10-16 下午3:43:20
      * @param advPayWriteoffBillPayDto,currentInfo
	 *      	预付冲应付参数Dto,当前登录用户
	 * @return List<BillAdvancedPaymentEntity>
	 * 			空运预付单集合
     * @see
     */
    List<BillAdvancedPaymentEntity> queryBillAdvancedPaymentParams(AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto,CurrentInfo currentInfo);

    /**
     * 
     * 根据传入的一到多个应付单号，获取一到多条应付单信息
     * 
     * @author foss-pengzhen
     * @date 2012-10-17 上午11:58:36
     * @param advPayWriteoffBillPayDto,currentInfo
	 *      	预付冲应付参数Dto,当前登录用户
	 * @return List<BillPayableEntity>
	 * 			应付单集合
     * @see 
     */
    List<BillPayableEntity> queryPayableNOs(AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto,CurrentInfo currentInfo);
    
    /**
     * 根据传入的参数获取一到多条应付单信息
     * 
     * @author foss-pengzhen
     * @date 2012-10-16 下午4:44:23
     * @param advPayWriteoffBillPayDto,currentInfo
	 *      	预付冲应付参数Dto,当前登录用户
	 * @return List<BillPayableEntity>
	 * 			应付单集合
     */
    List<BillPayableEntity> queryPayableParams(AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto,CurrentInfo currentInfo);
    
    /**
	 * 导出预付单
	 * @author foss-pengzhen
	 * @date 2012-12-11 下午4:16:38
	 * @param advPayWriteoffBillPayDto,currentInfo
	 *      	预付冲应付参数Dto,当前登录用户
	 * @return HSSFWorkbook
	 * 			报表
	 * @see
	 */
	HSSFWorkbook advancedListExport(AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto,CurrentInfo currentInfo);
	
	/**
	 * 导出应付单
	 * @author foss-pengzhen
	 * @date 2012-12-11 下午4:16:38
	 * @param advPayWriteoffBillPayDto,currentInfo
	 *      	预付冲应付参数Dto,当前登录用户
	 * @return HSSFWorkbook
	 * 			报表
	 * @see
	 */
	HSSFWorkbook payableListExport(AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto,CurrentInfo currentInfo);
	
    /**
	 * 预付冲应付
	 * @author foss-pengzhen
	 * @date 2012-10-18 上午10:50:06
	 * @param advPayWriteoffBillPayDto,currentInfo
	 *      	预付冲应付参数Dto,当前登录用户
	 * @return AdvPayWriteoffBillPayDto
	 * 			预付冲应付参数Dto
	 */
	AdvPayWriteoffBillPayDto writeoffAdvancedAndPayable(AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto,CurrentInfo currentInfo);
}
