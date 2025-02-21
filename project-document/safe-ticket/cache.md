
## Cache 사용

- https://github.com/f-lab-edu/safe-ticket/ 진행하면서 겪은 Cache 관련 내용을 정리한 문서입니다.
- 티켓 예매 서비스에서 순간적으로 트래픽이 몰리는 상황을 가정하였습니다.

### 1. 예매할 수 있는 티켓 정보를 조회할 경우 최적화 방법

- 조회 쿼리의 데이터들을 캐싱하는 방법 사용 (Database의 네트워크 I/O를 줄일 수 있음)
```java
// Ngrinder 테스트 결과 평균 TPS가 캐시를 사용하기 전 보다 약 2배 증가  
@Cacheable(value = "availableTickets", key = "#showtimeId")
@GetMapping("/available/{showtimeId}")
public ResponseEntity<AvailableTicketsDTO> getAvailableTickets(@PathVariable Long showtimeId) {
    return ResponseEntity.ok(ticketService.getAvailableTickets(showtimeId));
}
```

</br></br>

### 2. 하지만 예매를 하는 경우에는 데이터가 변경 될텐데..

- 예매를 하는 경우에 데이터가 변경되는데 어떻게 처리할 것인지에 대해서

1. Cache Evict 하는 방법
```java
// 1. 예매를 하는 경우 전체 캐시 삭제
@CacheEvict(value = "availableTickets")

// 2. 예매를 하는 경우 특정된 티켓 정보만 삭제
@CacheEvict(value = "availableTickets", key = "#ticketId")
```
- 위의 방법은 예매를 하는 경우에는 캐시를 삭제하는 방법이지만 순간적인 트래픽이 발생하는 경우 update가 자주 발생하므로 비효율적인 해결방법이다.

2. `TTL(Time To Live)`을 사용하는 방법
- 티켓 예매정보 조회는 데이터 일관성이 즉시 반영하지 않아도 된다고 판단하였다. 예매하는 비지니스 로직에서 한번더 티켓 상태를 검증하기 때문에 TTL을 사용하여 캐시를 삭제하는 방법을 사용하였다.
- TTL을 어떻게 해결할지 방법 모색
  - 1. @Scheduler를 사용하여 주기적으로 캐시를 삭제하는 방법 (이는 공연장마다 적용해야하기 때문에 제외)
  - 2. CacheManager의 EhCache를 사용하여 TTL을 적용하는 방법 (현재는 단일 서버이기 때문에 EhCache를 사용하여 TTL을 적용하는 방법을 사용하였다. 추가적으로 티켓 서버 리소스를 사용하는 것도 고려해야함)
  - 3. Redis를 사용하여 TTL을 적용하는 방법 (다중 서버 환경일 경우에 메모리 기반의 캐시 서버로 TTL을 적용하기에 적합하다.)
```java