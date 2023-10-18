/**
 * 사용자 입력데이터 유효성검증
 */

// 폼 데이터 검증결과 상태변수
let isUidOk       = false;
let isPassOk      = false;
let isNameOk      = false;
let isEmailOk     = false;
let isHpOk        = false;
let isCompanyOk   = false;
let isBizRegNumOk = false;
let isComRegNumOk = false;
let isTelOk       = false;
let isFaxOk       = false;

// 데이터 검증에 사용하는 정규표현식
const reUid     = /^[A-Za-z0-9]{4,12}$/;
const rePass    = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,12}$/;
const reName    = /^[가-힣]{2,10}$/
const reEmail   = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const reHp      = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;
const reCompany = /^\(주\)+[a-zA-Z가-힣0-9]{1,12}$/;
const reBizRegNum = /^[0-9]{3}-[0-9]{2}-[0-9]{5}$/;
const reComRegNum = /^[0-9]{4}-[가-힣]{2,6}-[0-9]{4}$/;
const reTel       = /^(0[2-8][0-5]?)-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})$/;
const reFax       = /^\d{2,3}-\d{3,4}-\d{4}$/;

// 유효성검증 (Validation)
$(function(){

    // 아이디 유효성검증 후 다시 포커스해서 수정 방지
    $('input[name=uid]').keydown(function(){

        $('.msgId').css('color', 'black').text('영문, 숫자로 4~12자까지 설정해 주세요.');
        isUidOk = false;
    });

    // 비밀번호 입력값 검사
    $('input[name=pass2]').focusout(function(){

        const pass1 = $('input[name=pass1]').val();
        const pass2 = $('input[name=pass2]').val();

        if (pass1 == pass2)
        {
            if (pass1.match(rePass))
            {
                $('.msgPass2').css('color', 'green').text('비밀번호가 일치합니다.');
                isPassOk = true;
            }
            else
            {
                $('.msgPass2').css('color', 'red').text('비밀번호는 영문, 숫자, 특수문자 조합 8~12자리여야합니다.');
                isPassOk = false;
            }
        }
        else
        {
            $('.msgPass2').css('color', 'red').text('비밀번호가 일치하지 않습니다.');
            isPassOk = false;
        }
    });

    // 이름 입력값 검사
    $('input[name=name]').focusout(function(){

        const name = $(this).val();

        if (!name.match(reName))
        {
            $('.resultName').css('color', 'red').text('이름 입력은 한글로만 가능합니다.');
            isNameOk = false;
        }
        else
        {
            $('.resultName').text('');
            isNameOk = true;
        }
    });

    // 판매자 회원가입 시 담당자 휴대폰번호 입력값 검사
    $('input[name=managerHp]').focusout(function(){

        const hp  = $(this).val();

        // 휴대폰번호 입력값 검사
        if(!hp.match(reHp))
        {
            $('.msgHp').css('color', 'red').text('유효한 휴대폰번호가 아닙니다.');
            isHpOk = false;
        }
        else
        {
            $('.msgHp').text('');
            isHpOk = true;
        }
    });

    // 일반회원 회원가입 최종 전송
    $('#formMember').submit(function(){

        if (!isUidOk)
        {
            alert('아이디를 확인하십시오.');
            return false; // 폼 전송 취소
        }
        if (!isPassOk)
        {
            alert('비밀번호를 확인하십시오.');
            return false; // 폼 전송 취소
        }
        if (!isNameOk)
        {
            alert('이름을 확인하십시오.');
            return false; // 폼 전송 취소
        }
        if (!isEmailOk)
        {
            alert('이메일을 확인하십시오.');
            return false; // 폼 전송 취소
        }
        if (!isHpOk)
        {
            alert('전화번호를 확인하십시오.');
            return false; // 폼 전송 취소
        }
        isCompanyOk   = true;
        isBizRegNumOk = true;
        isComRegNumOk = true;
        isTelOk       = true;
        isFaxOk       = true;

        return true; // 폼 전송 시작
    });

    // 판매자 회원가입 최종 전송
    $('#formSeller').submit(function(){

        if (!isUidOk)
        {
            alert('아이디를 확인하십시오.');
            return false; // 폼 전송 취소
        }
        if (!isPassOk)
        {
            alert('비밀번호를 확인하십시오.');
            return false; // 폼 전송 취소
        }
        if (!isEmailOk)
        {
            alert('이메일을 확인하십시오.');
            return false; // 폼 전송 취소
        }
        if (!isCompanyOk)
        {
            alert('회사명을 확인하십시오.');
            return false; // 폼 전송 취소
        }
        if (!isBizRegNumOk)
        {
            alert('사업자등록번호를 확인하십시오.');
            return false; // 폼 전송 취소
        }
        if (!isComRegNumOk)
        {
            alert('통신판매업 신고번호를 확인하십시오.');
            return false; // 폼 전송 취소
        }
        if (!isTelOk)
        {
            alert('전화번호를 확인하십시오.');
            return false; // 폼 전송 취소
        }
        if (!isFaxOk)
        {
            alert('팩스번호를 확인하십시오.');
            return false; // 폼 전송 취소
        }

        return true; // 폼 전송 시작
    });
});