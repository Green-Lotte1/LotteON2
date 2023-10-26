// 테이블 조회
function listReload(pg) {
    page = pg;

    const jsonData = {
        "pg" : pg
    }
    $.ajax({
      url: contextPath + '/product/productReview',
      type: 'GET',
      data: {'prodNo' : prodNo, 'jsonData' : JSON.stringify(jsonData)},
      success: function(data) {
        // 리스트
        const prodReviewList = $('.prodReviewList');
        // 첫 번째 <tr>를 제외한 모든 <tr> 초기화
          prodReviewList.children().remove();
        for (let i = 0; i < data.dtoList.length; i++) {
            prodReviewList.append(`
                <li>
                    <div>
                        <h5 class="rating star${data.dtoList[i].rating}"></h5>
                        <span>${data.dtoList[i].uid}  ${data.dtoList[i].rdate.substring(0, 10)}</span>
                    </div>
                    <h3>${data.dtoList[i].product.prodName}</h3>
                    <p>
                      ${data.dtoList[i].content}
                    </p>
                </li>
            `);
        }
        // 페이징
        const page = $('.paging');
        page.children().remove();

        if (data.prev) {
            page.append(`
                <span class="prev">
                    <a href="#" class="prev" onclick="listReload(data.start - 1); return false;"><&nbsp;이전</a>
                </span>
            `);
        }
        let pageStr = '<span class="num">';
        for (let i = data.start; i <= data.end; i++) {
            pageStr += '<a href="#" class="1 ' + (data.pg == i ? `on` : ``) + '" onclick="listReload(' + i + '); return false">' + i + '</a>';
        }
        pageStr += '</span>';
        page.append(pageStr);
        if (data.next) {
            page.append(`
                <span class="next">
                    <a href="#" class="next" onclick="listReload(data.end + 1); return false;">다음&nbsp;></a>
                </span>
            `);
        }
      }
  });
}

// 페이지 시작
$(function() {
    // 테이블 조회
    listReload(1);
});