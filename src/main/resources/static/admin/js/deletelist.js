$(function() {
    // 선택 삭제 버튼 클릭 시
    $('.list-delete').click(function(e) {
        e.preventDefault();
        if (confirm("선택한 상품을 삭제하시겠습니까?")) {
            $('#formCheck').submit();
        }
    });

    // 각 행의 "삭제" 링크 클릭 시
    $('.buttonDelete').click(function(e) {
        e.preventDefault();
        if (confirm("이 상품을 삭제하시겠습니까?")) {
            // 현재 행에서 체크박스의 값을 가져옵니다.
            var prodNo = $(this).closest('tr').find('input[name="chk"]').val();
            // 삭제 URL로 이동합니다.
            window.location.href = '/admin/product/delete?chk=' + prodNo;
        }
    });
});
