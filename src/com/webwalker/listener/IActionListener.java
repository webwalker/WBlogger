/**
 * 
 */
package com.webwalker.listener;

/**
 * @author Administrator
 * 
 */
public interface IActionListener {

	/**
	 * 当执行某个动作之后，空参行为
	 */
	public void OnAction();

	public void OnAction(Object data);
}
