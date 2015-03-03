/**
 * 
 */
package com.webwalker.utility;

import android.text.Html;
import android.text.Spanned;

/**
 * <p>注释</p>
 * @author Frank.fan
 * @version $Id: HtmlShowUtil.java, v 0.1 2012-3-16 下午4:03:38 fanmanrong Exp $
 */
public class HtmlShowUtil {

    public static Spanned getHtmlAmount(String amount) {
        Spanned ret = Html.fromHtml("<font color=red>" + amount + "</font>");
        return ret;
    }
}
