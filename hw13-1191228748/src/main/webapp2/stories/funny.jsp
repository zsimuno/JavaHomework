<%@ page import="java.lang.Math" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<style type="text/css">
		<% 
		String colorVal = Integer.toString((int)(Math.random() * 1000000));
		String color = "#" + "0".repeat(6-colorVal.length()) + colorVal;
		out.print("body {color: " + color + "; font-size: 15px;}");
		%>
		</style>
	</head>
	<body>
		<pre> 
		A lost dog strays into a jungle. A lion sees this from a distance and says with caution "this guy looks edible, never seen his kind before".
		So the lion starts rushing towards the dog with menace. 
		The dog notices and starts to panic but as he's about to run he sees some bones next to him and gets an idea and says loudly "mmm...that was some good lion meat!".
		
		The lion abruptly stops and says " woah! This guy seems tougher then he looks, I better leave while I can".
		
		Over by the tree top, a monkey witnessed everything. 
		Evidently, the monkey realizes the he can benefit from this situation by telling the lion and getting something in return. 
		So the monkey proceeds to tell the lion what really happened and the lion says angrily "get on my back, we'll get him together".
		
		So they start rushing back to the dog. 
		The dog sees them and realized what happened and starts to panic even more. 
		He then gets another idea and shouts "where the hell is that monkey! I told him to bring me another lion an hour ago..."
		</pre>
	</body>
</html>