package com.becks.common;

/**
 * 放置公共参数
 * 
 * @author BecksHwang
 *
 */
public class CommonParameter {

	public static String hqlOrderByAsc = "hqlOrderByAsc";

	public static String hqlOrderByDesc = "hqlOrderByDesc";

	// 判断新闻是否存在的唯一标识
	public static final String MISSION_CHECKCODE = "MISSION_CHECKCODE_GGZZJC";
	// 判断问答是否存在的唯一标识
	public static final String MISSION_CHECKCODE_ITRCT = "MISSION_CHECKCODE_ITRCT";
	// 获取最新消息
	public static final String NEW_MESSAGE = "NEW_MESSAGE";

	/****************************** 以下为网站类型代码 *****************************/
	// PDF网址 同花顺公告速递
	public static final Long PDF_URL = 1L;
	// 普通网址
	public static final Long COMMON_URL = 2L;
	// 全景网互动精华
	public static final Long INTERACTION_URL = 3L;
	// 金融界公告速递
	public static final Long JRJGGSD_URL = 4L;
	// 和讯公司新闻
	public static final Long HEXUN_URL = 5L;
	// 巨潮资讯网
	public static final Long JCZX_URL = 6L;
	// 第一财经快讯
	public static final Long DYCJKX_URL = 7L;
	// 莲花财经
	public static final Long LHCJ_URL = 8L;
	// 上交所互动平台
	public static final Long SJSHDPT_URL = 9L;
	// 深交所互动易
	public static final Long SJSHDY_URL = 10L;
}
