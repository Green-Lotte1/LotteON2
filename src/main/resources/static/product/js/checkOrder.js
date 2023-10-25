/**
 * 결제 확인
 */
// 포인트 사용 구현
function usePoint() {
	const savePoint = $('input[name=savePoint]');
	const point = savePoint.val() * 1;
	const ordTotPrice = $('input[name=ordTotPrice]');
	let TotPrice = ordTotPrice.val() * 1;
	let usedPoint = $('input[name=usedPoint]').val() * 1;
	if (TotPrice + point < usedPoint) {
	    usedPoint = TotPrice + point;
	    $('input[name=usedPoint]').val(usedPoint);
	}
	TotPrice = TotPrice + point - usedPoint;
	savePoint.val(usedPoint);
	$('#point').text(usedPoint.toLocaleString());
	$('#total').text(TotPrice.toLocaleString());
	ordTotPrice.val(TotPrice);
}

// 최종결제 정보 초기화
function finalFormClear() {
    const savePoint = $('input[name=savePoint]');
    const ordTotPrice = $('input[name=ordTotPrice]');
    const couponInput = $('input[name=coupon]');
    let usedPoint = $('input[name=usedPoint]').val(0);
    savePoint.val(0);
    $('#point').text(0);
    $('#total').text(finalFormTot.toLocaleString());
    ordTotPrice.val(finalFormTot);
    $('#coupon').text(0);
    couponInput.val(0);
}

// 쿠폰 정보 적용
function applyCoupon() {
    let discountMoney = 0;
    // 단순 금액 계산일 경우와 퍼센트 계산일 경우를 구분
    if (couponDetail.discountType == 'CASH') {
        discountMoney = couponDetail.discountMoney;
    } else if (couponDetail.discountType == 'PERCENT') {
        discountMoney = finalFormTot * (couponDetail.discountPercent / 100);

        // discountMoney를 100의 자리 미만으로 내림
        discountMoney = Math.floor(discountMoney);
        // 100의 자리 미만 값 계산
        let remainder = discountMoney % 100;

        // 할인된 금액에서 100의 자리 미만을 뺀 후 100의 자리로 올림
        discountMoney -= remainder;
        discountMoney = Math.ceil(discountMoney / 100) * 100;
    }
    const ordTotPrice = $('input[name=ordTotPrice]');
    const couponInput = $('input[name=coupon]');
    const newTotal = finalFormTot - discountMoney;
    $('#total').text(newTotal.toLocaleString());
    $('#coupon').text(discountMoney.toLocaleString());
    ordTotPrice.val(newTotal);
    couponInput.val(discountMoney);
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
	});
	// 기본주소
	$('input[name=recipAddr1]').change(function() {
		const recipAddr1 = $(this).val();
		if (recipAddr1 == null) {
			isRecipAddr1Ok = false;
		} else {
			isRecipAddr1Ok = true;
		}
	});
	// 상세주소
	$('input[name=recipAddr2]').change(function() {
		const recipAddr2 = $(this).val();
		if (recipAddr2 == null) {
			isRecipAddr2Ok = false;
		} else {
			isRecipAddr2Ok = true;
		}
	});
	$('input[name=ordPayment]').change(function() {
		isOrdPaymentOk = true;
	});
	$('input[name=usedPoint]').change(function() {
		let usedPoint = $(this).val();
		const memberPoint = $('input[name=memberPoint]').val() * 1;
		if (usedPoint > memberPoint) {
			$(this).val(memberPoint);
		}
		if (usedPoint < 5000) {
		    $(this).val(0);
		}
        usePoint();
	});

    // 쿠폰 가져오기
    $.ajax({
          url: contextPath + '/my/couponList',
          type: 'GET',
          data: {},
          async: false,
          success: function(data) {
            // 전체 주문 금액을 가져온다.
            const ordTotPrice = $('input[name=ordTotPrice]');
            let TotPrice = ordTotPrice.val() * 1;
            for (let i = 0; i < data.length; i++) {
                const couponDTO = data[i];
                // 쿠폰의 사용가능 금액보다 전체 주문금액이 클때만 해당 쿠폰을 표시해준다.
                if (TotPrice >= couponDTO.discountLimit) {
                    couponList.push(couponDTO);
                    $('#couponList').append(
                        $('<option>',{
                          value: couponDTO.couponSeq,
                          text: couponDTO.couponName,
                        })
                    );
                }
            }
          }
      })

	// 쿠폰 선택
	$('.coupon').change(function() {
	    couponSeq = this.value;
	    for (let i = 0; i < couponList.length; i++) {
	        if (couponList[i].couponSeq == couponSeq) {
	            couponDetail = couponList[i];
	        }
	    }
	    // 일단 최종 결제 정보를 전부 초기화
	    finalFormClear();

        if (couponSeq != 0) {
            // 쿠폰 정보를 적용
            applyCoupon();
        }
	})
});