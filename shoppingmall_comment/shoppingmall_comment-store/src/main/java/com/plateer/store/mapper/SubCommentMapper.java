package com.plateer.store.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.plateer.domain.CommentRecommend;
import com.plateer.domain.SubComment;

@Mapper
public interface SubCommentMapper {

	List<SubComment> retrieveAll(String userId);
	
	void insert(SubComment comment);
	
	void modify(SubComment comment);
	
	void delete(String orderId);
	
	void recommend(SubComment comment);
	
	List<SubComment> retrieve(String goodsCode);
	
	List<SubComment> retrievePhoto(String goodsCode);
	
	List<SubComment> retrieveFilter(HashMap<String, String> filter);
	
	String retrieveGoodsCode(String orderId);
	
	CommentRecommend retrieveRecommend(CommentRecommend commentRecommend);
	
	void insertRecommend(CommentRecommend commentRecommend);
	
	SubComment retreiveSubComment(String orderId);
}
