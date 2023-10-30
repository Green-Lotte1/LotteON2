// 페이지 시작
$(function() {
    $.ajax({
          url: contextPath + '/my/myInfo',
          type: 'GET',
          data: {},
          success: function(data) {
            $('#orderCount').text(data.orderCount.toLocaleString());
            $('#couponCount').text(data.couponCount.toLocaleString());
            $('#myPoint').text(data.myPoint.toLocaleString());
            $('#qnaCount').text(data.qnaCount.toLocaleString());
          }
    })
});