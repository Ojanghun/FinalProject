// DB에 있는 자격증 목록 불러오기
// 자동완성 키워드가 들어있는 배열
const suggestions =
	$.ajax({
		url: search,
		type: get,
		success: data,
		error: function() { alert("error...") }
	});

const input = document.getElementById("search"); // 검색 바 요소
const suggestionBox = document.getElementById("suggestions"); // 자동완성 박스 요소

input.addEventListener("input", () => {
	const query = input.value.trim(); // 사용자가 입력한 검색어 추출
	suggestionBox.innerHTML = ""; // 자동완성 박스에 추가할 자격증 목록

	if (query.length === 0) return; // 아무것도 입력하지 않으면 함수 종료
	
	// 검색어 필터링
	// Array.prototype.filter()
	// -> 배열의 각 요소에 대해 콜백 함수를 실행해서 true인 것만 새 배열로 반환
	const matched = suggestions.filter(item => item.includes(query));

	matched.forEach(item => {
		const div = document.createElement("div");
		div.textContent = item;
		div.onclick = () => {
			input.value = item;
			suggestionBox.innerHTML = "";
		};
		suggestionBox.appendChild(div);
	});
});

document.addEventListener("click", (e) => {
	if (e.target !== input) suggestionBox.innerHTML = "";
});