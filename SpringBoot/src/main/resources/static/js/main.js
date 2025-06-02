const licenseSearch = document.getElementById('license-search');
const licenseList = document.getElementById('license-list');

// Array.from()
// -> 문자열 등 유사 배열(Array-like) 객체나 이터러블한 객체를 배열로 만들어주는 메서드
const items = Array.from(licenseList.getElementsByTagName('a'));

// 검색창에 입력할 때 실행
licenseSearch.addEventListener('input', function() {
	// 검색한 키워드를 keyword라는 변수에 담기
	const keyword = this.value;

	items.forEach(item => { // 배열의 각 항목을 하나씩 꺼내서 item이라는 변수로 처리
		// item =>는 function(item)이랑 같음
		const text = item.innerText; // a태그의 text 컨텐츠

		// includes()
		// -> 문자열 안에 특정 문자열이 포함되어 있는지를 판단하여 true 또는 false를 반환
		// 삼항 연산자 : 조건 ? 참일 때 값 : 거짓일 때 값
		item.style.display = text.includes(keyword) ? 'inline' : 'none';
	});
});