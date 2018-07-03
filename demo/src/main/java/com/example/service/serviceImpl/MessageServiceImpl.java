package com.example.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.MessageDao;
import com.example.dao.RelationDao;
import com.example.domain.Message;
import com.example.domain.Relation;
import com.example.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private RelationDao relationDao;
	
	//
	//private RedisTemplate redisTemplate;
	
	@Override
	public List<Message> getTagsByUid(String uid) {
		List<Message> lm = new ArrayList<Message>();
//		// 从缓存中获取tags
//		String key = "tagsList_" + uid;
//        ValueOperations<String, List> operations = redisTemplate.opsForValue();
//        // 命中缓存
//        boolean hasKey = redisTemplate.hasKey(key);
//        if (hasKey) {
//            lm = operations.get(key);
//            return lm;
//        }
//        // 从DB获取数据
        lm = messageDao.selectOnesTags(uid);
        //LOGGER.info(""+lm.isEmpty());
        //for(Message m:lm) if(m.getVisibility()==0) m.setText("Blocked by user");
		return lm;
	}

	@Override
	public List<Message> getCmntsByTid(int tid) {
		// TODO Auto-generated method stub
		//if(false);
		List<Message> lm = messageDao.selectTagsComments(tid);
		return lm;
	}

	@Override
	@Transactional
	public int newMessage(Message m) {
		// TODO 得，既然都开放api了，，只能全部验证啊。。。
		
		//以及评论次数判定
		if(relationDao.selectRCount(m.getFromUid(), m.getToUid())==null)
			relationDao.insertR(m.getFromUid(), m.getToUid());
		else if(relationDao.selectRCount(m.getFromUid(), m.getToUid())==4) return 3;
		//m是标签
		if(m.getIsTag()==1)
			messageDao.newTag(m.getMtext(), m.getMsgType(), m.getFromUid(), m.getToUid());
		//m不是标签
		else if(m.getRefMessageID()>0 && m.getReply2MsgID()>0){
			messageDao.newCommentOnCmnt(m.getMtext(), m.getMsgType(), m.getRefMessageID(), m.getReply2MsgID(), m.getFromUid(), m.getToUid());
		}
		else if(m.getRefMessageID()>0)
			messageDao.newCommentOnTag(m.getMtext(), m.getMsgType(), m.getRefMessageID(), m.getFromUid(), m.getToUid());
		//返回2，输入有问题
		else return 2;
		//人际关系记录
		relationDao.insertR(m.getFromUid(), m.getToUid());
		//一切正常
		return 1;
	}

	@Override
	@Transactional
	public void likeMSG(String fromUid, int msgID, boolean likeFlag) {
		// TODO Auto-generated method stub
		if(likeFlag){
			messageDao.doULikeThis(fromUid, msgID);
			messageDao.likeThisMessage(msgID);
		}else{
			messageDao.doULikeThis(fromUid, msgID);
			messageDao.cacelLike(msgID);
		}
	}

	@Override
	public void hideMSG(int msgID) {
		// TODO Auto-generated method stub
		messageDao.hideMSG(msgID);
	}
	
	@Override
	public String showCmnts(int tid){
		// TODO ******************非常需要更优雅的实现，可维护性低*******************
		StringBuffer commentsList = new StringBuffer("<script type='text/javascript'>$('a.reply2Cmnt').click(function(event){var cid = $(event.target).attr('href').slice(14);$('#replyForm form #refCmnt').attr('value',cid);event.preventDefault();});</script>");
		List<Message> lm = getCmntsByTid(tid);
		Map<String,Message> mm = new HashMap<String,Message>();
		for(Message m : lm){
			mm.put(m.getMsgID()+"" , m);
			commentsList.append("<div class='card'><div class='card-body'><div class='rol d-flex justify-content-between align-items-center w-100'>"+
										((m.getMsgType()==1)?"匿名用户":("<a class='col-auto mr-auto' href='/user/"+m.getFromUid()+"'><strong>"+m.getFromUid()+"</strong></a>"))+
										"<a role='button' class='btn btn-outline-primary btn-sm col-auto likeCmnt' href='/likeMsg/"+m.getMsgID()+"'>赞 <span class='badge badge-light'>"+m.getLikesCount()+"</span></a>"+
									"</div><p class='d-block'>");//还差两个</div>
			//为了显示'回复:uid'而付出的代价，数据库字段不是很合理
			if(m.getReply2MsgID()!=0){
				Message rpy = mm.get(m.getReply2MsgID()+"");
				commentsList.append("回复<a href='/user/"+rpy.getFromUid()+"'>"+rpy.getFromUid()+"</a>:"); // "<div class='refer'><div class='user-info'>"+rpy.getFromUid()+"</div><div class='comment'>"+rpy.getText()+"</div></div>");
			}
			commentsList.append(m.getMtext()+"</p>"+
									//回复按钮
									"<a role='button' class='btn btn-primary btn-sm col-auto reply2Cmnt' data-toggle='collapse' data-target='#replyForm' aria-expanded='false' aria-controls='replyForm' href='/newCommentOn/"+m.getMsgID()+"'>回复</a>"+
								"</div></div>");
		}
		if(lm.size()==0)
			commentsList.append("<p class='card-text'>还没有人留言，成为第一个留言的人吧</p>");
								//);
		return commentsList.toString();
	}
	
	@Override
	public List<Message> showCmnt(int tid){
		return getCmntsByTid(tid);
	}
}
