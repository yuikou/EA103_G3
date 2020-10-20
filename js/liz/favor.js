function createXHR() {
  var xhr = null;
  if (window.XMLHttpRequest) {
    xhr = new XMLHttpRequest();
  } else if (window.ActiveXObject) {
    xhr = new ActiveXObject("Microsoft.XMLHTTP");
  }
  return xhr;
}

function getFavor() {
  xhr = createXHR();
  if (xhr == null) {
    alert("Does not support Ajax...");
    return;
  }
  //   console.log($("[name='sitNo']").val());
  //   console.log($("[name='action']").val());
  xhr.onload = function () {
    if (xhr.status == 200) {
      //   console.log("ajax回應：" + xhr.responseText);
      if (xhr.responseText == 1) {
        addFavor();
      } else if (xhr.responseText == 0) {
        delFavor();
      } else {
        alert("error");
      }
    } else {
      alert(xhr.status);
    }
  };
  var url = null;
  if ($(".myClick>span").hasClass("fa-heart")) {
    url =
      "sitFollow.do?action=del" +
      "&memNo=" +
      $("[name='memNo']").val() +
      "&sitNo=" +
      $("[name='sitNo']").val();
  } else {
    url =
      "sitFollow.do?action=add" +
      "&memNo=" +
      $("[name='memNo']").val() +
      "&sitNo=" +
      $("[name='sitNo']").val();
  }
  //   console.log(url);
  xhr.open("get", url, true);
  xhr.send(null);
}

// 加上追蹤
function addFavor() {
  $(".myClick").addClass("active");
  $(".myClick").addClass("active-2");
  setTimeout(function () {
    $(".myClick>span").addClass("fa-heart");
    $(".myClick>span").removeClass("fa-heart-o");
  }, 150);
  setTimeout(function () {
    $(".myClick").addClass("active-3");
  }, 150);
  $(".info").addClass("info-tog");
  setTimeout(function () {
    $(".info").removeClass("info-tog");
  }, 1000);
}

// 取消追蹤
function delFavor() {
  $(".myClick").removeClass("active");
  setTimeout(function () {
    $(".myClick").removeClass("active-2");
  }, 30);
  $(".myClick").removeClass("active-3");
  setTimeout(function () {
    $(".myClick>span").removeClass("fa-heart");
    $(".myClick>span").addClass("fa-heart-o");
  }, 15);
}

function addFavorOld() {
  $(".myClick").click(function () {
    if ($("span").hasClass("fa-heart")) {
      $(".myClick").removeClass("active");
      setTimeout(function () {
        $(".myClick").removeClass("active-2");
      }, 30);
      $(".myClick").removeClass("active-3");
      setTimeout(function () {
        $("span").removeClass("fa-heart");
        $("span").addClass("fa-heart-o");
      }, 15);
    } else {
      $(".myClick").addClass("active");
      $(".myClick").addClass("active-2");
      setTimeout(function () {
        $("span").addClass("fa-heart");
        $("span").removeClass("fa-heart-o");
      }, 150);
      setTimeout(function () {
        $(".myClick").addClass("active-3");
      }, 150);
      $(".info").addClass("info-tog");
      setTimeout(function () {
        $(".info").removeClass("info-tog");
      }, 1000);
    }
  });
}
