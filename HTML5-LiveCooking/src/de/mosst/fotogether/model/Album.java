package de.mosst.fotogether.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.mosst.fotogether.util.RandomSequenzeUtil;

public class Album implements Serializable {


	public final String id;
	public final String name;
	public final List<Image> images;
	
	
	public Album(String name){
		this.id = RandomSequenzeUtil.createRandomCharSequenze(10);
		this.name = name;
	    this.images = new ArrayList<Image>();
	}

	public Album(String id, String name, List<Image> images) {
		this.id = id;
		this.name = name;
	    this.images = images;
    }
	

	@SuppressWarnings("unused")	//only for RESTEasy
    private Album(){
		this(null, null, null);
	}


}
