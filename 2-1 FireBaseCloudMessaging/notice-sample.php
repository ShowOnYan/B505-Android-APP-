<?php
// 參考至:https://gist.github.com/zarankumar/9f8b2b9e7e3f8ed52af49dc0f92d60e1
// API access key from Google API's Console
    define( 'API_ACCESS_KEY', 'YOUR-SERVER-API-ACCESS-KEY-GOES-HERE' );
    $registrationIds = 'YOUR-DEVICE-TOKEN';

// prep the bundle
     $msg = array
          (
		'title' 	=> $_POST['title']/*Title Of Notification*/, // 推播標題
		'body'	=> $_POST['body']/*Body  Of Notification*/, // 推播內容
             	'icon'	=> 'myicon',/*Default Icon*/
              	'sound' => 'mySound'/*Default sound*/
          );

	$fields = array
			(
				'to'		=> $_POST['role']/*$registrationIds*/, // 欲推播的對象：使用$registrationIds的話就傳給指定裝置，照範例則是傳給指定對象
				'priority'	=> 'high', // 優先度：預設high的話就會立即傳送(大概啦)，其他設定項目可以自己google
				'notification'	=> $msg
			);
	
	
	$headers = array
			(
				'Authorization: key=' . API_ACCESS_KEY,
				'Content-Type: application/json'
			);

// Send Reponse To FireBase Server	
		$ch = curl_init();
		curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
		curl_setopt( $ch,CURLOPT_POST, true );
		curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
		curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
		curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
		curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
		$result = curl_exec($ch );
		curl_close( $ch );

// Echo Result Of FireBase Server
echo $result;
?>