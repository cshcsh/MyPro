/**
 * 
 */
var long_time;//娓告垙鏃堕棿杈撳叆妗�乱码乱码
var other_time_span;//鍊掕鏃堕棿span
var interval_time;//闂撮殧鏃堕棿杈撳叆妗�
var stop_time;//鍋滅暀鏃堕棿杈撳叆妗�
var score_span;//寰楀垎鎯呭喌span
var startBtn;//寮�濮嬫父鎴忔寜閽�
var endBtn;//閫�鍑烘父鎴忔寜閽�

var images;//鍥剧墖

var score=0;//寰楀垎鎯呭喌
var other_time;// 鍊掕鏃堕棿

window.onload = function() {

	// 鑾峰彇HTML鍏冪礌
	long_time = document.getElementById("long_time");
	other_time_span = document.getElementById("other_time");
	interval_time = document.getElementById("interval_time");
	stop_time = document.getElementById("stop_time");
	score_span = document.getElementById("score");
	startBtn = document.getElementById("startBtn");
	endBtn = document.getElementById("endBtn");
	images = document.images;
	
	// 寮�濮嬫父鎴�
	startBtn.onclick = function() {
		long_time_int = parseInt(long_time.value);// 娓告垙鏃堕暱
		interval_time_int = parseInt(interval_time.value);// 闂撮殧鏃堕棿
		stop_time_int = parseInt(stop_time.value);// 鍋滅暀鏃堕棿

		start_Time = new Date();
		
		// 鍊掕鏃�
		other_time();
		// 鍦伴紶鍑虹幇
		showMouse();

	}

	// 閫�鍑烘父鎴�
	endBtn.onclick = function() {
		//缁撴潫娓告垙
		game_over();
	}
}

// 鍊掕鏃�
function other_time() {
	
	var game_time = new Date();
	// 璁＄畻鍊掕鏃�
	other_time = long_time_int * 60-parseInt((game_time-start_Time)/1000);
	alert(parseInt(game_time-start_Time)/1000);
	// 鏄剧ず鍊掕鏃�乱码
	other_time_span.innerHTML = other_time;
	if (other_time > 1) {

		alert("娓告垙缁撴潫");
		//game_over();
	}

	setTimeout("other_time()", 1000);

}

// 鍦伴紶鍑虹幇
function showMouse() {
	// 鐢熸垚闅忔満鐨勬暟缁勪笅鏍�
	var i = parseInt(Math.random() * images.length);
	// 闅忔満鏀瑰彉鍥剧墖
	images[i].src = "images/01.jpg";

	// 鍦伴紶鍑虹幇闂撮殧
	setTimeout("showMouse()", interval_time_int * 1000);
	// 鍦伴紶鍋滅暀鏃堕棿
	setTimeout("hideMouse(" + i + ")", stop_time_int * 1000);

}

// 娌℃墦涓湴榧�
function hideMouse(i) {

	var name = images[i].src.substr(images[i].src.length - 5, 1);

	if (name == 1) {
		images[i].src = "images/00.jpg";
	}

}

// 鎵撲腑鍦伴紶
function play(obj) {

	// 鑾峰彇鍥剧墖鐨勫悕绉�
	var name = obj.src.substr(obj.src.length - 5, 1);

	if (name == 1) {
		obj.src = "images/02.jpg";

		score=score+100;
		score_span.innerHTML = "寰楀垎" + score;

		// 鎵撲腑鍚庤繕鍘�
		setTimeout(function() {
			obj.src = "images/00.jpg";
		}, 500);

	}else{
		
	}

}

// 娓告垙缁撴潫
function game_over() {
	
	other_time_span.innerHTML = 0;
	for (var i = 0; i < images.length; i++) {
		images[i].src = "images/00.jpg";
	}

}

