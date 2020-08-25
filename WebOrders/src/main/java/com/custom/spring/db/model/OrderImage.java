package com.custom.spring.db.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class OrderImage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Version 
	@ColumnDefault("0")
	private long version;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "img", nullable = true, insertable = true, updatable = true, unique = false)
	private byte[] img;
	@JsonIgnore
	@OneToOne(optional = true, mappedBy = "image")
	private Order order;
	
	public OrderImage() {
		
	}
	
	public OrderImage(byte[] image) {
		this.img = image;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the img
	 */
	public byte[] getImg() {
		return img;
	}
	/**
	 * @param img the img to set
	 */
	public void setImg(byte[] img) {
		this.img = img;
	}
	
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
	
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrderImage [version=" + version + ", id=" + id + ", image=" + img + "]";
	}
}   

