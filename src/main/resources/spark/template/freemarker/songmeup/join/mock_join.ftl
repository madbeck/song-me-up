<!DOCTYPE html>
	<head>
		<script src="js/jquery-3.1.1.js"></script>
		<script src="js/join.js"></script>

		<script src="https://sdk.scdn.co/spotify-player.js"></script>
   		<script src="js/player.js"></script>


		<link href="https://fonts.googleapis.com/css?family=Raleway:800,500" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="css/join.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.10/css/all.css" integrity="sha384-+d0P83n9kaQMCwj8F4RJB66tzIwOKmrdb46+porD/OvrJ+37WqIM7UoBtwHO6Nlg" crossorigin="anonymous">
		<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
	</head>
	<body>
		<div class="hidden" hidden>hallo</div>
		<nav role="navigation" id="navvy">
			<div id="menuToggle">
				<input type="checkbox" />
				<span></span>
				<span></span>
				<span></span>
				<ul id="menu">
					<a href="main"><li>Home</li></a>
					<a href="#"><li>Listeners</li></a>
					<a href="#"><li>Info</li></a>
					<a href="/main?leave=true"><li>Leave Party</li></a>
					<a href="/logout"><li>Log Out</li></a>
				</ul>
			</div>
		</nav>
		
		<div class="party">
			<div class="title">Listening to Bob's Party</div>
			<!-- replace Bob with $hostname -->
		</div>

		<div class="nowPlaying">
			<div class="imgContainer">
				<img class="albumArt" src="photos/flume.jpg">
				<div class="artistInfo">
					<span class="now">Now Playing</span>
					<span class="trackName">Lose It (feat. Vic Mensa)</span>
					<span class="artistName">Flume — Skin</span>
					<div class="musicControls">

						<i class="fas fa-backward fa-4x" onclick="switchToPrevious()"></i>
						<i class="fas fa-play fa-3x" onclick="playSong()"></i>
						<i class="fas fa-forward fa-4x" onclick="switchToNext()"></i>

					</div>
				</div>
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
					<li>
						<div id="playlistItem">
							<div class="track">
								<div class="song">Song name</div>
								<div class="artist">Artist</div>
							</div>
							<div id="buttons">
		    					<a href="javascript:;"><i class="fa fa-chevron-circle-down" id="down"></i></a>
		    					<a href="javascript:;"><i class="fa fa-chevron-circle-up" id="up"></i></a>
		    				</div>
		    			</div>
					</li>
					<li>
						<div id="playlistItem">
							<div class="track">
								<div class="song">Song name</div>
								<div class="artist">Artist</div>
							</div>
							<div id="buttons">
		    					<a href="#"><i class="fa fa-chevron-circle-down" id="down"></i></a>
		    					<a href="#"><i class="fa fa-chevron-circle-up" id="up"></i></a>
		    				</div>
		    			</div>
					</li>
					<li>
						<div id="playlistItem">
							<div class="track">
								<div class="song">Song name</div>
								<div class="artist">Artist</div>
							</div>
							<div id="buttons">
		    					<a href="#"><i class="fa fa-chevron-circle-down" id="down"></i></a>
		    					<a href="#"><i class="fa fa-chevron-circle-up" id="up"></i></a>
		    				</div>
		    			</div>
					</li>
					<li>
						<div id="playlistItem">
							<div class="track">
								<div class="song">Song name</div>
								<div class="artist">Artist</div>
							</div>
							<div id="buttons">
		    					<a href="#"><i class="fa fa-chevron-circle-down" id="down"></i></a>
		    					<a href="#"><i class="fa fa-chevron-circle-up" id="up"></i></a>
		    				</div>
		    			</div>
					</li>
					<li>
						<div id="playlistItem">
							<div class="track">
								<div class="song">Song name</div>
								<div class="artist">Artist</div>
							</div>
							<div id="buttons">
		    					<a href="#"><i class="fa fa-chevron-circle-down" id="down"></i></a>
		    					<a href="#"><i class="fa fa-chevron-circle-up" id="up"></i></a>
		    				</div>
		    			</div>
					</li>
				</ul>
			</div>
		</div>
	</body>
</html>
