package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgGisUrlDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgGisUrlEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 同步给移动开发GIS长地址Dao实现
 * 
 * @ClassName: OrgGisUrlDao
 * @author 200664-yangjinheng
 * @date 2014年10月21日 上午10:40:23
 */
public class OrgGisUrlDao extends SqlSessionDaoSupport implements IOrgGisUrlDao {

	/**
	 * 
	 * mybatis 命名空间
	 */
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".orgGisUrl.";

	private char hexChar[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 根据移动端提供的短地址查询长地址
	 * 
	 * @Title: queryLongUrlByShortUrl
	 * @author 200664-yangjinheng
	 * @date 2014年10月23日 上午11:31:51
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public OrgGisUrlEntity queryLongUrlByShortUrl(String shortUrl) {
		// 组装查询条件
		OrgGisUrlEntity entity = new OrgGisUrlEntity();
		entity.setShortUrl(shortUrl);
		entity.setActive(FossConstants.ACTIVE);
		List<OrgGisUrlEntity> entitys = this.getSqlSession().selectList(NAMESPACE + "queryLongUrlByShortUrl", entity);
		if (entitys == null || entitys.isEmpty()) {
			return null;
		} else {
			return entitys.get(NumberConstants.ZERO);
		}
	}

	/**
	 * 根据组织Code查询
	 * 
	 * @Title: queryGisUrlInfoByOrgCode
	 * @author 200664-yangjinheng
	 * @date 2014年10月21日 上午10:40:57
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public OrgGisUrlEntity queryGisUrlInfoByOrgCode(String code) {
		if(StringUtils.isBlank(code)){
			return null;
		}
		// 组装查询条件
		OrgGisUrlEntity entity = new OrgGisUrlEntity();
		entity.setOrgCode(code);
		entity.setActive(FossConstants.ACTIVE);

		List<OrgGisUrlEntity> entitys = this.getSqlSession().selectList(NAMESPACE + "queryGisUrlInfoByOrgCode", entity);
		if (entitys == null || entitys.isEmpty()) {
			return null;
		} else {
			return entitys.get(NumberConstants.ZERO);
		}
	}

	/**
	 * 作废GIS短地址信息
	 * 
	 * @Title: deleteGisUrlInfo
	 * @author 200664-yangjinheng
	 * @date 2014年10月21日 上午10:41:11
	 * @throws
	 */
	public OrgGisUrlEntity deleteGisUrlInfo(OrgAdministrativeInfoEntity entity) {

		OrgGisUrlEntity orgGisUrlEntity = new OrgGisUrlEntity();
		orgGisUrlEntity.setOrgCode(entity.getCode());
		orgGisUrlEntity.setActive(FossConstants.INACTIVE);
		orgGisUrlEntity.setModifyDate(entity.getModifyDate());
		orgGisUrlEntity.setModifyUser(entity.getModifyUser());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgGisUrlEntity", orgGisUrlEntity);
		// 只删除active为有效的：
		map.put("conditionActive", FossConstants.ACTIVE);
		int result = getSqlSession().update(NAMESPACE + "deleteGisUrlInfo", map);
		return result > NumberConstants.ZERO ? orgGisUrlEntity : null;
	}

	/**
	 * 添加新GIS短地址信息
	 * 
	 * @Title: addGisUrlInfo
	 * @author 200664-yangjinheng
	 * @date 2014年10月21日 上午10:41:24
	 * @throws
	 */
	public void addGisUrlInfo(OrgAdministrativeInfoEntity entity) {
		OrgGisUrlEntity orgGisUrlEntity = new OrgGisUrlEntity();
		orgGisUrlEntity.setId(entity.getId());
		orgGisUrlEntity.setOrgCode(entity.getCode());
		orgGisUrlEntity.setDepCoodinate(entity.getDepCoordinate());
		orgGisUrlEntity.setActive(FossConstants.ACTIVE);
		orgGisUrlEntity.setCreateUser(entity.getCreateUser());
		orgGisUrlEntity.setModifyUser(entity.getModifyUser());
		orgGisUrlEntity.setCreateDate(entity.getModifyDate());
		// ModifyDate为2999年，为一个常量
		orgGisUrlEntity.setModifyDate(new Date(NumberConstants.ENDTIME));
		orgGisUrlEntity.setShortUrl(this.longUrlToshortUrl(entity.getDepCoordinate()));

		getSqlSession().insert(NAMESPACE + "addGisUrlInfo", orgGisUrlEntity);
	}

	@SuppressWarnings("unchecked")
	public List<OrgAdministrativeInfoEntity> queryAll() {
		return getSqlSession().selectList(NAMESPACE + "queryAllIsNotNull", new RowBounds(0, NumberConstants.NUMBER_200));
	}

	/**
	 * 将长地址转换为短链接
	 * 
	 * @Title: LongUrlToshortUrl
	 * @author 200664-yangjinheng
	 * @date 2014年10月21日 下午3:49:50
	 * @throws
	 */
	private String longUrlToshortUrl(String longUrl) {

		// 密钥key
		String key = "http://www.deppon.com/FOSS_APP_GIS/shortUrl/20141211";
		// 要使用生成 URL 的字符
		String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1",
				"2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
				"Y", "Z" };
		// 对传入网址进行 MD5 加密
		String sMD5EncryptResult = (this.getMd5(key + longUrl));
		String hex = sMD5EncryptResult;

		String[] resUrl = new String[NumberConstants.NUMBER_4];
		for (int i = 0; i < NumberConstants.NUMBER_4; i++) {

			// 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
			String sTempSubString = hex.substring(i * NumberConstants.NUMBER_8, i * NumberConstants.NUMBER_8 + NumberConstants.NUMBER_8);

			// 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用
			// long ，则会越界
			long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, NumberConstants.NUMBER_16);
			//String outChars = "";
			StringBuffer outChars = new StringBuffer();
			for (int j = 0; j < NumberConstants.NUMBER_6; j++) {
				// 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
				long index = 0x0000003D & lHexLong;
				// 把取得的字符相加
				//outChars += chars[(int) index];
				outChars.append(chars[(int) index]);
				// 每次循环按位右移 5 位
				lHexLong = lHexLong >> NumberConstants.NUMBER_5;
			}
			// 把字符串存入对应索引的输出数组
			resUrl[i] = outChars.toString();
		}
		return resUrl[0].toString();
	}

	/**
	 * MD5加密，返回长度为32位的字符串
	 * 
	 * @Title: getMd5
	 * @author 200664-yangjinheng
	 * @date 2014年10月22日 上午8:32:52
	 * @throws
	 */
	private String getMd5(String s) {
		byte[] b = s.getBytes();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(b);
			byte[] b2 = md.digest();
			char str[] = new char[b2.length << 1];
			int len = 0;
			for (int i = 0; i < b2.length; i++) {
				byte val = b2[i];
				str[len++] = hexChar[(val >>> NumberConstants.NUMBER_4) & 0xf];
				str[len++] = hexChar[val & 0xf];
			}
			return new String(str);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
