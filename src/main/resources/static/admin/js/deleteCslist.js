$(function() {

    // 선택 삭제 버튼 클릭 시
    $('.cslist-delete').click(function(e) {
        e.preventDefault();
        if (confirm("선택한 항목을 삭제하시겠습니까?")) {
            // 선택된 모든 체크박스를 찾아서 숨겨진 입력 필드의 값으로 설정합니다
            var selectedCheckboxes = $('input[name="chk"]:checked');
            var selectedValues = selectedCheckboxes.map(function() {
                return $(this).val();
            }).get();
            $('#selectedBnos').val(selectedValues);

            // 양식을 제출합니다
            $('#formCheck').submit();
        }
    });

    // 각 행의 "삭제" 링크 클릭 시
    $('.csbuttonDelete').click(function(e) {
        e.preventDefault();
        if (confirm("이 항목을 삭제하시겠습니까?")) {
            // 같은 행의 체크박스에서 값을 가져옵니다
            var prodNo = $(this).closest('tr').find('input[name="chk"]').val();
            // 숨겨진 입력 필드의 값을 설정하고 양식을 제출합니다
            $('#selectedBnos').val([bno]);
            $('#formCheck').submit();
        }
    });
});
