# HappyScrolls

## 📝 Writing

### [1차 개발 후기](https://velog.io/@chs98412/1%EC%B0%A8-%EA%B0%9C%EB%B0%9C-%ED%9B%84%EA%B8%B0)
| 개발기  |
| ------ |
|[테스트 코드의 도입](https://velog.io/@chs98412/%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BD%94%EB%93%9C%EC%9D%98-%EB%8F%84%EC%9E%85)|
|[댓글과 대댓글의 구현](https://velog.io/@chs98412/%EB%8C%93%EA%B8%80%EA%B3%BC-%EB%8C%80%EB%8C%93%EA%B8%80%EC%9D%98-%EA%B5%AC%ED%98%84)|
|[jwt 필터 두 번 실행되는 문제](https://velog.io/@chs98412/jwt-%ED%95%84%ED%84%B0-%EB%91%90-%EB%B2%88-%EC%8B%A4%ED%96%89%EB%90%98%EB%8A%94-%EB%AC%B8%EC%A0%9C)|
|[성능 최적화](https://velog.io/@chs98412/%EC%84%B1%EB%8A%A5-%EC%B5%9C%EC%A0%81%ED%99%94)|
|[N+1 문제 해결기](https://velog.io/@chs98412/N1-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0%EA%B8%B0)|
|[연관관계 해소](https://velog.io/@chs98412/%EC%97%B0%EA%B4%80%EA%B4%80%EA%B3%84-%ED%95%B4%EC%86%8C)|
|[QueryDsl을 이용한 해결](https://velog.io/@chs98412/QueryDsl%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-%ED%95%B4%EA%B2%B0)|
|[간단한 리팩토링](https://velog.io/@chs98412/%EA%B0%84%EB%8B%A8%ED%95%9C-%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81)|
|[서비스 레이어 테스트 코드를 작성하면서 알게된 테스트 코드의 장점](https://velog.io/@chs98412/%EC%84%9C%EB%B9%84%EC%8A%A4-%EB%A0%88%EC%9D%B4%EC%96%B4-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BD%94%EB%93%9C%EB%A5%BC-%EC%9E%91%EC%84%B1%ED%95%98%EB%A9%B4%EC%84%9C-%EC%95%8C%EA%B2%8C%EB%90%9C-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BD%94%EB%93%9C%EC%9D%98-%EC%9E%A5%EC%A0%90)|
|[Jmeter를 통한 스트레스 테스트](https://velog.io/@chs98412/Jmeter%EB%A5%BC-%ED%86%B5%ED%95%9C-%EC%8A%A4%ED%8A%B8%EB%A0%88%EC%8A%A4-%ED%85%8C%EC%8A%A4%ED%8A%B8)|
|[아키텍처에 대한 고민](https://velog.io/@chs98412/%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98%EC%97%90-%EB%8C%80%ED%95%9C-%EA%B3%A0%EB%AF%BC-gy9biam4)|
|[event를 이용한 의존성 제거](https://velog.io/@chs98412/event)|
|[1차 개발 후기](https://velog.io/@chs98412/1%EC%B0%A8-%EA%B0%9C%EB%B0%9C-%ED%9B%84%EA%B8%B0)|
|[CQS 쿼리 명령 분리](https://velog.io/@chs98412/CQS-%EC%BF%BC%EB%A6%AC-%EB%AA%85%EB%A0%B9-%EB%B6%84%EB%A6%AC)|
|[Raw 타입 반환 리팩토링](https://velog.io/@chs98412/Raw-%ED%83%80%EC%9E%85-%EB%B0%98%ED%99%98-%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81)|



<div align="center">
  <img src="https://github.com/HappyScrolls/server/assets/79582366/6dce8b65-b114-4143-a3be-44d1ee63579a" width="300">
</div>

## 프로젝트 구조
![pzs](https://github.com/HappyScrolls/server/assets/79582366/2c602e63-066d-4798-b33d-8e749a1d4714)

- github action을 통해 배포 자동화를 구성하였습니다. </br> 
- aws 오토스케일을 통해 ec2 인스턴스의 cpu사용량이 증가하면 ec2 인스턴스가 추가되고, 도커로 컨테이너를 기반으로 스프링부트 어플리케이션이 가동됩니다. </br>
이렇게 생성된 여러개의 어플리케이션은 elb를 통해 로드밸런싱 됩니다. </br> 
- 레디스 서버를 통해 캐싱을 진행합니다. </br> 
- RDS의 서브넷을 프라이빗 서브넷으로 두어서 ec2 인스턴스가 아니면 접근할 수 없도록 하였습니다. </br> 

<div align="center">
  <img src="https://github.com/HappyScrolls/server/assets/79582366/af6540cd-463f-4577-92a7-b638554ed121" width="100">
</div>
테스트 코드를 작성하고 있으며, jacoco를 통해 커러비지를 측정하고 있습니다.



### 진행도
1차 개발 완료, 테스트 배포
