
let $playlist;
let $votingBlock;
let $playingBlock;
let $results;

$(document).ready(() => {

  // access playlist to add songs to later
  $playlist = $("#suggestions");
  $votingBlock = $("#voting");
  $playingBlock = $("#playing");
  $results = $("#dropdown");

  /*
  On page load, send post request to the backend to get CURRENT VERSION OF PLAYLIST
  POST REQUEST IS EMPTY AFTER 2 REFRESHES - this is BAD
  */
  $.post("/~jmst/playlist", responseJSON => {
    const responseObject = JSON.parse(responseJSON);
    console.log("/~jmst/playlist post request");
    console.log(responseObject);
    let output = responseObject;
    refresh_suggestions_block(output.suggest); //output.suggest are all Suggestion objects
    refresh_voting_block(output.vote);
    refresh_playing_block(output.play);
  });

	/*
	Toggle color for up and down buttons
	*/
	$("#down").click(function () {
		new_vote(false,"SongId"); //TODO: pretty sure this is literally passing the string "SongId" instead of the actual value
		if (document.getElementById("up").classList.contains("upColor")) {
			$("#up").toggleClass("upColor");
		}
		$("#down").toggleClass("downColor");
	});

	$("#up").click(function () {
		new_vote(true,"SongId");
		if (document.getElementById("down").classList.contains("downColor")) {
			$("#down").toggleClass("downColor");
		}
		$("#up").toggleClass("upColor");
	});

	/*
	Generate song suggestions based on user input. Send POST request on each key press inside search bar.
	*/
  $("#playlist").keyup(event => {
    	let song = document.getElementById('songName').value;
    	console.log(song);

	    if (song.length === 0) {
	    	$results.hide();
	    }
	    else {
        $results.show();
		   	const postParameters = {word: song};
		    console.log(postParameters);
			  $results.empty();
		    // send input to backend to generate song suggestions
		    $.post("/~jmst/suggestions", postParameters, responseJSON => {

				const responseObject = JSON.parse(responseJSON);
				console.log(responseObject);
				let output = responseObject;

				for(const sug of output){
          $results.append("<a href='javascript:;' onclick='new_song(\"" + sug.id.toString() + "\");'><div class='option'>" + sug.name + "</div></a>");
        };
		  });
		}
  });

  $results.click(function () {
    $results.hide(); // MAKE SMOOTH
  });


});

/*
Open socket for web communication and deal with different messages
*/
const MESSAGE_TYPE = {
  VOTESONG: 0,
  ADDSONG: 1,
  REMOVESONG: 2,
  PLAYLIST: 3
};
let conn;

// Setup the WebSocket connection for live updating of scores.
const setup_live_playlist = () => {
  // TODO Create the WebSocket connection and assign it to `conn`
  conn = new WebSocket("ws://localhost:4567/~jmst/songupdates");

  conn.onerror = err => {
    console.log('Connection error:', err);
  };

  conn.onmessage = msg => {
    const data = JSON.parse(msg.data);
    console.log(data);
    switch (data.type) {
      default:
        console.log('Unknown message type!', data.type);
        break;

      case MESSAGE_TYPE.VOTESONG:
      	// update number of votes for a specific song on the playlist
        console.log("A VOTE HAPPENED");
        let votingList = data.payload;
        // loop through json objects in payload
        // display number of votes for given song_id
        votingList.forEach(function(suggestion) {
          $votingBlock.append("<li id='" + $("#user_id").val() + "'>" 
            + "<div class='votingItem'>"
            + "<img class='albumCover' src='" + data.payload.album_cover + "'>"
            + "<div class='track'>"
            + "<div class='song'>" + suggestion.song_name + "</div>"
            + "<div class='artist'>" + suggestion.artist_names[0] + "</div>"

            + "</div>"
            + "<div class='buttons'>"
            + "<a href='javascript:;' onclick='new_vote(false, \"" + suggestion.song_id + "\")'><i class='fa fa-chevron-circle-down' id='down'></i></a>"
            + "<a href='javascript:;' onclick='new_vote(true, \"" + suggestion.song_id + "\")'><i class='fa fa-chevron-circle-up' id='up'></i></a>"
            + "</div>"
            + "</div>"

            + "</li>");
        });

      	break;

      case MESSAGE_TYPE.ADDSONG:
        console.log("Addsonging");
        console.log("inside"); // NOT WORKING
        console.log(data.payload);
        $playlist.append("<li id='" + $("#user_id").val() + "'>" 
          + "<div class='suggestingItem'>"
          + "<img class='albumCover' src='" + data.payload.album_cover + "'>"
          + "<div class='track'>"
          + "<div class='song'>" + data.payload.song_name + "</div>"

          + "<div class='artist'>" + data.payload.artist_names[0] + "</div>"

          + "</div>"
          + "<div class='buttons'>"
          + "<a href='javascript:;' ><i class='fa fa-chevron-circle-down' id='down_disabled'></i></a>"
          + "<a href='javascript:;' ><i class='fa fa-chevron-circle-up' id='up_disabled'></i></a>"
          + "</div>"
          + "</div>"

          + "</li>");
        break;

      case MESSAGE_TYPE.REMOVESONG:
      	$playlist.remove($("#" + $("#user_id").val())); // removes li of ul, referenced by userId
      	break;

      case MESSAGE_TYPE.PLAYLIST:
        // apend an entire list of li's to the displaySongs ul
        break;
    }
  };
}


/*
Send VOTESONG message to backend when a user votes on a song - params are boolean vote and song id
*/
function new_vote(vote_boolean, songId){
  // Send a VOTESONG message to the server using `conn`
  console.log("");
  let vote = {"type":MESSAGE_TYPE.VOTESONG, "payload": {
        "id":$("#user_id").val(), 
        "song_id":songId,
        "vote":vote_boolean}
      };
  conn.send(JSON.stringify(vote));
}

/*
Send ADDSONG message to backend when a user adds a song
*/
function new_song(songId) {
  // Send a ADDSONG message to the server using `conn`
  console.log("i want to add a song");
  console.log(songId);
  console.log($("#user_id").val());
  let userSuggestion = {"type":MESSAGE_TYPE.ADDSONG, "payload": {
        "id":$("#user_id").val(), 
        "song_id":songId}
      };
  conn.send(JSON.stringify(userSuggestion));
}

/*
Send PLAYLIST message to backend when the last song in the playing block is 10 seconds from finishing
*/
function get_playlist(songId) {
  // Send a PLAYLIST message to the server using `conn`
  let playlistRequest = {"type":MESSAGE_TYPE.PLAYLIST, "payload": {
        "id":$("#user_id").val()}
      };
  conn.send(JSON.stringify(playlistRequest));
}

/*
Refresh suggestions in the playlist (bottom block)
*/
function refresh_suggestions_block(toSuggest) {
  toSuggest.forEach(function(suggestion) {
    console.log(suggestion);
      $playlist.append("<li id='" + $("#user_id").val() + "'>"
          + "<div class='suggestingItem'>"
          + "<img class='albumCover' src='" + suggestion.song.album_cover + "'>"
          + "<div class='track'>"
          + "<div class='song'>" + suggestion.song.name + "</div>"
          + "<div class='artist'>" + suggestion.song.artistNames[0] + "</div>"

          + "</div>"
          + "<div class='buttons'>"
          + "<a href='javascript:;' ><i class='fa fa-chevron-circle-down' id='down_disabled'></i></a>"
          + "<a href='javascript:;' ><i class='fa fa-chevron-circle-up' id='up_disabled'></i></a>"
          + "</div>"
          + "</div>"

          + "</li>");
  });
}

/*
Refresh songs being voted on in the playlist (middle block)
*/
function refresh_voting_block(toVote) {
  toVote.forEach(function(voteSong) {
    $votingBlock.append("<li id='" + $("#user_id").val() + "'>"
      + "<div class='votingItem'>"
      + "<img class='albumCover' src='" + voteSong.song.album_cover + "'>"
      + "<div class='track'>"
      + "<div class='song'>" + voteSong.song.name + "</div>"
      + "<div class='artist'>" + voteSong.song.artistNames[0] + "</div>"

      + "</div>"
      + "<div class='buttons'>"
      + "<a href='javascript:;' onclick='new_vote(false, \"" + voteSong.song.id + "\")'><i class='fa fa-chevron-circle-down' id='down'></i></a>"
      + "<a href='javascript:;' onclick='new_vote(true, \"" + voteSong.song.id + "\")'><i class='fa fa-chevron-circle-up' id='up'></i></a>"
      + "</div>"
      + "</div>"

      + "</li>");
  });
}

/*
Refresh songs being played in the playlist (top block)
*/
function refresh_playing_block(toPlay) {
  toPlay.forEach(function(playSong) {
    $playingBlock.append("<li id='" + $("#user_id").val() + "'>"
      + "<div class='playingItem'>"
      + "<img class='albumCover' src='" + playSong.song.album_cover + "'>"
      + "<div class='track'>"
      + "<div class='song'>" + playSong.song.name + "</div>"
      + "<div class='artist'>" + playSong.song.artistNames[0] + "</div>"

      + "</div>"
      + "<div class='buttons'>"
      + "<a href='javascript:;' ><i class='fa fa-chevron-circle-down' id='down_disabled'></i></a>"
      + "<a href='javascript:;' ><i class='fa fa-chevron-circle-up' id='up_disabled'></i></a>"
      + "</div>"
      + "</div>"

      + "</li>");
  });
}


