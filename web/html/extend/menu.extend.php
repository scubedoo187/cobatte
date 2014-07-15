<?php
if (!defined('_GNUBOARD_')) exit;

if(defined('_INDEX_')) {
	set_session("me_code", "");
	$me_code = "";
}
else if (isset($_REQUEST['me_code']))  {
	$me_code = trim($_REQUEST['me_code']);
	set_session("me_code", $me_code);
	if ($me_code)
        $qstr .= '&amp;me_code=' . urlencode($me_code);
} else {
	if(get_session("me_code")) {
		$me_code = get_session("me_code");
		$qstr .= '&amp;me_code=' . urlencode($me_code);
	}
}

// 메뉴
function groupmenu($skin_dir='basic', $new_time=24)
{
    global $config, $g5, $is_admin, $me_code;
    
	if(G5_IS_MOBILE)
        return;

	//if(defined('_INDEX_')) 
	//	return ;

	if(defined('_INDEX_'))
      	return ;

    $groupmenu_skin_path = G5_SKIN_PATH.'/groupmenu/'.$skin_dir;
    $groupmenu_skin_url  = G5_SKIN_URL.'/groupmenu/'.$skin_dir;
	
	if(!$me_code)
		$sql = " select * from {$g5['menu_table']} where me_use = '1' and length(me_code) = '2' order by me_order, me_id ";
	else 
		$sql = " select * from {$g5['menu_table']} where me_use = '1' and length(me_code) = '2' and me_code = substring('$me_code', 1, 2) order by me_order, me_id ";	

    $result = sql_query($sql);

	$menu = array();		
	for ($i=0; $row=sql_fetch_array($result); $i++) { // i는 main menu index
		
		$menu[$i]['code'] = $row['me_code'];
		$menu[$i]['name'] = $row['me_name'];

		if(strstr($row['me_link'], ".php?")) 		
			$menu[$i]['link'] = $row['me_link']."&me_code=".$row['me_code'];
		else
			$menu[$i]['link'] = $row['me_link']."?me_code=".$row['me_code'];

		$menu[$i]['target'] = $row['me_target'];
		
		$submenu = array();

		$sql2 = " select * from {$g5['menu_table']} where me_use = '1' and length(me_code) = '4' and substring(me_code, 1, 2) = '{$row['me_code']}' order by me_order, me_id ";

        $result2 = sql_query($sql2);

		for($j=0; $row2=sql_fetch_array($result2); $j++) { // j는 sub menu index
			$submenu[$j]['code'] = $row2['me_code'];
			$submenu[$j]['name'] = $row2['me_name'];
			if(strstr($row2['me_link'], ".php?")) 		
				$submenu[$j]['link'] = $row2['me_link']."&me_code=".$row2['me_code'];
			else
				$submenu[$j]['link'] = $row2['me_link']."?me_code=".$row2['me_code'];

			$submenu[$j]['target'] = $row2['me_target'];	
			
			// link 정보에서 게시판으로 링크된 것만 찾아 게시판 글 갯수롤 조회
			if(strstr($row2['me_link'], "bo_table=")) {			
				$query = explode("=",strstr($row2['me_link'], "bo_table="));				
				$bo_table = $query[1];
				$board = sql_fetch(" select * from {$g5['board_table']} where bo_table = '$bo_table' ");

				if($board[bo_table]) {
					
					$board_table = $g5['write_prefix'] . $bo_table;
					$latest_count =  sql_fetch(" select count(*) as cnt from {$board_table} where wr_datetime > '".date('Y-m-d H:i:s', time() - (3600 * $new_time))."'");
					$total_count =  sql_fetch(" select count(*) as cnt from {$board_table} ");

					$submenu[$j]['cnt'] = $latest_count['cnt'];
					$submenu[$j]['total_cnt'] = $total_count['cnt'];
				}
			}
		}

		$menu[$i]['submenu'] = $submenu;		
	}			       
	
	if ($i > 0) { 
		ob_start();	   
		include_once ($groupmenu_skin_path.'/groupmenu.skin.php');
		$content = ob_get_contents();
		ob_end_clean();		
	}

	return $content;
}
?>