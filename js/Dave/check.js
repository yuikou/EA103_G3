        // 題目：在UI上，請製作一個簡易的登入頁面，有帳號，密碼，與確認密碼的欄位，帳號與密碼都必須是長度為4~7的字串，確認密碼必須要跟密碼一致，alert顯示登入的結果。

        // 1. 抓取DOM元素
        var send = document.getElementById('send');


        // 2. 對按鈕進行註冊click事件
        send.addEventListener('click', function() {
            // 抓取其他DOM元素
            var account = document.getElementById('salAc');
            var pwd = document.getElementById('salPw');
            var pwd_confirm = document.getElementById('salPw2');

            // 抓取其他DOM元素上的數值
            var username = account.value.trim();
            var password = pwd.value.trim();
            var password_2 = pwd_confirm.value.trim();

            // 清除所有CSS的效果  提示：classList.remove('hightlight')
            var trs = document.getElementsByTagName('tr');
            for (var i = 0; i < trs.length; i++) {
                trs[i].classList.remove('hightlight');
            }

            // 對帳號進行驗證
            // 如果有問題，彈出視窗並highlight欄位  提示：classList.add('hightlight')
            if (username.length < 4 || username.length > 7) {
                alert('帳號必須是長度為4~7的字串');
                account.closest('tr').classList.add('hightlight');
                return;
            }

            // 對密碼進行驗證
            // 如果有問題，彈出視窗並highlight欄位  提示：classList.add('hightlight')
            if (password.length < 4 || password.length > 7) {
                alert('密碼必須是長度為4~7的字串');
                pwd.closest('tr').classList.add('hightlight');
                return;
            }


            // 對確認密碼進行驗證
            // 如果有問題，彈出視窗並highlight欄位  提示：classList.add('hightlight')
            if (password_2 !== password) {
                alert('確認密碼必須與密碼相同');
                pwd_confirm.closest('tr').classList.add('hightlight');
                return;
            }

            // alert() 正確的內容
            alert('您的帳號是：' + username + '，您的密碼是：' + password);
        });