<?php
include_once('./_common.php');

if (!$member['mb_id'])
    alert('회원만 접근하실 수 있습니다.');

if ($is_admin == 'super')
    alert('최고 관리자는 탈퇴할 수 없습니다');

if (!($_POST['mb_password'] && $member['mb_password'] == sql_password($_POST['mb_password'])))
    alert('비밀번호가 틀립니다.');

// 회원탈퇴일을 저장
$date = date("Ymd");
$sql = " update {$g5['member_table']} set mb_leave_date = '{$date}' where mb_id = '{$member['mb_id']}' ";
sql_query($sql);

// 3.09 수정 (로그아웃)
session_unregister("ss_mb_id");

if (!$url)
    $url = G5_URL;

alert(''.$member['mb_nick'].'님께서는 '. date("Y년 m월 d일") .'에 회원에서 탈퇴 하셨습니다.', $url);
?>
