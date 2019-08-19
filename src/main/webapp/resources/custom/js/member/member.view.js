//1.최초 시작시에 고객 거래 정보 불러오기(장바구니 제외)
$(document).ready(function(){
	$.ajax({
		type: 'get',
		url : '/minishop/member/getUserInfo.do',
		dataType : 'json',
		success : function(data){
			var coupon = data.map.couponList;
			if(coupon=='') $('#goCoupon').text('사용할 수 있는 쿠폰이 존재하지 않습니다');
			else {
				var couponNum = 0;
				$.each(coupon,function(index,items){
					if (items.personal_code!=null && items.personal_code!='') couponNum++; 
				});
				$('#goCoupon').text(couponNum+'개');		
			}
			var order = data.map.orderList;
			if(order=='') $('#orderlistPg').text('주문하신 내역이 없습니다');
			else {
				$('#orderlistPg').text(order.length+'건');
			}
		}				
	});
	
});

//2. 1:1문의창 띄우기
$('#personalQA').click(function(){
	var personalQAPop = window.open('/minishop/member/personalQAForm.do','1:1문의','width=455,height=455,resizable=no');
});

//3. 회원 탈퇴 요청 띄우기
$('#memberDelete').click(function(){
	var memberDeletePop = window.open('/minishop/member/memberDelete.do','회원탈퇴요청','width=455,height=515,resizable=no');
});

//4. 회원 정보 수정 바로가기
$('#memberModify').click(function(){
	window.location='/minishop/member/memberModifyForm.do';
});
