/**
 * 
 */
package com.webwalker.rules;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.webwalker.rules.RulesDefine.TimeRule;

/**
 * @author Administrator
 * @param <T>
 * 
 */
public class TimeParser<T> implements IParser<T> {

	private Date jobDate;

	/**
	 * 考虑实现语法解析的复杂度，暂时通过SWITCH方式来实现解析，String regex = rule.getRegex();
	 * 
	 * @return
	 */
	@Override
	public T Parse(int id) {

		TimeRule rule = TimeRule.getByCode(id);
		switch (rule) {
		case TR: // 两小时内随机
			Random r = new Random(2 * 60);
			int seconds = r.nextInt();

			Calendar ca = Calendar.getInstance();
			ca.add(Calendar.MINUTE, seconds);
			jobDate = ca.getTime();

			break;
		}

		return (T) jobDate;
	}

}
