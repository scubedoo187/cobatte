<?php
if (!defined("_GNUBOARD_")) exit; // 개별 페이지 접근 불가
?>

<!-- 메뉴 시작 { -->
<link rel="stylesheet" href="<?php echo $groupmenu_skin_url ?>/style.css">

<div id="sir_lnb">
<?php for ($i=0; $i<count($menu); $i++) {  ?>
<aside id="lnb_cate">
<h2><? echo $menu[$i]['name'] ?></h2>
<div class="sir_ddl sir_ddl_pc" id="cate_2d">
<button class="ddl_btn" type="button"><? echo $menu[$i]['name'] ?></button>
</div>
<ul>
        <?php 
		for ($j=0; $j<count($menu[$i]['submenu']); $j++) {  
			$submenu = $menu[$i]['submenu'][$j];		
		?>
        <li<?php if($me_code==$submenu['code']) { echo " class=\"lnb_cate_on\""; } ?>><a href="<?php echo $submenu['link'] ?>" target="_<?php echo $submenu['target']; ?>" ><?php echo $submenu['name'] ?>
		<?php if($submenu['cnt']) { ?>
		<strong><?php echo $submenu['cnt'] ?></strong>
		<?php } ?>
		<?php if($submenu['total_cnt']) { ?>
		<span><?php echo $submenu['total_cnt'] ?></span>
		<?php } ?>
		</a>
		</li>
        <?php
		}  
		?>
</ul>    
 </aside>
<?php
}  
?>
</div><!-- #sir_lnb -->
<!-- } 메뉴 끝 -->