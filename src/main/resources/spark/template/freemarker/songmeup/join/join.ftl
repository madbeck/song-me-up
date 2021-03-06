<!DOCTYPE html>
	<head>
		<script src="js/jquery-3.1.1.js"></script>
        <script src="js/guest_join.js"></script>
        <script src="js/join_functions.js"></script>
		<link href="https://fonts.googleapis.com/css?family=Raleway:800,500" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="css/join.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.10/css/all.css" integrity="sha384-+d0P83n9kaQMCwj8F4RJB66tzIwOKmrdb46+porD/OvrJ+37WqIM7UoBtwHO6Nlg" crossorigin="anonymous">
		<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
	</head>
	<body>

		
		<!--<div id="hello" style="font-size: 30px; font-family: 'Raleway', sans-serif; position:absolute;top:30px;left:35px " > Party ID: ${party_id} </div>-->

		<input type="hidden" id="user_id" value="${user_id}">
		
		<!-- The Modal for Error Messages-->
		<div id="modal_query" class="modal" style="display:none;">

		  <!-- Modal content -->
		  <div class="modal-content">
			<p id="leave_reason" align="center"></p>
			<a class="btn" id="proceed" href="main">HOME</a>
		  </div>

		</div>

		<!-- The Modal for Listeners -->
		<div id="modal_users" class="modal">

		  <!-- Modal content -->
		  <div class="modal-content2">
		     <span class="close" id="close_modal">&times;</span>
			 <div id="user_data">
			 </div>
		  </div>

		</div>

		<nav role="navigation" id="navvy">
			<div id="menuToggle">
				<input type="checkbox" />
				<span></span>
				<span></span>
				<span></span>
				<ul id="menu">
					<li id="party_id">Party ID: ${party_id}</li>
					<a href="main"><li>Home</li></a>
					<a onclick="show_listeners()"><li>Listeners</li></a>
					<a href="main?leave=true"><li>Leave Party</li></a>
					<a href="logout"><li>Log Out</li></a>
					
				</ul>
			</div>
		</nav>
		
		<div class="party">
			<div class="title">Listening to ${hostname}'s Party</div>
		</div>

		<div class="nowPlaying">
            <div class="imgContainer">
            </div>
		</div>

		<div class="content">
			<div class="playlist" id="playlist">
				<!-- SEARCH BAR -->
				<div class="search">
					<button type="submit"><i class="fas fa-plus"></i></button>
					<input type="text" placeholder="Add Songs" name="search" id="songName">
				</div>
				<!-- SUGGESTIONS -->
				<div id="dropdown">
				</div>
				<!-- SONGS -->
				<ul id="displaySongs">
					<div id="playing">
<!--                         <li id="qiwhy07ib37iuwgujs1n9bj6j"><div class="playingItem"><img class="albumCover" src="https://i.scdn.co/image/5e47930ad4d70be88770531dc8875c13f9f0f82c"><div class="track"><div class="song">Resonance</div><div class="artist">Home</div></div><div class="buttons"><a href="javascript:;" ><i class="fa fa-chevron-circle-down" id="down"></i></a><a href="javascript:;" ><i class="fa fa-chevron-circle-up" id="up"></i></a></div></div></li>
                        <li id="qiwhy07ib37iuwgujs1n9bj6j"><div class="playingItem"><img class="albumCover" src="https://i.scdn.co/image/5e47930ad4d70be88770531dc8875c13f9f0f82c"><div class="track"><div class="song">THIS IS A TEST</div><div class="artist">this is still a test</div></div><div class="buttons"><a href="javascript:;" ><i class="fa fa-chevron-circle-down" id="down"></i></a><a href="javascript:;" ><i class="fa fa-chevron-circle-up" id="up"></i></a></div></div></li> -->
					</div>
					<div id="voting">
                        <#--<li id="qiwhy07ib37iuwgujs1n9bj6j"><div class="votingItem"><img class="albumCover" src="https://i.scdn.co/image/5e47930ad4d70be88770531dc8875c13f9f0f82c"><div class="track"><div class="song">Resonance</div><div class="artist">Home</div></div><div class="buttons"><a href="javascript:;" onclick="new_vote(false, &quot;1TuopWDIuDi1553081zvuU&quot;)"><i class="fa fa-chevron-circle-down" id="down"></i></a><a href="javascript:;" onclick="new_vote(true, &quot;1TuopWDIuDi1553081zvuU&quot;)"><i class="fa fa-chevron-circle-up" id="up"></i></a></div></div></li>-->
					</div>
					<div id="suggestions">
<!--                         <li id="qiwhy07ib37iuwgujs1n9bj6j"><div class="suggestingItem"><img class="albumCover" src="https://i.scdn.co/image/5e47930ad4d70be88770531dc8875c13f9f0f82c"><div class="track"><div class="song">Resonance</div><div class="artist">Home</div></div><div class="buttons"><a href="javascript:;" "><i class="fa fa-chevron-circle-down" id="down_disabled"></i></a><a href="javascript:;" ><i class="fa fa-chevron-circle-up" id="up_disabled"></i></a></div></div></li> -->
					</div>
				</ul>
			</div>
		</div>
	</body>
</html>

<script>
	$(document).ready(function() {
	  setup_live_playlist();
	});
</script>
