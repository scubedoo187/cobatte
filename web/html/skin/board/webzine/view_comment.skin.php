<?php
if (!defined('_GNUBOARD_')) exit; // 개별 페이지 접근 불가
?>

<script>
// 글자수 제한
var char_min = parseInt(<?php echo $comment_min ?>); // 최소
var char_max = parseInt(<?php echo $comment_max ?>); // 최대
</script>

<!-- 댓글 리스트 -->
<section id="bo_vc" class="commentbox">
    <h2>댓글목록</h2>
    <div class="tabs">
		<a href="#none" style="width:50%;" class="active"><span>댓글 (<?php echo $view['wr_comment'];?>)</span></a>
		<a href="#none" style="width:50%;" class="btn_comment_write"><span>코멘트쓰기</span></a>
	</div>
	<?php
    for ($i=0; $i<count($list); $i++) {
        $comment_id = $list[$i]['wr_id'];
        $cmt_depth = ""; // 댓글단계
        $cmt_depth = strlen($list[$i]['wr_comment_reply']) * 20;
            $str = $list[$i]['content'];
            if (strstr($list[$i]['wr_option'], "secret"))
                $str = $str;
            $str = preg_replace("/\[\<a\s.*href\=\"(http|https|ftp|mms)\:\/\/([^[:space:]]+)\.(mp3|wma|wmv|asf|asx|mpg|mpeg)\".*\<\/a\>\]/i", "<script>doc_write(obj_movie('$1://$2.$3'));</script>", $str);
    ?>
    <article id="c_<?php echo $comment_id ?>" <?php if ($cmt_depth) { ?>style="margin-left:<?php echo $cmt_depth ?>px;border-top-color:#e0e0e0"<?php } ?>>
        <header>
            <h1><?php echo $list[$i]['wr_name'] ?>님의 댓글</h1>
            <?php echo $list[$i]['name'] ?>
            <?php if ($cmt_depth) { ?><img src="<?php echo $board_skin_url ?>/img/icon_reply.gif" alt="댓글의 댓글" class="icon_reply"><?php } ?>
            <?php if ($is_ip_view) { ?>
            아이피
            <span class="bo_vc_hdinfo"><?php echo $list[$i]['ip']; ?></span>
            <?php } ?>
            작성일
            <span class="bo_vc_hdinfo"><time datetime="<?php echo date('Y-m-d\TH:i:s+09:00', strtotime($list[$i]['datetime'])) ?>"><?php echo $list[$i]['datetime'] ?></time></span>
            <?php
            include(G5_SNS_PATH."/view_comment_list.sns.skin.php");
            ?>
        </header>

        <!-- 댓글 출력 -->
        <p>
            <?php if (strstr($list[$i]['wr_option'], "secret")) echo "<img src=\"".$board_skin_url."/img/icon_secret.gif\" alt=\"비밀글\">"; ?>
            <?php echo $str ?>
        </p>

        <span id="edit_<?php echo $comment_id ?>"></span><!-- 수정 -->
        <span id="reply_<?php echo $comment_id ?>"></span><!-- 답변 -->

        <input type="hidden" id="secret_comment_<?php echo $comment_id ?>" value="<?php echo strstr($list[$i]['wr_option'],"secret") ?>">
        <textarea id="save_comment_<?php echo $comment_id ?>" style="display:none"><?php echo get_text($list[$i]['content1'], 0) ?></textarea>

        <?php if($list[$i]['is_reply'] || $list[$i]['is_edit'] || $list[$i]['is_del']) {
            $query_string = str_replace("&", "&amp;", $_SERVER['QUERY_STRING']);

            if($w == 'cu') {
                $sql = " select wr_id, wr_content from $write_table where wr_id = '$c_id' and wr_is_comment = '1' ";
                $cmt = sql_fetch($sql);
                $c_wr_content = $cmt['wr_content'];
            }

            $c_reply_href = './board.php?'.$query_string.'&amp;c_id='.$comment_id.'&amp;w=c#bo_vc_w';
            $c_edit_href = './board.php?'.$query_string.'&amp;c_id='.$comment_id.'&amp;w=cu#bo_vc_w';
        ?>
        <footer>
            <ul class="bo_vc_act">
                <?php if ($list[$i]['is_reply']) { ?><li><a href="<?php echo $c_reply_href; ?>" onclick="comment_box('<?php echo $comment_id ?>', 'c'); return false;">답변</a></li><?php } ?>
                <?php if ($list[$i]['is_edit']) { ?><li><a href="<?php echo $c_edit_href; ?>" onclick="comment_box('<?php echo $comment_id ?>', 'cu'); return false;">수정</a></li><?php } ?>
                <?php if ($list[$i]['is_del'])  { ?><li><a href="<?php echo $list[$i]['del_link']; ?>" onclick="return comment_delete();">삭제</a></li><?php } ?>
            </ul>
        </footer>
        <?php } ?>
    </article>
    <?php } ?>
    <?php if ($i == 0) { //댓글이 없다면 ?><p id="bo_vc_empty">등록된 댓글이 없습니다.</p><?php } ?>

</section>

<?php if ($is_comment_write) {
    ?>
    <script type="text/javascript">
    <!--
		function comment_delete()
		{
			return confirm("이 댓글을 삭제하시겠습니까?");
		}
    //-->
    </script>
<?php } ?>


<?php if ($is_comment_write) {
	if($w == '')
		$w = 'c';
?>
<div id="comment_frm" class="popup_bx" style="width:250px;margin-left:-125px;">
	<div class="header_popup">
		<h1>댓글</h1>
	</div>
	
	<form name="fviewcomment" action="./write_comment_update.php" onsubmit="return fviewcomment_submit(this);" method="post" autocomplete="off">
	<input type="hidden" name="w" value="<?php echo $w ?>" id="w">
	<input type="hidden" name="bo_table" value="<?php echo $bo_table ?>">
	<input type="hidden" name="wr_id" value="<?php echo $wr_id ?>">
	<input type="hidden" name="comment_id" value="<?php echo $c_id ?>" id="comment_id">
	<input type="hidden" name="sca" value="<?php echo $sca ?>">
	<input type="hidden" name="sfl" value="<?php echo $sfl ?>">
	<input type="hidden" name="stx" value="<?php echo $stx ?>">
	<input type="hidden" name="spt" value="<?php echo $spt ?>">
	<input type="hidden" name="page" value="<?php echo $page ?>">
	<input type="hidden" name="is_good" value="">

	<div class="content_popup">
		<!-- <div class="mt10 text_center">
			<p>설명</p>
		</div> -->

		<div class="writebox" style="border:none;background-color:#fff;box-shadow:none;-webkit-box-shadow:none;">
			<div class="inputbox">
			<fieldset>

			<?php if ($is_guest) { ?>
			<p class="inputnt">
				<strong class="sound_only"><label for="wr_name">이름</label></strong>
				<input type="text" name="wr_name" id="wr_name" required itemname="이름" placeholder="이름" class="frm_input required" maxlength="20">
			</p>
			<p class="inputnt">
				<strong class="sound_only"><label for="wr_password">패스워드</label></strong>
				<input type="text" name="wr_password" id="wr_password" required itemname="패스워드" placeholder="패스워드" class="frm_input required" maxlength="20">
			</p>
			<?php } ?>
			<p class="inputnt">
				<input type="checkbox" name="wr_secret" value="secret" id="wr_secret"><label for="wr_secret"> 비밀글사용</label>
			</p>
			<p class="inputarea">
				<strong class="sound_only"><label for="wr_subject">내용필수</label></strong>
				<?php if ($comment_min || $comment_max) { ?><strong id="char_cnt"><span id="char_count"></span>글자</strong><?php } ?>
				<textarea id="wr_content" name="wr_content" required title="댓글 내용"
				<?php if ($comment_min || $comment_max) { ?>onkeyup="check_byte('wr_content', 'char_count');"<?php } ?>><?php echo $c_wr_content; ?></textarea>
				<?php if ($comment_min || $comment_max) { ?><script> check_byte('wr_content', 'char_count'); </script><?php } ?>
			</p>

			<?php
            if($board['bo_use_sns'] && ($config['cf_facebook_appid'] || $config['cf_twitter_key'])) {
            ?>
			<div id="bo_vc_send_sns"></div>
            <?php
            }
            ?>

			<?php if ($is_guest) { ?>
				<strong class="sound_only">자동등록방지</strong>
				<?php echo $captcha_html; ?>
            <?php } ?>
			
			</fieldset>
			</div>
		</div>

		<ul class="grid_btn">
			<li><button type="submit" class="btn_small_gray" id="btn_submit" style="width:98%;">댓글등록</button></li>
			<li><button type="button" class="btn_small_gray btn_comment_write" style="width:98%;">창닫기</button></li>
		</ul>
	</div>
	<button type="button" class="btn close_popup btn_comment_write"></button>

	</form>

</div>



<script type="text/javascript">
<!--
	jQuery(document).ready(function () {
		$(".btn_comment_write").click(function(e) {

			// 데이터 초기화
			$("#w").val("c");
			$("#wr_content").val("");
			$("#comment_id").val("");
			document.getElementById('wr_secret').checked = false;
			_jsPopComment();
		});

		// 레이어 창 닫기. 
		// 작성중 잘못해서 클릭되는것을 방지하기 위해서 주석처리 함.
		/*
		$('.dimm').click(function()
		{
			$(".dimm").hide(); 
			$(".popup_bx").hide();
		});
		*/
	});
	function _jsPopComment() {
		if ($("#comment_frm").is(":visible")) {
			$(".dimm").hide();
			$(".popup_bx").hide();
		}
		else {
			$('.dimm').width($(document).width()).height($(document).height()).show();
			var headerHeight = 50; // 헤더의 height
			var top = ((($(window).height() - $("#comment_frm").height()) / 2) + $(window).scrollTop()) - headerHeight;
			$("#comment_frm").css({"top": top}).fadeIn();
		}
	}
	
	function good_and_write()
    {
        var f = document.fviewcomment;
        if (fviewcomment_submit(f)) {
            f.is_good.value = 1;
            f.submit();
        } else {
            f.is_good.value = 0;
        }
    }

    function fviewcomment_submit(f)
    {
        var pattern = /(^\s*)|(\s*$)/g; // \s 공백 문자

        f.is_good.value = 0;

        /*
        var s;
        if (s = word_filter_check(document.getElementById('wr_content').value))
        {
            alert("내용에 금지단어('"+s+"')가 포함되어있습니다");
            document.getElementById('wr_content').focus();
            return false;
        }
        */

        var subject = "";
        var content = "";
        $.ajax({
            url: g5_bbs_url+"/ajax.filter.php",
            type: "POST",
            data: {
                "subject": "",
                "content": f.wr_content.value
            },
            dataType: "json",
            async: false,
            cache: false,
            success: function(data, textStatus) {
                subject = data.subject;
                content = data.content;
            }
        });

        if (content) {
            alert("내용에 금지단어('"+content+"')가 포함되어있습니다");
            f.wr_content.focus();
            return false;
        }

        // 양쪽 공백 없애기
        var pattern = /(^\s*)|(\s*$)/g; // \s 공백 문자
        document.getElementById('wr_content').value = document.getElementById('wr_content').value.replace(pattern, "");
        if (char_min > 0 || char_max > 0)
        {
            check_byte('wr_content', 'char_count');
            var cnt = parseInt(document.getElementById('char_count').innerHTML);
            if (char_min > 0 && char_min > cnt)
            {
                alert("댓글은 "+char_min+"글자 이상 쓰셔야 합니다.");
                return false;
            } else if (char_max > 0 && char_max < cnt)
            {
                alert("댓글은 "+char_max+"글자 이하로 쓰셔야 합니다.");
                return false;
            }
        }
        else if (!document.getElementById('wr_content').value)
        {
            alert("댓글을 입력하여 주십시오.");
            return false;
        }

        if (typeof(f.wr_name) != 'undefined')
        {
            f.wr_name.value = f.wr_name.value.replace(pattern, "");
            if (f.wr_name.value == '')
            {
                alert('이름이 입력되지 않았습니다.');
                f.wr_name.focus();
                return false;
            }
        }

        if (typeof(f.wr_password) != 'undefined')
        {
            f.wr_password.value = f.wr_password.value.replace(pattern, "");
            if (f.wr_password.value == '')
            {
                alert('비밀번호가 입력되지 않았습니다.');
                f.wr_password.focus();
                return false;
            }
        }

        <?php if($is_guest) echo chk_captcha_js(); ?>

        document.getElementById("btn_submit").disabled = "disabled";

        return true;
    }

    function comment_box(comment_id, work)
    {
        var el_id;
        // 댓글 아이디가 넘어오면 답변, 수정
        if (comment_id)
        {
            if (work == 'c')
                el_id = '#reply_' + comment_id;
            else
                el_id = '#edit_' + comment_id;

			document.getElementById('comment_id').value = comment_id;
			document.getElementById('w').value = work;
        }

		// 댓글 수정
		if (work == "cu")
		{
			$("#wr_content").val($("#save_comment_"+ comment_id).val());

			if (typeof char_count != 'undefined')
                    check_byte('wr_content', 'char_count');

			if (document.getElementById('secret_comment_'+comment_id).value)
				document.getElementById('wr_secret').checked = true;
			else
				document.getElementById('wr_secret').checked = false;
		}
		else {
			$("#wr_content").val("");
		}

		_jsPopComment();
		//$("#captcha_reload").trigger("click");

    }

    function comment_delete()
    {
        return confirm("이 댓글을 삭제하시겠습니까?");
    }

	<?php if($board['bo_use_sns'] && ($config['cf_facebook_appid'] || $config['cf_twitter_key'])) { ?>
    // sns 등록
    $(function() {
        $("#bo_vc_send_sns").load( "<?php echo G5_SNS_URL; ?>/view_comment_write.sns.skin.php?bo_table=<?php echo $bo_table; ?>" );
    });
    <?php } ?>

//-->
</script>

<?php } ?>