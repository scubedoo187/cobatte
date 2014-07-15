<?php
if (!defined('_GNUBOARD_')) exit; // 개별 페이지 접근 불가
?>

<link rel="stylesheet" href="<?php echo $board_skin_url ?>/style.css">
<h2 id="container_title"><?php echo $board['bo_subject'] ?><span class="sound_only">MyPage : 마이페이지</span></h2>
<div style="height:1px; backgroung-color:#555;"></div>

<div id="mypage-container">
	<ul id="mypage-menu">
		<li><a href="<?php echo G5_SKIN_URL ?>/board/mypage/new.php?mb_id=<?php echo $member[mb_id] ?>" title="내가 작성한 글" target="tab_mypage">내가 작성한 글</a></li>
		<li><a href="<?php echo G5_BBS_URL ?>/memo.php" title="쪽지" target="tab_mypage" <?php $mypage=1; ?>>쪽지</a></li>
		<li><a href="<?php echo G5_BBS_URL ?>/scrap.php" title="스크랩" target="tab_mypage" <?php $mypage=2; ?>>스크랩</a></li>
		<li><a href="<?php echo G5_BBS_URL ?>/point.php" title="포인트" target="tab_mypage">포인트</a></li>
		<li><a href="<?php echo G5_SKIN_URL ?>/board/mypage/qalist.php" title="1:1 문의" target="tab_mypage">1:1 문의</a></li> 
	</ul>
</div>

<iframe id=memo name=tab_mypage src="<?php echo G5_SKIN_URL ?>/board/mypage/new.php?mb_id=<?php echo $member[mb_id] ?>" width="100%" height="800px" scrolling="no" frameborder="0"></iframe>