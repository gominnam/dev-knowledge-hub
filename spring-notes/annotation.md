
## Spring Annotation ?
- Spring Framework에서 Annotation은 Java 코드에 메타데이터를 추가하여, Spring Container에게 특정 작업을 수행하도록 지시하도록 합니다.
- Annotation을 사용하여 XML 설정 파일을 대체할 수 있어 코드를 간결하게 만들 수 있다.

</br></br>

### 컨트롤러 Annotation

1. @RestController
- @Controller와 @ResponseBody를 합친 어노테이션으로 HTTP 요청에 대한 응답을 JSON 형태로 반환한다.
2. RequestMapping
- 요청 URL을 매핑하는 어노테이션으로 클래스 레벨과 메서드 레벨에 사용할 수 있다.

</br></br>

### Lombok Annotation

1. @Data
- @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor를 한번에 사용하는 어노테이션
2. @NoArgsConstructor
- 파라미터가 없는 기본 생성자를 생성하는 어노테이션
```java
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자를 protected로 설정할 수 있다.
@NoArgsConstructor(access = AccessLevel.PRIVATE) // 생성자를 private로 설정할 수 있다.
@NoArgsConstructor(access = AccessLevel.PUBLIC) // 생성자를 public으로 설정할 수 있다.
```
3. @AllArgsConstructor
- 모든 필드 값을 파라미터로 받는 생성자를 생성하는 어노테이션
4. @Builder
- 빌더 패턴을 사용할 수 있게 해주는 어노테이션
```java
User user = User.builder()
                .name("John")
                .age(30)
                .build();
```
5. @Getter
- 필드에 대한 getter 메서드를 생성하는 어노테이션
6. @Setter
- 필드에 대한 setter 메서드를 생성하는 어노테이션

</br></br>

### JPA Annotation
1. @Entity
- JPA에서 테이블과 매핑되는 객체를 정의하는 어노테이션
2. @MappedSuperclass
- 부모 클래스로 사용하고 싶은 경우 사용하는 어노테이션
3. @EntityListeners(AuditingEntityListener.class)
- 엔티티의 생명주기 이벤트를 수신하는 리스너를 지정하는 어노테이션
- AuditingEntityListener.class는 JPA에서 제공하는 엔티티 리스너로 엔티티의 생성일, 수정일을 자동으로 관리할 수 있다.
4. @Lob
- 데이터베이스의 BLOB, CLOB 타입과 매핑되는 어노테이션. 큰 데이터를 저장할 때 사용.
- CLOB(Character Large Object) : 문자열 데이터를 저장할 때 사용
- BLOB(Binary Large Object) : 이진 데이터를 저장할 때 사용
5. @Embedded
- 객체를 엔티티의 필드로 사용할 때 사용하는 어노테이션
- 별도의 테이블이 아닌 엔티티의 필드로 사용할 수 있다. (가독성 향상)

</br></br>

### 테스트 Annotation

1. @ExtendWith(MockitoExtension.class)
- JUnit 5와 Mockito를 함께 사용할 때 필요한 어노테이션으로 Mockito의 확장을 통해 목 객체(Mock Object)를 쉽게 생성하고 주입 할 수 있다.
2. @Mock
- Mockito에서 목 객체를 생성하기 위해 사용하는 어노테이션. 테스트 중에 외부 의존성을 모킹하여 실제 객체 대신 사용.
3. @InjectMocks
- Mockito에서 목 객체를 주입하기 위해 사용하는 어노테이션. 목 객체를 주입받을 필드에 사용.
4. @BeforeEach
- 각 테스트 메서드가 실행되기 전에 실행되는 메서드를 지정하는 어노테이션이다.
```java
@BeforeEach
void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
}
```

</br></br>
