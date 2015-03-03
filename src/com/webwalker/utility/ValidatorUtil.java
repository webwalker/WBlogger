/**
 * 
 */
package com.webwalker.utility;

/**
 * <p>输入域校验集合</p>
 * @author Frank.fan
 * @version $Id: ValidatorUtil.java, v 0.1 2012-7-21 下午4:01:09 fanmanrong Exp $
 */
public class ValidatorUtil {
    
    /**
     * 判断金额的格式:
     *       isAmount(".89")  =  true
     *       isAmount(".898") =  false
     *       isAmount("3.98") =  true
     *       isAmount(".8")   =  true
     *       isAmount("22")   =  true
     *       isAmount("22.00")=  true
     * @param amount
     * @return
     */
    public static boolean isAmount(String amount) {
        if (StringUtil.isNotBlank(amount)) {
            if (amount.trim().indexOf(".") != -1) {
                String ext = amount.trim().substring(amount.trim().indexOf("."));
                if (ext == null || ext.length() > 3 || ext.length() == 1) {
                    return false;
                }else
                    return true;
            }else
                return true;
        } else
            return false;
    }
}
