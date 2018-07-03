package com.example.dao;

import org.apache.ibatis.annotations.*;

import com.example.domain.Message;

import java.util.List;

@Mapper
public interface MessageDao {
	
	//**************查询部分,感脚还行***************
	
	/**
	 * 查询to toUid的所有tags
	 * @param toUid
	 * @return tag列表
	 */
	@Select("select * from Message where toUid=#{toUid} and isTag=1")
	List<Message> selectOnesTags(@Param("toUid") String toUid);
	
	/**
	 * 查询to tagid 的所有comments
	 * @param refMessageID
	 * @return comment列表
	 */
	@Select("select * from Message where refMessageID=#{refMessageID} and visibility=1")
	List<Message> selectTagsComments(@Param("refMessageID") int refMessageID);
	
	//******************插入部分非常不优雅,根本就是手写sql*******************
	
	/**
	 * 插入tag记录
	 * @param mtext
	 * @param msgType
	 * @param fromUid
	 * @param toUid
	 */
	@Insert("insert into Message(mtext,isTag,msgType,fromUid,toUid)"
					+ " values(#{mtext},1,#{msgType},#{fromUid},#{toUid})")
	int newTag(@Param("mtext") String mtext,
				@Param("msgType") int msgType,
				@Param("fromUid") String fromUid,
				@Param("toUid") String toUid);
	/**
	 * 插入对tag的评论
	 * @param refMessageID 被评论tagID
	 */
	@Insert("insert into Message(mtext,isTag,msgType,refMessageID,fromUid,toUid)"
					+ " values(#{mtext},0,#{msgType},#{refMessageID},#{fromUid},#{toUid})")
	int newCommentOnTag(@Param("mtext") String mtext,
						@Param("msgType") int msgType,
						@Param("refMessageID") int refMessageID,
						@Param("fromUid") String fromUid,
						@Param("toUid") String toUid);
	/**
	 * 插入对评论的评论
	 * @param refMessageID 被评论tagID
	 * @param reply2MsgID 被评论commentID
	 */
	@Insert("insert into Message(mtext,isTag,msgType,refMessageID,reply2MsgID,fromUid,toUid)"
					+ " values(#{mtext},0,#{msgType},#{refMessageID},#{reply2MsgID},#{fromUid},#{toUid})")
	int newCommentOnCmnt(@Param("mtext") String mtext,
						@Param("msgType") int msgType,
						@Param("refMessageID") int refMessageID,
						@Param("reply2MsgID") int reply2MsgID,
						@Param("fromUid") String fromUid,
						@Param("toUid") String toUid);
	
	//****************不优雅的like实现开始于此******************
	
	/**
	 * 插入like记录，key重复则更新likeFlag(column默认值1)
	 * 感觉这种太容易触发的沙雕功能用write behind cache做比较合适。。
	 */
	@Insert("insert into UserLikeMSG(uid,msgID) values(#{uid},#{msgID})"
			+ " on duplicate key update likeFlag=-likeFlag")
	int doULikeThis(@Param("uid") String uid,@Param("msgID") int msgID);
	/**
	 * 该留言likeCount+1
	 */
	@Update("update Message set likesCount=likesCount+1 where msgID=#{msgID}")
	int likeThisMessage(int msgID);
	/**
	 * 该留言likeCount-1
	 */
	@Update("update Message set likesCount=likesCount-1 where msgID=#{msgID}")
	int cacelLike(int msgID);
	
	//*********隐藏功能开始**********
	/**
	 * 用黑幕遮起来,假隐藏
	 * visibility默认值1 表示显示
	 * @param msgID
	 */
	@Update("update Message set visibility=-visibility where msgID=#{msgID}")
	int hideMSG(int msgID);
}
