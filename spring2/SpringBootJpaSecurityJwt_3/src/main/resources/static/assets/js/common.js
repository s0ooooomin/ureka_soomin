function getCsrfTokenFromCookie() {
  const match = document.cookie.match(/XSRF-TOKEN=([^;]+)/);
  return match ? decodeURIComponent(match[1]) : null;
}

async function csrfFetch(url, options = {}) {
  const csrfToken = getCsrfTokenFromCookie();

  // 기본 옵션
  const defaultOptions = {
    credentials: 'same-origin',
    headers: {}
  };

  // 사용자 옵션 병합
  const mergedOptions = {
    ...defaultOptions,
    ...options,
    headers: {
      ...defaultOptions.headers,
      ...(options.headers || {})
    }
  };

  // ★ GET 요청에는 CSRF 헤더를 안 붙여도 되지만 붙여도 안전함
  if (csrfToken) {
    mergedOptions.headers['X-XSRF-TOKEN'] = csrfToken;
  }

  return fetch(url, mergedOptions);
}


// logout
async function csrfLogout() {
	let response = await csrfFetch('/logout', {
    	method: 'POST'
	});

	let data = await response.json();
    console.log(data);

	if( data.result == "success" ){
    	window.location.href = '/login.html';
  	} else {
    	alert('로그아웃에 실패했습니다. 다시 시도해주세요.');
  	}
}
