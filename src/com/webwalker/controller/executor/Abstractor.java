/**
 * 
 */
package com.webwalker.controller.executor;

/**
 * @author Administrator
 * 
 */
public abstract class Abstractor {
	public Implementor implementor;

	/**
	 * @param implementor
	 *            the implementor to set
	 */
	public void setImplementor(Implementor implementor) {
		this.implementor = implementor;
	}

	public void Operation() {
		implementor.Action();
	}
}
