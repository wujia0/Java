package com.yiibai.mybatis.main;

import java.io.Reader;
import java.text.MessageFormat;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.yiibai.mybatis.dao.UserDao;
import com.yiibai.mybatis.models.User;
public class Main {

	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;

	static {
		try {
			reader = Resources.getResourceAsReader("config/Configure.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static SqlSessionFactory getSession() {
		return sqlSessionFactory;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
		try {
			//User user = (User) session.selectOne(
			//		"com.yiibai.mybatis.models.UserMapper.getUserByID", 1);
			UserDao userdao = session.getMapper(UserDao.class);
			// 用户数据列表
			getUserList();
			// 插入数据
			testInsert();
			testUpdate();
			
			// 删除数据
			testDelete();
			
		} finally {
			session.close();
		}
	}
	
	public static void testInsert()
	{
		try
		{
			// 获取Session连接
			SqlSession session = sqlSessionFactory.openSession();
			// 获取Mapper
			UserDao userMapper = session.getMapper(UserDao.class);
			System.out.println("Test insert start...");
			// 执行插入
			User user = new User();
			user.setId(0);
			user.setName("Google");
			user.setDept("Tech");
			user.setWebsite("http://www.google.com");
			user.setPhone("120");
			userMapper.insertUser(user);
			// 提交事务
			session.commit();

			// 显示插入之后User信息
			System.out.println("After insert");
			getUserList();
			System.out.println("Test insert finished...");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// 获取用户列表
	public static void getUserList(){
		try
		{
			SqlSession session = sqlSessionFactory.openSession();
			UserDao iuser = session.getMapper(UserDao.class);
			// 显示User信息
			System.out.println("Test Get start...");
			printUsers(iuser.getUserList());
			System.out.println("Test Get finished...");
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void testUpdate()
	{
		try
		{
			SqlSession session = sqlSessionFactory.openSession();
			UserDao iuser = session.getMapper(UserDao.class);
			System.out.println("Test update start...");
			printUsers(iuser.getUserList());
			// 执行更新
			User user = iuser.getUser(1);
			user.setName("New name");
			iuser.updateUser(user);
			// 提交事务
			session.commit();
			// 显示更新之后User信息
			System.out.println("After update");
			printUsers(iuser.getUserList());
			System.out.println("Test update finished...");
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	// 删除用户信息
	public static void testDelete()
	{
		try
		{
			SqlSession session = sqlSessionFactory.openSession();
			UserDao iuser = session.getMapper(UserDao.class);
			System.out.println("Test delete start...");
			// 显示删除之前User信息
			System.out.println("Before delete");
			printUsers(iuser.getUserList());
			// 执行删除
			iuser.deleteUser(3);
			// 提交事务
			session.commit();
			// 显示删除之后User信息
			System.out.println("After delete");
			printUsers(iuser.getUserList());
			System.out.println("Test delete finished...");
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void printUsers(final List<User> users)
	{
		int count = 0;
		for (User user : users)
		{
			System.out.println(MessageFormat.format("============= User[{0}]=================", ++count));
			System.out.println("User Id: " + user.getId() +
					"| User Name: " + user.getName()+
					"| User Dept: " + user.getDept()+
					"| User Website: " + user.getWebsite());
		}
	}

}
