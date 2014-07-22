<?php
if (!defined('_GNUBOARD_')) exit;

// 최신글 추출
// $cache_time 캐시 갱신시간
function latestwz($skin_dir='', $bo_table, $rows=10, $subject_len=40, $cache_time=1, $options='', $page=1, $pagemax=5)
{
    global $g5;
    //static $css = array();

    if (!$skin_dir) $skin_dir = 'basic';

    if(G5_IS_MOBILE) {
        $latest_skin_path = G5_MOBILE_PATH.'/'.G5_SKIN_DIR.'/latest/'.$skin_dir;
        $latest_skin_url  = G5_MOBILE_URL.'/'.G5_SKIN_DIR.'/latest/'.$skin_dir;
    } else {
        $latest_skin_path = G5_SKIN_PATH.'/latest/'.$skin_dir;
        $latest_skin_url  = G5_SKIN_URL.'/latest/'.$skin_dir;
    }

    $cache_fwrite = false;
    if(G5_USE_CACHE) {
        $cache_file = G5_DATA_PATH."/cache/latest-{$bo_table}-{$skin_dir}-{$rows}-{$subject_len}.php";

        if(!file_exists($cache_file)) {
            $cache_fwrite = true;
        } else {
            if($cache_time > 0) {
                $filetime = filemtime($cache_file);
                if($filetime && $filetime < (G5_SERVER_TIME - 3600 * $cache_time)) {
                    @unlink($cache_file);
                    $cache_fwrite = true;
                }
            }

            if(!$cache_fwrite)
                include($cache_file);
        }
    }

    if(!G5_USE_CACHE || $cache_fwrite) {
        $list = array();

        $sql = " select * from {$g5['board_table']} where bo_table = '{$bo_table}' ";
        $board = sql_fetch($sql);
        $bo_subject = get_text($board['bo_subject']);

        $tmp_write_table = $g5['write_prefix'] . $bo_table; // 게시판 테이블 전체이름

		// G5_USE_CACHE 를 사용할경우 
		//	query : 0 , ($rows * $pagemax)
		//	list : ($page-1) * $rows , (count($list) - ($page * $rows))
		
		// G5_USE_CACHE 를 사용하지 않을경우 
		//	query : $from_record , $rows
		//	list : 0 , count($list)

        $sql = " select * from {$tmp_write_table} where wr_is_comment = 0 order by wr_num limit 0, ". $rows ." ";
        $result = sql_query($sql);
        for ($i=0; $row = sql_fetch_array($result); $i++) {
            $list[$i] = get_list($row, $board, $latest_skin_url, $subject_len);
        }

        if($cache_fwrite) {
            $handle = fopen($cache_file, 'w');
            $cache_content = "<?php\nif (!defined('_GNUBOARD_')) exit;\n\$bo_subject=\"".$bo_subject."\";\n\$list=".var_export($list, true)."?>";
            fwrite($handle, $cache_content);
            fclose($handle);
        }
    }

    /*
    // 같은 스킨은 .css 를 한번만 호출한다.
    if (!in_array($skin_dir, $css) && is_file($latest_skin_path.'/style.css')) {
        echo '<link rel="stylesheet" href="'.$latest_skin_url.'/style.css">';
        $css[] = $skin_dir;
    }
    */

    ob_start();
    include $latest_skin_path.'/latest.skin.php';
    $content = ob_get_contents();
    ob_end_clean();

    return $content;
}

// 페이징이 필요한 최신글 일경우
function latestwz_append($skin_dir='', $bo_table, $rows=10, $subject_len=40, $cache_time=1, $options='', $page=1, $pagemax=5)
{
    global $g5;
    //static $css = array();

    if (!$skin_dir) $skin_dir = 'basic';

	$from_record = ($page-1) * $rows; // 시작 열을 구함

    if(G5_IS_MOBILE) {
        $latest_skin_path = G5_MOBILE_PATH.'/'.G5_SKIN_DIR.'/latest/'.$skin_dir;
        $latest_skin_url  = G5_MOBILE_URL.'/'.G5_SKIN_DIR.'/latest/'.$skin_dir;
    } else {
        $latest_skin_path = G5_SKIN_PATH.'/latest/'.$skin_dir;
        $latest_skin_url  = G5_SKIN_URL.'/latest/'.$skin_dir;
    }

    $cache_fwrite = false;

    $list = array();

	$sql = " select * from {$g5['board_table']} where bo_table = '{$bo_table}' ";
	$board = sql_fetch($sql);
	$bo_subject = get_text($board['bo_subject']);

	$tmp_write_table = $g5['write_prefix'] . $bo_table; // 게시판 테이블 전체이름

	$sql = " select * from {$tmp_write_table} where wr_is_comment = 0 order by wr_num limit $from_record, $rows ";
	$result = sql_query($sql);
	for ($i=0; $row = sql_fetch_array($result); $i++) {
		$list[$i] = get_list($row, $board, $latest_skin_url, $subject_len);
	}

    /*
    // 같은 스킨은 .css 를 한번만 호출한다.
    if (!in_array($skin_dir, $css) && is_file($latest_skin_path.'/style.css')) {
        echo '<link rel="stylesheet" href="'.$latest_skin_url.'/style.css">';
        $css[] = $skin_dir;
    }
    */

    ob_start();
    include $latest_skin_path.'/latest.skin.php';
    $content = ob_get_contents();
    ob_end_clean();

    return $content;
}
?>
