package com.webwalker.utility;

public class Constants {
	public static final String CONFIGURE_FILE = "config.properties";
	public static final String LOG_PREFIX = "sdp_spm_";
	
	
	//条码支付
    /**
     * 条码订单支付check超时时间,默认2分钟
     */
	public static final int BARCODE_ORDER_CHECK_TIMEOUT = 2 * 60 * 1000;
	/**
	 * 条码支付订单check请求间隔
	 */
	public static final int BARCODE_ORDER_CHECK_INTERVAL_TIME =5000; 
	
	/**
	 * 测试银行编码
	 */
	public static String TEST_BANK_CODE = "SDTBNK";
}
