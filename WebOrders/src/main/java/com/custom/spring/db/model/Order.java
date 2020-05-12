package com.custom.spring.db.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.custom.spring.db.LDConverter;
import com.custom.spring.db.jpa.listeners.OrderListener;

@EntityListeners({OrderListener.class}) 
@Entity 
@Table(name = "j4jorders")
@NamedQueries({@NamedQuery(
		name = Order.DELETE_ORDER,
		query = "DELETE FROM Order o WHERE o.id = :oId"),
@NamedQuery(
		name = Order.SELECT_ORDERS,
		query = "SELECT o FROM Order o"),
@NamedQuery(
		name = Order.SELECT_NOT_RDY,
		query = "SELECT o FROM Order o WHERE o.done IS false"),
@NamedQuery(
		name = Order.SELECT_BY_NAME,
		query = "SELECT o FROM Order o WHERE o.name = :oNm")
})
public class Order implements Serializable { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String SELECT_ORDERS = "SELECT_ORDERS";
	public static final String DELETE_ORDER = "DELETE_ORDER";
	public static final String SELECT_NOT_RDY = "SELECT_NOT_RDY";
	public static final String SELECT_BY_NAME = "SELECT_BY_NAME";
	
	@Version 
	@ColumnDefault("0")
	private long version; 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "createDate", columnDefinition = "text", nullable = false, insertable = true, updatable = true, unique = false, length = 10)
	@Convert(converter = LDConverter.class)
	private LocalDate createDate;
	@OrderBy("name DESC") 
	private String name;
	private String description;
	@ColumnDefault("false") 
	private boolean done;
	@OneToOne(
			orphanRemoval = true,  
			cascade = CascadeType.ALL)
	@JoinColumn(name = "oi_fk",
			foreignKey = @ForeignKey(
					name = "oi_fk", 
					value = ConstraintMode.CONSTRAINT), 
			referencedColumnName = "id")
	private OrderImage image;
	
	public Order() {
	}
	
	public Order(String name, String description) {
		this.createDate = LocalDate.now();
		this.name = name;
		this.description = description;
		this.done = false;
		this.image = null;
	}
	
	public Order(String createDate, String name, String description, boolean done) {
		this.createDate = LocalDate.parse(createDate);
		this.name = name;
		this.description = description;
		this.done = done;
		this.image = null;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the done
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * @param done the done to set
	 */
	public void setDone(boolean done) {
		this.done = done;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the createDate
	 */
	public LocalDate getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(LocalDate ldate) {
		this.createDate = ldate;
	}
	
	/**
	 * @return the image
	 */
	public OrderImage getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(OrderImage image) {
		this.image = image;
	}
	
	/* 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + id;
		return result;
	}

	/* 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Order other = (Order) obj;
		if (createDate == null) {
			if (other.createDate != null) {
				return false;
			}
		} else if (!createDate.equals(other.createDate)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		return true;
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Order [version=" + version + ", id=" + id + ", createDate=" + createDate + ", name=" + name
				+ ", description=" + description + ", done=" + done + "]";
	}
}
