/**   
 * @Title: UploadFileService.java 
 * @Package com.deppon.pda.bdm.module.ocb.server.service.impl 
 * @Description: 
 * @author 183272
 * @date 2014年10月13日 下午5:21:37 
 * @version V1.0   
 */
package com.deppon.pda.bdm.module.ocb.server.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPicturePdaDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.server.service.IOcrService;
import com.deppon.pda.bdm.module.core.shared.constants.OcrContant;
import com.deppon.pda.bdm.module.core.shared.constants.version.OcbConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.DateUtils;
import com.deppon.pda.bdm.module.ocb.server.dao.IMobileExceptionDao;
import com.deppon.pda.bdm.module.ocb.server.dao.impl.UploadImageDao;
import com.deppon.pda.bdm.module.ocb.shared.domain.Constant;
import com.deppon.pda.bdm.module.ocb.shared.domain.MobileExceptionBean;
import com.deppon.pda.bdm.module.ocb.shared.domain.UploadImageSnippetBean;

/**
 * @ClassName: UploadFileService
 * @Description:
 * @author 183272
 * @date 2014年10月13日 下午5:21:37
 * 
 */
public class UploadImageService implements IBusinessService<UploadImageSnippetBean, Void> {
	private static final Log LOG = LogFactory.getLog(UploadImageService.class);
	private UploadImageDao uploadImageDao;
	private IPdaWaybillService pdaWaybillService;
	private IMobileExceptionDao mobileExceptionDao;
	private IOcrService ocrService;	
	public IOcrService getOcrService() {
		return ocrService;
	}
	public void setOcrService(IOcrService ocrService) {
		this.ocrService = ocrService;
	}
	/**
	 * @param uploadImageDao
	 *            要设置的 uploadImageDao
	 */
	public void setUploadImageDao(UploadImageDao uploadImageDao) {
		this.uploadImageDao = uploadImageDao;
	}
	/** 
	 * @param pdaWaybillService 要设置的 pdaWaybillService 
	 */
	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}
	
	public void setMobileExceptionDao(IMobileExceptionDao mobileExceptionDao) {
		this.mobileExceptionDao = mobileExceptionDao;
	}
	
	/**
	 * @Title: parseBody
	 * @Description:
	 * @author 183272
	 * @date 2014年10月14日 上午9:01:23
	 * @param @param asyncMsg
	 * @param @return
	 * @param @throws PdaBusiException 设定文件
	 * @throws
	 */
	@Override
	public Void parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		LOG.info("invoke uploadImage service parseBody start...");
		return null;
	}

	/**
	 * @Title: service
	 * @Description:
	 * @author 183272
	 * @date 2014年10月14日 上午9:01:23
	 * @param @param asyncMsg
	 * @param @param param
	 * @param @return
	 * @param @throws PdaBusiException 设定文件
	 * @throws
	 */
	@Override
	public UploadImageSnippetBean service(AsyncMsg asyncMsg, Void param) throws PdaBusiException {
		LOG.info("invoke uploadImage service start...");
		
		if (asyncMsg == null || StringUtils.isEmpty(asyncMsg.getContent())) {
			throw new PdaBusiException(new Throwable("图片上传异常"));
		}
		
		
		// 保存图片数据
		UploadImageSnippetBean uis = JSONObject.parseObject(asyncMsg.getContent(), UploadImageSnippetBean.class);
		if (uis == null) {
			throw new PdaBusiException(new Throwable("图片上传异常：上传数据不能正常转换"));
		}
		String md5Upload = uis.getFhashcode();
		String md5File = this.getMD5(uis.getFpath());
		if(!md5Upload.equals(md5File)){
			throw new PdaBusiException(new Throwable("图片上传异常：碎片保存失败uuid="+uis.getUuid()));
		}
		
		uploadImageDao.saveUploadFile(uis);

		// 文件拆分个数
		int fblocks = uis.getFblocks();
		// 已上传个数   uuid|uuid
//		int freadys = 0;
		String fready = uis.getFready();
		String[] freadyArray = null ;
		if (StringUtils.isNotEmpty(fready)) {
			 freadyArray = fready.split("\\|");
//			freadys = freadyArray.length;
		}
		
		File dirFile = new File(uis.getFpathparent());
		File[] files = dirFile.listFiles();
		
		
		if(files != null && files.length > 0){
		// 当为最后一块时,合并文件 ，存入数据库和文件系统中
			if (fblocks == files.length) {
				FileUtil fileUtil = new FileUtil();
				//所有的文件块的地址列表 
				ArrayList<String> pathList  = new ArrayList<String>();
	//			String fileFolderStr = com.deppon.pda.bdm.module.core.shared.constants.Constant.UPLOAD_IMAGE_PATH+"/"+uis.getDeptcode()+"/"+DateUtils.formatDate(new Date())+"/";
				//12.26 作死修改，文件夹格式   /时间/部门/UUID/运单图片
				String fileFolderStr = com.deppon.pda.bdm.module.core.shared.constants.Constant.UPLOAD_IMAGE_PATH+"/"+DateUtils.formatDate(new Date())+"/"+uis.getDeptcode()+"/"+uis.getWblcode()+"/"+uis.getUuid()+"/";
				File  fileFolder = new File(fileFolderStr);
					if(!fileFolder .exists()){
						fileFolder.mkdirs();
						fileFolder.setWritable(true);
						fileFolder.setReadable(true);
					}
				if("webp".equals(uis.getFtype())){
					uis.setFtype("zip");
				}
				String fileName = uis.getWblcode()+"."+uis.getFtype().split("\\.")[0];
				// 如果文件只有1块里
				if (fblocks != 1) {
					//查出所有的已上传的块的存放路径
					if(freadyArray!=null){
						for (int i = 0; i < freadyArray.length; i++) {
							String uuid = freadyArray[i];
							UploadImageSnippetBean u = uploadImageDao.getUploadFileByUUID(uuid);
							if(u!=null){
							pathList.add(u.getFpath());
							}else{
								return null;
							}
						}
					}
					//将当前块的存放路径放入列表中
					pathList.add(uis.getFpath());
					//对列表进行排序
					Collections.sort(pathList);
					
					//合并并保存图片
					
					boolean isSuccess = 	fileUtil.combFile(pathList, fileFolder+"/"+fileName);
					if(!isSuccess){
						//合成失败 删除文件及目录
						fileUtil.deleteDirectory(uis.getFpathparent());
						throw new PdaBusiException(new Throwable("图片合并异常"));
					}
				}else{
					//如果只有一个文件 ，将该 文件复制到文件系统中去
					File s = new File(uis.getFpath());
					File t = new File( fileFolder+"/"+fileName);
					fileChannelCopy(s, t);
				}
				/**
				 * @需求：图片上传加密优化需求
				 * @功能：对上传的文件进行解密
				 * @author:218371-foss-zhaoyanjun
				 * @date:2016-12-15上午10:51
				 */
				String newfilePath=decryptionDecompressionFile(fileFolder+"/"+fileName);
				
				WaybillPicturePdaDto wbp = new WaybillPicturePdaDto();
				wbp.setId(uis.getUuid());
				// 运单标识码
				wbp.setWaybillUuid(uis.getUuid());
				//运单号
				wbp.setWaybillNo(uis.getWblcode());
				//订单号
				wbp.setOrderNo(uis.getOrdercode());
				//司机code
				wbp.setDriverCode(uis.getUsercode());
				wbp.setTruckCode(uis.getTruckCode());
				wbp.setMobilephone(uis.getMobilephone());
				//是否大票货
				wbp.setBigGoodsFlag(uis.getIsfifty());
				//是否现金
				wbp.setCashPayFlag(uis.getIscash());
				//图片url
				wbp.setFilePath(newfilePath);
				//运单类型
				wbp.setPendgingType(Constant.UPLOAD_WAYBILL_TYPE);
				// 司机所在车队部门
				wbp.setBillOrgCode(asyncMsg.getDeptCode());
				//备注
				wbp.setRemark(null);
				//设备号
				wbp.setEquipmentNo(asyncMsg.getPdaCode());
				//receiveOrgCode
				wbp.setReceiveOrgCode(uis.getReceiveOrgCode());
				//百度push 拼接ID channleID+user_ID
				wbp.setBaiDuId(uis.getBaiDuId());
				//是否大件上楼加收
				wbp.setIsBigUp(uis.getIsBigUp());
				//500KG到1000KG超重件数
				wbp.setFhToOtOverQty(uis.getFhToOtOverQty());
				//1000KG到2000KG超重件数
				wbp.setOtToTtOverQty(uis.getOtToTtOverQty());
				//劳务费费率
				wbp.setServiceRate(uis.getServiceRate());
				//劳务费
				wbp.setServiceFee(uis.getServiceFee());
				//手机号
				wbp.setMobilephone(uis.getMobilephone());
				
				//author:245960 DATE:2015-08-08 COMMENT:王刚要求加字段传给foss
				wbp.setSpecialCustomer(uis.getSpecialCustomer());
				/**
				 * @项目：ocr一期
				 * @功能：保存开单时间
				 * @author:218371-foss-zhaoyanjun
				 * @date:20160801
				 */
				wbp.setCreatTime(new Date());
				//author  268974  展会货需求 前台对接鄢凌
				wbp.setIsExhibitCargo(uis.getExhibition());
				//删除运单号文件夹以及文件夹下的文件
				fileUtil.deleteDirectory(uis.getFpathparent());
				
				/*for (int i = 0; i < pathList.size(); i++) {
					fileUtil.DeleteFolder(pathList.get(i));
				}*/
				// 调用 FOSS接口
				/**
				 * @项目：ocr一期
				 * @功能：查询数据字典是否走ocr系统
				 * @author:218371-foss-zhaoyanjun
				 * @date:2016-08-01
				 */
				boolean dictionary=false;
				dictionary=whetherToOcr(wbp);
				if (dictionary&&wbp!=null) {
					try {
						//保存图片到数据库
						uploadImageDao.saveWaybillPicture(wbp);
						ocrService.insertToOcr(wbp);
					} catch (Exception e) {
						// TODO: handle exception
						LOG.error(e);
						uploadImageDao.saveWaybillPictureByOld(wbp);
						sendToFoss(wbp);
					}
				} else {
					if (wbp != null) {
						//保存图片到数据库
						uploadImageDao.saveWaybillPictureByOld(wbp);
						sendToFoss(wbp);
					}
				}
			}
		}
		return null;
	}
	
	//
	private String decryptionDecompressionFile(String filePath) {
		// TODO Auto-generated method stub
		try {
			ZipFile zFile = new ZipFile(filePath);
	        zFile.setFileNameCharset("GBK");
	        if (!zFile.isValidZipFile()) {
	            throw new ZipException("压缩文件不合法,可能被损坏.");
	        }
	        String dest=filePath.substring(0, filePath.lastIndexOf("/"));
	        if (zFile.isEncrypted()) {
	            zFile.setPassword("NoBug".toCharArray());
	        }
	        zFile.extractAll(dest);
	        zFile.getFile().delete();
	        return filePath.substring(0,filePath.indexOf(".")+1)+"webp";
		} catch (Exception e) {
			// TODO: handle exception
			throw new PdaBusiException(new Throwable("图片解压缩失败"));
		}
	}
	
	//判断是否经过ocr处理
	private boolean whetherToOcr(WaybillPicturePdaDto wbp) {
		// TODO Auto-generated method stub
		//数据字典判断map
		Map<String,Boolean> map=new HashMap<String,Boolean>();
		map.put("currentAreaFlag", false);
		map.put("totleFlag", false);
		try {
			DataDictionaryEntity dictEntity = DictUtil.getDataByTermsCode("OCR");
			List<DataDictionaryValueEntity> dataList = dictEntity.getDataDictionaryValueEntityList();
			if(dataList!=null&&!dataList.isEmpty()){
				for(DataDictionaryValueEntity entityAgree:dataList){
					if(whetherToOcrForSonar(wbp,entityAgree,map)){
						return true;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return false;
	}
	
	//whetherToOcr的sonar优化
	private boolean whetherToOcrForSonar(WaybillPicturePdaDto wbp, DataDictionaryValueEntity entityAgree, Map<String,Boolean> map){
		if("OCR_CONTROL".equals(entityAgree.getValueCode())){
			//全国运行ocr
			if(OcrContant.OcrAllArea.equals(entityAgree.getValueName())){
				return true;
			//ocr总开关关闭
			}else if(OcrContant.OcrNoArea.equals(entityAgree.getValueName())){
				return false;
			}else{
				map.put("totleFlag", true);
				// ocr总开关打开和地区开关匹配成功的情况下，返还true;
				if(map.get("totleFlag")&&map.get("currentAreaFlag")){
					return true;
				}
			}
		}else if(wbp!=null&&wbp.getBillOrgCode()!=null
				&&wbp.getBillOrgCode().equals(entityAgree.getValueCode())){
			map.put("currentAreaFlag", true);                                                                                                                                                                                                                                                                                                                                           
			// ocr总开关打开和地区开关匹配成功的情况下，返还true;
			if(map.get("totleFlag")&&map.get("currentAreaFlag")){
				return true;
			}
		}
		return false;
	}
	
	// 发送给foss
	private void sendToFoss(WaybillPicturePdaDto wbp) {
		// TODO Auto-generated method stub
		try {
			ResultDto rd = pdaWaybillService.submitWaybillPictureByPDA(wbp);
			LOG.info("FOSS接口调用返回" + rd.getCode() + "" + rd.getMsg());
		} catch (BusinessException s) {
			// TODO: handle exception
			LOG.error("FOSS接口调用返回异常", s);
			// 当抛异常时，保存异常信息
			MobileExceptionBean mobileExceptionBean = new MobileExceptionBean();
			mobileExceptionBean.setUsercode(wbp.getOrderNo());// 保存运单号
			mobileExceptionBean.setException(s.toString());// 保存异常信息
			// 调用FOSS接口失败时，将失败信息保存到数据库
			mobileExceptionDao.saveMobileException(mobileExceptionBean);
			throw new BusinessException("FOSS接口调用返回异常:" + s.getErrorCode());
		}
	}

	/**
	 * @Title: getOperType
	 * @Description:
	 * @author 183272
	 * @date 2014年10月14日 上午9:01:23
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public String getOperType() {
		// TODO Auto-generated method stub
		return OcbConstant.OPER_TYPE_OCB_IMG_SNIP.VERSION;
	}

	/**
	 * @Title: isAsync
	 * @Description:
	 * @author 183272
	 * @date 2014年10月14日 上午9:01:23
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	/**
	   * 
	  * @Title: getMD5 
	  * @Description: 得到HASHCODE值
	  * @author dpyuanjb@deppon.com/092039
	  * 2014年9月27日上午9:39:52
	  * @param @param filename
	  * @param @return
	  * @param @throws Exception    设定文件 
	  * @return String    返回类型 
	  * @throws
	   */
	public  String getMD5(String filename){
	byte[] b;
	String result = "";
	try {
	b = create(filename);
	for (int i = 0; i < b.length; i++) {
	result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	return result;
	}
	
	/**
	 * 
	* @Title: create 
	* @Description: 得到 文件字节数组
	* @author dpyuanjb@deppon.com/092039
	* 2014年9月27日上午9:45:59
	* @param @param filename
	* @param @return
	* @param @throws Exception    设定文件 
	* @return byte[]    返回类型 
	* @throws
	 */
	  public  byte[] create(String filename)   throws Exception{
	  InputStream fis = new FileInputStream(filename);
	  byte[] buf= new byte[1024];
	  MessageDigest com=MessageDigest.getInstance("MD5");
	  int num;
	  do{
	   num=fis.read(buf);
	   if(num>0){
	     com.update(buf,0,num);
	   }
	  }while(num!=-1);
	  fis.close();
	  return com.digest();
	  }
	
	
	/**
	 * 
	* @Title: fileChannelCopy 
	* @Description: 
	* @author 183272
	* @date 2014年10月28日 下午2:03:46 
	* @param @param s 源文件
	* @param @param t    复制到的新文件
	* @return void    返回类型 
	* @throws
	 */
	public void fileChannelCopy(File s, File t) {

        FileInputStream fi = null;

        FileOutputStream fo = null;

        FileChannel in = null;

        FileChannel out = null;

        try {

            fi = new FileInputStream(s);

            fo = new FileOutputStream(t);
            if(fi!=null){
            	in = fi.getChannel();//得到对应的文件通道
            }
            if(fo!=null){
            	out = fo.getChannel();//得到对应的文件通道
            }
            if(in!=null&&out!=null){
            	in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {
                if(fi!=null){
                  fi.close();
                }
                if(in!=null){
                	in.close();
                }
                if(fo!=null){
                	fo.close();
                }
                if(out!=null){
                	out.close();
                }
            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }
}
