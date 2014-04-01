'use strict';

angular.module('feTeil2App', ['ngRoute'])
  
.config(function ($routeProvider) {
    $routeProvider
      .when('/', {templateUrl: 'views/main.html', controller: 'MainController'})
		.when('/album', {templateUrl: 'views/album.html', controller: 'AlbumController'})
      .otherwise({redirectTo: '/'});
})

.service('backendAdapter', function($q, $http){
	
	var BACKEND			= '/backend';
	var ALBUM			= BACKEND + '/album';
	var ALBUM_CREATE	= ALBUM + '/create/';
	var ALBUM_LOAD		= ALBUM + '/load/';
	
	return{
		createAlbum: function(name){
			return $http.get(ALBUM_CREATE+name).then(function(result){
				var album = result.data;
				return album;
			});
		},
		loadAlbum: function(id){
			return $http.get(ALBUM_LOAD+id).then(function(result){
				return result.data;
			});		
		}
	};
})

.service('albumService', function(backendAdapter){
	return {
		createAlbum: function(albumName){
			var name = albumName? albumName: 'unbenannt';
			return backendAdapter.createAlbum(name);
		},
		loadAlbum: function(id){
			return backendAdapter.loadAlbum(id);
		}
	};
})

.controller('Foto2GetherController', function($rootScope){
	$rootScope.album = {};
	$rootScope.album.name = '';
})

.controller('MainController', function($scope, albumService, $location){
	$scope.createAlbum = function(){
		$scope.creatingAlbum = true;
		var p = albumService.createAlbum($scope.album.name);
		p.then(function(album){
			$location.path('/album');
			$location.hash(album.id);
		}, function(){
			//TOO Fehelerbehandlung
		});
		p['finally'](function(){
			$scope.creatingAlbum = false;
		});
	};
})

.controller('AlbumController', function($rootScope, $location, albumService){
	var id = $location.hash();
	var p = albumService.loadAlbum(id);
	p.then(function(album){
		$rootScope.album = album;
	}, function(){
		// fehler zurück auf Startseite
		$location.path('/');
		$location.hash(null);
	});
})
;
