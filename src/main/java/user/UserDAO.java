package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public UserDAO() {
		try {
			String driver = "oracle.jdbc.driver.OracleDriver"; // OracleDriver 클래스를 메모리에 로딩
			String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
			String dbID = "system"; // system 계정을 사용
			String dbPassword = "123456";
			Class.forName(driver); // OracleDriver 로딩
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword); // Java와 Oracle연결
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM bbsuser WHERE userID=?";
		try {
			pstmt = conn.prepareStatement(SQL); // prepareStatement에 SQL문 삽입
			pstmt.setString(1, userID); // userID=? 부분에 매개변수로 넘어온 userID를 넣어준다.
			rs = pstmt.executeQuery(); // 실행결과를 rs 객체에 대입
			if (rs.next()) {
				if (rs.getString(1).equals(userPassword))
					return 1;// 로그인성공
				else
					return 0;// 비밀번호 불일치
			}
			return -1;// 아이디가 없음
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2;// 데이터베이스 오류
	}
}