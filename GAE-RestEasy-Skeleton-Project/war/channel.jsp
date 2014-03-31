<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.google.appengine.api.channel.*" %>
<%
ChannelService channelService = ChannelServiceFactory.getChannelService();
String name = channelService.createChannel(request.getParameter("name"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF8">
<title>Channel</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script src='/_ah/channel/jsapi'></script>
</head>
<body>

  <script>
	openChannel = function() {
		var token = '<%= name %>';
		var channel = new goog.appengine.Channel(token);
		var handler = {
			'onopen' : function() {},
			'onmessage' : onMessage,
			'onerror' : function() {},
			'onclose' : function() {}
		};
		var socket = channel.open(handler);
		socket.onopen = onOpened;
		socket.onmessage = onMessage;
	}
	
	onMessage = function(message) {
		$('#output').html(message.data);
	}
	
	$( document ).ready(openChannel());
    
  </script>
  
  Message: <span id="output">---</span>
  
  
</body>
</html>