<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<!-- bootstrap 4.x is supported. You can also use the bootstrap css 3.3.x versions -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	crossorigin="anonymous">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/5.0.9/css/fileinput.min.css"
	media="all" rel="stylesheet" type="text/css" />
<!-- the font awesome icon library if using with `fas` theme (or Bootstrap 4.x). Note that default icons used in the plugin are glyphicons that are bundled only with Bootstrap 3.x. -->
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
	crossorigin="anonymous">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<span class="myset"> <a
					href="<%=request.getContextPath()%>/back-end/backEnd_index.jsp">回首頁</a></span>
				<div id="cancelBtn">
					<button id="back" class="btn btn-outline-secondary">回上一頁</button>
				</div>
			</div>
			<div class="col-md-12">
				<label for="input-res-1">File Gallery</label>
				<form method="post"
					action="<%=request.getContextPath()%>/salalb/salalb.do"
					enctype="multipart/form-data" role="form">
					<div class="file-loading">
						<input id="input-res-1" name="salpic" type="file" multiple>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script src="<%=request.getContextPath()%>/js/util.js"></script>
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"
		crossorigin="anonymous"></script>
	<!-- piexif.min.js is needed for auto orienting image files OR when restoring exif data in resized images and when you
    wish to resize images before upload. This must be loaded before fileinput.min.js -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/5.0.9/js/plugins/piexif.min.js"
		type="text/javascript"></script>
	<!-- sortable.min.js is only needed if you wish to sort / rearrange files in initial preview. 
    This must be loaded before fileinput.min.js -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/5.0.9/js/plugins/sortable.min.js"
		type="text/javascript"></script>
	<!-- popper.min.js below is needed if you use bootstrap 4.x (for popover and tooltips). You can also use the bootstrap js 
   3.3.x versions without popper.min.js. -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
	<!-- bootstrap.min.js below is needed if you wish to zoom and preview file content in a detail modal
    dialog. bootstrap 4.x is supported. You can also use the bootstrap js 3.3.x versions. -->
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<!-- the main fileinput plugin file -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/5.0.9/js/fileinput.min.js"></script>
	<!-- following theme script is needed to use the Font Awesome 5.x theme (`fas`) -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/5.0.9/themes/fas/theme.min.js"></script>

	<script>
		$(document).ready(function() {
			$("#input-res-1").fileinput({
				uploadAsync : true,
				uploadUrl : "<%=request.getContextPath()%>/salalb/salalb.do",
				enableResumableUpload : true,
				initialPreviewAsData : true,
				maxFileCount : 5,
				theme : 'fas',
				deleteUrl : '/site/file-delete',
				allowedFileExtensions : [ 'jpg', 'png', 'gif' ],
				fileActionSettings : {
					showZoom : function(config) {
						if (config.type === 'image') {
							return true;
						}
						return false;
					}
				}
			});
			$("#input-res-1").on("filebatchuploadcomplete", function() {
				alert("上傳成功");
			});
			$("#input-res-1").on('fileerror', function(event, data, msg) {
				alert(data.msg);
				// tokenTimeOut(data);
			});
		});
	</script>
</body>
</html>