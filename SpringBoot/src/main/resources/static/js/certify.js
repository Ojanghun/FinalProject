const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");

document.getElementById("certifyForm").addEventListener("submit", function(event) {
	event.preventDefault(); // 기본 submit 막기

	const formData = new FormData(this);

	$.ajax({
		url: "upload",
		type: "post",
		data: formData, // 보내는 데이터 form
		contentType: false, // 보내는 데이터 타입 false->"multipart/form-data"로 선언됩니다.
		processData: false, // 폼데이터가 name=값&name=값 형식으로 자동변경되는 것을 막아줍니다.
		beforeSend: function(xhr) {
			xhr.setRequestHeader(header, token); // ✅ 여기!
		},
		success: function(data) {
			if (data == "success") {
				alert("업로드가 완료되었습니다")
			} else if (data == "typeMismatch") {
				alert("동영상 파일만 업로드해주세요")
			} else if (data == "noRefundablePlan") {
				alert("환불 가능한 플랜이 없습니다")
			} else if (data == "alreadyRefunded") {
				alert("이미 환급 요청한 플랜입니다")
			}
		},
		error: function() { alert("업로드 에러 발생") }
	});
});
// 🔽 슬라이더 이미지 경로
const images = [
  "/images/certify_guide1.png",
  "/images/certify_guide2.png",
  "/images/certify_guide3.png",
  "/images/certify_guide4.png"
];

let currentIndex = 0;

function showImage(index) {
  const imgElement = document.getElementById("sliderImage");
  if (imgElement) {
    imgElement.src = images[index];
  }
}

function prevImage() {
  currentIndex = (currentIndex - 1 + images.length) % images.length;
  showImage(currentIndex);
}

function nextImage() {
  currentIndex = (currentIndex + 1) % images.length;
  showImage(currentIndex);
}

