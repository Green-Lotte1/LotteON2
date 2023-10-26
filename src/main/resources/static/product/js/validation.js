/**
 * 결제 유효성 검사
 */

let isRecipNameOk = false;
let isRecipHpOk = false;
let isRecipZipOk = false;
let isRecipAddr1Ok = false;
let isRecipAddr2Ok = false;
let isOrdPaymentOk = false;

const reHp = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

function checkValidation() {
    // 수령인
    const recipName = $('input[name=ordPayment]').val();
    if (recipName == null) {
        isRecipNameOk = false;
    } else {
        isRecipNameOk = true;
    }
    // 휴대폰번호 입력데이터
    const hp = $('input[name=recipHp]').val();
    // 휴대폰번호 입력값 검사
    if (!hp.match(reHp)) {
        $('.msgHp').css('color', 'red').text('유효한 휴대폰번호가 아닙니다.');
        isRecipHpOk = false;
    } else {
        $('.msgHp').css('color', 'green').text('');
        isRecipHpOk = true;
    }
    // 우편번호
    const recipZip = $('input[name=recipZip]').val();
    if (recipZip == null) {
        isRecipZipOk = false;
    } else {
        isRecipZipOk = true;
    }
    // 기본주소
    const recipAddr1 = $('input[name=recipAddr1]').val();
    if (recipAddr1 == null) {
        isRecipAddr1Ok = false;
    } else {
        isRecipAddr1Ok = true;
    }
    // 상세주소
    const recipAddr2 = $('input[name=recipAddr2]').val();
    if (recipAddr2 == null) {
        isRecipAddr2Ok = false;
    } else {
        isRecipAddr2Ok = true;
    }
    if ($('input[name=ordPayment]:checked').length > 0) {
        isOrdPaymentOk = true;
    } else {
        isOrdPaymentOk = false;
    }
}

function formCancle() {
    if (!isRecipNameOk){
        alert('수령인을 확인하십시오.');
        return false; // 폼 전송 취소
    }
    if (!isRecipHpOk){
        alert('휴대폰을 확인하십시오.');
        return false; // 폼 전송 취소
    }
    if (!isRecipZipOk){
        alert('우편번호를 확인하십시오.');
        return false; // 폼 전송 취소
    }
    if (!isRecipAddr1Ok){
        alert('기본주소 확인하십시오.');
        return false; // 폼 전송 취소
    }
    if (!isRecipAddr2Ok){
        alert('상세주소를 확인하십시오.');
        return false; // 폼 전송 취소
    }
    if (!isOrdPaymentOk){
        alert('결제방법을 확인하십시오.');
        return false; // 폼 전송 취소
    }
    return true;
}