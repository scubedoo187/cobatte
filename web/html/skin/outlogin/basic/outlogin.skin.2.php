<?php
if (!defined("_GNUBOARD_")) exit; // 개별 페이지 접근 불가
?>
      <!-- OUTLOGIN -->
	  <link rel="stylesheet" href="<?php echo $outlogin_skin_url ?>/style.css">
<section id="ol_after" class="ol">

    <ul id="ol_after_private">
      <div id="outlogin_main">
        <div style="position:relative;">
          <div class="olm_mphoto" style="background:url('<?php echo G5_DATA_URL;?>/member/te/<?php echo $member[mb_id] ?>.gif');"></div>

    <header id="ol_after_hd">
        <h2>나의 회원정보</h2>
        <strong><?php echo $nick ?>님</strong>
        <?php if ($is_admin == 'super' || $is_auth) {  ?><a href="<?php echo G5_ADMIN_URL ?>" class="btn_admin">관리자 모드</a><?php }  ?>
    </header>
          <div class="olm_message">
            <a href="<?php echo G5_BBS_URL ?>/memo.php" target="_blank" id="ol_after_memo" class="win_memo">
                <span class="sound_only">안 읽은 </span>쪽지 <b><font color="#ff6600"><?php echo $memo_not_read ?></font></b>
            </a>
          </div>
          <div class="olm_point">
            <a href="<?php echo G5_BBS_URL ?>/point.php" target="_blank" id="ol_after_pt" class="win_point"><b><font color="#ff6600"><?php echo $point ?>p</font></b></a>
          </div>
          
	  <div class="olm_myroom">
            <a href="<?php echo G5_BBS_URL ?>/board.php?bo_table=mypage" id="ol_after_myroom" class="win_myroom">마이룸</a>
          </div>
        
	  <div class="olm_scrap">
            <a href="<?php echo G5_BBS_URL ?>/scrap.php" target="_blank" id="ol_after_scrap" class="win_scrap">스크랩</a>
          </div>
        </div>        
        </form>
      </div>
    </ul>
    <footer id="ol_after_ft">
        <a href="<?php echo G5_BBS_URL ?>/member_confirm.php?url=register_form.php" id="ol_after_info">정보수정</a>
        <a href="<?php echo G5_BBS_URL ?>/logout.php" id="ol_after_logout">로그아웃</a>
    </footer>
</section>
<script>
// 탈퇴의 경우 아래 코드를 연동하시면 됩니다.
function member_leave()
{
    if (confirm("정말 회원에서 탈퇴 하시겠습니까?"))
        location.href = "<?php echo G5_BBS_URL ?>/member_confirm.php?url=member_leave.php";
}
</script>
<!-- } 로그인 후 아웃로그인 끝 -->
