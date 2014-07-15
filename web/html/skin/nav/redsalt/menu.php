<?
if (!defined('_GNUBOARD_')) exit; // 개별 페이지 접근 불가
?>
<link rel="stylesheet" href="<?php echo G5_URL?>/skin/nav/redsalt/style_redsalt.css" type="text/css" />


<script type="text/javascript">

//네비게이션 레이아웃 적용
 $(document).ready(function(){
	$("nav.nav a").mouseenter(function() {
			if($("div#subBox").css("display") != "block"){
				$("div#subBox").slideDown();
				$("ul.depth2").slideDown();
			}
	});
		    	$("div.navBox").mouseleave(function() {
					$("div#subBox").hide();
					$("ul.depth2").hide();
		   });
	})
	

// 네비게이션 서브메뉴 배경 세로사이즈 적용
$(document).ready(function(){
	var heights = $("ul.depth2").map(function ()
    {
        return $(this).height();
    }).get(),

    maxHeight = Math.max.apply(null, heights);
	
	$("div.subBox").css('height', maxHeight + 30);

});

</script>

<div class="navi">
	<div class="navBox">
        <div id="subBox" class="subBox"></div>
        <div class="navi">
        	<nav class="nav">
        	<ul class="depth1">
            <?php
            $sql = " select *
                        from {$g5['menu_table']}
                        where me_use = '1'
                          and length(me_code) = '2'
                        order by me_order, me_id ";
            $result = sql_query($sql, false);
            $gnb_zindex = 999; // gnb_1dli z-index 값 설정용

            for ($i=0; $row=sql_fetch_array($result); $i++) {
            ?>
            <li class="gnb1">
                <a href="<?php echo $row['me_link']; ?>" target="_<?php echo $row['me_target']; ?>" class="nav_1da"><?php echo $row['me_name'] ?></a>
                <?php
                $sql2 = " select *
                            from {$g5['menu_table']}
                            where me_use = '1'
                              and length(me_code) = '4'
                              and substring(me_code, 1, 2) = '{$row['me_code']}'
                            order by me_order, me_id ";
                $result2 = sql_query($sql2);

                for ($k=0; $row2=sql_fetch_array($result2); $k++) {
                    if($k == 0)
                        echo '<ul class="depth2">'.PHP_EOL;
                ?>
                    <li id="nav_2da"><a href="<?php echo $row2['me_link']; ?>" target="_<?php echo $row2['me_target']; ?>" class="nav_2da"><?php echo $row2['me_name'] ?></a></li>
                <?php
                }

                if($k > 0)
                    echo '</ul>'.PHP_EOL;
                ?>
            </li>
            <?php
            }

            if ($i == 0) {  ?>
                <li id="gnb_empty">메뉴 준비 중입니다.<?php if ($is_admin) { ?> <br><a href="<?php echo G5_ADMIN_URL; ?>/menu_list.php">관리자모드 &gt; 환경설정 &gt; 메뉴설정</a>에서 설정하실 수 있습니다.<?php } ?></li>
            <?php } ?>
        	</ul>
      	</nav>
      </div>
    </div>
</div>