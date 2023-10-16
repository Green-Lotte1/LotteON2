$(function() {
    // 전체 체크박스
    $('input[name=all]').change(function(){
        const isChecked = $(this).is(':checked');
        if (isChecked) {
            $('input[name=chk]').prop('checked', true);
            let count = 0;
            let price = 0;
            let disPrice = 0;
            let delivery = 0;
            let point = 0;
            let total = 0;
            const chk = $('input[name=chk]');
            for (let i = 0; i < chk.length; i++) {
                const cartNo = $('input[name=chk]')[i].classList[0];
                let tmpCount = $('input[name=count' + cartNo + ']').val() * 1;
                let tmpPrice = $('input[name=price' + cartNo + ']').val() * 1 * tmpCount;
                let tmpDisPrice = $('input[name=disPrice' + cartNo + ']').val() * 1 * tmpCount;
                let tmpDisPrice2 = tmpPrice - tmpDisPrice;
                let tmpDelivery = $('input[name=delivery' + cartNo + ']').val() * 1;
                let tmpPoint = $('input[name=point' + cartNo + ']').val() * 1;
                let tmpTotal = tmpDisPrice + tmpDelivery;

                count += tmpCount;
                price += tmpPrice;
                disPrice += tmpDisPrice2;
                delivery += tmpDelivery;
                point += tmpPoint;
                total += tmpTotal;
            }
            $('#count').text(count.toLocaleString());
            $('#price').text(price.toLocaleString());
            $('#disPrice').text(disPrice.toLocaleString());
            $('#delivery').text(delivery.toLocaleString());
            $('#point').text(point.toLocaleString());
            $('#total').text(total.toLocaleString());
        } else {
            $('input[name=chk]').prop('checked', false);
            $('.totalForm').text(0);
        }
    });

    $('input[name=chk]').change(function() {
        const chk = $('input[name=chk]');
        let cnt = 0;
        let count = 0;
        let price = 0;
        let disPrice = 0;
        let delivery = 0;
        let point = 0;
        let total = 0;
        for (let i = 0; i < chk.length; i++) {
            if (chk[i].checked) {
                const cartNo = $('input[name=chk]')[i].classList[0];
                let tmpCount = $('input[name=count' + cartNo + ']').val() * 1;
                let tmpPrice = $('input[name=price' + cartNo + ']').val() * 1 * tmpCount;
                let tmpDisPrice = $('input[name=disPrice' + cartNo + ']').val() * 1 * tmpCount;
                let tmpDisPrice2 = tmpPrice - tmpDisPrice;
                let tmpDelivery = $('input[name=delivery' + cartNo + ']').val() * 1;
                let tmpPoint = $('input[name=point' + cartNo + ']').val() * 1;
                let tmpTotal = tmpDisPrice + tmpDelivery;

                count += tmpCount;
                price += tmpPrice;
                disPrice += tmpDisPrice2;
                delivery += tmpDelivery;
                point += tmpPoint;
                total += tmpTotal;
                cnt++;
            }
        }
        $('#count').text(count.toLocaleString());
        $('#price').text(price.toLocaleString());
        $('#disPrice').text(disPrice.toLocaleString());
        $('#delivery').text(delivery.toLocaleString());
        $('#point').text(point.toLocaleString());
        $('#total').text(total.toLocaleString());
        if (!$(this).is(':checked')) {
            $('input[name=all]').prop('checked', false);
        } else if (cnt == chk.length) {
            $('input[name=all]').prop('checked', true);
        }
    });

    // 선택삭제
    $('.cartDelete').click(function(e) {
        e.preventDefault();
        if (!confirm('해당 장바구니 품목을 삭제 하시겠습니까?')) {
            return false;
        }
        $('#formCheck').attr("action", "/product/cartDelete");
        $('#formCheck').attr("method", "post");
        $('#formCheck').submit();
    });
});