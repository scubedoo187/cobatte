<?php
if (!defined("_GNUBOARD_")) exit; // 개별 페이지 접근 불가 
?>

<link rel="stylesheet" href="<?php echo $new_skin_url ?>/style.css">

<!-- 전체게시물 목록 시작 { -->
<div class="tbl_head01 tbl_wrap">
    <table>
    <thead>
    <tr>
        <th scope="col">그룹</th>
        <th scope="col">게시판</th>
        <th scope="col">제목</th>
        <th scope="col">이름</th>
        <th scope="col">일시</th>
    </tr>
    </thead>
    <tbody>
    <?php
    for ($i=0; $i<count($list); $i++) 
    {
        $gr_subject = cut_str($list[$i]['gr_subject'], 20);
        $bo_subject = cut_str($list[$i]['bo_subject'], 20);
        $wr_subject = get_text(cut_str($list[$i]['wr_subject'], 80));
    ?>
    <tr>
        <td class="td_group"><?php echo $gr_subject ?></td>
        <td class="td_board"><a href="./board.php?bo_table=<?php echo $list[$i]['bo_table'] ?>" target="_blank"><?php echo $bo_subject ?></a></td>
        <td><a href="<?php echo $list[$i]['href'] ?>" target="_blank"><?php echo $list[$i]['comment'] ?><?php echo $wr_subject ?></a></td>
        <td class="td_name"><div><?php echo $list[$i]['name'] ?></div></td>
        <td class="td_date"><?php echo $list[$i]['datetime2'] ?></td>
    </tr>
    <?php }  ?>

    <?php if ($i == 0)
        echo "<tr><td colspan=\"5\" class=\"empty_table\">게시물이 없습니다.</td></tr>";
    ?>
    </tbody>
    </table>
</div>

<?php echo $write_pages ?>
<!-- } 전체게시물 목록 끝 -->