$(function() {
    // 전체 체크박스
    $('#selectAll').change(function(){
        const isChecked = $(this).is(':checked');
        if (isChecked) {
            $('input[name=chk]').prop('checked', true);
        } else {
            $('input[name=chk]').prop('checked', false);
        }
    });

    $('input[name=chk]').change(function() {
        const chk = $('input[name=chk]');
        let cnt = 0;
        for (let i = 0; i < chk.length; i++) {
            if (chk[i].checked) {
                cnt++;
            }
        }
        if (!$(this).is(':checked')) {
            $('#selectAll').prop('checked', false);
        } else if (cnt === chk.length) {
            $('#selectAll').prop('checked', true);
        }
    });
});
