var country = document.getElementById("country");
var district = document.getElementById("district");

country.addEventListener('change', function() {
	var putCountry = document.getElementById("putCountry");
	var countryValue = country.value;
	putCountry.value = countryValue;

});

district.addEventListener('change', function() {
	var putDistrict = document.getElementById("putDistrict");
	var districtValue = district.value;
	putDistrict.value = districtValue;
});


