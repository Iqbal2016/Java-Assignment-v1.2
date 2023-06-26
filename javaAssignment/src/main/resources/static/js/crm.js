
alert($("#myFunction").val());
let selectEl = document.getElementById('#dropDownList');
selectEl.addEventListener('change', (e) => {
  if (e.target.value == 'Others') {
	alert(2222);
    //document.getElementById('txt-custom').style.display = 'block';
  } else {
   // document.getElementById('txt-custom').style.display = 'none';
    alert(333);
  }
});

$("#myFunction").change(function(){
  alert("The paragraph was clicked.");
});

$("#myFunction").on('change', 'input', function() {
 alert("The paragraph was clicked.");
});