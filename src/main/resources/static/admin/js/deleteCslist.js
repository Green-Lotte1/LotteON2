$(function() {

    $('.cslist-delete').click(function(e) {
        e.preventDefault();
        if (confirm("선택한 항목을 삭제하시겠습니까?")) {
            var selectedCheckboxes = $('input[name="chk"]:checked');
            var selectedValues = selectedCheckboxes.map(function() {
                return $(this).val();
            }).get();
            $('#selectedBnos').val(selectedValues);

            $('.csForm').submit();
        }
    });

    $('.csbuttonDelete').click(function(e) {
        e.preventDefault();
        if (confirm("이 항목을 삭제하시겠습니까?")) {
            var prodNo = $(this).closest('tr').find('input[name="chk"]').val();
            $('#selectedBnos').val([bno]);
            $('#formCheck').submit();
        }
    });
});
