<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class='photoWrapper'>
	<div class='photoInner'>
		<form action="/Novel/admin/myinfo/uploadphoto/easy" id='tou_form' method="post" enctype="multipart/form-data">
			<input type='hidden' name='left' />
			<input type='hidden' name='top' />
			<input type='hidden' name='areaWidth' />
			<input type='hidden' name='areaHeight' />
			<input type='hidden' name='width' />
			<input type='hidden' name='height' />
			<div class='photoUploadArea'>
				<div class='uploadPhoto' id='uploadPhoto'>
					<img src='/image/admin/touming.png' id='uploadedImg' />
				</div>
				<div class='uploadHandle'>
					<div>
						<input type='file' id='file' name='tou' accept="image/*" /><br />
						<label for='file'>支持图片格式：jpg、jpeg、png、gif，建议尺寸：120 * 160</label>
					</div>
					<div>
						<a href='javascript:void(0);' id='upload' class='disabled'>点击上传</a>
						<p class='emptyTip' id='photoErrorTip'></p>
					</div>
				</div>
			</div>
			<div class='photoStatusArea'>
				<div class='preview'>
					<div class='previewWrapper'>
						<img src='/image/admin/preview.jpg' id='preview' />
					</div>
					<p>头像预览</p>
				</div>
				<div class='curPhoto'>
					<div>
						<img id='curPhoto' src='/upload/tou/${user.imgurl }' />
					</div>
					<p>当前头像</p>
				</div>
			</div>
			<span id='size_image'></span>
		</form>
	</div>
</div>