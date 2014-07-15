<?php
if (!defined('_GNUBOARD_')) exit; // 개별 페이지 접근 불가
// add_stylesheet('css 구문', 출력순서); 숫자가 작을 수록 먼저 출력됨
add_stylesheet('<link rel="stylesheet" href="'.$member_skin_url.'/style.css">', 0);
?>
<? 
//////////설정 아주 중요!!!! 
$gr_id = "HUS";//그룹아이디를 이곳에 적으세요"; 
$bo_include_head = "_head.php";//"이곳에서 해더 파일명을 넣으세요"; 
$bo_include_tail = "_tail.php";//"이곳에서 푸터 파일명을 넣으세요"; 
$bo_skin = "img";//이곳에 스킨명 
$bo_table = $mb['mb_id'].; 
$bo_subject = $mb['mb_nick'].의 게시판";//자동 생성 게시판 제목 
?> 
<!-- 회원가입결과 시작 { -->
<div id="reg_result" class="mbskin">

    <p>
        <strong><?php echo $mb['mb_name'] ?></strong>님의 회원가입을 진심으로 축하합니다.<br>
    </p>

    <?php if ($config['cf_use_email_certify']) {  ?>
    <p>
        회원 가입 시 입력하신 이메일 주소로 인증메일이 발송되었습니다.<br>
        발송된 인증메일을 확인하신 후 인증처리를 하시면 사이트를 원활하게 이용하실 수 있습니다.
    </p>
    <div id="result_email">
        <span>아이디</span>
        <strong><?php echo $mb['mb_id'] ?></strong><br>
        <span>이메일 주소</span>
        <strong><?php echo $mb['mb_email'] ?></strong>
    </div>
    <p>
        이메일 주소를 잘못 입력하셨다면, 사이트 관리자에게 문의해주시기 바랍니다.
    </p>
    <?php }  ?>

    <p>
        회원님의 비밀번호는 아무도 알 수 없는 암호화 코드로 저장되므로 안심하셔도 좋습니다.<br>
        아이디, 비밀번호 분실시에는 회원가입시 입력하신 이메일 주소를 이용하여 찾을 수 있습니다.
    </p>

    <p>
        회원 탈퇴는 언제든지 가능하며 일정기간이 지난 후, 회원님의 정보는 삭제하고 있습니다.<br>
        감사합니다.
    </p>

    <div class="btn_confirm">
        <a href="<?php echo G5_URL ?>/bbs/board.php?bo_table=<?php echo $bo_table?>" class="btn02">나의 게시판으로</a>
    </div>

</div>

<!-- } 회원가입결과 끝 -->
<!-- } 회원 전용 게시판 생성 -->

<?


$board_path = G5_DATA_PATH.'/file/'.$bo_table;

// 게시판 디렉토리 생성
@mkdir($board_path, G5_DIR_PERMISSION);
@chmod($board_path, G5_DIR_PERMISSION);

// 디렉토리에 있는 파일의 목록을 보이지 않게 한다.
$file = $board_path . '/index.php';
$f = @fopen($file, 'w');
@fwrite($f, '');
@fclose($f);
@chmod($file, G5_FILE_PERMISSION);

// 분류에 & 나 = 는 사용이 불가하므로 2바이트로 바꾼다.
$src_char = array('&', '=');
$dst_char = array('＆', '〓');
$bo_category_list = str_replace($src_char, $dst_char, $bo_category_list);

$sql_common = " gr_id               = '$gr_id',
                bo_subject          = '$bo_subject',
                bo_mobile_subject   = '$bo_subject',
                bo_device           = 'both',
                bo_admin            = '$bo_table',
                bo_list_level       = '2',
                bo_read_level       = '2',
                bo_write_level      = '2',
                bo_reply_level      = '2',
                bo_comment_level    = '2',
                bo_html_level       = '2',
                bo_link_level       = '2',
                bo_count_modify     = '2',
                bo_count_delete     = '2',
                bo_upload_level     = '2',
                bo_download_level   = '2',
                bo_read_point       = '0',
                bo_write_point      = '0',
                bo_comment_point    = '0',
                bo_download_point   = '0',
                bo_use_category     = '0',
                bo_category_list    = '',
                bo_use_sideview     = '0',
                bo_use_file_content = '0',
                bo_use_secret       = '0',
                bo_use_dhtml_editor = '0',
                bo_use_rss_view     = '0',
                bo_use_good         = '0',
                bo_use_nogood       = '0',
                bo_use_name         = '0',
                bo_use_signature    = '0',
                bo_use_ip_view      = '0',
                bo_use_list_view    = '0',
                bo_use_list_file    = '0',
                bo_use_list_content = '0',
                bo_use_email        = '0',
                bo_use_cert         = '',
                bo_use_sns          = '',
                bo_table_width      = '99',
                bo_subject_len      = '60',
                bo_mobile_subject_len      = '30',
                bo_page_rows        = '15',
                bo_mobile_page_rows = '15',
                bo_new              = '24',
                bo_hot              = '100',
                bo_image_width      = '600',
                bo_skin             = '$bo_skin',
                bo_mobile_skin      = 'basic',
                bo_include_head     = '$bo_include_head',
                bo_include_tail     = '$bo_include_tail',
                bo_content_head     = '',
                bo_content_tail     = '',
                bo_mobile_content_head     = '',
                bo_insert_content   = '',
                bo_gallery_cols     = '4',
                bo_gallery_width    = '174',
                bo_gallery_height   = '124',
                bo_mobile_gallery_width = '125',
                bo_mobile_gallery_height= '100',
                bo_upload_count     = '2',
                bo_upload_size      = '204800',
                bo_reply_order      = '1',
                bo_use_search       = '1',
                bo_order            = '1',
                bo_write_min        = '0',
                bo_write_max        = '0',
                bo_comment_min      = '0',
                bo_comment_max      = '0',
                bo_sort_field       = '0',
                bo_1_subj           = '',
                bo_2_subj           = '',
                bo_3_subj           = '',
                bo_4_subj           = '',
                bo_5_subj           = '',
                bo_6_subj           = '',
                bo_7_subj           = '',
                bo_8_subj           = '',
                bo_9_subj           = '',
                bo_10_subj          = '',
                bo_1                = '',
                bo_2                = '',
                bo_3                = '',
                bo_4                = '',
                bo_5                = '',
                bo_6                = '',
                bo_7                = '',
                bo_8                = '',
                bo_9                = '',
                bo_10               = '' ";


    $row = sql_fetch(" select count(*) as cnt from {$g5['board_table']} where bo_table = '{$bo_table}' ");
    if ($row['cnt'])
        alert($bo_table.' 은(는) 이미 존재하는 TABLE 입니다.');

    $sql = " insert into {$g5['board_table']}
                set bo_table = '{$bo_table}',
                    bo_count_write = '0',
                    bo_count_comment = '0',
                    $sql_common ";
    sql_query($sql);

    // 게시판 테이블 생성
    $file = file(G5_ADMIN_PATH.'/sql_write.sql');
    $sql = implode($file, "\n");

    $create_table = $g5['write_prefix'] . $bo_table;

    // sql_board.sql 파일의 테이블명을 변환
    $source = array('/__TABLE_NAME__/', '/;/');
    $target = array($create_table, '');
    $sql = preg_replace($source, $target, $sql);
    sql_query($sql, FALSE);
?>
