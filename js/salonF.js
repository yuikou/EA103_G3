        /*收藏按鈕被點擊時*/
        /*
         * action 1. 判斷空心/實心後轉成另一個
         * action 2. 開啟ajax連線controller, 進行insert/delete
         */

        function getAjax() {
            if ($("#followIcon").hasClass("fa-heart-o")) {
                /* 空心, 呼叫insert然後轉愛心為實心 */
                $.ajax({
                    type: "POST",
                    url: "<%=request.getContextPath()%>/salf/salf.do?action=insert",
                    data: {
                        "memNo": $("[name='memNo']").val(),
                        "salno=": $("[name='salno']").val()
                    },
                    success: function(data) {
                        if (data == 1) {
                            $("#followIcon").removeClass("fa-heart-o");
                            $("#followIcon").addClass("fa-heart");
                        }
                    }
                })

            } else { /* 實心, 呼叫delete然後轉愛心為空心 */
                $.ajax({
                    type: "POST",
                    url: "<%=request.getContextPath()%>/salf/salf.do?action=delete",
                    data: {
                        "memNo": $("[name='memNo']").val(),
                        "salno=": $("[name='salno']").val()
                    },
                    success: function(data) {
                        if (data == 0) {
                            $("#followIcon").removeClass("fa-heart");
                            $("#followIcon").addClass("fa-heart-o");
                        }
                    }
                })

            }
        }