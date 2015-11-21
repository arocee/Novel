require(['jquery', 'jcrop', 'form'], function($, jc, fm) {

	//限制上传的文件大小，单位(k)
	var FileMaxSize = 1024 * 1024;
		origin_preview_img_file = $('#preview').attr('src'),
		origin_upload_img_file = $('#uploadedImg').attr('src'),
		requiredImageWidth = 120,
		requiredImageHeight = 160;

	var jcrop_api;

	var original_jcrop_width, // 选框的初始宽度
		original_jcrop_height; // 选框的初始高度

	var original_area_width = $('#uploadPhoto').width(),
		original_area_height = $('#uploadPhoto').height();

	var area_size = {
		width: original_area_width,
		height: original_area_height
	}

	/**
	*  添加图片
	**/
	$('#file').change(function(){
		var fileName = $(this).val();

		if(!fileName) return;

		// 检查文件格式
		if(!fileType(fileName)) {
			resetPre(PIC_ERROR_TYPE2);
			return;
		}
		// 检查文件大小
		if(getFileSize(this) > FileMaxSize) {
			resetPre(PIC_ERROR_TYPE3);
			return;
		}

		//销毁jcrop
		if(jcrop_api){
			jcrop_api.destroy();
		}

		/* 获取图片尺寸 */
		setImageSize(this, $('#uploadedImg').removeAttr('style'));

		imagePreview(this, $('#uploadedImg')); // 图片预览

		// 判断文件类型
		function fileType(fileName){
			var type = fileName.substring(fileName.lastIndexOf("\.") + 1, fileName.length);
			if (type.toUpperCase() == "GIF" || type.toUpperCase() == "JPG"|| type.toUpperCase() == "JPEG"|| type.toUpperCase() == "PNG"|| type.toUpperCase() == "BMP")
				return true;
			return false;
		}

		// 获取图片大小
		function getFileSize(file){
			var fileLenth = -1;
			try {
				//对于IE判断要上传的文件的大小
				var fso = new ActiveXObject("Scripting.FileSystemObject");
				fileLenth=parseInt(fso.getFile(file.value).size);
			} catch (e){
				try{
					//对于非IE获得要上传文件的大小
					fileLenth = parseInt(file.files[0].size);
				}catch (e){
					try{
						// IE6
						var filePath = file.value;
						var image = new Image();   
						image.dynsrc = filePath;   
						fileLenth = image.fileSize;
					}catch (e) {
						fileLenth = -1;           
					}
				}
			}
			return fileLenth;
		}

		// 设置图片宽高（Brower <= IE9）
		function setImageSize(file, $_img){
			var image = new Image();
			var path = file.value;

			if(typeof FileReader == "function" || window.URL || window.webkitURL){
				return;  // 高版本浏览器不需要设置
			} else {
				var size_img_file = $("<img src='/image/admin/touming.png' id='size_img_file' />");
				$('#size_image').append(size_img_file);

				if(size_img_file[0].filters && size_img_file[0].filters.item){
					try {
						size_img_file.get(0).filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = path;
						areaFix(size_img_file.width(), size_img_file.height());
					}
					catch (e){
						return false;
					} finally {
						size_img_file.remove();	
					}
				} else {
					image.onload = function(){
						areaFix();
						size_img_file.remove();	
					}
					// IE6
					image.src = path;
				}
			}
			
			function areaFix(w, h){
				if(!w)
					var w = image.width;
				if(!h)
					var h = image.height;

				var ratio = requiredImageWidth / requiredImageHeight;

				var scale = h / w;
				// TODO
				if(scale >= ratio) {
					area_size.height = original_area_height;
					area_size.width = original_area_height / scale;
				} else {
					area_size.width = original_area_width;
					area_size.height = original_area_width * scale;
				}

				$_img.width(area_size.width).height(area_size.height);
			}
		}

		function imagePreview(file, preImg){
			// 判断该浏览器是否为w3c标准
			if (file["files"] && file["files"][0]) {
				// 加入onerror监听函数
				preImg.on("error", function (){
					resetPre(PIC_ERROR_TYPE1);
				});

				if(preImg.is('#uploadedImg')) {
					preImg.one('load.pre', function(){
						imageLoaded(file);
					});
				}

				if(typeof FileReader == "function") {
					var reader = new FileReader();
					reader.onload = function(evt) {
						preImg.attr("src", evt.target.result);
					};
					reader.readAsDataURL(file.files[0]);
				} else if(window.URL || window.webkitURL){
					var URL = window.URL ? window.URL : window.webkitURL;
					var currentSrc = preImg.attr("src");
					if (currentSrc && currentSrc.indexOf("blob:") == 0){
						URL.revokeObjectURL(currentSrc);
					}
					var objectURL = URL.createObjectURL(file.files[0]);
					preImg.attr("src", objectURL);
				}
			}
			// 如果是低版本IE浏览器（IE9及以下），采用滤镜进行显示
			else {
				file.select();
				// 失去焦点。针对IE9下“拒绝访问”问题
				file.blur();
				path = document.selection.createRange().text;
				
				// 采用滤镜效果生成图片预览
				preImg.removeAttr('src');
				try {
					preImg[0].filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = path;
					preImg.attr("src", origin_upload_img_file); // 将img的src指向一张透明图，避免在IE中显示缺省图的问题。
					if(preImg.is('#uploadedImg')) {
						preImg.one('load.pre', function(){
							imageLoaded(file);
						});
					}
				} catch (e) {
					console.error(e);
					resetPre(PIC_ERROR_TYPE1);
				}
			}
		}

		function imageLoaded(file){
			imagePreview(file, $('#preview').width($('#uploadedImg').width()).height($('#uploadedImg').height()).css({
				'left': function(){
					return - ($(this).width() - $(this).parent('.previewWrapper').width()) / 2;
				},
				'top': function(){
					return - ($(this).height() - $(this).parent('.previewWrapper').width()) / 2;
				}
			}));
			
			var ratio = requiredImageWidth / requiredImageHeight;

			var filterPath; // 设置滤镜预览路径
			if($('#uploadedImg')[0].filters){
				filterPath = $('#uploadedImg')[0].filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src
			}

			$('#uploadedImg').Jcrop({
				aspectRatio: ratio,
				filterPath: filterPath,
				onChange: function(){
					var jcropBound = jcrop_api.tellSelect();
					var jcrop_width = jcropBound.w,
						jcrop_height = jcropBound.h,
						jcrop_x = jcropBound.x,
						jcrop_y = jcropBound.y;

					var scaledRatio = jcrop_width / original_jcrop_width;

					var w = $('#uploadedImg').width(),
						h = $('#uploadedImg').height();

					$('#preview').css({
						width: function(){
							return w * requiredImageWidth / jcropBound.w;
						},
						height: function() {
							return h * requiredImageHeight / jcropBound.h;
						},
						left: function(){
							return - jcrop_x * requiredImageWidth / jcrop_width;
						},
						top: function() {
							return - jcrop_y * requiredImageHeight / jcrop_height;
						}
					});

				}
			}, function(){
				jcrop_api = this;
			});

			var w = $('#uploadedImg').width(),
				h = $('#uploadedImg').height(),
				ratio_wh = w / h;

			var x0, y0, x1, y1;

			// 确定jcrop的位置
			if(w >= requiredImageWidth && h >= requiredImageHeight ){
				x0 = (w - requiredImageWidth) / 2,
				x1 = x0 + requiredImageWidth,
				y0 = (h - requiredImageHeight) / 2,
				y1 = y0 + requiredImageHeight;

				$('#preview').css({
					width: w,
					height: h,
					left: - x0,
					top: - y0
				});
			} else if(w < requiredImageWidth && h >= requiredImageHeight){
				var cropHeight = w / ratio;
				x0 = 0,
				x1 = w,
				y0 = (h - cropHeight) / 2,
				y1 = y0 + cropHeight;

				$('#preview').css({
					width: requiredImageWidth,
					height: requiredImageWidth / ratio_wh,
					left: 0,
					top: - y0 * ((requiredImageWidth / ratio_wh) / h)
				});
			} else if(w >= requiredImageWidth && h < requiredImageHeight) {
				var cropWidth = h * ratio;
				x0 = (w - cropWidth) / 2,
				x1 = w + cropWidth,
				y0 = 0,
				y1 = h;

				$('#preview').css({
					width: requiredImageHeight * ratio_wh,
					height: requiredImageHeight,
					left: - x0 * ((requiredImageHeight * ratio_wh) / w),
					top: 0
				});
			} else if(w < requiredImageWidth && h < requiredImageHeight) {
				if(ratio_wh > ratio){
					// 以高度为准 
					var cropWidth = h * ratio;
					x0 = (w - cropWidth) / 2,
					x1 = w + cropWidth,
					y0 = 0,
					y1 = h;

					$('#preview').css({
						width: requiredImageHeight * ratio_wh,
						height: requiredImageHeight,
						left: - (requiredImageHeight / h) * x0,
						top: 0
					});
				} else if(ratio_w = ratio) {
					x0 = 0,
					x1 = w,
					y0 = 0,
					y1 = h;

					$('#preview').css({
						width: requiredImageWidth,
						height: requiredImageHeight,
						left: 0,
						top: 0
					});
				} else if(ratio_wh < ratio) {
					// 以宽度为准
					var cropHeight = w / ratio;
					x0 = 0,
					x1 = w,
					y0 = (h - cropHeight) / 2,
					y1 = y0 + cropHeight;

					$('#preview').css({
						width: requiredImageWidth,
						height: requiredImageWidth / ratio_wh,
						left: 0,
						top: - (requiredImageWidth / w) * y0
					});
				}
			}
			
			jcrop_api.setSelect([x0,y0,x1,y1]);

			original_jcrop_width = x1 - x0, // 选框的初始宽度
			original_jcrop_height = y1 - y0; // 选框的初始高度
			
			// 居中
			$('.jcrop-holder').css({
				'left': '50%',
				'top': '50%',
				'margin-left': function(){
					return - $(this).width() / 2
				},
				'margin-top': function(){
					return - $(this).height() / 2
				}
			});
			// 隐藏错误提示
			$('#photoErrorTip').html('').hide();
			$('#upload').removeClass('disabled');
		}
	});

	/**
	* 点击上传
	*/
	$('#upload').click(function(){
		if($(this).is('.disabled')){
			return;
		}

		var selectedSize = jcrop_api.tellSelect();

		var height = $('#uploadedImg').height(), width = $('#uploadedImg').width();

		if(selectedSize.x == null || !selectedSize.y == null || selectedSize.x2 == null || selectedSize.y2 == null || selectedSize.w == null || selectedSize.h == null) {
			resetPre(PIC_ERROR_TYPE4);
			return;
		}
		if(selectedSize.w > $('#uploadedImg').width() || selectedSize.h > $('#uploadedImg').height()){
			resetPre(PIC_ERROR_TYPE6);
			return;
		}
		if(selectedSize.x < 0 || selectedSize.y < 0 || selectedSize.x2 < 0 || selectedSize.y2 < 0 
			|| selectedSize.x > selectedSize.x2 || selectedSize.y > selectedSize.y2
			|| selectedSize.w < 0 || selectedSize.h < 0) {
			resetPre(PIC_ERROR_TYPE6);
			return;
		}
		if(!$('#file').val()) {
			resetPre(PIC_ERROR_TYPE5);
			return;
		}

		$('#tou_form').find('input[name=left]').val(selectedSize.x).end()
			.find('input[name=top]').val(selectedSize.y).end()
			.find('input[name=areaWidth]').val(selectedSize.w).end()
			.find('input[name=areaHeight]').val(selectedSize.h).end()
			.find('input[name=width]').val(width).end()
			.find('input[name=height]').val(height);

		$('#tou_form').ajaxSubmit({
			timeout: 30000,
			beforeSend: function(){
				$('#upload').addClass('disabled');
				$('#cover').stop().fadeIn();
				$('#loading').stop().show();
			},
			success: function(data){
				if(data.success) {
					$('#upload').removeClass('disabled');
					$('#curPhoto').attr('src', '/upload/tou/' + data.src)
				} else {
					resetPre();
					alert(data.msg);
				}
			},
			error: function(){
				alert('上传图片失败');
			},
			complete: function(){
				$('#cover').stop().fadeOut();
				$('#loading').stop().hide();
			} 
		});
	});

	// 复位图片
	var PIC_ERROR_TYPE1 = "图片损坏，预览失败！";
	var PIC_ERROR_TYPE2 = "图片格式错误！";
	var PIC_ERROR_TYPE3 = "图片大小超过限制！图片最大不超过1MB。";
	var PIC_ERROR_TYPE4 = "缺失关键数据，无法上传！";
	var PIC_ERROR_TYPE5 = "没有可上传的图片！";
	var PIC_ERROR_TYPE6 = "参数有误，无法上传！";
	function resetPre(errorTip){
		//销毁jcrop
		if(jcrop_api){
			jcrop_api.destroy();
		}

		$('#upload').addClass('disabled');

		if(errorTip) {
			$('#photoErrorTip').html(errorTip).show();
		}

		$('#uploadedImg').off('.pre').removeAttr('style').attr('src', origin_upload_img_file);
		$('#preview').removeAttr('style').attr('src', origin_preview_img_file);

		$('#file').val('');
	}
});