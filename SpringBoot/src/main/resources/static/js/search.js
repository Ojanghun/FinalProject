// 자동완성 키워드가 들어갈 배열
let suggestions = []

$.ajax({
	url: "searchLicenses",
	type: "GET",
	success: function(data) { // DB에 있는 자격증 목록 불러오기
		// Array.map() -> 배열 요소 하나하나를 다른 값으로 변환해서 새 배열을 만듦
		suggestions = data.map(item => item.liName);
		initAutocomplete();
	},
	error: function() { alert("자격증 목록 불러오기 실패") }
});

function initAutocomplete() {
	const input = document.getElementById("search"); // 검색 바 요소
	const suggestionBox = document.getElementById("suggestions"); // 자동완성 박스 요소

	// 사용자가 검색바를 클릭했을 때 자격증 목록 보여주기 + 백스페이스로 값을 다 지웠을 때도 보여줘야 함
	input.addEventListener("click", () => {
		suggestionBox.style.visibility ='visible'
		// 클릭했을 때 검색바랑 suggestionBox가 비어있어야 실행되어야 함
		if (input.value == "" && suggestionBox.innerHTML == "") { 
			renderSuggestions(suggestions, input, suggestionBox);
		}
	})

	// 사용자가 검색어를 입력했을 때
	input.addEventListener("input", () => {
		const query = input.value.trim(); // 사용자가 입력한 검색어 추출
		suggestionBox.innerHTML = ""; // 자동완성 박스에 추가할 자격증 목록

		if (query.length === 0) return; // 아무것도 입력하지 않으면 함수 종료

		// 검색어 필터링
		// Array.prototype.filter()
		// -> 배열의 각 요소에 대해 콜백 함수를 실행해서 true인 것만 새 배열로 반환
		// DB에 있는 자격증 목록과 사용자가 입력한 값을 비교해서 일치한다면 matched 배열에 담기
		const matched = suggestions.filter(item => item.includes(query));

		renderSuggestions(matched, input, suggestionBox);
	});

	// 외부 클릭시 suggestionBox html 요소를 없애는게 아니라 숨겨야...
	document.addEventListener("click", (e) => {
		if (e.target !== input) suggestionBox.style.visibility ='hidden';
	});
}

function renderSuggestions(items, input, suggestionBox) {
	items.forEach(item => {
		const div = document.createElement("div");
		div.textContent = item;
		div.onclick = () => {
			// 클릭하면 검색 바 값을 해당 자격증 이름으로 변경
			input.value = item;
			suggestionBox.innerHTML = ""; // 자동완성 박스 초기화
		};
		suggestionBox.appendChild(div);
	});
}