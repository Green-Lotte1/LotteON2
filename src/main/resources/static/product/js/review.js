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
