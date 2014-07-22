<?php
if (!defined('_GNUBOARD_')) exit; // 개별 페이지 접근 불가

// add_stylesheet('css 구문', 출력순서); 숫자가 작을 수록 먼저 출력됨
if ($page==1) // appending 시에는 제외처리.
	add_stylesheet('<link rel="stylesheet" href="'.$latest_skin_url.'/style.css">', 0);
?>

<?php if ($page==1) { // appending 시에는 제외처리.?>
<section class="stylebox">
	<h4 class="tsubject"><?php echo $bo_subject ?></h4>
	<a href="<?php echo G5_BBS_URL ?>/board.php?bo_table=<?php echo $bo_table ?>" class="gopage"><span class="sound_only"><?php echo $bo_subject ?></span>더보기<span class="wzicon more_page"></span></a>
	<ul id="datalist_<?php echo $bo_table;?>">
	<?php } ?>
		
		<?php for ($i=0; $i<count($list); $i++) { 
			
			$aclass = "";
			if ($i == ($rows - 1)) { 
				$aclass = "last";
			}
			?>
			<li class="<?php if ($list[$i]["wr_comment"] > 0) {?>havecm<?php } ?>">
				<a href="<?php echo $list[$i]['href'];?>" class="list">
					<span class="wsubject">
					<?php
					echo ($list[$i]['is_notice'] ? "<strong>".$list[$i]['subject']."</strong>" : $list[$i]['subject']);

					// if ($list[$i]['link']['count']) { echo "[{$list[$i]['link']['count']}]"; }
					// if ($list[$i]['file']['count']) { echo "<{$list[$i]['file']['count']}>"; }
					if (isset($list[$i]['icon_new']))    echo " " . preg_replace("/(\<img )([^\>]*)(\>)/i", "\\1 style='position:relative;top:2px;margin-left:2px;' \\2 \\3", $list[$i]['icon_new']);
					if (isset($list[$i]['icon_hot']))    echo " " . preg_replace("/(\<img )([^\>]*)(\>)/i", "\\1 style='position:relative;top:2px;margin-left:2px;' \\2 \\3", $list[$i]['icon_hot']);
					if (isset($list[$i]['icon_file']))   echo " " . preg_replace("/(\<img )([^\>]*)(\>)/i", "\\1 style='position:relative;top:2px;margin-left:2px;' \\2 \\3", $list[$i]['icon_file']);
					if (isset($list[$i]['icon_link']))   echo " " . $list[$i]['icon_link'];
					if (isset($list[$i]['icon_secret'])) echo " " . $list[$i]['icon_secret'];
					?>
					</span>
					<span class="wname"><?php echo $list[$i]["wr_name"]; ?></span>
					<?php if ($list[$i]["wr_comment"] > 0) {?><span class="cment"><span class="cbox"><?=$list[$i]["wr_comment"]?></span></span><?php }?>
				</a>
			</li>

		<?php } ?>

	<?php if ($page==1) { // appending 시에는 제외처리.?>
	</ul>
	<nav class="bbn">
		<a href="#none" class="footer_box" id="datalist_<?php echo $bo_table;?>-ajaxbtnmore"><span class="sound_only"><?php echo $bo_subject ?></span>더보기<span class="wzicon more_down"></span></a>
		<a href="#" class="gotop"><span class="wzicon more_up"></span>맨위로</a>
	</nav>
</section>
<?php } ?>

<?php if ($page==1) { // appending 시에는 제외처리.?>
<script type="text/javascript">
	jQuery(document).ready(function () {
		var page_<?php echo $bo_table;?> = 2;
		jQuery('.stylebox #datalist_<?php echo $bo_table;?>-ajaxbtnmore').click(function() {
			jQuery.ajax({
				url: "<?php echo $latest_skin_url?>/latest.skin.append.php",
				type: "post",
				data: {
					"page": page_<?php echo $bo_table;?>,
					"bo_table": "<?php echo $bo_table?>",
					"rows": "<?php echo $rows?>",
					"subject_len": "<?php echo $subject_len?>"
				},
				dataType: "html",
				async: true,
				cache: false,
				contentType: "application/x-www-form-urlencoded; charset=utf-8" ,
				beforeSend : function() {
					jQuery('.stylebox #datalist_<?php echo $bo_table;?>-ajaxbtnmore').html("<img src='<?php echo $latest_skin_url?>/img/spinner.gif' />");
				},
				error : function(request, status, error) {
					alert("code : " + request.status + "\r\nmessage : " + request.reponseText+ "\r\error : " + request.error);
				},
				success: function(result) {
					jQuery('#datalist_<?php echo $bo_table;?>').append(result);
					if (jQuery.trim(result) == "") {
						jQuery('.stylebox #datalist_<?php echo $bo_table;?>-ajaxbtnmore').html("");
					}
					else {
						jQuery('.stylebox #datalist_<?php echo $bo_table;?>-ajaxbtnmore').html("더보기<span class='wzicon more_down'></span>");
					}
					page_<?php echo $bo_table;?>++;
				}
			});
		});
	});
</script>
<?php } ?>

