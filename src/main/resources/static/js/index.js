$(document).ready(function () {
    $('#top').click(function() {
		window.scrollTo({top:0, behavior: "smooth"});
	});
    $(".slider > ul").bxSlider({
      easing: "linear",
    });
  });

  $(function () {
    var best = $("aside > .best");

    $(window).scroll(function () {
      var t = $(this).scrollTop();

      if (t > 620) {
        best.css({
          position: "fixed",
          top: "0",
        });
      } else {
        best.css({ position: "static" });
      }
    });
});