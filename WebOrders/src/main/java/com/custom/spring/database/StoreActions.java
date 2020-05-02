package com.custom.spring.database;

public enum StoreActions {
	ALL("getOrders"),
	NOTRDY("getNotRdy"),
	ADD("addOrder"),
	DELETE("deleteOrder"),
	UPDATE("updateStatus");
	
	private String saName;
	
	StoreActions(String name) {
		this.saName = name;
	}
	
	public static StoreActions find(String value) {
		StoreActions result = null;
		for (StoreActions val : StoreActions.values()) {
			if (val.name().toLowerCase().equals(value.trim().toLowerCase())
					|| val.saName.toLowerCase().equals(value.trim().toLowerCase())) {
				result = val;
				break;
			}
		}
		return result;
	}
	
	public static boolean isShowActions(String act) {
		boolean result = false;
		StoreActions realAct = find(act);
		if (realAct != null && (realAct.equals(StoreActions.ALL) || realAct.equals(StoreActions.NOTRDY))) {
			result = true;
		}
		return result;
	}
}
