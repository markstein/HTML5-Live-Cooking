package de.mosst.skeletons.gae.resteasy.model;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class TraktorDAO {

	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	private MemcacheService cache = MemcacheServiceFactory.getMemcacheService("Traktor");
    
	public static final TraktorDAO INSTANCE = new TraktorDAO();

	public Traktor create(String marke) {
		Traktor traktor = new Traktor(marke);
		return update(traktor);
    }

	public Traktor update(Traktor traktor) {
		cache.put(traktor.id, traktor);
		datastore.put(traktorToEntity(traktor));
		return traktor;
    }

	public Traktor load(String id) throws EntityNotFoundException {
		Traktor traktor = (Traktor) cache.get(id);
		if(traktor == null){
			Entity entity = datastore.get(getKey(id));
			traktor = entityToTraktor(id, entity);
			cache.put(traktor.id, traktor);
		}
	    return traktor;
    }

	public void delete(String id) {
		cache.delete(id);
		datastore.delete(getKey(id));
	}
	
	public long getCountOfCachedTraktoren(){
		return cache.getStatistics().getItemCount();
	}
	
	private Traktor entityToTraktor(String id, Entity entity) {
		Traktor traktor = new Traktor(id, (String)entity.getProperty("marke"));
		return traktor;
    }

	private Entity traktorToEntity(Traktor traktor) {
		Entity entity = new Entity(getKey(traktor.id));
		entity.setProperty("marke", traktor.marke);
		return entity;
	}

	private Key getKey(String id) {
	    return KeyFactory.createKey("Traktor", id);
    }

}
