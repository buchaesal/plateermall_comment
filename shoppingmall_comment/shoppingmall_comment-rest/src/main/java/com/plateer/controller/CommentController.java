package com.plateer.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.plateer.domain.CommentRecommend;
import com.plateer.domain.CommentStatus;
import com.plateer.domain.SubComment;
import com.plateer.domain.dto.CommentDto;
import com.plateer.service.logic.CommentServiceImpl;
import com.plateer.service.logic.CommentStatusServiceImpl;
import com.plateer.service.logic.SubCommentServiceImpl;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(allowCredentials = "true", origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT},
allowedHeaders = {"Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
        "Access-Control-Request-Headers", "Access-Control-Allow-Origin", "Set-Cookie", "Authorization"},
exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"}, maxAge = 3000)
public class CommentController {
	
	private S3Client s3Client;
	
	CommentServiceImpl commentServiceImpl;
	
	SubCommentServiceImpl subCommentServiceImpl;
	
	CommentStatusServiceImpl commentStatusServiceImpl;
	
	public CommentController(S3Client s3Client, CommentServiceImpl commentServiceImpl, SubCommentServiceImpl subCommentServiceImpl, CommentStatusServiceImpl commentStatusServiceImpl) {
	
		this.s3Client = s3Client;
		this.commentServiceImpl = commentServiceImpl;
		this.subCommentServiceImpl = subCommentServiceImpl;
		this.commentStatusServiceImpl = commentStatusServiceImpl;
	}
	
	@GetMapping("getcommentlist/{goodsCode}")
	public CommentDto getComment(@PathVariable("goodsCode") String goodsCode){
		
		return commentServiceImpl.retrieveComment(goodsCode);
	}
	
	@GetMapping("getmycomment/{userId}")
	public List<SubComment> getMyComment(@PathVariable("userId") String userId){
		
		return subCommentServiceImpl.retrieveAll(userId);
	}
	
	@GetMapping("getunwrittenorderid/{userId}")
	public List<String> getUnWrittenComment(@PathVariable("userId") String userId){
		
		return commentStatusServiceImpl.retrieveOrderIdList(userId);
	}
	
	@PostMapping
	public void addComment(@RequestBody SubComment comment) {
		
		subCommentServiceImpl.insertSubComment(comment);
	}
	
	@PutMapping
	public void modifyComment(@RequestBody SubComment comment) {
		
		System.out.println(comment);
		subCommentServiceImpl.modifySubComment(comment);
	}
	
	@PutMapping("recommendation")
	public void recommendComment(@RequestBody SubComment comment) {
		
		subCommentServiceImpl.recommendComment(comment);
	}
	
	@GetMapping("getfiltergoodsoption/{goodsCode}/{goodsOption}/{orderByOption}")
	public List<SubComment> getFilterGoodsOption(@PathVariable("goodsCode") String goodsCode, @PathVariable("goodsOption") String goodsOption,
			@PathVariable("orderByOption") String orderByOption) throws UnsupportedEncodingException{
		
		return subCommentServiceImpl.retrieveFilteredComments(goodsCode, goodsOption, orderByOption);
	}
	
	@DeleteMapping("/{orderId}")
	public void deleteComment(@PathVariable("orderId") String orderId) {
		
		subCommentServiceImpl.deleteSubComment(orderId);
	}

	@PostMapping("/addcommentstatus")
	public void addCommentStatus(@RequestBody CommentStatus status) {
		
		commentStatusServiceImpl.insertCommentStatus(status);
	}
	
	@GetMapping("/isrecommend/{orderId}/{email}")
	public Boolean isRecommend(@PathVariable("orderId") String orderId, @PathVariable("email") String email) {
		
		CommentRecommend recommend = new CommentRecommend(orderId, email);
		return subCommentServiceImpl.retrieveRecommend(recommend);
	}
	
	@PostMapping("/addrecommend")
	public void addRecommend(@RequestBody CommentRecommend commentRecommend) {
		
		subCommentServiceImpl.insertRecommend(commentRecommend);
	}
	
	@GetMapping("/getphotolist/{goodsCode}")
	public List<SubComment> getPhotoList(@PathVariable("goodsCode") String goodsCode){
		
		return subCommentServiceImpl.retrievePhoto(goodsCode);
	}
	
	@PostMapping("/upload")
	public String uploadTest() {
		
		File file = new File("D:\\brave.png");
		
		s3Client.fileUpload("brave.png", file);
		
		return "success";
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public List<String> uploadFiles(@RequestParam List<MultipartFile> files) throws Exception{
		
		List<String> list = new ArrayList<String>();
		
		for (MultipartFile file : files) {
			String originalfileName = file.getOriginalFilename();
			File dest = new File("D:\\images\\" + originalfileName);

			file.transferTo(dest);
		}
		
		return list;
	}
}
