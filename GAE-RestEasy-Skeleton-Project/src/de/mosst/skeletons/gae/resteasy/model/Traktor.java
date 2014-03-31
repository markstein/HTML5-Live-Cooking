package de.mosst.skeletons.gae.resteasy.model;

import java.io.Serializable;

import de.mosst.skeletons.gae.resteasy.util.RandomSequenzeUtil;

public class Traktor implements Serializable {

	public final String id;
	public final String marke;
	
	public Traktor(String marke){
		this.id = RandomSequenzeUtil.createRandomCharSequenze(20);
		this.marke = marke;
	}

	public Traktor(String id, String marke) {
		this.id = id;
		this.marke = marke;
    }
	
	//for RESTEasy
	@SuppressWarnings("unused")
    private Traktor() {
		this(null, null);
    }
}