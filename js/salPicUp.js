function init() {
			var remove = document.getElementById('remove');
			var removeAll = document.getElementById('removeAll');
			var myFile = document.getElementById('myFile');
			var preview = document.getElementById('preview');
			var chk = document.getElementsByClassName('chk');
			var picked = document.getElementsByClassName('picked');
			//var Files = [];
			/*註冊檔案上傳事件*/
			myFile.addEventListener('change', function(e) {
				//取得檔案物件
				var files = myFile.files;
				//判斷檔案是否存在
				if (files != null) {
console.log(files.length);
					for (var i = 0; i < files.length; i++) {
						var file = files[i];
						//判斷file.type的型別是否包含'image', 不是的話要彈出警告
						if (file.type.indexOf('image') > -1) {
							//file物件存在:在FileReader物件上註冊load事件 - 載入檔案的意思
							var fr = new FileReader();
							fr.addEventListener('load', function(e) {
								//取得結果
								// console.log(e.target.result);
								//在img前面增加checkbox
								//新增checkbox元素
								//var targetIndex = Files.length;

								var chkbox = document.createElement('input');
								//賦予type屬性
								chkbox.setAttribute('type', "checkbox");
								chkbox.setAttribute('class', 'chk');
								//    chkbox.setAttribute('id', Files.length);

								//新增img元素
								var img = document.createElement('img');
								//賦予屬性
								img.setAttribute('src', e.target.result);
								
//								//新增文字輸入input
//								var textbox = document.createElement('input');
//								textbox.setAttribute('type', 'text');
//								textbox.setAttribute('name', 'salPortInfo');
//								textbox.setAttribute('class', 'picInfo');
								
								//新增一個div包住chkbox跟img
								var box = document.createElement('div');
								box.setAttribute('class', 'picked');
								box.append(chkbox, img);

								//將chkbox跟img寫入div
								preview.append(box);
								// Files.push(file);
							});
							// 使用FileReader物件上的 readAsDataURL(file) 的方法，傳入要讀取的檔案，並開始進行讀取
							fr.readAsDataURL(file);
						} else {
							alert('請上傳圖片檔!');
						}
					}
				}

			});

			/*註冊刪除按鈕事件*/
			remove.addEventListener('click', function() {

				for (var i = (chk.length - 1); i >= 0; i--) {
					let id = document.getElementById('id');
					var parentDiv = chk[i].closest('div');
					if (chk[i].checked) {
						parentDiv.remove();
						Files.splice(id, 1);
					}
				}
			});

			removeAll.addEventListener('click', function() {

				for (var i = (chk.length - 1); i >= 0; i--) {
					var parentDiv = chk[i].closest('div');

					parentDiv.remove();

				}
			});

		}