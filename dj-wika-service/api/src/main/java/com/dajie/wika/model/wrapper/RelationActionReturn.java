package com.dajie.wika.model.wrapper;

import java.io.Serializable;

public class RelationActionReturn implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4681357762182251454L;

	private int returnCode;

	private RelationUserView relationUserView;

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public RelationUserView getRelationUserView() {
		return relationUserView;
	}

	public void setRelationUserView(RelationUserView relationUserView) {
		this.relationUserView = relationUserView;
	}

}
