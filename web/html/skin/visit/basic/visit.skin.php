<?
if (!defined("_GNUBOARD_")) exit; // 개별 페이지 접근 불가 
?>
<link rel="stylesheet" href="<?php echo $visit_skin_url ?>/style.css">

<div class="section_ul">
<!--Today:-->Today <span style="color:#ff0000;"><?=number_format($visit[1])?></span>
<!--Yesterday:-->Yesterday <span style="color:#fca600;"> <?=number_format($visit[2])?></span><br />
<!--Max:-->Max <span style="color:#83af00;"><?=number_format($visit[3])?></span>
<!--Total:-->Total <span style="color:#0099cc;"><?=number_format($visit[4])?></span>
</div>