<!DOCTYPE html>
<html>
	<head>
		<script src="js/jquery-3.1.1.js"></script>
		<script src="js/join_jscript.js"></script>
		<link rel="stylesheet" type="text/css" href="css/main_style.css">
		<link href="https://fonts.googleapis.com/css?family=Raleway:800,500" rel="stylesheet">
	</head>
	<body>
		<div class="nav-bar">
			<a class="login" href="/login">SWITCH USER</a>
			<a id="faqs" href="/faq">FAQS</a>
			<a id="home" href="/main">HOME</a>
		</div>
		<div class="title">${hostname}, you have made a party! Your party id is: ${party_id}.</div>
		<div class="buttons">
			<a class="btn" onclick="spotify_search()">ADD SUGGESTION</a>
		</div>
		
	</body>
</html>