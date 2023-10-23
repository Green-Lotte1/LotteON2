/**
 * 결제 확인
 */
// 포인트 사용 구현
function usePoint() {
	const savePoint = $('input[name=savePoint]');
	const point = savePoint.val() * 1;
	const ordTotPrice = $('input[name=ordTotPrice]');
	let TotPrice = ordTotPrice.val() * 1;
	const usedPoint = $('input[name=usedPoint]').val() * 1;
	TotPrice = TotPrice + point - usedPoint;
	savePoint.val(usedPoint);
	$('#point').text(usedPoint.toLocaleString());
	$('#total').text(TotPrice.toLocaleString());
	ordTotPrice.val(TotPrice);
}
$(function() {
	// 수령인
	$('input[name=recipName]').focusout(function() {
		const recipName = $(this).val();
		if (recipName == null) {
			isRecipNameOk = false;
		} else {
			isRecipNameOk = true;
		}
	})
	// 휴대폰번호 입력데이터
	$('input[name=recipHp]').focusout(function() {
		const hp = $(this).val();
		// 휴대폰번호 입력값 검사
		if (!hp.match(reHp)) {
			$('.msgHp').css('color', 'red').text('유효한 휴대폰번호가 아닙니다.');
			isRecipHpOk = false;
			return;
		} else {
			$('.msgHp').css('color', 'green').text('');
			isRecipHpOk = true;
			return;
		}
	});
	// 우편번호
	$('input[name=recipZip]').change(function() {
		const recipZip = $(this).val();
		if (recipZip == null) {
			isRecipZipOk = false;
		} else {
			isRecipZipOk = true;
		}
	})
	// 기본주소
	$('input[name=recipAddr1]').change(function() {
		const recipAddr1 = $(this).val();
		if (recipAddr1 == null) {
			isRecipAddr1Ok = false;
		} else {
			isRecipAddr1Ok = true;
		}
	})
	// 상세주소
	$('input[name=recipAddr2]').change(function() {
		const recipAddr2 = $(this).val();
		if (recipAddr2 == null) {
			isRecipAddr2Ok = false;
		} else {
			isRecipAddr2Ok = true;
		}
	})
	$('input[name=ordPayment]').change(function() {
		isOrdPaymentOk = true;
	})
	$('input[name=usedPoint]').change(function() {
		const usedPoint = $(this).val();
		const memberPoint = $('input[name=memberPoint]').val() * 1;
		if (usedPoint > memberPoint) {
			$(this).val(memberPoint);
		}
		if (usedPoint >= 5000) {
		    usePoint();
		}
	})
});