package de.mosst.fotogether.model;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class AlbumDAO {

	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	private MemcacheService cache = MemcacheServiceFactory.getMemcacheService("Album");
    
	private static final AlbumDAO INSTANCE = new AlbumDAO();

	public static AlbumDAO getInstance() {
		return INSTANCE;
    }

	public Album create(String name) {
		Album album = new Album(name);
		return update(album);
    }


	public Album update(Album album) {
		datastore.put(albumToEntity(album));
		cache.put(album.id, album);
		//TODO store update images
		return album;
    }

	public Album load(String id) throws EntityNotFoundException {
		
		Album album = (Album) cache.get(id);
		
		if(album == null){
			Entity entity = datastore.get(getKey(id));
			album = entityToAlbum(id, entity);
			cache.put(album.id, album);
		}
		
	    return album;
    }

	public boolean delete(String id) {
		cache.delete(id);
		datastore.delete(getKey(id));
		return true;
	}
	
	private Album entityToAlbum(String id, Entity entity) {
		String name = (String)entity.getProperty("name");
		
		//TODO load images
		List<Image> images = new ArrayList<Image>();
		
		Album album = new Album(id,  name, images);
		return album;
    }

	private Entity albumToEntity(Album album) {
		Entity entity = new Entity(getKey(album.id));
		entity.setProperty("name", album.name);
		return entity;
	}

	private Key getKey(String id) {
	    return KeyFactory.createKey("Album", id);
    }

}
