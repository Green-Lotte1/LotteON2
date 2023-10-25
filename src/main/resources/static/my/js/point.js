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
      url: contextPath + '/my/pointList',
      type: 'GET',
      data: jsonData,
      success: function(data) {
        // 리스트
        const pointList = $('.pointList');
        // 첫 번째 <tr>를 제외한 모든 <tr> 초기화
        pointList.find('tr').slice(1).remove();
        for (let i = 0; i < data.dtoList.length; i++) {
            pointList.append(`
                <tr>
                    <td class="date">${data.dtoList[i].pointDate.substring(0, 10)}</td>
                    <td class="ordNo">${data.dtoList[i].ordNo}</td>
                    <td class="point">${data.dtoList[i].point}</td>
                    <td class="desc">${data.dtoList[i].descript}</td>
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