package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.example.domain.Relation;

@Mapper
public interface RelationDao {
	/**
	 * 插入rel，仅包含fromUid和toUid
	 * @return 受影响行数
	 */
	@Insert("insert into relations(fromUid,toUid) values(#{fromUid},#{toUid}) on duplicate key update rCount=rCount+1")
	int insertR(@Param("fromUid") String fromUid,@Param("toUid") String toUid);
	
	/**
	 * 查询评论次数
	 * @param fromUid,toUid
	 * return rCount
	 */
	@Select("select rCount from relations where fromUid=#{fromUid} and toUid=#{toUid}")
	Integer selectRCount(@Param("fromUid") String fromUid,@Param("toUid") String toUid);
	
	/**
	 * 评论数+1
	 * @param fromUid,toUid
	 * @return 受影响行数
	 */
	@Update("update relations set rCount=rCount+1 where fromUid=#{fromUid} and toUid=#{toUid}")
	int updateR(@Param("fromUid") String fromUid,@Param("toUid") String toUid);
	
	/**
	 * 删rel,根据fromUid,toUid
	 * @return 受影响行数
	 */
	@Delete("delete from relations where fromUid=#{fromUid} and toUid=#{toUid}")
	void deleteR(@Param("fromUid") String fromUid,@Param("toUid") String toUid);
	
	/**
	 *查rel，根据toUid
	 */
	@Select("select fromUid,toUid,rCount from relations where toUid=#{toUid}")
	@Results({
		@Result(property="fromUid",column="fromUid"),
		@Result(property="toUid",column="toUid"),
		@Result(property="rCount",column="rCount"),
	})
	List<Relation> selectR2Uid(@Param("toUid") String toUid);
	
	
}
