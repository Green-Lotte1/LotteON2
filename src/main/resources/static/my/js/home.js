
// 리뷰 팝업
function reviewPop(prodNo, no) {
    const popReview = $('#popReview');
    popReview.empty();

    let receiveFlag = false;
    // 리뷰 등록 여부 확인
    $.ajax({
          url: contextPath + '/product/checkReceive',
          type: 'GET',
          data: {"no" :no},
          async: false,
          success: function(data) {
              receiveFlag = data;
          }
    })
    if (!receiveFlag) {
        alert('구매확정을 먼저 해주세요.');
        return false;
    }

    let flag = false;
    // 리뷰 등록 여부 확인
    $.ajax({
          url: contextPath + '/product/checkReview',
          type: 'GET',
          data: {"prodNo" :prodNo},
          async: false,
          success: function(data) {
              flag = data;
          }
    })
    if (flag) {
        alert('이미 리뷰를 등록한 상품입니다.');
        return false;
    }

    $.ajax({
      url: contextPath + '/product/findProduct',
      type: 'GET',
      data: {"prodNo" :prodNo},
      success: function(data) {
        popReview.append(`
            <div>
                <nav>
                    <h1>상품평 작성하기</h1>
                    <button class="btnClose">X</button>
                </nav>
                <section>
                        <table>
                            <tr>
                                <th>상품명</th>
                                <td class="productName">${data.prodName}</td>
                            </tr>
                            <tr>
                                <th>만족도</th>
                                <td class="rating">
                                    <div class="my-rating"></div>
                                </td>
                            </tr>
                            <tr>
                                <th>내용</th>
                                <td class="review">
                                    <textarea name="review" placeholder="내용입력"></textarea>
                                </td>
                            </tr>
                        </table>
                        <p>
                            ※ 상품평을 작성하시면 구매확정 포인트와 더불어 추가 포인트를 지급합니다.
                        </p>

                        <div>
                            <button class="btnPositive" onclick="review(${prodNo})">작성완료</button>
                            <button class="btnNegative btnCancel">취소</button>
                        </div>
                </section>
            </div>
        `);

        // 팝업 닫기
        $('.btnClose').click(function(){
            $(this).closest('.popup').removeClass('on');
        });
        // 팝업 닫기
        $('.btnCancel').click(function(){
            $(this).closest('.popup').removeClass('on');
        });

        // 상품평 작성 레이팅바 기능
        $(".my-rating").starRating({
            starSize: 20,
            useFullStars: true,
            strokeWidth: 0,
            useGradient: false,
            minRating: 1,
            ratedColors: ['#ffa400', '#ffa400', '#ffa400', '#ffa400', '#ffa400'],
            callback: function(currentRating, $el){
                rating = currentRating;
                console.log('DOM element ', $el);
            }
        });
      }
    })
    popReview.addClass('on');
}

// 수취확인 팝업
function receivePop(no) {
    const popReceive = $('#popReceive');
    popReceive.empty();
    let flag = false;
    // 리뷰 등록 여부 확인
    $.ajax({
          url: contextPath + '/product/checkReceive',
          type: 'GET',
          data: {"no" :no},
          async: false,
          success: function(data) {
              flag = data;
          }
    })
    if (flag) {
        alert('이미 구매 확정한 상품입니다.');
        return false;
    }
    popReceive.append(`
        <div>
            <nav>
                <h1>수취확인</h1>
                <button class="btnClose">X</button>
            </nav>
            <section>
                <p>
                    상품을 잘 받으셨나요?<br>
                    상품을 받으셨으면 수취확인을 눌러 구매확정을 진행하세요.<br>
                    구매확정 후 포인트를 지급해 드립니다.
                </p>

                <div>
                    <button class="btnPositive btnConfirm" onclick="receive(${no})">확인</button>
                    <button class="btnNegative btnCancel">취소</button>
                </div>
            </section>
        </div>
    `);
    // 팝업 닫기
    $('.btnClose').click(function(){
        $(this).closest('.popup').removeClass('on');
    });
    // 팝업 닫기
    $('.btnCancel').click(function(){
        $(this).closest('.popup').removeClass('on');
    });
    popReceive.addClass('on');
}

// 리뷰
function review(prodNo) {
    let reviewText = $('textArea[name=review]').val();
    const jsonData = {
        "prodNo" : prodNo,
        "content" : reviewText,
        "rating" : rating
    }
    $.ajax({
        url: contextPath + '/product/orderReview',
        type: 'POST',
        data: jsonData,
        success: function(data) {
            alert("리뷰가 작성 되었습니다.")
            $('.btnClose').closest('.popup').removeClass('on');
        }
    })
}

// 수취확인
function receive(no) {
    $.ajax({
        url: contextPath + '/product/orderReceive',
        type: 'POST',
        data: {"no" :no},
        success: function(data) {
            if (data == 'success') {
                alert('구매확정 되었습니다.');
            } else {
                alert('오류가 발생 하였습니다. 잠시 후 다시 시도해주세요.');
            }
            $('.btnClose').closest('.popup').removeClass('on');
        }
    })
}
