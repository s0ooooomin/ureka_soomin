이전 프로젝트와의 비교점 (JpaHtmlLogin&Register vs REST)
	1. Rest API 구현
	2. Entity 와 Dto 사용
		- SpringBootJpaCrudFindLombok : Dto 사용 X, Entity 만 사용		
		- SpringBootJpaCrudRegister : 응답에만 Dto 사용
		
		- 이 프로젝트에서는 응답에도 사용
			- Controller에서 파라미터를 처리하는 데 Dto 사용 (기존엔 entity 바로 받음)
				=> Dto -> entity (StudentDto -> Student)
			- 응답 처리도 Dto 사용
				=> Entity -> Dto (Student -> StudentDto)