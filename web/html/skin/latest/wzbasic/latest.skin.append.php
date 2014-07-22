<?php
include_once("./_common.php");
include_once(G5_MOBILE_PATH.'/lib/latest.lib.wz.php'); // 2014-05-13 : wetoz 추가

echo latestwz_append("wzbasic", $bo_table, $rows, $subject_len, 1, "", $page);
?>