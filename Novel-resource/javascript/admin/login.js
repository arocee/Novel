require.config({
    //配置angular的路径
	baseUrl: '/javascript',
    paths:{
		"jquery": "jquery-1.11.1"
    }
});

require(['jquery'], function($) {

	$(function(){
		$('#sub').click(function(e){
			e.preventDefault();

			var username = $('#username').val(),
				password = $('#password').val();

			if(testUser(username, true) && testPass(password, true)) {
				$('form[name=loginForm]').submit();
			}
		});

		$('#username').change(function(){
			var username = $(this).val();
			testUser(username);
		});

		$('#password').change(function(){
			var password = $(this).val();
			testPass(password);
		});

		function testUser(username, isTip){
			if(!username) {
				isTip && $('#userTip').html('请输入用户名！');
				return false;
			} 
			if(username.length < 5 || username.length > 10){
				isTip && $('#userTip').html('用户名不正确');
				return false;
			} 
			if(!/^\w+$/.test(username)){
				isTip && $('#userTip').html('用户名不正确');
				return false;
			}

			$('#userTip').html('&nbsp;');
			return true;
		}

		function testPass(password, isTip){
			if(!password) {
				isTip && $('#passTip').html('请输入密码！');
				return false;
			} 
			if(password.length < 5 || password.length > 10){
				isTip && $('#passTip').html('密码不正确');
				return false;
			} 
			if(!/^\w+$/.test(password)){
				isTip && $('#passTip').html('密码不正确');
				return false;
			}

			$('#passTip').html('&nbsp;');
			return true;
		}
	});
});