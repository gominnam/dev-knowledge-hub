
## 잠금방식 (Lock)

### Lock?

- Ticket 예매일 경우 동시에 여러 사용자가 같은 좌석을 예매할 수 없도록 해야하는데 이때 잠금을 사용한다.
- 여기에서 락은 여러 스레드가 동시에 공유 자원에 접근하는 것을 막는 동기화 기법이다.
- 잠금 방식
    - 낙관적 잠금(Optimistic Lock)
    - 비관적 잠금(Pessimistic Lock)
    - Redis 기반 분산 잠금(Distributed Lock)

</br></br>

### 1. 낙관적 잠금(Optimistic Lock)

- 낙관적 잠금은 데이터 출동이 드물다는 가정하고, 감시 시점이 트랜잭션이 끝날 때 한다.
- Ticket 테이블에서 엔티티가 수정될 때마다 version 필드 값을 증가시켜 동기화 처리를 한다.
```java
// TicketRepository.java
@Lock(LockModeType.OPTIMISTIC)
Ticket findByTicketId(Long ticketId);

public class Ticket {
    ...(생략)
    
    @Version
    private int version; // 버전 필드 추가
}
```
- 트랜잭션이 완료 전에 다른 사용자가 업데이트하면 `OptimisticLockException`이 발생하여 중복 예약을 방지한다.

</br></br>

### 2. 비관적 잠금(Pessimistic Lock)

- 비관적 잠금은 데이터 충돌이 자주 발생한다는 가정하고, 트랜잭션 시작 시점에 잠금을 걸어 다른 사용자가 접근하지 못하도록 한다.
- SELECT ... FOR UPDATE 쿼리를 사용하여  다른 사용자가 해당 티켓을 변경할 수 없도록 합니다.
```sql
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("SELECT t FROM Ticket t WHERE t.id = :id")
Ticket findByIdForUpdate(@Param("id") Long id);

SELECT * FROM ticket WHERE ticket_id = 1 FOR UPDATE;
```
- 데이터가 잠겨있는 상태에서 다른 사용자는 쓰기 잠금이 해제될 때까지 대기하게 된다.
- 비관적 잠금은 Dead Lock이 생길 수 있는 단점이 있다. ex) 사용자 a가 1번 티켓 잠금, 2번 대기 / 사용자 b가 2번 티켓 잠금, 1번 대기 -> Dead Lock 발생
```java
// 대기하지 않고 바로 예외를 발생시키는 방법 (nativeQuery의 NOWAIT을 직접 사용)
@Query(value = "SELECT * FROM ticket WHERE ticket_id IN (:ticketIds) AND status = 'AVAILABLE' FOR UPDATE NOWAIT", nativeQuery = true)
List<Ticket> findAvailableTicketsWithLock(@Param("ticketIds") List<Long> ticketIds);
```

</br></br>

### 3. Redis 기반 분산 잠금(Distributed Lock)

- Redis를 사용하여 분산 환경에서 동시성 문제를 해결할 수 있다. (서버가 여러개인 경우)
```java
// 30초 후 자동 해제
Boolean locked = redisTemplate.opsForValue().setIfAbsent("lock:ticket:1", "locked", Duration.ofSeconds(30));
if(locked){
    // 잠금 성공 후 비지니스 로직 수행
} else{
    // 잠금 실패
    return false;    
}
```

