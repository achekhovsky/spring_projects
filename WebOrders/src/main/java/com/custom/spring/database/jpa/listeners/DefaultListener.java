package com.custom.spring.database.jpa.listeners;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class DefaultListener {
	@PrePersist
	void prePersist(Object object) {
		System.out.println("Default listener prePersist process. Object - " + object);
	}

	@PreUpdate
	void preUpdate(Object object) {
		System.out.println("Default listener preUpdate process Object - " + object);
	}

	@PreRemove
	void preRemove(Object object) {
		System.out.println("Default listener preRemove process Object - " + object);
	}
}
