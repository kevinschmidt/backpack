package eu.stupidsoup.backpack.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import eu.stupidsoup.backpack.model.GTDTag;


@Entity
@Table(name="GTD_TAG")
public class BasicGTDTag implements GTDTag {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Override
	public Long getId() {
		return id;
	}
	
	
	@Column(nullable=false, unique=true)
	@Override
	public String getName() {
		return name;
	}

}
