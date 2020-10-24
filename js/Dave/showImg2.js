        function readURL2(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function(a) {
                    $('#blah2').attr('src', a.target.result);
                }

                reader.readAsDataURL(input.files[0]); // convert to base64 string
            }
        }

        $("#myFile2").change(function() {
            readURL2(this);
        });