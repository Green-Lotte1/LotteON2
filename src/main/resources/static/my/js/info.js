$(function() {

    // infoAccessCheck 비밀번호 확인
    $('.btnPassCheck').click(function(e){
        e.preventDefault();
        const inputPass = $('input[name=pass1]').val();
        console.log(inputPass);

        // 컨트롤러에서 비밀번호 일치여부를 판단해서
        // true를 반환하면 페이지 이동, false를 반환하면 alert
        $.ajax({
            url: contextPath + '/my/infoAccessCheck',
            type: 'POST',
            data: {
                uid: uid,
                inputPass: inputPass
            },
            success: function(data) {
                if (data === "true") {
                    window.location.href=contextPath + "/my/info";
                } else {
                    alert("비밀번호가 일치하지 않습니다.");
                }
            }
        });
    });

    $('.btnComplete').click(function(e){



        $(this).closest('.popup').removeClass('on');
    });
});