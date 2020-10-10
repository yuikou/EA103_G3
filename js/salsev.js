        $('#salsevname').change(function() {
            if ($(this).val() == '基礎美容') {
                $('#salsevInfo').attr('placeholder', '項目包含:除垢清潔、深層清潔、剪指甲、毛髮吹整等...');
            }
            if ($(this).val() == '精緻美容') {
                $('#salsevInfo').attr('placeholder', '除垢清潔、深層清潔、深層護毛、剪指甲、修腳底毛、修腹部毛、修屁股毛、手工毛髮吹整等...')
            }

        });