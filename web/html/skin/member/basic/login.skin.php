<?php
if (!defined('_GNUBOARD_')) exit; // 개별 페이지 접근 불가

// add_stylesheet('css 구문', 출력순서); 숫자가 작을 수록 먼저 출력됨
add_stylesheet('<link rel="stylesheet" href="'.$member_skin_url.'/style.css">', 0);
add_stylesheet('<style>
.ms-box {position:relative;padding-right:18px;padding-left:18px;border:1px solid #d2d2db;background:#f7f7f7}
.ms-box-lbl {position:absolute;top:10px;left:22px;color:#666;font-size:0.95em;letter-spacing:-0.1em;cursor:text}
.ms-box-lbl-focus {color:#ccc}
.ms-box-inp {border:0}

.ms-confirm {margin:0 auto;padding:30px 0 0;width:300px !important}
.ms-confirm h1 {margin:0 0 20px;font-size:1.3em;text-align:center}
.ms-confirm h2 {margin:0 0 10px}
.ms-confirm p {margin:0 0 10px;line-height:1.8em}
.ms-confirm .ms-box {margin:0 0 10px}
.ms-confirm .ms-box-lbl {top:13px}
.ms-confirm .ms-box-inp {width:100%;height:40px;line-height:3em}
.ms-confirm-id {height:40px;line-height:3em}
.ms-confirm-submit {display:block;margin:0 0 30px;padding:0;width:100%;height:40px;border:0;background:#ff4b4b;color:#fff;text-decoration:none;cursor:pointer}
.ms-confirm-links {text-align:center}
.ms-confirm-links a {color:#666;font-size:0.95em}
.ms-confirm-links #login_password_lost {display:inline-block;margin:0 20px 0 0}</style>', 0);
?>

<!-- 로그인 시작 { -->
<div id="mb_login" class="mbskin ms-confirm">
    <h1><?php echo $g5['title'] ?></h1>

    <div id="mb_login_mb">
        <form name="flogin" action="<?php echo $login_action_url ?>" onsubmit="return flogin_submit(this);" method="post">
        <input type="hidden" name="url" value='<?php echo $login_url ?>'>

        <div class="ms-box ms-lbl-wrap">
            <label for="login_id" class="ms-box-lbl">회원아이디<strong class="sound_only"> 필수</strong></label>
            <input type="text" name="mb_id" id="login_id" required class="required ms-box-inp" maxLength="20">
        </div>

        <div class="ms-box ms-lbl-wrap">
            <label for="login_pw" class="ms-box-lbl">비밀번호<strong class="sound_only"> 필수</strong></label>
            <input type="password" name="mb_password" id="login_pw" required class="required ms-box-inp" maxLength="20">
        </div>

        <input type="submit" value="로그인" class="ms-confirm-submit">

        </form>
    </div>

    <?php // 쇼핑몰 사용시 여기부터 ?>
    <?php if ($default['de_level_sell'] == 1) { // 상품구입 권한 ?>

        <!-- 주문하기, 신청하기 -->
        <?php if (preg_match("/orderform.php/", $url)) { ?>

    <section id="mb_login_notmb">
        <h2>비회원 구매</h2>

        <p>
            비회원으로 주문하시는 경우 포인트는 지급하지 않습니다.
        </p>

        <div id="guest_privacy" class="ms-box">
            <span class="ms-box-tr ms-box-corner"></span>
            <span class="ms-box-tl ms-box-corner"></span>
            <span class="ms-box-br ms-box-corner"></span>
            <span class="ms-box-bl ms-box-corner"></span>
            <div id="guest_privacy_con"><?php echo $default['de_guest_privacy']; ?></div>
        </div>

        <input type="checkbox" id="agree" value="1">
        <label for="agree">개인정보수집에 대한 내용을 읽었으며 이에 동의합니다</label>

        <div class="btn_confirm">
            <a href="javascript:guest_submit(document.flogin);" id="mb_login_notmb_submit">비회원으로 구매하기</a>
        </div>

        <script>
        function guest_submit(f)
        {
            if (document.getElementById('agree')) {
                if (!document.getElementById('agree').checked) {
                    alert("개인정보수집에 대한 내용을 읽고 이에 동의하셔야 합니다.");
                    return;
                }
            }

            f.url.value = "<?php echo $url; ?>";
            f.action = "<?php echo $url; ?>";
            f.submit();
        }
        </script>
    </section>

        <?php } else if (preg_match("/orderinquiry.php$/", $url)) { ?>

    <section id="mb_login_od">
        <h2>비회원 주문조회</h2>
        <p>메일로 발송해드린 주문서의 <strong>주문번호</strong> 및 주문 시 입력하신 <strong>비밀번호</strong>를 정확히 입력해주십시오.</p>

        <form name="forderinquiry" method="post" action="<?php echo urldecode($url); ?>" autocomplete="off">

        <div class="ms-box ms-lbl-wrap">
            <span class="ms-box-tr ms-box-corner"></span>
            <span class="ms-box-tl ms-box-corner"></span>
            <span class="ms-box-br ms-box-corner"></span>
            <span class="ms-box-bl ms-box-corner"></span>
            <label for="od_id" class="od_id ms-box-lbl">주문서번호<strong class="sound_only"> 필수</strong></label>
            <input type="text" name="od_id" value="<?php echo $od_id; ?>" id="od_id" required class="required ms-box-inp" size="20">
        </div>
        <div class="ms-box ms-lbl-wrap">
            <span class="ms-box-tr ms-box-corner"></span>
            <span class="ms-box-tl ms-box-corner"></span>
            <span class="ms-box-br ms-box-corner"></span>
            <span class="ms-box-bl ms-box-corner"></span>
            <label for="id_pwd" class="od_pwd ms-box-lbl">비밀번호<strong class="sound_only"> 필수</strong></label>
            <input type="password" name="od_pwd" size="20" id="od_pwd" required class="required ms-box-inp">
        </div>
        <input type="submit" value="확인" id="mb_login_od_submit">

        </form>
    </section>

        <?php } ?>

    <?php } ?>
    <?php // 쇼핑몰 사용시 여기까지 반드시 복사해 넣으세요 ?>

    <div class="ms-confirm-links">
        <a href="<?php echo G5_BBS_URL ?>/password_lost.php" target="_blank" id="login_password_lost">아이디/비밀번호 찾기</a>
        <a href="./register.php">회원가입</a>
    </div>

</div>

<script>
$(function(){
    $("#login_id").focus();
});
function flogin_submit(f)
{
    return true;
}

// ms-box-lbl {
$(function() {
    var $msBoxInp = $('.ms-lbl-wrap .ms-box-inp');
    $msBoxInp.each(function() {
        var $this = $(this);
        var thisId = $(this).attr('id');
        var $msBoxLbl = $("label[for='"+thisId+"']");
        if ($this.attr('value') == "") $msBoxLbl.css('visibility','visible');
        else $msBoxLbl.css('visibility','hidden');
    });
    $msBoxInp.focus(function() {
        var $this = $(this);
        var thisId = $(this).attr('id');
        var $msBoxLbl = $("label[for='"+thisId+"']");
        if ($this.attr('value') == "") $msBoxLbl.addClass("ms-box-lbl-focus");
        else $msBoxLbl.css('visibility','hidden');
    });
    $msBoxInp.keydown(function() {
        var $this = $(this);
        var thisId = $(this).attr('id');
        var $msBoxLbl = $("label[for='"+thisId+"']");
        if ($this.attr('value') == "") $msBoxLbl.addClass("ms-box-lbl-focus");
        else $msBoxLbl.css('visibility','hidden');
    });
    $msBoxInp.change(function() {
        var $this = $(this);
        var thisId = $(this).attr('id');
        var $msBoxLbl = $("label[for='"+thisId+"']");
        if ($this.attr('value') == "") $msBoxLbl.addClass("ms-box-lbl-focus");
        else $msBoxLbl.css('visibility','hidden');
    });
    $msBoxInp.blur(function() {
        var $this = $(this);
        var thisId = $(this).attr('id');
        var $msBoxLbl = $("label[for='"+thisId+"']");
        if ($this.attr('value') == "") $msBoxLbl.css("visibility","visible").removeClass("ms-box-lbl-focus");
    });
});
// } ms-box-lbl
</script>
<!-- } 로그인 끝 -->