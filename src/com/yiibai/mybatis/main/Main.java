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
			// �û������б�
			getUserList();
			// ��������
			testInsert();
			testUpdate();
			
			// ɾ������
			testDelete();
			
		} finally {
			session.close();
		}
	}
	
	public static void testInsert()
	{
		try
		{
			// ��ȡSession����
			SqlSession session = sqlSessionFactory.openSession();
			// ��ȡMapper
			UserDao userMapper = session.getMapper(UserDao.class);
			System.out.println("Test insert start...");
			// ִ�в���
			User user = new User();
			user.setId(0);
			user.setName("Google");
			user.setDept("Tech");
			user.setWebsite("http://www.google.com");
			user.setPhone("120");
			userMapper.insertUser(user);
			// �ύ����
			session.commit();

			// ��ʾ����֮��User��Ϣ
			System.out.println("After insert");
			getUserList();
			System.out.println("Test insert finished...");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// ��ȡ�û��б�
	public static void getUserList(){
		try
		{
			SqlSession session = sqlSessionFactory.openSession();
			UserDao iuser = session.getMapper(UserDao.class);
			// ��ʾUser��Ϣ
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
			// ִ�и���
			User user = iuser.getUser(1);
			user.setName("New name");
			iuser.updateUser(user);
			// �ύ����
			session.commit();
			// ��ʾ����֮��User��Ϣ
			System.out.println("After update");
			printUsers(iuser.getUserList());
			System.out.println("Test update finished...");
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	// ɾ���û���Ϣ
	public static void testDelete()
	{
		try
		{
			SqlSession session = sqlSessionFactory.openSession();
			UserDao iuser = session.getMapper(UserDao.class);
			System.out.println("Test delete start...");
			// ��ʾɾ��֮ǰUser��Ϣ
			System.out.println("Before delete");
			printUsers(iuser.getUserList());
			// ִ��ɾ��
			iuser.deleteUser(3);
			// �ύ����
			session.commit();
			// ��ʾɾ��֮��User��Ϣ
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
