
## Spring Annotation ?
- Spring Framework에서 Annotation은 Java 코드에 메타데이터를 추가하여, Spring Container에게 특정 작업을 수행하도록 지시하도록 합니다.
- Annotation을 사용하여 XML 설정 파일을 대체할 수 있어 코드를 간결하게 만들 수 있다.

</br></br>

### 컨트롤러 Annotation

1. @RestController
- @Controller와 @ResponseBody를 합친 어노테이션으로 HTTP 요청에 대한 응답을 JSON 형태로 반환한다.
2. RequestMapping
- 요청 URL을 매핑하는 어노테이션으로 클래스 레벨과 메서드 레벨에 사용할 수 있다.
3. @RestControllerAdvice // @ControllerAdvice와 @ResponseBody를 합친 어노테이션으로 예외 처리를 위한 클래스에 사용한다.
- @ControllerAdvice는 전역적으로 예외를 처리하는 클래스에 사용한다.
- @ResponseBody는 메서드의 반환 값을 JSON 형태로 반환한다.
- @ExceptionHandler는 특정 예외를 처리하는 메서드에 사용한다.
- @ResponseStatus는 응답 상태 코드를 지정하는 어노테이션
```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleEventNotFoundException(EventNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
```
4. Spring에서의 Cache Annotation 

@Cacheable: 캐시 채우기를 트리거합니다.

@CacheEvict: 캐시 삭제를 트리거합니다.

@CachePut: 메서드 실행을 방해하지 않고 캐시를 업데이트합니다.

@Caching: 메서드에 적용할 여러 캐시 작업을 다시 그룹화합니다.

@CacheConfig: 클래스 수준에서 몇 가지 공통적인 캐시 관련 설정을 공유합니다.
```java
// Reference: https://docs.spring.io/spring-framework/reference/integration/cache/annotations.html#cache-annotations-cacheable
@Cacheable(cacheNames="books", key="#isbn") // 특정 인수를 키로 사용
public Book findBook(ISBN isbn, boolean checkWarehouse, boolean includeUsed)

@Cacheable(cacheNames="books", key="#isbn.rawNumber") // 특정 인수의 필드를 키로 사용
public Book findBook(ISBN isbn, boolean checkWarehouse, boolean includeUsed)

@Cacheable(cacheNames="books", key="T(someType).hash(#isbn)") // 특정 인수의 메서드 호출을 키로 사용(ex. someType.hash(isbn))
public Book findBook(ISBN isbn, boolean checkWarehouse, boolean includeUsed)
```

</br></br>

### Lombok Annotation

1. @Data
- @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor를 한번에 사용하는 어노테이션
- @RequiredArgsConstructor는 final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 생성한다.
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
6. @ManyToOne
- 다대일 관계를 매핑할 때 사용하는 어노테이션
```java
@ManyToOne(fetch = FetchType.LAZY) // @ManyToOne은 기본적으로 EAGER로 설정되어 있으므로 LAZY로 설정할 수 있다.
@OneToMany(mappedBy = "team") // mappedBy 속성을 사용하여 양방향 매핑을 할 수 있다.
private Team team; // table 명 
```
7. @OneToMany
- 일대 다 관계를 매핑할 때 사용하는 어노테이션
- mappedBy 속성을 사용하여 양방향 매핑을 할 수 있다.
- cascade 속성을 사용하여 연관된 엔티티의 상태를 함께 변경.
- orphanRemoval 속성을 사용하여 연관된 엔티티를 삭제.
```java
private List<Member> members; // table 명
@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
```
8. @JoinColumn
- 외래키를 매핑할 때 사용하는 어노테이션
9. @JsonIgnore
- JSON 응답 시 특정 필드를 제외할 때 사용하는 어노테이션 (양방향 관계에서 순환 참조가 발생할 때 사용)
10. @EnableJpaAuditing
- Spring Data JPA에서 제공하는 엔티티 생성 및 수정 시간을 자동으로 관리하도록 도와준다.

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

### Spring Annotation

1. @PostConstruct
- 빈이 생성된 후 초기화 작업을 수행하는 메서드에 사용하는 어노테이션
- 빈 의존성 주입이 완료된 후 호출된다.
2. @EventListener(ApplicationReadyEvent.class)
- Spring 애플리케이션이 완전히 시작된 후 실행되는 메서드에 사용한다.

</br></br>
