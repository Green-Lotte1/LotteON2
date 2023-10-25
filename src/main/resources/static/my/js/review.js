// 테이블 조회
function tableReload(pg) {
    page = pg;
    const jsonData = {
        "pg" : pg
    }
    $.ajax({
      url: contextPath + '/my/reviewList',
      type: 'GET',
      data: jsonData,
      success: function(data) {
        // 리스트
        const reviewList = $('.reviewList');
        // 첫 번째 <tr>를 제외한 모든 <tr> 초기화
        reviewList.find('tr').slice(1).remove();
        for (let i = 0; i < data.dtoList.length; i++) {
            const dtoList = data.dtoList[i];
            const product = dtoList.product;
            reviewList.append(`
                <tr>
                    <td class="no">${data.dtoList.length - i}</td>
                    <td class="prodName">
                        <a href="${contextPath + '/product/view?prodNo=' + product.prodNo + '&cate1=' + product.prodCate1 + '&cate2=' + product.prodCate2}">
                            ${product.prodName}
                        </a>
                    </td>
                    <td class="content">${dtoList.content}</td>
                    <td class="score"><span class="rating star${dtoList.rating}">평점</span></td>
                    <td class="date">${dtoList.rdate.substring(0, 10)}</td>
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

// 페이지 시작
$(function() {
    // 테이블 조회
    tableReload(1);
});