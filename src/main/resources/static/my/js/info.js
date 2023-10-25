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
        else // 입력이 빈칸일경우, 정상수정일경우
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
});