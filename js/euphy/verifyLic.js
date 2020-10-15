function createXHR() {
  var xhr = null;
  if (window.XMLHttpRequest) {
    xhr = new XMLHttpRequest();
  } else if (window.ActiveXObject) {
    xhr = new ActiveXObject("Microsoft.XMLHTTP");
  }
  return xhr;
}

function verifySuccess() {
  xhr = createXHR();
  if (xhr == null) {
    alert("Does not support Ajax...");
    return;
  }
  xhr.onload = function () {
    if (xhr.status == 200) {
    	var resText = xhr.responseText;
    	if (resText.indexOf("error") == -1) {
    		$("#"+resText).remove();
          } else {
            alert(xhr.responseText);
          }
    } else {
      alert(xhr.status);
    }
  };
  var url = "/EA103G3/front-end/sitLic/sitLic.do?action=verify&licNo="+
  $("#successDiv input[name='licNo']").val() + "&licStatus=" +
  $("#successDiv input[name='licStatus']").val();
  
  xhr.open("get", url, true);
  xhr.send(null);
}

function verifyFail() {
	  xhr = createXHR();
	  if (xhr == null) {
	    alert("Does not support Ajax...");
	    return;
	  }
	  xhr.onload = function () {
	    if (xhr.status == 200) {
	    	var resText = xhr.responseText;
	    	if (resText.indexOf("error") == -1) {
	    		$("#"+resText).remove();
	          } else {
	            alert(xhr.responseText);
	          }
	    } else {
	      alert(xhr.status);
	    }
	  };
	  var url = "/EA103G3/front-end/sitLic/sitLic.do?action=verify&licNo="+
	  $("#failDiv input[name='licNo']").val() + "&licStatus=" +
	  $("#failDiv input[name='licStatus']").val();
	  
	  xhr.open("get", url, true);
	  xhr.send(null);
}

