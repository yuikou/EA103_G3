        function init() {
            var myFile = document.getElementById('myFile');
            var preview = document.getElementById('preview');

            /*註冊檔案上傳事件*/
            myFile.addEventListener('change', function(e) {
                //取得檔案物件
                var file = e.target.files;
                //判斷檔案是否存在
                if (file !== null) {
                    for (var i = 0; i < file.length; i++) {
                        //判斷file.type的型別是否包含'image', 不是的話要彈出警告
                        if (file[i].type.indexOf('image') > -1) {
                            //file物件存在:在FileReader物件上註冊load事件 - 載入檔案的意思
                            var fr = new FileReader();
                            fr.addEventListener('load', function(e) {
                                //取得結果
                                // console.log(e.target.result);

                                //新增img元素
                                var img = document.createElement('img');
                                //賦予屬性
                                img.setAttribute('src', e.target.result);

                                //新增一個div包住img
                                var box = document.createElement('div');
                                box.setAttribute('class', 'myReview');
                                box.append(img);
                                preview.append(box);
                            });
                            // 使用FileReader物件上的 readAsDataURL(file) 的方法，傳入要讀取的檔案，並開始進行讀取
                            fr.readAsDataURL(file[i]);
                        } else {
                            alert('請上傳圖片檔!');
                        }
                    }
                }

                /*
                解決input type=file 同一個檔案二次上傳無效的問題!
                使用input[type=file]的檔案上傳功能，是通過onchange事件觸發js程式碼, 如果兩次檔案是重複的，所以這個時候onchange事件是沒有觸發到的。解決方法 :  把input的value設定為空
                */
            });


            /*註冊刪除按鈕事件*/
            remove.addEventListener('click', function() {

                $("#remove").next(".myReview").remove();

            });

        }
        window.onload = init;