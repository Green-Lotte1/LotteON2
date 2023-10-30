// 테이블 조회
function tableReload(pg) {
    page = pg;
    let beginDate = $('input[name=begin]').val();
    let endDate = $('input[name=end]').val();
    const jsonData = {
        "begin" : beginDate,
        "end" : endDate,
        "pg" : pg
    }
    $.ajax({
      url: contextPath + '/my/orderList',
      type: 'GET',
      data: jsonData,
      success: function(data) {
        // 리스트
        const orderList = $('.orderList');
        // 첫 번째 <tr>를 제외한 모든 <tr> 초기화
        orderList.find('tr').slice(1).remove();
        for (let i = 0; i < data.dtoList.length; i++) {
            orderList.append(`
                <tr>
                    <td class="date">${data.dtoList[i].ordDate.substring(0, 10)}</td>
                    <td>
                        <a href="#" class="thumb">
                            <img src="${contextPath + '/thumbs/' + data.dtoList[i].product.thumb1}" alt="">
                        </a>
                        <ul class="w350">
                            <li class="company">${data.dtoList[i].product.company}</li>
                            <li class="prodName"><a href="#">${data.dtoList[i].product.prodName}</a></li>
                            <li>수량 : <span class="prodCount">${data.dtoList[i].count}</span>개 / 주문번호 : <span class="ordNo">${data.dtoList[i].ordNo}</span></li>
                            <li class="prodPrice">${(data.dtoList[i].count * data.dtoList[i].product.price).toLocaleString()}</li>
                        </ul>
                    </td>
                    <td class="status">${data.dtoList[i].statusString}</td>
                    <td class="confirm">
                        <a href="#" class="receive" onclick="receivePop($(this), ${data.dtoList[i].no}); return false;">수취확인</a>
                        <a href="#" class="review" onclick="reviewPop($(this), ${data.dtoList[i].product.prodNo}, ${data.dtoList[i].no}); return false;">상품평</a>
                        <a href="#" class="refund">반품신청</a>
                        <a href="#" class="exchange">교환신청</a>
                    </td>
                </tr>
            `);
        }
        // 페이징
        const page = $('.page');
        page.children().remove();

        if (data.prev) {
            page.append('<a href="#" class="prev" onclick="tableReload(data.start - 1); return false;">이전</a>');
        }

        for (let i = data.start; i <= data.end; i++) {
            page.append('<a href="#" class="num ' + (data.pg == i ? `on` : ``) + '" onclick="tableReload(' + i + '); return false;">' + i + '</a>');
        }

        if (data.next) {
            page.append('<a href="#" class="next" onclick="tableReload(data.end + 1); return false;">다음</a>');
        }
      }
  });
}

// 선택값에 따라 일자를 설정해준다.
function setDatePickerValue(date, str) {
  var year = date.getFullYear();
  var month = (date.getMonth() + 1).toString().padStart(2, '0'); // 월은 0부터 시작하므로 1을 더하고 2자리로 만듭니다.
  var day = date.getDate().toString().padStart(2, '0'); // 일자를 2자리로 만듭니다.

  var dateString = `${year}-${month}-${day}`;
  if (str == 'end') {
    $('input[name=end]').val(dateString);
  } else {
    $('input[name=begin]').val(dateString);
  }
}

// 년도와 월을 넘기면 해당 달의 시작일과 마지막일을 리턴한다.
function getFirstAndLastDate(year, month) {
  // 월을 0부터 11까지의 숫자로 변환 (0이 1월, 11이 12월을 의미)
  month = month - 1;

  // 다음 달의 첫 날을 계산
  var firstDay = new Date(year, month + 1, 1);

  // 현재 달의 마지막 날을 계산
  var lastDay = new Date(firstDay - 1);

  return {
    firstDate: new Date(year, month, 1),
    lastDate: lastDay
  };
}

// 1주일, 15일, 1개월 버튼 클릭 시 이벤트
function changeDate(element, str) {
    $('.datePick').removeClass('on');
    element.addClass('on');

    var today = new Date(); // 현재 날짜를 가져옵니다.
    var oneWeekAgo = new Date(today);
    oneWeekAgo.setDate(today.getDate() - 7); // 일주일 전의 날짜를 계산합니다.
    var fifteenDaysAgo = new Date(today);
    fifteenDaysAgo.setDate(today.getDate() - 15); // 15일 전의 날짜를 계산합니다.
    var oneMonthAgo = new Date(today);
    oneMonthAgo.setMonth(today.getMonth() - 1); // 한 달 전의 날짜를 계산합니다.
    if (str == 'week') {
        setDatePickerValue(oneWeekAgo, 'begin');
    } else if (str == 'half') {
        setDatePickerValue(fifteenDaysAgo, 'begin');
    } else if (str == 'month') {
        setDatePickerValue(oneMonthAgo, 'begin');
    }
    setDatePickerValue(today, 'end');
    tableReload(1);
}

// xx월 클릭 시 이벤트
function changeMonth(element, month, year) {
    $('.datePick').removeClass('on');
    element.addClass('on');

    const dates = getFirstAndLastDate(year, month);
    setDatePickerValue(dates.firstDate, 'begin');
    setDatePickerValue(dates.lastDate, 'end');
    tableReload(1);
}

// 리뷰 팝업
function reviewPop(element, prodNo, no) {
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
function receivePop(element, no) {
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
            tableReload(page);
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
            tableReload(page);
            $('.btnClose').closest('.popup').removeClass('on');
        }
    })
}

// 페이지 시작
$(function() {
    // 페이지 시작 시 오늘 이후의 날짜를 선택할 수 없도록 설정
    const beginInput = $('input[name=begin]');
    const endInput = $('input[name=end]');
    const todayString = new Date().toISOString().split('T')[0]; // 현재 날짜를 YYYY-MM-DD 형식으로 가져옵니다.
    beginInput.attr('max', todayString);
    endInput.attr('max', todayString);

    // 오늘 날짜를 기준으로 5개월 전까지의 달을 구한다.
    const date5 = $('.date_5ea');
    const today = new Date();

    var oneMonthAgo = new Date(today);
    oneMonthAgo.setMonth(today.getMonth() - 1);
    setDatePickerValue(oneMonthAgo, 'begin');
    setDatePickerValue(today, 'end');

    for (let i = 0; i < 5; i++) {
        let currentMonth = today.getMonth() - i + 1;
        let currentYear = today.getFullYear();

        if (currentMonth < 0) {
          currentMonth += 12;
          currentYear--;
        }
        date5.append('<li><a href="#" class="datePick" onclick="changeMonth($(this), ' + currentMonth + ', ' + currentYear +'); return false;"><em>' + currentMonth + '</em>월</a></li>')
    }

    // 테이블 조회
    tableReload(1);


    // 조회하기 버튼 클릭 시 리스트 조회 기능
    $('.btnConfirm').click(function() {
        tableReload(1);
    });

    // end가 begin보다 작을 경우 begin을 end와 동일한 값으로 변경
    $('input[name=end]').change(function() {
        const begin = $('input[name=begin]').val();
        const end = $('input[name=end]').val();

        if (begin > end) {
            $('input[name=begin]').val(end);
        }
    });

    // begin이 end보다 클 경우 end를 begin과 동일한 값으로 변경
    $('input[name=begin]').change(function() {
        const begin = $('input[name=begin]').val();
        const end = $('input[name=end]').val();

        if (begin > end) {
            $('input[name=end]').val(begin);
        }
    });
});