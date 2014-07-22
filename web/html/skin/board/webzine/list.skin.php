<?php
if (!defined('_GNUBOARD_')) exit; // 개별 페이지 접근 불가
include_once(G5_LIB_PATH.'/thumbnail.lib.php');

$prevno = (int)($page - 1);

$qstr = preg_replace('#&amp;page=[0-9]*#', '', $qstr);
if ($prevno < 1) { 
  $linkprev = "#none";  
} 
else {
	$linkprev = "./board.php?bo_table=$bo_table&page=$prevno$qstr";  
}

$nextno = (int)($page + 1);
if ($nextno > $total_page) { 
  $linknext = "#none";  
} 
else {
	$linknext = "./board.php?bo_table=$bo_table&page=$nextno$qstr";  
}

// 선택옵션으로 인해 셀합치기가 가변적으로 변함
$colspan = 5;

if ($is_checkbox) $colspan++;
if ($is_good) $colspan++;
if ($is_nogood) $colspan++;

// add_stylesheet('css 구문', 출력순서); 숫자가 작을 수록 먼저 출력됨
add_stylesheet('<link rel="stylesheet" href="'.$board_skin_url.'/style.css">', 0);
?>

<?php if (!$wr_id) { ?>
<div class="writebox">
	<div class="inputbox">
		<fieldset>
			<legend>게시물 검색</legend>
			<p>
				<form name="fsearch" method="get">
				<input type="hidden" name="bo_table" value="<?php echo $bo_table ?>">
				<input type="hidden" name="sca" value="<?php echo $sca ?>">
				<input type="hidden" name="sop" value="and">

				<div class="searchcols">
					<p class="common_sfl">
					<label for="sfl" class="sound_only">검색대상</label>
					<select name="sfl" id="sfl">
						<option value="wr_subject"<?php echo get_selected($sfl, 'wr_subject', true); ?>>제목</option>
						<option value="wr_content"<?php echo get_selected($sfl, 'wr_content'); ?>>내용</option>
						<option value="wr_subject||wr_content"<?php echo get_selected($sfl, 'wr_subject||wr_content'); ?>>제목+내용</option>
						<option value="mb_id,1"<?php echo get_selected($sfl, 'mb_id,1'); ?>>회원아이디</option>
						<option value="mb_id,0"<?php echo get_selected($sfl, 'mb_id,0'); ?>>회원아이디(코)</option>
						<option value="wr_name,1"<?php echo get_selected($sfl, 'wr_name,1'); ?>>글쓴이</option>
						<option value="wr_name,0"<?php echo get_selected($sfl, 'wr_name,0'); ?>>글쓴이(코)</option>
					</select> 
					</p>
					<p class="inputnt">
						<input type="text" name="stx" value="<?php echo stripslashes($stx) ?>" placeholder="검색어(필수)" required id="stx" class="required" size="15" maxlength="15">
					</p>
					<button type="submit" class="common_btn"><span>검색</span></button>
				</div>

				</form>
			</p>
		</fieldset>
	</div>
</div>
<?php } ?>

<div class="gheight"></div>

<!-- 게시판 목록 시작 -->
<section id="bo_list<?php if ($is_admin) echo "_admin"; ?>">

    <?php if ($is_category) { ?>
    <nav id="bo_cate" class="cate-wrap">
        <h2><?php echo $board['bo_subject'] ?> 카테고리</h2>
        <ul>
            <?php echo $category_option ?>
        </ul>
    </nav>
    <?php } ?>

	<div class="gheight"></div>

	<div style="position: relative;">
		<div style="position: absolute;top:0;left:0;">
			<ul class="btn_bo_user">
				<?php if ($rss_href) { ?><li style="padding-right:4px;"><a href="<?php echo $rss_href ?>" class="btn_b01">RSS</a></li><?php } ?>
				<?php if ($admin_href) { ?><li><a href="<?php echo $admin_href ?>" class="btn_admin">관리자</a></li><?php } ?>
			</ul>
		</div>
		<nav class="paging_comm">
			<a href="<?php echo $linkprev;?>" class="btn_page btn_prev"><span class="wziconarrow ico_prev">Fun 이전</span></a>
			<span class="screen_out">현재페이지</span><span class="num_page"><?php echo $page; ?></span> <span class="txt_bar">/</span> <?php echo $total_page; ?>
			<a href="<?php echo $linknext;?>" class="btn_page btn_next"><span class="wziconarrow ico_next">Fun 다음</span></a>
		</nav>
		<div style="position: absolute;top:0;right:0;">
			<?php if ($write_href) { ?>
			<ul class="btn_bo_user">
				<?php if ($write_href) { ?><li><a href="<?php echo $write_href ?>" class="btn_b02">글쓰기</a></li><?php } ?>
			</ul>
			<?php } ?>
		</div>
	</div>

	<div class="gheight"></div>
	
	<div class="stylebox">

		<h2 id="container_title">
			<?php if ($is_checkbox) { ?>
				<label for="chkall" class="sound_only">현재 페이지 게시물 전체</label>
				<input type="checkbox" id="chkall" class="wzcheckbox" onclick="if (this.checked) all_checked(true); else all_checked(false);">
			<?php } ?>
			<?php echo $board['bo_subject'] ?><span class="sound_only"> 목록</span>
			<span class="bo_list_total">(Total <?php echo number_format($total_count) ?> 건 / <?php echo $page ?> 페이지)</span>
		</h2>
		

		<form name="fboardlist" id="fboardlist" action="./board_list_update.php" onsubmit="return fboardlist_submit(this);" method="post">
		<input type="hidden" name="bo_table" value="<?php echo $bo_table ?>">
		<input type="hidden" name="sfl" value="<?php echo $sfl ?>">
		<input type="hidden" name="stx" value="<?php echo $stx ?>">
		<input type="hidden" name="spt" value="<?php echo $spt ?>">
		<input type="hidden" name="page" value="<?php echo $page ?>">
		<input type="hidden" name="sw" value="">

		<ul id="datalist" class="webzine">
		<?php
		for ($i=0; $i<count($list); $i++) {
		
		$aclass = ($i == ($rows - 1)) ? "last" : "";

		$boheader = ""; $tstyle = ""; $liclass = "";
		if ($list[$i]['is_notice']) { // 공지사항 
			$boheader = "<span class='textheader'>공지</span> ";
			$tstyle = "font-weight:bold;";
			$liclass = "notice";
		}

		$thumb = get_list_thumbnail($board['bo_table'], $list[$i]['wr_id'], $board['bo_mobile_gallery_width'], $board['bo_mobile_gallery_height']);
		if($thumb['src']) {
			$img_content = '<img src="'.$thumb['src'].'" alt="'.$thumb['alt'].'" width="'.$board['bo_mobile_gallery_width'].'" height="'.$board['bo_mobile_gallery_height'].'" class="thumb_img">';
		} else {
			$img_content = '';
		}
		?>
		<li class="<?php echo $liclass;?> <?php if ($list[$i]['comment_cnt']) {?>havecm<?php } ?>">
			<?php if ($is_checkbox) { ?>
				<label for="chk_wr_id_<?php echo $i ?>" class="sound_only"><?php echo $list[$i]['subject'] ?></label>
                <input type="checkbox" name="chk_wr_id[]" class="list-check wzcheckbox" value="<?php echo $list[$i]['wr_id'] ?>" id="chk_wr_id_<?php echo $i ?>">
			<?php } ?>
			<a href="<?php echo $list[$i]['href']?>" class="list <?php echo ($is_checkbox ? "check" : "");?>">

				<?php if ($img_content) { ?>
				<span class="link_thumb">
					<?php echo $img_content;?>
					<span class="thumbframe"></span>
				</span>
				<?php } ?>

				<span class="dinfo">
					<strong class="tlink <?php echo (($wr_id == $list[$i]['wr_id']) ? "active" : "");?>">
						<?php
						echo $boheader;
						echo $list[$i]['icon_reply'];
						if ($is_category && $list[$i]['ca_name']) echo "<span class='textheader'>".$list[$i]['ca_name']."</span>";
						?>
						<span style="<?php echo $tstyle;?>"><?php echo $list[$i]['subject'];?></span>
						<?php
						if (isset($list[$i]['icon_new']))    echo " " . preg_replace("/(\<img )([^\>]*)(\>)/i", "\\1 style='position:relative;top:7px;margin-left:2px;' \\2 \\3", $list[$i]['icon_new']);
						if (isset($list[$i]['icon_hot']))    echo " " . preg_replace("/(\<img )([^\>]*)(\>)/i", "\\1 style='position:relative;top:7px;margin-left:2px;' \\2 \\3", $list[$i]['icon_hot']);
						if (isset($list[$i]['icon_file']))   echo " " . preg_replace("/(\<img )([^\>]*)(\>)/i", "\\1 style='position:relative;top:7px;margin-left:2px;' \\2 \\3", $list[$i]['icon_file']);
						if (isset($list[$i]['icon_link']))   echo " " . preg_replace("/(\<img )([^\>]*)(\>)/i", "\\1 style='position:relative;top:5px;margin-left:2px;' \\2 \\3", $list[$i]['icon_link']);
						if (isset($list[$i]['icon_secret'])) echo " " . preg_replace("/(\<img )([^\>]*)(\>)/i", "\\1 style='position:relative;top:5px;margin-left:2px;' \\2 \\3", $list[$i]['icon_secret']);
						?>
					</strong>
					<span class="dcon"><?php echo cut_str(strip_tags($list[$i]['wr_content']),300,"...")?></span>
					<span class="dname"><?php echo $list[$i]['wr_name'];?><span class="vbar">&#124;</span><?php echo $list[$i]['datetime2'];?></span>
				</span>
				
				<?php if ($list[$i]['comment_cnt']) {?><span class="sound_only">댓글</span><span class="cment"><span class="cbox"><?php echo $list[$i]['comment_cnt']; ?></span></span><span class="sound_only">개</span><?php } ?>
			</a>
			<?php if ($is_checkbox) { ?>
			<!-- <label for="chk_wr_id_<?php echo $i ?>" class="sound_only"><?php echo $list[$i]['subject'] ?></label>
			<input type="checkbox" name="chk_wr_id[]" value="<?php echo $list[$i]['wr_id'] ?>" id="chk_wr_id_<?php echo $i ?>"> -->
			<?php } ?>
		</li>
		<?php } ?>
		<?php if (count($list) == 0) { echo "<li><span class='list' style='text-align:center;'>게시물이 없습니다.</span></li>"; } ?>
		</ul>

	</div>

    <?php if ($list_href || $is_checkbox || $write_href) { ?>
	<div class="gheight"></div>
    <div class="bo_fx">
        <ul class="btn_bo_adm">
            <?php if ($list_href) { ?>
            <li><a href="<?php echo $list_href ?>" class="btn_b01"> 목록</a></li>
            <?php } ?>
            <?php if ($is_checkbox) { ?>
            <li><input type="submit" name="btn_submit" value="선택삭제" onclick="document.pressed=this.value"></li>
            <li><input type="submit" name="btn_submit" value="선택복사" onclick="document.pressed=this.value"></li>
            <li><input type="submit" name="btn_submit" value="선택이동" onclick="document.pressed=this.value"></li>
            <?php } ?>
        </ul>

        <ul class="btn_bo_user">
            <li><?php if ($write_href) { ?><a href="<?php echo $write_href ?>" class="btn_b02">글쓰기</a><?php } ?></li>
        </ul>
    </div>
	<div class="gheight"></div>

    <?php } ?>
    </form>
</section>

<?php if($is_checkbox) { ?>
<noscript>
<p>자바스크립트를 사용하지 않는 경우<br>별도의 확인 절차 없이 바로 선택삭제 처리하므로 주의하시기 바랍니다.</p>
</noscript>
<?php } ?>

<!-- 페이지 -->
<nav class="paging_comm">
	<a href="<?php echo $linkprev;?>" class="btn_page btn_prev"><span class="wziconarrow ico_prev">Fun 이전</span></a>
	<span class="screen_out">현재페이지</span><span class="num_page"><?php echo $page; ?></span> <span class="txt_bar">/</span> <?php echo $total_page; ?>
	<a href="<?php echo $linknext;?>" class="btn_page btn_next"><span class="wziconarrow ico_next">Fun 다음</span></a>
</nav>

<div class="gheight"></div>

<?php if ($is_checkbox) { ?>
<script>
function all_checked(sw) {
    var f = document.fboardlist;

    for (var i=0; i<f.length; i++) {
        if (f.elements[i].name == "chk_wr_id[]")
            f.elements[i].checked = sw;
    }
}

function fboardlist_submit(f) {
    var chk_count = 0;

    for (var i=0; i<f.length; i++) {
        if (f.elements[i].name == "chk_wr_id[]" && f.elements[i].checked)
            chk_count++;
    }

    if (!chk_count) {
        alert(document.pressed + "할 게시물을 하나 이상 선택하세요.");
        return false;
    }

    if(document.pressed == "선택복사") {
        select_copy("copy");
        return;
    }

    if(document.pressed == "선택이동") {
        select_copy("move");
        return;
    }

    if(document.pressed == "선택삭제") {
        if (!confirm("선택한 게시물을 정말 삭제하시겠습니까?\n\n한번 삭제한 자료는 복구할 수 없습니다\n\n답변글이 있는 게시글을 선택하신 경우\n답변글도 선택하셔야 게시글이 삭제됩니다."))
            return false;

        f.removeAttribute("target");
        f.action = "./board_list_update.php";
    }

    return true;
}

// 선택한 게시물 복사 및 이동
function select_copy(sw) {
    var f = document.fboardlist;

    if (sw == 'copy')
        str = "복사";
    else
        str = "이동";

    var sub_win = window.open("", "move", "left=50, top=50, width=500, height=550, scrollbars=1");

    f.sw.value = sw;
    f.target = "move";
    f.action = "./move.php";
    f.submit();
}
</script>
<?php } ?>
<!-- 게시판 목록 끝 -->
