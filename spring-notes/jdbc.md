
## JDBC?
- Java에서 JDBC의 API를 통해 데이터베이스에 접근할 수 있도록 지원
- JDBC는 데이터베이스에 연결하고 쿼리를 실행하고 결과를 처리하는 방법을 제공

### 1. JDBC의 실행 순서
쿼리를 실행했을 때의 상황을 가정으로 설명을 해보자면

1. Driver 로딩
2. Connection 생성 (DB 연결)
3. Transaction 시작 (옵션)
4. Statement 또는 PreparedStatement 생성 (SQL 실행 준비)
5. 쿼리 실행 (SELECT, INSERT, UPDATE, DELETE)
6. ResultSet 처리 (SELECT 결과 처리)
7. Transaction Commit / Rollback (옵션)
8. 자원 해제 (ResultSet, Statement, Connection close)

의 순서로 진행된다.</br></br>


#### 1.1. Driver 로딩

- JDBC API에서 호출한 명령을 데이터베이스가 이해할 수 있는 프로토콜로 변환하여 SQL 명령을 실행하고 결과를 반환한다.
- SpringBoot 설정 예시(application.yml)
```yml
spring:
datasource:
url: jdbc:mysql://localhost:3306/mydb
username: user 
password: password
driver-class-name: com.mysql.cj.jdbc.Driver
```
- application.yml에서 설정한대로 Class.forName("com.mysql.cj.jdbc.Driver")을 호출하여 드라이버를 로딩한다.
</br></br>


#### 1.2. Connection 생성 (DB 연결)

- Connection은 데이터베이스와의 연결을 나타내는 객체이다.
```java
// DB Connection 객체 생성
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "user", "password");
```
- Spring에서는 HikariCP (Connection Pool)를 사용하여 자동으로 Connection을 관리한다. (springboot 2.0 이상 디폴트 설정)
```java
@Autowired
private DataSource dataSource;

Connection conn = dataSource.getConnection();
```
</br></br>


#### 1.3. Transaction 시작 (옵션)
- 데이터베이스 트랜잭션을 사용할 경우 setAutoCommit(false)를 호출하여 수동 커밋 모드로 변경.
```java
conn.setAutoCommit(false);
```
- Spring 어노테이션을 사용할 경우 @Transactional은 자동으로 트랜잭션을 관리한다.
</br></br>


#### 1.4. Statement 또는 PreparedStatement 생성 (SQL 실행 준비)

- Statement는 SQL 쿼리를 문자열로 작성하므로, 보안상 취약(sql Injection)하므로 사용하지 않는다.
```java
Statement stmt = conn.createStatement();
String sql = "SELECT * FROM users WHERE id = 1"; // sql 변수에 String 변형 가능 문제
ResultSet rs = stmt.executeQuery(sql);
```
- PreparedStatement는 파라미터화된 쿼리를 사용하여 보안강화 (sql injection 방지)
```java
PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
pstmt.setInt(1, 1);  // id 값 설정
ResultSet rs = pstmt.executeQuery();
```
</br></br>

#### 1.5 쿼리 실행 (SELECT, INSERT, UPDATE, DELETE)

- JDBC에서 SQL을 실행하는 방식은 다음과 같다.

| Execution Type | Method          | Description                      |
|----------------|-----------------|----------------------------------|
| SELECT         | `executeQuery()`| `ResultSet` 을 반환                 |
| INSERT/UPDATE/DELETE | `executeUpdate()` | 영향받은 행(row) 개수를 반환    |
| DDL (CREATE, DROP) | `execute()` | Us테이블 생성/삭제 시 사용 |

```java
// SELECT 실행
ResultSet rs = pstmt.executeQuery();

// INSERT 실행
int affectedRows = pstmt.executeUpdate();
```
</br></br>


#### 1.6 ResultSet 처리 (SELECT 결과)

- 데이터베이스에서 반환된 결과를 행(Row) 단위로 처리하며, 데이터를 읽고 사용할 수 있도록 지원한다.
```java
while (rs.next()) { // 한 줄씩 데이터를 가져옴
    int id = rs.getInt("id"); // 컬럼 값 조회
    String name = rs.getString("name");
    System.out.println("ID: " + id + ", Name: " + name);
}
```
</br></br>


#### 1.7 Transaction Commit / Rollback (옵션)

- 트랜잭션이 있는 경우, commit() 또는 rollback()을 호출합니다.
```java
try {
    conn.commit();  // 변경 사항 저장
} catch (SQLException e) {
    conn.rollback();  // 오류 발생 시 롤백
}
```
- Spring에서 @Transactional을 사용하면 자동으로 commit/rollback이 처리

</br></br>


#### 1.8 자원 해제 (ResultSet, Statement, Connection close)

- 모든 JDBC 객체(ResultSet, Statement, Connection)는 사용 후 반드시 자원 해제를 해야 한다.
```java
rs.close();
pstmt.close();
conn.close();
```
- Spring에서 @Autowired DataSource를 사용하면 자동으로 Connection을 관리해 준다.


### 2. 추가 내용

#### 2.1 PreparedStatement의 재사용 범위
- PreparedStatement는 쿼리를 미리 컴파일하여 캐시에 저장하므로, 동일한 쿼리를 여러 번 실행할 때 성능이 향상된다.
- 한 개의 트랜잭션 내에서 실행할 경우 동일한 SQL 쿼리를 재사용할 수 있다.
- 커넥션 풀을 사용할 경우(HikariCP, DHCP 등)
  - 커넥션 풀에서는 PreparedStatement Pooling을 지원하여 다른 트랜잭션에서도 동일한 SQL을 재사용할 수 있다.
  - 예를 들어 HikariCP는 prepStmtCacheSize를 설정하여 SQL이 미리 컴파일된 상태로 캐싱한다.
  - 동일한 SQL을 실행할 경우, DB에서 다시 컴파일하지 않고 캐싱된 쿼리를 재사용하여 성능 최적화를 한다.
  
</br></br>

#### 2.2 JDBC ResultSet에서 데이터를 어떻게 객체로 변환하는지
- 수동 변환 방식
```java
List<User> users = new ArrayList<>();
while (resultSet.next()) {
    User user = new User();
    user.setId(resultSet.getLong("id"));
    user.setName(resultSet.getString("name"));
    users.add(user);
}
```
- Spring의 JdbcTemplate을 이용한 변환 방식
```java
List<User> users = jdbcTemplate.query("SELECT * FROM users", (rs, rowNum) -> {
    return new User(
        rs.getLong("id"),
        rs.getString("name")
    );
});
```
- JPA를 사용하면 ResultSet을 직접 다룰 필요 없이 Entity로 매핑할 수 있다.
  - 내부적으로 다음 과정을 수행합니다.
  ```
  SELECT * FROM users WHERE id = 1; 실행
  ResultSet을 받아와서 해당 컬럼 데이터를 Entity의 필드에 자동 매핑
  User 객체를 생성하여 반환
  ```

</br></br>

