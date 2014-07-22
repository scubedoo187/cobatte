<?
if (!defined("_GNUBOARD_")) exit; // 개별 페이지 접근 불가 

// 선택옵션으로 인해 셀합치기가 가변적으로 변함
$colspan = 6;

if ($is_category) $colspan++;
if ($is_checkbox) $colspan++;
if ($is_good) $colspan++;
if ($is_nogood) $colspan++;

?>

<link href="<?php echo $board_skin_url ?>/style.css" rel="stylesheet" type="text/css" />

<!-- 게시판 목록 시작 -->
<table width="<?=$width?>" align="center" cellpadding="0" cellspacing="0">
    <tr>
	    <td><? if ($is_category) { ?><table width="100%" cellspacing="8" cellpadding="0" style="position:relative; border:1px solid #e4e4e4"; bgcolor="#FFFFFF">
      <tr>
        <td>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="60" valign="top" style="padding:4px 0px 0px 10px"><?echo "  ";echo $bb_s;?><a href='./board.php?bo_table=<?=$bo_table?>&page=<?=$page?>'><b>전체</b></a><?=$bb_e?> <span style="font-size:8pt; color=#AEAEAE;">(<?=number_format($total_count)?>)</span></td>
			<td valign="top" style="padding:0px 10px 0px 10px" align="left"><!-- 분류 셀렉트 박스, 게시물 몇건, 관리자화면 링크 -->
<div style="float:left;height:22px;line-height:20px;">

<?  
$cnt_bo_1 = $bo_1[0] ? $bo_1[0] : 20; // 한줄당 분류 갯수(현재:10) 
$cnt = 1; 
$cnt0 = 0; 
$bb_s=""; $bb_e=""; 
$b_s=""; $b_e=""; 
$arr = explode("|", $board[bo_category_list]); // 구분자가 , 로 되어 있음 
	for ($i=0; $i<count($arr); $i++) 
	if (trim($arr[$i]))  { 
    $sql1 = " SELECT count(*) as cCount FROM $write_table WHERE wr_is_comment = 0 and ca_name='$arr[$i]'";
	//$sql1 = " SELECT count(*) as cCount FROM $write_table WHERE ca_name='$arr[$i]' and wr_comment >=0 ";
	$row1 = sql_fetch($sql1); 

	if ($sca == $arr[$i]) { $cnt0++; $b_s="<font color=#ff6600>"; $b_e="</font>"; } else {$b_s=""; $b_e="";} 
	$str .= " <a href='./board.php?bo_table=$bo_table&sca=".($arr[$i])."'><span class='small'>$b_s$arr[$i]$b_e&nbsp;</span><span style='font-family: Tahoma; font-size:10px; color:#919191;'>+$row1[cCount]</span></a>&nbsp;"; 
	if ($cnt == $cnt_bo_1) { $cnt = 0; $str .= "<br>"; } 
	$cnt++; 
	} 
	if ($cnt0 == 0 ) { $bb_s="<b>"; $bb_e="</b>"; } 
   
	$sql2 = " SELECT count(*) as cCount FROM $write_table WHERE wr_is_comment = 0 "; 
    $row2 = sql_fetch($sql2);
    $Total_Cat = $row2[cCount]
?> 
<?=$bb_e?> 
<?=$str?>
</div>
</td>
		  </tr>
		</table>
	</td>
 </tr>
</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td height="10"></td>
		  </tr>
		</table><?}?>
<!-- 제목 -->
<form name="fboardlist" method="post" style="margin:0px;">
<input type="hidden" name="bo_table" value="<?=$bo_table?>">
<input type="hidden" name="sfl"  value="<?=$sfl?>">
<input type="hidden" name="stx"  value="<?=$stx?>">
<input type="hidden" name="spt"  value="<?=$spt?>">
<input type="hidden" name="page" value="<?=$page?>">
<input type="hidden" name="sw"   value="">

<table width="100%" cellpadding="0" cellspacing="0">
    <tr>
	    <td colspan=<?=$colspan?> class="bbs_line3"></td>
	</tr>
    <tr>
    <tr class="bbs_top_title">
       <!--<td width="50">번호</td>-->
        <? if ($is_checkbox) { ?><td width="12" style='padding:0px 0px 0px 5px'><INPUT onclick="if (this.checked) all_checked(true); else all_checked(false);" type=checkbox></td><?}?>
        <td  height="35" align="left" style='padding:0px 0px 0px 10px'><b><?=$board[bo_subject]?>관련 참고사이트</b></td> 
		<? if ($is_category) { ?><td width="80">구분</td><?}?>
        <!--<td width="50" align="center"><?=subject_sort_link('wr_datetime', $qstr2, 1)?>등록일</a></td>-->	
	    <td width="50" align="center"><?=subject_sort_link('wr_hit', $qstr2, 1)?>조회수</a></td>
        <? if ($is_good) { ?><td width='40' align='center'><?=subject_sort_link('wr_good', $qstr2, 1)?>추천</a></td><?}?>
        <? if ($is_nogood) { ?><td width='40' align='center'><?=subject_sort_link('wr_nogood', $qstr2, 1)?>비추천</a></td><?}?>	
	    <td width="50" align="center"><?=subject_sort_link('wr_hit', $qstr2, 1)?>편집</a></td>
    </tr>
    <tr>
	    <td colspan=<?=$colspan?> class="bbs_line2"></td>
	</tr>
    <tr>
	    <td colspan=<?=$colspan?> class="bbs_line2_1"></td>
	</tr>
<!-- 목록 -->
<? for ($i=0; $i<count($list); $i++) { ?>
    <tr height="32" align="center" <?  if ($list[$i][is_notice])  { echo " bgcolor=#f8f9fa "; } ?>> 
        <? if ($is_checkbox) { ?><td width="12" style='padding:0px 0px 0px 5px'><input type=checkbox name=chk_wr_id[] value="<?=$list[$i][wr_id]?>"></td><? } ?>
		<td align=left style='word-break:break-all;padding:0px 0px 0px 10px'>
        <? 
        echo $nobr_begin;
        echo "<a href='".$list[$i][link_href][1]."' class='slink' target='_blank'>";
	    if ($list[$i][is_notice])
            echo "<span class='small'>{$list[$i]['subject']}</span>";
        else
        {
            $style1 = $style2 = "";
            if ($list[$i][icon_new])
                $style1 = "color:#;"; // 최신글 컬러
            if (!$list[$i][comment_cnt]) // 코멘트 없는것만 굵게
                $style2 = "";
            echo "<span style='small'><FONT COLOR='#ef6300'>{$list[$i]['subject']}</FONT></span>&nbsp;";
        }
        echo "</a>";

                // if ($list[$i]['link']['count']) { echo '['.$list[$i]['link']['count']}.']'; }
                // if ($list[$i]['file']['count']) { echo '<'.$list[$i]['file']['count'].'>'; }

                if (isset($list[$i]['icon_new'])) echo $list[$i]['icon_new'];
                if (isset($list[$i]['icon_hot'])) echo $list[$i]['icon_hot'];
                if (isset($list[$i]['icon_file'])) echo $list[$i]['icon_file'];
                if (isset($list[$i]['icon_link'])) echo $list[$i]['icon_link'];
                if (isset($list[$i]['icon_secret'])) echo $list[$i]['icon_secret'];
        ?>
		<span class=small> | <a href="<?=$list[$i][href]?>"><?=cut_str(strip_tags($list[$i][wr_content]),60,"…")?></a></span></td>
       <!-- <td class="text8" align="center"><?=date("m-d", strtotime($list[$i][wr_datetime]))?></td>-->
        
        <? if ($is_category) { ?><td width="80" style="padding:0px 0px 0px 0px" align="center"><a href="<?=$list[$i][ca_name_href]?>"><span class=small><font color="#a5a5a5"><?=$list[$i][ca_name]?></font></span></a></td><? } ?>
        <td class="text8" align="center"><?=$list[$i][wr_hit]?></td>
        <? if ($is_good) { ?><td align="center" class="text8"><?=$list[$i][wr_good]?></td><? } ?>
        <? if ($is_nogood) { ?><td align="center" class="text8"><?=$list[$i][wr_nogood]?></td><? } ?>
        <td class="text8" align="center"><?  // 수정버튼
		if (($member[mb_id] && ($member[mb_id] == $list[$i][mb_id])) || $is_admin) { 
		  $edit_href = "./write.php?w=u&bo_table=$bo_table&wr_id=".$list[$i][wr_id]."&page=$page"; 
		  echo "<a href={$edit_href}><img src='{$board_skin_url}/img/btn_list_modify.gif' border='0' align='absmiddle' alt='수정하기'></a>&nbsp;"; 
		}else{ 
		  $edit_href  = "./password.php?w=u&bo_table=$bo_table&wr_id=".$list[$i][wr_id]."&page=$page"; 
		  echo "<a href={$edit_href}><img src='{$board_skin_url}/img/btn_list_modify.gif' border='0' align='absmiddle' alt='수정하기'></a>&nbsp;"; 
		} 

	?></td>
    </tr>
    <tr>
	    <td colspan=<?=$colspan?> class='bbs_line'></td>
	</tr>
<?}?>
    <? if (count($list) == 0) { echo "<tr><td colspan='$colspan' height='400' align='center'>게시물이 없습니다.</td></tr>"; } ?>
    <tr><td colspan=<?=$colspan?> class='bbs_line'></td>
	</tr>
</table>
</form>

<!-- 페이징 -->
<div class="paginate_complex">
    <? if ($prev_part_href) { echo "<a href='$prev_part_href' class=\"direction prev\">	<span> </span><span> </span> 이전검색</a>"; } ?>
    <?
    $write_pages = preg_replace("/<b>([0-9]*)<\/b>/", "<strong>$1</strong>", $write_pages);
    $write_pages = str_replace(">처음", " class=\"direction prev\">	<span> </span><span> </span> ", $write_pages);
    $write_pages = str_replace(">이전", " class=\"direction prev\"><span> </span> ", $write_pages);
    $write_pages = str_replace(">다음", " class=\"direction next\" > <span> </span> ", $write_pages);
    $write_pages = str_replace(">맨끝", " class=\"direction next\" ><span> </span><span> </span> ", $write_pages);
    $write_pages = str_replace("&nbsp;", "", $write_pages);
    ?>
    <?=$write_pages?>
    <? if ($next_part_href) { echo "<a href='$next_part_href'> class=\"direction next\">다음검색 <span> </span><span> </span></a>"; } ?>
</div>
<br><br>
<!-- 검색&버튼 -->
<div style="float:left;">
<!-- 게시판 검색 시작 { -->
<fieldset id="bo_sch">
    <legend>게시물 검색</legend>

    <form name="fsearch" method="get">
    <input type="hidden" name="bo_table" value="<?php echo $bo_table ?>">
    <input type="hidden" name="sca" value="<?php echo $sca ?>">
    <input type="hidden" name="sop" value="and">
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
    <label for="stx" class="sound_only">검색어<strong class="sound_only"> 필수</strong></label>
    <input type="text" name="stx" value="<?php echo stripslashes($stx) ?>" required id="stx"  class="frm_input required" size="15" maxlength="15">
    <input type="submit" value="검색" class="btn_submit">
    </form>
</fieldset>
<!-- } 게시판 검색 끝 -->
</div>

<div style="float:right;">
    <? if ($is_checkbox) { ?>
    <a href="javascript:select_delete();"><img src="<?php echo $board_skin_url ?>/img/btn_select_delete.gif" border='0'></a><a href="javascript:select_copy('copy');"><img src="<?php echo $board_skin_url ?>/img/btn_select_copy.gif" border='0'></a><a href="javascript:select_copy('move');"><img src="<?php echo $board_skin_url ?>/img/btn_select_move.gif" border='0'></a>
    <? } ?>	
    <? if ($list_href) { ?><a href="<?=$list_href?>"><img src="<?php echo $board_skin_url ?>/img/btn_list.gif" border='0'></a><? } ?>
    <? if ($write_href) { ?><a href="<?=$write_href?>"><img src="<?php echo $board_skin_url ?>/img/btn_write.gif" border='0'></a><? } ?>
</div>

    </td>
    </tr>
</table>


<script language="JavaScript">
if ("<?=$sca?>") document.fcategory.sca.value = "<?=$sca?>";
if ("<?=$stx?>") {
    document.fsearch.sfl.value = "<?=$sfl?>";
}
</script>

<? if ($is_checkbox) { ?>
<script language="JavaScript">
function all_checked(sw)
{
    var f = document.fboardlist;

    for (var i=0; i<f.length; i++) {
        if (f.elements[i].name == "chk_wr_id[]")
            f.elements[i].checked = sw;
    }
}

function check_confirm(str)
{
    var f = document.fboardlist;
    var chk_count = 0;

    for (var i=0; i<f.length; i++) {
        if (f.elements[i].name == "chk_wr_id[]" && f.elements[i].checked)
            chk_count++;
    }

    if (!chk_count) {
        alert(str + "할 게시물을 하나 이상 선택하세요.");
        return false;
    }
    return true;
}

// 선택한 게시물 삭제
function select_delete()
{
    var f = document.fboardlist;

    str = "삭제";
    if (!check_confirm(str))
        return;

    if (!confirm("선택한 게시물을 정말 "+str+" 하시겠습니까?\n\n한번 "+str+"한 자료는 복구할 수 없습니다"))
        return;

    f.action = "./delete_all.php";
    f.submit();
}

// 선택한 게시물 복사 및 이동
function select_copy(sw)
{
    var f = document.fboardlist;

    if (sw == "copy")
        str = "복사";
    else
        str = "이동";
                       
    if (!check_confirm(str))
        return;

    var sub_win = window.open("", "move", "left=50, top=50, width=396, height=550, scrollbars=1");

    f.sw.value = sw;
    f.target = "move";
    f.action = "./move.php";
    f.submit();
}
</script>
<? } ?>
<!-- 게시판 목록 끝 -->