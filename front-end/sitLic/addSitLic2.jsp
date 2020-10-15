<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitLic.model.*" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- �s���������ۮe�� -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<TITLE>�s�W�Ү�</TITLE>

<!-- �פJ�~��CSS -->
<c:set var="path" value="/EA103G3/front-end" />
<c:set var="cssPath" value="/EA103G3/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/bootstrap.min.css">    
<link rel="stylesheet" type="text/css" href="${cssPath}/Main.css">
<link rel="stylesheet" type="text/css" href="${path}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/animate.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/animsition.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/util.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/Petfect.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/pitSitterForm.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/sitLicAll.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">

</head>

<BODY>
    
<!------------------ ����body ------------------>
    <div class="sub-container">
		
<!-- �s�W�Ү� -->
	    <div class="container-login100 cover" style="padding: 0;">
	    	<div class="wrap-login100 mybody" style="padding: 0 20px;box-shadow: none;">
	            
				<!-- �H�U���o�s�W�ҮѪ��Ѽ� -->
				<div class="licBox">
					<div class="info txt1 myP">
						�ҷӦW��
                    </div>
				    <div class="wrap-input100 validate-input">
				       	<input class="input100" type="text" name="licName" value="�S�w�d���~�\�i��" readonly="readonly" style="margin-top:0">
				        <span class="focus-input100"></span>
				    </div>
				    <div class="info txt1 myP">
                    	�ҷӥ��Ĥ� <span>*</span>
                    	<div class="add-note">
                    		<span>�Y�ҷӦ����Ĥ���A�Щ�U�C�������J</span>
                    	</div>
                    </div>
				    <div class="wrap-input100">
				    	<input class="input100" type="date" name="licEXP" placeholder="�ҷӨ����" style="margin-top:0">
				        <span class="focus-input100"></span>
				    </div>
				    <div class="wrap-input100 hideDiv">
				       	<input class="input100" type="text" name="licStatus" placeholder="�ҷӪ��A">
				       	<span class="focus-input100"></span>
				    </div>
					<div class="wrap-input100 myP">
					   	<label class="myLabel">
					   		<span class="txt1 myP uploadTXT info">�W���d�������ҷ�</span>
					       	<img src="${path}/img/upload.svg" class="myUpload">
					       	<input class="myFile" type="file" name="licPic" style="display:none">
					   	</label>
					</div>
					<div class="wrap-input100 validate-input input100 myP preview" style="margin-top:0;padding-bottom:5px;" data-validate="�ФW���ҷӹϤ�">
					   	<input class="input100 hideDiv" type="text" id="licPic">
					   	<span class="focus-input100"></span>
					                         �Ϥ��w��<button class="login100-form-btn remove hideDiv">�R��</button>
					</div>
				</div>
			</div>
      	</div>
    </div>


<!-- �פJjs -->
	<c:set var="jsPath" value="/EA103G3/js/euphy" />
	<script src="${jsPath}/jquery-3.2.1.min.js"></script>
	<script src="${jsPath}/animsition.min.js"></script>
	<script src="${jsPath}/popper.js"></script>
	<script src="${jsPath}/bootstrap.min.js"></script>
	<script src="${jsPath}/main.js"></script>
	<!-- �W�ǹϤ��w�� -->
	<script type="text/javascript">
    	function init() {
        	var myFile = document.getElementsByClassName('myFile');
            var preview = document.getElementsByClassName('preview');
                
            /*���U�ɮפW�Ǩƥ�*/
            for (let i = 0; i < myFile.length; i++) {
            	let index = i;
                myFile[index].addEventListener('change', function (e) {
                	//���o�ɮת���
                    var file = e.target.files;
                    //�P�_�ɮ׬O�_�s�b
                   	//console.log(myFile[index].value);
                    // �C�����s�W�ǴN�M�Źw���Ϥ�(�u�W�Ǥ@�i)
                   	$('.myReview').remove();
                    
                   	//�P�_�ɮ׬O�_�s�b
                   	if (file !== null) {
                   		$("#licPic").val("���ɮ��o");
                       for (var i = 0; i < file.length; i++) {
                       //�P�_file.type�����O�O�_�]�t'image', ���O���ܭn�u�Xĵ�i
                       if (file[i].type.indexOf('image') > -1) {
                            //file����s�b:�bFileReader����W���Uload�ƥ� - ���J�ɮת��N��
                            var fr = new FileReader();
                            fr.addEventListener('load', function (e) {
								 // ���}�R�����s
                            	 $(".remove").removeClass("hideDiv");
                                 //�s�Wimg����
                                 var img = document.createElement('img');
                                 //�ᤩ�ݩ�
                                 img.setAttribute('src', e.target.result);
                                 img.classList.add("reviewImg");
    
                                 //�s�W�@��div�]��img
                                 var box = document.createElement('div');
                                 box.setAttribute('class', 'myReview');
                                 box.append(img);
                                 preview[index].append(box);
                             });
                             // �ϥ�FileReader����W�� readAsDataURL(file) ����k�A�ǤJ�nŪ�����ɮסA�ö}�l�i��Ū��
                             fr.readAsDataURL(file[i]);
                        } else {
                             alert('�ФW�ǹϤ���!');
                      	}
               		}
            	}
                        //e.target.value = ''
                        /*
                        	�ѨMinput type=file �P�@���ɮפG���W�ǵL�Ī����D!
                        	�ϥ�input[type=file]���ɮפW�ǥ\��A�O�q�Lonchange�ƥ�Ĳ�ojs�{���X, �p�G�⦸�ɮ׬O���ƪ��A�ҥH�o�Ӯɭ�onchange�ƥ�O�S��Ĳ�o�쪺�C�ѨM��k :  ��input��value�]�w����
                        	!!! �o��Part�N������F
                        */
           });
       }
                
       // ���U�R���ƥ�
       $('.remove').on('click', function(e){
           e.preventDefault();
           $(this).next(".myReview").remove();
           myFile[0].value='';
           $("#licPic").val("");
           // �����R�����s
      	   $(".remove").addClass("hideDiv");
       });

	}
    window.onload = init;
    </script>
    
</body>
</html>