package com.custom.spring.db.jpa.listeners;

import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.DeleteEvent;
import org.hibernate.event.spi.DeleteEventListener;
import org.hibernate.event.spi.MergeEvent;
import org.hibernate.event.spi.MergeEventListener;
import org.hibernate.event.spi.PersistEvent;
import org.hibernate.event.spi.PersistEventListener;
import org.springframework.stereotype.Component;

@Component
public class CustomEventListener implements PersistEventListener, DeleteEventListener, MergeEventListener {
	private static final long serialVersionUID = 5850729336425310941L;

	@Override
	public void onMerge(MergeEvent event) throws HibernateException {
		System.out.println("CustomEventListener::The MERGE process handled with the - " + event.getEntity());
	}

	@Override
	public void onMerge(MergeEvent event, Map copiedAlready) throws HibernateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDelete(DeleteEvent event) throws HibernateException {
		System.out.println("CustomEventListener::The DELETE process handled with the - " + event.getObject());		
	}

	@Override
	public void onDelete(DeleteEvent event, Set transientEntities) throws HibernateException {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPersist(PersistEvent event) throws HibernateException {
		System.out.println("CustomEventListener::The PERSIST process handled with the - " + event.getObject());		
	}

	@Override
	public void onPersist(PersistEvent event, Map createdAlready) throws HibernateException {
		// TODO Auto-generated method stub
	}
}
