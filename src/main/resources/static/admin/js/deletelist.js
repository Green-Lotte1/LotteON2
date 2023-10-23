function deleteSelectedProducts() {
    var selectedIds = [];
    var checkboxes = document.getElementsByName("chk");

    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            selectedIds.push(checkboxes[i].value);
        }
    }

    if (selectedIds.length === 0) {
        alert("삭제할 상품을 선택하세요.");
        return;
    }

    var confirmation = confirm("선택한 상품을 삭제하시겠습니까?");
    if (confirmation) {
        $.ajax({
            type: "POST",
            url: "/admin/product/delete",
            data: {chk: selectedIds},
            success: function(response) {
                alert(response);
                // 다시 로드하거나 필요한 동작을 수행
            }
        });
    }
}