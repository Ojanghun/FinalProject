document.getElementById("uploadBtn").addEventListener("click", uploadFile);

function uploadFile() {
	const fileInput = document.getElementById("fileInput");
	const formData = new FormData();
	formData.append("file", fileInput.files[0]);
	
	$.ajax({
		url: "upload",
		type: "post",
		data: formData, // 보내는 데이터 form
		contentType: false, // 보내는 데이터 타입 false->"multipart/form-data"로 선언됩니다.
		processData: false, // 폼데이터가 name=값&name=값 형식으로 자동변경되는 것을 막아줍니다.
		success: function(data){
			if(data=="success"){
				alert("업로드가 완료되었습니다")
			} else if(data=="typeMismatch"){
				alert("동영상 파일만 업로드해주세요")
			}
		},
		error: function() { alert("업로드 에러 발생") }

	});

}