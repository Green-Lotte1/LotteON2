/**
 * 약관동의
 */
$(function(){
    $('.agree').click(function(e){

        const chk1 = $('input:checkbox[name="agree1"]');
        const chk2 = $('input:checkbox[name="agree2"]');
        const chk3 = $('input:checkbox[name="agree3"]');

        if (chk1.is(':checked') == false)
        {
            e.preventDefault();
            alert('사이트 이용약관에 동의를 하셔야 회원가입하실 수 있습니다.');
        }
        else if (chk2.is(':checked') == false)
        {
            e.preventDefault();
            alert('전자금융거래 이용약관에 동의를 하셔야 회원가입하실 수 있습니다.');
        }
        else if (chk3.is(':checked') == false)
        {
            e.preventDefault();
            alert('개인정보 수집동의에 동의를 하셔야 회원가입하실 수 있습니다.');
        }
    });

    // 체크박스 전체 선택
    $('input[name=agreeAll]').click(function(e){
        $(this).parents(".checkbox_group").find('input').prop("checked", $(this).is(":checked"));
    });

    // 체크박스 개별 선택
    $(".checkbox_group").on("click", ".checkAgree", function() {

        var is_checked = true;

        $(".checkbox_group .checkAgree").each(function(){
        is_checked = is_checked && $(this).is(":checked");
        });

        $("input[name=agreeAll]").prop("checked", is_checked);
    });
});