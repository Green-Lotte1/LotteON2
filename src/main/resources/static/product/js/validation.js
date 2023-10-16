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


$(function() {
	$('#ordForm').submit(function(){
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
		return true; // 폼 전송 시작
	});
});