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

    // 비밀번호 변경 팝업창
    $('.btnComplete').click(function(e){

        const uid = $('input[name=uid]').val();
        const inputPass = $('input[name=pass1]').val();

        if (!isPassOk)
        {
            alert('비밀번호를 확인하십시오.');
            return false;
        }
        else
        {
            $.ajax({
                url: contextPath + '/my/formMyinfoPassChange',
                type: 'POST',
                data: {
                    uid: uid,
                    inputPass: inputPass
                },
                success: function(data) {
                    if (data === "success") {
                        alert('비밀번호가 변경되었습니다.');
                        $('#popPassChange').closest('.popup').removeClass('on');
                    } else {
                        alert("error");
                    }
                }
            });
        }
    });

    // 날짜 포맷 설정
    let date = new Date(rdateData);
    let year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();
    let convertedData = year+"년 "+month+"월 "+day+"일";
    $('.rdateConvertedData').text(convertedData);

    // 탈퇴하기
    $('#btnWithdraw').click(function(e){

        let result = confirm("회원 탈퇴를 희망하시는게 맞나요?");

        if (result) {
            $('#popWithdraw').addClass('on'); // 비밀번호 입력 팝업

            $('.btnPopWithdraw').click(function(e){

                const uid = $('input[name=uid]').val();
                const inputPass = $('input[name=passCheck]').val();

                $.ajax({
                    url: contextPath + '/my/withdraw',
                    type: 'POST',
                    data: {
                        uid: uid,
                        inputPass: inputPass
                    },
                    success: function(data) {
                        if (data === "success") {
                            alert('탈퇴가 완료되었습니다.');
                            window.location.href=contextPath + "/member/logout";
                        } else {
                            alert("비밀번호가 일치하지 않습니다.");
                        }
                    }
                });
            });
        }
    });

    // 회원정보 수정 완료
    $('#btnWithdrawFinal').click(function(e){

        let result = confirm("회원정보를 수정하시겠습니까?");

        if (result) {
            const email = $('input[name=email]').val();
            const hp = $('input[name=hp]').val();
            const zip = $('#zip').val();
            const addr1 = $('#addr1').val();
            const addr2 = $('#addr2').val();
            console.log("최종버튼 데이터 : "+email+", "+hp+", "+zip+", "+addr1+", "+addr2);

            let data = {
                uid: uid,
                email: email,
                hp: hp,
                zip: zip,
                addr1: addr1,
                addr2: addr2
            };
            let jsonData = JSON.stringify(data);

            $.ajax({
                url: contextPath + '/my/withdrawFinal',
                type: 'POST',
                data: jsonData,
                contentType: 'application/json',
                success: function(data) {
                    if (data === "success") {
                        alert('회원정보 수정이 완료되었습니다.');
                        window.location.href=contextPath + "/my/";
                    } else {
                        alert("error : 새로고침 후 다시 시도해주세요.");
                    }
                }
            });
        }
    });
});