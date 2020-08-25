package com.custom.spring.db.model;

public class ObjectsWrapper {
	private Order order;
	private OrderImage orderImage;
	private ActionObjForWrapper actions;

	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}
	/**
	 * @return the orderImage
	 */
	public OrderImage getOrderImage() {
		return orderImage;
	}
	/**
	 * @param orderImage the orderImage to set
	 */
	public void setOrderImage(OrderImage orderImage) {
		this.orderImage = orderImage;
	}
	/**
	 * @return the actions
	 */
	public ActionObjForWrapper getActions() {
		return actions;
	}
	/**
	 * @param actions the actions to set
	 */
	public void setActions(ActionObjForWrapper actions) {
		this.actions = actions;
	}
	

}